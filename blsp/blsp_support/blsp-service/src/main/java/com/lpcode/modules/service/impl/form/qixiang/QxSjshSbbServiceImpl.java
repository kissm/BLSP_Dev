package com.lpcode.modules.service.impl.form.qixiang;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.FormCode;
import com.lpcode.modules.blsp.entity.FormQxSjshSbb;
import com.lpcode.modules.blsp.entity.FormQxSjshSbbJz;
import com.lpcode.modules.blsp.entity.FormQxSjshSbbJzExample;
import com.lpcode.modules.blsp.mapper.FormQxSjshSbbJzMapper;
import com.lpcode.modules.blsp.mapper.FormQxSjshSbbMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IQxSjshSbbService;
import com.lpcode.modules.service.project.dto.pinstance.FormQxSjshSbbJzVo;
import com.lpcode.modules.service.project.dto.pinstance.FormQxSjshSbbVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 防雷装置设计审核申报表
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.qixiang
 * @author maxiaowei
 * @createDate 2016年6月21日 下午2:06:49
 */
@Service
public class QxSjshSbbServiceImpl implements IQxSjshSbbService {
	
	@Autowired
	private FormQxSjshSbbMapper formQxSjshSbbMapper;
	@Autowired
	private FormQxSjshSbbJzMapper formQxSjshSbbJzMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_QX_SJSH_SBB;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormQxSjshSbbVo formQxSjshSbbVo = (FormQxSjshSbbVo) object;
		String url = "form/qxSjshSbbForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formQxSjshSbbVo.getId() == null) {
        	formQxSjshSbbVo.setCreatTime(new Date());
        	formQxSjshSbbVo.setCreator(UserUtils.getUser().getId());
        	formQxSjshSbbVo.setUpdateTime(new Date());
        	formQxSjshSbbVo.setUpdator(UserUtils.getUser().getId());
        	formQxSjshSbbVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	FormQxSjshSbb formQxSjshSbb = new FormQxSjshSbb();
        	BeanCopy.copyProperties(formQxSjshSbbVo, formQxSjshSbb, FormQxSjshSbb.class);
        	formQxSjshSbbMapper.insert(formQxSjshSbb);
        	Long id = formQxSjshSbb.getId();
        	if(id != null){
        		//存入对应   建筑物信息补充说明   数据
        		List<FormQxSjshSbbJzVo> buiList = formQxSjshSbbVo.getBuiList();
        		if( buiList != null ){
        			for (FormQxSjshSbbJzVo bui : buiList) {
        				bui.setqId(id);
        				bui.setbIsDelete(BaseCode.DEL_FLAG_NORMAL);
        				formQxSjshSbbJzMapper.insert(bui);
    				}
        		}
        	}
        }else {
        	formQxSjshSbbVo.setUpdateTime(new Date());
        	formQxSjshSbbVo.setUpdator(UserUtils.getUser().getId());
        	FormQxSjshSbb formQxSjshSbb = new FormQxSjshSbb();
        	BeanCopy.copyProperties(formQxSjshSbbVo, formQxSjshSbb, FormQxSjshSbb.class);
        	formQxSjshSbbMapper.updateByPrimaryKeySelective(formQxSjshSbb);
        	FormQxSjshSbbVo qxSjshSbbVo = getFormQxSjshSbbVo(formQxSjshSbb.getPrjId(),formQxSjshSbb.getTaskId().toString());
        	List<FormQxSjshSbbJzVo> buiList = qxSjshSbbVo.getBuiList();
        	//修改  建筑物信息补充说明    信息
        	List<FormQxSjshSbbJzVo> jzList = formQxSjshSbbVo.getBuiList();
        	//将未提交过来的   建筑物信息补充说明   删除标志位改为1（删除）
        	if(buiList != null){
        		for (FormQxSjshSbbJzVo bui : buiList) {
        			int i=0;
					for (FormQxSjshSbbJzVo jz : jzList) {
						if(bui.getbId() == jz.getbId()){
							i++;
						}
					}
					if(i == 0){
						bui.setbIsDelete("1");
						formQxSjshSbbJzMapper.updateByPrimaryKeySelective(bui);
					}
				}
        	}
        	if( jzList != null ){
        		for (FormQxSjshSbbJzVo jz : jzList) {
            		if(jz.getbId() != null){
            			formQxSjshSbbJzMapper.updateByPrimaryKeySelective(jz);
            		}else{
            			jz.setqId(formQxSjshSbb.getId());
    					jz.setbIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formQxSjshSbbJzMapper.insert(jz);
            		}
    			}
        	}
        }
        prjFormVO.setObject(formQxSjshSbbVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo, String taskDefId) {
		String url = "modules/bizform/qxSjshSbbForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormQxSjshSbbVo formQxSjshSbbVo = getFormQxSjshSbbVo(prjInstanceVo.getId(),taskDefId);
        if (formQxSjshSbbVo == null) {
        	formQxSjshSbbVo = new FormQxSjshSbbVo();
        	BeanCopy.copyProperties(prjInstanceVo,formQxSjshSbbVo);
            formQxSjshSbbVo.setPrjId(prjInstanceVo.getId());
            formQxSjshSbbVo.setId(null);
            formQxSjshSbbVo.setTaskId(Long.valueOf(taskDefId));
        }
        prjFormVO.setObject(formQxSjshSbbVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId, String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormQxSjshSbb record = new FormQxSjshSbb();
		FormQxSjshSbbVo formQxSjshSbbVo = new FormQxSjshSbbVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formQxSjshSbbMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formQxSjshSbbVo);
        //根据  防雷装置设计审核申报表  的ID  查询出 其中的  建筑物信息补充说明  信息
        FormQxSjshSbbJzExample jzExample = new FormQxSjshSbbJzExample();
        jzExample.createCriteria().andQIdEqualTo(record.getId());
        jzExample.createCriteria().andBIsDeleteEqualTo("0");
        List<FormQxSjshSbbJz> jzList = formQxSjshSbbJzMapper.selectByExample(jzExample);
        if( jzList != null ){
        	List<FormQxSjshSbbJzVo> buiList = new ArrayList<FormQxSjshSbbJzVo>();
            BeanCopy.copyPropertiesForList(jzList, buiList, FormQxSjshSbbJzVo.class);
            formQxSjshSbbVo.setBuiList(buiList);
        }
        prjFormVO.setObject(formQxSjshSbbVo);
        return prjFormVO;
	}

	/**
	 * 根据项目ID获取  防雷装置设计审核申报表   实例
	 * @param pid
	 * @return
	 */
	private FormQxSjshSbbVo getFormQxSjshSbbVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormQxSjshSbb record = new FormQxSjshSbb();
		FormQxSjshSbbVo formQxSjshSbbVo = new FormQxSjshSbbVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formQxSjshSbbMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formQxSjshSbbVo);
        //根据  防雷装置设计审核申报表  的ID  查询出 其中的  建筑物信息补充说明  信息
        FormQxSjshSbbJz jz = new FormQxSjshSbbJz();
        jz.setqId(record.getId());
        jz.setbIsDelete("0");
        List<FormQxSjshSbbJz> jzList = formQxSjshSbbJzMapper.selectByEntitySelective(jz);
        if( jzList != null ){
        	List<FormQxSjshSbbJzVo> buiList = new ArrayList<FormQxSjshSbbJzVo>();
            BeanCopy.copyPropertiesForList(jzList, buiList, FormQxSjshSbbJzVo.class);
            formQxSjshSbbVo.setBuiList(buiList);
        }
        return formQxSjshSbbVo;
	}
	
}
