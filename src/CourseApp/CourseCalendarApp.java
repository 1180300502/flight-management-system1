package CourseApp;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.logging.Logger;

import P1.*;


public class CourseCalendarApp {

	private final List<Location> locations = new ArrayList<Location>();// 可支配的位置
	private final List<Teacher> Teachers = new ArrayList<Teacher>();// 可支配的资源
	public final List<CourseEntry> list = new ArrayList<CourseEntry>();// 可使用的计划项

	public CourseCalendarApp() {
		System.out.println("-----课程管理系统-----");
		boolean over = false;
		int choise = 13;
		while (!over) {
			System.out.println("请选项您的下一步操作：");
			System.out.println("1:添加教室");
			System.out.println("2:添加老师");
			System.out.println("3:添加课程");
			System.out.println("4:为课程分配老师");
			System.out.println("5:取消指定节课");
			System.out.println("6:查看指定课程状态");
			System.out.println("7:设置信息版");
			System.out.println("8:删除位置");
			System.out.println("9:删除老师");
			System.out.println("10:检测资源独占冲突");
			System.out.println("11:检测位置独占冲突");
			System.out.println("12:寻找前序计划项");
			System.out.println("13:打印日志");
			System.out.println("14:退出");
			// 录入整数
			choise = num(1, 14);
			boolean flag =false;
			// 输出位置信息和资源信息
			if (locations.size() > 0) {
				flag =true;
				System.out.println("可支配的位置信息：");
				for (int i = 0; i < locations.size(); i++)
					System.out.println(i + ":" + locations.get(i).getname());
			}
			if (Teachers.size() > 0) {
				flag =true;
				System.out.println("可支配的资源信息：");
				for (int i = 0; i < Teachers.size(); i++)
					System.out.println(i + ":" + Teachers.get(i).toString());
			}
			if (list.size() > 0) {
				flag =true;
				System.out.println("可支配的课程信息：");
				for (int i = 0; i < list.size(); i++)
					System.out.println(i + ":" + list.get(i).getname());
			}
			if(!flag)
				System.out.println("当前无可支配的位置/资源/计划项信息");
			// 执行
			switch (choise) {
			case 1:
				addlocation();
				break;
			case 2:
				addresource();
				break;
			case 3:
				addentry();
				break;
			case 4:
				assign();
				break;
			case 5:
				cancal();
				break;
			case 6:
				getstate();
				break;
			case 7:
				boardout();
				break;
			case 8:
				deletlocation();
				break;
			case 9:
				deletresource();
				break;
			case 10:
				resourcesconflict();
				break;
			case 11:
				locationconflict();
				break;
			case 12:
				findearlyentry();
				break;
			case 13:
				print();
				break;
			case 14:
				over = true;
				break;
			default:
				break;
			}
		}

	}

	private void boardout() {// 选定时间地点创建表格
		if (list.size() <= 0) {
			System.out.println("无计划项，无法显示时间板");
			return;
		}
		System.out.println("选择当前位置");
		int i = 0;
		for (Location location : locations) {
			System.out.println("" + i + ":" + location.toString());
			i++;
		}
		i = num(0, locations.size()-1);
		Location location = locations.get(i);
		Scanner a = new Scanner(System.in);
		String time=null;
		boolean flag=false;
		System.out.println("时间格式为yyyy-mm-dd hh:mm，如：“2020-10-06 04:55”");
		System.out.println("请输入当前时间：");
		while (!flag){// 判断是否符合规则
			time = a.nextLine();
			if (TimesLot.istime(time))// 若违规flag设置为false
				flag=true;
			else{
				System.out.println("输入格式错误，请重新输入");
				flag = false;
			}
		} 
		CourseBoard board = new CourseBoard(list, location, time);
		System.out.println("表格已创建");
		System.out.println("遍历课程：");
		Iterator<CourseEntry> iterator = board.iterator();
		// board 是一个类型为 Board 的对象
		while (iterator.hasNext()) {
			CourseEntry pe = iterator.next();
			System.out.println(pe.getname() + ":" + pe.getstarttime() + "-" + pe.getovertime());
		}

	}

	private void findearlyentry() {
		if (list.size() <= 0) {
			System.out.println("无计划项，无法检查！");
			return;
		}

		int i = 0;
		System.out.println("输入想要列出其所有资源的计划项的编号：");
		for (CourseEntry entry : list) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, list.size()-1);
		CourseEntry entry = list.get(i);
		List<Teacher> Teacherlist = entry.getresources();
		PlanningEntry<Teacher> entry2 = (PlanningEntry<Teacher>) entry;
		if (Teacherlist.size() <= 0) {
			System.out.println("未分配资源，无法检查！");
			return;
		}
		System.out.println("输入想要用来查找前序计划项使用的资源的编号：");
		i=0;
		for (Teacher Teacher : Teacherlist) {
			System.out.println("" + i + ":" + Teacher.toString());
			i++;
		}
		i = num(0, Teacherlist.size()-1);
		Teacher thisTeacher = Teacherlist.get(i);

		List<PlanningEntry<Teacher>> lister = new ArrayList<PlanningEntry<Teacher>>();
		for (CourseEntry entry1 : list) {
			lister.add(entry1);
		}

