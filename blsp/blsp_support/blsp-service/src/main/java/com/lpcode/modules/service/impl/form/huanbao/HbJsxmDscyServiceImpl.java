package com.lpcode.modules.service.impl.form.huanbao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormHbJsxmDscy;
import com.lpcode.modules.blsp.mapper.FormHbJsxmDscyMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IHbJsxmDscyService;
import com.lpcode.modules.service.project.dto.pinstance.FormHbJsxmDscyVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 环保：建设项目环境影响登记表（第三产业）
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.huanbao
 * @author maxiaowei
 * @createDate 2016年7月12日 上午10:51:08
 */
@Service
public class HbJsxmDscyServiceImpl implements IHbJsxmDscyService {

	@Autowired
	private FormHbJsxmDscyMapper formHbJsxmDscyMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_HB_JSXM_DSCY;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormHbJsxmDscyVo formHbJsxmDscyVo = (FormHbJsxmDscyVo) object;
		String url = "form/hbJsxmDscyForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formHbJsxmDscyVo.getId() == null) {
        	formHbJsxmDscyVo.setCreatTime(new Date());
        	formHbJsxmDscyVo.setCreator(UserUtils.getUser().getId());
        	formHbJsxmDscyVo.setUpdateTime(new Date());
        	formHbJsxmDscyVo.setUpdator(UserUtils.getUser().getId());
        	formHbJsxmDscyVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	FormHbJsxmDscy formHbJsxmDscy = new FormHbJsxmDscy();
        	BeanCopy.copyProperties(formHbJsxmDscyVo, formHbJsxmDscy, FormHbJsxmDscy.class);
        	formHbJsxmDscyMapper.insert(formHbJsxmDscy);
        }else {
        	formHbJsxmDscyVo.setUpdateTime(new Date());
        	formHbJsxmDscyVo.setUpdator(UserUtils.getUser().getId());
        	FormHbJsxmDscy formHbJsxmDscy = new FormHbJsxmDscy();
        	BeanCopy.copyProperties(formHbJsxmDscyVo, formHbJsxmDscy, FormHbJsxmDscy.class);
        	formHbJsxmDscyMapper.updateByPrimaryKeySelective(formHbJsxmDscy);
        }
        prjFormVO.setObject(formHbJsxmDscyVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo, String taskDefId) {
		String url = "modules/bizform/hbJsxmDscyForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormHbJsxmDscyVo formHbJsxmDscyVo = getFormHbJsxmDscyVo(prjInstanceVo.getId(),taskDefId);
        if (formHbJsxmDscyVo == null) {
        	formHbJsxmDscyVo = new FormHbJsxmDscyVo();
        	BeanCopy.copyProperties(prjInstanceVo,formHbJsxmDscyVo);
            formHbJsxmDscyVo.setPrjId(prjInstanceVo.getId());
            formHbJsxmDscyVo.setId(null);
            formHbJsxmDscyVo.setTaskId(Long.valueOf(taskDefId));
        }
        prjFormVO.setObject(formHbJsxmDscyVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId, String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormHbJsxmDscy record = new FormHbJsxmDscy();
		FormHbJsxmDscyVo formHbJsxmDscyVo = new FormHbJsxmDscyVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formHbJsxmDscyMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formHbJsxmDscyVo);
        prjFormVO.setObject(formHbJsxmDscyVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取  建设项目环境影响登记表（第三产业）   实例
	 * @param pid
	 * @param taskDefId
	 * @return
	 */
	private FormHbJsxmDscyVo getFormHbJsxmDscyVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormHbJsxmDscy record = new FormHbJsxmDscy();
		FormHbJsxmDscyVo formHbJsxmDscyVo = new FormHbJsxmDscyVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formHbJsxmDscyMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formHbJsxmDscyVo);
        return formHbJsxmDscyVo;
	}

}
