package com.ionwallet.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/**
 * Use this class for getting the logged in user details
 * @author sharath.m
 *
 */
public final class SecurityContextHolder {
	
	public static ThreadLocal<Jws<Claims>> claims = new ThreadLocal<>();

}
