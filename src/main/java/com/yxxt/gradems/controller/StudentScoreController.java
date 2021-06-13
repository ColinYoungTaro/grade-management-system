package com.yxxt.gradems.controller;

import com.yxxt.gradems.req.StudentScoreQueryReq;
import com.yxxt.gradems.resp.CommonResp;
import com.yxxt.gradems.resp.PageResp;
import com.yxxt.gradems.resp.StudentScoreQueryResp;
import com.yxxt.gradems.service.StudentScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/studentscore")
public class StudentScoreController {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolUserController.class);

    @Resource
    private StudentScoreService studentScoreService;

    @GetMapping("/whole/list")
    public CommonResp wholeList(@Valid StudentScoreQueryReq req) {
        CommonResp<PageResp<StudentScoreQueryResp>> resp = new CommonResp<>();
        PageResp<StudentScoreQueryResp> list = studentScoreService.wholeList(req);
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/single/year/list")
    public CommonResp singleYearList(@Valid StudentScoreQueryReq req) {
        CommonResp<PageResp<StudentScoreQueryResp>> resp = new CommonResp<>();
        PageResp<StudentScoreQueryResp> list = studentScoreService.singleYearOrTermList(req);
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/single/term/list")
    public CommonResp singleTermList(@Valid StudentScoreQueryReq req) {
        CommonResp<PageResp<StudentScoreQueryResp>> resp = new CommonResp<>();
        PageResp<StudentScoreQueryResp> list = studentScoreService.singleYearOrTermList(req);
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/course/type/list")
    public CommonResp courseTypeList(@Valid StudentScoreQueryReq req) {
        CommonResp<PageResp<StudentScoreQueryResp>> resp = new CommonResp<>();
        PageResp<StudentScoreQueryResp> list = studentScoreService.courseTypeList(req);
        resp.setContent(list);
        return resp;
    }

}
