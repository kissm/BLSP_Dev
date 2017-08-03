package com.lpcode.modules.service.impl.form.qixiang;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormQxPjjcWts;
import com.lpcode.modules.blsp.mapper.FormQxPjjcWtsMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IQxPjjcWtsService;
import com.lpcode.modules.service.project.dto.pinstance.FormQxPjjcWtsVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 防雷装置设计技术评价和检测服务委托书
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.qixiang
 * @author maxiaowei
 * @createDate 2016年6月22日 下午1:45:39
 */
@Service
public class QxPjjcWtsServiceImpl implements IQxPjjcWtsService {

	@Autowired
	private FormQxPjjcWtsMapper formQxPjjcWtsMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_QX_PJJC_WTS;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormQxPjjcWtsVo formQxPjjcWtsVo = (FormQxPjjcWtsVo) object;
		String url = "form/qxPjjcWtsForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formQxPjjcWtsVo.getId() == null) {
        	formQxPjjcWtsVo.setCreatTime(new Date());
        	formQxPjjcWtsVo.setCreator(UserUtils.getUser().getId());
        	formQxPjjcWtsVo.setUpdateTime(new Date());
        	formQxPjjcWtsVo.setUpdator(UserUtils.getUser().getId());
        	formQxPjjcWtsVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	FormQxPjjcWts formQxPjjcWts = new FormQxPjjcWts();
        	BeanCopy.copyProperties(formQxPjjcWtsVo, formQxPjjcWts, FormQxPjjcWts.class);
        	formQxPjjcWtsMapper.insert(formQxPjjcWts);
        }else {
        	formQxPjjcWtsVo.setUpdateTime(new Date());
        	formQxPjjcWtsVo.setUpdator(UserUtils.getUser().getId());
        	FormQxPjjcWts formQxPjjcWts = new FormQxPjjcWts();
        	BeanCopy.copyProperties(formQxPjjcWtsVo, formQxPjjcWts, FormQxPjjcWts.class);
        	formQxPjjcWtsMapper.updateByPrimaryKeySelective(formQxPjjcWts);
        }
        prjFormVO.setObject(formQxPjjcWtsVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo, String taskDefId) {
		String url = "modules/bizform/qxPjjcWtsForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormQxPjjcWtsVo formQxPjjcWtsVo = getFormQxPjjcWtsVo(prjInstanceVo.getId(),taskDefId);
        if (formQxPjjcWtsVo == null) {
        	formQxPjjcWtsVo = new FormQxPjjcWtsVo();
        	BeanCopy.copyProperties(prjInstanceVo,formQxPjjcWtsVo);
            formQxPjjcWtsVo.setPrjId(prjInstanceVo.getId());
            formQxPjjcWtsVo.setId(null);
            formQxPjjcWtsVo.setTaskId(Long.parseLong(taskDefId));
        }
        prjFormVO.setObject(formQxPjjcWtsVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId, String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormQxPjjcWts record = new FormQxPjjcWts();
		FormQxPjjcWtsVo formQxPjjcWtsVo = new FormQxPjjcWtsVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formQxPjjcWtsMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formQxPjjcWtsVo);
        prjFormVO.setObject(formQxPjjcWtsVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取  防雷装置设计技术评价和检测服务委托书   实例
	 * @param pid
	 * @return
	 */
	private FormQxPjjcWtsVo getFormQxPjjcWtsVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormQxPjjcWts record = new FormQxPjjcWts();
		FormQxPjjcWtsVo formQxPjjcWtsVo = new FormQxPjjcWtsVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formQxPjjcWtsMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formQxPjjcWtsVo);
        return formQxPjjcWtsVo;
	}

}
