package com.lpcode.modules.service.impl.form.guotu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormGtJsydSqb;
import com.lpcode.modules.blsp.entity.FormGtJsydSqbJs;
import com.lpcode.modules.blsp.entity.FormGtJsydSqbZl;
import com.lpcode.modules.blsp.mapper.FormGtJsydSqbJsMapper;
import com.lpcode.modules.blsp.mapper.FormGtJsydSqbMapper;
import com.lpcode.modules.blsp.mapper.FormGtJsydSqbZlMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IGtJsydSqbService;
import com.lpcode.modules.service.project.dto.pinstance.FormGtJsydSqbJsVo;
import com.lpcode.modules.service.project.dto.pinstance.FormGtJsydSqbVo;
import com.lpcode.modules.service.project.dto.pinstance.FormGtJsydSqbZlVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 珠海市建设用地申请表
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.guotu
 * @author maxiaowei
 * @createDate 2016年6月20日 上午9:52:10
 */
@Service
public class GtJsydSqbServiceImpl implements IGtJsydSqbService {

	@Autowired
	private FormGtJsydSqbMapper formGtJsydSqbMapper;
	@Autowired
	private FormGtJsydSqbJsMapper formGtJsydSqbJsMapper;
	@Autowired
	private FormGtJsydSqbZlMapper formGtJsydSqbZlMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_GT_JSYD_SQB;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormGtJsydSqbVo formGtJsydSqbVo = (FormGtJsydSqbVo) object;
		if(formGtJsydSqbVo == null){
			formGtJsydSqbVo = new FormGtJsydSqbVo();
		}
		String url = "form/gtJsydSqbForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formGtJsydSqbVo.getId() == null) {
        	formGtJsydSqbVo.setCreatTime(new Date());
        	formGtJsydSqbVo.setCreator(UserUtils.getUser().getId());
        	formGtJsydSqbVo.setUpdateTime(new Date());
        	formGtJsydSqbVo.setUpdator(UserUtils.getUser().getId());
        	formGtJsydSqbVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	FormGtJsydSqb formGtJsydSqb = new FormGtJsydSqb();
        	BeanCopy.copyProperties(formGtJsydSqbVo, formGtJsydSqb, FormGtJsydSqb.class);
        	//填表日期
        	formGtJsydSqb.setFillFormDate(new Date());
        	formGtJsydSqbMapper.insert(formGtJsydSqb);
        	Long id = formGtJsydSqb.getId();
        	if(id != null){
        		//存入用地建设计划数据
        		List<FormGtJsydSqbJsVo> buiList = formGtJsydSqbVo.getBuiList();
        		if( buiList != null ){
        			for (FormGtJsydSqbJsVo bui : buiList) {
        				bui.setgId(id);
        				bui.setbIsDelete(BaseCode.DEL_FLAG_NORMAL);
        				formGtJsydSqbJsMapper.insert(bui);
    				}
        		}
        		//存入对应国土资源局及历史相关资料信息
        		List<FormGtJsydSqbZlVo> metList = formGtJsydSqbVo.getMetList();
        		if( metList != null ){
        			for (FormGtJsydSqbZlVo met : metList) {
        				met.setgId(id);
        				met.setdIsDelete(BaseCode.DEL_FLAG_NORMAL);
        				formGtJsydSqbZlMapper.insert(met);
    				}
        		}
        	}
        }else {
        	formGtJsydSqbVo.setUpdateTime(new Date());
        	formGtJsydSqbVo.setUpdator(UserUtils.getUser().getId());
        	FormGtJsydSqb formGtJsydSqb = new FormGtJsydSqb();
        	BeanCopy.copyProperties(formGtJsydSqbVo, formGtJsydSqb, FormGtJsydSqb.class);
        	formGtJsydSqbMapper.updateByPrimaryKeySelective(formGtJsydSqb);
        	FormGtJsydSqbVo example = getFormGtJsydSqbVo(formGtJsydSqb.getPrjId(),formGtJsydSqb.getTaskId().toString());
        	List<FormGtJsydSqbJsVo> buiList = example.getBuiList();
        	List<FormGtJsydSqbZlVo> metList = example.getMetList();
        	//修改用地建设计划信息
        	List<FormGtJsydSqbJsVo> jsList = formGtJsydSqbVo.getBuiList();
        	//将未提交过来的建设计划信息删除标志位改为1（删除）
        	if(buiList != null){
        		for (FormGtJsydSqbJsVo buiVo : buiList) {
        			int i=0;
					for (FormGtJsydSqbJsVo js : jsList) {
						if(buiVo.getbId() == js.getbId()){
							i++;
						}
					}
					if(i == 0){
						buiVo.setbIsDelete("1");
						formGtJsydSqbJsMapper.updateByPrimaryKeySelective(buiVo);
					}
				}
        	}
        	if( jsList != null ){
        		for (FormGtJsydSqbJsVo js : jsList) {
            		if(js.getbId() != null){
            			formGtJsydSqbJsMapper.updateByPrimaryKeySelective(js);
            		}else{
            			js.setgId(formGtJsydSqb.getId());
    					js.setbIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formGtJsydSqbJsMapper.insert(js);
            		}
    			}
        	}
        	//修改相关资料信息
        	List<FormGtJsydSqbZlVo> zlList = formGtJsydSqbVo.getMetList();
        	//将未提交过来的相关资料信息删除标志位改为1（删除）
        	if(metList != null){
        		for (FormGtJsydSqbZlVo metVo : metList) {
        			int i=0;
					for (FormGtJsydSqbZlVo zl : zlList) {
						if(metVo.getdId() == zl.getdId()){
							i++;
						}
					}
					if(i == 0){
						metVo.setdIsDelete("1");
						formGtJsydSqbZlMapper.updateByPrimaryKeySelective(metVo);
					}
				}
        	}
        	if( zlList != null ){
        		for (FormGtJsydSqbZlVo zl : zlList) {
            		if(zl.getdId() != null){
            			formGtJsydSqbZlMapper.updateByPrimaryKeySelective(zl);
            		}else{
            			zl.setgId(formGtJsydSqb.getId());
    					zl.setdIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formGtJsydSqbZlMapper.insert(zl);
            		}
    			}
        	}
        }
        prjFormVO.setObject(formGtJsydSqbVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo,String taskDefId) {
		String url = "modules/bizform/gtJsydSqbForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormGtJsydSqbVo formGtJsydSqbVo = getFormGtJsydSqbVo(prjInstanceVo.getId(),taskDefId);
        if (formGtJsydSqbVo == null) {
        	formGtJsydSqbVo = new FormGtJsydSqbVo();
        	BeanCopy.copyProperties(prjInstanceVo,formGtJsydSqbVo);
            formGtJsydSqbVo.setPrjId(prjInstanceVo.getId());
            formGtJsydSqbVo.setId(null);
            formGtJsydSqbVo.setTaskId(Long.parseLong(taskDefId));
        }
        prjFormVO.setObject(formGtJsydSqbVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId,String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormGtJsydSqb record = new FormGtJsydSqb();
		FormGtJsydSqbVo formGtJsydSqbVo = new FormGtJsydSqbVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formGtJsydSqbMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formGtJsydSqbVo);
        //根据 珠海市建设用地申请表 的ID  查询出 其中的用地建设规划
        FormGtJsydSqbJsVo buiExample = new FormGtJsydSqbJsVo();
        buiExample.setgId(record.getId());
        buiExample.setbIsDelete("0");
        List<FormGtJsydSqbJs> buiList = formGtJsydSqbJsMapper.selectByEntitySelective(buiExample);
        if(buiList != null){
        	List<FormGtJsydSqbJsVo> buiVoList = new ArrayList<FormGtJsydSqbJsVo>();
            BeanCopy.copyPropertiesForList(buiList, buiVoList, FormGtJsydSqbJsVo.class);
            formGtJsydSqbVo.setBuiList(buiVoList);
        }
        //根据 珠海市建设用地申请表  的ID  查询出 其中的相关信息
        FormGtJsydSqbZlVo metExample = new FormGtJsydSqbZlVo();
        metExample.setgId(record.getId());
        metExample.setdIsDelete("0");
        List<FormGtJsydSqbZl> metList = formGtJsydSqbZlMapper.selectByEntitySelective(metExample);
        if(metList != null){
        	List<FormGtJsydSqbZlVo> metVoList = new ArrayList<FormGtJsydSqbZlVo>();
            BeanCopy.copyPropertiesForList(metList, metVoList, FormGtJsydSqbZlVo.class);
            formGtJsydSqbVo.setMetList(metVoList);
        }
        prjFormVO.setObject(formGtJsydSqbVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取  珠海市建设用地申请表 实例
	 * @param pid
	 * @return
	 */
	private FormGtJsydSqbVo getFormGtJsydSqbVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormGtJsydSqb record = new FormGtJsydSqb();
		FormGtJsydSqbVo formGtJsydSqbVo = new FormGtJsydSqbVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formGtJsydSqbMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formGtJsydSqbVo);
        //根据 珠海市建设用地申请表 的ID  查询出 其中的用地建设规划
        FormGtJsydSqbJsVo buiExample = new FormGtJsydSqbJsVo();
        buiExample.setgId(record.getId());
        buiExample.setbIsDelete("0");
        List<FormGtJsydSqbJs> buiList = formGtJsydSqbJsMapper.selectByEntitySelective(buiExample);
        if(buiList != null){
        	List<FormGtJsydSqbJsVo> buiVoList = new ArrayList<FormGtJsydSqbJsVo>();
            BeanCopy.copyPropertiesForList(buiList, buiVoList, FormGtJsydSqbJsVo.class);
            formGtJsydSqbVo.setBuiList(buiVoList);
        }
        //根据 珠海市建设用地申请表  的ID  查询出 其中的相关信息
        FormGtJsydSqbZlVo metExample = new FormGtJsydSqbZlVo();
        metExample.setgId(record.getId());
        metExample.setdIsDelete("0");
        List<FormGtJsydSqbZl> metList = formGtJsydSqbZlMapper.selectByEntitySelective(metExample);
        if(metList != null){
        	List<FormGtJsydSqbZlVo> metVoList = new ArrayList<FormGtJsydSqbZlVo>();
            BeanCopy.copyPropertiesForList(metList, metVoList, FormGtJsydSqbZlVo.class);
            formGtJsydSqbVo.setMetList(metVoList);
        }
        return formGtJsydSqbVo;
	}

}
