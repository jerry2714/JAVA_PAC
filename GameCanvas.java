import java.util.*;
import java.awt.*;
import java.awt.image.*;


class GameCanvas extends Canvas implements Initialize
{
	private static BufferedImage img = null;//ø�ϥ�
	private static Graphics2D g2d;//img��Graphic context
	public static int frameWidth, frameHeight;	//�iø�ϰϤj�p
	
	public void setFrameSize(int width, int height)
	{
		frameWidth = width;
		frameHeight = height;
	}
	public void init()//��l�ơA�}�l�ϥΫe�����I�s
	{
		img = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_ARGB);
		g2d = img.createGraphics();
		g2d.clearRect(0, 0, frameWidth, frameHeight);
	}
	public int getCanvasWidth(){return getWidth();}
	public int getCanvasHeight(){return getHeight();}
	public void paintCanvas(Graphics g){}//���Ҧ��l���O��g2d�i��ާ@(ø��)

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