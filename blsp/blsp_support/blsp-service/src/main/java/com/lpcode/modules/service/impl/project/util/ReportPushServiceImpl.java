package com.lpcode.modules.service.impl.project.util;

import com.framework.core.constants.BaseCode;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.entity.User;
import com.framework.osp.modules.sys.utils.DictUtils;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.common.utils.NewServiceReportUtils;
import com.lpcode.common.utils.ServiceBasicInfoPushUtils;
import com.lpcode.modules.blsp.constdefine.DicConstants;
import com.lpcode.modules.blsp.entity.*;
import com.lpcode.modules.blsp.mapper.*;
import com.lpcode.modules.dto.report.ServiceBasicInfoPushDto;
import com.lpcode.modules.dto.report.ServiceBasicinfoPushLogDto;
import com.lpcode.modules.dto.report.ServiceReportDto;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjTaskVo;
import com.lpcode.modules.service.report.ReportPushService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 各环节 数据上报实现类
 * @author Pengs
 * @package com.lpcode.modules.service.impl.project.util
 * @fileName ReportPushUtil
 * @date 2016/12/15.
 */
@Service
public class ReportPushServiceImpl implements ReportPushService {
    @Autowired
    private PrjTaskDefineMapper prjTaskDefineMapper;
    @Autowired
    private PrjTaskMapper prjTaskMapper;
    @Autowired
    private PrjTaskMaterialDefMapper prjTaskMaterialDefMapper;
    @Autowired
    private PrjInstanceMapper prjInstanceMapper;
    @Autowired
    private PrjTaskPauseDetailMapper prjTaskPauseDetailMapper;
    @Autowired
    private ServiceSbQueueMapper serviceSbQueueMapper;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

    private String flag = DictUtils.getDictValue("report_push_switch", "report_push_switch", "0");//数据上报开关,默认为0关闭,1为打开
    /**
     * 上报企业基本信息
     *
     * @param company
     */
    @Override
    public void pushCompany(PrjBuildCompany company) {
        if("1".equals(flag)){
            ServiceBasicInfoPushDto pushDto = new ServiceBasicInfoPushDto();
            pushDto.setSbid(company.getId().toString());
            pushDto.setSbstatus(DicConstants.SB_TYPE_QY);
            pushDto.setZjlx("50");//企业证件类型 50为固定值
            pushDto.setZjhm(company.getCompanyCode());
            pushDto.setQymc(company.getCompany());
            pushDto.setDz(company.getCompanyAddr());
            pushDto.setGddh(company.getCompanyMphone());
            pushDto.setFrxm(company.getLegalEntity());
            ServiceBasicinfoPushLogDto dto = new ServiceBasicinfoPushLogDto();
            dto.setSbId(company.getId().toString());
            dto.setSbBasicId(company.getCompanyCode());

            savePushGxData(dto,pushDto);

//            pushGxToGxpt(dto,pushDto);
        }

    }

