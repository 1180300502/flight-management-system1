package FlightApp;

import P1.Location;
import P1.TimesLot;
import java.awt.BorderLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;

public class FlightBoard extends JFrame {// 令board继承
	private static final long serialVersionUID = 1L;
	private List<FlightEntry> list = new ArrayList<FlightEntry>();
	private List<FlightEntry> listarrive = new ArrayList<FlightEntry>();// 降落名单
	private List<FlightEntry> listgo = new ArrayList<FlightEntry>();// 起飞名单
	private Location location;// 当前地点
	private String time;// 当前时间
	
	//构造器
	public FlightBoard(List<FlightEntry> entry, Location location, String time) throws ParseException {		
		this.list=entry;
		this.location = location;
		this.time = time;
		find();// 放进名单
		visualize();// 表格可视化
	}

	public void find() throws ParseException {// 将计划项按要求放进名单中
		for (FlightEntry entry : list) {
			if (!entry.getentrystate().getstate().isEmpty()&&!entry.getentrystate().getstate().equals("unassigned")) {//如果计划项不是处于未分配状态且非空
				System.out.println(entry.getstartlocations()+"||"+entry.getstarttimes()+"---"+time);
				if (entry.getoverlocations()==location&&(inonehour(entry.getovertimes(),time)||(entry.gettimelots().contiantime(time)&&inonehour(time,entry.getovertimes())))) {// 加入前后1小时内该地降落的飞机
					listarrive.add(entry);
					System.out.println("in!");
				}
				if (entry.getstartlocations()==location&&(inonehour(time,entry.getstarttimes())||(entry.gettimelots().contiantime(time)&&inonehour(entry.getstarttimes(),time)))) {// 加入前后1小时内该地起飞的飞机
					listgo.add(entry);
					System.out.println("out!");
				}
			}
		}
	}

	static public boolean inonehour(String a, String b) throws ParseException {// 判断b是否在a的后一个小时内
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date startdate = s.parse(a);
		Date enddate = s.parse(b);
		long betweendate = (enddate.getTime() - startdate.getTime())/(60*1000);
		if((int)betweendate>60||(int)betweendate<0)
		return false;
		
		return true;
	}

	
	public Iterator<FlightEntry> iterator(){
		Collections.sort(list, new Comparator<FlightEntry>() {//给所有时间段按开始时间排序
	        @Override
	        public int compare(FlightEntry m, FlightEntry n) {
	        return (int) (m.gettimeslot().get(0).getstarttimelong()-n.gettimeslot().get(0).getstarttimelong());
	        }
	    });
		
		return list.iterator();
	}
	
	private void compare() {//将计划项按时间排序
		Collections.sort(listarrive, new Comparator<FlightEntry>() {//给所有时间段按开始时间排序
	        @Override
	        public int compare(FlightEntry m, FlightEntry n) {
	        return (int) (m.gettimeslot().get(0).getovertimelong()-n.gettimeslot().get(0).getovertimelong());
	        }
	    });
		
		Collections.sort(listgo, new Comparator<FlightEntry>() {//给所有时间段按开始时间排序
	        @Override
	        public int compare(FlightEntry m, FlightEntry n) {
	        return (int) (m.gettimeslot().get(0).getstarttimelong()-n.gettimeslot().get(0).getstarttimelong());
	        }
	    });
	}
	
	
	public void visualize() {//将表格可视化
		compare();//将计划项按时间排序
		String[][] data = new String[listarrive.size() + listgo.size() + 2][4];
		String[] datatitle = { "当前时间：" + time, "当前地点" + location ,"_______","_______"};
		int n = listgo.size();
		int m=listarrive.size();
		if (m != 0) {//初始化
			data[0][0] = "抵达航班";
			data[0][1] = "";
			data[0][2] = "";
			data[0][3] = "";
			for (int i = 0; i < m; i++) {// 分别录入名称，时间，始发站和终点站，状态
				FlightEntry arrive = listarrive.get(i - 1);
				data[i][0] = arrive.getname();// 名称
				data[i][1] = arrive.getovertimes();// 时间
				data[i][2] = arrive.getstartlocations().getname() + "-"+arrive.getoverlocations().getname();// 地点
				if (arrive.getentrystate().getstate().equals("cancel"))
					data[i][3] = "已取消";// 状态
				if (TimesLot.comparetimestring(time, arrive.getovertimes()))
				data[i][3] = "已抵达";// 状态
				else
				data[i][3] = "即将抵达";// 状态
			}
		}
		if (n != 0) {
			if(m==0)
				m--;
			data[m+1][0] = "起飞航班";
			data[m+1][1] = "";
			data[m+1][2] = "";
			data[m+1][3] = "";
			for (int i = m +2; i <= n+m+1; i++) {// 分别录入名称，时间，始发站和终点站，状态
				FlightEntry arrive = listgo.get(i -m-2);
				data[i][0] = arrive.getname();// 名称
				data[i][1] = arrive.getstarttimes();// 时间
				data[i][2] = arrive.getstartlocations().getname() + "-"+arrive.getoverlocations().getname();// 地点
				if (arrive.getentrystate().getstate().equals("cancel"))
					data[i][3] = "已取消";// 状态
				if (TimesLot.comparetimestring( arrive.getstarttimes(),time))
				data[i][3] = "即将起飞";// 状态
				else
				data[i][3] = "已起飞";// 状态
			}
		}
		JTable jtable = new JTable(data, datatitle);
		JScrollPane jscrollpane = new JScrollPane(jtable);
		setTitle("创建表格");
		setBounds(400, 400, 800, 400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(jscrollpane, BorderLayout.CENTER);
	}

}
