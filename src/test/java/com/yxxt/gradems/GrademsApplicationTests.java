package com.yxxt.gradems;

import javax.annotation.Resource;

import com.yxxt.gradems.service.StudentService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GrademsApplicationTests {

    @Resource
    StudentService studentService;

    @Test
    void contextLoads() {
        // studentService.
    }

}
