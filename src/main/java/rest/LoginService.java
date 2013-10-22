package rest;

import java.security.SecureRandom;
import java.util.Date;

import javax.annotation.security.PermitAll;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.Stateless;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;

@Stateless
@Path("/login")
@PermitAll
public class LoginService {
	static byte IV[] = new byte[16];
	static byte encryptionKey[] = new byte[16];
	{
		SecureRandom sr = new SecureRandom();
		sr.nextBytes(encryptionKey);
		sr.nextBytes(IV);
	}
	
	public static byte[] encrypt(byte[] plainText) 
			throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		SecretKeySpec key = new SecretKeySpec(encryptionKey, "AES");
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV));
		return cipher.doFinal(plainText);
	}
		 
	public static byte[] decrypt(byte[] cipherText) 
			throws Exception{
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
		SecretKeySpec key = new SecretKeySpec(encryptionKey, "AES");
		cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV));
		return cipher.doFinal(cipherText);
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@FormParam("login") String login, @FormParam("password") String password)
			throws Exception {
		Auth.SecurityToken st = Auth.SecurityToken.newBuilder()
				.setId(1)
				.setLogin(login)
				.addRoles("domain-view")
				.setExpires(new Date().getTime() + 86400 * 365 * 1000)
				.build();
		byte encrypted[] = encrypt(st.toByteArray()); 
		return DatatypeConverter.printBase64Binary(encrypted) + "\n";
	}
}
