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
		System.out.println("请输入教室位置坐标：");
		Scanner a = new Scanner(System.in);
		System.out.println("请输入楼层：");
		boolean flag=false;
		while (!flag) {
			if (a.hasNextInt()) // 检测输入的是不是整型数字
				flag = true;
			else {	
				System.out.println("输入格式错误，只能输入整型");
				flag = false;
			}
			this.x = a.nextInt();
		}

		System.out.println("请输入房间号：");
		flag=false;
		while (!flag) {
			if (a.hasNextInt()) // 检测输入的是不是整型数字
				flag = true;
			else {
				System.out.println("输入格式错误，只能输入整型");	
				flag = false;
			}
			this.y = a.nextInt();
		}
	}

	@Override
	public String toString() {
		return getname()+"\t楼层："+x+"\t房间号："+y;
	}
}