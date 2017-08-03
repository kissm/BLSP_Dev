package com.lpcode.modules.service.impl.form.guihua;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormGhJsxmGhxz;
import com.lpcode.modules.blsp.mapper.FormGhJsxmGhxzMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IGhJsxmGhxzService;
import com.lpcode.modules.service.project.dto.pinstance.FormGhJsxmGhxzVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 珠海市建设项目规划选址申请表
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.guihua
 * @author maxiaowei
 * @createDate 2016年6月16日 下午2:02:03
 */
@Service
public class GhJsxmGhxzServiceImpl implements IGhJsxmGhxzService {

	@Autowired
	private FormGhJsxmGhxzMapper formGhJsxmGhxzMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_GH_JSXM_GHXZ;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormGhJsxmGhxzVo formGhJsxmGhxzVo = (FormGhJsxmGhxzVo) object;
		String url = "form/ghJsxmGhxzForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formGhJsxmGhxzVo.getId() == null) {
        	formGhJsxmGhxzVo.setCreatTime(new Date());
        	formGhJsxmGhxzVo.setCreator(UserUtils.getUser().getId());
        	formGhJsxmGhxzVo.setUpdateTime(new Date());
        	formGhJsxmGhxzVo.setUpdator(UserUtils.getUser().getId());
        	formGhJsxmGhxzVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	formGhJsxmGhxzMapper.insert(formGhJsxmGhxzVo);
        }else {
        	formGhJsxmGhxzVo.setUpdateTime(new Date());
        	formGhJsxmGhxzVo.setUpdator(UserUtils.getUser().getId());
        	formGhJsxmGhxzMapper.updateByPrimaryKeySelective(formGhJsxmGhxzVo);
        }
        prjFormVO.setObject(formGhJsxmGhxzVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo, String taskId) {
		String url = "modules/bizform/ghJsxmGhxzForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormGhJsxmGhxzVo formGhJsxmGhxzVo = getFormGhJsxmGhxzVo(prjInstanceVo.getId(),taskId);
        if (formGhJsxmGhxzVo == null) {
        	formGhJsxmGhxzVo = new FormGhJsxmGhxzVo();
        	BeanCopy.copyProperties(prjInstanceVo,formGhJsxmGhxzVo);
            formGhJsxmGhxzVo.setPrjId(prjInstanceVo.getId());
            formGhJsxmGhxzVo.setId(null);
            formGhJsxmGhxzVo.setTaskId(Long.valueOf(taskId));
        }
        prjFormVO.setObject(formGhJsxmGhxzVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long projectId,String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (projectId == null)
            return null;
        FormGhJsxmGhxz record = new FormGhJsxmGhxz();
		FormGhJsxmGhxzVo formGhJsxmGhxzVo = new FormGhJsxmGhxzVo();
        record.setPrjId(projectId);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formGhJsxmGhxzMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formGhJsxmGhxzVo);
        prjFormVO.setObject(formGhJsxmGhxzVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取  珠海市建设项目规划选址申请表  实例
	 * @param pid
	 * @return
	 */
	private FormGhJsxmGhxzVo getFormGhJsxmGhxzVo(Long pid,String taskId){
		if (pid == null)
            return null;
		FormGhJsxmGhxz record = new FormGhJsxmGhxz();
		FormGhJsxmGhxzVo formGhJsxmGhxzVo = new FormGhJsxmGhxzVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskId));
        record = formGhJsxmGhxzMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formGhJsxmGhxzVo);
        return formGhJsxmGhxzVo;
	}

}
