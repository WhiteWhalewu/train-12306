package com.train.member.req;

import com.train.common.req.PageReq;
import lombok.Data;

@Data
public class PassengerQueryReq extends PageReq {


    /**
     * 会员id
     */
    private Long memberId;


}
