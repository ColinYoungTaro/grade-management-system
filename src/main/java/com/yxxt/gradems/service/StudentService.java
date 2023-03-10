package com.yxxt.gradems.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yxxt.gradems.config.VarConfig;
import com.yxxt.gradems.domain.*;
import com.yxxt.gradems.exception.BusinessException;
import com.yxxt.gradems.exception.BusinessExceptionCode;
import com.yxxt.gradems.mapper.*;
import com.yxxt.gradems.req.*;
import com.yxxt.gradems.resp.*;
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
public class StudentService {

    private static final Logger LOG = LoggerFactory.getLogger(StudentService.class);

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private SchoolUserMapper studentUserMapper;

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
    private SchoolUserMapper schoolUserMapper;

    @Resource
    private StudentScoreMapper studentScoreMapper;


    @Resource
    private SnowFlake snowFlake;

    public PageResp<StudentQueryResp> list(StudentQueryReq req) {
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getUserId())) {
            criteria.andUserIdEqualTo(req.getUserId());
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Student>  studentList = studentMapper.selectByExample(studentExample);

        PageInfo<Student> pageInfo = new PageInfo<>(studentList);
        LOG.info("????????????{}", pageInfo.getTotal());
        LOG.info("????????????{}", pageInfo.getPages());

        // ????????????
        List<StudentQueryResp> list = CopyUtil.copyList(studentList, StudentQueryResp.class);

        PageResp<StudentQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    /**
     * ??????
     *
     */
    public void save(StudentSaveReq req) {
        Student student = CopyUtil.copy(req, Student.class);
        if (ObjectUtils.isEmpty(req.getRowId())) {
            // ????????????????????????????????????????????????
            //student.setRowId(snowFlake.nextId());
            //student.setRowId(16L);
            studentMapper.insert(student);
        } else {
            // ??????????????????????????????????????????
            studentMapper.updateByPrimaryKey(student);
        }
    }

    /**
     * ??????id????????????
     * @param userId
     */
    public void delete(Long userId) {
        studentMapper.deleteByPrimaryKey(userId);
    }

    /**
     * ??????????????????
     * @param req
     * @return
     */
    public StudentInformationQueryResp getStudentInformation(SchoolStudentUserQueryReq req) {
        Student  student = studentMapper.selectByPrimaryKey(req.getUserId());
        SchoolUser studentUser = studentUserMapper.selectByPrimaryKey(req.getUserId());

        // ????????????
        StudentInformationQueryResp studentInformation = CopyUtil.copy(studentUser, StudentInformationQueryResp.class);
        studentInformation.setMajorId(student.getMajorId());
        studentInformation.setGrade(student.getGrade());
        studentInformation.setStatus(student.getStatus());
        studentInformation.setAdminClassId(student.getAdminClassId());
        studentInformation.setEnterYear(student.getEnterYear());
        if(studentInformation.getGender()){
            studentInformation.setStringGender(VarConfig.STR_MALE);
        }else{
            studentInformation.setStringGender(VarConfig.STR_FEMALE);

        }
        Major major = majorMapper.selectByPrimaryKey(studentInformation.getMajorId());
        Department department = departmentMapper.selectByPrimaryKey(studentInformation.getDepartmentId());
        studentInformation.setMajorName(major.getMajorName());
        studentInformation.setSchoolPeriod(major.getSchoolPeriod());
        studentInformation.setDepartmentName(department.getDepartmentName());

        return studentInformation;
    }

    /**
     * ??????
     *
     */
    public void studentInformationSave( StudentInformationSaveReq req) {
        Student student = CopyUtil.copy(req, Student.class);
        SchoolUser studentUser = CopyUtil.copy(req, SchoolUser.class);

        Student primaryStudent = studentMapper.selectByPrimaryKey(req.getUserId());
        SchoolUser primaryStudentUser = studentUserMapper.selectByPrimaryKey(req.getUserId());

        student.setRowId(primaryStudent.getRowId());
        studentMapper.updateByPrimaryKey(student);

        studentUser.setPasswordEncode(primaryStudentUser.getPasswordEncode());
        studentUser.setRowId(primaryStudentUser.getRowId());
        studentUserMapper.updateByPrimaryKeySelective(studentUser);
    }


    // ???????????????????????????????????????
    public List<StudentQueryCourseResp> getAllCourse(StudentQueryCourseReq req) {

        // ??????????????????????????????
        CourseScheduleExample courseScheduleExample = new CourseScheduleExample();
        CourseScheduleExample.Criteria criteriaCourseSchedule = courseScheduleExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getCourseUid())) {
            criteriaCourseSchedule.andCourseUidEqualTo(req.getCourseUid());
        }
        List<CourseSchedule>  courseScheduleList = courseScheduleMapper.selectByExample(courseScheduleExample);

        // ???????????????
        Student student = studentMapper.selectByPrimaryKey(req.getUserId());

        // ?????????????????????????????????
        if(student.getStatus()==1){
            // ???????????????????????????????????????course????????????????????????????????????
            List<StudentQueryCourseResp> list = new ArrayList<>();
            for (CourseSchedule courseSchedule : courseScheduleList) {

                String courseUid = courseSchedule.getCourseUid();
                Course course = courseMapper.selectByPrimaryKey(courseUid);

                if( !ObjectUtils.isEmpty(course)) {

                    StudentQueryCourseResp  studentQueryCourseResp = CopyUtil.copy(course, StudentQueryCourseResp.class);
                    studentQueryCourseResp.setTeacherId(courseSchedule.getTeacherId());
                    studentQueryCourseResp.setClassIndex(courseSchedule.getClassIndex());

                    if(course.getCourseType().equals(VarConfig.MAJOR_COURSE)) {
                        studentQueryCourseResp.setStringCourseType("?????????");
                    }else if(course.getCourseType().equals(VarConfig.GENERAL_COURSE)) {
                        studentQueryCourseResp.setStringCourseType("?????????");
                    }

                    // ??????????????????????????????
                    TrainingProgramExample trainingProgramExample = new TrainingProgramExample();
                    TrainingProgramExample.Criteria criteriaTrainingProgram = trainingProgramExample.createCriteria();
                    criteriaTrainingProgram.andCourseUidEqualTo(courseUid);
                    List<TrainingProgram> trainingProgramList = trainingProgramMapper.selectByExample(trainingProgramExample);
                    if(!ObjectUtils.isEmpty(trainingProgramList)){
                        TrainingProgram trainingProgram = trainingProgramList.get(0);
                        Integer majorId = trainingProgram.getMajorId();
                        MajorDepartmentExample majorDepartmentExample = new MajorDepartmentExample();
                        MajorDepartmentExample.Criteria criteriamajorDepartment = majorDepartmentExample.createCriteria();
                        criteriamajorDepartment.andMajorIdEqualTo(majorId);
                        List<MajorDepartment> majorDepartmentList = majorDepartmentMapper.selectByExample(majorDepartmentExample);
                        MajorDepartment majorDepartment = majorDepartmentList.get(0);
                        Integer departmentId = majorDepartment.getDepartmentId();
                        Department department = departmentMapper.selectByPrimaryKey(departmentId);

                        studentQueryCourseResp.setDepartmentName(department.getDepartmentName());
                    }


                    // ?????????????????????????????????????????????
                    CourseSelectionExample courseSelectionExample = new CourseSelectionExample();
                    CourseSelectionExample.Criteria criteriaCourseSelection = courseSelectionExample.createCriteria();
                    criteriaCourseSelection.andStudentIdEqualTo(req.getUserId());
                    criteriaCourseSelection.andCourseUidEqualTo(courseUid);
                    criteriaCourseSelection.andClassIndexEqualTo(courseSchedule.getClassIndex());
                    List<CourseSelection> courseSelectionList = courseSelectionMapper.selectByExample(courseSelectionExample);

                    StudentScoreExample studentScoreExample = new StudentScoreExample();
                    StudentScoreExample.Criteria criteriaStudentScore = studentScoreExample.createCriteria();
                    criteriaStudentScore.andStudentIdEqualTo(req.getUserId());
                    criteriaStudentScore.andCourseUidEqualTo(courseUid);
                    List<StudentScore> studentScoreList = studentScoreMapper.selectByExample(studentScoreExample);

                    // ??????????????????
                    if(!ObjectUtils.isEmpty(studentScoreList)){
                        studentQueryCourseResp.setSelectCourseStatus(2);
                        studentQueryCourseResp.setStringStatus("?????????");
                    } else if(ObjectUtils.isEmpty(courseSelectionList)){
                        studentQueryCourseResp.setSelectCourseStatus(0);
                        studentQueryCourseResp.setStringStatus("??????");
                    }else{
                        studentQueryCourseResp.setSelectCourseStatus(1);
                        studentQueryCourseResp.setStringStatus("??????");
                    }

                    SchoolUser teacherUser = schoolUserMapper.selectByPrimaryKey(courseSchedule.getTeacherId());
                    studentQueryCourseResp.setTeacherName(teacherUser.getUserName());

                    if(!ObjectUtils.isEmpty(course.getTerm())){
                        studentQueryCourseResp.setCourseTerm();
                    }

                    list.add(studentQueryCourseResp);
                }
            }

            return list;

        }else{
            // ?????????????????????????????????
            throw new BusinessException(BusinessExceptionCode.USER_STATUS_ERROR);
        }
    }

    /**
     * ??????
     * req: studentId courseUid courseIndex
     */
    public Integer studentSelectCourse( StudentSelectCourseReq req) {
        StudentScoreExample studentScoreExample = new StudentScoreExample();
        StudentScoreExample.Criteria criteriaStudentScore = studentScoreExample.createCriteria();
        criteriaStudentScore.andStudentIdEqualTo(req.getStudentId());
        criteriaStudentScore.andCourseUidEqualTo(req.getCourseUid());
        criteriaStudentScore.andClassIndexEqualTo(req.getClassIndex());
        List<StudentScore> studentScoreList = studentScoreMapper.selectByExample(studentScoreExample);

        // ??????????????????
        if(!ObjectUtils.isEmpty(studentScoreList)){
            return 0;
        }

        // ????????????????????????
        CourseSelectionExample courseSelectionExample = new CourseSelectionExample();
        CourseSelectionExample.Criteria criteriaCourseSelection = courseSelectionExample.createCriteria();
        criteriaCourseSelection.andStudentIdEqualTo(req.getStudentId());
        criteriaCourseSelection.andCourseUidEqualTo(req.getCourseUid());
        List<CourseSelection> courseSelectionList = courseSelectionMapper.selectByExample(courseSelectionExample);

        // ???????????????
        if(!ObjectUtils.isEmpty(courseSelectionList)){
            return 1;
        }

        // ??????
        CourseSelection courseSelection = CopyUtil.copy(req, CourseSelection.class);
        courseSelection.setRowId(snowFlake.nextId());
        courseSelectionMapper.insert(courseSelection);
        return 2;
    }


    /**
     * ??????
     *
     */
    public Integer unselectCourse(Long studentId, String courseUid, Integer classIndex) {
        StudentScoreExample studentScoreExample = new StudentScoreExample();
        StudentScoreExample.Criteria criteriaStudentScore = studentScoreExample.createCriteria();
        criteriaStudentScore.andStudentIdEqualTo(studentId);
        criteriaStudentScore.andCourseUidEqualTo(courseUid);
        criteriaStudentScore.andClassIndexEqualTo(classIndex);
        List<StudentScore> studentScoreList = studentScoreMapper.selectByExample(studentScoreExample);

        // ??????????????????
        if(!ObjectUtils.isEmpty(studentScoreList)){
            return 1;
        }else{

            // ??????????????????
            CourseSelectionExample courseSelectionExample = new CourseSelectionExample();
            CourseSelectionExample.Criteria criteriaCourseSelection = courseSelectionExample.createCriteria();
            criteriaCourseSelection.andStudentIdEqualTo(studentId);
            criteriaCourseSelection.andCourseUidEqualTo(courseUid);
            criteriaCourseSelection.andClassIndexEqualTo(classIndex);
            courseSelectionMapper.deleteByExample(courseSelectionExample);
            return 0;
        }
    }

    public Student getStudentById(Long studentId){
        return studentMapper.selectByPrimaryKey(studentId);
    }




    // ???????????????????????????????????????
    public List<StudentQueryCourseResp> getRecommendedCourse(StudentQueryCourseReq req) {

        // ???????????????
        Student student = studentMapper.selectByPrimaryKey(req.getUserId());
        // ?????????????????????????????????
        if(student.getStatus()==1){
            List<StudentQueryCourseResp> list = new ArrayList<>();

            // ??????id
            Integer majorId = student.getMajorId();

            // ????????????????????????department
            MajorDepartmentExample majorDepartmentExample = new MajorDepartmentExample();
            MajorDepartmentExample.Criteria criteriamajorDepartment = majorDepartmentExample.createCriteria();
            criteriamajorDepartment.andMajorIdEqualTo(majorId);
            List<MajorDepartment> majorDepartmentList = majorDepartmentMapper.selectByExample(majorDepartmentExample);
            MajorDepartment majorDepartment = majorDepartmentList.get(0);
            Integer departmentId = majorDepartment.getDepartmentId();
            Department department = departmentMapper.selectByPrimaryKey(departmentId);

            // ???????????????????????????
            TrainingProgramExample trainingProgramExample = new TrainingProgramExample();
            TrainingProgramExample.Criteria criteriaTrainingProgram = trainingProgramExample.createCriteria();
            criteriaTrainingProgram.andMajorIdEqualTo(majorId);
            List<TrainingProgram> trainingProgramList = trainingProgramMapper.selectByExample(trainingProgramExample);

            if(!ObjectUtils.isEmpty(trainingProgramList)){
                for (TrainingProgram trainingProgram : trainingProgramList){
                    // ??????????????????????????????
                    CourseScheduleExample courseScheduleExample = new CourseScheduleExample();
                    CourseScheduleExample.Criteria criteriaCourseSchedule = courseScheduleExample.createCriteria();
                    if (!ObjectUtils.isEmpty(req.getCourseUid())) {
                        criteriaCourseSchedule.andCourseUidEqualTo(req.getCourseUid());
                    }
                    criteriaCourseSchedule.andCourseUidEqualTo(trainingProgram.getCourseUid());
                    List<CourseSchedule>  courseScheduleList = courseScheduleMapper.selectByExample(courseScheduleExample);
                    for (CourseSchedule courseSchedule : courseScheduleList) {

                        String courseUid = courseSchedule.getCourseUid();
                        Course course = courseMapper.selectByPrimaryKey(courseUid);

                        if( !ObjectUtils.isEmpty(course)) {

                            StudentQueryCourseResp  studentQueryCourseResp = CopyUtil.copy(course, StudentQueryCourseResp.class);
                            studentQueryCourseResp.setTeacherId(courseSchedule.getTeacherId());
                            studentQueryCourseResp.setClassIndex(courseSchedule.getClassIndex());

                            if(course.getCourseType().equals(VarConfig.MAJOR_COURSE)) {
                                studentQueryCourseResp.setStringCourseType("?????????");
                            }else if(course.getCourseType().equals(VarConfig.GENERAL_COURSE)) {
                                studentQueryCourseResp.setStringCourseType("?????????");
                            }

                            // ?????????????????????????????????????????????
                            CourseSelectionExample courseSelectionExample = new CourseSelectionExample();
                            CourseSelectionExample.Criteria criteriaCourseSelection = courseSelectionExample.createCriteria();
                            criteriaCourseSelection.andStudentIdEqualTo(req.getUserId());
                            criteriaCourseSelection.andCourseUidEqualTo(courseUid);
                            criteriaCourseSelection.andClassIndexEqualTo(courseSchedule.getClassIndex());
                            List<CourseSelection> courseSelectionList = courseSelectionMapper.selectByExample(courseSelectionExample);

                            StudentScoreExample studentScoreExample = new StudentScoreExample();
                            StudentScoreExample.Criteria criteriaStudentScore = studentScoreExample.createCriteria();
                            criteriaStudentScore.andStudentIdEqualTo(req.getUserId());
                            criteriaStudentScore.andCourseUidEqualTo(courseUid);
                            List<StudentScore> studentScoreList = studentScoreMapper.selectByExample(studentScoreExample);

                            // ??????????????????
                            if(!ObjectUtils.isEmpty(studentScoreList)){
                                studentQueryCourseResp.setSelectCourseStatus(2);
                                studentQueryCourseResp.setStringStatus("?????????");
                            } else if(ObjectUtils.isEmpty(courseSelectionList)){
                                studentQueryCourseResp.setSelectCourseStatus(0);
                                studentQueryCourseResp.setStringStatus("??????");
                            }else{
                                studentQueryCourseResp.setSelectCourseStatus(1);
                                studentQueryCourseResp.setStringStatus("??????");
                            }

                            SchoolUser teacherUser = schoolUserMapper.selectByPrimaryKey(courseSchedule.getTeacherId());
                            studentQueryCourseResp.setTeacherName(teacherUser.getUserName());
                            studentQueryCourseResp.setDepartmentName(department.getDepartmentName());
                            if(!ObjectUtils.isEmpty(course.getTerm())){
                                studentQueryCourseResp.setCourseTerm();
                            }
                            list.add(studentQueryCourseResp);
                        }
                    }
                }
            }

            return list;

        }else{
            throw new BusinessException(BusinessExceptionCode.USER_STATUS_ERROR);
        }
    }

}