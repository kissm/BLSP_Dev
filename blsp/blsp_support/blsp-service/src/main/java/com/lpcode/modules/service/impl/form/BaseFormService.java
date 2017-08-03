package com.lpcode.modules.service.impl.form;

import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;

/**
 * @author Pengs
 * @package com.lpcode.modules.service.impl.form
 * @fileName BaseFormService
 * @date 16/4/26.
 */
public abstract class BaseFormService {
    /**
     * 得到场景
     * @return
     */
    public abstract String getScene();

    /**
     * 表单保存或更新
     * @param object
     * @return
     */
    public abstract PrjFormVO saveOrUpdateForm(PrjInstanceVo prjInstanceVo,Object object);

    /**
     * 初始化form页面（新增或是修改）
     * @param prjInstanceVo
     * @param pid
     * @return
     */
    public abstract PrjFormVO viewForm(PrjInstanceVo prjInstanceVo,String pid);

}
