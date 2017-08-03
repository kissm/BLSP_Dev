package com.lpcode.modules.service.impl.form.xiaofang;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormXfJsgcSjshsbb;
import com.lpcode.modules.blsp.entity.FormXfJsgcSjshsbbDw;
import com.lpcode.modules.blsp.entity.FormXfJsgcSjshsbbDwExample;
import com.lpcode.modules.blsp.entity.FormXfJsgcSjshsbbJz;
import com.lpcode.modules.blsp.entity.FormXfJsgcSjshsbbJzExample;
import com.lpcode.modules.blsp.mapper.FormXfJsgcSjshsbbDwMapper;
import com.lpcode.modules.blsp.mapper.FormXfJsgcSjshsbbJzMapper;
import com.lpcode.modules.blsp.mapper.FormXfJsgcSjshsbbMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IXfJsgcSjshsbbService;
import com.lpcode.modules.service.project.dto.pinstance.FormXfJsgcSjshsbbDwVo;
import com.lpcode.modules.service.project.dto.pinstance.FormXfJsgcSjshsbbJzVo;
import com.lpcode.modules.service.project.dto.pinstance.FormXfJsgcSjshsbbVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 建设工程消防设计审核申报表
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.xiaofang
 * @author maxiaowei
 * @createDate 2016年5月25日 上午10:24:43
 */
@Service
public class XfJsgcSjshsbbServiceImpl implements IXfJsgcSjshsbbService {
	
