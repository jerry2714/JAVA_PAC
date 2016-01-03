import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import java.util.*;
import java.time.*;

//幽靈
class Ghost extends GameObject
{
	private static BufferedImage bodyImage;	//身體樣圖
	private static BufferedImage eye;	//眼睛
	private static BufferedImage eyeWhite;	//眼白
	private static BufferedImage scared1;//被嚇到的圖
	private static BufferedImage scared2;
	private BufferedImage body;	//身體
	//img = 完整的ghost = 身體加眼睛
	
	double temp;
	double speed;
	final double COMMON_SPEED = 1.5;
	final double LOW_SPEED = 0.5;
	final double ESCAPING_SPEED = 4;
	static Random ran = new Random();
	ScaredControl sc = new ScaredControl();
	
	
	
	static
	{
		try
		{
			bodyImage = ImageIO.read(new File("res\\images\\Ghost1.png"));
			eye = ImageIO.read(new File("res\\images\\spinnyeyes.png"));
			eyeWhite = ImageIO.read(new File("res\\images\\eyes.png"));
			scared1 = ImageIO.read(new File("res\\images\\ghostscared1.png"));
			scared2 = ImageIO.read(new File("res\\images\\ghostscared2.png"));
			
			for(int i = 0; i < eyeWhite.getWidth(); i++)
				for(int j = 0; j < eyeWhite.getHeight(); j++)
				{
					if(eyeWhite.getRGB(i, j) >> 24 != 0 && eyeWhite.getRGB(i, j) != 0xffffffff)
						eyeWhite.setRGB(i, j, 0xffffffff);
				}
        }
		catch(Exception e){}
        
	}
	public Ghost()
	{
		img = new BufferedImage(bodyImage.getWidth(), bodyImage.getHeight(), bodyImage.TYPE_INT_ARGB);
		body = new BufferedImage(bodyImage.getWidth(), bodyImage.getHeight(), bodyImage.TYPE_INT_ARGB);
		x = y = 0;
		width = height = 30;
		
		setPriority(Priority.GHOST);
		symbol = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = symbol.createGraphics();
		g2d.drawImage(bodyImage, 0, 0, width, height, this);
		
		g2d = body.createGraphics();
		g2d.drawImage(bodyImage, 0, 0, this);
		setId(Number.GHOST);
		speed = COMMON_SPEED;
		
		
		//給新來的幽靈新的顏色
		int color;
		do{
			int i;
			color = ran.nextInt();
			for(i = 0; i < 32 && color >= 0; i++)
				color = color << 1;
			if(i != 32)
			{
				color = color >> 8;
				break;
			}
		}while(true);
		
		for(int i = 0; i < body.getWidth(); i++)
			for(int j = 0; j < body.getHeight(); j++)
			{
				if(body.getRGB(i, j) >> 24 != 0 && body.getRGB(i, j) != 0xffffffff)
					body.setRGB(i, j, color);
			}
		
		//
	}
	public void hitReact(GameObject g)
	{
		Number num = g.getId();
		switch(num)
		{
			case GHOST:
				//setDireciton();
				break;
			case PACMAN:
				if(sc.isShocked)
				{
					sc.isEscaping = true;
					sc.isShocked = false;
					sc.start = false;
					gscontrol.score += 200;
				}
				if(gscontrol.pac != gscontrol.pac.COMMON)
				break;
		}
	}
	
