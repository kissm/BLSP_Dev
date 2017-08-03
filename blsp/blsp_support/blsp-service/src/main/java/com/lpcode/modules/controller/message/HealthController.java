package com.lpcode.modules.controller.message;

import com.framework.core.base.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * aws健康检查服务，需要返回200 OK
 * Date: 15-6-11<br/>
 * Time: 上午10:40<br/>
 *
 * @author 郭煜华
 */
@RestController
@RequestMapping("/health")
public class HealthController extends BaseController {
  
  
  @RequestMapping("/check")
  String queryProductDetail(HttpServletRequest request){
	  return "200 OK";
  }
  
}
