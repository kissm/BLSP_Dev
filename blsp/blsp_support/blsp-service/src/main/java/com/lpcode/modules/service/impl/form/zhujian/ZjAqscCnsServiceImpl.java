package com.lpcode.modules.service.impl.form.zhujian;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormZjAqscCns;
import com.lpcode.modules.blsp.mapper.FormZjAqscCnsMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IZjAqscCnsService;
import com.lpcode.modules.service.project.dto.pinstance.FormZjAqscCnsVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 珠海市建设工程项目安全生产文明施工目标管理责任承诺书
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.zhujian
 * @author maxiaowei
 * @createDate 2016年6月23日 上午11:36:07
 */
@Service
public class ZjAqscCnsServiceImpl implements IZjAqscCnsService {

	@Autowired
	private FormZjAqscCnsMapper formZjAqscCnsMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_ZJ_AQSC_CNS;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormZjAqscCnsVo formZjAqscCnsVo = (FormZjAqscCnsVo) object;
		String url = "form/zjAqscCnsForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formZjAqscCnsVo.getId() == null) {
        	formZjAqscCnsVo.setCreatTime(new Date());
        	formZjAqscCnsVo.setCreator(UserUtils.getUser().getId());
        	formZjAqscCnsVo.setUpdateTime(new Date());
        	formZjAqscCnsVo.setUpdator(UserUtils.getUser().getId());
        	formZjAqscCnsVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	FormZjAqscCns formZjAqscCns = new FormZjAqscCns();
        	BeanCopy.copyProperties(formZjAqscCnsVo, formZjAqscCns, FormZjAqscCns.class);
        	formZjAqscCnsMapper.insert(formZjAqscCns);
        }else {
        	formZjAqscCnsVo.setUpdateTime(new Date());
        	formZjAqscCnsVo.setUpdator(UserUtils.getUser().getId());
        	FormZjAqscCns formZjAqscCns = new FormZjAqscCns();
        	BeanCopy.copyProperties(formZjAqscCnsVo, formZjAqscCns, FormZjAqscCns.class);
        	formZjAqscCnsMapper.updateByPrimaryKeySelective(formZjAqscCns);
        }
        prjFormVO.setObject(formZjAqscCnsVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo, String taskDefId) {
		String url = "modules/bizform/zjAqscCnsForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormZjAqscCnsVo formZjAqscCnsVo = getFormZjAqscCnsVo(prjInstanceVo.getId(),taskDefId);
        if (formZjAqscCnsVo == null) {
        	formZjAqscCnsVo = new FormZjAqscCnsVo();
        	BeanCopy.copyProperties(prjInstanceVo,formZjAqscCnsVo);
            formZjAqscCnsVo.setPrjId(prjInstanceVo.getId());
            formZjAqscCnsVo.setId(null);
            formZjAqscCnsVo.setTaskId(Long.valueOf(taskDefId));
        }
        prjFormVO.setObject(formZjAqscCnsVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId, String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormZjAqscCns record = new FormZjAqscCns();
		FormZjAqscCnsVo formZjAqscCnsVo = new FormZjAqscCnsVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formZjAqscCnsMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formZjAqscCnsVo);
        prjFormVO.setObject(formZjAqscCnsVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取  珠海市建设工程项目安全生产文明施工目标管理责任承诺书   实例
	 * @param pid
	 * @return
	 */
	private FormZjAqscCnsVo getFormZjAqscCnsVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormZjAqscCns record = new FormZjAqscCns();
		FormZjAqscCnsVo formZjAqscCnsVo = new FormZjAqscCnsVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formZjAqscCnsMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formZjAqscCnsVo);
        return formZjAqscCnsVo;
	}

}
