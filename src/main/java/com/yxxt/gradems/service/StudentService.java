package com.yxxt.gradems.service;

import com.yxxt.gradems.domain.Student;
import com.yxxt.gradems.domain.StudentExample;
import com.yxxt.gradems.mapper.StudentMapper;
import com.yxxt.gradems.req.StudentReq;
import com.yxxt.gradems.resp.StudentResp;
import com.yxxt.gradems.util.CopyUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentService {

    @Resource
    private StudentMapper studentMapper;

    public List<StudentResp> list(StudentReq req) {
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getUserId())) {
            criteria.andUserIdEqualTo(req.getUserId());
        }

        List<Student>  studentList = studentMapper.selectByExample(studentExample);

        // List<StudentResp> respList = new ArrayList<>();
        // for (Student student : studentList) {
        //     // 对象复制
        //     StudentResp studentResp = CopyUtil.copy(student, StudentResp.class);
        //
        //     respList.add(studentResp);
        // }

        // 列表复制
        List<StudentResp> list = CopyUtil.copyList(studentList, StudentResp.class);

        return list;
    }
}