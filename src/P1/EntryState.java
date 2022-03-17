package P1;

public class EntryState {
	private boolean assign;//����״̬
	private boolean	run;//����״̬
	private boolean hold;//����״̬
	private boolean complete;//���״̬
	private boolean cancel;//ȡ��״̬
	
	public EntryState(){//��ʼ��
		this.assign=false;
		this.run=false;
		this.hold=false;
		this.complete=false;
		this.cancel=false;
	}
	
	public void checkRep(){//�жϺ�����ֻ֤��ͬʱ����һ��״̬
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
	
	//����״̬�ĳ�ʼ��
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
	
	public String getstate() {//����״̬
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
	public String toString(){//ת��Ϊ�ַ���
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
