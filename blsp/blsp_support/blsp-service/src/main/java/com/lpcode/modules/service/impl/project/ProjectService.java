package com.lpcode.modules.service.impl.project;

import com.framework.core.base.page.Page;
import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.entity.User;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.DicConstants;
import com.lpcode.modules.blsp.entity.*;
import com.lpcode.modules.blsp.entity.PrjInstanceExample.Criteria;
import com.lpcode.modules.blsp.mapper.*;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.dto.project.PrjTaskDefineDTO;
import com.lpcode.modules.dto.project.PrjTaskMaterialDefDTO;
import com.lpcode.modules.service.dimension.holiday.DimHolidayService;
import com.lpcode.modules.service.form.IFactoryFormService;
import com.lpcode.modules.service.impl.project.util.FormUtils;
import com.lpcode.modules.service.impl.project.util.ProjectStepUtil;
import com.lpcode.modules.service.impl.project.util.ProjectUtil;
import com.lpcode.modules.service.material.inf.IMaterialService;
import com.lpcode.modules.service.message.IMessageSMS;
import com.lpcode.modules.service.project.constant.TaskConstants;
import com.lpcode.modules.service.project.dto.PrjTaskTransferDetailDTO;
import com.lpcode.modules.service.project.dto.Project;
import com.lpcode.modules.service.project.dto.ProjectChangeForm;
import com.lpcode.modules.service.project.dto.StopFormDTO;
import com.lpcode.modules.service.project.dto.pinstance.*;
import com.lpcode.modules.service.project.inf.*;
import com.lpcode.modules.service.report.ReportPushService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.Map.Entry;

@Service
@Transactional(readOnly = false)
public class ProjectService implements ProjectServiceInf {
	@Autowired
	private PrjInstanceMapper prjInstanceMapper;
	@Autowired
	private FormRfBjshMapper formRfBjshMapper;
	@Autowired
	private FormRfJsxmqkbMapper formRfJsxmqkbMapper;
	@Autowired
	private FormRfYdjsBjspMapper formRfYdjsBjspMapper;
	@Autowired
	private FormRfYdjsJgsbMapper formRfYdjsJgsbMapper;
	@Autowired
	private PrjStageMaterialMapper PrjStageMaterialMapper;
	@Autowired
	private PrjTaskDefineMapper prjTaskDefineMapper;
	@Autowired
	private PrjTaskMapper prjTaskMapper;
	@Autowired
	private PrjStageMapper prjStageMapper;
	@Autowired
	private PrjTaskTransferDetailService prjTaskTransferDetailService;
	@Autowired
	private DimHolidayService dimHolidayService;
	@Autowired
	private IMessageSMS iMessageSMS;
	@Autowired
	private PrjBusinessAcceptMapper PrjBusinessAcceptMapper;
	@Autowired
	private PrjTaskDependencyMapper prjTaskDependencyMapper;
	@Autowired
	private PrjTaskMaterialDefService prjTaskMaterialDefService;
	@Autowired
	private DimMaterialMapper materialDao;
	@Autowired
	private PrjBuildCompanyMapper prjBuildCompanyMapper;
	@Autowired
	private PrjTaskDefineService prjTaskDefineService;
	@Autowired 
	private IMaterialService materialService;
	@Autowired
	private PrjTaskService prjTaskService;
	@Autowired
	private PrjTaskMaterialDefMapper prjTaskMaterialDefMapper;
	@Autowired
	private ReportPushService reportPushService;
	
	/**
	 * 获取  表单事项  中间类对应mapper实例对象
	 */
	@Autowired
	private PrjFormTaskRealMapper prjFormTaskRealMapper;
	
	/**
	 * 获取 表单类 对应mapper实例对象
	 */
	@Autowired
	private FormDefineMapper formDefineMapper;
	
