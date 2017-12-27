import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Controller class for the GUI Calendar.
 * 
 * @author Aayush Dixit
 *
 */
public class Controller extends JPanel {
	private Model model;
	private ChangeListener cl;
	public Calendar cal = new GregorianCalendar();
	public Event e;

	final int width = 700;
	final int height = 50;
	JLabel l;
	String wDay;
	String month;
	int day;
	int year;

	/**
	 * Controller constructor
	 * 
	 * @param r
	 *            Model being called on by Controller.
	 */
	public Controller(Model r) {
		this.model = r;
		this.setPreferredSize(new Dimension(width, height));
		l = new JLabel();
		l.setHorizontalAlignment(SwingConstants.CENTER);
		day = cal.get(Calendar.DATE);
		year = cal.get(Calendar.YEAR);
		month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
		l.setText(month + " " + day + ", " + year);
		JButton firstButton = new JButton("<<");
		firstButton.addActionListener(move(-1));
		JButton secondButton = new JButton(">>");
		secondButton.addActionListener(move(1));
		JButton thirdButton = new JButton("Create");
		thirdButton.addActionListener(new ActionListener() {

			/**
			 * Creates a new event.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				newEvent();
			}

		});
		JButton fourthButton = new JButton("Quit");
		fourthButton.addActionListener(new ActionListener() {

			/**
			 * Saves the events and exits.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				saveEvents();
				System.exit(0);
			}

		});
		this.setLayout(new GridLayout(1, 5));
		this.add(l);
		this.add(firstButton);
		this.add(secondButton);
		this.add(thirdButton);
		this.add(fourthButton);
	}

	/**
	 * Loads the events from the model.
	 */
	public void loadEvents() {
		try {
			model.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves the events from the model and then quits.
	 */
	public void saveEvents() {
		try {
			model.quit();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new event. Parses the data from the user input.
	 */
	public void newEvent() {
		final JFrame frame = new JFrame();
		final JTextField textField = new JTextField("Event");
		final JTextField dateField = new JTextField(
				(cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));
		dateField.setEditable(false);
		final JTextField start = new JTextField("00:00");
		final JTextField end = new JTextField("23:59");
		JButton save = new JButton("Save");
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {

			/**
			 * Disposes the frame when user clicks cancel.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();

			}

		});
		save.addActionListener(new ActionListener() {

			/**
			 * Parses the data from user inputs.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				String eventName = textField.getText();
				String date = dateField.getText();
				String[] dateSplit = date.split("/");
				String monthString = dateSplit[0];
				int month = Integer.parseInt(monthString);
				String dayString = dateSplit[1];
				int day = Integer.parseInt(dayString);
				String yearString = dateSplit[2];
				int year = Integer.parseInt(yearString);
				String startTime = start.getText();
				String[] startSplit = startTime.split(":");
				String startHourString = startSplit[0];
				int startHour = Integer.parseInt(startHourString);
				String startMinString = startSplit[1];
				int startMinute = Integer.parseInt(startMinString);
				String endTime = end.getText();
				String[] endSplit = endTime.split(":");
				String endHourString = endSplit[0];
				int endHour = Integer.parseInt(endHourString);
				String endMinString = endSplit[1];
				int endMinute = Integer.parseInt(endMinString);
				Event r = new Event(eventName, year, month, day, startHour, startMinute, endHour, endMinute);
				model.add(r);
				frame.setVisible(false);
			}
		});

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));
		panel.add(textField);
		panel.add(dateField);
		panel.add(start);
		panel.add(end);
		panel.add(save);
		panel.add(cancel);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Moves the calendar's month based on the index to move it by.
	 * 
	 * @param index
	 *            index to move month by
	 * @return ActionListener that performs the action
	 */
	public ActionListener move(int index) {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cal.add(Calendar.DATE, index);
				change();
			}

		};

	}

	/**
	 * Gets all the events of the day and returns it as a string.
	 * 
	 * @param r
	 *            day to extract events from
	 * @return string of all the events
	 */
	public String getEventsOfDay(Calendar r) {
		wDay = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
		ArrayList<String> someStuff = model.returnEventsOfDay(r);
		String returnString = new String();
		if (someStuff.size() == 0) {
			returnString += "No events" + "\n";
		}
		for (String yo : someStuff) {
			returnString += yo + "\n";
		}
		return returnString;
	}

	/**
	 * Attaches ChangeListener.
	 * 
	 * @param c
	 *            ChangeListener
	 */
	public void attach(ChangeListener c) {
		cl = c;
	}

	/**
	 * Changes the month, day, and year based on the ChangeListener.
	 */
	public void change() {
		cl.stateChanged(new ChangeEvent(this));
		day = cal.get(Calendar.DATE);
		year = cal.get(Calendar.YEAR);
		month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
		l.setText(month + " " + day + ", " + year);
	}

}
