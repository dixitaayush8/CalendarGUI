import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * View class of the GUI calendar. Handles how the data is presented.
 * 
 * @author Aayush Dixit
 *
 */
public class View extends JPanel implements ChangeListener {
	DefaultTableModel cTable;
	DefaultTableModel dTable;
	final int height = 130;
	final int width = 700;
	int today;
	int someRow;
	int someCol;
	JTable table;
	JTextArea events;
	public Controller controller;
	public Model model = new Model();
	public Calendar myCal = new GregorianCalendar();

	/**
	 * Constructor for the View class. Adds the scrolling, the table for the month
	 * view, and the events for the day view.
	 * 
	 * @param c
	 *            Controller being integrated with View
	 */
	public View(Controller c) {
		events = new JTextArea();
		this.controller = c;
		controller.loadEvents();
		this.setPreferredSize(new Dimension(width, height));
		String[] cols = { "Sun", "Mon", "Tues", "Wed", "Thu", "Fri", "Sat" };
		cTable = new DefaultTableModel(null, cols);
		table = new JTable(cTable);
		table.setCellSelectionEnabled(true);
		JScrollPane tPane = new JScrollPane(table);
		this.setLayout(new GridLayout(1, 2));
		this.add(tPane);
		this.add(events);
		this.updateMonth();
		this.updateEvents();
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				JTable t = (JTable) e.getSource();
				int row = t.getSelectedRow();
				int col = t.getSelectedColumn();
				int yo = (int) t.getValueAt(row, col);
				c.cal.set(Calendar.DATE, yo);
				c.change();
			}
		});
	}

	/**
	 * Changes the state of this class by updating the events and the month.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		updateEvents();
		updateMonth();
	}

	/**
	 * Updates the events of the calendar by getting the events of the day from the
	 * Controller's calendar.
	 */
	public void updateEvents() {
		events.setText(controller.getEventsOfDay(controller.cal));
	}

	/**
	 * Updates what month it is based on the calendar API. Table is rendered from
	 * the CalendarRenderer.
	 */
	public void updateMonth() {
		today = controller.cal.get(Calendar.DATE);
		int month = controller.cal.get(Calendar.MONTH);
		myCal.set(Calendar.MONTH, month);
		myCal.set(Calendar.DAY_OF_MONTH, 1);
		int start = myCal.get(Calendar.DAY_OF_WEEK);
		int numDays = myCal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int weeks = myCal.getActualMaximum(Calendar.WEEK_OF_MONTH);
		cTable.setRowCount(0);
		cTable.setRowCount(weeks);
		int i = start - 1;
		for (int d = 1; d < numDays + 1; d++) {
			cTable.setValueAt(d, i / 7, i % 7);
			if (d == today) {
				someRow = i / 7;
				someCol = i % 7;
			}
			i = i + 1;
		}
		table.setDefaultRenderer(table.getColumnClass(0), new CalendarRenderer());
	}

	/**
	 * Renders the Calendar based on its superclass DefaultTableCellRenderer.
	 * 
	 * @author Aayush Dixit
	 *
	 */
	public class CalendarRenderer extends DefaultTableCellRenderer {
		/**
		 * Returns an instance of this class to highlight today's date and to set the
		 * color of the text.
		 */
		public Component getTableCellRendererComponent(JTable table, Object val, boolean select, boolean focus, int row,
				int col) {
			super.getTableCellRendererComponent(table, val, select, focus, row, col);
			if (row == someRow && col == someCol) {
				setBackground(new Color(200, 200, 200));
			} else {
				setBackground(new Color(255, 255, 255));
			}
			setBorder(null);
			setForeground(Color.BLUE);
			return this;
		}
	}

}
