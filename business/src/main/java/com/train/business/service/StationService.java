package com.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.train.business.domain.Station;
import com.train.business.domain.StationExample;
import com.train.business.mapper.StationMapper;
import com.train.business.req.StationQueryReq;
import com.train.business.req.StationSaveReq;
import com.train.business.resp.StationQueryResp;
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
public class StationService {
    private static final Logger LOG = LoggerFactory.getLogger(StationService.class);
    @Autowired
    private StationMapper stationMapper;


    public void save(StationSaveReq req) {

        DateTime now = DateTime.now();
        Station station = BeanUtil.copyProperties(req, Station.class);
        //通过唯一id判断是 新增操作还是更新操作
        if (ObjectUtil.isNull(station.getId())) {
            Station stationsDB = selectByUnique(station.getName());
            if (ObjectUtil.isNotEmpty(stationsDB)) {
                throw new BusinessException(BusinessExceptionEnum.BUSINESS_STATION_NAME_UNIQUE_ERROR);
            }
            station.setId(SnowUtil.getSnowflakeNextId());
            station.setCreateTime(now);
            station.setUpdateTime(now);
            stationMapper.insert(station);
        } else {
            station.setUpdateTime(now);
            stationMapper.updateByPrimaryKey(station);
        }
    }

    private Station selectByUnique(String name) {
        StationExample stationExample = new StationExample();
        stationExample.createCriteria().andNameEqualTo(name);
        List<Station> stations = stationMapper.selectByExample(stationExample);
        if (CollUtil.isNotEmpty(stations)) {
            return stations.get(0);
        } else {
            return null;
        }

    }

    public PageResp<StationQueryResp> queryList(StationQueryReq req) {
        StationExample stationExample = new StationExample();
        // createCriteria  生成一个条件
        StationExample.Criteria criteria = stationExample.createCriteria();

        PageHelper.startPage(req.getPage(), req.getSize());
        List<Station> stations = stationMapper.selectByExample(stationExample);
        PageInfo<Station> pageInfo = new PageInfo<>(stations);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());
        List<StationQueryResp> list = BeanUtil.copyToList(stations, StationQueryResp.class);
        PageResp<StationQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;

    }

    public void deletebyId(Long id) {
        stationMapper.deleteByPrimaryKey(id);
    }

    public List<StationQueryResp> queryAll() {
        List<Station> trains = stationMapper.selectByExample(null);
        List<StationQueryResp> trainQueryResps = BeanUtil.copyToList(trains, StationQueryResp.class);
        return trainQueryResps;
    }
}
