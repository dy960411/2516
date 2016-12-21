package a;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class yello {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					yello window = new yello();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public yello() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		final Frame A1 = new Frame("窗口");
		A1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				A1.setBackground(Color.green);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				A1.setBackground(Color.YELLOW);
			}
		});
		A1.setBounds(333, 333, 444, 444);
		A1.setBackground(Color.YELLOW);
		A1.setVisible(true);
		
	}
	

	

}

