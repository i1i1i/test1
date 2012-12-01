package src;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import entity.ForSingleBP;

public class ExcelClass {
	private static WritableWorkbook workbook;
	public static WritableSheet sheet;
	public static WritableCellFormat tahoma12BoldFormat;
	public static WritableCellFormat tahoma12Format;
	public static Label label;

	public ExcelClass(File file, String sheetName) {
		WorkbookSettings ws = new WorkbookSettings();
		ws.setLocale(new Locale("ru", "RU"));

		try {
			tahoma12BoldFormat = new WritableCellFormat(new WritableFont(
					WritableFont.TAHOMA, 12, WritableFont.BOLD));
			tahoma12BoldFormat.setAlignment(Alignment.CENTRE);
			tahoma12BoldFormat.setWrap(true);
			tahoma12BoldFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);

			tahoma12Format = new WritableCellFormat(new WritableFont(
					WritableFont.TAHOMA, 12));
			tahoma12Format.setAlignment(Alignment.CENTRE);
			tahoma12Format.setWrap(true);
			tahoma12Format.setBorder(Border.ALL, BorderLineStyle.MEDIUM);

			workbook = Workbook.createWorkbook(file, ws);
			sheet = workbook.createSheet(sheetName, 0);

		} catch (IOException | WriteException e) {
			e.printStackTrace();
		}

	}

	public void closeBook() {
		try {
			workbook.write();
			workbook.close();
		} catch (WriteException | IOException e) {
			e.printStackTrace();
		}
	}

	public void fillRowWithBP(int rowNumber, String rowName, ForSingleBP fsbp) {
		try {
			sheet.addCell(new Label(0, rowNumber, rowName, tahoma12BoldFormat));
			sheet.addCell(new Number(1, rowNumber, fsbp.getAmount(), tahoma12Format));
			sheet.addCell(new Number(2, rowNumber, fsbp.getAverageTerm(), tahoma12Format));
			sheet.addCell(new Number(3, rowNumber, fsbp.getBp().getBpTerm() * 24, tahoma12Format));
			sheet.addCell(new Number(4, rowNumber, fsbp.getMinTime(), tahoma12Format));
			sheet.addCell(new Number(5, rowNumber, fsbp.getMaxTime(), tahoma12Format));
			sheet.addCell(new Number(6, rowNumber, fsbp.getOverheadAmountPercent(), tahoma12Format));
			sheet.addCell(new Number(7, rowNumber, fsbp.getOverheadPercent(), tahoma12Format));;

		} catch (WriteException e) {
			e.printStackTrace();
		}
	}
	
	public void fillRowForYears(int rowNumber, String rowName, float[] floats) {
		try {
			int i = 0;
			sheet.addCell(new Label(i++, rowNumber, rowName, tahoma12BoldFormat));
			for (float fl : floats) {
				sheet.addCell(new Number(i++, rowNumber, fl, tahoma12Format));
			}
			
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}

	public void makeTitleRow(String... names) {
		int i = 0;
		try {
			for (String name : names) {
				sheet.setColumnView(i, 35);
				sheet.addCell(new Label(i++, 0, name, tahoma12BoldFormat));
			}
			//workbook.write();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}
}
