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
import com.lpcode.modules.blsp.entity.FormZjJsgcZljdzcb;
import com.lpcode.modules.blsp.entity.FormZjJsgcZljdzcbJl;
import com.lpcode.modules.blsp.entity.FormZjJsgcZljdzcbJs;
import com.lpcode.modules.blsp.entity.FormZjJsgcZljdzcbSg;
import com.lpcode.modules.blsp.mapper.FormZjJsgcZljdzcbJlMapper;
import com.lpcode.modules.blsp.mapper.FormZjJsgcZljdzcbJsMapper;
import com.lpcode.modules.blsp.mapper.FormZjJsgcZljdzcbMapper;
import com.lpcode.modules.blsp.mapper.FormZjJsgcZljdzcbSgMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IZjJsgcZljdzcbService;
import com.lpcode.modules.service.project.dto.pinstance.FormZjJsgcZljdzcbJlVo;
import com.lpcode.modules.service.project.dto.pinstance.FormZjJsgcZljdzcbJsVo;
import com.lpcode.modules.service.project.dto.pinstance.FormZjJsgcZljdzcbSgVo;
import com.lpcode.modules.service.project.dto.pinstance.FormZjJsgcZljdzcbVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 建设工程质量监督注册表
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.zhujian
 * @author maxiaowei
 * @createDate 2016年5月31日 下午5:31:01
 */
@Service
public class ZjJsgcZljdzcbServiceImpl implements IZjJsgcZljdzcbService {

