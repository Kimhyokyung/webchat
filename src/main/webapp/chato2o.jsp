<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>채팅고고</title>
<style type="text/css">
#connect-container {
	float: left;
	width: 400px
}

#connect-container div {
	padding: 5px;
}

#msgBox-container {
	float: left;
	margin-left: 15px;
	width: 400px;
}

#msgBox {
	border: 1px solid #CCCCCC;
	border-right-color: #999999;
	border-bottom-color: #999999;
	height: 170px;
	overflow-y: scroll;
	padding: 5px;
	width: 100%;
}

#msgBox p {
	padding: 0;
	margin: 0;
}
</style>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.0.3/sockjs.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>

stomp
<script type="text/javascript">
	var ws = null;
	var url = "http://localhost:8080/tutorial01/socketjs/chato2o";
	var transports = [];

	function setConnected(connected) { //버튼처리임
		document.getElementById('connect').disabled = connected;
		document.getElementById('disconnect').disabled = !connected;
		document.getElementById('send').disabled = !connected;//echo
	}

	function connect() {
		if (!url) {
			alert('Select whether to use W3C WebSocket or SockJS');
			return;
		}

		console.log(url);
		/*   ws = (url.indexOf('socketjs') != -1) ? 
		      new SockJS(url, undefined, {protocols_whitelist: transports}) : new WebSocket(url); */
		ws = new SockJS(url);

		stompClient = Stomp.over(ws);
		console.log('socket생성');

		stompClient.connect({}, function(frame) {
			stompClient.subscribe("/topic/socketjs/chato2o", function(message) {
				console.log("Received : " + message);
			});
		}, function(error) {
			console.log("STOMP protocal error " + error);
		});
		ws.onclose = function(event) {
			setConnected(false);
			log('Info: 연결을 끊었습니다.');
			log(event);
		};

		ws.ws.constructor = function() {
			console.log('Info: 연결되었습니다.');
		}
	}

	function disconnect() {
		if (ws != null) {
			ws.close();
			ws = null;
		}
		setConnected(false);
	}

	function send() { //echo()
		var message = document.getElementById('message').value;
		stompClient.send($('#'))
	}

	/*        function updateUrl(urlPath) {
	           if (urlPath.indexOf('socketjs') != -1) { 
	               url = urlPath;
	               document.getElementById('sockJsTransportSelect').style.visibility = 'visible';
	           }
	           else {
	             if (window.location.protocol == 'http:') {
	                 url = 'ws://' + window.location.host + urlPath;
	             } else {
	                 url = 'wss://' + window.location.host + urlPath;
	             }
	             document.getElementById('sockJsTransportSelect').style.visibility = 'hidden';
	           }
	       } */

	/* function updateTransport(transport) {
	  transports = (transport == 'all') ?  [] : [transport];
	} */

	function log(message) {
		var msgBox = document.getElementById('msgBox');//$('#msgBox');
		console.log('msgBox : ' + msgBox);
		var p = document.createElement('p');
		p.style.wordWrap = 'break-word';
		p.appendChild(document.createTextNode(message));
		msgBox.appendChild(p);
		while (msgBox.childNodes.length > 25) {
			msgBox.removeChild(msgBox.firstChild);
		}
		msgBox.scrollTop = msgBox.scrollHeight;
	}
	function clear() {
		$('#message').html('');
	}
</script>
</head>
<body>


	<!-- sockJS사용할꺼임. -->
	<div>
		<div id="connect-container">
			<!--   <input id="radio1" type="radio" name="group1" onclick="updateUrl('/tutorial01/echo');">
            <label for="radio1">W3C WebSocket</label>
        <br>
        <input id="radio2" type="radio" name="group1" onclick="updateUrl('/tutorial01/socketjs/echo');">
       -->
			<h1>채팅 창입니당</h1>
			<!--         <div id="sockJsTransportSelect">
            <span>SockJS transport:</span>
            <select onchange="updateTransport(this.value)">
              <option value="all">all</option>
              <option value="websocket">websocket</option>
              <option value="xhr-polling">xhr-polling</option>
              <option value="jsonp-polling">jsonp-polling</option>
              <option value="xhr-streaming">xhr-streaming</option>
              <option value="iframe-eventsource">iframe-eventsource</option>
              <option value="iframe-htmlfile">iframe-htmlfile</option>
            </select>
        </div> -->
			<div>

				이름:<input type="text" id="nickname"><br />
				<br />
				<button id="connect" onclick="connect();">Connect</button>
				<button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>
			</div>
			<div>
				<textarea id="message" style="width: 350px">Here is a message!</textarea>
			</div>
			<button id="send" onclick="send();" disabled="disabled">SEND</button>
		</div>
	</div>

	<h2>대화 영역</h2>
	<div id="msgBox-container">
		<div id="msgBox"></div>
	</div>
	</div>
</body>
</html>
