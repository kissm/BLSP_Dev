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
import com.lpcode.modules.blsp.entity.FormQxSjshSqs;
import com.lpcode.modules.blsp.entity.FormQxSjshSqsDz;
import com.lpcode.modules.blsp.entity.FormQxSjshSqsDzExample;
import com.lpcode.modules.blsp.entity.FormQxSjshSqsYr;
import com.lpcode.modules.blsp.entity.FormQxSjshSqsYrExample;
import com.lpcode.modules.blsp.mapper.FormQxSjshSqsDzMapper;
import com.lpcode.modules.blsp.mapper.FormQxSjshSqsMapper;
import com.lpcode.modules.blsp.mapper.FormQxSjshSqsYrMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.form.IQxSjshSqsService;
import com.lpcode.modules.service.project.dto.pinstance.FormQxSjshSqsDzVo;
import com.lpcode.modules.service.project.dto.pinstance.FormQxSjshSqsVo;
import com.lpcode.modules.service.project.dto.pinstance.FormQxSjshSqsYrVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * 防雷装置设计审核申请书
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.form.qixiang
 * @author maxiaowei
 * @createDate 2016年6月21日 上午9:04:14
 */
@Service
public class QxSjshSqsServiceImpl implements IQxSjshSqsService {

	@Autowired
	private FormQxSjshSqsMapper formQxSjshSqsMapper;
	@Autowired
	private FormQxSjshSqsYrMapper formQxSjshSqsYrMapper;
	@Autowired
	private FormQxSjshSqsDzMapper formQxSjshSqsDzMapper;
	
	@Override
	public String getScene() {
		return FormCode.FORM_QX_SJSH_SQS;
	}

