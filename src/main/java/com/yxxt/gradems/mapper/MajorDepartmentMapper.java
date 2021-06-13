package com.yxxt.gradems.mapper;

import com.yxxt.gradems.domain.MajorDepartment;
import com.yxxt.gradems.domain.MajorDepartmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MajorDepartmentMapper {
    long countByExample(MajorDepartmentExample example);

    int deleteByExample(MajorDepartmentExample example);

    int deleteByPrimaryKey(Long rowId);

    int insert(MajorDepartment record);

    int insertSelective(MajorDepartment record);

    List<MajorDepartment> selectByExample(MajorDepartmentExample example);

    MajorDepartment selectByPrimaryKey(Long rowId);

    int updateByExampleSelective(@Param("record") MajorDepartment record, @Param("example") MajorDepartmentExample example);

    int updateByExample(@Param("record") MajorDepartment record, @Param("example") MajorDepartmentExample example);

    int updateByPrimaryKeySelective(MajorDepartment record);

    int updateByPrimaryKey(MajorDepartment record);
}