package com.lpcode.modules.blsp.vo;

import java.io.Serializable;

/**
 * 项目批文生产龙贝码DTO
 *
 * @author wangyazhou
 *
 */
public class CertLpcodeVO implements Serializable {

	private static final long serialVersionUID = 9200570671163702657L;
	
	private String prjCode; // 项目编号
	private String prjName; //项目名称
	private String fzjg; //发证机关
	private String fzsj; //发证时间
	private String wh; //文号
	private String bt; //标题
	private String bz; //备注
	private String spjg; //审批结果
	
	
	private String sxid; //事项ID
	private String spid ; //审批ID
	private String spbm; //审批部门
	private String spr; //审批人
	private String spsj; //审批时间
	private String spyj; //审批意见
	
	public String getPrjCode() {
		return prjCode;
	}
	public void setPrjCode(String prjCode) {
		this.prjCode = prjCode;
	}
	public String getPrjName() {
		return prjName;
	}
	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}
	public String getFzjg() {
		return fzjg;
	}
	public void setFzjg(String fzjg) {
		this.fzjg = fzjg;
	}
	public String getFzsj() {
		return fzsj;
	}
	public void setFzsj(String fzsj) {
		this.fzsj = fzsj;
	}
	public String getWh() {
		return wh;
	}
	public void setWh(String wh) {
		this.wh = wh;
	}
	public String getBt() {
		return bt;
	}
	public void setBt(String bt) {
		this.bt = bt;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getSpjg() {
		return spjg;
	}
	public void setSpjg(String spjg) {
		this.spjg = spjg;
	}
	public String getSxid() {
		return sxid;
	}
	public void setSxid(String sxid) {
		this.sxid = sxid;
	}
	public String getSpid() {
		return spid;
	}
	public void setSpid(String spid) {
		this.spid = spid;
	}
	public String getSpbm() {
		return spbm;
	}
	public void setSpbm(String spbm) {
		this.spbm = spbm;
	}
	public String getSpr() {
		return spr;
	}
	public void setSpr(String spr) {
		this.spr = spr;
	}
	public String getSpsj() {
		return spsj;
	}
	public void setSpsj(String spsj) {
		this.spsj = spsj;
	}
	public String getSpyj() {
		return spyj;
	}
	public void setSpyj(String spyj) {
		this.spyj = spyj;
	}

}