	@Override
	public PrjFormVO saveOrUpdateForm(Object object) {
		FormQxSjshSqsVo formQxSjshSqsVo = (FormQxSjshSqsVo) object;
		String url = "form/qxSjshSqsForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        if (formQxSjshSqsVo.getId() == null) {
        	formQxSjshSqsVo.setCreatTime(new Date());
        	formQxSjshSqsVo.setCreator(UserUtils.getUser().getId());
        	formQxSjshSqsVo.setUpdateTime(new Date());
        	formQxSjshSqsVo.setUpdator(UserUtils.getUser().getId());
        	formQxSjshSqsVo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
        	FormQxSjshSqs formQxSjshSqs = new FormQxSjshSqs();
        	BeanCopy.copyProperties(formQxSjshSqsVo, formQxSjshSqs, FormQxSjshSqs.class);
        	//将当前时间作为申请日期
        	formQxSjshSqs.setApplyDates(new Date());
        	formQxSjshSqsMapper.insert(formQxSjshSqs);
        	Long id = formQxSjshSqs.getId();
        	if(id != null){
        		//存入对应   易燃易爆品、化学危险品情况   数据
        		List<FormQxSjshSqsYrVo> flaList = formQxSjshSqsVo.getFlaList();
        		if( flaList != null ){
        			for (FormQxSjshSqsYrVo fla : flaList) {
        				fla.setqId(id);
        				fla.setfIsDelete(BaseCode.DEL_FLAG_NORMAL);
        				formQxSjshSqsYrMapper.insert(fla);
    				}
        		}
        		//存入对应   电子信息系统情况   数据
        		List<FormQxSjshSqsDzVo> eleList = formQxSjshSqsVo.getEleList();
        		if( eleList != null ){
        			for (FormQxSjshSqsDzVo ele : eleList) {
        				ele.setqId(id);
        				ele.seteIsDelete(BaseCode.DEL_FLAG_NORMAL);
        				formQxSjshSqsDzMapper.insert(ele);
    				}
        		}
        	}
        }else {
        	formQxSjshSqsVo.setUpdateTime(new Date());
        	formQxSjshSqsVo.setUpdator(UserUtils.getUser().getId());
        	FormQxSjshSqs formQxSjshSqs = new FormQxSjshSqs();
        	BeanCopy.copyProperties(formQxSjshSqsVo, formQxSjshSqs, FormQxSjshSqs.class);
        	formQxSjshSqsMapper.updateByPrimaryKeySelective(formQxSjshSqs);
        	FormQxSjshSqsVo qxSjshSqsVo = getFormQxSjshSqsVo(formQxSjshSqs.getPrjId(),formQxSjshSqs.getTaskId().toString());
        	List<FormQxSjshSqsYrVo> flaList = qxSjshSqsVo.getFlaList();
        	List<FormQxSjshSqsDzVo> eleList = qxSjshSqsVo.getEleList();
        	//修改  易燃易爆品、化学危险品情况    信息
        	List<FormQxSjshSqsYrVo> yrList = formQxSjshSqsVo.getFlaList();
        	//将未提交过来的   易燃易爆品、化学危险品情况   删除标志位改为1（删除）
        	if(flaList != null){
        		for (FormQxSjshSqsYrVo fla : flaList) {
        			int i=0;
					for (FormQxSjshSqsYrVo yr : yrList) {
						if(fla.getfId() == yr.getfId()){
							i++;
						}
					}
					if(i == 0){
						fla.setfIsDelete("1");
						formQxSjshSqsYrMapper.updateByPrimaryKeySelective(fla);
					}
				}
        	}
        	if( yrList != null ){
        		for (FormQxSjshSqsYrVo yr : yrList) {
            		if(yr.getfId() != null){
            			formQxSjshSqsYrMapper.updateByPrimaryKeySelective(yr);
            		}else{
            			yr.setqId(formQxSjshSqs.getId());
    					yr.setfIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formQxSjshSqsYrMapper.insert(yr);
            		}
    			}
        	}
        	//修改   电子信息系统情况   信息
        	List<FormQxSjshSqsDzVo> dzList = formQxSjshSqsVo.getEleList();
        	//将未提交过来的  电子信息系统情况   信息删除标志位改为1（删除）
        	if(eleList != null){
        		for (FormQxSjshSqsDzVo ele : eleList) {
        			int i=0;
					for (FormQxSjshSqsDzVo dz : dzList) {
						if(ele.geteId() == dz.geteId()){
							i++;
						}
					}
					if(i == 0){
						ele.seteIsDelete("1");
						formQxSjshSqsDzMapper.updateByPrimaryKeySelective(ele);
					}
				}
        	}
        	if( dzList != null ){
        		for (FormQxSjshSqsDzVo dz : dzList) {
            		if(dz.geteId() != null){
            			formQxSjshSqsDzMapper.updateByPrimaryKeySelective(dz);
            		}else{
            			dz.setqId(formQxSjshSqs.getId());
    					dz.seteIsDelete(BaseCode.DEL_FLAG_NORMAL);
    					formQxSjshSqsDzMapper.insert(dz);
            		}
    			}
        	}
        }
        prjFormVO.setObject(formQxSjshSqsVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO viewForm(PrjInstanceVo prjInstanceVo, String taskDefId) {
		String url = "modules/bizform/qxSjshSqsForm";
        PrjFormVO prjFormVO = new PrjFormVO();
        FormQxSjshSqsVo formQxSjshSqsVo = getFormQxSjshSqsVo(prjInstanceVo.getId(),taskDefId);
        if (formQxSjshSqsVo == null) {
        	formQxSjshSqsVo = new FormQxSjshSqsVo();
        	BeanCopy.copyProperties(prjInstanceVo,formQxSjshSqsVo);
            formQxSjshSqsVo.setPrjId(prjInstanceVo.getId());
            formQxSjshSqsVo.setId(null);
            formQxSjshSqsVo.setTaskId(Long.valueOf(taskDefId));
        }
        prjFormVO.setObject(formQxSjshSqsVo);
        prjFormVO.setModuleUrl(url);
        return prjFormVO;
	}

	@Override
	public PrjFormVO getByPrjcetId(Long prjectId, String taskDefId) {
		PrjFormVO prjFormVO = new PrjFormVO();
        if (prjectId == null)
            return null;
        FormQxSjshSqs record = new FormQxSjshSqs();
		FormQxSjshSqsVo formQxSjshSqsVo = new FormQxSjshSqsVo();
        record.setPrjId(prjectId);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formQxSjshSqsMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formQxSjshSqsVo);
        //根据  防雷装置设计审核申请书 的ID  查询出 其中的  易燃易爆品、化学危险品情况  信息
        FormQxSjshSqsYrExample yrExample = new FormQxSjshSqsYrExample();
        yrExample.createCriteria().andQIdEqualTo(record.getId());
        yrExample.createCriteria().andFIsDeleteEqualTo("0");
        List<FormQxSjshSqsYr> yrList = formQxSjshSqsYrMapper.selectByExample(yrExample);
        if( yrList != null ){
        	List<FormQxSjshSqsYrVo> flaList = new ArrayList<FormQxSjshSqsYrVo>();
            BeanCopy.copyPropertiesForList(yrList, flaList, FormQxSjshSqsYrVo.class);
            formQxSjshSqsVo.setFlaList(flaList);
        }
        //根据   防雷装置设计审核申请书   的ID  查询出 其中的   电子信息系统情况    信息
        FormQxSjshSqsDzExample dzExample = new FormQxSjshSqsDzExample();
        dzExample.createCriteria().andQIdEqualTo(record.getId());
        dzExample.createCriteria().andEIsDeleteEqualTo("0");
        List<FormQxSjshSqsDz> dzList = formQxSjshSqsDzMapper.selectByExample(dzExample);
        if( dzList != null ){
        	List<FormQxSjshSqsDzVo> eleList = new ArrayList<FormQxSjshSqsDzVo>();
            BeanCopy.copyPropertiesForList(dzList, eleList, FormQxSjshSqsDzVo.class);
            formQxSjshSqsVo.setEleList(eleList);
        }
        prjFormVO.setObject(formQxSjshSqsVo);
        return prjFormVO;
	}
	
