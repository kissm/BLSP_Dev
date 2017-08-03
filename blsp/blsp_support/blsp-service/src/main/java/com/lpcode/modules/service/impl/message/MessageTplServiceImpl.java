package com.lpcode.modules.service.impl.message;

import com.framework.core.constants.ComErrorCodeConstants;
import com.lpcode.modules.blsp.constdefine.ConstDefine;
import com.lpcode.modules.blsp.mapper.MessageTplMapper;
import com.lpcode.modules.dto.message.ResultDTO;
import com.lpcode.modules.blsp.entity.MessageTpl;
import com.lpcode.modules.service.message.IServiceTemplate;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Package: com.lpcode.service.impl.message<br/>
 * Date: 15-6-2<br/>
 * Time: 下午2:45<br/>
 *
 * @author pengs
 */
@Service
public class MessageTplServiceImpl implements IServiceTemplate {
  @Autowired
  MessageTplMapper tplMapper;
  @Override
  public ResultDTO content(String tplid, Map data) {
    MessageTpl tpl = tplMapper.selectByPrimaryKey(tplid);
    String resCode = ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getErrorCode();
    String content = null;
    if(null==tpl) {
      resCode = ConstDefine.CODE_NO_TEMPLATE;
      content = ConstDefine.MSG_NO_TEMPLATE;
    }else{
      content = process(tpl.getName(),tpl.getTpl(),data);
      if(null==content){
        resCode = ConstDefine.CODE_ERR_TEMPLATE;
        content = ConstDefine.MSG_ERR_TEMPLATE;
      }else{
        content=content.trim();
      }
    }

    return new ResultDTO(resCode,content);
  }

  @Override
  public String subject(String tplid, Map data) {
    MessageTpl tpl = tplMapper.selectByPrimaryKey(tplid);
    return process(tpl.getName(),tpl.getSubject(),data);
  }

  @Override
  public boolean isHtml(String tplid) {
    MessageTpl tpl = tplMapper.selectByPrimaryKey(tplid);
    return "1".equals(tpl.getIshtml())||"true".equals(tpl.getIshtml());
  }
  
  @Override
  public boolean isUploadFile(String tplid) {
    MessageTpl tpl = tplMapper.selectByPrimaryKey(tplid);
    return "1".equals(tpl.getIsupload())||"true".equals(tpl.getIsupload());
  }
  
  public String process(String name,String tpl,Map data){
    try {
      Reader reader = new StringReader(tpl);
      Template template = new Template(name,reader);
      // Process the template
      Writer writer = new StringWriter();
      template.process(data, writer);
      return writer.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
