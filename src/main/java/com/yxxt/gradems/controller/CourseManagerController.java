package com.yxxt.gradems.controller;


import com.yxxt.gradems.req.CourseQueryReq;
import com.yxxt.gradems.resp.CommonResp;
import com.yxxt.gradems.resp.CourseQueryResp;
import com.yxxt.gradems.resp.PageResp;
import com.yxxt.gradems.service.CourseManageService;
import com.yxxt.gradems.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/course")
public class CourseManagerController {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolUserController.class);

    @Resource
    private CourseManageService courseManageService;

    @Resource
    private SnowFlake snowFlake;

    @RequestMapping("/list")
    public CommonResp courseList(@Valid CourseQueryReq req) {
        CommonResp<PageResp<CourseQueryResp>> resp = new CommonResp<>();
        PageResp<CourseQueryResp> list = courseManageService.courseList(req);
        resp.setContent(list);
        return resp;
    }


    @DeleteMapping("/delete/{courseUid}")
    public CommonResp deleteCourse(@PathVariable String courseUid) {
        CommonResp resp = new CommonResp<>();
        courseManageService.deleteCourse(courseUid);
        return resp;
    }


}
