package Socket;

import java.util.HashMap;

import util.MD5;
import util.MapString;

/*
 * 需要与服务器通信的功能项，由该类向client提供打包好的数据
 */
public class cPackMessage {
	static HashMap<String,String> map = new HashMap<>();
	static String data = null;
	//注册
	public static String Signup(String Username, String Email, String Password) throws Exception {
		map.put("Command", "SignUp");
		map.put("Username", Username);
		map.put("Email", Email);
		map.put("Password", MD5.encrypt(Password));
		data = new String(MapString.MapToString(map));
		return data;
	}
	//登录
	public static String Login(String Username, String Password) throws Exception {
		map.put("Command", "Login");
		map.put("Username", Username);
		map.put("Password", MD5.encrypt(Password));
		data = new String(MapString.MapToString(map));
		return data;
	}
	//请求文件列表
	public static String GetFileList(String Name, String Route) {
		map.put("Command", "GetFileList");
		//map.put("Type", Type);
		map.put("Name", Name);
		map.put("Route", Route);
		data = new String(MapString.MapToString(map));
		return data;
	}
	//新建文件夹
	//修改昵称
	public static String NewUsername(String Username, String NewUsername) {
		HashMap<String,String> map = new HashMap<>();
		map.put("Command", "NewUsername");
		map.put("Username", Username);
		map.put("NewUsername", NewUsername);
		String data = new String(MapString.MapToString(map));
		return data;
		
	}
	//新建用户组
	public static String NewGroup(String Username, String Groupname, String Password) throws Exception {
		map.put("Command", "NewGroup");
		map.put("Username", Username);
		map.put("Groupname", Groupname);
		map.put("Password", MD5.encrypt(Password));
		data = new String(MapString.MapToString(map));
		return data;
	}
	//加入用户组
	public static String AttendGroup(String Username, String Groupname, String Password) throws Exception {
		map.put("Command", "AttendGroup");
		map.put("Username", Username);
		map.put("Groupname", Groupname);
		map.put("Password", MD5.encrypt(Password));
		//System.out.println("U R HERE");
		data = new String(MapString.MapToString(map));
		return data;
	}
	//查询用户组
	public static String GetGroupname(String Username) {
		map.put("Command", "GetGroupname");
		map.put("Username", Username);
		data = new String(MapString.MapToString(map));
		return data;
	}
	//上传文件
	//下载文件
	//删除文件
	//请求向邮箱发送验证码
	public static String GetVcode(String Email,String Type) {
		map.put("Command", "GetVcode");
		map.put("Type", Type);
		map.put("Email", Email);
		data = new String(MapString.MapToString(map));
		return data;
	}
	//请求服务器进行邮箱验证码的验证
	public static String CheckVcode(String vcode, String Type) {
		map.put("Command", "CheckVcode");
		map.put("Type", Type);
		map.put("Vcode", vcode);
		data = new String(MapString.MapToString(map));
		return data;
	}
	//重置密码
	public static String ResetPwd(String Username, String Password) throws Exception {
		map.put("Command", "ResetPwd");
		map.put("Username", Username);
		map.put("Password", MD5.encrypt(Password));
		data = new String(MapString.MapToString(map));
		return data;
	}

}
