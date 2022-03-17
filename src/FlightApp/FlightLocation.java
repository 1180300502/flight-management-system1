package FlightApp;

import java.util.Scanner;

import P1.Location;

public class FlightLocation extends Location {

	private double x;//经纬度
	private double y;

	public FlightLocation(String name) {
		super(name);
	}
	public FlightLocation() {
		super();
	}

	@Override
	public void setindex() {
		System.out.println("请输入位置坐标：");
		Scanner a = new Scanner(System.in);
		System.out.println("请输入经度：");
		boolean flag=false;
		while (!flag) {
			if (a.hasNextDouble()) // 检测输入的是不是浮点型数字
				flag = true;
			else {	
				System.out.println("输入格式错误，只能输入浮点型");
				flag = false;
			}
			this.x = a.nextDouble();
		}

		System.out.println("请输入纬度：");
		flag=false;
		while (!flag) {
			if (a.hasNextDouble()) // 检测输入的是不是浮点型数字
				flag = true;
			else {
				System.out.println("输入格式错误，只能输入浮点型");	
				flag = false;
			}
			this.y = a.nextDouble();
		}
	}

	@Override
	public String toString() {
		return getname()+"\t经度："+x+"\t纬度："+y;
	}
}
