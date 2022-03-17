package P1;


import java.util.*;


public class PlanningEntryAPI<L> {//检查资源/位置冲突
	
	public boolean checkLocationConflict(List<PlanningEntry<L>> entries){//检查一组计划项中的位置冲突
		List<Location> locations= new ArrayList<Location>();//存在位置冲突的locations列表
		List<TimesLot> timelots =new ArrayList<TimesLot>();//记录每个地址对应的时间点
		
		for(PlanningEntry<L> entry:entries) {//将所有entry的所有的位置加入locations
			for(Location location: entry.getlocations()) {
				if(!locations.contains(location))
					locations.add(location);
			}
		}

		for(Location location : locations) {//依次检查输入的地址表中的位置是否有冲突
			for(PlanningEntry<L> entry: entries) {//将所有的计划项中的location对应的TimesLot提取出来
				if(entry.getlocations().contains(location))
				timelots.add(entry.gettimeslot().get(entry.getlocations().indexOf(location)));
			}			
			if(timeconflict(timelots)) {
				timelots.clear();//清空时间表
				return true;
			}				
			else
				return false;					
		}
		return true;
	}
	
	static public boolean timeconflict(List<TimesLot> lot) {//按照时间的早晚给TimesLot排序，一旦发现有前一个的结束时间在后一个开始时间之后，就判定该位置有位置冲突返回false
	    Collections.sort(lot, new Comparator<TimesLot>() {//给所有时间段按开始时间排序
	        @Override
	        public int compare(TimesLot m, TimesLot n) {
	        return (int) (m.getstarttimelong()-n.getstarttimelong());
	        }
	    });
	    if(lot.size()>1) {
	    	for(int i=0;i<lot.size()-1;i++) {
	    		if(lot.get(i).getovertimelong()>lot.get(i+1).getstarttimelong())
	    			return false;//若前一个结束时间大于后一个开始时间，返回false
	    	}
	    }
	    else
	    	return true;
		return true;
	}
	
	public boolean checkResourceExclusiveConflict(List<PlanningEntry<L>> entries){//检查一组计划项中的资源冲突,同上
		List<L> list= new ArrayList<L>();//记录所有资源的列表
		List<TimesLot> timelots =new ArrayList<TimesLot>();//提取所有资源
		for(PlanningEntry<L> entry:entries) {
			for(L l: entry.getresources()) {
				if(!list.contains(l))
				list.add(l);
			}
		}

		for(L l : list) {//依次检查输入的资源表中的位置是否有冲突
			for(PlanningEntry<L> entry: entries) {//将所有使用该资源的entry的所有timelots提取出来
				if(entry.getresources().contains(l))
					for(TimesLot time:entry.gettimeslot())
						timelots.add(time);
			}
			//按照时间的早晚给TimesLot排序，一旦发现有前一个的结束时间在后一个开始时间之后，就判定该位置有位置冲突返回false
			if(timeconflict(timelots)) {
				timelots.clear();//清空时间表
				return true;
			}				
			else
				return false;
		}
		return true;
	}
	
	public boolean checkResourceExclusiveConflict(List<L> list,List<PlanningEntry<L>> entries){//,同上
		List<TimesLot> timelots =new ArrayList<TimesLot>();//提取所有资源
		for(L l : list) {//依次检查输入的资源表中的位置是否有冲突
			for(PlanningEntry<L> entry: entries) {//将所有使用该资源的entry的所有timelots提取出来
				if(entry.getlocations().contains(l))
					for(TimesLot time:entry.gettimeslot())//
						timelots.add(time);
			}
			//按照时间的早晚给TimesLot排序，一旦发现有前一个的结束时间在后一个开始时间之后，就判定该位置有位置冲突返回false
			if(timeconflict(timelots)) {
				timelots.clear();//清空时间表
				return true;
			}				
			else
				return false;
		}
		return true;
	}
	
	//找到前序计划项
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