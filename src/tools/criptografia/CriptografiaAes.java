package tools.criptografia;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;



public class CriptografiaAes {
	
	private static  String chaveSegurancaEptus = "EptusAmazon@1425";
	private static  SecretKey key;
	private static Cipher chipher;
	private static Base64 base;
	private static String msgCriptograda;
	private static String msgDecript;
	/**
	 * Solicita ao usuário que informe uma chave com caracteres:
	 * (256 / 8 = 32) 32 caracteres = 256 bits
	 * (192 / 8 = 192) 24 caracteres = 192 bits
	 * (128 / 8 = 128) 16 caracteres = 128 bits
	 */
	@SuppressWarnings("static-access")
	public static String codifica(String mensagem){
		byte[] bytemensagem = mensagem.getBytes();
		byte[] bytechave = chaveSegurancaEptus.getBytes();
		key = new SecretKeySpec(bytechave,"AES");
		try {
			chipher = Cipher.getInstance("AES");
			chipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] cipher = chipher.doFinal(bytemensagem);
			msgCriptograda = base.getEncoder().encodeToString(cipher);
					
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return msgCriptograda;
		}
	
		@SuppressWarnings("static-access")
		public static String decodificar(String decmensagem){
		byte[] bytechave = chaveSegurancaEptus.getBytes();
		key = new SecretKeySpec(bytechave,"AES");
		try {
			chipher = Cipher.getInstance("AES");
			chipher.init(Cipher.DECRYPT_MODE, key);
			byte[] byteDecmensagem = base.getDecoder().decode(decmensagem);
					
			byte[] byteDecodifica = chipher.doFinal(byteDecmensagem);
			msgDecript = new String(byteDecodifica);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return msgDecript;
		
		}
	
	




}
