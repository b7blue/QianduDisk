package Window;

import util.*;


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
import java.util.HashMap;
import java.util.Random;

import javax.swing.*;

import Socket.*;

public class Find2 extends JFrame
{
	String Key = "2fh;mvok3;faojdjojf3n2";
	JCheckBox Agree = null;
	
	Container container = null;
	JPanel DownPanel = null;
	String Username = null;
	JTextField NicknameText = null;
	JTextField PasswordText = null;
	JTextField PasswordText2 = null;
	public JLabel NicknameError = null;
	JLabel PasswordError = null;
	JLabel Password2Error = null;
	JButton Confirm = null;
	static String Email = null;
	Toolkit tk = Toolkit.getDefaultToolkit();
	Dimension ds = tk.getScreenSize();
	int width = ds.width;
	int hight = ds.height;

	public Find2(String Email)
	{
		this.Email = Email;
		Init();
		AddDownPanel();		
		this.setVisible(true);
		this.setContentPane(DownPanel);
	}

	public void Init()
	{
		this.setSize(width/4,(int)hight/3+50);
		this.setLocation(width/4,hight/4+25);//设置窗体偏移、大小
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);//隐藏并释放窗体，dispose()，当最后一个窗口被释放后，则程序也随之运行结束。 
		this.setTitle("千度安全网盘 - 找回密码");
		Image img = tk.getImage("网盘.png");
		setIconImage(img);
		this.setVisible(true);         
		this.setResizable(false);
		this.setLocationRelativeTo(null) ;
		container = this.getContentPane();
		container.setLayout(null);
		
