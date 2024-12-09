package com.pj.current.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.pj.project.user.SpUserService;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/information/{phone}")
@Component
@Slf4j
public class WebSocketServer {

    /**
     * 以用户的姓名为key，WebSocket为对象保存起来
     */
    @Getter
    private static Map<String, WebSocketServer> webSocketMap  = new ConcurrentHashMap<>();

    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;


    private Session session;

    private String phone;


    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("phone") String phone) {
        this.session = session;
        this.phone=phone;
        if(webSocketMap.containsKey(phone)){
            webSocketMap.remove(phone);
            webSocketMap.put(phone,this);
            //加入set中
        }else{
            webSocketMap.put(phone,this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }

        log.info("用户连接:"+phone+",当前在线人数为:" + getOnlineCount());

//        try {
//            sendMessage("连接成功");
//        } catch (IOException e) {
//            log.error("用户:"+phone+",网络异常!!!!!!");
//        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(phone)){
            webSocketMap.remove(phone);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出:"+phone+",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("用户消息:"+phone+",报文:"+message);
        //可以群发消息
        //消息保存到数据库、redis
//        if(StringUtils.isNotBlank(message)){
//            try {
//                //解析发送的报文
//                JSONObject jsonObject = JSON.parseObject(message);
//                //追加发送人(防止串改)
//                jsonObject.put("fromUserId",this.phone);
//                String toUserId=jsonObject.getString("toUserId");
//                //传送给对应toUserId用户的websocket
//                if(StringUtils.isNotBlank(toUserId)&&webSocketMap.containsKey(toUserId)){
//                    webSocketMap.get(toUserId).sendMessage(jsonObject.toJSONString());
//                }else{
//                    log.error("请求的userId:"+toUserId+"不在该服务器上");
//                    //否则不在这个服务器上，发送到mysql或者redis
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:"+this.phone+",原因:"+error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */

    public void sendMessage(String message) throws IOException, EncodeException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发消息
     * 从Map.Entry<String, WebSocketServer>获取到所有在链接的用户
     * 遍历key得到WebSocketServer对象
     * 使用WebSocketServer对象向所有在线用户发送信息
     *
     */
    public  void massMessaging(String message) {
        message = "有新消息:" + message;

        //遍历所有的用户
//        for (Map.Entry<String, WebSocketServer> entry : webSocketMap.entrySet()) {
//            // 获取到所有的对应的webSocketServer对象
//            WebSocketServer webSocketServer = entry.getValue();
//            //使用webSocketServer发送消息
//            try {
//                webSocketServer.sendMessage(message);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//        }



    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }


}
