package FlightApp;

import java.util.Scanner;

public class Plane{ 
	String planename;//�ɻ�����
	String planemodel;//�ɻ��ͺ�
	int planeseats;//�ɻ���λ��
	double planeage;//�ɻ�����
	public Plane(String planename,String planemodel,int planeseats,double planeage) {//������
		this.planename=planename;
		this.planemodel=planemodel;
		this.planeseats=planeseats;
		this.planeage=planeage;
	}
	
	private void setplane() {//���û�����ɻ�����Ϣ����
		Scanner a = new Scanner(System.in);
		System.out.println("������ɻ����ƣ�");
		planename =a.nextLine();
		System.out.println("������ɻ��ͺţ�");
		planemodel =a.nextLine();
		boolean flag =false;
		while (!flag) {
			System.out.println("������ɻ���λ����");
			if (a.hasNextInt()) // ���������ǲ�����������
				flag = true;
			else {
				flag =false;
				System.out.println("�����ʽ������������������");
			}
			planeseats = a.nextInt();
		}		
		flag =false;
		while (!flag) {
			System.out.println("������ɻ����䣺");
			if (a.hasNextDouble()) // ���������ǲ�����������
				flag = true;
			else {
				flag =false;
				System.out.println("�����ʽ���������븡��������");
			}
			planeage = a.nextDouble();
		}
		
	}
	
	public Plane() {//����set��������ɻ���Ϣ
		setplane();
	}

	
	public String getname() {
		return planename;
	}
	@Override 
	public String toString() {
		return "�ɻ�����"+planename+"���ͣ�"+planemodel+"��λ����"+planeseats+"���䣺"+planeage;
	}
	
}
