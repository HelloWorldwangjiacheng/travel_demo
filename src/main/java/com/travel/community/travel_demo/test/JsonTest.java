package com.travel.community.travel_demo.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.travel.community.travel_demo.utils.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class JsonTest {

    @RequestMapping("/json")
    @ResponseBody
    public String json() throws JsonProcessingException {
        Date date = new Date();
        Date date1 = new Date(System.currentTimeMillis());
        return JsonUtils.getJson(date1);
    }
}
