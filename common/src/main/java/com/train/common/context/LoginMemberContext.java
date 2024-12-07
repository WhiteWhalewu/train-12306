package com.train.common.context;

import com.train.common.resp.MemberLoginResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoginMemberContext {
    private static final Logger LOG = LoggerFactory.getLogger(LoginMemberContext.class);

    //保存当前线程的唯一静态变量，类型为MemberLoginResp
    private static ThreadLocal<MemberLoginResp> member = new ThreadLocal<>();

    public static MemberLoginResp getMember() {
        return member.get();
    }

    public static void setMember(MemberLoginResp member) {
        LoginMemberContext.member.set(member);
    }

    public static Long getId() {
        try {
            return member.get().getId();
        } catch (Exception e) {
            LOG.error("获取登录会员信息异常", e);
            throw e;
        }
    }


    public static String getMobile() {
        try {
            return member.get().getMobile();
        } catch (Exception e) {
            LOG.error("获取登录会员moblie失败", e);
            throw e;
        }
    }

}
