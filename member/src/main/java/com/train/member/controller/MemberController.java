package com.train.member.controller;

import com.train.common.resp.CommonResp;
import com.train.member.req.MemberLoginReq;
import com.train.member.req.MemberRegisterReq;
import com.train.member.req.MemberSendCodeReq;
import com.train.member.resp.MemberLoginResp;
import com.train.member.service.MemberService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：Zhuiwei Wu
 *
 * @date ：2024-12-01 20:15
 */
@RestController
@RequestMapping("/member")
public class MemberController {
    private static final Logger LOG = LoggerFactory.getLogger(MemberController.class);
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
    public CommonResp<Long> register(@Valid MemberRegisterReq registerReq) {
        long id = memberService.register(registerReq);
//        CommonResp<Long> commonResp = new CommonResp<>();
//        commonResp.setContent(id);
//        return commonResp;
        return new CommonResp<>(id);
    }

    @PostMapping("/send-code")
    public CommonResp<Long> sendCode(@Valid MemberSendCodeReq req) {
        memberService.sendCode(req);
        return new CommonResp<>();
    }

    @PostMapping("/login")
    public CommonResp<MemberLoginResp> login(@Valid MemberLoginReq req) {
//        LOG.info("mobile={},code={}",mobile,code);
//        MemberLoginReq req = new MemberLoginReq();
//        req.setCode(code);
//        req.setMobile(mobile);
        MemberLoginResp resp = memberService.login(req);
        return new CommonResp<>(resp);
    }
}
