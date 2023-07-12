//package com.uiot.uiotsendnorth.controller;
//
//
//import com.uiot.uiotsendnorth.utils.RedisUtil;
//import com.uiot.uiotsendnorth.utils.SpringUtil;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * 描述：消费者(消息接收方)
// * @author yys
// * @date 2019.03.15
// */
//@RestController
//@RequestMapping("/consumer")
//public class RedisConsumerController {
//
//
//
//    /** 公共配置 */
//    private final static String MESSAGE = "testmq";
//
//    /**
//     * 接收消息API
//     * @return
//     */
//    @RequestMapping("/receiveMessage")
//    public String sendMessage() {
//        RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
//        return (String) redisUtil .brpop(MESSAGE, 0, TimeUnit.SECONDS);
//    }
//
//}