package util;
/*
 * CheckTools用于提供客户端需要的验证
 */
public class cCheckTools {
	//图形验证码是否输入正确
	public static boolean CheckVcode(Vcode right_vcode, String code ) {
		return right_vcode.code.toLowerCase().equals(code.toLowerCase());
	}
	
	//昵称是否符合要求:"2~8个汉字、字母或数字"
	public static boolean CheckUsername(String Username) {
		if (Username.length() < 2 || Username.length() > 8)
			return false;
		for (int i = 0; i < Username.length(); i++)
		{
			if (Username.charAt(i) >= 0x4e00 && Username.charAt(i) <= 0x9fa5)
				continue;
			if (Username.charAt(i) >= 0x30 && Username.charAt(i) <= 0x39)
				continue;
			if (Username.charAt(i) >= 0x61 && Username.charAt(i) <= 0x7a)
				continue;
			if (Username.charAt(i) >= 0x41 && Username.charAt(i) <= 0x5a)
				continue;
			return false;
		}
		return true;
	}
	
	//邮箱格式是否有误
	public static boolean CheckEmailFormat(String Email)
	{
		if (!Email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$"))
			return false;
		return true;
	}
	
	//密码设置是否符合要求
	public static boolean CheckPwdFormat(String Password)
	{
		if (Password.length() < 8 || Password.length() > 20)
			return false;
		for (int i = 0; i < Password.length(); i++)
		{
			if (Password.charAt(i) >= 0x30 && Password.charAt(i) <= 0x39)
				continue;
			if (Password.charAt(i) >= 0x61 && Password.charAt(i) <= 0x7a)
				continue;
			if (Password.charAt(i) >= 0x41 && Password.charAt(i) <= 0x5a)
				continue;
			return false;
		}
		return true;
	}
	
	//两次密码是否一致
	public static boolean isSame(String pwd1, String pwd2) {
		return pwd1.equals(pwd2);
	}

}
