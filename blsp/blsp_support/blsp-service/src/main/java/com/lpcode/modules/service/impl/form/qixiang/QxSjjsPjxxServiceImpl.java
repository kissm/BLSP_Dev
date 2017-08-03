package com.lpcode.modules.service.impl.form.qixiang;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormQxSjjsPjxx;
import com.lpcode.modules.blsp.mapper.FormQxSjjsPjxxMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IQxSjjsPjxxService;
import com.lpcode.modules.service.project.dto.pinstance.FormQxSjjsPjxxVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 防雷装置设计技术评价信息表
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.qixiang
 * @author maxiaowei
 * @createDate 2016年6月22日 上午10:55:10
 */
@Service
public class QxSjjsPjxxServiceImpl implements IQxSjjsPjxxService {

	@Autowired
	private FormQxSjjsPjxxMapper formQxSjjsPjxxMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_QX_SJJS_PJXX;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormQxSjjsPjxxVo formQxSjjsPjxxVo = (FormQxSjjsPjxxVo) object;
		String url = "form/qxSjjsPjxxForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formQxSjjsPjxxVo.getId() == null) {
        	formQxSjjsPjxxVo.setCreatTime(new Date());
        	formQxSjjsPjxxVo.setCreator(UserUtils.getUser().getId());
        	formQxSjjsPjxxVo.setUpdateTime(new Date());
        	formQxSjjsPjxxVo.setUpdator(UserUtils.getUser().getId());
        	formQxSjjsPjxxVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	formQxSjjsPjxxMapper.insert(formQxSjjsPjxxVo);
        }else {
        	formQxSjjsPjxxVo.setUpdateTime(new Date());
        	formQxSjjsPjxxVo.setUpdator(UserUtils.getUser().getId());
        	formQxSjjsPjxxMapper.updateByPrimaryKeySelective(formQxSjjsPjxxVo);
        }
        prjFormVO.setObject(formQxSjjsPjxxVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo, String taskDefId) {
		String url = "modules/bizform/qxSjjsPjxxForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormQxSjjsPjxxVo formQxSjjsPjxxVo = getFormQxSjjsPjxxVo(prjInstanceVo.getId(),taskDefId);
        if (formQxSjjsPjxxVo == null) {
        	formQxSjjsPjxxVo = new FormQxSjjsPjxxVo();
        	BeanCopy.copyProperties(prjInstanceVo,formQxSjjsPjxxVo);
            formQxSjjsPjxxVo.setPrjId(prjInstanceVo.getId());
            formQxSjjsPjxxVo.setId(null);
            formQxSjjsPjxxVo.setTaskId(Long.valueOf(taskDefId));
        }
        prjFormVO.setObject(formQxSjjsPjxxVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId, String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormQxSjjsPjxx record = new FormQxSjjsPjxx();
		FormQxSjjsPjxxVo formQxSjjsPjxxVo = new FormQxSjjsPjxxVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formQxSjjsPjxxMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formQxSjjsPjxxVo);
        prjFormVO.setObject(formQxSjjsPjxxVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取 防雷装置设计技术评价信息表   实例
	 * @param pid
	 * @return
	 */
	private FormQxSjjsPjxxVo getFormQxSjjsPjxxVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormQxSjjsPjxx record = new FormQxSjjsPjxx();
		FormQxSjjsPjxxVo formQxSjjsPjxxVo = new FormQxSjjsPjxxVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formQxSjjsPjxxMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formQxSjjsPjxxVo);
        return formQxSjjsPjxxVo;
	}

}
