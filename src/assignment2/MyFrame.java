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
import javax.swing.JPanel;

import se.his.it401g.todo.HomeTask;
import se.his.it401g.todo.StudyTask;
import se.his.it401g.todo.Task;

public class MyFrame extends JFrame implements ActionListener{
	JButton studyButton, homeButton, cButton;

	JComboBox<String> filterButton;
	
	//Panelen är global så att när man skapar med knapparna så läggs de här.
	JPanel buttonList, taskPanel;

	ArrayList<Task> taskSorter = new ArrayList<Task>();
	
	MyFrame() {
		
		//Study Button
		studyButton = studyButton();
		
		//HomeButton
		homeButton = homeButton();
		filterButton = filterButton();
		
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
	
		//vart i framen saken ska ligga i, north är högst upp.
		this.add(buttonList, BorderLayout.NORTH);
		this.add(taskPanel);
		
		
		
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
			repaintPanel();
		}	
		if(e.getSource()==homeButton) {
			
			//Samma som för studyButton
			Task homeTask = new HomeTask();
			taskSorter.add(homeTask);
			sortTasks("getTaskType");
			repaintPanel();
		}
		
		
		filterSwitch((String) filterButton.getSelectedItem());
	}
	
	public void sortTasks(String sortType) {
		Collections.sort(taskSorter, new Comparator<Task>() {
			public int compare(Task v1, Task v2) {
				if(sortType.equals("getText")) {
					System.out.println("Changed filter");
					return v1.getText().compareTo(v2.getText());
					
				} else {
					System.out.println("TaskType");
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
	
	
	public JComboBox<String> filterButton() {
		String[] filterOptions = {"getTaskType", "getTask"};
		JComboBox<String> filterButton = new JComboBox<String>(filterOptions);
		filterButton.addActionListener(this);
		return filterButton;
	}
	
	
	public void filterSwitch(String filter) {
		if(filter.equals("getTaskType")) {
			System.out.println("TaskType");
			sortTasks("getTaskType");
			repaintPanel();
		}else {
			System.out.println("getText");
			sortTasks("getText");
			repaintPanel();
		}
	}
	
	
	public void repaintPanel() {
		taskPanel.validate();
		taskPanel.repaint();
	}
}
