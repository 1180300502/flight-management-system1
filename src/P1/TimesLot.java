package P1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimesLot {
	private String startString="δ����";//����ʱ��
	private boolean startstate=false;//����״̬
	private int[] starttime=new int[5];//starttime[i]�ֱ��ʾ������ʱ��
	private long starttimelong;//��ʼ�ͽ���ʱ��ת��Ϊ���ӣ����ڱȽ�����TimesLot�Ĵ�С
	
	private String overString="δ����";//����ʱ��
	private boolean overstate=false;
	private int[] overtime=new int[5];
	private long overtimelong;
	
	
	static public boolean istime(String s) {//�ж��Ƿ�Ϊ�淶���ַ���
		Pattern a=Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2})");
	    Matcher b=a.matcher(s);
	    if(!b.matches())
	    {
	    	return false;
	    }
	    else
	    return true;
	}

	static public int[] timestringtoint(String s) {//���ַ�����Ϊ����		
		Pattern a=Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2})");		
	    Matcher b=a.matcher(s);//��ɹ淶��ʽ
	    int[] time=null;
	    if(b.matches()){
	    	time =new int[5];
	    	int i=0;
	    	for( i=0;i<5;i++)
	    	time[i]=Integer.parseInt(b.group(i+1));
	    } 
		return time;
	}

	
	static boolean comparetime(int[] a,int[] b) {//�Ƚ�����ʱ���Ƿ���a����b��a���ڵ���b����true
		if(a==null||b==null)
		return false;
		for(int i=0; i<5;i++) {//�Ƚ�ʱ��
			if(a[i]>b[i])
				return true;
			if(a[i]<b[i])
				return false;
		}
		return true;//��a��b���ʱ��������
	}
	
	public boolean setstart(String s) {
		int[] a=timestringtoint(s);//�ַ��������������
		if(a==null) {
			return false;
		}
		else{
	    	for(int i=0;i<5;i++)
	    	this.starttime[i]=a[i];
	    	this.starttimelong=((((long)a[0]*12+a[1])*31+a[2])*24+a[3])*60+a[4];//��ʱ���ɷ��ӣ���������Ƚ�
	    	return true;
	    }    
	}
	public boolean setover(String s) {//ͬ��
		int[] a=timestringtoint(s);
		if(a==null) {
			return false;
		}
		else{
	    	for(int i=0;i<5;i++)
	    	this.overtime[i]=a[i];
	    	this.overtimelong=((((long)a[0]*12+a[1])*31+a[2])*24+a[3])*60+a[4];
	    	return true;
	    }    
	}
	
	//������
	public TimesLot() {//û����ֹʱ��
		
	}
	public TimesLot(String s) {//ֻ�п�ʼ
		if(setstart(s));{
			this.startString=s;
			this.startstate=true;
		}
	}
	
	public TimesLot(String s,String o) {//����ֹʱ��
		if(setstart(s)) {
			this.startString=s;
			this.startstate=true;
		}
		if(setover(o)) {
			this.overString=o;
			this.overstate=true;
		}

	}
	
	static public boolean comparetimestring(String a,String b) {//�Ƚ�����ʱ���Ƿ�Ϊ�Ⱥ�
																//a����b����true
		if(comparetime(timestringtoint(a),timestringtoint(b)))
			return true;
		else
			return false;
	}
	
	
	public boolean contiantime(String a) {//�ж�ʱ����Ƿ���ʱ�����
		if(comparetimestring(a,startString)&&comparetimestring(overString,a)) {
			System.out.println("������ʱ�����ʱ�����");
			return true;
			
		}
		else return false;
	}
	
	static public boolean contiantime(String a,String b,String c) {//�ж�ʱ���c�Ƿ���ʱ���ab��
		int[] time=timestringtoint(c);
		if(comparetime(time,timestringtoint(a))&&comparetime(timestringtoint(b),time))
			return true;
		
		else return false;
	}
	
	public String getstarttime() {//get����
		return ""+startString;
	}
	
	
	public boolean getstartstate() {
		return startstate;
	}
	
	public int getstartnum(int i) {//����ʱ���ָ��λ�õ�����
		if(i<5)
		return starttime[i];
		
		else
			return -1;
	}
	
	public int[] getstartnum(){//����ʱ��
		int[] a=new int[5];
		for(int i=0;i<5;i++)
			a[i]=starttime[i];
		return a;
	}
	
	public long getstarttimelong() {
		return starttimelong;
	}

	public String getovertime() {
		return ""+overString;
	}
	
	public boolean getoverstate() {
		return overstate;
	}
		
	public int getovernum(int i) {//������ֵ���е�����
		if(i<5)
			return overtime[i];
			
			else
				return -1;
	}
	
	public int[] getovernum(){
		int[] a=new int[5];
		for(int i=0;i<5;i++)
			a[i]=overtime[i];
		return a;
	}
	
	public long getovertimelong() {
		return overtimelong;
	}
	
	private void checkRep() {
	    assert (startString!="δ����"&&  overString!="δ����");
	    for (int i = 0; i < 5; i++) {
	    	assert(starttime[i]!=0);
	        assert (overtime[i] != 0);
	    }
	}

	
	@Override
	public String toString() {
		return startString+"-"+overString;
	}
}

