package com.yxxt.gradems.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yxxt.gradems.domain.Course;
import com.yxxt.gradems.domain.CourseExample;
import com.yxxt.gradems.exception.BusinessException;
import com.yxxt.gradems.exception.BusinessExceptionCode;
import com.yxxt.gradems.mapper.CourseMapper;
import com.yxxt.gradems.req.CourseQueryReq;
import com.yxxt.gradems.resp.CourseQueryResp;
import com.yxxt.gradems.resp.PageResp;
import com.yxxt.gradems.util.CopyUtil;
import com.yxxt.gradems.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseManageService {

    private static final Logger LOG = LoggerFactory.getLogger(CourseManageService.class);

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private SnowFlake snowFlake;

    /**
     * 多表查询：查询管理员信息
     * @param req
     * @return
     */
    public PageResp<CourseQueryResp> courseList(CourseQueryReq req) {

        // 获取所有学校用户，返回到schoolUserList中
        CourseExample courseExample = new CourseExample();
        CourseExample.Criteria criteriaCourse = courseExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getCourseUid())) {
            criteriaCourse.andCourseUidEqualTo(req.getCourseUid());
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Course> courseList = courseMapper.selectByExample(courseExample);

        List<CourseQueryResp> list  = new ArrayList<>();
        for(Course course : courseList){
            if(!ObjectUtils.isEmpty(course)){
                CourseQueryResp courseQueryResp = CopyUtil.copy(course, CourseQueryResp.class);
                courseQueryResp.setStringCourseType();
                courseQueryResp.setStringTerm();
                list.add(courseQueryResp);
            }
        }

        PageInfo<Course> pageInfo = new PageInfo<>(courseList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        PageResp<CourseQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void deleteCourse(String courseUid) {

        Course course = courseMapper.selectByPrimaryKey(courseUid);

        // 获得roleId: 若是学生，去student中删除该记录；若是老师，去teacher表中删除该记录
        if(!ObjectUtils.isEmpty(course)){
            courseMapper.deleteByPrimaryKey(courseUid);
        }else{
            throw new BusinessException(BusinessExceptionCode.COURSE_ID_ERROR);
        }
    }

    /**
     * 保存教师
     * 新增教师：前端只新增school user的内容
     * 若teacher表中已有该学生，但未加入school user。则新增school user
     *
     */
//    public void managerSave(SchoolManagerUserSaveReq req) {
//        SchoolUser schoolUser = CopyUtil.copy(req, SchoolUser.class);
//
//        SchoolUser schoolUserQuery = schooluserMapper.selectByPrimaryKey(req.getUserId());
//
//        // Manager user 的 rowId为空
//        if (ObjectUtils.isEmpty(req.getRowId())) {
//            SchoolUser schoolUserDB = selectByUserId(req.getUserId());
//            if(ObjectUtils.isEmpty(schoolUserDB)){
//                // 新增：用户账号不存在，新增该管理员
//                schoolUser.setRoleId(ADMIN);
//                schoolUser.setRowId(snowFlake.nextId());
//                schooluserMapper.insert(schoolUser);
//            } else{
//                // 用户名已存在，无法新增
//                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
//            }
//        } else {
//            // 更新：编辑保存，已存在该教师
//            schoolUser.setRowId(schoolUserQuery.getRowId());
//            schoolUser.setRoleId(schoolUserQuery.getRoleId());
//
//            schoolUser.setPasswordEncode(null);
//            schooluserMapper.updateByPrimaryKeySelective(schoolUser);
//        }
//    }

}