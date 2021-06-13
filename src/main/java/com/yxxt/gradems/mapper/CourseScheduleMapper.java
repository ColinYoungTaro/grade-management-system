package com.yxxt.gradems.mapper;

import com.yxxt.gradems.domain.CourseSchedule;
import com.yxxt.gradems.domain.CourseScheduleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseScheduleMapper {
    long countByExample(CourseScheduleExample example);

    int deleteByExample(CourseScheduleExample example);

    int deleteByPrimaryKey(Long rowId);

    int insert(CourseSchedule record);

    int insertSelective(CourseSchedule record);

    List<CourseSchedule> selectByExample(CourseScheduleExample example);

    CourseSchedule selectByPrimaryKey(Long rowId);

    int updateByExampleSelective(@Param("record") CourseSchedule record, @Param("example") CourseScheduleExample example);

    int updateByExample(@Param("record") CourseSchedule record, @Param("example") CourseScheduleExample example);

    int updateByPrimaryKeySelective(CourseSchedule record);

    int updateByPrimaryKey(CourseSchedule record);
}