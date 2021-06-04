package com.yxxt.gradems.service;

import com.yxxt.gradems.domain.Test;
import com.yxxt.gradems.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    public List<Test> list() {
        return testMapper.list();
    }
}