	@Autowired
	private FormZjJsgcZljdzcbMapper formZjJsgcZljdzcbMapper;
	@Autowired
	private FormZjJsgcZljdzcbJsMapper formZjJsgcZljdzcbJsMapper;
	@Autowired
	private FormZjJsgcZljdzcbSgMapper formZjJsgcZljdzcbSgMapper;
	@Autowired
	private FormZjJsgcZljdzcbJlMapper formZjJsgcZljdzcbJlMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_ZJ_JSGC_ZLJDZCB;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormZjJsgcZljdzcbVo formZjJsgcZljdzcbVo = (FormZjJsgcZljdzcbVo) object;
		String url = "form/zjJsgcZljdzcbForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formZjJsgcZljdzcbVo.getId() == null) {
        	formZjJsgcZljdzcbVo.setCreatTime(new Date());
        	formZjJsgcZljdzcbVo.setCreator(UserUtils.getUser().getId());
        	formZjJsgcZljdzcbVo.setUpdateTime(new Date());
        	formZjJsgcZljdzcbVo.setUpdator(UserUtils.getUser().getId());
        	formZjJsgcZljdzcbVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	FormZjJsgcZljdzcb formZjJsgcZljdzcb = new FormZjJsgcZljdzcb();
        	BeanCopy.copyProperties(formZjJsgcZljdzcbVo, formZjJsgcZljdzcb, FormZjJsgcZljdzcb.class);
        	formZjJsgcZljdzcb.setFillFormDate(new Date());
        	formZjJsgcZljdzcbMapper.insert(formZjJsgcZljdzcb);
        	Long id = formZjJsgcZljdzcb.getId();
        	if(id != null){
        		//存入对应建设单位组织结构
        		List<FormZjJsgcZljdzcbJsVo> buiList = formZjJsgcZljdzcbVo.getBuiList();
        		if( buiList != null ){
        			for (FormZjJsgcZljdzcbJsVo js : buiList) {
    					js.setjId(id);
    					js.setbIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formZjJsgcZljdzcbJsMapper.insert(js);
    				}
        		}
        		//存入对应施工单位组织结构
        		List<FormZjJsgcZljdzcbSgVo> conList = formZjJsgcZljdzcbVo.getConList();
        		if( conList != null ){
        			for (FormZjJsgcZljdzcbSgVo sg : conList) {
    					sg.setjId(id);
    					sg.setcIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formZjJsgcZljdzcbSgMapper.insert(sg);
    				}
        		}
        		//存入对应监理单位组织结构
        		List<FormZjJsgcZljdzcbJlVo> supList = formZjJsgcZljdzcbVo.getSupList();
        		if( supList != null ){
        			for (FormZjJsgcZljdzcbJlVo jl : supList) {
    					jl.setjId(id);
    					jl.setsIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formZjJsgcZljdzcbJlMapper.insert(jl);
    				}
        		}
        	}
        }else {
        	formZjJsgcZljdzcbVo.setUpdateTime(new Date());
        	formZjJsgcZljdzcbVo.setUpdator(UserUtils.getUser().getId());
        	FormZjJsgcZljdzcb formZjJsgcZljdzcb = new FormZjJsgcZljdzcb();
        	BeanCopy.copyProperties(formZjJsgcZljdzcbVo, formZjJsgcZljdzcb, FormZjJsgcZljdzcb.class);
        	formZjJsgcZljdzcbMapper.updateByPrimaryKeySelective(formZjJsgcZljdzcb);
        	FormZjJsgcZljdzcbVo fzjz = getFormZjJsgcZljdzcbVo(formZjJsgcZljdzcb.getPrjId(),formZjJsgcZljdzcb.getTaskId().toString());
        	List<FormZjJsgcZljdzcbJsVo> buiList = fzjz.getBuiList();
        	List<FormZjJsgcZljdzcbSgVo> conList = fzjz.getConList();
        	List<FormZjJsgcZljdzcbJlVo> supList = fzjz.getSupList();
        	//修改建设单位组织结构信息
        	List<FormZjJsgcZljdzcbJsVo> jsList = formZjJsgcZljdzcbVo.getBuiList();
        	//将未提交过来的信息删除标志位改为1（删除）
        	if(buiList != null){
        		for (FormZjJsgcZljdzcbJsVo jsVo : buiList) {
        			int i=0;
					for (FormZjJsgcZljdzcbJsVo js : jsList) {
						if(jsVo.getbId() == js.getbId()){
							i++;
						}
					}
					if(i == 0){
						jsVo.setbIsDelete("1");
						formZjJsgcZljdzcbJsMapper.updateByPrimaryKeySelective(jsVo);
					}
				}
        	}
        	if( jsList != null ){
        		for (FormZjJsgcZljdzcbJsVo js : jsList) {
            		if(js.getbId() != null){
            			formZjJsgcZljdzcbJsMapper.updateByPrimaryKeySelective(js);
            		}else{
            			js.setjId(formZjJsgcZljdzcb.getId());
    					js.setbIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formZjJsgcZljdzcbJsMapper.insert(js);
            		}
    			}
        	}
        	//修改施工单位组织结构信息
        	List<FormZjJsgcZljdzcbSgVo> sgList = formZjJsgcZljdzcbVo.getConList();
        	//将未提交过来的信息删除标志位改为1（删除）
        	if(conList != null){
        		for (FormZjJsgcZljdzcbSgVo sgVo : conList) {
        			int i=0;
					for (FormZjJsgcZljdzcbSgVo sg : sgList) {
						if(sgVo.getcId() == sg.getcId()){
							i++;
						}
					}
					if(i == 0){
						sgVo.setcIsDelete("1");
						formZjJsgcZljdzcbSgMapper.updateByPrimaryKeySelective(sgVo);
					}
				}
        	}
        	if( sgList != null ){
        		for (FormZjJsgcZljdzcbSgVo sg : sgList) {
            		if(sg.getcId() != null){
            			formZjJsgcZljdzcbSgMapper.updateByPrimaryKeySelective(sg);
            		}else{
            			sg.setjId(formZjJsgcZljdzcb.getId());
    					sg.setcIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formZjJsgcZljdzcbSgMapper.insert(sg);
            		}
    			}
        	}
        	//修改监理单位组织结构信息
        	List<FormZjJsgcZljdzcbJlVo> jlList = formZjJsgcZljdzcbVo.getSupList();
        	//将未提交过来的信息删除标志位改为1（删除）
        	if(supList != null){
        		for (FormZjJsgcZljdzcbJlVo jlVo : supList) {
        			int i=0;
					for (FormZjJsgcZljdzcbJlVo jl : jlList) {
						if(jlVo.getsId() == jl.getsId()){
							i++;
						}
					}
					if(i == 0){
						jlVo.setsIsDelete("1");
						formZjJsgcZljdzcbJlMapper.updateByPrimaryKeySelective(jlVo);
					}
				}
        	}
        	if( jlList != null ){
        		for (FormZjJsgcZljdzcbJlVo jl : jlList) {
            		if(jl.getsId() != null){
            			formZjJsgcZljdzcbJlMapper.updateByPrimaryKeySelective(jl);
            		}else{
            			jl.setjId(formZjJsgcZljdzcb.getId());
    					jl.setsIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formZjJsgcZljdzcbJlMapper.insert(jl);
            		}
    			}
        	}
        }
        prjFormVO.setObject(formZjJsgcZljdzcbVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo,String taskDefId) {
		String url = "modules/bizform/zjJsgcZljdzcbForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormZjJsgcZljdzcbVo formZjJsgcZljdzcbVo = getFormZjJsgcZljdzcbVo(prjInstanceVo.getId(),taskDefId);
        if (formZjJsgcZljdzcbVo == null) {
        	formZjJsgcZljdzcbVo = new FormZjJsgcZljdzcbVo();
        	BeanCopy.copyProperties(prjInstanceVo,formZjJsgcZljdzcbVo);
            formZjJsgcZljdzcbVo.setPrjId(prjInstanceVo.getId());
            formZjJsgcZljdzcbVo.setId(null);
            formZjJsgcZljdzcbVo.setTaskId(Long.parseLong(taskDefId));
        }
        prjFormVO.setObject(formZjJsgcZljdzcbVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId,String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormZjJsgcZljdzcb record = new FormZjJsgcZljdzcb();
		FormZjJsgcZljdzcbVo formZjJsgcZljdzcbVo = new FormZjJsgcZljdzcbVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formZjJsgcZljdzcbMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formZjJsgcZljdzcbVo);
      //根据  建设工程质量监督注册表 的ID  查询出 其中的建设单位组织结构表信息
        FormZjJsgcZljdzcbJs jsExample = new FormZjJsgcZljdzcbJs();
        jsExample.setjId(record.getId());
        jsExample.setbIsDelete("0");
        List<FormZjJsgcZljdzcbJs> jsList = formZjJsgcZljdzcbJsMapper.selectByEntitySelective(jsExample);
        if(jsList != null){
        	List<FormZjJsgcZljdzcbJsVo> buiList = new ArrayList<FormZjJsgcZljdzcbJsVo>();
            BeanCopy.copyPropertiesForList(jsList, buiList, FormZjJsgcZljdzcbJsVo.class);
            formZjJsgcZljdzcbVo.setBuiList(buiList);
        }
        //根据  建设工程质量监督注册表 的ID  查询出 其中的施工单位组织结构表信息
        FormZjJsgcZljdzcbSg sgExample = new FormZjJsgcZljdzcbSg();
        sgExample.setjId(record.getId());
        sgExample.setcIsDelete("0");
        List<FormZjJsgcZljdzcbSg> sgList = formZjJsgcZljdzcbSgMapper.selectByEntitySelective(sgExample);
        if(sgList != null){
        	List<FormZjJsgcZljdzcbSgVo> conList = new ArrayList<FormZjJsgcZljdzcbSgVo>();
            BeanCopy.copyPropertiesForList(sgList, conList, FormZjJsgcZljdzcbSgVo.class);
            formZjJsgcZljdzcbVo.setConList(conList);
        }
        //根据  建设工程质量监督注册表 的ID  查询出 其中的监理单位组织结构表信息
        FormZjJsgcZljdzcbJl jlExample = new FormZjJsgcZljdzcbJl();
        jlExample.setjId(record.getId());
        jlExample.setsIsDelete("0");
        List<FormZjJsgcZljdzcbJl> jlList = formZjJsgcZljdzcbJlMapper.selectByEntitySelective(jlExample);
        if(jsList != null){
        	List<FormZjJsgcZljdzcbJlVo> supList = new ArrayList<FormZjJsgcZljdzcbJlVo>();
            BeanCopy.copyPropertiesForList(jlList, supList, FormZjJsgcZljdzcbJlVo.class);
            formZjJsgcZljdzcbVo.setSupList(supList);
        }
        prjFormVO.setObject(formZjJsgcZljdzcbVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取  建设工程质量监督注册表  实例
	 * @param pid
	 * @return
	 */
	private FormZjJsgcZljdzcbVo getFormZjJsgcZljdzcbVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormZjJsgcZljdzcb record = new FormZjJsgcZljdzcb();
		FormZjJsgcZljdzcbVo formZjJsgcZljdzcbVo = new FormZjJsgcZljdzcbVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formZjJsgcZljdzcbMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formZjJsgcZljdzcbVo);
        //根据  建设工程质量监督注册表 的ID  查询出 其中的建设单位组织结构表信息
        FormZjJsgcZljdzcbJs jsExample = new FormZjJsgcZljdzcbJs();
        jsExample.setjId(record.getId());
        jsExample.setbIsDelete("0");
        List<FormZjJsgcZljdzcbJs> jsList = formZjJsgcZljdzcbJsMapper.selectByEntitySelective(jsExample);
        if(jsList != null){
        	List<FormZjJsgcZljdzcbJsVo> buiList = new ArrayList<FormZjJsgcZljdzcbJsVo>();
            BeanCopy.copyPropertiesForList(jsList, buiList, FormZjJsgcZljdzcbJsVo.class);
            formZjJsgcZljdzcbVo.setBuiList(buiList);
        }
        //根据  建设工程质量监督注册表 的ID  查询出 其中的施工单位组织结构表信息
        FormZjJsgcZljdzcbSg sgExample = new FormZjJsgcZljdzcbSg();
        sgExample.setjId(record.getId());
        sgExample.setcIsDelete("0");
        List<FormZjJsgcZljdzcbSg> sgList = formZjJsgcZljdzcbSgMapper.selectByEntitySelective(sgExample);
        if(sgList != null){
        	List<FormZjJsgcZljdzcbSgVo> conList = new ArrayList<FormZjJsgcZljdzcbSgVo>();
            BeanCopy.copyPropertiesForList(sgList, conList, FormZjJsgcZljdzcbSgVo.class);
            formZjJsgcZljdzcbVo.setConList(conList);
        }
        //根据  建设工程质量监督注册表 的ID  查询出 其中的监理单位组织结构表信息
        FormZjJsgcZljdzcbJl jlExample = new FormZjJsgcZljdzcbJl();
        jlExample.setjId(record.getId());
        jlExample.setsIsDelete("0");
        List<FormZjJsgcZljdzcbJl> jlList = formZjJsgcZljdzcbJlMapper.selectByEntitySelective(jlExample);
        if(jsList != null){
        	List<FormZjJsgcZljdzcbJlVo> supList = new ArrayList<FormZjJsgcZljdzcbJlVo>();
            BeanCopy.copyPropertiesForList(jlList, supList, FormZjJsgcZljdzcbJlVo.class);
            formZjJsgcZljdzcbVo.setSupList(supList);
        }
        return formZjJsgcZljdzcbVo;
	}

}
