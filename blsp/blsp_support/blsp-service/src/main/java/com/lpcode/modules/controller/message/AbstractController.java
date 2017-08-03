package com.lpcode.modules.controller.message;

import com.framework.core.base.controller.BaseController;
import com.lpcode.modules.blsp.constdefine.ConstDefine;
import com.lpcode.modules.dto.message.RequestCommon;
import com.lpcode.modules.dto.message.RequestDTO;
import com.lpcode.modules.dto.message.ResultDTO;
import com.lpcode.modules.service.message.IServiceMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Package: com.lpcode.controller.message<br/>
 * Date: 15-6-2<br/>
 * Time: 下午2:57<br/>
 *
 * @author pengs
 */

public class AbstractController extends BaseController {
  @Autowired
  IServiceMessage service;

  protected ResultDTO checkSign(RequestDTO para) {
    String sign = para.getSign();
    if (StringUtils.isNotBlank(sign)) {
      return new ResultDTO(ConstDefine.CODE_SIGN_INVALID, ConstDefine.MSG_SIGN_INVALID);
    } else {
      return null;
    }
  }

  protected ResultDTO queue(RequestDTO para) {
    ResultDTO result = checkSign(para);
    if (null != result) return result;
    return service.queue(para);
  }

  protected String messageContent(RequestCommon para) {
    return null;
  }
}
