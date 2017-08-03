package com.framework.osp.modules.web.util;

import com.alibaba.fastjson.JSONObject;
import com.framework.osp.common.mapper.JsonMapper;
import com.framework.osp.common.utils.IdGen;
import com.framework.osp.modules.sys.dao.UserDao;
import com.framework.osp.modules.sys.entity.Office;
import com.framework.osp.modules.sys.entity.Role;
import com.framework.osp.modules.sys.entity.User;
import com.framework.osp.modules.sys.service.SystemService;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.dto.user.RespUserSync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Pengs
 * @package com.framework.osp.modules.web.util
 * @fileName UserSyncController
 * @date 16/7/13.
 */

@RestController
@RequestMapping(value = "/user")
public class UserSyncController {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	UserDao userService;
	/**
	 * 配置文件中token用户名
	 */
	@Value("${zuh.blsp.service.username}")
	protected String matterTokenUsername;
	/**
	 * 配置文件中token密码
	 */
	@Value("${zuh.blsp.service.password}")
	protected String matterTokenPassword;
	
	@Value("${zuh.blsp.service.defaultPassword}")
	protected String defaultPassword;
	@ResponseBody
	@RequestMapping(value = "/userSync", method = RequestMethod.POST)
	public RespUserSync userSync(HttpServletRequest request, HttpServletResponse response, String token) {
		RespUserSync resp = new RespUserSync();
		try {
			InputStream inputStream = request.getInputStream();
			String json = getRequestBodyJson(inputStream);
			JSONObject sobj = new JSONObject();
			sobj = sobj.parseObject(json);
			User user = JsonMapper.getInstance().fromJson(json, User.class);
			user.setDelFlag((String) sobj.get("delFlag"));
			// TODO 1\数据加密对比校验 2\用户名密码获取并存储 3\返回结果RespUserSync
			String nowDate = sdf.format(new Date()); 
			String wsbstoken = TokenUtil.createToken(matterTokenUsername + nowDate + matterTokenPassword);
			int count = 0;
			String msg = "";
			if (StringUtils.isEmpty(token) || user == null) {
				resp.setResult(100);
				resp.setMessage("请求数据格式有误，请检测后再提交！");
			} else if (token.equals(wsbstoken)) {
				// 根据loginName查询是否当前用户存在，不存在新增，存在修改
				User oldUser = userService.getByLoginName(user);
				if (StringUtils.isEmpty(oldUser)) {// 新增
					user.setId(IdGen.uuid());
					user.setCompany(new Office("1"));
					user.setOffice(new Office("518"));
					user.setCreateDate(new Date());
					user.setUpdateDate(new Date());
					user.setPassword(SystemService.entryptPassword(defaultPassword));
					user.setCreateBy(new User(sobj.getString("createBy")));
					user.setUpdateBy(new User(sobj.getString("updateBy")));
					count = userService.insert(user);
					//添加机构关联表以及默认角色
					if(count>0){
						List<Office> officeList = new ArrayList<Office>();
						officeList.add(user.getOffice());
						user.setOfficeList(officeList);
						userService.insertUserOffice(user);
						List<Role> roleList = new ArrayList<Role>();
						roleList.add(new Role("6"));
						user.setRoleList(roleList);
						userService.insertUserRole(user);
					}
					if (count > 0) {
						resp.setResult(200);
						resp.setMessage("添加用户成功");
						UserUtils.clearCache(user);
					} else {
						msg = "添加用户失败失败!";
						resp.setResult(101);
						resp.setMessage(msg);
					}
				} else {// 修改
					user.setId(oldUser.getId());
					user.setUpdateDate(new Date());
					user.setCompany(oldUser.getCompany());
					user.setPassword(oldUser.getPassword());
					user.setOffice(oldUser.getOffice());
					user.setUpdateBy(new User(sobj.getString("updateBy")));
					if("1".equals(user.getDelFlag())){//置为删除
						count = userService.delete(user);
					}else{//普通修改	
						count = userService.update(user);
					}
					if (count > 0) {
						resp.setResult(200);
						resp.setMessage("修改用户成功");
						UserUtils.clearCache(user);
					} else {
						msg = "修改用户失败失败!";
						resp.setResult(102);
						resp.setMessage(msg);
					}
				}
			} else {
				resp.setResult(103);
				resp.setMessage("Token验证失败,请核对后再提交！");
			}
		} catch (Exception ex) {
//			ex.printStackTrace();
			resp.setResult(104);
			resp.setMessage("请求失败:" + ex.toString().substring(0,255));
		}
		return resp;
	}

	private String getRequestBodyJson(InputStream inputStream) throws IOException {
		Reader input = new InputStreamReader(inputStream,"utf-8");
		Writer output = new StringWriter();
		char[] buffer = new char[1024 * 4];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
		return output.toString();
	}
}
