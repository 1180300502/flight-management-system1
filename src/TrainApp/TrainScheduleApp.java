package TrainApp;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Logger;

import P1.*;

public class TrainScheduleApp {

	private final List<Location> locations = new ArrayList<Location>();// 可支配的位置
	private final List<Train> trains = new ArrayList<Train>();// 可支配的资源
	public final List<TrainEntry> lists = new ArrayList<TrainEntry>();// 可使用的计划项

	public TrainScheduleApp() throws ParseException {
		System.out.println("-----高铁管理系统-----");
		boolean over = false;
		int choise = 12;
		while (!over) {
			System.out.println("请选项您的下一步操作：");
			System.out.println("1:添加位置");
			System.out.println("2:添加资源");
			System.out.println("3:添加车次");
			System.out.println("4:为车次分配车厢");
			System.out.println("5:取消指定车次");
			System.out.println("6:查看指定车次状态");
			System.out.println("7:设置信息版");
			System.out.println("8:删除位置");
			System.out.println("9:删除资源");
			System.out.println("10:检测资源独占冲突");
			System.out.println("11:寻找前序计划项");
			System.out.println("12:打印日志");
			System.out.println("13:退出");
			choise = num(1, 13);
			boolean flag =false;
			// 输出位置信息和资源信息
			if (locations.size() > 0) {
				flag=true;
				System.out.println("可支配的位置信息：");
				for (int i = 0; i < locations.size(); i++)
					System.out.println(i + ":" + locations.get(i).getname());
			}
			if (trains.size() > 0) {
				flag=true;
				System.out.println("可支配的车厢信息：");
				for (int i = 0; i < trains.size(); i++)
					System.out.println(i + ":" + trains.get(i).toString());
			}
			if (lists.size() > 0) {
				flag=true;
				System.out.println("可支配的车次信息：");
				for (int i = 0; i < lists.size(); i++)
					System.out.println(i + ":" + lists.get(i).getname());
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
				cancel();
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
				findearlyentry();
			case 12:
				print();
				break;
			case 13:
				over = true;
				break;
			default:
				break;
			}
		}

	}

	private void boardout() throws ParseException {// 选定时间地点创建表格
		if (lists.size() <= 0) {
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
		System.out.println("时间格式为yyyy-mm-dd hh:mm，例如：“2019-12-05 13:23”");
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
		TrainBoard board = new TrainBoard(lists, location, time);
		System.out.println("表格已创建");
		System.out.println("遍历计划项：");
		Iterator<TrainEntry> iterator = board.iterator();
		// board 是一个类型为 Board 的对象
		while (iterator.hasNext()) {
			TrainEntry pe = iterator.next();
			System.out.println(pe.getname() + ":" + pe.getlocations().get(0) + "-"
					+ pe.getlocations().get(pe.getlocations().size() - 1));
		}

	}
	
	private void findearlyentry() {
		if (lists.size() <= 0) {
			System.out.println("无计划项，无法检查！");
			return;
		}
		int i = 0;
		System.out.println("输入想要列出其所有资源的计划项的编号：");
		for (TrainEntry entry : lists) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, lists.size()-1);
		TrainEntry entry = lists.get(i);
		List<Train> Trainlist = entry.getresources();
		PlanningEntry<Train> entry2 = (PlanningEntry<Train>) entry;// 向上造型
		if (Trainlist.size() <= 0) {
			System.out.println("未分配资源，无法检查！");
			return;
		}
		System.out.println("输入想要用来查找前序计划项使用的资源的编号：");
		i=0;
		for (Train Train : Trainlist) {
			System.out.println("" + i + ":" + Train.toString());
			i++;
		}
		i = num(0, Trainlist.size()-1);
		Train thisTrain = Trainlist.get(i);

		List<PlanningEntry<Train>> lister = new ArrayList<PlanningEntry<Train>>();
		for (TrainEntry entry1 : lists) {
			lister.add(entry1);
		}

		PlanningEntry<Train> p = (new PlanningEntryAPI<Train>().findPreEntryPerResource(thisTrain, entry2,
				lister));
		if (p != null)		
			System.out.println("前序计划项为：" + p.getname());
		else
			System.out.println("没找到前序计划项！");
	}
	
