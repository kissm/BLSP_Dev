package com.framework.osp.modules.web.bean;

import java.io.Serializable;

public class PwbmDataBean implements Serializable {
	
	private static final long serialVersionUID = 912322149442673248L;

	private String prjCode;
	
	private String prjName;
	
	private String fzjg;
	
	private String fzsj;
	
	private String wh;
	
	private String bt;
	
	private String bz;
	
	private String spjg;
	
	public PwbmDataBean(){};
	
	public PwbmDataBean(String prjCode,String prjName,String fzjg,String fzsj,String wh,String bt,String bz,String spjq){
		this.setBt(bt);;
		this.setBz(bz);
		this.setFzjg(fzjg);
		this.setFzsj(fzsj);
		this.setPrjCode(prjCode);
		this.setPrjName(prjName);
		this.setSpjg(spjq);
		this.setWh(wh);
	}

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
}
