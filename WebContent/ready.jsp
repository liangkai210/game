
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
				<div id="game-role"></div>
				<table class="table table-bordered table-hover">
					<thead>
					</thead>
					<tbody id="table-body">
					</tbody>
				</table>
				<button id="ready-game" onclick='start()'
					class='btn btn-lg btn-default' type='submit'>Start!</button>
				<button id="next-round" onclick='nextround()'
					class='btn btn-lg btn-default' type='submit'>Next Round!</button>
				<jsp:include page="footer.jsp" />
				<jsp:include page="socket.jsp" />
			</div>
		</div>
	</div>
</body>
</html>