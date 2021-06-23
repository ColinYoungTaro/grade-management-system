package com.yxxt.gradems.controller;

import com.yxxt.gradems.domain.Course;
import com.yxxt.gradems.req.StudentQueryCourseReq;
import com.yxxt.gradems.resp.CommonResp;
import com.yxxt.gradems.resp.StudentQueryCourseResp;
import com.yxxt.gradems.service.CourseInfoService;
import com.yxxt.gradems.service.MajorService;
import com.yxxt.gradems.service.SchoolUserService;
import com.yxxt.gradems.service.StudentService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/course_info")
public class CourseInfoController {
    @Resource
    private CourseInfoService courseInfoService;

    @Resource
    private MajorService majorService;

    @Resource
    private StudentService studentService;

    @Resource
    private SchoolUserService schoolUserService;

    @RequestMapping("/training_program/{majorId}")
    public CommonResp getTrainingProgramCourses(@PathVariable("majorId") Integer majorId){
        List<Course> courses = courseInfoService.getTrainingProgram(majorId);
        if(courses == null){
            return CommonResp.error("没有查询到相关信息");
        }
        else{
            return CommonResp.success(null,courses);
        }
    }

    @RequestMapping ("/recommendedClass")
    public CommonResp getRecommendedClasses(@Valid StudentQueryCourseReq req){
        CommonResp<List<StudentQueryCourseResp>> resp = new CommonResp<>();
        List<StudentQueryCourseResp> recommendedCourseList = studentService.getRecommendedCourse(req);
        resp.setContent(recommendedCourseList);
        return resp;
    }

}