package com.ionwallet.expensemgrutility.common.dtos;

public class LoginResponseDTO  {
	
	public LoginResponseDTO(TokenDTO token) {
		super();
		this.setToken(token);
	}

	public TokenDTO getToken() {
		return token;
	}

	public void setToken(TokenDTO token) {
		this.token = token;
	}

	private TokenDTO token;


}
