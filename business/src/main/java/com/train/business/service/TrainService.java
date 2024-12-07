package com.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.train.business.domain.Train;
import com.train.business.domain.TrainExample;
import com.train.business.mapper.TrainMapper;
import com.train.business.req.TrainQueryReq;
import com.train.business.req.TrainSaveReq;
import com.train.business.resp.TrainQueryResp;
import com.train.common.Util.SnowUtil;
import com.train.common.exception.BusinessException;
import com.train.common.exception.BusinessExceptionEnum;
import com.train.common.resp.PageResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：Zhuiwei Wu
 * @description：TODO
 * @date ：2024-12-05 19:02
 */
@Service
public class TrainService {
    private static final Logger LOG = LoggerFactory.getLogger(TrainService.class);
    @Autowired
    private TrainMapper trainMapper;


    public void save(TrainSaveReq req) {

        DateTime now = DateTime.now();
        Train train = BeanUtil.copyProperties(req, Train.class);
        if (ObjectUtil.isNull(train.getId())) {
            Train trainDB = selectByUnique(train.getCode());
            if (ObjectUtil.isNotEmpty(trainDB)) {
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_TRAIN_CODE_UNIQUE_ERROR);
            }
            train.setId(SnowUtil.getSnowflakeNextId());
            train.setCreateTime(now);
            train.setUpdateTime(now);
            trainMapper.insert(train);
        } else {
            train.setUpdateTime(now);
            trainMapper.updateByPrimaryKey(train);
        }
    }

    public PageResp<TrainQueryResp> queryList(TrainQueryReq req) {
        TrainExample trainExample = new TrainExample();
        // createCriteria  生成一个条件
        TrainExample.Criteria criteria = trainExample.createCriteria();

        PageHelper.startPage(req.getPage(), req.getSize());
        List<Train> trains = trainMapper.selectByExample(trainExample);
        PageInfo<Train> pageInfo = new PageInfo<>(trains);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());
        List<TrainQueryResp> list = BeanUtil.copyToList(trains, TrainQueryResp.class);
        PageResp<TrainQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;

    }

    public void deletebyId(Long id) {
        trainMapper.deleteByPrimaryKey(id);
    }

    public List<TrainQueryResp> queryAll() {
        TrainExample trainExample = new TrainExample();
        trainExample.setOrderByClause("code asc");
        List<Train> trains = trainMapper.selectByExample(trainExample);
        List<TrainQueryResp> trainQueryResps = BeanUtil.copyToList(trains, TrainQueryResp.class);
        return trainQueryResps;
    }

    private Train selectByUnique(String code) {
        TrainExample trainExample = new TrainExample();
        trainExample.createCriteria().andCodeEqualTo(code);

        List<Train> trains = trainMapper.selectByExample(trainExample);
        if (CollUtil.isNotEmpty(trains)) {
            return trains.get(0);
        } else {
            return null;
        }
    }
}
