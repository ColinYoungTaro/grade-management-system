package com.yxxt.gradems.service;
import java.util.List;

import javax.annotation.Resource;

import com.yxxt.gradems.domain.Department;
import com.yxxt.gradems.domain.MajorDepartment;
import com.yxxt.gradems.domain.MajorDepartmentExample;
import com.yxxt.gradems.exception.BusinessException;
import com.yxxt.gradems.exception.BusinessExceptionCode;
import com.yxxt.gradems.mapper.DepartmentMapper;
import com.yxxt.gradems.mapper.MajorDepartmentMapper;

import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private MajorDepartmentMapper majorDepartmentMapper;

    public Department getDepartmentById(Integer departmentId){
        return departmentMapper.selectByPrimaryKey(departmentId);
    }

    public Department getDepartmentOfMajorById(Integer majorId){
        MajorDepartmentExample exp = new MajorDepartmentExample();
        exp.createCriteria().andMajorIdEqualTo(majorId);
        List<MajorDepartment> records = majorDepartmentMapper.selectByExample(exp);
        if(records.size() != 1){
            throw new BusinessException(BusinessExceptionCode.DB_OPERATION_ERROR);
        }
        Integer departmentId = records.get(0).getDepartmentId();
        return getDepartmentById(departmentId);
    }
}
