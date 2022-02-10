package assignment2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

	JButton studyButton, homeButton, cButton;

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
		filterButton = filterCombo();
		
		//Custom Button
		cButton = new JButton("CustomTask");
		cButton.setPreferredSize(new Dimension(100,50));

		buttonList = new JPanel();
		buttonList.add(studyButton);
		buttonList.add(homeButton);
		buttonList.add(cButton);
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
		
		progressionLabel = new JLabel(completed + " out of " + notCompleted + " tasks");
		
		progressionPanel.add(progressionLabel);
		this.add(progressionPanel, BorderLayout.SOUTH);

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==studyButton) {
			
			//När knappen trycks så skapas en ny instans av task
			Task studyTask = new StudyTask();
			
			taskSorter.add(studyTask);
			
			//Validerar och renderar om panelen
			//System.out.println(taskSorter);
			sortTasks("default");
			countCompleted(studyTask);
			repaintPanel();
		}	
		if(e.getSource()==homeButton) {
			
			//Samma som för studyButton
			Task homeTask = new HomeTask();
			homeTask.setTaskListener(this);
			taskSorter.add(homeTask);
			sortTasks("getTaskType");
			countCompleted(homeTask);
			repaintPanel();
	
		}
		if(e.getSource()==homeTaskListener) {
			System.out.print("Hi");
		}
		
		filterSwitch((String) filterButton.getSelectedItem());
	}
	
	public void sortTasks(String sortType) {
		Collections.sort(taskSorter, new Comparator<Task>() {
			public int compare(Task v1, Task v2) {
				if(sortType.equals("getText")) {
					return v1.getText().compareTo(v2.getText());
				} else {
					return v1.getTaskType().compareTo(v2.getTaskType());
				}
			}
		});
	
		for(int i = 0; i < taskSorter.size(); i++) {
			taskPanel.add(taskSorter.get(i).getGuiComponent());
		}
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
	
	
	public JComboBox<String> filterCombo() {
		String[] filterOptions = {"getTaskType", "getText"};
		JComboBox<String> filterButton = new JComboBox<String>(filterOptions);
		filterButton.addActionListener(this);
		return filterButton;
	}
	
	public void filterSwitch(String filter) {
		if(filter.equals("getTaskType")) {
			sortTasks("getTaskType");
			repaintPanel();
		}else {
			sortTasks("getText");
			repaintPanel();
		}
	}
	
	
	public void repaintPanel() {
		taskPanel.validate();
		taskPanel.repaint();
	}
	
	
	public void countCompleted(Task task) {
		if(task.isComplete()) {
			completed++;
		}else {
			notCompleted++;
		}
		setCountLabel();
	}
	
	public void setCountLabel() {
		progressionLabel.setText(completed + " out of " + notCompleted + " tasks");
	}

	@Override
	public void taskChanged(Task t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void taskCompleted(Task t) {
		System.out.print("Test");
		
	}

	@Override
	public void taskUncompleted(Task t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void taskCreated(Task t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void taskRemoved(Task t) {
		// TODO Auto-generated method stub
		
	}
}
