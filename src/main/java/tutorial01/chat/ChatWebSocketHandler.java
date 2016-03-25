package tutorial01.chat;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatWebSocketHandler extends TextWebSocketHandler {
	
	// 현재 접속자 세션 리스트
	private Map<String, WebSocketSession> users = new ConcurrentHashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		super.afterConnectionEstablished(session);
		log(session.getId() + " 연결 됨");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

		super.afterConnectionClosed(session, status);
		log(session.getId() + " 연결 종료됨");
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		String payloadMessage = (String) message.getPayload();
		String[] msgArr = payloadMessage.split("/");
		
		if(msgArr[0].equals("init")) {
			System.out.println("init");
			users.put(msgArr[1], session);
		} else if (msgArr[0].equals("chat")) {
			System.out.println("chat");
			WebSocketSession s = users.get(msgArr[1]);
			TextMessage msg = new TextMessage(msgArr[2]);
			s.sendMessage(msg);
			session.sendMessage(msg);
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		log(session.getId() + " 익셉션 발생: " + exception.getMessage());
	}

	private void log(String logmsg) {
		System.out.println(new Date() + " : " + logmsg);
	}
}