    /**
     * 上报项目基本信息
     *
     * @param prjvo
     */
    @Override
    public void pushProject(PrjInstance prjvo) {
        if("1".equals(flag)){
            ServiceBasicInfoPushDto pushDto = new ServiceBasicInfoPushDto();
            pushDto.setSbid(prjvo.getId().toString());
            pushDto.setSbstatus(DicConstants.SB_TYPE_TZJS);
            pushDto.setZjlx("50");//项目企业证件类型 50为固定值
            pushDto.setZjhm(prjvo.getCompanyCode());
            pushDto.setQymc(prjvo.getCompany());
            pushDto.setXmlx(prjvo.getPrjType());
            pushDto.setPrjCode(prjvo.getPrjCode());
            pushDto.setPrjCat(prjvo.getPrjCat());
            pushDto.setAgentName(prjvo.getAgentName());
            pushDto.setAgentMphone(prjvo.getAgentMphone());
            pushDto.setAgentPhone(prjvo.getAgentPhone());
            pushDto.setPrjName(prjvo.getPrjName());
            pushDto.setPrjNature(prjvo.getPrjNature());
            pushDto.setPrjRedlineSpace(prjvo.getPrjRedlineSpace());
            pushDto.setPrjAddr(prjvo.getPrjAddr());
            pushDto.setPrjFloorSpace(prjvo.getPrjFloorSpace());
            pushDto.setPrjDescription(prjvo.getPrjDescription());
            pushDto.setPriceType(prjvo.getPriceType());
            pushDto.setLandType(prjvo.getLandType());
            pushDto.setUseageType(prjvo.getUseageType());
            pushDto.setLpcodeAddr(prjvo.getLpcodeAddr());
            pushDto.setIsNeedPreAudit(prjvo.getIsNeedPreAudit());
            pushDto.setIsSpecialProject(prjvo.getIsSpecialProject());
            pushDto.setIsWithBasePart(prjvo.getIsWithBasePart());
            pushDto.setIsItType(prjvo.getIsItType());
            pushDto.setIsGovType(prjvo.getIsGovType());
            pushDto.setInvestEstimate(prjvo.getInvestEstimate());
            pushDto.setFundSource(prjvo.getFundSource());

            ServiceBasicinfoPushLogDto dto = new ServiceBasicinfoPushLogDto();
            dto.setSbId(prjvo.getId().toString());
            dto.setSbBasicId(prjvo.getCompanyCode());
            savePushGxData(dto,pushDto);
//            pushGxToGxpt(dto,pushDto);
        }
    }

