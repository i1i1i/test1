package ui;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLayeredPane;

public class CheckBoxGroup {
	List<JCheckBox> cb = new ArrayList<>();
	private static String[] years = new String[]{"2007","2008","2009","2010","2011","2012"};

	public CheckBoxGroup(JLayeredPane layeredPane) {
		JCheckBox cb1;
		int x = 28;
		int y = 80;
		for (String year : years) {
			if (y == 146) {
				y = 102;
				x += 100;
			} else {
				y += 22;
			}
			cb1 = new JCheckBox(year);
			cb1.setFont(new Font("Tahoma", Font.PLAIN, 15));
			cb1.setBounds(x, y, 97, 23);
			layeredPane.add(cb1);
			cb.add(cb1);
		}
	}
	
	public List<Integer> getValues() {
		List<Integer> values = new ArrayList<>();
		for (JCheckBox cb1 : cb) {
			if (cb1.isSelected())
				values.add(Integer.valueOf(cb1.getText()));
		}
		return values;
	}
	
	public void reset() {
		for (JCheckBox cb1 : cb) {
			cb1.setSelected(false);
		}
	}
	
	public List<Integer> getDefaultYears(){
		List<Integer> yearsI = new ArrayList<>();
		for (String y : years) {
			yearsI.add(Integer.valueOf(y));
		}
		return yearsI;
	}
	
	public String[] getDefaultYearsAsStrings(){
		return years;
	}
}
