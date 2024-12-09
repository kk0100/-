package com.pj.project.user;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.pj.current.satoken.AuthConst;
import com.pj.project4sp.SP;
import com.pj.project.user4password.SpUserPasswordService;
import com.pj.utils.sg.AjaxJson;
import com.pj.utils.so.SoMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller -- 普通用户表(添加用户表信息）
 * @author kong
 */
@RestController
@RequestMapping("/user/")
public class SpUserController {

    @Autowired
    SpUserMapper spUserMapper;

    @Autowired
    SpUserService spUserService;

    @Autowired
    SpUserPasswordService spUserPasswordService;

    /** 增
     * SaCheckPermission权限检查注解 检查当前一登录用户是否拥有执行这个方法的权限
     * 		参数是权限类型  AuthConst.ADMIN_ADD --> 添加用户权限
     *
     * 	AjaxJson 是自定义的返回结果集类
     *
     * */
    @RequestMapping("add")
    @SaCheckPermission(AuthConst.USER_ADD)
    AjaxJson add(@RequestBody SpUser user){
        long id = spUserService.add(user);
        return AjaxJson.getSuccessData(id);
    }

    /** 删 */
    @RequestMapping("delete")
    @SaCheckPermission(AuthConst.USER_LIST)
    AjaxJson delete(long id){
        // 不能自己删除自己
        if(StpUtil.getLoginIdAsLong() == id) {
            return AjaxJson.getError("不能自己删除自己");
        }
        int line = spUserMapper.delete(id);
        return AjaxJson.getByLine(line);
    }

    /** 删 - 根据id列表 */
    @RequestMapping("deleteByIds")
    @SaCheckPermission(AuthConst.USER_LIST)
    AjaxJson deleteByIds(){
        // 不能自己删除自己
        List<Long> ids = SoMap.getRequestSoMap().getListByComma("ids", long.class);
        if(ids.contains(StpUtil.getLoginIdAsLong())) {
            return AjaxJson.getError("不能自己删除自己");
        }
        // 开始删除
        int line = SP.publicMapper.deleteByIds("sp_user", ids);
        return AjaxJson.getByLine(line);
    }

    /** 改  -  name */
    @RequestMapping("update")
    @SaCheckPermission(AuthConst.USER_LIST)
    AjaxJson update(SpUser obj){
        SpUserUtil.checkName(obj.getId(), obj.getName());
        int line = spUserMapper.update(obj);
        return AjaxJson.getByLine(line);
    }

    /** 改密码 */
    @RequestMapping("updatePassword")
    @SaCheckPermission({AuthConst.USER_LIST, AuthConst.DEV})
    AjaxJson updatePassword(long id, String password){
        int line = spUserPasswordService.updatePassword(id, password);
        return AjaxJson.getByLine(line);
    }

    /** 查  */
    @RequestMapping("getById")
    @SaCheckPermission(AuthConst.USER_LIST)
    AjaxJson getById(long id){
        Object data = spUserMapper.getById(id);
        return AjaxJson.getSuccessData(data);
    }

    /** 返回当前 User 信息  */
    @RequestMapping("getByCurr")
    AjaxJson getByCurr() {
        SpUser admin = SpUserUtil.getCurrUser();
        return AjaxJson.getSuccessData(admin);
    }

    /** 查 - 集合 */
    @RequestMapping("getList")
    @SaCheckPermission(AuthConst.USER_LIST)
    AjaxJson getList(){
        SoMap so = SoMap.getRequestSoMap();
        List<SpUser> list = spUserMapper.getList(so.startPage());
        return AjaxJson.getPageData(so.getDataCount(), list);
    }

    /** 当前 User 修改自己信息 */
    @RequestMapping("updateInfo")
    AjaxJson updateInfo(SpUser obj){
        obj.setId(StpUtil.getLoginIdAsLong());
        SpUserUtil.checkName(obj.getId(), obj.getName());
        int line = spUserMapper.update(obj);
        return AjaxJson.getByLine(line);
    }

}