package com.pj.project.user4login;
import java.io.Serializable;
import java.util.*;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Model: sp_user_login -- 用户录日志表
 *
 * @author kk
 */
@Data
@Accessors(chain = true)
public class SpUserLogin implements Serializable {

    // ---------- 模块常量 ----------
    /**
     * 序列化版本id
     */
    private static final long serialVersionUID = 1L;
    /**
     * 此模块对应的表名
     */
    public static final String TABLE_NAME = "sp_user_login";
    /**
     * 此模块对应的权限码
     */
    public static final String PERMISSION_CODE = "sp-user-login";


    // ---------- 表中字段 ----------
    /**
     * id号
     */
    private Long id;

    /**
     * 用户账号id
     */
    private Long accId;

    /**
     * 本次登录Token
     */
    private String accToken;

    /**
     * 登陆IP
     */
    private String loginIp;

    /**
     * 登陆地点
     */
    private String address;

    /**
     * 客户端设备标识
     */
    private String device;

    /**
     * 客户端系统标识
     */
    private String system;

    /**
     * 创建时间
     */
    private Date createTime;



    // ---------- 额外字段 ----------
    /**
     * 管理员名称
     */
    private String spUserName;

    /**
     * 管理员头像
     */
    private String spUserAvatar;



}
