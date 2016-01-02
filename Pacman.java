import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

//小精靈
class Pacman extends GameObject
{
	Graphics2D g2d;
	
	private final int UPPER_LIP = 40;
	private final int BODY_ANGLE = 280;
	private int upperLip = UPPER_LIP;	//小精靈上嘴唇跟x軸夾角
	private int bodyAngle = BODY_ANGLE;//小精靈從upperLip開始算的角度(為了從upperLip開始逆時針畫出小精靈身體)
	private double theta = 0;
	double temp = 0;
	double speed = 3;	//移動速度(暫定speed，目前移動作法為 x+=speed這種，視窗resize就會影響移動速度)
	double mouthMotionSpeed = 4;//小精靈嘴巴開合速度(作法跟上面類似，問題也一樣)
	//int bigW, bigH;
	public Pacman()
	{
		width = height = 30;
		// bigW = width*50;
		// bigH = height*50;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g2d = img.createGraphics();
		g2d.setBackground(transparent);//Color4參數，最後一個是Alpha
		g2d.setColor(Color.yellow);
		g2d.fillArc(0, 0, width, height, upperLip, bodyAngle);//畫出小精靈
		setPriority(Priority.PACMAN);
		
		symbol = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = symbol.createGraphics();
		g.fillOval(0, 0, width, height);
		setId(Number.PACMAN);
	}
	public void drawPacman()//重畫小精靈(改方向和嘴巴張開角度)
	{
		double t = theta;	// 避免theta亂掉(theta的改變和此函式出現在不同thread)
		g2d.clearRect(0, 0, width, height);
		g2d.rotate(t, width/2, height/2);
		g2d.fillArc(0, 0, width, height, upperLip, bodyAngle);
		g2d.rotate(-t, width/2, height/2);
	}
	
	public void setDirection(Direction d)
	{
		direction = d;
		
		switch(d) //轉嘴巴的角度
		{
			case UP:
				theta = -90.0/180*Math.PI;
				break;
			case DOWN:
				theta = 90.0/180*Math.PI;
				break;
			case CENTER:
			case RIGHT:
				theta = 0;
				break;
			case LEFT:
				theta = Math.PI;
				break;
		}
		//System.out.println(theta);
	}
	
	public void action()
	{
		switch(gscontrol.pac)
		{
			case COMMON:
				upperLip -= mouthMotionSpeed;
				bodyAngle += mouthMotionSpeed*2;
				if(upperLip <= 0 || upperLip >= 40)
				{
					mouthMotionSpeed = -mouthMotionSpeed;
					if(upperLip < 0)
					{
						upperLip = 0;
						bodyAngle = 0;
					}
					else if(upperLip > UPPER_LIP)
					{
						upperLip = UPPER_LIP;
						bodyAngle = BODY_ANGLE;
					}
				}
				
				switch(direction)
				{
					case CENTER:
						break;
					case UP:
						temp = y;
						temp -= speed;
						y = (int)temp;
						break;
					case DOWN:
						temp = y;
						temp += speed;
						y = (int)temp;
						break;
					case LEFT:
						temp = x;
						temp -= speed;
						x = (int)temp;
						break;
					case RIGHT:
						temp = x;
						temp += speed;
						x = (int)temp;
						break;
					default:
				}
				break;
			case KILLED:
				if(!dying())
				{
					gscontrol.pacmanRetrive();
					upperLip = UPPER_LIP;
					bodyAngle = BODY_ANGLE;
				}
				break;
		}
		drawPacman();
		outOfAreaFix();
		rect.setBounds(x, y, width, height);
		gscontrol.pacPosUpdate(x+width/2, y+height/2);
	}
	public boolean dying() //死掉的動畫，動畫還沒結束就return true，動畫結束了就return false
	{
		mouthMotionSpeed = 1;
		bodyAngle -= mouthMotionSpeed;
		if(bodyAngle == 0)
			return false;
		else
			return true;
	}
	
	public void hitReact(GameObject g)
	{
		Number num = g.getId();
		switch(num)
		{
			case GHOST:
				if(((Ghost)g).ghostIsShocked() || ((Ghost)g).ghostIsEscaping())
				{}
				else
				{
					gscontrol.pacmanKilled();
				}
				break;
			case POWER_PELLET:
					gscontrol.ghostShock();
				break;
		}
	}
	
	public void paintCanvas(Graphics g)
	{
		action();
		g.drawImage(img, x, y, width, height, this);
		
	}
	// public static void main(String args[])
	// {
		
		// Frame frm = new Frame();
		// Canvas c = new Canvas();
		// frm.setSize(600, 600);
		
		// frm.add(c);
		// c.setBackground(Color.black);
		// Pacman pac = new Pacman();
		// pac.setBackground(Color.pink);
		// frm.add(pac);
		
		// frm.setVisible(true);
	// }
	
}
