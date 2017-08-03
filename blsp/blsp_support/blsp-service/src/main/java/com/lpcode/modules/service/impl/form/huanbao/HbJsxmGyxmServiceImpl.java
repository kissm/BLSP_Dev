package com.lpcode.modules.service.impl.form.huanbao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormHbJsxmGyxm;
import com.lpcode.modules.blsp.entity.FormHbJsxmGyxmCl;
import com.lpcode.modules.blsp.entity.FormHbJsxmGyxmCp;
import com.lpcode.modules.blsp.entity.FormHbJsxmGyxmSb;
import com.lpcode.modules.blsp.mapper.FormHbJsxmGyxmClMapper;
import com.lpcode.modules.blsp.mapper.FormHbJsxmGyxmCpMapper;
import com.lpcode.modules.blsp.mapper.FormHbJsxmGyxmMapper;
import com.lpcode.modules.blsp.mapper.FormHbJsxmGyxmSbMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IHbJsxmGyxmService;
import com.lpcode.modules.service.project.dto.pinstance.FormHbJsxmGyxmClVo;
import com.lpcode.modules.service.project.dto.pinstance.FormHbJsxmGyxmCpVo;
import com.lpcode.modules.service.project.dto.pinstance.FormHbJsxmGyxmSbVo;
import com.lpcode.modules.service.project.dto.pinstance.FormHbJsxmGyxmVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 环保：建设项目环境影响登记表（工业项目）
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.huanbao
 * @author maxiaowei
 * @createDate 2016年7月12日 下午4:35:57
 */
@Service
public class HbJsxmGyxmServiceImpl implements IHbJsxmGyxmService {

