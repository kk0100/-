package com.pj.current.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 扫描添加有ServerEndPoint注解的类
 */
@Configuration
public class WebsocketConfig {

    @Bean
    //注入ServerEndpointExporter,自动注册使用@ServerEndPoint注解的类 就是扫描带了@ServerEndpoint注解的类
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
