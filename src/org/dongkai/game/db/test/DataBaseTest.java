package org.dongkai.game.db.test;

import org.dongkai.game.db.DataBase;

public class DataBaseTest {

	public static void testMongoDB() {
		DataBase.MONGODB.getClient().getDatabase("test");
	}

	public static void main(String[] args) {
		testMongoDB();
	}
}
