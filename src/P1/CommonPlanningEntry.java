package P1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JButton;


public abstract class CommonPlanningEntry<L> implements PlanningEntry<L>{
	private String entryname;// 计划项的名字
	private final List<L> resources = new ArrayList<L>();// 分配的资源表
	private final List<Location> locations = new ArrayList<Location>();// 位置列表
	private final List<TimesLot> timeslot = new ArrayList<TimesLot>();// 时间对列表
	private EntryState entrystate;
	private String loader = "";// 记录当前的toString
	public JButton button;
	
	private void checkRep() {//状态检查
		entrystate.checkRep();
	}

	public CommonPlanningEntry(String name) {//构造器
		entryname = name;
		entrystate=new EntryState();
	}

	public CommonPlanningEntry(List<Location> list) {// 输入名字,设定计划项的时间地点
		System.out.println("请输入计划项名字：");
		Scanner a = new Scanner(System.in);
		entryname =a.nextLine();
		entrystate=new EntryState();
		setEntry(list);
	}
	
	//抽象方法，后续子类具体实现
	abstract public void setEntry(List<Location> list);//设定时间地点
    abstract public void assign(L l);//分配资源

	// add方法，添加资源时间位置。
	public void addlocation(Location location) {
		locations.add(location);
	}

	public void addtimelot(TimesLot timelot) {
		timeslot.add(timelot);
	}

	public void addresource(L resource) {// 添加资源,同时分配资源
		resources.add(resource);
		entrystate.assign();
	}

	public boolean ok() {// 判断时间位置列表是否为空，决定了是否可以开始计划
		if (locations.size() != 0&&timeslot.size()!=0)
			return true;
		else
			return false;
	}

	
	public L addresource(List<L> list) {// 让用户选择资源加入
		System.out.println("请选择你想加入的资源（输入数字）：");
		Scanner a = new Scanner(System.in);
		int i = 0;
		for(i=0;i<list.size();i++) {
			System.out.println("" + i + ":" + list.get(i).toString());
		}
		boolean flag=false;
		i=0;
		while(!flag) {
			if (a.hasNextInt()) // 检测输入的是不是整型数字
				flag = false;
			else {
				System.out.println("输入格式错误，请输入整形数字");
				flag = false;
			}
			i = a.nextInt();
			int j=list.size()-1;
			if (flag && (i < 0 || i > list.size())) {// 检测输入的是不是范围内的整数
				System.out.println("输入数据超出范围，请重新输入，范围是0到"+j);
				flag = false;
			}
			if (flag && resources.contains(list.get(i))) {// 检测输入的是不是已经在列表中
				System.out.println("该资源已存在，请重新输入");
				flag = false;
			}
		}
		L l =list.get(i);
		resources.add(l);
		entrystate.assign();//将状态设置为分配了资源
		return l;
	}
	
	public Location addlocation(List<Location> list) {// 让用户选择地址加入,同上
		System.out.println("请选择你的地址（输入数字）：");
		Scanner a = new Scanner(System.in);
		int i = 0;
		for(i=0;i<list.size();i++) {
			System.out.println("" + i + ":" + list.get(i).toString());
		}
		boolean flag=false;
		i=0;
		while(!flag) {
			if (a.hasNextInt()) // 检测输入的是不是整型数字
				flag = true;
			else {
				System.out.println("输入格式错误，请输入整形数字");
				flag = false;
			}
			i = a.nextInt();
			int j=list.size()-1;
			if (flag && (i < 0 || i >= list.size())) {// 检测输入的是不是范围内的整数
				System.out.println("输入数据超出范围，请重新输入，范围是0到"+j);
				flag = false;
			}
			if (flag && locations.contains(list.get(i))) {// 检测输入的是不是已经在列表中
				System.out.println("该资源已存在，请重新输入");
				flag = false;
			}
		}
		Location l=list.get(i);
		locations.add(l);
		entrystate.assign();//将状态设置为分配了资源
		return l;

	}

	public void addtimelot() {// 让用户输入一个时间，前一个时间的开始不能大于后一个时间的结束，boolean代表是否有结束时间
		Scanner a = new Scanner(System.in);
		String starttime = null;
		boolean flag = false;
		System.out.println("时间格式为yyyy-mm-dd hh:mm，例如：“2019-12-05 13:23”");
		System.out.println("请输入开始时间：");
		while (!flag) {// 判断是否符合规则
			starttime = a.nextLine();
			if (TimesLot.istime(starttime))
				flag = true;
			else{
				System.out.println("输入时间不符合规则，请重新输入");
				flag = false;
			}
			if (timeslot.size() > 0 && flag) {// 若前一个结束时间大于开始时间，即当前列表前方有其他计划项还未结束，将flag设置为false
				TimesLot time = timeslot.get(timeslot.size() - 1);// 获取该计划项的末尾时间段
				if (TimesLot.comparetimestring(time.getovertime(), starttime))// 前一个结束时间大于当前开始时间
				{
					flag = false;
					System.out.println("当前输入时间早于上一个结束时间，请重新输入");
					System.out.println("上一个结束时间为："+time.getovertime()+"，请输入晚于该时间的数据");
				}
			}
		}
		String overtime = null;
		flag = false;
		System.out.println("请输入结束时间：");//同上
		while (!flag) {			
			overtime = a.nextLine();
			if (TimesLot.istime(overtime))
				flag=true;
			else{
				System.out.println("输入有语法错误！");
				flag = false;
			}
			if (timeslot.size() > 0 && flag) {//如果结束时间早于开始时间
				if (TimesLot.comparetimestring(starttime, overtime))
				{
					flag = false;
					System.out.println("结束时间早于开始时间，请重新输入");
					System.out.println("开始时间为："+starttime+"，请输入晚于该时间的数据");
				}
			}
		}
		TimesLot time = new TimesLot(starttime, overtime);
		timeslot.add(time);
	}

	// get方法，获取值：
	public EntryState getentrystate() {
		return entrystate;
	}

	public List<Location> getlocations() {
		checkRep();
		return locations;
	}

	public List<TimesLot> gettimeslot() {
		checkRep();
		return timeslot;
	}

	public List<L> getresources() {
		return resources;
	}

	public void changetostring(String s) {
		loader = s;
		checkRep();
	}

	// find方法，找到参数对应的东西
	public int findlocation(Location location) {// 找到location对应的次序,如果没有，则为-1
		return locations.indexOf(location);
	}

	public int findtime(String time) {// 找到time对应的时间段
		for (int i = 0; i < timeslot.size(); i++) {
			if (timeslot.get(i).contiantime(time))
				return i;
		}
		return -1;//如果没有，则返回-1
	}
	
	//方法重写
	@Override
	public void Run() {
		entrystate.run();
	}

	@Override
	public void Cancel() {
		entrystate.cancel();
	}

	@Override
	public void Block() {

	}

	@Override
	public void Commplete() {
		entrystate.complete();
	}

	@Override
	public String toString() {
		checkRep();
		return loader;
	}

	@Override
	public String getname() {
		return entryname;
	}

	@Override
	public String getstate() {
		return entrystate.toString();
	}	
}