	public void setDirection(Direction d)
	{
		direction = d;
		Graphics2D g2d = img.createGraphics();
		
		if(!sc.isShocked)
		{
			if(!sc.isEscaping)
				g2d.drawImage(body, 0, 0, this);
			else
				g2d.drawImage(eyeWhite, 0, 0, this);
			switch(d)	//換方向同時更新眼睛的位置
			{
				case UP:
					g2d.drawImage(eye, 112, 146, this);
					break;
				case DOWN:
				case CENTER:
					g2d.drawImage(eye, 112, 256, this);
					break;
				case LEFT:
					g2d.drawImage(eye, 72, 200, this);
					break;
				case RIGHT:
					g2d.drawImage(eye, 153, 200, this);
					break;
			}
		}
		//img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}
	public boolean ghostIsShocked(){return sc.isShocked;}
	public boolean ghostIsEscaping(){return sc.isEscaping;}
	public void action()
	{
		if(gscontrol.pacmanIsKilled())
			return;
		if(gscontrol.ghostStateSwitch != sc.stateSwitch)
		{
			sc.stateSwitch = gscontrol.ghostStateSwitch;
			if(!sc.isEscaping)
			{
				sc.isShocked = true;
				sc.start = false;
			}
		}
		
		if(sc.isShocked)
		{
			speed = -LOW_SPEED;
			if(!sc.scaredReact())
			{
				sc.isShocked = false;
				setDirection(direction);
			}
		}
		else if(sc.isEscaping)
		{
			speed = ESCAPING_SPEED;
			if(!sc.escaping())
			{
				sc.isEscaping = false;
				setDirection(direction);
			}
		}
		else
			speed = COMMON_SPEED;
		if(sc.isEscaping)	//變換方向
		{
			if(ran.nextInt(25) == 1)	
			{
				switch(ran.nextInt(4))
				{
					case 0:setDirection(Direction.LEFT);  break;
					case 1:setDirection(Direction.RIGHT); break;
					case 2:setDirection(Direction.UP);    break;
					case 3:setDirection(Direction.DOWN);  break;
				}
			}
		
		}
		else if(ran.nextInt(50) == 1)
		{
			if(ran.nextInt(2) == 1)
			{
				if(x > gscontrol.pacX)
					setDirection(Direction.LEFT);
				else
					setDirection(Direction.RIGHT);
			}
			else
			{
				if(y > gscontrol.pacY)
					setDirection(Direction.UP);
				else
					setDirection(Direction.DOWN);
			}
		}
		temp += speed;
		if((int)temp != 0)
		{
			switch(direction)
			{
				case CENTER:
					break;
				case UP:
					y -= (int)temp;
					break;
				case DOWN:;
					y += (int)temp;
					break;
				case LEFT:
					x -= (int)temp;
					break;
				case RIGHT:
					x += (int)temp;
					break;
				default:
			}
			temp -= (int)temp;
		}	
		
		outOfAreaFix();
		rect.setBounds(x, y, width, height);
		//System.out.println(x+ " "+y);
	}
	
	public void init()
	{
		sc.isShocked = false;
		sc.isEscaping = false;
	}
	class ScaredControl
	{
		boolean stateSwitch = false;
		boolean isShocked = false;
		boolean isEscaping = false;
		
		int count = 0;
		byte imageNumber;
		Duration d = Duration.ofSeconds(10);
		Instant i1;
		Instant i2;
		boolean start = false;
		public boolean scaredReact()
		{
			if(!start)
			{
				i1 = i1.now();
				imageNumber = 1;
				start = true;
				setPriority(Priority.SHOCKED_GHOST);
				count = 0;
				switchImage();
			}
			i2 = i2.now();
			if(i2.minus(Duration.ofSeconds(1)).isAfter(i1))
			{	
				count++;
				i1 = i1.now();
				switchImage();
			}
			
			if(count == 15)
			{
				count = 0;
				start = false;
				setPriority(Priority.GHOST);
				return false;
			}
			else return true;
		}
		
		public void switchImage()
		{
			if(count > 8)
			{
				if(imageNumber == 1)
					imageNumber = 2;
				else
					imageNumber = 1; 
			}
			Graphics2D g2d = img.createGraphics();
			if(imageNumber == 1)
				g2d.drawImage(scared1, 0, 0, null);
			else
				g2d.drawImage(scared2, 0, 0, null);
		}
		public boolean escaping()
		{
			if(!start)
			{
				i1 = i1.now();
				start = true;
				setPriority(Priority.ESCAPING_GHOST);
				count = 0;
				Graphics2D g2d = img.createGraphics();
				g2d.setBackground(transparent);
				for(int i = 0; i < img.getWidth(); i++)
					for(int j = 0; j < img.getHeight(); j++)
						img.setRGB(i, j, 0x00000000);
				//g2d.clearRect(0, 0, width, height);
				g2d.drawImage(eyeWhite, 0, 0, null);
				setDirection(direction);
			}
			i2 = i2.now();
			if(i2.minus(Duration.ofSeconds(1)).isAfter(i1))
			{	
				count++;
				i1 = i1.now();
			}
			if(count == 6)
			{
				count = 0;
				start = false;
				setPriority(Priority.GHOST);
				return false;
			}
			else return true;
		}
	}
	// static public void main(String args[])
	// {
		// Frame frm = new Frame();
		// frm.setSize(800, 800);
		
		// Canvas c = new Canvas();
		// frm.add(c);
		// c.setBackground(Color.black);
		// frm.setVisible(true);
		
		
		// Graphics g = c.getGraphics();
		// while(true)
		// g.drawImage(eyeWhite, 0, 0, null);
		
		
		
	// }
}