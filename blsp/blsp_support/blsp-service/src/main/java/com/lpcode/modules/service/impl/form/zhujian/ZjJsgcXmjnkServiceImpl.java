package com.lpcode.modules.service.impl.form.zhujian;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormZjJsgcXmjnk;
import com.lpcode.modules.blsp.mapper.FormZjJsgcXmjnkMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IZjJsgcXmjnkService;
import com.lpcode.modules.service.project.dto.pinstance.FormZjJsgcXmjnkVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 金湾区建设工程项目节能卡
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.zhujian
 * @author maxiaowei
 * @createDate 2016年5月31日 下午2:15:33
 */
@Service
public class ZjJsgcXmjnkServiceImpl implements IZjJsgcXmjnkService {

	@Autowired
	private FormZjJsgcXmjnkMapper formZjJsgcXmjnkMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_ZJ_JSGC_XMJNK;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormZjJsgcXmjnkVo formZjJsgcXmjnkVo = (FormZjJsgcXmjnkVo) object;
		String url = "form/zjJsgcXmjnkForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formZjJsgcXmjnkVo.getId() == null) {
        	formZjJsgcXmjnkVo.setCreatTime(new Date());
        	formZjJsgcXmjnkVo.setCreator(UserUtils.getUser().getId());
        	formZjJsgcXmjnkVo.setUpdateTime(new Date());
        	formZjJsgcXmjnkVo.setUpdator(UserUtils.getUser().getId());
        	formZjJsgcXmjnkVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	formZjJsgcXmjnkMapper.insert(formZjJsgcXmjnkVo);
        }else {
        	formZjJsgcXmjnkVo.setUpdateTime(new Date());
        	formZjJsgcXmjnkVo.setUpdator(UserUtils.getUser().getId());
        	formZjJsgcXmjnkMapper.updateByPrimaryKeySelective(formZjJsgcXmjnkVo);
        }
        prjFormVO.setObject(formZjJsgcXmjnkVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo,String taskDefId) {
		String url = "modules/bizform/zjJsgcXmjnkForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormZjJsgcXmjnkVo formZjJsgcXmjnkVo = getFormZjJsgcXmjnkVo(prjInstanceVo.getId(),taskDefId);
        if (formZjJsgcXmjnkVo == null) {
        	formZjJsgcXmjnkVo = new FormZjJsgcXmjnkVo();
        	BeanCopy.copyProperties(prjInstanceVo,formZjJsgcXmjnkVo);
            formZjJsgcXmjnkVo.setPrjId(prjInstanceVo.getId());
            formZjJsgcXmjnkVo.setId(null);
            formZjJsgcXmjnkVo.setTaskId(Long.parseLong(taskDefId));
        }
        prjFormVO.setObject(formZjJsgcXmjnkVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId,String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormZjJsgcXmjnk record = new FormZjJsgcXmjnk();
		FormZjJsgcXmjnkVo formZjJsgcXmjnkVo = new FormZjJsgcXmjnkVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formZjJsgcXmjnkMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formZjJsgcXmjnkVo);
        prjFormVO.setObject(formZjJsgcXmjnkVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取 金湾区建设工程项目节能卡  实例
	 * @param pid
	 * @return
	 */
	private FormZjJsgcXmjnkVo getFormZjJsgcXmjnkVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormZjJsgcXmjnk record = new FormZjJsgcXmjnk();
		FormZjJsgcXmjnkVo formZjJsgcXmjnkVo = new FormZjJsgcXmjnkVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formZjJsgcXmjnkMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formZjJsgcXmjnkVo);
        return formZjJsgcXmjnkVo;
	}

}
