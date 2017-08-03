package com.lpcode.modules.blsp.mapper;

import com.lpcode.modules.blsp.entity.PrjTaskMaterialDef;
import com.lpcode.modules.blsp.vo.PrjTaskMaterialDefVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PrjTaskMaterialDefMapperSelf {

    
    List<PrjTaskMaterialDef> selectByTaskIds(List<Long> idList);

    /**
     * 通过TaskId得到所有配置下的材料信息
     * @param taskId
     * @return
     */
    List<PrjTaskMaterialDefVO> selectMaterByTaskId(@Param("taskId")Long taskId);
}