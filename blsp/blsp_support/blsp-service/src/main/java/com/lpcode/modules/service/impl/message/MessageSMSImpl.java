package com.lpcode.modules.service.impl.message;

import com.lpcode.modules.blsp.constdefine.ConstDefine;
import com.lpcode.modules.dto.message.RequestDTO;
import com.lpcode.modules.dto.message.RequestDataDTO;
import com.lpcode.modules.dto.message.ResultDTO;
import com.lpcode.modules.service.message.IMessageSMS;
import com.lpcode.modules.service.message.IServiceMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Pengs
 * @package com.lpcode.modules.service.impl.message
 * @fileName MessageSMSImpl
 * @date 16/2/25.
 */
@Service
public class MessageSMSImpl implements IMessageSMS {
    @Autowired
    IServiceMessage messageService;
    @Override
    public ResultDTO sendSmsByTplId(String mobile, String tplId) {
        RequestDTO requestDTO = new RequestDTO();
        List<String> receivers = new ArrayList<>();
        receivers.add(mobile);
        requestDTO.setTplid(tplId);
        requestDTO.setSendby(ConstDefine.SEND_BY_MOBILE);//通过短信ConstDefine.SEND_BY_MOBILE
        requestDTO.setReceivers(receivers);
        RequestDataDTO reqData = new RequestDataDTO();
        requestDTO.setData(reqData.getReqData());
        return messageService.queue(requestDTO);
    }

    @Override
    public ResultDTO sendSmsByTplIdAndMap(String mobile, String tplId,Map<String,String> map) {
        RequestDTO requestDTO = new RequestDTO();
        List<String> receivers = new ArrayList<>();
        receivers.add(mobile);
        requestDTO.setTplid(tplId);
        requestDTO.setSendby(ConstDefine.SEND_BY_MOBILE);//通过短信ConstDefine.SEND_BY_MOBILE
        requestDTO.setReceivers(receivers);
        requestDTO.setData(map);
        if(map == null){
            RequestDataDTO reqData = new RequestDataDTO();
            requestDTO.setData(reqData.getReqData());
        }
        return messageService.queue(requestDTO);
    }
}
