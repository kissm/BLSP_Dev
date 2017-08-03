/**
 *
 */
package com.lpcode.modules.service.impl.project;

import com.alibaba.fastjson.JSON;
import com.framework.core.base.page.Page;
import com.framework.core.constants.BaseCode;
import com.framework.core.result.*;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.dao.UserDao;
import com.framework.osp.modules.sys.entity.Office;
import com.framework.osp.modules.sys.entity.User;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.DicConstants;
import com.lpcode.modules.blsp.entity.*;
import com.lpcode.modules.blsp.mapper.*;
import com.lpcode.modules.blsp.vo.*;
import com.lpcode.modules.dto.project.change.PrjMaterialVO;
import com.lpcode.modules.service.dimension.holiday.DimHolidayService;
import com.lpcode.modules.service.impl.project.util.FormUtils;
import com.lpcode.modules.service.message.IMessageSMS;
import com.lpcode.modules.service.project.constant.TaskConstants;
import com.lpcode.modules.service.project.dto.*;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.dto.pinstance.StageTaskProcessVO;
import com.lpcode.modules.service.project.dto.pinstance.TaskProcessVO;
import com.lpcode.modules.service.project.inf.PrjTaskPauseDetailService;
import com.lpcode.modules.service.project.inf.PrjTaskService;
import com.lpcode.modules.service.project.inf.PrjTaskTransferDetailService;
import com.lpcode.modules.service.report.ReportPushService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 项目事项表ServiceImpl
 *
 * @author wangyazhou
 *
 */
