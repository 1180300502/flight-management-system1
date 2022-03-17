package P1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JButton;


public abstract class CommonPlanningEntry<L> implements PlanningEntry<L>{
	private String entryname;// �ƻ��������
	private final List<L> resources = new ArrayList<L>();// �������Դ��
	private final List<Location> locations = new ArrayList<Location>();// λ���б�
	private final List<TimesLot> timeslot = new ArrayList<TimesLot>();// ʱ����б�
	private EntryState entrystate;
	private String loader = "";// ��¼��ǰ��toString
	public JButton button;
	
	private void checkRep() {//״̬���
		entrystate.checkRep();
	}

	public CommonPlanningEntry(String name) {//������
		entryname = name;
		entrystate=new EntryState();
	}

	public CommonPlanningEntry(List<Location> list) {// ��������,�趨�ƻ����ʱ��ص�
		System.out.println("������ƻ������֣�");
		Scanner a = new Scanner(System.in);
		entryname =a.nextLine();
		entrystate=new EntryState();
		setEntry(list);
	}
	
	//���󷽷��������������ʵ��
	abstract public void setEntry(List<Location> list);//�趨ʱ��ص�
    abstract public void assign(L l);//������Դ

	// add�����������Դʱ��λ�á�
	public void addlocation(Location location) {
		locations.add(location);
	}

	public void addtimelot(TimesLot timelot) {
		timeslot.add(timelot);
	}

	public void addresource(L resource) {// �����Դ,ͬʱ������Դ
		resources.add(resource);
		entrystate.assign();
	}

	public boolean ok() {// �ж�ʱ��λ���б��Ƿ�Ϊ�գ��������Ƿ���Կ�ʼ�ƻ�
		if (locations.size() != 0&&timeslot.size()!=0)
			return true;
		else
			return false;
	}

	
	public L addresource(List<L> list) {// ���û�ѡ����Դ����
		System.out.println("��ѡ������������Դ���������֣���");
		Scanner a = new Scanner(System.in);
		int i = 0;
		for(i=0;i<list.size();i++) {
			System.out.println("" + i + ":" + list.get(i).toString());
		}
		boolean flag=false;
		i=0;
		while(!flag) {
			if (a.hasNextInt()) // ���������ǲ�����������
				flag = false;
			else {
				System.out.println("�����ʽ������������������");
				flag = false;
			}
			i = a.nextInt();
			int j=list.size()-1;
			if (flag && (i < 0 || i > list.size())) {// ���������ǲ��Ƿ�Χ�ڵ�����
				System.out.println("�������ݳ�����Χ�����������룬��Χ��0��"+j);
				flag = false;
			}
			if (flag && resources.contains(list.get(i))) {// ���������ǲ����Ѿ����б���
				System.out.println("����Դ�Ѵ��ڣ�����������");
				flag = false;
			}
		}
		L l =list.get(i);
		resources.add(l);
		entrystate.assign();//��״̬����Ϊ��������Դ
		return l;
	}
	
	public Location addlocation(List<Location> list) {// ���û�ѡ���ַ����,ͬ��
		System.out.println("��ѡ����ĵ�ַ���������֣���");
		Scanner a = new Scanner(System.in);
		int i = 0;
		for(i=0;i<list.size();i++) {
			System.out.println("" + i + ":" + list.get(i).toString());
		}
		boolean flag=false;
		i=0;
		while(!flag) {
			if (a.hasNextInt()) // ���������ǲ�����������
				flag = true;
			else {
				System.out.println("�����ʽ������������������");
				flag = false;
			}
			i = a.nextInt();
			int j=list.size()-1;
			if (flag && (i < 0 || i >= list.size())) {// ���������ǲ��Ƿ�Χ�ڵ�����
				System.out.println("�������ݳ�����Χ�����������룬��Χ��0��"+j);
				flag = false;
			}
			if (flag && locations.contains(list.get(i))) {// ���������ǲ����Ѿ����б���
				System.out.println("����Դ�Ѵ��ڣ�����������");
				flag = false;
			}
		}
		Location l=list.get(i);
		locations.add(l);
		entrystate.assign();//��״̬����Ϊ��������Դ
		return l;

	}

