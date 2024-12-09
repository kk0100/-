package com.pj.project.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pj.current.websocket.WebSocketServer;
import com.pj.project.user.SpUser;
import com.pj.project.user.SpUserService;
import com.pj.utils.sg.AjaxJson;
import com.pj.utils.sg.ResultMessage;
import com.pj.utils.sg.ResultUserRead;
import com.pj.utils.so.SoMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.pj.project4sp.SP.objectMapper;

@Service
@Slf4j
public class SpMessageService {

    @Autowired
    SpUserService spUserService;

    @Autowired
    SpMessageMapper spMessageMapper;

    @Autowired
    WebSocketServer webSocketServer;

   @Autowired
   SpUserMessageService spUserMessageService;


    private long messageId;

    private String messageContent;

    private Map<String, Object> contentMap;

    /**
     * 添加消息到数据库并且推送消息给用户
     * @param spMessage
     */
    public AjaxJson addAndPushMessage(SpMessage spMessage)  {

        // 判断为空
        if(spMessage == null){
            return AjaxJson.getError("数据不能空");
        }

        // 添加到数据库
        spMessageMapper.insert(spMessage);
        // 数据添加后 获取id 赋值给成员变量 作为当前类全局使用
        this.messageId=spMessage.getId();

//        // 根据最新的id 查询消息
//        SpMessage message = spMessageMapper.selectById(messageId);


        // 获取消息内容map
        Map<String, Object> contentMap = spMessage.getContent();
        //添加messageId到map  "{\"msg_id:"+messageId+","+"\""+"content:"+messageContent+"\"}
        contentMap.put("msg_id",messageId);
        //获取异常类型
        String warnType = contentMap.get("warn_type").toString();
        System.out.println("异常类型是:"+warnType);

        //
        try{
            //map-->json
            String messageContent = objectMapper.writeValueAsString(contentMap);
            System.out.println("这是消息内容String:"+messageContent);



        }catch (JsonProcessingException e){
            return AjaxJson.getError("JSON数据转换失败");
        }




        //获取所有用户信息
        List<SpUser> userList = spUserService.findByUser();

        //获取 private static Map<String, WebSocketServer> webSocketMap  = new ConcurrentHashMap<>();
       try{
//           // 获取WebSocketServer类的map对象
           Map<String, WebSocketServer> map = WebSocketServer.getWebSocketMap();

           //遍历用户列表
           for(SpUser item : userList){
               String phone = item.getPhone();
               Long userId = item.getId();
               WebSocketServer SocketServer = map.get(phone);
               if(SocketServer != null){

//                   String jsonString = "{\"id\":" + spMessage.getId() + ",\"content\":\"" + spMessage.getContent().replace("\"", "\\\"") + "\"}";
                   //实现json内部加新的键值对。
                   //发送消息"{\"id:"+spMessage.getId()+","+"\""+"content:"+messageContent+"\"}
                   // 将JSON字符串转换为JsonNode对象
//                   ObjectNode jsonNode =  (ObjectNode)objectMapper.readTree(messageContent);
//                   // 添加新的id
//                   String msg_id = "msg_id";
//                   jsonNode.put(msg_id,messageId);
//                   // 将修改后的JsonNode转换回JSON字符串
//                   String jsonString = objectMapper.writeValueAsString(jsonNode);
//                   System.out.println(jsonString);


                   // 发送消息
                   SocketServer.sendMessage(messageContent);


                   //当消息发送时 user_message表插入一条数据 表示谁收到信息 状态是未读
                   spUserMessageService.insertIsRead(userId,messageId);

               }else {
                   System.out.println("用户"+phone+"不在线");
               }

           }
       }catch (Exception e){
           e.printStackTrace();
       }

       //封装返回值数据
        ResultMessage resultMessage = new ResultMessage();
       resultMessage.setId(messageId);
       resultMessage.setData(messageContent);

        return AjaxJson.getSuccess("消息发送成功",resultMessage);
    }

    /**
     * 添加消息返回消息id
     */
    public void insertMessage(SpMessage spMessage){
        // 判断为空
        if(spMessage == null){
            log.error("消息为空");
            throw new IllegalArgumentException("消息为空");
        }
        // 添加到数据库
        spMessageMapper.insert(spMessage);

        // 数据添加后 获取id 赋值给成员变量 作为当前类全局使用
        this.messageId=spMessage.getId();

        // 获取消息内容map
        Map<String, Object> contentMap = spMessage.getContent();
        //添加messageId到map  "{\"msg_id:"+messageId+","+"\""+"content:"+messageContent+"\"}
        contentMap.put("msg_id",messageId);
        this.contentMap = contentMap;

    }

    /**
     * 获取消息内容Map<String, Object> contentMap
     */
    public Map<String, Object> getMessageContent() {

        return contentMap;
    }



    /**
     * 获取所有信息列表
     * @return
     */
    public List<Message> getMessageList(SoMap so,List<String> warnTypeCodes,List<String> sensorTypeCodes,String sTime,String eTime) {
        return spMessageMapper.getMessageList(so,warnTypeCodes, sensorTypeCodes, sTime,eTime);
    }

    /**
     * 根据信息id获取用户信息
     * @param messageId
     * @return
     */
    public List<ResultUserRead> getUserIsReadListByMsgId(Long messageId) {
        return spUserMessageService.getUserIsReadListByMsgId(messageId);
    }

    /**
     * 删除 批量删除信息
     * @param ids
     * @return
     */
    public int deleteByIds(List<Long> ids) {
        return spMessageMapper.deleteByIds(ids);
    }
}
