package org.dongkai.game.killer;

public interface Controller {

	public void assign();

	public Player killPerson();

	public boolean isGameOver();

	public Player addPlayer(String name);

}
