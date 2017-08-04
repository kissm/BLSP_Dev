package com.lpcode.modules.service.impl.project;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.entity.User;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.constdefine.DicConstants;
import com.lpcode.modules.blsp.entity.*;
import com.lpcode.modules.blsp.mapper.*;
import com.lpcode.modules.service.dimension.holiday.DimHolidayService;
import com.lpcode.modules.service.impl.project.util.FormUtils;
import com.lpcode.modules.service.impl.project.util.ProjectStepUtil;
import com.lpcode.modules.service.impl.project.util.ProjectUtil;
import com.lpcode.modules.service.message.IMessageSMS;
import com.lpcode.modules.service.project.constant.TaskConstants;
import com.lpcode.modules.service.project.dto.PrjTaskTransferDetailDTO;
import com.lpcode.modules.service.project.dto.pinstance.*;
import com.lpcode.modules.service.project.inf.PrjTaskMaterialDefService;
import com.lpcode.modules.service.project.inf.PrjTaskTransferDetailService;
import com.lpcode.modules.service.project.inf.ProjectAcceptServiceInf;
import com.lpcode.modules.service.report.ReportPushService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(readOnly = false)
public class ProjectAcceptService implements ProjectAcceptServiceInf {
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
	private PrjStageMaterialMapper prjStageMaterialMapper;
	@Autowired
	private PrjTaskDefineMapper prjTaskDefineMapper;
	@Autowired
	private PrjTaskMapper prjTaskMapper;
	@Autowired
	private PrjStageDefineMapper prjStageDefineMapper;
	@Autowired
	private PrjStageMaterialDefineMapper prjStageMaterialDefineMapper;
	@Autowired
	private PrjStageMapper prjStageMapper;
	@Autowired
	private PrjTaskTransferDetailService prjTaskTransferDetailService;
	@Autowired
	private DimHolidayService dimHolidayService;
	@Autowired
	private IMessageSMS iMessageSMS;
	@Autowired
	private PrjBusinessAcceptMapper prjBusinessAcceptMapper;
	@Autowired
	private PrjTaskDependencyMapper prjTaskDependencyMapper;
	@Autowired
	private PrjTaskMaterialDefService prjTaskMaterialDefService;
	@Autowired
	private DimMaterialMapper materialDao;
	@Autowired
	private PrjBuildCompanyMapper prjBuildCompanyMapper;
	@Autowired
	private PrjTaskMsgRelDefineMapper prjTaskMsgRelDefineMapper;
	@Autowired
	private PrjTaskMessageMapper prjTaskMessageMapper;
	@Autowired
	private PrjInstanceSnapshotMapper prjInstSnapMapper;
	@Autowired
	private ReportPushService reportPushService;


	@Override
	public void savePrjInstance(PrjInstanceVo vo) {
		PrjInstance prjvo = new PrjInstance();
		BeanCopy.copyProperties(vo, prjvo);

		prjvo.setIsDelete("0");
		prjvo.setCreator(UserUtils.getUser().getId());
		prjvo.setCreatTime(new Date());
		prjvo.setUpdateTime(new Date());
		prjvo.setUpdator(UserUtils.getUser().getId());
		prjvo.setIsWithCert("0");
		prjvo.setIsPrjComplete("0");
		prjvo.setIsStageComplete("0");
		//stageId 为空的时候取第一个阶段的 stageId
		Long stageId = vo.getStageId() == null ? (ProjectUtil.getFirstStage(vo.getPrjType() == null ? DicConstants.PROJECT_TYPE_ZF : vo.getPrjType()).getId()) : vo.getStageId();
		prjvo.setStageId(stageId);
//		prjvo.setStageId(ProjectUtil.getFirstStage(vo.getPrjType() == null ? DicConstants.PROJECT_TYPE_ZF : vo.getPrjType()).getId());
		prjInstanceMapper.insert(prjvo);
		vo.setId(prjvo.getId());
		saveOrUpdateBuild(prjvo);
		保存到prj_stage表
		saveStage(prjvo);
		initTask(prjvo);
		reportPushService.pushProject(vo);//上报项目基本信息
	}
	前台传过来的项目实体对象和项目阶段材料集合
	@Override
	public void savePrjMaterial(List<PrjStageMaterialVo> list, PrjInstanceVo vo) {
		// 保存材料
		List<PrjStageMaterial> records = new ArrayList<PrjStageMaterial>();
		if (list != null && list.size() > 0) {
			项目阶段历史
			PrjStage state = new PrjStage();
			设置阶段ID和项目ID
			state.setStageId(vo.getStageId());
			state.setPrjId(vo.getId());
			通过项目ID和阶段ID查对应的prjStage对象  表里存项目对应的阶段
			state = prjStageMapper.selectOneByEntitySelective(state);
			属性赋值给records
			BeanCopy.copyPropertiesForList(list, records, PrjStageMaterial.class);
			遍历赋值后的阶段材料集合，赋值存入
			for (PrjStageMaterial m : records) {
				m.setCreator(UserUtils.getUser().getId());
				m.setCreatTime(new Date());
				m.setPrjStageInstId(state.getId());
				m.setStageId(vo.getStageId());
				m.setUpdateTime(new Date());
				m.setUpdator(UserUtils.getUser().getId());
				if (m.getIsComplete() == null) {
					m.setIsComplete("0");
				}
                prj_stage_material表里是这个项目对应的阶段所需要的材料
				prjStageMaterialMapper.insert(m);
			}
		}
	}

