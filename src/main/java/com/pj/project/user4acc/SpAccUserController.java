package com.pj.project.user4acc;

import cn.dev33.satoken.stp.StpUtil;
import com.pj.project.user.SpUser;
import com.pj.project.user.SpUserUtil;
import com.pj.project4sp.role4permission.SpRolePermissionService;
import com.pj.project4sp.spcfg.SpCfgUtil;
import com.pj.utils.sg.AjaxJson;
import com.pj.utils.sg.IpCheckUtil;
import com.pj.utils.sg.NbUtil;
import com.pj.utils.so.SoMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * User账号相关的接口
 * 
 * @author kong
 *
 */
@RestController
	@RequestMapping("/AccUser/")
public class SpAccUserController {

	//user 登录service
	@Autowired
	SpAccUserService spAccUserService;
	
	@Autowired
	SpRolePermissionService spRolePermissionService;
	
	/** 账号、密码登录  */
	@RequestMapping(value = "doLogin")
	AjaxJson doLogin(String key, String password) {
		IpCheckUtil.checkResToNow("user-login", 1);
		// 1、验证参数 
		if(NbUtil.hasNull(key, password)) {
			return AjaxJson.getError("请提供key与password参数");
		}
		return spAccUserService.doLogin(key, password);
	}
	
	/** 退出登录  */
	@RequestMapping("doExit")
	AjaxJson doExit() {
		StpUtil.logout();
		return AjaxJson.getSuccess();
	}
	
	/** 获取会话信息 
	 * @throws InterruptedException */
	@RequestMapping("getLoginInfo")
	AjaxJson getLoginInfo() {
		
		// 当前user
		SpUser user = SpUserUtil.getCurrUser();
		
		// 组织参数 (user信息，权限信息，配置信息)
		SoMap map = new SoMap();
		map.set("user", user);
		map.set("perList", spRolePermissionService.getPcodeByRid(user.getRoleId()));
		map.set("appCfg", SpCfgUtil.getAppCfg());	
		return AjaxJson.getSuccessData(map); 
	}
	
}
