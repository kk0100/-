package com.pj.project.user4acc;


import cn.dev33.satoken.spring.SpringMVCUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.pj.current.config.SystemObject;
import com.pj.project.user.SpUser;
import com.pj.project.user.SpUserMapper;
import com.pj.project.user4login.SpUserLogin;
import com.pj.project.user4login.SpUserLoginMapper;
import com.pj.project4sp.role4permission.SpRolePermissionService;
import com.pj.project4sp.spcfg.SpCfgUtil;
import com.pj.utils.sg.AjaxJson;
import com.pj.utils.sg.IpUtil;
import com.pj.utils.sg.NbUtil;
import com.pj.utils.so.SoMap;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * service：user账号相关
 * @author kong
 *
 */
@Service
public class SpAccUserService {

	@Autowired
	SpUserMapper spUserMapper;
	
	@Autowired
	SpRolePermissionService spRolePermissionService;

	@Autowired
	SpUserLoginMapper sysLoginLogMapper;
	
	/**
	  * 登录 
	 * @param key 账号 (ID / 名称 / 手机号)
	 * @param password 密码 
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public AjaxJson doLogin(String key, String password) {
		
		// 0、判断 way (1=ID, 2=昵称，3=手机号  )
		/**
		 *  way = 2 就是表示默认登录输入的是名称 例如张三 李四
		 *  NbUtil.isNumber(key) == true 表示 根据传入的参数key判断 是否是数字
		 *  	如果是数字就将 way = 1 也就是使用id登录 例如10001 10002
		 *  if(key.length() == 11) 在判断 数字的长度如果等于11位 就表示是手机号登录
		 *  	way = 3
		 */
    	int way = 2;	
    	if(NbUtil.isNumber(key) == true){
    		way = 1;
    		if(key.length() == 11){
    			way = 3;
    		}
    	}

		/**
		 * 这里就是 根据判断的way的值 来选择查询方式 where id(name ,phone) = key
		 *
		 */

		// 2、获取user
        SpUser user = null;
        if(way == 1) {
        	user = spUserMapper.getById(Long.parseLong(key));
        }
        if(way == 2) {
        	user = spUserMapper.getByName(key);
        }
        if(way == 3) {
        	user = spUserMapper.getByPhone(key);
        }
        

        // 3、开始验证
        if(user == null){
        	return AjaxJson.getError("无此账号");	
        }
        if(NbUtil.isNull(user.getPassword2())) {
        	return AjaxJson.getError("此账号尚未设置密码，无法登陆");
        }
        String md5Password = SystemObject.getPasswordMd5(user.getId(), password);
        if(user.getPassword2().equals(md5Password) == false){
        	return AjaxJson.getError("密码错误");	
        }
        
        // 4、是否禁用
        if(user.getStatus() == 2) {
        	return AjaxJson.getError("此账号已被禁用，如有疑问，请联系管理员");	
        }

        // =========== 至此, 已登录成功 ============ 
        StpUtil.login(user.getId());
        String tokenValue = StpUtil.getTokenValue();
        successLogin(user, tokenValue);
        
        // 组织返回参数  
		SoMap map = new SoMap();
		map.put("user", user);
		map.put("appCfg", SpCfgUtil.getAppCfg());
		map.put("perList", spRolePermissionService.getPcodeByRid(user.getRoleId()));
		map.put("tokenInfo", StpUtil.getTokenInfo());
		return AjaxJson.getSuccessData(map);	
	}
	
	/**
	 * 指定id的账号成功登录一次 （修改最后登录时间等数据 ）
	 * @param s
	 * @return
	 */
	void successLogin(SpUser s, String tokenValue){
		HttpServletRequest request = SpringMVCUtil.getRequest();
		// 获取请求头包含的信息  例如 登录地点 客户端标识
		UserAgent ua = UserAgent.parseUserAgentString(request.getHeader("user-agent"));
		// 获取登录IP
		String loginIp = IpUtil.getIP(request);
		
		// 1、修改 user表 最后登录日志  根据id修改登录的次数 和登录IP
		int line = spUserMapper.updateLoginLog(s.getId(), loginIp);
		if(line > 0) {
	        s.setLoginIp(loginIp);
	        s.setLoginTime(new Date());
	        s.setLoginCount(s.getLoginCount() + 1);
		}
		
		// 2、在管理员登录日志表增加记录 
		SpUserLogin al = new SpUserLogin();	// 声明对象
		al.setAccId(s.getId());		// 管理员id 
		al.setAccToken(tokenValue);	// 本次登录Token 
		al.setLoginIp(loginIp);		// 登陆IP 
		al.setAddress(IpUtil.getAddres(loginIp));		// 客户端所在地址 
		al.setDevice(ua.getBrowser().getName());	// 客户端标识 
		al.setSystem(ua.getOperatingSystem().getName()); 	// 客户端系统 
		sysLoginLogMapper.add(al);
	}
	
}
