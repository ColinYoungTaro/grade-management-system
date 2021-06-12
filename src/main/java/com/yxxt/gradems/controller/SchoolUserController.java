package com.yxxt.gradems.controller;

import com.alibaba.fastjson.JSONObject;
import com.yxxt.gradems.req.*;
import com.yxxt.gradems.resp.*;
import com.yxxt.gradems.service.SchoolUserService;
import com.yxxt.gradems.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/schooluser")
public class SchoolUserController {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolUserController.class);

    @Resource
    private SchoolUserService schooluserService;


    @Resource
    private SnowFlake snowFlake;

    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping("/manager/list")
    public CommonResp managerList(@Valid SchoolManagerUserQueryReq req) {
        CommonResp<PageResp<SchoolManagerUserQueryResp>> resp = new CommonResp<>();
        PageResp<SchoolManagerUserQueryResp> list = schooluserService.managerList(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/manager/save")
    public CommonResp managerSave(@Valid @RequestBody SchoolManagerUserSaveReq req) {
        req.setPasswordEncode(DigestUtils.md5DigestAsHex(req.getPasswordEncode().getBytes()));
        CommonResp resp = new CommonResp<>();
        schooluserService.managerSave(req);
        return resp;
    }

    @GetMapping("/student/list")
    public CommonResp studentList(@Valid SchoolStudentUserQueryReq req) {
        CommonResp<PageResp<SchoolStudentUserQueryResp>> resp = new CommonResp<>();
        PageResp<SchoolStudentUserQueryResp> list = schooluserService.studentList(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/student/save")
    public CommonResp studentSave(@Valid @RequestBody SchoolStudentUserSaveReq req) {
        req.setPasswordEncode(DigestUtils.md5DigestAsHex(req.getPasswordEncode().getBytes()));
        CommonResp resp = new CommonResp<>();
        schooluserService.studentSave(req);
        return resp;
    }

    @GetMapping("/teacher/list")
    public CommonResp teacherList(@Valid SchoolTeacherUserQueryReq req) {
        CommonResp<PageResp<SchoolTeacherUserQueryResp>> resp = new CommonResp<>();
        PageResp<SchoolTeacherUserQueryResp> list = schooluserService.teacherList(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/teacher/save")
    public CommonResp teacherSave(@Valid @RequestBody SchoolTeacherUserSaveReq req) {
        req.setPasswordEncode(DigestUtils.md5DigestAsHex(req.getPasswordEncode().getBytes()));
        CommonResp resp = new CommonResp<>();
        schooluserService.teacherSave(req);
        return resp;
    }


    @DeleteMapping("/delete/{userId}")
    public CommonResp delete(@PathVariable Long userId) {
        CommonResp resp = new CommonResp<>();
        schooluserService.delete(userId);
        return resp;
    }

    @PostMapping("/login")
    public CommonResp login(@Valid @RequestBody SchoolUserLoginReq req) {
        req.setPasswordEncode(DigestUtils.md5DigestAsHex(req.getPasswordEncode().getBytes()));
        CommonResp<SchoolUserLoginResp> resp = new CommonResp<>();
        SchoolUserLoginResp schoolUserLoginResp = schooluserService.login(req);

        Long token = snowFlake.nextId();
        LOG.info("生成单点登录token：{}，存放至redis", token);
        schoolUserLoginResp.setToken(token.toString());
        redisTemplate.opsForValue().set(token.toString(), JSONObject.toJSONString(schoolUserLoginResp), 3600 * 24, TimeUnit.SECONDS);

        resp.setContent(schoolUserLoginResp);
        return resp;
    }

    @GetMapping("/logout/{token}")
    public CommonResp logout(@PathVariable String token) {
        CommonResp resp = new CommonResp<>();
        redisTemplate.delete(token);
        LOG.info("从redis中删除token: {}", token);
        return resp;
    }

}
