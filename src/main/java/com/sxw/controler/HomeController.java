package com.sxw.controler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class HomeController {

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public Map<String, Object> greeting() {
        return Collections.singletonMap("message", "Hello World");
    }
}
