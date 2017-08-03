package com.lpcode.modules.blsp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lpcode.modules.blsp.vo.PrjTaskDefineSumMaterialVO;

public interface PrjTaskDefineSumMaterialMapper {
	
    List<PrjTaskDefineSumMaterialVO> sumMaterialByStageId(@Param("stageId")Long stageId);

}