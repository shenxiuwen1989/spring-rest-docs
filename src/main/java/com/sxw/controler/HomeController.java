package com.sxw.controler;

import com.sxw.model.Student;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class HomeController {

    @RequestMapping(value = "/getStudent",method = RequestMethod.POST)
    public Student getStudent(@RequestBody  String name) {
        Student reponse = new Student();
        reponse.setId(1);
        reponse.setName("zhangsan");
        reponse.setAge(12);
        reponse.setCls("二年级");
        reponse.setAddress("重庆市大竹林");
        reponse.setSex("男");
        return reponse;
    }
}
