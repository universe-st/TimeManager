package guiview;

import java.awt.EventQueue;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import taskmodel.*;
import controller.TaskManager;

import java.awt.*;
public class MainWindow extends JFrame {
	private JPanel mPanel;
	private JButton mPMonth;
	private JButton mNMonth;
	private JButton mAddTask;
	private JButton mConfig;
	private JLabel mMonthLabel;
	private CalendarPanel mCalendarPane;
	private DayTaskPanel mDayTaskPanel;
	public MainWindow(){
		this.setTitle("时间管理器V1.0");
		init();
		this.setSize(800, 600);
	}
	public void setOutDate(Date d){
		mDayTaskPanel.setDate(d);
	}
	private void init(){
		mPanel=new JPanel();
		mPanel.setLayout(new BorderLayout(0,0));
		mCalendarPane=new CalendarPanel(this);
		//mCalendarPane.setPreferredSize(new Dimension(700,400));
		mPanel.add("Center",mCalendarPane);
		mDayTaskPanel=new DayTaskPanel(new Date(),this);
		mPanel.add("East",mDayTaskPanel);
		JPanel buttonPanel=new JPanel();
		mMonthLabel=new JLabel(mCalendarPane.getYear()+"年"+mCalendarPane.getChMonth()+"月");
		mMonthLabel.setFont(new Font(Font.DIALOG,Font.BOLD,30));
		mMonthLabel.setHorizontalAlignment(SwingConstants.CENTER);
		buttonPanel.setLayout(new GridLayout(2,2));
		mPMonth=new JButton("上一月");
		mPMonth.setFont(new Font(Font.DIALOG,Font.BOLD,20));
		mPMonth.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				mCalendarPane.previewMonth();
				mMonthLabel.setText(mCalendarPane.getYear()+"年"+mCalendarPane.getChMonth()+"月");
			}
		});
		mNMonth=new JButton("下一月");
		mNMonth.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				mCalendarPane.nextMonth();
				mMonthLabel.setText(mCalendarPane.getYear()+"年"+mCalendarPane.getChMonth()+"月");
				
			}
		});
		mNMonth.setFont(new Font(Font.DIALOG,Font.BOLD,20));
		mAddTask=new JButton("添加任务");
		mAddTask.setFont(new Font(Font.DIALOG,Font.BOLD,20));
		mAddTask.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				EventQueue.invokeLater(new Runnable(){
					@Override
					public void run(){
						AddTaskWindow frame=new AddTaskWindow(MainWindow.this);
						frame.setVisible(true);
					}
				});
			}
		});
		mConfig=new JButton("退出");
		mConfig.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				EventQueue.invokeLater(new Runnable(){
					@Override
					public void run(){
				    	TaskManager.getInstance().saveAll();
						System.exit(0);
					}
				});
			}
		});
		mConfig.setFont(new Font(Font.DIALOG,Font.BOLD,20));
		mPanel.add("North",mMonthLabel);
		buttonPanel.setSize(800, 50);
		buttonPanel.add(mPMonth);
		buttonPanel.add(mNMonth);
		buttonPanel.add(mAddTask);
		buttonPanel.add(mConfig);
		mPanel.add("South",buttonPanel);
		this.add(mPanel);
		new CheckThread().start();
	}
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run(){
				JFrame frame=new MainWindow();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.addWindowListener(new WindowAdapter() {  
				      
				      
				    public void windowClosing(WindowEvent e) {  
				    super.windowClosing(e);  
				    	TaskManager.getInstance().saveAll();
				     }  
				      
				    });   
				frame.setVisible(true);
			}
		});
	}
	public void refresh(){
		mCalendarPane.initDates();
		mDayTaskPanel.refresh();
	}
	private class CheckThread extends Thread{
		@Override
		public void run(){
			while(true){
				try{
					Thread.sleep(8000);
					EventQueue.invokeLater(new Runnable(){
						public void run(){
							MainWindow.this.refresh();
						}
					});
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
}
