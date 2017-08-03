package com.lpcode.modules.service.impl.form.guihua;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormGhJsydGhxk;
import com.lpcode.modules.blsp.mapper.FormGhJsydGhxkMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IGhJsydGhxkService;
import com.lpcode.modules.service.project.dto.pinstance.FormGhJsydGhxkVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 珠海市建设用地规划许可申请表
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.guihua
 * @author maxiaowei
 * @createDate 2016年6月16日 下午6:01:08
 */
@Service
public class GhJsydGhxkServiceImpl implements IGhJsydGhxkService {

	@Autowired
	private FormGhJsydGhxkMapper formGhJsydGhxkMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_GH_JSYD_GHXK;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormGhJsydGhxkVo formGhJsydGhxkVo = (FormGhJsydGhxkVo) object;
		String url = "form/ghJsydGhxkForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formGhJsydGhxkVo.getId() == null) {
        	formGhJsydGhxkVo.setCreatTime(new Date());
        	formGhJsydGhxkVo.setCreator(UserUtils.getUser().getId());
        	formGhJsydGhxkVo.setUpdateTime(new Date());
        	formGhJsydGhxkVo.setUpdator(UserUtils.getUser().getId());
        	formGhJsydGhxkVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	formGhJsydGhxkMapper.insert(formGhJsydGhxkVo);
        }else {
        	formGhJsydGhxkVo.setUpdateTime(new Date());
        	formGhJsydGhxkVo.setUpdator(UserUtils.getUser().getId());
        	formGhJsydGhxkMapper.updateByPrimaryKeySelective(formGhJsydGhxkVo);
        }
        prjFormVO.setObject(formGhJsydGhxkVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo,String taskDefId) {
		String url = "modules/bizform/ghJsydGhxkForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormGhJsydGhxkVo formGhJsydGhxkVo = getFormGhJsydGhxkVo(prjInstanceVo.getId(),taskDefId);
        if (formGhJsydGhxkVo == null) {
        	formGhJsydGhxkVo = new FormGhJsydGhxkVo();
        	BeanCopy.copyProperties(prjInstanceVo,formGhJsydGhxkVo);
            formGhJsydGhxkVo.setPrjId(prjInstanceVo.getId());
            formGhJsydGhxkVo.setId(null);
            formGhJsydGhxkVo.setTaskId(Long.parseLong(taskDefId));
        }
        prjFormVO.setObject(formGhJsydGhxkVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId,String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormGhJsydGhxk record = new FormGhJsydGhxk();
		FormGhJsydGhxkVo formGhJsydGhxkVo = new FormGhJsydGhxkVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formGhJsydGhxkMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formGhJsydGhxkVo);
        prjFormVO.setObject(formGhJsydGhxkVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取 珠海市建设用地规划许可申请表 实例
	 * @param pid
	 * @return
	 */
	private FormGhJsydGhxkVo getFormGhJsydGhxkVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormGhJsydGhxk record = new FormGhJsydGhxk();
		FormGhJsydGhxkVo formGhJsydGhxkVo = new FormGhJsydGhxkVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formGhJsydGhxkMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formGhJsydGhxkVo);
        return formGhJsydGhxkVo;
	}

}
