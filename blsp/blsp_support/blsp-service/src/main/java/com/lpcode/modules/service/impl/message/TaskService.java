package com.lpcode.modules.service.impl.message;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.DictUtils;
import com.lpcode.modules.blsp.constdefine.ConstDefine;
import com.lpcode.modules.blsp.entity.*;
import com.lpcode.modules.blsp.mapper.LogEmailMapper;
import com.lpcode.modules.blsp.mapper.LogSmsMapper;
import com.lpcode.modules.blsp.mapper.MessageQueueMapper;
import com.lpcode.modules.dto.message.RequestDTOEmail;
import com.lpcode.modules.dto.message.ResultDTO;
import com.lpcode.modules.dto.message.ResultEmail;
import com.lpcode.modules.dto.message.ResultSMS;
import com.lpcode.modules.service.message.IClientEmail;
import com.lpcode.modules.service.message.IClientSms;
import com.lpcode.modules.service.message.IServiceTemplate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Package: com.lpcode.service.impl.message<br/>
 * Date: 15-6-4<br/>
 * Time: 下午3:56<br/>
 *
 * @author pengs
 */
@Service
public class TaskService {
  @Autowired
  @Qualifier("smsTask")
  private TaskExecutor smsTask;
  @Autowired
  @Qualifier("emailTask")
  private TaskExecutor emailTask;
  @Autowired
  private IClientSms   smsClient;
  @Autowired
  private IClientEmail emailClient;
  @Autowired
  MessageQueueMapper messageMapper;
  @Autowired
  LogSmsMapper       logSmsMapper;
  @Autowired
  LogEmailMapper     emaileMapper;
  @Autowired
  IServiceTemplate   tplService;

  @Async
  public void cronTask() {
    String flag = DictUtils.getDictValue("sms_scheduled_tasks_flag", "sms_scheduled_tasks_flag", "0");
    if ("1".equals(flag)) {
      MessageQueueExample example = new MessageQueueExample();
      example.setPageSize(100);
      example.setStart(0);
      example.createCriteria();
      List<MessageQueueWithBLOBs> messages = messageMapper.selectByExampleWithBLOBs(example);
      if (null == messages || 0 == messages.size()) return;
      Date now = new Date();
      long nowTime = now.getTime() / 60000;
      for (MessageQueueWithBLOBs m : messages) {
        if (0 == m.getSendstatus()) {
          m.setSendstatus(1);
          m.setSendtime(now);
          messageMapper.updateByPrimaryKey(m);
          add(m);
        } else {// 如果时间超过10分钟,发送状态设为 0 ，下一次进入队列发送
          Date sendtime = m.getSendtime();
          if (null == sendtime) sendtime = m.getCreatetime();
          if ((nowTime - (sendtime.getTime() / 60000)) > 10) {
            m.setSendstatus(0);
            m.setSendtime(now);
            messageMapper.updateByPrimaryKey(m);
          }
        }
      }
    }
  }

  public void add(MessageQueueWithBLOBs po) {
    if (ConstDefine.SEND_BY_MOBILE.equals(po.getSendby())) {
      smsQueue(po);
    } else if (ConstDefine.SEND_BY_EMAIL.equals(po.getSendby())) {
      emailQueue(po);
    }
  }

  public void smsQueue(MessageQueueWithBLOBs msg) {
    smsTask.execute(new SmsMessageTask(msg, smsClient, tplService, messageMapper, logSmsMapper));
  }

  public void emailQueue(MessageQueueWithBLOBs msg) {
    emailTask.execute(new EmailMessageTask(msg, emailClient, tplService, messageMapper, emaileMapper));
  }
}


abstract class MessageTask<T> {
  protected boolean               isHtml;
  protected boolean               isupload;
  protected String                subject;
  protected String				  filepath;
  protected MessageQueueMapper    messageMapper;
  protected MessageQueueWithBLOBs msg;
  protected IServiceTemplate tplService;

