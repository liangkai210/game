package org.dongkai.game.db;

import com.mongodb.MongoClient;

public enum DataBase {

	MONGODB("10.48.1.61", 27017);

	private DataBase(String host, int port) {
		client = new MongoClient(host, port);
	}

	private MongoClient client;

	public MongoClient getClient() {
		return client;
	}

}
