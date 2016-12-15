package taskmodel;

import java.util.*;

public class UnlimitedTask extends Task {
	String[] mArgs;
	public UnlimitedTask(String name,Date startTime) {
		super(name,startTime);
		mArgs=new String[]{name,String.valueOf(startTime.getTime())};
	}
	public UnlimitedTask(String name){
		this(name,new Date());
	}
	@Override
	public String toReadableString(){
		return mArgs[0]+","+mArgs[1];
	}
	public static UnlimitedTask readFromString(String s){
		String[] ss=s.split(",");
		UnlimitedTask ut=null;
		ut=new UnlimitedTask(ss[0],new Date(Long.valueOf(ss[1])));
		return ut;
	}
	@Override
	public boolean isOn(Date date) {
		return date.getTime()>=mStartDate.getTime();
	}
	
	@Override
	public boolean isOnThisDay(Date date) {
		return date.getTime()/MSECOND_IN_A_DAY>=mStartDate.getTime()/MSECOND_IN_A_DAY;
	}
	
	@Override
	public int getPriority() {
		return -10000;
	}
	
	@Override
	public String toString(){
		return mName;
	}
}
