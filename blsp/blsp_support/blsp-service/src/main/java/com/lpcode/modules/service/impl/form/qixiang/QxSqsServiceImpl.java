package com.lpcode.modules.service.impl.form.qixiang;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormQxSqs;
import com.lpcode.modules.blsp.mapper.FormQxSqsMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IQxSqsService;
import com.lpcode.modules.service.project.dto.pinstance.FormQxSqsVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 防雷装置设计审核（授权书）
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.qixiang
 * @author maxiaowei
 * @createDate 2016年6月21日 下午5:55:04
 */
@Service
public class QxSqsServiceImpl implements IQxSqsService {

	@Autowired
	private FormQxSqsMapper formQxSqsMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_QX_SQS;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormQxSqsVo formQxSqsVo = (FormQxSqsVo) object;
		String url = "form/qxSqsForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formQxSqsVo.getId() == null) {
        	formQxSqsVo.setCreatTime(new Date());
        	formQxSqsVo.setCreator(UserUtils.getUser().getId());
        	formQxSqsVo.setUpdateTime(new Date());
        	formQxSqsVo.setUpdator(UserUtils.getUser().getId());
        	formQxSqsVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	FormQxSqs formQxSqs = new FormQxSqs();
        	BeanCopy.copyProperties(formQxSqsVo, formQxSqs, FormQxSqs.class);
        	formQxSqsMapper.insert(formQxSqs);
        }else {
        	formQxSqsVo.setUpdateTime(new Date());
        	formQxSqsVo.setUpdator(UserUtils.getUser().getId());
        	FormQxSqs formQxSqs = new FormQxSqs();
        	BeanCopy.copyProperties(formQxSqsVo, formQxSqs, FormQxSqs.class);
        	formQxSqsMapper.updateByPrimaryKeySelective(formQxSqs);
        }
        prjFormVO.setObject(formQxSqsVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo, String taskDefId) {
		String url = "modules/bizform/qxSqsForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormQxSqsVo formQxSqsVo = getFormQxSqsVo(prjInstanceVo.getId(),taskDefId);
        if (formQxSqsVo == null) {
        	formQxSqsVo = new FormQxSqsVo();
        	BeanCopy.copyProperties(prjInstanceVo,formQxSqsVo);
            formQxSqsVo.setPrjId(prjInstanceVo.getId());
            formQxSqsVo.setId(null);
            formQxSqsVo.setTaskId(Long.valueOf(taskDefId));
        }
        prjFormVO.setObject(formQxSqsVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId, String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormQxSqs record = new FormQxSqs();
		FormQxSqsVo formQxSqsVo = new FormQxSqsVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formQxSqsMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formQxSqsVo);
        prjFormVO.setObject(formQxSqsVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取  防雷装置设计审核（授权书）   实例
	 * @param pid
	 * @return
	 */
	private FormQxSqsVo getFormQxSqsVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormQxSqs record = new FormQxSqs();
		FormQxSqsVo formQxSqsVo = new FormQxSqsVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formQxSqsMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formQxSqsVo);
        return formQxSqsVo;
	}

}
