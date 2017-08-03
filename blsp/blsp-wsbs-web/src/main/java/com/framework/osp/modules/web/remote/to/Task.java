package com.framework.osp.modules.web.remote.to;

import java.io.Serializable;

public class Task implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String CONTENT;
	String TIME;
	String SBLSH;
	String TYPE;
	String SXDM;
	String SXMC;
	String SSBM;
	String SXZT;

	public String getCONTENT() {
		return CONTENT;
	}

	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}

	public String getTIME() {
		return TIME;
	}

	public void setTIME(String tIME) {
		TIME = tIME;
	}

	public String getSBLSH() {
		return SBLSH;
	}

	public void setSBLSH(String sBLSH) {
		SBLSH = sBLSH;
	}

	public String getTYPE() {
		return TYPE;
	}

	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}

	public String getSXDM() {
		return SXDM;
	}

	public void setSXDM(String sXDM) {
		SXDM = sXDM;
	}

	public String getSXMC() {
		return SXMC;
	}

	public void setSXMC(String sXMC) {
		SXMC = sXMC;
	}

	public String getSSBM() {
		return SSBM;
	}

	public void setSSBM(String sSBM) {
		SSBM = sSBM;
	}

	public String getSXZT() {
		return SXZT;
	}

	public void setSXZT(String sXZT) {
		SXZT = sXZT;
	}
}
