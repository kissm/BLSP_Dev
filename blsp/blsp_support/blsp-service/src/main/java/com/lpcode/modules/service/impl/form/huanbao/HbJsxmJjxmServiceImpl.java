package com.lpcode.modules.service.impl.form.huanbao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormHbJsxmJjxm;
import com.lpcode.modules.blsp.mapper.FormHbJsxmJjxmMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IHbJsxmJjxmService;
import com.lpcode.modules.service.project.dto.pinstance.FormHbJsxmJjxmVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 环保：建设项目环境影响登记表（基建项目）
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.huanbao
 * @author maxiaowei
 * @createDate 2016年7月13日 下午2:38:17
 */
@Service
public class HbJsxmJjxmServiceImpl implements IHbJsxmJjxmService {

	@Autowired
	private FormHbJsxmJjxmMapper formHbJsxmJjxmMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_HB_JSXM_JJXM;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormHbJsxmJjxmVo formHbJsxmJjxmVo = (FormHbJsxmJjxmVo) object;
		String url = "form/hbJsxmJjxmForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formHbJsxmJjxmVo.getId() == null) {
        	formHbJsxmJjxmVo.setCreatTime(new Date());
        	formHbJsxmJjxmVo.setCreator(UserUtils.getUser().getId());
        	formHbJsxmJjxmVo.setUpdateTime(new Date());
        	formHbJsxmJjxmVo.setUpdator(UserUtils.getUser().getId());
        	formHbJsxmJjxmVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	FormHbJsxmJjxm formHbJsxmJjxm = new FormHbJsxmJjxm();
        	BeanCopy.copyProperties(formHbJsxmJjxmVo, formHbJsxmJjxm, FormHbJsxmJjxm.class);
        	formHbJsxmJjxmMapper.insert(formHbJsxmJjxm);
        }else {
        	formHbJsxmJjxmVo.setUpdateTime(new Date());
        	formHbJsxmJjxmVo.setUpdator(UserUtils.getUser().getId());
        	FormHbJsxmJjxm formHbJsxmJjxm = new FormHbJsxmJjxm();
        	BeanCopy.copyProperties(formHbJsxmJjxmVo, formHbJsxmJjxm, FormHbJsxmJjxm.class);
        	formHbJsxmJjxmMapper.updateByPrimaryKeySelective(formHbJsxmJjxm);
        }
        prjFormVO.setObject(formHbJsxmJjxmVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo, String taskDefId) {
		String url = "modules/bizform/hbJsxmJjxmForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormHbJsxmJjxmVo formHbJsxmJjxmVo = getFormHbJsxmJjxmVo(prjInstanceVo.getId(),taskDefId);
        if (formHbJsxmJjxmVo == null) {
        	formHbJsxmJjxmVo = new FormHbJsxmJjxmVo();
        	BeanCopy.copyProperties(prjInstanceVo,formHbJsxmJjxmVo);
            formHbJsxmJjxmVo.setPrjId(prjInstanceVo.getId());
            formHbJsxmJjxmVo.setId(null);
            formHbJsxmJjxmVo.setTaskId(Long.valueOf(taskDefId));
        }
        prjFormVO.setObject(formHbJsxmJjxmVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId, String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormHbJsxmJjxm record = new FormHbJsxmJjxm();
		FormHbJsxmJjxmVo formHbJsxmJjxmVo = new FormHbJsxmJjxmVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formHbJsxmJjxmMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formHbJsxmJjxmVo);
        prjFormVO.setObject(formHbJsxmJjxmVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取  建设项目环境影响登记表（基建项目）   实例
	 * @param pid
	 * @param taskDefId
	 * @return
	 */
	private FormHbJsxmJjxmVo getFormHbJsxmJjxmVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormHbJsxmJjxm record = new FormHbJsxmJjxm();
		FormHbJsxmJjxmVo formHbJsxmJjxmVo = new FormHbJsxmJjxmVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formHbJsxmJjxmMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formHbJsxmJjxmVo);
        return formHbJsxmJjxmVo;
	}

}
