package com.pj.project.message;

import com.pj.project.user.SpUser;
import com.pj.utils.sg.ResultUserRead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实现消息已读未读的功能
 */
@Service
public class SpUserMessageService {

    @Autowired
    SpUserMessageMapper spUserMessageMapper;
    public void insertIsRead(Long userId, Long messageId) {
        spUserMessageMapper.insertIsRead(userId, messageId);
    }

    /**
     * 用户已读消息
     * @param userId
     * @return
     */
    public void userIsRead(Long userId, Long messageId) {
        spUserMessageMapper.userIsRead(userId, messageId);
    }



    /**
     * 根据信息id获取用户已读未读列表
     * @param messageId
     * @return
     */
    public List<ResultUserRead> getUserIsReadListByMsgId(Long messageId) {
        return spUserMessageMapper.getUserIsReadListByMsgId(messageId);
    }
}
