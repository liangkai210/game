<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="en">
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="Home page of the killer game">
<meta name="author" content="Kai Liang, Dong Zhou">


<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/cover.css" rel="stylesheet">
<link href="css/signin.css" rel="stylesheet">

</head>
<body>

	<div class="site-wrapper">

		<div class="site-wrapper-inner">

			<div class="cover-container">

				<jsp:include page="header.jsp" />

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