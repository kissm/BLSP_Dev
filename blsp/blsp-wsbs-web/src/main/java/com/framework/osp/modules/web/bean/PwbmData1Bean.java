package com.framework.osp.modules.web.bean;

import java.io.Serializable;

public class PwbmData1Bean implements Serializable {
	
	private static final long serialVersionUID = 9198123849439673248L;
	
	private String sxid;
	
	private String spid;
	
	private String spbm;
	
	private String spr;
	
	private String spsj;
	
	private String spyj;

	public PwbmData1Bean(String sxid,String spid,String spbm,String spr,String spsj,String spyj){
		this.setSxid(sxid);
		this.setSpid(spid);
		this.setSpbm(spbm);
		this.setSpr(spr);
		this.setSpsj(spsj);
		this.setSpyj(spyj);
	}
	
	public PwbmData1Bean(){}
	
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
