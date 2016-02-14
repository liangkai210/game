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

import org.dongkai.game.killer.core.Controller;
import org.dongkai.game.killer.core.Player;
import org.dongkai.game.killer.core.Util;

import com.mongodb.BasicDBObject;

@ServerEndpoint("/ready")
public class ReadyGameSocket {

	private static Set<Session> userSessions = Collections.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void onOpen(Session userSession) {
		userSessions.add(userSession);
		System.out.println(userSession.getId());
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
		Player current = Controller.getController().getPlayers().get(message);
		if (current == null) {
			return;
		}
		current.setReady(true);

		System.out.println("Message Receivede: " + message);
		boolean isAllReady = isAllReady();
		System.out.println(isAllReady);
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
