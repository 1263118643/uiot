package com.uiot.uiotreceivemsg.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class cotrollerTest {
    Logger logger = LoggerFactory.getLogger(cotrollerTest.class);

    @RequestMapping("/test")
    public String test(String s) {
        
       return s;
    }
}
