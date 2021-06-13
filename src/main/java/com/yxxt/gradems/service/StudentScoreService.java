package com.yxxt.gradems.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yxxt.gradems.domain.Course;
import com.yxxt.gradems.domain.StudentScore;
import com.yxxt.gradems.domain.StudentScoreExample;
import com.yxxt.gradems.mapper.CourseMapper;
import com.yxxt.gradems.mapper.StudentScoreMapper;
import com.yxxt.gradems.req.StudentScoreQueryReq;
import com.yxxt.gradems.resp.PageResp;
import com.yxxt.gradems.resp.StudentScoreQueryResp;
import com.yxxt.gradems.util.CopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentScoreService {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolUserService.class);

    @Resource
    private StudentScoreMapper studentScoreMapper;

    @Resource
    private CourseMapper courseMapper;

    // 查询一个学生的所有课程成绩
    public PageResp<StudentScoreQueryResp> wholeList(StudentScoreQueryReq req) {

        // 获取该学生的所有成绩，返回到studentScoreList中
        StudentScoreExample studentScoreExample = new StudentScoreExample();
        StudentScoreExample.Criteria criteriaStudentScore = studentScoreExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getCourseUid())) {
            criteriaStudentScore.andCourseUidEqualTo(req.getCourseUid());
        }
        criteriaStudentScore.andStudentIdEqualTo(req.getStudentId());

        // 根据分页参数进行查询
        PageHelper.startPage(req.getPage(), req.getSize());
        List<StudentScore>  studentScoreList = studentScoreMapper.selectByExample(studentScoreExample);

        // 对于查询到的每一条成绩记录，去course表中，根据courseUid查询
        List<StudentScoreQueryResp> list = new ArrayList<>();
        LOG.info("studentScoreList：{}", studentScoreList);
        for (StudentScore studentScore : studentScoreList) {
            String courseUid = studentScore.getCourseUid();
            Course course = courseMapper.selectByPrimaryKey(courseUid);
            StudentScoreQueryResp  studentScoreQueryResp = CopyUtil.copy(studentScore, StudentScoreQueryResp.class);

            if(!ObjectUtils.isEmpty(course)) {
                studentScoreQueryResp.setCourseName(course.getCourseName());
                studentScoreQueryResp.setCredit(course.getCredit());
                studentScoreQueryResp.setSchoolHour(course.getSchoolHour());
                studentScoreQueryResp.setCourseType(course.getCourseType());
                studentScoreQueryResp.setYear(course.getYear());
                studentScoreQueryResp.setTerm(course.getTerm());
                studentScoreQueryResp.setGpa();
            }
            list.add(studentScoreQueryResp);
        }

        PageInfo<StudentScore> pageInfo = new PageInfo<>(studentScoreList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        PageResp<StudentScoreQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    // 查询一个学生的单个学期的成绩,前端给出studentId year term
    public PageResp<StudentScoreQueryResp> singleYearOrTermList(StudentScoreQueryReq req) {

        // 获取该学生的所有成绩，返回到studentScoreList中
        StudentScoreExample studentScoreExample = new StudentScoreExample();
        StudentScoreExample.Criteria criteriaStudentScore = studentScoreExample.createCriteria();
        criteriaStudentScore.andStudentIdEqualTo(req.getStudentId());

        List<StudentScore>  studentScoreList = studentScoreMapper.selectByExample(studentScoreExample);

        // 对于查询到的每一条成绩记录，去course表中，根据courseUid查询
        List<StudentScoreQueryResp> list = new ArrayList<>();
        LOG.info("studentScoreList：{}", studentScoreList);
        for (StudentScore studentScore : studentScoreList) {
            String courseUid = studentScore.getCourseUid();
            Course course = courseMapper.selectByPrimaryKey(courseUid);

            if(!ObjectUtils.isEmpty(course) && course.getYear().equals(req.getYear())) {
                if(!ObjectUtils.isEmpty(req.getTerm())){
                    if( course.getTerm().equals(req.getTerm())){
                        StudentScoreQueryResp  studentScoreQueryResp = CopyUtil.copy(studentScore, StudentScoreQueryResp.class);
                        studentScoreQueryResp.setCourseName(course.getCourseName());
                        studentScoreQueryResp.setCredit(course.getCredit());
                        studentScoreQueryResp.setSchoolHour(course.getSchoolHour());
                        studentScoreQueryResp.setCourseType(course.getCourseType());
                        studentScoreQueryResp.setYear(course.getYear());
                        studentScoreQueryResp.setTerm(course.getTerm());
                        studentScoreQueryResp.setGpa();

                        list.add(studentScoreQueryResp);
                    }
                } else{
                    StudentScoreQueryResp  studentScoreQueryResp = CopyUtil.copy(studentScore, StudentScoreQueryResp.class);
                    studentScoreQueryResp.setCourseName(course.getCourseName());
                    studentScoreQueryResp.setCredit(course.getCredit());
                    studentScoreQueryResp.setSchoolHour(course.getSchoolHour());
                    studentScoreQueryResp.setCourseType(course.getCourseType());
                    studentScoreQueryResp.setYear(course.getYear());
                    studentScoreQueryResp.setTerm(course.getTerm());
                    studentScoreQueryResp.setGpa();

                    list.add(studentScoreQueryResp);
                }
            }
        }

        PageInfo<StudentScoreQueryResp> pageInfo = new PageInfo<>(list);
        LOG.info("总行数：{}", pageInfo.getTotal());

        PageResp<StudentScoreQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    // 查询一个学生的指定课程类型的成绩,前端给出studentId coursetype(0 1)
    public PageResp<StudentScoreQueryResp> courseTypeList(StudentScoreQueryReq req) {

        // 获取该学生的所有成绩，返回到studentScoreList中
        StudentScoreExample studentScoreExample = new StudentScoreExample();
        StudentScoreExample.Criteria criteriaStudentScore = studentScoreExample.createCriteria();
        criteriaStudentScore.andStudentIdEqualTo(req.getStudentId());

        // 根据分页参数进行查询
        List<StudentScore>  studentScoreList = studentScoreMapper.selectByExample(studentScoreExample);

        // 对于查询到的每一条成绩记录，去course表中，根据courseUid查询
        List<StudentScoreQueryResp> list = new ArrayList<>();
        LOG.info("studentScoreList：{}", studentScoreList);
        for (StudentScore studentScore : studentScoreList) {
            String courseUid = studentScore.getCourseUid();
            Course course = courseMapper.selectByPrimaryKey(courseUid);
            StudentScoreQueryResp  studentScoreQueryResp = CopyUtil.copy(studentScore, StudentScoreQueryResp.class);

            if(!ObjectUtils.isEmpty(course)) {
                if(course.getCourseType().equals(req.getCourseType())){
                    studentScoreQueryResp.setCourseName(course.getCourseName());
                    studentScoreQueryResp.setCredit(course.getCredit());
                    studentScoreQueryResp.setSchoolHour(course.getSchoolHour());
                    studentScoreQueryResp.setCourseType(course.getCourseType());
                    studentScoreQueryResp.setYear(course.getYear());
                    studentScoreQueryResp.setTerm(course.getTerm());
                    studentScoreQueryResp.setGpa();

                    list.add(studentScoreQueryResp);
                }
            }
        }

        PageInfo<StudentScoreQueryResp> pageInfo = new PageInfo<>(list);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        PageResp<StudentScoreQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

}
