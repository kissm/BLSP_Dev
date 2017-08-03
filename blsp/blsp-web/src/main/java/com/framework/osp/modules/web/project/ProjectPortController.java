package com.framework.osp.modules.web.project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.framework.osp.modules.web.util.TokenUtil;
import com.lpcode.modules.dto.project.ReqHeader;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.framework.core.base.page.Page;
import com.framework.core.utils.StringUtil;
import com.lpcode.modules.dto.project.ReqPrjPortMent;
import com.lpcode.modules.dto.project.RespPrjPortMent;
import com.lpcode.modules.service.impl.project.util.ProjectUtil;
import com.lpcode.modules.service.project.dto.Project;
import com.lpcode.modules.service.project.dto.pinstance.PrjCodeGeneratorVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 接口
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.project
 * @author maxiaowei
 * @createDate 2016年7月25日 下午1:18:05
 */
@RestController
@RequestMapping(value = "/projectPort")
public class ProjectPortController {
	/**
	 * 配置文件中token用户名
	 */
	@Value("${zuh.blsp.service.username}")
	protected String tokenUsername;
	/**
	 * 配置文件中token密码
	 */
	@Value("${zuh.blsp.service.password}")
	protected String tokenPassword;

	@Autowired
	private ProjectServiceInf projectServiceInf;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 生成项目编号
	 * @param reqPrjPortMent
	 * @return
	 */
	@RequestMapping(value = "/createCode", method = RequestMethod.POST)
    public RespPrjPortMent createCode(@RequestBody ReqPrjPortMent reqPrjPortMent){
		ReqHeader reqHeader = reqPrjPortMent.getReqHeader();
		RespPrjPortMent resp = checkToken(reqHeader);
		if(StringUtils.isNotBlank(resp.getResCode()) && !resp.getResCode().equals("0")){
			return resp;
		}
		HashMap<String,Object> map = new HashMap<String,Object>();
        PrjCodeGeneratorVo pvo = ProjectUtil.getProjectCode(reqPrjPortMent.getReqPortData().getPrjType());
		if (pvo.getSeq() != null) {
			pvo.setSeq(pvo.getSeq() + 1);
		}
		map.put("prjCode", pvo.toString());
		resp.setMsg("success");
		resp.setResCode("0");
		resp.setData(map);
        return resp;
    }

	/**
	 * 通过项目编号获取项目基本信息
	 * @param reqPrjPortMent
	 * @return
	 */
	@RequestMapping(value = "/prjInstanceByPrjCode", method = RequestMethod.POST)
    public RespPrjPortMent prjInstanceByPrjCode(@RequestBody ReqPrjPortMent reqPrjPortMent){
		ReqHeader reqHeader = reqPrjPortMent.getReqHeader();
		RespPrjPortMent resp = checkToken(reqHeader);
		if(StringUtils.isNotBlank(resp.getResCode()) && !resp.getResCode().equals("0")){
			return resp;
		}
		HashMap<String,Object> map = new HashMap<String,Object>();
		PrjInstanceVo prjInstanceVo = new PrjInstanceVo();
		prjInstanceVo.setPrjCode(reqPrjPortMent.getReqPortData().getPrjCode());
		prjInstanceVo.setChannel("0");//设置录入渠道为0的,即窗口录入的数据信息
		prjInstanceVo = projectServiceInf.getPrjInstanceVoByEntity(prjInstanceVo);
		map.put("prjInstance", prjInstanceVo);
		resp.setData(map);
		resp.setMsg("success");
		resp.setResCode("0");
        return resp;
    }
	
	/**
	 * 通过组织机构码获取项目列表，分页及相关信息
	 * @param reqPrjPortMent
	 * @return
	 */
	@RequestMapping(value = "/prjListByCompanyCode", method = RequestMethod.POST)
    public RespPrjPortMent prjListByCompanyCode(@RequestBody ReqPrjPortMent reqPrjPortMent){
		ReqHeader reqHeader = reqPrjPortMent.getReqHeader();
		RespPrjPortMent resp = checkToken(reqHeader);
		if(StringUtils.isNotBlank(resp.getResCode()) && !resp.getResCode().equals("0")){
			return resp;
		}
		HashMap<String,Object> map = new HashMap<String,Object>();
		String pageNo = reqPrjPortMent.getReqPortData().getPageNo();
		if(StringUtil.isEmpty(pageNo)){
			pageNo = "1";
		}
		String pageSize = reqPrjPortMent.getReqPortData().getPageSize();
		if(StringUtil.isEmpty(pageSize)){
			pageSize = "10";
		}
		Project project = new Project();
		PrjInstanceVo pvo = new PrjInstanceVo();
		if(reqPrjPortMent.getReqPortData() != null && StringUtils.isNotBlank(reqPrjPortMent.getReqPortData().getCompanyCode()) ) {
			pvo.setCompanyCode(reqPrjPortMent.getReqPortData().getCompanyCode());
		}
		pvo.setChannel("0");//设置录入渠道为0的,即窗口录入的数据信息
		project.setPrjInstanceVo(pvo);
		Page<Project> page = new Page<Project>();
		page.setPageNo(Integer.valueOf(pageNo));
		page.setPageSize(Integer.valueOf(pageSize));
		page.setOrderBy("creat_Time desc");
		projectServiceInf.getProjectPageByCompanyCode(project, page);
		map.put("page", page);
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		resp.setData(map);
		resp.setMsg("success");
		resp.setResCode("0");
        return resp;
    }

	/**
	 * token校验
	 * @param reqHeader
	 * @return
	 */
	private RespPrjPortMent checkToken(ReqHeader reqHeader){
		RespPrjPortMent resp = new RespPrjPortMent();
		String makeToken = tokenUsername + sdf.format(new Date()) + tokenPassword;
		if(reqHeader != null && StringUtils.isNotBlank(reqHeader.getToken())){
			boolean checkToken = TokenUtil.authenticateToken(reqHeader.getToken(),makeToken);
			if(!checkToken){
				resp.setResCode("1");
				resp.setMsg("token校验不通过");
			}
		}
		return resp;
	}

	/**
	 * 生成项目编号
	 * @param prjType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/newPrjCode", method = RequestMethod.POST)
	public String newPrjCode(@RequestBody String prjType){
		HashMap<String,String> map = new HashMap<String,String>();
		PrjCodeGeneratorVo pvo = ProjectUtil.getProjectCode(prjType);
		if (pvo.getSeq() != null) {
			pvo.setSeq(pvo.getSeq() + 1);
		}
		return pvo.toString();
	}
}
