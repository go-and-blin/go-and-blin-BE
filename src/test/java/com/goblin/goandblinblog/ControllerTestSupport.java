package com.goblin.goandblinblog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goblin.goandblinblog.domain.category.controller.CategoryController;
import com.goblin.goandblinblog.domain.category.controller.port.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
        controllers = {CategoryController.class},
        excludeAutoConfiguration = SecurityAutoConfiguration.class
)
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockitoBean
    protected CategoryService categoryService;
}
