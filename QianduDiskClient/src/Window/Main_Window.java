package Window;
//import util.ShareWindow;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import Socket.*;
import util.*;

public class Main_Window extends JFrame
{
	public Container container = null;
	public JPanel UpPanel = null;
	public JPanel RoutePane = null;
	public JPanel TableHeadPane = null;
	public JPanel TablePane = null;
	public JPanel DownPane = null;
	public JPanel TranListPane = null;
	public JPanel DownloadPane = null;
	public JPanel UploadPane = null;
	public JPanel DonePane = null;
	public JTabbedPane TabbedPane = null;
	public JScrollPane ScrollPane = null;
	public JTextField RouteText = null;
	public JTextField SearchText = null;
	public JLabel Nickname = null;
	public JButton BackButton = null;
	public JPanel MyFile = null;
	public Share share = null;
	public JPanel ShareFile = null;
	public JPanel Download = null;
	public JPanel Share = null;
	public JPanel Delete = null;
	public JPanel NewFolder = null;
	public JCheckBox AllSelectBox = null;
	public JButton FilenameButton = null;
	public JButton FileSizeButton = null;
	public JButton FileModTimeButton = null;
	public JButton TranList = null;
	public ArrayList<FileView> FileList = new ArrayList<>();
	public ArrayList<UploadView> UploadList = new ArrayList<>();
	public ArrayList<DownloadView> DownloadList = new ArrayList<>();
	public ArrayList<DoneView> DoneList = new ArrayList<>();
	public Point origin = new Point();
	public String DefaultDownloadRoute = "g:\\OnlineDiskLadyDownload";
	public boolean IsTranListOpened = false;
	private int CapacityPercent = 0;
	private String Username = null;
	public String Groupname = null;
	private static String Locate = null;
	private String Route = "";
	private int UploadNum = 0;
	private int DownloadNum = 0;
	private int DoneNum = 0;
	String data = null;
	Toolkit tk = Toolkit.getDefaultToolkit();
	public static Main_Window mainwindow = null;

	public Main_Window(String Username, String Groupname)// 主界面
	{
		Init();
		AddTitle();
		AddTable();
		AddRouteText();
		AddBackButton();
		AddMyDisk();
		AddShareFile();
		AddUpload();
		AddDownload();
		AddDelete();
		AddNewFolder();
		AddTranList();
		AddTabbedPane();
		AddMyFile();		
		
		this.Username = Username;
		this.Locate = Username;
		this.Groupname = Groupname;
		// 显示
		this.setVisible(true);
		mainwindow = this;
	}

