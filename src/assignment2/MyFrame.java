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

	JButton studyButton, homeButton, tvShowButton;

	JComboBox<String> filterButton;
	
	TaskListener homeTaskListener, studyTaskListener;
	
	//Panelen �r global s� att n�r man skapar med knapparna s� l�ggs de h�r.
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
		
		//tvShowButton
		tvShowButton = tvShowButton();
		
		filterButton = filterCombo();
		


		buttonList = new JPanel();
		buttonList.add(studyButton);
		buttonList.add(homeButton);
		buttonList.add(tvShowButton);
		buttonList.add(filterButton);
		
		taskPanel = new JPanel();
		this.setTitle("Todos");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(500, 500);
		this.setVisible(true);
		
		progressionPanel = new JPanel();
		
		//vart i framen saken ska ligga i, north �r h�gst upp.
		this.add(buttonList, BorderLayout.NORTH);
		this.add(taskPanel);
		
		progressionLabel = new JLabel(completed + " out of " + notCompleted + " task completed");
		
		progressionPanel.add(progressionLabel);
		this.add(progressionPanel, BorderLayout.SOUTH);

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==studyButton) {
			try {
				//N�r knappen trycks s� skapas en ny instans av task
				Task studyTask = new StudyTask();
				studyTask.setTaskListener(this);
				taskCreated(studyTask);
				//Validerar och renderar om panelen
				//System.out.println(taskSorter);
				sortTasks("default");
				
			}catch(Exception error) {
				System.out.println("Could not create study task");
			}
		}	
		if(e.getSource()==homeButton) {
			try {
				//Samma som f�r studyButton
				Task homeTask = new HomeTask();
				homeTask.setTaskListener(this);
				taskCreated(homeTask);
				
				sortTasks("Task type");
				
			}catch(Exception error) {
				System.out.println("Could not create home task");
			}
		}
		if(e.getSource()==tvShowButton) {
			try {
				Task tvShow = new TvShows();
				tvShow.setTaskListener(this);
				
				taskCreated(tvShow);
				
				sortTasks("Default");
				
			}catch (Exception error) {
				System.out.println("Could not create Tv shows");
			}
		}
		
		filterSwitch((String) filterButton.getSelectedItem());
	}
	
	//sortTasks method sorts the ArrayList of taskSorter by the compare 
	public void sortTasks(String sortType) {
		Collections.sort(taskSorter, new Comparator<Task>() {
			public int compare(Task v1, Task v2) {
				if(sortType.equals("Text")) {
					return v1.getText().compareTo(v2.getText());
				}else if (sortType.equals("Completed")) {
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
	public JButton tvShowButton() {
		//Custom Button
		tvShowButton = new JButton("Tv Shows");
		tvShowButton.setPreferredSize(new Dimension(100,50));
		tvShowButton.addActionListener(this);
		return tvShowButton;
	}

	
	public JComboBox<String> filterCombo() {
		String[] filterOptions = {"Filter by:","Task type", "Text", "Completed"};
		JComboBox<String> filterButton = new JComboBox<String>(filterOptions);
		filterButton.addActionListener(this);
		return filterButton;
	}
	//This method is used to check what the sortTask should sort on.
	public void filterSwitch(String filter) {
		if(filter.equals("Task type")) {
			sortTasks("Task type");
		} else if(filter.equals("Completed") ) {
			sortTasks("Completed");
		}
		else {
			sortTasks("Text");
		}
	}
	
	//Method to repaint the taskPanel.
	public void repaintPanel() {
		taskPanel.validate();
		taskPanel.repaint();
	}
	

	//This is the progressionLabel and it updates when a task is created and/or deleted.
	public void setCountLabel() {
		if(notCompleted < 2) {
			progressionLabel.setText(completed + " out of " + notCompleted + " task completed");
			
		}else {
			progressionLabel.setText(completed + " out of " + notCompleted + " tasks completed");
		}
	}

	@Override
	public void taskChanged(Task t) {
		repaintPanel();
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

	//taskRemoved takes the current task and remove it from the array list, remove notCompleted and updates setCountLabel and calls for removeTaskPanel
	@Override
	public void taskRemoved(Task t) {
		taskSorter.remove(t);
		System.out.println(taskSorter.size());
		notCompleted -= 1;
		setCountLabel();
		removeTaskPanel();
	}
	
	
	//This method is used to remove the current panel and repaint it. Otherwise the panel wont update when its removed.
	public void removeTaskPanel() {
		try {
			taskPanel.removeAll();
			taskPanel.validate();
			taskPanel.repaint();
			for(int i = 0; i < taskSorter.size(); i++) {
				taskPanel.add(taskSorter.get(i).getGuiComponent());
			}
			taskPanel.revalidate();
			
		}catch (Exception error) {
			System.out.println("Could not remove task");
		}
	}
}
