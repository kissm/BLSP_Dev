package com.lpcode.client.push;

import com.framework.osp.common.config.Global;
import com.framework.osp.common.mapper.JsonMapper;
import com.lpcode.modules.blsp.constdefine.DicConstants;
import com.lpcode.modules.service.impl.project.util.HttpClientPostRequestUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

public class ServieBasicInfoPushHttpUtil {
	
	private static Log _log = LogFactory.getLog(ServieBasicInfoPushHttpUtil.class);
	
	//读取配置稳健配置
	private final static String baseUrl;
	
	private final static String username;
	
	private final static String password;
	
	private final static String version;
	
	private final static String platformId;

	private final static String QY_URL = "/zygx/report/qyjbxx.json";
	
	private final static String TZJS_URL = "/zygx/report/xmjbxx.json";
	
	static{
		baseUrl = Global.getConfig("blsp.httpservice.push.baseurl");
		username = Global.getConfig("blsp.httpservice.push.username");
		password = Global.getConfig("blsp.httpservice.push.password");
		version = Global.getConfig("blsp.httpservice.push.version");
		platformId = Global.getConfig("blsp.httpservice.push.platformId");
	}
	public static PushInvokResult execute(String jsonObject,String type) throws Exception {
    	_log.debug("===============开始调用接口================baseUrl="+baseUrl+"username="+username+"password="+password);
    	_log.debug("===============调用参数================"+jsonObject);
    	PushInvokResult responseBody = null;
    	//如果调用接口必要信息为空直接返回错误信息
    	if(StringUtils.isBlank(baseUrl) || StringUtils.isBlank(username) || StringUtils.isBlank(password)){
    		responseBody = new PushInvokResult();
    		responseBody.setRespHeader(new PushRespHeader());
    		responseBody.getRespHeader().setRespmsg("数据上报接口配置信息为空，请检查配置文件中URL地址，用户名，密码信息是否完整");
    		responseBody.getRespHeader().setStatus("1");
    		return responseBody;
    	}
    	String requestUrl = baseUrl;
    	if(DicConstants.SB_TYPE_QY.equals(type)){
    		requestUrl += QY_URL;
    	}else if(DicConstants.SB_TYPE_TZJS.equals(type)){
    		requestUrl += TZJS_URL;
    	}
    	
    	//如果版本和平台信息为空，给默认值 1
    	PushReqHeader newReqHeader = new PushReqHeader(username,password,DicConstants.getSysdate(),DicConstants.getSysdate(),platformId,version);

    	if(StringUtils.isBlank(version)){
    		newReqHeader.setVersion("1");
    	}

		if(StringUtils.isBlank(platformId)){
			newReqHeader.setVersion("0");
		}
    	
		try{
			PushReqBody<Map<String,String>> reqBody = new PushReqBody<Map<String,String>>();
			reqBody.setReqHeader(newReqHeader);
			reqBody.setData((Map) JsonMapper.fromJsonString(jsonObject,Map.class));
			_log.debug("==================基本信息推送参数=================="+ JsonMapper.toJsonString(reqBody));
			String invokResult = HttpClientPostRequestUtil.CallingRequestService(JsonMapper.toJsonString(reqBody),requestUrl);
			_log.debug("==================基本信息推送返回结果=================="+invokResult);
			responseBody = (PushInvokResult) JsonMapper.fromJsonString(invokResult,PushInvokResult.class);
		}catch(Exception ex){
			ex.printStackTrace();
		}
        _log.debug("===============结束调用接口================");
        return responseBody;
    }
}