	private void Init()
	{
		// 初始化主界面
		this.setBounds(250, 80, 940, 553);// 大小位置
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// 关闭设置
		this.setResizable(false);// 不可改变大小
		this.setTitle("千度安全网盘");
		Image img = tk.getImage("网盘.png");
		setIconImage(img);
		this.setUndecorated(false);
		container = this.getContentPane();// 容器
		container.setLayout(null);
		container.setBackground(Color.WHITE);
		UpPanel = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(Color.BLACK);
			}
		};
		ImageIcon ico = new ImageIcon("网盘.png");
		Image temp = ico.getImage().getScaledInstance(120, 120, ico.getImage().SCALE_DEFAULT);
		ico = new ImageIcon(temp);
		JLabel Image = new JLabel(ico);
		Image.setBounds(0, 0, 120, 120);
		UpPanel.add(Image);
		UpPanel.setLayout(null);
		UpPanel.setBounds(0, 0, 120, 553);
		UpPanel.setBackground(new Color(245,245,245));
		UpPanel.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				origin.x = e.getX();
				origin.y = e.getY();
			}
		});
		Main_Window this_temp = this;
		UpPanel.addMouseMotionListener(new MouseMotionAdapter()
		{
			public void mouseDragged(MouseEvent e)
			{
				Point p = this_temp.getLocation();
				this_temp.setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
			}
		});
		container.add(UpPanel);

		RoutePane = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(Color.GRAY);
				g.drawLine(90, 0, 90, 45);
				g.drawLine(938, 0, 938, 45);
			}
		};
		RoutePane.setBounds(120, 0, 820, 95);
		RoutePane.setLayout(null);
		RoutePane.setBackground(Color.WHITE);
		container.add(RoutePane);

		DownPane = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(Color.GRAY);
			}
		};
		DownPane.setBounds(120, 552, 940, 1);
		DownPane.setLayout(null);
		DownPane.setBackground(Color.BLACK);
		container.add(DownPane);

		TabbedPane = new JTabbedPane(JTabbedPane.TOP)//传输列表
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(Color.WHITE);
				g.drawLine(0, 0, 0, 940);
				g.drawLine(0, 0, 0, 10000);
				g.drawLine(938, 0, 938, 10000);
			}
		};
		TabbedPane.setBounds(120, 0, 820, 553);
		TabbedPane.setBackground(Color.WHITE);
		TabbedPane.setVisible(false);
		container.add(TabbedPane);
	}

	private void AddTitle()
	{
		// 标题
		JLabel Title = new JLabel();
		Font f = new Font("楷体", Font.BOLD, 13);
		Title.setFont(f);
		Title.setBounds(40, 4, 50, 25);
		Title.setForeground(Color.BLACK);
		UpPanel.add(Title);
	}

	private void AddTable()
	{
		// 添加表头
		TableHeadPane = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(Color.GRAY);
				g.drawLine(0, 4, 1000, 4);
				g.drawLine(0, 36, 1000, 36);
			}
		};
		TableHeadPane.setBounds(120, 95, 820, 40);
		TableHeadPane.setLayout(null);
		TableHeadPane.setBackground(Color.WHITE);
		// 添加全选框
		AllSelectBox = new JCheckBox();
		AllSelectBox.setBounds(35, 11, 17, 17);
		AllSelectBox.setBackground(Color.WHITE);
		AllSelectBox.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg0)
			{
				// 判断是否全选
				boolean IsAllSelected = true;
				for (int i = 0; i < FileList.size(); i++)
				{
					if (FileList.get(i).checkbox.isSelected() == false)
					{
						IsAllSelected = false;
						break;
					}
				}
				if (IsAllSelected == true)// 如果全选，设置成全不选状态
				{
					for (int i = 0; i < FileList.size(); i++)
					{
						FileList.get(i).checkbox.setSelected(false);
					}
				} else// 如果有没选的，设置成全选
				{
					for (int i = 0; i < FileList.size(); i++)
					{
						FileList.get(i).checkbox.setSelected(true);
					}
				}
			}
		});
		TableHeadPane.add(AllSelectBox);
		// 添加“文件名”按钮
		FilenameButton = new JButton("文件名");
		Font f = new Font("微软雅黑", Font.PLAIN, 12);
		FilenameButton.setFont(f);
		FilenameButton.setBounds(55, 5, 80, 30);
		FilenameButton.setForeground(Color.BLACK);
		FilenameButton.setBackground(Color.WHITE);
		FilenameButton.setBorder(null);
		FilenameButton.setFocusPainted(false);
		TableHeadPane.add(FilenameButton);
		// 添加“大小”按钮
		FileSizeButton = new JButton("大小");
		FileSizeButton.setFont(f);
		FileSizeButton.setBounds(370, 5, 80, 30);
		FileSizeButton.setForeground(Color.BLACK);
		FileSizeButton.setBackground(Color.WHITE);
		FileSizeButton.setBorder(null);
		FileSizeButton.setFocusPainted(false);
		TableHeadPane.add(FileSizeButton);
		// 添加“修改时间”按钮
		FileModTimeButton = new JButton("修改时间");
		FileModTimeButton.setFont(f);
		FileModTimeButton.setBounds(630, 5, 80, 30);
		FileModTimeButton.setForeground(Color.BLACK);
		FileModTimeButton.setBackground(Color.WHITE);
		FileModTimeButton.setBorder(null);
		FileModTimeButton.setFocusPainted(false);
		TableHeadPane.add(FileModTimeButton);
		// 设置数据表格
		TablePane = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(Color.GRAY);
			}
		};
		;
		TablePane.setBounds(0, 0, 100, 100);
		TablePane.setPreferredSize(new Dimension(100, 100));
		TablePane.setBackground(Color.WHITE);
		TablePane.setLayout(null);
		ScrollPane = new JScrollPane(TablePane);
		ScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		ScrollPane.setBorder(null);
		ScrollPane.getVerticalScrollBar().setUnitIncrement(10);
		ScrollPane.setBounds(120, 145, 820, 408);
		container.add(ScrollPane);
		container.add(TableHeadPane);
	}

	private void AddRouteText()
	{
		// 添加路径显示条
		RouteText = new JTextField();
		RouteText.setBounds(100, 10, 430, 30);
		RouteText.setEditable(false);
		RouteText.setBackground(Color.WHITE);
		RouteText.setText("我的网盘 → ");
		RoutePane.add(RouteText);
	}

	private void AddBackButton()
	{
		// 后退按钮		
		BackButton = new JButton();
		BackButton.setText("←");
		BackButton.setFont(new Font("黑体", Font.PLAIN, 18));
		BackButton.setBounds(10, 10,60, 24);
		BackButton.setBackground(Color.WHITE);
		BackButton.setBorderPainted(false);
		BackButton.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (Route.isEmpty() || Route == null)
					return;
				int i;
				for (i = Route.length() - 1; i >= 0; i--)
				{
					if (Route.charAt(i) == '\\')
						break;
				}
				Route = Route.substring(0, i);
//				new Thread(new Ask_File_List(Username, Route)).start();
//				向服务器发请求获取文件列表
				try {
					sClient client = new sClient();
					client.send(cPackMessage.GetFileList(Username,""));
					client.recv();
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				String Route2 = Route.replace("\\", " → ");
				RouteText.setText("我的网盘" + Route2);
			}
		});
		RoutePane.add(BackButton);
	}

