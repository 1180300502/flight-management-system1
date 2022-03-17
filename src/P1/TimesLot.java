package P1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimesLot {
	private String startString="未设置";//出发时间
	private boolean startstate=false;//出发状态
	private int[] starttime=new int[5];//starttime[i]分别表示年月日时分
	private long starttimelong;//开始和结束时间转化为分钟，用于比较两个TimesLot的大小
	
	private String overString="未设置";//到达时间
	private boolean overstate=false;
	private int[] overtime=new int[5];
	private long overtimelong;
	
	
	static public boolean istime(String s) {//判断是否为规范的字符串
		Pattern a=Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2})");
	    Matcher b=a.matcher(s);
	    if(!b.matches())
	    {
	    	return false;
	    }
	    else
	    return true;
	}

	static public int[] timestringtoint(String s) {//将字符串变为数组		
		Pattern a=Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2})");		
	    Matcher b=a.matcher(s);//变成规范形式
	    int[] time=null;
	    if(b.matches()){
	    	time =new int[5];
	    	int i=0;
	    	for( i=0;i<5;i++)
	    	time[i]=Integer.parseInt(b.group(i+1));
	    } 
		return time;
	}

	
	static boolean comparetime(int[] a,int[] b) {//比较两个时间是否是a大于b，a大于等于b返回true
		if(a==null||b==null)
		return false;
		for(int i=0; i<5;i++) {//比较时间
			if(a[i]>b[i])
				return true;
			if(a[i]<b[i])
				return false;
		}
		return true;//当a和b相等时，返回正
	}
	
	public boolean setstart(String s) {
		int[] a=timestringtoint(s);//字符串变成整形数组
		if(a==null) {
			return false;
		}
		else{
	    	for(int i=0;i<5;i++)
	    	this.starttime[i]=a[i];
	    	this.starttimelong=((((long)a[0]*12+a[1])*31+a[2])*24+a[3])*60+a[4];//将时间变成分钟，方便后续比较
	    	return true;
	    }    
	}
	public boolean setover(String s) {//同理
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
	
	//构造器
	public TimesLot() {//没有起止时间
		
	}
	public TimesLot(String s) {//只有开始
		if(setstart(s));{
			this.startString=s;
			this.startstate=true;
		}
	}
	
	public TimesLot(String s,String o) {//有起止时间
		if(setstart(s)) {
			this.startString=s;
			this.startstate=true;
		}
		if(setover(o)) {
			this.overString=o;
			this.overstate=true;
		}

	}
	
	static public boolean comparetimestring(String a,String b) {//比较两个时间是否为先后
																//a大于b返回true
		if(comparetime(timestringtoint(a),timestringtoint(b)))
			return true;
		else
			return false;
	}
	
	
	public boolean contiantime(String a) {//判断时间点是否在时间段中
		if(comparetimestring(a,startString)&&comparetimestring(overString,a)) {
			System.out.println("所输入时间点在时间段中");
			return true;
			
		}
		else return false;
	}
	
	static public boolean contiantime(String a,String b,String c) {//判断时间点c是否在时间段ab中
		int[] time=timestringtoint(c);
		if(comparetime(time,timestringtoint(a))&&comparetime(timestringtoint(b),time))
			return true;
		
		else return false;
	}
	
	public String getstarttime() {//get函数
		return ""+startString;
	}
	
	
	public boolean getstartstate() {
		return startstate;
	}
	
	public int getstartnum(int i) {//返回时间的指定位置的数字
		if(i<5)
		return starttime[i];
		
		else
			return -1;
	}
	
	public int[] getstartnum(){//返回时间
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
		
	public int getovernum(int i) {//返回数值表中的数字
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
	    assert (startString!="未设置"&&  overString!="未设置");
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

