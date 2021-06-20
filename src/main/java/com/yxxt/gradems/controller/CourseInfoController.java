package com.yxxt.gradems.controller;

import com.yxxt.gradems.domain.*;
import com.yxxt.gradems.resp.CommonResp;
import com.yxxt.gradems.resp.CourseScheduleResp;
import com.yxxt.gradems.service.CourseInfoService;
import com.yxxt.gradems.service.MajorService;
import com.yxxt.gradems.service.SchoolUserService;
import com.yxxt.gradems.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    public CommonResp getTrainingProgramCourses(@PathVariable("majorId")Integer majorId){
        List<Course> courses = courseInfoService.getTrainingProgram(majorId);
        if(courses == null){
            return CommonResp.error("没有查询到相关信息");
        }
        else{
            return CommonResp.success(null,courses);
        }
    }

    @RequestMapping ("/recommendedClass/{studentId}")
    public CommonResp getRecommendedClasses(@PathVariable("studentId") Long studentId){

        Student student = studentService.getStudentById(studentId);
        if(student == null){
            CommonResp.error("没有该学生");
        }
        Integer majorId = student.getMajorId();
        Major major = majorService.getMajorById(majorId);
        List<Course> courses = majorService.getTrainingProgramCourses(majorId);
        List<CourseScheduleResp> resultSet = new ArrayList<>();
        for(Course course : courses){
            List<CourseSchedule> scheduleList = majorService.getScheduledClassesByCourse(course);
            for(CourseSchedule scheduleRecord : scheduleList){
                Long id = scheduleRecord.getTeacherId();
                SchoolUser user = schoolUserService.getSchoolUser(id);
                CourseScheduleResp respContent = new CourseScheduleResp();
                respContent.setCourse(course);
                respContent.setTeacher(user);
                respContent.setSchedule(scheduleRecord);
                resultSet.add(respContent);
            }
        }
        return CommonResp.success("获得可选课程成功",resultSet);
    }

}
