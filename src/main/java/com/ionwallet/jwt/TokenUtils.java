package com.ionwallet.jwt;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.ionwallet.CustomException.AuthorizationException;
import com.ionwallet.cache.service.CachingService;
import com.ionwallet.encrypt.utils.EncryptionUtils;
import com.ionwallet.expensemgrutility.common.dtos.TokenDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
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
		TokenDTO token = issueToken("sharath@tavant.com", "ionWallet", "ionWallet");
		System.out.println(token);
		Jws<Claims> tojen = verifyToken(token);
		Claims body = tojen.getBody();
		String object = (String) body.get(USER_ID);
		System.out.println(EncryptionUtils.decrypt(object));
	}
	
	
	/**
	 * Creates a jwt token 
	 * @param userId
	 * @param issuer
	 * @param audience
	 * @return
	 * @throws Exception 
	 */
	public static TokenDTO issueToken(String userId,String issuer,String audience)  {
		String token = null;
		try {
			Date now = Date.from(Instant.now());
			token = Jwts.builder().claim(USER_ID, EncryptionUtils.encrypt(userId))
					.setIssuer(issuer)
					.setIssuedAt(now)
					.setNotBefore(now)
					.setExpiration(getExpiryTime(now,Calendar.SECOND,10))
					.setAudience(audience)
					.signWith(SignatureAlgorithm.HS256, secret)
					.compressWith(CompressionCodecs.GZIP)
					.compact();
		
		} catch (Exception e) {
			throw new RuntimeException("Unable to create token");
		}
		return updateTokenToRegistry(token,userId);
	}

	/**
	 * Put token in to registry for validation of refreshToken 
	 * @param token
	 * @param userId 
	 * @return
	 */
	private static TokenDTO updateTokenToRegistry(String token, String userId) {
		TokenDTO tokenDTO =null;
		try {
			String refreshToken =EncryptionUtils.encrypt(token).substring(2, 22);
			Date now = Date.from(Instant.now());
			tokenDTO = new TokenDTO(token, refreshToken, now, getExpiryTime(now,Calendar.HOUR,1));
			CachingService.userTokenMap.put(token, userId);
			CachingService.tokenCache.put(userId, tokenDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tokenDTO;
	}


	/**
	 * Verify the token 
	 * @param token
	 * @return
	 */
	public static Jws<Claims> verifyToken(TokenDTO token) {
		try {
			return Jwts.parser().setSigningKey(secret).parseClaimsJws(token.getAccessToken());
		} catch (UnsupportedJwtException | MalformedJwtException | SignatureException
				| IllegalArgumentException e) {
			throw new AuthorizationException("Unauthorized, Please login again to continue");
		} catch(ExpiredJwtException e) {
			String userId = CachingService.userTokenMap.get(token.getAccessToken());
			TokenDTO tokenDTO = CachingService.tokenCache.get(userId);
			if(null!=tokenDTO && !tokenDTO.isExpired()) {
				TokenDTO renewedToken = issueToken(userId, defaultIssuer, defaultAudience);
				return Jwts.parser().setSigningKey(secret).parseClaimsJws(renewedToken.getAccessToken());
			}else {
				throw new AuthorizationException("Token is Expired, Login again to continue");
			}
		}
	}



	/**
	 * @param currentDate
	 * @param unit
	 * @param value
	 * @return
	 */
	private static Date getExpiryTime(Date currentDate, int unit, int value) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(unit, value);
		return cal.getTime();
	}

}
