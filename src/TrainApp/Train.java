package TrainApp;

import java.util.Scanner;

public class Train{
	String model;//机型
	int seats;//座位数
	public Train(String model,int seats) {//初始化高铁型号
		this.model=model;
		this.seats=seats;
	}
	
	public Train() {//输入高铁型号
		settrain();//让用户输入高铁的信息
	}

	private void settrain() {
		Scanner a = new Scanner(System.in);
		System.out.println("请输入车厢型号：");
		model =a.nextLine();
		boolean flag =false;
		while (!flag){			
			System.out.println("请输入座位数：");
			if (a.hasNextInt()) // 检测输入的是不是整型数字
				flag=true;
			else {
				flag = false;
				System.out.println("输入格式错误，请输入整型数据");
			}
		} 
		seats = a.nextInt();

	}
	
	@Override 
	public String toString() {
		return "机型："+model+"座位数："+seats;
	}
	
}
