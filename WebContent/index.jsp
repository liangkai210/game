
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + request.getContextPath();
%>
<html lang="en">
<head>
<title>Killer</title>
<jsp:include page="header.jsp" />
</head>
<body>

	<div class="site-wrapper">
		<div class="site-wrapper-inner">
			<div class="cover-container">

				<jsp:include page="nav.jsp" />

				<div class="inner cover">
					<h2 class="cover-heading">Join</h2>
				</div>

				<jsp:include page="msg.jsp" />

				<form class="form-signin" action="login" method="post">
					<label for="inputEmail" class="sr-only">name</label> <input
						type="text" id="username" class="form-control" name="username"
						placeholder="Enter your name" required autofocus> <br>
					<button class="btn btn-lg btn-default btn-block" type="submit">Go!</button>
				</form>


				<jsp:include page="footer.jsp" />

			</div>
		</div>
	</div>

</body>
</html>