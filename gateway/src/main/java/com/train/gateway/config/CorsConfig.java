package com.train.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * @author ：Zhuiwei Wu
 * @description：TODO
 * @date ：2024-12-05 17:09
 */
@Configuration
public class CorsConfig implements Ordered {
    private static final Logger Log = LoggerFactory.getLogger(CorsConfig.class);

    @Bean
    public CorsWebFilter corsFilter() {
        Log.info("cors bean 创建");
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}

