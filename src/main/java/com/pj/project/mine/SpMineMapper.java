package com.pj.project.mine;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pj.project.user.SpUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SpMineMapper extends BaseMapper<SpMine> {
    List<SpUser> selectUserByMineId(long mineId);
}
