package org.dongkai.game.killer.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dongkai.game.killer.core.Controller;
import org.dongkai.game.killer.core.Global;
import org.dongkai.game.killer.core.Player;
import org.dongkai.game.killer.core.Util;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (req.getSession().getAttribute(Global.PLAYER) != null) {
			sendError("You already Login", req, resp);
			return;
		}

		String username = req.getParameter("username");
		if (Util.isEmpty(username)) {
			sendError("Your Name is empty, try again", req, resp);
			return;
		}

		Player player = Controller.getController().addPlayer(username);
		if (player == null) {
			sendError("Name already been used, pls choose another one", req, resp);
			return;
		}
		req.getSession().setAttribute(Global.PLAYER, player);
		req.getRequestDispatcher("/ready.jsp").forward(req, resp);
	}

	private void sendError(String message, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("msg", message);
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