	/**
	 * 获取 表单 接口对象
	 */
	@Autowired
	private IFactoryFormService factoryFormService;

	
	@Override
	public void saveProject(Project project) {
		// 受理阶段实例
		updateAccept(project);
		List<PrjTaskVo> listPro = project.getPrjTaskVoList();
		PrjBusinessAcceptVo accept = project.getPrjBusinessAcceptVo();
		validate(project);
		PrjInstance prjvo = new PrjInstance();
		BeanCopy.copyProperties(project.getPrjInstanceVo(), prjvo);
		prjvo.setIsDelete("0");
		// 阶段
		Long stageId = prjvo.getStageId();
		if (stageId == null) {
			PrjStageDefineVo re = ProjectUtil.getFirstStage("1");
			stageId = re.getId();
		}
		// 保存项目
		prjvo.setStageId(stageId);
		prjvo.setCreator(UserUtils.getUser().getId());
		prjvo.setCreatTime(new Date());
		prjvo.setUpdateTime(new Date());
		prjvo.setUpdator(UserUtils.getUser().getId());
		prjvo.setIsWithCert("0");
		prjvo.setIsPrjComplete("0");
		prjvo.setIsStageComplete("0");
		prjvo.setAcceptId(accept.getId());
		// saveCode(project);
		prjvo.setPrjCode(project.getPrjInstanceVo().getPrjCode());
		try {
			if (listPro != null) {
				prjvo.setRealStartTime(new Date());
			}
			prjInstanceMapper.insert(prjvo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//20160421 增加建设单位的基础信息存储
		saveOrUpdateBuild(prjvo);

		BeanCopy.copyProperties(prjvo, project.getPrjInstanceVo());
		// 保存业务表数据
		if (project.getFormRfYdjsBjspVo() != null) {// 人防工程易地建设报建审批表(表1)
			FormRfYdjsBjsp record = new FormRfYdjsBjsp();
			record.setPrjId(prjvo.getId());
			record.setCreator(UserUtils.getUser().getId());
			record.setCreatTime(new Date());
			record.setUpdateTime(new Date());
			record.setUpdator(UserUtils.getUser().getId());
			BeanCopy.copyProperties(project.getFormRfYdjsBjspVo(), record);
			formRfYdjsBjspMapper.insert(record);
		}

		if (project.getFormRfYdjsJgsbVo() != null) {// 人防易地建设竣工申报表(表2)
			FormRfYdjsJgsb record = new FormRfYdjsJgsb();
			BeanCopy.copyProperties(project.getFormRfYdjsJgsbVo(), record);
			record.setPrjId(prjvo.getId());
			formRfYdjsJgsbMapper.insert(record);
			FormRfJsxmqkb ree = new FormRfJsxmqkb();
			BeanCopy.copyProperties(project.getFormRfJsxmqkbVo(), ree);
			formRfJsxmqkbMapper.insert(ree);
		}

		if (project.getFormRfBjshVo() != null) {// 人防工程易地建设报建审核表(表3)
			FormRfBjsh record = new FormRfBjsh();
			record.setPrjId(prjvo.getId());
			record.setCreator(UserUtils.getUser().getId());
			record.setCreatTime(new Date());
			record.setUpdateTime(new Date());
			record.setUpdator(UserUtils.getUser().getId());
			BeanCopy.copyProperties(project.getFormRfBjshVo(), record);
			formRfBjshMapper.insert(record);
		}

		// 保存项目阶段实例
		PrjStage state = new PrjStage();
		state.setPrjId(prjvo.getId());
		state.setStageCode(FormUtils.getFormNo());// 受理编号
		state.setStageId(stageId);
		state.setCreator(UserUtils.getUser().getId());
		state.setCreatTime(new Date());
		state.setUpdateTime(new Date());
		state.setUpdator(UserUtils.getUser().getId());
		if (listPro == null) {
			state.setStageStatus("0");// 资料待补齐
		} else {
			state.setStageStatus("1");// 待审批
			state.setStageStartTime(new Date());
		}
		state.setAcceptId(accept.getId());
		prjStageMapper.insert(state);
		PrjStageVo vo = new PrjStageVo();
		BeanCopy.copyProperties(state, vo);
		project.setPrjStageVo(vo);

		// 保存材料
		List<PrjStageMaterial> records = new ArrayList<PrjStageMaterial>();
		if (project.getPrjStageMaterialVoList() != null && project.getPrjStageMaterialVoList().size() > 0) {
			BeanCopy.copyPropertiesForList(project.getPrjStageMaterialVoList(), records, PrjStageMaterial.class);
			for (PrjStageMaterial m : records) {
				m.setPrjId(prjvo.getId());
				m.setCreator(UserUtils.getUser().getId());
				m.setCreatTime(new Date());
				m.setUpdateTime(new Date());
				m.setUpdator(UserUtils.getUser().getId());
				m.setPrjStageInstId(state.getId());
				if (m.getIsComplete() == null) {
					m.setIsComplete("0");
				}
				m.setAcceptId(accept.getId());
				PrjStageMaterialMapper.insert(m);
			}
		}

		Map<Long, PrjTaskVo> taskMap = new HashMap<Long, PrjTaskVo>();
		if (listPro != null && listPro.size() > 0) {
			for (PrjTaskVo v : listPro) {
				taskMap.put(v.getTaskId(), v);
			}
		}

		// 保存所有的事项
		List<PrjTaskDefineVo> l = ProjectUtil.getAllTaskByStage(stageId);
		if (l != null && l.size() > 0) {
			for (PrjTaskDefineVo v : l) {
				PrjTask task = new PrjTask();
				BeanCopy.copyProperties(v, task);
				task.setPrjStageInstId(state.getId());// 阶段实例id
				task.setPrjId(Long.parseLong(prjvo.getId().toString()));
				task.setCurrUser(task.getInitUser());
				task.setTaskStatus("0");// 草稿
				task.setIsWithCert(task.getIsWithCert() == null ? "0" : task.getIsWithCert());
				task.setIsFetched("0");
				task.setCreator(UserUtils.getUser().getId());
				task.setCreatTime(new Date());
				task.setUpdateTime(new Date());
				task.setUpdator(UserUtils.getUser().getId());
				task.setTaskId(task.getId());
				prjTaskMapper.insert(task);
				PrjTaskVo tv = taskMap.get(v.getId());
				if (tv != null)
					tv.setId(task.getId());
			}
		}

		if (listPro != null && listPro.size() > 0) {
			for (PrjTaskVo t : listPro) {
				PrjTask task = new PrjTask();
				BeanCopy.copyProperties(t, task);
				// 时间比对
				if (!StringUtils.isBlank(task.getInitUser())) {
					Date start = dimHolidayService.findNextWorkDay();
					task.setTaskStartTime(start);
					List<PrjTaskTimerDefineVo> taskvoList = ProjectUtil.getTaskTimerByTaskId(task.getTaskId().toString());
					PrjTaskTimerDefineVo taskvo = new PrjTaskTimerDefineVo();
					if (taskvoList != null && taskvoList.size() == 1) {
						taskvo = taskvoList.get(0);
					} else if (taskvoList.size() > 1) {
						for (PrjTaskTimerDefineVo v : taskvoList) {
							if (prjvo.getPriceType() != null && v.getPriceType() != null && prjvo.getPriceType().equals(v.getPriceType())) {
								taskvo = v;
								break;
							} else if (prjvo.getUseageType() != null && v.getUseageType() != null && prjvo.getUseageType().equals(v.getUseageType())) {
								taskvo = v;
								break;
							}
						}
					}
					Integer i = taskvo.getTimeLimit() == null ? 0 : taskvo.getTimeLimit();
					Date end = dimHolidayService.findWorkDay(start, i, taskvo.getTimeType() == null ? DicConstants.TASK_DEFINE_DIM_TYPE_CALENDARDAY : taskvo.getTimeType());
					task.setTaskEndTime(end);
					task.setTimeType(taskvo.getTimeType());
					task.setTaskDueDuration(taskvo.getTimeLimit());
					task.setCurrUser(task.getInitUser());
					task.setAcceptId(accept.getId());
					
					// 判断当前事项是否依赖其它事项，如果是，事项状态设置为：未启动
					boolean hasDepTask = hasDepTask(task.getTaskId(),prjvo.getId());
					if (hasDepTask) {
						task.setTaskStatus(TaskConstants.TASK_STATUS_NOSTART);// 未启动
					} else {
						task.setTaskStatus(TaskConstants.TASK_STATUS_AUDIT);// 开始
						
						// 发送短信 （未启动的项目不发送短信）
						User user = UserUtils.get(task.getInitUser());
						if (user != null) {
							sendMs(user.getMobile());
						}
					}
				}
				prjTaskMapper.updateByPrimaryKeySelective(task);
				// 项目事项状态
				if (!StringUtils.isBlank(task.getInitUser())) {
					PrjTaskTransferDetailDTO dto = new PrjTaskTransferDetailDTO();
					dto.setCreator(UserUtils.getUser().getId());
					dto.setCreatTime(new Date());
					dto.setPrjTaskInstId(task.getId());
					dto.setReceiver(task.getCurrUser());
					// dto.setTransfer(UserUtils.getUser().getId());
					prjTaskTransferDetailService.saveTransfer(dto);
				}
			}
		}
	}

	/**
	 * 插入新的建设单位基本信息表,已存在的数据进行更新
	 * @param prjvo
	 * @Date 20160421 13:44:34
	 */
	private void saveOrUpdateBuild(PrjInstance prjvo) {
		Date now = new Date();
		String companyCode = prjvo.getCompanyCode();
		if(StringUtils.isBlank(companyCode)){
			return ;
		}
		PrjBuildCompany prjBuildCompany = new PrjBuildCompany();
		BeanCopy.copyProperties(prjvo, prjBuildCompany, PrjBuildCompany.class);
		prjBuildCompany.setId(null);
		prjBuildCompany.setUpdateTime(now);
		prjBuildCompany.setIsDelete("0");
		PrjBuildCompanyExample example = new PrjBuildCompanyExample();
		example.createCriteria().andCompanyCodeEqualTo(companyCode);
		List<PrjBuildCompany> buildCompanyList = prjBuildCompanyMapper.selectByExample(example);
		if(buildCompanyList.size() == 0){
			prjBuildCompany.setCreatTime(now);
			prjBuildCompany.setCreator(UserUtils.getUser().getId());
			prjBuildCompanyMapper.insert(prjBuildCompany);
		}else if(buildCompanyList.size() == 1){
			prjBuildCompany.setId(buildCompanyList.get(0).getId());
			prjBuildCompany.setUpdator(UserUtils.getUser().getId());
			prjBuildCompanyMapper.updateByPrimaryKey(prjBuildCompany);
		}
	}

	/**
	 *  判断当前事项是否依赖其它事项，并且该事项未办结，如果是，返回true，否则返回false
	 * @param taskId
	 * @return
	 */
	private boolean hasDepTask(long taskId, long prjInsId) {
		PrjTaskDefine def = prjTaskDefineMapper.selectByPrimaryKey(taskId);
		// 当前事项不依赖其它事项
		if (def != null && !"1".equals(def.getIsReplyon())) {
			return false;
		} 
		
		PrjTaskDependencyExample depExample = new PrjTaskDependencyExample();
		depExample.createCriteria().andTaskIdEqualTo(taskId);
		List<PrjTaskDependency> depList = prjTaskDependencyMapper.selectByExample(depExample);// 查询依赖当前事项的事项
		if (depList != null && depList.size() > 0) {
			for (PrjTaskDependency dep : depList) {
				PrjTaskExample taskExample = new PrjTaskExample();
				PrjTaskExample.Criteria tcriteria = taskExample.createCriteria();
				tcriteria.andPrjIdEqualTo(prjInsId);
				tcriteria.andTaskIdEqualTo(dep.getDepTaskId());
				tcriteria.andTaskStatusNotEqualTo("4");
				tcriteria.andTaskStatusNotEqualTo("7"); // 线下办结
				
				List<PrjTask> taskList = prjTaskMapper.selectByExample(taskExample);
				if (taskList != null && taskList.size() > 0) {
					return true;
				}
			}
		}
		return false;
	}
	

	@Override
	public void updateProject(Project project) {

		// 受理阶段实例
		updateAccept(project);
		List<PrjTaskVo> listPro = project.getPrjTaskVoList();
		PrjBusinessAcceptVo accept = project.getPrjBusinessAcceptVo();
		PrjInstance prjvo = new PrjInstance();
		BeanCopy.copyProperties(project.getPrjInstanceVo(), prjvo);
		// 保存或更改业务表数据
		if (project.getFormRfYdjsBjspVo() != null) {// 人防工程易地建设报建审批表(表1)
			FormRfYdjsBjsp record = new FormRfYdjsBjsp();
			BeanCopy.copyProperties(project.getFormRfYdjsBjspVo(), record);
			if (record.getId() == null) {
				record.setPrjId(prjvo.getId());
				formRfYdjsBjspMapper.insert(record);
			} else {
				formRfYdjsBjspMapper.updateByPrimaryKeySelective(record);
			}
		}
		if (project.getFormRfYdjsJgsbVo() != null) {// 人防易地建设竣工申报表(表2)
			FormRfYdjsJgsb record = new FormRfYdjsJgsb();
			BeanCopy.copyProperties(project.getFormRfYdjsJgsbVo(), record);
			if (record.getId() == null) {
				record.setPrjId(prjvo.getId());
				formRfYdjsJgsbMapper.insert(record);
				FormRfJsxmqkb re = new FormRfJsxmqkb();
				BeanCopy.copyProperties(project.getFormRfJsxmqkbVo(), re);
				formRfJsxmqkbMapper.insert(re);
			} else {
				formRfYdjsJgsbMapper.updateByPrimaryKeySelective(record);
				FormRfJsxmqkb re = new FormRfJsxmqkb();
				BeanCopy.copyProperties(project.getFormRfJsxmqkbVo(), re);
				formRfJsxmqkbMapper.updateByPrimaryKeySelective(re);
			}
		}
		if (project.getFormRfBjshVo() != null) {// 人防工程易地建设报建审核表(表3)
			FormRfBjsh record = new FormRfBjsh();
			BeanCopy.copyProperties(project.getFormRfBjshVo(), record);
			if (record.getId() == null) {
				record.setPrjId(prjvo.getId());
				formRfBjshMapper.insert(record);
			} else {
				formRfBjshMapper.updateByPrimaryKeySelective(record);
			}
		}
		// 更改项目
		prjvo.setUpdateTime(new Date());
		prjvo.setUpdator(UserUtils.getUser().getId());
		if (prjvo.getAcceptId() == null && listPro != null && listPro.size() > 0) {
			prjvo.setRealStartTime(new Date());
		}
		prjvo.setAcceptId(accept.getId());
		
		// 修改项目时，如果没有上传文件，防止覆盖原文件
		if (StringUtils.isBlank(prjvo.getPreFilesAddr())) {
			prjvo.setPreFilesAddr(null);
			prjvo.setPreFilesName(null);
		}
		
		prjInstanceMapper.updateByPrimaryKeySelective(prjvo);
		//20160421 增加建设单位的基础信息存储
		saveOrUpdateBuild(prjvo);
		// 项目所处阶段
		PrjStageVo vo = null;
		
		if (project.getPrjStageVo() != null && "1".equals(project.getPrjStageVo().getNewStageFlag())) {
			// 新阶段受理
			vo = newStageAccept(project, listPro, accept, prjvo);
		} else {
			vo = ProjectUtil.getStageInstanceByProId(project.getPrjInstanceVo().getId());
		}
		
		
		BeanCopy.copyProperties(prjvo, project.getPrjInstanceVo());
		// 更改材料
		List<PrjStageMaterial> records = new ArrayList<PrjStageMaterial>();
		if (project.getPrjStageMaterialVoList() != null && project.getPrjStageMaterialVoList().size() > 0) {
			// 保存材料
			BeanCopy.copyPropertiesForList(project.getPrjStageMaterialVoList(), records, PrjStageMaterial.class);
			for (PrjStageMaterial p : records) {
				if (StringUtils.isBlank(p.getMaterialAddr())) {
					p.setMaterialAddr(null);
					p.setMaterialName(null);
				}
				p.setPrjId(prjvo.getId());
				p.setUpdateTime(new Date());
				p.setUpdator(UserUtils.getUser().getId());
				p.setPrjStageInstId(vo.getId());
				 if (p.getIsComplete() == null) {
				 p.setIsComplete("0");
				 }
				p.setAcceptId(accept.getId());
				PrjStageMaterialMapper.updateByPrimaryKeySelective(p);
			}
		}

		if (listPro != null && listPro.size() > 0) {
			// 更改阶段状态
			PrjStage state = new PrjStage();
			state.setId(vo.getId());
			state.setStageStatus("1");
			state.setStageStartTime(new Date());
			state.setUpdateTime(new Date());
			state.setUpdator(UserUtils.getUser().getId());
			state.setAcceptId(accept.getId());
			prjStageMapper.updateByPrimaryKeySelective(state);
			// 先删再添加
			// PrjTaskExample example = new PrjTaskExample();
			// com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria c =
			// example.createCriteria();
			// c.andPrjIdEqualTo(project.getPrjInstanceVo().getId());
			// c.andPrjStageInstIdEqualTo(vo.getId());
			// prjTaskMapper.deleteByExample(example);
			// 更改事项实例
			for (PrjTaskVo t : listPro) {
				PrjTask task = new PrjTask();
				BeanCopy.copyProperties(t, task);
				task.setPrjStageInstId(state.getId());// 阶段实例id
				task.setPrjId(Long.parseLong(prjvo.getId().toString()));
				task.setCurrUser(task.getInitUser());
				task.setTaskStatus("0");// 草稿
				task.setIsWithCert(task.getIsWithCert() == null ? "0" : task.getIsWithCert());
				task.setIsFetched("0");
				// task.setCreator(UserUtils.getUser().getId());
				// task.setCreatTime(new Date());
				task.setUpdateTime(new Date());
				task.setUpdator(UserUtils.getUser().getId());
				// 处理开始日期
				if (!StringUtils.isBlank(task.getInitUser())) {
					Date start = dimHolidayService.findNextWorkDay();
					task.setTaskStartTime(start);
					List<PrjTaskTimerDefineVo> taskvoList = ProjectUtil.getTaskTimerByTaskId(task.getTaskId().toString());
					PrjTaskTimerDefineVo taskvo = new PrjTaskTimerDefineVo();
					if (taskvoList != null && taskvoList.size() == 1) {
						taskvo = taskvoList.get(0);
					} else if (taskvoList.size() > 1) {
						for (PrjTaskTimerDefineVo v : taskvoList) {
							if (prjvo.getPriceType() != null && v.getPriceType() != null && prjvo.getPriceType().equals(v.getPriceType())) {
								taskvo = v;
								break;
							} else if (prjvo.getUseageType() != null && v.getUseageType() != null && prjvo.getUseageType().equals(v.getUseageType())) {
								taskvo = v;
								break;
							}
						}
					}
					Integer i = taskvo.getTimeLimit() == null ? 0 : taskvo.getTimeLimit();
					Date end = dimHolidayService.findWorkDay(start, i, taskvo.getTimeType() == null ? DicConstants.TASK_DEFINE_DIM_TYPE_CALENDARDAY : taskvo.getTimeType());
					task.setTaskEndTime(end);
					task.setTimeType(taskvo.getTimeType());
					task.setTaskDueDuration(taskvo.getTimeLimit());
					task.setCurrUser(task.getInitUser());
					task.setAcceptId(accept.getId());
					
					// 判断当前事项是否依赖其它事项，如果是，事项状态设置为：未启动
					boolean hasDepTask = hasDepTask(task.getTaskId(), prjvo.getId());
					if (hasDepTask) {
						task.setTaskStatus(TaskConstants.TASK_STATUS_NOSTART);// 未启动
					} else {
						task.setTaskStatus(TaskConstants.TASK_STATUS_AUDIT);// 开始
						
						// 发送短信 （未启动的项目不发送短信）
						User user = UserUtils.get(task.getInitUser());
						if (user != null) {
							sendMs(user.getMobile());
						}
					}
				}
				prjTaskMapper.updateByPrimaryKeySelective(task);
				if (!StringUtils.isBlank(task.getInitUser())) {
					PrjTaskTransferDetailDTO dto = new PrjTaskTransferDetailDTO();
					dto.setCreator(UserUtils.getUser().getId());
					dto.setCreatTime(new Date());
					dto.setPrjTaskInstId(task.getId());
					dto.setReceiver(task.getInitUser());
					// dto.setTransfer(UserUtils.getUser().getId());
					prjTaskTransferDetailService.saveTransfer(dto);
				}
			}
		}
	}

	/**
	 * 新阶段受理
	 * 
	 * @param project
	 * @param listPro
	 * @param accept
	 * @param prjvo
	 * @return
	 */
	private PrjStageVo newStageAccept(Project project, List<PrjTaskVo> listPro, PrjBusinessAcceptVo accept, PrjInstance prjvo) {
		PrjStageVo vo;
		// 新阶段受理，增加新的阶段表记录
		PrjStage state = new PrjStage();
		state.setPrjId(prjvo.getId());
		state.setStageCode(FormUtils.getFormNo());// 受理编号
		state.setStageId(prjvo.getStageId());
		state.setCreator(UserUtils.getUser().getId());
		state.setCreatTime(new Date());
		state.setUpdateTime(new Date());
		state.setUpdator(UserUtils.getUser().getId());
		if (listPro == null) {
			state.setStageStatus("0");// 资料待补齐
		} else {
			state.setStageStatus("1");// 待审批
			state.setStageStartTime(new Date());
		}
		state.setAcceptId(accept.getId());
		prjStageMapper.insert(state);
		vo = new PrjStageVo();
		BeanCopy.copyProperties(state, vo);
		project.setPrjStageVo(vo);
		
		// 保存材料
		List<PrjStageMaterial> records = new ArrayList<PrjStageMaterial>();
		if (project.getPrjStageMaterialVoList() != null && project.getPrjStageMaterialVoList().size() > 0) {
			BeanCopy.copyPropertiesForList(project.getPrjStageMaterialVoList(), records, PrjStageMaterial.class);
			for (PrjStageMaterial m : records) {
				m.setPrjId(prjvo.getId());
				m.setCreator(UserUtils.getUser().getId());
				m.setCreatTime(new Date());
				m.setUpdateTime(new Date());
				m.setUpdator(UserUtils.getUser().getId());
				m.setPrjStageInstId(state.getId());
				if (m.getIsComplete() == null) {
					m.setIsComplete("0");
				}
				m.setAcceptId(accept.getId());
				PrjStageMaterialMapper.insert(m);
			}
		}
		
		Map<Long, PrjTaskVo> taskMap = new HashMap<Long, PrjTaskVo>();
		if (listPro != null && listPro.size() > 0) {
			for (PrjTaskVo v : listPro) {
				taskMap.put(v.getTaskId(), v);
			}
		}

		// 保存所有的事项
		List<PrjTaskDefineVo> l = ProjectUtil.getAllTaskByStage(prjvo.getStageId());
		if (l != null && l.size() > 0) {
			for (PrjTaskDefineVo v : l) {
				PrjTask task = new PrjTask();
				BeanCopy.copyProperties(v, task);
				task.setPrjStageInstId(state.getId());// 阶段实例id
				task.setPrjId(Long.parseLong(prjvo.getId().toString()));
				task.setCurrUser(task.getInitUser());
				task.setTaskStatus("0");// 草稿
				task.setIsWithCert(task.getIsWithCert() == null ? "0" : task.getIsWithCert());
				task.setIsFetched("0");
				task.setCreator(UserUtils.getUser().getId());
				task.setCreatTime(new Date());
				task.setUpdateTime(new Date());
				task.setUpdator(UserUtils.getUser().getId());
				task.setTaskId(task.getId());
				prjTaskMapper.insert(task);
				PrjTaskVo tv = taskMap.get(v.getId());
				if (tv != null)
					tv.setId(task.getId());
			}
		}
		return vo;
	}

	@Override
	public Project getProject(Long id) {
		Project project = ProjectUtil.getProject(id);
		return project;
	}
	
	@Override
	public Project getEnterProject(String pid, String stageId) {
		Project project = ProjectUtil.getEnterProject(pid, stageId); 
		return project;
	}

	@Override
	public List<Project> getProjectList(Project project) {
		return ProjectUtil.getProject(project.getPrjInstanceVo());
	}

	@Override
	public void getProjectPage(Project project, Page<Project> page) {
		ProjectUtil.getProjectPage(project.getPrjInstanceVo(), page);
	}

	@Override
	public void delete(Project project) {
		PrjInstance prjvo = new PrjInstance();
		BeanCopy.copyProperties(project.getPrjInstanceVo(), prjvo);
		prjvo.setIsDelete("1");
		prjInstanceMapper.updateByPrimaryKeySelective(prjvo);
	}

	@Override
	public void validate(Project project) {
		List<PrjStageMaterialVo> list = project.getPrjStageMaterialVoList();
		if (project.getPrjInstanceVo() != null && project.getPrjInstanceVo().getId() != null) {
			if (list == null || list.size() == 0) {
				list = ProjectUtil.getStageMaterList(project.getPrjInstanceVo().getId());
			}
		}
		if (list == null || list.size() == 0) {
			project.setValidate(false);
		} else {
			for (PrjStageMaterialVo vo : list) {
				if (vo.getIsMandatory() != null && vo.getIsMandatory().equals("1")) {
					if (vo.getIsComplete() == null || !vo.getIsComplete().equals("1")) {
						project.setValidate(false);
						break;
					}
				}
			}
		}
	}

	@Override
	public void doTask(Project project) {
		List<PrjTaskVo> listPro = project.getPrjTaskVoList();
		List<PrjTask> prjTaskList = new ArrayList<PrjTask>();
		if (listPro != null) {
			BeanCopy.copyPropertiesForList(listPro, prjTaskList, PrjTask.class);
		} else {
			return;
		}
		if (project.getPrjInstanceVo() == null || project.getPrjInstanceVo().getId() == null) {
			return;
		}
		// 受理阶段实例
		updateAccept(project);
		PrjBusinessAcceptVo accept = project.getPrjBusinessAcceptVo();
		// 更改项目
		PrjInstance prjvo = new PrjInstance();
		BeanCopy.copyProperties(project.getPrjInstanceVo(), prjvo);
		prjvo.setUpdateTime(new Date());
		prjvo.setUpdator(UserUtils.getUser().getId());
		prjvo.setAcceptId(accept.getId());
		prjInstanceMapper.updateByPrimaryKeySelective(prjvo);
		prjvo = prjInstanceMapper.selectByPrimaryKey(prjvo.getId());
		// 获取阶段
		PrjStageVo vo = ProjectUtil.getStageInstanceByProId(prjvo.getId());
		// 先删再添加
		PrjTaskExample example = new PrjTaskExample();
		com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria c = example.createCriteria();
		c.andPrjIdEqualTo(prjvo.getId());
		c.andPrjStageInstIdEqualTo(vo.getId());
		prjTaskMapper.deleteByExample(example);
		// 处理事务
		if (listPro != null && listPro.size() > 0) {
			for (PrjTaskVo t : listPro) {
				PrjTask task = new PrjTask();
				BeanCopy.copyProperties(t, task);
				task.setPrjStageInstId(vo.getId());// 阶段实例id
				task.setPrjId(prjvo.getId());
				task.setCurrUser(task.getInitUser());
				task.setTaskStatus("0");// 草稿
				task.setIsWithCert(task.getIsWithCert() == null ? "0" : task.getIsWithCert());
				task.setIsFetched("0");
				task.setCreator(UserUtils.getUser().getId());
				task.setCreatTime(new Date());
				task.setUpdateTime(new Date());
				task.setUpdator(UserUtils.getUser().getId());
				task.setIsDelete("0");
				task.setAcceptId(accept.getId());
				// 时间比对
				if (!StringUtils.isBlank(task.getInitUser())) {
					Date start = dimHolidayService.findNextWorkDay();
					task.setTaskStartTime(start);
					List<PrjTaskTimerDefineVo> taskvoList = ProjectUtil.getTaskTimerByTaskId(task.getTaskId().toString());
					PrjTaskTimerDefineVo taskvo = new PrjTaskTimerDefineVo();
					if (taskvoList != null && taskvoList.size() == 1) {
						taskvo = taskvoList.get(0);
					} else if (taskvoList.size() > 1) {
						for (PrjTaskTimerDefineVo v : taskvoList) {
							if (prjvo.getPriceType() != null && v.getPriceType() != null && prjvo.getPriceType().equals(v.getPriceType())) {
								taskvo = v;
								break;
							} else if (prjvo.getUseageType() != null && v.getUseageType() != null && prjvo.getUseageType().equals(v.getUseageType())) {
								taskvo = v;
								break;
							}
						}
					}
					Integer i = taskvo.getTimeLimit() == null ? 0 : taskvo.getTimeLimit();
					Date end = dimHolidayService.findWorkDay(start, i, taskvo.getTimeType() == null ? DicConstants.TASK_DEFINE_DIM_TYPE_CALENDARDAY : taskvo.getTimeType());
					task.setTaskEndTime(end);
					task.setTimeType(taskvo.getTimeType());
					task.setTaskDueDuration(taskvo.getTimeLimit());

					// 判断当前事项是否依赖其它事项，如果是，事项状态设置为：未启动
					boolean hasDepTask = hasDepTask(task.getTaskId(), prjvo.getId());
					if (hasDepTask) {
						task.setTaskStatus(TaskConstants.TASK_STATUS_NOSTART);// 未启动
					} else {
						task.setTaskStatus(TaskConstants.TASK_STATUS_AUDIT);// 开始
						
						// 发送短信 （未启动的项目不发送短信）
						User user = UserUtils.get(task.getInitUser());
						if (user != null) {
							sendMs(user.getMobile());
						}
					}
				}
				prjTaskMapper.insert(task);
				if (!StringUtils.isBlank(task.getInitUser())) {
					PrjTaskTransferDetailDTO dto = new PrjTaskTransferDetailDTO();
					dto.setCreator(UserUtils.getUser().getId());
					dto.setCreatTime(new Date());
					dto.setPrjTaskInstId(task.getId());
					dto.setReceiver(task.getInitUser());
					// dto.setTransfer(UserUtils.getUser().getId());
					prjTaskTransferDetailService.saveTransfer(dto);
				}
			}
			// 更改阶段状态
			PrjStage state = new PrjStage();
			state.setId(vo.getId());
			state.setStageStatus("1");
			state.setStageStartTime(new Date());
			state.setUpdateTime(new Date());
			state.setUpdator(UserUtils.getUser().getId());
			state.setAcceptId(accept.getId());
			prjStageMapper.updateByPrimaryKeySelective(state);
		}
	}

	@Override
	public void doMaterial(Project project) {
		// 更改材料
		List<PrjStageMaterial> records = new ArrayList<PrjStageMaterial>();
		if (project.getPrjStageMaterialVoList() != null) {
			BeanCopy.copyPropertiesForList(project.getPrjStageMaterialVoList(), records, PrjStageMaterial.class);
			for (PrjStageMaterial p : records) {
				if (p.getIsComplete() == null) {
					p.setIsComplete("0");
				}
				if (p.getMaterialAddr() != null) {
					if (p.getMaterialAddr().equals("")) {
						p.setMaterialAddr(null);
						p.setMaterialName(null);
					}
				}
				PrjStageMaterialMapper.updateByPrimaryKeySelective(p);
			}
		}
	}

	@Override
	public void updateMater(String materid, String url) {
		PrjStageMaterial record = new PrjStageMaterial();
		record.setMaterialAddr(url);
		record.setId(Long.parseLong(materid));
		PrjStageMaterialMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public void nextState(String prjId) {
		//TODO Auto-generated method stub

	}

	@Override
	public ProjectChangeForm getProjectChangeForm(Long id){
		ProjectChangeForm project = ProjectStepUtil.getProject(id);
		return project;
	}

	private void sendMs(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			return;
		}
		new Thread() {
			@Override
			public void run() {
				iMessageSMS.sendSmsByTplId(mobile, "S010001");
			}
		}.start();
	}

	/**
	 * @param project
	 *            默认能生成序列号
	 */
	public void saveCode(Project project) {
		if (project == null) {
			return;
		}
		PrjCodeGeneratorVo vo = project.getPrjCodeGeneratorVo();
		if (vo == null) {
			return;
		}
		if (project.getPrjInstanceVo() == null) {
			return;
		}
		String code = project.getPrjInstanceVo().getPrjCode();
		if (StringUtils.isBlank(code)) {
			return;
		}
		vo.setMonth(Integer.parseInt(code.substring(8, 10)));
		vo.setYear(Integer.parseInt(code.substring(4, 8)));
		vo.setSeq(Integer.parseInt(code.substring(10)));
		Integer rcode = ProjectUtil.lockProjectCode(vo);
		for (int i = 0; i < 10; i++) {
			if (rcode == 0) {
				vo.setSeq(vo.getSeq() + 1);
				rcode = ProjectUtil.lockProjectCode(vo);
			} else {
				project.setPrjCodeGeneratorVo(vo);
				project.getPrjInstanceVo().setPrjCode(project.getPrjCodeGeneratorVo().toString());
				break;
			}
		}
	}

	public void saveAccept(PrjBusinessAccept a) {
		if (a != null) {
			a.setCreator(UserUtils.getUser().getId());
			a.setCreatTime(new Date());
			a.setUpdateTime(new Date());
			a.setUpdator(UserUtils.getUser().getId());
			a.setIsDelete("0");
			PrjBusinessAcceptMapper.insert(a);
		}
	}

	private void updateAccept(Project project) {
		PrjBusinessAcceptVo accept = project.getPrjBusinessAcceptVo();
		if (accept != null) {
			PrjBusinessAccept a = new PrjBusinessAccept();
			BeanCopy.copyProperties(accept, a);
			PrjBusinessAcceptMapper.updateByPrimaryKeySelective(a);
		} else {
			accept = new PrjBusinessAcceptVo();
			project.setPrjBusinessAcceptVo(accept);
		}
	}
	
	
	@Override
	public List<PrjTaskDefineVo> getNewStageTaskDefineList(Long stageId, Long prjInsId) {
		List<PrjTaskDefineVo> prjTaskDefVos = ProjectUtil.getAllTaskByStage(stageId);
		List<PrjTaskMaterialDefDTO> taskMateDefs = null;
		List<PrjTaskMaterialVo> prjTaskMaterialVos = null;
		PrjTaskMaterialVo taskMeterVo = null;
		for (PrjTaskDefineVo vo : prjTaskDefVos) {
			taskMateDefs = prjTaskMaterialDefService.findByTaskId(vo.getId());
			prjTaskMaterialVos = new ArrayList<PrjTaskMaterialVo>();
			for (PrjTaskMaterialDefDTO dto : taskMateDefs) {
				DimMaterial mater = new DimMaterial();
				mater.setId(dto.getMaterialId());
				mater = materialDao.selectOneByEntitySelective(mater);
				if (mater != null && "1".equals(mater.getIsValid())) {
					taskMeterVo = new PrjTaskMaterialVo();
					BeanCopy.copyProperties(dto, taskMeterVo);
					taskMeterVo.setMaterName(mater.getName());
					if (prjInsId != null) {
						PrjStageMaterial record = new PrjStageMaterial();
						record.setStageId(stageId);
						record.setPrjId(prjInsId);
						record.setMaterialId(mater.getId());
						List<PrjStageMaterial> materList = PrjStageMaterialMapper.selectByEntitySelective(record);
						if (materList != null && materList.size() > 0) {
							PrjStageMaterial ms = materList.get(0);
							taskMeterVo.setIsComplete(ms.getIsComplete());
							taskMeterVo.setMaterialAddr(ms.getMaterialAddr());
						}
					}
					
					prjTaskMaterialVos.add(taskMeterVo);
				} else {
					continue;
				}
			}
			vo.setPrjTaskMaterialVos(prjTaskMaterialVos);
		}
		return prjTaskDefVos;
	}

	/**
	 * 通过项目ID获取com.lpcode.modules.service.project.dto.Project
	 * @param pid
	 * @return
	 */
	@Override
	public Project getProjectByPrjId(Long pid){
		Project project = new Project();
		项目对应的材料数
		PrjInstanceVo vo = ProjectUtil.getInstance(pid);

		保存项目基本信息实体
        project.setPrjInstanceVo(vo);
		判断该项目有没有阶段
        if (vo.getStageId() == null) {
		    获取目前阶段上传的所有资料
            project.setPrjStageMaterialVoList(ProjectUtil.getDraftStageMaterList(pid));
        } else {
		    有阶段就获取这个项目对应的阶段的材料
            project.setPrjStageMaterialVoList(ProjectUtil.getStageMaterList(vo.getStageId(), pid));
        }
        获取这个项目阶段实例
        project.setPrjStageVo(ProjectUtil.getStageInstanceByProId(pid));

        Map<Long, List<PrjStageMaterialVo>> map = new LinkedHashMap<Long, List<PrjStageMaterialVo>>();
        //定义一个集合，用来存储已经填写的表单对应的事项ID
        List<Long> taskIds = new ArrayList<Long>();

        项目阶段材料清单表
        project.setPrjStageMaterialVoMap(map);
        如果项目阶段材料有值
        if (project.getPrjStageMaterialVoList() != null && project.getPrjStageMaterialVoList().size() > 0) {
            遍历当前阶段材料集合
            for (PrjStageMaterialVo v : project.getPrjStageMaterialVoList()) {
                阶段ID等于空，是的话为01， 不是的话就是这个阶段ID,
                List<PrjStageMaterialVo> taskList = map.get(v.getTaskId() == null ? 0l : v.getTaskId());
                如果集合是空，就添加当前阶段材料到takeList集合中
                if (taskList == null) {
                    项目阶段材料
                    taskList = new ArrayList<PrjStageMaterialVo>();
                    taskList.add(v);
                    map集合放0L或者v事项ID和taklist集合
                    map.put(v.getTaskId() == null ? 0L : v.getTaskId(), taskList);
                    taskIds.add(v.getTaskId());
                } else {
                    不为空则添加事项阶段材料
                    taskList.add(v);
                }
            }
        }


        //通过事项加载相应表单类，存入project的formMap中
        if(taskIds != null && taskIds.size() > 0){
        	 Map<String,Object> formMap = new HashMap<String, Object>();
        	for (Long taskId: taskIds) {

        		PrjFormTaskRealExample example = new PrjFormTaskRealExample();

                example.createCriteria().andTaskDefIdEqualTo(taskId);

                List<PrjFormTaskReal> prjFormTaskRealList = prjFormTaskRealMapper.selectByExample(example);

        		if(prjFormTaskRealList != null && prjFormTaskRealList.size() > 0){
        			for (PrjFormTaskReal prjFormTaskReal : prjFormTaskRealList) {

        				FormDefine formDefine = formDefineMapper.selectByPrimaryKey(prjFormTaskReal.getFormId());
        				String formCode = formDefine.getFormCode().toUpperCase();
        				PrjFormVO prjFormVO = factoryFormService.initBizForm(project.getPrjInstanceVo(), formCode,taskId.toString());
        				if(prjFormVO != null){
        					if(prjFormVO.getObject() != null){
        						formMap.put(formCode, prjFormVO.getObject());
        					}
        				}
        			}
        		}
    		}
        	project.setFormMap(formMap);
        }
        /*project.setFormRfBjshVo(ProjectUtil.getFormRfBjshVo(pid));
        project.setFormRfYdjsBjspVo(ProjectUtil.getFormRfYdjsBjsp(pid));
        FormRfYdjsJgsbVo jgsb = ProjectUtil.getFormRfYdjsJgsb(pid);
        project.setFormRfYdjsJgsbVo(jgsb);
        if (jgsb != null)
            project.setFormRfJsxmqkbVo(ProjectUtil.getFormRfJsxmqkb(jgsb.getId() == null ? null : jgsb.getId().toString()));*/
        return project;
	}
	
	/**
	 * 在com.lpcode.modules.service.project.dto.Project中 PrjStageMaterialVoMap内加入该阶段指定事项所有材料，并 返回该对象
	 * @param stateId
	 * @param project
	 * @return
	 */
	@Override
	public Project getProjectByTaskId(Long stateId,Long taskId,Project project){
		project.setFormMap(null);
		PrjTaskDefineDTO prjTaskDefineDTO = prjTaskDefineService.findByPrimaryKey(taskId);
		//获取表单类，并存入project的formMap中
		PrjFormTaskRealExample example = new PrjFormTaskRealExample();
		example.createCriteria().andTaskDefIdEqualTo(taskId);
		List<PrjFormTaskReal> prjFormTaskRealList = prjFormTaskRealMapper.selectByExample(example);
		if(prjFormTaskRealList != null && prjFormTaskRealList.size() > 0){
			Map<String,Object> formMap = new HashMap<String, Object>();
			Map<String,String> formNameMap = new HashMap<String,String>();
			for (PrjFormTaskReal prjFormTaskReal : prjFormTaskRealList) {
				FormDefine formDefine = formDefineMapper.selectByPrimaryKey(prjFormTaskReal.getFormId());
				String formName = formDefine.getFormName();
				String formCode = formDefine.getFormCode().toUpperCase();
				PrjFormVO prjFormVO = factoryFormService.initBizForm(project.getPrjInstanceVo(), formCode,taskId.toString());
				if(prjFormVO != null){
					if(prjFormVO.getObject() != null){
						formMap.put(formCode, prjFormVO.getObject());
						formNameMap.put(formCode, formName);
					}
				}
			}
			project.setFormMap(formMap);
			project.setFormNameMap(formNameMap);
		}
		//获取指定事项下的全部事项材料类  对应的集合
		List<PrjTaskMaterialDefDTO> materialList = prjTaskMaterialDefService.findByTaskId(taskId);
		List<PrjTaskMaterialVo> prjTaskMaterialVos = new ArrayList<PrjTaskMaterialVo>();
		//根据事项编号获取该事项所处阶段编号
		PrjTaskDefine prjTaskDefine = prjTaskDefineMapper.selectByPrimaryKey(taskId);
		//获取某阶段下全部材料名称
		Map<Long,String> materialNameMap = materialService.getAllMaterName(prjTaskDefine.getStageId());
		if(materialList != null){
			for (PrjTaskMaterialDefDTO temp:materialList) {
				PrjTaskMaterialVo prjTaskMaterialVo = new PrjTaskMaterialVo();
				BeanCopy.copyProperties(temp, prjTaskMaterialVo, PrjTaskMaterialVo.class);
				prjTaskMaterialVo.setMaterName(materialNameMap.get(temp.getMaterialId()));
				prjTaskMaterialVo.setIsComplete("0");
				prjTaskMaterialVo.setMaterialAddr("");
				prjTaskMaterialVos.add(prjTaskMaterialVo);
			}
		}
		PrjTaskDefineVo prjTaskDefineVo = new PrjTaskDefineVo();
		BeanCopy.copyProperties(prjTaskDefineDTO, prjTaskDefineVo, PrjTaskDefineVo.class);
		prjTaskDefineVo.setPrjTaskMaterialVos(prjTaskMaterialVos);
		//以 事项ID为key，以  阶段材料类  对应集合为value创建map
		Map<Long, List<PrjStageMaterialVo>> orderMap = new LinkedHashMap<Long, List<PrjStageMaterialVo>>();
		List<PrjStageMaterialVo> list = new ArrayList<PrjStageMaterialVo>();
		//把  事项材料类  对应集合转存到	阶段材料类	对应集合中
		for (PrjTaskMaterialVo prjTaskMaterialVo : prjTaskMaterialVos) {
			PrjStageMaterialVo prjStageMaterialVo = new PrjStageMaterialVo();
			BeanCopy.copyProperties(prjTaskMaterialVo, prjStageMaterialVo, PrjStageMaterialVo.class);
			list.add(prjStageMaterialVo);
		}
		orderMap.put(taskId, list);
		//获取  当前事项中被选中的   材料  所对应的map
		Map<Long, List<PrjStageMaterialVo>> map = new HashMap<Long, List<PrjStageMaterialVo>>();
		PrjStageMaterial record = new PrjStageMaterial();
		record.setPrjId(project.getPrjInstanceVo().getId());
		record.setStageId(prjTaskDefine.getStageId());
		List<PrjStageMaterial> prjStageMaterialList = PrjStageMaterialMapper.selectByEntitySelective(record);
		List<PrjStageMaterialVo> prjStageMaterialListVo = new ArrayList<PrjStageMaterialVo>();
		BeanCopy.copyPropertiesForList(prjStageMaterialList, prjStageMaterialListVo, PrjStageMaterialVo.class);
		map.put(taskId, prjStageMaterialListVo);
		//遍历两个map，将事项下的map中所选中的材料进行替换
		@SuppressWarnings("rawtypes")
		Iterator iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<Long, List<PrjStageMaterialVo>> nextMap = (Entry<Long, List<PrjStageMaterialVo>>) iterator.next();
			Long key2 = nextMap.getKey();
			if(key2.equals(taskId)){
				List<PrjStageMaterialVo> PrjStageMaterialVoListOrder = orderMap.get(taskId);
				List<PrjStageMaterialVo> PrjStageMaterialVoListMap = nextMap.getValue();
				for( int i = 0 ; i < PrjStageMaterialVoListOrder.size() ; i ++ ){
					for( int j = 0 ; j < PrjStageMaterialVoListMap.size() ; j ++ ){
						if(PrjStageMaterialVoListOrder.get(i).getMaterialId().equals(PrjStageMaterialVoListMap.get(j).getMaterialId())){
							PrjStageMaterialVo prjStageMaterialVo = PrjStageMaterialVoListOrder.get(i);
							prjStageMaterialVo.setMaterialAddr(PrjStageMaterialVoListMap.get(j).getMaterialAddr());
							prjStageMaterialVo.setIsComplete("1");
						}
					}
				}
			}
		}
		project.setPrjStageMaterialVoMap(orderMap);
		return project;
	}

	
	/**
	 * 在com.lpcode.modules.service.project.dto.Project中 PrjStageMaterialVoMap内加入该阶段所有材料，并 返回该对象
	 * @param stateId
	 * @param project
	 * @return
	 */
	@Override
	public Project getProjectById(Long stateId,Project project){
		//获取某项目某阶段下所有事项的集合
		List<PrjTaskDefineVo> prjTaskDefineVoList = ProjectStepUtil.getAllTaskDefineByPrjStage(project.getPrjInstanceVo().getId(), stateId);
		//通过orderNum对事项进行排序
		PrjTaskDefineVo p = null;
		boolean exchange = false;
		for (int i = 0; i < prjTaskDefineVoList.size(); i++) {
            exchange = false;
            for (int j = prjTaskDefineVoList.size() - 2; j >= i; j--) {
                if (((PrjTaskDefineVo) prjTaskDefineVoList.get(j + 1)).getOrderNum().compareTo(
                        ((PrjTaskDefineVo) prjTaskDefineVoList.get(j)).getOrderNum()) < 0) {
                    p = (PrjTaskDefineVo) prjTaskDefineVoList.get(j + 1);
                    prjTaskDefineVoList.set(j + 1, (PrjTaskDefineVo) prjTaskDefineVoList.get(j));
                    prjTaskDefineVoList.set(j, p);
                    exchange = true;
                }
            }
            if (!exchange)
                break;
        }
		//获取某阶段下全部材料名称
		Map<Long,String> materialNameMap = materialService.getAllMaterName(Long.valueOf(stateId));
		for (PrjTaskDefineVo prjTaskDefineVo : prjTaskDefineVoList) {
			//根据事项ID获取材料集合
			List<PrjTaskMaterialDefDTO> materialList = prjTaskMaterialDefService.findByTaskId(prjTaskDefineVo.getId());
			//获取   事项材料类  对应集合  并将materialList集合中的类存入到  该集合中
			List<PrjTaskMaterialVo> prjTaskMaterialVos = new ArrayList<PrjTaskMaterialVo>();
			if(materialList != null){
				for (PrjTaskMaterialDefDTO temp:materialList) {
					PrjTaskMaterialVo prjTaskMaterialVo = new PrjTaskMaterialVo();
					BeanCopy.copyProperties(temp, prjTaskMaterialVo, PrjTaskMaterialVo.class);
					prjTaskMaterialVo.setMaterName(materialNameMap.get(temp.getMaterialId()));
					prjTaskMaterialVo.setIsComplete("0");
					prjTaskMaterialVo.setMaterialAddr("");
					prjTaskMaterialVos.add(prjTaskMaterialVo);
				}
			}
			//将  prjTaskMaterialVos 集合存入  事项类中
			prjTaskDefineVo.setPrjTaskMaterialVos(prjTaskMaterialVos);
		}
		project.setPrjTaskDefineVoList(prjTaskDefineVoList);
		//以 事项ID为key，以  阶段材料类  对应集合为value创建map
		Map<Long, List<PrjStageMaterialVo>> orderMap = new LinkedHashMap<Long, List<PrjStageMaterialVo>>();
		for (PrjTaskDefineVo prjTaskDefineVo : prjTaskDefineVoList) {
			List<PrjTaskMaterialVo> prjTaskMaterialVos = prjTaskDefineVo.getPrjTaskMaterialVos();
			List<PrjStageMaterialVo> list = new ArrayList<PrjStageMaterialVo>();
			for (PrjTaskMaterialVo prjTaskMaterialVo : prjTaskMaterialVos) {
				PrjStageMaterialVo prjStageMaterialVo = new PrjStageMaterialVo();
				BeanCopy.copyProperties(prjTaskMaterialVo, prjStageMaterialVo, PrjStageMaterialVo.class);
				list.add(prjStageMaterialVo);
			}
			orderMap.put(prjTaskDefineVo.getId(), list);
		}
		Map<Long, List<PrjStageMaterialVo>> map = project.getPrjStageMaterialVoMap();
		//遍历两个map，将事项下的map中所选中的材料进行替换
		@SuppressWarnings("rawtypes")
		Iterator orderIterator = orderMap.entrySet().iterator();
		while (orderIterator.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<Long, List<PrjStageMaterialVo>> nextOrder = (Entry<Long, List<PrjStageMaterialVo>>) orderIterator.next();
			Long key1 = nextOrder.getKey();
			@SuppressWarnings("rawtypes")
			Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				@SuppressWarnings("unchecked")
				Map.Entry<Long, List<PrjStageMaterialVo>> nextMap = (Entry<Long, List<PrjStageMaterialVo>>) iterator.next();
				Long key2 = nextMap.getKey();
				if(key1 == key2){
					List<PrjStageMaterialVo> PrjStageMaterialVoListOrder = nextOrder.getValue();
					List<PrjStageMaterialVo> PrjStageMaterialVoListMap = nextMap.getValue();
					for( int i = 0 ; i < PrjStageMaterialVoListOrder.size() ; i ++ ){
						for( int j = 0 ; j < PrjStageMaterialVoListMap.size() ; j ++ ){
							if(PrjStageMaterialVoListOrder.get(i).getMaterialId().equals(PrjStageMaterialVoListMap.get(j).getMaterialId())){
								PrjStageMaterialVoListOrder.set(i, PrjStageMaterialVoListMap.get(j));
							}
						}
					}
				}
			}
		}
		project.setPrjStageMaterialVoMap(orderMap);
		return project;
	}

