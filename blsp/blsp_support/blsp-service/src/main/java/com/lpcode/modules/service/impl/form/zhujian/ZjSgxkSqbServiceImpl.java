package com.lpcode.modules.service.impl.form.zhujian;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormZjSgxkSqb;
import com.lpcode.modules.blsp.entity.FormZjSgxkSqbJl;
import com.lpcode.modules.blsp.entity.FormZjSgxkSqbJs;
import com.lpcode.modules.blsp.entity.FormZjSgxkSqbSg;
import com.lpcode.modules.blsp.mapper.FormZjSgxkSqbJlMapper;
import com.lpcode.modules.blsp.mapper.FormZjSgxkSqbJsMapper;
import com.lpcode.modules.blsp.mapper.FormZjSgxkSqbMapper;
import com.lpcode.modules.blsp.mapper.FormZjSgxkSqbSgMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IZjSgxkSqbService;
import com.lpcode.modules.service.project.dto.pinstance.FormZjSgxkSqbJlVo;
import com.lpcode.modules.service.project.dto.pinstance.FormZjSgxkSqbJsVo;
import com.lpcode.modules.service.project.dto.pinstance.FormZjSgxkSqbSgVo;
import com.lpcode.modules.service.project.dto.pinstance.FormZjSgxkSqbVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 珠海市房屋建筑和市政基础设施工程施工许可并联审批申请表
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.zhujian
 * @author maxiaowei
 * @createDate 2016年6月24日 下午1:31:54
 */
@Service
public class ZjSgxkSqbServiceImpl implements IZjSgxkSqbService {

