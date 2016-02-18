<%@page import="org.dongkai.game.killer.core.Player"%>
<%
	String path = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + request.getContextPath() + "/";
%>
<div class="masthead clearfix">
	<div class="inner">
		<h3 class="masthead-brand">
			<a href="<%=path%>">Killer</a>
		</h3>
		<nav>
			<ul class="nav masthead-nav">
				<!-- 
				<li class="active"><a href="#">Home</a></li>
				 -->
				<%
					Player player = (Player) session.getAttribute("player");
					if (player != null) {
						String name = "<li><a>" + player.getName() + "</a></li>";
				%>
				<%=name%>
				<%="<li><a href='ready.jsp'>Play</a></li>"%>
				<%="<li><a href='signout'>Sign out</a></li>"%>
				<%
					} else {
				%>
				<%="<li><a>Visitor</a></li>"%>
				<%
					}
				%>
			</ul>
		</nav>
	</div>
</div>