	/**
	 * 通过项目ID获取项目基本信息
	 */
	@Override
	public PrjInstanceVo getPrjInstanceVoById(Long id) {
		PrjInstance prjInstance = prjInstanceMapper.selectByPrimaryKey(id);
		PrjInstanceVo prjInstanceVo = new PrjInstanceVo();
		BeanCopy.copyProperties(prjInstance, prjInstanceVo, PrjInstanceVo.class);
		return prjInstanceVo;
	}

	/**
	 * 获取网上办事项目列表
	 * @param prj
	 * @param pages
	 */
	@Override
	public void getWsbsProjectPage(Project prj, Page<Project> pages,boolean b) {
		PrjInstanceVo vo = prj.getPrjInstanceVo();
		List<PrjInstance> list = null;
        List<PrjInstanceVo> listvo = new ArrayList<PrjInstanceVo>();
        List<Project> project = new ArrayList<Project>();
        PrjInstance record = new PrjInstance();
        if (vo == null) {
            vo = new PrjInstanceVo();
        }
        BeanCopy.copyProperties(vo, record);
        Page<PrjInstance> page = new Page<PrjInstance>();
        PrjInstanceExample example = new PrjInstanceExample();
        Criteria c = example.createCriteria();
        if (StringUtils.isNotBlank(vo.getPrjCode())) {
            c.andPrjCodeLike("%" + vo.getPrjCode() + "%");
        }
        if (StringUtils.isNotBlank(vo.getPrjName())) {
            c.andPrjNameLike("%" + vo.getPrjName() + "%");
        }
        if (vo.getCompanyCode() != null) {
            c.andCompanyCodeEqualTo(vo.getCompanyCode());
        }
		if (vo.getCompany() != null) {
			c.andCompanyLike("%" + vo.getCompany() + "%");
		}
        if (vo.getWsbsUserName() != null) {
            c.andWsbsUserNameEqualTo(vo.getWsbsUserName());
        }
        if (vo.getStartTime() != null && vo.getEndTime() != null)
            c.andCreatTimeBetween(vo.getStartTime(), vo.getEndTime());
        c.andIsDeleteNotEqualTo("1");
        c.andChannelEqualTo("1");//0为后台窗口录入,1为前台用户提交
        //判断是否属于网上办事控制层调用
        if(!b){
        	c.andApplyStateNotEqualTo("0");
        }
        example.setStart(pages.getPageSize() * (pages.getPageNo() - 1));
        example.setPageSize(pages.getPageSize());
        example.setOrderByClause(pages.getOrderBy());
        page.setPageNo(pages.getPageNo());
        page.setPageSize(pages.getPageSize());
        page = prjInstanceMapper.pagedSelectByExample(example, page);
        list = page.getList();
        if (list != null)
            BeanCopy.copyPropertiesForList(list, listvo, PrjInstanceVo.class);
        for (PrjInstanceVo v : listvo) {
            Project p = new Project();
			String prjCode = v.getPrjCode();
			PrjInstance blspPrj = null;
			if(StringUtils.isNotBlank(prjCode)){
				blspPrj = new PrjInstance();
				blspPrj.setPrjCode(prjCode);
				blspPrj.setChannel("0");
				blspPrj.setPrjName(v.getPrjName());
				blspPrj = prjInstanceMapper.selectOneByEntitySelective(blspPrj);
			}
			Long prjId = (blspPrj != null && blspPrj.getId() != 0) ? blspPrj.getId() : v.getId() ;
			if(blspPrj != null && blspPrj.getId() != 0)
				v.setIsPrjComplete(blspPrj.getIsPrjComplete()) ;
			PrjStageDefineVo de = ProjectUtil.getStageByProId(prjId.toString());
            p.setPrjStageDefineVo(de);
            p.setPrjStageVo(ProjectUtil.getStageInstanceByProId(prjId));
            p.setPrjInstanceVo(v);
            project.add(p);
            
            // 建设单位监控，查询暂停事项数
            if (vo.getPauseTimesForMater() == 99) {
            	Integer pauTimes = prjTaskService.countPauseTimes(v.getId()).getObj();
            	v.setPauseTimesForMater(pauTimes == null ? 0 : pauTimes);
            }
        }
        pages.setCount(page.getCount());
        pages.setList(project);
	}

