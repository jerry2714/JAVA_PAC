import java.awt.*;
import java.awt.image.*;
import java.util.Random;


class Dot extends GameObject
{
	Graphics2D g2d;

	static Random ran = new Random();
	Dot()
	{
		width = height = 5;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g2d = img.createGraphics();
		g2d.setColor(Color.yellow);
		g2d.fillRect(0, 0, width, height);
		symbol = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g2d = symbol.createGraphics();
		g2d.drawImage(img, 0, 0, this);
		setPriority(Priority.DOT);
		setId(Number.DOT);
		
	}
	public void changePos()
	{
		int x = ran.nextInt(frameWidth-80)+40;
		int y = ran.nextInt(frameHeight-80)+40;
		setPosition(x, y);
		//System.out.println(x + " " + y);
		rect.setBounds(x, y, width, height);
		
		
	}
	public void hitReact(GameObject g)
	{
		Number num = g.getId();
		switch(num)
		{
			case PACMAN:
				changePos();
				gscontrol.score += 10;
				break;
		}
	}
	public void action()
	{
		
	}
}