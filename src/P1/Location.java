package P1;

import java.util.Scanner;

public abstract class Location {//���ó��󷽷�
	private String name;//����
	
	public Location(String name) {//������
		this.name =name;
	}
	
	public Location() { //���û���������
		setname();
		setindex();
	}
		
	abstract public void setindex();
	
	//set��get����
	public void setname(String name) {
		this.name=name;
	}
	public String getname() {
		return ""+name;
	}
	
	public void setname() {
		
		System.out.println("������ص����ƣ�");
		Scanner a = new Scanner(System.in);
		this.name=a.nextLine();
	}
}
