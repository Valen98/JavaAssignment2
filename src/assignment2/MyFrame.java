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
	JButton sButton, hButton, cButton;
	
	//Panelen är global så att när man skapar med knapparna så läggs de här.
	JPanel buttonList, homePanel, studyPanel;


	MyFrame() {
		
		//Varje knapp skapas här.
		JPanel buttonList = new JPanel();
		sButton = new JButton("studyTask");
		sButton.setPreferredSize(new Dimension(150,50));
		sButton.addActionListener(this);
		hButton = new JButton("HomeTask");
		hButton.setPreferredSize(new Dimension(150,50));
		hButton.addActionListener(this);
		cButton = new JButton("CustomTask");
		cButton.setPreferredSize(new Dimension(150,50));

		homePanel = new JPanel();
		studyPanel = new JPanel();
		this.setTitle("Todos");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(500, 500);
		this.setVisible(true);
		
		//Lägger till 3 knappar i buttonList Panelen
		buttonList.add(sButton);
		buttonList.add(hButton);
		buttonList.add(cButton);
		
		
		//vart i framen saken ska ligga i, north är högst upp.
		this.add(buttonList, BorderLayout.NORTH);
		this.add(homePanel, BorderLayout.CENTER);
		this.add(studyPanel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==sButton) {
			
			//När knappen trycks så skapas en ny instans av task
			Task studyTask = new StudyTask();
			
			//Lägger till i panelen (Som en div)
			studyPanel.add(studyTask.getGuiComponent());
			//Validerar och renderar om panelen
			
			studyPanel.validate();
			studyPanel.repaint();
		}	
		if(e.getSource()==hButton) {
			System.out.print("HUYG(AHY(UI)GFHAUIOSJDFHBAUIO=");
			
			//Samma som för sButton
			Task task = new HomeTask();
			homePanel.add(task.getGuiComponent());
			homePanel.validate();
			homePanel.repaint();
		}
	}
	
}
