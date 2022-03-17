package CourseApp;

import java.util.List;
import P1.*;


public class CourseEntry extends CommonPlanningEntry<Teacher>{
	
	//���ø��෽��
	public CourseEntry(String name) {
		super(name);		
	}
	
	public CourseEntry(List<Location> list) {//���û�����һ������������
		super(list);
	}
	
	public void setCourseEntry(Teacher teacher,TimesLot timelot,Location start) {
		this.addresource(teacher);
		this.addlocation(start);
		this.addtimelot(timelot);
	}
	
	@Override
	public void setEntry(List<Location> list) {//Ҫ���û�������ң�ʱ���
		System.out.println("ѡ���ң�");
		addlocation(list);//ѡ�����
		addtimelot();//����ʱ���
	}
	
	@Override
	public void assign(Teacher teacher) {
		getresources().add(teacher);
	}
	public Location getstartlocation() {//����
		return this.getlocations().get(0);
	}
	
	public String getstarttime() {//��ʼʱ��
		return this.gettimeslot().get(0).getstarttime();
	}
	
	public String getovertime() {//����ʱ��
		return this.gettimeslot().get(0).getovertime();
	}

}