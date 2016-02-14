
<%@page import="org.dongkai.game.killer.Player"%>
<div class="masthead clearfix">
	<div class="inner">
		<h3 class="masthead-brand">Killer</h3>
		<nav>
			<ul class="nav masthead-nav">
				<li class="active"><a href="#">Home</a></li>
				<%
					Player player = (Player) session.getAttribute("player");
					if (player != null) {
				%>
				<%="<li><a>" + player.getName() + "</a></li>"%>
				<%="<li><a>Sign out</a></li>"%>
				<%
					}
				%>
			</ul>
		</nav>
	</div>
</div>


