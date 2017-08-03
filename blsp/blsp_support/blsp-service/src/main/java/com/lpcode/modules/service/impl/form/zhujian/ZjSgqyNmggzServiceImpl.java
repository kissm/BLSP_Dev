package com.lpcode.modules.service.impl.form.zhujian;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormZjSgqyNmggz;
import com.lpcode.modules.blsp.mapper.FormZjSgqyNmggzMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IZjSgqyNmggzService;
import com.lpcode.modules.service.project.dto.pinstance.FormZjSgqyNmggzVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 珠海市金湾区施工企业农民工工资支付情况报表
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.zhujian
 * @author maxiaowei
 * @createDate 2016年5月30日 下午6:25:03
 */
@Service
public class ZjSgqyNmggzServiceImpl implements IZjSgqyNmggzService {

	@Autowired
	private FormZjSgqyNmggzMapper formZjSgqyNmggzMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_ZJ_SGQY_NMGGZ;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormZjSgqyNmggzVo formZjSgqyNmggzVo = (FormZjSgqyNmggzVo) object;
		String url = "form/zjSgqyNmggzForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formZjSgqyNmggzVo.getId() == null) {
        	formZjSgqyNmggzVo.setCreatTime(new Date());
        	formZjSgqyNmggzVo.setCreator(UserUtils.getUser().getId());
        	formZjSgqyNmggzVo.setUpdateTime(new Date());
        	formZjSgqyNmggzVo.setUpdator(UserUtils.getUser().getId());
        	formZjSgqyNmggzVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	formZjSgqyNmggzMapper.insert(formZjSgqyNmggzVo);
        }else {
        	formZjSgqyNmggzVo.setUpdateTime(new Date());
        	formZjSgqyNmggzVo.setUpdator(UserUtils.getUser().getId());
        	formZjSgqyNmggzMapper.updateByPrimaryKeySelective(formZjSgqyNmggzVo);
        }
        prjFormVO.setObject(formZjSgqyNmggzVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo,String taskDefId) {
		String url = "modules/bizform/zjSgqyNmggzForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormZjSgqyNmggzVo formZjSgqyNmggzVo = getFormZjSgqyNmggzVo(prjInstanceVo.getId(),taskDefId);
        if (formZjSgqyNmggzVo == null) {
        	formZjSgqyNmggzVo = new FormZjSgqyNmggzVo();
        	BeanCopy.copyProperties(prjInstanceVo,formZjSgqyNmggzVo);
            formZjSgqyNmggzVo.setPrjId(prjInstanceVo.getId());
            formZjSgqyNmggzVo.setId(null);
            formZjSgqyNmggzVo.setTaskId(Long.valueOf(taskDefId));
        }
        prjFormVO.setObject(formZjSgqyNmggzVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId,String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormZjSgqyNmggz record = new FormZjSgqyNmggz();
		FormZjSgqyNmggzVo formZjSgqyNmggzVo = new FormZjSgqyNmggzVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formZjSgqyNmggzMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formZjSgqyNmggzVo);
        prjFormVO.setObject(formZjSgqyNmggzVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取 珠海市金湾区施工企业农民工工资支付情况报表 实例
	 * @param pid
	 * @return
	 */
	private FormZjSgqyNmggzVo getFormZjSgqyNmggzVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormZjSgqyNmggz record = new FormZjSgqyNmggz();
		FormZjSgqyNmggzVo formZjSgqyNmggzVo = new FormZjSgqyNmggzVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formZjSgqyNmggzMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formZjSgqyNmggzVo);
        return formZjSgqyNmggzVo;
	}

}
