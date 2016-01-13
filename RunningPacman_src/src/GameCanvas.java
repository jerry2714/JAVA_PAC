import java.util.*;
import java.awt.*;
import java.awt.image.*;


class GameCanvas extends Canvas implements Initialize
{
	private static BufferedImage img = null;//繪圖用
	private static Graphics2D g2d;//img的Graphic context
	public static int frameWidth, frameHeight;	//可繪圖區大小
	
	public void setFrameSize(int width, int height)
	{
		frameWidth = width;
		frameHeight = height;
	}
	public void init()//初始化，開始使用前必須呼叫
	{
		img = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_ARGB);
		g2d = img.createGraphics();
		g2d.clearRect(0, 0, frameWidth, frameHeight);
	}
	public int getCanvasWidth(){return getWidth();}
	public int getCanvasHeight(){return getHeight();}
	public void paintCanvas(Graphics g){}//讓所有子類別對g2d進行操作(繪圖)

	public void update(Graphics g)
	{	
		if(img == null)
			init();
		paint(g);
	}
	public void paint(Graphics g)
	{
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}
	
	public void draw(GameObject go)
	{
		go.paintCanvas(g2d);
	}
}