//	显示我的文件，改了
//	add filelist收到结果之后调用
//	但是client的recv到filelist接下来咋办还没写
//	写了但是不知道行不行
	private void AddMyFile()
	{		
		MyFile = new JPanel();
		MyFile.setLayout(new FlowLayout());
		MyFile.setBackground(new Color(245,245,245));
		JLabel Label = new JLabel("我的文件");
		Font f = new Font("微软雅黑", Font.BOLD, 13);
		Label.setFont(f);
		Label.setBounds(60,0, 60, 20);
		Label.setForeground(new Color(0,0,0));
		MyFile.add(Label);
		MyFile.setBounds(0, 182, 120, 35);
		UpPanel.add(MyFile);
		MyFile.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent e)
			{
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			public void mousePressed(MouseEvent e)
			{
				MyFile.setLocation(0, 182);
				MyFile.setBackground(new Color(218,165,32));
			}
			public void mouseReleased(MouseEvent e)
			{
				MyFile.setLocation(0, 180);
				MyFile.setBackground(new Color(245,245,245));
			}
			
			public void mouseClicked(MouseEvent e)
			{
				Locate = Username;
				System.out.println("我的文件"+Locate);
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				Route = "";
				
//				向服务器发请求
				try {
					sClient client = new sClient();
					client.send(cPackMessage.GetFileList(Username,""));
					client.recv();
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
//				new Thread(new Ask_File_List(Username, Route)).start();
				
				RouteText.setText("我的网盘 → ");

				RoutePane.setVisible(true);
				ScrollPane.setVisible(true);
				TableHeadPane.setVisible(true);
				TablePane.setVisible(true);
				TranList.removeAll();
				TabbedPane.setVisible(false);
				IsTranListOpened = false;
			}
		});
	}
	
	private void AddShareFile()
	{	
		ShareFile = new JPanel();
		ShareFile.setLayout(new FlowLayout());
		ShareFile.setBackground(new Color(245,245,245));
		JLabel Label = new JLabel("共享文件");
		Font f = new Font("微软雅黑", Font.BOLD, 13);
		Label.setFont(f);
		Label.setBounds(18, 53, 60, 20);
		Label.setForeground(new Color(0,0,0));
		ShareFile.add(Label);
		ShareFile.setBounds(0, 240, 120, 35);
		UpPanel.add(ShareFile);
		ShareFile.addMouseListener(new MouseAdapter()
		{
			

			public void mouseEntered(MouseEvent e)
			{
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent e)
			{
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			public void mousePressed(MouseEvent e)
			{
				ShareFile.setLocation(0, 240);
				ShareFile.setBackground(new Color(218,165,32));
			}
			public void mouseReleased(MouseEvent e)
			{
				ShareFile.setLocation(0, 240);
				ShareFile.setBackground(new Color(245,245,245));
			}
			
			public void mouseClicked(MouseEvent e)
			{
				Locate = Groupname;
				System.out.println("共享文件"+Locate);
				try {
					
					if(Groupname.equals("-")) {
						mainwindow.share = new Share(Username);
					}
					else {
						sClient client = new sClient();
						client.send(cPackMessage.GetFileList(Groupname,""));
						client.recv();
					}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
	}
	
//	
	private void AddMyDisk()
	{
		JButton MydiskButton = new JButton();
		MydiskButton = new JButton();
		MydiskButton.setForeground(new Color(255,255,255));
		MydiskButton.setBounds(10, 55, 120, 30);
		MydiskButton.setBackground(new Color(70, 81, 97));
		Font f = new Font("黑体", Font.PLAIN, 13);
		MydiskButton.setFont(f);
		MydiskButton.setText("我的网盘");
		MydiskButton.setFocusable(false);
		MydiskButton.addMouseListener(new MouseAdapter()
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
				Locate = Username;
				System.out.println("我的文件"+Locate);
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				Route = "";
				
//				向服务器发请求
				try {
					sClient client = new sClient();
					client.send(cPackMessage.GetFileList(Username,""));
					client.recv();
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				
				
				RouteText.setText("我的网盘 → ");
	
				RoutePane.setVisible(true);
				ScrollPane.setVisible(true);
				TableHeadPane.setVisible(true);
				TablePane.setVisible(true);
				TranList.removeAll();
				TabbedPane.setVisible(false);
				IsTranListOpened = false;
			}
		});
		RoutePane.add(MydiskButton);
	}
			
//	上传按钮，改过了，还没改完
	private void AddUpload()
	{
		JButton UploadButton = new JButton();
		UploadButton = new JButton();
		UploadButton.setForeground(new Color(255,255,255));
		UploadButton.setBounds(180, 55, 120, 30);
		UploadButton.setBackground(new Color(101,168,66));
		Font f = new Font("黑体", Font.PLAIN, 13);
		UploadButton.setFont(f);
		UploadButton.setText("上传文件");
		UploadButton.setFocusable(false);
		UploadButton.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent e)
			{
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			public void mouseClicked(MouseEvent arg0)
			{
				String[] FileRoute = SelectFile();
				if(FileRoute == null || FileRoute.length == 0)
					return;
				for (String s : FileRoute)
				{
					if (s == null || s.isEmpty())
						return;
					try {
						sClient Client = new sClient();
						System.out.println("上传文件"+Locate);
						Client.sendFile(Locate,new File(s));
					} catch (Exception e) {
						e.printStackTrace();
					}
					
//					String RemoteRoute = Route + "\\" + new File(s).getName();
//					new Thread(new Upload(Username, s, RemoteRoute)).start();
//					try
//					{
//						Thread.sleep(100);
//					} catch (InterruptedException e)
//					{
//					}
				}
			}
		});
		RoutePane.add(UploadButton);
	}

//	下载按钮，改过了,下载之后呢？
	private void AddDownload()
	{
		JButton DownloadButton = new JButton();
		DownloadButton = new JButton();
		DownloadButton.setForeground(new Color(255,255,255));
		DownloadButton.setBounds(10+170*2, 55, 120, 30);
		DownloadButton.setBackground(new Color(116,66,144));
		Font f = new Font("黑体", Font.PLAIN, 13);
		DownloadButton.setFont(f);
		DownloadButton.setText("下载文件");
		DownloadButton.setFocusable(false);
		DownloadButton.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent e)
			{
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			public void mouseClicked(MouseEvent e) {
				ArrayList<FileView> selectfile = GetSelectFile();// 获取选择的文件列表
				if (selectfile.isEmpty()) {
					return;
				}
				for (int i = 0; i < selectfile.size(); i++) {
					try {
						sClient Client = new sClient();
						HashMap<String, String> data = new HashMap<String, String>();
						data.put("Username", Locate);
						data.put("Filename", selectfile.get(i).Filename.getText());
						data.put("Command", "Download");
						Client.send(MapString.MapToString(data));
						Client.recv();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
//					new Thread(new Download(Username, selectfile.get(i).Filename.getText(),
//							selectfile.get(i).FileSize.getText(), Route)).start();
				}
			}
		});
		RoutePane.add(DownloadButton);
	}
	
//	删除文件，还没改
	private void AddDelete()
	{
		JButton DeleteButton = new JButton();
		DeleteButton = new JButton();
		DeleteButton.setForeground(new Color(255,255,255));
		DeleteButton.setBounds(10+170*3, 55, 120, 30);
		DeleteButton.setBackground(new Color(233,143,54));
		Font f = new Font("黑体", Font.PLAIN, 13);
		DeleteButton.setFont(f);
		DeleteButton.setText("删除文件");
		DeleteButton.setFocusable(false);
		DeleteButton.addMouseListener(new MouseAdapter()
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
				ArrayList<FileView> selectfile = GetSelectFile();
				for (int i = 0; i < selectfile.size(); i++)
				{
					
					sClient client;
					try {
						client = new sClient();
						HashMap<String,String> data = new HashMap<String,String>();
						data.put("Name", Locate);
						data.put("Filename", selectfile.get(i).Filename.getText());
						data.put("Command", "DeleteFile");
						client.send(MapString.MapToString(data));
						client.recv();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
//					if (i == selectfile.size() - 1)
//						new Thread(new DeleteFile(Username, Route + "\\" + selectfile.get(i).Filename.getText(), true))
//								.start();
//					else
//						new Thread(new DeleteFile(Username, Route + "\\" + selectfile.get(i).Filename.getText(), false))
//								.start();
				}
				AllSelectBox.setSelected(false);
//				try
//				{
//					Thread.sleep(1000);
//					new Thread(new Ask_File_List(Username, Route)).start();
//				} catch (InterruptedException e1)
//				{
//				}
			}
		});
		RoutePane.add(DeleteButton);
	}

	private void AddNewFolder()
	{
		JButton NewFolderButton = new JButton();
		NewFolderButton = new JButton();
		NewFolderButton.setForeground(new Color(255,255,255));
		NewFolderButton.setBounds(10+170*4, 55, 120, 30);
		NewFolderButton.setBackground(new Color(186,77,63));
		Font f = new Font("黑体", Font.PLAIN, 13);
		NewFolderButton.setFont(f);
		NewFolderButton.setText("新建文件夹");
		NewFolderButton.setFocusable(false);
		NewFolderButton.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent e)
			{
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			public void mouseClicked(MouseEvent arg0)
			{
				NewFolder();
			}
		});
		RoutePane.add(NewFolderButton);
	}

	//新建文件夹功能
	private void NewFolder()
	{
		// 创建移动点
		Point origin = new Point();
		// 创建对话框
		JDialog Dialog = new JDialog();
		Dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Container container = Dialog.getContentPane();
		container.setLayout(null);
		Dialog.setBounds(600, 250, 210, 110);
		Dialog.setUndecorated(true);
		// 创建面板
		JPanel Panel = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(Color.GRAY);
				g.drawRect(0, 0, 209, 109);
			}
		};
		Panel.setBounds(0, 0, 210, 110);
		Panel.setLayout(null);
		container.add(Panel);
		Panel.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				origin.x = e.getX();
				origin.y = e.getY();
			}
		});
		Panel.addMouseMotionListener(new MouseMotionAdapter()
		{
			public void mouseDragged(MouseEvent e)
			{
				Point p = Dialog.getLocation();
				Dialog.setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
			}
		});

		Panel.setBackground(Color.WHITE);

		// 文件夹名提示标签
		JLabel tip = new JLabel("文件夹名");
		tip.setBounds(20, 0, 200, 30);
		Panel.add(tip);
		// 文件夹名输入框
		JTextField Foldername = new JTextField("新建文件夹");
		Foldername.setBounds(20, 30, 170, 27);
		Panel.add(Foldername);
		Foldername.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent arg0)
			{
//				if (arg0.getKeyChar() == '\n')
//				{
//					if (CheckFoldername(Foldername.getText()) == true)
//					{
//						new Thread(new Create_New_Folder(Foldername.getText(), Username, Route)).start();
//						Dialog.dispose();
//					} else
//					{
//						tip.setForeground(Color.RED);
//						tip.setText("文件夹名称不合法或重复");
//					}
//				}
			}
		});
		// 确定按钮
		JButton yes = new JButton("确定");
		yes.setBackground(new Color(245,245,220));
		yes.setBounds(30, 70, 70, 30);
		Panel.add(yes);
		yes.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg0)
			{
//				if (CheckFoldername(Foldername.getText()) == true)
//				{
//					new Thread(new Create_New_Folder(Foldername.getText(), Username, Route)).start();
//					Dialog.dispose();
//				} else
//				{
//					tip.setForeground(Color.RED);
//					tip.setText("文件夹名称不合法或重复");
//				}
			}
		});
		// 取消按钮
		JButton no = new JButton("取消");
		no.setBackground(new Color(245,245,220));
		no.setBounds(110, 70, 70, 30);
		Panel.add(no);
		Dialog.setVisible(true);
		no.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg0)
			{
				Dialog.dispose();
			}
		});
	}
	
	

