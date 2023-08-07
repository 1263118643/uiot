package com.uiot.uiotreceivemsg.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "uiot-fegin-ctwing")
public interface CtwingService {
    @GetMapping(value = "/ctwing/findByDeviceId")
    public String findByDeviceId(@RequestParam String name);
}
