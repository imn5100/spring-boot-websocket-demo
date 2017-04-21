package com.shaw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by imn5100 on 2017/4/21.
 */
@Controller
public class SampleController {
    @RequestMapping
    @ResponseBody
    public String home() {
        return "hello world";
    }

}
