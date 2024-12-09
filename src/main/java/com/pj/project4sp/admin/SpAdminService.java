package com.pj.project4sp.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pj.project4sp.SP;
import com.pj.project4sp.admin4password.SpAdminPasswordService;

import cn.dev33.satoken.stp.StpUtil;

/**
 * Service: admin管理员
 * @author kong
 *
 */
@Service
public class SpAdminService {


	//admin 表mapper
	@Autowired
	SpAdminMapper spAdminMapper;

	// 管理员修改用户密码service
	@Autowired
	SpAdminPasswordService spAdminPasswordService;
	
	/**
	 * 管理员添加一个管理员  (添加用户）
	 * @param admin
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)	
	public long add(SpAdmin admin) {
		// 检查姓名是否合法
		SpAdminUtil.checkAdmin(admin);
		
		// 创建人，为当前账号  
		admin.setCreateByAid(StpUtil.getLoginIdAsLong());	
		// 开始添加
		spAdminMapper.add(admin);	
		// 获取主键
		long id = SP.publicMapper.getPrimarykey();
		// 更改密码（md5与明文）
		spAdminPasswordService.updatePassword(id, admin.getPassword2());	
		
		// 返回主键 
		return id;
	}
	
}
