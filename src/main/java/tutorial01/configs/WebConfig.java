package tutorial01.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import tutorial01.chat.ChatWebSocketHandler;
import tutorial01.echo.EchoWebSocketHandler;

@Configuration
@EnableWebMvc
@EnableWebSocket //webSocketHandler를 Spring@MVC에 통합하기 위해
public class WebConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        // SocketJS 지원 url을 /socketjs/echo에 연결합니다.
        registry.addHandler(echoHandler(), "/socketjs/echo").withSockJS();	//define    EndPoint

        // SocketJS 지원 url을 /socketjs/chat에 연결합니다.
        registry.addHandler(chatHandler(), "/socketjs/chat").withSockJS();
    }

    @Bean
    public WebSocketHandler echoHandler() {
        return new EchoWebSocketHandler();
    }
    
    @Bean
    public WebSocketHandler chatHandler() {
        return new ChatWebSocketHandler();
    }
 
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    @Bean  
    public UrlBasedViewResolver setupViewResolver() {  
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();  
        resolver.setPrefix("/jsp/");  
        resolver.setSuffix(".jsp");  
        resolver.setViewClass(JstlView.class);  
        return resolver;  
    }
}
