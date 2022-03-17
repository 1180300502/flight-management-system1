package TrainApp;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Logger;

import P1.*;

public class TrainScheduleApp {

	private final List<Location> locations = new ArrayList<Location>();// ��֧���λ��
	private final List<Train> trains = new ArrayList<Train>();// ��֧�����Դ
	public final List<TrainEntry> lists = new ArrayList<TrainEntry>();// ��ʹ�õļƻ���

	public TrainScheduleApp() throws ParseException {
		System.out.println("-----��������ϵͳ-----");
		boolean over = false;
		int choise = 12;
		while (!over) {
			System.out.println("��ѡ��������һ��������");
			System.out.println("1:���λ��");
			System.out.println("2:�����Դ");
			System.out.println("3:��ӳ���");
			System.out.println("4:Ϊ���η��䳵��");
			System.out.println("5:ȡ��ָ������");
			System.out.println("6:�鿴ָ������״̬");
			System.out.println("7:������Ϣ��");
			System.out.println("8:ɾ��λ��");
			System.out.println("9:ɾ����Դ");
			System.out.println("10:�����Դ��ռ��ͻ");
			System.out.println("11:Ѱ��ǰ��ƻ���");
			System.out.println("12:��ӡ��־");
			System.out.println("13:�˳�");
			choise = num(1, 13);
			boolean flag =false;
			// ���λ����Ϣ����Դ��Ϣ
			if (locations.size() > 0) {
				flag=true;
				System.out.println("��֧���λ����Ϣ��");
				for (int i = 0; i < locations.size(); i++)
					System.out.println(i + ":" + locations.get(i).getname());
			}
			if (trains.size() > 0) {
				flag=true;
				System.out.println("��֧��ĳ�����Ϣ��");
				for (int i = 0; i < trains.size(); i++)
					System.out.println(i + ":" + trains.get(i).toString());
			}
			if (lists.size() > 0) {
				flag=true;
				System.out.println("��֧��ĳ�����Ϣ��");
				for (int i = 0; i < lists.size(); i++)
					System.out.println(i + ":" + lists.get(i).getname());
			}
			if(!flag)
				System.out.println("��ǰ�޿�֧���λ��/��Դ/�ƻ�����Ϣ");
			// ִ��
			switch (choise) {
			case 1:
				addlocation();
				break;
			case 2:
				addresource();
				break;
			case 3:
				addentry();
				break;
			case 4:
				assign();
				break;
			case 5:
				cancel();
				break;
			case 6:
				getstate();
				break;
			case 7:
				boardout();
				break;
			case 8:
				deletlocation();
				break;
			case 9:
				deletresource();
				break;
			case 10:
				resourcesconflict();
				break;
			case 11:
				findearlyentry();
			case 12:
				print();
				break;
			case 13:
				over = true;
				break;
			default:
				break;
			}
		}

	}

	private void boardout() throws ParseException {// ѡ��ʱ��ص㴴�����
		if (lists.size() <= 0) {
			System.out.println("�޼ƻ���޷���ʾʱ���");
			return;
		}
		System.out.println("ѡ��ǰλ��");
		int i = 0;
		for (Location location : locations) {
			System.out.println("" + i + ":" + location.toString());
			i++;
		}
		i = num(0, locations.size()-1);
		Location location = locations.get(i);
		Scanner a = new Scanner(System.in);
		String time=null;
		boolean flag=false;
		System.out.println("ʱ���ʽΪyyyy-mm-dd hh:mm�����磺��2019-12-05 13:23��");
		System.out.println("�����뵱ǰʱ�䣺");
		while (!flag){// �ж��Ƿ���Ϲ���
			time = a.nextLine();
			if (TimesLot.istime(time))// ��Υ��flag����Ϊfalse
				flag=true;
			else{
				System.out.println("�����ʽ��������������");
				flag = false;
			}
		} 
		TrainBoard board = new TrainBoard(lists, location, time);
		System.out.println("����Ѵ���");
		System.out.println("�����ƻ��");
		Iterator<TrainEntry> iterator = board.iterator();
		// board ��һ������Ϊ Board �Ķ���
		while (iterator.hasNext()) {
			TrainEntry pe = iterator.next();
			System.out.println(pe.getname() + ":" + pe.getlocations().get(0) + "-"
					+ pe.getlocations().get(pe.getlocations().size() - 1));
		}

	}
	
	private void findearlyentry() {
		if (lists.size() <= 0) {
			System.out.println("�޼ƻ���޷���飡");
			return;
		}
		int i = 0;
		System.out.println("������Ҫ�г���������Դ�ļƻ���ı�ţ�");
		for (TrainEntry entry : lists) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, lists.size()-1);
		TrainEntry entry = lists.get(i);
		List<Train> Trainlist = entry.getresources();
		PlanningEntry<Train> entry2 = (PlanningEntry<Train>) entry;// ��������
		if (Trainlist.size() <= 0) {
			System.out.println("δ������Դ���޷���飡");
			return;
		}
		System.out.println("������Ҫ��������ǰ��ƻ���ʹ�õ���Դ�ı�ţ�");
		i=0;
		for (Train Train : Trainlist) {
			System.out.println("" + i + ":" + Train.toString());
			i++;
		}
		i = num(0, Trainlist.size()-1);
		Train thisTrain = Trainlist.get(i);

		List<PlanningEntry<Train>> lister = new ArrayList<PlanningEntry<Train>>();
		for (TrainEntry entry1 : lists) {
			lister.add(entry1);
		}

