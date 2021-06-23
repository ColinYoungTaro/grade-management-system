package com.yxxt.gradems;

import com.yxxt.gradems.config.GrademsApplication;
import com.yxxt.gradems.controller.AppealController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GrademsApplication.class)
public class AppealControllerTest {
    
    private MockMvc mvc;
    
    @Before
    public void setUp(){
        mvc = MockMvcBuilders.standaloneSetup(new AppealController()).build();
    }

    @Test
    public void appeal() throws Exception {
        RequestBuilder request;
        request = MockMvcRequestBuilders.post("/appeal/student_appeal").content("\"courseUid\":\"10001'\",\"studentId\":\"3180104668\",\"classIndex\":1");
        mvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
