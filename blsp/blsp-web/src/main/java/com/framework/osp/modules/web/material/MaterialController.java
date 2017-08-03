package com.framework.osp.modules.web.material;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.core.constants.BaseCode;
import com.framework.core.result.PageResult;
import com.framework.core.result.RequestDTOPage;
import com.framework.core.result.Result;
import com.framework.osp.common.persistence.Page;
import com.framework.osp.common.utils.StringUtils;
import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.material.dto.MaterialDto;
import com.lpcode.modules.service.material.inf.IMaterialService;

/**
 * @author Pengs
 * @package com.framework.osp.modules.web.material
 * @fileName MaterialController
 * @date 16/2/19.
 */

@Controller
@RequestMapping(value = "${adminPath}/material")
public class MaterialController extends BaseController {

    @Autowired
    IMaterialService materialService;

    @ModelAttribute("materialDto")
    public MaterialDto get(@RequestParam(value="id",required=false)Long id) {
        if (null!=id){
            return materialService.getMaterialById(id);
        }else {
            return new MaterialDto();
        }
    }
    @RequestMapping(value = "/list")
    public String findMaterialList(MaterialDto materialDto,HttpServletRequest request, HttpServletResponse resp,Model model) {
        RequestDTOPage<MaterialDto> requestPage = new RequestDTOPage<MaterialDto>();
        Page<MaterialDto> page = new Page<MaterialDto>(request,resp);
        requestPage.setObj(materialDto);
        requestPage.setPage(page);
        PageResult<MaterialDto> pageResult  = materialService.findList(requestPage);
        model.addAttribute("page", page);
        String mode = request.getParameter("mode");
        if(StringUtils.isNotBlank(mode) && "select".equals(mode)){
        	 return "modules/material/materialSelectList";
        }
        return "modules/material/materialList";
    }

    @RequestMapping(value = "/add")
    public String materialAdd(){
        return "modules/material/materialForm";
    }

    @RequestMapping(value = "/findById")
    public String findById(){
        return "modules/material/materialForm";
    }

//    @RequestMapping(value = "/save")
//    public String materialSave(MaterialDto materialDto,HttpServletRequest request){
//        Date now = new Date();
//        materialDto.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
//        materialDto.setCreatTime(now);
//        materialDto.setUpdateTime(now);
//        materialService.save(materialDto);
//        return "redirect:" + adminPath + "/material/list";
//    }

    @RequestMapping(value = "/modify")
    public String materialModify(MaterialDto materialDto,HttpServletRequest request, Model model){
        if(materialDto.getId() == null){
            Date now = new Date();
            materialDto.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
            materialDto.setCreatTime(now);
            materialDto.setUpdateTime(now);
            materialService.save(materialDto);
        }else{
            materialDto.setUpdateTime(new Date());
            materialService.edit(materialDto);
        }
        return "redirect:" + adminPath + "/material/list";
    }

    @RequestMapping(value = "/deleteById")
    @ResponseBody
    public Result<Boolean> deleteById(@Valid @RequestBody MaterialDto param,HttpServletRequest request){
        materialService.delete(param.getId());
//        return "redirect:" + adminPath + "/material/list";
        return new Result<>(true);
    }
    /**
     * 验证材料名称是否可用
     * @param oldMaterialName
     * @param name
     * @return
     */
    @ResponseBody
//    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "checkMaterialName")
    public String checkMaterialName(String oldMaterialName, String name) {
        if (name !=null && name.equals(oldMaterialName)) {
            return "true";
        } else if (name !=null && materialService.enableMaterialName(name)) {
            return "true";
        }
        return "false";
    }
}
