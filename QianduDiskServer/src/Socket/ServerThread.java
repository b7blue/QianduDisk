package Socket;

import java.io.*;
import java.net.*;
import java.text.Format;
import java.util.HashMap;

import Database.FileList;
import Database.db;
import Socket.sHandleMessage;
import util.*;

public class ServerThread extends Thread {

	private Socket socket = null;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	private FileOutputStream fos;
	private FileInputStream fis;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
//	因为这里是继承thread这个类所以说一定要重写run
//	其实功能和sClient.recv()是相对应的
	public void run() {

		try {

			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());

			System.out.println("客户端socket接收成功");
			

//				在输入流中获取指令字段
			HashMap<String, String> data = MapString.StringToMap(in.readUTF());
			String Command = data.get("Command");
			String feedback = null;
			System.out.println("服务器收到："+data);

//				根据客户端发来的不同指令，服务端调用不同的函数
//				发送String给客户端就用out.writeUTF();
			switch (Command) {
			case "Upload":
				this.recvFile(data);
				break;
			case "Download":
				this.sendFile(data);
				break;
			case "SignUp":
				feedback = sHandleMessage.Handle_Signup(data);
				send(feedback);
				break;
			case "Login":
				feedback = sHandleMessage.Handle_Login(data);
				send(feedback);
				break;
			case "NewUsername":
				feedback = sHandleMessage.Handle_NewUsername(data);
				send(feedback);
				break;
			case "NewFolder":
				feedback = sHandleMessage.Handle_NewFolder(data);
				send(feedback);
				break;
			
			case "GetVcode":
				feedback = sHandleMessage.Handle_GetVcode(data);
				send(feedback);
				break;
			case "NewGroup":
				feedback = sHandleMessage.Handle_NewGroup(data);
				send(feedback);
				break;
			case "AttendGroup":
				feedback = sHandleMessage.Handle_AttendGroup(data);
				send(feedback);
				break;
			case "GetGroupname":
				feedback = sHandleMessage.Handle_GetGroupname(data);
				send(feedback);
				break;
			case "CheckVcode":
				feedback = sHandleMessage.Handle_CheckVcode(data);
				send(feedback);
				break;
			case "ResetPwd":
				feedback = sHandleMessage.Handle_ResetPwd(data);
				send(feedback);
				break;
			case "GetFileList":
				feedback = sHandleMessage.Handle_GetFileList(data);
				send(feedback);
				break;
			case "DeleteFile":
				feedback = sHandleMessage.Handle_DeleteFile(data);
				send(feedback);
				break;
			}
//			关闭资源
			socket.close();
			System.out.println("这一次交互结束");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	服务器要发送String给客户端就调用这个方法
	public void send(String send) {
		try {
			out.writeUTF(send);
			System.out.println("Server发送：" + send);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	服务器要发送文件给客户端就调用这个方法
	public void sendFile(HashMap<String, String> data) throws Exception {
		try {
//            out = new DataOutputStream(socket.getOutputStream());

			// 从客户端发来的map中获取文件的名字和大小
			String Username = data.get("Username");
			String Filename = data.get("Filename");
			

//			根据用户名和文件名生成文件路径，获得要下载的文件
			String dir = "D:\\QianduDiskUpload\\"+Username+"\\"+Filename;
			File file = new File(dir);

			if (file.exists()) {
				fis = new FileInputStream(file);

				// 在正式传输文件之前，先发送"Download Permit"标识、文件名、文件大小给客户端，让他做好准备接收文件
				data = new HashMap<String, String>();
				data.put("Filename", Filename);
				data.put("Filesize", Long.toString(file.length()));
				data.put("Command", "Download Permit");
				this.send(MapString.MapToString(data));
				String key;
//				获取解码文件所需密钥：用户密码md5值的前16位
				if(db.checkUsername(Username)) {
					key = db.getUserPwd(Username).substring(0, 16);
					System.out.println(":)");
				}else {
					key = db.getGroupPwd(Username).substring(0,16);
					System.out.println(":(");
				}
				
				System.out.println(key);
				
//				解码文件
//				先创建一个临时文件区和临时文件
				File temp = new File("D:\\QianduDiskTemp");
				if(!temp.exists())
					temp.mkdir();
				File decFile = new File("D:\\QianduDiskTemp\\"+Filename);
				decFile.createNewFile();
				FileEncDec.DecFile(file, decFile, key);
				
				fis = new FileInputStream(decFile);
				// 开始传输文件
				System.out.println("======== 开始传输文件 ========");
				byte[] bytes = new byte[1024];
				int length = 0;
				long progress = 0;
				while ((length = fis.read(bytes, 0, bytes.length)) != -1) {
					out.write(bytes, 0, length);
					out.flush();
					progress += length;
					System.out.print("| " + (100 * progress / file.length()) + "% |");
				}
				System.out.println();
//				传输完之后把临时文件删除
				decFile.delete();
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
	
//	接收客户端发来的文件
	public void recvFile(HashMap<String, String> data) {
		try {

			// 从客户端发来的map中获取文件的名字和大小
			String Username = data.get("Username");
			String Filename = data.get("Filename");
			long Filesize = Long.valueOf(data.get("Filesize"));

//			接收到临时路径、临时文件
			File directory = new File("D:\\QianduDiskTemp");
			if (!directory.exists()) {
				directory.mkdir();
			}
			File file = new File("D:\\QianduDiskTemp\\"+ Filename);
//			file.createNewFile();
			fos = new FileOutputStream(file);


			// 开始接收文件
			byte[] bytes = new byte[1024];
			int length = 0;
			long recv_length = 0;
			while ((length = in.read(bytes, 0, bytes.length)) != -1) {
				recv_length += length;
				fos.write(bytes, 0, length);
				fos.flush();
				if (recv_length == Filesize)
					break;
			}
//			System.out.println("======== 文件接收成功 [File Name：" + Filename + "] [Size："
//					+ Format.getFormatFileSize(Filesize) + "] ========");
			
//			给客户端返回结果，表示服务器已经收到文件，上传成功
			FileList list = new FileList(data.get("Username"),"");
			this.send(list.getFileList());
//			获取加密文件所需密钥：用户密码md5值的前16位
			//String key = db.getUserPwd(Username).substring(0, 16);
			String key;
//			获取解码文件所需密钥：用户密码md5值的前16位
			if(db.checkUsername(Username)) {
				key = db.getUserPwd(Username).substring(0, 16);
				System.out.println(":)");
			}else {
				key = db.getGroupPwd(Username).substring(0,16);
				System.out.println(":(");
			}
			System.out.println(key);
			
//			加密文件
//			创建一个用户文件区和加密后文件
			File temp = new File("D:\\QianduDiskUpload\\"+Username);
			if(!temp.exists())
				temp.mkdir();
			File encFile = new File("D:\\QianduDiskUpload\\"+Username+"\\"+Filename);
			encFile.createNewFile();
			FileEncDec.EncFile(file, encFile, key);
			
//			完事之后把临时文件删除
			file.delete();
			
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

}