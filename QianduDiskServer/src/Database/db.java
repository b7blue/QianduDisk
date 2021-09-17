package Database;

import java.sql.*;

/**
 * 
 * @author 70408
 * 包含四个方法
 * 1.添加新用户
 * 2.查询用户密码
 * 3.重设用户密码（根据邮箱）
 * 4.查询邮箱是否注册过
 *
 *user表目前的字段create table user(Username varchar(10),Email varchar(30),Password varchar(30));
 */

public class db {

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // 数据库驱动名称
	static final String DB_URL = "jdbc:mysql://localhost:3306/QianduDiskData";// 数据库连接url

	// 数据库的用户名与密码
	private static final String user = "QianduDisk";
	private static final String psw = "7846h37iwue29";

	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static String sql = "";
//    private static ResultSet res = null;

//	初始化数据库：加载驱动和连接数据库
	public static void init() {
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("数据库驱动加载成功");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("数据库驱动加载失败");
			System.exit(0);
		}

		try {
			con = DriverManager.getConnection(DB_URL, user, psw);
			System.out.println("数据库连接成功");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("数据库连接失败");
			System.exit(0);
		}
	}

//    在user表中添加一个新的用户
//    假如抛出錯誤，一般原因就是新用戶的名字和以前用戶的重複了或者邮箱重复，这两个字段都必须是唯一的
//	  或者有可能是这三个字段中任意一个为null，数据库规定了这些字段不能为空
	public static void addUser(String Username, String Email, String Password,String Groupname) throws SQLException {
		sql = "insert into user values(?,?,?,?)";

		ps = con.prepareStatement(sql);
		ps.setString(1, Username);
		ps.setString(2, Email);
		ps.setString(3, Password);
		ps.setString(4, Groupname);

		ps.executeUpdate();

	}
	
//	表log新添一行
	public static void addLog(String Username, String Filename, String Filesize) throws SQLException {
		sql = "insert into log values(?,?,?)";

		ps = con.prepareStatement(sql);
		ps.setString(1, Username);
		ps.setString(2, Filename);
		ps.setString(3, Filesize);

		ps.executeUpdate();

	}

//	表usergroup新添一行
	public static void addGroup(String Groupname, String Password) throws SQLException {
		sql = "insert into usergroup values(?,?)";

		ps = con.prepareStatement(sql);
		ps.setString(1, Groupname);
		ps.setString(2, Password);

		ps.executeUpdate();

	}
	
//	表hash新添一行
	public static void addHash(String Username, String Filename, String Remoteroute, String Hash) throws SQLException {
		sql = "insert into hash values(?,?,?,?)";

		ps = con.prepareStatement(sql);
		ps.setString(1, Username);
		ps.setString(2, Filename);
		ps.setString(3, Remoteroute);
		ps.setString(4, Hash);

		ps.executeUpdate();

	}
	

//  返回指定用户的密码，假如用户不存在则返回null
	public static String getUserPwd(String Username) throws SQLException {
		sql = "select Password from user where Username = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, Username);
		ResultSet res = ps.executeQuery();
		
		String pwd=null;
		while(res.next()) {
			pwd =  res.getString("Password");
		}

		return pwd;

	}

//  返回指定用户组的密码，假如用户组不存在则返回null
	public static String getGroupPwd(String Groupname) throws SQLException {
		sql = "select Password from usergroup where Groupname = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, Groupname);
		ResultSet res = ps.executeQuery();
		
		String pwd=null;
		while(res.next()) {
			pwd =  res.getString("Password");
		}

		return pwd;

	}
	
//	修改用户的密码
//	用户忘记密码，通过邮箱验证之后修改密码
	public static void resetPassword(String Username,String newPWD) throws SQLException {
		sql = "update user set Password = ? where Username = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, newPWD);
		ps.setString(2, Username);
		
		ps.executeUpdate();
	}
	
//	修改用户的昵称
	public static void resetUsername(String Username,String NewUsername) throws SQLException {
		sql = "update user set Username = ? where Username = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, NewUsername);
		ps.setString(2, Username);
		
		ps.executeUpdate();
	}

//	修改用户的用户组
	public static void resetGroupname(String Groupname,String Username) throws SQLException {
		sql = "update user set Groupname = ? where Username = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, Groupname);
		ps.setString(2, Username);
		
		ps.executeUpdate();
	}
	