		PlanningEntry<Train> p = (new PlanningEntryAPI<Train>().findPreEntryPerResource(thisTrain, entry2,
				lister));
		if (p != null)		
			System.out.println("ǰ��ƻ���Ϊ��" + p.getname());
		else
			System.out.println("û�ҵ�ǰ��ƻ��");
	}
	
	private void resourcesconflict() {
		if (lists.size() <= 0) {
			System.out.println("�޼ƻ���޷���飡");
			return;
		}
		List<PlanningEntry<Train>> lister = new ArrayList<PlanningEntry<Train>>();
		for (TrainEntry entry : lists) {
			lister.add(entry);
		}
		System.out.println("��ѡ�����ģʽ��\n1���ƻ����ģʽ \n2:�ƻ����+��Դ��ģʽ");
		int n = num(1, 2);
		if (n == 1) {
			if (!new PlanningEntryAPI<Train>().checkResourceExclusiveConflict(lister))
				System.out.println("�г�ͻ��");

			else {
				System.out.println("�޳�ͻ��");
				return;
			}
		}
		if (n == 2) {
			if (!new PlanningEntryAPI<Train>().checkResourceExclusiveConflict(trains, lister)) 
				System.out.println("�г�ͻ��");

			else {
				System.out.println("�޳�ͻ��");
				return;
			}
		}		
	}
	
	
	private String getstate() {// ��ȡ�ƻ����״̬

		if (lists.size() <= 0) {
			System.out.println("�޼ƻ���޷�����");
			return "";
		}
		System.out.println("�ɻ�ȡ�ļƻ������£�");
		int i = 0;
		for (TrainEntry entry : lists) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, lists.size()-1);
		TrainEntry entry = lists.get(i);

		System.out.println(entry + "״̬Ϊ��" + entry.getstate().toString());
		return entry.getstate().toString();
	}
	
	public TrainEntry addentry(String name) {
		TrainEntry entry = new TrainEntry(name);
		lists.add(entry);
		return entry;
	}

	public boolean addresource(Train Train) {
		if (!trains.contains(Train)) {
			trains.add(Train);
			return true;
		}
		else
			return false;
	}

	public boolean addlocation(Location location) {
		if (!locations.contains(location)) {
			locations.add(location);
			return true;
		}
		else
			return false;
	}

	public TrainEntry addentry() {// �û�����һ���ƻ��������ʱ�䣬�ص�
		if (locations.size() <= 1) {
			System.out.println("λ��С���������޷���Ӻ���");
			return null;
		}
		TrainEntry entry = new TrainEntry(locations);
		lists.add(entry);
		System.out.println("������ɣ�");
		return entry;
	}
	
	public Train addresource() {// ���룬���һ����Դ
		Train Train = new Train();
		trains.add(Train);
		System.out.println("�Ѽ�����Դ��");
		return Train;
	}
	
	public Location addlocation() {// ����һ���µ�λ��
		TrainLocation location = new TrainLocation();
		locations.add(location);
		System.out.println("�Ѽ���λ�ã�");
		return location;
	}

	public void assign() {// Ϊһ���ƻ��������Դ��
		if (lists.size() <= 0) {
			System.out.println("�޼ƻ���޷�����");
			return;
		}
		if (trains.size() <= 0) {
			System.out.println("�޳��ᣬ�޷�����");
			return;
		}
		int i = 0;
		System.out.println("ѡ����ĳ��Σ�");
		for (TrainEntry entry : lists) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, lists.size()-1);
		TrainEntry entry = lists.get(i);
		entry.addresources(trains);
		System.out.println("����ɹ���");
	}
	
	public void cancel() {// ȡ��ĳһ����
		if (lists.size() <= 0) {
			System.out.println("�޼ƻ���޷�����");
			return;
		}
		int i = 0;
		System.out.println("���복�κ���ȡ����");
		for (TrainEntry entry : lists) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, lists.size()-1);
		TrainEntry entry = lists.get(i);
		entry.Cancel();
		System.out.println("ȡ���ɹ���");
	}

	public void deletresource() {// ɾ��ĳ����Դ
		if (trains.size() <= 0) {
			System.out.println("�޳��ᣬ�޷�ɾ��");
			return;
		}
		int i = 0;
		System.out.println("���복�����ȡ����");
		for (Train Train : trains) {
			System.out.println("" + i + ":" + Train.toString());
			i++;
		}
		i = num(0, trains.size()-1);
		Train Train = trains.get(i);
		trains.remove(Train);
		System.out.println("ɾ���ɹ���");
	}

	public void deletlocation() {// ȡ��ĳһλ��
		if (locations.size() <= 0) {
			System.out.println("��λ�ã��޷�ɾ��");
			return;
		}
		int i = 0;
		System.out.println("����λ�ú���ȡ����");
		for (Location location : locations) {
			System.out.println("" + i + ":" + location.getname());
			i++;
		}
		i = num(0, locations.size()-1);
		Location location = locations.get(i);
		locations.remove(location);
		System.out.println("ɾ���ɹ���");
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

	public static void print() {
		Logger logger = Logger.getLogger(TrainScheduleApp.class.getName());
		try {
			"".getBytes("invalidCharsetName");
		} catch (UnsupportedEncodingException e) {
			logger.severe(e.toString());
			e.printStackTrace();
		}
	}
	
	public static void main(String[] arg0) throws ParseException {
		TrainScheduleApp app = new TrainScheduleApp();
	}

}
