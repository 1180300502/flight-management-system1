package TrainApp;

import P1.Location;
import P1.TimesLot;
import java.awt.BorderLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;

import FlightApp.FlightEntry;

public class TrainBoard extends JFrame {// ��board�̳�
	private static final long serialVersionUID = 1L;
	private Location location;// ��ǰ�ص�
	private String time;// ��ǰʱ��
	private List<TrainEntry> listarrive = new ArrayList<TrainEntry>();//��������
	private List<TrainEntry> listgo = new ArrayList<TrainEntry>();//��������
	private List<TrainEntry> list = new ArrayList<TrainEntry>();
	
	//������

	public TrainBoard(List<TrainEntry> entry, Location location, String time) throws ParseException {		
		this.list=entry;
		this.location = location;
		this.time = time;
		find(entry);// �Ž�����
		visualize();// �����ӻ�
	}

	private void find(List<TrainEntry> entrys) throws ParseException {
		for (TrainEntry entry : entrys) {// ���ܺ��иõ�վ��ĳ���
			if (!entry.getentrystate().getstate().isEmpty()&&!entry.getentrystate().getstate().equals("unassigned")&& entry.getlocations().contains(location)) {//����ƻ���Ǵ���δ����״̬�ҷǿ�
				// �ų���һ��Сʱ֮�����κζ����ĳ���
				List<TimesLot> t = entry.gettimeslot();
				for (TimesLot lot : t) {

					if (inonehour(lot.getovertime(), time) || (lot.contiantime(time) && inonehour(time, lot.getovertime()))) 
					{
						listarrive.add(entry);
						System.out.println("in!");
					}
  
					if (inonehour(time, lot.getstarttime())|| (lot.contiantime(time) && inonehour(lot.getstarttime(), time))) 
					{
						listgo.add(entry);
						System.out.println("out!");
					}
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
	
		
	public Iterator<TrainEntry> iterator(){
		Collections.sort(list, new Comparator<TrainEntry>() {//������ʱ��ΰ���ʼʱ������
	        @Override
	        public int compare(TrainEntry m, TrainEntry n) {
	        return (int) (m.gettimeslot().get(0).getstarttimelong()-n.gettimeslot().get(0).getstarttimelong());
	        }
	    });
		
		return list.iterator();
	}
	
	
	public void visualize() throws ParseException {// �����ӻ�
		String[][] data = new String[listarrive.size() + listgo.size() + 2][4];
		String[] datatitle = { "��ǰʱ�䣺" + time, "��ǰ�ص�" + location, "_______", "_______" };
		int n = listgo.size();
		int m = listarrive.size();
		if (m != 0) {//��ʼ��
			data[0][0] = "�ִﳵ��";
			data[0][1] = "";
			data[0][2] = "";
			data[0][3] = "";
			for (int i = 1; i <= m; i++) {// �ֱ�¼�����ƣ�ʱ�䣬ʼ��վ���յ�վ��״̬
				TrainEntry arrive = listarrive.get(i - 1);
				data[i][0] = arrive.getname();// ����
				List<TimesLot> t = arrive.gettimeslot();
				for (int j = t.size() - 1; j >= 0; j--) {

					TimesLot lot = arrive.gettimeslot().get(j);
					if ((lot.contiantime(time) && inonehour(time, lot.getovertime()))) {// ʱ���֮��
						data[i][1] = lot.getovertime();
						data[i][2] = arrive.getnumlocations(j).getname() +"-"+  arrive.getnumlocations(j + 1).getname();
						data[i][3] = "�����ִ�";
						break;
					}

					if (inonehour(lot.getovertime(), time)) {
						data[i][1] = lot.getovertime();
						data[i][2] = arrive.getnumlocations(j).getname() +"-"+  arrive.getnumlocations(j + 1).getname();
						data[i][3] = "�ѵִ�";
						break;
					}
				}
			}
		}
			
		if(m==0)
			m--;
		if (n != 0) {//ͬ��
			data[m+1][0] = "��������";
			data[m+1][1] = "";
			data[m+1][2] = "";
			data[m+1][3] = "";
			for (int i = 2+m; i <= n+m+1; i++) {// �ֱ�¼�����ƣ�ʱ�䣬ʼ��վ���յ�վ��״̬
				TrainEntry go = listgo.get(i - m- 2);
				data[i][0] = go.getname();// ����
				List<TimesLot> t = go.gettimeslot();
				for (int j = 0; j < t.size(); j++) {

					TimesLot lot = go.gettimeslot().get(j);
					if ((lot.contiantime(time) && inonehour(lot.getstarttime(), time))) {// ʱ���֮��
						data[i][1] = lot.getstarttime();
						data[i][2] = go.getnumlocations(j).getname() + "-"+ go.getnumlocations(j + 1).getname();
						data[i][3] = "�ѳ���";
						break;
					}

					if (inonehour(time, lot.getstarttime())) {
						data[i][1] = lot.getstarttime();
						data[i][2] = go.getnumlocations(j).getname() +"-"+ go.getnumlocations(j + 1).getname();
						data[i][3] = "��������";
						break;
					}
				}
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