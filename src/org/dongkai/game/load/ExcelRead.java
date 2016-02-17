package org.dongkai.game.load;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelRead {

	static Logger logger = Logger.getLogger(ExcelRead.class.getName());

	private String path = "/Users/dongdong/Downloads/Chem_Chem_Structure_Score.xlsx";

	public void read() throws IOException {

		FileInputStream file = new FileInputStream(new File(path));

		// Get the workbook instance for XLS file
		XSSFWorkbook workbook = new XSSFWorkbook(file);

		// Get first sheet from the workbook
		XSSFSheet sheet = workbook.getSheetAt(0);

		// Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = sheet.iterator();

		// Get iterator to all cells of current row
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			System.out.println(row);

			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				System.out.print(cell.getStringCellValue());
			}
			System.out.println();
		}
		close(workbook);
		close(file);
	}

	public void close(Closeable close) {
		if (close == null) {
			return;
		}
		try {
			close.close();
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
	}

}
