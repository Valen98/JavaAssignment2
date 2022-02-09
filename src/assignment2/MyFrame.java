package assignment2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import se.his.it401g.todo.HomeTask;
import se.his.it401g.todo.StudyTask;
import se.his.it401g.todo.Task;

public class MyFrame extends JFrame implements ActionListener{
	JButton studyButton, homeButton, cButton;
	
	//Panelen är global så att när man skapar med knapparna så läggs de här.
	JPanel buttonList, taskPanel;


	MyFrame() {
		
		//Varje knapp skapas här.
		JPanel buttonList = new JPanel();
		studyButton = new JButton("studyTask");
		studyButton.setPreferredSize(new Dimension(150,50));
		studyButton.addActionListener(this);
		homeButton = new JButton("HomeTask");
		homeButton.setPreferredSize(new Dimension(150,50));
		homeButton.addActionListener(this);
		cButton = new JButton("CustomTask");
		cButton.setPreferredSize(new Dimension(150,50));
		
		taskPanel = new JPanel();
		this.setTitle("Todos");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(500, 500);
		this.setVisible(true);
		
		//Lägger till 3 knappar i buttonList Panelen
		buttonList.add(studyButton);
		buttonList.add(homeButton);
		buttonList.add(cButton);
		
		
		//vart i framen saken ska ligga i, north är högst upp.
		this.add(buttonList, BorderLayout.NORTH);
		this.add(taskPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==studyButton) {
			
			//När knappen trycks så skapas en ny instans av task
			Task studyTask = new StudyTask();
			
			//Lägger till i panelen (Som en div)
			taskPanel.add(studyTask.getGuiComponent());
			//Validerar och renderar om panelen
			
			taskPanel.validate();
			taskPanel.repaint();
		}	
		if(e.getSource()==homeButton) {
			System.out.print("HUYG(AHY(UI)GFHAUIOSJDFHBAUIO=");
			
			//Samma som för studyButton
			Task task = new HomeTask();
			taskPanel.add(task.getGuiComponent());
			taskPanel.validate();
			taskPanel.repaint();
		}
	}
	
}