//	传输列表
	private void AddTranList()
	{
		TranList = new JButton();
		TranList.setLayout(new FlowLayout());
		TranList.setBorderPainted(false);
		TranList.setBounds(0, 480, 120, 35);
		TranList.setText("传输列表");		
		UpPanel.add(TranList);
		TranList.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg0)
			{
				RoutePane.setVisible(IsTranListOpened);
				ScrollPane.setVisible(IsTranListOpened);
				TableHeadPane.setVisible(IsTranListOpened);
				TablePane.setVisible(IsTranListOpened);
				TranList.removeAll();
				if (IsTranListOpened == true)
				{
					TranList.setBounds(0, 480, 120, 35);
					TranList.setText("传输列表");
					TabbedPane.setVisible(false);
				} else
				{
					TranList.setBounds(0, 480, 120, 35);
					TranList.setText("传输列表");;
					TabbedPane.setVisible(true);
				}
				IsTranListOpened = !IsTranListOpened;
			}

			public void mousePressed(MouseEvent arg0)
			{
				TranList.removeAll();
				if (IsTranListOpened)
					TranList.setText("传输列表");
				else
					TranList.setText("传输列表");
			}

			public void mouseReleased(MouseEvent arg0)
			{
				TranList.removeAll();
				if (IsTranListOpened)
					TranList.setText("传输列表");
				else
					TranList.setText("传输列表");
			}
		});
	}

	private void AddTabbedPane()
	{
		AddUploading();
		AddDownloading();
		AddDone();
		Font f = new Font("微软雅黑", Font.PLAIN, 13);
		TabbedPane.setFont(f);

		JLabel Uploading = new JLabel(CreateLongName("上传中", 30), JLabel.CENTER);
		Uploading.setBackground(new Color(242, 246, 250));
		Uploading.setFont(f);
		TabbedPane.setTabComponentAt(0, Uploading);

		JLabel Downloading = new JLabel(CreateLongName("下载中", 30), JLabel.CENTER);
		Downloading.setBackground(new Color(242, 246, 250));
		Downloading.setFont(f);
		TabbedPane.setTabComponentAt(1, Downloading);

		JLabel Done = new JLabel(CreateLongName("已完成", 29), JLabel.CENTER);
		Done.setBackground(new Color(242, 246, 250));
		Done.setFont(f);
		TabbedPane.setTabComponentAt(2, Done);
	}

	private String CreateLongName(String name, int width)
	{
		String result = "";
		for (int i = 0; i < width; i++)
			result = result + " ";
		result = result + name;
		for (int i = 0; i < width; i++)
			result = result + " ";
		return result;
	}

	private void AddDownloading()
	{
		DownloadPane = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.drawLine(936, 0, 936, 10000);
			}
		};
		DownloadPane.setBounds(0, 0, 100, 100);
		DownloadPane.setPreferredSize(new Dimension(100, 100));
		DownloadPane.setBackground(Color.WHITE);
		DownloadPane.setLayout(null);
		JScrollPane ScrollPane = new JScrollPane(DownloadPane);
		ScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		ScrollPane.setBorder(null);
		ScrollPane.getVerticalScrollBar().setUnitIncrement(10);
		ScrollPane.setBounds(0, 190, 820, 360);
		TabbedPane.add("正在下载 ", ScrollPane);
	}

	private void AddUploading()
	{
		UploadPane = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.drawLine(936, 0, 936, 10000);
			}
		};
		UploadPane.setBounds(0, 0, 100, 100);
		UploadPane.setPreferredSize(new Dimension(100, 100));
		UploadPane.setBackground(Color.WHITE);
		UploadPane.setLayout(null);
		JScrollPane ScrollPane = new JScrollPane(UploadPane);
		ScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		ScrollPane.setBorder(null);
		ScrollPane.getVerticalScrollBar().setUnitIncrement(10);
		ScrollPane.setBounds(0, 190, 940, 360);
		TabbedPane.add("正在上传", ScrollPane);
	}

	/*
	 * 以下是实例化按钮用到的实现方法
	 */
	private void AddDone()
	{
		DonePane = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.drawLine(936, 0, 936, 10000);
			}
		};
		DonePane.setBounds(0, 0, 100, 100);
		DonePane.setPreferredSize(new Dimension(100, 100));
		DonePane.setBackground(Color.WHITE);
		DonePane.setLayout(null);
		JScrollPane ScrollPane = new JScrollPane(DonePane);
		ScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		ScrollPane.setBorder(null);
		ScrollPane.getVerticalScrollBar().setUnitIncrement(10);
		ScrollPane.setBounds(0, 190, 910, 360);
		TabbedPane.add("传输完成", ScrollPane);
	}