    /**
     * 审批环节推送 1.申办 2.受理
     *
     * @param prjTask
     * @param prj
     */
    @Override
    public void sbSlDataMethod(PrjTaskVo prjTask, PrjInstanceVo prj) {
        try {
            PrjTaskDefine taskDefine = prjTaskDefineMapper.selectByPrimaryKey(prjTask.getTaskId());
            PrjTaskMaterialDefExample example = new PrjTaskMaterialDefExample();
            example.createCriteria().andTaskIdEqualTo(prjTask.getTaskId());
            List<PrjTaskMaterialDef> defMaterials = prjTaskMaterialDefMapper.selectByExample(example);
            PrjTaskExample prjTaskExample = new PrjTaskExample();
            prjTaskExample.createCriteria().andPrjIdEqualTo(prjTask.getPrjId()).andTaskIdEqualTo(prjTask.getTaskId());
            List<PrjTask> tasks = prjTaskMapper.selectByExample(prjTaskExample);
            PrjTask prjTaskPo = tasks.get(0);
            BeanCopy.copyProperties(prjTaskPo, prjTask);
            String materialIds = "";
            for (PrjTaskMaterialDef material : defMaterials) {
                materialIds += material.getMaterialId() + ";";
            }
            ServiceReportDto dto = new ServiceReportDto();
            //申办（必要环节）
            dto.setSblsh(prjTask.getId().toString());
            dto.setSxbm(taskDefine.getTaskCode().split("_")[0]);//事项编码
            dto.setSxmc(taskDefine.getTaskName());//事项名称
            dto.setFsxbm(prj.getPrjCode());//父事项编码
            dto.setFsxmc(prj.getPrjName());//父事项名称
            dto.setSqrlx(DicConstants.SQQY_CODE);//申请人类型
            dto.setSqrmc(StringUtils.isNotBlank(prj.getCompany()) ? prj.getCompany():"暂无");//申请人名称
            dto.setSqrzjlx(DicConstants.ZZJGDM_CODE);//申请人证件类型
            dto.setSqrzjhm(StringUtils.isNotBlank(prj.getCompanyCode()) ? prj.getCompanyCode():"暂无");//申请人证件
            dto.setLxrxm(StringUtils.isNotBlank(prj.getAgentName()) ? prj.getAgentName():"暂无");//联系人姓名
            dto.setLxrsj(StringUtils.isNotBlank(prj.getAgentMphone()) ? prj.getAgentMphone():"暂无");//联系人手机
            dto.setSbxmmc(prj.getPrjName());//申办项目名称
            dto.setSbclqd(StringUtils.isNotBlank(materialIds) ? materialIds:"暂无");//申办材料清单
            dto.setTjfs("1");//提取方式 窗口提交为0，网上为1
            dto.setSbhzh(prj.getPrjCode());//申办回执号
            dto.setSbsj(new Date());//申办时间
            dto.setYwlsh(prjTask.getId().toString());//	业务流水号
            dto.setSlbmmc(ProjectUtil.getDeptNameByDeptId(taskDefine.getDeptId()).trim());    //	受理部门名称
            dto.setSlbmzzjddm(ProjectUtil.getDeptCodeByDeptId(taskDefine.getDeptId()).trim());    //	受理部门组织机构代码
            User currUser = UserUtils.get(prjTask.getCurrUser());
            dto.setBlrxm(StringUtils.isNotBlank(currUser.getName()) ? currUser.getName() : "审批人员");    //	办理人姓名
            dto.setBlrgh(currUser.getNo());    //	办理人工号
            dto.setSlztdm("1");    //	受理状态代码
            dto.setSlhzh(prj.getPrjCode());    //	受理回执号 (暂用项目编号)
            dto.setSlsj(sdfDate.format(prjTask.getTaskStartTime()));    //	受理时间
            dto.setXzqhdm(DicConstants.KWXZQY_CODE);// xzqhdm;业务发生所在行政区划代码
            dto.setGxdxzqhdm(DicConstants.KWXZQY_CODE);// gxdxzqhdm;	 发生业务管辖地行政区划代码
            dto.setBlsx(sdfDate.format(prjTask.getTaskEndTime())); //blsx 办理时限（当前事项应办结日期）yyyy-MM-dd
            dto.setTransactor(getName());//处理人姓名
            dto.setSbStatus(DicConstants.REPORT_STATUS_SB);
            savePushSbData(dto);

//            newSbDataService.dataReport(dto);
//            dto.setSbStatus(DicConstants.REPORT_STATUS_SL);
//            newSbDataService.dataReport(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 审批环节推送 5.办结
     *
     * @param prjTask
     * @param bjjgCode
     */
    @Override
    public void sbBjDataMethod(PrjTask prjTask, String bjjgCode) {
        try {
            PrjTaskDefine taskDefine = prjTaskDefineMapper.selectByPrimaryKey(prjTask.getTaskId());
            PrjInstance prjInstance = prjInstanceMapper.selectByPrimaryKey(prjTask.getPrjId());
            ServiceReportDto dto = new ServiceReportDto();
            //办结
            dto.setSblsh(prjTask.getId().toString());//	申办流水号
            dto.setSxbm(taskDefine.getTaskCode().split("_")[0]);//事项编码
            dto.setSxmc(taskDefine.getTaskName());//事项名称
            dto.setBjbmmc(ProjectUtil.getDeptNameByDeptId(taskDefine.getDeptId()).trim());//	办结部门名称
            dto.setBjbmzzjddm(ProjectUtil.getDeptCodeByDeptId(taskDefine.getDeptId()).trim());    //	办结部门组织机构代码
            dto.setXzqhdm(DicConstants.KWXZQY_CODE);//	办结部门行政区划代码
            dto.setBjjgdm(bjjgCode);
            //退回意见或
            dto.setBjjgms(prjTask.getAuditDesc());
            if ("1".equals(bjjgCode)){
                dto.setZfhthyy(prjTask.getAuditDesc());
            }
            dto.setSfje("0");    //	收费金额
            dto.setJedwdm("CNY");    //	金额单位代码
            Date endTime = prjTask.getTaskRealEndtime() == null ? new Date() : prjTask.getTaskRealEndtime();
            dto.setBjsj(sdf.format(endTime)); //办结时间
            dto.setZjgzmc(prjTask.getCertTitle());//	证件/盖章名称
            dto.setZjbh(prjTask.getCertCode());    //	证件编号
            dto.setFzgzdw(prjTask.getCertDept());    //	发证/盖章单位
            //private String zjyxqx;	//	证件有效期限
            if( "2".equals(bjjgCode)) {
                dto.setZjbh("");
                dto.setZjgzmc("");
                dto.setFzgzdw("");
                dto.setZfhthyy(prjInstance.getStopReason());
            }
            dto.setTransactor(getName());//处理人姓名
            dto.setSbStatus(DicConstants.REPORT_STATUS_BJ);
            savePushSbData(dto);
//            newSbDataService.dataReport(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 领证登记
     * @param prjTask
     */
    @Override
    public void sbLzdjDataMethod(PrjTask prjTask) {
        try {
            ServiceReportDto dto = new ServiceReportDto();
            prjTask = prjTaskMapper.selectByPrimaryKey(prjTask.getId());
            PrjInstance prjInstance = prjInstanceMapper.selectByPrimaryKey(prjTask.getPrjId());
            PrjTaskDefine taskDefine = prjTaskDefineMapper.selectByPrimaryKey(prjTask.getTaskId());
            dto.setSblsh(prjTask.getId().toString());//	申办流水号
            dto.setSxbm(taskDefine.getTaskCode().split("_")[0]);//事项编码
            dto.setLqrxm(prjTask.getFetchMan()); //领取人姓名
            dto.setLqrzjlx("50"); //领取人证件类型
            dto.setLqrsfzjhm(StringUtils.isNotBlank(prjInstance.getCompanyCode()) ? prjInstance.getCompanyCode():"暂无"); //领取人身份证件号码
            dto.setLqfs("0");     //领取方式 0窗口领取
            Date fetchTime = prjTask.getFetchTime() == null ? new Date() : prjTask.getFetchTime();
            dto.setLqsj(sdf.format(fetchTime));     //领取时间
            dto.setXzqhdm(DicConstants.KWXZQY_CODE);//	行政区划代码
            dto.setTransactor(getName());//处理人姓名
            dto.setSbStatus(DicConstants.REPORT_STATUS_LZDJ);
            savePushSbData(dto);
//            newSbDataService.dataReport(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 特别程序申请
     * @param prjTaskPauseDetail
     */
    @Override
    public void sbTbcxsqDataMethod(PrjTaskPauseDetail prjTaskPauseDetail) {
        //特别程序申请
        try {
            ServiceReportDto dto = new ServiceReportDto();
            PrjTask prjTask = prjTaskMapper.selectByPrimaryKey(prjTaskPauseDetail.getPrjTaskInstId());
            PrjTaskDefine taskDefine = prjTaskDefineMapper.selectByPrimaryKey(prjTask.getTaskId());
            PrjTaskPauseDetailExample example = new PrjTaskPauseDetailExample();
            example.createCriteria().andPrjTaskInstIdEqualTo(prjTask.getId());
            int tbcxsortNum = prjTaskPauseDetailMapper.countByExample(example);
            dto.setSblsh(prjTask.getId().toString());//	申办流水号
            dto.setSxbm(taskDefine.getTaskCode().split("_")[0]);//事项编码
            dto.setXh(tbcxsortNum + "");    //	序号
            dto.setTbcxsort(tbcxsortNum + "");//特别程序排序
            dto.setTbcxzl("B");    //	特别程序种类
            dto.setTbcxzlmc(DictUtils.getDictLabel(prjTaskPauseDetail.getPauseType(), "task_pause_type", ""));    //	特别程序种类名称
            Date pauseStartTime = prjTaskPauseDetail.getPauseStartTime() == null ? new Date() : prjTaskPauseDetail.getPauseStartTime();
            dto.setTbcxksrq(sdf.format(pauseStartTime));    //	特别程序开始日期
            dto.setTbcxpzr(UserUtils.get(prjTaskPauseDetail.getCreator()).getName());    //	特别程序批准人
            dto.setTbcxqdly(prjTaskPauseDetail.getPauseDesc());    //	特别程序启动理由或依据
            dto.setSqnr(prjTaskPauseDetail.getPauseDesc());    //	申请内容
            dto.setTbcxsx(prjTaskPauseDetail.getDuration().toString());    //	特别程序时限
            dto.setTbcxsxdw("G"); //	特别程序时限单位 工作日:G 自然日:Z
            dto.setXzqhdm(DicConstants.KWXZQY_CODE);//	行政区划代码
            dto.setTransactor(getName());//处理人姓名
            dto.setSbStatus(DicConstants.REPORT_STATUS_TBCXSQ);
            savePushSbData(dto);
//            newSbDataService.dataReport(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 特别程序申请结果
     * @param prjTaskPauseDetail
     */
    @Override
    public void sbTbcxsqjgDataMethod(PrjTaskPauseDetail prjTaskPauseDetail) {
        //特别程序申请结果
        try {
            ServiceReportDto dto = new ServiceReportDto();
            PrjTask prjTask = prjTaskMapper.selectByPrimaryKey(prjTaskPauseDetail.getPrjTaskInstId());
            PrjTaskDefine taskDefine = prjTaskDefineMapper.selectByPrimaryKey(prjTask.getTaskId());
            PrjTaskPauseDetailExample example = new PrjTaskPauseDetailExample();
            example.createCriteria().andPrjTaskInstIdEqualTo(prjTask.getId());
            int tbcxsortNum = prjTaskPauseDetailMapper.countByExample(example);
            dto.setSblsh(prjTask.getId().toString());//	申办流水号
            dto.setSxbm(taskDefine.getTaskCode().split("_")[0]);//事项编码
            dto.setTbcxjg("暂停恢复");    //	特别程序结果
            dto.setXh(tbcxsortNum + "");    //	序号
            dto.setTbcxsort(tbcxsortNum + "");//特别程序排序
            Date pauseEndTime = prjTaskPauseDetail.getPauseEndTime() == null ? new Date() : prjTaskPauseDetail.getPauseEndTime();
            String pauseEndTimeStr = sdf.format(pauseEndTime);
            dto.setJgcsrq(pauseEndTimeStr);//结果产生日期
            dto.setTbcxjsrq(pauseEndTimeStr);//特别程序结束日期
            dto.setTbcxsfje("0");    //	特别程序收费金额
            dto.setJedwdm("CNY");    //	金额单位代码
            dto.setXzqhdm(DicConstants.KWXZQY_CODE);//	行政区划代码
            dto.setTransactor(getName());//处理人姓名
            dto.setSbStatus(DicConstants.REPORT_STATUS_TBCXSQJG);
            savePushSbData(dto);
//            newSbDataService.dataReport(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取操作人名称
     * @return
     */
    private String getName(){
        String name = "窗口工作人员";
        String operationName = UserUtils.getUser().getName();
        if(StringUtils.isBlank(operationName)){
            operationName = name;
        }
        return operationName;
    }


    /**
     * 上报指定事项
     *
     * @param taskInstId 事项实例ID
     * @return
     */
    @Override
    public int initPushTaskBegin(Long taskInstId) {

        PrjTask prjTask = prjTaskMapper.selectByPrimaryKey(taskInstId);
        PrjInstance prjInstance = prjInstanceMapper.selectByPrimaryKey(prjTask.getPrjId());
        PrjInstanceVo prjInstanceVo = new PrjInstanceVo();
        BeanCopy.copyProperties(prjInstance , prjInstanceVo);
        PrjTaskVo prjTaskVo = new PrjTaskVo();
        BeanCopy.copyProperties(prjTask , prjTaskVo);
        //上报申办受理
        sbSlDataMethod(prjTaskVo, prjInstanceVo);
        return 0;
    }

    /**
     * 把共享数据先记入到数据库中
     * @param pushDto
     */
    private void savePushGxData(ServiceBasicinfoPushLogDto dto,ServiceBasicInfoPushDto pushDto){
        Date now = new Date();
        ServiceSbQueue serviceSbQueue = new ServiceSbQueue();
        try{
            String resultJson = ServiceBasicInfoPushUtils.getRequestBody(pushDto);
            serviceSbQueue.setCreatTime(now);
            serviceSbQueue.setCreator(UserUtils.getUser().getId());
            serviceSbQueue.setSbStatus(pushDto.getSbstatus());
            serviceSbQueue.setSbType(DicConstants.SB_TYPE_GXPT);//1为上报统一平台，2为共享平台，3为廉情预警
            serviceSbQueue.setSbXml(resultJson);
            serviceSbQueue.setSbDate(now);
            serviceSbQueue.setSbIsjh(DicConstants.SB_ISJH_NOUP);//是否已经上报；1是0否
            serviceSbQueue.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
            serviceSbQueue.setSbSort(0);
            serviceSbQueue.setTbcxSort(0);
            serviceSbQueue.setSbTimes(0);
            serviceSbQueue.setSbId(dto.getSbId());
            serviceSbQueue.setSbBasicId(dto.getSbBasicId());
            serviceSbQueueMapper.insert(serviceSbQueue);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 把上报数据先记入到数据库中
     * @param serviceReportDto
     */
    @Override
    public void savePushSbData(ServiceReportDto serviceReportDto){
        ServiceSbQueue serviceSbQueue = new ServiceSbQueue();
        Date now = new Date();
        try{
            String resultJson = NewServiceReportUtils.getRequestBody(serviceReportDto,serviceReportDto.getSbStatus());
            serviceSbQueue.setCreatTime(now);
            serviceSbQueue.setCreator(UserUtils.getUser().getId());
            serviceSbQueue.setSbStatus(serviceReportDto.getSbStatus());
            serviceSbQueue.setSbType(DicConstants.SB_TYPE_TYPT);//1为上报统一平台，2为共享平台，3为廉情预警
            serviceSbQueue.setSbXml(resultJson);
            serviceSbQueue.setSbDate(now);
            serviceSbQueue.setSbIsjh(DicConstants.SB_ISJH_NOUP);//是否已经上报；1是0否
            serviceSbQueue.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
            int xh = Integer.parseInt(StringUtils.isNotBlank(serviceReportDto.getXh()) ? serviceReportDto.getXh():"0" );
            serviceSbQueue.setSbSort(xh);
            serviceSbQueue.setTbcxSort(xh);
            serviceSbQueue.setSbTimes(0);
            serviceSbQueueMapper.insert(serviceSbQueue);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 通过事项实例ID生成事项的受理
     * @param taskId
     */
    @Override
    public void createSlJob(Long taskId) {
        //受理
        try {
            PrjTask prjTask = prjTaskMapper.selectByPrimaryKey(taskId);
            PrjInstance prj = prjInstanceMapper.selectByPrimaryKey(prjTask.getPrjId());
            PrjTaskDefine taskDefine = prjTaskDefineMapper.selectByPrimaryKey(prjTask.getTaskId());
            PrjTaskMaterialDefExample example = new PrjTaskMaterialDefExample();
            example.createCriteria().andTaskIdEqualTo(prjTask.getTaskId());
            List<PrjTaskMaterialDef> defMaterials = prjTaskMaterialDefMapper.selectByExample(example);
            PrjTaskExample prjTaskExample = new PrjTaskExample();
            prjTaskExample.createCriteria().andPrjIdEqualTo(prjTask.getPrjId()).andTaskIdEqualTo(prjTask.getTaskId());
            List<PrjTask> tasks = prjTaskMapper.selectByExample(prjTaskExample);
            PrjTask prjTaskPo = tasks.get(0);
            BeanCopy.copyProperties(prjTaskPo, prjTask);
            String materialIds = "";
            for (PrjTaskMaterialDef material : defMaterials) {
                materialIds += material.getMaterialId() + ";";
            }
            ServiceReportDto dto = new ServiceReportDto();
            //申办（必要环节）
            dto.setSblsh(prjTask.getId().toString());
            dto.setSxbm(taskDefine.getTaskCode().split("_")[0]);//事项编码
            dto.setSxmc(taskDefine.getTaskName());//事项名称
            dto.setFsxbm(prj.getPrjCode());//父事项编码
            dto.setFsxmc(prj.getPrjName());//父事项名称
            dto.setSqrlx(DicConstants.SQQY_CODE);//申请人类型
            dto.setSqrmc(org.apache.commons.lang.StringUtils.isNotBlank(prj.getCompany()) ? prj.getCompany():"暂无");//申请人名称
            dto.setSqrzjlx(DicConstants.ZZJGDM_CODE);//申请人证件类型
            dto.setSqrzjhm(org.apache.commons.lang.StringUtils.isNotBlank(prj.getCompanyCode()) ? prj.getCompanyCode():"暂无");//申请人证件
            dto.setLxrxm(org.apache.commons.lang.StringUtils.isNotBlank(prj.getAgentName()) ? prj.getAgentName():"暂无");//联系人姓名
            dto.setLxrsj(org.apache.commons.lang.StringUtils.isNotBlank(prj.getAgentMphone()) ? prj.getAgentMphone():"暂无");//联系人手机
            dto.setSbxmmc(prj.getPrjName());//申办项目名称
            dto.setSbclqd(org.apache.commons.lang.StringUtils.isNotBlank(materialIds) ? materialIds:"暂无");//申办材料清单
            dto.setTjfs("1");//提取方式 窗口提交为0，网上为1
            dto.setSbhzh(prj.getPrjCode());//申办回执号
            dto.setSbsj(new Date());//申办时间
            dto.setYwlsh(prjTask.getId().toString());//	业务流水号
            dto.setSlbmmc(ProjectUtil.getDeptNameByDeptId(taskDefine.getDeptId()).trim());    //	受理部门名称
            dto.setSlbmzzjddm(ProjectUtil.getDeptCodeByDeptId(taskDefine.getDeptId()).trim());    //	受理部门组织机构代码
            User currUser = UserUtils.get(prjTask.getCurrUser());
            dto.setBlrxm(org.apache.commons.lang.StringUtils.isNotBlank(currUser.getName()) ? currUser.getName() : "审批人员");    //	办理人姓名
            dto.setBlrgh(currUser.getNo());    //	办理人工号
            dto.setSlztdm("1");    //	受理状态代码
            dto.setSlhzh(prj.getPrjCode());    //	受理回执号 (暂用项目编号)
            dto.setSlsj(sdfDate.format(prjTask.getTaskStartTime()));    //	受理时间
            dto.setXzqhdm(DicConstants.KWXZQY_CODE);// xzqhdm;业务发生所在行政区划代码
            dto.setGxdxzqhdm(DicConstants.KWXZQY_CODE);// gxdxzqhdm;	 发生业务管辖地行政区划代码
            dto.setBlsx(sdfDate.format(prjTask.getTaskEndTime())); //blsx 办理时限（当前事项应办结日期）yyyy-MM-dd
            dto.setTransactor(getName());//处理人姓名
            dto.setSbStatus(DicConstants.REPORT_STATUS_SL);
            savePushSbData(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 通过事项实例ID生成事项的申办
     * @param taskId
     */
    @Override
    public void createSbJob(Long taskId) {
        //受理
        try {
            PrjTask prjTask = prjTaskMapper.selectByPrimaryKey(taskId);
            PrjInstance prj = prjInstanceMapper.selectByPrimaryKey(prjTask.getPrjId());
            PrjTaskDefine taskDefine = prjTaskDefineMapper.selectByPrimaryKey(prjTask.getTaskId());
            PrjTaskMaterialDefExample example = new PrjTaskMaterialDefExample();
            example.createCriteria().andTaskIdEqualTo(prjTask.getTaskId());
            List<PrjTaskMaterialDef> defMaterials = prjTaskMaterialDefMapper.selectByExample(example);
            PrjTaskExample prjTaskExample = new PrjTaskExample();
            prjTaskExample.createCriteria().andPrjIdEqualTo(prjTask.getPrjId()).andTaskIdEqualTo(prjTask.getTaskId());
            List<PrjTask> tasks = prjTaskMapper.selectByExample(prjTaskExample);
            PrjTask prjTaskPo = tasks.get(0);
            BeanCopy.copyProperties(prjTaskPo, prjTask);
            String materialIds = "";
            for (PrjTaskMaterialDef material : defMaterials) {
                materialIds += material.getMaterialId() + ";";
            }
            ServiceReportDto dto = new ServiceReportDto();
            //申办（必要环节）
            dto.setSxbm(taskDefine.getTaskCode().split("_")[0]);//事项编码
            dto.setSxmc(taskDefine.getTaskName());//事项名称
            dto.setSblsh(prjTask.getId().toString());
            dto.setFsxbm(prj.getPrjCode());//父事项编码
            dto.setFsxmc(prj.getPrjName());//父事项名称
            dto.setSqrlx(DicConstants.SQQY_CODE);//申请人类型
            dto.setSqrmc(org.apache.commons.lang.StringUtils.isNotBlank(prj.getCompany()) ? prj.getCompany():"暂无");//申请人名称
            dto.setSqrzjlx(DicConstants.ZZJGDM_CODE);//申请人证件类型
            dto.setSqrzjhm(org.apache.commons.lang.StringUtils.isNotBlank(prj.getCompanyCode()) ? prj.getCompanyCode():"暂无");//申请人证件
            dto.setLxrxm(org.apache.commons.lang.StringUtils.isNotBlank(prj.getAgentName()) ? prj.getAgentName():"暂无");//联系人姓名
            dto.setLxrsj(org.apache.commons.lang.StringUtils.isNotBlank(prj.getAgentMphone()) ? prj.getAgentMphone():"暂无");//联系人手机
            dto.setSbxmmc(prj.getPrjName());//申办项目名称
            dto.setSbclqd(org.apache.commons.lang.StringUtils.isNotBlank(materialIds) ? materialIds:"暂无");//申办材料清单
            dto.setTjfs("1");//提取方式 窗口提交为0，网上为1
            dto.setSbhzh(prj.getPrjCode());//申办回执号
            dto.setSbsj(new Date());//申办时间
            dto.setYwlsh(prjTask.getId().toString());//	业务流水号
            dto.setSlbmmc(ProjectUtil.getDeptNameByDeptId(taskDefine.getDeptId()).trim());    //	受理部门名称
            dto.setSlbmzzjddm(ProjectUtil.getDeptCodeByDeptId(taskDefine.getDeptId()).trim());    //	受理部门组织机构代码
            User currUser = UserUtils.get(prjTask.getCurrUser());
            dto.setBlrxm(org.apache.commons.lang.StringUtils.isNotBlank(currUser.getName()) ? currUser.getName() : "审批人员");    //	办理人姓名
            dto.setBlrgh(currUser.getNo());    //	办理人工号
            dto.setSlztdm("1");    //	受理状态代码
            dto.setSlhzh(prj.getPrjCode());    //	受理回执号 (暂用项目编号)
            dto.setSlsj(sdfDate.format(prjTask.getTaskStartTime()));    //	受理时间
            dto.setXzqhdm(DicConstants.KWXZQY_CODE);// xzqhdm;业务发生所在行政区划代码
            dto.setGxdxzqhdm(DicConstants.KWXZQY_CODE);// gxdxzqhdm;	 发生业务管辖地行政区划代码
            dto.setBlsx(sdfDate.format(prjTask.getTaskEndTime())); //blsx 办理时限（当前事项应办结日期）yyyy-MM-dd
            dto.setTransactor(getName());//处理人姓名
            dto.setSbStatus(DicConstants.REPORT_STATUS_SB);
            savePushSbData(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
