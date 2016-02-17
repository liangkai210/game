package org.dongkai.game.db.test;

import java.io.IOException;

import org.dongkai.game.load.ExcelRead;

public class ExcelTest {

	public static void testRead() throws IOException {

		ExcelRead read = new ExcelRead();

		read.read();
	}

	public static void main(String[] args) throws IOException {
		testRead();
	}
}
