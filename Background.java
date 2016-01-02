import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

class Background extends GameObject
{
	public void init()
	{
		img = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
		System.out.println(img.getWidth()+" "+ img.getHeight());
		setPriority(Priority.BACK_GROUND);
	}
	public void paintCanvas(Graphics g)
	{
		//System.out.println("bg");
		g.drawImage(img, 0, 0, frameWidth, frameHeight, this);
	}
	public void action(){}
	public void hitReact(Number num){}
}
class Foreground extends GameObject
{
	static public int foreX;
	static public int foreY;
	static public int foreW;
	static public int foreH;
	public void init()
	{
		img = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		g2d.setColor(Color.white);
		g2d.setBackground(transparent);
		g2d.clearRect(0, 0, frameWidth, frameHeight);
		g2d.fillRect(0, 0, frameWidth, 30);
		g2d.fillRect(0, 0, 30, frameHeight);
		g2d.fillRect(0, frameHeight-30, frameWidth, 30);
		g2d.fillRect(frameWidth-30, 0, 30, frameHeight);
		
		foreX = 30; foreY = 30; foreW = frameWidth-60; foreH = frameHeight-60;
		setPriority(Priority.FORE_GROUND);
	}
	public void paintCanvas(Graphics g)
	{
		//System.out.println("bg");
		g.drawImage(img, 0, 0, frameWidth, frameHeight, this);
	}
	public void action(){}
	public void hitReact(Number num){}
	
}