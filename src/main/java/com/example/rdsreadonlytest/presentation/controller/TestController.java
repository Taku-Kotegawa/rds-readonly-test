package com.example.rdsreadonlytest.presentation.controller;

import com.example.rdsreadonlytest.application.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TestController {

    private TestService testService;

    @RequestMapping("readonly")
    public String readOnly(Model model) {
        return testService.readOnly();
    }

    @RequestMapping("writable")
    public String writable(Model model) {
        return testService.writable();
    }

}
