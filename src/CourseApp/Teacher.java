package CourseApp;

import java.util.Scanner;

public class Teacher{
	String name;//����
	int ID;//ID
	double age;//����
	
	//������
	public Teacher(String name,int ID,double age) {
		this.name=name;
		this.ID=ID;
		this.age=age;
	}
	
	public Teacher() {//�����ʦ��Ϣ
		setteacher();//���û������ʦ����Ϣ
	}
	
	private void setteacher() {
		Scanner a = new Scanner(System.in);
		System.out.println("��������ʦ����");
		name =a.nextLine();
		boolean flag =false;
		while (!flag){
			System.out.println("��������ʦID��");
			if (a.hasNextInt()) // ���������ǲ�����������
				flag=true;
			else {
				flag = false;
				System.out.println("�����ʽ������������������");
			}
		}		
		flag =false;
		while (!flag) {
			flag=true;
			System.out.println("���������䣺");
			if (a.hasNextDouble()) // ���������ǲ��Ǹ���������
				flag=true;
			else {
				flag = false;
				System.out.println("�����ʽ���������븡��������");
			}
			age = a.nextDouble();
		}		
	}
	
	@Override 
	public String toString() {
		return "���֣�"+name+"ID��"+ID+"���䣺"+age;
	}
	
}
