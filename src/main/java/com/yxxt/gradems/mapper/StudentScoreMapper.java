package com.yxxt.gradems.mapper;

import com.yxxt.gradems.domain.StudentScore;
import com.yxxt.gradems.domain.StudentScoreExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentScoreMapper {
    long countByExample(StudentScoreExample example);

    int deleteByExample(StudentScoreExample example);

    int deleteByPrimaryKey(Long rowId);

    int insert(StudentScore record);

    int insertSelective(StudentScore record);

    List<StudentScore> selectByExample(StudentScoreExample example);

    StudentScore selectByPrimaryKey(Long rowId);

    int updateByExampleSelective(@Param("record") StudentScore record, @Param("example") StudentScoreExample example);

    int updateByExample(@Param("record") StudentScore record, @Param("example") StudentScoreExample example);

    int updateByPrimaryKeySelective(StudentScore record);

    int updateByPrimaryKey(StudentScore record);
}