	/**
	 * 修改项目基本信息
	 */
	@Override
	public void updatePrjInstaceVo(PrjInstanceVo vo) {
		PrjInstance record = new PrjInstance();
		BeanCopy.copyProperties(vo, record, PrjInstance.class);
		prjInstanceMapper.updateByPrimaryKey(record);
	}

	@Override
	public void stopProject(StopFormDTO dto) {
		String userId = UserUtils.getUser().getId();
		
		//根据prjId查询是否是网厅提交通过项目是，则对网厅项目同步更改
		PrjInstance prjIn = prjInstanceMapper.selectByPrimaryKey(dto.getPrjInstId());
		PrjInstance wsbsPrjIn = new PrjInstance();
		if(!StringUtils.isBlank(prjIn.getWsbsUserName())){
			PrjInstance record = new PrjInstance();
			record.setPrjCode(prjIn.getPrjCode());
			record.setChannel("1");
			wsbsPrjIn = prjInstanceMapper.selectOneByEntitySelective(record);
		}
		
		PrjInstance record = new PrjInstance();
		BeanCopy.copyProperties(dto, record, PrjInstance.class);
		record.setId(dto.getPrjInstId());
		record.setIsPrjComplete("9"); // 终止
		record.setStopUser(userId);
		record.setStopTime(new Date());
		//同步修改网上办事项目
		if(wsbsPrjIn != null && !"".equals(wsbsPrjIn.getId())){
			wsbsPrjIn.setIsPrjComplete("9"); // 终止
			wsbsPrjIn.setStopUser(userId);
			wsbsPrjIn.setStopTime(new Date());
			wsbsPrjIn.setStopReason(dto.getStopReason());
			wsbsPrjIn.setStopFileName(dto.getStopFileName());
			wsbsPrjIn.setStopFileAddr(dto.getStopFileAddr());
			prjInstanceMapper.updateByPrimaryKeySelective(wsbsPrjIn);
		}
		prjInstanceMapper.updateByPrimaryKeySelective(record);
		
		// 更改阶段状态
		/*PrjStage state = new PrjStage();
		state.setId("");
		state.setStageStatus("9"); // 终止
		state.setUpdateTime(new Date());
		state.setUpdator(userId);
		prjStageMapper.updateByPrimaryKeySelective(state);*/
		PrjTaskExample example = new PrjTaskExample();
		example.createCriteria().andPrjIdEqualTo(prjIn.getId()).andTaskStatusNotEqualTo("0");
		List<PrjTask> list = prjTaskMapper.selectByExample(example);
		for(PrjTask task : list){
			reportPushService.sbBjDataMethod(task,"2");//项目事项中止
		}

	}

