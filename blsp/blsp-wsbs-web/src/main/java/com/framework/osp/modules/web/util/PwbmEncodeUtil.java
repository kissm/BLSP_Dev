package com.framework.osp.modules.web.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.framework.osp.common.config.Global;
import com.framework.osp.common.mapper.JsonMapper;
import com.framework.osp.modules.web.bean.FormReqHeader;
import com.framework.osp.modules.web.bean.PwbmData1Bean;
import com.framework.osp.modules.web.bean.PwbmDataBean;
import com.framework.osp.modules.web.bean.PwbmRequestBody;
import com.framework.osp.modules.web.bean.PwbmResponseBody;
import com.lpcode.modules.blsp.constdefine.DicConstants;

public class PwbmEncodeUtil {

	private static final String USER_NAME;// = "SJ_SERVICE_TEST";
	
	private static final String PASSWORD;// = "TEST1234";
	
	private static final String PLATFORM_ID;// = "100";
	
	private static final String PROPORTION;// = "1:1";
	
	private static final String SERVICE_PWBM_URL;// = "http://demo.lpcode.com/services/zuh/service/pwbm/fjsq.json";

	private static final String SCALE;// = "3";
	
	private static final String GAP;// = "5";
	
	static{
		USER_NAME = Global.getConfig("service.username");
		PASSWORD = Global.getConfig("service.password");
		PLATFORM_ID = Global.getConfig("service.platformid");
		PROPORTION = Global.getConfig("service.proportion");
		SERVICE_PWBM_URL = Global.getConfig("service.pwbm.url");
		SCALE = Global.getConfig("service.scale");
		GAP = Global.getConfig("service.gap");
	}
	
	private static final Log _log = LogFactory.getLog(PwbmEncodeUtil.class);
	/**
	 * 生成批文编码（分级授权）龙贝码
	 * @param object
	 * @param formId 必填   请定义在DicConstants类
	 * @return
	 * @throws Exception
	 */
	public static byte[] generateFormLpocde(PwbmDataBean data,PwbmData1Bean data1) throws Exception{
		_log.debug("PwbmEncodeUtil:data"+JsonMapper.toJsonString(data));
		_log.debug("PwbmEncodeUtil:data1"+JsonMapper.toJsonString(data1));
		byte[] ret = null;
		PwbmRequestBody<PwbmDataBean,PwbmData1Bean> requestBody = new PwbmRequestBody<PwbmDataBean,PwbmData1Bean>();
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
		requestBody.setData(data);
		requestBody.setData1(data1);
		try{
			String result = HttpClientRequestUtil.CallingRequestService(JsonMapper.toJsonString(requestBody),SERVICE_PWBM_URL);
			PwbmResponseBody responseBody = (PwbmResponseBody)JsonMapper.fromJsonString(result,PwbmResponseBody.class);
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