	@Autowired
	private FormZjSgxkSqbMapper formZjSgxkSqbMapper;
	@Autowired
	private FormZjSgxkSqbJsMapper formZjSgxkSqbJsMapper;
	@Autowired
	private FormZjSgxkSqbSgMapper formZjSgxkSqbSgMapper;
	@Autowired
	private FormZjSgxkSqbJlMapper formZjSgxkSqbJlMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_ZJ_SGXK_SQB;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormZjSgxkSqbVo formZjSgxkSqbVo = (FormZjSgxkSqbVo) object;
		String url = "form/zjSgxkSqbForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formZjSgxkSqbVo.getId() == null) {
        	formZjSgxkSqbVo.setCreatTime(new Date());
        	formZjSgxkSqbVo.setCreator(UserUtils.getUser().getId());
        	formZjSgxkSqbVo.setUpdateTime(new Date());
        	formZjSgxkSqbVo.setUpdator(UserUtils.getUser().getId());
        	formZjSgxkSqbVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	FormZjSgxkSqb formZjSgxkSqb = new FormZjSgxkSqb();
        	BeanCopy.copyProperties(formZjSgxkSqbVo, formZjSgxkSqb, FormZjSgxkSqb.class);
        	formZjSgxkSqbMapper.insert(formZjSgxkSqb);
        	Long id = formZjSgxkSqb.getId();
        	if(id != null){
        		//存入对应   建设单位送样见证人信息   数据
        		List<FormZjSgxkSqbJsVo> buiList = formZjSgxkSqbVo.getBuiList();
        		if( buiList != null ){
        			for (FormZjSgxkSqbJsVo bui : buiList) {
        				bui.setzId(id);
        				bui.setbIsDelete(BaseCode.DEL_FLAG_NORMAL);
        				formZjSgxkSqbJsMapper.insert(bui);
    				}
        		}
        		//存入对应   施工专业分包单位信息   数据
        		List<FormZjSgxkSqbSgVo> conList = formZjSgxkSqbVo.getConList();
        		if( conList != null ){
        			for (FormZjSgxkSqbSgVo con : conList) {
        				con.setzId(id);
        				con.setcIsDelete(BaseCode.DEL_FLAG_NORMAL);
        				formZjSgxkSqbSgMapper.insert(con);
    				}
        		}
        		//存入对应  监理单位项目主要监理人员信息   数据
        		List<FormZjSgxkSqbJlVo> supList = formZjSgxkSqbVo.getSupList();
        		if( supList != null ){
        			for (FormZjSgxkSqbJlVo sup : supList) {
        				sup.setzId(id);
        				sup.setsIsDelete(BaseCode.DEL_FLAG_NORMAL);
        				formZjSgxkSqbJlMapper.insert(sup);
    				}
        		}
        	}
        }else {
        	formZjSgxkSqbVo.setUpdateTime(new Date());
        	formZjSgxkSqbVo.setUpdator(UserUtils.getUser().getId());
        	FormZjSgxkSqb formZjSgxkSqb = new FormZjSgxkSqb();
        	BeanCopy.copyProperties(formZjSgxkSqbVo, formZjSgxkSqb, FormZjSgxkSqb.class);
        	formZjSgxkSqbMapper.updateByPrimaryKeySelective(formZjSgxkSqb);
        	FormZjSgxkSqbVo zjSgxkSqbVo = getFormZjSgxkSqbVo(formZjSgxkSqb.getPrjId(),formZjSgxkSqb.getTaskId().toString());
        	List<FormZjSgxkSqbJsVo> buiList = zjSgxkSqbVo.getBuiList();
        	List<FormZjSgxkSqbSgVo> conList = zjSgxkSqbVo.getConList();
        	List<FormZjSgxkSqbJlVo> supList = zjSgxkSqbVo.getSupList();
        	//修改  建设单位送样见证人信息
        	List<FormZjSgxkSqbJsVo> jsList = formZjSgxkSqbVo.getBuiList();
        	//将未提交过来的   建设单位送样见证人信息   删除标志位改为1（删除）
        	if(buiList != null){
        		for (FormZjSgxkSqbJsVo bui : buiList) {
        			int i=0;
					for (FormZjSgxkSqbJsVo js : jsList) {
						if(bui.getbId() == js.getbId()){
							i++;
						}
					}
					if(i == 0){
						bui.setbIsDelete("1");
						formZjSgxkSqbJsMapper.updateByPrimaryKeySelective(bui);
					}
				}
        	}
        	if( jsList != null ){
        		for (FormZjSgxkSqbJsVo js : jsList) {
            		if(js.getbId() != null){
            			formZjSgxkSqbJsMapper.updateByPrimaryKeySelective(js);
            		}else{
            			js.setzId(formZjSgxkSqb.getId());
            			js.setbIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formZjSgxkSqbJsMapper.insert(js);
            		}
    			}
        	}
        	//修改   施工专业分包单位信息
        	List<FormZjSgxkSqbSgVo> sgList = formZjSgxkSqbVo.getConList();
        	//将未提交过来的  施工专业分包单位信息   删除标志位改为1（删除）
        	if(conList != null){
        		for (FormZjSgxkSqbSgVo con : conList) {
        			int i=0;
					for (FormZjSgxkSqbSgVo sg : sgList) {
						if(con.getcId() == sg.getcId()){
							i++;
						}
					}
					if(i == 0){
						con.setcIsDelete("1");
						formZjSgxkSqbSgMapper.updateByPrimaryKeySelective(con);
					}
				}
        	}
        	if( sgList != null ){
        		for (FormZjSgxkSqbSgVo sg : sgList) {
            		if(sg.getcId() != null){
            			formZjSgxkSqbSgMapper.updateByPrimaryKeySelective(sg);
            		}else{
            			sg.setzId(formZjSgxkSqb.getId());
    					sg.setcIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formZjSgxkSqbSgMapper.insert(sg);
            		}
    			}
        	}
        	//修改   监理单位项目主要监理人员信息
        	List<FormZjSgxkSqbJlVo> jlList = formZjSgxkSqbVo.getSupList();
        	//将未提交过来的   监理单位项目主要监理人员信息   删除标志位改为1（删除）
        	if(supList != null){
        		for (FormZjSgxkSqbJlVo sup : supList) {
        			int i=0;
					for (FormZjSgxkSqbJlVo jl : jlList) {
						if(sup.getsId() == jl.getsId()){
							i++;
						}
					}
					if(i == 0){
						sup.setsIsDelete("1");
						formZjSgxkSqbJlMapper.updateByPrimaryKeySelective(sup);
					}
				}
        	}
        	if( jlList != null ){
        		for (FormZjSgxkSqbJlVo jl : jlList) {
            		if(jl.getsId() != null){
            			formZjSgxkSqbJlMapper.updateByPrimaryKeySelective(jl);
            		}else{
            			jl.setzId(formZjSgxkSqb.getId());
            			jl.setsIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formZjSgxkSqbJlMapper.insert(jl);
            		}
    			}
        	}
        }
        prjFormVO.setObject(formZjSgxkSqbVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo, String taskDefId) {
		String url = "modules/bizform/zjSgxkSqbForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormZjSgxkSqbVo formZjSgxkSqbVo = getFormZjSgxkSqbVo(prjInstanceVo.getId(),taskDefId);
        if (formZjSgxkSqbVo == null) {
        	formZjSgxkSqbVo = new FormZjSgxkSqbVo();
        	BeanCopy.copyProperties(prjInstanceVo,formZjSgxkSqbVo);
            formZjSgxkSqbVo.setPrjId(prjInstanceVo.getId());
            formZjSgxkSqbVo.setId(null);
            formZjSgxkSqbVo.setTaskId(Long.valueOf(taskDefId));
        }
        prjFormVO.setObject(formZjSgxkSqbVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId, String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormZjSgxkSqb record = new FormZjSgxkSqb();
		FormZjSgxkSqbVo formZjSgxkSqbVo = new FormZjSgxkSqbVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formZjSgxkSqbMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formZjSgxkSqbVo);
        //根据  珠海市房屋建筑和市政基础设施工程施工许可并联审批申请表 的ID  查询出 其中的  建设单位送样见证人信息
        FormZjSgxkSqbJs js = new FormZjSgxkSqbJs();
        js.setzId(record.getId());
        js.setbIsDelete("0");
        List<FormZjSgxkSqbJs> jsList = formZjSgxkSqbJsMapper.selectByEntitySelective(js);
        if( jsList != null ){
        	List<FormZjSgxkSqbJsVo> buiList = new ArrayList<FormZjSgxkSqbJsVo>();
            BeanCopy.copyPropertiesForList(jsList, buiList, FormZjSgxkSqbJsVo.class);
            formZjSgxkSqbVo.setBuiList(buiList);
        }
        //根据   珠海市房屋建筑和市政基础设施工程施工许可并联审批申请表   的ID  查询出 其中的   施工专业分包单位信息
        FormZjSgxkSqbSg sg = new FormZjSgxkSqbSg();
        sg.setzId(record.getId());
        sg.setcIsDelete("0");
        List<FormZjSgxkSqbSg> sgList = formZjSgxkSqbSgMapper.selectByEntitySelective(sg);
        if( sgList != null ){
        	List<FormZjSgxkSqbSgVo> conList = new ArrayList<FormZjSgxkSqbSgVo>();
            BeanCopy.copyPropertiesForList(sgList, conList, FormZjSgxkSqbSgVo.class);
            formZjSgxkSqbVo.setConList(conList);
        }
        //根据   珠海市房屋建筑和市政基础设施工程施工许可并联审批申请表   的ID  查询出 其中的   监理单位项目主要监理人员信息
        FormZjSgxkSqbJl jl = new FormZjSgxkSqbJl();
        jl.setzId(record.getId());
        jl.setsIsDelete("0");
        List<FormZjSgxkSqbJl> jlList = formZjSgxkSqbJlMapper.selectByEntitySelective(jl);
        if( jlList != null ){
        	List<FormZjSgxkSqbJlVo> supList = new ArrayList<FormZjSgxkSqbJlVo>();
            BeanCopy.copyPropertiesForList(jlList, supList, FormZjSgxkSqbJlVo.class);
            formZjSgxkSqbVo.setSupList(supList);
        }
        prjFormVO.setObject(formZjSgxkSqbVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取   珠海市房屋建筑和市政基础设施工程施工许可并联审批申请表   实例
	 * @param pid
	 * @return
	 */
	private FormZjSgxkSqbVo getFormZjSgxkSqbVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormZjSgxkSqb record = new FormZjSgxkSqb();
		FormZjSgxkSqbVo formZjSgxkSqbVo = new FormZjSgxkSqbVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formZjSgxkSqbMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formZjSgxkSqbVo);
        //根据  珠海市房屋建筑和市政基础设施工程施工许可并联审批申请表 的ID  查询出 其中的  建设单位送样见证人信息
        FormZjSgxkSqbJs js = new FormZjSgxkSqbJs();
        js.setzId(record.getId());
        js.setbIsDelete("0");
        List<FormZjSgxkSqbJs> jsList = formZjSgxkSqbJsMapper.selectByEntitySelective(js);
        if( jsList != null ){
        	List<FormZjSgxkSqbJsVo> buiList = new ArrayList<FormZjSgxkSqbJsVo>();
            BeanCopy.copyPropertiesForList(jsList, buiList, FormZjSgxkSqbJsVo.class);
            formZjSgxkSqbVo.setBuiList(buiList);
        }
        //根据   珠海市房屋建筑和市政基础设施工程施工许可并联审批申请表   的ID  查询出 其中的   施工专业分包单位信息
        FormZjSgxkSqbSg sg = new FormZjSgxkSqbSg();
        sg.setzId(record.getId());
        sg.setcIsDelete("0");
        List<FormZjSgxkSqbSg> sgList = formZjSgxkSqbSgMapper.selectByEntitySelective(sg);
        if( sgList != null ){
        	List<FormZjSgxkSqbSgVo> conList = new ArrayList<FormZjSgxkSqbSgVo>();
            BeanCopy.copyPropertiesForList(sgList, conList, FormZjSgxkSqbSgVo.class);
            formZjSgxkSqbVo.setConList(conList);
        }
        //根据   珠海市房屋建筑和市政基础设施工程施工许可并联审批申请表   的ID  查询出 其中的   监理单位项目主要监理人员信息
        FormZjSgxkSqbJl jl = new FormZjSgxkSqbJl();
        jl.setzId(record.getId());
        jl.setsIsDelete("0");
        List<FormZjSgxkSqbJl> jlList = formZjSgxkSqbJlMapper.selectByEntitySelective(jl);
        if( jlList != null ){
        	List<FormZjSgxkSqbJlVo> supList = new ArrayList<FormZjSgxkSqbJlVo>();
            BeanCopy.copyPropertiesForList(jlList, supList, FormZjSgxkSqbJlVo.class);
            formZjSgxkSqbVo.setSupList(supList);
        }
        return formZjSgxkSqbVo;
	}

}
