package TrainApp;

import java.util.List;
import java.util.Scanner;
import P1.CommonPlanningEntry;
import P1.Location;

public class TrainEntry extends CommonPlanningEntry<Train> {

	//调用父类方法
	public TrainEntry(String name) {
		super(name);
	}
	public TrainEntry(List<Location> list) {// 让用户键入一个名字来构造
		super(list);
	}

	@Override
	public void setEntry(List<Location> list) {// 要求用户依次键入始发站，终点站，时间段
		System.out.println("请输入一个数字确定要火车要经过几站：");
		int num =num(2, list.size());// 要求用户键入一个数字来确认该火车经过几站； 
		System.out.println("选择始发站：");
		addlocation(list);// 选择始发站
		for (int i = 0; i < num - 1; i++) {
			addtimelot();// 键入时间段
			System.out.println("选择第"+(i+2)+"站：");	
			addlocation(list);
		}
	}


	static public int num(int a, int b) {// 要求输入数字，检测是否为范围内的整数
		Scanner s = new Scanner(System.in);
		boolean flag=false;
		int i = 0;
		while (!flag){
			if (s.hasNextInt()) // 检测输入的是不是整型数字
				flag = true;
			else {
				System.out.println("输入格式错误，只能输入整型");
				flag = false;
			}
			i = s.nextInt();
			if (flag && (i < a || i > b)) {// 检测输入的是不是范围内的整数
				System.out.println("输入数据超出范围，请重新输入");
				flag = false;
			}
		}
		return i;
	}

	@Override
	public void assign(Train plane) {
		getresources().add(plane);
	}

	public Location getnumlocations(int num) {// 获取第n个站点
		return this.getlocations().get(num);
	}

	public String getstarttimes(int num) {// 第n次出发时间
		return this.gettimeslot().get(num).getstarttime();
	}

	public String getovertimes(int num) {// 第n次到站时间
		return this.gettimeslot().get(num).getovertime();
	}

	public void addresources(List<Train> trains) {
		System.out.println("请输入一个数字确定火车有几个车厢：");
		int num =num(1, trains.size());// 要求用户键入一个数字来确认该火车有几节车厢； 
		for (int i = 0; i < num; i++) {
			System.out.println("选择第"+(i+1)+"个车厢：");	
			addresource(trains);
		}
	}

}