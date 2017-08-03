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
import com.lpcode.modules.blsp.entity.FormGhJsgcSzGhys;
import com.lpcode.modules.blsp.entity.FormGhJsgcSzGhysCl;
import com.lpcode.modules.blsp.mapper.FormGhJsgcSzGhysClMapper;
import com.lpcode.modules.blsp.mapper.FormGhJsgcSzGhysMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IGhJsgcSzGhysService;
import com.lpcode.modules.service.project.dto.pinstance.FormGhJsgcSzGhysClVo;
import com.lpcode.modules.service.project.dto.pinstance.FormGhJsgcSzGhysVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 珠海市建设工程（市政类）规划验收申请表
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.guihua
 * @author maxiaowei
 * @createDate 2016年6月15日 下午2:45:31
 */
@Service
public class GhJsgcSzGhysServiceImpl implements IGhJsgcSzGhysService {

	@Autowired
	private FormGhJsgcSzGhysMapper formGhJsgcSzGhysMapper;
	@Autowired
	private FormGhJsgcSzGhysClMapper formGhJsgcSzGhysClMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_GH_JSGC_SZ_GHYS;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormGhJsgcSzGhysVo formGhJsgcSzGhysVo = (FormGhJsgcSzGhysVo) object;
		if(formGhJsgcSzGhysVo == null){
			formGhJsgcSzGhysVo = new FormGhJsgcSzGhysVo();
		}
		String url = "form/ghJsgcSzGhysForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formGhJsgcSzGhysVo.getId() == null) {
        	formGhJsgcSzGhysVo.setCreatTime(new Date());
        	formGhJsgcSzGhysVo.setCreator(UserUtils.getUser().getId());
        	formGhJsgcSzGhysVo.setUpdateTime(new Date());
        	formGhJsgcSzGhysVo.setUpdator(UserUtils.getUser().getId());
        	formGhJsgcSzGhysVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	FormGhJsgcSzGhys formGhJsgcSzGhys = new FormGhJsgcSzGhys();
        	BeanCopy.copyProperties(formGhJsgcSzGhysVo, formGhJsgcSzGhys, FormGhJsgcSzGhys.class);
        	formGhJsgcSzGhysMapper.insert(formGhJsgcSzGhys);
        	Long id = formGhJsgcSzGhys.getId();
        	if(id != null){
        		//存入对应所需材料数据
        		List<FormGhJsgcSzGhysClVo> metList = formGhJsgcSzGhysVo.getMetList();
        		if( metList != null ){
        			for (FormGhJsgcSzGhysClVo met : metList) {
        				met.setgId(id);
        				met.setmIsDelete(BaseCode.DEL_FLAG_NORMAL);
        				formGhJsgcSzGhysClMapper.insert(met);
    				}
        		}
        	}
        }else {
        	formGhJsgcSzGhysVo.setUpdateTime(new Date());
        	formGhJsgcSzGhysVo.setUpdator(UserUtils.getUser().getId());
        	FormGhJsgcSzGhys formGhJsgcSzGhys = new FormGhJsgcSzGhys();
        	BeanCopy.copyProperties(formGhJsgcSzGhysVo, formGhJsgcSzGhys, FormGhJsgcSzGhys.class);
        	formGhJsgcSzGhysMapper.updateByPrimaryKeySelective(formGhJsgcSzGhys);
        	FormGhJsgcSzGhysVo example = getFormGhJsgcSzGhysVo(formGhJsgcSzGhys.getPrjId(),formGhJsgcSzGhys.getTaskId().toString());
        	List<FormGhJsgcSzGhysClVo> metList = example.getMetList();
        	//修改所需材料信息
        	List<FormGhJsgcSzGhysClVo> clList = formGhJsgcSzGhysVo.getMetList();
        	//将未提交过来的信息删除标志位改为1（删除）
        	if(metList != null){
        		for (FormGhJsgcSzGhysClVo metVo : metList) {
        			int i=0;
					for (FormGhJsgcSzGhysClVo cl : clList) {
						if(metVo.getmId() == cl.getmId()){
							i++;
						}
					}
					if(i == 0){
						metVo.setmIsDelete("1");
						formGhJsgcSzGhysClMapper.updateByPrimaryKeySelective(metVo);
					}
				}
        	}
        	if( clList != null ){
        		for (FormGhJsgcSzGhysClVo cl : clList) {
            		if(cl.getmId() != null){
            			formGhJsgcSzGhysClMapper.updateByPrimaryKeySelective(cl);
            		}else{
            			cl.setgId(formGhJsgcSzGhys.getId());
    					cl.setmIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formGhJsgcSzGhysClMapper.insert(cl);
            		}
    			}
        	}
        }
        prjFormVO.setObject(formGhJsgcSzGhysVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo,String taskDefId) {
		String url = "modules/bizform/ghJsgcSzGhysForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormGhJsgcSzGhysVo formGhJsgcSzGhysVo = getFormGhJsgcSzGhysVo(prjInstanceVo.getId(),taskDefId);
        
        if (formGhJsgcSzGhysVo == null) {
        	formGhJsgcSzGhysVo = new FormGhJsgcSzGhysVo();
        	BeanCopy.copyProperties(prjInstanceVo,formGhJsgcSzGhysVo);
        	formGhJsgcSzGhysVo.setPrjId(prjInstanceVo.getId());
        	formGhJsgcSzGhysVo.setId(null);
        	formGhJsgcSzGhysVo.setTaskId(Long.parseLong(taskDefId));
        }
        prjFormVO.setObject(formGhJsgcSzGhysVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId,String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormGhJsgcSzGhys record = new FormGhJsgcSzGhys();
		FormGhJsgcSzGhysVo formGhJsgcSzGhysVo = new FormGhJsgcSzGhysVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formGhJsgcSzGhysMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formGhJsgcSzGhysVo);
        //根据  珠海市建设工程（市政类）规划验收申请表 的ID  查询出 其中所需材料的信息
        FormGhJsgcSzGhysClVo example = new FormGhJsgcSzGhysClVo();
        example.setgId(record.getId());
        example.setmIsDelete("0");
        List<FormGhJsgcSzGhysCl> metList = formGhJsgcSzGhysClMapper.selectByEntitySelective(example);
        if(metList != null){
        	List<FormGhJsgcSzGhysClVo> metVoList = new ArrayList<FormGhJsgcSzGhysClVo>();
            BeanCopy.copyPropertiesForList(metList, metVoList, FormGhJsgcSzGhysClVo.class);
            formGhJsgcSzGhysVo.setMetList(metVoList);
        }
        prjFormVO.setObject(formGhJsgcSzGhysVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取 珠海市建设工程（市政类）规划验收申请表  实例
	 * @param pid
	 * @return
	 */
	private FormGhJsgcSzGhysVo getFormGhJsgcSzGhysVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormGhJsgcSzGhys record = new FormGhJsgcSzGhys();
		FormGhJsgcSzGhysVo formGhJsgcSzGhysVo = new FormGhJsgcSzGhysVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formGhJsgcSzGhysMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formGhJsgcSzGhysVo);
        //根据  珠海市建设工程（市政类）规划验收申请表 的ID  查询出 其中所需材料的信息
        FormGhJsgcSzGhysClVo example = new FormGhJsgcSzGhysClVo();
        example.setgId(record.getId());
        example.setmIsDelete("0");
        List<FormGhJsgcSzGhysCl> metList = formGhJsgcSzGhysClMapper.selectByEntitySelective(example);
        if(metList != null){
        	List<FormGhJsgcSzGhysClVo> metVoList = new ArrayList<FormGhJsgcSzGhysClVo>();
            BeanCopy.copyPropertiesForList(metList, metVoList, FormGhJsgcSzGhysClVo.class);
            formGhJsgcSzGhysVo.setMetList(metVoList);
        }
        return formGhJsgcSzGhysVo;
	}

}
