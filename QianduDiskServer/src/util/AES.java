package util;
import java.util.Arrays;
//import java.util.Base64;
//import java.util.Base64.Decoder;
//import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
/**
 *
 * @author Administrator
 *
 */
public class AES {

    // ����
    public static byte[] encrypt(byte[] sSrc, String sKey) throws Exception {
    	
        if (sKey == null) {
            System.out.print("KeyΪ��null");
            return null;
        }
        // �ж�Key�Ƿ�Ϊ16λ
        if (sKey.length() != 16) {
            System.out.print("Key���Ȳ���16λ");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");//"�㷨/ģʽ/���뷽ʽ"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc);

        return encrypted;//�˴�ʹ��BASE64��ת�빦�ܣ�ͬʱ����2�μ��ܵ����á�
    }

    // ����
    public static byte[] decrypt(byte[] sSrc, String sKey) throws Exception {
    	//Decoder decoder = Base64.getDecoder();
        try {
            // �ж�Key�Ƿ���ȷ
            if (sKey == null) {
                System.out.print("KeyΪ��null");
                return null;
            }
            // �ж�Key�Ƿ�Ϊ16λ
            if (sKey.length() != 16) {
                System.out.print("Key���Ȳ���16λ");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            //byte[] encrypted1 = decoder.decode(sSrc);//����base64����
            try {
                byte[] original = cipher.doFinal(sSrc);
                //String originalString = new String(original,"utf-8");
                return original;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
       
         //�˴�ʹ��AES-128-ECB����ģʽ��key��ҪΪ16λ��
        
    	String cKey = "1234567890123456";
        // ��Ҫ���ܵ��ִ�
    	byte[] cSrc = new byte[1024];
    	for (int j = 0; j<cSrc.length; j++){  
    		cSrc[j] = (byte) (j % 16);
    		}  
        System.out.println(Arrays.toString(cSrc));
        // ����
        byte[] enString = AES.encrypt(cSrc, cKey);
        System.out.println("���ܺ���ִ��ǣ�" + Arrays.toString(enString));

        // ����
        byte[] DeString = AES.decrypt(enString, cKey);
        System.out.println("���ܺ���ִ��ǣ�" + Arrays.toString(DeString));
    }
}
