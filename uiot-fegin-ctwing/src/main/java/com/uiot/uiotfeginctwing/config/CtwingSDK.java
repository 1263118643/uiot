package com.uiot.uiotfeginctwing.config;

import com.ctg.ag.sdk.biz.AepDeviceManagementClient;
import com.ctg.ag.sdk.biz.AepDeviceStatusClient;
import com.ctg.ag.sdk.biz.AepProductManagementClient;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用于存放ctwing的sdk
 */
@Configuration
public class CtwingSDK {
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Bean
    public AepDeviceManagementClient AepDeviceManagementClient(){
        AepDeviceManagementClient client = null;
        try{
            client = AepDeviceManagementClient.newClient()
                    .appKey("HGPLFdvztm").appSecret("bwOxMmhQ5u")
                    .build();
            //scheme(Scheme.HTTP).server("10.224.153.252:10000")
        }catch (Exception e){
            LOGGER.error("网络无法连接"+e);
        }
        return client;
    }

    @Bean
    public AepDeviceStatusClient AepDeviceStatusClient(){
        AepDeviceStatusClient client = null;
        try{
            client = AepDeviceStatusClient.newClient()
                    .appKey("HGPLFdvztm").appSecret("bwOxMmhQ5u")
                    .build();
        }catch (Exception e){
            LOGGER.error("网络无法连接"+e);
        }
        return client;
    }

    @Bean
    public AepProductManagementClient AepProductManagementClient(){
        AepProductManagementClient client = null;
        try {
            client = AepProductManagementClient.newClient()
                    .appKey("HGPLFdvztm").appSecret("bwOxMmhQ5u")
                    .build();
        }catch (Exception e){
            LOGGER.error("网络无法连接"+e);
        }
        return client;
    }
}
