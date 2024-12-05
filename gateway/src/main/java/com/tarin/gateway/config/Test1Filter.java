package com.tarin.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author ：Zhuiwei Wu
 * @description：TODO
 * @date ：2024-12-05 15:22
 */
@Component
public class Test1Filter implements GlobalFilter {
    private static final Logger LOG = LoggerFactory.getLogger(Test1Filter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LOG.info("Test1Filter");
        return chain.filter(exchange);
    }


}
