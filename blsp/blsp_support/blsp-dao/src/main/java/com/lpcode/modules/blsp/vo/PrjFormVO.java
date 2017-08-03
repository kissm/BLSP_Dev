package com.lpcode.modules.blsp.vo;

/**
 * @author Pengs
 * @package com.lpcode.modules.blsp.vo
 * @fileName prjFormVO
 * @date 16/4/26.
 */
public class PrjFormVO {
    public Long prjId;
    public String moduleUrl;
    public Object object;

    public Long getPrjId() {
        return prjId;
    }

    public void setPrjId(Long prjId) {
        this.prjId = prjId;
    }

    public String getModuleUrl() {
        return moduleUrl;
    }

    public void setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
