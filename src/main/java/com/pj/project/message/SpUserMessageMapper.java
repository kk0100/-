package com.pj.project.message;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pj.utils.sg.ResultUserRead;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户消息关联表mapper
 */
@Mapper
public interface SpUserMessageMapper extends BaseMapper<SpUserMessage> {
    void insertIsRead(Long userId, Long messageId);

    void userIsRead(Long userId, Long messageId);

    List<ResultUserRead> getUserIsReadListByMsgId(Long messageId);

}
