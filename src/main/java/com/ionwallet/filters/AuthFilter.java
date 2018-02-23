package com.ionwallet.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;

import com.ionwallet.CustomException.AuthorizationException;



public class AuthFilter extends GenericFilterBean{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
			throws IOException, ServletException {
		   HttpServletRequest httpRequest = (HttpServletRequest) request;
		   String header = httpRequest.getHeader("logged-in-user");
		   if(header!=null){
			   
			   //do this when user logged in
			   fc.doFilter(httpRequest, response);
			   
		   }else{
			   throw new AuthorizationException("User not authorized");
		   }
		   
		
	}

}
