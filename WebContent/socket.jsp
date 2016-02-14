<%@page import="org.dongkai.game.killer.core.Player"%>
<%@page import="org.dongkai.game.killer.core.Global"%>

<%
	Player player = (Player) session.getAttribute(Global.PLAYER);
	String name = player != null ? player.getName() : "visit";
	String url = "ws://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
			+ "/ready";
%>
<input id="username" value='<%=name%>' type="hidden" />
<input id="url" value='<%=url%>' type="hidden" />
<div id="messages"></div>
<script type="text/javascript">
	var webSocket = new WebSocket($('#url').val());

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
		tbodyContent(event.data);
	}

	function onOpen(event) {
		tbodyContent(event.data);
	}

	function onError(event) {
		alert(event.data);
	}

	function start() {
		webSocket.send($("#username").val());
		$("#ready-game").remove();
		return false;
	}

	function tbodyContent(data) {
		if (data == null || data == undefined) {
			return;
		}
		var role;
		var username = $("#username").val();
		var json = JSON.parse(data);
		var isReady = json['isReady'];
		var players = JSON.parse(json['players']);
		var result = "<tr><th>USER</th><th>READY</th></tr>";
		var ok = '<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>';
		var no = '<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>';
		for (var i = 0; i < players.length; i++) {
			var player = JSON.parse(players[i]);
			var name = player.name;
			if (username == name) {
				role = player.role
			}
			var isReady = no;
			if (player.ready == true) {
				isReady = ok;
			}
			result += "<tr><td>" + name + "</td>" + "<td>" + isReady
					+ "</td></tr>";
		}
		if (isReady) {
			showRole(role);
		} else {
			$("#table-body").html(result);
		}
	}

	function showRole(role) {
		var actor = '<img src="./img/folk.png">';
		if (role == 'POLICE') {
			actor = '<img src="./img/soldier.png">';
		} else if (role == 'KILLER') {
			actor = '<img src="./img/killer.png">';
		} else if (role == 'SPEAKER') {
			actor = '<img src="./img/tangseng.png">';
		}
		$("#game-role").html(actor);
	}
</script>