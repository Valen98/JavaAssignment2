package assignment2;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import se.his.it401g.todo.Task;
import se.his.it401g.todo.TaskInputListener;
import se.his.it401g.todo.TaskListener;

public class Whiskey extends JPanel implements Task {

	private JTextField text;
	private JLabel textLabel;
	JCheckBox completed = new JCheckBox();
	private JPanel gradeRemovePanel;
	private JComboBox<String> whiskeyGrade;
	
	private TaskListener listener;
	public Whiskey() {
		super(new BorderLayout());
		this.text = new JTextField("Whiskey Brand", 16);
		this.textLabel = new JLabel();
		JPanel center = new JPanel();
		
		center.add(text);
		center.add(textLabel);
		this.add(center);
		
		TaskInputListener inputListener = new TaskInputListener(this, text, textLabel);
		this.text.addKeyListener(inputListener);
		this.textLabel.addMouseListener(inputListener);
		
		
		JButton remove = new JButton("Remove");
//		this.add(remove,BorderLayout.EAST);
		remove.addActionListener(inputListener);
		
		whiskeyGrade = whiskeyGrade();
		
		this.add(completed,BorderLayout.WEST);
		completed.addItemListener(inputListener);
		
		gradeRemovePanel = new JPanel();
		gradeRemovePanel.setLayout(new BorderLayout(3,1));
		
		gradeRemovePanel.add(remove, BorderLayout.EAST);
		
		gradeRemovePanel.add(whiskeyGrade, BorderLayout.WEST);
		
		
		this.add(gradeRemovePanel, BorderLayout.EAST);
		
		setMaximumSize(new Dimension(1000,50));
		setBorder(new TitledBorder(getTaskType()));
	}
	@Override
	public String getText() {
		return text.getText();
	}

	@Override
	public String getTaskType() {
		return "Whiskey";
	}

	@Override
	public void setTaskListener(TaskListener t) {
		listener = t;
		
	}

	@Override
	public TaskListener getTaskListener() {
		return listener;
	}

	@Override
	public boolean isComplete() {
		return completed.isSelected();
	}

	@Override
	public Component getGuiComponent() {
		return this;
	}

	
	public JComboBox<String> whiskeyGrade() {
		String[] gradeOptions = {"Grade","S","A","B","C","D","E", "F"};
		JComboBox<String> gradeButton = new JComboBox<String>(gradeOptions);
		return gradeButton;
	}
}
