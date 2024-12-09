package com.pj.project.mine;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SpUserMineMapper {
    void insertByMineId(long userId, Long mineId);
    void deleteByUserId(long userId);

    List<SpUserMine> getMineByUserId(long userId);
}
