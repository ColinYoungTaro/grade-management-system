package com.yxxt.gradems.mapper;

import com.yxxt.gradems.domain.ScoreAppeal;
import com.yxxt.gradems.domain.ScoreAppealExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ScoreAppealMapper {
    long countByExample(ScoreAppealExample example);

    int deleteByExample(ScoreAppealExample example);

    int deleteByPrimaryKey(Long rowId);

    int insert(ScoreAppeal record);

    int insertSelective(ScoreAppeal record);

    List<ScoreAppeal> selectByExample(ScoreAppealExample example);

    ScoreAppeal selectByPrimaryKey(Long rowId);

    int updateByExampleSelective(@Param("record") ScoreAppeal record, @Param("example") ScoreAppealExample example);

    int updateByExample(@Param("record") ScoreAppeal record, @Param("example") ScoreAppealExample example);

    int updateByPrimaryKeySelective(ScoreAppeal record);

    int updateByPrimaryKey(ScoreAppeal record);
}