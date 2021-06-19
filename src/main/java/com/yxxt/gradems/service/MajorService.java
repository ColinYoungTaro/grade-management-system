package com.yxxt.gradems.service;

import java.util.ArrayList;
import java.util.List;

import com.yxxt.gradems.domain.*;
import com.yxxt.gradems.exception.BusinessException;
import com.yxxt.gradems.exception.BusinessExceptionCode;
import com.yxxt.gradems.mapper.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Major Service 管理专业和班级相关的服务接口
 */
@Service
public class MajorService {
    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private MajorDepartmentMapper majorDepartmentMapper;

    @Resource
    private MajorMapper majorMapper;

    @Resource
    private TrainingProgramMapper trainingProgramMapper;

    @Resource
    private CourseScheduleMapper courseScheduleMapper;

    @Resource
    private CourseMapper courseMapper;

    public List<Major> getMajorsOfDepartment(Integer departmentId){
        List<Major> majorList = new ArrayList<>();
        MajorDepartmentExample mdExmple = new MajorDepartmentExample();
        mdExmple.createCriteria()
        .andDepartmentIdEqualTo(departmentId);
        // 判断学院是否存在
        Department department = departmentMapper.selectByPrimaryKey(departmentId);
        if(department == null){
            throw new BusinessException(BusinessExceptionCode.KEY_NOT_EXIST);
        }
        List<MajorDepartment> mds = majorDepartmentMapper.selectByExample(mdExmple);
        for(MajorDepartment record : mds ){
            Integer majorId = record.getMajorId();
            Major major = majorMapper.selectByPrimaryKey(majorId);
            majorList.add(major);
        }
        return majorList;
    }

    public Major getMajorById(Integer majorId){
        return majorMapper.selectByPrimaryKey(majorId);
    }

    public List<Course> getTrainingProgramCourses(Integer majorId){
        TrainingProgramExample example = new TrainingProgramExample();
        example.createCriteria().andMajorIdEqualTo(majorId);
        List<TrainingProgram> courseItemList =  trainingProgramMapper.selectByExample(example);
        example.clear();
        List<String> uids = new ArrayList<>();
        for(TrainingProgram record : courseItemList){
            String courseUid = record.getCourseUid();
            uids.add(courseUid);
        }
        // List<Course> courseList;
        CourseExample courseExample = new CourseExample();
        courseExample.createCriteria().andCourseUidIn(uids);
        return courseMapper.selectByExample(courseExample);
    }


    public List<CourseSchedule> getScheduledClassesByCourse(Course course){
        String courseUid = course.getCourseUid();
        CourseScheduleExample courseScheduleExample = new CourseScheduleExample();
        courseScheduleExample.createCriteria().andCourseUidEqualTo(courseUid);
        List<CourseSchedule> scheduleList = courseScheduleMapper.selectByExample(courseScheduleExample);
        return scheduleList;
    }

}
