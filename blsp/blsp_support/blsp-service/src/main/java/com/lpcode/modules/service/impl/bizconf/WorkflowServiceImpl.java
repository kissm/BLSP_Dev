package com.lpcode.modules.service.impl.bizconf;

import com.framework.core.constants.BaseCode;
import com.framework.core.result.PageResult;
import com.framework.core.result.RequestDTOPage;
import com.framework.core.utils.BeanCopy;
import com.framework.core.base.page.Page;
import com.lpcode.modules.blsp.entity.*;
import com.lpcode.modules.blsp.mapper.DimWorkflowTimeLimitMapper;
import com.lpcode.modules.blsp.mapper.LogSmsMapper;
import com.lpcode.modules.service.bizconf.dto.LogSmsDto;
import com.lpcode.modules.service.bizconf.dto.WorkflowTimeLimitDto;
import com.lpcode.modules.service.bizconf.inf.IWorkflowService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pengs
 * @package com.lpcode.modules.service.impl.bizconf
 * @fileName WorkflowServiceImpl
 * @date 16/2/19.
 */
@Service
public class WorkflowServiceImpl implements IWorkflowService {

    @Autowired
    DimWorkflowTimeLimitMapper mapper;
    @Autowired
    LogSmsMapper logSmsMapper;

    @Override
    public PageResult<WorkflowTimeLimitDto> findList(RequestDTOPage<WorkflowTimeLimitDto> requestPage) {
        WorkflowTimeLimitDto req = requestPage.getObj();
        PageResult<WorkflowTimeLimitDto> pageResult = new PageResult<>();
        Page<DimWorkflowTimeLimit> queryPage = new Page<DimWorkflowTimeLimit>();
        queryPage.setPageNo(requestPage.getPage().getPageNo());
        queryPage.setPageSize(requestPage.getPage().getPageSize());

        DimWorkflowTimeLimitExample example = new DimWorkflowTimeLimitExample();
        DimWorkflowTimeLimitExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
        if (StringUtils.isNotBlank(req.getName())) {
            criteria.andNameLike("%" + req.getName().trim() + "%");
        }
        if (StringUtils.isNotBlank(req.getIsValid())) {
            criteria.andIsValidEqualTo(req.getIsValid());
        }
        if (StringUtils.isNotBlank(req.getFlowType())) {
            criteria.andFlowTypeEqualTo(req.getFlowType());
        }
        example.setOrderByClause(" id");
        Page<DimWorkflowTimeLimit> dimMaterialList = mapper.pagedSelectByExample(example, queryPage);
        requestPage.getPage().setList(new ArrayList<WorkflowTimeLimitDto>());
        requestPage.getPage().setCount(queryPage.getCount());
        BeanCopy.copyPropertiesForList(dimMaterialList.getList(), requestPage.getPage().getList(), WorkflowTimeLimitDto.class);
        pageResult.setObj(requestPage.getPage());
        return pageResult;
    }
    @Override
    public WorkflowTimeLimitDto getWorkflowById(Long id) {
        DimWorkflowTimeLimit workflowTimeLimit = mapper.selectByPrimaryKey(id);
        WorkflowTimeLimitDto dto = copyFromDto(workflowTimeLimit);
        return dto;
    }

    @Override
    public void save(WorkflowTimeLimitDto workflowTimeLimitDto) {
        DimWorkflowTimeLimit workflowTimeLimit = copyToDto(workflowTimeLimitDto);
        mapper.insert(workflowTimeLimit);
    }

    @Override
    public void edit(WorkflowTimeLimitDto workflowTimeLimitDto) {
        DimWorkflowTimeLimit workflowTimeLimit = copyToDto(workflowTimeLimitDto);
        mapper.updateByPrimaryKey(workflowTimeLimit);
    }

    @Override
    public void delete(Long id) {
        DimWorkflowTimeLimit workflowTimeLimit = mapper.selectByPrimaryKey(id);
        workflowTimeLimit.setIsDelete(BaseCode.DEL_FLAG_DELETE);
        mapper.updateByPrimaryKey(workflowTimeLimit);
    }

    @Override
    public boolean enableWorkflowName(String name) {
        DimWorkflowTimeLimitExample example = new DimWorkflowTimeLimitExample();
        example.createCriteria().andNameEqualTo(name).andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
        List<DimWorkflowTimeLimit> list = mapper.selectByExample(example);
        if(list != null && list.size() >0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public PageResult<LogSmsDto> findSmsList(RequestDTOPage<LogSmsDto> requestPage) {
        LogSmsDto req = requestPage.getObj();
        PageResult<LogSmsDto> pageResult = new PageResult<>();
        Page<LogSms> queryPage = new Page<LogSms>();
        queryPage.setPageNo(requestPage.getPage().getPageNo());
        queryPage.setPageSize(requestPage.getPage().getPageSize());

        LogSmsExample example = new LogSmsExample();
        LogSmsExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(" sendtime desc");
        Page<LogSms> logSmsPage = logSmsMapper.pagedSelectByExample(example, queryPage);
        ArrayList<LogSmsWithBLOBs> secondFindList = new ArrayList<>();
        for(LogSms logSms : logSmsPage.getList()){
            secondFindList.add(logSmsMapper.selectByPrimaryKey(logSms.getId()));
        }
        requestPage.getPage().setList(new ArrayList<LogSmsDto>());
        requestPage.getPage().setCount(queryPage.getCount());
        BeanCopy.copyPropertiesForList(secondFindList, requestPage.getPage().getList(), LogSmsDto.class);
        pageResult.setObj(requestPage.getPage());
        return pageResult;
    }

    private DimWorkflowTimeLimit copyToDto(WorkflowTimeLimitDto dto) {
        DimWorkflowTimeLimit workflowTimeLimit = new DimWorkflowTimeLimit();
        BeanCopy.copyProperties(dto, workflowTimeLimit, DimWorkflowTimeLimit.class);
        return workflowTimeLimit;
    }

    private WorkflowTimeLimitDto copyFromDto(DimWorkflowTimeLimit workflowTimeLimit) {
        WorkflowTimeLimitDto dto = new WorkflowTimeLimitDto();
        BeanCopy.copyProperties(workflowTimeLimit, dto, WorkflowTimeLimitDto.class);
        return dto;
    }
}
