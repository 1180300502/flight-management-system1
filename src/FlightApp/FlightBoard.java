package FlightApp;

import P1.Location;
import P1.TimesLot;
import java.awt.BorderLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;

public class FlightBoard extends JFrame {// ��board�̳�
	private static final long serialVersionUID = 1L;
	private List<FlightEntry> list = new ArrayList<FlightEntry>();
	private List<FlightEntry> listarrive = new ArrayList<FlightEntry>();// ��������
	private List<FlightEntry> listgo = new ArrayList<FlightEntry>();// �������
	private Location location;// ��ǰ�ص�
	private String time;// ��ǰʱ��
	
	//������
	public FlightBoard(List<FlightEntry> entry, Location location, String time) throws ParseException {		
		this.list=entry;
		this.location = location;
		this.time = time;
		find();// �Ž�����
		visualize();// �����ӻ�
	}

	public void find() throws ParseException {// ���ƻ��Ҫ��Ž�������
		for (FlightEntry entry : list) {
			if (!entry.getentrystate().getstate().isEmpty()&&!entry.getentrystate().getstate().equals("unassigned")) {//����ƻ���Ǵ���δ����״̬�ҷǿ�
				System.out.println(entry.getstartlocations()+"||"+entry.getstarttimes()+"---"+time);
				if (entry.getoverlocations()==location&&(inonehour(entry.getovertimes(),time)||(entry.gettimelots().contiantime(time)&&inonehour(time,entry.getovertimes())))) {// ����ǰ��1Сʱ�ڸõؽ���ķɻ�
					listarrive.add(entry);
					System.out.println("in!");
				}
				if (entry.getstartlocations()==location&&(inonehour(time,entry.getstarttimes())||(entry.gettimelots().contiantime(time)&&inonehour(entry.getstarttimes(),time)))) {// ����ǰ��1Сʱ�ڸõ���ɵķɻ�
					listgo.add(entry);
					System.out.println("out!");
				}
			}
		}
	}

	static public boolean inonehour(String a, String b) throws ParseException {// �ж�b�Ƿ���a�ĺ�һ��Сʱ��
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date startdate = s.parse(a);
		Date enddate = s.parse(b);
		long betweendate = (enddate.getTime() - startdate.getTime())/(60*1000);
		if((int)betweendate>60||(int)betweendate<0)
		return false;
		
		return true;
	}

	
	public Iterator<FlightEntry> iterator(){
		Collections.sort(list, new Comparator<FlightEntry>() {//������ʱ��ΰ���ʼʱ������
	        @Override
	        public int compare(FlightEntry m, FlightEntry n) {
	        return (int) (m.gettimeslot().get(0).getstarttimelong()-n.gettimeslot().get(0).getstarttimelong());
	        }
	    });
		
		return list.iterator();
	}
	
	private void compare() {//���ƻ��ʱ������
		Collections.sort(listarrive, new Comparator<FlightEntry>() {//������ʱ��ΰ���ʼʱ������
	        @Override
	        public int compare(FlightEntry m, FlightEntry n) {
	        return (int) (m.gettimeslot().get(0).getovertimelong()-n.gettimeslot().get(0).getovertimelong());
	        }
	    });
		
		Collections.sort(listgo, new Comparator<FlightEntry>() {//������ʱ��ΰ���ʼʱ������
	        @Override
	        public int compare(FlightEntry m, FlightEntry n) {
	        return (int) (m.gettimeslot().get(0).getstarttimelong()-n.gettimeslot().get(0).getstarttimelong());
	        }
	    });
	}
	
	
	public void visualize() {//�������ӻ�
		compare();//���ƻ��ʱ������
		String[][] data = new String[listarrive.size() + listgo.size() + 2][4];
		String[] datatitle = { "��ǰʱ�䣺" + time, "��ǰ�ص�" + location ,"_______","_______"};
		int n = listgo.size();
		int m=listarrive.size();
		if (m != 0) {//��ʼ��
			data[0][0] = "�ִﺽ��";
			data[0][1] = "";
			data[0][2] = "";
			data[0][3] = "";
			for (int i = 0; i < m; i++) {// �ֱ�¼�����ƣ�ʱ�䣬ʼ��վ���յ�վ��״̬
				FlightEntry arrive = listarrive.get(i - 1);
				data[i][0] = arrive.getname();// ����
				data[i][1] = arrive.getovertimes();// ʱ��
				data[i][2] = arrive.getstartlocations().getname() + "-"+arrive.getoverlocations().getname();// �ص�
				if (arrive.getentrystate().getstate().equals("cancel"))
					data[i][3] = "��ȡ��";// ״̬
				if (TimesLot.comparetimestring(time, arrive.getovertimes()))
				data[i][3] = "�ѵִ�";// ״̬
				else
				data[i][3] = "�����ִ�";// ״̬
			}
		}
		if (n != 0) {
			if(m==0)
				m--;
			data[m+1][0] = "��ɺ���";
			data[m+1][1] = "";
			data[m+1][2] = "";
			data[m+1][3] = "";
			for (int i = m +2; i <= n+m+1; i++) {// �ֱ�¼�����ƣ�ʱ�䣬ʼ��վ���յ�վ��״̬
				FlightEntry arrive = listgo.get(i -m-2);
				data[i][0] = arrive.getname();// ����
				data[i][1] = arrive.getstarttimes();// ʱ��
				data[i][2] = arrive.getstartlocations().getname() + "-"+arrive.getoverlocations().getname();// �ص�
				if (arrive.getentrystate().getstate().equals("cancel"))
					data[i][3] = "��ȡ��";// ״̬
				if (TimesLot.comparetimestring( arrive.getstarttimes(),time))
				data[i][3] = "�������";// ״̬
				else
				data[i][3] = "�����";// ״̬
			}
		}
		JTable jtable = new JTable(data, datatitle);
		JScrollPane jscrollpane = new JScrollPane(jtable);
		setTitle("�������");
		setBounds(400, 400, 800, 400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(jscrollpane, BorderLayout.CENTER);
	}

}
