package com.train.member.controller;

import com.train.common.context.LoginMemberContext;
import com.train.common.resp.CommonResp;
import com.train.common.resp.PageResp;
import com.train.member.req.PassengerQueryReq;
import com.train.member.req.PassengerSaveReq;
import com.train.member.resp.PassengerQueryResp;
import com.train.member.service.PassengerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：Zhuiwei Wu
 * @description：TODO
 * @date ：2024-12-05 16:05
 */
@RestController
@RequestMapping("/passenger")
public class PassengerController {
    private static final Logger LOG = LoggerFactory.getLogger(PassengerController.class);
    @Autowired
    private PassengerService passengerService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid PassengerSaveReq req) {
        LOG.info("Passenger:{}", req.toString());
        passengerService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<PassengerQueryResp>> query(@Valid PassengerQueryReq req) {
        req.setMemberId(LoginMemberContext.getId());
        PageResp<PassengerQueryResp> list = passengerService.queryList(req);

        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        LOG.info("ID={}", id);
        passengerService.deletebyId(id);
        return new CommonResp<>();
    }
}
