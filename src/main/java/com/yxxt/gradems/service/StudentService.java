package com.yxxt.gradems.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yxxt.gradems.domain.Student;
import com.yxxt.gradems.domain.StudentExample;
import com.yxxt.gradems.mapper.StudentMapper;
import com.yxxt.gradems.req.StudentQueryReq;
import com.yxxt.gradems.req.StudentSaveReq;
import com.yxxt.gradems.resp.PageResp;
import com.yxxt.gradems.resp.StudentQueryResp;
import com.yxxt.gradems.util.CopyUtil;
import com.yxxt.gradems.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentService {

    private static final Logger LOG = LoggerFactory.getLogger(StudentService.class);

    @Resource
    private StudentMapper studentMapper;


    @Resource
    private SnowFlake snowFlake;

    public PageResp<StudentQueryResp> list(StudentQueryReq req) {
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getUserId())) {
            criteria.andUserIdEqualTo(req.getUserId());
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Student>  studentList = studentMapper.selectByExample(studentExample);

        PageInfo<Student> pageInfo = new PageInfo<>(studentList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        // 列表复制
        List<StudentQueryResp> list = CopyUtil.copyList(studentList, StudentQueryResp.class);

        PageResp<StudentQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    /**
     * 保存
     *
     */
    public void save(StudentSaveReq req) {
        Student student = CopyUtil.copy(req, Student.class);
        if (ObjectUtils.isEmpty(req.getRowId())) {
            // 新增：用户账号不存在，新增该学生
            //student.setRowId(snowFlake.nextId());
            //student.setRowId(16L);
            studentMapper.insert(student);
        } else {
            // 更新：编辑保存，已存在该学生
            studentMapper.updateByPrimaryKey(student);
        }
    }

    /**
     * 根据id删除学生
     * @param userId
     */
    public void delete(Long userId) {
        studentMapper.deleteByPrimaryKey(userId);
    }
}