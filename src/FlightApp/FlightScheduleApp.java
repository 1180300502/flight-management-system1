package FlightApp;

import P1.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class FlightScheduleApp {

	private final List<Location> locations = new ArrayList<Location>();// ��֧���λ��
	private final List<Plane> planes = new ArrayList<Plane>();// ��֧�����Դ
	public final List<FlightEntry> entries = new ArrayList<FlightEntry>();// ��ʹ�õļƻ���

	public FlightScheduleApp() throws ParseException, IOException {
		System.out.println("-----�������ϵͳ-----");
		boolean over = false;
		int choise = 13;
		while (!over) {

			System.out.println("��ѡ��������һ��������");			
			System.out.println("1:���λ��");
			System.out.println("2:�����Դ");
			System.out.println("3:��Ӻ���");
			System.out.println("4:Ϊ�������ɻ�");
			System.out.println("5:ȡ��ָ������");
			System.out.println("6:�鿴ָ������״̬");
			System.out.println("7:������Ϣ��");
			System.out.println("8:ɾ��λ��");
			System.out.println("9:ɾ����Դ");
			System.out.println("10:�����Դ��ռ��ͻ");
			System.out.println("11:Ѱ��ǰ��ƻ���");
			System.out.println("12:��ȡ�ļ�");
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
			if (planes.size() > 0) {
				flag =true;
				System.out.println("��֧����ĺ�����Ϣ��");
				for (int i = 0; i < planes.size(); i++)
					System.out.println(i + ":" + planes.get(i).toString());
			}
			if (entries.size() > 0) {
				flag =true;
				System.out.println("��ʹ�õļƻ�����Ϣ��");
				for (int i = 0; i < entries.size(); i++)
					System.out.println(i + ":" + entries.get(i).getname()+"---------"+entries.get(i).gettimelots().toString());
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
				break;
			case 12:
				readfile();		
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

	private boolean readfile()throws IOException, ParseException{
		System.out.println("��������������ļ���ţ�(��Ŵ�1��5���ֱ��Ӧ��ʦ������5���ļ�)");
		int i=num(1,5);
		BufferedReader reader=new BufferedReader(new FileReader("src\\Flights\\FLightSchedule_"+i+".txt"));
        StringBuilder result=new StringBuilder();
        String theLine=null;
        i=0;
        while ((theLine = reader.readLine()) != null) {
			i++;// ��¼����
			result.append(theLine + "\n");
		}
		String s1 = result.toString();
		SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd");

		Pattern b = Pattern.compile("Flight:(\\d{4}-\\d{2}-\\d{2}),([A-Z]{2})(\\d{2,4})\\n"
				+ "\\{\\nDepartureAirport:([A-Za-z]+)\\nArrivalAirport:([A-Za-z]+)\\nDepatureTime:(\\d{4}-\\d{2}-\\d{2})"
				+ " (\\d{2}:\\d{2})\\nArrivalTime:(\\d{4}-\\d{2}-\\d{2}) (\\d{2}:\\d{2})\\nPlane:(B|N)"
				+ "(\\d{4})+\\n\\{\\nType:([A-Z0-9]+)\\nSeats:(\\d{1,3})\\nAge:(\\d+)(.\\d{1}|)\\n\\}\\n\\}\\n");
		Matcher matcher1 = b.matcher(s1);

		List<String> flightdate = new ArrayList<String>(); 
		List<String> flightname = new ArrayList<String>();
		List<String> start = new ArrayList<String>();
		List<String> over = new ArrayList<String>();
		List<String> starttime1 = new ArrayList<String>();
		List<String> starttime2 = new ArrayList<String>();
		List<String> overtime1 = new ArrayList<String>();
		List<String> overtime2 = new ArrayList<String>();
		List<String> starttime = new ArrayList<String>();
		List<String> overtime = new ArrayList<String>();
		List<String> planename = new ArrayList<String>();
		List<String> planemodel = new ArrayList<String>();
		List<String> planeseat = new ArrayList<String>();
		List<String> planeage = new ArrayList<String>();
		while (matcher1.find())// �����۲��Ƿ������ݷǷ�
		{
			System.out.println("_____________");
			System.out.println(matcher1.group(1) + "\n" + // ����
					matcher1.group(2) + matcher1.group(3) + "\n" + // ����
					matcher1.group(4) + "\n" + // ��������
					matcher1.group(5) + "\n" + // �ִ����
					matcher1.group(6) + " " + matcher1.group(7) + "\n" + // ����ʱ��
					matcher1.group(8) + " " + matcher1.group(9) + "\n" + // �ִ�ʱ��
					matcher1.group(10) + matcher1.group(11) + "\n" + // �ɻ���
					matcher1.group(12) + "\n" + // �ɻ��ͺ�
					matcher1.group(13) + "\n" + // ��λ��
					matcher1.group(14) + "\n" +matcher1.group(15)+ "over\n");// ����
			flightdate.add(matcher1.group(1));// ����
			flightname.add(matcher1.group(2) + Integer.parseInt(matcher1.group(3)));// �淶�������
			start.add(matcher1.group(4));
			over.add(matcher1.group(5));
			int m=0;
			{// �ж������Ƿ����
				Date startDate = s2.parse(matcher1.group(6));
				Date endDate = s2.parse(matcher1.group(8));
				int betweenDate = (int) (endDate.getTime() - startDate.getTime()) / (60 * 60 * 24 * 1000);
				if (betweenDate < 0 || betweenDate > 1)// ��Ϊһ�죬����false
				{
					System.out.println("���ڴ���ʱ����̫��");
					return false;

				}
				if (matcher1.group(6).equals(matcher1.group(1)))// ���һ�в�һ�£�����false
					m++;
				else{
					System.out.println("���ڴ���");
					return false;

				}
			}
			starttime1.add(matcher1.group(6));
			starttime.add(matcher1.group(6) + " " + matcher1.group(7));
			starttime2.add(matcher1.group(7));
			overtime1.add(matcher1.group(8));
			overtime.add(matcher1.group(8) + " " + matcher1.group(9));
			overtime2.add(matcher1.group(9));
			planename.add(matcher1.group(10) + matcher1.group(11));
			planemodel.add(matcher1.group(12));
			if (Integer.parseInt(matcher1.group(13)) > 600 || Integer.parseInt(matcher1.group(13)) < 50)// ���һ�в�һ�£�����false
			{
				System.out.println("��λ���Ƿ�");
				return false;

			}
			planeseat.add(matcher1.group(13));
			if (matcher1.group(15).length()==2&&matcher1.group(14).indexOf("0") == 0 && matcher1.group(14).length() > 1) {
				System.out.println("��������Ƿ���");
				return false;

			}
			double age =0; 
			if(matcher1.group(15).length()==2)
				{
					Double.parseDouble(matcher1.group(14)+ matcher1.group(15));
					planeage.add(matcher1.group(14)+ matcher1.group(15));
				}
			if(matcher1.group(15).length()==0)
				{
					Double.parseDouble(matcher1.group(14));
					planeage.add(matcher1.group(14));
				}
			if (age < 0 || age > 30) {
				System.out.println("������ֵ�Ƿ���");
				return false;
			}
			int n = flightname.size() - 1;// �жϵ�ǰ�ĺ��������Ƿ�����������г�ͻ
			String name = flightname.get(n);
			String thisstarttime1 = starttime1.get(n);
			String thisovertime1 = overtime1.get(n);
			String thisstarttime2 = starttime2.get(n);
			String thisovertime2 = overtime2.get(n);
			String thisstart = start.get(n);
			String thisover = over.get(n);
			for (int j = 0; j < n - 1; j++) {
				if (flightname.get(j).equals(name)) {// ͬ������
					if (!start.get(j).equals(thisstart) || !over.get(j).equals(thisover)) {
						System.out.println("ͬ�������ַ��ƥ�䣡");
						return false;
					}
					if (starttime1.get(j).equals(thisstarttime1) || overtime1.get(j).equals(thisovertime1)) {
						System.out.println("ͬ������ÿ�����������ڣ�");
						return false;
					}
					if (!starttime2.get(j).equals(thisstarttime2) || !overtime2.get(j).equals(thisovertime2)) {
						System.out.println("ͬ�����ൽ��ʱ�䲻ͬ��");
						return false;
					}
				}
			}
		}

		if (i / 13 > flightname.size() )// ÿ����Ϣ���13�У������������13���ں����������˵���еĺ���û��¼�ϣ�����Ƿ�
		{
			System.out.println("�ļ����벻���Ϲ涨�����ļ��޷����룡");
			System.out.println(i);
			System.out.println(flightname.size());
			return false;
		}

		
		//�ȼ����ַ
		Set<String> set =new HashSet<String>(start);
		set.addAll(over);
		int n=0;
		for(String a:set) {//�������е�ַ��û���ظ�
			boolean flag =true;
			for(Location locat: locations)//���ܼ���ͬ��վ��
			{
				if(!locat.getname().equals(a))
					flag=true;
				else
					flag=false;
			}
			if(flag)
			locations.add(new FlightLocation(a));
		}

		//�ټ���ɻ�
		for(int j=0;j<planename.size();j++) {//�������зɻ���û���ظ�
			boolean flag =true;
			String a=planename.get(j);
			for(Plane plane: planes)//���ܼ���ͬ���ɻ�
			{
				if(!plane.getname().equals(a))
					flag=true;
				else
					flag=false;
			}
			if(flag) {
				Plane p=new Plane(planename.get(j),
						planemodel.get(j),
						Integer.parseInt(planeseat.get(j)),
						Double.parseDouble(planeage.get(j)));//����ɻ�
				planes.add(p);
			}
			
		}
		for(int j=0;j<flightname.size();j++) {
			FlightEntry flight =new FlightEntry(flightname.get(j));
			for(Location locat: locations)//����ʼ��վ
			{
				if(!locat.getname().equals(start.get(j)))
					n++;
				else{
					flight.addlocation(locat);
					break;
					}
			}
			for(Location locat: locations)//�����յ�վ
			{
				if(!locat.getname().equals(over.get(j))) 
					n++;
				else{
					flight.addlocation(locat);
					break;
				}
					
			}
			
			flight.addtimelot(new TimesLot(starttime.get(j),overtime.get(j)));//������ֹʱ��
			for(Plane plane: planes)//�����յ�վ
			{
				if(!plane.getname().equals(planename.get(j))) 
					n++;
				else{
					flight.addresource(plane);
					break;
				}
				
			}
			
			entries.add(flight);
		}
		
		// �ж��Ƿ��зǷ�����
		return true;
	}
        
	

	private void findearlyentry() {//Ѱ��ǰ��ƻ���
		if (entries.size() <= 0) {
			System.out.println("�޼ƻ���޷���飡");
			return;
		}

		int i = 0;
		System.out.println("������Ҫ�г���������Դ�ļƻ���ı�ţ�");
		for (FlightEntry entry : entries) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, entries.size()-1);
		FlightEntry entry = entries.get(i);
		List<Plane> planelist = entry.getresources();
		if (planelist.size() <= 0) {
			System.out.println("δ������Դ���޷���飡");
			return;
		}
		PlanningEntry<Plane> entry2 = (PlanningEntry<Plane>) entry;// ��������
		System.out.println("������Ҫ��������ǰ��ƻ���ʹ�õ���Դ�ı�ţ�");
		i=0;
		for (Plane plane : planelist) {
			System.out.println("" + i + ":" + plane.toString());
			i++;
		}
		i = num(0, planelist.size()-1);
		Plane thisplane = planelist.get(i);

		List<PlanningEntry<Plane>> lister = new ArrayList<PlanningEntry<Plane>>();
		for (FlightEntry entry1 : entries) {
			lister.add(entry1);
		}

		PlanningEntry<Plane> p = (new PlanningEntryAPI<Plane>().findPreEntryPerResource(thisplane, entry2, lister));
		if (p != null)		
			System.out.println("ǰ��ƻ���Ϊ��" + p.getname());
		else
			System.out.println("û�ҵ�ǰ��ƻ��");
	}

	private void resourcesconflict() {
		if (entries.size() <= 0) {
			System.out.println("�޼ƻ���޷���飡");
			return;
		}
		List<PlanningEntry<Plane>> lister = new ArrayList<PlanningEntry<Plane>>();
		for (FlightEntry entry : entries) {
			lister.add(entry);
		}
		System.out.println("��ѡ�����ģʽ��\n1���ƻ����ģʽ \n2:�ƻ����+��Դ��ģʽ");

		int n = num(1, 2);
		if (n == 1) {
			if (!new PlanningEntryAPI<Plane>().checkResourceExclusiveConflict(lister))
				System.out.println("�г�ͻ��");

			else {
				System.out.println("�޳�ͻ��");
				return;
			}
		}
		if (n == 2) {
			if (!new PlanningEntryAPI<Plane>().checkResourceExclusiveConflict(planes, lister)) 
				System.out.println("�г�ͻ��");

			else {
				System.out.println("�޳�ͻ��");
				return;
			}
		}

	}

	private void boardout() throws ParseException {// ѡ��ʱ��ص㴴�����
		if (entries.size() <= 0) {
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
			flag=true;
			time = a.nextLine();
			if (TimesLot.istime(time))// ��Υ��flag����Ϊfalse
				flag=true;
			else{
				System.out.println("�����ʽ��������������");
				flag = false;
			}
		} 
		FlightBoard board = new FlightBoard(entries, location, time);
		System.out.println("����Ѵ���");
		System.out.println("�����ƻ��");
		Iterator<FlightEntry> iterator = board.iterator();
		// board ��һ������Ϊ Board �Ķ���
		while (iterator.hasNext()) {
			FlightEntry pe = iterator.next();
			System.out.println(pe.getname() + ":" + pe.getstarttimes() + "-" + pe.getovertimes());
		}

	}

	private String getstate() {// ��ȡ�ƻ����״̬

		if (entries.size() <= 0) {
			System.out.println("�޼ƻ���޷�����");
			return "";
		}
		System.out.println("�ɻ�ȡ�ļƻ������£�");
		int i = 0;
		for (FlightEntry entry : entries) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, entries.size()-1);
		FlightEntry entry = entries.get(i);

		System.out.println(entry + "״̬Ϊ��" + entry.getstate().toString());
		return entry.getstate().toString();
	}

	public FlightEntry addentry(String name) {
		FlightEntry entry = new FlightEntry(name);
		entries.add(entry);
		return entry;
	}

	public boolean addresource(Plane plane) {
		if (!planes.contains(plane)) {
			planes.add(plane);
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

	public FlightEntry addentry() {// �û�����һ���ƻ��������ʱ�䣬�ص�
		if (locations.size() <= 1) {
			System.out.println("λ��С���������޷���Ӻ���");
			return null;
		}
		FlightEntry entry = new FlightEntry(locations);
		entries.add(entry);
		System.out.println("������ɣ�");
		return entry;
	}

	public Plane addresource() {// ���룬���һ����Դ
		Plane plane = new Plane();
		planes.add(plane);
		System.out.println("�Ѽ�����Դ��");
		return plane;
	}

	public Location addlocation() {// ����һ���µ�λ��
		FlightLocation location = new FlightLocation();
		locations.add(location);
		System.out.println("�Ѽ���λ�ã�");
		return location;
	}

	public void assign() {// Ϊһ���ƻ��������Դ��
		if (entries.size() <= 0) {
			System.out.println("�޼ƻ���޷�����");
			return;
		}
		if (planes.size() <= 0) {
			System.out.println("�޷ɻ����޷�����");
			return;
		}
		int i = 0;
		System.out.println("ѡ����ĺ��ࣺ");
		for (FlightEntry entry : entries) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, entries.size()-1);
		FlightEntry entry = entries.get(i);
		entry.addresource(planes);
		System.out.println("����ɹ�");
	}

	public void cancel() {// ȡ��ĳһ����
		if (entries.size() <= 0) {
			System.out.println("�޼ƻ���޷�����");
			return;
		}
		int i = 0;
		System.out.println("���뺽�����ȡ����");
		for (FlightEntry entry : entries) {
			System.out.println("" + i + ":" + entry.getname());
			i++;
		}
		i = num(0, entries.size()-1);
		FlightEntry entry = entries.get(i);
		entry.Cancel();
		System.out.println("ȡ���ɹ�");
	}

	public void deletresource() {// ɾ��ĳ����Դ
		if (planes.size() <= 0) {
			System.out.println("�޷ɻ����޷�ɾ��");
			return;
		}
		int i = 0;
		System.out.println("����ɻ�����ȡ����");
		for (Plane plane : planes) {
			System.out.println("" + i + ":" + plane.toString());
			i++;
		}
		i = num(0, planes.size()-1);
		Plane plane = planes.get(i);
		planes.remove(plane);
		System.out.println("ɾ���ɹ�");
	}

	public void deletlocation() {// ȡ��ĳһλ��
		if (locations.size() <= 0) {
			System.out.println("��λ�ã��޷�ɾ��");
			return;
		}
		int i = 0;
		System.out.println("����λ�ú���ɾ����");
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
		Logger logger = Logger.getLogger(FlightScheduleApp.class.getName());
		try {
			"".getBytes("invalidCharsetName");
		} catch (UnsupportedEncodingException e) {
			logger.severe(e.toString());
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] arg0) throws ParseException, IOException {
			FlightScheduleApp app = new FlightScheduleApp();
	}
	/*public class Main {
		public static void main(String[] args) {
			Logger logger = Logger.getLogger(Main.class.getName());
			logger.info("begin");
			try {
				"".getBytes("invalidCharsetName");
			} catch (UnsupportedEncodingException e) {
				logger.severe(e.toString());
				e.printStackTrace();
			}
			logger.info("end");
		}
	}*/
	
}