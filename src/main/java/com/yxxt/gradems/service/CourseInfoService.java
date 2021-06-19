package com.yxxt.gradems.service;

import com.yxxt.gradems.domain.Course;
import com.yxxt.gradems.domain.CourseExample;
import com.yxxt.gradems.domain.TrainingProgram;
import com.yxxt.gradems.domain.TrainingProgramExample;
import com.yxxt.gradems.mapper.CourseMapper;
import com.yxxt.gradems.mapper.TrainingProgramMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseInfoService {

    @Resource
    private TrainingProgramMapper trainingProgramMapper;

    @Resource
    private CourseMapper courseMapper;

    /**
     * 获得某专业培养方案的所有课程
     * @param majorId
     * @return
     */
    public List<Course> getTrainingProgram(Integer majorId){
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

    public Course getCourseByUid(String courseUid){
        return courseMapper.selectByPrimaryKey(courseUid);
    }

}
