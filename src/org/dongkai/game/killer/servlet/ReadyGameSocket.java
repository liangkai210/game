package org.dongkai.game.killer.servlet;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.dongkai.game.killer.core.Controller;
import org.dongkai.game.killer.core.Player;
import org.dongkai.game.killer.core.Util;

import com.mongodb.BasicDBList;

@ServerEndpoint("/ready")
public class ReadyGameSocket {

	private static Set<Session> userSessions = Collections.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void onOpen(Session userSession) {
		userSessions.add(userSession);

		System.out.println("connect");

	}

	@OnClose
	public void onClose(Session userSession) {
		userSessions.remove(userSession);
	}

	@OnMessage
	public void onMessage(String message, Session userSession) {

		if (Util.isEmpty(message))
			return;
		Player current = Controller.getController().getPlayers().get(message);
		if (current == null) {
			return;
		}
		current.setReady(true);

		if (isAllReady()) {
		}

		BasicDBList allInfo = new BasicDBList();
		for (Player player : Controller.getController().getPlayers().values()) {
			allInfo.add(player.toString());
		}

		System.out.println("Message Received: " + message);
		for (Session session : userSessions) {
			System.out.println("Sending to " + session.getId());
			session.getAsyncRemote().sendText(allInfo.toString());
		}
	}

	private boolean isAllReady() {
		for (Player player : Controller.getController().getPlayers().values())
			if (player.isReady() == false)
				return false;
		return true;
	}
}
