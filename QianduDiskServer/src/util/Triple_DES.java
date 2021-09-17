package util;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 3DES加密工具类
 */
public class Triple_DES {

	final static Base64.Decoder decoder = Base64.getDecoder();
	final static Base64.Encoder encoder = Base64.getEncoder();

	// 密钥 长度不得小于24
	private final static String secretKey = "3897jq487f65du49j2d934701fhis";
	// 向量 可有可无 终端后台也要约定
	private final static String iv = "01234567";
	// 加解密统一使用的编码方式
	private final static String encoding = "utf-8";

//        3DES加密
	public static String encode(String plainText) throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
		return encoder.encodeToString(encryptData);
	}

//       3DES解密
	public static String decode(String encryptText) throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

		byte[] decryptData = cipher.doFinal(decoder.decode(encryptText));

		return new String(decryptData, encoding);
	}

//	public static void main(String args[]) throws Exception {
//		String str = "hello my friend";
//		System.out.println("----加密前-----：" + str);
//		String encodeStr = Triple_DES.encode(str);
//		System.out.println("----加密后-----：" + encodeStr);
//		System.out.println("----解密后-----：" + Triple_DES.decode("v7F3V1U3Q+/dz7Edp5lRX8AwJG1Wfi8I/WwzOQZTCeBK1wgShly6bvSXP2MvKoilKY2U/jKqHo5FtOi/CxUmGlAfL3ev2pPu"));
//	}
}
