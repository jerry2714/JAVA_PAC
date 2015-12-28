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
	public void init()
	{
		img = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		g2d.setColor(Color.red);
		g2d.setBackground(transparent);
		g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
		System.out.println(img.getWidth()+" "+ img.getHeight());
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