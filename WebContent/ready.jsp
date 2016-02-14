
<%@page import="org.dongkai.game.killer.core.Player"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Ready to go</title>
<jsp:include page="header.jsp" />

</head>
<body>
	<div class="site-wrapper">
		<div class="site-wrapper-inner">
			<div class="cover-container">
				<jsp:include page="nav.jsp" />
				<%
					Player player = (Player) session.getAttribute("player");
					if (player != null) {
				%>
				<h3>
					<%="Welcome: " + player.getName()%>
				</h3>
				<form class="form-signin" action="ready" method="post">
					<%="<button class='btn btn-lg btn-default btn-block' type='submit'>Start!</button>"%>
				</form>
				<%
					}
				%>
				<jsp:include page="footer.jsp" />
			</div>
		</div>
	</div>
</body>
</html>