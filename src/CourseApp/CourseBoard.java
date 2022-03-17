package CourseApp;

import java.awt.BorderLayout;
import java.util.*;
import P1.Location;
import P1.TimesLot;
import javax.swing.*;

public class CourseBoard extends JFrame {// ��board�̳�
	private static final long serialVersionUID = 1L;
	public List<CourseEntry> list = new ArrayList<CourseEntry>();// �γ�����
	private Location location;// ��ǰ�ص�
	private String time;// ��ǰʱ��

	public CourseBoard(List<CourseEntry> entrys, Location location, String time) {
		this.time = time;
		this.location = location;
		find(entrys);// �Ž�����
		visualize();// �����ӻ�
	}

	public void find(List<CourseEntry> entrys) {// ���ƻ��Ҫ��Ž�������
		for (CourseEntry entry : entrys) {
			if (!entry.getentrystate().getstate().isEmpty()&&!entry.getentrystate().getstate().equals("unassigned")) {//����ƻ���Ǵ���δ����״̬�ҷǿ�
				if (entry.getstartlocation() == location) {
					list.add(entry);
				}
			}
		}
	}
	public Iterator<CourseEntry> iterator(){
		Collections.sort(list, new Comparator<CourseEntry>() {//������ʱ��ΰ���ʼʱ������
	        @Override
	        public int compare(CourseEntry m, CourseEntry n) {
	        return (int) (m.gettimeslot().get(0).getstarttimelong()-n.gettimeslot().get(0).getstarttimelong());
	        }
	    });
		
		return list.iterator();
	}
	public void visualize() {// �����ӻ�
		String[][] data = new String[list.size() + 1][4];
		String[] datatitle = { "��ǰʱ�䣺" + time, "��ǰ�ص�" + location ,"_______","_______"};
		int n = list.size();

			for (int i = 0; i < n; i++) {// �ֱ�¼�����ƣ�ʱ�䣬���ң�״̬
				CourseEntry arrive = list.get(i - n - 1);
				data[i][0] = arrive.getname();// ����
				data[i][1] = arrive.getovertime();// ʱ��
				data[i][2] = arrive.getstartlocation().getname();// �ص�
				if (arrive.getentrystate().getstate().equals("cancel"))
					data[i][3] = "��ȡ��";// ״̬
				if (TimesLot.comparetimestring(time,arrive.getovertime()))
				data[i][3] = "�ѽ���";// ״̬
				else if(TimesLot.comparetimestring(arrive.getstarttime(),time))
				data[i][3] = "δ��ʼ";// ״̬
				else 
				data[i][3] = "���ڽ���";
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
