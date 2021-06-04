package com.yxxt.gradems.controller;

import com.yxxt.gradems.req.StudentReq;
import com.yxxt.gradems.resp.CommonResp;
import com.yxxt.gradems.resp.StudentResp;
import com.yxxt.gradems.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentService studentService;

    @GetMapping("/list")
    public CommonResp list(StudentReq req) {
        CommonResp<List<StudentResp>> resp = new CommonResp<>();
        List<StudentResp> list = studentService.list(req);
        resp.setContent(list);
        return resp;
    }
}
