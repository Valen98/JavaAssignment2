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

public class TvShows extends JPanel implements Task {

	private JTextField text;
	private JLabel textLabel;
	JCheckBox completed = new JCheckBox();
	private JPanel gradeRemovePanel;
	private JComboBox<String> tvShowGenre;

	private TaskListener listener;

	public TvShows() {
		super(new BorderLayout());
		//Default value for the input field.
		this.text = new JTextField("Tv show to watch", 16);
		this.textLabel = new JLabel();
		JPanel center = new JPanel();

		center.add(text);
		center.add(textLabel);
		this.add(center);

		TaskInputListener inputListener = new TaskInputListener(this, text, textLabel);
		this.text.addKeyListener(inputListener);
		this.textLabel.addMouseListener(inputListener);

		JButton remove = new JButton("Remove");
		remove.addActionListener(inputListener);

		tvShowGenre = tvShowGenre();

		this.add(completed, BorderLayout.WEST);
		completed.addItemListener(inputListener);
		//gradeRemovePanel groups the remove button and grade drop down menu otherwise it will override each other 
		gradeRemovePanel = new JPanel();
		gradeRemovePanel.setLayout(new BorderLayout(3, 1));

		gradeRemovePanel.add(remove, BorderLayout.EAST);

		gradeRemovePanel.add(tvShowGenre, BorderLayout.WEST);

		this.add(gradeRemovePanel, BorderLayout.EAST);

		setMaximumSize(new Dimension(1000, 50));
		setBorder(new TitledBorder(getTaskType()));
	}

	@Override
	public String getText() {
		return text.getText();
	}

	@Override
	public String getTaskType() {
		return "Tv Show";
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
	//Drop down menu for tv show genre
	public JComboBox<String> tvShowGenre() {
		String[] gradeOptions = { "Genre", "Drama", "Action", "Thriller", "Comedy", "Adventure", "Western", "Horror" };
		JComboBox<String> gradeButton = new JComboBox<String>(gradeOptions);
		return gradeButton;
	}
}
