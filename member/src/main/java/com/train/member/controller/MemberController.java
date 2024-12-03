package com.train.member.controller;

import com.train.common.resp.CommonResp;
import com.train.member.req.MemberRegisterReq;
import com.train.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：Zhuiwei Wu
 *
 * @date ：2024-12-01 20:15
 */
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @GetMapping("/count")
    public CommonResp<Long> count(){
        Long count = memberService.count();
        CommonResp<Long> commonResp = new CommonResp<>();
        commonResp.setContent(count);
        return commonResp;
    }


    @PostMapping("/register")
    public CommonResp<Long> register(@Valid MemberRegisterReq registerReq){
        long id = memberService.register(registerReq);
//        CommonResp<Long> commonResp = new CommonResp<>();
//        commonResp.setContent(id);
//        return commonResp;
        return new CommonResp<>(id);
    }
}
