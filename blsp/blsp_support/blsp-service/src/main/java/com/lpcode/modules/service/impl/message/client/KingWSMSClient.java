package com.lpcode.modules.service.impl.message.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.lpcode.sms.client.SmsWebserviceClient;
import com.lpcode.sms.client.SmsWebserviceSoap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.tempuri.SendSms;

import com.lpcode.modules.dto.message.ResultSMS;
import com.lpcode.modules.service.message.IClientSms;

/**
 * @author Pengs
 * @package com.lpcode.modules.service.impl.message.client
 * @fileName KingWSMSClient
 * @date 16/2/23.
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@PropertySource(value = {"classpath:config/message-setting.properties"})
public class KingWSMSClient implements IClientSms {
    @Value("${sms.kingw.username}")
    private String username;// = "zitui";
    @Value("${sms.kingw.password}")
    private String password;// = "zitui123";
    @Value("${sms.kingw.sendurl}")
    private String sendurl;// =
    // "http://221.179.180.158:9007/QxtSms/QxtFirewall";

    private String cnname = "";
    private static Log log_ = LogFactory.getLog(KingWSMSClient.class);

    @Override
    public ResultSMS send(List<String> mobiles, String msg) {

        SendSms sendSms = initSendSms();
        String mobile;
        if (mobiles != null && mobiles.size() > 0) {
            mobile = mobiles.get(0);
            sendSms.setMobile(mobile);
        } else {
            return new ResultSMS("00000010", "手机号码为空");
        }

        if (StringUtils.isNotBlank(msg)) {
            sendSms.setMessage(msg);
        } else {
            return new ResultSMS("00000011", "短信内容为空");
        }
//		log_.error("短信发送 " + sendSms.getMobile() + " , " + sendSms.getMessage() + " , " + sendSms.getSendtime());
        try {
            SmsWebserviceSoap smsWebserviceSoap = new SmsWebserviceClient().getSmsWebserviceSoap(sendurl);
            int res = smsWebserviceSoap.sendSms(
                    sendSms.getSysacct(),
                    sendSms.getSyspwd(),
                    sendSms.getBscode(),
                    sendSms.getStaffno(),
                    sendSms.getMobile(),
                    sendSms.getMessage(),
                    sendSms.getSendtime());
//			int res = 0;
//			System.out.println("return:" + res);
            if (res == 0) {
                return new ResultSMS();
            } else {
                return new ResultSMS(res + "", "短信服务商错误信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultSMS("00000020", "短信服务调用异常");
        }
    }

    private SendSms initSendSms() {
        SendSms sendSms = new SendSms();
        sendSms.setSysacct(username);
        sendSms.setSyspwd(password);
        sendSms.setBscode("00");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long currentTime = System.currentTimeMillis();
        currentTime += 60 * 1000;
        String strDate = sdf.format(new Date(currentTime));
        sendSms.setSendtime(strDate);
        return sendSms;
    }
}
