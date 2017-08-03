package com.lpcode.modules.service.impl.form.renfang;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormRfYdjsBjsp;
import com.lpcode.modules.blsp.entity.PrjInstance;
import com.lpcode.modules.blsp.mapper.FormRfYdjsBjspMapper;
import com.lpcode.modules.blsp.mapper.PrjInstanceMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IRfYdjsBjspService;
import com.lpcode.modules.service.project.dto.pinstance.FormRfYdjsBjspVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 人防工程易地建设报建审批
 *
 * @author Pengs
 * @package com.lpcode.modules.service.impl.form.renfang
 * @fileName RfYdjsBjspService
 * @date 16/4/26.
 */
@Service
public class RfYdjsBjspServiceImpl implements IRfYdjsBjspService {
	
	@Autowired
    private FormRfYdjsBjspMapper formRfYdjsBjspMapper ;
	@Autowired
	private PrjInstanceMapper prjInstanceMapper;
	
    @Override
    public String getScene() {
        return FormCode.FORM_RF_YDJS_BJSP;
    }

    @Override
    public PrjFormVO saveOrUpdateForm(Object object) {
    	FormRfYdjsBjspVo formRfYdjsBjspVo = (FormRfYdjsBjspVo) object;
        String url = "form/rfBjshForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formRfYdjsBjspVo.getId() == null) {
        	formRfYdjsBjspVo.setCreatTime(new Date());
        	formRfYdjsBjspVo.setCreator(UserUtils.getUser().getId());
        	formRfYdjsBjspVo.setUpdateTime(new Date());
        	formRfYdjsBjspVo.setUpdator(UserUtils.getUser().getId());
        	formRfYdjsBjspVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	formRfYdjsBjspMapper.insert(formRfYdjsBjspVo);
        }else {
        	formRfYdjsBjspVo.setUpdateTime(new Date());
        	formRfYdjsBjspVo.setUpdator(UserUtils.getUser().getId());
            formRfYdjsBjspMapper.updateByPrimaryKeySelective(formRfYdjsBjspVo);
        }
        prjFormVO.setObject(formRfYdjsBjspVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
    }

    @Override
    public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo,String taskDefId) {
    	String url = "modules/bizform/rfYdjsBjspForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormRfYdjsBjspVo formRfYdjsBjspVo = getFormRfYdjsBjspVo(prjInstanceVo.getId(),taskDefId);
        PrjInstance prjInstance = prjInstanceMapper.selectByPrimaryKey(prjInstanceVo.getId());
        BeanCopy.copyProperties(prjInstance, prjInstanceVo);
        if (formRfYdjsBjspVo == null) {
        	formRfYdjsBjspVo = new FormRfYdjsBjspVo();
        	BeanCopy.copyProperties(prjInstanceVo,formRfYdjsBjspVo);
        	formRfYdjsBjspVo.setPrjId(prjInstanceVo.getId());
        	formRfYdjsBjspVo.setLinkman(prjInstanceVo.getAgentName());
            formRfYdjsBjspVo.setLinkmanPhone(prjInstanceVo.getAgentMphone());
            formRfYdjsBjspVo.setPrjAddress(prjInstanceVo.getPrjAddr());
            formRfYdjsBjspVo.setId(null);
            formRfYdjsBjspVo.setTaskId(Long.parseLong(taskDefId));
        }else{
        	formRfYdjsBjspVo.setPrjName(prjInstanceVo.getPrjName());
        	formRfYdjsBjspVo.setLinkman(prjInstanceVo.getAgentName());
            formRfYdjsBjspVo.setLinkmanPhone(prjInstanceVo.getAgentMphone());
            if(!StringUtils.isBlank(prjInstanceVo.getCompanyAddr())){
            	formRfYdjsBjspVo.setPrjAddress(prjInstanceVo.getPrjAddr());
            }
            if(!StringUtils.isBlank(prjInstanceVo.getPrjCode())){
            	formRfYdjsBjspVo.setPrjCode(prjInstanceVo.getPrjCode());
            }
            if(!StringUtils.isBlank(prjInstanceVo.getCompany())){
            	formRfYdjsBjspVo.setCompany(prjInstanceVo.getCompany());
            }
            if(!StringUtils.isBlank(prjInstanceVo.getCompanyAddr())){
            	formRfYdjsBjspVo.setCompanyAddr(prjInstanceVo.getCompanyAddr());
            }
            if(!StringUtils.isBlank(prjInstanceVo.getCompanyMphone())){
            	formRfYdjsBjspVo.setCompanyMphone(prjInstanceVo.getCompanyMphone());
            }
            if(!StringUtils.isBlank(prjInstanceVo.getLegalEntity())){
            	formRfYdjsBjspVo.setLegalEntity(prjInstanceVo.getLegalEntity());
            }
            if(!StringUtils.isBlank(prjInstanceVo.getEntityPhone())){
            	formRfYdjsBjspVo.setEntityPhone(prjInstanceVo.getEntityPhone());
            }
        }
        prjFormVO.setObject(formRfYdjsBjspVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
    }

    @Override
    public PrjFormVO getByPrjcetId(Long prjectId,String taskDefId) {
        PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormRfYdjsBjsp record = new FormRfYdjsBjsp();
        FormRfYdjsBjspVo formRfYdjsBjspVo = new FormRfYdjsBjspVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formRfYdjsBjspMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formRfYdjsBjspVo);
        prjFormVO.setObject(formRfYdjsBjspVo);
        return prjFormVO;
    }
    
    public FormRfYdjsBjspVo getFormRfYdjsBjspVo(Long pid,String taskDefId) {
        if (pid == null)
            return null;
        FormRfYdjsBjsp record = new FormRfYdjsBjsp();
        FormRfYdjsBjspVo formRfYdjsBjspVo = new FormRfYdjsBjspVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formRfYdjsBjspMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formRfYdjsBjspVo);
        return formRfYdjsBjspVo;
    }


}
