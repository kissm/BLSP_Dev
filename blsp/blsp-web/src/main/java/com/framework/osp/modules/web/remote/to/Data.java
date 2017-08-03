package com.framework.osp.modules.web.remote.to;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	String prjCode;
	String prjCat;
	String companyMphone;
	String comapnyFax;
	String company;
	String companyAddr;
	String companyCode;
	String legalEntity;
	String entityMphone;
	String entityPhone;
	String agentName;
	String agentMphone;
	String agentPhone;
	String prjName;
	String prjNature;
	String prjAddr;
	String prjFloorSpace;
	String prjRedlineSpace;
	String prjDescription;
	List<Task> RESULTS;

	public String getPrjCode() {
		return prjCode;
	}

	public void setPrjCode(String prjCode) {
		this.prjCode = prjCode;
	}

	public String getPrjCat() {
		return prjCat;
	}

	public void setPrjCat(String prjCat) {
		this.prjCat = prjCat;
	}

	public String getCompanyMphone() {
		return companyMphone;
	}

	public void setCompanyMphone(String companyMphone) {
		this.companyMphone = companyMphone;
	}

	public String getComapnyFax() {
		return comapnyFax;
	}

	public void setComapnyFax(String comapnyFax) {
		this.comapnyFax = comapnyFax;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompanyAddr() {
		return companyAddr;
	}

	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}

	public String getEntityMphone() {
		return entityMphone;
	}

	public void setEntityMphone(String entityMphone) {
		this.entityMphone = entityMphone;
	}

	public String getEntityPhone() {
		return entityPhone;
	}

	public void setEntityPhone(String entityPhone) {
		this.entityPhone = entityPhone;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentMphone() {
		return agentMphone;
	}

	public void setAgentMphone(String agentMphone) {
		this.agentMphone = agentMphone;
	}

	public String getAgentPhone() {
		return agentPhone;
	}

	public void setAgentPhone(String agentPhone) {
		this.agentPhone = agentPhone;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public String getPrjNature() {
		return prjNature;
	}

	public void setPrjNature(String prjNature) {
		this.prjNature = prjNature;
	}

	public String getPrjAddr() {
		return prjAddr;
	}

	public void setPrjAddr(String prjAddr) {
		this.prjAddr = prjAddr;
	}

	public String getPrjFloorSpace() {
		return prjFloorSpace;
	}

	public void setPrjFloorSpace(String prjFloorSpace) {
		this.prjFloorSpace = prjFloorSpace;
	}

	public String getPrjRedlineSpace() {
		return prjRedlineSpace;
	}

	public void setPrjRedlineSpace(String prjRedlineSpace) {
		this.prjRedlineSpace = prjRedlineSpace;
	}

	public String getPrjDescription() {
		return prjDescription;
	}

	public void setPrjDescription(String prjDescription) {
		this.prjDescription = prjDescription;
	}

	public List<Task> getRESULTS() {
		return RESULTS;
	}

	public void setRESULTS(List<Task> rESULTS) {
		RESULTS = rESULTS;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
