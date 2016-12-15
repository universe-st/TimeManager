package taskmodel;
import java.util.*;
public abstract class Task implements Comparable<Task>{
	public static final int MSECOND_IN_A_DAY=1000*3600*24;
	protected String mName;
	protected Date mStartDate;
	private UUID mId;
	abstract public boolean isOn(Date date);
	abstract public boolean isOnThisDay(Date date);
	abstract public int getPriority();
	abstract public String toReadableString();
	protected Task(String name,Date startTime){
		mStartDate=new Date(startTime.getTime());
		mName=name;
		mId=UUID.randomUUID();
	}
	public Date getStartDate(){
		return new Date(mStartDate.getTime());
	}
	public String getName(){
		return mName;
	}
	public UUID getID(){
		return mId;
	}
	public void setName(String name){
		mName=name;
	}
	@Override
	public int compareTo(Task t){
		return this.getPriority()-t.getPriority();
	}	
	
}
