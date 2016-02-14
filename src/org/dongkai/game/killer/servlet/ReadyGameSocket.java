package org.dongkai.game.killer.servlet;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;
import org.dongkai.game.killer.core.Controller;
import org.dongkai.game.killer.core.Player;
import org.dongkai.game.killer.core.Player.Role;
import org.dongkai.game.killer.core.Util;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

@ServerEndpoint("/ready")
public class ReadyGameSocket {

	static Logger logger = Logger.getLogger(ReadyGameSocket.class.getName());

	private static Set<Session> userSessions = Collections.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void onOpen(Session userSession) {
		userSessions.add(userSession);
		boolean isAllReady = isAllReady();
		String msg = createMsg(isAllReady);
		dispatch(msg);
	}

	@OnClose
	public void onClose(Session userSession) {
		userSessions.remove(userSession);
	}

	@OnMessage
	public void onMessage(String message, Session userSession) {

		if (Util.isEmpty(message)) {
			return;
		}

		BasicDBObject json = (BasicDBObject) JSON.parse(message.toString());

		String name = json.getString("name");
		if (Util.isEmpty(name)) {
			return;
		}

		Player current = Controller.getController().getPlayer(name);
		if (current == null) {
			return;
		}
		String type = json.getString("type");

		if ("ready".equals(type)) {
			current.setReady(true);
		} else if ("next-round".equals(type) && current.getRole() == Role.SPEAKER) {
			Controller.getController().clearReady();
			Controller.getController().clearRole();
		}

		boolean isAllReady = isAllReady();
		if (isAllReady) {
			Controller.getController().assignStatus();
		}
		String msg = createMsg(isAllReady);
		dispatch(msg);
	}

	private String createMsg(boolean isAllReady) {
		BasicDBObject msg = new BasicDBObject();
		msg.put("isReady", isAllReady);
		msg.put("players", Controller.getController().getAllData());
		return msg.toString();
	}

	private void dispatch(String msg) {
		for (Session session : userSessions) {
			session.getAsyncRemote().sendText(msg);
		}
	}

	private boolean isAllReady() {
		Map<String, Player> map = Controller.getController().getPlayers();
		if (map.size() < 4) {
			return false;
		}
		for (Player player : map.values())
			if (player.isReady() == false)
				return false;
		return true;
	}

}
