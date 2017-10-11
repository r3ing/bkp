package tools.criptografia;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptMD5 {

	/** 
	 * 
	 * @param cadena 
	 * @return encryptMD5
	 */
	public static String getMD5(String cadena) {
		
		String encryptMD5 = null;
		
		try {
			MessageDigest msgDigest = MessageDigest.getInstance("MD5");
			msgDigest.update(cadena.getBytes());
			encryptMD5 = new BigInteger(1, msgDigest.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return encryptMD5;
	}

}
