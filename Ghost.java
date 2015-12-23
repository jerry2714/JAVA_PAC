import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
class Ghost extends GameObject
{
	private BufferedImage body;
	private BufferedImage eye;
	public Ghost(String str)
	{
		try
		{
			body = ImageIO.read(new File(str));
			img = new BufferedImage(body.getWidth(), body.getHeight(), body.TYPE_INT_ARGB);
			eye = ImageIO.read(new File("src\\images\\spinnyeyes.png"));
        }
        catch (Exception ex)
		{
            System.out.println(str);
        }
		// setSize(50, 50);
		ox = oy = x = y = 0;
		width = height = 150;
		//System.out.println(""+isDoubleBuffered());
		setPriority(3);
	}
	public void paintCanvas(Graphics g)
	{
		/*if(img == null)
			System.out.println("ghost missed");*/
		move();
		g.drawImage(img, x, y, width, height, this);
	} 
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
	}
	public void move()
	{
		oy = y;
		ox = x;
		switch(direction)
		{
			case CENTER:
				break;
			case UP:
				y--;
				break;
			case DOWN:
				y++;
				break;
			case LEFT:
				x--;
				break;
			case RIGHT:
				x++;
				break;
			default:
		}
	}
}