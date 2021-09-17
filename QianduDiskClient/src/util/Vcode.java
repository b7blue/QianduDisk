package util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JComponent;

/*
 * Vcode产生图形验证码及相关功能
 * 验证码默认长度为4，由大小写字母组成，验证时不区分大小写
 */
public class Vcode extends JComponent implements MouseListener{

	//code记录生成的验证码，用于认证时使用
	public String code;
    private int width = 100, height = 27;
    private int codeLength = 4;
    private Random random = new Random();
    
    //图形验证码的构造函数
    public Vcode() {
    	//设置图形验证码的大小
    	width = this.codeLength * 16 + (this.codeLength - 1) * 10;
        setPreferredSize(new Dimension(width, height));
        setSize(width, height);
        
        //添加监听器，以实现点击更换验证码
        this.addMouseListener(this);
        
    }
    
	//生成验证码内容
    public String generateVcode() {
    	//codes用于存储长度为【codelength】的验证码
    	char[] codes = new char[this.codeLength];
    	//利用随机数验证码
        for (int i = 0, len = codes.length; i < len; i++) {
            if (random.nextBoolean()) {
                codes[i] = (char) (random.nextInt(26) + 65);
            } else {
                codes[i] = (char) (random.nextInt(26) + 97);
            }
        }
        //成员变量code存储生成的验证码
        this.code = new String(codes);
        return this.code;
    }
    
    //生成图形验证码
    protected void paintComponent(Graphics g) {
    	if(this.code == null) {
            this.code = generateVcode();
        }

        width = this.codeLength * 16 + (this.codeLength - 1) * 10;
        super.setSize(width, height);
        super.setPreferredSize(new Dimension(width, height));
        Font mFont = new Font("Arial", Font.BOLD | Font.ITALIC, 20);
        g.setFont(mFont);
        Graphics2D g2d = (Graphics2D) g;
        //绘制图形验证码纯色背景
        g2d.setColor(getRandColor(200, 250));
        g2d.fillRect(0, 0, width, height);
        //绘制图形验证码边框
        g2d.setColor(getRandColor(180, 200));
        g2d.drawRect(0, 0, width - 1, height - 1);
        
        int i = 0, len = 150;

        Color color;
        //绘制图形验证码混乱背景
        for (; i < len; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int x1 = random.nextInt(width - 10) + 10;
            int y1 = random.nextInt(height - 4) + 4;
            color = getRandColor(180, 200);

            g2d.setColor(color);
            g2d.drawLine(x, y, x1, y1);
        }
        i = 0; 
        len = this.codeLength;
        FontMetrics fm = g2d.getFontMetrics();
        int base = (height - fm.getHeight())/2 + fm.getAscent();
       //绘制四位验证码内容
        for(;i<len;i++) {
        	int b = random.nextBoolean() ? 1 : -1;
            g2d.rotate(random.nextInt(5)*0.01*b);
            g2d.setColor(getRandColor(20, 130));
            g2d.drawString(code.charAt(i)+"", 16 * i + 10, base);
            }
        }
    
	//生成下一个验证码
    public void nextVcode() {
        generateVcode();
        repaint();
    }
	//获取随机颜色
	public Color getRandColor(int min, int max) {
	
	    if (min > 255)
	        min = 255;
	    if (max > 255)
	        max = 255;
	    int red = random.nextInt(max - min) + min;
	    int green = random.nextInt(max - min) + min;
	    int blue = random.nextInt(max - min) + min;
	    return new Color(red, green, blue);
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//点击生成下一个验证码
		nextVcode();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
}
