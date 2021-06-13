package com.yxxt.gradems.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yxxt.gradems.config.VarConfig;
import com.yxxt.gradems.domain.*;
import com.yxxt.gradems.mapper.*;
import com.yxxt.gradems.req.*;
import com.yxxt.gradems.resp.PageResp;
import com.yxxt.gradems.resp.TeacherClassStudentsQueryResp;
import com.yxxt.gradems.resp.TeacherCourseClassQueryResp;
import com.yxxt.gradems.resp.TeacherInformationQueryResp;
import com.yxxt.gradems.util.CopyUtil;
import com.yxxt.gradems.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {

    private static final Logger LOG = LoggerFactory.getLogger(TeacherService.class);

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private SchoolUserMapper schoolUserMapper;

    @Resource
    private MajorMapper majorMapper;

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private CourseScheduleMapper courseScheduleMapper;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private TrainingProgramMapper trainingProgramMapper;

    @Resource
    private MajorDepartmentMapper majorDepartmentMapper;

    @Resource
    private CourseSelectionMapper courseSelectionMapper;


    @Resource
    private StudentScoreMapper studentScoreMapper;


    @Resource
    private SnowFlake snowFlake;


    /**
     * 获取教师信息
     * @param req
     * @return
     */
    public TeacherInformationQueryResp getTeacherInformation(SchoolTeacherUserQueryReq req) {
        SchoolUser teacherUser = schoolUserMapper.selectByPrimaryKey(req.getUserId());

        // 单体复制
        TeacherInformationQueryResp teacherInformation = CopyUtil.copy(teacherUser, TeacherInformationQueryResp.class);

        if(teacherInformation.getGender()){
            teacherInformation.setStringGender(VarConfig.STR_MALE);
        }else{
            teacherInformation.setStringGender(VarConfig.STR_FEMALE);

        }

        Department department = departmentMapper.selectByPrimaryKey(teacherInformation.getDepartmentId());
        teacherInformation.setDepartmentName(department.getDepartmentName());

        return teacherInformation;
    }

    /**
     * 保存
     *
     */
    public void teacherInformationSave( TeacherInformationSaveReq req) {
        SchoolUser teacherUser = CopyUtil.copy(req, SchoolUser.class);

        SchoolUser primaryTeacherUser = schoolUserMapper.selectByPrimaryKey(req.getUserId());

        teacherUser.setPasswordEncode(primaryTeacherUser.getPasswordEncode());
        teacherUser.setRowId(primaryTeacherUser.getRowId());
        schoolUserMapper.updateByPrimaryKeySelective(teacherUser);
    }


    /**
     * 查询老师的教学班级
     */
    public PageResp<TeacherCourseClassQueryResp> teacherClassList(TeacherCourseClassQueryReq req) {

        // 获取所有学校用户，返回到schoolUserList中
        CourseScheduleExample courseScheduleExample = new CourseScheduleExample();
        CourseScheduleExample.Criteria criteriaCourseSchedule = courseScheduleExample.createCriteria();
        criteriaCourseSchedule.andTeacherIdEqualTo(req.getTeacherId());
        PageHelper.startPage(req.getPage(), req.getSize());

        List<CourseSchedule> courseScheduleList = courseScheduleMapper.selectByExample(courseScheduleExample);

        // 对于查询到的每一个学校用户，去student表中，根据userId查询是否有对应记录
        List<TeacherCourseClassQueryResp> list = new ArrayList<>();
        for (CourseSchedule courseSchedule : courseScheduleList) {
            String courseUid = courseSchedule.getCourseUid();
            Course course = courseMapper.selectByPrimaryKey(courseUid);

            TeacherCourseClassQueryResp teacherCourseClassQueryResp = CopyUtil.copy(courseSchedule, TeacherCourseClassQueryResp.class);
            if(!ObjectUtils.isEmpty(course)) {
                teacherCourseClassQueryResp.setCourseName(course.getCourseName());
                teacherCourseClassQueryResp.setCredit(course.getCredit());
                teacherCourseClassQueryResp.setSchoolHour(course.getSchoolHour());
                teacherCourseClassQueryResp.setCourseType(course.getCourseType());
                teacherCourseClassQueryResp.setYear(course.getTerm());
                teacherCourseClassQueryResp.setStringCourseType();
                teacherCourseClassQueryResp.setStringTerm();
            }

            CourseSelectionExample courseSelectionExample = new CourseSelectionExample();
            CourseSelectionExample.Criteria criteriaCourseSelection = courseSelectionExample.createCriteria();
            criteriaCourseSelection.andCourseUidEqualTo(courseSchedule.getCourseUid());
            criteriaCourseSelection.andClassIndexEqualTo(courseSchedule.getClassIndex());
            List<CourseSelection> courseSelectionList = courseSelectionMapper.selectByExample(courseSelectionExample);
            teacherCourseClassQueryResp.setStudentCount(courseSelectionList.size());

            list.add(teacherCourseClassQueryResp);
        }

        PageInfo<CourseSchedule> pageInfo = new PageInfo<>(courseScheduleList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        PageResp<TeacherCourseClassQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }


    /**
     * 查询老师的教学班级的所有学生
     */
    public List<TeacherClassStudentsQueryResp>  courseClassStudentList(TeacherClassStudentsQueryReq req) {

        // 查询选了该课程的所有选课记录
        CourseSelectionExample courseSelectionExample = new CourseSelectionExample();
        CourseSelectionExample.Criteria criteriaCourseSelection = courseSelectionExample.createCriteria();
        criteriaCourseSelection.andCourseUidEqualTo(req.getCourseUid());
        criteriaCourseSelection.andClassIndexEqualTo(req.getClassIndex());
        List<CourseSelection> courseSelectionList = courseSelectionMapper.selectByExample(courseSelectionExample);

        // 对于查询到的每一个学校用户，去User表中查名字
        List<TeacherClassStudentsQueryResp> list = new ArrayList<>();
        for (CourseSelection courseSelection : courseSelectionList) {
            Long studentId = courseSelection.getStudentId();
            SchoolUser studentUser = schoolUserMapper.selectByPrimaryKey(studentId);

            TeacherClassStudentsQueryResp teacherClassStudentsQueryResp = CopyUtil.copy(courseSelection, TeacherClassStudentsQueryResp.class);
            if(!ObjectUtils.isEmpty(studentUser)) {
                teacherClassStudentsQueryResp.setUserName(studentUser.getUserName());
                teacherClassStudentsQueryResp.setUserId(studentUser.getUserId());
            }
            teacherClassStudentsQueryResp.setClassIndex(req.getClassIndex());
            teacherClassStudentsQueryResp.setCourseUid(req.getCourseUid());

            StudentScoreExample studentScoreExample = new StudentScoreExample();
            StudentScoreExample.Criteria criteriaStudentScore = studentScoreExample.createCriteria();
            criteriaStudentScore.andClassIndexEqualTo(req.getClassIndex());
            criteriaStudentScore.andCourseUidEqualTo(req.getCourseUid());
            criteriaStudentScore.andStudentIdEqualTo(studentId);
            List<StudentScore> studentScoreList = studentScoreMapper.selectByExample(studentScoreExample);
            if(!ObjectUtils.isEmpty(studentScoreList)){
                teacherClassStudentsQueryResp.setScore(studentScoreList.get(0).getScore());
                teacherClassStudentsQueryResp.setScoredStatus(true);
            }else{
                teacherClassStudentsQueryResp.setScore(null);
                teacherClassStudentsQueryResp.setScoredStatus(false);

            }

            list.add(teacherClassStudentsQueryResp);
        }

        return list;
    }


    /**
     * 提交成绩
     *
     */
    public Integer teacherCommitScore(List<TeacherCommitScoreReq> req) {

        for(TeacherCommitScoreReq teacherCommitScore : req){
            if(!ObjectUtils.isEmpty(teacherCommitScore)){
                StudentScore studentScore = CopyUtil.copy(teacherCommitScore, StudentScore.class);

                StudentScoreExample studentScoreExample = new StudentScoreExample();
                StudentScoreExample.Criteria criteriaStudentScore = studentScoreExample.createCriteria();
                criteriaStudentScore.andClassIndexEqualTo(studentScore.getClassIndex());
                criteriaStudentScore.andCourseUidEqualTo(studentScore.getCourseUid());
                criteriaStudentScore.andStudentIdEqualTo(studentScore.getStudentId());
                List<StudentScore> studentScoreList = studentScoreMapper.selectByExample(studentScoreExample);
                if(ObjectUtils.isEmpty(studentScoreList)){
                    studentScoreMapper.insert(studentScore);
                }
            }
        }
        return 0;
    }




}