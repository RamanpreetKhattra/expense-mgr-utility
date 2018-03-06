package com.ionwallet.expensemgrutility.common.dtos;

public class LoginResponseDTO  {
	
	public LoginResponseDTO(String accessToken) {
		super();
		this.accessToken = accessToken;
	}

	private String accessToken;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}
