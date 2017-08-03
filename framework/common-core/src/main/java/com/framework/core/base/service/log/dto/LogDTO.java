package com.framework.core.base.service.log.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author guoyuhua
 *
 */
public class LogDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String intfCode;
	protected String memberToken;
	protected Integer memberId;
	protected String channelId;
	protected Date startTime;
	protected Date endTime;
	protected String resCode;
	protected String requestMsg;
	protected String responseMsg;
	protected String sysid;

	public String getIntfCode() {
		return intfCode;
	}

	public void setIntfCode(String intfCode) {
		this.intfCode = intfCode;
	}

	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getMemberToken() {
		return memberToken;
	}

	public void setMemberToken(String memberToken) {
		this.memberToken = memberToken;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getRequestMsg() {
		return requestMsg;
	}

	public void setRequestMsg(String requestMsg) {
		this.requestMsg = requestMsg;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	@Override
	public String toString() {
		return "LogDTO [intfCode=" + intfCode + ", sysid=" + sysid + ", channelId=" + channelId + ", memberToken="
				+ memberToken + ", memberId=" + memberId + ", requestMsg=" + requestMsg + ", responseMsg=" + responseMsg
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", resCode=" + resCode + "]";
	}

}
