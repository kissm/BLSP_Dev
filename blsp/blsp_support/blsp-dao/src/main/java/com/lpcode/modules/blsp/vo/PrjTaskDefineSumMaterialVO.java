package com.lpcode.modules.blsp.vo;

public class PrjTaskDefineSumMaterialVO {
	
    private Long id;
    
    private Long materialId;
    
    private Long stageId;
    
    private Integer originalNum;
    
    private Integer copyNum;
    
    private String isMandatory;
    
    private String isByCondition;
    
    private String landType;

    private String isNeedPreAudit;
    
    private String isSpecialProject;
    
    private String isWithBasePart;
    
    private String isItType;
    
    private String isGovType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public Long getStageId() {
		return stageId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}

	public Integer getOriginalNum() {
		return originalNum;
	}

	public void setOriginalNum(Integer originalNum) {
		this.originalNum = originalNum;
	}

	public Integer getCopyNum() {
		return copyNum;
	}

	public void setCopyNum(Integer copyNum) {
		this.copyNum = copyNum;
	}

	public String getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}

	public String getIsByCondition() {
		return isByCondition;
	}

	public void setIsByCondition(String isByCondition) {
		this.isByCondition = isByCondition;
	}

	public String getLandType() {
		return landType;
	}

	public void setLandType(String landType) {
		this.landType = landType;
	}

	public String getIsNeedPreAudit() {
		return isNeedPreAudit;
	}

	public void setIsNeedPreAudit(String isNeedPreAudit) {
		this.isNeedPreAudit = isNeedPreAudit;
	}

	public String getIsSpecialProject() {
		return isSpecialProject;
	}

	public void setIsSpecialProject(String isSpecialProject) {
		this.isSpecialProject = isSpecialProject;
	}

	public String getIsWithBasePart() {
		return isWithBasePart;
	}

	public void setIsWithBasePart(String isWithBasePart) {
		this.isWithBasePart = isWithBasePart;
	}

	public String getIsItType() {
		return isItType;
	}

	public void setIsItType(String isItType) {
		this.isItType = isItType;
	}

	public String getIsGovType() {
		return isGovType;
	}

	public void setIsGovType(String isGovType) {
		this.isGovType = isGovType;
	}
    
}