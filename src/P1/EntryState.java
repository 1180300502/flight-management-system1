package P1;

public class EntryState {
	private boolean assign;//分配状态
	private boolean	run;//运行状态
	private boolean hold;//挂起状态
	private boolean complete;//完成状态
	private boolean cancel;//取消状态
	
	public EntryState(){//初始化
		this.assign=false;
		this.run=false;
		this.hold=false;
		this.complete=false;
		this.cancel=false;
	}
	
	public void checkRep(){//判断函数保证只有同时存在一个状态
		int i=0;
		if(run)
			i++;
		if(hold)
			i++;
		if(complete)
			i++;
		if(cancel)
			i++;
		assert (i>=0&&i<=1);
		//assert (i==0&&!assign)||(i==1&&assign);		
	}
	
	//各种状态的初始化
	public void assign() {
		assign=true;
		run=false;
		hold=false;
		complete=false;		
		cancel=false;
	}
	public void run(){
		run=true;
		hold=false;
		complete=false;		
		cancel=false;
	}
	public void hold(){
		run=false;
		hold=true;
		complete=false;		
		cancel=false;
	}
	public void complete(){
		run=false;
		hold=false;
		complete=true;		
		cancel=false;
	}
	public void cancel(){
		run=false;
		hold=false;
		complete=false;		
		cancel=true;
	}
	
	public String getstate() {//返回状态
		checkRep();
		if(!assign)
			return "unassigned";
		else if(run)
			return "run";
		else if(hold)
			return "hold";
		else if(complete)
			return "complete";
		else if(cancel)
			return "cancel";
		else 
			return "assigned";
	}
	
	@Override 
	public String toString(){//转换为字符串
		if(!assign)
			return "unassigned";
		else if(run)
			return "run";
		else if(hold)
			return "hold";
		else if(complete)
			return "complete";
		else if(cancel)
			return "cancel";	
		else 
			return "assigned";
	}
	
}
