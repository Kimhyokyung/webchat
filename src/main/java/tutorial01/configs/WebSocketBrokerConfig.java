package tutorial01.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic", "/queue");
	}
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/socketjs/chato2o").withSockJS();
	}
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		// TODO Auto-generated method stub
		
	}
} 