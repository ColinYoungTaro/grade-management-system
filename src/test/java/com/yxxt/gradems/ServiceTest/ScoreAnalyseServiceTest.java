package com.yxxt.gradems.ServiceTest;

import com.yxxt.gradems.config.GrademsApplication;
import com.yxxt.gradems.service.ScoreAnalyseService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@SpringBootTest(classes = GrademsApplication.class)
@RunWith(SpringRunner.class)
public class ScoreAnalyseServiceTest {

    @Resource
    private ScoreAnalyseService scoreAnalyseService;

    @Test
    public void getAnalysis(){
        float gpa = scoreAnalyseService.calcGPA(3180104668L);
        Pair<Integer, Integer> pair = scoreAnalyseService.getRank(3180104668L);
        int rank = pair.getFirst();
        int total = pair.getSecond();
        Assert.assertEquals(1,rank);
        Assert.assertEquals(1,total);
    }

    @Test
    @Transactional
    @Rollback
    public void scoreListTest() throws Exception {
        
    }
}