		PlanningEntry<Teacher> p = (new PlanningEntryAPI<Teacher>().findPreEntryPerResource(thisTeacher, entry2,
				lister));
		if (p != null)		
			System.out.println("前序计划项为：" + p.getname());
		else
			System.out.println("没找到前序计划项！");
	}

	private void locationconflict() {
	
		if (list.size() <= 0 ){
			System.out.println("无计划项，无法检查！");
			return;
		}
		List<PlanningEntry<Teacher>> lister = new ArrayList<PlanningEntry<Teacher>>();
		for (CourseEntry entry : list) {
			lister.add(entry);
		}

		if (!new PlanningEntryAPI<Teacher>().checkResourceExclusiveConflict(lister))
			System.out.println("有冲突！");

		else {
			System.out.println("无冲突！");
			return;
		}
	}

	private void resourcesconflict() {

		if (list.size() <= 0) {
			System.out.println("无计划项，无法检查！");
			return;
		}
		List<PlanningEntry<Teacher>> lister = new ArrayList<PlanningEntry<Teacher>>();
		for (CourseEntry entry : list) {
			lister.add(entry);
		}
		System.out.println("请选择策略模式：\n1：计划项表模式 \n2:计划项表+资源表模式");

		int n = num(1, 2);
		if (n == 1) {
			if (!new PlanningEntryAPI<Teacher>().checkResourceExclusiveConflict(lister))
				System.out.println("有冲突！");

			else {
				System.out.println("无冲突！");
				return;
			}
		}
		if (n == 2) {
			if (!new PlanningEntryAPI<Teacher>().checkResourceExclusiveConflict(Teachers, lister)) 
				System.out.println("有冲突！");

			else {
				System.out.println("无冲突！");
				return;
			}
		}
	}

	private String getstate() {// 获取计划项的状态

		if (list.size() <= 0) {
			System.out.println("无课程，无法分配");
			return "";
		}
		System.out.println("可获取的计划项如下：");
		int i = 0;
		for (CourseEntry entry : list) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, list.size()-1);
		CourseEntry entry = list.get(i);

		System.out.println(entry + "状态为：" + entry.getstate().toString());
		return entry.getstate().toString();
	}

	public CourseEntry addentry(String name) {
		CourseEntry entry = new CourseEntry(name);
		list.add(entry);
		return entry;
	}

	public boolean addresource(Teacher Teacher) {
		if (!Teachers.contains(Teacher)) {
			Teachers.add(Teacher);
			return true;
		}
		else
			return false;
	}

	public boolean addlocation(Location location) {		
		if (!locations.contains(location)) {
			locations.add(location);
			return true;
		}
		else
			return false;
	}

	public CourseEntry addentry() {// 用户输入一个计划项，并设置时间，地点
		if (locations.size() <= 0) {
			System.out.println("教室小于一个，无法添加课程");
			return null;
		}
		CourseEntry entry = new CourseEntry(locations);
		list.add(entry);
		System.out.println("设置完成");
		return entry;
	}

	public Teacher addresource() {// 输入，添加一个资源
		Teacher Teacher = new Teacher();
		Teachers.add(Teacher);
		System.out.println("已加入老师");
		return Teacher;
	}

	public Location addlocation() {// 键入一个新的位置
		CourseLocation location = new CourseLocation();
		locations.add(location);
		System.out.println("已加入教室");
		return location;
	}

	public void assign() {// 为一个计划项分配资源，
		if (list.size() <= 0) {
			System.out.println("无课程，无法分配");
			return;
		}
		if (Teachers.size() <= 0) {
			System.out.println("无老师，无法分配");
			return;
		}
		int i = 0;
		System.out.println("选择你的课程：");
		for (CourseEntry entry : list) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, list.size()-1);
		CourseEntry entry = list.get(i);
		entry.addresource(Teachers);
		System.out.println("分配成功");
	}

	public void cancal() {// 取消某一航班
		if (list.size() <= 0) {
			System.out.println("无计课程，无法分配");
			return;
		}
		int i = 0;
		System.out.println("输入课程编号来取消：");
		for (CourseEntry entry : list) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, list.size()-1);
		CourseEntry entry = list.get(i);
		entry.Cancel();
		System.out.println("取消成功！");
	}

	public void deletresource() {// 删除某项资源
		if (Teachers.size() <= 0) {
			System.out.println("无老师，无法删除");
			return;
		}
		int i = 0;
		System.out.println("输入老师号来删除：");
		for (Teacher Teacher : Teachers) {
			System.out.println("" + i + ":" + Teacher.toString());
			i++;
		}
		i = num(0, Teachers.size()-1);
		Teacher Teacher = Teachers.get(i);
		Teachers.remove(Teacher);
		System.out.println("删除成功");
	}

	public void deletlocation() {// 取消某一位置
		if (locations.size() <= 0) {
			System.out.println("无位置，无法删除");
			return;
		}
		int i = 0;
		System.out.println("输入位置号来取消：");
		for (Location location : locations) {
			System.out.println("" + i + ":" + location.getname());
			i++;
		}
		i = num(0, locations.size()-1);
		Location location = locations.get(i);
		locations.remove(location);
		System.out.println("删除成功");
	}

	static public int num(int a, int b) {// 要求输入数字，检测是否为范围内的整数
		Scanner s = new Scanner(System.in);
		boolean flag=false;
		int i = 0;
		while (!flag){
			if (s.hasNextInt()) // 检测输入的是不是整型数字
				flag = true;
			else {
				System.out.println("输入格式错误，只能输入整型");
				flag = false;
			}
			i = s.nextInt();
			if (flag && (i < a || i > b)) {// 检测输入的是不是范围内的整数
				System.out.println("输入数据超出范围，请重新输入");
				flag = false;
			}
		}
		return i;
	}
	
	public static void print() {
		Logger logger = Logger.getLogger(CourseCalendarApp.class.getName());
		try {
			"".getBytes("invalidCharsetName");
		} catch (UnsupportedEncodingException e) {
			logger.severe(e.toString());
			e.printStackTrace();
		}
	}

	public static void main(String[] arg0) {
		CourseCalendarApp app = new CourseCalendarApp();

	}

}
