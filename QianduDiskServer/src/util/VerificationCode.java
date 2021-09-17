package util;

import java.util.Random;

public class VerificationCode {
	public static String code;
	private static char[] chars = new char[] {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','1','2','3','4','5','6','7','8','9'};
	
	public  static String produce() {
		Random r = new Random();
		int rand;
		String VerificationCode="";
		
		for(int i= 0;i<6;i++) {
			rand = r.nextInt(35);
			VerificationCode+=chars[rand];
		}
		code = VerificationCode;
		return code;
	}

}
