package TrainApp;

import java.util.List;
import java.util.Scanner;
import P1.CommonPlanningEntry;
import P1.Location;

public class TrainEntry extends CommonPlanningEntry<Train> {

	//���ø��෽��
	public TrainEntry(String name) {
		super(name);
	}
	public TrainEntry(List<Location> list) {// ���û�����һ������������
		super(list);
	}

	@Override
	public void setEntry(List<Location> list) {// Ҫ���û����μ���ʼ��վ���յ�վ��ʱ���
		System.out.println("������һ������ȷ��Ҫ��Ҫ������վ��");
		int num =num(2, list.size());// Ҫ���û�����һ��������ȷ�ϸû𳵾�����վ�� 
		System.out.println("ѡ��ʼ��վ��");
		addlocation(list);// ѡ��ʼ��վ
		for (int i = 0; i < num - 1; i++) {
			addtimelot();// ����ʱ���
			System.out.println("ѡ���"+(i+2)+"վ��");	
			addlocation(list);
		}
	}


	static public int num(int a, int b) {// Ҫ���������֣�����Ƿ�Ϊ��Χ�ڵ�����
		Scanner s = new Scanner(System.in);
		boolean flag=false;
		int i = 0;
		while (!flag){
			if (s.hasNextInt()) // ���������ǲ�����������
				flag = true;
			else {
				System.out.println("�����ʽ����ֻ����������");
				flag = false;
			}
			i = s.nextInt();
			if (flag && (i < a || i > b)) {// ���������ǲ��Ƿ�Χ�ڵ�����
				System.out.println("�������ݳ�����Χ������������");
				flag = false;
			}
		}
		return i;
	}

	@Override
	public void assign(Train plane) {
		getresources().add(plane);
	}

	public Location getnumlocations(int num) {// ��ȡ��n��վ��
		return this.getlocations().get(num);
	}

	public String getstarttimes(int num) {// ��n�γ���ʱ��
		return this.gettimeslot().get(num).getstarttime();
	}

	public String getovertimes(int num) {// ��n�ε�վʱ��
		return this.gettimeslot().get(num).getovertime();
	}

	public void addresources(List<Train> trains) {
		System.out.println("������һ������ȷ�����м������᣺");
		int num =num(1, trains.size());// Ҫ���û�����һ��������ȷ�ϸû��м��ڳ��᣻ 
		for (int i = 0; i < num; i++) {
			System.out.println("ѡ���"+(i+1)+"�����᣺");	
			addresource(trains);
		}
	}

}