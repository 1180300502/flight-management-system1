package P1;

import java.util.Scanner;

public abstract class Location {//设置抽象方法
	private String name;//名字
	
	public Location(String name) {//构造器
		this.name =name;
	}
	
	public Location() { //让用户键入名字
		setname();
		setindex();
	}
		
	abstract public void setindex();
	
	//set和get方法
	public void setname(String name) {
		this.name=name;
	}
	public String getname() {
		return ""+name;
	}
	
	public void setname() {
		
		System.out.println("请输入地点名称：");
		Scanner a = new Scanner(System.in);
		this.name=a.nextLine();
	}
}
