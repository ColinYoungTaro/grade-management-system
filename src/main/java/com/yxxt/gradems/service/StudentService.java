package com.yxxt.gradems.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yxxt.gradems.domain.Student;
import com.yxxt.gradems.domain.StudentExample;
import com.yxxt.gradems.mapper.StudentMapper;
import com.yxxt.gradems.req.StudentReq;
import com.yxxt.gradems.resp.PageResp;
import com.yxxt.gradems.resp.StudentResp;
import com.yxxt.gradems.util.CopyUtil;
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

    public PageResp<StudentResp> list(StudentReq req) {
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
        // List<StudentResp> respList = new ArrayList<>();
        // for (Student student : studentList) {
        //     // 对象复制
        //     StudentResp studentResp = CopyUtil.copy(student, StudentResp.class);
        //
        //     respList.add(studentResp);
        // }

        // 列表复制
        List<StudentResp> list = CopyUtil.copyList(studentList, StudentResp.class);

        PageResp<StudentResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }
}