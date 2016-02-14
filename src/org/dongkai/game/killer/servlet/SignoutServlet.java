package org.dongkai.game.killer.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dongkai.game.killer.core.Controller;
import org.dongkai.game.killer.core.Global;
import org.dongkai.game.killer.core.Player;
import org.dongkai.game.killer.core.SimpleController;

public class SignoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private Controller controller = new SimpleController();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Player player = (Player) req.getSession().getAttribute(Global.PLAYER);
		if (player != null) {
			controller.removePlayer(player.getName());
			req.getSession().removeAttribute(Global.PLAYER);
		}
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
