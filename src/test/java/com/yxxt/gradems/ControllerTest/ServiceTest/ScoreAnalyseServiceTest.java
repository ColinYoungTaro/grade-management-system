package com.yxxt.gradems.ControllerTest.ServiceTest;

import javax.annotation.Resource;

import com.yxxt.gradems.config.GrademsApplication;
import com.yxxt.gradems.service.ScoreAnalyseService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.junit4.statements.SpringRepeat;
import org.springframework.data.util.Pair;

@SpringBootTest(classes = GrademsApplication.class)
@RunWith(SpringRunner.class)
public class ScoreAnalyseServiceTest {

    @Resource
    private ScoreAnalyseService scoreAnalyseService;

    @Test
    public void getAnalysis(){
        float gpa = scoreAnalyseService.calcGPA(3180104668L);
        Pair<Integer,Integer> pair = scoreAnalyseService.getRank(3180104668L);
        int rank = pair.getFirst();
        int total = pair.getSecond();
        Assert.assertEquals(1,rank);
        Assert.assertEquals(1,total);
    }
}
