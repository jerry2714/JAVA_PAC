import java.awt.*;
import java.awt.image.*;
import java.util.Random;

class PowerPellet extends GameObject
{
	Graphics2D g2d;
	static Random ran = new Random();
	boolean appear = false;
	
	PowerPellet()
	{
		width = height = 10;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g2d = img.createGraphics();
		g2d.setColor(Color.pink);
		g2d.fillOval(0, 0, width, height);
		symbol = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g2d = symbol.createGraphics();
		g2d.drawImage(img, 0, 0, this);
		setPriority(Priority.POWER_PELLET);
		setId(Number.POWER_PELLET);
		
	}
	public void changePos()
	{
		int x = ran.nextInt(frameWidth-20)+10;
		int y = ran.nextInt(frameHeight-20)+10;
		setPosition(x, y);
		System.out.println(x + " " + y);
		rect.setBounds(x, y, width, height);
	}
	public void hitReact(Number num)
	{
		switch(num)
		{
			case PACMAN:
				appear = false;
				break;
		}
	}
	public void action()
	{
		if(appear == true)
			return;
		if(ran.nextInt(1000) == 0)
		{
			appear = true;
			changePos();
		}
	}
	public void paintCanvas(Graphics g)//預設遊戲物件繪圖方法
	{
		action();
		if(appear)
			g.drawImage(img, x, y, width, height, this);
	}
}