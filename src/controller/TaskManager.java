package controller;
import java.util.*;

import javax.swing.JLabel;

import taskmodel.*;
import java.io.*;
public class TaskManager {
	private static TaskManager sTaskManager=null;
	public static TaskManager getInstance(){
		if(sTaskManager == null){
			sTaskManager = new TaskManager();
		}
		return sTaskManager;
	}
	private ArrayList<Task> mTaskList;
	
	public void addTask(Task t){
		mTaskList.add(t);
	}
	
	public void removeTask(UUID id){
		Iterator<Task> i=mTaskList.iterator();
		while(i.hasNext()){
			Task t=(Task)i.next();
			if(t.getID().equals(id)){
				i.remove();
				break;
			}
		}
	}
	
	public ArrayList<Task> getTasksInADay(Date d){
		ArrayList<Task> tl=new ArrayList<>();
		for(Task t : mTaskList){
			if(t.isOnThisDay(d)){
				tl.add(t);
			}
		}
		tl.sort(new SortByPriority());
		return tl;
	}
	
	protected TaskManager(){
		mTaskList=new ArrayList<>();
		InputStreamReader isr=null;
		try{
		isr=new InputStreamReader(new FileInputStream("task.dat"));
		BufferedReader reader=new BufferedReader(isr);
		String head;
		while((head=reader.readLine())!=null){
			switch(head){
			case "ET":
				mTaskList.add(EverydayTask.readFromString(reader.readLine()));
				break;
			case "NT":
				mTaskList.add(NormalTask.readFromString(reader.readLine()));
			case "ULT":
				mTaskList.add(UnlimitedTask.readFromString(reader.readLine()));
			}
		}
		isr.close();
		}catch(Exception e){
			
		}
	}
	public void saveAll(){
		try{
			FileOutputStream out=new FileOutputStream("task.dat");
			StringBuilder sb=new StringBuilder();
			for(Task t:mTaskList){
				switch(t.getClass().getName()){
				case "taskmodel.EverydayTask":
					sb.append("ET\n");
					sb.append(t.toReadableString());
					break;
				case "taskmodel.NormalTask":
					sb.append("NT\n");
					sb.append(t.toReadableString());
					break;
				case "taskmodel.UnlimitedTask":
					sb.append("ULT\n");
					sb.append(t.toReadableString());
					break;
				}
			}
			out.write(sb.toString().getBytes());
			out.close();
		}catch(IOException e){
			
		}
	}
	private static class SortByPriority implements Comparator<Task> {
		@Override
		public int compare(Task o1, Task o2){
			return -o1.compareTo(o2);
		}
	}
}