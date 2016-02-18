package org.dongkai.game.load;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

/**
 * XSSF and SAX (Event API)
 */
public class ExcelToMongo {

	private static Map<String, List<Pair>> maps = new ConcurrentHashMap<>();

	static BasicDBList mongoData = new BasicDBList();
	static int flushSize = 50;

	public static void processOneSheet(String filename) throws Exception {
		OPCPackage pkg = OPCPackage.open(filename);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();
		XMLReader parser = fetchSheetParser(sst);
		InputStream sheet1 = r.getSheet("rId1");
		InputSource sheetSource = new InputSource(sheet1);
		parser.parse(sheetSource);
		sheet1.close();
	}

	public static XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
		XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
		ContentHandler handler = new SheetHandler(sst);
		parser.setContentHandler(handler);
		return parser;
	}

	static class Pair {
		boolean isVal = false;
		String col;
		String val;
		String key;

		public Pair(String col, String val) {
			this.col = col;
			this.val = val;
			setName(col);
		}

		private void setName(String col) {
			if ("C".equalsIgnoreCase(col)) {
				key = "tmScore";
				isVal = true;
			} else if ("A".equalsIgnoreCase(col)) {
				key = "CID_1";
			} else if ("B".equalsIgnoreCase(col)) {
				key = "CID_2";
			}
		}

		public BasicDBObject getJson() {
			BasicDBObject result = new BasicDBObject();
			if (isVal) {
				try {
					result.append(key, Double.parseDouble(val));
				} catch (Exception e) {
				}
			} else {
				result.append(key, val);
			}
			return result;
		}

	}

	private static class SheetHandler extends DefaultHandler {
		private SharedStringsTable sst;
		private String row;
		private String col;
		private String val;
		private boolean nextIsString;

		private SheetHandler(SharedStringsTable sst) {
			this.sst = sst;
		}

		@Override
		public void startElement(String uri, String localName, String name, Attributes attributes)
				throws SAXException {
			// c => cell
			if (name.equals("c")) {
				// Print the cell reference
				String rowColumn = attributes.getValue("r");
				row = rowColumn.substring(1);
				col = rowColumn.substring(0, 1);
				// System.out.print(attributes.getValue("r") + " - " + row + ":
				// " + col + " [");
				// Figure out if the value is an index in the SST
				String cellType = attributes.getValue("t");
				if (cellType != null && cellType.equals("s")) {
					nextIsString = true;
				} else {
					nextIsString = false;
				}
			}
			// Clear contents cache
			val = "";
		}

		@Override
		public void endElement(String uri, String localName, String name) throws SAXException {
			// Process the last contents as required.
			// Do now, as characters() may be called more than once
			if (nextIsString) {
				int idx = Integer.parseInt(val);
				val = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
				nextIsString = false;
			}
			// v => contents of a cell
			// Output after we've seen the string contents
			if (name.equals("v")) {
				// System.out.println(val + "]");
				addToMap();
			}
		}

		private void addToMap() {
			if (!maps.containsKey(row)) {
				maps.put(row, new LinkedList<>());
			}
			maps.get(row).add(new Pair(col, val));
		}

		public void characters(char[] ch, int start, int length) throws SAXException {
			val += new String(ch, start, length);
		}
	}

	private static void readMap() {
		for (Entry<String, List<Pair>> entry : maps.entrySet()) {
			BasicDBList item = new BasicDBList();
			for (int i = 0; i < 3; i++) {
				item.add(entry.getValue().get(i).getJson());
				mongoData.add(item);
				if (mongoData.size() >= flushSize) {
					System.out.println(mongoData);
					mongoData = new BasicDBList();
				}
			}
		}
	}

	public static void main(String[] args) {
		String path = "/Users/dongdong/Downloads/Chem_Chem_Structure_Score.xlsx";
		try {
			ExcelToMongo.processOneSheet(path);
			readMap();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}