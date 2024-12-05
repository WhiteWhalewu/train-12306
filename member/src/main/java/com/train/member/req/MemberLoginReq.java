package com.train.member.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author ：Zhuiwei Wu
 * @description：TODO
 * @date ：2024-12-03 14:48
 */
@Data
public class MemberLoginReq {
    //jakarta.validation.constraints.NotBlank.List.class  加了springboot 验证模块
    //不允许为空
    @NotBlank(message = "手机号不能为空")
    private String mobile;
    @NotBlank(message = "验证码非空")
    private String code;


}
