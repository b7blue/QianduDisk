package Window;

import Socket.*;
import util.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
/*
 * Login_Window是登录窗口的界面设计
 */
public class Login_Window extends JFrame implements KeyListener
{
	public static Vcode vcode;

	Container container = null;
	JPanel UpPanel = null;
	JPanel DownPanel = null;
	JPanel RightPanel = null;
	JTextField UsernameText = null;
	JTextField ValidCodeText = null;
	JTextField PasswordText = null;
	JTextField PasswordTipText = null;
	public JLabel Error = null;
	JCheckBox RemPassword = null;
	JCheckBox AutoLogin = null;
	public JButton LoginButton = null;
	public Signup signup = null;
	public Find find = null;
	String Key = "2fh;mvok3;faojdjojf3n2";
	public String validCode;
	public Point origin = new Point();
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	Dimension ds = tk.getScreenSize();
	int width = ds.width;
	int hight = ds.height;

	public Login_Window()
	{
		Init();
		MakeRightPanel();
		this.setVisible(true);
		this.setContentPane(RightPanel);
	}
	
	public void Init()
	{
		
		this.setSize(width/4,hight/2);
		this.setLocation(width/4,hight/4);//设置窗体偏移、大小	
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);//隐藏并释放窗体，dispose()，当最后一个窗口被释放后，则程序也随之运行结束。 
		//this.setUndecorated(true);//去边框，无法改变大小，便于设计底图
		this.setTitle("千度安全网盘 - 登录");
		Image img = tk.getImage("网盘.png");
		setIconImage(img);
		this.setVisible(true);         
		this.setResizable(false);
		this.setLocationRelativeTo(null) ;
		container = this.getContentPane();//获取当前窗口的内容窗格
		container.setLayout(null);//加上这句话，所有组件都可以用setBounds();来控制位置和大小了

