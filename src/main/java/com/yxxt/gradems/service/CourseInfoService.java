package com.yxxt.gradems.service;

import com.yxxt.gradems.config.VarConfig;
import com.yxxt.gradems.domain.Course;
import com.yxxt.gradems.domain.CourseExample;
import com.yxxt.gradems.domain.CourseSelection;
import com.yxxt.gradems.domain.CourseSelectionExample;
import com.yxxt.gradems.domain.StudentScore;
import com.yxxt.gradems.domain.StudentScoreExample;
import com.yxxt.gradems.domain.TrainingProgram;
import com.yxxt.gradems.domain.TrainingProgramExample;
import com.yxxt.gradems.mapper.CourseMapper;
import com.yxxt.gradems.mapper.CourseSelectionMapper;
import com.yxxt.gradems.mapper.StudentScoreMapper;
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

    @Resource
    private CourseSelectionMapper courseSelectionMapper;

    @Resource
    private StudentScoreMapper studentScoreMapper;

    /**
     * 获得某专业培养方案的所有课程
     * @param majorId
     * @return
     */
    public List<Course> getTrainingProgram(Integer majorId){
        TrainingProgramExample example = new TrainingProgramExample();
        example.createCriteria().andMajorIdEqualTo(majorId);
        List<TrainingProgram> courseItemList =  trainingProgramMapper.selectByExample(example);
        // 没有关于培养方案的信息
        if(courseItemList.size() == 0){
            return null;
        }
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

    public Integer getCourseSelectionStateByStudentId(Long studentId,String courseUid){
        // 选过且有成绩
        CourseSelectionExample exp = new CourseSelectionExample();
        exp.createCriteria().andStudentIdEqualTo(studentId).andCourseUidEqualTo(courseUid);
        List<CourseSelection> records = courseSelectionMapper.selectByExample(exp);
        if(records.size() == 0){
            return VarConfig.NOT_SELECTED;
        }
        else{
            StudentScoreExample scoreExample = new StudentScoreExample();
            scoreExample.createCriteria().andStudentIdEqualTo(studentId).andCourseUidEqualTo(courseUid);
            List<StudentScore> scoreRecords = studentScoreMapper.selectByExample(scoreExample);
            if(scoreRecords.size() == 0){
                return VarConfig.SELECTED;
            }
            else{
                return VarConfig.FINISHED;
            }
        }
    }

}
