package org.dongkai.game.killer.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dongkai.game.killer.Controller;
import org.dongkai.game.killer.Player;
import org.dongkai.game.killer.SimpleController;
import org.dongkai.game.killer.Util;

public class LoginSevlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Controller gameController = new SimpleController();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		if (Util.isEmpty(username)) {
			String message = "username is empty, pls type again";
			sendError(message, req, resp);
		} else {
			Player player = gameController.addPlayer(username);
			if (player == null) {
				String message = "This name already been used, pls choose another one";
				sendError(message, req, resp);
			} else {
				req.getSession().setAttribute("player", player);
				req.getRequestDispatcher("/game.jsp").forward(req, resp);
			}

		}
	}

	private void sendError(String message, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("msg", message);
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