	@Override
	public void savePrjBusiness() {
		// TODO Auto-generated method stub

	}
    保存所有事项
	@Override
	public void savePrjTask(List<PrjTaskVo> list, PrjInstanceVo vo) {
		// 保存所有的事项
		if (list != null && list.size() > 0) {
			for (PrjTaskVo v : list) {
				Map<Long, PrjTaskVo> m = ProjectStepUtil.getHasStartTask(vo);
				if (m.get(v.getTaskId()) == null) {
					startTaskOther(v, vo);
				}
			}
			// 更改阶段状态
			PrjStage record = new PrjStage();
			record.setPrjId(vo.getId());
			record.setStageId(vo.getStageId());
			record = prjStageMapper.selectOneByEntitySelective(record);
			if (record != null) {
				record.setAcceptId(vo.getAcceptId());
				updateStage(record);
			}

		}
	}

	private void initTask(PrjInstance vo) {
		// 保存所有的事项
		List<PrjTaskDefineVo> l = ProjectStepUtil.getAllTaskByStageGovType(vo.getIsGovType(), vo.getStageId().toString());
		if (l != null && l.size() > 0) {
			for (PrjTaskDefineVo v : l) {
				PrjTask task = new PrjTask();
				BeanCopy.copyProperties(v, task);
				Long stateid = vo.getStageId();
				PrjStage state = new PrjStage();
				state.setStageId(stateid);
				state.setPrjId(vo.getId());
				state = prjStageMapper.selectOneByEntitySelective(state);
				task.setPrjStageInstId(state.getId());// 阶段实例id
				task.setPrjId(vo.getId());
				task.setCurrUser(task.getInitUser());
				task.setTaskStatus("0");// 草稿
				task.setIsWithCert(task.getIsWithCert() == null ? "0" : task.getIsWithCert());
				task.setIsFetched("0");
				task.setAcceptId(vo.getAcceptId());
				task.setCreator(UserUtils.getUser().getId());
				task.setCreatTime(new Date());
				task.setUpdateTime(new Date());
				task.setUpdator(UserUtils.getUser().getId());
				task.setTaskId(task.getId());
				prjTaskMapper.insert(task);
			}
		}
	}

