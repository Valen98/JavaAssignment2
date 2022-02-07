package assignment2;
import javax.swing.JFrame;


import se.his.it401g.todo.HomeTask;
import se.his.it401g.todo.Task;

public class ToDo {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Leos Todos");
		frame.setBounds(100, 100, 400,100);
		
		Task task = new HomeTask();
		frame.add(task.getGuiComponent());

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public void completedStatusBar() {
		
		
	}
	

}
