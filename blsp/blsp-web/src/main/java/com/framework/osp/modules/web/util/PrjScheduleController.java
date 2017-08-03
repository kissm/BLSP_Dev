package com.framework.osp.modules.web.util;

import com.lpcode.modules.service.impl.project.util.ProjectStepUtil;
import com.lpcode.modules.service.impl.project.util.ProjectUtil;
import com.lpcode.modules.service.project.dto.Project;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjStageVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjTaskVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;
import com.framework.core.result.Result;
import com.framework.osp.common.utils.StringUtils;
import com.framework.osp.modules.web.bean.prjschedule.PrjInstanceInfo;
import com.framework.osp.modules.web.bean.prjschedule.PrjTaskInfo;
import com.framework.osp.modules.web.bean.prjschedule.ProjectScheduleReqBean;
import com.framework.osp.modules.web.bean.prjschedule.ProjectScheduleRespBean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 查询项目进度接口
 * 
 * @author Administrator
 *
 */

@RestController
@RequestMapping(value = "/projectSchedule")
public class PrjScheduleController {
	@Autowired
	private ProjectServiceInf projectServiceInf;

	@RequestMapping(value = "/progressTask", method = RequestMethod.POST, produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
	public Result<ProjectScheduleRespBean> progressTask(@RequestBody ProjectScheduleReqBean psRequest,
			HttpServletRequest request, HttpServletResponse response) {
		Result<ProjectScheduleRespBean> result = new Result<ProjectScheduleRespBean>();

		PrjInstanceInfo prjInstanceInfo = new PrjInstanceInfo();

		ProjectScheduleRespBean respBean = new ProjectScheduleRespBean();
		PrjInstanceVo prjInstanceVo = new PrjInstanceVo();
		prjInstanceVo.setPrjCode(psRequest.getPrjCode());
		if(StringUtils.isNotEmpty(psRequest.getCompany())){
		prjInstanceVo.setCompany(psRequest.getCompany());
		}
		// 通过项目编号（建设单位）查询项目基本信息
		PrjInstanceVo prjInstanceV = projectServiceInf.getPrjInstanceVoByPrj(prjInstanceVo);
		if (prjInstanceV==null) {
			result.setMsg("未能查询到该项目信息");
			result.setResCode("400");
		} else {
			Project project = projectServiceInf.getProjectByPrjId(prjInstanceV.getId());
			project.setPrjStageVo(ProjectUtil.getStageInstanceByProId(prjInstanceV.getId()));
			project.setPrjStageVoList(ProjectUtil.getStageInsListByProId(prjInstanceV.getId().toString()));
			prjInstanceInfo.setPrjName(project.getPrjInstanceVo().getPrjName());
			prjInstanceInfo.setPrjCode(project.getPrjInstanceVo().getPrjCode());
			prjInstanceInfo.setCompany(project.getPrjInstanceVo().getCompany());
			List<PrjTaskInfo> prjList = new ArrayList<PrjTaskInfo>();
			if (project.getPrjStageVoList().size() > 0) {
				for (int i = 0; i < project.getPrjStageVoList().size(); i++) {
					PrjStageVo prjStageVo = project.getPrjStageVoList().get(i);
					List<PrjTaskVo> prjTaskVoList = ProjectStepUtil.getAllTaskByInstanceStage(prjStageVo.getId(),
							project.getPrjInstanceVo().getId());
					for (int j = 0; j < prjTaskVoList.size(); j++) {
						PrjTaskVo prjTaskVo = prjTaskVoList.get(j);
						PrjTaskInfo prjTaskInfo = new PrjTaskInfo();
						prjTaskInfo.setTaskName(prjTaskVo.getTaskName());
						prjTaskInfo.setStatus(getReturnStatus(project.getPrjInstanceVo().getIsPrjComplete(),
								prjTaskVo.getTaskStatus()));
						prjList.add(prjTaskInfo);
					}

				}
			}
			respBean.setPrjInstanceInfo(prjInstanceInfo);
			respBean.setPrjTaskInfo(prjList);
		}
		 result.setObj(respBean);
		return result;
	}

	public String getReturnStatus(String isPrjComplete, String taskStatus) {
		StringBuffer status = new StringBuffer("");
		if (isPrjComplete.equals("9")) {
			status.append("终止(");
		}
		if (taskStatus.equals("0")) {
			status.append("暂存");
		} else if (taskStatus.equals("1")) {
			status.append("审批中");
		} else if (taskStatus.equals("2")) {
			status.append("暂停");
		} else if (taskStatus.equals("4")) {
			status.append("已办结");
		} else if (taskStatus.equals("5")) {
			status.append("未启动");
		} else if (taskStatus.equals("6")) {
			status.append("不通过");
		} else if (taskStatus.equals("7")) {
			status.append("已完成");
		} else if (taskStatus.equals("8")) {
			status.append("免办");
		}
		if (isPrjComplete.equals("9")) {
			status.append(")");
		}
		return status.toString();

	}
}