	private boolean isHaveTask(PrjTaskVo v) {
		PrjTask task = new PrjTask();
		task.setPrjId(v.getPrjId());
		task.setPrjStageInstId(v.getPrjStageInstId());
		task.setTaskId(v.getTaskId());
		List<PrjTask> list = prjTaskMapper.selectByEntitySelective(task);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	private void startTaskOther(PrjTaskVo t, PrjInstanceVo vo) {
		Date start = dimHolidayService.findNextWorkDay();
		PrjTask record = new PrjTask();
		record.setTaskId(t.getTaskId());
		record.setPrjId(t.getPrjId());
		Long stateid = vo.getStageId();
		PrjStage state = new PrjStage();
		state.setStageId(stateid);
		state.setPrjId(vo.getId());
		state = prjStageMapper.selectOneByEntitySelective(state);
		record.setPrjStageInstId(state.getId());
		PrjTask task = prjTaskMapper.selectOneByEntitySelective(record);
		task.setInitUser(t.getInitUser());
		// 时间比对
		if (!StringUtils.isBlank(task.getInitUser())) {
			task.setTaskStartTime(start);
			List<PrjTaskTimerDefineVo> taskvoList = ProjectUtil.getTaskTimerByTaskId(task.getTaskId().toString());
			PrjTaskTimerDefineVo taskvo = new PrjTaskTimerDefineVo();
			if (taskvoList != null && taskvoList.size() == 1) {
				taskvo = taskvoList.get(0);
			} else if (taskvoList.size() > 1) {
				for (PrjTaskTimerDefineVo v : taskvoList) {
					if (vo.getPriceType() != null && v.getPriceType() != null && vo.getPriceType().equals(v.getPriceType())) {
						taskvo = v;
						break;
					} else if (vo.getUseageType() != null && v.getUseageType() != null && vo.getUseageType().equals(v.getUseageType())) {
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
			task.setAcceptId(vo.getAcceptId());
			// 判断当前事项是否依赖其它事项，如果是，事项状态设置为：未启动
			boolean hasDepTask = hasDepTask(task.getTaskId(), vo.getId());
			if (hasDepTask) {
				task.setTaskStatus(TaskConstants.TASK_STATUS_NOSTART);// 未启动
			} else {
				task.setTaskStatus(TaskConstants.TASK_STATUS_AUDIT);// 开始
				// 发送短信 （未启动的项目不发送短信）
				User user = UserUtils.get(task.getInitUser());
				if (user != null && StringUtils.isNotBlank(user.getMobile())) {
					this.sendSms(user.getMobile(), "S010001");
				}
			}
		}
		prjTaskMapper.updateByPrimaryKeySelective(task);
		//@HeroP 事项启动时,对建设单位的通知 仅用于政府项目
		if(vo.getPrjType().equals("1")){
			tellBuildRepresentative(t);
		}

		if (!StringUtils.isBlank(task.getInitUser())) {
			PrjTaskTransferDetailDTO dto = new PrjTaskTransferDetailDTO();
			dto.setCreator(UserUtils.getUser().getId());
			dto.setCreatTime(new Date());
			dto.setPrjTaskInstId(task.getId());
			dto.setReceiver(task.getCurrUser());
			// dto.setTransfer(UserUtils.getUser().getId());
			prjTaskTransferDetailService.saveTransfer(dto);
		}
		if(vo.getRealStartTime() == null){
			vo.setRealStartTime(start);
			prjInstanceMapper.updateByPrimaryKey(vo);
		}
		reportPushService.sbSlDataMethod(t,vo);
	}

	/**
	 * 根据事项的分组及当前组内事项状态情况,在事项启动时,对建设单位的通知
	 * 仅用于政府项目,当word中右侧事项框中的某一个事项开始启动时,向建设单位发送短信通知, 一个事项框只发一次短信
	 * @param prjTaskVo
	 */
	private void tellBuildRepresentative(PrjTaskVo prjTaskVo) {
		//通过taskID 得到通知企业的信息分组
		PrjTaskMsgRelDefine vo = new PrjTaskMsgRelDefine();
		vo.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
		List<PrjTaskMsgRelDefine> msgRelList =  prjTaskMsgRelDefineMapper.selectByEntitySelective(vo);
		Long taskId = prjTaskVo.getTaskId();
		PrjTaskMsgRelDefine thisTaskRelDef = null;
		for (PrjTaskMsgRelDefine prjTaskMsgRelDefine : msgRelList){
			if (prjTaskMsgRelDefine.getTaskId().equals(taskId)){
				thisTaskRelDef = prjTaskMsgRelDefine;
				break;
			}
		}
		if (thisTaskRelDef == null)
			return;
		Long thisTaskMsgId = thisTaskRelDef.getTaskMsgId();
		List<Long> taskIdValues = new ArrayList<Long>();
		for (PrjTaskMsgRelDefine prjTaskMsgRelDefine : msgRelList){
			if (prjTaskMsgRelDefine.getTaskMsgId().equals(thisTaskMsgId)){
				taskIdValues.add(prjTaskMsgRelDefine.getTaskId());
			}
		}
		//通过分组下的taskId是得到项目下的事项,暂存状态的事项数量
		PrjTaskExample prjTaskExample = new PrjTaskExample();
		prjTaskExample.createCriteria().andPrjIdEqualTo(prjTaskVo.getPrjId()).andTaskIdIn(taskIdValues).
				andTaskStatusEqualTo(TaskConstants.TASK_STATUS_DRAFT);
		List<PrjTask> prjTaskList = prjTaskMapper.selectByExample(prjTaskExample);

		//判断是不是该组的第一个事项,如果不是则返回,如果是则发送短信进行通知,1 2 组数据事项没有互斥,3 4组事项有互斥数据
		if(((thisTaskMsgId == 1 || thisTaskMsgId == 2) && prjTaskList.size() < taskIdValues.size() - 1) ||
			((thisTaskMsgId == 3 || thisTaskMsgId == 4) && prjTaskList.size() < taskIdValues.size() - 2))
			return;

		PrjTaskMessage prjTaskMessages = prjTaskMessageMapper.selectByPrimaryKey(thisTaskMsgId);
		PrjInstance prjInstance = prjInstanceMapper.selectByPrimaryKey(prjTaskVo.getPrjId());
		PrjTaskDefine prjTaskDefine = prjTaskDefineMapper.selectByPrimaryKey(prjTaskVo.getTaskId());
		String mobile = prjInstance.getAgentMphone();

		//组装参数拼接短信发送
		Map<String,String> taskMessage = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		taskMessage.put("taskName", prjTaskDefine.getTaskName());
		taskMessage.put("matrialContent", prjTaskMessages.getMatrialContent());
		taskMessage.put("finalDate", sdf.format(new Date()));
		if(StringUtils.isNotBlank(mobile)){
			sendSmsByTplIdAndMap(mobile, "S010009", taskMessage);
		}
	}

	/**
	 * 保存公司信息
	 * @param prjvo
	 */
	private void saveOrUpdateBuild(PrjInstance prjvo) {
		Date now = new Date();
		String companyCode = prjvo.getCompanyCode();
		if (StringUtils.isBlank(companyCode)) {
			return;
		}
		PrjBuildCompany prjBuildCompany = new PrjBuildCompany();
		BeanCopy.copyProperties(prjvo, prjBuildCompany, PrjBuildCompany.class);
		prjBuildCompany.setId(null);
		prjBuildCompany.setUpdateTime(now);
		prjBuildCompany.setIsDelete("0");
		PrjBuildCompanyExample example = new PrjBuildCompanyExample();
		example.createCriteria().andCompanyCodeEqualTo(companyCode);
		List<PrjBuildCompany> buildCompanyList = prjBuildCompanyMapper.selectByExample(example);
		if (buildCompanyList.size() == 0) {
			prjBuildCompany.setCreatTime(now);
			prjBuildCompany.setCreator(UserUtils.getUser().getId());
			prjBuildCompanyMapper.insert(prjBuildCompany);
		} else if (buildCompanyList.size() == 1) {
			prjBuildCompany.setId(buildCompanyList.get(0).getId());
			prjBuildCompany.setUpdator(UserUtils.getUser().getId());
			prjBuildCompanyMapper.updateByPrimaryKey(prjBuildCompany);
		}
		reportPushService.pushCompany(prjBuildCompany);

	}

	@Override
	public void updatePrjInstance(PrjInstanceVo vo) {
		// TODO Auto-generated method stub
		saveOrUpdateBuild(vo);
		prjInstanceMapper.updateByPrimaryKeySelective(vo);
		reportPushService.pushProject(vo);//上报项目基本信息
		/**
		 * 企业项目当前阶段不存在时,更新项目要新建此阶段信息,并初始化对应的事项
		 */
		if(DicConstants.PROJECT_TYPE_QY.equals(vo.getPrjType()) && !hasPrjStage(vo)){
			saveStage(vo);
			initTask(vo);
		}
	}

	/**
	 * 查看该项目的当前阶段是否存在
	 * @param vo
	 * @return
	 */
	private boolean hasPrjStage(PrjInstanceVo vo){

		PrjStage prjStage = new PrjStage();
		prjStage.setPrjId(vo.getId());
		prjStage.setStageId(vo.getStageId());
		List<PrjStage> list = prjStageMapper.selectByEntitySelective(prjStage);
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}

	}
	@Override
	public void updatePrjMaterial(List<PrjStageMaterialVo> list, PrjInstanceVo v) {
		// 修改材料
		if (list != null && list.size() > 0) {
			Long prjid = v.getId();
			Long stateId = v.getStageId();
			if (prjid == null || stateId == null)
				return;
			com.lpcode.modules.blsp.entity.PrjStageMaterialExample example = new com.lpcode.modules.blsp.entity.PrjStageMaterialExample();
			com.lpcode.modules.blsp.entity.PrjStageMaterialExample.Criteria criteria = example.createCriteria();
			criteria.andStageIdEqualTo(stateId);
			criteria.andPrjIdEqualTo(prjid);
			prjStageMaterialMapper.deleteByExample(example);
			this.savePrjMaterial(list, v);
		}
	}

	@Override
	public void updatePrjBusiness() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePrjTask(List<PrjTaskVo> list) {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public void startPrjTask(List<PrjTaskVo> list, PrjInstanceVo vo) {

	}

	public void sendSms(String mobile, String code) {
		if (StringUtils.isBlank(mobile)) {
			return;
		}
		new Thread() {
			@Override
			public void run() {
				iMessageSMS.sendSmsByTplId(mobile, code);
			}
		}.start();
	}

	/**
	 *
	 * @param mobile
	 * @param code
	 */
	public void sendSmsByTplIdAndMap(String mobile, String code, Map<String,String> map) {
		if (StringUtils.isBlank(mobile)) {
			return;
		}
		new Thread() {
			@Override
			public void run() {
				iMessageSMS.sendSmsByTplIdAndMap(mobile, code, map);
			}
		}.start();
	}

	/**
	 * 判断当前事项是否依赖其它事项，并且该事项未办结，如果是，返回true，否则返回false
	 *
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
	public String createPrjCode(String type) {
		PrjCodeGeneratorVo pvo = ProjectUtil.getProjectCode(type);
		if (pvo.getSeq() != null) {
			pvo.setSeq(pvo.getSeq() + 1);
		}
		return pvo.toString();
	}

	@Override
	public String lockPrjCode(PrjCodeGeneratorVo vo, String code) {
		if (vo == null)
			return null;
		if (StringUtils.isBlank(code)) {
			return null;
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
				break;
			}
		}
		return vo.toString();
	}
	保存项目当前阶段的数据
	public void saveStage(PrjInstance vo) {
		PrjStage state = new PrjStage();
		state.setPrjId(vo.getId());
		state.setStageCode(FormUtils.getFormNo());// 受理编号
		state.setStageId(vo.getStageId());
		state.setCreator(UserUtils.getUser().getId());
		state.setCreatTime(new Date());
		state.setUpdateTime(new Date());
		state.setUpdator(UserUtils.getUser().getId());
		state.setStageStatus("0");// 资料待补齐
		state.setAcceptId(vo.getAcceptId());
		prjStageMapper.insert(state);
	}

	public void updateStage(PrjStage stage) {
		stage.setStageStatus("1");// 待审批
		stage.setStageStartTime(new Date());
		prjStageMapper.updateByPrimaryKeySelective(stage);
	}


	@Override
	public void updateAccept(PrjBusinessAcceptVo vo) {
		PrjBusinessAccept a = new PrjBusinessAccept();
		BeanCopy.copyProperties(vo, a);
		prjBusinessAcceptMapper.updateByPrimaryKeySelective(a);
	}

	@Override
	public boolean enableProjectName(String prjName) {
		PrjInstanceExample example = new PrjInstanceExample();
		example.createCriteria().andPrjNameEqualTo(prjName).andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL).andChannelEqualTo("0");
		List<PrjInstance> prjInstanceList = prjInstanceMapper.selectByExample(example);
		if (prjInstanceList != null && prjInstanceList.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void savePrjInstanceTemp(PrjInstanceVo vo) {
		PrjInstance prjvo = new PrjInstance();
		vo.setIsDelete("1");
		vo.setCreator(UserUtils.getUser().getId());
		vo.setCreatTime(new Date());
		vo.setUpdateTime(new Date());
		vo.setUpdator(UserUtils.getUser().getId());
		vo.setIsWithCert("0");
		vo.setIsPrjComplete("0");
		vo.setIsStageComplete("0");
		vo.setStageId(ProjectUtil.getFirstStage(vo.getPrjType() == null ? DicConstants.PROJECT_TYPE_ZF : vo.getPrjType()).getId());
		BeanCopy.copyProperties(vo, prjvo);
		prjInstanceMapper.insert(prjvo);
		vo.setId(prjvo.getId());
		saveOrUpdateBuild(prjvo);
		saveStage(prjvo);
		initTask(prjvo);
	}

	@Override
	public void savePrjInstanceTempByWsbs(PrjInstanceVo vo) {
		PrjInstance prjvo = new PrjInstance();
		vo.setIsDelete("1");
		vo.setCreator(UserUtils.getUser().getId());
		vo.setCreatTime(new Date());
		vo.setUpdateTime(new Date());
		vo.setUpdator(UserUtils.getUser().getId());
		vo.setIsWithCert("0");
		vo.setIsPrjComplete("0");
		vo.setIsStageComplete("0");
		//stageId 为空的时候取第一个阶段的 stageId
		Long stageId = vo.getStageId() == null ? (ProjectUtil.getFirstStage(vo.getPrjType() == null ? DicConstants.PROJECT_TYPE_ZF : vo.getPrjType()).getId()) : vo.getStageId();
		vo.setStageId(stageId);
		BeanCopy.copyProperties(vo, prjvo);
		prjInstanceMapper.insert(prjvo);
		vo.setId(prjvo.getId());
		saveOrUpdateBuild(prjvo);
		saveStage(prjvo);
		initTask(prjvo);
	}

	@Override
	public boolean enablePrjCode(String prjCode) {
		PrjInstanceExample example = new PrjInstanceExample();
		example.createCriteria().andPrjCodeEqualTo(prjCode).andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
		List<PrjInstance> prjInstanceList = prjInstanceMapper.selectByExample(example);
		if (prjInstanceList != null && prjInstanceList.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public List<PrjStageDefineVo> getAllStage() {

		PrjStageDefineExample example = new PrjStageDefineExample();
		example.createCriteria().andStageTypeEqualTo("2");
		example.setOrderByClause(" id ");
		List<PrjStageDefine> prjStageDefineList = prjStageDefineMapper.selectByExample(example);
		List<PrjStageDefineVo> prjStageVoList = new ArrayList<>();
		BeanCopy.copyPropertiesForList(prjStageDefineList,prjStageVoList,PrjStageDefineVo.class );
		return prjStageVoList;
	}

	@Override
	public PrjInstanceVo checkPrjByPrjCode(String prjCode) {
		if(StringUtils.isNotBlank(prjCode)){
			PrjInstanceVo pv = new PrjInstanceVo();
			PrjInstance record = new PrjInstance();
			record.setPrjCode(prjCode);
			record.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
			record.setChannel("0");
			PrjInstance prjInstance = prjInstanceMapper.selectOneByEntitySelective(record);
			if(prjInstance != null){
				BeanCopy.copyProperties(prjInstance, pv, PrjInstanceVo.class);
				return pv;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

	@Override
	public void insertInstanceSnapshot(PrjInstanceVo blspPrj) {
		PrjInstanceSnapshot snapshot = new PrjInstanceSnapshot();
		BeanCopy.copyProperties(blspPrj, snapshot, PrjInstanceSnapshot.class);
		snapshot.setPrjId(snapshot.getId());
		snapshot.setId(null);
		snapshot.setCreator(UserUtils.getUser().getId());
		snapshot.setCreatTime(new Date());
		snapshot.setUpdator(UserUtils.getUser().getId());
		snapshot.setUpdateTime(new Date());
		prjInstSnapMapper.insert(snapshot);
	}

}
