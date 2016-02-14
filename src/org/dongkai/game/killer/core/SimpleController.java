package org.dongkai.game.killer.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dongkai.game.killer.core.Player.Role;
import org.dongkai.game.killer.core.Player.Status;

public class SimpleController implements Controller {

	private List<String> names;

	private Role winner;

	public SimpleController() {
		names = new ArrayList<>();
	}

	public void initRound() {
		clearDeathCount();
	}

	@Override
	public Player addPlayer(String name) {
		if (Util.isEmpty(name) || players.containsKey(name)) {
			return null;
		}
		names.add(name);

		System.out.println(name);

		Player player = new Player(name, null);
		players.put(name, player);
		return player;
	}

	public Role getWinner() {
		return winner;
	}

	public void setWinner(Role winner) {
		this.winner = winner;
	}

	@Override
	public void assignStatus() {
		clear();
		Set<Integer> set = new HashSet<>();
		assignKiller(set);
		assignPolice(set);
		assignSpeak(set);
		assignFolks();
	}

	private void clear() {
		for (Player player : players.values()) {
			player.clear();
		}
	}

	private void assignPolice(Set<Integer> set) {
		assign(set, Role.POLICE);
	}

	private void assignKiller(Set<Integer> set) {
		assign(set, Role.KILLER);
	}

	private void assignSpeak(Set<Integer> set) {
		assign(set, Role.SPEAKER);
	}

	private void assignFolks() {
		for (Player player : players.values()) {
			if (player.getRole() == null) {
				player.setRole(Role.FOLK);
			}
		}
	}

	private void assign(Set<Integer> set, Role role) {
		int randomNum = Util.getRandom(set, players.size());
		players.get(names.get(randomNum)).setRole(role);
	}

	@Override
	public Player killPerson() {
		int maxDeathCount = 0;
		Player result = null;
		for (Player player : players.values()) {
			if (player.getStatus() == Status.ALIVE) {
				int deathCount = player.getDeathCount();
				if (maxDeathCount < deathCount) {
					maxDeathCount = deathCount;
					result = player;
				}
			}
		}
		// double check, have some death count with max count player.
		for (Player player : players.values()) {
			if (player.getStatus() != Status.ALIVE)
				continue;
			if (player == result)
				continue;
			if (maxDeathCount == player.getDeathCount())
				return null;
		}
		result.setStatus(Status.DEAD);
		return result;
	}

	private void clearDeathCount() {
		for (Player player : players.values()) {
			if (player.getStatus() != Status.DEAD) {
				player.setDeathCount(0);
			}
		}
	}

	private int countAlive() {
		int result = 0;
		for (Player player : players.values()) {
			if (player.getStatus() == Status.ALIVE) {
				result++;
			}
		}
		return result;
	}

	@Override
	public boolean isGameOver() {
		Role role = killPerson().getRole();
		if (role == Role.POLICE) {
			winner = Role.POLICE;
			return true;
		}
		if (role == Role.KILLER) {
			winner = Role.KILLER;
			return true;
		}
		if (countAlive() < 3) {
			winner = Role.KILLER;
			return true;
		}
		return false;
	}

	@Override
	public void removePlayer(String name) {
		players.remove(name);
	}

}
