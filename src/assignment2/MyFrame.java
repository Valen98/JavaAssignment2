package assignment2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import se.his.it401g.todo.HomeTask;
import se.his.it401g.todo.Task;

public class MyFrame extends JFrame implements ActionListener{
	JButton sButton, hButton;

	MyFrame() {
		sButton = new JButton("studyTask");
		sButton.setBounds(20, 20, 100, 50);
		sButton.addActionListener(this);
		hButton = new JButton("HomeTask");
		hButton.setBounds(150,20, 100, 50);
		hButton.addActionListener(this);
		
		
		Task task = new HomeTask();
		
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(500, 500);
		this.setVisible(true);
		
		this.add(sButton);
		this.add(hButton);
		this.add(task.getGuiComponent());
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==sButton) {
			System.out.print("Hi");
		}	
		if(e.getSource()==hButton) {
			System.out.print("HUYG(AHY(UI)GFHAUIOSJDFHBAUIO=");
		}
		
	}
}
