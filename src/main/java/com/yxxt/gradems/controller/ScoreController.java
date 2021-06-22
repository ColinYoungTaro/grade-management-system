package com.yxxt.gradems.controller;

import com.yxxt.gradems.domain.Course;
import com.yxxt.gradems.domain.StudentScore;
import com.yxxt.gradems.req.TeacherClassStudentsQueryReq;
import com.yxxt.gradems.resp.AnalysisResp;
import com.yxxt.gradems.resp.CommonResp;
import com.yxxt.gradems.service.ScoreAnalyseService;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

class ClassReq{
    public String courseUid;
    public int classIndex;
}

@RestController
@RequestMapping("/score")
public class ScoreController {
    @Resource
    private ScoreAnalyseService scoreAnalyseService;

    @RequestMapping("/analysis/student/{studentId}")
    public CommonResp getScoreAnalysis(@PathVariable("studentId")Long studentId){
        float gpa = scoreAnalyseService.calcGPA(studentId);
        float credit = scoreAnalyseService.getCredit(studentId);
        Pair<Integer,Integer> rankPair = scoreAnalyseService.getRank(studentId);
        List<Course> failedCourses = scoreAnalyseService.getFailedCourses();
        AnalysisResp resp = new AnalysisResp();
        resp.setCredit(credit);
        resp.setGPA(gpa);
        resp.setRank(rankPair.getFirst());
        resp.setTotal(rankPair.getSecond());
        resp.setFailedCourses(failedCourses);
        return CommonResp.success("获得成绩分析成功",resp);
    }

    @GetMapping("/analysis/class")
    public CommonResp ClassAnalysis(TeacherClassStudentsQueryReq req){
        List<StudentScore> scoreList = scoreAnalyseService.getStudentScoresOfClass(req.getCourseUid(),req.getClassIndex());
        List<Integer> scores = scoreList.stream().map(StudentScore::getScore).collect(Collectors.toList());
        return CommonResp.success("",scores);
    }

}
