package tutorial01.chat.onetoone;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatO2OWebSocketHandler extends TextWebSocketHandler {
	
	class userInfo {
		String Email;
		String SessionId;
	}
	
	private Map<String, WebSocketSession> user = new ConcurrentHashMap<String, WebSocketSession>();
	//private List<userInfo> userInfo = new List<ChatO2OWebSocketHandler.userInfo>();
	//채팅 메시지를 연결된 전체 클라에 전달할 때 사용 -> 이걸 1:1로 할수 있지 않으까?

	@Override
	public void afterConnectionEstablished(
			WebSocketSession session) throws Exception {
		
		super.afterConnectionEstablished(session);
		log(session.getId() + " 연결 됨");
		
		user.put(session.getId(), session);
	}

	@Override
	public void afterConnectionClosed(
			WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		log(session.getId() + " 연결 종료됨");
		user.remove(session.getId());
	}

	@Override
	protected void handleTextMessage(
			WebSocketSession session, TextMessage message) throws Exception {
		String payloadMessage = (String) message.getPayload();
		log(session.getId() + "로부터 메시지 수신: " + payloadMessage);//클라이언트가 전송한 메시지를 
/*		for (WebSocketSession s : user.values()) {	//user 맵에 보관한 전체 WebSocketSession에 다시 전달.
			s.sendMessage(message);
			log(s.getId() + "에 메시지 발송: " + payloadMessage);
		}*/
		for (WebSocketSession s : user.values()) {	//user 맵에 보관한 전체 WebSocketSession에 다시 전달.
			
			if(s.getId() == "")
				s.sendMessage(message);
			log(s.getId() + "에 메시지 발송: " + payloadMessage);
		}
		session.sendMessage(message);
		log(session.getId() + "에 메시지 발송: " + payloadMessage);
	}//클라는 메시지를 수신하면 채팅 영역에 보여주도록 구현할 예정. 특정 클라가 채팅 메시지를 서버에 보내면
	//전체 클라는 다시 그 메시지를 받아서 화면에 뿌려줌.

	@Override
	public void handleTransportError(
			WebSocketSession session, Throwable exception) throws Exception {
		log(session.getId() + " 익셉션 발생: " + exception.getMessage());
	}

	private void log(String logmsg) {
		System.out.println(new Date() + " : " + logmsg);
	}
}