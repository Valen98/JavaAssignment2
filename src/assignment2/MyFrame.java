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

	private JButton studyButton, homeButton, tvShowButton;

	private JComboBox<String> filterButton;
	
	private TaskListener homeTaskListener, studyTaskListener;
	
	//Panelen är global så att när man skapar med knapparna så läggs de här.
	private JPanel buttonList, taskPanel, progressionPanel;

	private ArrayList<Task> taskSorter = new ArrayList<Task>();
	
	private JLabel progressionLabel;
	
	//Counts if the task is marked as complete
	private int completed = 0;
	
	//Counts how many tasks there are in total
	private int notCompleted = 0;
	
	//This is the main frame of the program
	MyFrame() {
		
		//Study Button
		studyButton = studyButton();
		
		//HomeButton
		homeButton = homeButton();
		
		//tvShowButton
		tvShowButton = tvShowButton();
		
		//FilterButton
		filterButton = filterCombo();
		
		//Adds all buttons to the JPanel
		buttonList = new JPanel();
		buttonList.add(studyButton);
		buttonList.add(homeButton);
		buttonList.add(tvShowButton);
		buttonList.add(filterButton);
		
		//Creates the JPanle, names it and also adds a size.
		tas
		kPanel = new JPanel();
		this.setTitle("Todos");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(500, 500);
		this.setVisible(true);
		
		//Creates the part in the JPanel that shows how many tasks are made and done
		progressionPanel = new JPanel();
		
		//Where in the frame that this is placed, north is at the top of the frame.
		this.add(buttonList, BorderLayout.NORTH);
		this.add(taskPanel);
		
		//What will be said in the progressionPanel
		progressionLabel = new JLabel(completed + " out of " + notCompleted + " task completed");
		
		//adds a location to the progressionPanel
		progressionPanel.add(progressionLabel);
		this.add(progressionPanel, BorderLayout.SOUTH);
	}

	@Override
	//Cheacks if the buttons are used as well as keeps the information what will happen if they are pressed or other wise used.
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==studyButton) {
			try {
				//When the button is pressed down a new instace of a task created
				Task studyTask = new StudyTask();
				studyTask.setTaskListener(this);
				taskCreated(studyTask);
				//Validates and renders the frame again
				//System.out.println(taskSorter);
				sortTasks("default");
				
			}catch(Exception error) {
				//Prints out a error message if the task couldn't be created.
				System.out.println("Could not create study task");
			}
		}	
		if(e.getSource()==homeButton) {
			try {
				//Same as for the studyButton
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
				//Same as for the studyButton
				Task tvShow = new TvShows();
				tvShow.setTaskListener(this);
				
				taskCreated(tvShow);
				
				sortTasks("Default");
				
			}catch (Exception error) {
				System.out.println("Could not create Tv shows");
			}
		}
		
		//Uses the filterSwitch method to choose what you sort after also changes depending on what is chosen
		filterSwitch((String) filterButton.getSelectedItem());
	}
	
	//sortTasks method sorts the ArrayList of taskSorter by the compare 
	public void sortTasks(String sortType) {
		Collections.sort(taskSorter, new Comparator<Task>() {
			public int compare(Task v1, Task v2) {
				if(sortType.equals("Text")) {
					//toLowerCase() because Java sort capitalized letters over non-capitalized 
					return v1.getText().toLowerCase().compareTo(v2.getText().toLowerCase());
				}else if (sortType.equals("Completed")) {
					return Boolean.compare(v2.isComplete(), v1.isComplete());
				}
				else {
					return v1.getTaskType().compareTo(v2.getTaskType());
				}
			}
		});
	
		//
		for(int i = 0; i < taskSorter.size(); i++) {
			taskPanel.add(taskSorter.get(i).getGuiComponent());
		}
		repaintPanel();
	}


	//Creates the studyButton on the frame that you can press to create a task
	public JButton studyButton() {
		//Name on the Button
		studyButton = new JButton("studyTask");
		//Size of the button
		studyButton.setPreferredSize(new Dimension(100,50));
		//Makes sure that it listens if it is pressed
		studyButton.addActionListener(this);
		return studyButton;
	}
	//Same as for the studyButton
	public JButton homeButton() {
		homeButton = new JButton("HomeTask");
		homeButton.setPreferredSize(new Dimension(100,50));
		homeButton.addActionListener(this);
		return homeButton;
	}
	//Same as for studyButton
	public JButton tvShowButton() {
		//Custom Button
		tvShowButton = new JButton("Tv Shows");
		tvShowButton.setPreferredSize(new Dimension(100,50));
		tvShowButton.addActionListener(this);
		return tvShowButton;
	}

	//Creates the options for the filter so that it can see what the difference.
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
	//Repaints frame if the task changed
	public void taskChanged(Task t) {
		repaintPanel();
	}

	@Override
	//If a task is marked as complete it will be added to the completed variable
	public void taskCompleted(Task t) {
		completed++;
		System.out.print(completed);
		setCountLabel();
		
	}

	@Override
	//changes the task completed variable if one is removed
	public void taskUncompleted(Task t) {
		completed -= 1;
		setCountLabel();
	}

	@Override
	//Adds a task to the sorter and also to the progressbar when one is created
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
		if(t.isComplete()) {
			completed -= 1;
		}
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
