package com.pj.project.user;

import com.pj.project.mine.SpUserMineService;
import com.pj.project4sp.SP;
import com.pj.project.user4password.SpUserPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service: admin管理员
 * @author kong
 *
 */
@Service
public class SpUserService {


	//user 表mapper
	@Autowired
    SpUserMapper spUserMapper;

	// 管理员修改用户密码service
	@Autowired
	SpUserPasswordService spUserPasswordService;

	@Autowired
	SpUserMineService spUserMineService;
	
	/**
	 * 管理员添加一个普通用户  (添加用户）
	 * @param user
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)	
	public long add(SpUser user) {
		// 检查姓名是否合法
		SpUserUtil.checkUser(user);

		// 开始添加
		spUserMapper.add(user);
		// 获取主键
		long userId = SP.publicMapper.getPrimarykey();

		//判断煤矿id是否为空
		if(user.getMineIds() != null){
			//获取煤矿id
			List<Long> mineIdsList = user.getMineIds();

			// 使用增强型for循环遍历列表
			for (Long mineId : mineIdsList) {
				System.out.println("Mine ID: " + mineId);
				spUserMineService.insertByMineId(userId, mineId);
				// 在这里执行你需要的操作
			}
		}
		// 更改密码（md5与明文）
		spUserPasswordService.updatePassword(userId, user.getPassword2());

		// 返回主键 
		return userId;
	}


	public List<SpUser> findByUser() {
		return spUserMapper.findByUser();
	}
}
