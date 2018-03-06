package com.ionwallet.encrypt.utils;

import java.security.Security;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author sharath.m
 *
 */
public class EncryptionUtils {

	private static final String AES = "AES";
	
	private static final String AES_ECB_PKCS7_PADDING = "AES/ECB/PKCS7Padding";
	
	private static final String BC = "BC";
	
	private static final String key ="123asdfghjklpoiu";

	static {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}


	public static void main(String args[]) throws Exception {
		String input = "sharath";
		System.out.println(encrypt(input));
		System.out.println(decrypt(encrypt(input)));


	}

	public static String encrypt(String input) throws Exception {
		SecretKeySpec secretKeySpec = getSecretKey();
		Cipher cipher = getCipher(secretKeySpec,Cipher.ENCRYPT_MODE);
		byte[] cipherText = new byte[cipher.getOutputSize(input.getBytes().length)];
		int ctLength = cipher.update(input.getBytes(), 0, input.getBytes().length, cipherText, 0);
		cipher.doFinal(cipherText, ctLength);
		byte[] encrptByte = Base64.getEncoder().encode(cipherText);
		return  new String(encrptByte);
	}


	public static String decrypt(String password) throws Exception {
		SecretKeySpec secretKeySpec = getSecretKey();
		Cipher cipher = getCipher(secretKeySpec,Cipher.DECRYPT_MODE);
		byte input[]=Base64.getDecoder().decode(password);
		byte[] plainText = new byte[cipher.getOutputSize(input.length)];
		int ptLength = cipher.update(input, 0, input.length, plainText, 0);
		ptLength += cipher.doFinal(plainText, ptLength);
		byte[] removePadding = new byte[ptLength];
		for(int i =0; i< ptLength; i++){
			removePadding[i]=plainText[i];
		}
		return new String(removePadding);
	}


	private static Cipher getCipher(SecretKey secretKey, int mode) throws Exception {
		Cipher cipher = Cipher.getInstance(AES_ECB_PKCS7_PADDING, BC);
		cipher.init(mode, secretKey);
		return  cipher;
	}
	
	private static SecretKeySpec getSecretKey() {
		SecretKeySpec secretKeySpec= new SecretKeySpec(key.getBytes(), AES);
		return secretKeySpec;
	}


}
