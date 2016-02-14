package org.dongkai.game.killer.core;

import java.util.HashMap;
import java.util.Map;

public interface Controller {

	public Map<String, Player> players = new HashMap<>();

	public void assignStatus();

	public Player killPerson();

	public boolean isGameOver();

	public Player addPlayer(String name);

	public void removePlayer(String name);

}