  public Object readJsonObject(String json, Class clazz) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    try {
      return mapper.readValue(json, clazz);
    } catch (Exception e) {
      return null;
    }
  }

  public Object readJsonObject(String json, Class<?> collectionClass, Class<?>... elementClasses) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    try {
      return mapper.readValue(json, getCollectionType(mapper, collectionClass, elementClasses));
    } catch (Exception e) {
      return null;
    }
  }

  public JavaType getCollectionType(ObjectMapper mapper, Class<?> collectionClass, Class<?>... elementClasses) {
    return mapper.getTypeFactory().constructParametrizedType(collectionClass, collectionClass, elementClasses);
  }

  public MessageQueueMapper getMessageMapper() {
    return messageMapper;
  }

  public void setMessageMapper(MessageQueueMapper messageMapper) {
    this.messageMapper = messageMapper;
  }

  public MessageQueueWithBLOBs getMsg() {
    return msg;
  }

  public void setMsg(MessageQueueWithBLOBs msg) {
    this.msg = msg;
  }

  public IServiceTemplate getTplService() {
    return tplService;
  }

  public void setTplService(IServiceTemplate tplService) {
    this.tplService = tplService;
  }

  /**
   * 取接收人
   * 如果无接收人，记日志并删除队列
   *
   * @param clazz
   * @return
   */
  public List<T> receivers(Class clazz) {
    List<T> receivers = (List) readJsonObject(msg.getReceivers(), clazz);
    if (null == receivers || receivers.isEmpty()) {
      logAndRemove(ConstDefine.CODE_NO_RECEIVER, ConstDefine.MSG_NO_RECEIVER);
      return null;
    }
    return receivers;
  }

  public List<T> receivers(Class collection, Class type) {
    List<T> receivers = (List) readJsonObject(msg.getReceivers(), collection, type);
    if (null == receivers || receivers.isEmpty()) {
      logAndRemove(ConstDefine.CODE_NO_RECEIVER, ConstDefine.MSG_NO_RECEIVER);
      return null;
    }
    return receivers;
  }

  public String content() {
    String content = msg.getContent();
    if (StringUtils.isNotBlank(content)) {
      subject = msg.getSubject();
      return content;
    }
    Map data = (Map) readJsonObject(msg.getData(), Map.class);
    if (null == data || data.isEmpty()) {
      logAndRemove(ConstDefine.CODE_NO_CONTENT, ConstDefine.MSG_NO_CONTENT);
      return null;
    }
    String tplid = msg.getTplid();
    ResultDTO result = tplService.content(tplid, data);
    if (result.isSuccess()) {
      isHtml = tplService.isHtml(tplid);
      isupload = tplService.isUploadFile(tplid);
      if(isupload){
    	  filepath = msg.getFilepath();
      }
      subject = msg.getSubject();
      if (StringUtils.isBlank(subject)) subject = tplService.subject(tplid, data);
      return result.getMsg();
    }

    Date now = new Date();
    int sendtimes = msg.getSendtimes();
    msg.setSendtime(now);
    msg.setSendtimes(++sendtimes);
    msg.setSendstatus(0);
    messageMapper.updateByPrimaryKey(msg);
    if(sendtimes > ConstDefine.MAX_SEND_TIMES){
      messageMapper.deleteByPrimaryKey(msg.getId());
    }
    // 取模版内容失败，记日志
    sendLog(result);
    return null;
  }

  public abstract void sendLog(ResultDTO result);

  public abstract void logAndRemove(String code, String msg);
}

class SmsMessageTask extends MessageTask<String> implements Runnable {
  private IClientSms   client;
  private LogSmsMapper smsMapper;

  public SmsMessageTask(MessageQueueWithBLOBs msg, IClientSms client, IServiceTemplate tplService, MessageQueueMapper messageMapper, LogSmsMapper smsMapper) {
    this.msg = msg;
    this.client = client;
    this.tplService = tplService;
    this.messageMapper = messageMapper;
    this.smsMapper = smsMapper;
  }

  @Override
  public void run() {
    List<String> receivers = receivers(List.class);
    if (null == receivers) return;
    String content = content();
    if (null == content) return;
    ResultSMS result = client.send(receivers, content);
    msg.setContent(content);
    afterProcess(result);
  }

