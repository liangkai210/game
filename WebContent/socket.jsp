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
		username = $("#username").val();
		var msg = '{"type" : "ready","name" : "' + username + '"}';
		webSocket.send(msg);
		return false;
	}

	function tbodyContent(data) {
		if (data == null || data == undefined) {
			return;
		}
		var role;
		var isAllNotReady = true;
		var username = $("#username").val();
		var json = JSON.parse(data);
		var isAllReady = json['isReady'];
		var isCurrentPlayerReady = false;
		var players = JSON.parse(json['players']);
		var result = "<tr><th>USER</th><th>READY</th></tr>";
		var ok = '<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>';
		var no = '<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>';
		for (var i = 0; i < players.length; i++) {
			var player = JSON.parse(players[i]);
			var name = player.name;
			if (username == name) {
				role = player.role
				isCurrentPlayerReady = player.ready;
			}
			var this_ready = no;
			if (player.ready == true) {
				this_ready = ok;
				isAllNotReady = false;
			}
			result += "<tr><td>" + name + "</td>" + "<td>" + this_ready
					+ "</td></tr>";
		}
		showRole(role);
		$("#table-body").html(result);
		if (isCurrentPlayerReady) {
			$("#ready-game").hide();
		} else {
			$("#ready-game").show();
		}
		if (isAllNotReady) {
			showRole("knife");
		}
		addNextRound(isAllReady, role);
	}

	function addNextRound(isAllReady, role) {
		if (isAllReady && role == "SPEAKER") {
			$('#next-round').show();
		} else {
			$('#next-round').hide();
		}
	}

	function nextround() {
		var msg = '{"type" : "next-round","name" : "' + username + '"}';
		webSocket.send(msg);
	}

	function showRole(role) {
		var actor = '<img src="./img/knife.gif">';
		if (role == 'POLICE') {
			actor = '<img src="./img/soldier.png">';
		} else if (role == 'KILLER') {
			actor = '<img src="./img/killer.png">';
		} else if (role == 'SPEAKER') {
			actor = '<img src="./img/tangseng.png">';
		} else if (role == "FOLK") {
			actor = '<img src="./img/folk.png">';
		}
		$("#game-role").html(actor);
	}
</script>