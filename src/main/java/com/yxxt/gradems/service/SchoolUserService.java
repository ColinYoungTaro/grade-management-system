package com.yxxt.gradems.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yxxt.gradems.domain.SchoolUser;
import com.yxxt.gradems.domain.SchoolUserExample;
import com.yxxt.gradems.domain.Student;
import com.yxxt.gradems.domain.Teacher;
import com.yxxt.gradems.exception.BusinessException;
import com.yxxt.gradems.exception.BusinessExceptionCode;
import com.yxxt.gradems.mapper.SchoolUserMapper;
import com.yxxt.gradems.mapper.StudentMapper;
import com.yxxt.gradems.mapper.TeacherMapper;
import com.yxxt.gradems.req.*;
import com.yxxt.gradems.resp.*;
import com.yxxt.gradems.util.CopyUtil;
import com.yxxt.gradems.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.yxxt.gradems.config.VarConfig.*;

@Service
public class SchoolUserService {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolUserService.class);

    @Resource
    private SchoolUserMapper schooluserMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private SnowFlake snowFlake;

    /**
     * 多表查询：查询管理员信息
     * @param req
     * @return
     */
    public PageResp<SchoolManagerUserQueryResp> managerList(SchoolManagerUserQueryReq req) {

        // 获取所有学校用户，返回到schoolUserList中
        SchoolUserExample schooluserExample = new SchoolUserExample();
        SchoolUserExample.Criteria criteriaSchoolUser = schooluserExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getUserId())) {
            criteriaSchoolUser.andUserIdEqualTo(req.getUserId());
        }
        criteriaSchoolUser.andRoleIdEqualTo(ADMIN);
        PageHelper.startPage(req.getPage(), req.getSize());
        List<SchoolUser>  schoolUserList = schooluserMapper.selectByExample(schooluserExample);

        // 对于查询到的每一个教师用户，去student表中，根据userId查询是否有对应记录
        List<SchoolManagerUserQueryResp> list = CopyUtil.copyList(schoolUserList, SchoolManagerUserQueryResp.class);
        for(SchoolManagerUserQueryResp schoolUser: list){
            schoolUser.setStrGender(schoolUser.getGender()?"男":"女");
        }
        PageInfo<SchoolUser> pageInfo = new PageInfo<>(schoolUserList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        PageResp<SchoolManagerUserQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    /**
     * 保存教师
     * 新增教师：前端只新增school user的内容
     * 若teacher表中已有该学生，但未加入school user。则新增school user
     *
     */
    public void managerSave(SchoolManagerUserSaveReq req) {
        SchoolUser schoolUser = CopyUtil.copy(req, SchoolUser.class);

        SchoolUser schoolUserQuery = schooluserMapper.selectByPrimaryKey(req.getUserId());

        // Manager user 的 rowId为空
        if (ObjectUtils.isEmpty(req.getRowId())) {
            SchoolUser schoolUserDB = selectByUserId(req.getUserId());
            if(ObjectUtils.isEmpty(schoolUserDB)){
                // 新增：用户账号不存在，新增该管理员
                schoolUser.setRoleId(ADMIN);
                schoolUser.setRowId(snowFlake.nextId());
                schooluserMapper.insert(schoolUser);
            } else{
                // 用户名已存在，无法新增
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
        } else {
            // 更新：编辑保存，已存在该教师
            schoolUser.setRowId(schoolUserQuery.getRowId());
            schoolUser.setRoleId(schoolUserQuery.getRoleId());

            schoolUser.setPasswordEncode(null);
            schooluserMapper.updateByPrimaryKeySelective(schoolUser);
        }
    }



    /**
     * 多表查询：查询学生信息
     * @param req
     * @return
     */
    public PageResp<SchoolStudentUserQueryResp> studentList(SchoolStudentUserQueryReq req) {

        // 获取所有学校用户，返回到schoolUserList中
        SchoolUserExample schooluserExample = new SchoolUserExample();
        SchoolUserExample.Criteria criteriaSchoolUser = schooluserExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getUserId())) {
            criteriaSchoolUser.andUserIdEqualTo(req.getUserId());
        }
        criteriaSchoolUser.andRoleIdEqualTo(STUDENT);
        PageHelper.startPage(req.getPage(), req.getSize());
        List<SchoolUser>  schoolUserList = schooluserMapper.selectByExample(schooluserExample);

        // 对于查询到的每一个学校用户，去student表中，根据userId查询是否有对应记录
        List<SchoolStudentUserQueryResp> list = new ArrayList<>();
        LOG.info("schoolList：{}", schoolUserList);
        for (SchoolUser schoolUser : schoolUserList) {
            Long schoolUserId = schoolUser.getUserId();
            Student student = studentMapper.selectByPrimaryKey(schoolUserId);
            SchoolStudentUserQueryResp  schoolStudentUserQueryResp = CopyUtil.copy(schoolUser, SchoolStudentUserQueryResp.class);
            schoolStudentUserQueryResp.setStrGender(schoolUser.getGender()?"男":"女");
            if(!ObjectUtils.isEmpty(student)) {
                schoolStudentUserQueryResp.setMajorId(student.getMajorId());
                schoolStudentUserQueryResp.setGrade(student.getGrade());
                schoolStudentUserQueryResp.setStatus(student.getStatus());
                schoolStudentUserQueryResp.setAdminClassId(student.getAdminClassId());
                schoolStudentUserQueryResp.setEnterYear(student.getEnterYear());
            }
            list.add(schoolStudentUserQueryResp);
        }

        PageInfo<SchoolUser> pageInfo = new PageInfo<>(schoolUserList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        PageResp<SchoolStudentUserQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }


    /**
     * 保存学生
     * 新增学生：前端只新增school user的内容
     * 若student表中已有该学生，但未加入school user。则新增school user
     * 若student
     *
     */
    public void studentSave(SchoolStudentUserSaveReq req) {
        SchoolUser schoolUser = CopyUtil.copy(req, SchoolUser.class);
        Student student = CopyUtil.copy(req, Student.class);

        SchoolUser schoolUserQuery = schooluserMapper.selectByPrimaryKey(req.getUserId());
        Student studentQuery = studentMapper.selectByPrimaryKey(req.getUserId());

        // school user 的 rowId为空
        if (ObjectUtils.isEmpty(req.getRowId())) {
            SchoolUser schoolUserDB = selectByUserId(req.getUserId());
            if(ObjectUtils.isEmpty(schoolUserDB)){
                // 新增：用户账号不存在，新增该学生
                schoolUser.setRoleId(STUDENT);
                schoolUser.setRowId(snowFlake.nextId());
                schooluserMapper.insert(schoolUser);

                if (ObjectUtils.isEmpty(studentQuery)) {
                    // 新增：school user已存在，但不存在student
                    student.setRowId(snowFlake.nextId());
                    studentMapper.insert(student);
                } else {
                    // 更新：编辑保存，已存在该学生
                    student.setRowId(studentQuery.getRowId());
                    studentMapper.updateByPrimaryKey(student);
                }
            } else{
                // 用户名已存在，无法新增
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
        } else {
            // 更新：编辑保存，已存在该学生
            schoolUser.setRowId(schoolUserQuery.getRowId());
            schoolUser.setRoleId(schoolUserQuery.getRoleId());

            schoolUser.setPasswordEncode(null);
            schooluserMapper.updateByPrimaryKeySelective(schoolUser);
            if (ObjectUtils.isEmpty(studentQuery)) {
                // 新增：school user已存在，但不存在student
                student.setRowId(snowFlake.nextId());
                studentMapper.insert(student);
            } else {
                // 更新：编辑保存，已存在该学生
                student.setRowId(studentQuery.getRowId());
                studentMapper.updateByPrimaryKey(student);
            }
        }
    }


    /**
     * 多表查询：查询教师信息
     * @param req
     * @return
     */
    public PageResp<SchoolTeacherUserQueryResp> teacherList(SchoolTeacherUserQueryReq req) {

        // 获取所有学校用户，返回到schoolUserList中
        SchoolUserExample schooluserExample = new SchoolUserExample();
        SchoolUserExample.Criteria criteriaSchoolUser = schooluserExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getUserId())) {
            criteriaSchoolUser.andUserIdEqualTo(req.getUserId());
        }
        criteriaSchoolUser.andRoleIdEqualTo(TEACHER);
        PageHelper.startPage(req.getPage(), req.getSize());
        List<SchoolUser>  schoolUserList = schooluserMapper.selectByExample(schooluserExample);

        List<SchoolTeacherUserQueryResp> list = CopyUtil.copyList(schoolUserList, SchoolTeacherUserQueryResp.class);
        for(SchoolTeacherUserQueryResp schoolUser: list){
                schoolUser.setStrGender(schoolUser.getGender()?"男":"女");
        }
        PageInfo<SchoolUser> pageInfo = new PageInfo<>(schoolUserList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        PageResp<SchoolTeacherUserQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    /**
     * 保存教师
     * 新增教师：前端只新增school user的内容
     * 若teacher表中已有该学生，但未加入school user。则新增school user
     *
     */
    public void teacherSave(SchoolTeacherUserSaveReq req) {
        SchoolUser schoolUser = CopyUtil.copy(req, SchoolUser.class);
        Teacher teacher = CopyUtil.copy(req, Teacher.class);

        SchoolUser schoolUserQuery = schooluserMapper.selectByPrimaryKey(req.getUserId());
        Teacher teacherQuery = teacherMapper.selectByPrimaryKey(req.getUserId());

        // teacher user 的 rowId为空
        if (ObjectUtils.isEmpty(req.getRowId())) {
            SchoolUser schoolUserDB = selectByUserId(req.getUserId());
            if(ObjectUtils.isEmpty(schoolUserDB)){
                // 新增：用户账号不存在，新增该教师
                schoolUser.setRoleId(TEACHER);
                schoolUser.setRowId(snowFlake.nextId());
                schooluserMapper.insert(schoolUser);

                if (ObjectUtils.isEmpty(teacherQuery)) {
                    // 新增：school user已存在，但不存在teacher
                    teacher.setRowId(snowFlake.nextId());
                    teacherMapper.insert(teacher);
                } else {
                    // 更新：编辑保存，已存在该学生
                    teacher.setRowId(teacherQuery.getRowId());
                    teacherMapper.updateByPrimaryKey(teacher);
                }
            } else{
                // 用户名已存在，无法新增
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
        } else {
            // 更新：编辑保存，已存在该教师
            schoolUser.setRowId(schoolUserQuery.getRowId());
            schoolUser.setRoleId(schoolUserQuery.getRoleId());

            schoolUser.setPasswordEncode(null);
            schooluserMapper.updateByPrimaryKeySelective(schoolUser);
            if (ObjectUtils.isEmpty(teacherQuery)) {
                // 新增：school user已存在，但不存在student
                teacher.setRowId(snowFlake.nextId());
                teacherMapper.insert(teacher);
            } else {
                // 更新：编辑保存，已存在该学生
                teacher.setRowId(teacherQuery.getRowId());
                teacherMapper.updateByPrimaryKey(teacher);
            }
        }
    }


    /**
     * 根据id删除学生
     * @param userId
     */
    public void delete(Long userId) {
        // 去schooluser表中根据userId查询用户记录
        SchoolUser schoolUser = schooluserMapper.selectByPrimaryKey(userId);



        // 获得roleId: 若是学生，去student中删除该记录；若是老师，去teacher表中删除该记录
        if(!ObjectUtils.isEmpty(schoolUser)){
            schooluserMapper.deleteByPrimaryKey(userId);
            Integer roleId = schoolUser.getRoleId();
            if(roleId.equals(STUDENT)){
                Student student = studentMapper.selectByPrimaryKey(userId);
                if(!ObjectUtils.isEmpty(student))
                    studentMapper.deleteByPrimaryKey(userId);
            } else if(roleId.equals(TEACHER)){
                Teacher teacher = teacherMapper.selectByPrimaryKey(userId);
                if(!ObjectUtils.isEmpty(teacher))
                    teacherMapper.deleteByPrimaryKey(userId);
            }
        }else{
            throw new BusinessException(BusinessExceptionCode.USER_ID_ERROR);
        }
    }

    /**
     * 登录
     */
    public SchoolUserLoginResp login(SchoolUserLoginReq req) {
        SchoolUser userDb = selectByUserId(req.getUserId());
        if (ObjectUtils.isEmpty(userDb)) {
            // 用户名不存在
            LOG.info("用户名不存在");
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        } else {
            if (userDb.getPasswordEncode().equals(req.getPasswordEncode())) {
                // 登录成功
                SchoolUserLoginResp schoolUserLoginResp = CopyUtil.copy(userDb, SchoolUserLoginResp.class);
                return schoolUserLoginResp;
            } else {
                // 密码不对
                LOG.info("密码不对, 输入密码：{}, 数据库密码：{}", req.getPasswordEncode(), userDb.getPasswordEncode());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }
    }

    // 根据school user的userId查询记录，若存在返回该用户，否则返回null
    public SchoolUser selectByUserId(Long UserId) {
        SchoolUserExample userExample = new SchoolUserExample();
        SchoolUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserIdEqualTo(UserId);
        List<SchoolUser> schoolUserList = schooluserMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(schoolUserList)) {
            return null;
        } else {
            return schoolUserList.get(0);
        }
    }

    public SchoolUser getSchoolUser(Long userId){
        return schooluserMapper.selectByPrimaryKey(userId);
    }

}