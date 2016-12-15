package guiview;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import taskmodel.*;
import controller.*;
public class DayTaskPanel extends JPanel{
	private Date mDate;
	private ArrayList<Task> mTasks;
	private JLabel mLabel;
	private ArrayList<JButton> mTaskButtons;
	private JFrame mParent;
	public DayTaskPanel(Date d,JFrame parent){
		mParent=parent;
		mDate=d;
		init();
	}
	public void init(){
		mDate=new Date();
		mTasks=TaskManager.getInstance().getTasksInADay(mDate);
		this.setLayout(new GridLayout(0,1));
		Calendar c=Calendar.getInstance();
		c.setTime(mDate);
		String s="<html>"+c.get(Calendar.YEAR)+"年"+(c.get(Calendar.MONTH)+1)+"月"
				+c.get(Calendar.DAY_OF_MONTH)+"日<br>任务：</html>";
		mLabel=new JLabel(s);
		this.add(mLabel);
		mTaskButtons=new ArrayList<>();
		for(Task t : mTasks){
			JButton j=new JButton(t.toString());
			j.setPreferredSize(new Dimension(50,50));
			mTaskButtons.add(j);
			if(t.isOn(new Date())){
				j.setBackground(Color.red);
				j.setForeground(Color.white);
			}
			j.addActionListener(new TaskButtonListener(t));
		}
		for(JButton j:mTaskButtons){
			this.add(j);
		}
	}
	public void setDate(Date d){
		mDate=d;
		refresh();
	}
	public void refresh(){
		for(JButton j:mTaskButtons){
			this.remove(j);
		}
		mTaskButtons=new ArrayList<>();
		mTasks=TaskManager.getInstance().getTasksInADay(mDate);
		Calendar c=Calendar.getInstance();
		c.setTime(mDate);
		String s="<html>"+c.get(Calendar.YEAR)+"年"+(c.get(Calendar.MONTH)+1)+"月"
				+c.get(Calendar.DAY_OF_MONTH)+"日<br>任务：</html>";
		mLabel.setText(s);
		for(Task t : mTasks){
			JButton j=new JButton(t.toString());
			j.setPreferredSize(new Dimension(100,50));
			mTaskButtons.add(j);
			if(t.isOn(new Date())){
				j.setBackground(Color.red);
				j.setForeground(Color.white);
			}
			j.addActionListener(new TaskButtonListener(t));
		}
		for(JButton j:mTaskButtons){
			this.add(j);
		}
	}
	private class TaskButtonListener implements ActionListener{
		Task mTask;
		public TaskButtonListener(Task t){
			mTask=t;
		}
		@Override
		public void actionPerformed(ActionEvent event){
			EventQueue.invokeLater(new Runnable(){
				public void run(){
					JFrame j=new JFrame();
					j.setTitle(mTask.getName());
					j.setSize(300, 300);
					j.setLayout(new GridLayout(0,1));
					j.add(new JLabel(mTask.getName()));
					j.add(new JLabel("开始时间："+mTask.getStartDate().toString()));
					System.out.println(mTask.getClass().getName());
					switch(mTask.getClass().getName()){
					case "taskmodel.EverydayTask":
						j.add(new JLabel("每日任务"));
						break;
					case "taskmodel.NormalTask":
						j.add(new JLabel("结束时间："+((NormalTask)mTask).getEndDate().toString()));
						break;
					case "taskmodel.UnlimitedTask":
						j.add(new JLabel("无期任务"));
						break;
					}
					JButton bt=new JButton("删除");
					bt.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent event){
							TaskManager.getInstance().removeTask(mTask.getID());
							j.setVisible(false);
							((MainWindow)DayTaskPanel.this.mParent).refresh();
						}
					});
					j.add(bt);
					j.setVisible(true);
				}
			});
		}
	}
}