	/**
	 * 获取可直接办结的事项集合
	 */
	@Override
	public List<PrjTaskDefineVo> mayFinishTaskList(List<PrjStageMaterialVo> maList) {
		List<PrjTaskDefineVo> list = new ArrayList<PrjTaskDefineVo>();
		//材料编号集合
		List<Long> materialIds = new ArrayList<Long>();
		for (PrjStageMaterialVo prjStageMaterialVo : maList) {
			if(prjStageMaterialVo.getIsComplete().equals("1")){
				if(!materialIds.contains(prjStageMaterialVo.getMaterialId())){
					materialIds.add(prjStageMaterialVo.getMaterialId());
				}
			}
		}
		com.lpcode.modules.blsp.entity.PrjTaskMaterialDefExample example = new com.lpcode.modules.blsp.entity.PrjTaskMaterialDefExample();
		com.lpcode.modules.blsp.entity.PrjTaskMaterialDefExample.Criteria criteria = example.createCriteria();
		criteria.andMaterialIdIn(materialIds);
		List<PrjTaskMaterialDef> prjTaskMaterialDefList = prjTaskMaterialDefMapper.selectByExample(example);
		if(prjTaskMaterialDefList != null && prjTaskMaterialDefList.size() > 0){
			//结果事项编号集合
			List<Long> taskIds = new ArrayList<Long>();
			for (PrjTaskMaterialDef prjTaskMaterialDef : prjTaskMaterialDefList) {
				if(prjTaskMaterialDef.getIsResultMaterial().equals("1")){
					if(!taskIds.contains(prjTaskMaterialDef.getResultTaskId())){
						taskIds.add(prjTaskMaterialDef.getResultTaskId());
					}
				}
			}
			if(taskIds != null && taskIds.size() > 0){
				com.lpcode.modules.blsp.entity.PrjTaskDefineExample prjTaskDefineExample = new com.lpcode.modules.blsp.entity.PrjTaskDefineExample();
				com.lpcode.modules.blsp.entity.PrjTaskDefineExample.Criteria prjTaskDefineCriteria = prjTaskDefineExample.createCriteria();
				prjTaskDefineCriteria.andIdIn(taskIds);
				//获取事项集合
				List<PrjTaskDefine> prjTaskDefineList = prjTaskDefineMapper.selectByExample(prjTaskDefineExample);
				BeanCopy.copyPropertiesForList(prjTaskDefineList, list, PrjTaskDefineVo.class);
			}
		}
		return list;
	}

