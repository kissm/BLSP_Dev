package com.lpcode.modules.service.impl.message;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.core.utils.BeanCopy;
import com.lpcode.modules.blsp.constdefine.ConstDefine;
import com.lpcode.modules.blsp.entity.MessageQueueWithBLOBs;
import com.lpcode.modules.blsp.mapper.MessageQueueMapper;
import com.lpcode.modules.dto.message.RequestDTO;
import com.lpcode.modules.dto.message.ResultDTO;
import com.lpcode.modules.blsp.entity.MessageQueue;
import com.lpcode.modules.service.message.IServiceMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Package: com.lpcode.service.impl.message<br/>
 * Date: 15-6-2<br/>
 * Time: 下午7:29<br/>
 *
 * @author pengs
 */
@Service
public class MessageServiceImpl implements IServiceMessage {
  @Autowired
  MessageQueueMapper messageMapper;


  @Autowired
  TaskService taskService;

  @Override
  public ResultDTO queue(RequestDTO para) {
    Date now = new Date();
    MessageQueueWithBLOBs po = new MessageQueueWithBLOBs();
    Map data = para.getData();
    List receivers = para.getReceivers();
    para.setData(null);
    para.setReceivers(null);
    BeanCopy.copyProperties(para, po);
    if(null==po.getSendtype()) po.setSendtype(ConstDefine.SEND_TYPE_API);
    po.setReceivers(json(receivers));
    po.setData(json(data));
    po.setId(java.util.UUID.randomUUID().toString().replaceAll("-", ""));
    po.setCreatetime(now);
    po.setSendtime(now);
    po.setSendtimes(0);
    po.setSendstatus(0);
    //去掉即使触发,并把插入队列的短信状态设置为0待发送,（1为发送中）
//    po.setSendstatus(1);
    messageMapper.insert(po);
    //去掉即使触发
//    taskService.add(po);
    return new ResultDTO();
  }

  protected String json(Object data) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    try {
      StringWriter writer = new StringWriter();
      mapper.writeValue(writer,data);
      return writer.toString();
    } catch (Exception e) {
      return null;
    }
  }
}

