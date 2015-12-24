import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

class Pacman extends GameObject
{
	private int mouthTop = 300;
	private int mouthBottom = 30;
	
	public Pacman()
	{
		width = height = 200;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		g2d.setColor(Color.yellow);
		g2d.fillArc(0, 0, width, height, mouthBottom, mouthTop);
		setPriority(2);
	}
	public void paintCanvas(Graphics g)
	{
		// move();
		g.drawImage(img, x, y, width, height, this);
	} 
	public void paint(Graphics g)
	{
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