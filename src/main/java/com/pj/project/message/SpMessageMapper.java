package com.pj.project.message;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pj.utils.so.SoMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SpMessageMapper extends BaseMapper<SpMessage> {
    List<Message> getMessageList(SoMap so,List<String> warnTypeCodes,List<String> sensorTypeCodes,String sTime,String eTime);

    int deleteByIds(List<Long> ids);


}
