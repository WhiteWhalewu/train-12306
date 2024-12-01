package com.example.train;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：Zhuiwei Wu
 *
 * @date ：2024-12-01 20:15
 */
@RestController
public class TestController {
    @GetMapping("/hello")
    public String hello(){
        System.out.println("hello");

        return "ee";
    }
}
