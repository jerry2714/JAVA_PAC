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
		Graphics g2d = img.createGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
		System.out.println(img.getWidth()+" "+ img.getHeight());
		setPriority(0);
	}
	public void paintCanvas(Graphics g)
	{
		//System.out.println("bg");
		g.drawImage(img, 0, 0, frameWidth, frameHeight, this);
	}
}