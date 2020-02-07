package com.travel.community.travel_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
            )
    {

        return "index";
    }
}
