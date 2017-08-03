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
import com.lpcode.modules.blsp.entity.FormGhJsgcSzGhxk;
import com.lpcode.modules.blsp.entity.FormGhJsgcSzGhxkGx;
import com.lpcode.modules.blsp.mapper.FormGhJsgcSzGhxkGxMapper;
import com.lpcode.modules.blsp.mapper.FormGhJsgcSzGhxkMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IGhJsgcSzGhxkService;
import com.lpcode.modules.service.project.dto.pinstance.FormGhJsgcSzGhxkGxVo;
import com.lpcode.modules.service.project.dto.pinstance.FormGhJsgcSzGhxkVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 珠海市建设工程（市政类）规划许可申请表
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.guihua
 * @author maxiaowei
 * @createDate 2016年6月7日 下午3:39:32
 */
@Service
public class GhJsgcSzGhxkServiceImpl implements IGhJsgcSzGhxkService {

	@Autowired
	private FormGhJsgcSzGhxkMapper formGhJsgcSzGhxkMapper;
	@Autowired
	private FormGhJsgcSzGhxkGxMapper formGhJsgcSzGhxkGxMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_GH_JSGC_SZ_GHXK;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormGhJsgcSzGhxkVo formGhJsgcSzGhxkVo = (FormGhJsgcSzGhxkVo) object;
		if(formGhJsgcSzGhxkVo == null){
			formGhJsgcSzGhxkVo = new FormGhJsgcSzGhxkVo();
		}
		String url = "form/ghJsgcSzGhxkForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formGhJsgcSzGhxkVo.getId() == null) {
        	formGhJsgcSzGhxkVo.setCreatTime(new Date());
        	formGhJsgcSzGhxkVo.setCreator(UserUtils.getUser().getId());
        	formGhJsgcSzGhxkVo.setUpdateTime(new Date());
        	formGhJsgcSzGhxkVo.setUpdator(UserUtils.getUser().getId());
        	formGhJsgcSzGhxkVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	FormGhJsgcSzGhxk formGhJsgcSzGhxk = new FormGhJsgcSzGhxk();
        	BeanCopy.copyProperties(formGhJsgcSzGhxkVo, formGhJsgcSzGhxk, FormGhJsgcSzGhxk.class);
        	formGhJsgcSzGhxkMapper.insert(formGhJsgcSzGhxk);
        	Long id = formGhJsgcSzGhxk.getId();
        	if(id != null){
        		//存入对应管线工程数据
        		List<FormGhJsgcSzGhxkGxVo> pipList = formGhJsgcSzGhxkVo.getPipList();
        		if( pipList != null ){
        			for (FormGhJsgcSzGhxkGxVo pip : pipList) {
        				pip.setgId(id);
        				pip.setpIsDelete(BaseCode.DEL_FLAG_NORMAL);
        				formGhJsgcSzGhxkGxMapper.insert(pip);
    				}
        		}
        	}
        }else {
        	formGhJsgcSzGhxkVo.setUpdateTime(new Date());
        	formGhJsgcSzGhxkVo.setUpdator(UserUtils.getUser().getId());
        	FormGhJsgcSzGhxk formGhJsgcSzGhxk = new FormGhJsgcSzGhxk();
        	BeanCopy.copyProperties(formGhJsgcSzGhxkVo, formGhJsgcSzGhxk, FormGhJsgcSzGhxk.class);
        	formGhJsgcSzGhxkMapper.updateByPrimaryKeySelective(formGhJsgcSzGhxk);
        	FormGhJsgcSzGhxkVo example = getFormGhJsgcSzGhxkVo(formGhJsgcSzGhxk.getPrjId(),formGhJsgcSzGhxk.getTaskId().toString());
        	List<FormGhJsgcSzGhxkGxVo> pipList = example.getPipList();
        	//修改管线工程信息
        	List<FormGhJsgcSzGhxkGxVo> gxList = formGhJsgcSzGhxkVo.getPipList();
        	//将未提交过来的单位信息删除标志位改为1（删除）
        	if(pipList != null){
        		for (FormGhJsgcSzGhxkGxVo pipVo : pipList) {
        			int i=0;
					for (FormGhJsgcSzGhxkGxVo gx : gxList) {
						if(pipVo.getpId() == gx.getpId()){
							i++;
						}
					}
					if(i == 0){
						pipVo.setpIsDelete("1");
						formGhJsgcSzGhxkGxMapper.updateByPrimaryKeySelective(pipVo);
					}
				}
        	}
        	if( gxList != null ){
        		for (FormGhJsgcSzGhxkGxVo gx : gxList) {
            		if(gx.getpId() != null){
            			formGhJsgcSzGhxkGxMapper.updateByPrimaryKeySelective(gx);
            		}else{
            			gx.setgId(formGhJsgcSzGhxk.getId());
    					gx.setpIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formGhJsgcSzGhxkGxMapper.insert(gx);
            		}
    			}
        	}
        }
        prjFormVO.setObject(formGhJsgcSzGhxkVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo,String taskDefId) {
		String url = "modules/bizform/ghJsgcSzGhxkForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormGhJsgcSzGhxkVo formGhJsgcSzGhxkVo = getFormGhJsgcSzGhxkVo(prjInstanceVo.getId(),taskDefId);
        
        if (formGhJsgcSzGhxkVo == null) {
        	formGhJsgcSzGhxkVo = new FormGhJsgcSzGhxkVo();
        	BeanCopy.copyProperties(prjInstanceVo,formGhJsgcSzGhxkVo);
        	formGhJsgcSzGhxkVo.setPrjId(prjInstanceVo.getId());
        	formGhJsgcSzGhxkVo.setId(null);
        	formGhJsgcSzGhxkVo.setTaskId(Long.parseLong(taskDefId));
        }
        prjFormVO.setObject(formGhJsgcSzGhxkVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId,String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormGhJsgcSzGhxk record = new FormGhJsgcSzGhxk();
		FormGhJsgcSzGhxkVo formGhJsgcSzGhxkVo = new FormGhJsgcSzGhxkVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formGhJsgcSzGhxkMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formGhJsgcSzGhxkVo);
        //根据  珠海市建设工程（市政类）规划许可申请表 的ID  查询出 其中管线的信息
        FormGhJsgcSzGhxkGxVo example = new FormGhJsgcSzGhxkGxVo();
        example.setgId(record.getId());
        example.setpIsDelete("0");
        List<FormGhJsgcSzGhxkGx> pipList = formGhJsgcSzGhxkGxMapper.selectByEntitySelective(example);
        if(pipList != null){
        	List<FormGhJsgcSzGhxkGxVo> pipVoList = new ArrayList<FormGhJsgcSzGhxkGxVo>();
            BeanCopy.copyPropertiesForList(pipList, pipVoList, FormGhJsgcSzGhxkGxVo.class);
            formGhJsgcSzGhxkVo.setPipList(pipVoList);
        }
        prjFormVO.setObject(formGhJsgcSzGhxkVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取 珠海市建设工程（市政类）规划许可申请表  实例
	 * @param pid
	 * @return
	 */
	private FormGhJsgcSzGhxkVo getFormGhJsgcSzGhxkVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormGhJsgcSzGhxk record = new FormGhJsgcSzGhxk();
		FormGhJsgcSzGhxkVo formGhJsgcSzGhxkVo = new FormGhJsgcSzGhxkVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formGhJsgcSzGhxkMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formGhJsgcSzGhxkVo);
        //根据  珠海市建设工程（市政类）规划许可申请表 的ID  查询出 其中管线的信息
        FormGhJsgcSzGhxkGxVo example = new FormGhJsgcSzGhxkGxVo();
        example.setgId(record.getId());
        example.setpIsDelete("0");
        List<FormGhJsgcSzGhxkGx> pipList = formGhJsgcSzGhxkGxMapper.selectByEntitySelective(example);
        if(pipList != null){
        	List<FormGhJsgcSzGhxkGxVo> pipVoList = new ArrayList<FormGhJsgcSzGhxkGxVo>();
            BeanCopy.copyPropertiesForList(pipList, pipVoList, FormGhJsgcSzGhxkGxVo.class);
            formGhJsgcSzGhxkVo.setPipList(pipVoList);
        }
        return formGhJsgcSzGhxkVo;
	}

}
