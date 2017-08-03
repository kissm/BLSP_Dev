package com.lpcode.modules.service.impl.form.guotu;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormGtSqwtsFrdbzms;
import com.lpcode.modules.blsp.mapper.FormGtSqwtsFrdbzmsMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IGtSqwtsFrdbzmsService;
import com.lpcode.modules.service.project.dto.pinstance.FormGtSqwtsFrdbzmsVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 授权委托书、法人代表证明书
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.guotu
 * @author maxiaowei
 * @createDate 2016年5月27日 下午2:58:15
 */
@Service
public class GtSqwtsFrdbzmsServiceImpl implements IGtSqwtsFrdbzmsService {

	@Autowired
	private FormGtSqwtsFrdbzmsMapper formGtSqwtsFrdbzmsMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_GT_SQWTS_FRDBZMS;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormGtSqwtsFrdbzmsVo formGtSqwtsFrdbzmsVo = (FormGtSqwtsFrdbzmsVo) object;
		String url = "form/gtSqwtsFrdbzmsForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formGtSqwtsFrdbzmsVo.getId() == null) {
        	formGtSqwtsFrdbzmsVo.setCreatTime(new Date());
        	formGtSqwtsFrdbzmsVo.setCreator(UserUtils.getUser().getId());
        	formGtSqwtsFrdbzmsVo.setUpdateTime(new Date());
        	formGtSqwtsFrdbzmsVo.setUpdator(UserUtils.getUser().getId());
        	formGtSqwtsFrdbzmsVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	formGtSqwtsFrdbzmsMapper.insert(formGtSqwtsFrdbzmsVo);
        }else {
        	formGtSqwtsFrdbzmsVo.setUpdateTime(new Date());
        	formGtSqwtsFrdbzmsVo.setUpdator(UserUtils.getUser().getId());
        	formGtSqwtsFrdbzmsMapper.updateByPrimaryKeySelective(formGtSqwtsFrdbzmsVo);
        }
        prjFormVO.setObject(formGtSqwtsFrdbzmsVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo,String taskId) {
		String url = "modules/bizform/gtSqwtsFrdbzmsForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormGtSqwtsFrdbzmsVo formGtSqwtsFrdbzmsVo = getFormGtSqwtsFrdbzmsVo(prjInstanceVo.getId(),taskId);
        if (formGtSqwtsFrdbzmsVo == null) {
        	formGtSqwtsFrdbzmsVo = new FormGtSqwtsFrdbzmsVo();
        	BeanCopy.copyProperties(prjInstanceVo,formGtSqwtsFrdbzmsVo);
            formGtSqwtsFrdbzmsVo.setPrjId(prjInstanceVo.getId());
            formGtSqwtsFrdbzmsVo.setId(null);
            formGtSqwtsFrdbzmsVo.setTaskId(Long.valueOf(taskId));
        }
        prjFormVO.setObject(formGtSqwtsFrdbzmsVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long projectId,String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (projectId == null)
            return null;
        FormGtSqwtsFrdbzms record = new FormGtSqwtsFrdbzms();
        FormGtSqwtsFrdbzmsVo formGtSqwtsFrdbzmsVo = new FormGtSqwtsFrdbzmsVo();
        record.setPrjId(projectId);
        record.setTaskId(Long.valueOf(taskDefId));
        record.setIsDelete("0");
        record = formGtSqwtsFrdbzmsMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formGtSqwtsFrdbzmsVo);
        prjFormVO.setObject(formGtSqwtsFrdbzmsVo);
        return prjFormVO;
	}

	/**
	 * 根据项目ID获取  授权委托书、法人代表证明书  实例
	 * @param pid
	 * @return
	 */
	private FormGtSqwtsFrdbzmsVo getFormGtSqwtsFrdbzmsVo(Long pid,String taskId){
		if (pid == null)
            return null;
		FormGtSqwtsFrdbzms record = new FormGtSqwtsFrdbzms();
		FormGtSqwtsFrdbzmsVo formGtSqwtsFrdbzmsVo = new FormGtSqwtsFrdbzmsVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskId));
        record = formGtSqwtsFrdbzmsMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formGtSqwtsFrdbzmsVo);
        return formGtSqwtsFrdbzmsVo;
	}
	
}
