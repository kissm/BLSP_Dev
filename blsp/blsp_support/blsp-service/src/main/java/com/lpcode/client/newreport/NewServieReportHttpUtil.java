package com.lpcode.client.newreport;

import com.framework.osp.common.config.Global;
import com.framework.osp.common.mapper.JsonMapper;
import com.lpcode.modules.blsp.constdefine.DicConstants;
import com.lpcode.modules.service.impl.project.util.HttpClientPostRequestUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NewServieReportHttpUtil {
	
	private static Log _log = LogFactory.getLog(NewServieReportHttpUtil.class);
	
	//读取配置稳健配置
	private final static String baseUrl;
	
	private final static String username;
	
	private final static String password;
	
	private final static String version;
	
	private final static String platformId;
	
	static{
		baseUrl = Global.getConfig("blsp.httpservice.report.baseurl");
		username = Global.getConfig("blsp.httpservice.report.username");
		password = Global.getConfig("blsp.httpservice.report.password");
		version = Global.getConfig("blsp.httpservice.report.version");
		platformId = Global.getConfig("blsp.httpservice.report.platformId");
	}
	public static NewInvokResult execute(String jsonObject,String sbStatus) throws Exception {
    	_log.debug("===============开始调用接口================baseUrl="+baseUrl+"username="+username+"password="+password);
    	_log.debug("===============调用参数================"+jsonObject);
    	NewInvokResult responseBody = null;
    	//如果调用接口必要信息为空直接返回错误信息
    	if(StringUtils.isBlank(baseUrl) || StringUtils.isBlank(username) || StringUtils.isBlank(password)){
    		responseBody = new NewInvokResult();
    		responseBody.setRespHeader(new NewRespHeader());
    		responseBody.getRespHeader().setRespmsg("数据上报接口配置信息为空，请检查配置文件中URL地址，用户名，密码信息是否完整");
    		responseBody.getRespHeader().setStatus("1");
    		return responseBody;
    	}
    	
    	//如果版本和平台信息为空，给默认值 1
    	NewReqHeader newReqHeader = new NewReqHeader(username,password, DicConstants.getSysdate(),DicConstants.getSysdate(),platformId,version);

    	if(StringUtils.isBlank(version)){
    		newReqHeader.setVersion("1");
    	}

		if(StringUtils.isBlank(platformId)){
			newReqHeader.setVersion("0");
		}
    	
		try{
			NewReqBody reqBody = new NewReqBody();
			reqBody.setReqHeader(newReqHeader);
			reqBody.setData(new NewReqData("1",sbStatus,jsonObject));
			_log.debug(baseUrl+"/typt/sbReport.json---->上报调用url");
			_log.debug("==================数据上报参数=================="+ JsonMapper.toJsonString(reqBody));
			String invokResult = HttpClientPostRequestUtil.CallingRequestService(JsonMapper.toJsonString(reqBody),baseUrl+"/typt/sbReport.json");
			_log.debug("==================数据上报返回结果=================="+invokResult);
			responseBody = (NewInvokResult) JsonMapper.fromJsonString(invokResult,NewInvokResult.class);
		}catch(Exception ex){
			ex.printStackTrace();
		}
        _log.debug("===============结束调用接口================");
        return responseBody;
    }
}
