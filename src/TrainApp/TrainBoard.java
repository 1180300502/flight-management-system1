package TrainApp;

import P1.Location;
import P1.TimesLot;
import java.awt.BorderLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;

import FlightApp.FlightEntry;

public class TrainBoard extends JFrame {// 令board继承
	private static final long serialVersionUID = 1L;
	private Location location;// 当前地点
	private String time;// 当前时间
	private List<TrainEntry> listarrive = new ArrayList<TrainEntry>();//到达名单
	private List<TrainEntry> listgo = new ArrayList<TrainEntry>();//出发名单
	private List<TrainEntry> list = new ArrayList<TrainEntry>();
	
	//构造器

	public TrainBoard(List<TrainEntry> entry, Location location, String time) throws ParseException {		
		this.list=entry;
		this.location = location;
		this.time = time;
		find(entry);// 放进名单
		visualize();// 表格可视化
	}

	private void find(List<TrainEntry> entrys) throws ParseException {
		for (TrainEntry entry : entrys) {// 接受含有该地站点的车次
			if (!entry.getentrystate().getstate().isEmpty()&&!entry.getentrystate().getstate().equals("unassigned")&& entry.getlocations().contains(location)) {//如果计划项不是处于未分配状态且非空
				// 排除掉一个小时之内无任何动作的车次
				List<TimesLot> t = entry.gettimeslot();
				for (TimesLot lot : t) {

					if (inonehour(lot.getovertime(), time) || (lot.contiantime(time) && inonehour(time, lot.getovertime()))) 
					{
						listarrive.add(entry);
						System.out.println("in!");
					}
  
					if (inonehour(time, lot.getstarttime())|| (lot.contiantime(time) && inonehour(lot.getstarttime(), time))) 
					{
						listgo.add(entry);
						System.out.println("out!");
					}
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
	
		
	public Iterator<TrainEntry> iterator(){
		Collections.sort(list, new Comparator<TrainEntry>() {//给所有时间段按开始时间排序
	        @Override
	        public int compare(TrainEntry m, TrainEntry n) {
	        return (int) (m.gettimeslot().get(0).getstarttimelong()-n.gettimeslot().get(0).getstarttimelong());
	        }
	    });
		
		return list.iterator();
	}
	
	
	public void visualize() throws ParseException {// 表格可视化
		String[][] data = new String[listarrive.size() + listgo.size() + 2][4];
		String[] datatitle = { "当前时间：" + time, "当前地点" + location, "_______", "_______" };
		int n = listgo.size();
		int m = listarrive.size();
		if (m != 0) {//初始化
			data[0][0] = "抵达车次";
			data[0][1] = "";
			data[0][2] = "";
			data[0][3] = "";
			for (int i = 1; i <= m; i++) {// 分别录入名称，时间，始发站和终点站，状态
				TrainEntry arrive = listarrive.get(i - 1);
				data[i][0] = arrive.getname();// 名称
				List<TimesLot> t = arrive.gettimeslot();
				for (int j = t.size() - 1; j >= 0; j--) {

					TimesLot lot = arrive.gettimeslot().get(j);
					if ((lot.contiantime(time) && inonehour(time, lot.getovertime()))) {// 时间段之内
						data[i][1] = lot.getovertime();
						data[i][2] = arrive.getnumlocations(j).getname() +"-"+  arrive.getnumlocations(j + 1).getname();
						data[i][3] = "即将抵达";
						break;
					}

					if (inonehour(lot.getovertime(), time)) {
						data[i][1] = lot.getovertime();
						data[i][2] = arrive.getnumlocations(j).getname() +"-"+  arrive.getnumlocations(j + 1).getname();
						data[i][3] = "已抵达";
						break;
					}
				}
			}
		}
			
		if(m==0)
			m--;
		if (n != 0) {//同理
			data[m+1][0] = "出发车次";
			data[m+1][1] = "";
			data[m+1][2] = "";
			data[m+1][3] = "";
			for (int i = 2+m; i <= n+m+1; i++) {// 分别录入名称，时间，始发站和终点站，状态
				TrainEntry go = listgo.get(i - m- 2);
				data[i][0] = go.getname();// 名称
				List<TimesLot> t = go.gettimeslot();
				for (int j = 0; j < t.size(); j++) {

					TimesLot lot = go.gettimeslot().get(j);
					if ((lot.contiantime(time) && inonehour(lot.getstarttime(), time))) {// 时间段之内
						data[i][1] = lot.getstarttime();
						data[i][2] = go.getnumlocations(j).getname() + "-"+ go.getnumlocations(j + 1).getname();
						data[i][3] = "已出发";
						break;
					}

					if (inonehour(time, lot.getstarttime())) {
						data[i][1] = lot.getstarttime();
						data[i][2] = go.getnumlocations(j).getname() +"-"+ go.getnumlocations(j + 1).getname();
						data[i][3] = "即将出发";
						break;
					}
				}
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