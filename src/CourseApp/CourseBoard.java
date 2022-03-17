package CourseApp;

import java.awt.BorderLayout;
import java.util.*;
import P1.Location;
import P1.TimesLot;
import javax.swing.*;

public class CourseBoard extends JFrame {// 令board继承
	private static final long serialVersionUID = 1L;
	public List<CourseEntry> list = new ArrayList<CourseEntry>();// 课程名单
	private Location location;// 当前地点
	private String time;// 当前时间

	public CourseBoard(List<CourseEntry> entrys, Location location, String time) {
		this.time = time;
		this.location = location;
		find(entrys);// 放进名单
		visualize();// 表格可视化
	}

	public void find(List<CourseEntry> entrys) {// 将计划项按要求放进名单中
		for (CourseEntry entry : entrys) {
			if (!entry.getentrystate().getstate().isEmpty()&&!entry.getentrystate().getstate().equals("unassigned")) {//如果计划项不是处于未分配状态且非空
				if (entry.getstartlocation() == location) {
					list.add(entry);
				}
			}
		}
	}
	public Iterator<CourseEntry> iterator(){
		Collections.sort(list, new Comparator<CourseEntry>() {//给所有时间段按开始时间排序
	        @Override
	        public int compare(CourseEntry m, CourseEntry n) {
	        return (int) (m.gettimeslot().get(0).getstarttimelong()-n.gettimeslot().get(0).getstarttimelong());
	        }
	    });
		
		return list.iterator();
	}
	public void visualize() {// 表格可视化
		String[][] data = new String[list.size() + 1][4];
		String[] datatitle = { "当前时间：" + time, "当前地点" + location ,"_______","_______"};
		int n = list.size();

			for (int i = 0; i < n; i++) {// 分别录入名称，时间，教室，状态
				CourseEntry arrive = list.get(i - n - 1);
				data[i][0] = arrive.getname();// 名称
				data[i][1] = arrive.getovertime();// 时间
				data[i][2] = arrive.getstartlocation().getname();// 地点
				if (arrive.getentrystate().getstate().equals("cancel"))
					data[i][3] = "已取消";// 状态
				if (TimesLot.comparetimestring(time,arrive.getovertime()))
				data[i][3] = "已结束";// 状态
				else if(TimesLot.comparetimestring(arrive.getstarttime(),time))
				data[i][3] = "未开始";// 状态
				else 
				data[i][3] = "正在进行";
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
