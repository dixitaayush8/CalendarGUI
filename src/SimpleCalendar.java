import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * The SimpleCalendar class consisting of a frame with the model, view, and
 * controller design.
 * 
 * @author Aayush Dixit
 *
 */
public class SimpleCalendar {
	/**
	 * Testing the MVC pattern of the GUI Calendar.
	 * 
	 * @param args
	 *            arguments
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame();
		Model m = new Model();
		Controller controller = new Controller(m);
		View views = new View(controller);
		controller.attach(views);
		f.add(views, BorderLayout.SOUTH);
		f.add(controller, BorderLayout.NORTH);
		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
