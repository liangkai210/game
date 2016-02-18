package org.dongkai.game.killer.core;

import com.mongodb.BasicDBObject;

public class Player {

	public enum Role {
		POLICE, KILLER, FOLK, SPEAKER;
	}

	public enum Status {
		ALIVE, DEAD;
	}

	private boolean isReady;
	private int deathCount;
	private Status status;
	private Role role;
	private String name;
	private String ip;

	public Player() {
	}

	public Player(String name, String ip) {
		this.name = name;
		this.ip = ip;
		this.status = Status.ALIVE;
		this.isReady = false;
	}

	@Override
	public String toString() {
		BasicDBObject result = new BasicDBObject();
		result.put("deathCount", deathCount);
		result.put("name", name);
		result.put("ready", isReady);
		result.put("role", role == null ? role : role.toString());
		result.put("status", status == null ? status : status.toString());
		return result.toString();
	}

	public void voteToDeath(Player player) {
		if (this.status == Status.ALIVE) {
			player.addDeathCount();
		}
	}

	public void clear() {
		this.role = null;
		this.status = Status.ALIVE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getDeathCount() {
		return deathCount;
	}

	public void setDeathCount(int deathCount) {
		this.deathCount = deathCount;
	}

	public void addDeathCount() {
		this.deathCount++;
	}

	public boolean isReady() {
		return isReady;
	}

	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}
}
