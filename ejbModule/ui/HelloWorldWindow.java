package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.SessionFactory;

import src.ExcelClass;
import src.HibernateUtil;
import src.NewsHandler;
import src.NewsHandler.Keys;
import dao.BussinessProcessDao;
import entity.BussinesProcess;
import entity.ForSingleBP;
import entity.News;

public class HelloWorldWindow {
	private final Font customFont = new Font("Tahoma", Font.PLAIN, 15);
	private final SimpleDateFormat sdf = new SimpleDateFormat(
			"dd-MM-yyyy; HH:mm");

	private JFrame frame;
	private JComboBox<String> comboBox;
	private static SessionFactory sf = HibernateUtil.getSessionFactory();
	private JTextField handled;
	private JTextField expectedTerm;
	private JTextField averageTerm;
	private CheckBoxGroup group;
	private JTextField employee;
	private JTextField biggestVariance;
	private JTextField pathField;
	private JTextField comment;
	private JTextField startedDateField;
	private JTextField completedDateField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HelloWorldWindow window = new HelloWorldWindow();
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
	public HelloWorldWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 744, 638);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		List<BussinesProcess> bp = new BussinessProcessDao(sf).getAll();
		List<String> bp2 = new ArrayList<>();
		for (BussinesProcess b : bp) {
			bp2.add(b.getName());
		}

		JLayeredPane layeredPane = new JLayeredPane();
		frame.getContentPane().add(layeredPane, BorderLayout.CENTER);

		comboBox = new JComboBox<>();
		comboBox.setBounds(28, 30, 669, 35);
		layeredPane.add(comboBox);
		comboBox.setMaximumRowCount(6);
		comboBox.setFont(customFont);
		comboBox.setModel(new DefaultComboBoxModel(bp2.toArray()));

		JLabel lblNewLabel = new JLabel(
				"Choose a bussines process from the list below, please");
		lblNewLabel.setBounds(179, 11, 367, 19);
		layeredPane.add(lblNewLabel);
		lblNewLabel.setFont(customFont);
		final JLabel readyIndicatorAllProcesses = new JLabel("File is ready");
		final JLabel fileIndicator = new JLabel("File is ready");

		JButton saveAndCount = new JButton("Calculate");
		saveAndCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Integer> values = group.getValues();
				if (ArrayUtils.isEmpty(values.toArray())) {
					JOptionPane.showMessageDialog(frame,
							"Select at least a year, please.");
				} else {
					try {
						String bpName = comboBox.getSelectedItem().toString();
						Map<Keys, Object> results = new NewsHandler(sf)
								.countWholeStatistics(bpName, values);
						handled.setText(results.get(Keys.HANDLED).toString());
						News news = (News) results.get(Keys.NEWS);
						expectedTerm.setText(String.valueOf(news.getBpTerm() * 24));
						comment.setText(StringUtils.isBlank((news.getComment())) ? "-"
								: news.getComment());
						employee.setText(news.getEmployee());
						biggestVariance.setText(String.valueOf(news
								.getVariance()));
						averageTerm.setText(results.get(Keys.AVERAGE_TERM)
								.toString());
						startedDateField.setText(sdf.format(news.getPlaned()));
						completedDateField.setText(news.getDone() == null ? sdf
								.format(DateUtils.addDays(news.getPlaned(),
										news.getBpTerm())) : sdf.format(news
								.getDone()));
					} catch (NullPointerException ee) {
						JOptionPane
								.showMessageDialog(
										frame,
										"There are no data for these input parameters. Choose other time frames or business process, please.");
						reset(readyIndicatorAllProcesses, fileIndicator);
					}
				}
			}
		});
		saveAndCount.setBounds(405, 86, 292, 35);
		layeredPane.add(saveAndCount);

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset(readyIndicatorAllProcesses, fileIndicator);
			}
		});
		btnReset.setBounds(405, 142, 292, 35);
		layeredPane.add(btnReset);

		group = new CheckBoxGroup(layeredPane);

		JLabel lblNewLabel_1 = new JLabel("Select years to search in");
		lblNewLabel_1.setFont(customFont);
		lblNewLabel_1.setBounds(28, 76, 182, 28);
		layeredPane.add(lblNewLabel_1);

		handled = new JTextField();
		handled.setEditable(false);
		handled.setFont(customFont);
		handled.setBounds(28, 213, 139, 35);
		layeredPane.add(handled);
		handled.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Handled scores");
		lblNewLabel_2.setFont(customFont);
		lblNewLabel_2.setBounds(28, 188, 139, 23);
		layeredPane.add(lblNewLabel_2);

		JLabel lblExpectedTerm = new JLabel("Expected term, h.");
		lblExpectedTerm.setFont(customFont);
		lblExpectedTerm.setBounds(198, 188, 129, 23);
		layeredPane.add(lblExpectedTerm);

		expectedTerm = new JTextField();
		expectedTerm.setEditable(false);
		expectedTerm.setFont(customFont);
		expectedTerm.setColumns(10);
		expectedTerm.setBounds(198, 213, 129, 35);
		layeredPane.add(expectedTerm);

		JLabel lblTheBiggest = new JLabel("Average term, h.");
		lblTheBiggest.setFont(customFont);
		lblTheBiggest.setBounds(365, 188, 137, 23);
		layeredPane.add(lblTheBiggest);

		averageTerm = new JTextField();
		averageTerm.setEditable(false);
		averageTerm.setFont(customFont);
		averageTerm.setColumns(10);
		averageTerm.setBounds(365, 213, 137, 35);
		layeredPane.add(averageTerm);

		JLabel lblCommentsoptional = new JLabel("Employee");
		lblCommentsoptional.setFont(customFont);
		lblCommentsoptional.setBounds(461, 259, 236, 23);
		layeredPane.add(lblCommentsoptional);

		employee = new JTextField();
		employee.setFont(customFont);
		employee.setEditable(false);
		employee.setColumns(10);
		employee.setBounds(461, 284, 236, 35);
		layeredPane.add(employee);

		biggestVariance = new JTextField();
		biggestVariance.setFont(customFont);
		biggestVariance.setEditable(false);
		biggestVariance.setColumns(10);
		biggestVariance.setBounds(533, 213, 164, 35);
		layeredPane.add(biggestVariance);

		JLabel lblTheBiggestVariance = new JLabel("The biggest variance, h.");
		lblTheBiggestVariance.setFont(customFont);
		lblTheBiggestVariance.setBounds(533, 188, 164, 23);
		layeredPane.add(lblTheBiggestVariance);

		JButton btnCalculation = new JButton("Calculation for single process");
		btnCalculation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (StringUtils.isBlank(pathField.getText())) {
					JOptionPane.showMessageDialog(frame,
							"Enter path in field above, please.");
					return;
				}
				String bpName = comboBox.getSelectedItem().toString();
				BussinesProcess bp = new BussinessProcessDao(sf)
						.getOneByName(bpName);
				Map<NewsHandler.Keys, Object> statistics;
				String fileName = "Proc" + bp.getId() + "_"
						+ String.valueOf(new Date().getTime() + ".xls");
				File file = new File(pathField.getText().concat(fileName));
				ExcelClass excel = new ExcelClass(file, "single_business_process");
				excel.makeTitleRow("YEAR", "AMOUNT", "AVERAGE_TERM, H", "EXPECTED_TERM, H", "MIN_TERM, H", "MAX_TERM, H", "OVERHEAD(AMOUNT), %", "OVERHEAD(DURATION), %");
				int row = 0;
				for (int i = 0; i < group.getDefaultYears().size(); i++) {
					statistics = new NewsHandler(sf).countWholeStatistics(
							bpName, group.getDefaultYears().subList(i, i + 1));
					if (statistics == null)
						continue;
					ForSingleBP fsbp = new ForSingleBP(bp, (Float) statistics
							.get(Keys.MIN_TERM), (Float) statistics
							.get(Keys.MAX_TERM), (Float) statistics
							.get(Keys.AVERAGE_TERM), (Integer) statistics
							.get(Keys.OVERHEAD_AMOUNT_PERCENT), (Integer) statistics
							.get(Keys.HANDLED), (Integer) statistics
							.get(Keys.OVERHEAD_PERCENT));
					excel.fillRowWithBP(++row, String.valueOf(group.getDefaultYears().get(i)), fsbp);
				}
				excel.closeBook();
				fileIndicator.setText("File is ready. Name is "
						+ fileName);
				fileIndicator.setVisible(true);
			}
		});
		btnCalculation.setBounds(28, 465, 214, 36);
		layeredPane.add(btnCalculation);

		pathField = new JTextField();
		pathField.setFont(customFont);
		pathField.setBounds(28, 418, 563, 36);
		layeredPane.add(pathField);
		pathField.setColumns(10);

		JLabel lblSelectFileTo = new JLabel("Select path to save results");
		lblSelectFileTo.setFont(customFont);
		lblSelectFileTo.setBounds(28, 397, 563, 23);
		layeredPane.add(lblSelectFileTo);

		JButton btnFile = new JButton("File");
		btnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int r = chooser.showOpenDialog(new JFrame());
				if (r == JFileChooser.APPROVE_OPTION) {
					pathField.setText(chooser.getSelectedFile().getPath());
				}
			}
		});
		btnFile.setBounds(601, 419, 96, 36);
		layeredPane.add(btnFile);

		fileIndicator.setForeground(new Color(0, 153, 0));
		fileIndicator.setFont(new Font("Tahoma", Font.BOLD, 15));
		fileIndicator.setBounds(248, 470, 449, 23);
		fileIndicator.setVisible(false);
		layeredPane.add(fileIndicator);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnClose.setBounds(395, 554, 302, 35);
		layeredPane.add(btnClose);

		comment = new JTextField();
		comment.setFont(customFont);
		comment.setEditable(false);
		comment.setColumns(10);
		comment.setBounds(28, 355, 669, 35);
		layeredPane.add(comment);

		JLabel label_1 = new JLabel("Comments (optional)");
		label_1.setFont(customFont);
		label_1.setBounds(28, 330, 669, 23);
		layeredPane.add(label_1);

		startedDateField = new JTextField();
		startedDateField.setFont(customFont);
		startedDateField.setEditable(false);
		startedDateField.setColumns(10);
		startedDateField.setBounds(28, 284, 182, 35);
		layeredPane.add(startedDateField);

		JLabel lblProcessWasStarted = new JLabel("Process was started");
		lblProcessWasStarted.setFont(customFont);
		lblProcessWasStarted.setBounds(28, 259, 182, 23);
		layeredPane.add(lblProcessWasStarted);

		completedDateField = new JTextField();
		completedDateField.setFont(customFont);
		completedDateField.setEditable(false);
		completedDateField.setColumns(10);
		completedDateField.setBounds(242, 284, 186, 35);
		layeredPane.add(completedDateField);

		JLabel lblProcessWasCompleted = new JLabel("Process was completed");
		lblProcessWasCompleted.setFont(customFont);
		lblProcessWasCompleted.setBounds(242, 259, 186, 23);
		layeredPane.add(lblProcessWasCompleted);

		JButton btnCalculationForAll = new JButton(
				"Calculation for all processes");
		btnCalculationForAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (StringUtils.isBlank(pathField.getText())) {
					JOptionPane.showMessageDialog(frame,
							"Enter path in field above, please.");
					return;
				}

				List<BussinesProcess> bps = new BussinessProcessDao(sf).getAll();
				String fileName = "Processes_" + String.valueOf(new Date().getTime() + ".xls");
				File file = new File(pathField.getText().concat(fileName));
				ExcelClass excel = new ExcelClass(file, "business_processes");
				excel.makeTitleRow(ArrayUtils.addAll(new String[]{"BUSINESS_PROCESS"}, group.getDefaultYearsAsStrings()));
				List<Integer> years = group.getDefaultYears();
				int yearsSize = years.size();
				float[] floats = new float[yearsSize];
				int row = 0;
				for (int y = 0; y < bps.size(); y++) {
					BussinesProcess bp = bps.get(y);
//					int nulls = 0;
					for (int i = 0; i < yearsSize; i++) {
						NewsHandler nh = new NewsHandler(sf);
						List<News> news = nh.getNewsWithParamByBPId(bp.getId(), years.subList(i, i + 1));
						floats[i] = nh.getPercentOfHandling(news, bp.getBpTerm() * 24);
//						if (floats[i] == 0)
//							nulls++;
					}
//					if (nulls != yearsSize)
						excel.fillRowForYears(++row, bp.getName(), floats);
				}
				excel.closeBook();
				readyIndicatorAllProcesses.setText("File is ready. Name is " + fileName);
				
				readyIndicatorAllProcesses.setVisible(true);
			}
		});
		btnCalculationForAll.setBounds(28, 512, 214, 36);
		layeredPane.add(btnCalculationForAll);

		readyIndicatorAllProcesses.setForeground(new Color(0, 153, 0));
		readyIndicatorAllProcesses.setFont(new Font("Tahoma", Font.BOLD, 15));
		readyIndicatorAllProcesses.setBounds(248, 516, 449, 25);
		readyIndicatorAllProcesses.setVisible(false);
		layeredPane.add(readyIndicatorAllProcesses);
	}

	private void reset(JLabel readyIndicatorAllProcesses, JLabel fileIndicator) {
		handled.setText("");
		expectedTerm.setText("");
		comment.setText("");
		employee.setText("");
		biggestVariance.setText("");
		pathField.setText("");
		readyIndicatorAllProcesses.setVisible(false);
		fileIndicator.setVisible(false);
		averageTerm.setText("");
		comboBox.setSelectedIndex(0);
		startedDateField.setText("");
		completedDateField.setText("");
		group.reset();
	}
}
