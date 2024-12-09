package com.pj.project.mine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SpUserMineService {

    @Autowired
    private SpUserMineMapper spUserMineMapper;

    /**
     * 根据用户id添加 用户煤矿关联表 记录
     * @param userId
     * @param mineId
     */
    public void insertByMineId(long userId, Long mineId) {
        spUserMineMapper.insertByMineId(userId, mineId);
    }


    public List<SpUserMine> getMineByUserId(Long userId) {
        List<SpUserMine> spUserMineList = spUserMineMapper.getMineByUserId(userId);
        return spUserMineList;
    }


    public void updateMineByUserId(SpUserMine spUserMine) {
        //先获取userID
        Long userId = spUserMine.getUserId();
        //根据userId删除对应煤矿记录
        spUserMineMapper.deleteByUserId(userId);
        //获取煤矿列表 遍历List获取煤矿id
        for (Long mineId : spUserMine.getMineIds()) {
            //添加新的关联记录
            spUserMineMapper.insertByMineId(userId, mineId);
        }
    }
}
