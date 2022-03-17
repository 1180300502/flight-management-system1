package CourseApp;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.logging.Logger;

import P1.*;


public class CourseCalendarApp {

	private final List<Location> locations = new ArrayList<Location>();// ��֧���λ��
	private final List<Teacher> Teachers = new ArrayList<Teacher>();// ��֧�����Դ
	public final List<CourseEntry> list = new ArrayList<CourseEntry>();// ��ʹ�õļƻ���

	public CourseCalendarApp() {
		System.out.println("-----�γ̹���ϵͳ-----");
		boolean over = false;
		int choise = 13;
		while (!over) {
			System.out.println("��ѡ��������һ��������");
			System.out.println("1:��ӽ���");
			System.out.println("2:�����ʦ");
			System.out.println("3:��ӿγ�");
			System.out.println("4:Ϊ�γ̷�����ʦ");
			System.out.println("5:ȡ��ָ���ڿ�");
			System.out.println("6:�鿴ָ���γ�״̬");
			System.out.println("7:������Ϣ��");
			System.out.println("8:ɾ��λ��");
			System.out.println("9:ɾ����ʦ");
			System.out.println("10:�����Դ��ռ��ͻ");
			System.out.println("11:���λ�ö�ռ��ͻ");
			System.out.println("12:Ѱ��ǰ��ƻ���");
			System.out.println("13:��ӡ��־");
			System.out.println("14:�˳�");
			// ¼������
			choise = num(1, 14);
			boolean flag =false;
			// ���λ����Ϣ����Դ��Ϣ
			if (locations.size() > 0) {
				flag =true;
				System.out.println("��֧���λ����Ϣ��");
				for (int i = 0; i < locations.size(); i++)
					System.out.println(i + ":" + locations.get(i).getname());
			}
			if (Teachers.size() > 0) {
				flag =true;
				System.out.println("��֧�����Դ��Ϣ��");
				for (int i = 0; i < Teachers.size(); i++)
					System.out.println(i + ":" + Teachers.get(i).toString());
			}
			if (list.size() > 0) {
				flag =true;
				System.out.println("��֧��Ŀγ���Ϣ��");
				for (int i = 0; i < list.size(); i++)
					System.out.println(i + ":" + list.get(i).getname());
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
				cancal();
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
				locationconflict();
				break;
			case 12:
				findearlyentry();
				break;
			case 13:
				print();
				break;
			case 14:
				over = true;
				break;
			default:
				break;
			}
		}

	}

	private void boardout() {// ѡ��ʱ��ص㴴�����
		if (list.size() <= 0) {
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
		System.out.println("ʱ���ʽΪyyyy-mm-dd hh:mm���磺��2020-10-06 04:55��");
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
		CourseBoard board = new CourseBoard(list, location, time);
		System.out.println("����Ѵ���");
		System.out.println("�����γ̣�");
		Iterator<CourseEntry> iterator = board.iterator();
		// board ��һ������Ϊ Board �Ķ���
		while (iterator.hasNext()) {
			CourseEntry pe = iterator.next();
			System.out.println(pe.getname() + ":" + pe.getstarttime() + "-" + pe.getovertime());
		}

	}

	private void findearlyentry() {
		if (list.size() <= 0) {
			System.out.println("�޼ƻ���޷���飡");
			return;
		}

		int i = 0;
		System.out.println("������Ҫ�г���������Դ�ļƻ���ı�ţ�");
		for (CourseEntry entry : list) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, list.size()-1);
		CourseEntry entry = list.get(i);
		List<Teacher> Teacherlist = entry.getresources();
		PlanningEntry<Teacher> entry2 = (PlanningEntry<Teacher>) entry;
		if (Teacherlist.size() <= 0) {
			System.out.println("δ������Դ���޷���飡");
			return;
		}
		System.out.println("������Ҫ��������ǰ��ƻ���ʹ�õ���Դ�ı�ţ�");
		i=0;
		for (Teacher Teacher : Teacherlist) {
			System.out.println("" + i + ":" + Teacher.toString());
			i++;
		}
		i = num(0, Teacherlist.size()-1);
		Teacher thisTeacher = Teacherlist.get(i);

		List<PlanningEntry<Teacher>> lister = new ArrayList<PlanningEntry<Teacher>>();
		for (CourseEntry entry1 : list) {
			lister.add(entry1);
		}

		PlanningEntry<Teacher> p = (new PlanningEntryAPI<Teacher>().findPreEntryPerResource(thisTeacher, entry2,
				lister));
		if (p != null)		
			System.out.println("ǰ��ƻ���Ϊ��" + p.getname());
		else
			System.out.println("û�ҵ�ǰ��ƻ��");
	}

