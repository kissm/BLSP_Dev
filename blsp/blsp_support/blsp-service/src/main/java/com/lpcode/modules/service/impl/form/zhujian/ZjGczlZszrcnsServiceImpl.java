package com.lpcode.modules.service.impl.form.zhujian;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormZjGczlZszrcns;
import com.lpcode.modules.blsp.mapper.FormZjGczlZszrcnsMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IZjGczlZszrcnsService;
import com.lpcode.modules.service.project.dto.pinstance.FormZjGczlZszrcnsVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 工程质量终身责任承诺书
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.zhujian
 * @author maxiaowei
 * @createDate 2016年5月31日 上午10:43:02
 */
@Service
public class ZjGczlZszrcnsServiceImpl implements IZjGczlZszrcnsService {

	@Autowired
	private FormZjGczlZszrcnsMapper formZjGczlZszrcnsMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_ZJ_GCZL_ZSZRCNS;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormZjGczlZszrcnsVo formZjGczlZszrcnsVo = (FormZjGczlZszrcnsVo) object;
		String url = "form/zjGczlZszrcnsForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formZjGczlZszrcnsVo.getId() == null) {
        	formZjGczlZszrcnsVo.setCreatTime(new Date());
        	formZjGczlZszrcnsVo.setCreator(UserUtils.getUser().getId());
        	formZjGczlZszrcnsVo.setUpdateTime(new Date());
        	formZjGczlZszrcnsVo.setUpdator(UserUtils.getUser().getId());
        	formZjGczlZszrcnsVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	formZjGczlZszrcnsMapper.insert(formZjGczlZszrcnsVo);
        }else {
        	formZjGczlZszrcnsVo.setUpdateTime(new Date());
        	formZjGczlZszrcnsVo.setUpdator(UserUtils.getUser().getId());
        	formZjGczlZszrcnsMapper.updateByPrimaryKeySelective(formZjGczlZszrcnsVo);
        }
        prjFormVO.setObject(formZjGczlZszrcnsVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo,String taskDefId) {
		String url = "modules/bizform/zjGczlZszrcnsForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormZjGczlZszrcnsVo formZjGczlZszrcnsVo = getFormZjGczlZszrcnsVo(prjInstanceVo.getId(),taskDefId);
        
        if (formZjGczlZszrcnsVo == null) {
        	formZjGczlZszrcnsVo = new FormZjGczlZszrcnsVo();
        	BeanCopy.copyProperties(prjInstanceVo,formZjGczlZszrcnsVo);
            formZjGczlZszrcnsVo.setPrjId(prjInstanceVo.getId());
            formZjGczlZszrcnsVo.setId(null);
            formZjGczlZszrcnsVo.setTaskId(Long.valueOf(taskDefId));
        }
        prjFormVO.setObject(formZjGczlZszrcnsVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId,String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormZjGczlZszrcns record = new FormZjGczlZszrcns();
		FormZjGczlZszrcnsVo formZjGczlZszrcnsVo = new FormZjGczlZszrcnsVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formZjGczlZszrcnsMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formZjGczlZszrcnsVo);
        prjFormVO.setObject(formZjGczlZszrcnsVo);
        return prjFormVO;
	}

	/**
	 * 根据项目ID获取 工程质量终身责任承诺书  实例
	 * @param pid
	 * @return
	 */
	private FormZjGczlZszrcnsVo getFormZjGczlZszrcnsVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormZjGczlZszrcns record = new FormZjGczlZszrcns();
		FormZjGczlZszrcnsVo formZjGczlZszrcnsVo = new FormZjGczlZszrcnsVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formZjGczlZszrcnsMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formZjGczlZszrcnsVo);
        return formZjGczlZszrcnsVo;
	}
	
}
