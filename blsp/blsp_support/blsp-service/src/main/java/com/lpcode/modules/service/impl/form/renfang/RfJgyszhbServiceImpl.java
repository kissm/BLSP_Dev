package com.lpcode.modules.service.impl.form.renfang;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormRfJgyszhb;
import com.lpcode.modules.blsp.mapper.FormRfJgyszhbMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IRfJgyszhbService;
import com.lpcode.modules.service.project.dto.pinstance.FormRfJgyszhbVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

@Service
public class RfJgyszhbServiceImpl implements IRfJgyszhbService {
	
	@Autowired
    private  FormRfJgyszhbMapper formRfJgyszhbMapper;

	@Override
	public String getScene() {
		return FormCode.FORM_RF_JGYSZHB;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormRfJgyszhbVo formRfJgyszhbVo = (FormRfJgyszhbVo) object;
        String url = "form/rfBjshForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formRfJgyszhbVo.getId() == null) {
        	formRfJgyszhbVo.setCreatTime(new Date());
        	formRfJgyszhbVo.setCreator(UserUtils.getUser().getId());
        	formRfJgyszhbVo.setUpdateTime(new Date());
        	formRfJgyszhbVo.setUpdator(UserUtils.getUser().getId());
        	formRfJgyszhbVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	formRfJgyszhbMapper.insert(formRfJgyszhbVo);
        }else {
            formRfJgyszhbVo.setUpdateTime(new Date());
            formRfJgyszhbVo.setUpdator(UserUtils.getUser().getId());
            formRfJgyszhbMapper.updateByPrimaryKeySelective(formRfJgyszhbVo);
        }
        prjFormVO.setObject(formRfJgyszhbVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo,String taskDefId) {
		String url = "modules/bizform/rfJgyszhbForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormRfJgyszhbVo formRfJgyszhbVo = getFormRfJgyszhbVo(prjInstanceVo.getId(),taskDefId);
        if (formRfJgyszhbVo == null) {
        	formRfJgyszhbVo = new FormRfJgyszhbVo();
        	BeanCopy.copyProperties(prjInstanceVo, formRfJgyszhbVo);
            formRfJgyszhbVo.setPrjId(prjInstanceVo.getId());
            formRfJgyszhbVo.setLinkman(prjInstanceVo.getAgentName());
            formRfJgyszhbVo.setLinkmanMobilephone(prjInstanceVo.getAgentMphone());
            formRfJgyszhbVo.setPrjAddr(prjInstanceVo.getPrjAddr());
            formRfJgyszhbVo.setId(null);
            formRfJgyszhbVo.setTaskId(Long.parseLong(taskDefId));
        }
        prjFormVO.setObject(formRfJgyszhbVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId,String taskDefId) {
		 PrjFormVO prjFormVO = new PrjFormVO();
	        if (prjectId == null)
	            return null;
	        FormRfJgyszhb record = new FormRfJgyszhb();
	        FormRfJgyszhbVo formRfJgyszhbVo = new FormRfJgyszhbVo();
	        record.setPrjId(prjectId);
	        record.setIsDelete("0");
	        record.setTaskId(Long.parseLong(taskDefId));
	        record = formRfJgyszhbMapper.selectOneByEntitySelective(record);
	        if (record == null)
	            return null;
	        BeanCopy.copyProperties(record, formRfJgyszhbVo);
	        prjFormVO.setObject(formRfJgyszhbVo);
	        return prjFormVO;
	}
	
	private FormRfJgyszhbVo getFormRfJgyszhbVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormRfJgyszhb record = new FormRfJgyszhb();
        FormRfJgyszhbVo formRfJgyszhbVo = new FormRfJgyszhbVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formRfJgyszhbMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formRfJgyszhbVo);
        return formRfJgyszhbVo;
	}

}
