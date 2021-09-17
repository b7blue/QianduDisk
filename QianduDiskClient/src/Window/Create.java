package Window;

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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import Socket.*;

import javax.swing.*;

import Socket.cPackMessage;
/*
 * Create为“创建群组”窗口
 */
public class Create extends JFrame implements KeyListener
{
	String Key = "2fh;mvok3;faojdjojf3n2";
	JCheckBox Agree = null;
	
	Container container = null;
	JPanel DownPanel = null;
	JTextField GroupText = null;
	JTextField GroupCodeText = null;
	public JLabel Error = null;
	JButton Confirm = null;
	String Username = null;
	JPasswordField PasswordText = new JPasswordField();
	Toolkit tk = Toolkit.getDefaultToolkit();
	Dimension ds = tk.getScreenSize();
	int width = ds.width;
	int hight = ds.height;

	public Create(String Username)
	{
		this.Username = Username;
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
		this.setTitle("千度安全网盘 - 创建组群");
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

	private void AddGroup()
	{
		// 组群标签
		JLabel GroupLabel = new JLabel();
		GroupLabel.setBounds((int)(width/4-290)/2, 105, 50, 30);
		Font f = new Font("楷体", Font.PLAIN, 16);
		GroupLabel.setFont(f);
		GroupLabel.setText("群组");
		DownPanel.add(GroupLabel);
		// 组群
		GroupText = new JTextField("请输入群组名称");
		GroupText.setBounds((int)(width/4-290)/2+50, 109, 240, 27);
		GroupText.setForeground(Color.GRAY);
		DownPanel.add(GroupText);
		GroupText.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				if (GroupText.getText().equals("请输入群组名称"))
				{
					GroupText.setText("");
					GroupText.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if (GroupText.getText().equals(""))
				{
					GroupText.setForeground(Color.GRAY);
					GroupText.setText("请输入群组名称");
				}
			}
		});
		//DownPanel.add(MailError);
	}
	
	private void AddGroupCode()
	{
		// 验证码标签
		JLabel GroupCodeLabel = new JLabel();
		GroupCodeLabel.setBounds((int)(width/4-290)/2, 150, 50, 30);
		Font f = new Font("楷体", Font.PLAIN, 16);
		GroupCodeLabel.setFont(f);
		GroupCodeLabel.setText("密码");
		DownPanel.add(GroupCodeLabel);
		// 邮箱验证码
		PasswordText = new JPasswordField();
		PasswordText.setBounds((int)(width/4-290)/2+50, 154, 240, 27);
		PasswordText.setText("");
		PasswordText.setForeground(Color.GRAY);
		PasswordText.setVisible(false);
		PasswordText.addKeyListener(this);
		DownPanel.add(PasswordText);
		GroupCodeText = new JTextField("请输入密码");
		GroupCodeText.setBounds((int)(width/4-290)/2+50, 154, 240, 27);
		GroupCodeText.setForeground(Color.GRAY);
		DownPanel.add(GroupCodeText);
		GroupCodeText.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				GroupCodeText.setVisible(false);
				PasswordText.setVisible(true);
				PasswordText.requestFocusInWindow();
			}

			
		});
		PasswordText.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				if (PasswordText.getText().equals("请输入密码") )
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
					GroupCodeText.setText("密码不能为空");
					GroupCodeText.setVisible(true);
					PasswordText.setVisible(false);
				}
			}
		});
	}


	private void AddErrorLabel()
	{
		// 错误标签
		Error = new JLabel();
		Error.setBounds((int)(width/4-200)/2, 180, 200, 30);
		Error.setForeground(new Color(252, 109, 123));
		Font f = new Font("黑体", Font.PLAIN, 13);
		Error.setFont(f);
		Error.setText("");
		DownPanel.add(Error);
	}
	
	private void AddConfirmButton()
	{
		// 确认按钮
		Confirm = new JButton("确认");
		Font f = new Font("黑体", Font.PLAIN, 16);
		Confirm.setForeground(new Color(0,160,233));
		Confirm.setBackground(new Color(236, 247, 255));
		Confirm.setFont(f);
		Confirm.setBounds((int)(width/4-290)/2, 230, 290, 40);
		//Confirm.setFocusable(false);
		Confirm.setEnabled(true);
		Confirm.requestFocusInWindow();
		Create this_temp = this;
		Confirm.addKeyListener(this);
		Confirm.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				try {
					String data = cPackMessage.NewGroup(Username, GroupText.getText(), PasswordText.getText());
					sClient client = new sClient();
					client. send(data);
					client.recv();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return;
				// 检查群组号格式是否正确
				/*
				// 检查群组号是否已经存在
				 发送群组数据并得到返回值，判断群组号是否存在
				if (组号已存在)
				{
					MailError.setText("该组号已创建，请重新输入！");
					return;
				} else
				{
					关闭该窗口（表示创建成功）
					数据库里添加该信息
				}
				*/
			}
		});
		DownPanel.add(Confirm);
	}
	
	public void AddDownPanel()
	{
		AddGirlImage();
		AddGroup();
		AddGroupCode();
		AddConfirmButton();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
