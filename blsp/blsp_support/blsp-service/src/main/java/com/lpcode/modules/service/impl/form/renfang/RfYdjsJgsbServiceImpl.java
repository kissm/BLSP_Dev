package com.lpcode.modules.service.impl.form.renfang;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormRfYdjsJgsb;
import com.lpcode.modules.blsp.mapper.FormRfYdjsJgsbMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IRfYdjsJgsbService;
import com.lpcode.modules.service.project.dto.pinstance.FormRfYdjsJgsbVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;


/**
 * 珠海市人防工程易地建设竣工申报表
 * @author ZERO
 *
 */
@Service
public class RfYdjsJgsbServiceImpl implements IRfYdjsJgsbService {
	
	
	@Autowired
    private FormRfYdjsJgsbMapper formRfYdjsJgsbMapper ;

	@Override
	public String getScene() {
		return FormCode.FORM_RF_YDJS_JGSB;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormRfYdjsJgsbVo formRfYdjsJgsbVo = (FormRfYdjsJgsbVo) object;
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formRfYdjsJgsbVo.getId() == null) {
        	formRfYdjsJgsbVo.setCreatTime(new Date());
        	formRfYdjsJgsbVo.setCreator(UserUtils.getUser().getId());
        	formRfYdjsJgsbVo.setUpdateTime(new Date());
        	formRfYdjsJgsbVo.setUpdator(UserUtils.getUser().getId());
        	formRfYdjsJgsbVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	formRfYdjsJgsbMapper.insert(formRfYdjsJgsbVo);
        }else {
        	formRfYdjsJgsbVo.setUpdateTime(new Date());
        	formRfYdjsJgsbVo.setUpdator(UserUtils.getUser().getId());
        	formRfYdjsJgsbMapper.updateByPrimaryKeySelective(formRfYdjsJgsbVo);
        }
        prjFormVO.setObject(formRfYdjsJgsbVo);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo,String taskDefId) {
		String url = "modules/bizform/rfYdjsJgsbForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormRfYdjsJgsbVo formRfYdjsBjspVo = getFormRfYdjsJgsbVo(prjInstanceVo.getId(),taskDefId);
        if (formRfYdjsBjspVo == null) {
        	formRfYdjsBjspVo = new FormRfYdjsJgsbVo();
        	BeanCopy.copyProperties(prjInstanceVo,formRfYdjsBjspVo);
            formRfYdjsBjspVo.setPrjId(prjInstanceVo.getId());
            formRfYdjsBjspVo.setLinkman(prjInstanceVo.getAgentName());
            formRfYdjsBjspVo.setLinkmanPhone(prjInstanceVo.getAgentMphone());
            formRfYdjsBjspVo.setPrjAddress(prjInstanceVo.getPrjAddr());
            formRfYdjsBjspVo.setId(null);
            formRfYdjsBjspVo.setTaskId(Long.parseLong(taskDefId));
        }
        prjFormVO.setObject(formRfYdjsBjspVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId,String taskDefId) {
		 PrjFormVO prjFormVO = new PrjFormVO();
	        if (prjectId == null)
	            return null;
	        FormRfYdjsJgsb record = new FormRfYdjsJgsb();
	        FormRfYdjsJgsbVo formRfYdjsBjspVo = new FormRfYdjsJgsbVo();
	        record.setPrjId(prjectId);
	        record.setIsDelete("0");
	        record.setTaskId(Long.parseLong(taskDefId));
	        record = formRfYdjsJgsbMapper.selectOneByEntitySelective(record);
	        if (record == null)
	            return null;
	        BeanCopy.copyProperties(record, formRfYdjsBjspVo);
	        prjFormVO.setObject(formRfYdjsBjspVo);
	        return prjFormVO;
	}
	
	 public FormRfYdjsJgsbVo getFormRfYdjsJgsbVo(Long pid,String taskDefId) {
	        if (pid == null)
	            return null;
	        FormRfYdjsJgsb record = new FormRfYdjsJgsb();
	        FormRfYdjsJgsbVo formRfYdjsJgsb = new FormRfYdjsJgsbVo();
	        record.setPrjId(pid);
	        record.setIsDelete("0");
	        record.setTaskId(Long.parseLong(taskDefId));
	        record = formRfYdjsJgsbMapper.selectOneByEntitySelective(record);
	        if (record == null)
	            return null;
	        BeanCopy.copyProperties(record, formRfYdjsJgsb);
	        return formRfYdjsJgsb;
	    }


}
