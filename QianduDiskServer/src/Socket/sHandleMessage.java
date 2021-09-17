package Socket;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;

import Database.db;

import util.*;

/*
 * sHandleMessage用于处理收到的socket消息
 */
public class sHandleMessage {
	static String ret = null;
	//注册
	public static String Handle_Signup(HashMap<String,String> data) throws SQLException {
		//检测用户名是否存在
		String Username = data.get("Username");
		String Email = data.get("Email");
		if(db.checkUsername(Username)) {
			//用户名已存在，返回错误信息
			ret = sPackMessage.Signup_UsernameExisted();
			return ret;
		}
		
		else
		{
			//通过验证，返回注册成功信息
			String Password = data.get("Password");
			db.addUser(Username, Email, Password,"-");
			ret = sPackMessage.Signup_Success();
			return ret;
		}
		
	}

	//登录
	public static String Handle_Login(HashMap<String,String> data) throws SQLException {

		String Username = data.get("Username");
		//用户不存在：返回错误信息
		if(!db.checkUsername(Username)) {
			ret = sPackMessage.Login_UserNotExist();
			return ret;
		}
		String Password = data.get("Password");
		//登录成功，返回正确信息
		if(Password.equals(db.getUserPwd(Username))) {
			String Groupname = db.getGroupname(Username);
			ret = sPackMessage.Login_Success(Username,Groupname);
			return ret;
		}else {
			//密码错误：返回错误信息
			
			ret = sPackMessage.Login_WrongPwd();
			return ret;
		}
		
	}

	//修改昵称
	public static String Handle_NewUsername(HashMap<String,String> data) throws SQLException {
		String Username = data.get("Username");
		String NewUsername = data.get("NewUsername");
		//检测新昵称是否被注册
		if(db.checkUsername(NewUsername)) {
			ret = sPackMessage.NewUsername_Existed();
			return ret;
		}
		else {
			//新昵称未被注册：返回正确信息
			db.resetUsername(NewUsername, Username);
			ret = sPackMessage.NewUsername_Success();
			return ret;
		}
	}

	//向邮箱发送验证码
	public static String Handle_GetVcode(HashMap<String,String> data) throws Exception {
		String Email = data.get("Email");
		String Type = data.get("Type");
		switch(Type) {
		case "Signup":
			if(db.checkEmail(Email)) {
				ret = sPackMessage.Signup_EmailExisted();
			}
		}
		mailSender.sendMail(Email, VerificationCode.produce());
		ret = sPackMessage.GetVcode_Success(Type);
		return ret;
	}
	
	//验证邮箱验证码
	public static String Handle_CheckVcode(HashMap<String,String> data) {
		//验证不区分不小写
		String Vcode = data.get("Vcode").toUpperCase();
		String Type = data.get("Type");
		//验证成功，返回正确信息
		if(Vcode.equals(VerificationCode.code)) {
			ret = sPackMessage.CheckVcode_Success(Type);
			return ret;
		}else {
			ret = sPackMessage.CheckVcode_Failed(Type);
			return ret;
		}
	}

	//重置密码
	public static String Handle_ResetPwd(HashMap<String,String> data) throws SQLException {
		String Username = data.get("Username");
		String Password = data.get("Password");
		if(db.checkUsername(Username)) {
			db.resetPassword(Username, Password);
			ret = sPackMessage.ResetPwd_Success();	
		}else {
			ret = sPackMessage.ResetPwd_Failed();
		}
		
		return ret;
	}
	
	//查询用户组
	public static String Handle_GetGroupname(HashMap<String,String> data) throws SQLException {
		String Username = data.get("Username");
		String Groupname = db.getGroupname(Username);
		if(Groupname.equals("-")) {
			ret = sPackMessage.NotInGroup();
			return ret;
		}
		ret = sPackMessage.GetGroupname_Success(Groupname);
		return ret;
	}
	
	//新建用户组
	public static String Handle_NewGroup(HashMap<String,String> data) throws SQLException {
		String Groupname = data.get("Groupname");
		//创建失败，用户组名被占用
		if(db.checkGroupname(Groupname)) {
			ret = sPackMessage.NewGroup_Existed();
			return ret;
		}else {
			//创建成功，更新用户组表以及用户表，并返回成功信息
			String Password = data.get("Password");
			String Username = data.get("Username");
			db.addGroup(Groupname,Password);
			db.resetGroupname(Groupname,Username);
			ret = sPackMessage.NewGroup_Success(Groupname);
			return ret;
		}
	}

	//加入用户组
	public static String Handle_AttendGroup(HashMap<String,String> data) throws SQLException {
		String Groupname = data.get("Groupname");
		//加入失败，用户组不存在
		if(!db.checkGroupname(Groupname)) {
			ret = sPackMessage.AttendGroup_NotFound();
			return ret;
			
		}else {
			String Password = data.get("Password");
			//加入失败，密码错误
			if(!Password.equals(db.getGroupPwd(Groupname))) {
				ret = sPackMessage.AttendGroup_WrongPwd();
				return ret;
			}else {
				//加入成功，设置该用户的用户组为该组
				String Username = data.get("Username");
				db.resetGroupname(Groupname, Username);
				ret = sPackMessage.AttendGroup_Success(Groupname);
				return ret;
			}
			
		}
	}

	//获取文件列表
	public static String Handle_GetFileList(HashMap<String,String> data) {
		String Name = data.get("Name");
//		String Route = data.get("Route");
		ret = sPackMessage.GetFileList(Name, "");
		return ret;
	}
	
	public static String Handle_DeleteFile(HashMap<String,String> data) {
//		先获取要删除的文件名和用户名
		String Name = data.get("Name");
		String Filename = data.get("Filename");
		String dir = "D:\\QianduDiskUpload\\"+Name;
		String DeleteFileDir = FindFile.getDir(Filename, dir);
		
		File DeleteFile = new File(DeleteFileDir);
		if (DeleteFile.exists())
			DeleteFile.delete();
		
		ret = sPackMessage.GetFileList(Name, "");
		return ret;
	}

	//新建文件夹
	public static String Handle_NewFolder(HashMap<String,String> data) {
		return ret;
	}
}
