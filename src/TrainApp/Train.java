package TrainApp;

import java.util.Scanner;

public class Train{
	String model;//����
	int seats;//��λ��
	public Train(String model,int seats) {//��ʼ�������ͺ�
		this.model=model;
		this.seats=seats;
	}
	
	public Train() {//��������ͺ�
		settrain();//���û������������Ϣ
	}

	private void settrain() {
		Scanner a = new Scanner(System.in);
		System.out.println("�����복���ͺţ�");
		model =a.nextLine();
		boolean flag =false;
		while (!flag){			
			System.out.println("��������λ����");
			if (a.hasNextInt()) // ���������ǲ�����������
				flag=true;
			else {
				flag = false;
				System.out.println("�����ʽ������������������");
			}
		} 
		seats = a.nextInt();

	}
	
	@Override 
	public String toString() {
		return "���ͣ�"+model+"��λ����"+seats;
	}
	
}
