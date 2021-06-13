package com.yxxt.gradems.controller;

import com.yxxt.gradems.req.*;
import com.yxxt.gradems.resp.*;
import com.yxxt.gradems.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentService studentService;

    @GetMapping("/list")
    public CommonResp list(@Valid StudentQueryReq req) {
        CommonResp<PageResp<StudentQueryResp>> resp = new CommonResp<>();
        PageResp<StudentQueryResp> list = studentService.list(req);
        resp.setContent(list);
        return resp;
    }

    /*
    *  前端用户管理：编辑学生信息，保存信息的接口
    *  前端接收到保存成功后，会去刷新列表，不需要返回任何东西
    * */
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody StudentSaveReq req) {
        CommonResp resp = new CommonResp<>();
        studentService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{userId}")
    public CommonResp delete(@PathVariable Long userId) {
        CommonResp resp = new CommonResp<>();
        studentService.delete(userId);
        return resp;
    }

    @GetMapping("/information")
    public CommonResp getStudentInformation(@Valid SchoolStudentUserQueryReq req) {
        CommonResp<StudentInformationQueryResp> resp = new CommonResp<>();
        StudentInformationQueryResp studentInformation = studentService.getStudentInformation(req);
        resp.setContent(studentInformation);
        return resp;
    }

    @PostMapping("/information/save")
    public CommonResp studentInformationSave(@Valid @RequestBody StudentInformationSaveReq req) {
        CommonResp resp = new CommonResp<>();
        studentService.studentInformationSave(req);
        return resp;
    }

    /**
     *
     * req: currentYear currentTerm
     */
    @GetMapping("/get/all/course")
    public CommonResp getAllCourse(@Valid StudentQueryCourseReq req) {
        CommonResp<List<StudentQueryCourseResp>> resp = new CommonResp<>();
        List<StudentQueryCourseResp> allCourseList = studentService.getAllCourse(req);
        resp.setContent(allCourseList);
        return resp;
    }

    @PostMapping("/select/course")
    public CommonResp studentSelectCourse(@Valid @RequestBody StudentSelectCourseReq req) {
        CommonResp resp = new CommonResp<>();
        Integer selectStatus = studentService.studentSelectCourse(req);
        if(selectStatus==0){
            resp.setSuccess(false);
            resp.setMessage("选课失败：已修读该课程");
        }else if(selectStatus==1){
            resp.setSuccess(false);
            resp.setMessage("选课失败：已选上该课程的教学班");
        }else if(selectStatus==2){
            resp.setMessage("选课成功");
        }
        return resp;
    }

    @DeleteMapping("/unselect/course/{studentId}/{courseUid}/{classIndex}")
    public CommonResp unselectCourse(@Valid @PathVariable Long studentId, @Valid @PathVariable String courseUid, @Valid @PathVariable Integer classIndex) {
        CommonResp resp = new CommonResp<>();
        Integer unselectStatus = studentService.unselectCourse(studentId, courseUid, classIndex);
        if(unselectStatus==0){
            resp.setMessage("退课成功");
        }else if(unselectStatus==1){
            resp.setSuccess(false);
            resp.setMessage("退课失败：已结课");
        }
        return resp;
    }

//
//    @GetMapping("/show/major/course")
//    public CommonResp getMajorCourse(@Valid SelectCourseReq req) {
//        CommonResp<List<QueryMajorCourseResp>> resp = new CommonResp<>();
//        List<QueryMajorCourseResp> majorCourseList = studentService.getMajorCourse(req);
//        resp.setContent(majorCourseList);
//        return resp;
//    }
//
//    @GetMapping("/show/general/course")
//    public CommonResp getGeneralCourse(@Valid SelectCourseReq req) {
//        CommonResp<List<QueryGeneralCourseResp>> resp = new CommonResp<>();
//        List<QueryGeneralCourseResp> generalCourseList = studentService.getGeneralCourse(req);
//        resp.setContent(generalCourseList);
//        return resp;
//    }




}
