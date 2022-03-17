package CourseApp;

import java.util.Scanner;

public class Teacher{
	String name;//名字
	int ID;//ID
	double age;//年龄
	
	//构造器
	public Teacher(String name,int ID,double age) {
		this.name=name;
		this.ID=ID;
		this.age=age;
	}
	
	public Teacher() {//输入教师信息
		setteacher();//让用户输入教师的信息
	}
	
	private void setteacher() {
		Scanner a = new Scanner(System.in);
		System.out.println("请输入老师名：");
		name =a.nextLine();
		boolean flag =false;
		while (!flag){
			System.out.println("请输入老师ID：");
			if (a.hasNextInt()) // 检测输入的是不是整型数字
				flag=true;
			else {
				flag = false;
				System.out.println("输入格式错误，请输入整型数据");
			}
		}		
		flag =false;
		while (!flag) {
			flag=true;
			System.out.println("请输入年龄：");
			if (a.hasNextDouble()) // 检测输入的是不是浮点型数字
				flag=true;
			else {
				flag = false;
				System.out.println("输入格式错误，请输入浮点型数据");
			}
			age = a.nextDouble();
		}		
	}
	
	@Override 
	public String toString() {
		return "名字："+name+"ID："+ID+"年龄："+age;
	}
	
}
