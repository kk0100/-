package com.pj.current.websocket;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("information")
public class WebsocketController {

    @Autowired
    WebSocketServer webSocketServer;

    @GetMapping("massMessaging")
    public String massMessaging( String message){
        webSocketServer.massMessaging(message);
        return "消息发送成功";
    }

}
