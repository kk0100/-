package com.pj.project.messagePush;

import com.pj.current.websocket.WebSocketServer;
import com.pj.project.message.SpMessageService;
import com.pj.project.message.SpUserMessageService;
import com.pj.project.mine.SpMineService;
import com.pj.project.user.SpUser;
import com.pj.project.user.SpUserService;
import com.pj.project4sp.spcfg.SpCfgService;
import com.pj.utils.sg.AjaxJson;
import com.pj.utils.sg.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.pj.project4sp.SP.objectMapper;

@Component
public class messagePushService {

    @Autowired
    WebSocketServer webSocketServer;

    @Autowired
    SpMessageService spMessageService;

    @Autowired
    SpUserService spUserService;

    @Autowired
    SpUserMessageService spUserMessageService;

    @Autowired
    SpCfgService spCfgService;

    @Autowired
    SpMineService spMineService;

    /**
     * 消息推送
     * 1.消息发送给谁--> user
     * 2.消息内容 ---> message
     * 2.
     */

    public AjaxJson messagePush() throws Exception {

        //消息内容map
        Map<String, Object> contentMap = spMessageService.getMessageContent();
        String warnType = (String) contentMap.get("warn_type");
        //煤矿id
        long mine_id = Long.parseLong(String.valueOf(contentMap.get("mine_id")));
        System.out.println("煤矿id是:"+mine_id);

        //获取当前添加消息的id
        long msgId = (long) contentMap.get("msg_id");
        System.out.println("消息的id是:" + msgId);

        //消息内容map-->json(String类型)
        String messageContent = objectMapper.writeValueAsString(contentMap);
        System.out.println("这是消息内容String:" + messageContent);

        //获取系统配置warn_types,json--->map
        String app_cfg = spCfgService.getCfgValue("app_cfg");
        //获取配置中的warn_type_code
        Map<String, Object> warnMap = objectMapper.readValue(app_cfg, Map.class);
        //遍历系统配置的warn_type_code集合{"warnTypes":["报警","断电报警","馈电异常","传感器断线"]}
        List<String> warnTypeList = (List<String>) warnMap.get("warnTypes");

        if(warnTypeList.contains(warnType)){
            System.out.println("此消息需推送"+messageContent);
            //系统配置中的warn_type_code包含了warnTypeCode，则当前消息需要推送消息
            //根据煤矿id获取用户列表
            List<SpUser> userList = spMineService.selectUserByMineId(mine_id);

            // 获取WebSocketServer类的map对象
            Map<String, WebSocketServer> map = WebSocketServer.getWebSocketMap();
            //遍历用户列表
            for (SpUser item : userList) {
                String phone = item.getPhone();
                Long userId = item.getId();
                WebSocketServer SocketServer = map.get(phone);
                if (SocketServer != null) {
                    // 发送消息
                    SocketServer.sendMessage(messageContent);
                    System.out.println("用户:"+phone+"收到通知");
                    //当消息发送时 user_message表插入一条数据 表示谁收到信息 状态是未读
                    spUserMessageService.insertIsRead(userId, msgId);
                }else {
                    System.out.println("用户"+phone+"不在线");
                }
            }
        }else {
            System.out.println("此消息不需推送");
        }
        //封装返回值数据
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setId(msgId);
        resultMessage.setData(messageContent);

        return AjaxJson.getSuccess("消息发送成功",resultMessage);
    }

}
