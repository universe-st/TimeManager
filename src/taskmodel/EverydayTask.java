package taskmodel;

import java.util.*;
public class EverydayTask extends Task {
	
	private int mStartHour;
	private int mEndHour;
	private int mStartMin;
	private int mEndMin;
	private String[] mArgs;
	@Override
	public String toReadableString(){
		return mArgs[0]+","+mArgs[1]+","+mArgs[2]+","+mArgs[3]+","+mArgs[4];
	}
	public static EverydayTask readFromString(String s){
		String[] ss=s.split(",");
		EverydayTask edt=null;
		try{
		edt= new EverydayTask(ss[0],new Date(Long.valueOf(ss[1])),Integer.valueOf(ss[2]),Integer.valueOf(ss[3]),Integer.valueOf(ss[4]));
		}catch(Exception e){
			
		}
		return edt;
	}
	public EverydayTask(String name,Date date,int startHour,int startMin,int durationMin)throws Exception{
		super(name,date);
		mArgs=new String[]{name,String.valueOf(date.getTime()),String.valueOf(startHour),String.valueOf(durationMin)};
		mStartHour=startHour;
		mStartMin=startMin;
		mEndMin=durationMin%60+mStartMin;
		mEndHour=durationMin/60+mStartHour;
		if(mEndMin>=60){
			mEndMin-=60;
			mEndHour+=1;
		}
		if(mEndHour>=24){
			throw new Exception("Everyday task cannot go through 00:00 am.");
		}
	}
	public EverydayTask(String name,int startHour,int startMin,int durationMin) throws Exception {
		this(name,new Date(),startHour,startMin,durationMin);
	}
	@Override
	public boolean isOn(Date date) {
		if(!isOnThisDay(date)){
			return false;
		}
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		int h=c.get(Calendar.HOUR_OF_DAY);
		int m=c.get(Calendar.MINUTE);
		return (h>=mStartHour && h<mEndHour) || (h==mEndHour && m<=mEndMin);
	}

	@Override
	public boolean isOnThisDay(Date date) {
		return date.getTime()/MSECOND_IN_A_DAY>=mStartDate.getTime()/MSECOND_IN_A_DAY;
	}

	@Override
	public int getPriority() {
		return 10;
	}
	@Override
	public String toString(){
		String s1=(mStartHour<10)?("0"+String.valueOf(mStartHour)):String.valueOf(mStartHour);
		String s2=(mStartMin<10)?("0"+String.valueOf(mStartMin)):String.valueOf(mStartMin);
		String s3=(mEndHour<10)?("0"+String.valueOf(mEndHour)):String.valueOf(mEndHour);
		String s4=(mEndMin<10)?("0"+String.valueOf(mEndMin)):String.valueOf(mEndMin);
		String s="<HTML>"+mName+"<BR>"+s1+":"+s2+"~"+s3+":"+s4+"</HTML>";
		return s;
	}
	
}
