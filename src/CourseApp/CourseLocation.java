package CourseApp;

import java.util.Scanner;

import P1.Location;

public class CourseLocation extends Location {
	private double x;
	private double y;
	
	
	public CourseLocation(String name) {
		super(name);

	}

	public CourseLocation() {
		super();
	}

	@Override
	public void setindex() {
		System.out.println("���������λ�����꣺");
		Scanner a = new Scanner(System.in);
		System.out.println("������¥�㣺");
		boolean flag=false;
		while (!flag) {
			if (a.hasNextInt()) // ���������ǲ�����������
				flag = true;
			else {	
				System.out.println("�����ʽ����ֻ����������");
				flag = false;
			}
			this.x = a.nextInt();
		}

		System.out.println("�����뷿��ţ�");
		flag=false;
		while (!flag) {
			if (a.hasNextInt()) // ���������ǲ�����������
				flag = true;
			else {
				System.out.println("�����ʽ����ֻ����������");	
				flag = false;
			}
			this.y = a.nextInt();
		}
	}

	@Override
	public String toString() {
		return getname()+"\t¥�㣺"+x+"\t����ţ�"+y;
	}
}