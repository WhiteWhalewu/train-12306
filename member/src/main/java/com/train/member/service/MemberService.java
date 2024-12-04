package com.train.member.service;

import cn.hutool.core.collection.CollUtil;
import com.train.common.Util.SnowUtil;
import com.train.common.exception.BusinessException;
import com.train.common.exception.BusinessExceptionEnum;
import com.train.member.domain.Member;
import com.train.member.domain.MemberExample;
import com.train.member.mapper.MemberMapper;
import com.train.member.req.MemberRegisterReq;
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
    @Autowired
    private MemberMapper memberMapper;

    public Long count(){
       return memberMapper.countByExample(null);
    }


    public long register(MemberRegisterReq memberRegisterReq){
        String mobile=memberRegisterReq.getMobile();

        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> members = memberMapper.selectByExample(memberExample);
        //如果已经被注册
        if (CollUtil.isNotEmpty(members)){
            //return members.get(0).getId();
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }
        Member member = new Member();

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
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();


    }
}
