package com.lpcode.modules.service.impl.message;

import com.lpcode.modules.dto.message.ResultEmail;
import com.lpcode.modules.service.message.ISendParaEmail;
import com.lpcode.modules.service.message.IServiceEmail;
import org.springframework.stereotype.Service;

/**
 * Package: com.lpcode.service.impl.message<br/>
 * Date: 15-6-1<br/>
 * Time: 下午5:33<br/>
 *
 * @author pengs
 */
@Service
public class EmailServiceImpl implements IServiceEmail {
  @Override
  public ResultEmail send(ISendParaEmail para) {
    return null;
  }
}
