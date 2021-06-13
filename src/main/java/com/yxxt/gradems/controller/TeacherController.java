package com.yxxt.gradems.controller;

import com.yxxt.gradems.req.*;
import com.yxxt.gradems.resp.*;
import com.yxxt.gradems.service.StudentService;
import com.yxxt.gradems.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    private StudentService studentService;

    @Resource
    private TeacherService teacherService;


    @GetMapping("/information")
    public CommonResp getTeacherInformation(@Valid SchoolTeacherUserQueryReq req) {
        CommonResp<TeacherInformationQueryResp> resp = new CommonResp<>();
        TeacherInformationQueryResp teacherInformation = teacherService.getTeacherInformation(req);
        resp.setContent(teacherInformation);
        return resp;
    }

    @PostMapping("/information/save")
    public CommonResp teacherInformationSave(@Valid @RequestBody TeacherInformationSaveReq req) {
        CommonResp resp = new CommonResp<>();
        teacherService.teacherInformationSave(req);
        return resp;
    }

    @GetMapping("/class/list")
    public CommonResp teacherClassList(@Valid TeacherCourseClassQueryReq req) {
        CommonResp<PageResp<TeacherCourseClassQueryResp>> resp = new CommonResp<>();
        PageResp<TeacherCourseClassQueryResp> list = teacherService.teacherClassList(req);
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/courseClass/student/list")
    public CommonResp courseClassStudentList(@Valid TeacherClassStudentsQueryReq req) {
        CommonResp<List<TeacherClassStudentsQueryResp>> resp = new CommonResp<>();
        List<TeacherClassStudentsQueryResp> list = teacherService.courseClassStudentList(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/commit/score")
    public CommonResp teacherCommitScore(@Valid @RequestBody List<TeacherCommitScoreReq> req) {
        CommonResp resp = new CommonResp<>();
        Integer status = teacherService.teacherCommitScore(req);
        if(status==0){
            resp.setMessage("提交成功");
        }
        return resp;
    }


}