	private void locationconflict() {
	
		if (list.size() <= 0 ){
			System.out.println("�޼ƻ���޷���飡");
			return;
		}
		List<PlanningEntry<Teacher>> lister = new ArrayList<PlanningEntry<Teacher>>();
		for (CourseEntry entry : list) {
			lister.add(entry);
		}

		if (!new PlanningEntryAPI<Teacher>().checkResourceExclusiveConflict(lister))
			System.out.println("�г�ͻ��");

		else {
			System.out.println("�޳�ͻ��");
			return;
		}
	}

	private void resourcesconflict() {

		if (list.size() <= 0) {
			System.out.println("�޼ƻ���޷���飡");
			return;
		}
		List<PlanningEntry<Teacher>> lister = new ArrayList<PlanningEntry<Teacher>>();
		for (CourseEntry entry : list) {
			lister.add(entry);
		}
		System.out.println("��ѡ�����ģʽ��\n1���ƻ����ģʽ \n2:�ƻ����+��Դ��ģʽ");

		int n = num(1, 2);
		if (n == 1) {
			if (!new PlanningEntryAPI<Teacher>().checkResourceExclusiveConflict(lister))
				System.out.println("�г�ͻ��");

			else {
				System.out.println("�޳�ͻ��");
				return;
			}
		}
		if (n == 2) {
			if (!new PlanningEntryAPI<Teacher>().checkResourceExclusiveConflict(Teachers, lister)) 
				System.out.println("�г�ͻ��");

			else {
				System.out.println("�޳�ͻ��");
				return;
			}
		}
	}

	private String getstate() {// ��ȡ�ƻ����״̬

		if (list.size() <= 0) {
			System.out.println("�޿γ̣��޷�����");
			return "";
		}
		System.out.println("�ɻ�ȡ�ļƻ������£�");
		int i = 0;
		for (CourseEntry entry : list) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, list.size()-1);
		CourseEntry entry = list.get(i);

		System.out.println(entry + "״̬Ϊ��" + entry.getstate().toString());
		return entry.getstate().toString();
	}

	public CourseEntry addentry(String name) {
		CourseEntry entry = new CourseEntry(name);
		list.add(entry);
		return entry;
	}

	public boolean addresource(Teacher Teacher) {
		if (!Teachers.contains(Teacher)) {
			Teachers.add(Teacher);
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

	public CourseEntry addentry() {// �û�����һ���ƻ��������ʱ�䣬�ص�
		if (locations.size() <= 0) {
			System.out.println("����С��һ�����޷���ӿγ�");
			return null;
		}
		CourseEntry entry = new CourseEntry(locations);
		list.add(entry);
		System.out.println("�������");
		return entry;
	}

	public Teacher addresource() {// ���룬���һ����Դ
		Teacher Teacher = new Teacher();
		Teachers.add(Teacher);
		System.out.println("�Ѽ�����ʦ");
		return Teacher;
	}

	public Location addlocation() {// ����һ���µ�λ��
		CourseLocation location = new CourseLocation();
		locations.add(location);
		System.out.println("�Ѽ������");
		return location;
	}

	public void assign() {// Ϊһ���ƻ��������Դ��
		if (list.size() <= 0) {
			System.out.println("�޿γ̣��޷�����");
			return;
		}
		if (Teachers.size() <= 0) {
			System.out.println("����ʦ���޷�����");
			return;
		}
		int i = 0;
		System.out.println("ѡ����Ŀγ̣�");
		for (CourseEntry entry : list) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, list.size()-1);
		CourseEntry entry = list.get(i);
		entry.addresource(Teachers);
		System.out.println("����ɹ�");
	}

	public void cancal() {// ȡ��ĳһ����
		if (list.size() <= 0) {
			System.out.println("�޼ƿγ̣��޷�����");
			return;
		}
		int i = 0;
		System.out.println("����γ̱����ȡ����");
		for (CourseEntry entry : list) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, list.size()-1);
		CourseEntry entry = list.get(i);
		entry.Cancel();
		System.out.println("ȡ���ɹ���");
	}

	public void deletresource() {// ɾ��ĳ����Դ
		if (Teachers.size() <= 0) {
			System.out.println("����ʦ���޷�ɾ��");
			return;
		}
		int i = 0;
		System.out.println("������ʦ����ɾ����");
		for (Teacher Teacher : Teachers) {
			System.out.println("" + i + ":" + Teacher.toString());
			i++;
		}
		i = num(0, Teachers.size()-1);
		Teacher Teacher = Teachers.get(i);
		Teachers.remove(Teacher);
		System.out.println("ɾ���ɹ�");
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
		System.out.println("ɾ���ɹ�");
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
		Logger logger = Logger.getLogger(CourseCalendarApp.class.getName());
		try {
			"".getBytes("invalidCharsetName");
		} catch (UnsupportedEncodingException e) {
			logger.severe(e.toString());
			e.printStackTrace();
		}
	}

	public static void main(String[] arg0) {
		CourseCalendarApp app = new CourseCalendarApp();

	}

}
