package Socket;

import util.*;
import javax.swing.JProgressBar;
import Window.*;
import java.io.*;
import java.net.*;
import java.util.*;
/*
 * sClient类为客户端，而每个sClient实例对象为一次socket通信
 */
public class sClient {

	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private FileInputStream fis;
	private FileOutputStream fos;
	static boolean isFirstTry = true;
	static Login_Window login = null;
	static Main_Window mainwindow = null;
	static String Username = null;
	static String Groupname = null;
	String Email ;
	
//	初始化客户端
	public sClient() throws Exception {

		try {
			socket = new Socket("192.168.43.46", 43211);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			System.out.println("客户端连接服务端成功");
		} catch (IOException e) {
			e.printStackTrace();
		}
//		if(isFirstTry) {
//			//如果是第一次启动客户端，则弹出登录窗口
//			isFirstTry = false;
//			login = new Login_Window();
//			login.LoginButton.setEnabled(true);
//			login.AutoLogin();
//			this.send(send);
//		}

	}

//	开启接收服务器数据的通道
	public void recv() {
		try {

			in = new DataInputStream(socket.getInputStream());
//				out = new DataOutputStream(socket.getOutputStream());

//				在输入流中获取指令字段
			HashMap<String, String> data = MapString.StringToMap(in.readUTF());
			String Command = data.get("Command");
			System.out.println(data);

//				关闭客户端的标志
			if (Command.equals("bye")) {
				this.send("bye");
				in.close();
				socket.close();
				System.out.println("client已下线");
			}
			String Type = null;
			switch (Command) {
			case "Upload Success":
//				System.out.println("文件上传成功");
				break;
			case "Upload Permit":
//				System.out.println("文件上传成功");
				break;
			case "Download Permit":
				this.recvFile(data);
				break;
			case "SignUp UsernameExited":
				//注册时用户名已存在，显示错误信息“用户名已存在”
				login.signup.signup2.NicknameError.setText("用户名已存在");
				
				break;
			case "Signup EmailExisted":
				//注册时邮箱已存在，显示错误信息"该邮箱已注册，请重新输入！"
				login.signup.MailError.setText("该邮箱已注册，请重新输入！");
				break;
			case "Signup Success":
				//注册成功，注册页面消失，准备登录
				login.signup.signup2.dispose();
				break;
			case "Login UserNotExist":
				//登录时用户名不存在，显示错误信息“请输入正确的用户名”
				login.Error.setText("请输入正确的用户名");
				break;
			case "Login WrongPwd":
				//登录时密码错误，显示错误信息“请输入正确的密码”
				login.Error.setText("请输入正确的密码");
				break;
			case "Login Success":
				//登录成功，跳转入主页面
				Username = data.get("Username");
				Groupname = data.get("Groupname");
				login.dispose();
				login = null;
				mainwindow = new Main_Window(Username,Groupname);
				break;
			case "GetVcode Success":
				//邮箱验证码获取成功
				Type = data.get("Type");
				switch(Type) {
				//注册页面邮箱验证码获取成功,显示提示信息“验证码已发送，请输入验证码”
				case "Signup":
					login.signup.MailError.setText("验证码已发送，请输入验证码");	
					break;
				//忘记密码界面邮箱验证码获取成功	
				case "ForgetPwd":
					login.find.MailError.setText("验证码已发送，请输入验证码");	
					break;
				}
				
				
				break;
			case "CheckVcode Failed":
				//邮箱验证码验证失败
				Type = data.get("Type");
				switch(Type) {
				//注册页面邮箱验证码验证失败,显示提示信息"验证码错误，请重新输入！"
				case "Signup":
					login.signup.MailCodeError.setText("验证码错误，请重新输入！");	
					break;
				//忘记密码界面邮箱验证码验证失败	
				case "ForgetPwd":
					login.find.MailCodeError.setText("验证码错误，请重新输入！");	
					break;
				}
				
				break;
			case "CheckVcode Success":
				//邮箱验证码验证成功
				Type = data.get("Type");
				switch(Type) {
				//注册页面邮箱验证码验证成功，跳转进注册的第二个页面
				case "Signup":
				Email = login.signup.Email;
				login.signup.dispose();
				login.signup.signup2 = new Signup2(Email);
				break;
				case "ForgetPwd":
					Email = login.find.Email;
					login.find.dispose();
					login.find.find2 = new Find2(Email);
					break;
				}
				break;
			case "ResetPwd Success":
				//重置密码窗口消失
				login.find.find2.dispose();
				break;
			case "ResetPwd Failed":
				login.find.find2.NicknameError.setText("该用户不存在");
			case "GetGroupname Success":
				//用户在用户组中，返回用户组名及共享空间文件
				
				break;
			case "NotInGroup":
				//用户不在任何用户组中，弹出对话框
				//mainwindow.share = new Share(Username);
				
				break;
			case "NewGroup Existed":
				//新建用户组失败，显示错误信息”该用户组已存在”
				mainwindow.share.create.Error.setText("该用户组已存在");
				break;
			case "NewGroup Success":
				//新建用户组成功，新建框消失，进入用户组的共享空间
				mainwindow.share.create.dispose();
				mainwindow.share.dispose();
				Groupname = data.get("Groupname");
				mainwindow.Groupname = Groupname;
				
				break;
			case "AttendGroup NotFound":
				//加入用户组失败，显示错误信息“该用户组不存在”
				mainwindow.share.join.Error.setText("该用户组不存在");
				break;
			case "AttendGroup WrongPwd":
				//加入用户组失败，显示错误信息“用户组密码错误”
				mainwindow.share.join.Error.setText("用户组密码错误");
				break;
			case "AttendGroup Success":
				//加入用户组成功，加入框消失，进入用户组的共享空间
				mainwindow.share.join.dispose();
				mainwindow.share.dispose();
				Groupname = data.get("Groupname");
				mainwindow.Groupname = Groupname;
				break;
			case "FileList Empty":
				//获取文件列表时，文件列表为空；
				break;
			case "FileList":
//				收到filelist标志就要刷新文件显示的页面
//				调用啥函数呢？
//				服务器在返回filelist的标志的时候会用filelist函数将所有文件信息打包成data发给客户端
				Add_File_List list = new Add_File_List(mainwindow, data);
				list.get();
				//获取文件列表成功，展示列表
				break;
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

//	客户端要发送String给服务器就调用这个方法
	public void send(String send) {
		try {
			out.writeUTF(send);
			System.out.println("client发送：" + send);
			
//			接收服务端反馈
//			this.recv();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	客户端要发送文件给服务器就调用这个方法
	public void sendFile(String Username, File file) throws Exception {
		try {
//            out = new DataOutputStream(socket.getOutputStream());
			if (file.exists()) {
				//传输之前先进行内容检测，若有违规内容即可停止传送
				String str = txttest.txt2String(file);
				if(txttest.detect("北风", str))return;
				fis = new FileInputStream(file);
				
				
				// 在正式传输文件之前，先发送文件名和文件大小和上传文件的请求给服务器
				HashMap<String, String> data = new HashMap<String, String>();
				data.put("Username", Username);
				data.put("Filename", file.getName());
				data.put("Filesize", Long.toString(file.length()));
				data.put("Command", "Upload");
				this.send(MapString.MapToString(data));

//				进度条
				JProgressBar bar = mainwindow.AddOneDownloadFile(file.getName(), Long.toString(file.length()));
				// 开始传输文件
				System.out.println("======== 开始传输文件 ========");
				byte[] bytes = new byte[1024];
				int length = 0;
				long progress = 0;
				while ((length = fis.read(bytes, 0, bytes.length)) != -1) {
					out.write(bytes, 0, length);
					out.flush();
					progress += length;
					int percent = (int)(100 * progress / file.length());
					System.out.print("| " + (100 * progress / file.length()) + "% |");
//					进度条
					bar.setValue(percent);
					bar.setString(percent + " %");
				}
				System.out.println();
				
//				接收服务端反馈
				this.recv();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("关闭资源");
			if (fis != null)
				fis.close();
			if (out != null)
				out.close();
			socket.close();
			System.out.println(socket.isClosed());

		}
	}

//	接收服务器发来的文件
	public void recvFile(HashMap<String, String> data) {
		try {

			// 从服务器发来的map中获取文件的名字和大小
			String Filename = data.get("Filename");
			long Filesize = Long.valueOf(data.get("Filesize"));

//			进度条
			JProgressBar bar = mainwindow.AddOneDownloadFile(Filename, Long.toString(Filesize));
			
//			路径可以改
			File directory = new File("D:\\QianduDiskDownload");
			if (!directory.exists()) {
				directory.mkdir();
			}
			File file = new File(directory.getAbsolutePath() + File.separatorChar + Filename);
			fos = new FileOutputStream(file);

			// 开始接收文件
			byte[] bytes = new byte[1024];
			int length = 0;
			long recv_length = 0;
			while ((length = in.read(bytes, 0, bytes.length)) != -1) {
				recv_length += length;
				fos.write(bytes, 0, length);
				fos.flush();
//				进度条
				int percent = (int)(100 * recv_length / Filesize);
				bar.setValue(percent);
				bar.setString(percent + " %");
				
				if (recv_length == Filesize)
					break;
			}
//			System.out.println("======== 文件接收成功 [File Name：" + Filename + "] [Size："
//					+ Format.getFormatFileSize(Filesize) + "] ========");
//			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("关闭资源");
				if (fos != null)
					fos.close();
				if (in != null)
					in.close();
				socket.close();
				System.out.println(socket.isClosed());
			} catch (Exception e) {

			}
		}
	}
	
//	测试
	public static void main(String[] args) {

		try {
			
			sClient.login = new Login_Window();
//			Client.sendFile("gigi",new File("C:\\Users\\70408\\Desktop\\sql.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}

//		try {
//			sClient Client = new sClient("localhost",8088);
//			Client.sendFile("gigi",new File("C:\\Users\\70408\\Downloads\\baidu_animals.zip"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		try {
//			sClient Client = new sClient("localhost",8088);
//			HashMap<String,String> data = new HashMap<String,String>();
//			data.put("Username", "gigi");
//			data.put("Filename", "baidu_animals.zip");
//			data.put("Command", "Download");
//			Client.send(MapString.MapToString(data));
//			Client.recv();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
