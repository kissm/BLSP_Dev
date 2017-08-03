package com.lpcode.modules.service.impl.form.caishen;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormCsGcxmGsysjs;
import com.lpcode.modules.blsp.mapper.FormCsGcxmGsysjsMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.ICsGcxmGsysjsService;
import com.lpcode.modules.service.project.dto.pinstance.FormCsGcxmGsysjsVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 财审概算、预算、结算表格表单
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.caishen
 * @author maxiaowei
 * @createDate 2017年3月21日 下午3:09:13
 */
@Service
public class CsGcxmGsysjsServiceImpl implements ICsGcxmGsysjsService {

	@Autowired
	private FormCsGcxmGsysjsMapper formCsGcxmGsysjsMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_CS_GCXM_GSYSJS;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormCsGcxmGsysjsVo formCsGcxmGsysjsVo = (FormCsGcxmGsysjsVo) object;
		if(formCsGcxmGsysjsVo == null){
			formCsGcxmGsysjsVo = new FormCsGcxmGsysjsVo();
		}
		String url = "form/csGcxmGsysjsForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formCsGcxmGsysjsVo.getId() == null) {
        	formCsGcxmGsysjsVo.setCreateTime(new Date());
        	formCsGcxmGsysjsVo.setCreator(UserUtils.getUser().getId());
        	formCsGcxmGsysjsVo.setUpdateTmie(new Date());
        	formCsGcxmGsysjsVo.setUpdator(UserUtils.getUser().getId());
        	formCsGcxmGsysjsVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	FormCsGcxmGsysjs formCsGcxmGsysjs = new FormCsGcxmGsysjs();
        	BeanCopy.copyProperties(formCsGcxmGsysjsVo, formCsGcxmGsysjs, FormCsGcxmGsysjs.class);
        	formCsGcxmGsysjsMapper.insert(formCsGcxmGsysjs);
        }else {
        	formCsGcxmGsysjsVo.setUpdateTmie(new Date());
        	formCsGcxmGsysjsVo.setUpdator(UserUtils.getUser().getId());
        	FormCsGcxmGsysjs formCsGcxmGsysjs = new FormCsGcxmGsysjs();
        	BeanCopy.copyProperties(formCsGcxmGsysjsVo, formCsGcxmGsysjs, FormCsGcxmGsysjs.class);
        	formCsGcxmGsysjsMapper.updateByPrimaryKeySelective(formCsGcxmGsysjs);
        }
        prjFormVO.setObject(formCsGcxmGsysjsVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo, String taskDefId) {
		String url = "modules/bizform/csGcxmGsysjsForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormCsGcxmGsysjsVo formCsGcxmGsysjsVo = getFormCsGcxmGsysjsVo(prjInstanceVo.getId(),taskDefId);
        
        if (formCsGcxmGsysjsVo == null) {
        	formCsGcxmGsysjsVo = new FormCsGcxmGsysjsVo();
        	BeanCopy.copyProperties(prjInstanceVo,formCsGcxmGsysjsVo);
            formCsGcxmGsysjsVo.setPrjId(prjInstanceVo.getId());
            formCsGcxmGsysjsVo.setId(null);
            formCsGcxmGsysjsVo.setTaskId(Long.parseLong(taskDefId));
        }
        prjFormVO.setObject(formCsGcxmGsysjsVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId, String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormCsGcxmGsysjs record = new FormCsGcxmGsysjs();
		FormCsGcxmGsysjsVo formCsGcxmGsysjsVo = new FormCsGcxmGsysjsVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formCsGcxmGsysjsMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formCsGcxmGsysjsVo);
        prjFormVO.setObject(formCsGcxmGsysjsVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取 财审概算、预算、结算表格表单 实例
	 * @param pid
	 * @return
	 */
	private FormCsGcxmGsysjsVo getFormCsGcxmGsysjsVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormCsGcxmGsysjs record = new FormCsGcxmGsysjs();
		FormCsGcxmGsysjsVo formCsGcxmGsysjsVo = new FormCsGcxmGsysjsVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formCsGcxmGsysjsMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formCsGcxmGsysjsVo);
        return formCsGcxmGsysjsVo;
	}

}
