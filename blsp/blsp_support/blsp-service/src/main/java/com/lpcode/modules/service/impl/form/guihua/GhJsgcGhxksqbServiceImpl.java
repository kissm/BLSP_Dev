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
import com.lpcode.modules.blsp.entity.FormGhJsgcGhxksqb;
import com.lpcode.modules.blsp.entity.FormGhJsgcGhxksqbSj;
import com.lpcode.modules.blsp.mapper.FormGhJsgcGhxksqbMapper;
import com.lpcode.modules.blsp.mapper.FormGhJsgcGhxksqbSjMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IGhJsgcGhxksqbService;
import com.lpcode.modules.service.project.dto.pinstance.FormGhJsgcGhxksqbSjVo;
import com.lpcode.modules.service.project.dto.pinstance.FormGhJsgcGhxksqbVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 珠海市建设工程（建筑类）规划许可申请表
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.guihua
 * @author maxiaowei
 * @createDate 2016年6月3日 下午5:56:48
 */
@Service
public class GhJsgcGhxksqbServiceImpl implements IGhJsgcGhxksqbService {

	@Autowired
	private FormGhJsgcGhxksqbMapper formGhJsgcGhxksqbMapper;
	@Autowired
	private FormGhJsgcGhxksqbSjMapper formGhJsgcGhxksqbSjMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_GH_JSGC_GHXKSQB;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormGhJsgcGhxksqbVo formGhJsgcGhxksqbVo = (FormGhJsgcGhxksqbVo) object;
		if(formGhJsgcGhxksqbVo == null){
			formGhJsgcGhxksqbVo = new FormGhJsgcGhxksqbVo();
		}
		String url = "form/ghJsgcGhxksqbForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formGhJsgcGhxksqbVo.getId() == null) {
        	formGhJsgcGhxksqbVo.setCreatTime(new Date());
        	formGhJsgcGhxksqbVo.setCreator(UserUtils.getUser().getId());
        	formGhJsgcGhxksqbVo.setUpdateTime(new Date());
        	formGhJsgcGhxksqbVo.setUpdator(UserUtils.getUser().getId());
        	formGhJsgcGhxksqbVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	FormGhJsgcGhxksqb formGhJsgcGhxksqb = new FormGhJsgcGhxksqb();
        	BeanCopy.copyProperties(formGhJsgcGhxksqbVo, formGhJsgcGhxksqb, FormGhJsgcGhxksqb.class);
        	formGhJsgcGhxksqbMapper.insert(formGhJsgcGhxksqb);
        	Long id = formGhJsgcGhxksqb.getId();
        	if(id != null){
        		//存入对应单位数据
        		List<FormGhJsgcGhxksqbSjVo> desList = formGhJsgcGhxksqbVo.getDesList();
        		if( desList != null ){
        			for (FormGhJsgcGhxksqbSjVo des : desList) {
        				des.setgId(id);
        				des.setIsDDelete(BaseCode.DEL_FLAG_NORMAL);
        				formGhJsgcGhxksqbSjMapper.insert(des);
    				}
        		}
        	}
        }else {
        	formGhJsgcGhxksqbVo.setUpdateTime(new Date());
        	formGhJsgcGhxksqbVo.setUpdator(UserUtils.getUser().getId());
        	FormGhJsgcGhxksqb formGhJsgcGhxksqb = new FormGhJsgcGhxksqb();
        	BeanCopy.copyProperties(formGhJsgcGhxksqbVo, formGhJsgcGhxksqb, FormGhJsgcGhxksqb.class);
        	formGhJsgcGhxksqbMapper.updateByPrimaryKeySelective(formGhJsgcGhxksqb);
        	FormGhJsgcGhxksqbVo example = getFormGhJsgcGhxksqbVo(formGhJsgcGhxksqb.getPrjId(),formGhJsgcGhxksqb.getTaskId().toString());
        	List<FormGhJsgcGhxksqbSjVo> desList = example.getDesList();
        	//修改单位信息
        	List<FormGhJsgcGhxksqbSjVo> sjList = formGhJsgcGhxksqbVo.getDesList();
        	//将未提交过来的单位信息删除标志位改为1（删除）
        	if(desList != null){
        		for (FormGhJsgcGhxksqbSjVo desVo : desList) {
        			int i=0;
					for (FormGhJsgcGhxksqbSjVo sj : sjList) {
						if(desVo.getdId() == sj.getdId()){
							i++;
						}
					}
					if(i == 0){
						desVo.setIsDDelete("1");
						formGhJsgcGhxksqbSjMapper.updateByPrimaryKeySelective(desVo);
					}
				}
        	}
        	if( sjList != null ){
        		for (FormGhJsgcGhxksqbSjVo sj : sjList) {
            		if(sj.getdId() != null){
            			formGhJsgcGhxksqbSjMapper.updateByPrimaryKeySelective(sj);
            		}else{
            			sj.setgId(formGhJsgcGhxksqb.getId());
    					sj.setIsDDelete(BaseCode.DEL_FLAG_NORMAL);
    					formGhJsgcGhxksqbSjMapper.insert(sj);
            		}
    			}
        	}
        }
        prjFormVO.setObject(formGhJsgcGhxksqbVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo,String taskDefId) {
		String url = "modules/bizform/ghJsgcGhxksqbForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormGhJsgcGhxksqbVo formGhJsgcGhxksqbVo = getFormGhJsgcGhxksqbVo(prjInstanceVo.getId(),taskDefId);
        
        if (formGhJsgcGhxksqbVo == null) {
        	formGhJsgcGhxksqbVo = new FormGhJsgcGhxksqbVo();
        	BeanCopy.copyProperties(prjInstanceVo,formGhJsgcGhxksqbVo);
            formGhJsgcGhxksqbVo.setPrjId(prjInstanceVo.getId());
            formGhJsgcGhxksqbVo.setId(null);
            formGhJsgcGhxksqbVo.setTaskId(Long.parseLong(taskDefId));
        }
        prjFormVO.setObject(formGhJsgcGhxksqbVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId,String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormGhJsgcGhxksqb record = new FormGhJsgcGhxksqb();
		FormGhJsgcGhxksqbVo formGhJsgcGhxksqbVo = new FormGhJsgcGhxksqbVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formGhJsgcGhxksqbMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formGhJsgcGhxksqbVo);
        //根据  珠海市建设工程（建筑类）规划许可申请表 的ID  查询出 其中的单位信息
        FormGhJsgcGhxksqbSjVo example = new FormGhJsgcGhxksqbSjVo();
        example.setgId(record.getId());
        example.setIsDDelete("0");
        List<FormGhJsgcGhxksqbSj> desList = formGhJsgcGhxksqbSjMapper.selectByEntitySelective(example);
        if(desList != null){
        	List<FormGhJsgcGhxksqbSjVo> desVoList = new ArrayList<FormGhJsgcGhxksqbSjVo>();
            BeanCopy.copyPropertiesForList(desList, desVoList, FormGhJsgcGhxksqbSjVo.class);
            formGhJsgcGhxksqbVo.setDesList(desVoList);
        }
        prjFormVO.setObject(formGhJsgcGhxksqbVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取 珠海市建设工程（建筑类）规划许可申请表 实例
	 * @param pid
	 * @return
	 */
	private FormGhJsgcGhxksqbVo getFormGhJsgcGhxksqbVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormGhJsgcGhxksqb record = new FormGhJsgcGhxksqb();
		FormGhJsgcGhxksqbVo formGhJsgcGhxksqbVo = new FormGhJsgcGhxksqbVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formGhJsgcGhxksqbMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formGhJsgcGhxksqbVo);
        //根据  珠海市建设工程（建筑类）规划许可申请表 的ID  查询出 其中的单位信息
        FormGhJsgcGhxksqbSjVo example = new FormGhJsgcGhxksqbSjVo();
        example.setgId(record.getId());
        example.setIsDDelete("0");
        List<FormGhJsgcGhxksqbSj> desList = formGhJsgcGhxksqbSjMapper.selectByEntitySelective(example);
        if(desList != null){
        	List<FormGhJsgcGhxksqbSjVo> desVoList = new ArrayList<FormGhJsgcGhxksqbSjVo>();
            BeanCopy.copyPropertiesForList(desList, desVoList, FormGhJsgcGhxksqbSjVo.class);
            formGhJsgcGhxksqbVo.setDesList(desVoList);
        }
        return formGhJsgcGhxksqbVo;
	}

}
