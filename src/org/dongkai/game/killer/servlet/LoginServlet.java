package org.dongkai.game.killer.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dongkai.game.killer.Controller;
import org.dongkai.game.killer.Global;
import org.dongkai.game.killer.Player;
import org.dongkai.game.killer.SimpleController;
import org.dongkai.game.killer.Util;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Controller gameController = new SimpleController();

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

		Player player = gameController.addPlayer(username);
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
