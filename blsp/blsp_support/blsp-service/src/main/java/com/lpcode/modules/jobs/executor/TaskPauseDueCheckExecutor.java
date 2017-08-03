package com.lpcode.modules.jobs.executor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.framework.core.result.ListResult;
import com.framework.core.utils.HttpUtil;
import com.framework.core.utils.JsonUtil;
import com.lpcode.modules.blsp.constdefine.ConstDefine;
import com.lpcode.modules.blsp.mapper.PrjTaskAdapterMapper;
import com.lpcode.modules.blsp.vo.PrjTaskPauseDueVO;
import com.lpcode.modules.service.project.inf.PrjTaskScheduleService;

@Component
@Scope("prototype")
public class TaskPauseDueCheckExecutor implements Runnable {
	private String name;
	private Long id;
    
    @Value("${quartz.monitor.callBackUrl}")
    private String callBackUlr;
    
    @Autowired
	private PrjTaskAdapterMapper taskAdapterMapper;
    @Autowired
    private PrjTaskScheduleService service;

	@Override
	public void run() {
		try {
			ListResult<PrjTaskPauseDueVO> result = service.findTaskPauseDueList();
			if(result.getResCode().equals(ConstDefine.CODE_SUCCESS)){
				List<PrjTaskPauseDueVO> list = result.getObj();
				for(PrjTaskPauseDueVO vo:list){
					if(StringUtils.isNotBlank(vo.getMobile())){
						service.setTaskPauseToEnd(vo);
					}
				}
			}
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("taskId", id);
        paramMap.put("resCode", "0");
        paramMap.put("msg", "success");
        HttpUtil.doPost(callBackUlr,JsonUtil.getJSONString(paramMap));

	}
	

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

}
