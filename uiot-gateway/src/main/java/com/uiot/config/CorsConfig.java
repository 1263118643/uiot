package com.uiot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;


@Configuration
public class CorsConfig {

    /**
     * 添加跨域过滤器
     *
     * @return CorsWebFilter
     */
    @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //配置跨域
        CorsConfiguration config = new CorsConfiguration();
        //任意请求头
        config.addAllowedHeader("*");
        //任意方式
        config.addAllowedMethod("GET,POST,PUT,DELETE");
        //任意请求来源
        config.addAllowedOrigin("*");
        //允许携带cookie跨域
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }

}