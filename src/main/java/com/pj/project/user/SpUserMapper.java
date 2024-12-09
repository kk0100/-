package com.pj.project.user;
import com.pj.utils.so.SoMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper: 系统管理员表
 * @author kong
 */
@Mapper
public interface SpUserMapper {


    /**
     * 增 #{name}, #{password}, #{roleId}
     * @param obj
     * @return
     */
    int add(SpUser obj);

    /**
     * 删
     * @param id
     * @return
     */
    int delete(long id);

    /**
     * 改
     * @param obj
     * @return
     */
    int update(SpUser obj);

    /**
     * 查
     * @param id
     * @return
     */
    SpUser getById(long id);

    /**
     * 查
     * @param so
     * @return
     */
    List<SpUser> getList(SoMap so);

    /**
     * 查询，根据name
     * @param name
     * @return
     */
    SpUser getByName(String name);

    /**
     * 查询，根据 phone
     * @param phone
     * @return
     */
    SpUser getByPhone(String phone);


    /**
     * 修改指定账号的 最后登录记录
     * @param id
     * @param loginIp
     * @return
     */
    public int updateLoginLog(@Param("id")long id, @Param("loginIp")String loginIp);


    List<SpUser> findByUser();
}
