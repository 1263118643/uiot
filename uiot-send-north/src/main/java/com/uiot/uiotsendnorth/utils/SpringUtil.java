//package com.uiot.uiotsendnorth.utils;
//
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.stereotype.Component;
//
///**
// * 通过封装applicationContext上线文
// * 获取 spring bean对象 bean启动时候 已经被打印出，可直接根据name、class、name class获取
// *
// * 很多地方能用得到
// * @author xiaoshuguo
// */
//@Component
//public class SpringUtil implements ApplicationContextAware {
//
//    private static ApplicationContext applicationContext;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        if (SpringUtil.applicationContext == null) {
//            SpringUtil.applicationContext = applicationContext;
//        }
//    }
//
//    public static ApplicationContext getApplicationContext() {
//        return applicationContext;
//    }
//
//    /***
//     * 根据name获取bean
//     * @param name
//     * @param <T>
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//    public static <T> T getBean(String name) {
//        return (T) getApplicationContext().getBean(name);
//    }
//
//    public static <T> T getBean(Class<T> clazz) {
//        return getApplicationContext().getBean(clazz);
//    }
//
//    public static <T> T getBean(String name, Class<T> clazz) {
//        return getApplicationContext().getBean(name, clazz);
//    }
//
//}