	public void addtimelot() {// ���û�����һ��ʱ�䣬ǰһ��ʱ��Ŀ�ʼ���ܴ��ں�һ��ʱ��Ľ�����boolean�����Ƿ��н���ʱ��
		Scanner a = new Scanner(System.in);
		String starttime = null;
		boolean flag = false;
		System.out.println("ʱ���ʽΪyyyy-mm-dd hh:mm�����磺��2019-12-05 13:23��");
		System.out.println("�����뿪ʼʱ�䣺");
		while (!flag) {// �ж��Ƿ���Ϲ���
			starttime = a.nextLine();
			if (TimesLot.istime(starttime))
				flag = true;
			else{
				System.out.println("����ʱ�䲻���Ϲ�������������");
				flag = false;
			}
			if (timeslot.size() > 0 && flag) {// ��ǰһ������ʱ����ڿ�ʼʱ�䣬����ǰ�б�ǰ���������ƻ��δ��������flag����Ϊfalse
				TimesLot time = timeslot.get(timeslot.size() - 1);// ��ȡ�üƻ����ĩβʱ���
				if (TimesLot.comparetimestring(time.getovertime(), starttime))// ǰһ������ʱ����ڵ�ǰ��ʼʱ��
				{
					flag = false;
					System.out.println("��ǰ����ʱ��������һ������ʱ�䣬����������");
					System.out.println("��һ������ʱ��Ϊ��"+time.getovertime()+"�����������ڸ�ʱ�������");
				}
			}
		}
		String overtime = null;
		flag = false;
		System.out.println("���������ʱ�䣺");//ͬ��
		while (!flag) {			
			overtime = a.nextLine();
			if (TimesLot.istime(overtime))
				flag=true;
			else{
				System.out.println("�������﷨����");
				flag = false;
			}
			if (timeslot.size() > 0 && flag) {//�������ʱ�����ڿ�ʼʱ��
				if (TimesLot.comparetimestring(starttime, overtime))
				{
					flag = false;
					System.out.println("����ʱ�����ڿ�ʼʱ�䣬����������");
					System.out.println("��ʼʱ��Ϊ��"+starttime+"�����������ڸ�ʱ�������");
				}
			}
		}
		TimesLot time = new TimesLot(starttime, overtime);
		timeslot.add(time);
	}

	// get��������ȡֵ��
	public EntryState getentrystate() {
		return entrystate;
	}

	public List<Location> getlocations() {
		checkRep();
		return locations;
	}

	public List<TimesLot> gettimeslot() {
		checkRep();
		return timeslot;
	}

	public List<L> getresources() {
		return resources;
	}

	public void changetostring(String s) {
		loader = s;
		checkRep();
	}

	// find�������ҵ�������Ӧ�Ķ���
	public int findlocation(Location location) {// �ҵ�location��Ӧ�Ĵ���,���û�У���Ϊ-1
		return locations.indexOf(location);
	}

	public int findtime(String time) {// �ҵ�time��Ӧ��ʱ���
		for (int i = 0; i < timeslot.size(); i++) {
			if (timeslot.get(i).contiantime(time))
				return i;
		}
		return -1;//���û�У��򷵻�-1
	}
	
	//������д
	@Override
	public void Run() {
		entrystate.run();
	}

	@Override
	public void Cancel() {
		entrystate.cancel();
	}

	@Override
	public void Block() {

	}

	@Override
	public void Commplete() {
		entrystate.complete();
	}

	@Override
	public String toString() {
		checkRep();
		return loader;
	}

	@Override
	public String getname() {
		return entryname;
	}

	@Override
	public String getstate() {
		return entrystate.toString();
	}	
}
