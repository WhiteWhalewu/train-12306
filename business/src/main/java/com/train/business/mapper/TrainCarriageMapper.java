package com.train.business.mapper;

import com.train.business.domain.TrainCarriage;
import com.train.business.domain.TrainCarriageExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TrainCarriageMapper {
    long countByExample(TrainCarriageExample example);

    int deleteByExample(TrainCarriageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TrainCarriage record);

    int insertSelective(TrainCarriage record);

    List<TrainCarriage> selectByExample(TrainCarriageExample example);

    TrainCarriage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TrainCarriage record, @Param("example") TrainCarriageExample example);

    int updateByExample(@Param("record") TrainCarriage record, @Param("example") TrainCarriageExample example);

    int updateByPrimaryKeySelective(TrainCarriage record);

    int updateByPrimaryKey(TrainCarriage record);
}