package com.yxxt.gradems.controller;

import com.sun.tools.javac.util.Pair;
import com.yxxt.gradems.domain.Course;
import com.yxxt.gradems.domain.StudentScore;
import com.yxxt.gradems.resp.AnalysisResp;
import com.yxxt.gradems.resp.CommonResp;
import com.yxxt.gradems.service.ScoreAnalyseService;
import com.yxxt.gradems.service.StudentScoreService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
        resp.setRank(rankPair.fst);
        resp.setTotal(rankPair.snd);
        resp.setFailedCourses(failedCourses);
        return CommonResp.success("",resp);
    }

    @PostMapping("/analysis/class")
    public CommonResp ClassAnalysis(@RequestBody ClassReq req){
        List<StudentScore> scoreList = scoreAnalyseService.getStudentScoresOfClass(req.courseUid,req.classIndex);
        List<Integer> scores = scoreList.stream().map(StudentScore::getScore).collect(Collectors.toList());
        return CommonResp.success("",scores);
    }

}
