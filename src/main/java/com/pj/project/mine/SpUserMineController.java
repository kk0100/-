package com.pj.project.mine;

import com.pj.utils.sg.AjaxJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("userMine")
public class SpUserMineController {

    @Autowired
    SpUserMineService spUserMineService;

    @RequestMapping("getMineByUserId")
    public AjaxJson getMineByUserId(Long userId){
        return AjaxJson.getSuccessData(spUserMineService.getMineByUserId(userId));
    }

    @RequestMapping("updateMineByUserId")
    public AjaxJson updateMineByUserId(@RequestBody SpUserMine spUserMine){
        spUserMineService.updateMineByUserId(spUserMine);
        return AjaxJson.getSuccess("修改成功！");
    }

}
