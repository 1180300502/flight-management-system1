package FlightApp;

import java.util.Scanner;

import P1.Location;

public class FlightLocation extends Location {

	private double x;//��γ��
	private double y;

	public FlightLocation(String name) {
		super(name);
	}
	public FlightLocation() {
		super();
	}

	@Override
	public void setindex() {
		System.out.println("������λ�����꣺");
		Scanner a = new Scanner(System.in);
		System.out.println("�����뾭�ȣ�");
		boolean flag=false;
		while (!flag) {
			if (a.hasNextDouble()) // ���������ǲ��Ǹ���������
				flag = true;
			else {	
				System.out.println("�����ʽ����ֻ�����븡����");
				flag = false;
			}
			this.x = a.nextDouble();
		}

		System.out.println("������γ�ȣ�");
		flag=false;
		while (!flag) {
			if (a.hasNextDouble()) // ���������ǲ��Ǹ���������
				flag = true;
			else {
				System.out.println("�����ʽ����ֻ�����븡����");	
				flag = false;
			}
			this.y = a.nextDouble();
		}
	}

	@Override
	public String toString() {
		return getname()+"\t���ȣ�"+x+"\tγ�ȣ�"+y;
	}
}