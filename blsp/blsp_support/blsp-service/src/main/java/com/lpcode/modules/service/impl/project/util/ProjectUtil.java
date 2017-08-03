package com.lpcode.modules.service.impl.project.util;

import com.framework.core.base.page.Page;
import com.framework.core.constants.BaseCode;
import com.framework.core.result.ListResult;
import com.framework.core.utils.BeanCopy;
import com.framework.core.utils.DateUtil;
import com.framework.core.utils.StringUtil;
import com.framework.osp.common.utils.SpringContextHolder;
import com.framework.osp.modules.sys.dao.OfficeDao;
import com.framework.osp.modules.sys.entity.Office;
import com.framework.osp.modules.sys.entity.User;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.entity.*;
import com.lpcode.modules.blsp.entity.PrjInstanceExample.Criteria;
import com.lpcode.modules.blsp.mapper.*;
import com.lpcode.modules.blsp.vo.*;
import com.lpcode.modules.blsp.vo.ProjectVo;
import com.lpcode.modules.service.dimension.holiday.DimHolidayService;
import com.lpcode.modules.service.form.IFactoryFormService;
import com.lpcode.modules.service.project.dto.PrjTaskHandleDTO;
import com.lpcode.modules.service.project.dto.Project;
import com.lpcode.modules.service.project.dto.pinstance.*;
import com.lpcode.modules.service.project.inf.PrjStageDefineService;
import com.lpcode.modules.service.project.inf.PrjTaskService;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class ProjectUtil {
    private static PrjTaskDefineMapper taskDao = SpringContextHolder.getBean(PrjTaskDefineMapper.class);
    private static PrjTaskMapper taskInstanceDao = SpringContextHolder.getBean(PrjTaskMapper.class);
    private static PrjStageDefineMapper StateDao = SpringContextHolder.getBean(PrjStageDefineMapper.class);
    private static PrjStageMapper stateInstanceDao = SpringContextHolder.getBean(PrjStageMapper.class);
    private static PrjInstanceMapper projectDao = SpringContextHolder.getBean(PrjInstanceMapper.class);
    private static PrjStageMaterialDefineMapper stagedefineMaterialDao = SpringContextHolder.getBean(PrjStageMaterialDefineMapper.class);
    private static PrjStageMaterialMapper stageMaterialDao = SpringContextHolder.getBean(PrjStageMaterialMapper.class);
    private static DimMaterialMapper materialDao = SpringContextHolder.getBean(DimMaterialMapper.class);
    private static FormRfBjshMapper FormRfBjshDao = SpringContextHolder.getBean(FormRfBjshMapper.class);
    private static FormRfYdjsBjspMapper FormRfYdjsBjspDao = SpringContextHolder.getBean(FormRfYdjsBjspMapper.class);
    private static PrjTaskService prjTaskService = SpringContextHolder.getBean(PrjTaskService.class);
    private static PrjTaskTimerDefineMapper prjTaskTimerDefineDao = SpringContextHolder.getBean(PrjTaskTimerDefineMapper.class);
    private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);
    private static PrjCodeGeneratorMapper PrjCodeGeneratorDao = SpringContextHolder.getBean(PrjCodeGeneratorMapper.class);
    private static PrjBusinessAcceptMapper PrjBusinessAcceptDao = SpringContextHolder.getBean(PrjBusinessAcceptMapper.class);
    private static PrjTaskTransferDetailMapper PrjTaskTransferDetailDao = SpringContextHolder.getBean(PrjTaskTransferDetailMapper.class);
    private static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static ProjectMapper prjDao = SpringContextHolder.getBean(ProjectMapper.class);
    private static PrjStageDefineService prjStageDefineService = SpringContextHolder.getBean(PrjStageDefineService.class);
    private static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
    private static PrjTaskMaterialDefMapper prjTaskMaterialDefDao = SpringContextHolder.getBean(PrjTaskMaterialDefMapper.class);
    private static PrjTaskMaterialDefMapperSelf prjTaskMaterialDefDaoSelf = SpringContextHolder.getBean(PrjTaskMaterialDefMapperSelf.class);
    private static PrjTaskPauseDetailMapper prjTaskPauseDetailMapper = SpringContextHolder.getBean(PrjTaskPauseDetailMapper.class);
    private static DimHolidayService dimHolidayService = SpringContextHolder.getBean(DimHolidayService.class);
    private static PrjFormTaskRealMapper prjFormTaskRealMapper = SpringContextHolder.getBean(PrjFormTaskRealMapper.class);
    private static IFactoryFormService factoryFormService = SpringContextHolder.getBean(IFactoryFormService.class);
    private static FormDefineMapper formDefineMapper = SpringContextHolder.getBean(FormDefineMapper.class);

    /**
     * @param prjId
     * @return 根据项目id获取某阶段所有审批事项
     */
    public static List<PrjTaskVo> getAllTaskByPrjId(Long prjId) {
        if (prjId == null)
            return null;
        PrjStageVo vo = getStageInstanceByProId(prjId);
        return getAllTaskByInstanceStage(vo.getId().toString(), prjId.toString());
    }

    /**
     * @param acceptId
     * @return
     */
    public static Map<Long, List<PrjStageMaterialVo>> getAllTaskMaterByAcceptId(Long acceptId) {
        Map<Long, List<PrjStageMaterialVo>> map = new HashMap<Long, List<PrjStageMaterialVo>>();
        if (acceptId == null)
            return map;
        PrjTask define = new PrjTask();
        define.setAcceptId(acceptId);
        define.setTaskStatus("1");
        List<PrjTask> t = taskInstanceDao.selectByEntitySelective(define);
        if (t != null && t.size() > 0)
            for (PrjTask task : t) {
                com.lpcode.modules.blsp.entity.PrjStageMaterialExample example = new com.lpcode.modules.blsp.entity.PrjStageMaterialExample();
                com.lpcode.modules.blsp.entity.PrjStageMaterialExample.Criteria c = example.createCriteria();
                c.andTaskIdEqualTo(task.getTaskId());
                c.andPrjStageInstIdEqualTo(task.getPrjStageInstId());
                List<PrjStageMaterial> listMatr = stageMaterialDao.selectByExample(example);
                List<PrjStageMaterialVo> list = new ArrayList<PrjStageMaterialVo>();
                if (listMatr != null && listMatr.size() > 0)
                    BeanCopy.copyPropertiesForList(listMatr, list, PrjStageMaterialVo.class);
                //得到事项中所有材料并替换
                PrjTaskMaterialDef prjTaskMaterialDef = new PrjTaskMaterialDef();
                prjTaskMaterialDef.setTaskId(task.getTaskId());
                List<PrjTaskMaterialDef> prjTaskMaterialDefList = prjTaskMaterialDefDao.selectByEntitySelective(prjTaskMaterialDef);
                List<PrjStageMaterialVo> prjStageMaterialVoList = new ArrayList<PrjStageMaterialVo>();
                if(prjTaskMaterialDefList != null && prjTaskMaterialDefList.size() > 0){
                	BeanCopy.copyPropertiesForList(prjTaskMaterialDefList, prjStageMaterialVoList, PrjStageMaterialVo.class);
                }
                map.put(task.getTaskId(), prjStageMaterialVoList);
                if (prjStageMaterialVoList != null && prjStageMaterialVoList.size() > 0){
                    for ( int i = 0 ; i < prjStageMaterialVoList.size() ; i++ ) {
                    	prjStageMaterialVoList.get(i).setIsComplete("0");
                    	if (list != null && list.size() > 0){
                    		for (PrjStageMaterialVo l : list) {
                    			if(prjStageMaterialVoList.get(i).getMaterialId().equals(l.getMaterialId())){
                    				prjStageMaterialVoList.set(i, l);
                    			}
                    			DimMaterial mater = new DimMaterial();
                                mater.setId(prjStageMaterialVoList.get(i).getMaterialId());
                                mater = materialDao.selectOneByEntitySelective(mater);
                                prjStageMaterialVoList.get(i).setMaterName(mater.getName());
                    		}
                    	}
                    }
                }
            }
        return map;
    }

    /**
     * @param taskId
     * @param prjId
     * @param stageInsId
     * @return 具体taskVo
     */
    public static PrjTaskVo getPrjTaskVo(Long taskId, Long prjId, Long stageInsId) {
        if (prjId == null)
            return null;
        PrjTask define = new PrjTask();
        PrjTaskVo vo = new PrjTaskVo();
        define.setPrjStageInstId(stageInsId);
        define.setPrjId(prjId);
        define.setTaskId(taskId);
        PrjTask task = taskInstanceDao.selectOneByEntitySelective(define);
        if (task != null) {
            BeanCopy.copyProperties(task, vo);
        }
        return vo;
    }

    /**
     * @param defineId
     * @return 获取某阶段所有审批事项
     */
    public static PrjTaskDefineVo getPrjTaskDefineVo(String defineId) {
        PrjTaskDefineVo vo = new PrjTaskDefineVo();
        PrjTaskDefine d = taskDao.selectByPrimaryKey(Long.parseLong(defineId));
        if (d != null)
            BeanCopy.copyProperties(d, vo);
        return vo;
    }

    /**
     * @param stateId
     * @return 获取某阶段所有审批事项
     */
    public static List<PrjTaskDefineVo> getAllTaskByStage(Long stateId) {

        List<PrjTaskDefineVo> task = new ArrayList<PrjTaskDefineVo>();
        if (stateId == null || stateId.equals("")) {
            PrjTaskDefine define = new PrjTaskDefine();
            PrjStageDefineVo vo = getFirstStage("1");
            事项设置阶段ID
            define.setStageId(vo.getId());
            define.setIsValid("1");
            define.setIsDelete("0");

            List<PrjTaskDefine> list = taskDao.selectByEntitySelective(define);
            if (list != null)
                BeanCopy.copyPropertiesForList(list, task, PrjTaskDefineVo.class);
        } else {

            PrjTaskDefine define = new PrjTaskDefine();
            define.setStageId(stateId);
            define.setIsValid("1");
            define.setIsDelete("0");
            List<PrjTaskDefine> list = taskDao.selectByEntitySelective(define);
            if (list != null)
                BeanCopy.copyPropertiesForList(list, task, PrjTaskDefineVo.class);
        }
        return task;
    }

    /**
     * 根据项目类型获取首阶段所有审批事项
     *
     * @param prjType 1、政府；2、企业
     * @return
     */
    public static List<PrjTaskDefineVo> getPrjFirstStageTask(String prjType) {
        List<PrjTaskDefineVo> task = new ArrayList<PrjTaskDefineVo>();
        PrjTaskDefine define = new PrjTaskDefine();
        PrjStageDefineVo vo = getFirstStage(prjType);
        define.setStageId(vo.getId());
        define.setIsValid("1");
        define.setIsDelete("0");
        List<PrjTaskDefine> list = taskDao.selectByEntitySelective(define);
        if (list != null)
            BeanCopy.copyPropertiesForList(list, task, PrjTaskDefineVo.class);
        return task;
    }

    /**
     * @param taskDefinId
     * @return 获取定义事项的时常设置
     */
    public static List<PrjTaskTimerDefineVo> getTaskTimerByTaskId(String taskDefinId) {
        List<PrjTaskTimerDefineVo> vo = new ArrayList<PrjTaskTimerDefineVo>();
        PrjTaskTimerDefine de = new PrjTaskTimerDefine();
        de.setTaskId(Long.parseLong(taskDefinId));
        List<PrjTaskTimerDefine> list = prjTaskTimerDefineDao.selectByEntitySelective(de);
        if (list != null)
            BeanCopy.copyPropertiesForList(list, vo, PrjTaskTimerDefineVo.class);
        return vo;
    }

    /**
     * @param stateInstanceId
     * @param prjId
     * @return 获取某阶段所有审批事项
     */
    public static List<PrjTaskVo> getAllTaskByInstanceStage(String stateInstanceId, String prjId) {
        if (stateInstanceId == null || prjId == null)
            return null;
        List<PrjTaskVo> task = new ArrayList<PrjTaskVo>();
        PrjTask define = new PrjTask();
        define.setPrjStageInstId(Long.parseLong(stateInstanceId));
        define.setPrjId(Long.parseLong(prjId));
        List<PrjTask> list = taskInstanceDao.selectByEntitySelective(define);
        if (list != null)
            BeanCopy.copyPropertiesForList(list, task, PrjTaskVo.class);
        for (PrjTaskVo de : task) {
            Long id = de.getTaskId();
            PrjTaskDefine mater = new PrjTaskDefine();
            mater.setId(id);
            if (id == null)
                continue;
            mater = taskDao.selectOneByEntitySelective(mater);// 缓存处理,待优化
            if (mater == null)
                continue;
            de.setTaskName(mater.getTaskName());
            de.setDeptId(mater.getDeptId());

            User curr = UserUtils.get(de.getCurrUser());// 待优化
            if (curr != null)
                de.setCurrUserName(curr.getName());
            User init = UserUtils.get(de.getInitUser());
            if (init != null)
                de.setInitUserName(init.getName());
            Office office = officeDao.get(mater.getDeptId());
            if (office != null) {
                de.setDeptName(office.getName());
            }
            de.setTaskCode(mater.getTaskCode());
            //判断是否是暂停阶段，若是则查出暂停类型和时间加入到de对象中
            if("2".equals(de.getTaskStatus())){
            	PrjTaskPauseDetailExample example = new PrjTaskPauseDetailExample();
				example.setOrderByClause("PAUSE_START_TIME");
				example.createCriteria().andPrjTaskInstIdEqualTo(de.getId());
				List<PrjTaskPauseDetail> prjTaskPauseDetailList = prjTaskPauseDetailMapper.selectByExample(example);
				if(prjTaskPauseDetailList != null){
					PrjTaskPauseDetail prjTaskPauseDetail = prjTaskPauseDetailList.get(prjTaskPauseDetailList.size()-1);
					de.setTaskPauseStartTime(prjTaskPauseDetail.getPauseStartTime());
	            	de.setTaskPauseType(prjTaskPauseDetail.getPauseType());
				}
            }
        }
        return task;
    }

    /**
     * @param projectId 根据项目ID 获取该项目所处阶段
     */
    public static PrjStageDefineVo getStageByProId(String projectId) {
        if (projectId == null || projectId.equals("")) {
            return getFirstStage("1");
        } else {
            PrjInstanceVo prjInstance = getInstance(Long.parseLong(projectId));
            PrjStageDefineVo vo = new PrjStageDefineVo();
            PrjStageDefine record = new PrjStageDefine();
            record.setId(prjInstance.getStageId());
            record = StateDao.selectOneByEntitySelective(record);
            if (record != null) {
                BeanCopy.copyProperties(record, vo);
                // 获取下一阶段ID
                Long nextStageId = prjStageDefineService.getNextStageId(record.getId()).getObj();
                if (nextStageId != null)
                    vo.setNextStageId(nextStageId);
            }
            return vo;
        }
    }

    /**
     * @param id
     * @return 根据项目ID 获取该项目所处阶段
     */
    public static PrjStageDefineVo getStageDefineById(String id) {
        PrjStageDefineVo vo = new PrjStageDefineVo();
        PrjStageDefine record = new PrjStageDefine();
        record = StateDao.selectByPrimaryKey(Long.parseLong(id));
        if (record != null)
            BeanCopy.copyProperties(record, vo);
        return vo;
    }

    /**
     * @param taskid
     * @return 根据taskid 获取审批记录
     */
    public static ListResult<PrjTaskHandleDTO> getHandleBytaskId(String taskid) {
        ListResult<PrjTaskHandleDTO> handleList = prjTaskService.taskHandleList(Long.parseLong(taskid)); // 事项操作列表
        return handleList;
    }

    /**
     * 根据项目ID 获取该项目所处阶段
     *
     * @return
     */
    public static List<PrjStageDefineVo> getAllStageList() {
        List<PrjStageDefineVo> list = new ArrayList<PrjStageDefineVo>();
        PrjStageDefineExample example = new PrjStageDefineExample();
        com.lpcode.modules.blsp.entity.PrjStageDefineExample.Criteria c = example.createCriteria();
        c.andStageTypeEqualTo("2");
        example.setOrderByClause("pre_stage_id asc");
        List<PrjStageDefine> listd = StateDao.selectByExample(example);
        if (listd != null)
            BeanCopy.copyPropertiesForList(listd, list, PrjStageDefineVo.class);
        return list;
    }

    /**
     * 根据项目ID 获取该项目阶段实例
     *
     * @param projectId
     * @return
     */
    public static PrjStageVo getStageInstanceByProId(Long projectId) {
        if (projectId == null || projectId.equals("")) {
            return null;
        } else {
            PrjInstanceVo prjInstance = getInstance(projectId);
            PrjStageVo vo = new PrjStageVo();
            PrjStage record = new PrjStage();
            如果有项目有阶段
            if (prjInstance.getStageId() != null)
                record.setStageId(prjInstance.getStageId());
            record.setPrjId(projectId);
            获取阶段历史实体
            record = stateInstanceDao.selectOneByEntitySelective(record);
            将vo值赋值给record
            if (record != null)
                BeanCopy.copyProperties(record, vo);
            return vo;
        }
    }

    /**
     * 根据项目ID 获取该项目阶段实例
     *
     * @param projectId
     * @return
     */
    public static List<PrjStageVo> getStageInsListByProId(String projectId) {
        List<PrjStageVo> list = new ArrayList<PrjStageVo>();
        if (projectId == null || projectId.equals("")) {
            return null;
        } else {
            项目阶段
            PrjStage record = new PrjStage();
            PrjStageExample实体类用来where查询
            PrjStageExample example = new PrjStageExample();
            example.createCriteria().andPrjIdEqualTo(Long.parseLong(projectId));
            example.setOrderByClause(" STAGE_ID ");
            拿到项目阶段实例集合
            List<PrjStage> lists = stateInstanceDao.selectByExample(example);
            加源码看。
            if (lists != null)
                BeanCopy.copyPropertiesForList(lists, list, PrjStageVo.class);
            return list;
        }
    }

    /**
     * 获取项目的第一个阶段
     *
     * @return
     */
    // public static PrjStageDefineVo getFirstStage() {
    // PrjStageDefineVo vo = new PrjStageDefineVo();
    // PrjStageDefine record = new PrjStageDefine();
    // record.setPreStageId(0L);
    // record = StateDao.selectOneByEntitySelective(record);
    // if (record != null)
    // BeanCopy.copyProperties(record, vo);
    // return vo;
    // }

    /**
     * 获取项目的第一个阶段
     *
     * @param stageType 阶段类型：1、政府；2、企业
     * @return
     */
    public static PrjStageDefineVo getFirstStage(String stageType) {

        PrjStageDefineVo vo = new PrjStageDefineVo();  有下一个阶段ID
        PrjStageDefine record = new PrjStageDefine();   项目四个阶段

        record.setPreStageId(0L);
        record.setStageType(stageType);  阶段类型1


        根据阶段类型查
        record = StateDao.selectOneByEntitySelective(record);
        把vo的值赋值给record
        if (record != null)
            BeanCopy.copyProperties(record, vo);
        return vo;
    }

    /**
     * 获取项目实体
     *
     * @return
     */
    public static PrjInstanceVo getInstance(Long prjId) {
        if (prjId == null)
            return null;
        PrjInstanceVo vo = new PrjInstanceVo();
        PrjInstance prjInstance = new PrjInstance();
        prjInstance.setId(prjId);
        prjInstance = projectDao.selectOneByEntitySelective(prjInstance);
        if (prjInstance != null)
            BeanCopy.copyProperties(prjInstance, vo);
        return vo;
    }

    /**
     * 获取项目实体
     *
     * @param acceptId
     * @return
     */
    public static PrjInstanceVo getInstanceByAcceptId(Long acceptId) {
        if (acceptId == null)
            return null;
        PrjInstanceVo vo = new PrjInstanceVo();
        PrjInstance prjInstance = new PrjInstance();
        prjInstance.setAcceptId(acceptId);
        prjInstance = projectDao.selectOneByEntitySelective(prjInstance);
        if (prjInstance != null)
            BeanCopy.copyProperties(prjInstance, vo);
        return vo;
    }

    /**
     * 获取项目实体
     *
     * @param pid
     * @return
     */
    public static Project getProject(Long pid) {
        Project project = new Project();
        if (pid == null) {
            Long id = ProjectUtil.getFirstStage("1").getId();
            List<PrjStageMaterialDefineVo> list = ProjectUtil.getStageDefineMaterList(id);
            project.setPrjStageMaterialDefineVoList(list);
            Map<Long, List<PrjStageMaterialDefineVo>> map = new LinkedHashMap<Long, List<PrjStageMaterialDefineVo>>();
            project.setPrjStageMaterialDefineVoMap(map);
            if (list != null && list.size() > 0) {
                for (PrjStageMaterialDefineVo v : list) {
                    List<PrjStageMaterialDefineVo> taskList = map.get(v.getTaskId() == null ? 0l : v.getTaskId());
                    if (taskList == null) {
                        taskList = new ArrayList<PrjStageMaterialDefineVo>();
                        taskList.add(v);
                        map.put(v.getTaskId() == null ? 0L : v.getTaskId(), taskList);
                    } else {
                        taskList.add(v);
                    }
                }
            }
            project.setPrjTaskDefineVoList(ProjectUtil.getAllTaskByStage(id));
        } else {
            PrjInstanceVo vo = ProjectUtil.getInstance(pid);
            project.setPrjInstanceVo(vo);
            if (vo.getStageId() == null) {
                project.setPrjStageMaterialVoList(ProjectUtil.getDraftStageMaterList(pid));
            } else {
                project.setPrjStageMaterialVoList(ProjectUtil.getStageMaterList(vo.getStageId(), pid));
            }
            project.setPrjStageVo(ProjectUtil.getStageInstanceByProId(pid));
            Map<Long, List<PrjStageMaterialVo>> map = new LinkedHashMap<Long, List<PrjStageMaterialVo>>();
            project.setPrjStageMaterialVoMap(map);
            if (project.getPrjStageMaterialVoList() != null && project.getPrjStageMaterialVoList().size() > 0) {
                for (PrjStageMaterialVo v : project.getPrjStageMaterialVoList()) {
                    List<PrjStageMaterialVo> taskList = map.get(v.getTaskId() == null ? 0l : v.getTaskId());
                    if (taskList == null) {
                        taskList = new ArrayList<PrjStageMaterialVo>();
                        taskList.add(v);
                        map.put(v.getTaskId() == null ? 0L : v.getTaskId(), taskList);
                    } else {
                        taskList.add(v);
                    }
                }
            }
            Long id = vo.getStageId();
            project.setPrjStageMaterialDefineVoList(ProjectUtil.getStageDefineMaterList(id));
//            project.setFormRfBjshVo(ProjectUtil.getFormRfBjshVo(pid));
//            project.setFormRfYdjsBjspVo(ProjectUtil.getFormRfYdjsBjsp(pid));
//            FormRfYdjsJgsbVo jgsb = ProjectUtil.getFormRfYdjsJgsb(pid);
//            project.setFormRfYdjsJgsbVo(jgsb);
//            if (jgsb != null)
//                project.setFormRfJsxmqkbVo(ProjectUtil.getFormRfJsxmqkb(jgsb.getId() == null ? null : jgsb.getId().toString()));
        }
        return project;
    }

    /**
     * @param pid
     * @param stageId
     * @return
     */
    public static Project getEnterProject(String pid, String stageId) {
        Project project = new Project();
        if (pid != null) {
            PrjInstanceVo vo = ProjectUtil.getInstance(Long.parseLong(pid));
            project.setPrjInstanceVo(vo);
        }
        if (stageId == null) {
            stageId = ProjectUtil.getFirstStage("2").getId().toString();
        }
        List<PrjStageMaterialDefineVo> list = ProjectUtil.getStageDefineMaterList(Long.parseLong(stageId));
        project.setPrjStageMaterialDefineVoList(list);
        Map<Long, List<PrjStageMaterialDefineVo>> map = new TreeMap<Long, List<PrjStageMaterialDefineVo>>();
        project.setPrjStageMaterialDefineVoMap(map);
        if (list != null && list.size() > 0) {
            for (PrjStageMaterialDefineVo v : list) {
                List<PrjStageMaterialDefineVo> taskList = map.get(v.getTaskId() == null ? 0l : v.getTaskId());
                if (taskList == null) {
                    taskList = new ArrayList<PrjStageMaterialDefineVo>();
                    taskList.add(v);
                    map.put(v.getTaskId() == null ? 0L : v.getTaskId(), taskList);
                } else {
                    taskList.add(v);
                }
            }
        }
        return project;
    }

    /**
     * 获取某阶段的业务表单和材料列表，项目查询使用
     *
     * @param pid
     * @param stageId
     * @return
     */
    public static Project getPrjStageFormAndMaterials(Long pid, Long stageId) {
        Project project = new Project();
        PrjInstanceVo vo = ProjectUtil.getInstance(pid);
        project.setPrjInstanceVo(vo);

        List<PrjStageMaterialVo> stmlist = ProjectUtil.getStageMaterList(stageId, pid);

        Map<Long, List<PrjStageMaterialVo>> map = new TreeMap<Long, List<PrjStageMaterialVo>>();
        project.setPrjStageMaterialVoMap(map);
        if (stmlist != null && stmlist.size() > 0) {
            for (PrjStageMaterialVo v : stmlist) {
                List<PrjStageMaterialVo> taskList = map.get(v.getTaskId() == null ? 0l : v.getTaskId());
                if (taskList == null) {
                    taskList = new ArrayList<PrjStageMaterialVo>();
                    taskList.add(v);
                    map.put(v.getTaskId() == null ? 0L : v.getTaskId(), taskList);
                } else {
                    taskList.add(v);
                }
            }
        }

//        // 该阶段是否有人防表单
//        List<PrjTaskDefineVo> taskList = getAllTaskByStage(stageId);
//        if (taskList != null) {
//            for (PrjTaskDefineVo task : taskList) {
//                if ("10007200174172973914440404".equals(task.getTaskCode())) {
//                    project.setFormRfYdjsBjspVo(getFormRfYdjsBjsp(pid));
//                }
//                if ("10007200374172973914440404".equals(task.getTaskCode())) {
//                    project.setFormRfBjshVo(getFormRfBjshVo(pid));
//                }
//            }
//        }

        return project;
    }

    /**
     * 获取当前阶段配置需要的所有材料
     *
     * @return
     */
    public static List<PrjStageMaterialDefineVo> getStageDefineMaterList(Long stageId) {
        if (stageId == null)
            return null;
        List<PrjStageMaterialDefineVo> list = new ArrayList<PrjStageMaterialDefineVo>();
        List<PrjStageMaterialDefineVo> nlist = new ArrayList<PrjStageMaterialDefineVo>();
        List<PrjTaskDefineVo> taskList = getAllTaskByStage(stageId);
        if (taskList != null && taskList.size() > 0) {
            List<Long> taskIds = new ArrayList<Long>();
            for (PrjTaskDefineVo vo : taskList) {
                taskIds.add(vo.getId());
            }

//			PrjTaskMaterialDefExample dexample = new PrjTaskMaterialDefExample();
//			dexample.createCriteria().andTaskIdIn(taskIds);
            List<PrjTaskMaterialDef> materList = prjTaskMaterialDefDaoSelf.selectByTaskIds(taskIds);

            if (materList != null)
                BeanCopy.copyPropertiesForList(materList, list, PrjStageMaterialDefineVo.class);

            for (PrjStageMaterialDefineVo de : list) {
                Long id = de.getMaterialId();
                DimMaterial mater = new DimMaterial();
                mater.setId(id);
                mater = materialDao.selectOneByEntitySelective(mater);
                if (mater.getIsValid().equals("1")) {
                    nlist.add(de);
                } else {
                    continue;
                }
                de.setMaterName(mater.getName());
                de.setStageId(stageId);
            }
        }

        return nlist;
    }

    /**
     * 获取当前阶段配置需要的所有材料
     *
     * @return
     *
     * 		public static List
     *         <PrjStageMaterialDefineVo> getStageDefineMaterList(String
     *         stageId) { if (stageId == null) return null; List
     *         <PrjStageMaterialDefineVo> list = new ArrayList
     *         <PrjStageMaterialDefineVo>(); List
     *         <PrjStageMaterialDefineVo> nlist = new ArrayList
     *         <PrjStageMaterialDefineVo>(); PrjStageMaterialDefine record = new
     *         PrjStageMaterialDefine();
     *         record.setStageId(Long.parseLong(stageId));
     *         record.setIsDelete("0"); List<PrjStageMaterialDefine> materList =
     *         stagedefineMaterialDao.selectByEntitySelective(record); if
     *         (materList != null) BeanCopy.copyPropertiesForList(materList,
     *         list, PrjStageMaterialDefineVo.class); for
     *         (PrjStageMaterialDefineVo de : list) { Long id =
     *         de.getMaterialId(); DimMaterial mater = new DimMaterial();
     *         mater.setId(id); mater =
     *         materialDao.selectOneByEntitySelective(mater); if
     *         (mater.getIsValid().equals("1")) { nlist.add(de); } else {
     *         continue; } de.setMaterName(mater.getName()); } return nlist; }
     */

    /**
     * 获取此项目目前阶段所有上传的材料
     *
     * @return
     */
    public static List<PrjStageMaterialVo> getStageMaterList(Long stageId, Long prjId) {
        if (stageId == null)
            return null;
        List<PrjStageMaterialVo> list = new ArrayList<PrjStageMaterialVo>();
        PrjStageMaterial record = new PrjStageMaterial();
        record.setStageId(stageId);
        record.setPrjId(prjId);
        List<PrjStageMaterial> materList = stageMaterialDao.selectByEntitySelective(record);

        if (materList != null)
            BeanCopy.copyPropertiesForList(materList, list, PrjStageMaterialVo.class);

        List<PrjTaskDefineVo> taskList = getAllTaskByStage(stageId);
        List<Long> taskIds = new ArrayList<Long>();
        Map<Long, List<PrjStageMaterialVo>> orderMap = new LinkedHashMap<>();
        if (taskList != null && taskList.size() > 0) {
            for (PrjTaskDefineVo vo : taskList) {
                orderMap.put(vo.getId(), new ArrayList<PrjStageMaterialVo>());
            }
        }
        for (PrjStageMaterialVo de : list) {
            orderMap.get(de.getTaskId()).add(de);
        }
        List<PrjStageMaterialVo> orderList = new ArrayList<PrjStageMaterialVo>();
        for (List<PrjStageMaterialVo> l : orderMap.values()) {
            orderList.addAll(l);
        }
        for (PrjStageMaterialVo de : orderList) {
            Long id = de.getMaterialId();
            DimMaterial mater = new DimMaterial();
            mater.setId(id);
            mater = materialDao.selectOneByEntitySelective(mater);
            de.setMaterName(mater.getName());
        }
        return orderList;
    }

    /**
     * 获取此项目目前阶段所有上传的材料
     *
     * @param prjId
     * @return
     */
    public static List<PrjStageMaterialVo> getStageMaterList(Long prjId) {
        if (prjId == null)
            return null;
        PrjInstanceVo vo = ProjectUtil.getInstance(prjId);
        if (vo == null)
            return null;
        List<PrjStageMaterialVo> list = new ArrayList<PrjStageMaterialVo>();
        PrjStageMaterial record = new PrjStageMaterial();
        record.setStageId(vo.getStageId());
        record.setPrjId(prjId);
        List<PrjStageMaterial> materList = stageMaterialDao.selectByEntitySelective(record);
        if (materList != null)
            BeanCopy.copyPropertiesForList(materList, list, PrjStageMaterialVo.class);
        for (PrjStageMaterialVo de : list) {
            Long id = de.getMaterialId();
            DimMaterial mater = new DimMaterial();
            mater.setId(id);
            mater = materialDao.selectOneByEntitySelective(mater);
            de.setMaterName(mater.getName());
        }
        return list;
    }

    /**
     * 获取此项目目前阶段所有上传的材料
     *
     * @param prjId
     * @return
     */
    public static List<PrjStageMaterialVo> getDraftStageMaterList(Long prjId) {
        if (prjId == null)
            return null;
        List<PrjStageMaterialVo> list = new ArrayList<PrjStageMaterialVo>();
        PrjStageMaterial record = new PrjStageMaterial();
        record.setPrjId(prjId);
        List<PrjStageMaterial> materList = stageMaterialDao.selectByEntitySelective(record);
        if (materList != null)
            BeanCopy.copyPropertiesForList(materList, list, PrjStageMaterialVo.class);
        for (PrjStageMaterialVo de : list) {
            Long id = de.getMaterialId();
            DimMaterial mater = new DimMaterial();
            mater.setId(id);
            mater = materialDao.selectOneByEntitySelective(mater);
            de.setMaterName(mater.getName());
        }
        return list;
    }

    /**
     * 获取此项目表单FormRfBjshVo 的结果
     *
     * @param pid
     * @return
     */
    public static FormRfBjshVo getFormRfBjshVo(String pid) {
        if (pid == null)
            return null;
        FormRfBjsh record = new FormRfBjsh();
        FormRfBjshVo FormRfBjshVo = new FormRfBjshVo();
        record.setPrjId(Long.parseLong(pid));
        record = FormRfBjshDao.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, FormRfBjshVo);
        return FormRfBjshVo;
    }

    /**
     * 获取此项目表单FormRfBjshVo 的结果
     *
     * @param pid
     * @return
     */
    public static FormRfYdjsBjspVo getFormRfYdjsBjsp(String pid) {
        if (pid == null)
            return null;
        FormRfYdjsBjsp record = new FormRfYdjsBjsp();
        FormRfYdjsBjspVo vo = new FormRfYdjsBjspVo();
        record.setPrjId(Long.parseLong(pid));
        record = FormRfYdjsBjspDao.selectOneByEntitySelective(record);
        if (record == null)
            return null;
        BeanCopy.copyProperties(record, vo);
        return vo;
    }

    /**
     * 获取项目列表
     *
     * @param vo
     * @return
     */
    public static List<Project> getProject(PrjInstanceVo vo) {
        List<PrjInstance> list = null;
        List<PrjInstanceVo> listvo = new ArrayList<PrjInstanceVo>();
        List<Project> project = new ArrayList<Project>();
        PrjInstance record = new PrjInstance();
        if (vo == null) {
            vo = new PrjInstanceVo();
            vo.setPrjType("1");
        }
        BeanCopy.copyProperties(vo, record);
        list = projectDao.selectByEntitySelective(record);
        BeanCopy.copyPropertiesForList(list, listvo, PrjInstanceVo.class);
        for (PrjInstanceVo v : listvo) {
            Project p = new Project();
            PrjStageDefine de = new PrjStageDefine();
            de.setId(v.getStageId());
            if (v.getStageId() != null) {
                de = StateDao.selectOneByEntitySelective(de);// 需要缓存
                PrjStageDefineVo dvo = new PrjStageDefineVo();
                if (de != null)
                    BeanCopy.copyProperties(de, dvo);
                p.setPrjStageDefineVo(dvo);
            } else {
                p.setPrjStageDefineVo(getFirstStage("1"));
            }
            p.setPrjInstanceVo(v);
            project.add(p);
        }
        return project;
    }

    /**
     * 获取项目列表
     *
     * @param vo
     * @param pages
     */
    public static void getProjectPage(PrjInstanceVo vo, Page<Project> pages) {
        List<PrjInstance> list = null;
        List<PrjInstanceVo> listvo = new ArrayList<PrjInstanceVo>();
        List<Project> project = new ArrayList<Project>();
        PrjInstance record = new PrjInstance();
        if (vo == null) {
            vo = new PrjInstanceVo();
            vo.setPrjType("1");
        }
        BeanCopy.copyProperties(vo, record);
        Page<PrjInstance> page = new Page<PrjInstance>();
        PrjInstanceExample example = new PrjInstanceExample();
        Criteria c = example.createCriteria();
        if (vo.getPrjCode() != null) {
            c.andPrjCodeLike("%" + vo.getPrjCode() + "%");
        }
        if (vo.getPrjName() != null) {
            c.andPrjNameLike("%" + vo.getPrjName() + "%");
        }
        if (vo.getCompany() != null) {
            c.andCompanyLike("%" + vo.getCompany() + "%");
        }
        if (vo.getCompanyCode() != null) {
            c.andCompanyCodeEqualTo(vo.getCompanyCode());
        }
        if (vo.getStartTime() != null && vo.getEndTime() != null)
            c.andCreatTimeBetween(vo.getStartTime(), vo.getEndTime());
        if (!"3".equals(vo.getPrjType()))
            c.andPrjTypeEqualTo(vo.getPrjType());
        c.andIsDeleteNotEqualTo("1");
        c.andChannelEqualTo("0");//0为后台窗口录入,1为前台用户提交
        example.setStart(pages.getPageSize() * (pages.getPageNo() - 1));
        example.setPageSize(pages.getPageSize());
        example.setOrderByClause(pages.getOrderBy());
        page.setPageNo(pages.getPageNo());
        page.setPageSize(pages.getPageSize());
        page = projectDao.pagedSelectByExample(example, page);
        list = page.getList();
        if (list != null)
            BeanCopy.copyPropertiesForList(list, listvo, PrjInstanceVo.class);
        for (PrjInstanceVo v : listvo) {
            Project p = new Project();
            PrjStageDefineVo de = getStageByProId(v.getId().toString());
            p.setPrjStageDefineVo(de);
            p.setPrjStageVo(getStageInstanceByProId(v.getId()));
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
     * @param seqType
     * @return 获取项目编号
     */
    public static PrjCodeGeneratorVo getProjectCode(String seqType) {
        Date d = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        PrjCodeGenerator record = new PrjCodeGenerator();
        PrjCodeGeneratorVo vo = new PrjCodeGeneratorVo();
        record.setMonth(month);
        record.setYear(year);
        record.setSeqType(seqType);// 1：政府，2：企业 ，3：网厅
        record = PrjCodeGeneratorDao.selectOneByEntitySelective(record);
        if (record != null)
            BeanCopy.copyProperties(record, vo);
        return vo;
    }

    /**
     * @param vo
     * @return 锁定序列号
     */
    public static Integer lockProjectCode(PrjCodeGeneratorVo vo) {
        if (vo == null)
            return null;
        PrjCodeGenerator record = new PrjCodeGenerator();
        BeanCopy.copyProperties(vo, record);
        PrjCodeGeneratorExample example = new PrjCodeGeneratorExample();
        example.createCriteria().andSeqEqualTo(vo.getSeq() - 1).andIdEqualTo(vo.getId());
        Integer i = PrjCodeGeneratorDao.updateByExampleSelective(record, example);
        return i;
    }

    /**
     * @param vo
     * @return 获取受理实体
     */
    public static PrjBusinessAcceptVo getPrjBusinessAccept(PrjBusinessAcceptVo vo) {
        if (vo == null)
            return null;
        PrjBusinessAccept record = new PrjBusinessAccept();
        BeanCopy.copyProperties(vo, record);
        PrjBusinessAccept a = PrjBusinessAcceptDao.selectOneByEntitySelective(record);
        if (a != null)
            BeanCopy.copyProperties(a, vo);
        return vo;
    }

    /**
     * @param tskid
     * @return 获取受理记录
     */
    public static PrjTaskTransferDetail getPrjTaskTransferDetail(Long tskid) {
        if (tskid == null)
            return null;
        PrjTaskTransferDetail record = new PrjTaskTransferDetail();
        record.setPrjTaskInstId(tskid);
        record.setIsFinished("1");
        record = PrjTaskTransferDetailDao.selectOneByEntitySelective(record);
        return record;
    }

    /**
     * @param vo
     * @return 根据受理实体获取具体事项
     */
    public static List<PrjTaskVo> getPrjTask(PrjBusinessAcceptVo vo) {
        if (vo == null || vo.getId() == null)
            return null;
        List<PrjTaskVo> task = new ArrayList<PrjTaskVo>();
        PrjTask define = new PrjTask();
        define.setAcceptId(vo.getId());
        List<PrjTask> list = taskInstanceDao.selectByEntitySelective(define);
        if (list != null)
            BeanCopy.copyPropertiesForList(list, task, PrjTaskVo.class);
        for (PrjTaskVo de : task) {
            Long id = de.getTaskId();
            PrjTaskDefine mater = new PrjTaskDefine();
            mater.setId(id);
            if (id == null)
                continue;
            mater = taskDao.selectOneByEntitySelective(mater);// 缓存处理,待优化
            if (mater == null)
                continue;
            de.setTaskName(mater.getTaskName());
            de.setDeptId(mater.getDeptId());
            User curr = UserUtils.get(de.getCurrUser());// 待优化
            if (curr != null)
                de.setCurrUserName(curr.getName());
            User init = UserUtils.get(de.getInitUser());
            if (init != null)
                de.setInitUserName(init.getName());
            Office office = officeDao.get(mater.getDeptId());
            if (office != null) {
                de.setDeptName(office.getName());
            }
            de.setTaskCode(mater.getTaskCode());
        }
        return task;
    }

    /**
     * @param taskid
     * @return 待审批
     */
    public static Integer getTaskPendingStatus(String taskid) {
        String status = "1";
        com.lpcode.modules.blsp.entity.PrjTaskExample example = new com.lpcode.modules.blsp.entity.PrjTaskExample();
        com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdEqualTo(Long.parseLong(taskid));
        criteria.andTaskStartTimeGreaterThan(new Date());
        criteria.andTaskStatusEqualTo(status);
        int i = taskInstanceDao.countByExample(example);
        return i;
    }

    /**
     * @param page
     * @param taskid
     * @return 待审批列表
     */
    public static Page<TaskVo> getTaskPendingList(Page<TaskVo> page, String taskid) {
        Page<PrjTask> taskPage = new Page<PrjTask>();
        String status = "1";
        com.lpcode.modules.blsp.entity.PrjTaskExample example = new com.lpcode.modules.blsp.entity.PrjTaskExample();
        com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdEqualTo(Long.parseLong(taskid));
        criteria.andTaskStartTimeGreaterThan(new Date());
        criteria.andTaskStatusEqualTo(status);
        taskPage.setPageNo(page.getPageNo());
        taskPage.setPageSize(page.getPageSize());
        taskPage = taskInstanceDao.pagedSelectByExample(example, taskPage);
        page.setCount(taskPage.getCount());
        List<TaskVo> list = new ArrayList<TaskVo>();
        if (taskPage.getList() != null && taskPage.getList().size() > 0)
            for (PrjTask pjt : taskPage.getList()) {
                TaskVo vo = new TaskVo();
                if (pjt.getTaskRealEndtime() != null)
                	vo.setEndTime(pjt.getTaskRealEndtime());
                if (pjt.getTaskEndTime() != null)
                	vo.setNeedEndTime(pjt.getTaskEndTime());
                if (pjt.getTaskStartTime() != null)
                	vo.setStartTime(pjt.getTaskStartTime());
                PrjTaskDefine mater = new PrjTaskDefine();
                mater.setId(pjt.getTaskId());
                mater = taskDao.selectOneByEntitySelective(mater);// 缓存处理,待优化
                vo.setTaskName(mater.getTaskName());
                vo.setStatus("待审批");
                vo.setProjectName(getInstance(pjt.getPrjId()).getPrjName());
                list.add(vo);
            }
        page.setList(list);
        return page;
    }

    /**
     * @param taskid
     * @return 待审批
     */
    public static Integer getTaskPendingStatus(List<Long> taskid) {
        String status = "1";
        com.lpcode.modules.blsp.entity.PrjTaskExample example = new com.lpcode.modules.blsp.entity.PrjTaskExample();
        com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria criteria = example.createCriteria();
        criteria.andAcceptIdIn((taskid));
        criteria.andTaskStartTimeGreaterThan(new Date());
        criteria.andTaskStatusEqualTo(status);
        int i = taskInstanceDao.countByExample(example);
        return i;
    }

    /**
     * @param page
     * @param taskid
     * @return 办理中
     */
    public static Page<TaskVo> getTaskCheckingList(Page<TaskVo> page, String taskid) {
        Page<PrjTask> taskPage = new Page<PrjTask>();
        String status = "1";
        com.lpcode.modules.blsp.entity.PrjTaskExample example = new com.lpcode.modules.blsp.entity.PrjTaskExample();
        com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdEqualTo(Long.parseLong(taskid));
        criteria.andTaskStatusEqualTo(status);
        taskPage.setPageNo(page.getPageNo());
        taskPage.setPageSize(page.getPageSize());
        taskPage = taskInstanceDao.pagedSelectByExample(example, taskPage);
        page.setCount(taskPage.getCount());
        List<TaskVo> list = new ArrayList<TaskVo>();
        List<Long> prjIds = new ArrayList<Long>();
        if (taskPage.getList() != null && taskPage.getList().size() > 0){
        	//项目终止的情况过滤
	        for (PrjTask prjTask : taskPage.getList()) {
	        	prjIds.add(prjTask.getPrjId());
			}
	        com.lpcode.modules.blsp.entity.PrjInstanceExample prjExample = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
	        Criteria prjCriteria = prjExample.createCriteria();
	        prjCriteria.andIdIn(prjIds);
	        List<PrjInstance> prjList = projectDao.selectByExample(prjExample);
	        for(int i=0;i<taskPage.getList().size();i++){
	        	for (PrjInstance prjInstance : prjList) {
					if(taskPage.getList().get(i).getPrjId().equals(prjInstance.getId())){
						if(StringUtil.isNotEmpty(prjInstance.getStopUser())){    						
							taskPage.getList().remove(i);
						}
					}
				}
			}
            for (PrjTask pjt : taskPage.getList()) {
                TaskVo vo = new TaskVo();
                if (pjt.getTaskRealEndtime() != null)
                	vo.setEndTime(pjt.getTaskRealEndtime());
                if (pjt.getTaskEndTime() != null)
                	vo.setNeedEndTime(pjt.getTaskEndTime());
                if (pjt.getTaskStartTime() != null)
                	vo.setStartTime(pjt.getTaskStartTime());
                PrjTaskDefine mater = new PrjTaskDefine();
                mater.setId(pjt.getTaskId());
                mater = taskDao.selectOneByEntitySelective(mater);// 缓存处理,待优化
                vo.setTaskName(mater.getTaskName());
                vo.setProjectName(getInstance(pjt.getPrjId()).getPrjName());
                vo.setStatus(pjt.getTaskStatus());
                //当前处理人
                vo.setUserName(UserUtils.get(pjt.getCurrUser().toString()).getName());
                //超时天数
                int calDatePeriod = dimHolidayService.calDatePeriod(new Date(), pjt.getTaskEndTime(), pjt.getTimeType());
                vo.setOverDate(String.valueOf(calDatePeriod));
                if(pjt.getTaskStatus().equals("2")){
                	//获取暂停类型
                    PrjTaskPauseDetailExample prjTaskPause = new PrjTaskPauseDetailExample();
                    prjTaskPause.setOrderByClause("PAUSE_START_TIME");
                    prjTaskPause.createCriteria().andPrjTaskInstIdEqualTo(pjt.getId());
    				List<PrjTaskPauseDetail> prjTaskPauseDetailList = prjTaskPauseDetailMapper.selectByExample(prjTaskPause);
    				if(prjTaskPauseDetailList != null){
    					PrjTaskPauseDetail prjTaskPauseDetail = prjTaskPauseDetailList.get(prjTaskPauseDetailList.size()-1);
    					vo.setTaskPauseStartTime(prjTaskPauseDetail.getPauseStartTime());
    	            	vo.setTaskPauseType(prjTaskPauseDetail.getPauseType());
    				}
                }
                vo.setPrjStageInstId(pjt.getId());
                vo.setTaskInstanceId(pjt.getId());
                list.add(vo);
            }
        }
        page.setList(list);
        return page;
    }

    /**
     * @param taskid
     * @return 办理中
     */
    public static Integer getTaskCheckingStatus(String taskid) {
        String status = "1";
        com.lpcode.modules.blsp.entity.PrjTaskExample example = new com.lpcode.modules.blsp.entity.PrjTaskExample();
        com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdEqualTo(Long.parseLong(taskid));
        criteria.andTaskStatusEqualTo(status);
        //项目终止过滤
        List<PrjTask> prjTaskList = taskInstanceDao.selectByExample(example);
        int i = 0;
        if(prjTaskList != null && prjTaskList.size()>0){
        	List<Long> prjIds = new ArrayList<Long>();
            for (PrjTask prjTask : prjTaskList) {
            	prjIds.add(prjTask.getPrjId());
    		}
            com.lpcode.modules.blsp.entity.PrjInstanceExample prjExample = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
            Criteria prjCriteria = prjExample.createCriteria();
            prjCriteria.andIdIn(prjIds);
            List<PrjInstance> prjList = projectDao.selectByExample(prjExample);
            for (PrjTask prjTask : prjTaskList) {
            	for (PrjInstance prjInstance : prjList) {
    				if(prjTask.getPrjId().equals(prjInstance.getId())){
    					if(StringUtil.isEmpty(prjInstance.getStopUser())){    						
    						i++;
    					}
    				}
    			}
    		}
        }
        return i;
    }

    /**
     * @param taskid
     * @return 办理中
     */
    public static Integer getTaskCheckingStatus(List<Long> taskid) {
        String status = "1";
        com.lpcode.modules.blsp.entity.PrjTaskExample example = new com.lpcode.modules.blsp.entity.PrjTaskExample();
        com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdIn(taskid);
        criteria.andTaskStatusEqualTo(status);
        //项目终止过滤
        List<PrjTask> prjTaskList = taskInstanceDao.selectByExample(example);
        int i = 0;
        if(prjTaskList != null && prjTaskList.size()>0){
        	List<Long> prjIds = new ArrayList<Long>();
            for (PrjTask prjTask : prjTaskList) {
            	prjIds.add(prjTask.getPrjId());
    		}
            com.lpcode.modules.blsp.entity.PrjInstanceExample prjExample = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
            Criteria prjCriteria = prjExample.createCriteria();
            prjCriteria.andIdIn(prjIds);
            List<PrjInstance> prjList = projectDao.selectByExample(prjExample);
            for (PrjTask prjTask : prjTaskList) {
            	for (PrjInstance prjInstance : prjList) {
    				if(prjTask.getPrjId().equals(prjInstance.getId())){
    					if(StringUtil.isEmpty(prjInstance.getStopUser())){    						
    						i++;
    					}
    				}
    			}
    		}
        }
        return i;
    }

    /**
     * 办结
     *
     * @param page
     * @param from
     * @param to
     * @param taskid
     * @return
     */
    public static Page<TaskVo> getTaskOverList(Page<TaskVo> page, Date from, Date to, String taskid) {
        Page<PrjTask> taskPage = new Page<PrjTask>();
        String status = "4";
        com.lpcode.modules.blsp.entity.PrjTaskExample example = new com.lpcode.modules.blsp.entity.PrjTaskExample();
        com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdEqualTo(Long.parseLong(taskid));
        criteria.andTaskStatusEqualTo(status);
        criteria.andTaskRealEndtimeBetween(from, to);
        taskPage.setPageNo(page.getPageNo());
        taskPage.setPageSize(page.getPageSize());
        taskPage = taskInstanceDao.pagedSelectByExample(example, taskPage);
        page.setCount(taskPage.getCount());
        List<TaskVo> list = new ArrayList<TaskVo>();
        List<Long> prjIds = new ArrayList<Long>();
        if (taskPage.getList() != null && taskPage.getList().size() > 0){
        	//项目终止的情况过滤
	        for (PrjTask prjTask : taskPage.getList()) {
	        	prjIds.add(prjTask.getPrjId());
			}
	        com.lpcode.modules.blsp.entity.PrjInstanceExample prjExample = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
	        Criteria prjCriteria = prjExample.createCriteria();
	        prjCriteria.andIdIn(prjIds);
	        List<PrjInstance> prjList = projectDao.selectByExample(prjExample);
	        for(int i=0;i<taskPage.getList().size();i++){
	        	for (PrjInstance prjInstance : prjList) {
					if(taskPage.getList().get(i).getPrjId().equals(prjInstance.getId())){
						if(StringUtil.isNotEmpty(prjInstance.getStopUser())){    						
							taskPage.getList().remove(i);
						}
					}
				}
			}
            for (PrjTask pjt : taskPage.getList()) {
                TaskVo vo = new TaskVo();
                if (pjt.getTaskRealEndtime() != null)
                    //vo.setEndTime(longSdf.format(pjt.getTaskRealEndtime()));
                	vo.setEndTime(pjt.getTaskRealEndtime());
                if (pjt.getTaskEndTime() != null)
                    //vo.setNeedEndTime(longSdf.format(pjt.getTaskEndTime()));
                	vo.setNeedEndTime(pjt.getTaskEndTime());
                if (pjt.getTaskStartTime() != null)
                    //vo.setStartTime(longSdf.format(pjt.getTaskStartTime()));
                	vo.setStartTime(pjt.getTaskStartTime());
                PrjTaskDefine mater = new PrjTaskDefine();
                mater.setId(pjt.getTaskId());
                mater = taskDao.selectOneByEntitySelective(mater);// 缓存处理,待优化
                vo.setTaskName(mater.getTaskName());
                vo.setStatus("办结");
                //逾期天数
                int calDatePeriod = dimHolidayService.calDatePeriod(pjt.getTaskRealEndtime(), pjt.getTaskEndTime(), pjt.getTimeType());
                vo.setOverDate(String.valueOf(calDatePeriod));
                vo.setPrjStageInstId(pjt.getId());
                vo.setTaskInstanceId(pjt.getId());
                list.add(vo);
                vo.setProjectName(getInstance(pjt.getPrjId()).getPrjName());
            }
        }
        page.setList(list);
        return page;
    }

    /**
     * @param from
     * @param to
     * @return 办结
     */
    public static Integer getTaskOverStatus(Date from, Date to, String taskid) {
        String status = "4";
        com.lpcode.modules.blsp.entity.PrjTaskExample example = new com.lpcode.modules.blsp.entity.PrjTaskExample();
        com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdEqualTo(Long.parseLong(taskid));
        criteria.andTaskStatusEqualTo(status);
        criteria.andUpdateTimeBetween(from, to);
        //加入项目终止情况的过滤
        List<PrjTask> prjTaskList = taskInstanceDao.selectByExample(example);
        int i = 0;
        if(prjTaskList != null && prjTaskList.size()>0){
        	List<Long> prjIds = new ArrayList<Long>();
            for (PrjTask prjTask : prjTaskList) {
            	prjIds.add(prjTask.getPrjId());
    		}
            com.lpcode.modules.blsp.entity.PrjInstanceExample prjExample = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
            Criteria prjCriteria = prjExample.createCriteria();
            prjCriteria.andIdIn(prjIds);
            List<PrjInstance> prjList = projectDao.selectByExample(prjExample);
            for (PrjTask prjTask : prjTaskList) {
            	for (PrjInstance prjInstance : prjList) {
    				if(prjTask.getPrjId().equals(prjInstance.getId())){
    					if(StringUtil.isEmpty(prjInstance.getStopUser())){    						
    						i++;
    					}
    				}
    			}
    		}
        }
        return i;
    }

    /**
     * @param from
     * @param to
     * @return 办结
     */
    public static Integer getTaskOverStatus(Date from, Date to, List<Long> taskid) {
        String status = "4";
        com.lpcode.modules.blsp.entity.PrjTaskExample example = new com.lpcode.modules.blsp.entity.PrjTaskExample();
        com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdIn((taskid));
        criteria.andTaskStatusEqualTo(status);
        criteria.andUpdateTimeBetween(from, to);
        //项目终止情况的过滤
        List<PrjTask> prjTaskList = taskInstanceDao.selectByExample(example);
        int i = 0;
        if(prjTaskList != null && prjTaskList.size()>0){
        	List<Long> prjIds = new ArrayList<Long>();
            for (PrjTask prjTask : prjTaskList) {
            	prjIds.add(prjTask.getPrjId());
    		}
            com.lpcode.modules.blsp.entity.PrjInstanceExample prjExample = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
            Criteria prjCriteria = prjExample.createCriteria();
            prjCriteria.andIdIn(prjIds);
            List<PrjInstance> prjList = projectDao.selectByExample(prjExample);
            for (PrjTask prjTask : prjTaskList) {
            	for (PrjInstance prjInstance : prjList) {
    				if(prjTask.getPrjId().equals(prjInstance.getId())){
    					if(StringUtil.isEmpty(prjInstance.getStopUser())){    						
    						i++;
    					}
    				}
    			}
    		}
        }
        return i;
    }

    /**
     * @param from
     * @param to
     * @return 暂停
     */
    public static Integer getTaskStopingStatus(Date from, Date to, String taskid) {
        String status = "2";
        com.lpcode.modules.blsp.entity.PrjTaskExample example = new com.lpcode.modules.blsp.entity.PrjTaskExample();
        com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdEqualTo(Long.parseLong(taskid));
        criteria.andTaskStatusEqualTo(status);
        criteria.andUpdateTimeBetween(from, to);
        //项目终止情况的过滤
        List<PrjTask> prjTaskList = taskInstanceDao.selectByExample(example);
        int i = 0;
        if(prjTaskList != null && prjTaskList.size()>0){
        	List<Long> prjIds = new ArrayList<Long>();
            for (PrjTask prjTask : prjTaskList) {
            	prjIds.add(prjTask.getPrjId());
    		}
            com.lpcode.modules.blsp.entity.PrjInstanceExample prjExample = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
            Criteria prjCriteria = prjExample.createCriteria();
            prjCriteria.andIdIn(prjIds);
            List<PrjInstance> prjList = projectDao.selectByExample(prjExample);
            for (PrjTask prjTask : prjTaskList) {
            	for (PrjInstance prjInstance : prjList) {
    				if(prjTask.getPrjId().equals(prjInstance.getId())){
    					if(StringUtil.isEmpty(prjInstance.getStopUser())){    						
    						i++;
    					}
    				}
    			}
    		}
        }
        return i;
    }

    /**
     * @param from
     * @param to
     * @return 暂停
     */
    public static Page<TaskVo> getTaskStopingList(Page<TaskVo> page, Date from, Date to, String taskid) {
        Page<PrjTask> taskPage = new Page<PrjTask>();
        String status = "2";
        com.lpcode.modules.blsp.entity.PrjTaskExample example = new com.lpcode.modules.blsp.entity.PrjTaskExample();
        com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdEqualTo(Long.parseLong(taskid));
        criteria.andTaskStatusEqualTo(status);
        //criteria.andUpdateTimeBetween(from, to);
        taskPage.setPageNo(page.getPageNo());
        taskPage.setPageSize(page.getPageSize());
        taskPage = taskInstanceDao.pagedSelectByExample(example, taskPage);
        page.setCount(taskPage.getCount());
        List<TaskVo> list = new ArrayList<TaskVo>();
        List<Long> prjIds = new ArrayList<Long>();
        if (taskPage.getList() != null && taskPage.getList().size() > 0){
        	//项目终止的情况过滤
	        for (PrjTask prjTask : taskPage.getList()) {
	        	prjIds.add(prjTask.getPrjId());
			}
	        com.lpcode.modules.blsp.entity.PrjInstanceExample prjExample = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
	        Criteria prjCriteria = prjExample.createCriteria();
	        prjCriteria.andIdIn(prjIds);
	        List<PrjInstance> prjList = projectDao.selectByExample(prjExample);
	        for(int i=0;i<taskPage.getList().size();i++){
	        	for (PrjInstance prjInstance : prjList) {
					if(taskPage.getList().get(i).getPrjId().equals(prjInstance.getId())){
						if(StringUtil.isNotEmpty(prjInstance.getStopUser())){    						
							taskPage.getList().remove(i);
						}
					}
				}
			}
            for (PrjTask pjt : taskPage.getList()) {
                TaskVo vo = new TaskVo();
                if (pjt.getTaskRealEndtime() != null)
                	vo.setEndTime(pjt.getTaskRealEndtime());
                if (pjt.getTaskEndTime() != null)
                	vo.setNeedEndTime(pjt.getTaskEndTime());
                if (pjt.getTaskStartTime() != null)
                	vo.setStartTime(pjt.getTaskStartTime());
                PrjTaskDefine mater = new PrjTaskDefine();
                mater.setId(pjt.getTaskId());
                mater = taskDao.selectOneByEntitySelective(mater);// 缓存处理,待优化
                vo.setTaskName(mater.getTaskName());
                vo.setStatus("暂停");
                //当前处理人
                vo.setUserName(UserUtils.get(pjt.getCurrUser().toString()).getName());
                //超时天数
                int calDatePeriod = dimHolidayService.calDatePeriod(new Date(), pjt.getTaskEndTime(), pjt.getTimeType());
                vo.setOverDate(String.valueOf(calDatePeriod));
                //获取暂停类型
                PrjTaskPauseDetailExample prjTaskPause = new PrjTaskPauseDetailExample();
                prjTaskPause.setOrderByClause("PAUSE_START_TIME");
                prjTaskPause.createCriteria().andPrjTaskInstIdEqualTo(pjt.getId());
				List<PrjTaskPauseDetail> prjTaskPauseDetailList = prjTaskPauseDetailMapper.selectByExample(prjTaskPause);
				if(prjTaskPauseDetailList != null){
					PrjTaskPauseDetail prjTaskPauseDetail = prjTaskPauseDetailList.get(prjTaskPauseDetailList.size()-1);
					vo.setTaskPauseStartTime(prjTaskPauseDetail.getPauseStartTime());
	            	vo.setTaskPauseType(prjTaskPauseDetail.getPauseType());
				}
				vo.setPrjStageInstId(pjt.getId());
                vo.setTaskInstanceId(pjt.getId());
                list.add(vo);
                vo.setProjectName(getInstance(pjt.getPrjId()).getPrjName());
            }
        }
        page.setList(list);
        return page;

    }

    /**
     * @param from
     * @param to
     * @return 暂停
     */
    public static Integer getTaskStopingStatus(Date from, Date to, List<Long> taskid) {
        String status = "2";
        com.lpcode.modules.blsp.entity.PrjTaskExample example = new com.lpcode.modules.blsp.entity.PrjTaskExample();
        com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdIn(taskid);
        criteria.andTaskStatusEqualTo(status);
        //criteria.andUpdateTimeBetween(from, to);
        //项目终止情况的过滤
        List<PrjTask> prjTaskList = taskInstanceDao.selectByExample(example);
        int i = 0;
        if(prjTaskList != null && prjTaskList.size()>0){
        	List<Long> prjIds = new ArrayList<Long>();
            for (PrjTask prjTask : prjTaskList) {
            	prjIds.add(prjTask.getPrjId());
    		}
            com.lpcode.modules.blsp.entity.PrjInstanceExample prjExample = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
            Criteria prjCriteria = prjExample.createCriteria();
            prjCriteria.andIdIn(prjIds);
            List<PrjInstance> prjList = projectDao.selectByExample(prjExample);
            for (PrjTask prjTask : prjTaskList) {
            	for (PrjInstance prjInstance : prjList) {
    				if(prjTask.getPrjId().equals(prjInstance.getId())){
    					if(StringUtil.isEmpty(prjInstance.getStopUser())){    						
    						i++;
    					}
    				}
    			}
    		}
        }
        return i;
    }

    /**
     * @param taskid
     * @return 超时
     */
    public static Integer getTaskOverTimeStatus(String taskid) {
        com.lpcode.modules.blsp.entity.PrjTaskExample example = new com.lpcode.modules.blsp.entity.PrjTaskExample();
        com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdEqualTo(Long.parseLong(taskid));
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        criteria.andTaskStatusIn(list);
        criteria.andTaskEndTimeLessThan(getCurrDayFirst());
        //项目终止情况过滤
        List<PrjTask> prjTaskList = taskInstanceDao.selectByExample(example);
        int i = 0;
        if(prjTaskList != null && prjTaskList.size()>0){
        	List<Long> prjIds = new ArrayList<Long>();
            for (PrjTask prjTask : prjTaskList) {
            	prjIds.add(prjTask.getPrjId());
    		}
            com.lpcode.modules.blsp.entity.PrjInstanceExample prjExample = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
            Criteria prjCriteria = prjExample.createCriteria();
            prjCriteria.andIdIn(prjIds);
            List<PrjInstance> prjList = projectDao.selectByExample(prjExample);
            for (PrjTask prjTask : prjTaskList) {
            	for (PrjInstance prjInstance : prjList) {
    				if(prjTask.getPrjId().equals(prjInstance.getId())){
    					if(StringUtil.isEmpty(prjInstance.getStopUser())){    						
    						i++;
    					}
    				}
    			}
    		}
        }
        return i;
    }

    /**
     * 超时
     *
     * @param page
     * @param taskid
     * @return
     */
    public static Page<TaskVo> getTaskOverTimeList(Page<TaskVo> page, String taskid) {
        Page<PrjTask> taskPage = new Page<PrjTask>();
        com.lpcode.modules.blsp.entity.PrjTaskExample example = new com.lpcode.modules.blsp.entity.PrjTaskExample();
        com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdEqualTo(Long.parseLong(taskid));
        List<String> list1 = new ArrayList<String>();
        list1.add("1");
        list1.add("2");
        criteria.andTaskStatusIn(list1);
        criteria.andTaskEndTimeLessThan(getCurrDayFirst());
        taskPage.setPageNo(page.getPageNo());
        taskPage.setPageSize(page.getPageSize());
        taskPage = taskInstanceDao.pagedSelectByExample(example, taskPage);
        page.setCount(taskPage.getCount());
        List<TaskVo> list = new ArrayList<TaskVo>();
        List<Long> prjIds = new ArrayList<Long>();
        if (taskPage.getList() != null && taskPage.getList().size() > 0){
        	//项目终止的情况过滤
	        for (PrjTask prjTask : taskPage.getList()) {
	        	prjIds.add(prjTask.getPrjId());
			}
	        com.lpcode.modules.blsp.entity.PrjInstanceExample prjExample = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
	        Criteria prjCriteria = prjExample.createCriteria();
	        prjCriteria.andIdIn(prjIds);
	        List<PrjInstance> prjList = projectDao.selectByExample(prjExample);
	        for(int i=0;i<taskPage.getList().size();i++){
	        	for (PrjInstance prjInstance : prjList) {
					if(taskPage.getList().get(i).getPrjId().equals(prjInstance.getId())){
						if(StringUtil.isNotEmpty(prjInstance.getStopUser())){    						
							taskPage.getList().remove(i);
						}
					}
				}
			}
            for (PrjTask pjt : taskPage.getList()) {
                TaskVo vo = new TaskVo();
                if (pjt.getTaskRealEndtime() != null)
                	vo.setEndTime(pjt.getTaskRealEndtime());
                if (pjt.getTaskEndTime() != null)
                	vo.setNeedEndTime(pjt.getTaskEndTime());
                if (pjt.getTaskStartTime() != null)
                	vo.setStartTime(pjt.getTaskStartTime());
                PrjTaskDefine mater = new PrjTaskDefine();
                mater.setId(pjt.getTaskId());
                mater = taskDao.selectOneByEntitySelective(mater);// 缓存处理,待优化
                vo.setTaskName(mater.getTaskName());
                vo.setStatus(pjt.getTaskStatus());
                //当前处理人
                vo.setUserName(UserUtils.get(pjt.getCurrUser().toString()).getName());
                //超时天数
                int calDatePeriod = dimHolidayService.calDatePeriod(new Date(), pjt.getTaskEndTime(), pjt.getTimeType());
                vo.setOverDate(String.valueOf(calDatePeriod));
                if(pjt.getTaskStatus().equals("2")){
                	//获取暂停类型
                    PrjTaskPauseDetailExample prjTaskPause = new PrjTaskPauseDetailExample();
                    prjTaskPause.setOrderByClause("PAUSE_START_TIME");
                    prjTaskPause.createCriteria().andPrjTaskInstIdEqualTo(pjt.getId());
    				List<PrjTaskPauseDetail> prjTaskPauseDetailList = prjTaskPauseDetailMapper.selectByExample(prjTaskPause);
    				if(prjTaskPauseDetailList != null){
    					PrjTaskPauseDetail prjTaskPauseDetail = prjTaskPauseDetailList.get(prjTaskPauseDetailList.size()-1);
    					vo.setTaskPauseStartTime(prjTaskPauseDetail.getPauseStartTime());
    	            	vo.setTaskPauseType(prjTaskPauseDetail.getPauseType());
    				}
                }
                vo.setPrjStageInstId(pjt.getId());
                vo.setTaskInstanceId(pjt.getId());
                list.add(vo);
                vo.setProjectName(getInstance(pjt.getPrjId()).getPrjName());
            }
        }
        page.setList(list);
        return page;
    }

    /**
     * @param taskids
     * @return 超时
     */
    public static Integer getTaskOverTimeStatus(List<Long> taskids) {
        com.lpcode.modules.blsp.entity.PrjTaskExample example = new com.lpcode.modules.blsp.entity.PrjTaskExample();
        com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdIn(taskids);
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        criteria.andTaskStatusIn(list);
        criteria.andTaskEndTimeLessThan(getCurrDayFirst());
        List<PrjTask> prjTaskList = taskInstanceDao.selectByExample(example);
        int i = 0;
        if(prjTaskList != null && prjTaskList.size()>0){
        	List<Long> prjIds = new ArrayList<Long>();
            for (PrjTask prjTask : prjTaskList) {
            	prjIds.add(prjTask.getPrjId());
    		}
            com.lpcode.modules.blsp.entity.PrjInstanceExample prjExample = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
            Criteria prjCriteria = prjExample.createCriteria();
            prjCriteria.andIdIn(prjIds);
            List<PrjInstance> prjList = projectDao.selectByExample(prjExample);
            for (PrjTask prjTask : prjTaskList) {
            	for (PrjInstance prjInstance : prjList) {
    				if(prjTask.getPrjId().equals(prjInstance.getId())){
    					if(StringUtil.isEmpty(prjInstance.getStopUser())){    						
    						i++;
    					}
    				}
    			}
    		}
        }
        return i;
    }

    /**
     * @param depid
     * @param stageId
     * @return 超时
     */
    public static Integer getTaskOverTimeStatus(String depid, String stageId) {
        PrjTaskDefine vo = new PrjTaskDefine();
        vo.setDeptId(depid);
        vo.setStageId(Long.parseLong(stageId));
        List<PrjTaskDefine> list = taskDao.selectByEntitySelective(vo);
        List<Long> listtask = new ArrayList<Long>();
        for (PrjTaskDefine de : list) {
            listtask.add(de.getId());
        }
        return getTaskOverTimeStatus(listtask);
    }

    /**
     * @param depid
     * @param stageId
     * @return
     */
    public static List<Long> getPrjTaskDefineList(String depid, String stageId) {
        PrjTaskDefine vo = new PrjTaskDefine();
        vo.setDeptId(depid);
        vo.setStageId(Long.parseLong(stageId));
        List<PrjTaskDefine> list = taskDao.selectByEntitySelective(vo);
        List<Long> listtask = new ArrayList<Long>();
        for (PrjTaskDefine de : list) {
            listtask.add(de.getId());
        }
        return listtask;
    }

    /**
     * 项目审批中
     *
     * @param stateid
     * @return
     */
    public static Integer getPrjPendingStatus(Long stateid) {
        com.lpcode.modules.blsp.entity.PrjStageExample example = new com.lpcode.modules.blsp.entity.PrjStageExample();
        com.lpcode.modules.blsp.entity.PrjStageExample.Criteria criteria = example.createCriteria();
        criteria.andStageIdEqualTo(stateid);
        criteria.andStageStatusEqualTo("1");
        int i = stateInstanceDao.countByExample(example);
        return i;
    }

    /**
     * 企业项目受理数
     *
     * @param type
     * @param from
     * @param to
     * @return
     */
    public static Integer getPrjPendingStatus(String type, Date from, Date to) {
        int i = prjDao.selectCount(from, to, type);
        return i;
    }

    /**
     * 企业项目受理列表
     *
     * @param page
     * @param from
     * @param to
     * @param type
     * @return
     */
    public static Page<ProjectVo> getPrjPendingList(Page<ProjectVo> page, Date from, Date to, String type) {
        try {
            int no = page.getPageNo();
            int pagesize = page.getPageSize();
            List<ProjectVo> listvo = new ArrayList<ProjectVo>();
            List<ProjectVo> list = prjDao.selectList(from, to, (no - 1) * pagesize, pagesize, type);
            BeanCopy.copyPropertiesForList(list, listvo, ProjectVo.class);
            page.setCount(ProjectUtil.getPrjPendingStatus(type, from, to));
            page.setList(listvo);
            return page;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 项目办结数
     *
     * @param from
     * @param to
     * @param type
     * @return
     */
    public static Integer getPrjOverStatus(Date from, Date to, String type) {
        com.lpcode.modules.blsp.entity.PrjInstanceExample example = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
        com.lpcode.modules.blsp.entity.PrjInstanceExample.Criteria criteria = example.createCriteria();
        criteria.andPrjTypeEqualTo(type);
        criteria.andRealEndTimeBetween(from, to);
        criteria.andIsPrjCompleteEqualTo("1");
        criteria.andChannelEqualTo("0");//数据渠道为窗口录入的数据
        int i = projectDao.countByExample(example);
        return i;
    }

    /**
     * 项目受理数
     *
     * @param from
     * @param to
     * @param type
     * @return
     */
    public static Integer getPrjAcceptStatus(Date from, Date to, String type) {
        com.lpcode.modules.blsp.entity.PrjInstanceExample example = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
        com.lpcode.modules.blsp.entity.PrjInstanceExample.Criteria criteria = example.createCriteria();
        if (type.equals("1")) {// 政府
            criteria.andPrjTypeEqualTo(type);
        } else {// 企业
            criteria.andPrjTypeEqualTo(type);
        }
        criteria.andChannelEqualTo("0");//数据渠道为窗口录入的数据
        criteria.andCreatTimeBetween(from, to);
        criteria.andRealStartTimeIsNotNull();
        int i = projectDao.countByExample(example);
        return i;
    }

    /**
     * 项目审批中数量
     *
     * @param from
     * @param to
     * @param type
     * @return
     */
    public static Integer getPrjProcessStatus(Date from, Date to, String type) {
        com.lpcode.modules.blsp.entity.PrjInstanceExample example = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
        com.lpcode.modules.blsp.entity.PrjInstanceExample.Criteria criteria = example.createCriteria();
        if (type.equals("1")) {// 政府
            criteria.andPrjTypeEqualTo(type);
        } else {// 企业
            criteria.andPrjTypeEqualTo(type);
        }
        criteria.andChannelEqualTo("0");//数据渠道为窗口录入的数据
        criteria.andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);//数据未删除标识
        to = dimHolidayService.findNextWorkDay();
        to = DateUtil.dayEnd(to);//最当前日期的最后23:59:59.999时间
        criteria.andRealStartTimeBetween(from, to);
        int i = projectDao.countByExample(example);
        return i;
    }

    /**
     * 时间段内所有项目
     *
     * @param from
     * @param to
     * @param type
     * @return
     */
    public static Integer getPrjAllStatus(Date from, Date to, String type) {
        com.lpcode.modules.blsp.entity.PrjInstanceExample example = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
        com.lpcode.modules.blsp.entity.PrjInstanceExample.Criteria criteria = example.createCriteria();
        if (type.equals("1")) {// 政府
            criteria.andPrjTypeEqualTo(type);
        } else {// 企业
            criteria.andPrjTypeEqualTo(type);
        }
        criteria.andCreatTimeBetween(from, to);
        criteria.andChannelEqualTo("0");//数据渠道为窗口录入的数据
        criteria.andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
        int i = projectDao.countByExample(example);
        return i;
    }
    
    /**
     * 项目终止数
     *
     * @param from
     * @param to
     * @param type
     * @return
     */
    public static Integer getPrjStopStatus(Date from, Date to, String type) {
    	com.lpcode.modules.blsp.entity.PrjInstanceExample example = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
        com.lpcode.modules.blsp.entity.PrjInstanceExample.Criteria criteria = example.createCriteria();
        criteria.andPrjTypeEqualTo(type);
        criteria.andStopTimeBetween(from, to);
        criteria.andIsPrjCompleteEqualTo("9");
        int i = projectDao.countByExample(example);
        return i;
    }

    /**
     * 受理项目列表
     *
     * @param page
     * @param from
     * @param to
     * @param type
     * @return
     */
    public static Page<ProjectVo> getPrjAcceptList(Page<ProjectVo> page, Date from, Date to, String type) {
        try {
            int no = page.getPageNo();
            int pagesize = page.getPageSize();
            List<ProjectVo> listvo = new ArrayList<ProjectVo>();
            List<ProjectVo> list = prjDao.select(from, to, (no - 1) * pagesize, pagesize, type);
            BeanCopy.copyPropertiesForList(list, listvo, ProjectVo.class);
            page.setCount(ProjectUtil.getPrjAcceptStatus(from, to, type));
            page.setList(listvo);
            return page;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 审批中项目列表
     *
     * @param page
     * @param from
     * @param to
     * @param type
     * @return
     */
    public static Page<ProjectVo> getPrjProcessList(Page<ProjectVo> page, Date from, Date to, String type) {
        try {
            int no = page.getPageNo();
            int pagesize = page.getPageSize();
            List<ProjectVo> listvo = new ArrayList<ProjectVo>();
            to = dimHolidayService.findNextWorkDay();//审批开始计时日期是从下一个工作日开始
            to = DateUtil.dayEnd(to);//最当前日期的最后23:59:59.999时间
            List<ProjectVo> list = prjDao.selectProcessPrj(from, to, (no - 1) * pagesize, pagesize, type);
            BeanCopy.copyPropertiesForList(list, listvo, ProjectVo.class);
            page.setCount(ProjectUtil.getPrjProcessStatus(from, to, type));
            page.setList(listvo);
            return page;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 时间段内所有项目列表
     *
     * @param page
     * @param from
     * @param to
     * @param type
     * @return
     */
    public static Page<ProjectVo> getAllPrjList(Page<ProjectVo> page, Date from, Date to, String type) {
        try {
            int no = page.getPageNo();
            int pagesize = page.getPageSize();
            List<ProjectVo> listvo = new ArrayList<ProjectVo>();
            List<ProjectVo> list = prjDao.selectAllproject(from, to, (no - 1) * pagesize, pagesize, type);
            BeanCopy.copyPropertiesForList(list, listvo, ProjectVo.class);
            page.setCount(ProjectUtil.getPrjAllStatus(from, to, type));
            page.setList(listvo);
            return page;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 办结项目列表
     *
     * @param page
     * @param from
     * @param to
     * @param type
     * @return
     */
    public static Page<ProjectVo> getPrjOverList(Page<ProjectVo> page, Date from, Date to, String type) {
        com.lpcode.modules.blsp.entity.PrjInstanceExample example = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
        com.lpcode.modules.blsp.entity.PrjInstanceExample.Criteria criteria = example.createCriteria();
        criteria.andPrjTypeEqualTo(type);
        criteria.andUpdateTimeBetween(from, to);
        criteria.andIsPrjCompleteEqualTo("1");
        Page<PrjInstance> instancePage = new Page<PrjInstance>();
        instancePage.setPageNo(page.getPageNo());
        instancePage.setPageSize(page.getPageSize());
        instancePage = projectDao.pagedSelectByExample(example, instancePage);
        List<PrjInstance> list = instancePage.getList();
        List<ProjectVo> lists = new ArrayList<ProjectVo>();
        for (PrjInstance prj : list) {
            ProjectVo vo = new ProjectVo();
            vo.setId(prj.getId().toString());
            vo.setProjectName(prj.getPrjName());
            vo.setApplyDate(prj.getCreatTime());
            vo.setEndDate(prj.getUpdateTime());
            vo.setProjectCode(prj.getPrjCode());
            vo.setStageName("");
            vo.setStatus("已结束");
            lists.add(vo);
        }
        page.setCount(instancePage.getCount());
        page.setList(lists);
        return page;
    }
    
    /**
     * 终止项目列表
     *
     * @param page
     * @param from
     * @param to
     * @param type
     * @return
     */
    public static Page<ProjectVo> getPrjStopList(Page<ProjectVo> page, Date from, Date to, String type) {
    	com.lpcode.modules.blsp.entity.PrjInstanceExample example = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
    	com.lpcode.modules.blsp.entity.PrjInstanceExample.Criteria criteria = example.createCriteria();
    	criteria.andPrjTypeEqualTo(type);
    	criteria.andStopTimeBetween(from, to);
    	criteria.andIsPrjCompleteEqualTo("9");
    	Page<PrjInstance> instancePage = new Page<PrjInstance>();
    	instancePage.setPageNo(page.getPageNo());
    	instancePage.setPageSize(page.getPageSize());
    	instancePage = projectDao.pagedSelectByExample(example, instancePage);
    	List<PrjInstance> list = instancePage.getList();
    	List<ProjectVo> lists = new ArrayList<ProjectVo>();
    	for (PrjInstance prj : list) {
    		ProjectVo vo = new ProjectVo();
            vo.setId(prj.getId().toString());
    		vo.setProjectName(prj.getPrjName());
    		vo.setApplyDate(prj.getCreatTime());
    		vo.setEndDate(prj.getStopTime());
    		vo.setProjectCode(prj.getPrjCode());
    		vo.setStageName("");
    		vo.setStatus("终止");
    		lists.add(vo);
    	}
    	page.setCount(instancePage.getCount());
    	page.setList(lists);
    	return page;
    }

    /**
     * 获取
     *
     * @param type
     * @return
     */
    public static List<PrjStageDefineVo> getAllStageList(String type) {
        try {
            List<PrjStageDefineVo> list = new ArrayList<PrjStageDefineVo>();
            PrjStageDefineExample example = new PrjStageDefineExample();
            com.lpcode.modules.blsp.entity.PrjStageDefineExample.Criteria c = example.createCriteria();
            c.andStageTypeEqualTo(type);
            example.setOrderByClause("pre_stage_id asc");
            List<PrjStageDefine> listd = StateDao.selectByExample(example);
            if (listd != null)
                BeanCopy.copyPropertiesForList(listd, list, PrjStageDefineVo.class);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param type
     * @return
     */
    public static ProjectReportVo getProjectReportVo(String type) {
        ProjectReportVo vo = new ProjectReportVo();
        if (StringUtils.isBlank(type))
            return null;
        if (type.equals("1")) {// 政府
            List<PrjStageDefineVo> list = ProjectUtil.getAllStageList(type);
            if (list != null && list.size() > 0) {
                List<ProjectStageVo> plist = new ArrayList<ProjectStageVo>();
                for (PrjStageDefineVo v : list) {
                    ProjectStageVo svo = new ProjectStageVo();
                    svo.setStageName(v.getStageName());
                    svo.setStageid(v.getId().toString());
                    List<PrjTaskDefineVo> listvo = getAllTaskByStage(v.getId());
                    if (listvo != null && listvo.size() > 0) {
                        StageTaskVo stv = new StageTaskVo();
                        Map<String, List<TaskVo>> map = new HashMap<String, List<TaskVo>>();
                        for (PrjTaskDefineVo tvo : listvo) {
                            TaskVo t = new TaskVo();
                            t.setTaskName(tvo.getTaskName());
                            t.setDepId(tvo.getDeptId());
                            t.setTaskid(tvo.getId().toString());
                            String name = tvo.getDeptId();
                            if (map.get(name) != null) {
                                List<TaskVo> tvolist = map.get(name);
                                tvolist.add(t);
                            } else {
                                List<TaskVo> tvolist = new ArrayList<TaskVo>();
                                tvolist.add(t);
                                map.put(name, tvolist);
                            }
                        }
                        stv.setStageTask(map);
                        svo.setStageTaskVo(stv);
                    }
                    plist.add(svo);
                }
                vo.setProjectStageVoList(plist);
            }
        } else {
            List<PrjStageDefineVo> list = ProjectUtil.getAllStageList(type);
            Integer prjPendingNum = 0;
            if (list != null && list.size() > 0) {
                List<ProjectStageVo> plist = new ArrayList<ProjectStageVo>();
                for (PrjStageDefineVo v : list) {
                    ProjectStageVo svo = new ProjectStageVo();
                    svo.setStageName(v.getStageName());
                    svo.setStageid(v.getId().toString());
                    Integer i = getPrjPendingStatus(v.getId());
                    prjPendingNum = prjPendingNum + i;
                    svo.setPendingNum(i);
                    List<PrjTaskDefineVo> listvo = getAllTaskByStage(v.getId());
                    if (listvo != null && listvo.size() > 0) {
                        StageTaskVo stv = new StageTaskVo();
                        Map<String, List<TaskVo>> map = new HashMap<String, List<TaskVo>>();
                        for (PrjTaskDefineVo tvo : listvo) {
                            TaskVo t = new TaskVo();
                            t.setTaskName(tvo.getTaskName());
                            t.setDepId(tvo.getDeptId());
                            t.setTaskid(tvo.getId().toString());
                            String name = tvo.getDeptId();
                            if (map.get(name) != null) {
                                List<TaskVo> tvolist = map.get(name);
                                tvolist.add(t);
                            } else {
                                List<TaskVo> tvolist = new ArrayList<TaskVo>();
                                tvolist.add(t);
                                map.put(name, tvolist);
                            }
                        }
                        stv.setStageTask(map);
                        svo.setStageTaskVo(stv);
                    }
                    plist.add(svo);
                }
                // vo.setPendingNum(prjPendingNum);
                vo.setProjectStageVoList(plist);
            }
        }
        return vo;
    }

    /**
     * 本天开始时间
     */
    public static Date getCurrDayFirst() {
        Date now = new Date();
        try {
            now = shortSdf.parse(shortSdf.format(now));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * @param depid
     * @param stageId
     * @return 得到部门审批中的所有事项数
     */
    public static Integer getTaskPressingStatus(String depid, String stageId) {
        PrjTaskDefine vo = new PrjTaskDefine();
        vo.setDeptId(depid);
        vo.setStageId(Long.parseLong(stageId));
        List<PrjTaskDefine> list = taskDao.selectByEntitySelective(vo);
        List<Long> listtask = new ArrayList<Long>();
        for (PrjTaskDefine de : list) {
            listtask.add(de.getId());
        }
        com.lpcode.modules.blsp.entity.PrjTaskExample example = new com.lpcode.modules.blsp.entity.PrjTaskExample();
        com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdIn(listtask);
        List<String> listStatus = new ArrayList<String>();
        listStatus.add("1");
        listStatus.add("2");
        criteria.andTaskStatusIn(listStatus);
        return taskInstanceDao.countByExample(example);
    }

    /**
     * @param depid
     * @param stageId
     * @return 得到部门审批中的所有事项数
     */
    public static Integer getTaskByDepId(String depid, String stageId,String dType) {
        Date from = new Date();
        Date to = new Date();
        from = getDate(dType, from);
        PrjTaskDefine vo = new PrjTaskDefine();
        vo.setDeptId(depid);
        vo.setStageId(Long.parseLong(stageId));
        List<PrjTaskDefine> list = taskDao.selectByEntitySelective(vo);
        List<Long> listtask = new ArrayList<Long>();
        for (PrjTaskDefine de : list) {
            listtask.add(de.getId());
        }
        com.lpcode.modules.blsp.entity.PrjTaskExample example = new com.lpcode.modules.blsp.entity.PrjTaskExample();
        com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdIn(listtask);
        //TODO hero 时间from to 待修改
        criteria.andTaskRealEndtimeBetween(from, to);
        List<String> listStatus = new ArrayList<String>();
        listStatus.add("1");
        listStatus.add("2");
//        listStatus.add("3");
        listStatus.add("4");
        listStatus.add("6");
        criteria.andTaskStatusIn(listStatus);
        return taskInstanceDao.countByExample(example);
    }

    /**
     * 通过部门ID得到部门名称
     * @param deptId
     * @return
     */
    public static String getDeptNameByDeptId(String deptId){
        Office office = officeDao.get(deptId);
        if (office != null) {
            return office.getName();
        }
        return "";
    }

    /**
     * 通过部门ID得到部门code
     * @param deptId
     * @return
     */
    public static String getDeptCodeByDeptId(String deptId){
        Office office = officeDao.get(deptId);
        if (office != null) {
            return office.getCode();
        }
        return "";
    }
    
    /**
     * 根据项目中的事项集合以及acceptId获取打印所需事项材料信息
     * @param acceptId
     * @param taskIds
     * @return
     */
    public static Map<Long, List<PrjStageMaterialVo>> getAllTaskMaterByAcceptIdAndTaskIds(Long acceptId,List<Long> taskIds) {
        Map<Long, List<PrjStageMaterialVo>> map = new HashMap<Long, List<PrjStageMaterialVo>>();
        if (acceptId == null)
            return map;
        PrjTask define = new PrjTask();
        define.setAcceptId(acceptId);
        define.setTaskStatus("1");
        List<PrjTask> t = taskInstanceDao.selectByEntitySelective(define);
        List<PrjTask> taskList = new ArrayList<PrjTask>();
        if(taskIds != null && taskIds.size() > 0){
        	for (PrjTask task : t) {
				for(Long taskId : taskIds){
					if(task.getTaskId().equals(taskId)){
						taskList.add(task);
						break;
					}
				}
			}
        }
        if (taskList != null && taskList.size() > 0)
            for (PrjTask task : taskList) {
                com.lpcode.modules.blsp.entity.PrjStageMaterialExample example = new com.lpcode.modules.blsp.entity.PrjStageMaterialExample();
                com.lpcode.modules.blsp.entity.PrjStageMaterialExample.Criteria c = example.createCriteria();
                c.andTaskIdEqualTo(task.getTaskId());
                c.andPrjStageInstIdEqualTo(task.getPrjStageInstId());
                List<PrjStageMaterial> listMatr = stageMaterialDao.selectByExample(example);
                List<PrjStageMaterialVo> list = new ArrayList<PrjStageMaterialVo>();
                if (listMatr != null && listMatr.size() > 0)
                    BeanCopy.copyPropertiesForList(listMatr, list, PrjStageMaterialVo.class);
                //得到事项中所有材料并替换
                PrjTaskMaterialDef prjTaskMaterialDef = new PrjTaskMaterialDef();
                prjTaskMaterialDef.setTaskId(task.getTaskId());
                List<PrjTaskMaterialDef> prjTaskMaterialDefList = prjTaskMaterialDefDao.selectByEntitySelective(prjTaskMaterialDef);
                List<PrjStageMaterialVo> prjStageMaterialVoList = new ArrayList<PrjStageMaterialVo>();
                if(prjTaskMaterialDefList != null && prjTaskMaterialDefList.size() > 0){
                	BeanCopy.copyPropertiesForList(prjTaskMaterialDefList, prjStageMaterialVoList, PrjStageMaterialVo.class);
                }
                map.put(task.getTaskId(), prjStageMaterialVoList);
                if (prjStageMaterialVoList != null && prjStageMaterialVoList.size() > 0){
                    for ( int i = 0 ; i < prjStageMaterialVoList.size() ; i++ ) {
                    	prjStageMaterialVoList.get(i).setIsComplete("0");
                    	if (list != null && list.size() > 0){
                    		for (PrjStageMaterialVo l : list) {
                    			if(prjStageMaterialVoList.get(i).getMaterialId().equals(l.getMaterialId())){
                    				prjStageMaterialVoList.set(i, l);
                    			}
                    			DimMaterial mater = new DimMaterial();
                                mater.setId(prjStageMaterialVoList.get(i).getMaterialId());
                                mater = materialDao.selectOneByEntitySelective(mater);
                                prjStageMaterialVoList.get(i).setMaterName(mater.getName());
                    		}
                    	}
                    }
                }
            }
        return map;
    }
    
    /**
     * 获取审批中数量信息
     * @param depid
     * @param stageId
     * @return
     */
    public static Integer getTaskApprovalStatus(String depid, String stageId) {
        PrjTaskDefine vo = new PrjTaskDefine();
        vo.setDeptId(depid);
        vo.setStageId(Long.parseLong(stageId));
        vo.setIsDelete("0");
        List<PrjTaskDefine> list = taskDao.selectByEntitySelective(vo);
        List<Long> listtask = new ArrayList<Long>();
        for (PrjTaskDefine de : list) {
            listtask.add(de.getId());
        }
        com.lpcode.modules.blsp.entity.PrjTaskExample example = new com.lpcode.modules.blsp.entity.PrjTaskExample();
        com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdIn(listtask);
        criteria.andTaskStatusEqualTo("1");
        criteria.andIsDeleteEqualTo("0");
        List<PrjTask> prjTaskList = taskInstanceDao.selectByExample(example);
        int i = 0;
        if(prjTaskList != null && prjTaskList.size()>0){
        	List<Long> prjIds = new ArrayList<Long>();
            for (PrjTask prjTask : prjTaskList) {
            	prjIds.add(prjTask.getPrjId());
    		}
            com.lpcode.modules.blsp.entity.PrjInstanceExample prjExample = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
            Criteria prjCriteria = prjExample.createCriteria();
            prjCriteria.andIdIn(prjIds);
            List<PrjInstance> prjList = projectDao.selectByExample(prjExample);
            for (PrjTask prjTask : prjTaskList) {
            	for (PrjInstance prjInstance : prjList) {
    				if(prjTask.getPrjId().equals(prjInstance.getId())){
    					if(StringUtil.isEmpty(prjInstance.getStopUser())){    						
    						i++;
    					}
    				}
    			}
    		}
        }
        return i;
    }
    
    /**
     * 获取已办结数量信息
     * @param depid
     * @param stageId
     * @return
     */
    public static Integer getTaskAchieveStatus(String depid, String stageId,String dType) {
    	Date from = new Date();
		Date to = new Date();
		from = getDate(dType, from);
        PrjTaskDefine vo = new PrjTaskDefine();
        vo.setDeptId(depid);
        vo.setStageId(Long.parseLong(stageId));
        vo.setIsDelete("0");
        List<PrjTaskDefine> list = taskDao.selectByEntitySelective(vo);
        List<Long> listtask = new ArrayList<Long>();
        for (PrjTaskDefine de : list) {
            listtask.add(de.getId());
        }
        com.lpcode.modules.blsp.entity.PrjTaskExample example = new com.lpcode.modules.blsp.entity.PrjTaskExample();
        com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdIn(listtask);
        criteria.andTaskStatusEqualTo("4");
        criteria.andIsDeleteEqualTo("0");
        criteria.andUpdateTimeBetween(from, to);
        List<PrjTask> prjTaskList = taskInstanceDao.selectByExample(example);
        int i = 0;
        if(prjTaskList != null && prjTaskList.size()>0){
        	List<Long> prjIds = new ArrayList<Long>();
            for (PrjTask prjTask : prjTaskList) {
            	prjIds.add(prjTask.getPrjId());
    		}
            com.lpcode.modules.blsp.entity.PrjInstanceExample prjExample = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
            Criteria prjCriteria = prjExample.createCriteria();
            prjCriteria.andIdIn(prjIds);
            List<PrjInstance> prjList = projectDao.selectByExample(prjExample);
            for (PrjTask prjTask : prjTaskList) {
            	for (PrjInstance prjInstance : prjList) {
    				if(prjTask.getPrjId().equals(prjInstance.getId())){
    					if(StringUtil.isEmpty(prjInstance.getStopUser())){    						
    						i++;
    					}
    				}
    			}
    		}
        }
        return i;
    }
    
    /**
     * 获取暂停中数量信息
     * @param depid
     * @param stageId
     * @return
     */
    public static Integer getTaskPauseStatus(String depid, String stageId) {
        PrjTaskDefine vo = new PrjTaskDefine();
        vo.setDeptId(depid);
        vo.setStageId(Long.parseLong(stageId));
        vo.setIsDelete("0");
        List<PrjTaskDefine> list = taskDao.selectByEntitySelective(vo);
        List<Long> listtask = new ArrayList<Long>();
        for (PrjTaskDefine de : list) {
            listtask.add(de.getId());
        }
        com.lpcode.modules.blsp.entity.PrjTaskExample example = new com.lpcode.modules.blsp.entity.PrjTaskExample();
        com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria criteria = example.createCriteria();
        criteria.andTaskIdIn(listtask);
        criteria.andTaskStatusEqualTo("2");
        criteria.andIsDeleteEqualTo("0");
        List<PrjTask> prjTaskList = taskInstanceDao.selectByExample(example);
        int i = 0;
        if(prjTaskList != null && prjTaskList.size()>0){
        	List<Long> prjIds = new ArrayList<Long>();
            for (PrjTask prjTask : prjTaskList) {
            	prjIds.add(prjTask.getPrjId());
    		}
            com.lpcode.modules.blsp.entity.PrjInstanceExample prjExample = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
            Criteria prjCriteria = prjExample.createCriteria();
            prjCriteria.andIdIn(prjIds);
            List<PrjInstance> prjList = projectDao.selectByExample(prjExample);
            for (PrjTask prjTask : prjTaskList) {
            	for (PrjInstance prjInstance : prjList) {
    				if(prjTask.getPrjId().equals(prjInstance.getId())){
    					if(StringUtil.isEmpty(prjInstance.getStopUser())){    						
    						i++;
    					}
    				}
    			}
    		}
        }
        return i;
    }
    
    /**
     * 获取某项目下事项信息，并其中加入表单信息
     * @param stateInstanceId
     * @param prjId
     * @return
     */
    public static List<PrjTaskVo> getAllTaskByInstanceStageAndFormInfo(String stateInstanceId, String prjId) {
        if (stateInstanceId == null || prjId == null)
            return null;
        List<PrjTaskVo> task = new ArrayList<PrjTaskVo>();
        PrjTask define = new PrjTask();
        define.setPrjStageInstId(Long.parseLong(stateInstanceId));
        define.setPrjId(Long.parseLong(prjId));
        List<PrjTask> list = taskInstanceDao.selectByEntitySelective(define);
        if (list != null){
        	//该项目所有事项ID集合
        	List<Long> taskIds = new ArrayList<Long>();
        	for (PrjTask prjTask : list) {
        		taskIds.add(prjTask.getTaskId());
			}
        	com.lpcode.modules.blsp.entity.PrjFormTaskRealExample realExample = new com.lpcode.modules.blsp.entity.PrjFormTaskRealExample();
        	com.lpcode.modules.blsp.entity.PrjFormTaskRealExample.Criteria criteria = realExample.createCriteria();
        	criteria.andIsDeleteEqualTo("0");
        	criteria.andTaskDefIdIn(taskIds);
        	//根据事项ID获取所有默认事项列表
        	List<PrjFormTaskReal> prjFormTaskRealList = prjFormTaskRealMapper.selectByExample(realExample);
        	for (PrjTask prjTask : list) {
        		Long prjDefId = prjTask.getPrjId();
        		prjTask.setPrjId(null);
        		if(prjFormTaskRealList != null && prjFormTaskRealList.size() > 0){
        			//表单ID集合
        			List<Long> formIds = new ArrayList<Long>();
        			for (PrjFormTaskReal prjFormTaskReal : prjFormTaskRealList) {
        				formIds.add(prjFormTaskReal.getFormId());
					}
        			com.lpcode.modules.blsp.entity.FormDefineExample defExample = new com.lpcode.modules.blsp.entity.FormDefineExample();
        			com.lpcode.modules.blsp.entity.FormDefineExample.Criteria defCriteria = defExample.createCriteria();
        			defCriteria.andIsDeleteEqualTo("0");
        			defCriteria.andIdIn(formIds);
        			//根据表单ID获取表单默认表的列表
        			List<FormDefine> formDefineList = formDefineMapper.selectByExample(defExample);
        			for (PrjFormTaskReal prjFormTaskReal : prjFormTaskRealList) {
        				//判断事项是否和表单事项默认表匹配
            			if(prjTask.getTaskId().equals(prjFormTaskReal.getTaskDefId())){
            				if(formDefineList != null && formDefineList.size() > 0){
            					for (FormDefine formDefine : formDefineList) {
            						//判断表单事项表是否和表单默认表匹配
    								if(prjFormTaskReal.getFormId().equals(formDefine.getId())){
    									//获取该事项下的表单类
    									PrjFormVO prjFormVO = factoryFormService.checkBizForm(formDefine.getFormCode().toUpperCase(), prjDefId, prjFormTaskReal.getTaskDefId().toString());
    									//如果表单不存在设置项目ID为0
    									prjTask.setPrjId(Long.valueOf(0));
    									if(prjFormVO != null && !"".equals(prjFormVO)){
    										if(prjFormVO.getObject() != null && !"".equals(prjFormVO.getObject())){
    											prjTask.setPrjId(prjDefId);
    										}
    									}
    								}
    							}
            				}
            			}
            		}
        		}
			}
            BeanCopy.copyPropertiesForList(list, task, PrjTaskVo.class);
        }
        for (PrjTaskVo de : task) {
            Long id = de.getTaskId();
            PrjTaskDefine mater = new PrjTaskDefine();
            mater.setId(id);
            if (id == null)
                continue;
            mater = taskDao.selectOneByEntitySelective(mater);// 缓存处理,待优化
            if (mater == null)
                continue;
            de.setTaskName(mater.getTaskName());
            de.setDeptId(mater.getDeptId());

            User curr = UserUtils.get(de.getCurrUser());// 待优化
            if (curr != null)
                de.setCurrUserName(curr.getName());
            User init = UserUtils.get(de.getInitUser());
            if (init != null)
                de.setInitUserName(init.getName());
            Office office = officeDao.get(mater.getDeptId());
            if (office != null) {
                de.setDeptName(office.getName());
            }
            de.setTaskCode(mater.getTaskCode());
            //判断是否是暂停阶段，若是则查出暂停类型和时间加入到de对象中
            if("2".equals(de.getTaskStatus())){
            	PrjTaskPauseDetailExample example = new PrjTaskPauseDetailExample();
				example.setOrderByClause("PAUSE_START_TIME");
				example.createCriteria().andPrjTaskInstIdEqualTo(de.getId());
				List<PrjTaskPauseDetail> prjTaskPauseDetailList = prjTaskPauseDetailMapper.selectByExample(example);
				if(prjTaskPauseDetailList != null){
					PrjTaskPauseDetail prjTaskPauseDetail = prjTaskPauseDetailList.get(prjTaskPauseDetailList.size()-1);
					de.setTaskPauseStartTime(prjTaskPauseDetail.getPauseStartTime());
	            	de.setTaskPauseType(prjTaskPauseDetail.getPauseType());
				}
            }
        }
        return task;
    }

    /**
     * 通过项目类型与项目
     * @param type
     * @param dType
     * @return
     */
    public static Integer getAllPrjInTime(String type, String dType,String reportDate){
        Date from = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            from = sdf.parse(reportDate);
        }catch (Exception e){
            e.printStackTrace();
            from = new Date();
        }
        from = getDate(dType, from);
        Date to = getEndDate(dType, from);
        PrjInstanceExample example = new PrjInstanceExample();
        Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
        criteria.andChannelEqualTo("0");//数据渠道为窗口录入的数据
        criteria.andPrjTypeEqualTo(type);
        criteria.andCreatTimeBetween(from, to);
        List<PrjInstance> list = projectDao.selectByExample(example);
        if(list != null && list.size() > 0)
            return list.size();
        else
            return 0;
    }
    /**
     * 通过项目类型与项目
     * @param type
     * @param dType
     * @return
     */
    public static Integer getAllProcess(String type, String dType,String reportDate){
        Date from = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            from = sdf.parse(reportDate);
        }catch (Exception e){
            e.printStackTrace();
            from = new Date();
        }
        Date to = from;
        from = getDate(dType, from);
        PrjInstanceExample example = new PrjInstanceExample();
        Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
        criteria.andChannelEqualTo("0");//数据渠道为窗口录入的数据
        criteria.andPrjTypeEqualTo(type);
//        criteria.and
        criteria.andUpdateTimeBetween(from, to);
        List<PrjInstance> list = projectDao.selectByExample(example);
        if(list != null && list.size() > 0)
            return list.size();
        else
            return 0;
    }

    /**
     * 项目完成或终止数
     * @param type
     * @param completeType
     * @return
     */
    public static Integer getProcessOverOrStop(String type,  String completeType){
        PrjInstanceExample example = new PrjInstanceExample();
        Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
        criteria.andChannelEqualTo("0");//数据渠道为窗口录入的数据
        criteria.andPrjTypeEqualTo(type);
        criteria.andIsPrjCompleteEqualTo(completeType);//项目完成或终止
        List<PrjInstance> list = projectDao.selectByExample(example);
        if(list != null && list.size() > 0)
            return list.size();
        else
            return 0;
    }

    /**
     * 审批中的数量
     * @param type
     * @returns
     */
    public static Integer getAllProcessing(String type){
        com.lpcode.modules.blsp.entity.PrjInstanceExample example = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
        com.lpcode.modules.blsp.entity.PrjInstanceExample.Criteria criteria = example.createCriteria();
        if (type.equals("1")) {// 政府
            criteria.andPrjTypeEqualTo(type);
        } else {// 企业
            criteria.andPrjTypeEqualTo(type);
        }
        criteria.andChannelEqualTo("0");//数据渠道为窗口录入的数据
        criteria.andRealStartTimeIsNotNull();
        criteria.andIsPrjCompleteEqualTo("0");
        int i = projectDao.countByExample(example);
        return i;
    }

    public static Date getDate(String dType, Date from) {
        if (dType.equals("1")) {// 本日
            from = SempleDateUtil.getCurrDayFirstBegin(from);
        } else if (dType.equals("2")) {// 本周
            from = SempleDateUtil.getCurrWeekFirst(from);
        } else if (dType.equals("3")) {// 本月
            from = SempleDateUtil.getCurrMonthFirst(from);
        } else if (dType.equals("4")) {// 本季
            from = SempleDateUtil.getCurrQuarterFirst(from);
        } else if (dType.equals("5")) {// 本年
            from = SempleDateUtil.getCurrYearFirst(from);
        }
        return from;
    }

    private static Date getEndDate(String dType, Date from) {
        Date to = new Date();
        if (dType.equals("1")) {// 本日
            to = SempleDateUtil.getCurrDayFirstEnd(from);
        } else if (dType.equals("2")) {// 本周
            to = SempleDateUtil.getCurrWeekEnd(from);
        } else if (dType.equals("3")) {// 本月
            to = SempleDateUtil.getCurrMonthEnd(from);
        } else if (dType.equals("4")) {// 本季
            to = SempleDateUtil.getCurrQuarterEnd(from);
        } else if (dType.equals("5")) {// 本年
            to = SempleDateUtil.getCurrYearEnd(from);
        }
        return to;
    }
}
