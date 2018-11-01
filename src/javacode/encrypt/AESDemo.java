package javacode.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public final class AESDemo {

	public static void encrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, 
	UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		//Get key generator,即AES的密钥生成器
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		//密钥长度为128 bit, AES还支持192， 256 bit，
		keyGenerator.init(128, new SecureRandom("123456".getBytes()));
		
		//生成初始密钥
		SecretKey secretKey = keyGenerator.generateKey();
		byte[] encodedKey = secretKey.getEncoded();
		//AES 专用密钥
		SecretKeySpec aseKey = new SecretKeySpec(encodedKey, "AES");
		//创建加密器， AES工作模式：CBC,另外也支持 ECB， CTR, CFB, OFB。 填充方式：NoPadding，另外也支持  PKCS5Padding, ISO10126Padding
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		String content = "Hello World!";
		byte[] byteContent = content.getBytes("utf-8");
		
		//加密器初始化为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, aseKey);
		
		//得到密文
		byte[] encrypted = cipher.doFinal(byteContent);
		String txtEncrypt = new String(encrypted, "utf-8");
		System.out.println(txtEncrypt);
	}
	
}
