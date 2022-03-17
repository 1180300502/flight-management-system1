package CourseApp;

import java.util.List;
import P1.*;


public class CourseEntry extends CommonPlanningEntry<Teacher>{
	
	//调用父类方法
	public CourseEntry(String name) {
		super(name);		
	}
	
	public CourseEntry(List<Location> list) {//让用户输入一个名字来构造
		super(list);
	}
	
	public void setCourseEntry(Teacher teacher,TimesLot timelot,Location start) {
		this.addresource(teacher);
		this.addlocation(start);
		this.addtimelot(timelot);
	}
	
	@Override
	public void setEntry(List<Location> list) {//要求用户输入教室，时间段
		System.out.println("选教室：");
		addlocation(list);//选择教室
		addtimelot();//键入时间段
	}
	
	@Override
	public void assign(Teacher teacher) {
		getresources().add(teacher);
	}
	public Location getstartlocation() {//教室
		return this.getlocations().get(0);
	}
	
	public String getstarttime() {//开始时间
		return this.gettimeslot().get(0).getstarttime();
	}
	
	public String getovertime() {//结束时间
		return this.gettimeslot().get(0).getovertime();
	}

}