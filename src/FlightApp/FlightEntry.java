package FlightApp;

import java.util.List;
import P1.*;

public class FlightEntry extends CommonPlanningEntry<Plane> implements PlanningEntry<Plane>{
	
	//调用父类方法
	public FlightEntry(String name) {
		super(name);		
	}
	
	public FlightEntry(List<Location> list) {//让用户输入名字构造
		super(list);
	}
	
	public void setFlightEntry(Plane plane,TimesLot time,Location start,Location over) {
		this.addresource(plane);
		this.addlocation(start);
		this.addlocation(over);
		this.addtimelot(time);
	}
	
	@Override
	public void setEntry(List<Location> list) {//用户依次选择始发站，时间段，终点站
		System.out.println("选择始发站：");
		addlocation(list);//选择始发站
		System.out.println("输入时间段");
		addtimelot();//输入时间段
		System.out.println("选择终点站：");
		addlocation(list);//选择终点站
	}
	
	@Override
	public void assign(Plane plane) {//方法重写，分配资源
		getresources().add(plane);
	}
	
	//get方法，获取数据
	public Location getstartlocations() {//始发站
		return this.getlocations().get(0);
	}
	
	public Location getoverlocations() {//终点站
		return this.getlocations().get(1);
	}
	public String getstarttimes() {//始发时间
		return this.gettimeslot().get(0).getstarttime();
	}
	
	public String getovertimes() {//终点时间
		return this.gettimeslot().get(0).getovertime();
	}

	public TimesLot gettimelots() {//获取时间段
		return this.gettimeslot().get(0);
	}
}
