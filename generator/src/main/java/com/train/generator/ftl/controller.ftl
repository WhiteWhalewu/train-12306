package com.train.${module}.controller;

import com.train.common.context.LoginMemberContext;
import com.train.common.resp.CommonResp;
import com.train.common.resp.PageResp;
import com.train.${module}.req.${Domain}QueryReq;
import com.train.${module}.req.${Domain}SaveReq;
import com.train.${module}.resp.${Domain}QueryResp;
import com.train.${module}.service.${Domain}Service;
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
@RequestMapping("/${domain}")
public class ${Domain}Controller {
private static final Logger LOG = LoggerFactory.getLogger(${Domain}Controller.class);
@Autowired
private ${Domain}Service ${domain}Service;

@PostMapping("/save")
public CommonResp
<Object> save(@Valid ${Domain}SaveReq req){
    LOG.info("${Domain}:{}",req.toString());
    ${domain}Service.save(req);
    return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp
    <PageResp
    <${Domain}QueryResp>> query(@Valid ${Domain}QueryReq req){
        req.setMemberId(LoginMemberContext.getId());
        PageResp
        <${Domain}QueryResp> list = ${domain}Service.queryList(req);

            return new CommonResp<>(list);
            }

            @DeleteMapping("/delete/{id}")
            public CommonResp
            <Object> delete(@PathVariable Long id ){
                LOG.info("ID={}",id);
                ${domain}Service.deletebyId(id);
                return new CommonResp<>();
                }
                }
