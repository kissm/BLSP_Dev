package com.lpcode.modules.service.impl.form.qixiang;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormQxJgysSqs;
import com.lpcode.modules.blsp.mapper.FormQxJgysSqsMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IQxJgysSqsService;
import com.lpcode.modules.service.project.dto.pinstance.FormQxJgysSqsVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 防雷装置竣工验收申请书
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.qixiang
 * @author maxiaowei
 * @createDate 2016年6月22日 下午4:20:44
 */
@Service
public class QxJgysSqsServiceImpl implements IQxJgysSqsService {

	@Autowired
	private FormQxJgysSqsMapper formQxJgysSqsMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_QX_JGYS_SQS;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormQxJgysSqsVo formQxJgysSqsVo = (FormQxJgysSqsVo) object;
		String url = "form/qxJgysSqsForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formQxJgysSqsVo.getId() == null) {
        	formQxJgysSqsVo.setCreatTime(new Date());
        	formQxJgysSqsVo.setCreator(UserUtils.getUser().getId());
        	formQxJgysSqsVo.setUpdateTime(new Date());
        	formQxJgysSqsVo.setUpdator(UserUtils.getUser().getId());
        	formQxJgysSqsVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	FormQxJgysSqs formQxJgysSqs = new FormQxJgysSqs();
        	BeanCopy.copyProperties(formQxJgysSqsVo, formQxJgysSqs, FormQxJgysSqs.class);
        	formQxJgysSqsMapper.insert(formQxJgysSqs);
        }else {
        	formQxJgysSqsVo.setUpdateTime(new Date());
        	formQxJgysSqsVo.setUpdator(UserUtils.getUser().getId());
        	FormQxJgysSqs formQxJgysSqs = new FormQxJgysSqs();
        	BeanCopy.copyProperties(formQxJgysSqsVo, formQxJgysSqs, FormQxJgysSqs.class);
        	formQxJgysSqsMapper.updateByPrimaryKeySelective(formQxJgysSqs);
        }
        prjFormVO.setObject(formQxJgysSqsVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo, String taskDefId) {
		String url = "modules/bizform/qxJgysSqsForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormQxJgysSqsVo formQxJgysSqsVo = getFormQxJgysSqsVo(prjInstanceVo.getId(),taskDefId);
        if (formQxJgysSqsVo == null) {
        	formQxJgysSqsVo = new FormQxJgysSqsVo();
        	BeanCopy.copyProperties(prjInstanceVo,formQxJgysSqsVo);
            formQxJgysSqsVo.setPrjId(prjInstanceVo.getId());
            formQxJgysSqsVo.setApplyPrjName(prjInstanceVo.getPrjName());
            formQxJgysSqsVo.setId(null);
            formQxJgysSqsVo.setTaskId(Long.valueOf(taskDefId));
        }
        prjFormVO.setObject(formQxJgysSqsVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId, String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormQxJgysSqs record = new FormQxJgysSqs();
		FormQxJgysSqsVo formQxJgysSqsVo = new FormQxJgysSqsVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formQxJgysSqsMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formQxJgysSqsVo);
        prjFormVO.setObject(formQxJgysSqsVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取  防雷装置竣工验收申请书   实例
	 * @param pid
	 * @return
	 */
	private FormQxJgysSqsVo getFormQxJgysSqsVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormQxJgysSqs record = new FormQxJgysSqs();
		FormQxJgysSqsVo formQxJgysSqsVo = new FormQxJgysSqsVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formQxJgysSqsMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formQxJgysSqsVo);
        return formQxJgysSqsVo;
	}

}
