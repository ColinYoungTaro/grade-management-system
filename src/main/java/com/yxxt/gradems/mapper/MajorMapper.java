package com.yxxt.gradems.mapper;

import com.yxxt.gradems.domain.Major;
import com.yxxt.gradems.domain.MajorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MajorMapper {
    long countByExample(MajorExample example);

    int deleteByExample(MajorExample example);

    int deleteByPrimaryKey(Integer majorId);

    int insert(Major record);

    int insertSelective(Major record);

    List<Major> selectByExample(MajorExample example);

    Major selectByPrimaryKey(Integer majorId);

    int updateByExampleSelective(@Param("record") Major record, @Param("example") MajorExample example);

    int updateByExample(@Param("record") Major record, @Param("example") MajorExample example);

    int updateByPrimaryKeySelective(Major record);

    int updateByPrimaryKey(Major record);
}