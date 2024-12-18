package com.train.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class GatewayApplication {
    private static final Logger Log=LoggerFactory.getLogger(GatewayApplication.class);

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(GatewayApplication.class);
        Environment environment = application.run(args).getEnvironment();
        Log.info("启动成功");
        Log.info("网关地址：\thttp://127.0.0.1:{}",environment.getProperty("server.port"));
    }

}
