package com.lpcode.modules.service.impl.form.guihua;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormGhJsgcGhyssqb;
import com.lpcode.modules.blsp.entity.FormGhJsgcGhyssqbCl;
import com.lpcode.modules.blsp.entity.FormGhJsgcGhyssqbJg;
import com.lpcode.modules.blsp.mapper.FormGhJsgcGhyssqbClMapper;
import com.lpcode.modules.blsp.mapper.FormGhJsgcGhyssqbJgMapper;
import com.lpcode.modules.blsp.mapper.FormGhJsgcGhyssqbMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IGhJsgcGhyssqbService;
import com.lpcode.modules.service.project.dto.pinstance.FormGhJsgcGhyssqbClVo;
import com.lpcode.modules.service.project.dto.pinstance.FormGhJsgcGhyssqbJgVo;
import com.lpcode.modules.service.project.dto.pinstance.FormGhJsgcGhyssqbVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 珠海市建设工程（建筑类）规划验收申请表
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.guihua
 * @author maxiaowei
 * @createDate 2016年6月6日 下午3:15:12
 */
@Service
public class GhJsgcGhyssqbServiceImpl implements IGhJsgcGhyssqbService {

	@Autowired
	private FormGhJsgcGhyssqbMapper formGhJsgcGhyssqbMapper;
	@Autowired
	private FormGhJsgcGhyssqbJgMapper formGhJsgcGhyssqbJgMapper;
	@Autowired
	private FormGhJsgcGhyssqbClMapper formGhJsgcGhyssqbClMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_GH_JSGC_GHYSSQB;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormGhJsgcGhyssqbVo formGhJsgcGhyssqbVo = (FormGhJsgcGhyssqbVo) object;
		if(formGhJsgcGhyssqbVo == null){
			formGhJsgcGhyssqbVo = new FormGhJsgcGhyssqbVo();
		}
		String url = "form/ghJsgcGhyssqbForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formGhJsgcGhyssqbVo.getId() == null) {
        	formGhJsgcGhyssqbVo.setCreatTime(new Date());
        	formGhJsgcGhyssqbVo.setCreator(UserUtils.getUser().getId());
        	formGhJsgcGhyssqbVo.setUpdateTime(new Date());
        	formGhJsgcGhyssqbVo.setUpdator(UserUtils.getUser().getId());
        	formGhJsgcGhyssqbVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	FormGhJsgcGhyssqb formGhJsgcGhyssqb = new FormGhJsgcGhyssqb();
        	BeanCopy.copyProperties(formGhJsgcGhyssqbVo, formGhJsgcGhyssqb, FormGhJsgcGhyssqb.class);
        	formGhJsgcGhyssqbMapper.insert(formGhJsgcGhyssqb);
        	Long id = formGhJsgcGhyssqb.getId();
        	if(id != null){
        		//存入对应竣工项目数据
        		List<FormGhJsgcGhyssqbJgVo> comList = formGhJsgcGhyssqbVo.getComList();
        		if( comList != null ){
        			for (FormGhJsgcGhyssqbJgVo com : comList) {
        				com.setgId(id);
        				com.setcIsDelete(BaseCode.DEL_FLAG_NORMAL);
        				formGhJsgcGhyssqbJgMapper.insert(com);
    				}
        		}
        		//存入对应所需材料数据
        		List<FormGhJsgcGhyssqbClVo> metList = formGhJsgcGhyssqbVo.getMetList();
        		if( metList != null ){
        			for (FormGhJsgcGhyssqbClVo met : metList) {
        				met.setgId(id);
        				met.setmIsDelete(BaseCode.DEL_FLAG_NORMAL);
        				formGhJsgcGhyssqbClMapper.insert(met);
    				}
        		}
        	}
        }else {
        	formGhJsgcGhyssqbVo.setUpdateTime(new Date());
        	formGhJsgcGhyssqbVo.setUpdator(UserUtils.getUser().getId());
        	FormGhJsgcGhyssqb formGhJsgcGhyssqb = new FormGhJsgcGhyssqb();
        	BeanCopy.copyProperties(formGhJsgcGhyssqbVo, formGhJsgcGhyssqb, FormGhJsgcGhyssqb.class);
        	formGhJsgcGhyssqbMapper.updateByPrimaryKeySelective(formGhJsgcGhyssqb);
        	FormGhJsgcGhyssqbVo example = getFormGhJsgcGhyssqbVo(formGhJsgcGhyssqb.getPrjId(),formGhJsgcGhyssqb.getTaskId().toString());
        	List<FormGhJsgcGhyssqbJgVo> comList = example.getComList();
        	List<FormGhJsgcGhyssqbClVo> metList = example.getMetList();
        	//修改竣工项目信息
        	List<FormGhJsgcGhyssqbJgVo> jgList = formGhJsgcGhyssqbVo.getComList();
        	//将未提交过来的单位信息删除标志位改为1（删除）
        	if(comList != null){
        		for (FormGhJsgcGhyssqbJgVo comVo : comList) {
        			int i=0;
					for (FormGhJsgcGhyssqbJgVo jg : jgList) {
						if(comVo.getcId() == jg.getcId()){
							i++;
						}
					}
					if(i == 0){
						comVo.setcIsDelete("1");
						formGhJsgcGhyssqbJgMapper.updateByPrimaryKeySelective(comVo);
					}
				}
        	}
        	if( jgList != null ){
        		for (FormGhJsgcGhyssqbJgVo jg : jgList) {
            		if(jg.getcId() != null){
            			formGhJsgcGhyssqbJgMapper.updateByPrimaryKeySelective(jg);
            		}else{
            			jg.setgId(formGhJsgcGhyssqb.getId());
    					jg.setcIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formGhJsgcGhyssqbJgMapper.insert(jg);
            		}
    			}
        	}
        	//修改所需材料信息
        	List<FormGhJsgcGhyssqbClVo> clList = formGhJsgcGhyssqbVo.getMetList();
        	//将未提交过来的单位信息删除标志位改为1（删除）
        	if(metList != null){
        		for (FormGhJsgcGhyssqbClVo metVo : metList) {
        			int i=0;
					for (FormGhJsgcGhyssqbClVo cl : clList) {
						if(metVo.getmId() == cl.getmId()){
							i++;
						}
					}
					if(i == 0){
						metVo.setmIsDelete("1");
						formGhJsgcGhyssqbClMapper.updateByPrimaryKeySelective(metVo);
					}
				}
        	}
        	if( clList != null ){
        		for (FormGhJsgcGhyssqbClVo cl : clList) {
            		if(cl.getmId() != null){
            			formGhJsgcGhyssqbClMapper.updateByPrimaryKeySelective(cl);
            		}else{
            			cl.setgId(formGhJsgcGhyssqb.getId());
    					cl.setmIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formGhJsgcGhyssqbClMapper.insert(cl);
            		}
    			}
        	}
        }
        prjFormVO.setObject(formGhJsgcGhyssqbVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo,String taskDefId) {
		String url = "modules/bizform/ghJsgcGhyssqbForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormGhJsgcGhyssqbVo formGhJsgcGhyssqbVo = getFormGhJsgcGhyssqbVo(prjInstanceVo.getId(),taskDefId);
        if (formGhJsgcGhyssqbVo == null) {
        	formGhJsgcGhyssqbVo = new FormGhJsgcGhyssqbVo();
        	BeanCopy.copyProperties(prjInstanceVo,formGhJsgcGhyssqbVo);
            formGhJsgcGhyssqbVo.setPrjId(prjInstanceVo.getId());
            formGhJsgcGhyssqbVo.setId(null);
            formGhJsgcGhyssqbVo.setTaskId(Long.parseLong(taskDefId));
        }
        prjFormVO.setObject(formGhJsgcGhyssqbVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId,String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormGhJsgcGhyssqb record = new FormGhJsgcGhyssqb();
		FormGhJsgcGhyssqbVo formGhJsgcGhyssqbVo = new FormGhJsgcGhyssqbVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formGhJsgcGhyssqbMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formGhJsgcGhyssqbVo);
        //根据 珠海市建设工程（建筑类）规划验收申请表 的ID  查询出 其中的竣工信息
        FormGhJsgcGhyssqbJgVo comExample = new FormGhJsgcGhyssqbJgVo();
        comExample.setgId(record.getId());
        comExample.setcIsDelete("0");
        List<FormGhJsgcGhyssqbJg> comList = formGhJsgcGhyssqbJgMapper.selectByEntitySelective(comExample);
        if(comList != null){
        	List<FormGhJsgcGhyssqbJgVo> comVoList = new ArrayList<FormGhJsgcGhyssqbJgVo>();
            BeanCopy.copyPropertiesForList(comList, comVoList, FormGhJsgcGhyssqbJgVo.class);
            formGhJsgcGhyssqbVo.setComList(comVoList);
        }
        //根据 珠海市建设工程（建筑类）规划验收申请表 的ID  查询出 其中的所需材料信息
        FormGhJsgcGhyssqbClVo metExample = new FormGhJsgcGhyssqbClVo();
        metExample.setgId(record.getId());
        metExample.setmIsDelete("0");
        List<FormGhJsgcGhyssqbCl> metList = formGhJsgcGhyssqbClMapper.selectByEntitySelective(metExample);
        if(metList != null){
        	List<FormGhJsgcGhyssqbClVo> metVoList = new ArrayList<FormGhJsgcGhyssqbClVo>();
            BeanCopy.copyPropertiesForList(metList, metVoList, FormGhJsgcGhyssqbClVo.class);
            formGhJsgcGhyssqbVo.setMetList(metVoList);
        }
        prjFormVO.setObject(formGhJsgcGhyssqbVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取  珠海市建设工程（建筑类）规划验收申请表 实例
	 * @param pid
	 * @return
	 */
	private FormGhJsgcGhyssqbVo getFormGhJsgcGhyssqbVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormGhJsgcGhyssqb record = new FormGhJsgcGhyssqb();
		FormGhJsgcGhyssqbVo formGhJsgcGhyssqbVo = new FormGhJsgcGhyssqbVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formGhJsgcGhyssqbMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formGhJsgcGhyssqbVo);
        //根据 珠海市建设工程（建筑类）规划验收申请表 的ID  查询出 其中的竣工信息
        FormGhJsgcGhyssqbJgVo comExample = new FormGhJsgcGhyssqbJgVo();
        comExample.setgId(record.getId());
        comExample.setcIsDelete("0");
        List<FormGhJsgcGhyssqbJg> comList = formGhJsgcGhyssqbJgMapper.selectByEntitySelective(comExample);
        if(comList != null){
        	List<FormGhJsgcGhyssqbJgVo> comVoList = new ArrayList<FormGhJsgcGhyssqbJgVo>();
            BeanCopy.copyPropertiesForList(comList, comVoList, FormGhJsgcGhyssqbJgVo.class);
            formGhJsgcGhyssqbVo.setComList(comVoList);
        }
        //根据 珠海市建设工程（建筑类）规划验收申请表 的ID  查询出 其中的所需材料信息
        FormGhJsgcGhyssqbClVo metExample = new FormGhJsgcGhyssqbClVo();
        metExample.setgId(record.getId());
        metExample.setmIsDelete("0");
        List<FormGhJsgcGhyssqbCl> metList = formGhJsgcGhyssqbClMapper.selectByEntitySelective(metExample);
        if(metList != null){
        	List<FormGhJsgcGhyssqbClVo> metVoList = new ArrayList<FormGhJsgcGhyssqbClVo>();
            BeanCopy.copyPropertiesForList(metList, metVoList, FormGhJsgcGhyssqbClVo.class);
            formGhJsgcGhyssqbVo.setMetList(metVoList);
        }
        return formGhJsgcGhyssqbVo;
	}

}
