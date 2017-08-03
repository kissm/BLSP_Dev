/**
 *
 */
package com.framework.osp.modules.web.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.framework.core.result.ListResult;
import com.framework.osp.common.mapper.JsonMapper;
import com.lpcode.modules.dto.project.PrjStageDefineDTO;
import com.lpcode.modules.service.project.inf.PrjStageDefineService;

@Controller
@RequestMapping(value = "${adminPath}/prjStageDefine")
public class PrjStageDefineController{

	@Autowired
	private PrjStageDefineService service;
	
	@RequestMapping(value = "/findListByType")
	public String findListByType(PrjStageDefineDTO dto,HttpServletRequest request, HttpServletResponse response, Model model) {
		ListResult<PrjStageDefineDTO> result = null;
		try{
			result = service.findListByType(dto.getStageType());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return JsonMapper.toJsonString(result==null?"":result);
	}
}