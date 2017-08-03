package com.lpcode.modules.blsp.vo;

import com.lpcode.modules.blsp.entity.PrjTaskMaterialDef;

/**
 * @author Pengs
 * @package com.lpcode.modules.blsp.vo
 * @fileName PrjTaskMaterialDefVO
 * @date 16/8/3.
 */
public class PrjTaskMaterialDefVO extends PrjTaskMaterialDef {
    private String materName;

    public String getMaterName() {
        return materName;
    }

    public void setMaterName(String materName) {
        this.materName = materName;
    }
}
