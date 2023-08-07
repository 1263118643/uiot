package com.uiot.uiotreceivemsg.controller;

import com.uiot.uiotreceivemsg.feigns.CtwingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class cotrollerTest {
    Logger logger = LoggerFactory.getLogger(cotrollerTest.class);
    @Autowired
    CtwingService ctwingService;
    @GetMapping("/test2")
    public String test2(String name){
        String s = ctwingService.findByDeviceId(name);
        return s;
    }
}
