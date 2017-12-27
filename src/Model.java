import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * Model class for the GUI Calendar. Handles events.
 * 
 * @author Aayush Dixit
 *
 */
public class Model {
	private TreeSet<Event> e; // instance variable for TreeSet of events
	private Event l; // instance variable for event

	/**
	 * Constructor for Model class.
	 */
	public Model() {
		this.e = new TreeSet<>();
	}

	/**
	 * Returns all events of specified day in the form of an ArrayList of strings.
	 * 
	 * @param r
	 *            day to specify from calendar
	 * @return an ArrayList of Strings
	 */
	public ArrayList<String> returnEventsOfDay(Calendar r) {
		ArrayList<String> stuff = new ArrayList<>();
		List<Event> jj = new ArrayList<Event>(e);
		int month = r.get(Calendar.MONTH);
		if (r.get(Calendar.MONTH) == 0) {
			month = 11;
		}
		for (int i = 0; i < jj.size(); i++) {
			int jjMonth = jj.get(i).getStartTime().get(Calendar.MONTH) - 1;
			int jjYear = jj.get(i).getStartTime().get(Calendar.YEAR);
			if (jj.get(i).getStartTime().get(Calendar.MONTH) == 0) {
				jjMonth = 11;
				jjYear = jjYear - 1;
			}
			if (jjYear == r.get(Calendar.YEAR)) {
				if (jjMonth == month) {
					if (jj.get(i).getStartTime().get(Calendar.DAY_OF_MONTH) == r.get(Calendar.DAY_OF_MONTH)) {
						stuff.add(jj.get(i).toString());
					}
				}
			}
		}
		return stuff;
	}

	/**
	 * Loads events from events.txt file into TreeSet of events.
	 * 
	 * @throws IOException
	 */
	public void load() throws IOException {
		File file = new File("src/events.txt");
		if(file.exists() && file.isFile())
		{
		BufferedReader x = new BufferedReader(new FileReader(file));
		ArrayList<String> j = new ArrayList<>();
		String line1 = null;
		while ((line1 = x.readLine()) != null) {
			j.add(line1);
		}
		x.close();
		if (j.isEmpty()) {
			e.add(new Event("No events",2000,11,11,1,20,2,30));
		}
		String[] r;
		for (int i = 0; i < j.size(); i++) {
			r = j.get(i).split(" ");
			String[] date = r[0].split("/");
			int[] dates = new int[date.length];
			for (int m = 0; m < date.length; m++) {
				try {
					dates[m] = Integer.parseInt(date[m]);
				} catch (NumberFormatException nfe) {
				}
			}
			String delimiterOne = "-";
			String delimiterTwo = ":";
			r[1] = r[1].replaceAll(delimiterOne, delimiterTwo);
			String[] time = r[1].split(delimiterTwo);
			int[] times = new int[time.length];
			for (int n = 0; n < times.length; n++) {
				try {
					times[n] = Integer.parseInt(time[n]);
				} catch (NumberFormatException nfe) {
				}
			}
			String name = "";
			for (int y = 2; y < r.length; y++) {
				name += r[y] + " ";
			}
			l = new Event(name, dates[2], dates[0], dates[1], times[0], times[1], times[2], times[3]);
			e.add(l);
		}
		}
	}

	/**
	 * Adds an event to the TreeSet of Events. Automatically updates the TreeSet
	 * based on compareTo method from Event class.
	 * 
	 * @param r
	 *            event that is being added
	 */
	public void add(Event r) {
		e.add(r);
	}

	/**
	 * Writes existing data in TreeSet to a new txt file.
	 * 
	 * @throws FileNotFoundException
	 */
	public void quit() throws FileNotFoundException {
		File file = new File("src/events.txt");
		PrintWriter pW = new PrintWriter(file);
		int year = 0;
		for (Event kk : e) {
			year = kk.getStartTime().get(Calendar.YEAR);
			if (kk.getStartTime().get(Calendar.MONTH) == 0) {
				year = kk.getStartTime().get(Calendar.YEAR) - 1;
			}
			pW.println(year + " " + kk.toString());
		}
		pW.close();
	}

}
