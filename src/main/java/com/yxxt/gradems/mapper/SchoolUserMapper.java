package com.yxxt.gradems.mapper;

import com.yxxt.gradems.domain.SchoolUser;
import com.yxxt.gradems.domain.SchoolUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SchoolUserMapper {
    long countByExample(SchoolUserExample example);

    int deleteByExample(SchoolUserExample example);

    int deleteByPrimaryKey(Long userId);

    int insert(SchoolUser record);

    int insertSelective(SchoolUser record);

    List<SchoolUser> selectByExample(SchoolUserExample example);

    SchoolUser selectByPrimaryKey(Long userId);

    int updateByExampleSelective(@Param("record") SchoolUser record, @Param("example") SchoolUserExample example);

    int updateByExample(@Param("record") SchoolUser record, @Param("example") SchoolUserExample example);

    int updateByPrimaryKeySelective(SchoolUser record);

    int updateByPrimaryKey(SchoolUser record);
}