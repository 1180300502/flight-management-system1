package FlightApp;

import P1.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class FlightScheduleApp {

	private final List<Location> locations = new ArrayList<Location>();// 可支配的位置
	private final List<Plane> planes = new ArrayList<Plane>();// 可支配的资源
	public final List<FlightEntry> entries = new ArrayList<FlightEntry>();// 可使用的计划项

	public FlightScheduleApp() throws ParseException, IOException {
		System.out.println("-----航班管理系统-----");
		boolean over = false;
		int choise = 13;
		while (!over) {

			System.out.println("请选项您的下一步操作：");			
			System.out.println("1:添加位置");
			System.out.println("2:添加资源");
			System.out.println("3:添加航班");
			System.out.println("4:为航班分配飞机");
			System.out.println("5:取消指定航班");
			System.out.println("6:查看指定航班状态");
			System.out.println("7:设置信息版");
			System.out.println("8:删除位置");
			System.out.println("9:删除资源");
			System.out.println("10:检测资源独占冲突");
			System.out.println("11:寻找前序计划项");
			System.out.println("12:读取文件");
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
			if (planes.size() > 0) {
				flag =true;
				System.out.println("可支配配的航班信息：");
				for (int i = 0; i < planes.size(); i++)
					System.out.println(i + ":" + planes.get(i).toString());
			}
			if (entries.size() > 0) {
				flag =true;
				System.out.println("可使用的计划项信息：");
				for (int i = 0; i < entries.size(); i++)
					System.out.println(i + ":" + entries.get(i).getname()+"---------"+entries.get(i).gettimelots().toString());
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
				break;
			case 12:
				readfile();		
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

	private boolean readfile()throws IOException, ParseException{
		System.out.println("请输入你想读的文件编号：(编号从1到5，分别对应老师给出的5个文件)");
		int i=num(1,5);
		BufferedReader reader=new BufferedReader(new FileReader("src\\Flights\\FLightSchedule_"+i+".txt"));
        StringBuilder result=new StringBuilder();
        String theLine=null;
        i=0;
        while ((theLine = reader.readLine()) != null) {
			i++;// 记录行数
			result.append(theLine + "\n");
		}
		String s1 = result.toString();
		SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd");

		Pattern b = Pattern.compile("Flight:(\\d{4}-\\d{2}-\\d{2}),([A-Z]{2})(\\d{2,4})\\n"
				+ "\\{\\nDepartureAirport:([A-Za-z]+)\\nArrivalAirport:([A-Za-z]+)\\nDepatureTime:(\\d{4}-\\d{2}-\\d{2})"
				+ " (\\d{2}:\\d{2})\\nArrivalTime:(\\d{4}-\\d{2}-\\d{2}) (\\d{2}:\\d{2})\\nPlane:(B|N)"
				+ "(\\d{4})+\\n\\{\\nType:([A-Z0-9]+)\\nSeats:(\\d{1,3})\\nAge:(\\d+)(.\\d{1}|)\\n\\}\\n\\}\\n");
		Matcher matcher1 = b.matcher(s1);

		List<String> flightdate = new ArrayList<String>(); 
		List<String> flightname = new ArrayList<String>();
		List<String> start = new ArrayList<String>();
		List<String> over = new ArrayList<String>();
		List<String> starttime1 = new ArrayList<String>();
		List<String> starttime2 = new ArrayList<String>();
		List<String> overtime1 = new ArrayList<String>();
		List<String> overtime2 = new ArrayList<String>();
		List<String> starttime = new ArrayList<String>();
		List<String> overtime = new ArrayList<String>();
		List<String> planename = new ArrayList<String>();
		List<String> planemodel = new ArrayList<String>();
		List<String> planeseat = new ArrayList<String>();
		List<String> planeage = new ArrayList<String>();
		while (matcher1.find())// 处理，观察是否有数据非法
		{
			System.out.println("_____________");
			System.out.println(matcher1.group(1) + "\n" + // 日期
					matcher1.group(2) + matcher1.group(3) + "\n" + // 名字
					matcher1.group(4) + "\n" + // 出发机场
					matcher1.group(5) + "\n" + // 抵达机场
					matcher1.group(6) + " " + matcher1.group(7) + "\n" + // 出发时间
					matcher1.group(8) + " " + matcher1.group(9) + "\n" + // 抵达时间
					matcher1.group(10) + matcher1.group(11) + "\n" + // 飞机名
					matcher1.group(12) + "\n" + // 飞机型号
					matcher1.group(13) + "\n" + // 座位数
					matcher1.group(14) + "\n" +matcher1.group(15)+ "over\n");// 机龄
			flightdate.add(matcher1.group(1));// 日期
			flightname.add(matcher1.group(2) + Integer.parseInt(matcher1.group(3)));// 规范化航班号
			start.add(matcher1.group(4));
			over.add(matcher1.group(5));
			int m=0;
			{// 判断日期是否合理
				Date startDate = s2.parse(matcher1.group(6));
				Date endDate = s2.parse(matcher1.group(8));
				int betweenDate = (int) (endDate.getTime() - startDate.getTime()) / (60 * 60 * 24 * 1000);
				if (betweenDate < 0 || betweenDate > 1)// 相差不为一天，返回false
				{
					System.out.println("日期错误，时间间隔太长");
					return false;

				}
				if (matcher1.group(6).equals(matcher1.group(1)))// 与第一行不一致，返回false
					m++;
				else{
					System.out.println("日期错误！");
					return false;

				}
			}
			starttime1.add(matcher1.group(6));
			starttime.add(matcher1.group(6) + " " + matcher1.group(7));
			starttime2.add(matcher1.group(7));
			overtime1.add(matcher1.group(8));
			overtime.add(matcher1.group(8) + " " + matcher1.group(9));
			overtime2.add(matcher1.group(9));
			planename.add(matcher1.group(10) + matcher1.group(11));
			planemodel.add(matcher1.group(12));
			if (Integer.parseInt(matcher1.group(13)) > 600 || Integer.parseInt(matcher1.group(13)) < 50)// 与第一行不一致，返回false
			{
				System.out.println("座位数非法");
				return false;

			}
			planeseat.add(matcher1.group(13));
			if (matcher1.group(15).length()==2&&matcher1.group(14).indexOf("0") == 0 && matcher1.group(14).length() > 1) {
				System.out.println("机龄输入非法！");
				return false;

			}
			double age =0; 
			if(matcher1.group(15).length()==2)
				{
					Double.parseDouble(matcher1.group(14)+ matcher1.group(15));
					planeage.add(matcher1.group(14)+ matcher1.group(15));
				}
			if(matcher1.group(15).length()==0)
				{
					Double.parseDouble(matcher1.group(14));
					planeage.add(matcher1.group(14));
				}
			if (age < 0 || age > 30) {
				System.out.println("机龄数值非法！");
				return false;
			}
			int n = flightname.size() - 1;// 判断当前的航班数据是否和其他航班有冲突
			String name = flightname.get(n);
			String thisstarttime1 = starttime1.get(n);
			String thisovertime1 = overtime1.get(n);
			String thisstarttime2 = starttime2.get(n);
			String thisovertime2 = overtime2.get(n);
			String thisstart = start.get(n);
			String thisover = over.get(n);
			for (int j = 0; j < n - 1; j++) {
				if (flightname.get(j).equals(name)) {// 同名航班
					if (!start.get(j).equals(thisstart) || !over.get(j).equals(thisover)) {
						System.out.println("同名航班地址不匹配！");
						return false;
					}
					if (starttime1.get(j).equals(thisstarttime1) || overtime1.get(j).equals(thisovertime1)) {
						System.out.println("同名航班每天有两个日期！");
						return false;
					}
					if (!starttime2.get(j).equals(thisstarttime2) || !overtime2.get(j).equals(thisovertime2)) {
						System.out.println("同名航班到达时间不同！");
						return false;
					}
				}
			}
		}

		if (i / 13 > flightname.size() )// 每个信息项都有13行，如果行数除以13大于航班的数量，说明有的航班没有录上，输入非法
		{
			System.out.println("文件输入不符合规定，该文件无法读入！");
			System.out.println(i);
			System.out.println(flightname.size());
			return false;
		}

		
		//先加入地址
		Set<String> set =new HashSet<String>(start);
		set.addAll(over);
		int n=0;
		for(String a:set) {//加入所有地址，没有重复
			boolean flag =true;
			for(Location locat: locations)//不能加入同名站点
			{
				if(!locat.getname().equals(a))
					flag=true;
				else
					flag=false;
			}
			if(flag)
			locations.add(new FlightLocation(a));
		}

		//再加入飞机
		for(int j=0;j<planename.size();j++) {//加入所有飞机，没有重复
			boolean flag =true;
			String a=planename.get(j);
			for(Plane plane: planes)//不能加入同名飞机
			{
				if(!plane.getname().equals(a))
					flag=true;
				else
					flag=false;
			}
			if(flag) {
				Plane p=new Plane(planename.get(j),
						planemodel.get(j),
						Integer.parseInt(planeseat.get(j)),
						Double.parseDouble(planeage.get(j)));//加入飞机
				planes.add(p);
			}
			
		}
		for(int j=0;j<flightname.size();j++) {
			FlightEntry flight =new FlightEntry(flightname.get(j));
			for(Location locat: locations)//加入始发站
			{
				if(!locat.getname().equals(start.get(j)))
					n++;
				else{
					flight.addlocation(locat);
					break;
					}
			}
			for(Location locat: locations)//加入终点站
			{
				if(!locat.getname().equals(over.get(j))) 
					n++;
				else{
					flight.addlocation(locat);
					break;
				}
					
			}
			
			flight.addtimelot(new TimesLot(starttime.get(j),overtime.get(j)));//加入起止时间
			for(Plane plane: planes)//加入终点站
			{
				if(!plane.getname().equals(planename.get(j))) 
					n++;
				else{
					flight.addresource(plane);
					break;
				}
				
			}
			
			entries.add(flight);
		}
		
		// 判断是否有非法输入
		return true;
	}
        
	

	private void findearlyentry() {//寻找前序计划项
		if (entries.size() <= 0) {
			System.out.println("无计划项，无法检查！");
			return;
		}

		int i = 0;
		System.out.println("输入想要列出其所有资源的计划项的编号：");
		for (FlightEntry entry : entries) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, entries.size()-1);
		FlightEntry entry = entries.get(i);
		List<Plane> planelist = entry.getresources();
		if (planelist.size() <= 0) {
			System.out.println("未分配资源，无法检查！");
			return;
		}
		PlanningEntry<Plane> entry2 = (PlanningEntry<Plane>) entry;// 向上造型
		System.out.println("输入想要用来查找前序计划项使用的资源的编号：");
		i=0;
		for (Plane plane : planelist) {
			System.out.println("" + i + ":" + plane.toString());
			i++;
		}
		i = num(0, planelist.size()-1);
		Plane thisplane = planelist.get(i);

		List<PlanningEntry<Plane>> lister = new ArrayList<PlanningEntry<Plane>>();
		for (FlightEntry entry1 : entries) {
			lister.add(entry1);
		}

		PlanningEntry<Plane> p = (new PlanningEntryAPI<Plane>().findPreEntryPerResource(thisplane, entry2, lister));
		if (p != null)		
			System.out.println("前序计划项为：" + p.getname());
		else
			System.out.println("没找到前序计划项！");
	}

	private void resourcesconflict() {
		if (entries.size() <= 0) {
			System.out.println("无计划项，无法检查！");
			return;
		}
		List<PlanningEntry<Plane>> lister = new ArrayList<PlanningEntry<Plane>>();
		for (FlightEntry entry : entries) {
			lister.add(entry);
		}
		System.out.println("请选择策略模式：\n1：计划项表模式 \n2:计划项表+资源表模式");

		int n = num(1, 2);
		if (n == 1) {
			if (!new PlanningEntryAPI<Plane>().checkResourceExclusiveConflict(lister))
				System.out.println("有冲突！");

			else {
				System.out.println("无冲突！");
				return;
			}
		}
		if (n == 2) {
			if (!new PlanningEntryAPI<Plane>().checkResourceExclusiveConflict(planes, lister)) 
				System.out.println("有冲突！");

			else {
				System.out.println("无冲突！");
				return;
			}
		}

	}

	private void boardout() throws ParseException {// 选定时间地点创建表格
		if (entries.size() <= 0) {
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
			flag=true;
			time = a.nextLine();
			if (TimesLot.istime(time))// 若违规flag设置为false
				flag=true;
			else{
				System.out.println("输入格式错误，请重新输入");
				flag = false;
			}
		} 
		FlightBoard board = new FlightBoard(entries, location, time);
		System.out.println("表格已创建");
		System.out.println("遍历计划项：");
		Iterator<FlightEntry> iterator = board.iterator();
		// board 是一个类型为 Board 的对象
		while (iterator.hasNext()) {
			FlightEntry pe = iterator.next();
			System.out.println(pe.getname() + ":" + pe.getstarttimes() + "-" + pe.getovertimes());
		}

	}

	private String getstate() {// 获取计划项的状态

		if (entries.size() <= 0) {
			System.out.println("无计划项，无法分配");
			return "";
		}
		System.out.println("可获取的计划项如下：");
		int i = 0;
		for (FlightEntry entry : entries) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, entries.size()-1);
		FlightEntry entry = entries.get(i);

		System.out.println(entry + "状态为：" + entry.getstate().toString());
		return entry.getstate().toString();
	}

	public FlightEntry addentry(String name) {
		FlightEntry entry = new FlightEntry(name);
		entries.add(entry);
		return entry;
	}

	public boolean addresource(Plane plane) {
		if (!planes.contains(plane)) {
			planes.add(plane);
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

	public FlightEntry addentry() {// 用户输入一个计划项，并设置时间，地点
		if (locations.size() <= 1) {
			System.out.println("位置小于两个，无法添加航班");
			return null;
		}
		FlightEntry entry = new FlightEntry(locations);
		entries.add(entry);
		System.out.println("设置完成！");
		return entry;
	}

	public Plane addresource() {// 输入，添加一个资源
		Plane plane = new Plane();
		planes.add(plane);
		System.out.println("已加入资源！");
		return plane;
	}

	public Location addlocation() {// 输入一个新的位置
		FlightLocation location = new FlightLocation();
		locations.add(location);
		System.out.println("已加入位置！");
		return location;
	}

	public void assign() {// 为一个计划项分配资源，
		if (entries.size() <= 0) {
			System.out.println("无计划项，无法分配");
			return;
		}
		if (planes.size() <= 0) {
			System.out.println("无飞机，无法分配");
			return;
		}
		int i = 0;
		System.out.println("选择你的航班：");
		for (FlightEntry entry : entries) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, entries.size()-1);
		FlightEntry entry = entries.get(i);
		entry.addresource(planes);
		System.out.println("分配成功");
	}

	public void cancel() {// 取消某一航班
		if (entries.size() <= 0) {
			System.out.println("无计划项，无法分配");
			return;
		}
		int i = 0;
		System.out.println("输入航班号来取消：");
		for (FlightEntry entry : entries) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, entries.size()-1);
		FlightEntry entry = entries.get(i);
		entry.Cancel();
		System.out.println("取消成功");
	}

	public void deletresource() {// 删除某项资源
		if (planes.size() <= 0) {
			System.out.println("无飞机，无法删除");
			return;
		}
		int i = 0;
		System.out.println("输入飞机号来取消：");
		for (Plane plane : planes) {
			System.out.println("" + i + ":" + plane.toString());
			i++;
		}
		i = num(0, planes.size()-1);
		Plane plane = planes.get(i);
		planes.remove(plane);
		System.out.println("删除成功");
	}

	public void deletlocation() {// 取消某一位置
		if (locations.size() <= 0) {
			System.out.println("无位置，无法删除");
			return;
		}
		int i = 0;
		System.out.println("输入位置号来删除：");
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
		Logger logger = Logger.getLogger(FlightScheduleApp.class.getName());
		try {
			"".getBytes("invalidCharsetName");
		} catch (UnsupportedEncodingException e) {
			logger.severe(e.toString());
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] arg0) throws ParseException, IOException {
			FlightScheduleApp app = new FlightScheduleApp();
	}
	/*public class Main {
		public static void main(String[] args) {
			Logger logger = Logger.getLogger(Main.class.getName());
			logger.info("begin");
			try {
				"".getBytes("invalidCharsetName");
			} catch (UnsupportedEncodingException e) {
				logger.severe(e.toString());
				e.printStackTrace();
			}
			logger.info("end");
		}
	}*/
	
}