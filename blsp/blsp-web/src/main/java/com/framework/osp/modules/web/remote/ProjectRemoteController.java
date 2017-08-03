package com.framework.osp.modules.web.remote;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.framework.core.utils.JsonUtil;
import com.framework.osp.common.web.BaseController;
import com.framework.osp.modules.web.remote.come.Data;
import com.framework.osp.modules.web.remote.come.Input;
import com.framework.osp.modules.web.remote.come.ReqHeader;
import com.framework.osp.modules.web.remote.to.RespHeader;
import com.framework.osp.modules.web.remote.to.Return;
import com.framework.osp.modules.web.remote.to.Task;
import com.lpcode.modules.service.impl.project.util.ProjectUtil;
import com.lpcode.modules.service.project.dto.pinstance.PrjBusinessAcceptVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjTaskVo;

@Controller
@RequestMapping(value = "/remote/project")
public class ProjectRemoteController extends BaseController {

	@RequestMapping(value = "/progress")
	public String progress(HttpServletRequest request, HttpServletResponse response) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			String jsonString = sb.toString();
			Input input = (Input) JSON.parseObject(jsonString, Input.class);
			String re = getProject(input.getReqHeader(), input.getData());
			return renderString(response, re, "text/json");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getProject(ReqHeader header, Data accept) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			PrjBusinessAcceptVo vo = new PrjBusinessAcceptVo();
			vo.setAcceptCode(accept.getSblsh());
			vo = ProjectUtil.getPrjBusinessAccept(vo);
			com.framework.osp.modules.web.remote.to.Data d = new com.framework.osp.modules.web.remote.to.Data();
			if (vo != null) {
				PrjInstanceVo v = ProjectUtil.getInstanceByAcceptId(vo.getId());
				if (v != null) {
					List<PrjTaskVo> list = ProjectUtil.getAllTaskByPrjId(v.getId());
					copy(v, d);
					List<Task> listTask = d.getRESULTS();
					if (listTask == null)
						listTask = new ArrayList<Task>();
					d.setRESULTS(listTask);
					if (list != null && list.size() > 0) {
						for (PrjTaskVo task : list) {
							Task t = new Task();
							t.setSBLSH(vo.getAcceptCode());
							t.setSSBM(task.getDeptName());
							t.setSXDM(task.getTaskCode());
							t.setSXMC(task.getTaskName());
							String status = task.getTaskStatus();
							if (status != null) {
								if (status.equals("0")) {
									t.setCONTENT(task.getDeptName() + task.getTaskName() + "  未开始");
									t.setSXZT("未开始");
									t.setTIME(dateformat.format(vo.getCreatTime()));
									t.setTYPE("EX_GDBS_SB");
								} else if (status.equals("1")) {
									t.setCONTENT(task.getDeptName() + task.getTaskName() + "  审批中");
									t.setSXZT("审批中");
									t.setTYPE("EX_GDBS_SL");
									t.setTIME(dateformat.format(task.getCreatTime()));
								} else if (status.equals("2")) {
									t.setCONTENT(task.getDeptName() + task.getTaskName() + "  审批中");
									t.setSXZT("审批中");
									t.setTIME(dateformat.format(task.getCreatTime()));
									t.setTYPE("EX_GDBS_SL");
								} else if (status.equals("3")) {

								} else if (status.equals("4")) {
									t.setCONTENT(task.getDeptName() + task.getTaskName() + "  已办结");
									t.setSXZT("已办结");
									Date dd = ProjectUtil.getPrjTaskTransferDetail(task.getId()).getUpdateTime();
									if (dd != null) {
										t.setTIME(dateformat.format(dd));
									}
									t.setTYPE("EX_GDBS_BJ");

								} else if (status.equals("5")) {
									t.setCONTENT(task.getDeptName() + task.getTaskName() + "  不通过");
									t.setSXZT("不通过");
									t.setTIME(dateformat.format(task.getCreatTime()));
									t.setTYPE("EX_GDBS_BJ");
								}
							}
							listTask.add(t);
						}
					}
				}
			}
			RespHeader resp = new RespHeader();
			resp.setReqno(accept.getSblsh());
			resp.setRespmsg("success");
			resp.setRespno("000001");
			resp.setResptime(getNow());
			resp.setStatus("0");
			Return ret = new Return();
			ret.setData(d);
			ret.setRespHeader(resp);
			return JsonUtil.getJSONString(ret);
		} catch (Exception e) {
			RespHeader resp = new RespHeader();
			resp.setReqno(accept == null ? "" : accept.getSblsh());
			resp.setRespmsg("error");
			resp.setRespno("000001");
			resp.setResptime(getNow());
			resp.setStatus("-1");
			Return ret = new Return();
			ret.setRespHeader(resp);
			return JsonUtil.getJSONString(ret);
		}
	}

	private void copy(PrjInstanceVo vo, com.framework.osp.modules.web.remote.to.Data data) {
		if (vo != null && data != null) {
			data.setAgentMphone(vo.getAgentMphone());
			data.setAgentName(vo.getAgentName());
			data.setAgentPhone(vo.getAgentPhone());
			data.setComapnyFax(vo.getComapnyFax());
			data.setCompany(vo.getCompany());
			data.setCompanyAddr(vo.getCompanyAddr());
			data.setCompanyCode(vo.getCompanyCode());
			data.setCompanyMphone(vo.getCompanyMphone());
			data.setEntityMphone(vo.getEntityMphone());
			data.setEntityPhone(vo.getEntityPhone());
			data.setLegalEntity(vo.getLegalEntity());
			data.setPrjAddr(vo.getPrjAddr());
			data.setPrjCat(vo.getPrjCat());
			data.setPrjCode(vo.getPrjCode());
			data.setPrjDescription(vo.getPrjDescription());
			data.setPrjFloorSpace(vo.getPrjFloorSpace().toString());
			data.setPrjName(vo.getPrjName());
			data.setPrjNature(vo.getPrjNature());
			data.setPrjRedlineSpace(vo.getPrjRedlineSpace().toString());
		}

	}

	private String getNow() {
		StringBuffer sb = new StringBuffer();
		java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyyyMMddhhmmss");
		sb.append(format2.format(new Date()));
		return sb.toString();
	}
}
