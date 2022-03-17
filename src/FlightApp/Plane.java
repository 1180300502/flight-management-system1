package FlightApp;

import java.util.Scanner;

public class Plane{ 
	String planename;//飞机名称
	String planemodel;//飞机型号
	int planeseats;//飞机座位数
	double planeage;//飞机机龄
	public Plane(String planename,String planemodel,int planeseats,double planeage) {//构造器
		this.planename=planename;
		this.planemodel=planemodel;
		this.planeseats=planeseats;
		this.planeage=planeage;
	}
	
	private void setplane() {//将用户输入飞机的信息存入
		Scanner a = new Scanner(System.in);
		System.out.println("请输入飞机名称：");
		planename =a.nextLine();
		System.out.println("请输入飞机型号：");
		planemodel =a.nextLine();
		boolean flag =false;
		while (!flag) {
			System.out.println("请输入飞机座位数：");
			if (a.hasNextInt()) // 检测输入的是不是整型数字
				flag = true;
			else {
				flag =false;
				System.out.println("输入格式错误，请输入整型数字");
			}
			planeseats = a.nextInt();
		}		
		flag =false;
		while (!flag) {
			System.out.println("请输入飞机机龄：");
			if (a.hasNextDouble()) // 检测输入的是不是整型数字
				flag = true;
			else {
				flag =false;
				System.out.println("输入格式错误，请输入浮点型数字");
			}
			planeage = a.nextDouble();
		}
		
	}
	
	public Plane() {//调用set函数输入飞机信息
		setplane();
	}

	
	public String getname() {
		return planename;
	}
	@Override 
	public String toString() {
		return "飞机名："+planename+"机型："+planemodel+"座位数："+planeseats+"年龄："+planeage;
	}
	
}
