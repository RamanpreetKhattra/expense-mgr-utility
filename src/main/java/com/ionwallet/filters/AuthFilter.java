package com.ionwallet.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import com.ionwallet.cache.service.CachingService;
import com.ionwallet.expensemgrutility.common.dtos.TokenDTO;
import com.ionwallet.jwt.TokenUtils;
import com.ionwallet.security.SecurityContextHolder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class AuthFilter extends GenericFilterBean{
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String header = httpRequest.getHeader("access-token");
		String userId = CachingService.userTokenMap.get(header);
		TokenDTO token = CachingService.tokenCache.get(userId);
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		if(token!=null){
			try {
				Jws<Claims> claims = TokenUtils.verifyToken(token);
				SecurityContextHolder.claims.set(claims);
				fc.doFilter(httpRequest, response);
			} catch (Exception e) {
				httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
				writeToResponse(httpResponse, e.getMessage());
			}
		}else{
			httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
			writeToResponse(httpResponse, "Invalid Token, Login again to continue");
		}


	}
	
	public static void writeToResponse(HttpServletResponse response,
			String message) {
		try {
			response.getWriter().write(message);
			response.getWriter().flush();
		}
		catch (IOException e) {
			new RuntimeException(e);
		}
		finally {
			try {
				response.getWriter().close();
			}
			catch (IOException e) {
				new RuntimeException(e);
			}
		}

	}

}
