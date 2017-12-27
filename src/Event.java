import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

/**
 * Event class, which consists of a string, start time, and end time based on a
 * user's input that uses the GregorianCalendar API.
 */
public class Event implements Comparable<Object> {
	/**
	 * Enums for all the months
	 */
	enum MONTHS {
		Jan, Feb, March, Apr, May, June, July, Aug, Sep, Oct, Nov, Dec;
	}

	/**
	 * Enums for all the days
	 */
	enum DAYS {
		Sun, Mon, Tue, Wed, Thur, Fri, Sat;
	}

	private GregorianCalendar startTime;
	private GregorianCalendar endTime;
	private String name;
	private int x = 0; // variable used to handle the 31st of the month

	/**
	 * A constructor for Event. Takes title, year, month, day, first hour, first
	 * minute, last hour, and last minute as parameters. Initializes the name,
	 * startTime, and endTime instance variables based on those parameters.
	 * 
	 * @param title
	 *            the string of the event that the user enters
	 * @param year
	 *            the year of the event
	 * @param month
	 *            month of the event
	 * @param day
	 *            day of event
	 * @param startHour
	 *            first hour of event
	 * @param startMin
	 *            first minute of event
	 * @param endHour
	 *            last hour of event
	 * @param endMin
	 *            last minute of event
	 */
	public Event(String title, int year, int month, int day, int startHour, int startMin, int endHour, int endMin) {
		name = title;
		startTime = new GregorianCalendar(year, month, day, startHour, startMin);
		if (day == startTime.getActualMaximum(Calendar.DAY_OF_MONTH) && month != 12) {
			x = 1;
		}
		endTime = new GregorianCalendar(year, month, day, endHour, endMin);
	}

	/**
	 * Returns the start time.
	 * 
	 * @return startTime
	 */
	public GregorianCalendar getStartTime() {
		return startTime;
	}

	/**
	 * Returns the end time.
	 * 
	 * @return endTime
	 */
	public GregorianCalendar getEndTime() {
		return endTime;
	}

	/**
	 * Returns the name of the event.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the start time.
	 * 
	 * @param start
	 *            start time
	 */
	public void setStartTime(GregorianCalendar start) {
		this.startTime = start;
	}

	/**
	 * Sets the end time.
	 * 
	 * @param end
	 *            end time
	 */
	public void setEndTime(GregorianCalendar end) {
		this.endTime = end;
	}

	/**
	 * Sets the name of the event.
	 * 
	 * @param s
	 *            name of the event
	 */
	public void setName(String s) {
		this.name = s;
	}

	/**
	 * Returns the month, day of the month, year, start time, end time, and name of
	 * the Event object as a string.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		MONTHS[] arrayOfMonths = MONTHS.values();
		String s = String.valueOf(startTime.get(Calendar.DAY_OF_MONTH));
		String n = String.valueOf(startTime.get(Calendar.MONTH));
		String r = String.valueOf(startTime.get(Calendar.YEAR));
		int z = startTime.get(Calendar.MONTH);
		int nn = startTime.getActualMaximum(Calendar.DAY_OF_MONTH);

		if (x == 1) {
			startTime.set(Calendar.YEAR, z - 1, nn - 1);
			System.out.println(startTime.get(Calendar.MONTH));
			s = String.valueOf(nn);
			n = String.valueOf(startTime.get(Calendar.MONTH));
			LocalDate date = LocalDate.of(startTime.get(Calendar.YEAR), startTime.get(Calendar.MONTH), nn);
			// date.toString();
			// System.out.println(date.toString());
		}

		if (n.equals("0")) { // handling December value. Month value converts
								// from "0" to "12."
			n = "12";
			r = String.valueOf(startTime.get(Calendar.YEAR) - 1);
		}
		String startMinute = String.valueOf(startTime.get(Calendar.MINUTE));
		if (startTime.get(Calendar.MINUTE) < 10) {
			startMinute = "0" + startMinute;
		}
		int intString = Integer.parseInt(n);
		int inTwoString = Integer.parseInt(s);
		DayOfWeek day;
		if (intString < 10) {
			if (inTwoString < 10) { // uses LocalDate tool to parse dates
				day = LocalDate.parse(r + "-" + "0" + n + "-" + "0" + s).getDayOfWeek();
			} else {
				day = LocalDate.parse(r + "-" + "0" + n + "-" + s).getDayOfWeek();
			}
		} else if (intString >= 10 && inTwoString < 10) {
			day = LocalDate.parse(r + "-" + n + "-" + "0" + s).getDayOfWeek();
		} else {
			day = LocalDate.parse(r + "-" + n + "-" + s).getDayOfWeek();
		}
		if (endTime != null) { // if the user enters an end time, return the
								// Event string with the end time
			String endMinute = String.valueOf(endTime.get(Calendar.MINUTE));
			if (endTime.get(Calendar.MINUTE) < 10) {
				endMinute = "0" + endMinute;
			}
			return "" + day.getDisplayName(TextStyle.FULL, Locale.US) + " " + arrayOfMonths[intString - 1] + " " + s
					+ " " + startTime.get(Calendar.HOUR_OF_DAY) + ":" + startMinute + " - "
					+ endTime.get(Calendar.HOUR_OF_DAY) + ":" + endMinute + " " + getName();
		}
		return "" + day.getDisplayName(TextStyle.FULL, Locale.US) + " " + arrayOfMonths[intString - 1] + " " + s + " "
				+ startTime.get(Calendar.HOUR_OF_DAY) + ":" + startMinute + " " + getName();

	}

	/**
	 * compareTo method for comparing the event start times and end times. Organizes
	 * events based on starting date and starting time. Handles conflicting events
	 * based on their start time and end time by returning 0.
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Object arg0) {
		Event that = (Event) arg0;
		if (that.endTime != null && this.endTime != null) {
			if (that.startTime.before(this.startTime) && that.endTime.before(this.startTime)) {
				return 1;
			} else if (that.startTime.after(this.endTime) && that.endTime.after(this.endTime)) {
				return -1;
			} else {
				return 0;
			}
		} else if (that.endTime != null && this.endTime == null) {
			if (that.endTime.after(this.startTime) && that.startTime.after(this.startTime)) {
				return -1;
			} else if (that.startTime.before(this.startTime) && that.endTime.before(this.startTime)) {
				return 1;
			} else {
				return 0;
			}
		} else if (that.endTime == null && this.endTime != null) {
			if (that.startTime.after(this.startTime) && that.startTime.after(this.endTime)) {
				return -1;
			} else if (that.startTime.before(this.startTime) && that.startTime.before(this.endTime)) {
				return 1;
			}
			return 0;
		} else if (that.endTime == null && this.endTime == null) {
			if (that.startTime.after(this.startTime)) {
				return -1;
			} else if (that.startTime.before(this.startTime)) {
				return 1;
			}
		}
		return 0;
	}

}
