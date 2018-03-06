package com.ionwallet.jwt;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import com.ionwallet.CustomException.AuthorizationException;
import com.ionwallet.encrypt.utils.EncryptionUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class TokenUtils {
	
	public static final String defaultIssuer = "ionWallet";
	
	public static final String defaultAudience = "ionWallet";

	private static final String USER_ID = "userId";
	
	private static String secret ="!@#$SDFGH;ionwallet";

	
	public static void main(String args[]) throws Exception {
		String token= issueToken("sharath@tavant.com", "ionWallet", "ionWallet");
		System.out.println(token);
		Jws<Claims> tojen = verifyToken(token);
		Claims body = tojen.getBody();
		String object = (String) body.get(USER_ID);
		System.out.println(EncryptionUtils.decrypt(object));
	}
	
	
	/**
	 * Creates a jwt token 
	 * 
	 * @param userId
	 * @param issuer
	 * @param audience
	 * @return
	 * @throws Exception 
	 */
	public static String issueToken(String userId,String issuer,String audience)  {
		String token = null;
		try {
			Date now = Date.from(Instant.now());
			token = Jwts.builder().claim(USER_ID, EncryptionUtils.encrypt(userId))
					.setIssuer(issuer)
					.setIssuedAt(now)
					.setNotBefore(now)
					.setExpiration(getExpiryTime(now))
					.setAudience(audience)
					.signWith(SignatureAlgorithm.HS256, secret)
					.compact();
		} catch (Exception e) {
			throw new RuntimeException("Unable to create token");
		}
		return token;
	}

	/**
	 * Verify the token 
	 * @param token
	 * @return
	 */
	public static Jws<Claims> verifyToken(String token) {
		try {
			return Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
				| IllegalArgumentException e) {
			throw new AuthorizationException("invalid jwt token");
		}
	}



	private static Date getExpiryTime(Date currentDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.HOUR, 10);
		return cal.getTime();
	}

}
