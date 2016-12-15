package taskmodel;

import java.util.*;
public class NormalTask extends Task {
	private int mBasePriority;
	private Date mEndDate;
	private String[] mArgs;
	public NormalTask(String name,Date dateEnd){
		this(name,new Date(),dateEnd);
	}
	@Override
	public String toReadableString(){
		return mArgs[0]+","+mArgs[1]+","+mArgs[2];
	}
	public static NormalTask readFromString(String s){
		String[] ss=s.split(",");
		NormalTask nt=null;
		nt= new NormalTask(ss[0],new Date(Long.valueOf(ss[1])),new Date(Long.valueOf(ss[2])));
		return nt;
	}
	public NormalTask(String name,Date dateStart,Date dateEnd){
		super(name,dateStart);
		mArgs=new String[]{name,String.valueOf(dateStart.getTime()),String.valueOf(dateEnd.getTime())};
		mEndDate=new Date(dateEnd.getTime());
		mBasePriority=10;
	}
	
	@Override
	public int getPriority() {
		return mBasePriority-(int)(mEndDate.getTime()/1000*3600-(new Date()).getTime()/1000*3600);
	}
	
	@Override
	public boolean isOn(Date date){
		long t=date.getTime();
		long t1=mStartDate.getTime();
		long t2=mEndDate.getTime();
		return t>=t1 && t<=t2;
	}
	
	@Override
	public boolean isOnThisDay(Date date) {
		long a=date.getTime()/MSECOND_IN_A_DAY;
		long b=mStartDate.getTime()/MSECOND_IN_A_DAY;
		long c=mEndDate.getTime()/MSECOND_IN_A_DAY;
		return a>=b && a<=c;
	}
	
	public Date getEndDate() {
		return new Date(mEndDate.getTime());
	}
	@Override
	public String toString(){
		return mName;
	}
}
