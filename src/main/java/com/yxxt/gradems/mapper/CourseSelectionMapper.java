package com.yxxt.gradems.mapper;

import com.yxxt.gradems.domain.CourseSelection;
import com.yxxt.gradems.domain.CourseSelectionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseSelectionMapper {
    long countByExample(CourseSelectionExample example);

    int deleteByExample(CourseSelectionExample example);

    int deleteByPrimaryKey(Long rowId);

    int insert(CourseSelection record);

    int insertSelective(CourseSelection record);

    List<CourseSelection> selectByExample(CourseSelectionExample example);

    CourseSelection selectByPrimaryKey(Long rowId);

    int updateByExampleSelective(@Param("record") CourseSelection record, @Param("example") CourseSelectionExample example);

    int updateByExample(@Param("record") CourseSelection record, @Param("example") CourseSelectionExample example);

    int updateByPrimaryKeySelective(CourseSelection record);

    int updateByPrimaryKey(CourseSelection record);
}