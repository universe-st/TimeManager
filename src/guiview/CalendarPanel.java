package guiview;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import taskmodel.*;
import controller.TaskManager;

import java.util.*;
public class CalendarPanel extends JPanel {
	private int mYear;
	private int mMonth;
	private JButton[] mDateButtons;
	private TaskManager mTaskManager;
	private MainWindow mParent;
	long[] mDate;
	public void previewMonth(){
		mMonth--;
		if(mMonth<Calendar.JANUARY){
			mMonth=Calendar.DECEMBER;
			mYear--;
		}
		if(mYear>1975)
			initDates();
		else
			nextMonth();
	}
	public void nextMonth(){
		mMonth++;
		if(mMonth>Calendar.DECEMBER){
			mMonth=Calendar.JANUARY;
			mYear++;
		}
		initDates();
	}
	public CalendarPanel(MainWindow parent){
		super();
		mParent=parent;
		mTaskManager=TaskManager.getInstance();
		mDate=new long[42];
		Calendar c=Calendar.getInstance();
		mYear=c.get(Calendar.YEAR);
		mMonth=c.get(Calendar.MONTH);
		mDateButtons=new JButton[42];
		for(int i=0;i<42;i++){
			mDateButtons[i]=new JButton();
		}
		this.setLayout(new GridLayout(0,7,0,0));
		String[] strs={"日","一","二","三","四","五","六"};
		for(int i=0;i<7;i++){
			Label l=new Label(" "+strs[i]);
			l.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,30));
			this.add(l);
		}
		for(int i=0;i<42;i++){
			this.add(mDateButtons[i]);
		}
		initDates();
	}
	public void initDates(){
		for(JButton bt:mDateButtons){
			ActionListener[] als=bt.getActionListeners();
			if(als.length>0){
				bt.removeActionListener(als[0]);
			}
		}
		Calendar a=Calendar.getInstance();
		a.set(Calendar.DAY_OF_MONTH, 1);
		a.set(Calendar.HOUR_OF_DAY,12);
		a.set(Calendar.MINUTE, 0);
		a.set(Calendar.YEAR,mYear);
		a.set(Calendar.MONTH,mMonth);
		int d=a.get(Calendar.DAY_OF_WEEK)-1;
		for(int i=0;i<d;i++){
			JButton db=mDateButtons[i];
			db.setEnabled(false);
			db.setBackground(Color.white);
			db.setForeground(Color.BLACK);
			mDate[i]=-1;
			db.setText("");
		}
		while(a.get(Calendar.MONTH)==mMonth) {
			mDateButtons[d].setEnabled(true);
			mDateButtons[d].setForeground(Color.BLACK);
			setDateButton(d,a);
			a.add(Calendar.DAY_OF_MONTH, 1);
			d++;
		}
		for(int i=d;i<42;i++){
			JButton db=mDateButtons[i];
			db.setForeground(Color.BLACK);
			db.setEnabled(false);
			db.setBackground(Color.white);
			mDate[i]=-1;
			db.setText("");
		}
	}
	private void setDateButton(int d,Calendar a){
		JButton jb=mDateButtons[d];
		jb.setAlignmentX(Component.LEFT_ALIGNMENT);
		jb.setHorizontalAlignment(SwingConstants.LEFT);
		jb.setVerticalAlignment(SwingConstants.TOP);
		ArrayList<Task> taskList=
				mTaskManager.getTasksInADay(a.getTime());
		StringBuilder sb=new StringBuilder();
		sb.append("<HTML>"+a.get(Calendar.DAY_OF_MONTH)+"<BR>");
		boolean hasEDTask=false;
		boolean hasNormalTask=false;
		for(Task t : taskList){
			sb.append(t.getName()+"<BR>");
			if(t instanceof EverydayTask){
				hasEDTask=true;
			}else if(t instanceof NormalTask){
				hasNormalTask=true;
			}
		}
		jb.setBackground(Color.yellow);
		if(!taskList.isEmpty()){
			jb.setBackground(Color.orange);
		}
		if(hasEDTask){
			jb.setForeground(Color.black);
		}
		if(hasNormalTask){
			jb.setForeground(Color.white);
			jb.setBackground(Color.blue);
		}
		jb.setText(sb.toString());
		mDate[d]=a.getTimeInMillis();
		jb.addActionListener(new DateButtonListener(mDate[d]));
	}
	public String getChMonth(){
		final String[] s={"一","二","三","四","五","六","七","八","九","十","十一","十二"};
		return s[mMonth];
	}
	public int getYear(){
		return mYear;
	}
	public int getMonth(){
		return mMonth;
	}
	private class DateButtonListener implements ActionListener {
		private long mDate;
		public DateButtonListener(long date){
			mDate=date;
		}
		@Override
		public void actionPerformed(ActionEvent event){
			EventQueue.invokeLater(new Runnable(){
				@Override
				public void run(){
					Date d=new Date(mDate);
					System.out.println("Date "+d.toString()+" is clicked.");
					mParent.setOutDate(d);
				}
			});
		}
	}
}
