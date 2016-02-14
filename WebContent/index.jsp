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

				<div class="inner cover">
					<h1 class="cover-heading">Killer !</h1>
				</div>


				<%=request.getAttribute("msg")%>

				<form action="login">
					<label for="inputEmail" class="sr-only">name</label> <input
						type="text" id="username" class="form-control" name="username"
						placeholder="Enter your name" required autofocus> <br>
					<button class="btn btn-lg btn-primary btn-block" type="submit">Go!</button>
				</form>


				<div class="mastfoot">
					<div class="inner">
						<p>
							Killer Game From <a href="https://twitter.com/mdo">@DongKai</a>.
						</p>
					</div>
				</div>

			</div>

		</div>

	</div>

	<script src="js/jquery-1.12.0.min.js"></script>
</body>
</html>