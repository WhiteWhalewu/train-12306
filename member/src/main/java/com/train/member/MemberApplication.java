package com.train.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication

public class MemberApplication {
    private static final Logger Log=LoggerFactory.getLogger(MemberApplication.class);

    public static void main(String[] args) {

       // SpringApplication.run(MemberApplication.class, args);
        SpringApplication application = new SpringApplication(MemberApplication.class);
        Environment environment = application.run(args).getEnvironment();
        Log.info("启动成功");
        Log.info("地址：\thttp://127.0.0.1:{}",environment.getProperty("server.port"));
    }

}