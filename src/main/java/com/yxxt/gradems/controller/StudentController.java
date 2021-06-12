package com.yxxt.gradems.controller;

import com.yxxt.gradems.req.StudentQueryReq;
import com.yxxt.gradems.req.StudentSaveReq;
import com.yxxt.gradems.resp.CommonResp;
import com.yxxt.gradems.resp.PageResp;
import com.yxxt.gradems.resp.StudentQueryResp;
import com.yxxt.gradems.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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

}
