package org.dongkai.game.load;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.dongkai.game.db.DataBase;

public class ExcelRead {

	static Logger logger = Logger.getLogger(ExcelRead.class.getName());

	// private String path =
	// "/Users/dongdong/Downloads/Chem_Chem_Structure_Score.xlsx";

	public void read() throws IOException {
		XSSFSheet sheet = DataBase.EXCEL.getFirstXSSFSheet();
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				System.out.print(cell.getNumericCellValue() + " ");
			}
			System.out.println();
		}
		DataBase.EXCEL.closeWorkBook();
	}

}
