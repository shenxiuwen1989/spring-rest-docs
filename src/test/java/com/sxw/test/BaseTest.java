package com.sxw.test;


import com.sxw.Application;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.AbstractMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureRestDocs(outputDir = "target/asciidoc/generated")
@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
public class BaseTest {

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

    protected MockHttpServletRequestBuilder buildRequest(String url, String content) {
        return post(url).contentType(MediaType.APPLICATION_JSON_UTF8).content(content)
                .accept(MediaType.APPLICATION_JSON_UTF8);
    }

    protected void request(String url,String docs) throws Exception{
        String path = StringUtils.substringAfterLast(url, "/");
        InputStream input = MyTest.class.getClassLoader().getResourceAsStream("request/" + path + ".json");
        String json = IOUtils.toString(input, StandardCharsets.UTF_8);
        MockHttpServletRequestBuilder request = buildRequest(url, json);
        mockMvc.perform(request).andDo(print()).andExpect(status().isOk()).andDo(document(docs + url));
    }

}
