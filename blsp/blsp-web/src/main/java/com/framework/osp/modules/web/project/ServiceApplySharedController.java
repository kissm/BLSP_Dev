package com.framework.osp.modules.web.project;

import com.framework.core.utils.Encodes;
import com.framework.osp.common.mapper.JsonMapper;
import com.lpcode.client.push.InvokeResult;
import com.lpcode.client.push.InvokeResultForAttachment;
import com.lpcode.client.push.ServieBasicInfoHttpUtil;
import com.lpcode.common.utils.ServiceBasicInfoReuseUtils;
import com.lpcode.modules.blsp.constdefine.DicConstants;
import com.lpcode.modules.service.impl.project.util.HttpClientRequestUtil;
import com.lpcode.modules.service.impl.project.util.ProjectStepUtil;
import com.lpcode.modules.service.project.dto.pinstance.PrjStageMaterialDefineVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjTaskMaterialVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value = "${adminPath}/shared")
public class ServiceApplySharedController {

	private static Log log = LogFactory.getLog(ServiceApplySharedController.class);
	@Value("${blsp.check.shengprjcode.baseurl}")
	protected String checkShengPrjcodeBaseUrl;

	@ResponseBody
	@RequestMapping(value = "/information/reuse")
	public String informationReuse(HttpServletRequest request, HttpServletResponse response,  Model model) {
		String companyCode = request.getParameter("companyCode");
		String prjCode = request.getParameter("prjCode");
		String type = request.getParameter("type");
		Map<String,String> jsonMap = new HashMap<String,String>();
		//默认 resType = 1 抓取JSON数据，如果抓取龙贝码数据 resType = 2
		jsonMap.put("resType","1");
		//type为空时，默认给投资建设类 templateType 为 3
		if(StringUtils.isBlank(type)){
			type = DicConstants.SB_TYPE_TZJS;
		}
        //投资建设类表单证件类型为固定值 50（项目单位企业信用代码或组织机构代码）
        jsonMap.put("zjlx","50");
        jsonMap.put("zjhm",companyCode);
        jsonMap.put("prjCode",prjCode);
		String sharedDtoStr = "";
		try {
			InvokeResult invokeResult = ServieBasicInfoHttpUtil.informationReuse(jsonMap, type);
			if(invokeResult != null){
				sharedDtoStr = ServiceBasicInfoReuseUtils.getSharedInfoDto(invokeResult.getData(),type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return StringUtils.isBlank(sharedDtoStr)?"{}":sharedDtoStr;
	}

	/**
	 * 获取可复用材料
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/attachment/reuse/list")
	public String attachmentReuseList(HttpServletRequest request, HttpServletResponse response,Model model) {
		String companyCode = request.getParameter("companyCode");
		String prjCode = request.getParameter("prjCode");
		String stageId = request.getParameter("stageId");
		Map<String,String> jsonMap = new HashMap<String,String>();
		jsonMap.put("xmbm","");
		jsonMap.put("clbm","");
		//投资建设类 templateType 为 3
		jsonMap.put("ztlx",DicConstants.TEMPLATE_TYPE_TZJS);
		//投资建设类表单证件类型为固定值 50（项目单位企业信用代码或组织机构代码）
		jsonMap.put("zjlx","50");
		jsonMap.put("zjhm",companyCode);
		jsonMap.put("xmbm",prjCode);
		try {
			InvokeResultForAttachment invokeResult = ServieBasicInfoHttpUtil.attachmentReuse(jsonMap);
			Map<String,Map<String,String>> sharedAttachments = new HashMap<String, Map<String,String>>();
			if(invokeResult != null && "0".equals(invokeResult.getRespHeader().getStatus())){
				List<Map<String,String>> data = invokeResult.getData();
				for(Map<String,String> map : data){
					sharedAttachments.put(map.get("yxtcldm"),map);
				}
			}

			Long sid = stageId == null ? 0L : Long.parseLong(stageId);
			List<PrjStageMaterialDefineVo> listDefine = ProjectStepUtil.getStageDefineMaterList(sid);
			List<PrjTaskMaterialVo> listMaterial = new ArrayList<>();
			Map<String,PrjTaskMaterialVo> materialVoMap = new HashMap<String,PrjTaskMaterialVo>();
			for(PrjStageMaterialDefineVo materialDefine : listDefine){
				String yxtcldm = materialDefine.getMaterialId().toString();
				if(sharedAttachments.containsKey(yxtcldm)){
					PrjTaskMaterialVo prjTaskMaterialVo = new PrjTaskMaterialVo();
					prjTaskMaterialVo.setId(materialDefine.getMaterialId());
					prjTaskMaterialVo.setMaterialAddr(sharedAttachments.get(yxtcldm).get("clnburl"));
					prjTaskMaterialVo.setMaterName(materialDefine.getMaterName());
					materialVoMap.put(yxtcldm,prjTaskMaterialVo);
				}
			}
			for(String key : materialVoMap.keySet()){
				listMaterial.add(materialVoMap.get(key));
			}

			model.addAttribute("listMaterial",listMaterial);
			JsonMapper.toJsonString(listMaterial);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "modules/project/accept/sharedAttachmentList";
	}

	/**
	 * 获取可复用材料
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/authattachment/reuse/list")
	public String authattachmentReuseList(HttpServletRequest request, HttpServletResponse response,Model model) {
		String companyCode = request.getParameter("companyCode");
		String prjCode = request.getParameter("prjCode");
		String stageId = request.getParameter("stageId");
		Map<String,String> jsonMap = new HashMap<String,String>();
		jsonMap.put("xmbm","");
		jsonMap.put("clbm","");
		//投资建设类 templateType 为 3
		jsonMap.put("ztlx",DicConstants.TEMPLATE_TYPE_TZJS);
		//投资建设类表单证件类型为固定值 50（项目单位企业信用代码或组织机构代码）
		jsonMap.put("zjlx","50");
		jsonMap.put("zjhm",companyCode);
		jsonMap.put("xmbm",prjCode);
		String sharedDtoStr = "";
		try {
			InvokeResultForAttachment invokeResult = ServieBasicInfoHttpUtil.attachmentReuse(jsonMap);
			System.out.println("=======");
			Map<String,Map<String,String>> sharedAttachments = new HashMap<String, Map<String,String>>();
			if(invokeResult != null && "0".equals(invokeResult.getRespHeader().getStatus())){
				List<Map<String,String>> data = invokeResult.getData();
				for(Map<String,String> map : data){
					sharedAttachments.put(map.get("yxtcldm"),map);
				}
			}

			Long sid = stageId == null ? 0L : Long.parseLong(stageId);
			List<PrjStageMaterialDefineVo> listDefine = ProjectStepUtil.getStageDefineMaterList(sid);
			List<PrjTaskMaterialVo> listMaterial = new ArrayList<>();
			Map<String,PrjTaskMaterialVo> materialVoMap = new HashMap<String,PrjTaskMaterialVo>();
			for(PrjStageMaterialDefineVo materialDefine : listDefine){
				String yxtcldm = materialDefine.getMaterialId().toString();
				if(sharedAttachments.containsKey(yxtcldm)){
					PrjTaskMaterialVo prjTaskMaterialVo = new PrjTaskMaterialVo();
					prjTaskMaterialVo.setId(materialDefine.getMaterialId());
					prjTaskMaterialVo.setMaterName(materialDefine.getMaterName());
					prjTaskMaterialVo.setMaterialAddr(sharedAttachments.get(yxtcldm).get("clnburl"));
					materialVoMap.put(yxtcldm,prjTaskMaterialVo);
				}
			}
			for(String key : materialVoMap.keySet()){
				listMaterial.add(materialVoMap.get(key));
			}

			model.addAttribute("listMaterial",listMaterial);
			sharedDtoStr = JsonMapper.toJsonString(listMaterial);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return StringUtils.isBlank(sharedDtoStr)?"{}":sharedDtoStr;
	}



	@ResponseBody
	@RequestMapping(value = "/checkShengPrjCode")
	public String checkShengPrjCode(HttpServletRequest request, HttpServletResponse response,Model model){
		String shengPrjCode = request.getParameter("shengPrjCode");
		log.info("*********验证省项目编码基础URL***********="+checkShengPrjcodeBaseUrl);
		String result = chenckShengByUrl(shengPrjCode);

		return result;
	}

	/**
	 * 验证项目编号是否可用
	 * @param shengPrjCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "validateShengPrjCode")
	public String validateShengPrjCode(String shengPrjCode) {
		String result = chenckShengByUrl(shengPrjCode);
		if(result.contains("PM0000")){
			return "true";
		}else{
			return "false";
		}
	}

	/**
	 * 省编码的查询校验
	 * @param shengPrjCode
	 * @return
	 */
	private String chenckShengByUrl(String shengPrjCode){
		String result = "";
		if(org.apache.commons.lang.StringUtils.isNotBlank(checkShengPrjcodeBaseUrl)){

			try {
				String reqUrl = buildAuthCheckPrjRequest(checkShengPrjcodeBaseUrl, Encodes.urlEncode(shengPrjCode));
				log.info("*********验证省项目编码最终URL为***********"+reqUrl);
				result = HttpClientRequestUtil.CallingRequestService(reqUrl);
				if(result.contains("PM0005")){
					result = result.replace("该项目不存在","省项目编码为:"+shengPrjCode+" 的项目不存在");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			log.info("*********验证省项目编码基础URL为空，请检查配置文件是否配置正确***********="+checkShengPrjcodeBaseUrl);
			result = "{\"projectName\":\"\",\"resultCode\":\"PM0002\",\"errmsg\":\"验证省项目编码地址错误，请联系系统管理员！\",\"projInfo\":[]}";
		}
		return result;
	}

	private final String CLIENT_ID = "75601022";
	private final char LINK_CHAR = '&';
	private String buildAuthCheckPrjRequest(String baseUrl,String projectCode) {
		String now = String.valueOf(System.currentTimeMillis());
		//nonce为随机数
		//Digest = Base64(Hash(ClientId+ TimeStamp+nonce))
		// 其中，Hash算法采用SHA-1。
		StringBuilder sb = new StringBuilder();
		String random = String.valueOf(new Random().nextInt(9999));
		String[] arr = new String[]{CLIENT_ID,now,random};
		Arrays.sort(arr);
		sb.append(arr[0]).append(arr[1]).append(arr[2]);
		String digestStr = new String(DigestUtils.shaHex(sb.toString().getBytes()));
		System.out.println("**digestStr**\n  " + digestStr);

		StringBuilder encSB = new StringBuilder();
		//signature=&timestamp=&nonce=&clientSystemId=&projectCode=
		encSB.append(baseUrl);
		encSB.append("?");
		encSB.append("signature="+digestStr);
		encSB.append(LINK_CHAR);
		encSB.append("timestamp="+now);
		encSB.append(LINK_CHAR);
		encSB.append("nonce="+random);
		encSB.append(LINK_CHAR);
		encSB.append("clientSystemId="+CLIENT_ID);
		encSB.append(LINK_CHAR);
		encSB.append("projectCode="+projectCode);
		return encSB.toString();
	}


}