	private void resourcesconflict() {
		if (lists.size() <= 0) {
			System.out.println("无计划项，无法检查！");
			return;
		}
		List<PlanningEntry<Train>> lister = new ArrayList<PlanningEntry<Train>>();
		for (TrainEntry entry : lists) {
			lister.add(entry);
		}
		System.out.println("请选择策略模式：\n1：计划项表模式 \n2:计划项表+资源表模式");
		int n = num(1, 2);
		if (n == 1) {
			if (!new PlanningEntryAPI<Train>().checkResourceExclusiveConflict(lister))
				System.out.println("有冲突！");

			else {
				System.out.println("无冲突！");
				return;
			}
		}
		if (n == 2) {
			if (!new PlanningEntryAPI<Train>().checkResourceExclusiveConflict(trains, lister)) 
				System.out.println("有冲突！");

			else {
				System.out.println("无冲突！");
				return;
			}
		}		
	}
	
	
	private String getstate() {// 获取计划项的状态

		if (lists.size() <= 0) {
			System.out.println("无计划项，无法分配");
			return "";
		}
		System.out.println("可获取的计划项如下：");
		int i = 0;
		for (TrainEntry entry : lists) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, lists.size()-1);
		TrainEntry entry = lists.get(i);

		System.out.println(entry + "状态为：" + entry.getstate().toString());
		return entry.getstate().toString();
	}
	
	public TrainEntry addentry(String name) {
		TrainEntry entry = new TrainEntry(name);
		lists.add(entry);
		return entry;
	}

	public boolean addresource(Train Train) {
		if (!trains.contains(Train)) {
			trains.add(Train);
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

	public TrainEntry addentry() {// 用户输入一个计划项，并设置时间，地点
		if (locations.size() <= 1) {
			System.out.println("位置小于两个，无法添加航班");
			return null;
		}
		TrainEntry entry = new TrainEntry(locations);
		lists.add(entry);
		System.out.println("设置完成！");
		return entry;
	}
	
	public Train addresource() {// 输入，添加一个资源
		Train Train = new Train();
		trains.add(Train);
		System.out.println("已加入资源！");
		return Train;
	}
	
	public Location addlocation() {// 输入一个新的位置
		TrainLocation location = new TrainLocation();
		locations.add(location);
		System.out.println("已加入位置！");
		return location;
	}

	public void assign() {// 为一个计划项分配资源，
		if (lists.size() <= 0) {
			System.out.println("无计划项，无法分配");
			return;
		}
		if (trains.size() <= 0) {
			System.out.println("无车厢，无法分配");
			return;
		}
		int i = 0;
		System.out.println("选择你的车次：");
		for (TrainEntry entry : lists) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, lists.size()-1);
		TrainEntry entry = lists.get(i);
		entry.addresources(trains);
		System.out.println("分配成功！");
	}
	
	public void cancel() {// 取消某一车次
		if (lists.size() <= 0) {
			System.out.println("无计划项，无法分配");
			return;
		}
		int i = 0;
		System.out.println("输入车次号来取消：");
		for (TrainEntry entry : lists) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, lists.size()-1);
		TrainEntry entry = lists.get(i);
		entry.Cancel();
		System.out.println("取消成功！");
	}

	public void deletresource() {// 删除某项资源
		if (trains.size() <= 0) {
			System.out.println("无车厢，无法删除");
			return;
		}
		int i = 0;
		System.out.println("输入车厢号来取消：");
		for (Train Train : trains) {
			System.out.println("" + i + ":" + Train.toString());
			i++;
		}
		i = num(0, trains.size()-1);
		Train Train = trains.get(i);
		trains.remove(Train);
		System.out.println("删除成功！");
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
		System.out.println("删除成功！");
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
		Logger logger = Logger.getLogger(TrainScheduleApp.class.getName());
		try {
			"".getBytes("invalidCharsetName");
		} catch (UnsupportedEncodingException e) {
			logger.severe(e.toString());
			e.printStackTrace();
		}
	}
	
	public static void main(String[] arg0) throws ParseException {
		TrainScheduleApp app = new TrainScheduleApp();
	}

}
