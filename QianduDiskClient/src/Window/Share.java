package Window;

import util.Vcode;

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

public class Share extends JFrame
{
	String Key = "2fh;mvok3;faojdjojf3n2";
	JCheckBox Agree = null;	
	Container container = null;
	JPanel DownPanel = null;
	JButton JoinButton = null;
	JButton CreateButton = null;
	public Join join = null;
	public Create create = null;
	Toolkit tk = Toolkit.getDefaultToolkit();
	Dimension ds = tk.getScreenSize();
	String Username = null;
	public String Groupname = null;
	int width = ds.width;
	int hight = ds.height;

	public Share(String Username)
	{
		this.Username = Username;
		Init();
		AddDownPanel();		
		this.setVisible(true);
		this.setContentPane(DownPanel);
	}

	public void Init()
	{
		this.setSize(width/4,(int)hight/4);
		this.setLocation(width/4,hight/4);//设置窗体偏移、大小
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);//隐藏并释放窗体，dispose()，当最后一个窗口被释放后，则程序也随之运行结束。 
		this.setTitle("千度安全网盘 - 注册");
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

	private void Join()
	{
		// 加入群组按钮
		JoinButton = new JButton("加入群组");
		Font f = new Font("黑体", Font.PLAIN, 16);
		JoinButton.setForeground(new Color(0,160,233));
		JoinButton.setBackground(new Color(236, 247, 255));
		JoinButton.setFont(f);
		JoinButton.setBounds((int)(width/4-290)/2, 120, 140, 40);
		//Send.setFocusable(false);
		JoinButton.setEnabled(true);
		Share this_temp = this;
		JoinButton.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				
				join = new Join(Username);
			}
		});
		DownPanel.add(JoinButton);
	}
	
	private void Create()
	{
		// 创建群组按钮
		CreateButton = new JButton("创建群组");
		Font f = new Font("黑体", Font.PLAIN, 16);
		CreateButton.setForeground(new Color(0,160,233));
		CreateButton.setBackground(new Color(236, 247, 255));
		CreateButton.setFont(f);
		CreateButton.setBounds((int)(width/4)/2+5, 120, 140, 40);
		//Confirm.setFocusable(false);
		CreateButton.setEnabled(true);
		Share this_temp = this;
		CreateButton.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{				
				create = new Create(Username);		
			}
		});
		DownPanel.add(CreateButton);
	}
	
	public void AddDownPanel()
	{
		AddGirlImage();
		Join();
		Create();
	}
}
