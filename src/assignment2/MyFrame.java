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
	
	//Panelen �r global s� att n�r man skapar med knapparna s� l�ggs de h�r.
	JPanel buttonList, taskPanel;


	MyFrame() {
		
		//Varje knapp skapas h�r.
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
		
		//L�gger till 3 knappar i buttonList Panelen
		buttonList.add(studyButton);
		buttonList.add(homeButton);
		buttonList.add(cButton);
		
		
		//vart i framen saken ska ligga i, north �r h�gst upp.
		this.add(buttonList, BorderLayout.NORTH);
		this.add(taskPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==studyButton) {
			
			//N�r knappen trycks s� skapas en ny instans av task
			Task studyTask = new StudyTask();
			
			//L�gger till i panelen (Som en div)
			taskPanel.add(studyTask.getGuiComponent());
			//Validerar och renderar om panelen
			
			taskPanel.validate();
			taskPanel.repaint();
		}	
		if(e.getSource()==homeButton) {
			System.out.print("HUYG(AHY(UI)GFHAUIOSJDFHBAUIO=");
			
			//Samma som f�r studyButton
			Task task = new HomeTask();
			taskPanel.add(task.getGuiComponent());
			taskPanel.validate();
			taskPanel.repaint();
		}
	}
	
}
