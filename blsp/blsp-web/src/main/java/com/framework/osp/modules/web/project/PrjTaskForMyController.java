package com.framework.osp.modules.web.project;

import com.framework.core.result.ListResult;
import com.framework.core.result.RequestDTOPage;
import com.framework.core.result.Result;
import com.framework.core.utils.BeanCopy;
import com.framework.core.utils.DateUtil;
import com.framework.osp.common.config.Global;
import com.framework.osp.common.persistence.Page;
import com.framework.osp.modules.sys.entity.Office;
import com.framework.osp.modules.sys.entity.User;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.framework.osp.modules.web.bean.PwbmData1Bean;
import com.framework.osp.modules.web.bean.PwbmDataBean;
import com.framework.osp.modules.web.util.PwbmEncodeUtil;
import com.google.gson.Gson;
import com.lpcode.modules.service.impl.project.util.ProjectUtil;
import com.lpcode.modules.service.project.dto.*;
import com.lpcode.modules.service.project.inf.PrjTaskDefineService;
import com.lpcode.modules.service.project.inf.PrjTaskService;
import com.lpcode.modules.service.project.inf.PrjTaskTransferDetailService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的已办和办结Controller
 *
 * @author lpcode
 *
 */

@Controller
@RequestMapping(value = "${adminPath}/prjTask")
public class PrjTaskForMyController {
	@Autowired
	private PrjTaskService prjTaskService;
	@Autowired
	private PrjTaskDefineService prjTaskDefineService;
	@Autowired
	private PrjTaskTransferDetailService prjTaskTransferDetailService;

	/**
	 * 我的已办、办结
	 *
	 * @param request
	 * @param response
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pass/list")
	public String list(HttpServletRequest request, HttpServletResponse response, PrjTaskTodoListDTO dto, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())) {
			dto.setUserId(user.getId());
			RequestDTOPage<PrjTaskTodoListDTO> requestPage = new RequestDTOPage<PrjTaskTodoListDTO>();
			Page<PrjTaskTodoListDTO> page = new Page<PrjTaskTodoListDTO>(request, response);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (StringUtils.isNotBlank(dto.getBeginCreateDateStr())
					&& StringUtils.isNotBlank(dto.getEndCreateDateStr())) {
				try {
					dto.setBeginCreateDate(sdf.parse(dto.getBeginCreateDateStr() + " 00:00:00"));
					dto.setEndCreateDate(sdf.parse(dto.getEndCreateDateStr() + " 23:59:59"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			requestPage.setObj(dto);
			requestPage.setPage(page);

			ListResult<PrjTaskTransferDetailDTO> transList = null;
			// 从项目流转表中获取我的已办和办结事项
			if ("1".equals(dto.getPassType())) {
				// 已办
				transList = prjTaskTransferDetailService.findMyPass(user.getId());
			} else {
				// 办结
				transList = prjTaskTransferDetailService.findMyFinish(user.getId(), false);
			}

			if (transList != null && transList.getObj() != null && transList.getObj().size() > 0) {
				List<Long> transferId = new ArrayList<>();
				for (PrjTaskTransferDetailDTO t : transList.getObj()) {
					transferId.add(t.getId());
				}
				dto.setPrjTaskInstIds(transferId);

				// 根据项目流转表中查询出的项目事项ID查询我的已办、办结
				prjTaskService.findMyTaskPage(requestPage);

				// 根据登陆用户所属部门ID查询其部门下的事项名称，供页面查询列表使用
				List<Office> oList = user.getOfficeList();
				if (oList != null && oList.size() > 0) {
					List<String> officeIds = new ArrayList<>();
					for (Office off : oList) {
						officeIds.add(off.getId());
					}
					ListResult<com.lpcode.modules.dto.project.PrjTaskDefineDTO> taskNames = prjTaskDefineService
							.findTaskNameByDepyIds(officeIds);
					model.addAttribute("taskName", taskNames.getObj());

				}
			}
			model.addAttribute("page", page);
			model.addAttribute("prjTaskDTO", dto);
		}
		// 跳转页面：已办 or 办结
		String viewPage = "";
		if ("1".equals(dto.getPassType())) {
			// 已办
			viewPage = "taskPassList";
		} else if ("2".equals(dto.getPassType())) {
			// 办结
			viewPage = "taskFinishList";
		} else {
			return "";
		}
		return "modules/project/" + viewPage;

	}

	/**
	 * 可上传批文项目列表
	 *
	 * @param request
	 * @param response
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cert/list")
	public String certList(HttpServletRequest request, HttpServletResponse response, PrjTaskTodoListDTO dto,
			Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())) {
			dto.setUserId(user.getId());
			RequestDTOPage<PrjTaskTodoListDTO> requestPage = new RequestDTOPage<PrjTaskTodoListDTO>();
			Page<PrjTaskTodoListDTO> page = new Page<PrjTaskTodoListDTO>(request, response);
			requestPage.setObj(dto);
			requestPage.setPage(page);

			ListResult<PrjTaskTransferDetailDTO> transList = null;
			transList = prjTaskTransferDetailService.findMyFinish(user.getId(), true);
			if (transList != null && transList.getObj() != null && transList.getObj().size() > 0) {
				List<Long> transferId = new ArrayList<>();
				for (PrjTaskTransferDetailDTO t : transList.getObj()) {
					transferId.add(t.getId());
				}
				dto.setPrjTaskInstIds(transferId);
				prjTaskService.findMyTaskPage(requestPage);
			}
			model.addAttribute("page", page);
			model.addAttribute("prjTaskDTO", dto);
		}
		return "modules/project/taskCertUploadList";

	}
	

	/**
	 * 批文上传输入框ajax
	 * @param request
	 * @param prjTaskInstId
	 * @param model
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "/cert/uploadInput")
	public String certUploadInput(HttpServletRequest request, Long prjTaskInstId, Model model) {
		if (prjTaskInstId != null) {
			PrjTaskDTO dto = prjTaskService.findTaskById(prjTaskInstId).getObj();
			if (dto != null) {
				Result<PrjTaskDefineDTO> taskDefine = prjTaskService.findTaskDefine(dto.getId()); // 事项定义
				dto.setIsAllMater(taskDefine.getObj().getIsAllMater());
				dto.setIsConMater(taskDefine.getObj().getIsConMater());
				/**
				 * 通过事项配置去获取部门名称
				 */
				dto.setCertDept(ProjectUtil.getDeptNameByDeptId(taskDefine.getObj().getDeptId()));
				// 查询未提供的材料
				if ("1".equals(dto.getIsAllMater())) {
					ListResult<PrjTaskMaterialDTO> materialList = prjTaskService.findTaskMaterialList(prjTaskInstId); // 事项材料
					if (materialList.getObj() != null && materialList.getObj().size() > 0) {
						List<PrjTaskMaterialDTO> prjTaskMaterialDTOs = new ArrayList<PrjTaskMaterialDTO>();
						for (PrjTaskMaterialDTO ma: materialList.getObj()) {
							if (!"1".equals(ma.getIsComplete())) {
								prjTaskMaterialDTOs.add(ma);
							}
						}
						if (prjTaskMaterialDTOs.size() > 0) {
							dto.setPrjTaskMaterialDTOs(prjTaskMaterialDTOs);
						}
					}
				}
				
