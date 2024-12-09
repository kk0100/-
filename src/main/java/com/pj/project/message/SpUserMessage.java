package com.pj.project.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户 消息 关联表
 * 用于操作消息已读未读
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpUserMessage {

    private Long userId;

    private Long msgId;

    private boolean isRead;

}
