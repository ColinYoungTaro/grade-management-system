package com.yxxt.gradems.service;

import com.yxxt.gradems.aspect.LogAspect;
import com.yxxt.gradems.config.VarConfig;
import com.yxxt.gradems.domain.*;
import com.yxxt.gradems.exception.BusinessException;
import com.yxxt.gradems.exception.BusinessExceptionCode;
import com.yxxt.gradems.mapper.CourseScheduleMapper;
import com.yxxt.gradems.mapper.ScoreAppealMapper;
import com.yxxt.gradems.mapper.StudentScoreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class AppealStateMachine{
    private static HashMap<Integer, List<Integer>> transitionMap;
    
    static {
        transitionMap = new HashMap<>();
        transitionMap.put(VarConfig.APPEAL_STATUS_INITIAL, Arrays.asList(
                VarConfig.APPEAL_STATUS_REFUSED_BY_TEACHER,
                VarConfig.APPEAL_STATUS_VERIFIED_BY_TEACHER)
        );
        transitionMap.put(VarConfig.APPEAL_STATUS_VERIFIED,null);
        transitionMap.put(VarConfig.APPEAL_STATUS_REFUSED_BY_ADMIN,null);
        transitionMap.put(VarConfig.APPEAL_STATUS_REFUSED_BY_TEACHER,null);
        transitionMap.put(VarConfig.APPEAL_STATUS_VERIFIED_BY_TEACHER, Arrays.asList(
                VarConfig.APPEAL_STATUS_REFUSED_BY_ADMIN,
                VarConfig.APPEAL_STATUS_VERIFIED
        ));

    }
    public static boolean validTransition(int currentState,int nextState){
        List<Integer> nextStates = transitionMap.get(currentState);
        if(nextStates == null){
            return false;
        }
        return nextStates.contains(nextState);
    }
}

@Service
public class AppealService {

    @Resource
    private ScoreAppealMapper scoreAppealMapper;

    @Resource
    private StudentScoreMapper studentScoreMapper;

    @Resource
    private CourseScheduleMapper courseScheduleMapper;


    private final static Logger LOG = LoggerFactory.getLogger(LogAspect.class);
    /**
     * register score
     * @param studentId
     * @param courseUid
     * @param classIndex
     * @param score
     * @brief set the score of the class of a student
     */
    public void registerScore(Long studentId, String courseUid, int classIndex, int score) {
        StudentScore scoreRecord = new StudentScore();
        scoreRecord.setClassIndex(classIndex);
        scoreRecord.setCourseUid(courseUid);
        scoreRecord.setStudentId(studentId);
        scoreRecord.setScore(score);
        studentScoreMapper.insert(scoreRecord);
    }

    /**
     * update Score: alter a student's score of a certain class
     * @param studentId
     * @param courseUid
     * @param classIndex
     * @param score
     * @return
     */
    public void updateScore(Long studentId, String courseUid, Integer classIndex, Short score) {

        StudentScoreExample example = new StudentScoreExample();
        StudentScoreExample.Criteria criteria = example.createCriteria();
        criteria.andClassIndexEqualTo(classIndex)
                .andCourseUidEqualTo(courseUid)
                .andStudentIdEqualTo(studentId);
        List<StudentScore> list = studentScoreMapper.selectByExample(example);
        if(list.size() != 1){
            //lisSystem.out.println("list.size=" + String.valueOf(list.size()));
            throw new BusinessException(BusinessExceptionCode.KEY_NOT_EXIST);
        }
        StudentScore record = list.get(0);
        record.setScore(Integer.valueOf(score));
        int result = studentScoreMapper.updateByExampleSelective(record, example);
        if(result == 0){
            throw new BusinessException(BusinessExceptionCode.DB_OPERATION_ERROR);
        }
    }

    public void updateScoreByAppealId(Long rowId){
        ScoreAppeal appeal = getAppeal(rowId);
        if(appeal == null){
            throw new BusinessException(BusinessExceptionCode.KEY_NOT_EXIST);
        }
        updateScore(appeal.getStudentId(),appeal.getCourseUid(),appeal.getClassIndex(),appeal.getAlteredScore());
    }

    public void appealScore(Long studentId, String courseUid, int classIndex) {
        ScoreAppeal scoreAppealRecord = new ScoreAppeal();
        scoreAppealRecord.setStudentId(studentId);
        scoreAppealRecord.setStatus(VarConfig.APPEAL_STATUS_INITIAL);
        scoreAppealRecord.setCourseUid(courseUid);
        scoreAppealRecord.setClassIndex(classIndex);

        // 查询学生的这门课程是否有分数
        StudentScoreExample studentScoreExample = new StudentScoreExample();
        studentScoreExample.createCriteria()
            .andCourseUidEqualTo(courseUid)
            .andClassIndexEqualTo(classIndex)
            .andStudentIdEqualTo(studentId);
        List<StudentScore> record = studentScoreMapper.selectByExample(studentScoreExample);
        if(record.size() == 0){
            throw new BusinessException(BusinessExceptionCode.KEY_NOT_EXIST);
        }
        // TODO: 存在多条记录
        int score = record.get(0).getScore();
        scoreAppealRecord.setOriginalScore((short)score);
        scoreAppealMapper.insert(scoreAppealRecord);
    }