	@Autowired
	private FormHbJsxmGyxmMapper formHbJsxmGyxmMapper;
	@Autowired
	private FormHbJsxmGyxmCpMapper formHbJsxmGyxmCpMapper;
	@Autowired
	private FormHbJsxmGyxmClMapper formHbJsxmGyxmClMapper;
	@Autowired
	private FormHbJsxmGyxmSbMapper formHbJsxmGyxmSbMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_HB_JSXM_GYXM;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormHbJsxmGyxmVo formHbJsxmGyxmVo = (FormHbJsxmGyxmVo) object;
		String url = "form/hbJsxmGyxmForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formHbJsxmGyxmVo.getId() == null) {
        	formHbJsxmGyxmVo.setCreatTime(new Date());
        	formHbJsxmGyxmVo.setCreator(UserUtils.getUser().getId());
        	formHbJsxmGyxmVo.setUpdateTime(new Date());
        	formHbJsxmGyxmVo.setUpdator(UserUtils.getUser().getId());
        	formHbJsxmGyxmVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	FormHbJsxmGyxm formHbJsxmGyxm = new FormHbJsxmGyxm();
        	BeanCopy.copyProperties(formHbJsxmGyxmVo, formHbJsxmGyxm, FormHbJsxmGyxm.class);
        	formHbJsxmGyxmMapper.insert(formHbJsxmGyxm);
        	Long id = formHbJsxmGyxm.getId();
        	if(id != null){
        		//存入对应   主要产品产量信息   数据
        		List<FormHbJsxmGyxmCpVo> proList = formHbJsxmGyxmVo.getProList();
        		if( proList != null ){
        			for (FormHbJsxmGyxmCpVo pro : proList) {
        				pro.sethId(id);
        				pro.setpIsDelete(BaseCode.DEL_FLAG_NORMAL);
        				formHbJsxmGyxmCpMapper.insert(pro);
    				}
        		}
        		//存入对应   主要材料用量信息   数据
        		List<FormHbJsxmGyxmClVo> metList = formHbJsxmGyxmVo.getMetList();
        		if( metList != null ){
        			for (FormHbJsxmGyxmClVo met : metList) {
        				met.sethId(id);
        				met.setmIsDelete(BaseCode.DEL_FLAG_NORMAL);
        				formHbJsxmGyxmClMapper.insert(met);
    				}
        		}
        		//存入对应  主要设备或设施信息   数据
        		List<FormHbJsxmGyxmSbVo> equList = formHbJsxmGyxmVo.getEquList();
        		if( equList != null ){
        			for (FormHbJsxmGyxmSbVo equ : equList) {
        				equ.sethId(id);
        				equ.seteIsDelete(BaseCode.DEL_FLAG_NORMAL);
        				formHbJsxmGyxmSbMapper.insert(equ);
    				}
        		}
        	}
        }else {
        	formHbJsxmGyxmVo.setUpdateTime(new Date());
        	formHbJsxmGyxmVo.setUpdator(UserUtils.getUser().getId());
        	FormHbJsxmGyxm formHbJsxmGyxm = new FormHbJsxmGyxm();
        	BeanCopy.copyProperties(formHbJsxmGyxmVo, formHbJsxmGyxm, FormHbJsxmGyxm.class);
        	formHbJsxmGyxmMapper.updateByPrimaryKeySelective(formHbJsxmGyxm);
        	FormHbJsxmGyxmVo hbJsxmGyxmVo = getFormHbJsxmGyxmVo(formHbJsxmGyxm.getPrjId(),formHbJsxmGyxm.getTaskId().toString());
        	List<FormHbJsxmGyxmCpVo> proList = hbJsxmGyxmVo.getProList();
        	List<FormHbJsxmGyxmClVo> metList = hbJsxmGyxmVo.getMetList();
        	List<FormHbJsxmGyxmSbVo> equList = hbJsxmGyxmVo.getEquList();
        	//修改  主要产品产量信息
        	List<FormHbJsxmGyxmCpVo> cpList = formHbJsxmGyxmVo.getProList();
        	//将未提交过来的   主要产品产量信息   删除标志位改为1（删除）
        	if(proList != null){
        		for (FormHbJsxmGyxmCpVo pro : proList) {
        			int i=0;
					for (FormHbJsxmGyxmCpVo cp : cpList) {
						if(pro.getpId() == cp.getpId()){
							i++;
						}
					}
					if(i == 0){
						pro.setpIsDelete("1");
						formHbJsxmGyxmCpMapper.updateByPrimaryKeySelective(pro);
					}
				}
        	}
        	if( cpList != null ){
        		for (FormHbJsxmGyxmCpVo cp : cpList) {
            		if(cp.getpId() != null){
            			formHbJsxmGyxmCpMapper.updateByPrimaryKeySelective(cp);
            		}else{
            			cp.sethId(formHbJsxmGyxm.getId());
            			cp.setpIsDelete(BaseCode.DEL_FLAG_NORMAL);
            			formHbJsxmGyxmCpMapper.insert(cp);
            		}
    			}
        	}
        	//修改   主要材料用量信息
        	List<FormHbJsxmGyxmClVo> clList = formHbJsxmGyxmVo.getMetList();
        	//将未提交过来的 主要材料用量信息   删除标志位改为1（删除）
        	if(metList != null){
        		for (FormHbJsxmGyxmClVo met : metList) {
        			int i=0;
					for (FormHbJsxmGyxmClVo cl : clList) {
						if(met.getmId() == cl.getmId()){
							i++;
						}
					}
					if(i == 0){
						met.setmIsDelete("1");
						formHbJsxmGyxmClMapper.updateByPrimaryKeySelective(met);
					}
				}
        	}
        	if( clList != null ){
        		for (FormHbJsxmGyxmClVo cl : clList) {
            		if(cl.getmId() != null){
            			formHbJsxmGyxmClMapper.updateByPrimaryKeySelective(cl);
            		}else{
            			cl.sethId(formHbJsxmGyxm.getId());
            			cl.setmIsDelete(BaseCode.DEL_FLAG_NORMAL);
            			formHbJsxmGyxmClMapper.insert(cl);
            		}
    			}
        	}
        	//修改   主要设备或设施信息
        	List<FormHbJsxmGyxmSbVo> sbList = formHbJsxmGyxmVo.getEquList();
        	//将未提交过来的   主要设备或设施信息   删除标志位改为1（删除）
        	if(equList != null){
        		for (FormHbJsxmGyxmSbVo equ : equList) {
        			int i=0;
					for (FormHbJsxmGyxmSbVo sb : sbList) {
						if(equ.geteId() == sb.geteId()){
							i++;
						}
					}
					if(i == 0){
						equ.seteIsDelete("1");
						formHbJsxmGyxmSbMapper.updateByPrimaryKeySelective(equ);
					}
				}
        	}
        	if( sbList != null ){
        		for (FormHbJsxmGyxmSbVo sb : sbList) {
            		if(sb.geteId() != null){
            			formHbJsxmGyxmSbMapper.updateByPrimaryKeySelective(sb);
            		}else{
            			sb.sethId(formHbJsxmGyxm.getId());
            			sb.seteIsDelete(BaseCode.DEL_FLAG_NORMAL);
            			formHbJsxmGyxmSbMapper.insert(sb);
            		}
    			}
        	}
        }
        prjFormVO.setObject(formHbJsxmGyxmVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo, String taskDefId) {
		String url = "modules/bizform/hbJsxmGyxmForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormHbJsxmGyxmVo formHbJsxmGyxmVo = getFormHbJsxmGyxmVo(prjInstanceVo.getId(),taskDefId);
        if (formHbJsxmGyxmVo == null) {
        	formHbJsxmGyxmVo = new FormHbJsxmGyxmVo();
        	BeanCopy.copyProperties(prjInstanceVo,formHbJsxmGyxmVo);
            formHbJsxmGyxmVo.setPrjId(prjInstanceVo.getId());
            formHbJsxmGyxmVo.setId(null);
            formHbJsxmGyxmVo.setTaskId(Long.valueOf(taskDefId));
        }
        prjFormVO.setObject(formHbJsxmGyxmVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId, String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormHbJsxmGyxm record = new FormHbJsxmGyxm();
		FormHbJsxmGyxmVo formHbJsxmGyxmVo = new FormHbJsxmGyxmVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formHbJsxmGyxmMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formHbJsxmGyxmVo);
        //根据  建设项目环境影响登记表（工业项目）表 的ID  查询出 其中的  主要产品产量信息
        FormHbJsxmGyxmCp cp = new FormHbJsxmGyxmCp();
        cp.sethId(record.getId());
        cp.setpIsDelete("0");
        List<FormHbJsxmGyxmCp> cpList = formHbJsxmGyxmCpMapper.selectByEntitySelective(cp);
        if( cpList != null ){
        	List<FormHbJsxmGyxmCpVo> proList = new ArrayList<FormHbJsxmGyxmCpVo>();
            BeanCopy.copyPropertiesForList(cpList, proList, FormHbJsxmGyxmCpVo.class);
            formHbJsxmGyxmVo.setProList(proList);
        }
        //根据   建设项目环境影响登记表（工业项目）表   的ID  查询出 其中的   主要原材料用量信息
        FormHbJsxmGyxmCl cl = new FormHbJsxmGyxmCl();
        cl.sethId(record.getId());
        cl.setmIsDelete("0");
        List<FormHbJsxmGyxmCl> clList = formHbJsxmGyxmClMapper.selectByEntitySelective(cl);
        if( clList != null ){
        	List<FormHbJsxmGyxmClVo> metList = new ArrayList<FormHbJsxmGyxmClVo>();
            BeanCopy.copyPropertiesForList(clList, metList, FormHbJsxmGyxmClVo.class);
            formHbJsxmGyxmVo.setMetList(metList);
        }
        //根据   建设项目环境影响登记表（工业项目）表   的ID  查询出 其中的   主要设备或设施信息
        FormHbJsxmGyxmSb sb = new FormHbJsxmGyxmSb();
        sb.sethId(record.getId());
        sb.seteIsDelete("0");
        List<FormHbJsxmGyxmSb> sbList = formHbJsxmGyxmSbMapper.selectByEntitySelective(sb);
        if( sbList != null ){
        	List<FormHbJsxmGyxmSbVo> equList = new ArrayList<FormHbJsxmGyxmSbVo>();
            BeanCopy.copyPropertiesForList(sbList, equList, FormHbJsxmGyxmSbVo.class);
            formHbJsxmGyxmVo.setEquList(equList);
        }
        prjFormVO.setObject(formHbJsxmGyxmVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取  建设项目环境影响登记表（工业项目）   实例
	 * @param pid
	 * @return
	 */
	private FormHbJsxmGyxmVo getFormHbJsxmGyxmVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormHbJsxmGyxm record = new FormHbJsxmGyxm();
		FormHbJsxmGyxmVo formHbJsxmGyxmVo = new FormHbJsxmGyxmVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formHbJsxmGyxmMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formHbJsxmGyxmVo);
        //根据  建设项目环境影响登记表（工业项目）表 的ID  查询出 其中的  主要产品产量信息
        FormHbJsxmGyxmCp cp = new FormHbJsxmGyxmCp();
        cp.sethId(record.getId());
        cp.setpIsDelete("0");
        List<FormHbJsxmGyxmCp> cpList = formHbJsxmGyxmCpMapper.selectByEntitySelective(cp);
        if( cpList != null ){
        	List<FormHbJsxmGyxmCpVo> proList = new ArrayList<FormHbJsxmGyxmCpVo>();
            BeanCopy.copyPropertiesForList(cpList, proList, FormHbJsxmGyxmCpVo.class);
            formHbJsxmGyxmVo.setProList(proList);
        }
        //根据   建设项目环境影响登记表（工业项目）表   的ID  查询出 其中的   主要原材料用量信息
        FormHbJsxmGyxmCl cl = new FormHbJsxmGyxmCl();
        cl.sethId(record.getId());
        cl.setmIsDelete("0");
        List<FormHbJsxmGyxmCl> clList = formHbJsxmGyxmClMapper.selectByEntitySelective(cl);
        if( clList != null ){
        	List<FormHbJsxmGyxmClVo> metList = new ArrayList<FormHbJsxmGyxmClVo>();
            BeanCopy.copyPropertiesForList(clList, metList, FormHbJsxmGyxmClVo.class);
            formHbJsxmGyxmVo.setMetList(metList);
        }
        //根据   建设项目环境影响登记表（工业项目）表   的ID  查询出 其中的   主要设备或设施信息
        FormHbJsxmGyxmSb sb = new FormHbJsxmGyxmSb();
        sb.sethId(record.getId());
        sb.seteIsDelete("0");
        List<FormHbJsxmGyxmSb> sbList = formHbJsxmGyxmSbMapper.selectByEntitySelective(sb);
        if( sbList != null ){
        	List<FormHbJsxmGyxmSbVo> equList = new ArrayList<FormHbJsxmGyxmSbVo>();
            BeanCopy.copyPropertiesForList(sbList, equList, FormHbJsxmGyxmSbVo.class);
            formHbJsxmGyxmVo.setEquList(equList);
        }
        return formHbJsxmGyxmVo;
	}

}
