package com.lpcode.modules.service.impl.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.framework.core.base.page.Page;
import com.framework.core.result.RequestDTOPage;
import com.framework.core.utils.BeanCopy;
import com.framework.core.utils.StringUtil;
import com.framework.osp.modules.sys.utils.TokenUtil;
import com.lpcode.modules.blsp.entity.PrjInstance;
import com.lpcode.modules.blsp.entity.PrjInstanceExample;
import com.lpcode.modules.blsp.mapper.PrjInstanceMapper;
import com.lpcode.modules.service.impl.project.util.HttpClientRequestUtil;
import com.lpcode.modules.service.project.dto.WsbsProjInstanceDto;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.WsbsPrjInstanceServiceInf;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Pengs
 * @package com.lpcode.modules.service.impl.project
 * @fileName WsbsPrjInstanceServiceImpl
 * @date 16/8/12.
 */
@Service
public class WsbsPrjInstanceServiceImpl implements WsbsPrjInstanceServiceInf {
    protected Logger logger = LoggerFactory.getLogger(WsbsPrjInstanceServiceImpl.class);
    /**
     * 网厅项目信息接口查询URL
     */
    @Value("${zuh.wsbs.interface.url}")
    private String WSBS_WEB_INTF_URL;

    /**
     * 配置文件中token用户名
     */
    @Value("${zuh.blsp.service.username}")
    private String matterTokenUsername;

    /**
     * 配置文件中token密码
     */
    @Value("${zuh.blsp.service.password}")
    private String matterTokenPassword;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private PrjInstanceMapper instanceMapper;

    @Override
    public Page<WsbsProjInstanceDto> wsbsPrjListByCompanyCode(RequestDTOPage<WsbsProjInstanceDto> requestDTOPage) {

        //调用服务
        String url = WSBS_WEB_INTF_URL + "/project/list/" + getToken();
        url += (requestDTOPage.getPage().getPageNo() == 0 ? "/1" : "/" + requestDTOPage.getPage().getPageNo());
        url += (requestDTOPage.getPage().getPageSize() == 0 ? "/10" : "/" + requestDTOPage.getPage().getPageSize());
        url += ((requestDTOPage.getObj() != null && StringUtils.isNotBlank(requestDTOPage.getObj().getCompanyCode())) ? "?companyCode=" + requestDTOPage.getObj().getCompanyCode() : "");
        String result = "";
        try {
            result = HttpClientRequestUtil.CallingRequestService(url);
        } catch (HttpException | IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        //解析结果
        if (StringUtil.isNotEmpty(result)) {
            JSONObject json = JSONObject.parseObject(result);
            if ("0".equals(json.get("resCode"))) {//结果正确
                JSONObject jonsPage = json.getJSONObject("obj");
                requestDTOPage.getPage().setCount(jonsPage.getIntValue("count"));
                JSONArray jsonArray = jonsPage.getJSONArray("list");
                List<WsbsProjInstanceDto> list = new ArrayList<WsbsProjInstanceDto>();
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        WsbsProjInstanceDto dto = JSON.parseObject(jsonArray.getJSONObject(i).toString(), WsbsProjInstanceDto.class);
                        list.add(dto);
                    }
                }
                requestDTOPage.getPage().setList(list);
            }
        }
        return requestDTOPage.getPage();
    }

    @Override
    public WsbsProjInstanceDto wsbsPrjInstanceById(String id) {

        //调用服务
        String url = WSBS_WEB_INTF_URL + "/project/view/" + getToken() + "/" + id;
        String result = "";
        try {
            result = HttpClientRequestUtil.CallingRequestService(url);
        } catch (HttpException | IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        WsbsProjInstanceDto dto = new WsbsProjInstanceDto();
        //解析结果
        if (StringUtil.isNotEmpty(result)) {
            JSONObject json = JSONObject.parseObject(result);
            if ("0".equals(json.get("resCode"))) {//结果正确
                JSONObject jonsPage = json.getJSONObject("obj");
                dto = JSON.parseObject(jonsPage.toString(), WsbsProjInstanceDto.class);
            }
        }
        return dto;
    }

    @Override
    public Page<PrjInstanceVo> blspPrjList(RequestDTOPage<PrjInstanceVo> requestPage, PrjInstanceVo prjInstanceVo) {
        Page page = requestPage.getPage();
        Integer pageNo = requestPage.getPage().getPageNo();
        Integer pageSize = requestPage.getPage().getPageSize();
        page.setPageNo(pageNo == 0 ? 1 : pageNo);
        page.setPageSize(pageSize == 0 ? 10 : pageSize);
        page.setOrderBy("creat_Time desc");
        getProjectPage(prjInstanceVo, page);
        requestPage.getPage().setList(page.getList());
        return page;
    }

    @Override
    public PrjInstanceVo blspPrjInstanceById(Long id) {
        PrjInstanceVo prjInstanceVo = new PrjInstanceVo();
        PrjInstance prjInstance = instanceMapper.selectByPrimaryKey(id);
        if (prjInstance != null) {
            BeanCopy.copyProperties(prjInstance, prjInstanceVo, PrjInstanceVo.class);
        }
        return prjInstanceVo;
    }


    /**
     * 生成token
     *
     * @return token
     */
    private String getToken() {
        return TokenUtil.createToken(matterTokenUsername + sdf.format(new Date()) + matterTokenPassword);
    }

    /**
     * 获取项目列表
     *
     * @param vo
     * @param pages
     */
    public void getProjectPage(PrjInstanceVo vo, Page<PrjInstanceVo> pages) {
        List<PrjInstance> list = null;
        List<PrjInstanceVo> listvo = new ArrayList<PrjInstanceVo>();
        PrjInstance record = new PrjInstance();
        if (vo == null) {
            return;
        }
        BeanCopy.copyProperties(vo, record);
        Page<PrjInstance> page = new Page<PrjInstance>();
        PrjInstanceExample example = new PrjInstanceExample();
        PrjInstanceExample.Criteria c = example.createCriteria();
        if (StringUtils.isNotBlank(vo.getCompanyCode())) {
            c.andCompanyCodeEqualTo(vo.getCompanyCode());
        }
        if (StringUtils.isNotBlank(vo.getPrjCode())) {
            c.andPrjCodeLike(vo.getPrjCode());
        }
        if (!"3".equals(vo.getPrjType()))
            c.andPrjTypeEqualTo(vo.getPrjType());
        c.andIsDeleteNotEqualTo("1");
        c.andChannelEqualTo("0");//0为后台窗口录入,1为前台用户提交
        example.setStart(pages.getPageSize() * (pages.getPageNo() - 1));
        example.setPageSize(pages.getPageSize());
        example.setOrderByClause(pages.getOrderBy());
        page.setPageNo(pages.getPageNo());
        page.setPageSize(pages.getPageSize());
        page = instanceMapper.pagedSelectByExample(example, page);
        list = page.getList();
        if (list != null)
            BeanCopy.copyPropertiesForList(list, listvo, PrjInstanceVo.class);
        pages.setCount(page.getCount());
        pages.setList(listvo);
    }


}
