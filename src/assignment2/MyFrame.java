package assignment2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import se.his.it401g.todo.HomeTask;
import se.his.it401g.todo.StudyTask;
import se.his.it401g.todo.Task;
import se.his.it401g.todo.TaskListener;

public class MyFrame extends JFrame implements ActionListener, TaskListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JButton studyButton, homeButton, whiskeyButton;

	JComboBox<String> filterButton;
	
	TaskListener homeTaskListener, studyTaskListener;
	
	//Panelen är global så att när man skapar med knapparna så läggs de här.
	JPanel buttonList, taskPanel, progressionPanel;

	ArrayList<Task> taskSorter = new ArrayList<Task>();
	
	JLabel progressionLabel;
	
	int completed = 0;
	
	int notCompleted = 0;
	MyFrame() {
		
		//Study Button
		studyButton = studyButton();
		
		//HomeButton
		homeButton = homeButton();
		
		//whiskeyButton
		whiskeyButton = whiskeyButton();
		
		filterButton = filterCombo();
		


		buttonList = new JPanel();
		buttonList.add(studyButton);
		buttonList.add(homeButton);
		buttonList.add(whiskeyButton);
		buttonList.add(filterButton);
		
		taskPanel = new JPanel();
		this.setTitle("Todos");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(500, 500);
		this.setVisible(true);
		
		progressionPanel = new JPanel();
		
		//vart i framen saken ska ligga i, north är högst upp.
		this.add(buttonList, BorderLayout.NORTH);
		this.add(taskPanel);
		
		progressionLabel = new JLabel(completed + " out of " + notCompleted + " task completed");
		
		progressionPanel.add(progressionLabel);
		this.add(progressionPanel, BorderLayout.SOUTH);

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==studyButton) {
			
			//När knappen trycks så skapas en ny instans av task
			Task studyTask = new StudyTask();
			studyTask.setTaskListener(this);
			taskCreated(studyTask);
			//Validerar och renderar om panelen
			//System.out.println(taskSorter);
			sortTasks("default");
		}	
		if(e.getSource()==homeButton) {
			
			//Samma som för studyButton
			Task homeTask = new HomeTask();
			homeTask.setTaskListener(this);
			taskCreated(homeTask);

			sortTasks("getTaskType");
	
		}
		if(e.getSource()==whiskeyButton) {
			System.out.println("HI Whiskey");
			Task whiskey = new Whiskey();
			whiskey.setTaskListener(this);
			
			taskCreated(whiskey);
			
			sortTasks("Default");
		}
		
		filterSwitch((String) filterButton.getSelectedItem());
	}
	
	public void sortTasks(String sortType) {
		Collections.sort(taskSorter, new Comparator<Task>() {
			public int compare(Task v1, Task v2) {
				if(sortType.equals("getText")) {
					return v1.getText().compareTo(v2.getText());
				}else if (sortType.equals("getCompleted")) {
					return Boolean.compare(v2.isComplete(), v1.isComplete());
				}
				else {
					return v1.getTaskType().compareTo(v2.getTaskType());
				}
			}
		});
	
		for(int i = 0; i < taskSorter.size(); i++) {
			taskPanel.add(taskSorter.get(i).getGuiComponent());
		}
		repaintPanel();
	}


	
	public JButton studyButton() {
		studyButton = new JButton("studyTask");
		studyButton.setPreferredSize(new Dimension(100,50));
		studyButton.addActionListener(this);
		return studyButton;
	}
	
	public JButton homeButton() {
		homeButton = new JButton("HomeTask");
		homeButton.setPreferredSize(new Dimension(100,50));
		homeButton.addActionListener(this);
		return homeButton;
	}
	public JButton whiskeyButton() {
		//Custom Button
		whiskeyButton = new JButton("Whiskey");
		whiskeyButton.setPreferredSize(new Dimension(100,50));
		whiskeyButton.addActionListener(this);
		return whiskeyButton;
	}

	
	public JComboBox<String> filterCombo() {
		String[] filterOptions = {"getTaskType", "getText", "getCompleted"};
		JComboBox<String> filterButton = new JComboBox<String>(filterOptions);
		filterButton.addActionListener(this);
		return filterButton;
	}
	
	public void filterSwitch(String filter) {
		if(filter.equals("getTaskType")) {
			sortTasks("getTaskType");
			repaintPanel();
		} else if(filter.equals("getCompleted") ) {
			sortTasks("getCompleted");
			repaintPanel();
		}
		else {
			sortTasks("getText");
			repaintPanel();
		}
	}
	
	
	public void repaintPanel() {
		taskPanel.validate();
		taskPanel.repaint();
	}
	
	public void removeTaskPanel() {
		taskPanel.removeAll();
		taskPanel.validate();
		taskPanel.repaint();
		for(int i = 0; i < taskSorter.size(); i++) {
			taskPanel.add(taskSorter.get(i).getGuiComponent());
		}
		taskPanel.revalidate();
	}
	
	public void setCountLabel() {
		if(notCompleted < 2) {
			progressionLabel.setText(completed + " out of " + notCompleted + " task completed");
			
		}else {
			progressionLabel.setText(completed + " out of " + notCompleted + " tasks completed");
		}
	}

	@Override
	public void taskChanged(Task t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void taskCompleted(Task t) {
		completed++;
		System.out.print(completed);
		setCountLabel();
		
	}

	@Override
	public void taskUncompleted(Task t) {
		completed -= 1;
		setCountLabel();
	}

	@Override
	public void taskCreated(Task t) {
		taskSorter.add(t);
		notCompleted++;
		setCountLabel();
	}

	@Override
	public void taskRemoved(Task t) {
		taskSorter.remove(t);
		System.out.println(taskSorter.size());
		notCompleted -= 1;
		setCountLabel();
		removeTaskPanel();
	}
}
