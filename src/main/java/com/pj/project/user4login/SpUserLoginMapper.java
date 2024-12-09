package com.pj.project.user4login;

import com.pj.utils.so.SoMap;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SpUserLoginMapper {

    /**
     * 增
     * @param s 实体对象
     * @return 受影响行数
     */
    int add(SpUserLogin s);

    /**
     * 删
     * @param id 要删除的数据id
     * @return 受影响行数
     */
    int delete(Long id);

    /**
     * 查 - 根据id
     * @param id 要查询的数据id
     * @return 实体对象
     */
    SpUserLogin getById(Long id);

    /**
     * 查集合 - 根据条件（参数为空时代表忽略指定条件）
     * @param so 参数集合
     * @return 数据列表
     */
    List<SpUserLogin> getList(SoMap so);
}