				return new Gson().toJson(dto);
			}
		}
		return "redirect:" + Global.getAdminPath() + "/prjTask/cert/list";
	}

	/**
	 * 保存批文上传
	 *
	 * @param request
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cert/upload")
	public String certUpload(HttpServletRequest request, PrjTaskAuditDTO dto, Model model) {
		String userId = UserUtils.getUser().getId();
		if (dto.getPrjTaskInstId() != null && StringUtils.isNotBlank(userId)) {
			dto.setUserId(userId);
			prjTaskService.certUpload(dto);
		}
		if ("1".equals(request.getParameter("certCodeFlag"))) 
			return "redirect:" + Global.getAdminPath() + "/prjTask/cert/lpcode/list";
		else 
			return "redirect:" + Global.getAdminPath() + "/prjTask/cert/list";
	}

	/**
	 * 可领取批文项目列表
	 *
	 * @param request
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cert/getList")
	public String certGet(HttpServletRequest request, HttpServletResponse response, PrjFetchViewDTO dto,
			Model model) {
		String userId = UserUtils.getUser().getId();
		if (StringUtils.isNotBlank(userId)) {
			RequestDTOPage<PrjFetchViewDTO> requestPage = new RequestDTOPage<PrjFetchViewDTO>();
			Page<PrjFetchViewDTO> page = new Page<PrjFetchViewDTO>(request, response);
			requestPage.setObj(dto);
			requestPage.setPage(page);

			prjTaskService.findPrjForCertGetList(requestPage);

			model.addAttribute("page", page);
		}

		return "modules/project/prjCertGetList";
	}

	/**
	 * ajax获取项目可领取批文列表
	 *
	 * @param request
	 * @param prjInsId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cert/getCert")
	public String certUpload(HttpServletRequest request, Long prjInsId, Model model) {
		String cert = "0";
		if (prjInsId != null) {
			ListResult<PrjCertGetDTO> resultList = prjTaskService.getCert(prjInsId);
			model.addAttribute("list",resultList.getObj());
			model.addAttribute("prjId",prjInsId);
		} 
		return "modules/project/prjCertGetForm";
	}
	
	/**
	 * 保存批文领取
	 *
	 * @param request
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cert/saveCert")
	public String saveCert(HttpServletRequest request, PrjCertGetDTO dto, Model model) {
		if (dto != null && dto.getPrjTaskInstIds() != null && dto.getPrjTaskInstIds().length > 0) {
			prjTaskService.saveCert(dto);
			model.addAttribute("message","ok");
		}
		return "modules/project/prjCertGetForm";
	}
	
	
	/**
	 * 批文加码列表
	 *
	 * @param request
	 * @param response
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cert/lpcode/list")
	public String certLpcodeList(HttpServletRequest request, HttpServletResponse response, PrjTaskTodoListDTO dto, Model model) {
		RequestDTOPage<PrjTaskTodoListDTO> requestPage = new RequestDTOPage<PrjTaskTodoListDTO>();
		Page<PrjTaskTodoListDTO> page = new Page<PrjTaskTodoListDTO>(request, response);
		requestPage.setObj(dto);
		requestPage.setPage(page);
		ListResult<PrjTaskTransferDetailDTO> transList = null;
		transList = prjTaskTransferDetailService.findMyFinish(null, true);

		if (transList != null && transList.getObj() != null && transList.getObj().size() > 0) {
			List<Long> transferId = new ArrayList<>();
			for (PrjTaskTransferDetailDTO t : transList.getObj()) {
				transferId.add(t.getId());
			}
			dto.setPrjTaskInstIds(transferId);
			dto.setCompany("code");
			prjTaskService.findMyTaskPage(requestPage);
		}
		model.addAttribute("page", page);
		model.addAttribute("prjTaskDTO", dto);
		return "modules/project/taskCertLpcodeList";

	}
	
	/**
	 * 生成码图功能
	 * @param request
	 * @param response
	 * @param prjTaskInstId
	 * @param taskStatus
     * @param model
     */
	@RequestMapping(value = "/cert/createLpcode")
	public void createLpcode(HttpServletRequest request,HttpServletResponse response,  String prjTaskInstId, String taskStatus, Model model) {
		if (StringUtils.isNotBlank(prjTaskInstId)) {

			OutputStream os=null;
			try {
				String status = "1";
				if ("6".equals(taskStatus))
					status =  taskStatus;
				
				CertLpcodeDTO cert = prjTaskService.findCertLpcodeInfo(Long.parseLong(prjTaskInstId), status).getObj();
				PwbmDataBean data = new PwbmDataBean();
				PwbmData1Bean data1 = new PwbmData1Bean();
				BeanCopy.copyProperties(cert, data);
				BeanCopy.copyProperties(cert, data1);
				if ("1".equals(data.getSpjg()))
					data.setSpjg("通过");
				else
					data.setSpjg("不通过");
				
				if (StringUtils.isNotBlank(data.getFzsj()))
					data.setFzsj(DateUtil.formatDate(data.getFzsj()));
				if (StringUtils.isNotBlank(data1.getSpsj())) {
					data1.setSpsj(DateUtil.formatDate(data1.getSpsj()));
				}
				
				byte[] result = PwbmEncodeUtil.generateFormLpocde(data,data1);
				if(result!=null && result.length>0){
					os = response.getOutputStream();  
			    	response.reset();  
			    	response.setHeader("Content-Disposition","attachment;filename=lpcode.bmp");  
			    	response.setContentType("application/octet-stream; charset=utf-8");  
		    	    os.write(result);  
		    	    os.flush();  
				}else{
					System.out.println("生成码图失败！"+result);
				}
		    		
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if (os != null) {
		        	try{
		        		os.close();
		        	}catch(Exception e){
		        		e.printStackTrace();
		        	}
		        }  
			}
		}
	}
	
	
	/**
	 * 可领取退件项目列表
	 *
	 * @param request
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/reject/list")
	public String rejectList(HttpServletRequest request, HttpServletResponse response, PrjTaskTodoListDTO dto,
			Model model) {
		String userId = UserUtils.getUser().getId();
		if (StringUtils.isNotBlank(userId)) {
			dto.setUserId(userId);
			RequestDTOPage<PrjTaskTodoListDTO> requestPage = new RequestDTOPage<PrjTaskTodoListDTO>();
			Page<PrjTaskTodoListDTO> page = new Page<PrjTaskTodoListDTO>(request, response);
			requestPage.setObj(dto);
			requestPage.setPage(page);

			prjTaskService.pagedSelectForRejectBackList(requestPage);

			model.addAttribute("page", page);
		}

		return "modules/project/rejectList";
	}
	
	/**
	 * 可领取退件项目表单
	 * @param request
	 * @param prjInsId
	 * @param model
     * @return
     */
	@RequestMapping(value = "/reject/form")
	public String rejectForm(HttpServletRequest request, Long prjInsId, Model model) {
		if (prjInsId != null) {
			ListResult<PrjRejectTaskDTO> resultList = prjTaskService.getRejectTask(prjInsId);
			model.addAttribute("list",resultList.getObj());
			model.addAttribute("prjId",prjInsId);
		} 
		return "modules/project/rejectForm";
	}
	
	/**
	 * 保存可领取退件项目表单
	 *
	 * @param request
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/reject/save")
	public String saveReject(HttpServletRequest request, PrjRejectTaskDTO dto, Model model) {
		if (dto != null && dto.getPrjRejectInstIds() != null && dto.getPrjRejectInstIds().length > 0) {
			prjTaskService.saveReject(dto);
			model.addAttribute("message","ok");
		}
		return "modules/project/rejectForm";
	}
}
