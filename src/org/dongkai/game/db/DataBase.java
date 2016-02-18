package org.dongkai.game.db;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mongodb.MongoClient;

public enum DataBase {

	MONGODB("10.48.1.61", 27017), EXCEL("/Users/dongdong/Downloads/test.xlsx", 0);

	Logger logger = Logger.getLogger(DataBase.class.getName());

	private DataBase(String host, int port) {
		this.host = host;
		this.port = port;
	}

	private String host;
	private int port;
	private MongoClient client;
	private XSSFWorkbook workbook;

	public MongoClient getClient() {
		if (client == null) {
			synchronized (DataBase.class) {
				if (client == null) {
					client = new MongoClient(host, port);
				}
			}
		}
		return client;
	}

	public XSSFWorkbook getWorkbook() {
		if (workbook == null) {
			synchronized (DataBase.class) {
				if (workbook == null) {
					FileInputStream file = null;
					try {
						file = new FileInputStream(new File(host));
						workbook = new XSSFWorkbook(file);
					} catch (FileNotFoundException e) {
						logger.warning(e.getMessage());
					} catch (IOException e) {
						logger.warning(e.getMessage());
					} finally {
						closeIO(file);
					}
				}
			}
		}
		return workbook;
	}

	public XSSFSheet getFirstXSSFSheet() {
		return getXSSFSheetAt(0);
	}

	public XSSFSheet getXSSFSheetAt(int index) {
		XSSFWorkbook workbook = getWorkbook();
		if (workbook == null) {
			return null;
		}
		return workbook.getSheetAt(index);
	}

	public void closeWorkBook() {
		closeIO(workbook);
	}

	private void closeIO(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				logger.warning(e.getMessage());
			}
		}
	}

}
