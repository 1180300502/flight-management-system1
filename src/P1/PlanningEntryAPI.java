package P1;


import java.util.*;


public class PlanningEntryAPI<L> {//�����Դ/λ�ó�ͻ
	
	public boolean checkLocationConflict(List<PlanningEntry<L>> entries){//���һ��ƻ����е�λ�ó�ͻ
		List<Location> locations= new ArrayList<Location>();//����λ�ó�ͻ��locations�б�
		List<TimesLot> timelots =new ArrayList<TimesLot>();//��¼ÿ����ַ��Ӧ��ʱ���
		
		for(PlanningEntry<L> entry:entries) {//������entry�����е�λ�ü���locations
			for(Location location: entry.getlocations()) {
				if(!locations.contains(location))
					locations.add(location);
			}
		}

		for(Location location : locations) {//���μ������ĵ�ַ���е�λ���Ƿ��г�ͻ
			for(PlanningEntry<L> entry: entries) {//�����еļƻ����е�location��Ӧ��TimesLot��ȡ����
				if(entry.getlocations().contains(location))
				timelots.add(entry.gettimeslot().get(entry.getlocations().indexOf(location)));
			}			
			if(timeconflict(timelots)) {
				timelots.clear();//���ʱ���
				return true;
			}				
			else
				return false;					
		}
		return true;
	}
	
	static public boolean timeconflict(List<TimesLot> lot) {//����ʱ��������TimesLot����һ��������ǰһ���Ľ���ʱ���ں�һ����ʼʱ��֮�󣬾��ж���λ����λ�ó�ͻ����false
	    Collections.sort(lot, new Comparator<TimesLot>() {//������ʱ��ΰ���ʼʱ������
	        @Override
	        public int compare(TimesLot m, TimesLot n) {
	        return (int) (m.getstarttimelong()-n.getstarttimelong());
	        }
	    });
	    if(lot.size()>1) {
	    	for(int i=0;i<lot.size()-1;i++) {
	    		if(lot.get(i).getovertimelong()>lot.get(i+1).getstarttimelong())
	    			return false;//��ǰһ������ʱ����ں�һ����ʼʱ�䣬����false
	    	}
	    }
	    else
	    	return true;
		return true;
	}
	
	public boolean checkResourceExclusiveConflict(List<PlanningEntry<L>> entries){//���һ��ƻ����е���Դ��ͻ,ͬ��
		List<L> list= new ArrayList<L>();//��¼������Դ���б�
		List<TimesLot> timelots =new ArrayList<TimesLot>();//��ȡ������Դ
		for(PlanningEntry<L> entry:entries) {
			for(L l: entry.getresources()) {
				if(!list.contains(l))
				list.add(l);
			}
		}

		for(L l : list) {//���μ���������Դ���е�λ���Ƿ��г�ͻ
			for(PlanningEntry<L> entry: entries) {//������ʹ�ø���Դ��entry������timelots��ȡ����
				if(entry.getresources().contains(l))
					for(TimesLot time:entry.gettimeslot())
						timelots.add(time);
			}
			//����ʱ��������TimesLot����һ��������ǰһ���Ľ���ʱ���ں�һ����ʼʱ��֮�󣬾��ж���λ����λ�ó�ͻ����false
			if(timeconflict(timelots)) {
				timelots.clear();//���ʱ���
				return true;
			}				
			else
				return false;
		}
		return true;
	}
	
	public boolean checkResourceExclusiveConflict(List<L> list,List<PlanningEntry<L>> entries){//,ͬ��
		List<TimesLot> timelots =new ArrayList<TimesLot>();//��ȡ������Դ
		for(L l : list) {//���μ���������Դ���е�λ���Ƿ��г�ͻ
			for(PlanningEntry<L> entry: entries) {//������ʹ�ø���Դ��entry������timelots��ȡ����
				if(entry.getlocations().contains(l))
					for(TimesLot time:entry.gettimeslot())//
						timelots.add(time);
			}
			//����ʱ��������TimesLot����һ��������ǰһ���Ľ���ʱ���ں�һ����ʼʱ��֮�󣬾��ж���λ����λ�ó�ͻ����false
			if(timeconflict(timelots)) {
				timelots.clear();//���ʱ���
				return true;
			}				
			else
				return false;
		}
		return true;
	}
	
	//�ҵ�ǰ��ƻ���
	public PlanningEntry<L> findPreEntryPerResource(L l, PlanningEntry<L> e, List<PlanningEntry<L>> entries)
	{
		String time=e.gettimeslot().get(0).getstarttime();
		for(PlanningEntry<L> entry : entries) {
			List<TimesLot> lot =entry.gettimeslot();
			int num =lot.size();
			if(entry.getresources().contains(l)&&TimesLot.comparetimestring(time,lot.get(num-1).getovertime()))
				return entry;
		}
		return null;
	}
	
	

}