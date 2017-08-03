package com.framework.osp.modules.web.util;

import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.osp.common.config.Global;
import com.framework.osp.common.mapper.JsonMapper;
import com.framework.osp.modules.web.bean.FormReqHeader;
import com.framework.osp.modules.web.bean.FormRequestBody;
import com.framework.osp.modules.web.bean.FormResponseBody;
import com.lpcode.modules.blsp.constdefine.DicConstants;
import com.lpcode.modules.service.project.dto.pinstance.AcceptVo;
import com.lpcode.modules.service.project.dto.pinstance.ProjectVo;

public class FormEncodeUtil {

	private static final String USER_NAME;// = "SJ_SERVICE_TEST";
	
	private static final String PASSWORD;// = "TEST1234";
	
	private static final String PLATFORM_ID;// = "100";
	
	private static final String PROPORTION;// = "1:1";
	
	private static final String SERVICE_FORM_URL;// = "http://wsbs.lpcode.com/services/zuh/service/form/bm.json";

	private static final String SERVICE_PRI_URL;
	private static final String SERVICE_ACCEPT_URL;
	private static final String SCALE;// = "3";
	
	private static final String GAP;// = "5";
	
	private static final String FORM_ID = "formId";
	
	static{
		USER_NAME = Global.getConfig("service.username");
		PASSWORD = Global.getConfig("service.password");
		PLATFORM_ID = Global.getConfig("service.platformid");
		PROPORTION = Global.getConfig("service.proportion");
		SERVICE_FORM_URL = Global.getConfig("service.form.url");
		SERVICE_PRI_URL = Global.getConfig("service.prj.url");
		SERVICE_ACCEPT_URL = Global.getConfig("service.accept.url");
		SCALE = Global.getConfig("service.scale");
		GAP = Global.getConfig("service.gap");
	}
	
	private static final Log _log = LogFactory.getLog(FormEncodeUtil.class);
	/**
	 * 生成表单龙贝码
	 * @param object
	 * @param formId 必填   请定义在DicConstants类
	 * @return
	 * @throws Exception
	 */
	public static byte[] generateFormLpocde(Object object,String formId) throws Exception{
		_log.debug("FormEncodeUtil:formId:"+formId+",object:"+JsonMapper.toJsonString(object));
		byte[] ret = null;
		FormRequestBody requestBody = new FormRequestBody();
		FormReqHeader header = new FormReqHeader();
		header.setUsername(USER_NAME);
		header.setPassword(PASSWORD);
		header.setPlatformId(PLATFORM_ID);
		header.setProportion(PROPORTION);
		header.setReqno(DicConstants.getSysdate());
		header.setScale(SCALE);
		header.setGap(GAP);
		header.setTimestamp(DicConstants.getSysdate());
		header.setVersion(DicConstants.SERVICE_VERSION);
		requestBody.setReqHeader(header);
		Map map = (Map)JsonMapper.fromJsonString(JsonMapper.toJsonString(object),Map.class);
		map.put(FORM_ID,formId);
		requestBody.setData(map);
		try{
			String result = HttpClientRequestUtil.CallingRequestService(JsonMapper.toJsonString(requestBody),SERVICE_FORM_URL);
			FormResponseBody responseBody = (FormResponseBody)JsonMapper.fromJsonString(result,FormResponseBody.class);
			if(responseBody!= null && "0".equals(responseBody.getRespHeader().getStatus())  && StringUtils.isNotEmpty(responseBody.getData().getPicData())){
		    	ret = Base64.decodeBase64(responseBody.getData().getPicData());
			}else{
				_log.debug("生成码图失败:"+result);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		_log.debug("龙贝码编码完成！");
		
		return ret;
	}
	/**
	 * 生成项目龙贝码，用于受理通知书
	 * @param projectVo
	 * @return
	 * @throws Exception
	 */
	public static byte[] generatePrjLpocde(ProjectVo projectVo) throws Exception{
		_log.debug("FormEncodeUtil:object:"+JsonMapper.toJsonString(projectVo));
		byte[] ret = null;
		FormRequestBody<ProjectVo> requestBody = new FormRequestBody<ProjectVo>();
		FormReqHeader header = new FormReqHeader();
		header.setUsername(USER_NAME);
		header.setPassword(PASSWORD);
		header.setPlatformId(PLATFORM_ID);
		header.setProportion(PROPORTION);
		header.setReqno(DicConstants.getSysdate());
		header.setScale(SCALE);
		header.setGap(GAP);
		header.setTimestamp(DicConstants.getSysdate());
		header.setVersion(DicConstants.SERVICE_VERSION);
		requestBody.setReqHeader(header);
		requestBody.setData(projectVo);
		try{
			String result = HttpClientRequestUtil.CallingRequestService(JsonMapper.toJsonString(requestBody),SERVICE_PRI_URL);
			FormResponseBody responseBody = (FormResponseBody)JsonMapper.fromJsonString(result,FormResponseBody.class);
			if(responseBody!= null && "0".equals(responseBody.getRespHeader().getStatus())  && StringUtils.isNotEmpty(responseBody.getData().getPicData())){
		    	ret = Base64.decodeBase64(responseBody.getData().getPicData());
			}else{
				_log.debug("生成码图失败:"+result);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		_log.debug("龙贝码编码完成！");
		
		return ret;
	}
	
	/**
	 * 生成项目龙贝码，用于受理通知书
	 * @param projectVo
	 * @return
	 * @throws Exception
	 */
	public static byte[] generateAcceptLpocde(AcceptVo acceptVo) throws Exception{
		_log.debug("FormEncodeUtil:object:"+JsonMapper.toJsonString(acceptVo));
		byte[] ret = null;
		FormRequestBody<AcceptVo> requestBody = new FormRequestBody<AcceptVo>();
		FormReqHeader header = new FormReqHeader();
		header.setUsername(USER_NAME);
		header.setPassword(PASSWORD);
		header.setPlatformId(PLATFORM_ID);
		header.setProportion(PROPORTION);
		header.setReqno(DicConstants.getSysdate());
		header.setScale(SCALE);
		header.setGap(GAP);
		header.setTimestamp(DicConstants.getSysdate());
		header.setVersion(DicConstants.SERVICE_VERSION);
		requestBody.setReqHeader(header);
		requestBody.setData(acceptVo);
		try{
			String result = HttpClientRequestUtil.CallingRequestService(JsonMapper.toJsonString(requestBody),SERVICE_ACCEPT_URL);
			FormResponseBody responseBody = (FormResponseBody)JsonMapper.fromJsonString(result,FormResponseBody.class);
			if(responseBody!= null && "0".equals(responseBody.getRespHeader().getStatus())  && StringUtils.isNotEmpty(responseBody.getData().getPicData())){
		    	ret = Base64.decodeBase64(responseBody.getData().getPicData());
			}else{
				_log.debug("生成码图失败:"+result);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		_log.debug("龙贝码编码完成！");
		
		return ret;
	}
}
