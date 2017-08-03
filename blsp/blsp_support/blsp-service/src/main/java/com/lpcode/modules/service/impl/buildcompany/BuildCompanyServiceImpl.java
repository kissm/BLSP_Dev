package com.lpcode.modules.service.impl.buildcompany;

import com.framework.core.base.page.Page;
import com.framework.core.constants.BaseCode;
import com.framework.core.result.PageResult;
import com.framework.core.result.RequestDTOPage;
import com.framework.core.utils.BeanCopy;
import com.lpcode.modules.blsp.entity.PrjBuildCompany;
import com.lpcode.modules.blsp.entity.PrjBuildCompanyExample;
import com.lpcode.modules.blsp.mapper.PrjBuildCompanyMapper;
import com.lpcode.modules.service.buildcompany.dto.BuildCompanyDto;
import com.lpcode.modules.service.buildcompany.inf.IBuildCompanyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author Pengs
 * @package com.lpcode.modules.service.impl.buildcompany
 * @fileName BuildCompanyServiceImpl
 * @date 16/4/22.
 */
@Service
public class BuildCompanyServiceImpl implements IBuildCompanyService {

    @Autowired
    private PrjBuildCompanyMapper prjBuildCompanyMapper;
    @Override
    public PageResult<BuildCompanyDto> findList(RequestDTOPage<BuildCompanyDto> requestPage) {
        BuildCompanyDto req = requestPage.getObj();
        PageResult<BuildCompanyDto> pageResult = new PageResult<>();
        Page<PrjBuildCompany> queryPage = new Page<PrjBuildCompany>();
        queryPage.setPageNo(requestPage.getPage().getPageNo());
        queryPage.setPageSize(requestPage.getPage().getPageSize());

        PrjBuildCompanyExample example = new PrjBuildCompanyExample();
        PrjBuildCompanyExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
        if (StringUtils.isNotBlank(req.getCompany())) {
            criteria.andCompanyLike("%" + req.getCompany().trim() + "%");
        }
        if (StringUtils.isNotBlank(req.getCompanyCode())) {
            criteria.andCompanyCodeLike("%" + req.getCompanyCode() + "%");
        }
        example.setOrderByClause(" id");
        Page<PrjBuildCompany> prjBuildCompanyPage = prjBuildCompanyMapper.pagedSelectByExample(example, queryPage);
        requestPage.getPage().setList(new ArrayList<BuildCompanyDto>());
        requestPage.getPage().setCount(queryPage.getCount());
        BeanCopy.copyPropertiesForList(prjBuildCompanyPage.getList(), requestPage.getPage().getList(), BuildCompanyDto.class);
        pageResult.setObj(requestPage.getPage());
        return pageResult;
    }

    @Override
    public BuildCompanyDto getBuildCompanyById(Long id){
        PrjBuildCompany prjBuildCompany = prjBuildCompanyMapper.selectByPrimaryKey(id);
        BuildCompanyDto buildCompanyDto = new BuildCompanyDto();
        if (prjBuildCompany != null) {
            BeanCopy.copyProperties(prjBuildCompany, buildCompanyDto, BuildCompanyDto.class);
        }
        return buildCompanyDto;
    }

}
