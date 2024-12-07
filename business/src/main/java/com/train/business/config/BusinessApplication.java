package com.train.business.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan("com.train")
@MapperScan("com.train.*.mapper")
public class BusinessApplication {
    private static final Logger Log = LoggerFactory.getLogger(BusinessApplication.class);

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(BusinessApplication.class);
        Environment environment = application.run(args).getEnvironment();
        Log.info("启动成功");
        Log.info("地址：\thttp://127.0.0.1:{}{}/hello", environment.getProperty("server.port"), environment.getProperty("server.servlet.context-path"));
    }

}