	/**
	 * 根据项目ID获取 防雷装置设计审核申请书 实例
	 * @param pid
	 * @return
	 */
	private FormQxSjshSqsVo getFormQxSjshSqsVo(Long pid,String taskDefId){
		if (pid == null)
            return null;
		FormQxSjshSqs record = new FormQxSjshSqs();
		FormQxSjshSqsVo formQxSjshSqsVo = new FormQxSjshSqsVo();
        record.setPrjId(pid);
        record.setIsDelete("0");
        record.setTaskId(Long.valueOf(taskDefId));
        record = formQxSjshSqsMapper.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, formQxSjshSqsVo);
        //根据  防雷装置设计审核申请书 的ID  查询出 其中的  易燃易爆品、化学危险品情况  信息
        FormQxSjshSqsYr yr = new FormQxSjshSqsYr();
        yr.setqId(record.getId());
        yr.setfIsDelete("0");
        List<FormQxSjshSqsYr> yrList = formQxSjshSqsYrMapper.selectByEntitySelective(yr);
        if( yrList != null ){
        	List<FormQxSjshSqsYrVo> flaList = new ArrayList<FormQxSjshSqsYrVo>();
            BeanCopy.copyPropertiesForList(yrList, flaList, FormQxSjshSqsYrVo.class);
            formQxSjshSqsVo.setFlaList(flaList);
        }
        //根据   防雷装置设计审核申请书   的ID  查询出 其中的   电子信息系统情况    信息
        FormQxSjshSqsDz dz = new FormQxSjshSqsDz();
        dz.setqId(record.getId());
        dz.seteIsDelete("0");
        List<FormQxSjshSqsDz> dzList = formQxSjshSqsDzMapper.selectByEntitySelective(dz);
        if( dzList != null ){
        	List<FormQxSjshSqsDzVo> eleList = new ArrayList<FormQxSjshSqsDzVo>();
            BeanCopy.copyPropertiesForList(dzList, eleList, FormQxSjshSqsDzVo.class);
            formQxSjshSqsVo.setEleList(eleList);
        }
        return formQxSjshSqsVo;
	}

}