//	下载进度条
	public JProgressBar AddOneDownloadFile(String Filename, String Size)
	{
		DownloadNum = DownloadList.size();
		// 面板
		JPanel panel = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(new Color(153, 153, 153));
				g.drawLine(0, 0, 940, 0);
				g.drawLine(0, 69, 940, 69);
			}
		};
		panel.setLayout(null);
		panel.setBounds(2, -57 + (DownloadNum + 1) * 69, 920, 70);
		panel.setBackground(Color.WHITE);
		DownloadPane.add(panel);
		// 文件名标签
		JLabel FilenameLabel = new JLabel(Filename);
		Font f = new Font("微软雅黑", Font.BOLD, 16);
		FilenameLabel.setFont(f);
		FilenameLabel.setBounds(38, 10, 300, 30);
		panel.add(FilenameLabel);
		// 文件大小标签
		JLabel FileSize = new JLabel(Size);
		FileSize.setBounds(48, 40, 300, 30);
		panel.add(FileSize);
		// 进度条
		JProgressBar bar = new JProgressBar();
		bar.setBounds(400, 12, 200, 20);
		bar.setMinimum(0);
		bar.setMaximum(100);
		bar.setValue(0);
		bar.setStringPainted(true);
		bar.setForeground(new Color(61, 149, 215));
		bar.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				if (bar.getValue() == bar.getMaximum())
				{
					RemoveOneDownloadFile(Filename);
					AddOneDoneFile(Filename, Size);
				}
			}
		});
		panel.add(bar);
		DownloadPane.updateUI();
		if (76 + DownloadNum * 69 > DownloadPane.getHeight())
		{
			DownloadPane.setPreferredSize(new Dimension(100, 69 + DownloadNum * 75));
		}
		// 加入到列表
		DownloadView downloadview = new DownloadView();
		downloadview.Filename = FilenameLabel;
		downloadview.FileSize = FileSize;
		downloadview.bar = bar;
		downloadview.panel = panel;
		DownloadList.add(downloadview);
		return bar;
	}

