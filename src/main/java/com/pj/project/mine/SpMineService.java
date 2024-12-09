package com.pj.project.mine;

import com.pj.project.user.SpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pj.project.mine.SpMineMapper;

import java.util.List;

@Service
public class SpMineService{

    @Autowired
    private SpMineMapper spMineMapper;

    public List<SpUser> selectUserByMineId(long mineId) {
        return spMineMapper.selectUserByMineId(mineId);
    }
}