		RightPanel = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(new Color(245,245,245));
			}
		};
		RightPanel.setSize(width/4,hight/2);
		RightPanel.setLayout(null);
		RightPanel.setBackground(new Color(245,245,245));
	}

	private void AddGirlImage()
	{
		ImageIcon ico = new ImageIcon("logo.png");
		JLabel Image = new JLabel(ico);
		Image.setBounds((int)(width/4-360)/2, 10, 360, 65);//数值可能只能设为int
		RightPanel.add(Image);
	}
	
	private void AddTitle()
	{
		
	}

	private void AddErrorLabel()
	{
		// 错误标签
		Error = new JLabel();
		Error.setBounds((int)(width/4-100)/2, 245, 200, 30);
		Error.setForeground(new Color(252, 109, 123));
		Font f = new Font("黑体", Font.PLAIN, 13);
		Error.setFont(f);
		Error.setText("");
		RightPanel.add(Error);
	}

	private void AddUsername()
	{
		// 用户名
		UsernameText = new JTextField("请输入用户名");//JTextField 是一个轻量级组件，它允许编辑单行文本。
		UsernameText.setBounds((int)(width/4-240)/2, 95, 240, 27);
		UsernameText.setForeground(Color.GRAY);
		UsernameText.addKeyListener(this);
		RightPanel.add(UsernameText);
		UsernameText.addFocusListener(new FocusAdapter()//文本框焦点事件
		{
			@Override
			public void focusGained(FocusEvent e)//当组件获得键盘焦点时调用。
			{
				if (UsernameText.getText().equals("请输入用户名") || UsernameText.getText().equals("用户名不能为空"))
				{
					UsernameText.setText("");
					UsernameText.setForeground(Color.BLACK);//设置字体及等待线的颜色
				}
			}

			@Override
			public void focusLost(FocusEvent e)//离开焦点
			{
				if (UsernameText.getText().equals(""))
				{
					UsernameText.setForeground(Color.GRAY);
					UsernameText.setText("用户名不能为空");
				}
			}
		});
		// 用户名前面的图片
		ImageIcon ico = new ImageIcon("用户名.png");
		Image temp = ico.getImage().getScaledInstance(20, 20, ico.getImage().SCALE_DEFAULT);
		ico = new ImageIcon(temp);
		JLabel Image = new JLabel(ico);
		Image.setBounds((int)(width/4-240)/2-30, 95, 20, 20);
		RightPanel.add(Image);
	}

	private void AddPassword()
	{
		// 密码
		PasswordText = new JPasswordField();
		PasswordText.setBounds((int)(width/4-240)/2, 140, 240, 27);//40
		PasswordText.setText("");
		PasswordText.setForeground(Color.GRAY);
		PasswordText.setVisible(false);
		PasswordText.addKeyListener(this);
		RightPanel.add(PasswordText);

		PasswordTipText = new JTextField();
		PasswordTipText.setBounds((int)(width/4-240)/2, 140, 240, 27);
		PasswordTipText.setText("请输入密码");//+String.valueOf(width/4)+" "+String.valueOf(hight/2)
		PasswordTipText.setForeground(Color.GRAY);
		RightPanel.add(PasswordTipText);
		PasswordTipText.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				PasswordTipText.setVisible(false);
				PasswordText.setVisible(true);
				PasswordText.requestFocusInWindow();
			}
		});
		PasswordText.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				if (PasswordText.getText().equals("请输入密码") || UsernameText.getText().equals("密码不能为空"))
				{
					PasswordText.setText("");
					PasswordText.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if (PasswordText.getText().equals(""))
				{
					PasswordTipText.setText("密码不能为空");
					PasswordTipText.setVisible(true);
					PasswordText.setVisible(false);
				}
			}
		});
		// 密码前面的图片
		ImageIcon ico = new ImageIcon("密码.png");
		Image temp = ico.getImage().getScaledInstance(20, 20, ico.getImage().SCALE_DEFAULT);
		ico = new ImageIcon(temp);
		JLabel Image = new JLabel(ico);
		Image.setBounds((int)(width/4-240)/2-30, 140, 20, 20);
		RightPanel.add(Image);
	}
	
	private void AddValidCode(){
		// 验证码输入框
		ValidCodeText = new JTextField("请输入验证码");
		ValidCodeText.setBounds((int)(width/4-240)/2, 185, 135, 27);
		ValidCodeText.setForeground(Color.GRAY);
		ValidCodeText.addKeyListener(this);
		RightPanel.add(ValidCodeText);
		ValidCodeText.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				if (ValidCodeText.getText().equals("请输入验证码"))
				{
					ValidCodeText.setText("");
					ValidCodeText.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if (ValidCodeText.getText().equals(""))
				{
					ValidCodeText.setForeground(Color.GRAY);
					ValidCodeText.setText("请输入验证码");
				}
			}
		});

		vcode = new Vcode();
		vcode.setBounds((int)(width/4-240)/2+140,185,100,20);
		RightPanel.add(Box.createGlue());
		RightPanel.add(vcode);
		// 密码前面的图片
		ImageIcon ico = new ImageIcon("验证码.png");
		Image temp = ico.getImage().getScaledInstance(20, 20, ico.getImage().SCALE_DEFAULT);
		ico = new ImageIcon(temp);
		JLabel Image = new JLabel(ico);
		Image.setBounds((int)(width/4-240)/2-30, 185, 20, 20);
		RightPanel.add(Image);
	}
	
	private void AddRemPassword()
	{
		// 记住密码前面的复选框
		RemPassword = new JCheckBox();
		RemPassword.setBounds((int)(width/4-240)/2, 223, 20, 20);
		RemPassword.setBackground(new Color(245,245,220));
		RemPassword.addKeyListener(this);
		RemPassword.addMouseListener(new MouseAdapter()
				{
					public void mouseClicked(MouseEvent e)
					{
						if(!RemPassword.isSelected())
							AutoLogin.setSelected(false);
					}
				});
		RightPanel.add(RemPassword);
		// 记住密码标签
		JLabel RemPassLabel = new JLabel();
		RemPassLabel.setBounds((int)(width/4-240)/2+20, 217, 56, 30);
		RemPassLabel.setForeground(new Color(102, 102, 102));//字体的颜色
		Font f = new Font("黑体", Font.PLAIN, 13);
		RemPassLabel.setFont(f);
		RemPassLabel.setText("记住密码");
		RemPassLabel.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				RemPassword.setSelected(!RemPassword.isSelected());
				if(!RemPassword.isSelected())
					AutoLogin.setSelected(false);
			}
		});
		RightPanel.add(RemPassLabel);
		//如果有存储的密码，优先使用存储的
		try
		{
			File file = new File("Password.txt");
			FileReader filereader = new FileReader(file);
			BufferedReader br = new BufferedReader(filereader);
			String Username = null;
			Username = br.readLine();
			if(Username == null || Username.isEmpty())
			{
				br.close();
				return;
			}
			String Password = Triple_DES.decode(br.readLine());
			br.close();
			UsernameText.setText(Username);
			//PasswordText.setText(Password);
			PasswordTipText.setVisible(false);
			RemPassword.setSelected(true);
			PasswordText.setVisible(true);
		} catch (Exception e1)
		{
		}
	}

	private void AddAuto()
	{
		// 自动登录前面的复选框
		AutoLogin = new JCheckBox();
		AutoLogin.setBounds((int)(width/4)/2, 223, 20, 20);
		AutoLogin.setBackground(new Color(245,245,220));
		AutoLogin.addKeyListener(this);
		AutoLogin.addMouseListener(new MouseAdapter()
				{
					public void mouseClicked(MouseEvent e)
					{
						if(AutoLogin.isSelected())
							RemPassword.setSelected(true);
					}
				});
		RightPanel.add(AutoLogin);
		// 自动登录标签
		JLabel AutoLabel = new JLabel();
		AutoLabel.setBounds((int)(width/4)/2+20, 217, 56, 30);
		AutoLabel.setForeground(new Color(102, 102, 102));
		Font f = new Font("黑体", Font.PLAIN, 13);
		AutoLabel.setFont(f);
		AutoLabel.setText("自动登录");
		AutoLabel.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				AutoLogin.setSelected(!AutoLogin.isSelected());
				if(AutoLogin.isSelected())
					RemPassword.setSelected(true);
			}
		});
		RightPanel.add(AutoLabel);
		AutoLogin.addChangeListener(new ChangeListener()
				{
					public void stateChanged(ChangeEvent arg0)
					{
						if((!AutoLogin.isSelected()) && Error.getText().equals("正在自动登录..."))
							Error.setText("");
					}
				});
	}

	private void AddLoginButton()
	{
		LoginButton = new JButton();
		LoginButton.setForeground(new Color(255, 255, 255));
		LoginButton.setBounds((int)(width/4-240)/2, 330, 240, 34);
		LoginButton.setBorder(null);
		LoginButton.setBackground(new Color(18,181,255));
		Font f = new Font("黑体", Font.PLAIN, 18);
		LoginButton.setFont(f);
		LoginButton.setText("安全登录");
		
		LoginButton.setEnabled(true);
		LoginButton.requestFocusInWindow();
		//Login_Window this_temp = this;
		LoginButton.addKeyListener(this);
		LoginButton.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(LoginButton.isEnabled() == false)
					return;

				if(UsernameText.getText().isEmpty()){//没输入用户名
					Error.setText("请输入用户");
					return;
				}
				if(PasswordText.getText().isEmpty()) {//没输入密码
					Error.setText("请输入密码");
					return;
				}
				if(ValidCodeText.getText().isEmpty()||ValidCodeText.getText().equals("")
				||ValidCodeText.getText().equals("请输入验证码")) {//没输入密码
					Error.setText("请输入验证码");
					return;
				}else if (!cCheckTools.CheckVcode(Login_Window.vcode,ValidCodeText.getText()) ){
					Error.setText("验证码错误");
					ValidCodeText.setText("");
					Login_Window.vcode.nextVcode();
					return;
				}
				//发送交互信息给服务器
				try {
					String data = cPackMessage.Login(UsernameText.getText(),PasswordText.getText());
					sClient client = new sClient();
					client.send(data);
					client.recv();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				if(RemPassword.isSelected())
				{
					File file = new File("Password.txt");
					try
					{
						FileWriter filewriter = new FileWriter(file); 
						BufferedWriter bw = new BufferedWriter(filewriter);
						bw.write(UsernameText.getText() + "\r\n");
						bw.write(Triple_DES.encode(PasswordText.getText()));
						bw.close();
						filewriter.close();
					} catch (Exception e1)
					{
						e1.printStackTrace();
					}
				}
				else
				{
					File file = new File("Password.txt");
					file.delete();
				}
				//是否自动登录，记录到文件中
				File file = new File("AutoLogin.txt");
				if(AutoLogin.isSelected())
				{
					try
					{
						file.createNewFile();
					} catch (IOException e1)
					{
					}
				}
				else
				{
					file.delete();
				}
			}
		});
		RightPanel.add(LoginButton);
	}

	private void Signup()
	{
		
		JLabel NoAccount = new JLabel();
		NoAccount.setBounds(30, 300, 100, 30);
		NoAccount.setForeground(Color.GRAY);
		Font f = new Font("黑体", Font.PLAIN, 16);
		NoAccount.setFont(f);
		// 注册按钮
		JButton SignupButton = new JButton();
		SignupButton = new JButton();
		SignupButton.setForeground(new Color(18,181,255));
		SignupButton.setBounds((int)(width/4)/2+20, 280, 100, 34);
		SignupButton.setBackground(new Color(255, 255, 255));
		f = new Font("黑体", Font.PLAIN, 12);
		SignupButton.setFont(f);
		SignupButton.setText("注册账号");
		SignupButton.setFocusable(false);
		SignupButton.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent e)
			{
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			public void mouseClicked(MouseEvent e)
			{
				signup = new Signup();
			}
		});
		RightPanel.add(SignupButton);
	}
	
	private void Find()
	{
		// 找回密码按钮
		JButton FindButton = new JButton();
		FindButton = new JButton();
		FindButton.setForeground(new Color(18, 181, 255));
		FindButton.setBounds((int)(width/4)/2-120, 280, 100, 34);
		FindButton.setBackground(new Color(255,255,255));
		Font f = new Font("黑体", Font.PLAIN, 12);
		FindButton.setFont(f);
		FindButton.setText("找回密码");
		FindButton.setFocusable(false);
		FindButton.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent e)
			{
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			public void mouseClicked(MouseEvent e)
			{
				//signup = new Signup();
				find = new Find();
			}
		});
		RightPanel.add(FindButton);
	}

	public void MakeRightPanel()
	{
		AddGirlImage();
		AddTitle();
		AddErrorLabel();
		AddUsername();
		AddPassword();
		AddValidCode();
		AddRemPassword();
		AddAuto();
		AddLoginButton();
		Signup();
		Find();
	}

	public void AutoLogin() throws Exception
	{
		
		File file1 = new File("AutoLogin.txt");
		File file2 = new File("Password.txt");
		if(file1.exists())
			AutoLogin.setSelected(true);
		else
			AutoLogin.setSelected(false);
		
		if(file1.exists() && file2.exists() && AutoLogin.isSelected())
		{
			Error.setText("正在自动登录...");
			try
			{
				Thread.sleep(2000);
			} catch (InterruptedException e)
			{
			}
			if(!AutoLogin.isSelected())
				return;
			if(LoginButton.isEnabled() == false)
				return;
			if(UsernameText.getText().isEmpty())//没输入用户名
				Error.setText("请输入用户名");
			if(PasswordText.getText().isEmpty())//没输入密码
				Error.setText("请输入密码");
			
			String data = cPackMessage.Login(UsernameText.getText(),PasswordText.getText());
			sClient client = new sClient();
			client.send(data);
			client.recv();
			
			//如果选了记住密码，把密码保存到本地
			if(RemPassword.isSelected())
			{
				try
				{
					FileWriter filewriter = new FileWriter(file2); 
					BufferedWriter bw = new BufferedWriter(filewriter);
					bw.write(UsernameText.getText() + "\r\n");
					bw.write(Triple_DES.encode(PasswordText.getText()));
					bw.close();
					filewriter.close();
				} catch (Exception e1)
				{
					e1.printStackTrace();
				}
			}
			else
			{
				file2.delete();
			}
			//是否自动登录，记录到文件中
			if(AutoLogin.isSelected())
			{
				try
				{
					file1.createNewFile();
				} catch (IOException e1)
				{
				}
			}
			else
			{
				file1.delete();
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		
		
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		if(e.getKeyChar() == '\n')
		{
			if(UsernameText.getText().isEmpty())//没输入用户名
				Error.setText("请输入用户名");
			if(PasswordText.getText().isEmpty())//没输入密码
				Error.setText("请输入密码");
			if(ValidCodeText.getText().isEmpty())//没输入密码
				Error.setText("请输入验证码");

			String data;
			try {
				data = cPackMessage.Login(UsernameText.getText(),PasswordText.getText());
				sClient client = new sClient();
				client.send(data);
				client.recv();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			
			//如果选了记住密码，把密码保存到本地
			if(RemPassword.isSelected())
			{
				File file = new File("Password.txt");
				try
				{
					FileWriter filewriter = new FileWriter(file); 
					BufferedWriter bw = new BufferedWriter(filewriter);
					bw.write(UsernameText.getText() + "\r\n");
					bw.write(Triple_DES.encode(PasswordText.getText()));
					bw.close();
					filewriter.close();
				} catch (Exception e1)
				{
					e1.printStackTrace();
				}
			}
			else
			{
				File file = new File("Password.txt");
				file.delete();
			}
			//是否自动登录，记录到文件中
			File file = new File("AutoLogin.txt");
			if(AutoLogin.isSelected())
			{
				try
				{
					file.createNewFile();
				} catch (IOException e1)
				{
				}
			}
			else
			{
				file.delete();
			}
		}
	}	
}
