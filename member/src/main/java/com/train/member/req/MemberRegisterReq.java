package com.train.member.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author ：Zhuiwei Wu
 * @description：TODO
 * @date ：2024-12-03 14:48
 */
@Data
public class MemberRegisterReq {
    @NotBlank(message = "手机号不能为空")
    private String mobile;
}
