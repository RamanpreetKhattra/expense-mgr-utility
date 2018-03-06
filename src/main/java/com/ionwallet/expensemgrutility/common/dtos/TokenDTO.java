package com.ionwallet.expensemgrutility.common.dtos;

import java.util.Date;

public class TokenDTO {

	private String accessToken;
	
	private String refreshToken;
	
	private Date timeOfIssue;
	
	private Date validUpTo;
	
	

	public TokenDTO(String accessToken, String refreshToken, Date timeOfIssue, Date validUpTo) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.timeOfIssue = timeOfIssue;
		this.validUpTo = validUpTo;
	}
	
	public boolean isExpired() {
		if (validUpTo != null) {
			if (validUpTo.before(new java.util.Date()))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else 
			return false;
	}

	public TokenDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Date getTimeOfIssue() {
		return timeOfIssue;
	}

	public void setTimeOfIssue(Date timeOfIssue) {
		this.timeOfIssue = timeOfIssue;
	}

	public Date getValidUpTo() {
		return validUpTo;
	}

	public void setValidUpTo(Date validUpTo) {
		this.validUpTo = validUpTo;
	}
	
}
