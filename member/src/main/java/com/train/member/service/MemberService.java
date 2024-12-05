package com.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.train.common.Util.JwtUtil;
import com.train.common.Util.SnowUtil;
import com.train.common.exception.BusinessException;
import com.train.common.exception.BusinessExceptionEnum;
import com.train.member.domain.Member;
import com.train.member.domain.MemberExample;
import com.train.member.mapper.MemberMapper;
import com.train.member.req.MemberLoginReq;
import com.train.member.req.MemberRegisterReq;
import com.train.member.req.MemberSendCodeReq;
import com.train.member.resp.MemberLoginResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：Zhuiwei Wu
 * @description：TODO
 * @date ：2024-12-02 20:37
 */
@Service
public class MemberService {
    private static final Logger LOG = LoggerFactory.getLogger(MemberService.class);
    @Autowired
    private MemberMapper memberMapper;

    public Long count() {
        return memberMapper.countByExample(null);
    }

    //雪花算法 64位
    //   1bit不用：因为二进制中最高位是符号位，1表示负数，0表示正数，生成的id一般都是用整数，所以最高位固定为0
    //   41bit时间戳：这里采用的就是当前系统的具体时间，单位为毫秒
    //   10bit工作机器ID（workerId）：每台机器分配一个id，这样可以标示不同的机器，但是上限为1024，标示一个集群某个业务最多部署的机器个数上限
    //   12bit序列号（自增域）：表示在某一毫秒下，这个自增域最大可以分配的bit个数，在当前这种配置下，每一毫秒可以分配2^12 = 4096个数据
    //1------数据中心，机器id怎么设置
    // 利用redis自增id， ip1启动时，就去redis中自动获取自增id
    // 利用数据据， 建立ip和id的表，第一次建立的时候的新增元组
    //2------时间回拨    机器时间和实际时间不同
    //  将原本10位的机器码拆分成3位时钟序列及7位机器码
    //long ID = IdUtil.getSnowflake(1, 1).nextId();
    public long register(MemberRegisterReq memberRegisterReq) {
        String mobile = memberRegisterReq.getMobile();

        Member members = selectByMobile(mobile);
        //如果已经被注册
        if (ObjectUtil.isNotNull(members)) {
            //return members.get(0).getId();
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }
        Member member = new Member();
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();
    }

    /**
     * MemberService -- sendCode
     *
     * @Author: Wu Zhuiwei
     * @Date: 2024-12-04 20:01
     * @Description: Used in:
     * @Param memberSendCodeReq:
     * @Return: void
     */
    public void sendCode(MemberSendCodeReq memberSendCodeReq) {
        String mobile = memberSendCodeReq.getMobile();
        Member memberDB = selectByMobile(mobile);
        if (ObjectUtil.isNull(memberDB)) {
            LOG.info("手机号不存在，插入一条记录");
            Member member = new Member();
            member.setId(SnowUtil.getSnowflakeNextId());
            member.setMobile(mobile);
            memberMapper.insert(member);
        }

        String code = "8888";
        LOG.info("生成短信验证码：{}", code);

        // 保存短信记录表：手机号，短信验证码，有效期，是否已使用，业务类型，发送时间，使用时间
        LOG.info("保存短信记录表");

        // 对接短信通道，发送短信
        LOG.info("对接短信通道");
    }

    public MemberLoginResp login(MemberLoginReq memberLoginReq) {
        String mobile = memberLoginReq.getMobile();
        String code = memberLoginReq.getCode();
        Member membersDB = selectByMobile(mobile);
        //如果手机号未注册，则插入一条用户记录
        if (ObjectUtil.isNull(membersDB)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_NOT_EXIST);
        }
        //校验短信验证码
        // 如果验证码错误
        if (!("8888".equals(code))) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_CODE_ERROR);
        }

        MemberLoginResp memberLoginResp = BeanUtil.copyProperties(membersDB, MemberLoginResp.class);
        String token = JwtUtil.createToken(memberLoginResp.getId(), memberLoginResp.getMobile());
        memberLoginResp.setToken(token);
        return memberLoginResp;
    }

    /**
     * 、 -- selectByMobile
     *
     * @Author: Wu Zhuiwei
     * @Date: 2024-12-04 16:40
     * @Description: Used in:
     * @Param mobile:
     * @Return: com.train.member.domain.Member
     */
    private Member selectByMobile(String mobile) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> members = memberMapper.selectByExample(memberExample);

        if (CollUtil.isEmpty(members)) {
            return null;
        } else {
            return members.get(0);
        }

    }
}
