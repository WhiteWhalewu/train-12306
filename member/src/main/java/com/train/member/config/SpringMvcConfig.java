package com.train.member.config;

import com.train.common.interceptor.MemberInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ：Zhuiwei Wu
 * @description：TODO
 * @date ：2024-12-05 19:56
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
    @Resource
    private MemberInterceptor memberInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(memberInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/member/member/send-code",
                        "/member/member/login");
    }
}