    private void updateAppeal(Long rowId, int state) {
        ScoreAppeal appeal = scoreAppealMapper.selectByPrimaryKey(rowId);
        if(appeal == null){
            LOG.error("appeal not found rowId = " + rowId.toString());
            throw new BusinessException(BusinessExceptionCode.KEY_NOT_EXIST);
        }
        if(!AppealStateMachine.validTransition(appeal.getStatus(),state)){
            throw new BusinessException(BusinessExceptionCode.INVALID_OPERATION);
        }
        appeal.setStatus(state);
        scoreAppealMapper.updateByPrimaryKeySelective(appeal);
    }

    /**
     * 不同的修改成绩申诉的方式
     * @param rowId
     */
    public void adminRefuseAppeal(Long rowId){
        updateAppeal(rowId, VarConfig.APPEAL_STATUS_REFUSED_BY_ADMIN);
    }

    public void adminVerifyAppeal(Long rowId){
        ScoreAppeal appeal = getAppeal(rowId);
        if(appeal == null || appeal.getAlteredScore() == null || appeal.getAlteredScore() < 0){
            throw new BusinessException(BusinessExceptionCode.KEY_NOT_EXIST);
        }
        updateAppeal(rowId, VarConfig.APPEAL_STATUS_VERIFIED);
        updateScoreByAppealId(rowId);
    }

    public void teacherRefuseAppeal(Long rowId){
        updateAppeal(rowId, VarConfig.APPEAL_STATUS_REFUSED_BY_TEACHER);
    }

    /**
     * 老师同意修改 修改appeal的状态和altered分数
     * @param rowId
     * @param score
     */
    public void teacherVerifyAppeal(Long rowId, Short score){
        ScoreAppeal appeal = getAppeal(rowId);
        if(!AppealStateMachine.validTransition(appeal.getStatus(), VarConfig.APPEAL_STATUS_VERIFIED_BY_TEACHER)){
            throw new BusinessException(BusinessExceptionCode.INVALID_OPERATION);
        }
        appeal.setStatus(VarConfig.APPEAL_STATUS_VERIFIED_BY_TEACHER);
        appeal.setAlteredScore(score);
        scoreAppealMapper.updateByPrimaryKeySelective(appeal);
    }

    public List<ScoreAppeal> getAppealsOfStudent(Long studentId) {
        ScoreAppealExample example = new ScoreAppealExample();
        ScoreAppealExample.Criteria criteria = example.createCriteria();
        criteria.andStudentIdEqualTo(studentId);
        return scoreAppealMapper.selectByExample(example);
    }


    public List<ScoreAppeal> getAppealsOfClass(String courseUid, int classIndex) {
        ScoreAppealExample example = new ScoreAppealExample();
        ScoreAppealExample.Criteria criteria = example.createCriteria();
        criteria.andCourseUidGreaterThan(courseUid).andClassIndexEqualTo(classIndex);
        return scoreAppealMapper.selectByExample(example);
    }


    public List<ScoreAppeal> getAllAppeals() {
        return scoreAppealMapper.selectByExample(null);
    }

    public ScoreAppeal getAppeal(Long rowId){
        ScoreAppeal appeal = scoreAppealMapper.selectByPrimaryKey(rowId);
        if(appeal == null)
            throw new BusinessException(BusinessExceptionCode.KEY_NOT_EXIST);
        return appeal;
    }
    public List<ScoreAppeal> getAppealsOfTeacher(Long teacherId) {
        CourseScheduleExample example = new CourseScheduleExample();
        CourseScheduleExample.Criteria criteria = example.createCriteria();
        criteria.andTeacherIdEqualTo(teacherId);
        List<CourseSchedule> scheduleRecords = courseScheduleMapper.selectByExample(example);
        List<ScoreAppeal> appeals = new ArrayList<>();
        for(CourseSchedule record : scheduleRecords){
            int classIndex = record.getClassIndex();
            String courseUid = record.getCourseUid();
            ScoreAppealExample schoolAppealExample = new ScoreAppealExample();
            ScoreAppealExample.Criteria schoolAppealCriteria = schoolAppealExample.createCriteria();
            schoolAppealCriteria.andClassIndexEqualTo(classIndex).andCourseUidEqualTo(courseUid);
            List<ScoreAppeal> courseAppeals = scoreAppealMapper.selectByExample(schoolAppealExample);
            appeals.addAll(courseAppeals);
        }
        return appeals;
    }
}
