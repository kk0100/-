package com.pj.project.user4login;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.pj.project4sp.SP;
import com.pj.utils.sg.AjaxJson;
import com.pj.utils.so.SoMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/SpUserLogin/")
public class SpUserLoginController {
    /** 底层 Mapper 对象 */
    @Autowired
    SpUserLoginMapper sysLoginLogMapper;

    /** 删 */
    @RequestMapping("delete")
    @SaCheckPermission(SpUserLogin.PERMISSION_CODE)
    public AjaxJson delete(Long id){
        int line = sysLoginLogMapper.delete(id);
        return AjaxJson.getByLine(line);
    }

    /** 删 - 根据id列表 */
    @RequestMapping("deleteByIds")
    @SaCheckPermission(SpUserLogin.PERMISSION_CODE)
    public AjaxJson deleteByIds(){
        List<Long> ids = SoMap.getRequestSoMap().getListByComma("ids", long.class);
        int line = SP.publicMapper.deleteByIds(SpUserLogin.TABLE_NAME, ids);
        return AjaxJson.getByLine(line);
    }

    /** 查 - 根据id */
    @RequestMapping("getById")
    @SaCheckPermission(SpUserLogin.PERMISSION_CODE)
    public AjaxJson getById(Long id){
        SpUserLogin s = sysLoginLogMapper.getById(id);
        return AjaxJson.getSuccessData(s);
    }

    /** 查集合 - 根据条件（参数为空时代表忽略指定条件） */
    @RequestMapping("getList")
    @SaCheckPermission(SpUserLogin.PERMISSION_CODE)
    public AjaxJson getList() {
        //返回当前request请求的的所有参数
        SoMap so = SoMap.getRequestSoMap();
        List<SpUserLogin> list = sysLoginLogMapper.getList(so.startPage());
        return AjaxJson.getPageData(so.getDataCount(), list);
    }

}
