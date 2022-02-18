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

public class MyFrame extends JFrame implements ActionListener, TaskListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	// Declaring the variables and initiate the Class

	private JButton studyButton, homeButton, tvShowButton;

	private JComboBox<String> filterButton;

	TaskListener homeTaskListener, studyTaskListener;

	private JPanel buttonList, taskPanel, progressionPanel;

	private ArrayList<Task> taskSorter = new ArrayList<Task>();

	private JLabel progressionLabel;

	// Counts if the task is marked as complete
	int completed = 0;

	// Counts how many tasks there are in total
	int notCompleted = 0;

	// This is the main frame of the program
	MyFrame() {

		// Study Button
		studyButton = studyButton();

		// HomeButton
		homeButton = homeButton();

		// tvShowButton
		tvShowButton = tvShowButton();

		filterButton = filterCombo();

		// Creates buttonListPanel and adds all buttons to the Panel

		buttonList = new JPanel();
		buttonList.add(studyButton);
		buttonList.add(homeButton);
		buttonList.add(tvShowButton);
		buttonList.add(filterButton);
    
		// This is the panel for all the tasks
		taskPanel = new JPanel();

		this.setTitle("Todos");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(500, 500);
		this.setVisible(true);
    
		// Creates the progressionPanel
		progressionPanel = new JPanel();

		// Adds the JPanels to the MyFrame
		this.add(buttonList, BorderLayout.NORTH);
		this.add(taskPanel);

		progressionLabel = new JLabel(completed + " out of " + notCompleted + " task completed");

		progressionPanel.add(progressionLabel);
		this.add(progressionPanel, BorderLayout.SOUTH);
	}

	@Override
	//Cheacks if the buttons are used as well as keeps the information what will happen if they are pressed or other wise used.
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == studyButton) {
			try {
				// When button is pressed it creates a new instance of StudyTask();

				Task studyTask = new StudyTask();
				// set TaskListener to each studyTask.
				studyTask.setTaskListener(this);
				taskCreated(studyTask);
				sortTasks("default");

			} catch (Exception error) {
				//Prints out a error message if the task couldn't be created.
				System.out.println("Could not create study task");
			}
		}
		if (e.getSource() == homeButton) {
			try {
				// Same functionality as studyButton.
				Task homeTask = new HomeTask();
				homeTask.setTaskListener(this);
				taskCreated(homeTask);
				sortTasks("Task type");

			} catch (Exception error) {
				System.out.println("Could not create home task");
			}
		}
		if (e.getSource() == tvShowButton) {
			try {
				// Same functionality as studyButton.

				Task tvShow = new TvShows();
				tvShow.setTaskListener(this);
				taskCreated(tvShow);
				sortTasks("Default");

			} catch (Exception error) {
				System.out.println("Could not create Tv shows");
			}
		}
		// Calls the filterSwitch function and sends in the values into filterSwitch
		// (filterButton.getSelectedItem());

		filterSwitch((String) filterButton.getSelectedItem());
	}

	// sortTasks method sorts the ArrayList of taskSorter by the compare
	public void sortTasks(String sortType) {
		Collections.sort(taskSorter, new Comparator<Task>() {
			public int compare(Task v1, Task v2) {
				if (sortType.equals("Text")) {
					// toLowerCase() because Java sort capitalized letters over non-capitalized
					return v1.getText().toLowerCase().compareTo(v2.getText().toLowerCase());
				} else if (sortType.equals("Completed")) {
					return Boolean.compare(v2.isComplete(), v1.isComplete());
				} else {
					return v1.getTaskType().compareTo(v2.getTaskType());
				}
			}
		});
    
		for (int i = 0; i < taskSorter.size(); i++) {
			taskPanel.add(taskSorter.get(i).getGuiComponent());
		}
		repaintPanel();
	}
	// Function for studyButton and only need to call it when its in need

	public JButton studyButton() {
		//Name on the Button
		studyButton = new JButton("studyTask");
		studyButton.setPreferredSize(new Dimension(100, 50));
		studyButton.addActionListener(this);
		return studyButton;
	}

	// Function for homeButton and only need to call it when its in need
	public JButton homeButton() {
		homeButton = new JButton("HomeTask");
		homeButton.setPreferredSize(new Dimension(100, 50));
		homeButton.addActionListener(this);
		return homeButton;
	}
  
	// Function for tvShowButton and only need to call it when its in need
	public JButton tvShowButton() {
		// Custom Button
		tvShowButton = new JButton("Tv Shows");
		tvShowButton.setPreferredSize(new Dimension(100, 50));
		tvShowButton.addActionListener(this);
		return tvShowButton;
	}
  
	// The values for the JComboBox is an array and "filter by:" is the default
	// value. the function creates a drop down menu for filter

	public JComboBox<String> filterCombo() {
		String[] filterOptions = { "Filter by:", "Task type", "Text", "Completed" };
		JComboBox<String> filterButton = new JComboBox<String>(filterOptions);
		filterButton.addActionListener(this);
		return filterButton;
	}

	// This method is used to check what the sortTask should sort on.
	public void filterSwitch(String filter) {
		if (filter.equals("Task type")) {
			sortTasks("Task type");
		} else if (filter.equals("Completed")) {
			sortTasks("Completed");
		} else {
			sortTasks("Text");
		}
	}

	// Function to repaint the taskPanel.
	public void repaintPanel() {
		taskPanel.validate();
		taskPanel.repaint();
	}

	// This is the progressionLabel and it updates when a task is created and/or
	// deleted.
	public void setCountLabel() {
		if (notCompleted < 2) {
			progressionLabel.setText(completed + " out of " + notCompleted + " task completed");

		} else {
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

	// adds the task into taskSorter array with all the tasks, add 1 to
	// notCompleted(amount of tasks) and update setCountLabel();
	@Override
	//Adds a task to the sorter and also to the progressbar when one is created
	public void taskCreated(Task t) {
		taskSorter.add(t);
		notCompleted++;
		setCountLabel();
	}

	// taskRemoved takes the current task and remove it from the array list, remove
	// notCompleted and updates setCountLabel and calls for removeTaskPanel
	@Override
	public void taskRemoved(Task t) {
		taskSorter.remove(t);
		System.out.println(taskSorter.size());
		notCompleted -= 1;
		if (t.isComplete()) {
			completed -= 1;
		}
		setCountLabel();
		removeTaskPanel();
	}

	// This method is used to remove the current panel and repaint it. Otherwise the
	// panel wont update when task removed.
	public void removeTaskPanel() {
		try {
			taskPanel.removeAll();
			taskPanel.validate();
			taskPanel.repaint();
			for (int i = 0; i < taskSorter.size(); i++) {
				taskPanel.add(taskSorter.get(i).getGuiComponent());
			}
			taskPanel.revalidate();

		} catch (Exception error) {
			System.out.println("Could not remove task");
		}
	}
}
