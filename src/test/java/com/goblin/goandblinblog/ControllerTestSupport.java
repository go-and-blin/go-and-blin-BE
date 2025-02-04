package com.goblin.goandblinblog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goblin.goandblinblog.domain.category.controller.CategoryController;
import com.goblin.goandblinblog.domain.category.controller.port.CategoryService;
import com.goblin.goandblinblog.domain.member.controller.MemberController;
import com.goblin.goandblinblog.domain.member.controller.port.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
        controllers = {
                CategoryController.class,
                MemberController.class
        },
        excludeAutoConfiguration = SecurityAutoConfiguration.class
)
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockitoBean
    protected CategoryService categoryService;

    @MockitoBean
    protected MemberService memberService;

}