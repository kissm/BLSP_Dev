package com.lpcode.modules.controller.message;

import com.framework.core.result.AbstractResult;
import com.lpcode.modules.blsp.constdefine.ConstDefine;
import com.lpcode.modules.dto.message.RequestDTOEmail;
import com.lpcode.modules.service.message.IServiceEmail;
import com.lpcode.modules.service.message.IValidSend;
import com.lpcode.modules.service.message.IValidTplSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Package: com.lpcode.controller.message<br/>
 * Date: 15-6-1<br/>
 * Time: 下午5:32<br/>
 *
 * @author pengs
 */
@RestController
@RequestMapping("/email")
public class EmailServiceController  extends AbstractController {
  @Autowired
  private IServiceEmail service;

  @RequestMapping(value = "/send", method = RequestMethod.POST)
  public AbstractResult send(@Validated(IValidTplSend.class) @RequestBody RequestDTOEmail para, HttpServletRequest request) {
    para.setSendby(ConstDefine.SEND_BY_EMAIL);
    return queue(para);
    
  }

  @RequestMapping(value = "/senddirect", method = RequestMethod.POST)
  public AbstractResult senddirect(@Validated(IValidSend.class) @RequestBody RequestDTOEmail para, HttpServletRequest request) {
    para.setSendby(ConstDefine.SEND_BY_EMAIL);
    return queue(para);
  }
}