//	查找邮箱是否注册过，返回true或者false
	public static boolean checkEmail(String Email) throws SQLException {
		sql = "select COUNT(Email) as amount from user where Email = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, Email);
		
		ResultSet res = ps.executeQuery();
		int amount = 0;
		while(res.next()) {
			amount =  res.getInt("amount");
		}

		if(amount==0)
			return false;
		else
			return true;
	}

//	查找用户名是否被注册过，返回true或者false
	public static boolean checkUsername(String Username) throws SQLException {
		sql = "select COUNT(Username) as amount from user where Username = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, Username);
		
		ResultSet res = ps.executeQuery();
		int amount = 0;
		while(res.next()) {
			amount =  res.getInt("amount");
		}

		if(amount==0)
			return false;
		else
			return true;
	}

//	查找用户组名是否被注册过，返回true或者false
	public static boolean checkGroupname(String Groupname) throws SQLException {
		sql = "select COUNT(Groupname) as amount from usergroup where Groupname = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, Groupname);
		
		ResultSet res = ps.executeQuery();
		int amount = 0;
		while(res.next()) {
			amount =  res.getInt("amount");
		}

		if(amount==0)
			return false;
		else
			return true;
	}
//	给用户分配空间
	public static void setSpace(String Username) throws SQLException {
		sql = "insert into capacity values(?,?,?)";

		ps = con.prepareStatement(sql);
		ps.setString(1, Username);
		ps.setString(2, "0");
		ps.setString(3, "482400");

		ps.executeUpdate(); 
	}
	
//	查询总容量
	public static String getTotal(String Username) throws SQLException {
		sql = "select Total from capacity where Username = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, Username);
		ResultSet res = ps.executeQuery();
		
		String Total=null;
		while(res.next()) {
			Total =  res.getString("Total");
		}
		return Total;

	}
	
//	查询已用容量
	public static String getUsed(String Username) throws SQLException {
		sql = "select Used from capacity where Username = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, Username);
		ResultSet res = ps.executeQuery();
		
		String Used=null;
		while(res.next()) {
			Used =  res.getString("Used");
		}
		return Used;

	}

//	查询用户组
	public static String getGroupname(String Username) throws SQLException {
		sql = "select Groupname from user where Username = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, Username);
		ResultSet res = ps.executeQuery();
		
		String Groupname=null;
		while(res.next()) {
			Groupname =  res.getString("Groupname");
		}
		return Groupname;

	}
	
//	更新已用容量
	public static void resetUsed(String Username,String newUsed) throws SQLException {
		sql = "update capacity set Used = ? where Username = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, newUsed);
		ps.setString(2, Username);
		
		ps.executeUpdate();
	}
	
	
//  根据邮箱查找昵称
//	public static String getNicknamebyEmail(String Email) throws SQLException {
//		sql = "select Username from user where Email = ?";
//		ps = con.prepareStatement(sql);
//		ps.setString(1, Email);
//		ResultSet res = ps.executeQuery();
//		
//		String Username=null;
//		while(res.next()) {
//			Username =  res.getString("Password");
//		}
//
//		return Username;
//	}
	
//	删除log中的一行
	public static void deleteLog(String Username,String Filename) throws SQLException {
		sql = "delete from log where Username = ? and Filename = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, Username);
		ps.setString(2, Filename);
		
		ps.executeUpdate();
	}
	
//	查询log中的filename
	public static String getLogFilename(String Username) throws SQLException {
	sql = "select Filename from log where Username = ?";
	ps = con.prepareStatement(sql);
	ps.setString(1, Username);
	ResultSet res = ps.executeQuery();
	
	String Filename=null;
	while(res.next()) {
		Filename =  res.getString("Filename");
	}

	return Filename;
}
	
//	查询log中的filesize
	public static String getLogFilesize(String Username) throws SQLException {
	sql = "select Filesize from log where Username = ?";
	ps = con.prepareStatement(sql);
	ps.setString(1, Username);
	ResultSet res = ps.executeQuery();
	
	String Filesize=null;
	while(res.next()) {
		Filesize =  res.getString("Filesize");
	}

	return Filesize;
}
	
}