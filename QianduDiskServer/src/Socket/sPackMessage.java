package Socket;

import java.util.HashMap;
import Database.*;

import util.MapString;

/*
 * 需要与客户端交互的功能项，由该类向server提供打包好的数据
 */
public class sPackMessage {
	static String data = null;
	static HashMap<String,String> map = new HashMap<>();
	
	//注册反馈信息
	//1.用户名已被注册
	public static String Signup_UsernameExisted() {
		map.put("Command", "SignUp UsernameExited");
		data = MapString.MapToString(map);
		return data;
	}
	//2.邮箱已被注册
	public static String Signup_EmailExisted() {
		map.put("Command", "Signup EmailExisted");
		data = MapString.MapToString(map);
		return data;
	}
	//3.注册成功
	public static String Signup_Success() {
		map.put("Command", "Signup Success");
		data = MapString.MapToString(map);
		return data;
	}
	
	//登录反馈信息
	//1. 用户名有误
	public static String Login_UserNotExist() {
		map.put("Command", "Login UserNotExist");
		data = MapString.MapToString(map);
		return data;
	}
	//2. 密码有误
	public static String Login_WrongPwd() {
		map.put("Command", "Login WrongPwd");
		data = MapString.MapToString(map);
		return data;
		
	}	
	//3. 登录成功
	public static String Login_Success(String Username,String Groupname) {
		map.put("Command", "Login Success");
		map.put("Username", Username);
		map.put("Groupname", Groupname);
		data = MapString.MapToString(map);
		return data;
		
	}
	
	//修改昵称
	//1. 新用户名已被注册
	public static String NewUsername_Existed() {
		map.put("Command", "NewUsername Existed");
		data = MapString.MapToString(map);
		return data;
	}
	//2. 修改昵称成功
	public static String NewUsername_Success() {
		map.put("Command", "NewUsername Success");
		data = MapString.MapToString(map);
		return data;
		
	}
	
	//通过邮箱发送验证码
	public static String GetVcode_Success(String Type) {
		map.put("Command", "GetVcode Success");
		map.put("Type", Type);
		data = MapString.MapToString(map);
		return data;
		
	}
	
	//验证邮箱验证码及反馈验证信息
	//1. 验证失败
	public static String CheckVcode_Failed(String Type) {
		map.put("Command", "CheckVcode Failed");
		map.put("Type", Type);
		data = MapString.MapToString(map);
		return data;
		
	}
	//2. 验证成功
	public static String CheckVcode_Success(String Type) {
		map.put("Command", "CheckVcode Success");
		map.put("Type", Type);
		data = MapString.MapToString(map);
		return data;
		
	}
	
	//重置密码
	//1. 成功
	public static String ResetPwd_Success() {
		map.put("Command", "ResetPwd Success");
		data = MapString.MapToString(map);
		return data;
		
	}
	//2. 用户不存在
	public static String ResetPwd_Failed() {
		map.put("Command", "ResetPwd Failed");
		data = MapString.MapToString(map);
		return data;
		
	}
	
	//返回文件列表
	public static String GetFileList(String Name, String Route) {
		FileList filelist = new FileList(Name,Route);
		data = filelist.getFileList();
		return data;
	}
	//上传文件
	//下载文件
	//删除文件
	
	//返回用户的用户组及共享文件列表
		public static String GetGroupname_Success(String Groupname) {
			FileList filelist = new FileList(Groupname,"");
			String filelist_str = filelist.getFileList();
			map.put("Command", "GetGroupname Success");
			map.put("Groupname", Groupname);
			map.put("Filelist", filelist_str);
			data = MapString.MapToString(map);
			return data;
		}
		//用户不在用户组中
		public static String NotInGroup() {
			map.put("Command", "NotInGroup");
			data = MapString.MapToString(map);
			return data;
		}
	
		//新建用户组
		//1. 创建失败，用户组名被占用
		public static String NewGroup_Existed() {
			map.put("Command", "NewGroup Existed");
			data = MapString.MapToString(map);
			return data;
			
		}
		//2. 创建成功
		public static String NewGroup_Success(String Groupname) {
			FileList filelist = new FileList(Groupname,"");
			String filelist_str = filelist.getFileList();
			map.put("Command", "NewGroup Success");
			map.put("Groupname", Groupname);
			map.put("Filelist", filelist_str);
			data = MapString.MapToString(map);
			return data;
		}
	
	//加入用户组
	//1. 加入失败，用户组不存在
	public static String AttendGroup_NotFound() {
		map.put("Command", "AttendGroup NotFound");
		data = MapString.MapToString(map);
		return data;
	//2. 加入失败，密码错误	
	}public static String AttendGroup_WrongPwd() {
		map.put("Command", "AttendGroup WrongPwd");
		data = MapString.MapToString(map);
		return data;
	//3. 加入成功	
	}public static String AttendGroup_Success(String Groupname) {
		FileList filelist = new FileList(Groupname,"");
		String filelist_str = filelist.getFileList();
		map.put("Command", "AttendGroup Success");
		map.put("Groupname", Groupname);
		map.put("Filelist", filelist_str);
		data = MapString.MapToString(map);
		return data;
		
	}
	//新建文件夹
	
	//容量监控

}
