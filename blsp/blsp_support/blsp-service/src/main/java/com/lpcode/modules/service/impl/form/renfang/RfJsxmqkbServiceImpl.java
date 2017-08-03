package com.lpcode.modules.service.impl.form.renfang;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormRfJsxmqkb;
import com.lpcode.modules.blsp.entity.FormRfJsxmqkbQk;
import com.lpcode.modules.blsp.mapper.FormRfJsxmqkbMapper;
import com.lpcode.modules.blsp.mapper.FormRfJsxmqkbQkMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IRfJsxmqkbService;
import com.lpcode.modules.service.project.dto.pinstance.FormRfJsxmqkbQkVo;
import com.lpcode.modules.service.project.dto.pinstance.FormRfJsxmqkbVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 人防建设项目情况表
 * @author ZERO
 * @updateAutor maxiaowei
 */
@Service
public class RfJsxmqkbServiceImpl implements IRfJsxmqkbService{
	
	@Autowired
    private FormRfJsxmqkbMapper formRfJsxmqkbMapper;
	@Autowired
	private FormRfJsxmqkbQkMapper formRfJsxmqkbQkMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_RF_JSXMQKB;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormRfJsxmqkbVo formRfJsxmqkbVo = (FormRfJsxmqkbVo) object;
		if(formRfJsxmqkbVo == null){
			formRfJsxmqkbVo = new FormRfJsxmqkbVo();
		}
		String url = "form/rfJsxmqkbForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formRfJsxmqkbVo.getId() == null) {
        	formRfJsxmqkbVo.setCreatTime(new Date());
        	formRfJsxmqkbVo.setCreator(UserUtils.getUser().getId());
        	formRfJsxmqkbVo.setUpdateTime(new Date());
        	formRfJsxmqkbVo.setUpdator(UserUtils.getUser().getId());
        	formRfJsxmqkbVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	FormRfJsxmqkb formRfJsxmqkb = new FormRfJsxmqkb();
        	BeanCopy.copyProperties(formRfJsxmqkbVo, formRfJsxmqkb, FormRfJsxmqkb.class);
        	formRfJsxmqkbMapper.insert(formRfJsxmqkb);
        	Long id = formRfJsxmqkb.getId();
        	if(id != null){
        		//存入对应详情数据
        		List<FormRfJsxmqkbQkVo> conList = formRfJsxmqkbVo.getConList();
        		if( conList != null ){
        			for (FormRfJsxmqkbQkVo con : conList) {
        				con.setrId(id);
        				con.setcIsDelete(BaseCode.DEL_FLAG_NORMAL);
        				formRfJsxmqkbQkMapper.insert(con);
    				}
        		}
        	}
        }else {
        	formRfJsxmqkbVo.setUpdateTime(new Date());
        	formRfJsxmqkbVo.setUpdator(UserUtils.getUser().getId());
        	FormRfJsxmqkb formRfJsxmqkb = new FormRfJsxmqkb();
        	BeanCopy.copyProperties(formRfJsxmqkbVo, formRfJsxmqkb, FormRfJsxmqkb.class);
        	formRfJsxmqkbMapper.updateByPrimaryKeySelective(formRfJsxmqkb);
        	FormRfJsxmqkbVo example = getFormRfJsxmqkbVo(formRfJsxmqkb.getPrjId(),formRfJsxmqkb.getTaskId().toString());
        	List<FormRfJsxmqkbQkVo> conList = example.getConList();
        	//修改详情信息
        	List<FormRfJsxmqkbQkVo> qkList = formRfJsxmqkbVo.getConList();
        	//将未提交过来的详情信息删除标志位改为1（删除）
        	if(conList != null){
        		for (FormRfJsxmqkbQkVo con : conList) {
        			int i=0;
					for (FormRfJsxmqkbQkVo qk : qkList) {
						if(con.getcId() == qk.getcId()){
							i++;
						}
					}
					if(i == 0){
						con.setcIsDelete("1");
						formRfJsxmqkbQkMapper.updateByPrimaryKeySelective(con);
					}
				}
        	}
        	if( qkList != null ){
        		for (FormRfJsxmqkbQkVo qk : qkList) {
            		if(qk.getcId() != null){
            			formRfJsxmqkbQkMapper.updateByPrimaryKeySelective(qk);
            		}else{
            			qk.setrId(formRfJsxmqkb.getId());
            			qk.setcIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formRfJsxmqkbQkMapper.insert(qk);
            		}
    			}
        	}
        }
        prjFormVO.setObject(formRfJsxmqkbVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo,String taskDefId) {
		String url = "modules/bizform/rfJsxmqkbForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormRfJsxmqkbVo formRfJsxmqkbVo = getFormRfJsxmqkbVo(prjInstanceVo.getId(),taskDefId);
        
        if (formRfJsxmqkbVo == null) {
        	formRfJsxmqkbVo = new FormRfJsxmqkbVo();
        	BeanCopy.copyProperties(prjInstanceVo,formRfJsxmqkbVo);
        	formRfJsxmqkbVo.setPrjId(prjInstanceVo.getId());
        	formRfJsxmqkbVo.setId(null);
        	formRfJsxmqkbVo.setTaskId(Long.parseLong(taskDefId));
        }
        prjFormVO.setObject(formRfJsxmqkbVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId,String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormRfJsxmqkb record = new FormRfJsxmqkb();
		FormRfJsxmqkbVo formRfJsxmqkbVo = new FormRfJsxmqkbVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formRfJsxmqkbMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formRfJsxmqkbVo);
        //根据  建设项目情况表  的ID  查询出 其中的详情信息
        FormRfJsxmqkbQkVo example = new FormRfJsxmqkbQkVo();
        example.setrId(record.getId());
        example.setcIsDelete("0");
        List<FormRfJsxmqkbQk> conList = formRfJsxmqkbQkMapper.selectByEntitySelective(example);
        if(conList != null){
        	List<FormRfJsxmqkbQkVo> conVoList = new ArrayList<FormRfJsxmqkbQkVo>();
            BeanCopy.copyPropertiesForList(conList, conVoList, FormRfJsxmqkbQkVo.class);
            formRfJsxmqkbVo.setConList(conVoList);
        }
        prjFormVO.setObject(formRfJsxmqkbVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取 建设项目情况表   实例
	 * @param pid
	 * @return
	 */
	private FormRfJsxmqkbVo getFormRfJsxmqkbVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormRfJsxmqkb record = new FormRfJsxmqkb();
		FormRfJsxmqkbVo formRfJsxmqkbVo = new FormRfJsxmqkbVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.parseLong(taskDefId));
        record = formRfJsxmqkbMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formRfJsxmqkbVo);
        //根据  建设项目情况表  的ID  查询出 其中的详情信息
        FormRfJsxmqkbQkVo example = new FormRfJsxmqkbQkVo();
        example.setrId(record.getId());
        example.setcIsDelete("0");
        List<FormRfJsxmqkbQk> conList = formRfJsxmqkbQkMapper.selectByEntitySelective(example);
        if(conList != null){
        	List<FormRfJsxmqkbQkVo> conVoList = new ArrayList<FormRfJsxmqkbQkVo>();
            BeanCopy.copyPropertiesForList(conList, conVoList, FormRfJsxmqkbQkVo.class);
            formRfJsxmqkbVo.setConList(conVoList);
        }
        return formRfJsxmqkbVo;
	}

}