//	上传进度条
	public JProgressBar AddOneUploadFile(String Filename, String Size)
	{
		UploadNum = UploadList.size();
		// 面板
		JPanel panel = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(Color.GRAY);
				g.drawLine(0, 0, 940, 0);
				g.drawLine(0, 69, 940, 69);
			}
		};
		panel.setLayout(null);
		panel.setBounds(2, -60 + (UploadNum + 1) * 69, 920, 70);
		panel.setBackground(Color.WHITE);
		UploadPane.add(panel);
		// 文件名标签
		JLabel FilenameLabel = new JLabel(Filename);
		Font f = new Font("微软雅黑", Font.BOLD, 14);
		FilenameLabel.setFont(f);
		FilenameLabel.setBounds(38, 10, 300, 30);
		panel.add(FilenameLabel);
		// 文件大小标签
		JLabel FileSize = new JLabel(Size);
		FileSize.setBounds(48, 40, 300, 30);
		panel.add(FileSize);
		// 进度条
		JProgressBar bar = new JProgressBar();
		bar.setBounds(400, 22, 200, 20);
		bar.setMinimum(0);
		bar.setMaximum(100);
		bar.setValue(0);
		bar.setStringPainted(true);
		bar.setForeground(new Color(61, 149, 215));
		bar.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				if (bar.getValue() == bar.getMaximum())
				{
					RemoveOneUploadFile(Filename);
					AddOneDoneFile(Filename, Size);
				}
			}
		});
		panel.add(bar);
		
		if (76 + UploadNum * 69 > UploadPane.getHeight())
		{
			UploadPane.setPreferredSize(new Dimension(100, 69 + UploadNum * 75));
		}
		//暂停标签
		ImageIcon ico1 = new ImageIcon("暂停.png");
		Image temp1 = ico1.getImage().getScaledInstance(17, 17, ico1.getImage().SCALE_DEFAULT);
		ico1 = new ImageIcon(temp1);
		JLabel Stop = new JLabel(ico1);
		Stop.setBounds(700, 15, 17, 17);
		Stop.setVisible(true);
		panel.add(Stop);
		//继续标签				
		ImageIcon ico2 = new ImageIcon("继续.png");
		Image temp2 = ico2.getImage().getScaledInstance(17, 17, ico2.getImage().SCALE_DEFAULT);
		ico2 = new ImageIcon(temp2);
		JLabel Continue = new JLabel(ico2);
		Continue.setBounds(700, 24, 17, 17);
		Continue.setVisible(false);
		panel.add(Continue);
		UploadPane.updateUI();
		
		Stop.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				Stop.setVisible(!Stop.isVisible());
				Continue.setVisible(!Continue.isVisible());
			}
		});
		
		Continue.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				Stop.setVisible(!Stop.isVisible());
				Continue.setVisible(!Continue.isVisible());
			}
		});
		
		// 加入到列表
		UploadView uploadview = new UploadView();
		uploadview.panel = panel;
		uploadview.Filename = FilenameLabel;
		uploadview.FileSize = FileSize;
		uploadview.bar = bar;
		UploadList.add(uploadview);
		return bar;
	}

	public void AddOneDoneFile(String Filename, String Size)
	{
		DoneNum = DoneList.size();
		// 面板
		JPanel panel = new JPanel()
		{
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(new Color(153, 153, 153));
				g.drawLine(0, 0, 940, 0);
				g.drawLine(0, 69, 940, 69);
			}
		};
		panel.setLayout(null);
		panel.setBounds(2, -57 + (DoneNum + 1) * 69, 920, 70);
		panel.setBackground(Color.WHITE);
		DonePane.add(panel);
		// 文件名标签
		JLabel FilenameLabel = new JLabel(Filename);
		Font f = new Font("微软雅黑", Font.BOLD, 16);
		FilenameLabel.setFont(f);
		FilenameLabel.setBounds(38, 10, 300, 30);
		panel.add(FilenameLabel);
		// 文件大小标签
		JLabel FileSize = new JLabel(Size);
		FileSize.setBounds(48, 40, 300, 30);
		panel.add(FileSize);
		DonePane.updateUI();
		if (76 + DoneNum * 69 > DonePane.getHeight())
		{
			DonePane.setPreferredSize(new Dimension(100, 69 + DoneNum * 75));
		}
		// 删除按钮
		JLabel Delete = new JLabel();
		f = new Font("微软雅黑", Font.BOLD, 20);
		Delete.setBounds(500, 20, 30, 30);
		Delete.setFont(f);
		Delete.setText("×");
		Delete.setBorder(null);
		Delete.setFocusable(false);
		Delete.setBackground(Color.WHITE);
		panel.add(Delete);
		Delete.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				RemoveOneDoneFile(Filename);
			}

			public void mouseEntered(MouseEvent e)
			{
				Delete.setForeground(Color.RED);
			}

			public void mouseExited(MouseEvent e)
			{
				Delete.setForeground(Color.BLACK);
			}

			public void mousePressed(MouseEvent e)
			{
				Delete.setLocation(502, 22);
			}

			public void mouseReleased(MouseEvent e)
			{
				Delete.setLocation(500, 20);
			}
		});
		// 打开文件按钮
		ImageIcon ico = new ImageIcon("打开文件.jpg");
		Image temp = ico.getImage().getScaledInstance(15, 15, ico.getImage().SCALE_DEFAULT);
		ico = new ImageIcon(temp);
		JLabel Image = new JLabel(ico);
		Image.setBounds(0, 0, 15, 15);

		JLabel OpenFile = new JLabel();
		OpenFile.setBounds(550, 29, 15, 15);
		OpenFile.setBorder(null);
		OpenFile.setFocusable(false);
		OpenFile.setBackground(Color.WHITE);
		OpenFile.add(Image);
		panel.add(OpenFile);
		OpenFile.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				//根据Filename在表中查询到文件名对应的路径
				try {
					int MAXLEN = 65535;
					String route = null;
					String routename[] = new String[MAXLEN];
					String filepath = "D:\\OnlineDiskLadyDownload\\RouteList.txt";
					File routefile = new File(filepath);
			        FileInputStream fileInputStream = new FileInputStream(routefile);  
			        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "GBK");  
			        BufferedReader reader = new BufferedReader(inputStreamReader);  
			        String lineContent = ""; 
			        int i = 0;
			        while ((lineContent = reader.readLine()) != null) {  
			        	System.out.println(lineContent);  
			            routename[i] = lineContent.split("\\|")[1];
			            System.out.println("路径为："+routename[i]);  //对
			            int len = routename[i].split("\\\\").length;
			            System.out.println("分割后的长度为："+len);
			            String tempfilename = routename[i].split("\\\\")[len-1];
			            System.out.println("得到的文件名为："+tempfilename);
			            System.out.println(Filename);
			            if(tempfilename.contains("\n"))
			            	System.out.println("111");
			            if(tempfilename.equals(Filename)){
			            	//匹配成功
			            	route = routename[i];
			            	System.out.println("查询到的文件路径为："+route);
			            	break;
			            }
			            
			            i++;
			        }  
			          
			        fileInputStream.close();  
			        inputStreamReader.close();  
			        reader.close();
			        //打开文件部分
			        String command = "cmd.exe /c start "+route;
			        System.out.println("命令为："+command);
		            Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象
		            try {
		                Process p = run.exec(command);// 启动另一个进程来执行命令
		                BufferedInputStream in = new BufferedInputStream(p.getInputStream());
		                BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
		                String lineStr;
		                while ((lineStr = inBr.readLine()) != null)
		                    //获得命令执行后在控制台的输出信息
		                    System.out.println(lineStr);// 打印输出信息
		                //检查命令是否执行失败。
		                if (p.waitFor() != 0) {
		                    if (p.exitValue() == 1)//p.exitValue()==0表示正常结束，1：非正常结束
		                        System.err.println("命令执行失败!");
		                }
		                inBr.close();
		                in.close();
		            } catch (Exception e1) {
		                e1.printStackTrace();
		            }
			        
			    } catch (Exception e1) {  
			        e1.printStackTrace();  
			    }
			}

			public void mousePressed(MouseEvent e)
			{
				OpenFile.setLocation(552, 30);
			}

			public void mouseReleased(MouseEvent e)
			{
				OpenFile.setLocation(550, 29);
			}
		});
		// 加入到列表
		DoneView doneview = new DoneView();
		doneview.Filename = FilenameLabel;
		doneview.FileSize = FileSize;
		doneview.panel = panel;
		DoneList.add(doneview);
	}

	private void RemoveOneDownloadFile(String Filename)
	{
		boolean IsFound = false;
		int i = 0;
		for (i = 0; i < DownloadList.size(); i++)
		{
			if (DownloadList.get(i).Filename.getText().equals(Filename))
			{
				DownloadPane.remove(DownloadList.get(i).panel);
				DownloadList.remove(i);
				IsFound = true;
				break;
			}
		}
		if (IsFound == true)
		{
			for (; i < DownloadList.size(); i++)
			{
				DownloadList.get(i).panel.setLocation(2, -18 + (i + 1) * 60);
			}
		}

		DownloadPane.updateUI();
	}

	private void RemoveOneUploadFile(String Filename)
	{
		boolean IsFound = false;
		int i = 0;
		for (i = 0; i < UploadList.size(); i++)
		{
			if (UploadList.get(i).Filename.getText().equals(Filename))
			{
				UploadPane.remove(UploadList.get(i).panel);
				UploadList.remove(i);
				IsFound = true;
				break;
			}
		}
		if (IsFound == true)
		{
			for (; i < DownloadList.size(); i++)
			{
				UploadList.get(i).panel.setLocation(2, -18 + (i + 1) * 60);
			}
		}
		UploadPane.updateUI();
	}

	private void RemoveOneDoneFile(String Filename)
	{
//		new Thread(new Delete_A_File(Username, Filename)).start();// 通知服务器删除这条记录
//		boolean IsFound = false;
//		int i = 0;
//		for (i = 0; i < DoneList.size(); i++)
//		{
//			if (DoneList.get(i).Filename.getText().equals(Filename))
//			{
//				DonePane.remove(DoneList.get(i).panel);
//				DoneList.remove(i);
//				IsFound = true;
//				break;
//			}
//		}
//		if (IsFound == true)
//		{
//			for (; i < DoneList.size(); i++)
//			{
//				DoneList.get(i).panel.setLocation(2, -48 + (i + 1) * 60);
//			}
//		}
//		DonePane.updateUI();
	}

