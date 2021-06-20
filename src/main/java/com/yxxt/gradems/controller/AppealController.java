package com.yxxt.gradems.controller;

import com.yxxt.gradems.domain.Course;
import com.yxxt.gradems.domain.SchoolUser;
import com.yxxt.gradems.domain.ScoreAppeal;
import com.yxxt.gradems.resp.CommonResp;
import com.yxxt.gradems.resp.AppealResp;
import com.yxxt.gradems.service.AppealService;
import com.yxxt.gradems.service.CourseInfoService;
import com.yxxt.gradems.service.SchoolUserService;
import com.yxxt.gradems.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

class ScoreInfoReq{
    Long studentId;
    String courseUid;
    Integer classIndex;
    Integer score;
    public Long getStudentId() {
        return studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public String getCourseUid() {
        return courseUid;
    }
    public void setCourseUid(String courseUid) {
        this.courseUid = courseUid;
    }
    public Integer getClassIndex() {
        return classIndex;
    }
    public void setClassIndex(Integer classIndex) {
        this.classIndex = classIndex;
    }
    public Integer getScore() {
        return score;
    }
    public void setScore(Integer score) {
        this.score = score;
    }
}

class AppealReq{
    Long rowId;
    Short score;
    boolean agree;

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }

    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    public Short getScore() {
        return score;
    }

    public void setScore(Short score) {
        this.score = score;
    }

}


@RestController
@RequestMapping("/appeal")
public class AppealController {

    @Resource
    private AppealService appealService;

    @Resource
    private CourseInfoService courseService;

    @Resource
    private SchoolUserService schoolUserService;

    /**
     * 申诉成绩
     * @param req
     */
    @PostMapping("/student_appeal")
    public CommonResp appealScore(@RequestBody ScoreInfoReq req){
        appealService.appealScore(
                req.getStudentId(),
                req.getCourseUid(),
                req.getClassIndex()
        );
        return CommonResp.success("成功提交申诉",null);
    }

    private List<AppealResp> wrapAppeals(List<ScoreAppeal> appeals){
        List<AppealResp> content = new ArrayList<AppealResp>();

        for(ScoreAppeal appeal : appeals){
            String courseUid = appeal.getCourseUid();
            Course course = courseService.getCourseByUid(courseUid);
            SchoolUser schoolUser = schoolUserService.getSchoolUser(appeal.getStudentId());
            if(schoolUser == null){
                return null;
            }
            AppealResp item = new AppealResp();

            item.setAppeal(appeal);
            item.setCourse(course);
            item.setStudentId(schoolUser.getUserId());
            item.setStudentName(schoolUser.getUserName());

            content.add(item);
        }
        return content;
    }

    /**
     * 获得所有teacher可见的所有appeal
     * @param teacherId
     * @return
     */
    @RequestMapping(value="/teacher_appeals/{teacherId}")
    public CommonResp getAppealsOfTeacher(@PathVariable("teacherId") Long teacherId){
        List<ScoreAppeal> appeals = appealService.getAppealsOfTeacher(teacherId);
        List<AppealResp> content = wrapAppeals(appeals);
        if(content == null)
            return CommonResp.error("申诉功能内部错误");
        return CommonResp.success(null,content);
    }

    @RequestMapping(value="/student_appeals/{studentId}")
    public CommonResp getAppealsOfStudent(@PathVariable("studentId") Long studentId){
        List<ScoreAppeal> appeals = appealService.getAppealsOfStudent(studentId);
        List<AppealResp> content = wrapAppeals(appeals);
        if(content == null)
            return CommonResp.error("申诉功能内部错误");
        return CommonResp.success(null,content);
    }
    //
    @RequestMapping(value="/appeals")
    public CommonResp getAllAppeals(){
        List<ScoreAppeal> appeals = appealService.getAllAppeals();
        List<AppealResp> content = wrapAppeals(appeals);
        if(content == null)
            return CommonResp.error("申诉功能内部错误");
        return CommonResp.success(null,content);
    }

    /**
     * 老师确认是否同意
     * @param req
     */
    @PostMapping(value="appeal_handle/teacher")
    public CommonResp teacherHandleAppeal(@RequestBody AppealReq req){
        boolean isAgree = req.isAgree();
        if(isAgree){
            appealService.teacherVerifyAppeal(req.getRowId(),req.getScore());
        }
        else{
            appealService.teacherRefuseAppeal(req.getRowId());
        }
        return CommonResp.success("申请已提交","同意意见：" + String.valueOf(req.isAgree()));
    }

    @PostMapping(value="appeal_handle/admin")
    public CommonResp adminHandleAppeal(@RequestBody AppealReq req){
        boolean isAgree = req.isAgree();
        if(isAgree){
            appealService.adminVerifyAppeal(req.getRowId());
            // ScoreAppeal appeal = appealService.getAppeal(req.getRowId());
        }
        else{
            appealService.adminRefuseAppeal(req.getRowId());
        }
        return CommonResp.success("申请已提交","同意意见：" + String.valueOf(req.isAgree()));
    }

}

