<%@page import="org.dongkai.game.killer.core.Player"%>
<%@page import="org.dongkai.game.killer.core.Global"%>

<%
	Player player = (Player) session.getAttribute(Global.PLAYER);
	String name = player != null ? player.getName() : "visit";
%>
<div id="messages"></div>
<script type="text/javascript">
	var webSocket = new WebSocket('ws://localhost:8080/game/ready');

	webSocket.onerror = function(event) {
		onError(event)
	};

	webSocket.onopen = function(event) {
		onOpen(event)
	};

	webSocket.onmessage = function(event) {
		onMessage(event)
	};

	function onMessage(event) {
		document.getElementById('messages').innerHTML += '<br />' + event.data;
	}

	function onOpen(event) {
		document.getElementById('messages').innerHTML = 'Connection established';
	}

	function onError(event) {
		alert(event.data);
	}

	function start() {
		webSocket.send('<%=name%>');
		return false;
	}
</script>