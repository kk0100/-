package com.pj.project.user4password;

import com.pj.current.config.SystemObject;
import com.pj.project4sp.SP;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户表 密码相关 
 * @author kong
 *
 */
@Service
public class SpUserPasswordService {

	
	// REQUIRED=如果调用方有事务  就继续使用调用方的事务 
	/** 修改一个admin的密码为  */
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)	
	public int updatePassword(long userId, String password) {
		// 更改密码 
		SP.publicMapper.updateColumnById("sp_user", "password", SystemObject.getPasswordMd5(userId, password), userId);
		if(SystemObject.config.getIsPw()) {
			// 明文密码 
			SP.publicMapper.updateColumnById("sp_user", "pw", password, userId);
			return 2;
		}
		return 1;
	}
	
	
}
