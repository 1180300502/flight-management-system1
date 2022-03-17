package P1;

import java.util.List;


public interface PlanningEntry<L> {
	public void Run();//启动计划项
	public void Cancel();//取消计划项
	public void Block();//挂起计划项
	public void Commplete();//完成计划项
	public String getname();//获取计划项的名字
	public String getstate();//获取当前状态
	public List<Location> getlocations();//获取位置
	public List<L> getresources();//获取资源
	public List<TimesLot> gettimeslot();//获取时间表
}

