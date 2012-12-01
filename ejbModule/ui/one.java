package ui;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import java.awt.BorderLayout;
import javax.swing.Box;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import javax.swing.SpringLayout;
import javax.swing.BoxLayout;
import javax.swing.JSpinner;


public class one {

	private JFrame frame;
	private JProgressBar progressBar;
	private Box verticalBox;
	private final Action action = new SwingAction();
	private JPanel panel1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					one window = new one();
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
	public one() {
		initialize();
	}
	
	public class ImagePanel extends JPanel {        
        private Image image;
        
        public ImagePanel() {
            
        }
        
        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(image, 0, 0, null);
            
        }
    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 663, 422);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(new ImagePanel());
		
		JButton btnOne = new JButton("one");
		btnOne.setAction(action);
		btnOne.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				panel1.setBackground(Color.CYAN);
			}
		});
		menuBar.add(btnOne);
		
		JButton btnTwo = new JButton("two");
		btnTwo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				panel1.setBackground(Color.GRAY);
			}
		});
		menuBar.add(btnTwo);
		
		JButton btnThree = new JButton("three");
		menuBar.add(btnThree);
		
		progressBar = new JProgressBar();
		frame.getContentPane().add(progressBar, BorderLayout.SOUTH);
		
		verticalBox = Box.createVerticalBox();
		frame.getContentPane().add(verticalBox, BorderLayout.EAST);
		
		panel1 = new JPanel();
		frame.getContentPane().add(panel1, BorderLayout.CENTER);
		SpringLayout sl_panel1 = new SpringLayout();
		panel1.setLayout(sl_panel1);
		
		JSpinner spinner = new JSpinner();
		sl_panel1.putConstraint(SpringLayout.WEST, spinner, 38, SpringLayout.WEST, panel1);
		sl_panel1.putConstraint(SpringLayout.SOUTH, spinner, -72, SpringLayout.SOUTH, panel1);
		panel1.add(spinner);
	}

	protected JProgressBar getProgressBar() {
		return progressBar;
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
