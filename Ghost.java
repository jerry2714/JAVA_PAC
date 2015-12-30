import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import java.util.Random;

//幽靈
class Ghost extends GameObject
{
	private static BufferedImage bodyImage;	//身體樣圖
	private static BufferedImage eye;	//眼睛
	private BufferedImage body;	//身體
	//img = 完整的ghost = 身體加眼睛
	
	double temp;
	double speed;
	final double COMMON_SPEED = 3;
	final double LOW_SPEED = 2;
	static Random ran = new Random();
	
	static
	{
		try
		{
			bodyImage = ImageIO.read(new File("res\\images\\Ghost1.png"));
			eye = ImageIO.read(new File("res\\images\\spinnyeyes.png"));
        }
		catch(Exception e){}
        
	}
	public Ghost()
	{
		img = new BufferedImage(bodyImage.getWidth(), bodyImage.getHeight(), bodyImage.TYPE_INT_ARGB);
		body = new BufferedImage(bodyImage.getWidth(), bodyImage.getHeight(), bodyImage.TYPE_INT_ARGB);
		x = y = 0;
		width = height = 50;
		
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
	public void hitReact(Number num){}
	
	public void setDirection(Direction d)
	{
		direction = d;
		Graphics2D g2d = img.createGraphics();
		g2d.drawImage(body, 0, 0, this);
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
		//img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}
	public void action()
	{
		if(ran.nextInt(10) == 1)
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
}