//	改了
	private void AddOneFile(String Filename, String Size, String ModTime, int location)
	{
		FileView fileview = new FileView();
		// 面板
		JPanel panel = new JPanel();
		panel.setBounds(0, -30 + location * 38, 836, 38);
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);

		// 添加分享
		JButton shareButton = new JButton();
		shareButton.setBackground(new Color(100, 186, 243));
		shareButton.setBounds(850, -26 + location * 38, 80, 26);
		shareButton.setEnabled(true);
		shareButton.setBorder(null);
		shareButton.setText("分享");
		shareButton.requestFocusInWindow();

		shareButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				HashMap<String,String> map = new HashMap<>();
//				map.put("Mark", "Share");
//				map.put("Username",Username);
//				map.put("Filename",Filename);
//				;
//				map.put("Route",Route + "\\" +Filename);
//				Send.send(Map_String.MapToString(map));
			}
		});
		TablePane.add(shareButton);

		// 添加复选框
		JCheckBox checkbox = new JCheckBox();
		checkbox.setBounds(38, 7, 17, 17);
		checkbox.setBackground(Color.WHITE);
		checkbox.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent arg0)
			{
				if (checkbox.isSelected())
					panel.setBackground(new Color(245,245,220));
				else
					panel.setBackground(Color.WHITE);
			}
		});
		checkbox.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg0)
			{
				if (checkbox.isSelected() == false)
					AllSelectBox.setSelected(false);

				if (checkbox.isSelected())
					panel.setBackground(new Color(245,245,220));
				else
					panel.setBackground(new Color(240, 249, 255));

			}

			public void mouseEntered(MouseEvent arg0)
			{
				if (!checkbox.isSelected())
					panel.setBackground(new Color(240, 249, 255));
			}

			public void mouseExited(MouseEvent arg0)
			{
				if (!checkbox.isSelected())
					panel.setBackground(Color.WHITE);
			}
		});
		panel.add(checkbox);
		fileview.checkbox = checkbox;
		// 设置面板触发
		panel.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent arg0)
			{
				if (!checkbox.isSelected())
					panel.setBackground(new Color(245,245,220));
			}

			public void mouseExited(MouseEvent arg0)
			{
				if (!checkbox.isSelected())
					panel.setBackground(Color.WHITE);
			}

			public void mouseClicked(MouseEvent arg0)
			{
				checkbox.setSelected(!checkbox.isSelected());
				if (checkbox.isSelected())
					panel.setBackground(new Color(245,245,220));
				else
					panel.setBackground(new Color(245,245,220));
			}
		});
		// 添加文件大小标签
		JLabel SizeLabel = null;
		if (Size.equals("-"))
			SizeLabel = new JLabel("       —");
		else
			SizeLabel = new JLabel(Size);
		SizeLabel.setBounds(380, 0, 100, 30);
		panel.add(SizeLabel);
		fileview.FileSize = SizeLabel;
		// 添加文件名标签
		JLabel FilenameLabel = new JLabel(Filename);
		Font f = new Font("微软雅黑", Font.BOLD, 12);
		FilenameLabel.setFont(f);
		FilenameLabel.setBounds(78, 0, 300, 30);
		panel.add(FilenameLabel);
		fileview.Filename = FilenameLabel;
		FilenameLabel.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent e)
			{
				if (Size.equals("-"))// 如果是文件夹
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				if (!checkbox.isSelected())
					panel.setBackground(new Color(240, 249, 255));
			}

			public void mouseExited(MouseEvent e)
			{
				if (Size.equals("-"))// 如果是文件夹
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				if (!checkbox.isSelected())
					panel.setBackground(Color.WHITE);
			}

			public void mouseClicked(MouseEvent e)
			{
				if (Size.equals("-"))// 如果是文件夹
				{
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					Route = Route + "\\" + FilenameLabel.getText();
//					new Thread(new Ask_File_List(Username, Route)).start();
//					向服务器发请求获取文件列表
					try {
						sClient client = new sClient();
						client.send(cPackMessage.GetFileList(Username,""));
						client.recv();
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					String Route2 = Route.replace("\\", " → ");
					RouteText.setText("我的网盘" + Route2);
				}
				checkbox.setSelected(!checkbox.isSelected());
				if (checkbox.isSelected())
					panel.setBackground(new Color(245,245,220));
				else
					panel.setBackground(new Color(240, 249, 255));
			}
		});
		// 添加修改时间标签
		JLabel ModTimeLabel = new JLabel(ModTime);
		ModTimeLabel.setBounds(615, 0, 150, 30);
		panel.add(ModTimeLabel);
		if (location * 38 > TablePane.getHeight())
		{
			TablePane.setPreferredSize(new Dimension(100, 38 + location * 38));
		}
		TablePane.add(panel);
		TablePane.updateUI();
		fileview.ModTime = ModTimeLabel;
		FileList.add(fileview);
	}

	public void AddMulTableData(String[] Filename, String[] Size, String[] ModTime, int TotalNum)
	{
		TablePane.removeAll();
		FileList.removeAll(FileList);
		TablePane.setPreferredSize(new Dimension(100, 20 + TotalNum * 20));
		for (int i = 0; i < TotalNum; i++)
		{
			AddOneFile(Filename[i], Size[i], ModTime[i], i + 1);
		}
		this.repaint();
	}

	
	public String[] SelectFile()// 提示用户通过浏览选择一个文件，返回这个文件路径
	{
		// 文件选择器
		JFileChooser chooser = new JFileChooser("D:\\");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setMultiSelectionEnabled(true);
		Font f = new Font("微软雅黑", Font.BOLD, 13);
		updateFont(chooser, f);
		File DefaultLoc = new File("");
		chooser.setCurrentDirectory(DefaultLoc);
		int ret = chooser.showOpenDialog(this);
		File[] dir = null;
		if (ret == JFileChooser.APPROVE_OPTION)
		{
			dir = chooser.getSelectedFiles();
		}
		if (dir != null)
		{
			String[] result = new String[dir.length];
			for (int i = 0; i < result.length; i++)
			{
				result[i] = dir[i].getAbsolutePath();
			}
			return result;
		} else
			return null;
	}

	public String GetFolder(String Route)
	{
		int i;
		for (i = Route.length() - 1; i >= 0; i--)
		{
			if (Route.charAt(i) == '\\')
				break;
		}
		return Route.substring(0, i);
	}

	public ArrayList<FileView> GetSelectFile()
	{
		ArrayList<FileView> result = new ArrayList<>();
		for (int i = 0; i < FileList.size(); i++)
		{
			if (FileList.get(i).checkbox.isSelected())
				result.add(FileList.get(i));
		}
		return result;
	}

	private static void updateFont(Component comp, Font font)
	{
		comp.setFont(font);
		if (comp instanceof Container)
		{
			Container c = (Container) comp;
			int n = c.getComponentCount();
			for (int i = 0; i < n; i++)
			{
				updateFont(c.getComponent(i), font);
			}
		}
	}
}

class FileView
{
	JCheckBox checkbox = null;
	JLabel Filename = null;
	JLabel FileSize = null;
	JLabel ModTime = null;
}

class UploadView
{
	JPanel panel = null;
	JLabel Filename = null;
	JLabel FileSize = null;
	JProgressBar bar = null;
}

class DownloadView
{
	JPanel panel = null;
	JLabel Filename = null;
	JLabel FileSize = null;
	JProgressBar bar = null;
}

class DoneView
{
	JPanel panel = null;
	JLabel Filename = null;
	JLabel FileSize = null;
}