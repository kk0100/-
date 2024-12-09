package com.pj.project.message;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sp_message" ,autoResultMap = true)
/**
 * message 实体类
 * 用于封装传过来的消息数据
 */
public class Message implements Serializable {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 消息内容 json 格式存储
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private String content;

    /**
     * 创建时间
     */
    private Date createdTime;



}
