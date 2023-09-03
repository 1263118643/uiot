package com.uiot.uiotfeginctwing.controller;

import cn.hutool.http.server.HttpServerRequest;
import com.ctg.ag.sdk.biz.AepDeviceManagementClient;
import com.ctg.ag.sdk.biz.aep_device_management.QueryDeviceRequest;
import com.uiot.uiotfeginctwing.config.CtwingSDK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ctwing")
public class CtwingApiController {
    @Autowired
    CtwingSDK ctwingSDK;
    /**
     * 通过Id查询设备信息
     * @param name
     * @return
     */
    @GetMapping("/findByDeviceId")
    public String findByDeviceName(String deviceId) throws Exception {
        AepDeviceManagementClient client = ctwingSDK.AepDeviceManagementClient();
        QueryDeviceRequest request = new QueryDeviceRequest();
        request.setParamMasterKey("4977122f9eaf4a7b804e6b5c8f141291");	// single value
        request.setParamDeviceId(deviceId);	// single value
        request.setParamProductId("16899988");	// single value
        String deviceName = client.QueryDevice(request).toString();
        return deviceName;
    }
}
