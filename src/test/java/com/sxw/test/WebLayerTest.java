package com.sxw.test;


import com.sxw.Application;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.AbstractMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureRestDocs(outputDir = "target/asciidoc/generated")
@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
public class WebLayerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/asciidoc/generated");

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        AbstractMockMvcBuilder<?> mvcBuilder = null;

        mvcBuilder = MockMvcBuilders.webAppContextSetup(context);

        this.mockMvc = mvcBuilder
                //.setMessageConverters(jackson2HttpMessageConverter)

                //.alwaysDo(commonDocumentation())
                //.setControllerAdvice(new CustomRestExceptionHandler())
                //.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()) //
                .apply(documentationConfiguration(restDocumentation)
                        .uris()
                        .withScheme("http")
                        .withHost("127.0.0.1")
                        .withPort(9090)
                        .and()
                        .snippets()
                        .withDefaults(
                                HttpDocumentation.httpRequest(),
                                HttpDocumentation.httpResponse()
                        )
                ).build();
    }

}
