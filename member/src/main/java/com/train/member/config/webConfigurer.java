package com.train.member.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ：Zhuiwei Wu
 * @description：TODO
 * @date ：2024-12-04 20:21
 */
@Configuration
public class webConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  //指定允许跨域请求的路径模式为"/**"，表示所有的路径都将允许跨域访问。
                .allowedOrigins("*") // 允许访问资源的域名
                .allowedMethods("*") // 允许的HTTP方法
                .allowedHeaders("*") // 允许的请求头
                .allowCredentials(false) // 是否允许发送凭证信息
                .maxAge(3600); // 预检请求的有效期
    }

}