		// 下面板
		DownPanel = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(new Color(245,245,245));
			}
		};
		DownPanel.setSize(width/4,(int)hight/3+50);
		DownPanel.setLayout(null);
		DownPanel.setBackground(new Color(245,245,245));
	}
	
	private void AddGirlImage()
	{
		ImageIcon ico = new ImageIcon("logo.png");
		JLabel Image = new JLabel(ico);
		Image.setBounds((int)(width/4-360)/2, 10, 360, 65);//数值可能只能设为int
		DownPanel.add(Image);
	}

	private void AddNickname()
	{
		// 昵称标签
		JLabel NicknameLabel = new JLabel();
		NicknameLabel.setBounds((int)(width/4-290)/2, 105, 50, 30);
		Font f = new Font("楷体", Font.PLAIN, 16);
		NicknameLabel.setFont(f);
		NicknameLabel.setText("昵称");
		DownPanel.add(NicknameLabel);
		// 昵称
		NicknameText = new JTextField("2~8个汉字、字母或数字");
		NicknameText.setBounds((int)(width/4-290)/2+50, 109, 240, 27);
		NicknameText.setForeground(Color.GRAY);
		DownPanel.add(NicknameText);
		NicknameText.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				if (NicknameText.getText().equals("2~8个汉字、字母或数字"))
				{
					NicknameText.setText("");
					NicknameText.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if (NicknameText.getText().equals(""))
				{
					NicknameText.setForeground(Color.GRAY);
					NicknameText.setText("2~8个汉字、字母或数字");
				}
			}
		});
		DownPanel.add(NicknameText);
		// 错误标签
		NicknameError = new JLabel();
		NicknameError.setBounds((int)(width/4-200)/2, 210, 200, 30);
		f = new Font("楷体", Font.PLAIN, 13);
		NicknameError.setForeground(Color.RED);
		NicknameError.setFont(f);
		NicknameError.setText("");
		DownPanel.add(NicknameError);
	}

	private void AddPassword()
	{
		// 密码标签
		JLabel PasswordLabel = new JLabel();
		PasswordLabel.setBounds((int)(width/4-290)/2, 145, 50, 30);
		Font f = new Font("楷体", Font.PLAIN, 16);
		PasswordLabel.setFont(f);
		PasswordLabel.setText("密码");
		DownPanel.add(PasswordLabel);
		// 密码
		PasswordText = new JPasswordField();
		PasswordText.setBounds((int)(width/4-290)/2+50, 149, 240, 27);
		PasswordText.setText("");
		PasswordText.setForeground(Color.GRAY);
		PasswordText.setVisible(false);
		DownPanel.add(PasswordText);

		JTextField PasswordTipText = new JTextField();
		PasswordTipText.setBounds((int)(width/4-290)/2+50, 149, 240, 27);
		PasswordTipText.setText("8~20个字母和数字组合");
		PasswordTipText.setForeground(Color.GRAY);
		DownPanel.add(PasswordTipText);
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
				if (PasswordText.getText().equals("8~20个字母和数字组合"))
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
					PasswordTipText.setText("8~20个字母和数字组合");
					PasswordTipText.setVisible(true);
					PasswordText.setVisible(false);
				}
			}
		});
		// 错误标签
		PasswordError = new JLabel();
		PasswordError.setBounds((int)(width/4-200)/2, 210, 200, 30);
		f = new Font("楷体", Font.PLAIN, 13);
		PasswordError.setForeground(Color.RED);
		PasswordError.setFont(f);
		PasswordError.setText("");
		DownPanel.add(PasswordError);
	}
	
	private void AddPassword2()
	{
		// 再次密码标签
		JLabel PasswordLabel2 = new JLabel();
		PasswordLabel2.setBounds((int)(width/4-290)/2-20, 185, 80, 30);
		Font f = new Font("楷体", Font.PLAIN, 16);
		PasswordLabel2.setFont(f);
		PasswordLabel2.setText("确认密码");
		DownPanel.add(PasswordLabel2);
		// 再次密码
		PasswordText2 = new JPasswordField();
		PasswordText2.setBounds((int)(width/4-290)/2+50, 185, 240, 27);
		PasswordText2.setText("");
		PasswordText2.setForeground(Color.GRAY);
		PasswordText2.setVisible(false);
		DownPanel.add(PasswordText2);

		JTextField PasswordTipText2 = new JTextField();
		PasswordTipText2.setBounds((int)(width/4-290)/2+50, 185, 240, 27);
		PasswordTipText2.setText("两次密码必须一致");
		PasswordTipText2.setForeground(Color.GRAY);
		DownPanel.add(PasswordTipText2);
		PasswordTipText2.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				PasswordTipText2.setVisible(false);
				PasswordText2.setVisible(true);
				PasswordText2.requestFocusInWindow();
			}
		});
		PasswordText2.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				if (PasswordText2.getText().equals("两次密码必须一致"))
				{
					PasswordText2.setText("");
					PasswordText2.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if (PasswordText2.getText().equals(""))
				{
					PasswordTipText2.setText("两次密码必须一致");
					PasswordTipText2.setVisible(true);
					PasswordText2.setVisible(false);
				}
			}
		});
		// 错误标签
		Password2Error = new JLabel();
		Password2Error.setBounds((int)(width/4-200)/2, 210, 200, 30);
		f = new Font("楷体", Font.PLAIN, 13);
		Password2Error.setForeground(Color.RED);
		Password2Error.setFont(f);
		Password2Error.setText("");
		DownPanel.add(Password2Error);
	}

	private void AddConfirmButton()
	{
		// 确认按钮
		Confirm = new JButton("确认");
		Font f = new Font("黑体", Font.PLAIN, 16);
		Confirm.setForeground(new Color(0,160,233));
		Confirm.setBackground(new Color(236, 247, 255));
		Confirm.setFont(f);
		Confirm.setBounds((int)(width/4-290)/2, 245, 290, 40);
		//Confirm.setFocusable(false);
		Confirm.setEnabled(true);
		//Signup2 this_temp = this;
		Confirm.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				Username = NicknameText.getText();
				if (Username.isEmpty() || "2~8个汉字、字母或数字".equals(NicknameText.getText()))
				{
					NicknameError.setText("请填写昵称");
					return;
				}
				if (!cCheckTools.CheckUsername(Username))
				{
					NicknameError.setText("昵称格式不正确");
					return;
				} else
				{
					NicknameError.setText("");
				}
				
				// 检查密码
				if (PasswordText.getText().isEmpty() || "8~20个字母和数字组合".equals(PasswordText.getText()))
				{
					PasswordError.setText("请输入密码");
					return;
				}
				if (!cCheckTools.CheckPwdFormat(PasswordText.getText()))
				{
					PasswordError.setText("密码格式不正确");
					return;
				} else
				{
					PasswordError.setText("");
				}
				// 检查第二次输入的密码
				if (PasswordText2.getText().isEmpty())
				{
					Password2Error.setText("请再次输入密码");
					return;
				}
				if (cCheckTools.isSame(PasswordText.getText(),PasswordText2.getText()) == false)
				{
					System.out.println(PasswordText.getText() + " " + PasswordText2.getText());
					Password2Error.setText("两次密码不一致");
					return;
				} else
				{
					Password2Error.setText("");
				}
				String Pwd;
				try {
					Pwd = PasswordText.getText();
					String data = cPackMessage.ResetPwd(Username, Pwd);
					sClient client = new sClient();
					client.send(data);
					client.recv();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//发送注册数据
				//SendSignupData();
			}
		});
		DownPanel.add(Confirm);
	}
	
	
	
	public void AddDownPanel()
	{
		AddGirlImage();
		AddNickname();
		AddPassword();
		AddPassword2();
		AddConfirmButton();
	}
}