	/**
	 * 通过项目字段获取项目基本信息
	 */
	@Override
	public PrjInstanceVo getPrjInstanceVoByEntity(PrjInstanceVo prjInstanceVo) {
		PrjInstance prjInstance = new PrjInstance();
		if(prjInstanceVo != null && !"".equals(prjInstanceVo)){
			BeanCopy.copyProperties(prjInstanceVo, prjInstance, PrjInstance.class);
			prjInstance = prjInstanceMapper.selectOneByEntitySelective(prjInstance);
		}
		BeanCopy.copyProperties(prjInstance, prjInstanceVo, PrjInstanceVo.class);
		return prjInstanceVo;
	}

	/**
	 * 通过组织机构码获取实体page
	 */
	@Override
	public void getProjectPageByCompanyCode(Project pro, Page<Project> pages) {
		PrjInstanceVo vo = pro.getPrjInstanceVo();
		List<PrjInstance> list = null;
        List<PrjInstanceVo> listvo = new ArrayList<PrjInstanceVo>();
        List<Project> project = new ArrayList<Project>();
        PrjInstance record = new PrjInstance();
        BeanCopy.copyProperties(vo, record);
        Page<PrjInstance> page = new Page<PrjInstance>();
        PrjInstanceExample example = new PrjInstanceExample();
        Criteria c = example.createCriteria();
        if (vo.getCompanyCode() != null) {
            c.andCompanyCodeEqualTo(vo.getCompanyCode());
        }
        c.andIsDeleteNotEqualTo("1");
        c.andChannelEqualTo("0");//0为后台窗口录入,1为前台用户提交
        example.setStart(pages.getPageSize() * (pages.getPageNo() - 1));
        example.setPageSize(pages.getPageSize());
        example.setOrderByClause(pages.getOrderBy());
        page.setPageNo(pages.getPageNo());
        page.setPageSize(pages.getPageSize());
        page = prjInstanceMapper.pagedSelectByExample(example, page);
        list = page.getList();
        if (list != null)
            BeanCopy.copyPropertiesForList(list, listvo, PrjInstanceVo.class);
        for (PrjInstanceVo v : listvo) {
            Project p = new Project();
            PrjStageDefineVo de = ProjectUtil.getStageByProId(v.getId().toString());
            p.setPrjStageDefineVo(de);
            p.setPrjStageVo(ProjectUtil.getStageInstanceByProId(v.getId()));
            p.setPrjInstanceVo(v);
            project.add(p);
            
            // 建设单位监控，查询暂停事项数
            if (vo.getPauseTimesForMater() == 99) {
            	Integer pauTimes = prjTaskService.countPauseTimes(v.getId()).getObj();
            	v.setPauseTimesForMater(pauTimes == null ? 0 : pauTimes);
            }
        }
        pages.setCount(page.getCount());
        pages.setList(project);
	}

