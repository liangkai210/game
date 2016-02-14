package org.dongkai.game.killer.core;

public interface Controller {

	public void assign();

	public Player killPerson();

	public boolean isGameOver();

	public Player addPlayer(String name);

	public void removePlayer(String name);
}
