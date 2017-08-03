package com.lpcode.modules.service.impl.form.renfang;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormRfBjsh;
import com.lpcode.modules.blsp.entity.PrjInstance;
import com.lpcode.modules.blsp.mapper.FormRfBjshMapper;
import com.lpcode.modules.blsp.mapper.PrjInstanceMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IRfBjshService;
import com.lpcode.modules.service.project.dto.pinstance.FormRfBjshVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;


/**
 * 人防工程报建审核
 * @author Pengs
 * @package com.lpcode.modules.service.impl.form.renfang
 * @fileName RfBjshService
 * @date 16/4/26.
 */
@Service
public class RfBjshServiceImpl implements IRfBjshService {
    @Autowired
    private FormRfBjshMapper formRfBjshMapper ;
    @Autowired
    private PrjInstanceMapper prjInstanceMapper;

    @Override
    public String getScene() {
        return FormCode.FORM_RF_BJSH;
    }

    @Override
    public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo,String taskDefId) {
        String url = "modules/bizform/rfBjshForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormRfBjshVo formRfBjshVo = getFormRfBjshVo(prjInstanceVo.getId(),taskDefId);
        PrjInstance prjInstance = prjInstanceMapper.selectByPrimaryKey(prjInstanceVo.getId());
        BeanCopy.copyProperties(prjInstance, prjInstanceVo);
        if (formRfBjshVo == null) {
            formRfBjshVo = new FormRfBjshVo();
            BeanCopy.copyProperties(prjInstanceVo, formRfBjshVo);
            formRfBjshVo.setPrjId(prjInstanceVo.getId());
            formRfBjshVo.setCode(prjInstanceVo.getPrjCode());
        	formRfBjshVo.setLinkman(prjInstanceVo.getAgentName());
            formRfBjshVo.setLinkmanPhone(prjInstanceVo.getAgentMphone());
            formRfBjshVo.setPrjAddress(prjInstanceVo.getPrjAddr());
            formRfBjshVo.setId(null);
            formRfBjshVo.setTaskId(Long.parseLong(taskDefId));
        }else{
        	formRfBjshVo.setPrjName(prjInstanceVo.getPrjName());
            formRfBjshVo.setCode(prjInstanceVo.getPrjCode());
        	formRfBjshVo.setLinkman(prjInstanceVo.getAgentName());
            formRfBjshVo.setLinkmanPhone(prjInstanceVo.getAgentMphone());
            if(!StringUtils.isBlank(prjInstanceVo.getPrjAddr())){
            	formRfBjshVo.setPrjAddress(prjInstanceVo.getPrjAddr());
            }
            if(!StringUtils.isBlank(prjInstanceVo.getCompany())){
            	formRfBjshVo.setCompany(prjInstanceVo.getCompany());
            }
            if(!StringUtils.isBlank(prjInstanceVo.getCompanyAddr())){
            	formRfBjshVo.setCompanyAddr(prjInstanceVo.getCompanyAddr());
            }
            if(!StringUtils.isBlank(prjInstanceVo.getCompanyMphone())){
            	formRfBjshVo.setCompanyMphone(prjInstanceVo.getCompanyMphone());
            }
            if(!StringUtils.isBlank(prjInstanceVo.getLegalEntity())){
            	formRfBjshVo.setLegalEntity(prjInstanceVo.getLegalEntity());
            }
            if(!StringUtils.isBlank(prjInstanceVo.getEntityPhone())){
            	formRfBjshVo.setEntityPhone(prjInstanceVo.getEntityPhone());
            }
        }
        prjFormVO.setObject(formRfBjshVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
    }

    @Override
    public PrjFormVO getByPrjcetId(Long prjectId,String taskDefId) {
        PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormRfBjsh record = new FormRfBjsh();
        FormRfBjshVo formRfBjshVo = new FormRfBjshVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formRfBjshMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formRfBjshVo);
        prjFormVO.setObject(formRfBjshVo);
        return prjFormVO;
    }

    @Override
    public PrjFormVO saveOrUpdateForm(Object object) {
    	FormRfBjshVo formRfBjshVo = (FormRfBjshVo) object;
        String url = "form/rfBjshForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formRfBjshVo.getId() == null) {
        	formRfBjshVo.setCreatTime(new Date());
        	formRfBjshVo.setCreator(UserUtils.getUser().getId());
        	formRfBjshVo.setUpdateTime(new Date());
        	formRfBjshVo.setUpdator(UserUtils.getUser().getId());
        	formRfBjshVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	formRfBjshMapper.insert(formRfBjshVo);
        }else {
            formRfBjshVo.setUpdateTime(new Date());
            formRfBjshVo.setUpdator(UserUtils.getUser().getId());
            formRfBjshMapper.updateByPrimaryKeySelective(formRfBjshVo);
        }
        prjFormVO.setObject(formRfBjshVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
    }


    /**
     * 获取此项目表单FormRfBjshVo 的结果
     *
     * @param pid
     * @return
     */
    public FormRfBjshVo getFormRfBjshVo(Long pid,String taskDefId) {
        if (pid == null)
            return null;
        FormRfBjsh record = new FormRfBjsh();
        FormRfBjshVo FormRfBjshVo = new FormRfBjshVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formRfBjshMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, FormRfBjshVo);
        return FormRfBjshVo;
    }



    // 保存业务表数据
//    if (project.getFormRfYdjsBjspVo() != null) {// 人防工程易地建设报建审批表(表1)
//        FormRfYdjsBjsp record = new FormRfYdjsBjsp();
//        record.setPrjId(prjvo.getId());
//        record.setCreator(UserUtils.getUser().getId());
//        record.setCreatTime(new Date());
//        record.setUpdateTime(new Date());
//        record.setUpdator(UserUtils.getUser().getId());
//        BeanCopy.copyProperties(project.getFormRfYdjsBjspVo(), record);
//        formRfYdjsBjspMapper.insert(record);
//    }
//
//    if (project.getFormRfYdjsJgsbVo() != null) {// 人防易地建设竣工申报表(表2)
//        FormRfYdjsJgsb record = new FormRfYdjsJgsb();
//        BeanCopy.copyProperties(project.getFormRfYdjsJgsbVo(), record);
//        record.setPrjId(prjvo.getId());
//        formRfYdjsJgsbMapper.insert(record);
//        FormRfJsxmqkb ree = new FormRfJsxmqkb();
//        BeanCopy.copyProperties(project.getFormRfJsxmqkbVo(), ree);
//        formRfJsxmqkbMapper.insert(ree);
//    }
//
//    if (project.getFormRfBjshVo() != null) {// 人防工程易地建设报建审核表(表3)
//        FormRfBjsh record = new FormRfBjsh();
//        record.setPrjId(prjvo.getId());
//        record.setCreator(UserUtils.getUser().getId());
//        record.setCreatTime(new Date());
//        record.setUpdateTime(new Date());
//        record.setUpdator(UserUtils.getUser().getId());
//        BeanCopy.copyProperties(project.getFormRfBjshVo(), record);
//        formRfBjshMapper.insert(record);
//    }
}
