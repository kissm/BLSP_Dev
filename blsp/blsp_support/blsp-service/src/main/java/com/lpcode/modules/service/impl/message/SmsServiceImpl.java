package com.lpcode.modules.service.impl.message;

import com.lpcode.modules.dto.message.ResultSMS;
import com.lpcode.modules.service.message.IClientSms;
import com.lpcode.modules.service.message.IServiceSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Package: com.lpcode.service.impl.message<br/>
 * Date: 15-6-1<br/>
 * Time: 下午5:33<br/>
 *
 * @author pengs
 */
@Service
public class SmsServiceImpl implements IServiceSms {
  @Autowired
  IClientSms client;

  @Override
  public ResultSMS send(List<String> mobiles, String content) {
    ResultSMS res = client.send(mobiles, content);
    return null;
  }
}
