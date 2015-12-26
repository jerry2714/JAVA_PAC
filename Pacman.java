import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

//小精靈
class Pacman extends GameObject
{
	Graphics2D g2d;
		
	private int upperLip = 40;	//小精靈上嘴唇跟x軸夾角
	private int bodyAngle = 280;//小精靈從upperLip開始算的角度(為了從upperLip開始逆時針畫出小精靈身體)
	private double theta = 0;
	double temp = 0;
	double speed = 1;	//移動速度(暫定speed，目前移動作法為 x+=speed這種，視窗resize就會影響移動速度)
	double mouthMotionSpeed = 2;//小精靈嘴巴開合速度(作法跟上面類似，問題也一樣)
	//int bigW, bigH;
	public Pacman()
	{
		width = height = 50;
		// bigW = width*50;
		// bigH = height*50;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g2d = img.createGraphics();
		g2d.setBackground(transparent);//Color4參數，最後一個是Alpha
		g2d.setColor(Color.yellow);
		g2d.fillArc(0, 0, width, height, upperLip, bodyAngle);//畫出小精靈
		setPriority(2);
		
		symbol = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = symbol.createGraphics();
		g.fillOval(0, 0, width, height);
	}
	public void drawPacman()//重畫小精靈(改方向和嘴巴張開角度)
	{
		g2d.clearRect(0, 0, width, height);
		g2d.rotate(theta, width/2, height/2);
		g2d.fillArc(0, 0, width, height, upperLip, bodyAngle);
		g2d.rotate(-theta, width/2, height/2);
	}
	
	// public void paintCanvas(Graphics g)
	// {
		// move();
		// g.drawImage(symbol,x, y, this);
	// }
	public void setDirection(Direction d)
	{
		direction = d;
		
		switch(d)
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
	}
	
	public void move()
	{
		upperLip -= mouthMotionSpeed;
		bodyAngle += mouthMotionSpeed*2;
		if(upperLip == 0 || upperLip == 40)
			mouthMotionSpeed = -mouthMotionSpeed;
		drawPacman();
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
		rect.setBounds(x, y, width, height);
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