	@Autowired
	private FormXfJsgcSjshsbbMapper formXfJsgcSjshsbbMapper;
	@Autowired
	private FormXfJsgcSjshsbbDwMapper formXfJsgcSjshsbbDwMapper;
	@Autowired
	private FormXfJsgcSjshsbbJzMapper formXfJsgcSjshsbbJzMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_XF_JSGC_SJSHSBB;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormXfJsgcSjshsbbVo formXfJsgcSjshsbbVo = (FormXfJsgcSjshsbbVo) object;
		String url = "form/xfJsgcSjshsbbForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formXfJsgcSjshsbbVo.getId() == null) {
        	formXfJsgcSjshsbbVo.setCreateTime(new Date());
        	formXfJsgcSjshsbbVo.setCreator(UserUtils.getUser().getId());
        	formXfJsgcSjshsbbVo.setUpdateTmie(new Date());
        	formXfJsgcSjshsbbVo.setUpdator(UserUtils.getUser().getId());
        	formXfJsgcSjshsbbVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	FormXfJsgcSjshsbb formXfJsgcSjshsbb = new FormXfJsgcSjshsbb();
        	BeanCopy.copyProperties(formXfJsgcSjshsbbVo, formXfJsgcSjshsbb, FormXfJsgcSjshsbb.class);
        	formXfJsgcSjshsbb.setFillFormDate(new Date());
        	formXfJsgcSjshsbbMapper.insert(formXfJsgcSjshsbb);
        	Long id = formXfJsgcSjshsbb.getId();
        	if(id != null){
        		//存入对应单位数据
        		List<FormXfJsgcSjshsbbDwVo> dwList = formXfJsgcSjshsbbVo.getDwList();
        		if( dwList != null ){
        			for (FormXfJsgcSjshsbbDwVo dw : dwList) {
    					dw.setjId(id);
    					dw.setuIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formXfJsgcSjshsbbDwMapper.insert(dw);
    				}
        		}
        		//存入对应建筑数据
        		List<FormXfJsgcSjshsbbJzVo> jzList = formXfJsgcSjshsbbVo.getJzList();
        		if( jzList != null ){
        			for (FormXfJsgcSjshsbbJzVo jz : jzList) {
    					jz.setjId(id);
    					jz.setbIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formXfJsgcSjshsbbJzMapper.insert(jz);
    				}
        		}
        	}
        }else {
        	formXfJsgcSjshsbbVo.setUpdateTmie(new Date());
        	formXfJsgcSjshsbbVo.setUpdator(UserUtils.getUser().getId());
        	FormXfJsgcSjshsbb formXfJsgcSjshsbb = new FormXfJsgcSjshsbb();
        	BeanCopy.copyProperties(formXfJsgcSjshsbbVo, formXfJsgcSjshsbb, FormXfJsgcSjshsbb.class);
        	formXfJsgcSjshsbbMapper.updateByPrimaryKeySelective(formXfJsgcSjshsbb);
        	FormXfJsgcSjshsbbVo fxjs = getFormXfJsgcSjshsbbVo(formXfJsgcSjshsbb.getPrjId(),formXfJsgcSjshsbb.getTaskId().toString());
        	List<FormXfJsgcSjshsbbDwVo> unitList = fxjs.getDwList();
        	List<FormXfJsgcSjshsbbJzVo> buildList = fxjs.getJzList();
        	//修改单位信息
        	List<FormXfJsgcSjshsbbDwVo> dwList = formXfJsgcSjshsbbVo.getDwList();
        	//将未提交过来的单位信息删除标志位改为1（删除）
        	if(unitList != null){
        		for (FormXfJsgcSjshsbbDwVo dwVo : unitList) {
        			int i=0;
					for (FormXfJsgcSjshsbbDwVo dw : dwList) {
						if(dwVo.getUnitId() == dw.getUnitId()){
							i++;
						}
					}
					if(i == 0){
						dwVo.setuIsDelete("1");
						formXfJsgcSjshsbbDwMapper.updateByPrimaryKeySelective(dwVo);
					}
				}
        	}
        	if( dwList != null ){
        		for (FormXfJsgcSjshsbbDwVo dw : dwList) {
            		if(dw.getUnitId() != null){
            			formXfJsgcSjshsbbDwMapper.updateByPrimaryKeySelective(dw);
            		}else{
            			dw.setjId(formXfJsgcSjshsbb.getId());
    					dw.setuIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formXfJsgcSjshsbbDwMapper.insert(dw);
            		}
    			}
        	}
        	//修改建筑信息
        	List<FormXfJsgcSjshsbbJzVo> jzList = formXfJsgcSjshsbbVo.getJzList();
        	//将未提交过来的建筑信息删除标志位改为1（删除）
        	if(buildList != null){
        		for (FormXfJsgcSjshsbbJzVo jzVo : buildList) {
        			int i=0;
					for (FormXfJsgcSjshsbbJzVo jz : jzList) {
						if(jzVo.getBuildId() == jz.getBuildId()){
							i++;
						}
					}
					if(i == 0){
						jzVo.setbIsDelete("1");
						formXfJsgcSjshsbbJzMapper.updateByPrimaryKeySelective(jzVo);
					}
				}
        	}
        	if( jzList != null ){
        		for (FormXfJsgcSjshsbbJzVo jz : jzList) {
            		if(jz.getBuildId() != null){
            			formXfJsgcSjshsbbJzMapper.updateByPrimaryKeySelective(jz);
            		}else{
            			jz.setjId(formXfJsgcSjshsbb.getId());
    					jz.setbIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formXfJsgcSjshsbbJzMapper.insert(jz);
            		}
    			}
        	}
        }
        prjFormVO.setObject(formXfJsgcSjshsbbVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo,String taskDefId) {
		String url = "modules/bizform/xfJsgcSjshsbbForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormXfJsgcSjshsbbVo formXfJsgcSjshsbbVo = getFormXfJsgcSjshsbbVo(prjInstanceVo.getId(),taskDefId);
        if (formXfJsgcSjshsbbVo == null) {
        	formXfJsgcSjshsbbVo = new FormXfJsgcSjshsbbVo();
        	BeanCopy.copyProperties(prjInstanceVo,formXfJsgcSjshsbbVo);
            formXfJsgcSjshsbbVo.setPrjId(prjInstanceVo.getId());
            formXfJsgcSjshsbbVo.setLinkMan(prjInstanceVo.getAgentName());
            formXfJsgcSjshsbbVo.setLinkPhone(prjInstanceVo.getAgentPhone());
            formXfJsgcSjshsbbVo.setId(null);
            formXfJsgcSjshsbbVo.setTaskId(Long.parseLong(taskDefId));
        }
        prjFormVO.setObject(formXfJsgcSjshsbbVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId,String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormXfJsgcSjshsbb record = new FormXfJsgcSjshsbb();
        FormXfJsgcSjshsbbVo formXfJsgcSjshsbbVo = new FormXfJsgcSjshsbbVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formXfJsgcSjshsbbMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formXfJsgcSjshsbbVo);
        //根据  建设工程消防设计审核申报表 的ID  查询出 其中的单位信息
        FormXfJsgcSjshsbbDwExample dwExample = new FormXfJsgcSjshsbbDwExample();
        dwExample.createCriteria().andJIdEqualTo(record.getId());
        dwExample.createCriteria().andUIsDeleteEqualTo("0");
        List<FormXfJsgcSjshsbbDw> dwList = formXfJsgcSjshsbbDwMapper.selectByExample(dwExample);
        if( dwList != null ){
        	List<FormXfJsgcSjshsbbDwVo> dwVoList = new ArrayList<FormXfJsgcSjshsbbDwVo>();
            BeanCopy.copyPropertiesForList(dwList, dwVoList, FormXfJsgcSjshsbbDwVo.class);
            formXfJsgcSjshsbbVo.setDwList(dwVoList);
        }
        //根据  建设工程消防设计审核申报表 的ID  查询出 其中的建筑信息
        FormXfJsgcSjshsbbJzExample JzExample = new FormXfJsgcSjshsbbJzExample();
        JzExample.createCriteria().andJIdEqualTo(record.getId());
        JzExample.createCriteria().andBIsDeleteEqualTo("0");
        List<FormXfJsgcSjshsbbJz> jzList = formXfJsgcSjshsbbJzMapper.selectByExample(JzExample);
        if( jzList != null ){
        	List<FormXfJsgcSjshsbbJzVo> jzVoList = new ArrayList<FormXfJsgcSjshsbbJzVo>();
            BeanCopy.copyPropertiesForList(jzList, jzVoList, FormXfJsgcSjshsbbJzVo.class);
            formXfJsgcSjshsbbVo.setJzList(jzVoList);
        }
        prjFormVO.setObject(formXfJsgcSjshsbbVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取 建设工程消防设计审核申报表 实例
	 * @param pid
	 * @return
	 */
	private FormXfJsgcSjshsbbVo getFormXfJsgcSjshsbbVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormXfJsgcSjshsbb record = new FormXfJsgcSjshsbb();
		FormXfJsgcSjshsbbVo formXfJsgcSjshsbbVo = new FormXfJsgcSjshsbbVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formXfJsgcSjshsbbMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formXfJsgcSjshsbbVo);
        //根据  建设工程消防设计审核申报表 的ID  查询出 其中的单位信息
        FormXfJsgcSjshsbbDw dw = new FormXfJsgcSjshsbbDw();
        dw.setjId(record.getId());
        dw.setuIsDelete("0");
        List<FormXfJsgcSjshsbbDw> dwList = formXfJsgcSjshsbbDwMapper.selectByEntitySelective(dw);
        if( dwList != null ){
        	List<FormXfJsgcSjshsbbDwVo> dwVoList = new ArrayList<FormXfJsgcSjshsbbDwVo>();
            BeanCopy.copyPropertiesForList(dwList, dwVoList, FormXfJsgcSjshsbbDwVo.class);
            formXfJsgcSjshsbbVo.setDwList(dwVoList);
        }
        //根据  建设工程消防设计审核申报表 的ID  查询出 其中的建筑信息
        FormXfJsgcSjshsbbJz jz = new FormXfJsgcSjshsbbJz();
        jz.setjId(record.getId());
        jz.setbIsDelete("0");
        List<FormXfJsgcSjshsbbJz> jzList = formXfJsgcSjshsbbJzMapper.selectByEntitySelective(jz);
        if( jzList != null ){
        	List<FormXfJsgcSjshsbbJzVo> jzVoList = new ArrayList<FormXfJsgcSjshsbbJzVo>();
            BeanCopy.copyPropertiesForList(jzList, jzVoList, FormXfJsgcSjshsbbJzVo.class);
            formXfJsgcSjshsbbVo.setJzList(jzVoList);
        }
        return formXfJsgcSjshsbbVo;
	}

}
