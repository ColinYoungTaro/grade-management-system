package com.yxxt.gradems.service;

import com.yxxt.gradems.domain.*;
import com.yxxt.gradems.exception.BusinessException;
import com.yxxt.gradems.exception.BusinessExceptionCode;
import com.yxxt.gradems.mapper.*;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ScoreAnalyseService {

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private StudentScoreMapper scoreMapper;

    @Resource
    private CourseSelectionMapper courseSelectionMapper;

    @Resource
    private StudentScoreMapper studentScoreMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private TeacherMapper teacherMapper;

    /**
     * 获取一个同学挂科的课程
     * @return
     */
    public List<Course> getFailedCourses(){
        StudentScoreExample example = new StudentScoreExample();
        example.createCriteria().andScoreLessThan(60);
        List<StudentScore> scoreRecordList = scoreMapper.selectByExample(example);
        List<String> courseUidList = scoreRecordList.stream().map(StudentScore::getCourseUid).collect(Collectors.toList());
        if(courseUidList.size() == 0){
            return new ArrayList<Course>();
        }
        CourseExample courseExample = new CourseExample();
        courseExample.createCriteria().andCourseUidIn(courseUidList);
        return courseMapper.selectByExample(courseExample);
    }

    /**
     * 计算id编号的同学的GPA
     * @param studentId
     * @return
     */
    public float getCredit(Long studentId){
        List<StudentScore> studentScores = getAllScoresOfStudents(studentId);
        float credits = 0f;
        for(StudentScore score : studentScores){
            Course course = courseMapper.selectByPrimaryKey(score.getCourseUid());
            credits += course.getCredit();
        }
        return credits;
    }
    public float calcGPA(Long studentId){
        List<StudentScore> studentScores = getAllScoresOfStudents(studentId);
        float credits = 0f;
        float accScores = 0f;
        for(StudentScore score : studentScores){
            Course course = courseMapper.selectByPrimaryKey(score.getCourseUid());
            accScores += score.getScore() * course.getCredit();
            credits += course.getCredit();
        }
        return credits == 0f ? 0 : (float)((int)(accScores / credits*100))/100;
    }

    /**
     * 获取成绩排名
     * @param studentId
     * @return 名词 / 总人数 （Tuple)
     */
    public Pair<Integer, Integer> getRank(Long studentId){
        Student student = studentMapper.selectByPrimaryKey(studentId);
        if(student == null){
            throw new BusinessException(BusinessExceptionCode.KEY_NOT_EXIST);
        }
        Integer adminClass = student.getAdminClassId();
        StudentExample stuExample = new StudentExample();
        stuExample.createCriteria().andAdminClassIdEqualTo(adminClass);
        List<Student> sameClassStudents = studentMapper.selectByExample(stuExample);
        List<Long> ids = sameClassStudents.stream().map(Student::getUserId).collect(Collectors.toList());
        int rank = 1;
        float myGPA = this.calcGPA(studentId);
        for(Long id : ids ){
            float gpa = calcGPA(id);
            if(gpa > myGPA){
                rank++;
            }
        }
        return Pair.of(rank,ids.size());
    }

    /**
     * 获得course列表对应的score信息
     * @param studentId
     * @param courses
     * @return
     */
    /*public List<StudentScore> getScoreRecords(Long studentId,List<Course> courses){
        StudentScoreExample exp = new StudentScoreExample();
        List<String> uids = courses.stream().map(Course::getCourseUid).collect(Collectors.toList());
        CourseSelectionExample courseSelectionExample = new CourseSelectionExample();
        courseSelectionExample.createCriteria().andStudentIdEqualTo(studentId);
        List<CourseSelection> courseSelectionList = courseSelectionMapper.selectByExample(courseSelectionExample);
        // 得到了同学的选的所有课程
        for(CourseSelection selection : courseSelectionList){
            // 查询所有选课 获取该门课程的成绩
            exp.clear();
            exp.get

        }

        return scoreRecords;
    }*/

    /**
     * 获取一个班级中所有的学生分数表单
     * @param courseUid
     * @param classIndex
     * @return
     */
    public List<StudentScore> getStudentScoresOfClass(String courseUid, int classIndex){
        StudentScoreExample example = new StudentScoreExample();
        StudentScoreExample.Criteria criteria = example.createCriteria();
        criteria.andClassIndexEqualTo(classIndex)
                .andCourseUidEqualTo(courseUid);
        List<StudentScore> studentScoreList = studentScoreMapper.selectByExample(example);
        return studentScoreList;
    }

    /**
     * 获得学生的所有课程成绩表单
     * @param studentId
     * @return
     */
    public List<StudentScore> getAllScoresOfStudents(Long studentId) {
        StudentScoreExample example = new StudentScoreExample();
        StudentScoreExample.Criteria criteria = example.createCriteria();
        criteria.andStudentIdEqualTo(studentId);
        return studentScoreMapper.selectByExample(example);
    }

    /**
     * getAllCoursesOfStudent 查询编号为id的同学修过的所有课程
     * @param studentId
     * @return course的列表
     */
    public List<Course> getAllCoursesOfStudent(Long studentId){

        CourseSelectionExample courseSelectionExample = new CourseSelectionExample();
        courseSelectionExample.createCriteria().andStudentIdEqualTo(studentId);
        List<CourseSelection> records = courseSelectionMapper.selectByExample(courseSelectionExample);
        List<String> courseUids = records.stream().map(CourseSelection::getCourseUid).collect(Collectors.toList());
        if(courseUids.size() == 0){
            return new ArrayList<Course>();
        }
        CourseExample courseExample = new CourseExample();
        courseExample.createCriteria().andCourseUidIn(courseUids);
        List<Course> selectedCourseList = courseMapper.selectByExample(courseExample);

        return selectedCourseList;
    }

    public Integer getClassScoreOfStudent(Long studentId, String courseUid, int classIndex) {
        // 获得的是选课的最高分
        StudentScoreExample example = new StudentScoreExample();
        StudentScoreExample.Criteria criteria = example.createCriteria();
        criteria.andClassIndexEqualTo(classIndex)
                .andCourseUidEqualTo(courseUid)
                .andStudentIdEqualTo(studentId);
        List<StudentScore> scoreList = studentScoreMapper.selectByExample(example);
        if(scoreList.size() == 0){
            return null;
        }
        return scoreList.get(0).getScore();
    }

}
