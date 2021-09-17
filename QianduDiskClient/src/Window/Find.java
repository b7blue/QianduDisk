package Window;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import Socket.cPackMessage;
import Socket.sClient;
import util.cCheckTools;

public class Find extends JFrame {
	
	String Key = "2fh;mvok3;faojdjojf3n2";
	JCheckBox Agree = null;
	
	Container container = null;
	JPanel DownPanel = null;
	JTextField MailText = null;
	JTextField MailCodeText = null;
	public JLabel MailError = null;
	public JLabel MailCodeError = null;
	JButton SendButton = null;
	JButton Confirm = null;
	public Signup2 signup2 = null;
	public String Email = null;
	Toolkit tk = Toolkit.getDefaultToolkit();
	Dimension ds = tk.getScreenSize();
	int width = ds.width;
	int hight = ds.height;
	public Find2 find2;

	public Find()
	{
		
		Init();
		AddDownPanel();		
		this.setVisible(true);
		this.setContentPane(DownPanel);
	}

	public void Init()
	{
		this.setSize(width/4,(int)hight/3);
		this.setLocation(width/4,hight/4);//设置窗体偏移、大小
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
		DownPanel.setSize(width/4,hight/2);
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

	private void AddMail()
	{
		// 邮箱标签
		JLabel MailLabel = new JLabel();
		MailLabel.setBounds((int)(width/4-290)/2, 105, 50, 30);
		Font f = new Font("楷体", Font.PLAIN, 16);
		MailLabel.setFont(f);
		MailLabel.setText("邮箱");
		DownPanel.add(MailLabel);
		// 邮箱
		MailText = new JTextField("请输入邮箱");
		MailText.setBounds((int)(width/4-290)/2+50, 109, 240, 27);
		MailText.setForeground(Color.GRAY);
		DownPanel.add(MailText);
		MailText.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				if (MailText.getText().equals("请输入邮箱"))
				{
					MailText.setText("");
					MailText.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if (MailText.getText().equals(""))
				{
					MailText.setForeground(Color.GRAY);
					MailText.setText("请输入邮箱");
				}
			}
		});
		// 错误标签
		MailError = new JLabel();
		MailError.setBounds((int)(width/4-200)/2, 180, 200, 30);
		f = new Font("楷体", Font.PLAIN, 13);
		MailError.setForeground(Color.RED);
		MailError.setFont(f);
		MailError.setText("");
		DownPanel.add(MailError);
	}
	
	private void AddMailCode()
	{
		// 验证码标签
		JLabel MailCodeLabel = new JLabel();
		MailCodeLabel.setBounds((int)(width/4-290)/2-10, 150, 60, 30);
		Font f = new Font("楷体", Font.PLAIN, 16);
		MailCodeLabel.setFont(f);
		MailCodeLabel.setText("验证码");
		DownPanel.add(MailCodeLabel);
		// 邮箱验证码
		MailCodeText = new JTextField("请输入邮箱验证码");
		MailCodeText.setBounds((int)(width/4-290)/2+50, 154, 240, 27);
		MailCodeText.setForeground(Color.GRAY);
		DownPanel.add(MailCodeText);
		MailCodeText.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				if (MailCodeText.getText().equals("请输入邮箱验证码"))
				{
					MailCodeText.setText("");
					MailCodeText.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if (MailCodeText.getText().equals(""))
				{
					MailCodeText.setForeground(Color.GRAY);
					MailCodeText.setText("请输入邮箱验证码");
				}
			}
		});
		// 错误标签
		MailCodeError = new JLabel();
		MailCodeError.setBounds((int)(width/4-200)/2, 180, 200, 30);
		f = new Font("楷体", Font.PLAIN, 13);
		MailCodeError.setForeground(Color.RED);
		MailCodeError.setFont(f);
		MailCodeError.setText("");
		DownPanel.add(MailCodeError);
	}

	private void AddSendButtonButton()
	{
		// 发送验证码按钮
		SendButton = new JButton("发送验证码");
		Font f = new Font("黑体", Font.PLAIN, 16);
		SendButton.setForeground(new Color(0,160,233));
		SendButton.setBackground(new Color(236, 247, 255));
		SendButton.setFont(f);
		SendButton.setBounds((int)(width/4-290)/2, 220, 140, 40);
		//SendButton.setFocusable(false);
		SendButton.setEnabled(true);
		//Signup this_temp = this;
		SendButton.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				// 检查邮箱格式是否正确
				if (MailText.getText().isEmpty() || "请输入邮箱".equals(MailText.getText()))
				{
					MailError.setText("请输入邮箱");
					return;
				}
				Email = MailText.getText();
				if (!cCheckTools.CheckEmailFormat(Email))
				{
					MailError.setText("邮箱格式不正确");
					return;
				} else
				{
					MailError.setText("");
				}
				//请求服务器向所填写的邮箱发送验证码
				/*
				// 检查邮箱是否已经存在
				 发送邮箱数据并得到返回值，判断邮箱已存在
				if (邮箱已存在)
				{
					MailError.setText("该邮箱已注册，请重新输入！");
					return;
				} else
				{
					MailError.setText("验证码已发送，请输入验证码");
				}
				*/
				String data = cPackMessage.GetVcode(Email,"ForgetPwd");
				sClient client;
				try {
					client = new sClient();
					client.send(data);
					client.recv();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				
			}
		});
		DownPanel.add(SendButton);
	}

	
	private void AddConfirmButton()
	{
		//确认验证码按钮
		Confirm = new JButton("确认");
		Font f = new Font("黑体", Font.PLAIN, 16);
		Confirm.setForeground(new Color(0,160,233));
		Confirm.setBackground(new Color(236, 247, 255));
		Confirm.setFont(f);
		Confirm.setBounds((int)(width/4)/2+5, 220, 140, 40);
		//Confirm.setFocusable(false);
		Confirm.setEnabled(true);
		//Signup this_temp = this;
		Confirm.addMouseListener(new MouseAdapter()
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
				String vcode = MailCodeText.getText();
				String data = cPackMessage.CheckVcode(vcode,"ForgetPwd");
				try {
					sClient client = new sClient();
					client.send(data);
					client.recv();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//signup2 = new Signup2();
			}
			/*
			public void mouseClicked(MouseEvent e)
			{
				//return;
				/*
				// 检查邮箱是否已经存在
				 发送验证码数据并得到返回值，判断验证码是否正确
				MailError.setText("");
				if (验证码错误)
				{
					MailCodeError.setText("验证码错误，请重新输入！");
					return;										
				} else
				{
					MailCodeError.setText("");
				}
				//发送注册数据
				SendButtonSignupData();
				调用下一个类，即跳转到下一个界面 即signup2 = new Signup2();
								
			}
			*/
		});
		DownPanel.add(Confirm);
	}
	
	public void AddDownPanel()
	{
		AddGirlImage();
		AddMail();
		AddMailCode();
		AddSendButtonButton();
		AddConfirmButton();
	}
}	

