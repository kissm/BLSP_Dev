package com.lpcode.modules.service.impl.form.guihua;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormGhSqwts;
import com.lpcode.modules.blsp.mapper.FormGhSqwtsMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IGhSqwtsService;
import com.lpcode.modules.service.project.dto.pinstance.FormGhSqwtsVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 授权委托书、法人代表证明书
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.guihua
 * @author maxiaowei
 * @createDate 2016年6月17日 下午3:22:37
 */
@Service
public class GhSqwtsServiceImpl implements IGhSqwtsService {

	@Autowired
	private FormGhSqwtsMapper formGhSqwtsMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_GH_SQWTS;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormGhSqwtsVo formGhSqwtsVo = (FormGhSqwtsVo) object;
		String url = "form/ghSqwtsForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formGhSqwtsVo.getId() == null) {
        	formGhSqwtsVo.setCreatTime(new Date());
        	formGhSqwtsVo.setCreator(UserUtils.getUser().getId());
        	formGhSqwtsVo.setUpdateTime(new Date());
        	formGhSqwtsVo.setUpdator(UserUtils.getUser().getId());
        	formGhSqwtsVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	formGhSqwtsMapper.insert(formGhSqwtsVo);
        }else {
        	formGhSqwtsVo.setUpdateTime(new Date());
        	formGhSqwtsVo.setUpdator(UserUtils.getUser().getId());
        	formGhSqwtsMapper.updateByPrimaryKeySelective(formGhSqwtsVo);
        }
        prjFormVO.setObject(formGhSqwtsVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo, String taskId) {
		String url = "modules/bizform/ghSqwtsForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormGhSqwtsVo formGhSqwtsVo = getFormGhSqwtsVo(prjInstanceVo.getId(),taskId);
        if (formGhSqwtsVo == null) {
        	formGhSqwtsVo = new FormGhSqwtsVo();
        	BeanCopy.copyProperties(prjInstanceVo,formGhSqwtsVo);
            formGhSqwtsVo.setPrjId(prjInstanceVo.getId());
            formGhSqwtsVo.setId(null);
            formGhSqwtsVo.setTaskId(Long.valueOf(taskId));
        }
        prjFormVO.setObject(formGhSqwtsVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long projectId,String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (projectId == null)
            return null;
        FormGhSqwts record = new FormGhSqwts();
		FormGhSqwtsVo formGhSqwtsVo = new FormGhSqwtsVo();
        record.setPrjId(projectId);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formGhSqwtsMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formGhSqwtsVo);
        prjFormVO.setObject(formGhSqwtsVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取  珠海市建设项目规划选址申请表  实例
	 * @param pid
	 * @return
	 */
	private FormGhSqwtsVo getFormGhSqwtsVo(Long pid,String taskId){
		if (pid == null)
            return null;
		FormGhSqwts record = new FormGhSqwts();
		FormGhSqwtsVo formGhSqwtsVo = new FormGhSqwtsVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskId));
        record = formGhSqwtsMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formGhSqwtsVo);
        return formGhSqwtsVo;
	}

}
