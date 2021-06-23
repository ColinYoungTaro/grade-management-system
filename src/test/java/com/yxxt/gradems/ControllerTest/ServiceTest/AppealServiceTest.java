package com.yxxt.gradems.ControllerTest.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.annotation.Resource;

import com.yxxt.gradems.config.GrademsApplication;
import com.yxxt.gradems.config.VarConfig;
import com.yxxt.gradems.domain.ScoreAppeal;
import com.yxxt.gradems.domain.StudentScore;
import com.yxxt.gradems.service.AppealService;
import com.yxxt.gradems.service.ScoreAnalyseService;
import com.yxxt.gradems.service.StudentScoreService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest(classes = GrademsApplication.class)
@RunWith(SpringRunner.class)
public class AppealServiceTest {

    @Resource
    private AppealService appealService;

    @Resource
    private ScoreAnalyseService scoreAnalyseService;

    @Test
    @Transactional
    @Rollback
    public void TeacherVerify(){
        appealService.teacherVerifyAppeal(1L,(short)98);
        ScoreAppeal appeal = appealService.getAppeal(1L);
        Assert.assertEquals((int)98,(int)appeal.getAlteredScore());
        Assert.assertEquals((Integer)VarConfig.APPEAL_STATUS_VERIFIED_BY_TEACHER,appeal.getStatus());
    }

    @Test
    @Transactional
    @Rollback
    public void TeacheRefuse(){
        appealService.teacherRefuseAppeal(1L);
        ScoreAppeal appeal = appealService.getAppeal(1L);
        Assert.assertEquals((Integer)VarConfig.APPEAL_STATUS_REFUSED_BY_TEACHER,appeal.getStatus());
    }

    @Test
    @Transactional
    @Rollback
    public void AdminRefuses(){
        appealService.teacherVerifyAppeal(1L,(short)100);
        appealService.adminVerifyAppeal(1L);
        ScoreAppeal appeal = appealService.getAppeal(1L);
        Assert.assertEquals((Integer)VarConfig.APPEAL_STATUS_VERIFIED,appeal.getStatus());
        Assert.assertEquals((short)100, (short)appeal.getAlteredScore());
        Integer score = scoreAnalyseService
                                .getClassScoreOfStudent(
                                    appeal.getStudentId(),
                                    appeal.getCourseUid(),
                                    appeal.getClassIndex()
                                );
        Assert.assertEquals((short)100, score.shortValue());
    }
}
