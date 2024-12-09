package com.pj.project.msgcfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息配置相关
 */
@RestController
@RequestMapping("/MsgCfg/")
public class MsgCfgController {

    @Autowired
    private MsgCfgService msgCfgService;


}
