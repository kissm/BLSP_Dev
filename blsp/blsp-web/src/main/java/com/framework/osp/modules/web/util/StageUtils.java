package com.framework.osp.modules.web.util;

import java.util.List;
import com.framework.core.result.ListResult;
import com.framework.osp.common.utils.SpringContextHolder;
import com.lpcode.modules.dto.project.PrjStageDefineDTO;
import com.lpcode.modules.service.project.inf.PrjStageDefineService;

public class StageUtils {
	
	private static PrjStageDefineService prjStageDefineService = SpringContextHolder.getBean(PrjStageDefineService.class);
	
	public static List<PrjStageDefineDTO> findListByType(String stageType){
		ListResult<PrjStageDefineDTO> result = prjStageDefineService.findListByType(stageType);
		return result.getObj();
	}
}
