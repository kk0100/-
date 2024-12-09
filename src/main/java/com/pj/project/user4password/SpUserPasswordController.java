package com.pj.project.user4password;

import com.pj.current.config.SystemObject;
import com.pj.project.user.SpUser;
import com.pj.project.user.SpUserUtil;
import com.pj.utils.sg.AjaxJson;
import com.pj.utils.sg.NbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * user表 密码相关
 * 
 * @author shengzhang
 */
@RestController
@RequestMapping("/UserPassword/")
public class SpUserPasswordController {

	@Autowired
	SpUserPasswordService spUserPasswordService;

	/** 指定用户修改自己密码 */
	@RequestMapping("update")
	AjaxJson updatePassword(String oldPwd, String newPwd) {
		// 1、转md5
		SpUser a = SpUserUtil.getCurrUser();
		String oldPwdMd5 = SystemObject.getPasswordMd5(a.getId(), oldPwd);
		
		// 2、验证
		if(NbUtil.isNull(a.getPassword2()) && NbUtil.isNull(oldPwd)) {
			// 如果没有旧密码，则不用取验证 
		} else {
			if(oldPwdMd5.equals(a.getPassword2()) == false) {
				return AjaxJson.getError("旧密码输入错误");
			}
		}
		
		// 3、开始改 
		int line = spUserPasswordService.updatePassword(a.getId(), newPwd);
		return AjaxJson.getByLine(line);
	}
	
}
