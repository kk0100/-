package com.pj.project.message;

import com.pj.project4sp.SP;
import com.pj.utils.sg.AjaxJson;
import com.pj.utils.sg.ResultUserRead;
import com.pj.utils.so.SoMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pj.project.messagePush.messagePushService;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/message/")
public class SpMessageController {

    @Autowired
    private SpMessageService spMessageService;

    @Autowired
    private SpUserMessageService spUserMessageService;

    @Autowired
    private messagePushService messagePushService;


    @PostMapping("addAndPushMessage")
    /**
     * @RequestBody作用
     * 获取传过来的json数据类型的message，
     * {"content":{数据}}
     */
    public AjaxJson addAndPushMessage(@RequestBody SpMessage spMessage) throws Exception {

        //添加到数据库
        spMessageService.insertMessage(spMessage);
        //推送消息
        messagePushService.messagePush();
        return AjaxJson.getSuccess("推送成功,请在app上查看");
    }


    /**
     * 用户已读未读
     *
     * @param userId
     * @param messageId
     * @return
     */
    @RequestMapping("userIsRead")
    public AjaxJson userIsRead(Long userId, Long messageId) {
        spUserMessageService.userIsRead(userId, messageId);
        return AjaxJson.getSuccess("用户标记为已读");
    }

    /**
     * 获取所有消息列表
     *
     * @return
     */
    @RequestMapping("getMessageList")
    public AjaxJson getMessageList() {
        SoMap so = SoMap.getRequestSoMap();
        List<String> warnTypeCodes = so.getListByComma("warnTypeCodes", String.class);
        List<String> sensorTypeCodes = so.getListByComma("sensorTypeCodes", String.class);
        String sTime = so.getString("sTime");
        String eTime = so.getString("eTime");
        List<Message> spMessageList = spMessageService.getMessageList(so.startPage(),warnTypeCodes,sensorTypeCodes,sTime,eTime);
        return AjaxJson.getPageData(so.getDataCount(), spMessageList);
    }








    /**
     * 获取所有已读用户信息
     * 根据消息id
     *
     * @param messageId
     * @return
     */
    @RequestMapping("getUserIsReadListByMsgId")
    public AjaxJson getUserIsReadByMsgIdList(Long messageId) {
        List<ResultUserRead> resultUserReads = spUserMessageService.getUserIsReadListByMsgId(messageId);
        return AjaxJson.getSuccessData(resultUserReads);

    }

    /**
     * 根据id批量删除
     */
    @RequestMapping("deleteByIds")
    public AjaxJson deleteByIds() {
        List<Long> ids = SoMap.getRequestSoMap().getListByComma("ids", long.class);
        int line = SP.publicMapper.deleteByIds("sp_message", ids);
//        int line = spMessageService.deleteByIds(ids);
        return AjaxJson.getByLine(line);
    }


}