  public void afterProcess(ResultSMS result) {
    String id = msg.getId();
    String resCode = result.getResCode();
    LogSmsWithBLOBs log = new LogSmsWithBLOBs();
    int sendtimes = msg.getSendtimes();
    msg.setSendtimes(++sendtimes);
    // 发送成功，记入日志,删除待发送表
    if (result.isSuccess()) {
      messageMapper.deleteByPrimaryKey(id);
      log.setMsgid(result.getMsgid());
    } else if (sendtimes > ConstDefine.MAX_SEND_TIMES) { // 超出最大发送次数
      messageMapper.deleteByPrimaryKey(id);
    } else {
      msg.setSendstatus(0);
      messageMapper.updateByPrimaryKey(msg);
    }

    BeanCopy.copyProperties(msg, log);
    log.setId(java.util.UUID.randomUUID().toString().replaceAll("-", ""));
    log.setResult(resCode);
    log.setMessage(result.getMsg());
    log.setSendtime(new Date());
    smsMapper.insert(log);
  }

  // 取模版内容失败，记日志
  @Override
  public void sendLog(ResultDTO result) {
    LogSmsWithBLOBs log = new LogSmsWithBLOBs();
    BeanCopy.copyProperties(msg, log);
    log.setId(java.util.UUID.randomUUID().toString().replaceAll("-",""));
    log.setResult(result.getResCode());
    log.setMessage(result.getMsg());
    log.setSendtime(new Date());
    smsMapper.insert(log);
  }

  /**
   * 记日志并删除记录
   */
  @Override
  public void logAndRemove(String resCode, String message) {
    String id = msg.getId();
    messageMapper.deleteByPrimaryKey(id);
    LogSmsWithBLOBs log = new LogSmsWithBLOBs();
    BeanCopy.copyProperties(msg, log);
    log.setId(java.util.UUID.randomUUID().toString().replaceAll("-",""));
    log.setResult(resCode);
    log.setMessage(message);
    log.setSendtime(new Date());
    smsMapper.insert(log);
  }
}

class EmailMessageTask extends MessageTask<RequestDTOEmail.RequestEmail> implements Runnable {
  private IClientEmail    client;
  private LogEmailMapper emaileMapper;
  public EmailMessageTask(MessageQueueWithBLOBs msg, IClientEmail client, IServiceTemplate tplService, MessageQueueMapper messageMapper, LogEmailMapper logEmaileMapper) {
    this.msg = msg;
    this.client = client;
    this.tplService = tplService;
    this.messageMapper = messageMapper;
    this.emaileMapper = logEmaileMapper;
  }

  @Override
  public void run() {
    List<RequestDTOEmail.RequestEmail> receivers = receivers(List.class, RequestDTOEmail.RequestEmail.class);
    if (null == receivers) return;
    String content = content();
    if (null == content) return;
    isHtml = true;
    ResultEmail result = client.send(receivers, subject, content,isHtml,isupload,filepath);
    msg.setContent(content);
    afterProcess(result);
  }

  @Override
  public void sendLog(ResultDTO result) {
    LogEmailWithBLOBs log = new LogEmailWithBLOBs();
    BeanCopy.copyProperties(msg, log);
    log.setId(java.util.UUID.randomUUID().toString().replaceAll("-",""));
    log.setResult(result.getResCode());
    log.setMessage(result.getMsg());
    log.setSendtime(new Date());
    if(result instanceof ResultEmail){
      log.setSender(((ResultEmail) result).getSender());
      log.setSendername(((ResultEmail) result).getSendername());
    }
    emaileMapper.insert(log);
  }

  public void afterProcess(ResultEmail result) {
    String id = msg.getId();
    int sendtimes = msg.getSendtimes();
    msg.setSendtimes(++sendtimes);
    // 发送成功，记入日志,删除待发送表
    if (result.isSuccess()) {
      messageMapper.deleteByPrimaryKey(id);
    } else if (sendtimes > ConstDefine.MAX_SEND_TIMES) { // 超出最大发送次数
      messageMapper.deleteByPrimaryKey(id);
    } else {
      msg.setSendstatus(0);
      messageMapper.updateByPrimaryKey(msg);
    }
    sendLog(result);
  }

  /**
   * 记日志并删除记录
   */
  @Override
  public void logAndRemove(String resCode, String message) {
    String id = msg.getId();
    messageMapper.deleteByPrimaryKey(id);
    LogEmailWithBLOBs log = new LogEmailWithBLOBs();
    BeanCopy.copyProperties(msg, log);
    log.setId(java.util.UUID.randomUUID().toString().replaceAll("-",""));
    log.setResult(resCode);
    log.setMessage(message);
    log.setSendtime(new Date());
    emaileMapper.insert(log);
  }
}