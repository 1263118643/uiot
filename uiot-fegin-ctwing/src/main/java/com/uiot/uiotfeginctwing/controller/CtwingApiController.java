package com.uiot.uiotfeginctwing.controller;

import cn.hutool.http.server.HttpServerRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ctwing")
public class CtwingApiController {

    /**
     * 通过Id查询设备信息
     * @param name
     * @return
     */
    @GetMapping("/findByDeviceId")
    public String findByDeviceId(String name){
        return name;
    }
}