	/**
	 * 通过项目编号查询项目基本信息是否存在
	 */
	@Override
	public Boolean checkIsHasPrjInf(PrjInstanceVo prjInstanceVo) {
		PrjInstanceExample example = new PrjInstanceExample();
		Criteria criteria = example.createCriteria();
		criteria.andIsDeleteEqualTo("0");
		criteria.andPrjCodeEqualTo(prjInstanceVo.getPrjCode());
		int count = prjInstanceMapper.countByExample(example);
		if(count == 2){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 通过项目编号（建设单位）获取基本信息
	 */
	@Override
	public PrjInstanceVo getPrjInstanceVoByPrj(PrjInstanceVo prjInstanceVo) {
		PrjInstanceExample example = new PrjInstanceExample();
		Criteria criteria = example.createCriteria();
		criteria.andIsDeleteEqualTo("0");
		if(prjInstanceVo != null && !"".equals(prjInstanceVo)){
			if(prjInstanceVo.getPrjCode() != null && !"".equals(prjInstanceVo.getPrjCode())){				
				criteria.andPrjCodeEqualTo(prjInstanceVo.getPrjCode());
			}
			if(prjInstanceVo.getCompany() != null && !"".equals(prjInstanceVo.getCompany())){				
				criteria.andCompanyEqualTo(prjInstanceVo.getCompany());
			}
		}
		criteria.andChannelEqualTo("0");
		List<PrjInstance> list = prjInstanceMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			BeanCopy.copyProperties(list.get(0), prjInstanceVo, PrjInstanceVo.class);
		}
		return prjInstanceVo;
	}

	@Override
	public PrjInstanceVo getBlspPrjByPrjCode(String prjCode){
		PrjInstance prjInstance = new PrjInstance();
		prjInstance.setChannel("0");
		prjInstance.setPrjCode(prjCode);
		prjInstance.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
		prjInstance = prjInstanceMapper.selectOneByEntitySelective(prjInstance);
		PrjInstanceVo prjInstanceVo = new PrjInstanceVo();
		BeanCopy.copyProperties(prjInstance, prjInstanceVo , PrjInstanceVo.class);
		return prjInstanceVo;
	}
}