@Service
@Transactional(readOnly = true)
public class PrjTaskServiceImpl implements PrjTaskService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private PrjTaskMapper prjTaskMapper;
	@Autowired
	private PrjInstanceMapper prjInstanceMapper;
	@Autowired
	private PrjTaskAdapterMapper prjTaskAdapterMapper;
	@Autowired
	private PrjTaskPauseDetailMapper prjTaskPauseDetailMapper;
	@Autowired
	private PrjTaskPauseDetailService prjTaskPauseDetailService;
	@Autowired
	private PrjTaskTransferDetailService prjTaskTransferDetailService;
	@Autowired
	private PrjTaskTransferDetailMapper prjTaskTransferDetailMapper;
	@Autowired
	private PrjTaskDefineMapper prjTaskDefineMapper;
	@Autowired
	private PrjStageMapper prjStageMapper;
	@Autowired
	private PrjStageDefineMapper prjStageDefineMapper;
	@Autowired
	private PrjTaskMaterialDefMapper prjTaskMaterialDefMapper;
	@Autowired
	private PrjStageMaterialMapper prjStageMaterialMapper;
	@Autowired
	private DimMaterialMapper dimMaterialMapper;
	@Autowired
	private DimHolidayService dimHolidayService;
	@Autowired
	private IMessageSMS iMessageSMS;
	@Autowired
	private PrjTaskDependencyMapper prjTaskDependencyMapper;
	@Autowired
	private PrjTaskRejectMaterialMapper prjTaskRejectMaterialMapper;
	@Autowired
	private PrjFetchViewMapper prjFetchViewMapper;
	@Autowired
	private PrjBusinessAcceptMapper prjBusinessAcceptMapper;
	@Autowired
	private ReportPushService reportPushService;
	@Autowired
	private ServiceSbQueueMapper serviceSbQueueMapper;
	protected Logger logger = LoggerFactory.getLogger(PrjTaskServiceImpl.class);
	/**
	 * 我的代办列表查询
	 *
	 *  prjCode
	 *            项目编号
	 *  prjName
	 *            项目名称
	 *  taskName
	 *            事项名称
	 *  taskStatus
	 *            事项状态
	 *  beginCreateDate
	 *            起始日期
	 *  endCreateDate
	 *            截止日期
	 * @return
	 */
	@Override
	public PageResult<PrjTaskTodoListDTO> findPage(RequestDTOPage<PrjTaskTodoListDTO> para) {
		PageResult<PrjTaskTodoListDTO> pageResult = new PageResult<>();
		Page<PrjTaskTodoListVO> queryPage = new Page<PrjTaskTodoListVO>();
		PrjTaskTodoListVO vo = new PrjTaskTodoListVO();
		queryPage.setPageNo(para.getPage().getPageNo());
		queryPage.setPageSize(para.getPage().getPageSize());
		vo.setOrderBy(para.getPage().getOrderBy());
		BeanCopy.copyPropertiesWithBlank2Null(para.getObj(), vo);
		queryPage = prjTaskAdapterMapper.pagedSelectByEntity(vo, queryPage);
		List<PrjTaskTodoListVO> list = queryPage.getList();
		for (PrjTaskTodoListVO prjTaskTodoListVO : list) {
			if(prjTaskTodoListVO.getTaskStatus().equals("2")){
				PrjTaskPauseDetailExample example = new PrjTaskPauseDetailExample();
				example.setOrderByClause("PAUSE_START_TIME");
				example.createCriteria().andPrjTaskInstIdEqualTo(Long.parseLong(prjTaskTodoListVO.getPrjTaskInstId()));
				List<PrjTaskPauseDetail> prjTaskPauseDetailList = prjTaskPauseDetailMapper.selectByExample(example);
				if(prjTaskPauseDetailList != null){
					PrjTaskPauseDetail prjTaskPauseDetail = prjTaskPauseDetailList.get(prjTaskPauseDetailList.size()-1);
					prjTaskTodoListVO.setTaskPauseDesc(prjTaskPauseDetail.getPauseDesc());
					prjTaskTodoListVO.setTaskPauseType(prjTaskPauseDetail.getPauseType());
					prjTaskTodoListVO.setTaskPauseStartTime(prjTaskPauseDetail.getPauseStartTime());
				}
			}
		}
		para.getPage().setList(new ArrayList<PrjTaskTodoListDTO>());
		para.getPage().setCount(queryPage.getCount());
		BeanCopy.copyPropertiesForListWithBlank2Null(queryPage.getList(), para.getPage().getList(),
				PrjTaskTodoListDTO.class);

		// 计算剩余办结工作日
		for (PrjTaskTodoListDTO p : para.getPage().getList()) {
			int datePer = dimHolidayService.calDatePeriod(new Date(), p.getTaskEndTime(), p.getTimeType());
			p.setTaskRemainTime(datePer);
		}

		pageResult.setObj(para.getPage());

		return pageResult;
	}

	/**
	 *
	 */
	@Override
	public PageResult<PrjTaskTodoListDTO> findMyTaskPage(RequestDTOPage<PrjTaskTodoListDTO> para) {
		PageResult<PrjTaskTodoListDTO> pageResult = new PageResult<>();
		Page<PrjTaskTodoListVO> queryPage = new Page<PrjTaskTodoListVO>();
		PrjTaskTodoListVO vo = new PrjTaskTodoListVO();
		queryPage.setPageNo(para.getPage().getPageNo());
		queryPage.setPageSize(para.getPage().getPageSize());
		vo.setOrderBy(para.getPage().getOrderBy());
		BeanCopy.copyPropertiesWithBlank2Null(para.getObj(), vo);
		if ("1".equals(para.getObj().getPassType())) {
			// 查询我的已办
			queryPage = prjTaskAdapterMapper.pagedSelectByEntityForMyTask(vo, queryPage);
			List<PrjTaskTodoListVO> list = queryPage.getList();
			for (PrjTaskTodoListVO prjTaskTodoListVO : list) {
				if(prjTaskTodoListVO.getTaskStatus().equals("2")){
					PrjTaskPauseDetailExample example = new PrjTaskPauseDetailExample();
					example.setOrderByClause("PAUSE_START_TIME");
					example.createCriteria().andPrjTaskInstIdEqualTo(Long.parseLong(prjTaskTodoListVO.getPrjTaskInstId()));
					List<PrjTaskPauseDetail> prjTaskPauseDetailList = prjTaskPauseDetailMapper.selectByExample(example);
					if(prjTaskPauseDetailList != null){
						PrjTaskPauseDetail prjTaskPauseDetail = prjTaskPauseDetailList.get(prjTaskPauseDetailList.size()-1);
						prjTaskTodoListVO.setTaskPauseDesc(prjTaskPauseDetail.getPauseDesc());
						prjTaskTodoListVO.setTaskPauseType(prjTaskPauseDetail.getPauseType());
						prjTaskTodoListVO.setTaskPauseStartTime(prjTaskPauseDetail.getPauseStartTime());
					}
				}
			}
		} else if ("2".equals(para.getObj().getPassType())) {
			// 查询我的办结
			queryPage = prjTaskAdapterMapper.pagedSelectByEntityForMyTaskFinish(vo, queryPage);
		} else {
			// TODO 查询我办结未上传批文的事项
			queryPage = prjTaskAdapterMapper.pagedSelectByEntityForCert(vo, queryPage);
		}
		para.getPage().setList(new ArrayList<PrjTaskTodoListDTO>());
		para.getPage().setCount(queryPage.getCount());
		BeanCopy.copyPropertiesForListWithBlank2Null(queryPage.getList(), para.getPage().getList(),
				PrjTaskTodoListDTO.class);

		pageResult.setObj(para.getPage());

		return pageResult;
	}

	/**
	 * 项目事项通过并继续
	 *
	 * @param dto
	 * @return
	 */
	@Override
	@Transactional(readOnly = false)
	public CommonResult taskPass(PrjTaskAuditDTO dto) {
		PrjTask entity = new PrjTask();
		entity.setId(dto.getPrjTaskInstId());
		entity.setCurrUser(dto.getCurrUser());
		entity.setUpdateTime(new Date());
		prjTaskMapper.updateByPrimaryKeySelective(entity);

		// 增加事项流转记录
		PrjTaskTransferDetailDTO transferDto = new PrjTaskTransferDetailDTO();
		transferDto.setPrjTaskInstId(dto.getPrjTaskInstId());
		transferDto.setDescription(dto.getAuditDesc());
		transferDto.setTransfer(dto.getUserId());
		transferDto.setReceiver(dto.getCurrUser());

		prjTaskTransferDetailService.saveTransfer(transferDto);

		// 发送短信
		User user = UserUtils.get(dto.getCurrUser());
		if (user != null && StringUtils.isNotBlank(user.getMobile())) {
			iMessageSMS.sendSmsByTplId(user.getMobile(), "S010002");
		}

		return new CommonResult();
	}

	/**
	 * 项目事项办结
	 *
	 * @param dto
	 * @return
	 */
	@Override
	@Transactional(readOnly = false)
	public CommonResult taskFinish(PrjTaskAuditDTO dto) {
		PrjTask entity = prjTaskMapper.selectByPrimaryKey(dto.getPrjTaskInstId());
		PrjTaskDefine prjTaskDefine = prjTaskDefineMapper.selectByPrimaryKey(entity.getTaskId());
		if (entity != null  && prjTaskDefine != null) {
			String bjjgCode = "0";//出证办结
			if (TaskConstants.TASK_STATUS_FINISH.equals(dto.getTaskStatus())) {
				// 办结
				Date currDate = new Date();
				BeanCopy.copyProperties(dto, entity);
				entity.setId(dto.getPrjTaskInstId());
				entity.setTaskStatus(TaskConstants.TASK_STATUS_FINISH);// 更新项目状态为已办结

				// 更新事项实际结束时间
				entity.setTaskRealEndtime(currDate);
				// 更新事项总耗时（减去暂停时间）
				int allDate = dimHolidayService.calDatePeriod(entity.getTaskStartTime(), entity.getTaskRealEndtime(), entity.getTimeType());
				int taskPauseDuration = entity.getTaskPauseDuration() == null ? 0 : entity.getTaskPauseDuration();
				int taskDuration = allDate - taskPauseDuration;
				if (taskDuration <= 0)
					taskDuration = 1;
				entity.setTaskDuration(taskDuration);

				// 如果上传批文附件，则更新项目需要领取批文
				if (StringUtils.isNotBlank(entity.getAuditAttachAddr()))
					updatePrjInstanceIsWithCert(entity.getPrjId(), "1");

				prjTaskMapper.updateByPrimaryKeySelective(entity);

				// 更新审批结束时间
				prjTaskTransferDetailService.updateAuditEndTime(dto.getPrjTaskInstId(), dto.getUserId(), dto.getAuditDesc(), "1");

				// 发送短信
				PrjInstance prjInsTemp = prjInstanceMapper.selectByPrimaryKey(entity.getPrjId());
				if (prjInsTemp != null){
					Map<String, String> map = new HashMap<String, String>();
					map.put("taskName", prjTaskDefine.getTaskName());
					if(StringUtils.isNotBlank(prjInsTemp.getAgentMphone())){
						iMessageSMS.sendSmsByTplIdAndMap(prjInsTemp.getAgentMphone(), "S010004", map);
					}
				}

				// 事项办结处理
				taskFinishDeal(entity);
				//TODO 廉情预警调用接口
				postLqyjApply(entity);

			} else {
				// 审批不通过
				BeanCopy.copyProperties(dto, entity);
				entity.setId(dto.getPrjTaskInstId());
				entity.setTaskStatus(TaskConstants.TASK_STATUS_REFUSER);// 更新项目状态为已不通过
				// 更新事项实际结束时间
				Date currDate = new Date();
				entity.setTaskRealEndtime(currDate);
				entity.setUpdateTime(currDate);
				// 如果上传批文附件，则更新项目需要领取批文
				if (StringUtils.isNotBlank(entity.getAuditAttachAddr()))
					updatePrjInstanceIsWithCert(entity.getPrjId(), "1");

				prjTaskMapper.updateByPrimaryKeySelective(entity);

				// 更新审批结束时间
				prjTaskTransferDetailService.updateAuditEndTime(dto.getPrjTaskInstId(), dto.getUserId(), dto.getAuditDesc(), "6");
				
				// 退件领取表
				PrjTaskRejectMaterial rej = new PrjTaskRejectMaterial();
				rej.setPrjInstId(entity.getPrjId());
				rej.setPrjTaskInstId(entity.getId());
				rej.setCreatTime(currDate);
				rej.setIsFetched("0");
				prjTaskRejectMaterialMapper.insertSelective(rej);
				bjjgCode = "1";//退回办结
			}

			reportPushService.sbBjDataMethod(entity,bjjgCode);
		}
		return new CommonResult();
	}


	/**
	 * 事项办结处理
	 * 
	 * @param task
	 */
	@Transactional(readOnly = false)
	private void taskFinishDeal(PrjTask task) {
		//根据prjId查询是否是网厅提交通过项目是，则对网厅项目同步更改
		PrjInstance prjIn = prjInstanceMapper.selectByPrimaryKey(task.getPrjId());
		PrjInstance wsbsPrjIn = new PrjInstance();
		if(!StringUtils.isBlank(prjIn.getWsbsUserName())){
			PrjInstance record = new PrjInstance();
			record.setPrjCode(prjIn.getPrjCode());
			record.setChannel("1");
			wsbsPrjIn = prjInstanceMapper.selectOneByEntitySelective(record);
		}
		Date currDate = new Date();
		
		// 企业项目：
		// 判断有无事项依赖当前事项，如果有，并且其依赖的其他事项也全部办结，则激活此事项
		startDepTask(task);
		
		// 1、判断当前阶段其他事项是否全部完成
		PrjTaskExample example = new PrjTaskExample();
		example.createCriteria().andPrjStageInstIdEqualTo(task.getPrjStageInstId());
		List<PrjTask> prjList = prjTaskMapper.selectByExample(example);
		if (prjList != null && prjList.size() > 0) {
			
			// 材料依赖事项
			isDependencyMaterial(prjList,task.getTaskId(), task.getAuditAttachAddr(), task.getAuditAttachName());
			
			boolean allFinsihed = true;
			for (PrjTask prj : prjList) {
				if (!TaskConstants.TASK_STATUS_FINISH.equals(prj.getTaskStatus()) && !TaskConstants.TASK_STATUS_OFFINISH.equals(prj.getTaskStatus()) && !TaskConstants.TASK_STATUS_NONEED.equals(prj.getTaskStatus())) {
					allFinsihed = false;
					break;
				}

			}
			// 当前项目阶段所有事项已经完成，跟新阶段状态和时间
			if (allFinsihed) {
				PrjStage prjStage = prjStageMapper.selectByPrimaryKey(task.getPrjStageInstId());
				if (prjStage != null) {
					PrjStageDefine currPrjStageDefine = prjStageDefineMapper.selectByPrimaryKey(prjStage.getStageId());
					if (currPrjStageDefine != null) {

						prjStage.setStageStatus("4"); // 阶段状态：办结
						prjStage.setStageRealEndtime(currDate); // 阶段实际结束时间
						// 阶段耗时
						int stageAllDate = dimHolidayService.calDatePeriod(prjStage.getStageStartTime(), prjStage.getStageRealEndtime(), currPrjStageDefine.getTimeType());
						if (stageAllDate <= 0)
							stageAllDate = 0;

						prjStage.setStageDuration(stageAllDate);
						prjStage.setUpdateTime(currDate);
						prjStageMapper.updateByPrimaryKeySelective(prjStage);
						// 更新项目当前阶段审批完成
						PrjInstance prjIns = new PrjInstance();
						prjIns.setId(task.getPrjId());
						prjIns.setIsStageComplete("1"); // 项目所处阶段是否审批完成
						prjIns.setUpdateTime(currDate);
						//同步修改网上办事项目
						if(wsbsPrjIn != null && !"".equals(wsbsPrjIn.getId())){
							wsbsPrjIn.setIsStageComplete("1");
							wsbsPrjIn.setUpdateTime(currDate);
							prjInstanceMapper.updateByPrimaryKeySelective(wsbsPrjIn);
						}
						prjInstanceMapper.updateByPrimaryKeySelective(prjIns);

						// 2、判断当前阶段是否是最后一个阶段，如果是，则更新项目完成状态和时间
						PrjStageDefineExample dexample = new PrjStageDefineExample();
						dexample.createCriteria().andPreStageIdEqualTo(prjStage.getStageId());
						List<PrjStageDefine> dList = prjStageDefineMapper.selectByExample(dexample);
						if (dList == null || dList.size() == 0) {
							
							PrjStageExample pexample = new PrjStageExample();
							PrjStageExample.Criteria pcriteria = pexample.createCriteria();
							pcriteria.andPrjIdEqualTo(prjStage.getPrjId());
							List<PrjStage> prjStageList = prjStageMapper.selectByExample(pexample);
							int stageDuration = 0;
							if (prjStageList != null) {
								for (PrjStage ps : prjStageList) {
									// 项目总耗时等于各阶段耗时之和
									stageDuration += ps.getStageDuration();
								}
							}

							// 最后一个阶段更新项目
							PrjInstance prjInsLast = new PrjInstance();
							prjInsLast.setId(task.getPrjId());
							prjInsLast.setIsPrjComplete("1"); // 项目全流程是否完成
							prjInsLast.setTotalDuration(stageDuration);// 项目总耗时等于各阶段耗时之和
							prjInsLast.setUpdateTime(currDate);
							prjInsLast.setRealEndTime(currDate);
							//同步修改网上办事项目
							if(wsbsPrjIn != null && !"".equals(wsbsPrjIn.getId())){
								wsbsPrjIn.setIsPrjComplete("1");
								wsbsPrjIn.setTotalDuration(stageDuration);
								wsbsPrjIn.setUpdateTime(currDate);
								wsbsPrjIn.setRealEndTime(currDate);
								prjInstanceMapper.updateByPrimaryKeySelective(wsbsPrjIn);
							}
							prjInstanceMapper.updateByPrimaryKeySelective(prjInsLast);

						}
						
						
						
					}

				}
			}
		}
	}
	
	
	/**
	 * 判断当前事项办结产物是否是其他事项依赖材料
	 */
	private void isDependencyMaterial(List<PrjTask> list, Long taskId, String auditAttachAddr, String auditAttachName) {
		 
		 for (PrjTask task : list) {
			 PrjTaskMaterialDefExample mdefExample = new PrjTaskMaterialDefExample();
			 mdefExample.createCriteria().andTaskIdEqualTo(task.getTaskId());
			 List<PrjTaskMaterialDef> matdefList = prjTaskMaterialDefMapper.selectByExample(mdefExample);
			 PrjStage prjStage = prjStageMapper.selectByPrimaryKey(task.getPrjStageInstId());
			 if (matdefList != null && matdefList.size() > 0) {
				 boolean flag = false;
				 for (PrjTaskMaterialDef mdef : matdefList) {
					 if ("1".equals(mdef.getIsResultMaterial()) && mdef.getResultTaskId() != null && mdef.getResultTaskId() == taskId) {
						 PrjStageMaterial record = new PrjStageMaterial();
						 record.setPrjId(task.getPrjId());
						 record.setPrjStageInstId(task.getPrjStageInstId());
						 record.setTaskId(task.getTaskId());
						 record.setMaterialId(mdef.getMaterialId());
						 PrjStageMaterial prjStageMaterial = prjStageMaterialMapper.selectOneByEntitySelective(record);

						/**
						* 事项办结修改材料新增,如果结果材料为其他事项的材料,并且没有上传,要重新插入此材料,把原来的更新材料,改为插入材料
						*/
//						  if (prjStageMaterial != null) {
//							 prjStageMaterial.setIsComplete("1");
//							 if (StringUtils.isNotBlank(auditAttachAddr))
//								 prjStageMaterial.setMaterialAddr(auditAttachAddr);
//							 if (StringUtils.isNotBlank(auditAttachName))
//								 prjStageMaterial.setMaterialName(auditAttachName);
//							 prjStageMaterial.setOriginalNum(prjStageMaterial.getOriginalNum() == null ? 1: prjStageMaterial.getOriginalNum() + 1); // 材料份数加1
//							 prjStageMaterial.setUpdateTime(new Date());
//							 prjStageMaterialMapper.updateByPrimaryKeySelective(prjStageMaterial);
//							 flag = true;
//						 }
						 if(prjStageMaterial == null){
							 prjStageMaterial = new PrjStageMaterial();
							 prjStageMaterial.setIsComplete("1");
							 prjStageMaterial.setPrjId(task.getPrjId());
							 prjStageMaterial.setPrjStageInstId(task.getPrjStageInstId());
							 prjStageMaterial.setTaskId(task.getTaskId());
							 prjStageMaterial.setMaterialId(mdef.getMaterialId());
							 prjStageMaterial.setStageId(prjStage.getStageId());
							 if (StringUtils.isNotBlank(auditAttachAddr))
								 prjStageMaterial.setMaterialAddr(auditAttachAddr);
							 if (StringUtils.isNotBlank(auditAttachName))
								 prjStageMaterial.setMaterialName(auditAttachName);
							 prjStageMaterial.setOriginalNum(mdef.getOriginalNum());
							 prjStageMaterial.setCopyNum(mdef.getCopyNum());
							 prjStageMaterial.setIsMandatory(mdef.getIsMandatory());
							 prjStageMaterial.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
							 prjStageMaterial.setCreatTime(new Date());
							 prjStageMaterial.setUpdateTime(new Date());
							 prjStageMaterial.setCreator(UserUtils.getUser().getId());
							 prjStageMaterial.setUpdator(UserUtils.getUser().getId());
							 prjStageMaterialMapper.insert(prjStageMaterial);
							 flag = true;
						 }
					 }
				 }
				 
				 // 发送短信通知，判断该事项是否能受理（全部必须材料已经提供）
				 if (flag && TaskConstants.TASK_STATUS_DRAFT.equals(task.getTaskStatus())) {
					 PrjStageMaterial record = new PrjStageMaterial();
					 record.setPrjId(task.getPrjId());
					 record.setPrjStageInstId(task.getPrjStageInstId());
					 record.setTaskId(task.getTaskId());
					 List<PrjStageMaterial> taskMates = prjStageMaterialMapper.selectByEntitySelective(record);
					 boolean send1 = false;
					 for (PrjStageMaterial mate : taskMates) {
						 if ("1".equals(mate.getIsMandatory()) && !"1".equals(mate.getIsComplete())) {
							 // 存在必须且未提供的材料，不能受理
							 send1 = true;
							 break;
						 }
					 }
					 if (!send1) {
						 PrjInstance prjIns = prjInstanceMapper.selectByPrimaryKey(task.getPrjId());
						 if (prjIns != null) {
							 PrjBusinessAccept prjAcc = prjBusinessAcceptMapper.selectByPrimaryKey(prjIns.getAcceptId());
							 if (prjAcc != null) {
								User user = userDao.get(prjAcc.getCreator());
								if (user != null && StringUtils.isNotBlank(user.getMobile())) {
									PrjTaskDefine prjTaskDefine = prjTaskDefineMapper.selectByPrimaryKey(task.getTaskId());
									 Map<String,String> map = new HashMap<String, String>();
									 map.put("prjNo", prjIns.getPrjCode());
									 map.put("taskName", StringUtils.isNotBlank(prjTaskDefine.getTaskName()) ? prjTaskDefine.getTaskName() : "");
									 iMessageSMS.sendSmsByTplIdAndMap(user.getMobile(), "S010008", map);
								}
							 }
						 }
					 }
				 }
				 
				 // 发送短信通知，判断该事项是否能办结（全部材料已经提供）
				 if (flag && (TaskConstants.TASK_STATUS_AUDIT.equals(task.getTaskStatus()) || TaskConstants.TASK_STATUS_PAUSE.equals(task.getTaskStatus()))) {
					 PrjStageMaterial record = new PrjStageMaterial();
					 record.setPrjId(task.getPrjId());
					 record.setPrjStageInstId(task.getPrjStageInstId());
					 record.setTaskId(task.getTaskId());
					 List<PrjStageMaterial> taskMates = prjStageMaterialMapper.selectByEntitySelective(record);
					 boolean send2 = false;
					 for (PrjStageMaterial mate : taskMates) {
						 if (!"1".equals(mate.getIsComplete())) {
							 // 存在未提供的材料，不能办结
							 send2 = true;
							 break;
						 }
					 }
					 
					 if (!send2) {
						 User user = userDao.get(task.getCurrUser());
						 if (user != null && StringUtils.isNotBlank(user.getMobile()))
							 iMessageSMS.sendSmsByTplId(user.getMobile(), "S010007");
					 }
				 }
				 
			 }
			 
		 }
	}
		

	/**
	 * 激活依赖项目
	 *
	 * @param entity
	 */
	private void startDepTask(PrjTask entity) {
		PrjTaskDependencyExample depExample = new PrjTaskDependencyExample();
		depExample.createCriteria().andDepTaskIdEqualTo(entity.getTaskId());
		List<PrjTaskDependency> depList = prjTaskDependencyMapper.selectByExample(depExample);// 查询依赖当前事项的事项
		if (depList != null && depList.size() > 0) {
			for (PrjTaskDependency dep : depList) {
				PrjTaskDependencyExample depExample2 = new PrjTaskDependencyExample();
				depExample2.createCriteria().andTaskIdEqualTo(dep.getTaskId());
				List<PrjTaskDependency> depList2 = prjTaskDependencyMapper.selectByExample(depExample2);// 查询依赖当前事项的事项的所有依赖事项
				if (depList2 != null && depList2.size() > 0) {
					boolean isStart = true;
					for (PrjTaskDependency dep2 : depList2) { // 判断所有依赖事项是否全部办结
						PrjTaskExample taskExample = new PrjTaskExample();
						PrjTaskExample.Criteria tacriteria = taskExample.createCriteria();
						tacriteria.andPrjIdEqualTo(entity.getPrjId());
						tacriteria.andTaskIdEqualTo(dep2.getDepTaskId());
						tacriteria.andTaskStatusEqualTo(TaskConstants.TASK_STATUS_FINISH);

						PrjTaskExample.Criteria tacriteria2 = taskExample.createCriteria();
						tacriteria2.andPrjIdEqualTo(entity.getPrjId());
						tacriteria2.andTaskIdEqualTo(dep2.getDepTaskId());
						tacriteria2.andTaskStatusEqualTo(TaskConstants.TASK_STATUS_OFFINISH);
						taskExample.or(tacriteria2);

						PrjTaskExample.Criteria tacriteria3 = taskExample.createCriteria();
						tacriteria3.andPrjIdEqualTo(entity.getPrjId());
						tacriteria3.andTaskIdEqualTo(dep2.getDepTaskId());
						tacriteria3.andTaskStatusEqualTo(TaskConstants.TASK_STATUS_NONEED);
						taskExample.or(tacriteria3);

						List<PrjTask> depTasks = prjTaskMapper.selectByExample(taskExample);
						if (!(depTasks != null && depTasks.size() > 0)) {
							isStart = false;
							break;
						}
					}
					if (isStart) {
						PrjTaskExample taskExample2Start = new PrjTaskExample();
						PrjTaskExample.Criteria tacriteria2Start = taskExample2Start.createCriteria();
						tacriteria2Start.andPrjIdEqualTo(entity.getPrjId());
						tacriteria2Start.andTaskIdEqualTo(dep.getTaskId());
						tacriteria2Start.andTaskStatusEqualTo(TaskConstants.TASK_STATUS_NOSTART);
						List<PrjTask> prjList2Starts = prjTaskMapper.selectByExample(taskExample2Start);
						if (prjList2Starts != null && prjList2Starts.size() > 0) {
							PrjTask prjEntity2Start = prjList2Starts.get(0);
							prjEntity2Start.setTaskStatus(TaskConstants.TASK_STATUS_AUDIT);
							prjEntity2Start.setUpdateTime(new Date());
							Date startDate = dimHolidayService.findNextWorkDay();
							prjEntity2Start.setTaskStartTime(startDate);
							int amount = prjEntity2Start.getTaskDueDuration() == null ? 0
									: prjEntity2Start.getTaskDueDuration();
							Date endDate = dimHolidayService.findWorkDay(startDate, amount,
									prjEntity2Start.getTimeType() == null
											? DicConstants.TASK_DEFINE_DIM_TYPE_CALENDARDAY
											: prjEntity2Start.getTimeType());
							prjEntity2Start.setTaskEndTime(endDate);
							prjTaskMapper.updateByPrimaryKeySelective(prjEntity2Start);

							// 发送短信
							User user = UserUtils.get(prjEntity2Start.getInitUser());
							if (user != null && StringUtils.isNotBlank(user.getMobile())){
								iMessageSMS.sendSmsByTplId(user.getMobile(), "S010001");
							}
						}
					}
				}
			}
		}
	}



	@Override
	@Transactional(readOnly = false)
	public CommonResult certUpload(PrjTaskAuditDTO dto) {
		PrjTask entity = new PrjTask();
		BeanCopy.copyProperties(dto, entity);
		entity.setId(dto.getPrjTaskInstId());
		
		if (StringUtils.isBlank(entity.getAuditAttachAddr()))
			entity.setAuditAttachAddr(null);
		else 
			// 上传批文后，需更新依赖材料地址
			updateDependencyMaterial(dto.getPrjTaskInstId(), dto.getAuditAttachAddr(), dto.getAuditAttachName());
			
		if (StringUtils.isBlank(entity.getAuditAttachName()))
			entity.setAuditAttachName(null);
		if (StringUtils.isBlank(entity.getAuditAttachCodeAddr()))
			entity.setAuditAttachCodeAddr(null);
		if (StringUtils.isBlank(entity.getAuditAttachCodeName()))
			entity.setAuditAttachCodeName(null);
		
		prjTaskMapper.updateByPrimaryKeySelective(entity);

		// 上传原始批文时，更新项目为需要领取证件
		if (StringUtils.isNotBlank(dto.getAuditAttachAddr())) {
			PrjTask prj = prjTaskMapper.selectByPrimaryKey(dto.getPrjTaskInstId());
			updatePrjInstanceIsWithCert(prj.getPrjId(), "1");
		}

		return new CommonResult();
	}
	
	/**
	 * 更新当前事项办结产物是其他事项依赖材料地址
	 */
	private void updateDependencyMaterial(Long taskInsId, String auditAttachAddr, String auditAttachName) {
		PrjTask prjTask = prjTaskMapper.selectByPrimaryKey(taskInsId);
		PrjStageMaterialExample psmExample = new PrjStageMaterialExample();
		psmExample.createCriteria().andPrjIdEqualTo(prjTask.getPrjId());
		List<PrjStageMaterial> psmMatList = prjStageMaterialMapper.selectByExample(psmExample);
		if (psmMatList != null && psmMatList.size() > 0) {
			for (PrjStageMaterial psm : psmMatList) {
				PrjTaskMaterialDef mdef = new PrjTaskMaterialDef();
				mdef.setTaskId(psm.getTaskId());
				mdef.setMaterialId(psm.getMaterialId());
				mdef.setIsDelete("0");
				mdef.setIsResultMaterial("1");
				mdef.setResultTaskId(prjTask.getTaskId());
				PrjTaskMaterialDef smdef = prjTaskMaterialDefMapper.selectOneByEntitySelective(mdef);
				if (smdef != null) {
					psm.setMaterialAddr(auditAttachAddr);
					psm.setMaterialName(auditAttachName);
					prjStageMaterialMapper.updateByPrimaryKeySelective(psm);
				} 
			}
		}
	}

	/**
	 * 项目事项暂停
	 *
	 * @param dto
	 * @return
	 */
	@Override
	@Transactional(readOnly = false)
	public CommonResult taskPause(PrjTaskPauseDTO dto) {
		PrjTask prjTask = prjTaskMapper.selectByPrimaryKey(dto.getPrjTaskInstId());
		PrjTaskDefine prjTaskDefine = prjTaskDefineMapper.selectByPrimaryKey(prjTask.getTaskId());
		if (prjTask != null && prjTaskDefine != null) {
			prjTask.setTaskStatus(TaskConstants.TASK_STATUS_PAUSE);
			// 暂停次数加1
			prjTask.setPauseNum(prjTask.getPauseNum() == null ? 1 : prjTask.getPauseNum() + 1);
			if ("1".equals(dto.getPauseType())) {
				// 补齐材料暂停次数
				prjTask.setPauseNumMat(prjTask.getPauseNumMat() == null ? 1 : prjTask.getPauseNumMat() + 1);
			}
			

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date pauseDate = sdf.parse(dto.getPauseDateStr());
				dto.setPauseDate(pauseDate);
				if (StringUtils.isNotBlank(dto.getProvideEndDateStr())) {
					Date provideEndDate = sdf.parse(dto.getProvideEndDateStr());
					dto.setProvideEndDate(provideEndDate);
				}
				Date newDate = new Date();
				Calendar aCalendar = Calendar.getInstance();
				aCalendar.setTime(prjTask.getTaskStartTime());
				int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
				aCalendar.setTime(newDate);
				int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
				boolean b = false;
				if (day1 > day2) {
					newDate = prjTask.getTaskStartTime();
					b = true;
				}
				int duration = dimHolidayService.calculatePausePeriod(newDate, pauseDate, prjTask.getTimeType());
				if (b)
					duration++;
				int allPauseDuration = prjTask.getTaskPauseDuration() == null ? duration : duration + prjTask.getTaskPauseDuration();
				// 暂停时更新事项应结束时间
				// 如果项目应结束时间为今天或已经超期，则不更新事项应结束时间
				aCalendar.setTime(prjTask.getTaskEndTime());
				int day3 = aCalendar.get(Calendar.DAY_OF_YEAR);
				if (day3 >= day2) {
					Date newEndTime = dimHolidayService.findWorkDay(prjTask.getTaskStartTime(),allPauseDuration + prjTask.getTaskDueDuration(), prjTask.getTimeType());
					prjTask.setTaskEndTime(newEndTime);
				}
				prjTaskMapper.updateByPrimaryKeySelective(prjTask);
				dto.setDuration(duration);
				// 记录暂停记录表
				prjTaskPauseDetailService.savePause(dto);

				// 发送短信
				if ("1".equals(dto.getSendMessage())) {
					PrjInstance prjIns = prjInstanceMapper.selectByPrimaryKey(prjTask.getPrjId());
					if (prjIns != null && StringUtils.isNotBlank(prjIns.getAgentMphone())) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("taskName", prjTaskDefine.getTaskName());
						map.put("dateTime", dto.getProvideEndDateStr());
						iMessageSMS.sendSmsByTplIdAndMap(prjIns.getAgentMphone(), "S010005", map);
					}
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return new CommonResult();
	}

	/**
	 * 项目事项恢复
	 *
	 * @param dto
	 * @return
	 */
	@Override
	@Transactional(readOnly = false)
	public CommonResult taskResume(PrjTaskPauseDTO dto) {
		PrjTask prjTask = prjTaskMapper.selectByPrimaryKey(dto.getPrjTaskInstId());
		if (prjTask != null) {
			dto.setTimeType(prjTask.getTimeType());
			// 项目事项恢复
			int pauseDuration = prjTaskPauseDetailService.resume(dto).getObj();

			prjTask.setTaskPauseDuration(prjTask.getTaskPauseDuration() == null ? pauseDuration
					: prjTask.getTaskPauseDuration() + pauseDuration); // 更新项目暂停总耗时

			// 恢复时更新项目应结束时间(只有当项目结束时间大于暂停开始时间时才更新)
			Calendar c = Calendar.getInstance();
			c.setTime(prjTask.getTaskEndTime());
			int taskEndDay = c.get(Calendar.DAY_OF_YEAR);
			c.setTime(dto.getPauseDate());
			int pauseStartDay = c.get(Calendar.DAY_OF_YEAR);
			if (taskEndDay > pauseStartDay) {
				int realPauseDate = prjTask.getTaskPauseDuration() + prjTask.getTaskDueDuration();

				Date newEndTime = dimHolidayService.findWorkDay(prjTask.getTaskStartTime(), realPauseDate,
						prjTask.getTimeType());
				prjTask.setTaskEndTime(newEndTime);
			}

			prjTask.setTaskStatus(TaskConstants.TASK_STATUS_AUDIT); // 更新项目状态
			prjTaskMapper.updateByPrimaryKeySelective(prjTask);
		}
		return new CommonResult();
	}

	/**
	 * 项目事项操作列表
	 *
	 * @param prjTaskInstId
	 *            项目事项ID
	 * @return
	 */
	@Override
	public ListResult<PrjTaskHandleDTO> taskHandleList(long prjTaskInstId) {

		ListResult<PrjTaskHandleDTO> result = new ListResult<PrjTaskHandleDTO>();
		List<PrjTaskHandleDTO> dtoList = new ArrayList<PrjTaskHandleDTO>();
		PrjTaskHandleDTO dto = null;

		// 审批
		PrjTaskTransferDetailExample texample = new PrjTaskTransferDetailExample();
		texample.createCriteria().andPrjTaskInstIdEqualTo(prjTaskInstId);
		List<PrjTaskTransferDetail> tlist = prjTaskTransferDetailMapper.selectByExample(texample);

		if (tlist != null && tlist.size() > 0) {
			for (PrjTaskTransferDetail ta : tlist) {
				if (ta.getUpdateTime() != null) {
					dto = new PrjTaskHandleDTO();
					if ("1".equals(ta.getIsFinished())) 
						dto.setHandleType(TaskConstants.TASK_HANDLE_FINISH);
					else 
						dto.setHandleType(TaskConstants.TASK_HANDLE_AUDIT);
					dto.setUserName(getUserName(ta.getReceiver()));
					dto.setStartTime(ta.getCreatTime());
					dto.setEndTime(ta.getUpdateTime());
					dto.setDesc(ta.getDescription());
					dtoList.add(dto);
				}
			}
		}

		// 暂停
		PrjTaskPauseDetailExample pexample = new PrjTaskPauseDetailExample();
		pexample.createCriteria().andPrjTaskInstIdEqualTo(prjTaskInstId);
		List<PrjTaskPauseDetail> plist = prjTaskPauseDetailMapper.selectByExample(pexample);

		if (plist != null && plist.size() > 0) {
			for (PrjTaskPauseDetail pa : plist) {
				dto = new PrjTaskHandleDTO();
				dto.setHandleType(TaskConstants.TASK_HANDLE_PAUSE);
				dto.setPauseType(pa.getPauseType());
				dto.setUserName(getUserName(pa.getCreator()));
				dto.setStartTime(pa.getPauseStartTime());
				dto.setEndTime(pa.getPauseEndTime());
				dto.setDesc(pa.getPauseDesc());
				dtoList.add(dto);
			}
		}

		Collections.sort(dtoList, new Comparator<PrjTaskHandleDTO>() {
			@Override
			public int compare(PrjTaskHandleDTO arg0, PrjTaskHandleDTO arg1) {
				Date d0 = arg0.getEndTime();
				Date d1 = arg1.getEndTime();
				if (d0 == null)
					d0 = new Date();
				if (d1 == null)
					d1 = new Date();
					
				return d0.compareTo(d1);
			}
		});

		result.setObj(dtoList);
		return result;
	}

	private String getUserName(String userId) {
		User user = userDao.get(userId);
		String userName = "";
		if (user != null) {
			userName = user.getName();
		}
		return userName;
	}

	@Override
	public Result<PrjTaskDefineDTO> findTaskDefine(long prjTaskInstId) {
		Result<PrjTaskDefineDTO> result = new Result<PrjTaskDefineDTO>();
		PrjTaskDefineDTO dto = new PrjTaskDefineDTO();
		PrjTask prjTask = prjTaskMapper.selectByPrimaryKey(prjTaskInstId);
		if (prjTask != null) {
			PrjTaskDefine prjTaskDefine = prjTaskDefineMapper.selectByPrimaryKey(prjTask.getTaskId());
			if (prjTaskDefine != null) {
				BeanCopy.copyProperties(prjTaskDefine, dto);
				
				// 事项所需材料
				PrjStageMaterialExample psmExample = new PrjStageMaterialExample();
				PrjStageMaterialExample.Criteria criteria = psmExample.createCriteria();
				criteria.andPrjIdEqualTo(prjTask.getPrjId());// 项目ID
				criteria.andPrjStageInstIdEqualTo(prjTask.getPrjStageInstId());// 项目阶段实例ID
				criteria.andTaskIdEqualTo(prjTask.getTaskId());
				List<PrjStageMaterial> pmsList = prjStageMaterialMapper.selectByExample(psmExample);
				
				for (PrjStageMaterial md : pmsList) {
					// 材料是否全部提供
					if (!"1".equals(md.getIsComplete())) {
						dto.setIsAllMater("1"); // 未全部提供
					}
				}
			}
		}
		result.setObj(dto);
		return result;
	}

	/**
	 * 根据项目事项ID查询项目事项定义表
	 *
	 * @param id
	 *            项目事项ID
	 * @return
	 */
	@Override
	public Result<PrjTaskDTO> findTaskById(long id) {

		PrjTaskDTO dto = new PrjTaskDTO();
		PrjTask prjTask = prjTaskMapper.selectByPrimaryKey(id);
		if (prjTask != null)
			BeanCopy.copyProperties(prjTask, dto);

		return new Result<PrjTaskDTO>(dto);
	}

	/**
	 * 查询项目事项所需材料
	 *
	 * @param prjTaskInstId
	 *            项目事项ID
	 * @return
	 */
	@Override
	public ListResult<PrjTaskMaterialDTO> findTaskMaterialList(long prjTaskInstId) {
		ListResult<PrjTaskMaterialDTO> resultList = new ListResult<PrjTaskMaterialDTO>();
		List<PrjTaskMaterialDTO> dtoList = new ArrayList<PrjTaskMaterialDTO>();

		// 项目事项
		PrjTask prjTask = prjTaskMapper.selectByPrimaryKey(prjTaskInstId);

		if (prjTask != null) {
			Long taskId = prjTask.getTaskId();
			PrjTaskMaterialDefExample materExample = new PrjTaskMaterialDefExample();
			materExample.createCriteria().andTaskIdEqualTo(taskId);
			// 事项所需材料
			List<PrjTaskMaterialDef> materList = prjTaskMaterialDefMapper.selectByExample(materExample);
			PrjTaskMaterialDTO dto = null;

			for (PrjTaskMaterialDef ma : materList) {
				dto = new PrjTaskMaterialDTO();
				PrjStageMaterialExample psmExample = new PrjStageMaterialExample();
				PrjStageMaterialExample.Criteria criteria = psmExample.createCriteria();
				criteria.andPrjIdEqualTo(prjTask.getPrjId());// 项目ID
				criteria.andPrjStageInstIdEqualTo(prjTask.getPrjStageInstId());// 项目阶段实例ID
				criteria.andMaterialIdEqualTo(ma.getMaterialId());// 材料ID
				criteria.andTaskIdEqualTo(taskId);
				List<PrjStageMaterial> pmsList = prjStageMaterialMapper.selectByExample(psmExample);
				if (pmsList != null && pmsList.size() > 0) {
					PrjStageMaterial psm = pmsList.get(0);
					dto.setMaterialAddr(psm.getMaterialAddr());
					dto.setCreatTime(psm.getCreatTime());
					dto.setIsMandatory(ma.getIsMandatory());
					dto.setIsComplete(psm.getIsComplete());
					
					DimMaterial dimMaterial = dimMaterialMapper.selectByPrimaryKey(ma.getMaterialId());
					if (dimMaterial != null)
						dto.setMaterialName(dimMaterial.getName());
					dtoList.add(dto);
				}
			}
		}
		resultList.setObj(dtoList);
		return resultList;
	}

	/**
	 * 从视图获取新的成果领取列表
	 * @param requestPage
	 * @return
     */
	@Override
	public PageResult<PrjFetchViewDTO> findPrjForCertGetList(RequestDTOPage<PrjFetchViewDTO> requestPage) {
		PageResult<PrjFetchViewDTO> pageResult = new PageResult<PrjFetchViewDTO>();
		Page<PrjFetchView> queryPage = new Page<PrjFetchView>();
		queryPage.setPageNo(requestPage.getPage().getPageNo());
		queryPage.setPageSize(requestPage.getPage().getPageSize());
		PrjFetchView vo = new PrjFetchView();
		PrjFetchViewExample example = new PrjFetchViewExample();
		PrjFetchViewExample.Criteria criteria = example.createCriteria();
		PrjFetchViewDTO dto = requestPage.getObj();
		if (StringUtils.isNotBlank(dto.getPrjCode())) {
			criteria.andPrjCodeLike("%" + dto.getPrjCode().trim() + "%");
		}
		if (StringUtils.isNotBlank(dto.getPrjName())) {
			criteria.andPrjNameLike("%" + dto.getPrjName().trim() + "%");
		}
		if (StringUtils.isNotBlank(dto.getPrjFetchStatus())) {
			criteria.andPrjFetchStatusEqualTo(dto.getPrjFetchStatus());
		}
		if (StringUtils.isNotBlank(dto.getCompany())) {
			criteria.andCompanyLike("%" + dto.getCompany().trim() + "%");
		}
		queryPage = prjFetchViewMapper.pagedSelectByExample(example, queryPage);
		requestPage.getPage().setList(new ArrayList<PrjFetchViewDTO>());
		requestPage.getPage().setCount(queryPage.getCount());
		BeanCopy.copyPropertiesForListWithBlank2Null(queryPage.getList(), requestPage.getPage().getList(),
				PrjFetchViewDTO.class);
		pageResult.setObj(requestPage.getPage());
		return pageResult;
	}

	/**
	 * 老的成果领取列表,现在重新梳理逻辑
	 * @param requestPage
	 * @return
     */
//	@Override
//	public PageResult<PrjTaskTodoListDTO> findPrjForCertGetList(RequestDTOPage<PrjTaskTodoListDTO> requestPage) {
//		PageResult<PrjTaskTodoListDTO> pageResult = new PageResult<PrjTaskTodoListDTO>();
//		Page<PrjTaskTodoListVO> queryPage = new Page<PrjTaskTodoListVO>();
//		queryPage.setPageNo(requestPage.getPage().getPageNo());
//		queryPage.setPageSize(requestPage.getPage().getPageSize());
//		PrjTaskTodoListVO vo = new PrjTaskTodoListVO();
//		PrjTaskTodoListDTO dto = requestPage.getObj();
//		vo.setUserId(dto.getUserId());
//		if (StringUtils.isNotBlank(dto.getPrjCode())) {
//			vo.setPrjCode(dto.getPrjCode());
//		}
//		if (StringUtils.isNotBlank(dto.getPrjName())) {
//			vo.setPrjName(dto.getPrjName());
//		}
//		queryPage = prjTaskAdapterMapper.pagedSelectForPrjCert(vo, queryPage);
//		requestPage.getPage().setList(new ArrayList<PrjTaskTodoListDTO>());
//		requestPage.getPage().setCount(queryPage.getCount());
//		BeanCopy.copyPropertiesForListWithBlank2Null(queryPage.getList(), requestPage.getPage().getList(),
//				PrjTaskTodoListDTO.class);
//		pageResult.setObj(requestPage.getPage());
//		return pageResult;
//	}

	@Override
	public PageResult<PrjTaskTodoListDTO> pagedSelectForRejectBackList(RequestDTOPage<PrjTaskTodoListDTO> requestPage) {
		PageResult<PrjTaskTodoListDTO> pageResult = new PageResult<PrjTaskTodoListDTO>();
		Page<PrjTaskTodoListVO> queryPage = new Page<PrjTaskTodoListVO>();
		queryPage.setPageNo(requestPage.getPage().getPageNo());
		queryPage.setPageSize(requestPage.getPage().getPageSize());
		PrjTaskTodoListVO vo = new PrjTaskTodoListVO();
		PrjTaskTodoListDTO dto = requestPage.getObj();
		vo.setUserId(dto.getUserId());
		if (StringUtils.isNotBlank(dto.getPrjCode())) {
			vo.setPrjCode(dto.getPrjCode());
		}
		if (StringUtils.isNotBlank(dto.getPrjName())) {
			vo.setPrjName(dto.getPrjName());
		}
		queryPage = prjTaskAdapterMapper.pagedSelectForRejectBackList(vo, queryPage);
		requestPage.getPage().setList(new ArrayList<PrjTaskTodoListDTO>());
		requestPage.getPage().setCount(queryPage.getCount());
		
		BeanCopy.copyPropertiesForListWithBlank2Null(queryPage.getList(), requestPage.getPage().getList(),
				PrjTaskTodoListDTO.class);
		pageResult.setObj(requestPage.getPage());
		return pageResult;
	}
 
	@Override
	public ListResult<PrjCertGetDTO> getCert(Long prjInsId) {
		PrjTaskExample example = new PrjTaskExample();
		PrjTaskExample.Criteria criteria = example.createCriteria();
		criteria.andPrjIdEqualTo(prjInsId);
		criteria.andIsWithCertEqualTo("1");
		criteria.andAuditAttachAddrIsNotNull();
		criteria.andAuditAttachAddrNotEqualTo("");
		List<PrjTask> list = prjTaskMapper.selectByExample(example);
		List<PrjCertGetDTO> dtoList = new ArrayList<PrjCertGetDTO>();
		if (list != null && list.size() > 0) {
			BeanCopy.copyPropertiesForListWithBlank2Null(list, dtoList, PrjCertGetDTO.class);
		}
		ListResult<PrjCertGetDTO> resultList = new ListResult<PrjCertGetDTO>();
		resultList.setObj(dtoList);
		return resultList;
	}
	
	@Override
	public ListResult<PrjTaskForOffLineFinishDTO> getTaskForOffLineFinish(Long prjInsId, Long stageInsId) {
		List<PrjTaskForOffLineFinishVO> voList = prjTaskAdapterMapper.findTaskForOffLineFinish(prjInsId, stageInsId);
		List<PrjTaskForOffLineFinishDTO> dtoList = new ArrayList<PrjTaskForOffLineFinishDTO>();
		if (voList != null && voList.size() > 0) {
			BeanCopy.copyPropertiesForListWithBlank2Null(voList, dtoList, PrjTaskForOffLineFinishDTO.class);
		}
		ListResult<PrjTaskForOffLineFinishDTO> resultList = new ListResult<PrjTaskForOffLineFinishDTO>();
		resultList.setObj(dtoList);
		return resultList;
	}
	
	@Override
	public ListResult<PrjRejectTaskDTO> getRejectTask(Long prjInsId) {
		ListResult<PrjRejectTaskDTO> resultList = new ListResult<PrjRejectTaskDTO>();
		List<PrjRejectTaskDTO> dtoList = new ArrayList<PrjRejectTaskDTO>();
		PrjRejectTaskDTO dto = null;
		PrjTaskRejectMaterialExample rexample = new PrjTaskRejectMaterialExample();
		rexample.createCriteria().andPrjInstIdEqualTo(prjInsId);
		rexample.setOrderByClause("IS_FETCHED");
		List<PrjTaskRejectMaterial> rList=  prjTaskRejectMaterialMapper.selectByExample(rexample);
		if (rList != null && rList.size() > 0) {
			for (PrjTaskRejectMaterial r : rList) {
				
				// 已领取退件的事项
				if ("1".equals(r.getIsFetched())) {
					PrjTask record = new PrjTask();
					record.setId(r.getPrjTaskInstId());
					PrjTask npt = prjTaskMapper.selectOneByEntitySelective(record);
					PrjTaskDefine prjTaskDefine = prjTaskDefineMapper.selectByPrimaryKey(npt.getTaskId());
					
					dto = new PrjRejectTaskDTO();
					dto.setTaskName(prjTaskDefine.getTaskName());
					BeanCopy.copyProperties(r, dto);
					dtoList.add(dto);
				} else {
					// 未领取退件的事项
					PrjTaskExample example = new PrjTaskExample();
					PrjTaskExample.Criteria criteria = example.createCriteria();
					criteria.andPrjIdEqualTo(prjInsId);
					criteria.andIdEqualTo(r.getPrjTaskInstId());
					criteria.andTaskStatusEqualTo("6");
					List<PrjTask> tList = prjTaskMapper.selectByExample(example);
					if (tList != null && tList.size() > 0) {
						PrjTaskDefine prjTaskDefine = prjTaskDefineMapper.selectByPrimaryKey(tList.get(0).getTaskId());
						dto = new PrjRejectTaskDTO();
						dto.setId(r.getId());
						dto.setTaskName(prjTaskDefine.getTaskName());
						dto.setIsFetched("0");
						dtoList.add(dto);
					}
				}
				
				
			}
		}
		 
		resultList.setObj(dtoList);
		return resultList;
	}

	@Override
	@Transactional(readOnly = false)
	public CommonResult saveCert(PrjCertGetDTO dto) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PrjTask prj = null;
		for (long id : dto.getPrjTaskInstIds()) {
			prj = new PrjTask();
			prj.setId(id);
			prj.setIsFetched("1"); // 已领取批文
			prj.setFetchMan(dto.getFetchMan());
			prj.setFetchContactInfo(dto.getFetchContactInfo());
			prj.setFetchDesc(dto.getFetchDesc());
			try {
				prj.setFetchTime(sdf.parse(dto.getFetchTimeStr() + " 09:00:00"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			prjTaskMapper.updateByPrimaryKeySelective(prj);
			//领证登记后的上报
			reportPushService.sbLzdjDataMethod(prj);
		}
		// 判断当前项目所有事项的批文是否全部领取
		PrjTaskExample example = new PrjTaskExample();
		PrjTaskExample.Criteria criteria = example.createCriteria();
		criteria.andPrjIdEqualTo(dto.getPrjId());
		criteria.andIsWithCertEqualTo("1");
		List<PrjTask> list = prjTaskMapper.selectByExample(example);
		if (list != null) {
			boolean isAllFetch = true;
			for (PrjTask p : list) {
				if (!"1".equals(p.getIsFetched()) && StringUtils.isNotBlank(p.getAuditAttachAddr())) {
					isAllFetch = false;
					break;
				}
			}
			if (isAllFetch) {
				// 更新项目为不需要领取证件
				updatePrjInstanceIsWithCert(dto.getPrjId(), "0");
			}
		}
		return new CommonResult();
	}
	
	
	@Override
	@Transactional(readOnly = false)
	public CommonResult saveReject(PrjRejectTaskDTO dto) {
		for (long id : dto.getPrjRejectInstIds()) {
			PrjTaskRejectMaterial rej = new PrjTaskRejectMaterial();
			BeanCopy.copyProperties(dto, rej);
			rej.setId(id);
			rej.setUpdateTime(new Date());
			rej.setIsFetched("1");
			prjTaskRejectMaterialMapper.updateByPrimaryKeySelective(rej);
		}
		return new CommonResult();
		
	}

	/**
	 * 更新项目为是否需要领取证件
	 *
	 * @param prjId
	 * @param isWithCert
	 *            0：否，1：是
	 */
	@Transactional(readOnly = false)
	private void updatePrjInstanceIsWithCert(long prjId, String isWithCert) {
		PrjInstance prjIns = new PrjInstance();
		prjIns.setId(prjId);
		prjIns.setIsWithCert(isWithCert);
		prjInstanceMapper.updateByPrimaryKeySelective(prjIns);
	}
	
	/**
	 * 查询依赖事项办结信息
	 */
	@Override
	public ListResult<PrjTaskDependencyListDTO> findPrjTaskDependency(Long taskId, Long prjInsId) {
		ListResult<PrjTaskDependencyListDTO> resultList = new ListResult<PrjTaskDependencyListDTO>();
		PrjTaskDependencyExample example = new PrjTaskDependencyExample();
		PrjTaskDependencyExample.Criteria criteria = example.createCriteria();
		criteria.andTaskIdEqualTo(taskId);
		List<PrjTaskDependency> depList = prjTaskDependencyMapper.selectByExample(example);
		if (depList != null && depList.size() > 0) {
			List<PrjTaskDependencyListVO> voList = null;
			List<PrjTaskDependencyListDTO> dtoList = new ArrayList<PrjTaskDependencyListDTO>();
			for (PrjTaskDependency dep : depList) {
				voList = prjTaskAdapterMapper.findTaskDependencyList(prjInsId, dep.getDepTaskId());
				BeanCopy.copyPropertiesForListWithBlank2Null(voList, dtoList, PrjTaskDependencyListDTO.class);
			}
			resultList.setObj(dtoList);
		}
		
		return resultList;
	}

	@Override
	public Result<CertLpcodeDTO> findCertLpcodeInfo(long prjTaskInstId, String status) {
		Result<CertLpcodeDTO> result = new Result<CertLpcodeDTO>();
		CertLpcodeVO vo = prjTaskAdapterMapper.findCertLpcodeInfo(prjTaskInstId, status);
		CertLpcodeDTO dto = new CertLpcodeDTO();
		BeanCopy.copyProperties(vo, dto);
		result.setObj(dto);
		return result;
	}

	/**
	 * 线下办结
	 */
	@Override
	@Transactional(readOnly = false)
	public CommonResult saveTaskFinishOffLine(PrjTaskForOffLineFinishDTO dto) {
		//String userId = UserUtils.getPrincipal().getId();
		PrjTask task = null;
		for (int i = 0; i < dto.getPrjTaskInstIds().length; i++) {
			Long taskId = dto.getPrjTaskInstIds()[i];
			String finshiType = dto.getFinishType()[i];
			if ("1".equals(finshiType)) {
				// 线下已办结
				task = new PrjTask();
				task.setId(taskId);
				task.setTaskStatus(TaskConstants.TASK_STATUS_OFFINISH);// 更新项目状态为线下已办结
				Date currDate = new Date();
				
				task.setTaskRealEndtime(currDate);// 更新事项实际结束时间
				task.setUpdateTime(currDate);
				task.setTaskDuration(0); // 更新事项总耗时
				
				prjTaskMapper.updateByPrimaryKeySelective(task);
				
				// 增加事项流转记录
				PrjTaskTransferDetail entity = new PrjTaskTransferDetail();
				entity.setPrjTaskInstId(task.getId());
				entity.setReceiver(UserUtils.getUser().getId());
				entity.setDescription("线下办结");
				entity.setUpdateTime(new Date());
				entity.setUpdator(entity.getReceiver());
				entity.setIsFinished(TaskConstants.TASK_STATUS_OFFINISH); // 线下办结
				entity.setCreator(entity.getReceiver());
				entity.setCreatTime(entity.getCreatTime());
				prjTaskTransferDetailMapper.insert(entity);
			} else if ("2".equals(finshiType)) {
				// 免办
				task = new PrjTask();
				task.setId(taskId);
				task.setTaskStatus(TaskConstants.TASK_STATUS_NONEED);// 更新项目状态为免办
				Date currDate = new Date();
				
				task.setTaskRealEndtime(currDate);// 更新事项实际结束时间
				task.setUpdateTime(currDate);
				task.setTaskDuration(0); // 更新事项总耗时
				
				prjTaskMapper.updateByPrimaryKeySelective(task);
				
				// 增加事项流转记录
				PrjTaskTransferDetail entity = new PrjTaskTransferDetail();
				entity.setPrjTaskInstId(task.getId());
				entity.setReceiver(UserUtils.getUser().getId());
				entity.setDescription("免办");
				entity.setUpdateTime(new Date());
				entity.setUpdator(entity.getReceiver());
				entity.setIsFinished(TaskConstants.TASK_STATUS_NONEED); // 免办
				entity.setCreator(entity.getReceiver());
				entity.setCreatTime(entity.getCreatTime());
				prjTaskTransferDetailMapper.insert(entity);
			}
			
			// 事项办结处理
			task = prjTaskMapper.selectByPrimaryKey(taskId);
			taskFinishDeal(task);

			/*PrjStage prjStage = prjStageMapper.selectByPrimaryKey(task.getPrjStageInstId());
			// 材料置为已提供 :1删除所有材料,2得到事项下的材料配置
			PrjStageMaterialExample examplePrj = new PrjStageMaterialExample();
			examplePrj.createCriteria().andTaskIdEqualTo(task.getTaskId()).andPrjIdEqualTo(task.getPrjId());
			prjStageMaterialMapper.deleteByExample(examplePrj);
			PrjTaskMaterialDefExample exampleTaskMatDef = new PrjTaskMaterialDefExample();
			PrjTaskMaterialDefExample.Criteria taskMatDefCriteria = exampleTaskMatDef.createCriteria();
			taskMatDefCriteria.andTaskIdEqualTo(task.getTaskId());
			List<PrjTaskMaterialDef> prjTaskMaterList = prjTaskMaterialDefMapper.selectByExample(exampleTaskMatDef);
			for(PrjTaskMaterialDef prjTaskMaterialDef : prjTaskMaterList){
				PrjStageMaterial pms = new PrjStageMaterial();
//				BeanCopy.copyProperties(prjTaskMaterialDef, pms, PrjStageMaterial.class);
				pms.setId(null);
				pms.setPrjId(task.getPrjId());
				pms.setPrjStageInstId(task.getPrjStageInstId());
				pms.setStageId(prjStage.getStageId());
				pms.setOriginalNum(prjTaskMaterialDef.getOriginalNum());
				pms.setCopyNum(prjTaskMaterialDef.getCopyNum());
				pms.setIsComplete("1");
				pms.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
				pms.setIsMandatory(prjTaskMaterialDef.getIsMandatory());
				pms.setMaterialId(prjTaskMaterialDef.getMaterialId());
				pms.setTaskId(task.getTaskId());
				List<PrjStageMaterial> pmsList = prjStageMaterialMapper.selectByEntitySelective(pms);
				if(pmsList.size() == 0){
					pms.setCreatTime(currDate);
					pms.setUpdateTime(currDate);
					pms.setUpdator(userId);
					pms.setCreator(userId);
					prjStageMaterialMapper.insert(pms);
					//插入不叠加原件的材料记录
					insertUnCumulationOriginal(pms);
				}

			}*/

		}
		
		return new CommonResult();
	}

	/**
	 * 通过项目ID,材料ID,插入不叠加原件的材料记录
	 * @param psm
	 */
	private void insertUnCumulationOriginal(PrjStageMaterial psm ) {
		Date now = new Date();
		String userId = UserUtils.getPrincipal().getId();
		Long prjId = psm.getPrjId();
		//1.通过材料ID与事项ID,得到当前阶段的事项ID列表;
		PrjStageExample prjStageExample = new PrjStageExample();
		prjStageExample.createCriteria().andStageIdEqualTo(psm.getStageId()).andPrjIdEqualTo(prjId);
		List<PrjStage> prjStageList = prjStageMapper.selectByExample(prjStageExample);
		Long prjStageInstId = prjStageList.get(0).getId();
		//1.1 通过材料ID查找是否有其他事项用到此材料,得到材料对应的事项集合
//		PrjTaskDefineExample prjTaskDefineExample = new PrjTaskDefineExample();
//		prjTaskDefineExample.createCriteria().andStageIdEqualTo(psm.getStageId()).andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
//		List<PrjTaskDefine> prjTaskDefines = prjTaskDefineMapper.selectByExample(prjTaskDefineExample);
		PrjTaskExample prjTaskExample = new PrjTaskExample();
		prjTaskExample.createCriteria().andPrjIdEqualTo(prjId).andPrjStageInstIdEqualTo(prjStageInstId).andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
		List<PrjTask> prjTaskList = prjTaskMapper.selectByExample(prjTaskExample);
		List<Long> taskIds = new ArrayList<Long>();
		for(PrjTask prjTask : prjTaskList){
			taskIds.add(prjTask.getTaskId());
		}
		PrjTaskMaterialDefExample example = new PrjTaskMaterialDefExample();
		example.createCriteria().andMaterialIdEqualTo(psm.getMaterialId()).andTaskIdIn(taskIds).andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
		List<PrjTaskMaterialDef> prjTaskMaterialDefList = prjTaskMaterialDefMapper.selectByExample(example);

		//通过材料ID得到材料的详细信息,从中得到材料是否可以重复
//		List<Long> materialIds = new ArrayList<Long>();
//		for(PrjTaskMaterialDef prjTaskMaterialDef : prjTaskMaterialDefList){
//			materialIds.add(prjTaskMaterialDef.getMaterialId());
//		}

//		DimMaterialExample materialExample = new DimMaterialExample();
//		materialExample.createCriteria().andIdIn(materialIds).andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
//		List<DimMaterial> materialList = dimMaterialMapper.selectByExample(materialExample);
//		Map<Long,String> materialCumuOrigMap = new HashMap<Long,String>();
//		for(DimMaterial dimMaterial : materialList){
//			materialCumuOrigMap.put(dimMaterial.getId(),dimMaterial.getIsOriginalCumulation());
//		}
		//2 遍历事项集合,如果事项ID和当前办结的事项ID相同则不插入数据记录,否则插入新的材料数据
		for(PrjTaskMaterialDef prjTaskMaterialDef : prjTaskMaterialDefList){
//			if(prjTaskMaterialDef.getTaskId() != psm.getTaskId() && (DicConstants.IS_ORIGINAL_CUMULATION).equals(materialCumuOrigMap.get(prjTaskMaterialDef.getMaterialId()))){
			if(prjTaskMaterialDef.getTaskId() != psm.getTaskId()){
				PrjStageMaterial newOtherPms = new PrjStageMaterial();
//				BeanCopy.copyProperties(prjTaskMaterialDef, newOtherPms, PrjStageMaterial.class);
				newOtherPms.setId(null);
				newOtherPms.setPrjId(psm.getPrjId());
				newOtherPms.setPrjStageInstId(psm.getPrjStageInstId());
				newOtherPms.setIsComplete("1");
				newOtherPms.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
				newOtherPms.setTaskId(prjTaskMaterialDef.getTaskId());
				newOtherPms.setStageId(psm.getStageId());
				newOtherPms.setOriginalNum(prjTaskMaterialDef.getOriginalNum());
				newOtherPms.setCopyNum(prjTaskMaterialDef.getCopyNum());
				newOtherPms.setIsMandatory(prjTaskMaterialDef.getIsMandatory());
				newOtherPms.setMaterialId(prjTaskMaterialDef.getMaterialId());
				List<PrjStageMaterial> newOtherPmsList = prjStageMaterialMapper.selectByEntitySelective(newOtherPms);
				if(newOtherPmsList.size() == 0){
					newOtherPms.setCreatTime(now);
					newOtherPms.setUpdateTime(now);
					newOtherPms.setUpdator(userId);
					newOtherPms.setCreator(userId);
					prjStageMaterialMapper.insert(newOtherPms);
				}
			}
		}
	}


	/**
	 *
	 * @param dto
	 * @return
	 */
	private CommonResult saveTaskFinsh(PrjTaskForOffLineFinishDTO dto) {
		PrjTask task = null;
		for (long id : dto.getPrjTaskInstIds()) {
			task = new PrjTask();
			task.setId(id);
			task.setTaskStatus(TaskConstants.TASK_STATUS_OFFINISH);// 更新项目状态为线下已办结
			Date currDate = new Date();

			task.setTaskRealEndtime(currDate);// 更新事项实际结束时间
			task.setUpdateTime(currDate);
			task.setTaskDuration(0); // 更新事项总耗时

			prjTaskMapper.updateByPrimaryKeySelective(task);

			// 事项办结处理
			task = prjTaskMapper.selectByPrimaryKey(id);
			taskFinishDeal(task);

			// 材料置为已提供
			PrjStageMaterialExample psmExample = new PrjStageMaterialExample();
			PrjStageMaterialExample.Criteria criteria = psmExample.createCriteria();
			criteria.andPrjIdEqualTo(task.getPrjId());// 项目ID
			criteria.andPrjStageInstIdEqualTo(task.getPrjStageInstId());// 项目阶段实例ID
			criteria.andTaskIdEqualTo(task.getTaskId());
			List<PrjStageMaterial> pmsList = prjStageMaterialMapper.selectByExample(psmExample);
			if (pmsList != null && pmsList.size() > 0) {
				for (PrjStageMaterial pms : pmsList) {
					pms.setIsComplete("1");
					// 材料份数
					PrjTaskMaterialDef mdef = new PrjTaskMaterialDef();
					mdef.setTaskId(pms.getTaskId());
					mdef.setMaterialId(pms.getMaterialId());
					mdef.setIsDelete("0");
					PrjTaskMaterialDef matdef = prjTaskMaterialDefMapper.selectOneByEntitySelective(mdef);
					if (matdef != null) {
						pms.setOriginalNum(matdef.getOriginalNum());
						pms.setCopyNum(matdef.getCopyNum());
					}

					pms.setUpdateTime(currDate);
					prjStageMaterialMapper.updateByPrimaryKeySelective(pms);
				}
			}

			// 增加事项流转记录
			PrjTaskTransferDetail entity = new PrjTaskTransferDetail();
			entity.setPrjTaskInstId(task.getId());
			entity.setReceiver(UserUtils.getUser().getId());
			entity.setDescription("线下办结");
			entity.setUpdateTime(new Date());
			entity.setUpdator(entity.getReceiver());
			entity.setIsFinished("7"); // 线下办结
			entity.setCreator(entity.getReceiver());
			entity.setCreatTime(entity.getCreatTime());
			prjTaskTransferDetailMapper.insert(entity);

		}

		return new CommonResult();
	}

	@Override
	public Result<PrjTaskDTO> findPrjTaskByTaskId(Long taskId, Long prjInsId) {
		Result<PrjTaskDTO> result = new Result<>();
		PrjTask record = new PrjTask();
		record.setPrjId(prjInsId);
		record.setTaskId(taskId);
		PrjTask prjTask = prjTaskMapper.selectOneByEntitySelective(record);
		if (prjTask != null) {
			PrjTaskDTO dto = new PrjTaskDTO();
			BeanCopy.copyProperties(prjTask, dto);
			result.setObj(dto);
		}
		return result;
	}
	
	@Override
	public Result<Integer> countPauseTimes(Long prjId) {
		Result<Integer> result = new Result<Integer>();
		int times = prjTaskAdapterMapper.countPauseTimes(prjId);
		result.setObj(times);
		return result;
	}

	@Override
	public ListResult<PrjTaskPauseTimesDTO> findPauseForMater(Long prjId) {
		ListResult<PrjTaskPauseTimesDTO> result = new ListResult<PrjTaskPauseTimesDTO>();
		List<PrjTaskPauseTimesDTO> dtoList = new ArrayList<PrjTaskPauseTimesDTO>();
		List<PrjTaskPauseTimesVO> voList = prjTaskAdapterMapper.findPauseForMater(prjId);
		if (voList != null && voList.size() > 0) {
			BeanCopy.copyPropertiesForListWithBlank2Null(voList, dtoList, PrjTaskPauseTimesDTO.class);
			result.setObj(dtoList);
		}
		return result;
	}

	@Override
	public ListResult<PrjTaskForOffLineFinishDTO> findTaskForUnPass(Long prjInsId, Long stageInsId) {
		List<PrjTaskForOffLineFinishVO> voList = prjTaskAdapterMapper.findTaskForUnPass(prjInsId, stageInsId);
		List<PrjTaskForOffLineFinishDTO> dtoList = new ArrayList<PrjTaskForOffLineFinishDTO>();
		if (voList != null && voList.size() > 0) {
			BeanCopy.copyPropertiesForListWithBlank2Null(voList, dtoList, PrjTaskForOffLineFinishDTO.class);
		}
		ListResult<PrjTaskForOffLineFinishDTO> resultList = new ListResult<PrjTaskForOffLineFinishDTO>();
		resultList.setObj(dtoList);
		return resultList;
	}

	@Override
	@Transactional(readOnly = false)
	public CommonResult updateTaskState(PrjTaskForOffLineFinishDTO dto) {
		Date now = new Date();
		PrjTask task = null;
		for (long id : dto.getPrjTaskInstIds()) {
			task = new PrjTask();
			task.setId(id);
			task.setUpdateTime(now);
			task.setTaskStatus(TaskConstants.TASK_STATUS_DRAFT);// 更新项目状态为线下已办结
			prjTaskMapper.updateByPrimaryKeySelective(task);
			// 增加事项流转记录
			PrjTaskTransferDetail entity = new PrjTaskTransferDetail();
			entity.setPrjTaskInstId(task.getId());
			entity.setReceiver(UserUtils.getUser().getId());
			entity.setDescription("不通过事项激活");
			entity.setUpdateTime(now);
			entity.setUpdator(entity.getReceiver());
			entity.setIsFinished("0"); // 不通过事项激活
			entity.setCreator(entity.getReceiver());
			entity.setCreatTime(now);
			prjTaskTransferDetailMapper.insert(entity);
		}
		return new CommonResult();
	}

	/**
	 * 处理项目中已经保存的材料
	 */
	@Override
	public void conductMaterial(ProjectChangeForm project) {
		Map<Long, PrjMaterialVO> map = project.getMaterialDefMap();
		PrjStageMaterial record = new PrjStageMaterial();
		record.setPrjId(project.getPrjInstanceVo().getId());
		List<PrjStageMaterial> list = prjStageMaterialMapper.selectByEntitySelective(record);
		Set<Long> keySet = map.keySet();
		for (Long materialId : keySet) {
			for (PrjStageMaterial material : list) {
				if(materialId.equals(material.getMaterialId())){
					map.get(materialId).setMaterialAddr(material.getMaterialAddr());
					break;
				}
			}
		}
	}
	
	/**
	 * 将网上办事项目中的项目材料关系为新项目提供一份
	 */
	@Override
	@Transactional(readOnly = false)
	public void pretrialWsbsMaterial(Long wsbsId, Long id) {
		PrjStageMaterial record = new PrjStageMaterial();
		record.setPrjId(wsbsId);
		List<PrjStageMaterial> list = prjStageMaterialMapper.selectByEntitySelective(record);
		PrjInstance wsbsInstance = prjInstanceMapper.selectByPrimaryKey(wsbsId);
		PrjInstance blspInstance = new PrjInstance();
		blspInstance.setPrjCode(wsbsInstance.getPrjCode());
		blspInstance.setChannel("0");
		blspInstance = prjInstanceMapper.selectOneByEntitySelective(blspInstance) ;

		if(list != null && list.size() > 0){
			for (PrjStageMaterial prjStageMaterial : list) {

				//选出此材料此阶段的材料数据配置,然后循环list 去找出
				//得出ID下的所有材料事项配置,从而得到Task的配置列表
				PrjTaskMaterialDefExample taskMaterDefExample = new PrjTaskMaterialDefExample();
				taskMaterDefExample.createCriteria().andMaterialIdEqualTo(prjStageMaterial.getMaterialId());
				List<PrjTaskMaterialDef> taskMaterialDefList = prjTaskMaterialDefMapper.selectByExample(taskMaterDefExample);

				//得到当前阶段的事项
				PrjTaskExample example = new PrjTaskExample();
				example.createCriteria().andPrjIdEqualTo(blspInstance.getId());
				List<PrjTask> taskList = prjTaskMapper.selectByExample(example);
				Map<Long,PrjTask> taskMap = new HashMap<>();
				for(PrjTask task : taskList){
					taskMap.put(task.getTaskId(),task);
				}
				for(PrjTaskMaterialDef def : taskMaterialDefList){
					//如果此材料配置不属于当前阶段的事项,则不进行比较存储
					if(taskMap.get(def.getTaskId()) != null){
						PrjStage state = new PrjStage();
						state.setStageId(blspInstance.getStageId());
						state.setPrjId(blspInstance.getId());
						state = prjStageMapper.selectOneByEntitySelective(state);
						PrjStageMaterial materialBlsp = new PrjStageMaterial();
						materialBlsp.setId(null);
						materialBlsp.setPrjId(blspInstance.getId());
						materialBlsp.setStageId(blspInstance.getStageId());
						materialBlsp.setPrjStageInstId(state.getId());
						materialBlsp.setTaskId(def.getTaskId());
						materialBlsp.setMaterialId(def.getMaterialId());
						materialBlsp.setMaterialAddr(prjStageMaterial.getMaterialAddr());

						materialBlsp.setOriginalNum(def.getOriginalNum());
						materialBlsp.setCopyNum(def.getCopyNum());
						materialBlsp.setIsMandatory(def.getIsMandatory());
						materialBlsp.setIsDelete("0");
						materialBlsp.setIsComplete("1");
						materialBlsp.setCreatTime(new Date());
						materialBlsp.setCreator(UserUtils.getUser().getId());
						materialBlsp.setUpdateTime(new Date());
						materialBlsp.setUpdator(UserUtils.getUser().getId());
						prjStageMaterialMapper.insert(materialBlsp);
					}
				}
			}
		}
	}

	/**
	 * 根据当前用户ID获取用户待办列表
	 */
	@Override
	public List<PrjTaskTodoListDTO> backlogList(String id) {
		PrjTaskTodoListVO vo = new PrjTaskTodoListVO();
		vo.setUserId(id);
		List<PrjTaskTodoListVO> list = prjTaskAdapterMapper.selectBacklogListByEntity(vo);
		for (PrjTaskTodoListVO prjTaskTodoListVO : list) {
			if(prjTaskTodoListVO.getTaskStatus().equals("2")){
				PrjTaskPauseDetailExample example = new PrjTaskPauseDetailExample();
				example.setOrderByClause("PAUSE_START_TIME");
				example.createCriteria().andPrjTaskInstIdEqualTo(Long.parseLong(prjTaskTodoListVO.getPrjTaskInstId()));
				List<PrjTaskPauseDetail> prjTaskPauseDetailList = prjTaskPauseDetailMapper.selectByExample(example);
				if(prjTaskPauseDetailList != null){
					PrjTaskPauseDetail prjTaskPauseDetail = prjTaskPauseDetailList.get(prjTaskPauseDetailList.size()-1);
					prjTaskTodoListVO.setTaskPauseDesc(prjTaskPauseDetail.getPauseDesc());
					prjTaskTodoListVO.setTaskPauseType(prjTaskPauseDetail.getPauseType());
					prjTaskTodoListVO.setTaskPauseStartTime(prjTaskPauseDetail.getPauseStartTime());
				}
			}
		}
		List<PrjTaskTodoListDTO> dtoList = new ArrayList<PrjTaskTodoListDTO>();
		BeanCopy.copyPropertiesForListWithBlank2Null(list, dtoList, PrjTaskTodoListDTO.class);
		// 计算剩余办结工作日
		for (PrjTaskTodoListDTO p : dtoList) {
			int datePer = dimHolidayService.calDatePeriod(new Date(), p.getTaskEndTime(), p.getTimeType());
			p.setTaskRemainTime(datePer);
		}
		return dtoList;
	}

	@Override
	@Transactional(readOnly = false)
	public void creatProcessedTask(PrjInstanceVo prjVO, List<StageTaskProcessVO> stageTaskProcessList) {
		String userId = UserUtils.getUser().getId();
		Date now = new Date();
		for(StageTaskProcessVO stageVO : stageTaskProcessList){
			// 创建阶段实例
			PrjStage state = new PrjStage();
			state.setPrjId(prjVO.getId());
			state.setStageCode(FormUtils.getFormNo());// 受理编号
			state.setStageId(stageVO.getStageId());
			state.setCreator(userId);
			state.setCreatTime(now);
			state.setUpdateTime(now);
			state.setUpdator(userId);
			state.setStageStatus("4");// 阶段办结
			state.setStageStartTime(now);
			state.setStageEndTime(now);
			state.setAcceptId(prjVO.getAcceptId());
			prjStageMapper.insert(state);

			for(TaskProcessVO taskProVO : stageVO.getTaskProcList()){
				// 创建事项实例
				PrjTask task = new PrjTask();
				task.setPrjStageInstId(state.getId());// 阶段实例id
				task.setPrjId(prjVO.getId());
				task.setCurrUser(userId);
				task.setTaskId(taskProVO.getTaskId());
				task.setTaskStatus(taskProVO.getStatus());// 处理状态
				task.setIsWithCert("1");
				task.setIsFetched("0");
				task.setAcceptId(prjVO.getAcceptId());
				task.setCreator(userId);
				task.setCreatTime(now);
				task.setUpdateTime(now);
				task.setUpdator(userId);
				prjTaskMapper.insert(task);
			}
		}
	}


	/**
	 * 通过处理的事项信息,推送廉情预警的信息补录（把数据推送到网厅,在网厅的服务里处理）
	 * @param prjTask
	 */
	@Override
	public void postLqyjApply(PrjTask prjTask) {
		try{
			//通过 taskId 得到 task的配置信息
			PrjTaskDefine taskDefin = prjTaskDefineMapper.selectByPrimaryKey(prjTask.getTaskId());
			LqyjApplyDTO lqyjApplyDTO = new LqyjApplyDTO();
			PrjInstance project = prjInstanceMapper.selectByPrimaryKey(prjTask.getPrjId());
			Office spdwOffice = UserUtils.getOffice(taskDefin.getDeptId());
			lqyjApplyDTO.setSpxmmc(project.getPrjName());//项目名称
			lqyjApplyDTO.setJsdw(project.getCompany()); //建设单位
			lqyjApplyDTO.setZzjgdm(project.getCompanyCode()); //组织机构代码
			lqyjApplyDTO.setSlsj(prjTask.getTaskStartTime()); //受理时间
			lqyjApplyDTO.setPfsj(prjTask.getTaskEndTime()); //审批时间
			lqyjApplyDTO.setPfbh(""); //批复编号
			lqyjApplyDTO.setSpdw(spdwOffice == null ? "" : spdwOffice.getName()); //审批单位
			lqyjApplyDTO.setTaskInstId(prjTask.getId().toString());//事项的实例ID
			lqyjApplyDTO.setTaskId(taskDefin.getId().toString());//事项配置 Id
			lqyjApplyDTO.setTaskCode(taskDefin.getTaskCode());//事项编号
			lqyjApplyDTO.setTaskName(taskDefin.getTaskName());//事项名称
			lqyjApplyDTO.setSbSeq(prjTask.getId().toString());//申报流水号(并联审批项目对应的事项ID)
			lqyjApplyDTO.setSbName(project.getAgentName());//申请人(从项目基本信息中可以获取受委托人);
			lqyjApplyDTO.setSbType("企业");//并联审批推送的类型都是企业（个人,企业）
			lqyjApplyDTO.setXmje(project.getInvestEstimate()); //项目金额
			savePushLqyjData(lqyjApplyDTO);//廉情预警数据保存到推送日志表中
			//TODO 调用提到外层服务，做成任务调度

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 把廉情预警数据先记入到数据库中
	 * @param lqyjApplyDTO
	 */
	private void savePushLqyjData(LqyjApplyDTO lqyjApplyDTO){
		ServiceSbQueue serviceSbQueue = new ServiceSbQueue();
		Date now = new Date();
		String bodyJson = JSON.toJSONString(lqyjApplyDTO);
		serviceSbQueue.setCreatTime(now);
		serviceSbQueue.setCreator(UserUtils.getUser().getId());
		serviceSbQueue.setSbStatus(lqyjApplyDTO.getTaskName());
		serviceSbQueue.setSbType(DicConstants.SB_TYPE_LQYJ);//1为上报统一平台，2为共享平台，3为廉情预警
		serviceSbQueue.setSbXml(bodyJson);
		serviceSbQueue.setSbDate(now);
		serviceSbQueue.setSbIsjh(DicConstants.SB_ISJH_NOUP);//是否已经上报；1是0否
		serviceSbQueue.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
		serviceSbQueue.setSbSort(0);
		serviceSbQueue.setTbcxSort(0);
		serviceSbQueue.setSbTimes(0);
		serviceSbQueueMapper.insert(serviceSbQueue);
	}

}