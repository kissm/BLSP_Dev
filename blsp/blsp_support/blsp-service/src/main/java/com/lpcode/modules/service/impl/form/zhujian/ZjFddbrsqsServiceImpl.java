package com.lpcode.modules.service.impl.form.zhujian;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormZjFddbrsqs;
import com.lpcode.modules.blsp.mapper.FormZjFddbrsqsMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IZjFddbrsqsService;
import com.lpcode.modules.service.project.dto.pinstance.FormZjFddbrsqsVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 法定代表人授权书
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.zhujian
 * @author maxiaowei
 * @createDate 2016年5月31日 下午1:12:39
 */
@Service
public class ZjFddbrsqsServiceImpl implements IZjFddbrsqsService {

	@Autowired
	private FormZjFddbrsqsMapper formZjFddbrsqsMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_ZJ_FDDBRSQS;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormZjFddbrsqsVo formZjFddbrsqsVo = (FormZjFddbrsqsVo) object;
		String url = "form/zjGczlZszrcnsForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formZjFddbrsqsVo.getId() == null) {
        	formZjFddbrsqsVo.setCreatTime(new Date());
        	formZjFddbrsqsVo.setCreator(UserUtils.getUser().getId());
        	formZjFddbrsqsVo.setUpdateTime(new Date());
        	formZjFddbrsqsVo.setUpdator(UserUtils.getUser().getId());
        	formZjFddbrsqsVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	formZjFddbrsqsMapper.insert(formZjFddbrsqsVo);
        }else {
        	formZjFddbrsqsVo.setUpdateTime(new Date());
        	formZjFddbrsqsVo.setUpdator(UserUtils.getUser().getId());
        	formZjFddbrsqsMapper.updateByPrimaryKeySelective(formZjFddbrsqsVo);
        }
        prjFormVO.setObject(formZjFddbrsqsVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo,String taskDefId) {
		String url = "modules/bizform/zjFddbrsqsForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormZjFddbrsqsVo formZjFddbrsqsVo = getFormZjFddbrsqsVo(prjInstanceVo.getId(),taskDefId);
        
        if (formZjFddbrsqsVo == null) {
        	formZjFddbrsqsVo = new FormZjFddbrsqsVo();
        	BeanCopy.copyProperties(prjInstanceVo,formZjFddbrsqsVo);
            formZjFddbrsqsVo.setPrjId(prjInstanceVo.getId());
            formZjFddbrsqsVo.setId(null);
            formZjFddbrsqsVo.setTaskId(Long.parseLong(taskDefId));
        }
        prjFormVO.setObject(formZjFddbrsqsVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId,String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormZjFddbrsqs record = new FormZjFddbrsqs();
		FormZjFddbrsqsVo formZjFddbrsqsVo = new FormZjFddbrsqsVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formZjFddbrsqsMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formZjFddbrsqsVo);
        prjFormVO.setObject(formZjFddbrsqsVo);
        return prjFormVO;
	}

	/**
	 * 根据项目ID获取 法定代表人授权书  实例
	 * @param pid
	 * @return
	 */
	private FormZjFddbrsqsVo getFormZjFddbrsqsVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormZjFddbrsqs record = new FormZjFddbrsqs();
		FormZjFddbrsqsVo formZjFddbrsqsVo = new FormZjFddbrsqsVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formZjFddbrsqsMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formZjFddbrsqsVo);
        return formZjFddbrsqsVo;
	}
	
}
