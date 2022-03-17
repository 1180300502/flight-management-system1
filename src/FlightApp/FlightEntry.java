package FlightApp;

import java.util.List;
import P1.*;

public class FlightEntry extends CommonPlanningEntry<Plane> implements PlanningEntry<Plane>{
	
	//���ø��෽��
	public FlightEntry(String name) {
		super(name);		
	}
	
	public FlightEntry(List<Location> list) {//���û��������ֹ���
		super(list);
	}
	
	public void setFlightEntry(Plane plane,TimesLot time,Location start,Location over) {
		this.addresource(plane);
		this.addlocation(start);
		this.addlocation(over);
		this.addtimelot(time);
	}
	
	@Override
	public void setEntry(List<Location> list) {//�û�����ѡ��ʼ��վ��ʱ��Σ��յ�վ
		System.out.println("ѡ��ʼ��վ��");
		addlocation(list);//ѡ��ʼ��վ
		System.out.println("����ʱ���");
		addtimelot();//����ʱ���
		System.out.println("ѡ���յ�վ��");
		addlocation(list);//ѡ���յ�վ
	}
	
	@Override
	public void assign(Plane plane) {//������д��������Դ
		getresources().add(plane);
	}
	
	//get��������ȡ����
	public Location getstartlocations() {//ʼ��վ
		return this.getlocations().get(0);
	}
	
	public Location getoverlocations() {//�յ�վ
		return this.getlocations().get(1);
	}
	public String getstarttimes() {//ʼ��ʱ��
		return this.gettimeslot().get(0).getstarttime();
	}
	
	public String getovertimes() {//�յ�ʱ��
		return this.gettimeslot().get(0).getovertime();
	}

	public TimesLot gettimelots() {//��ȡʱ���
		return this.gettimeslot().get(0);
	}
}
