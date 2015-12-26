import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

//�p���F
class Pacman extends GameObject
{
	Graphics2D g2d;
		
	private int upperLip = 40;	//�p���F�W�L�B��x�b����
	private int bodyAngle = 280;//�p���F�qupperLip�}�l�⪺����(���F�qupperLip�}�l�f�ɰw�e�X�p���F����)
	private double theta = 0;
	double temp = 0;
	double speed = 1;	//���ʳt��(�ȩwspeed�A�ثe���ʧ@�k�� x+=speed�o�ءA����resize�N�|�v�T���ʳt��)
	double mouthMotionSpeed = 2;//�p���F�L�ڶ}�X�t��(�@�k��W�������A���D�]�@��)
	//int bigW, bigH;
	public Pacman()
	{
		width = height = 50;
		// bigW = width*50;
		// bigH = height*50;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g2d = img.createGraphics();
		g2d.setBackground(transparent);//Color4�ѼơA�̫�@�ӬOAlpha
		g2d.setColor(Color.yellow);
		g2d.fillArc(0, 0, width, height, upperLip, bodyAngle);//�e�X�p���F
		setPriority(2);
		
		symbol = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = symbol.createGraphics();
		g.fillOval(0, 0, width, height);
	}
	public void drawPacman()//���e�p���F(���V�M�L�ڱi�}����)
	{
		g2d.clearRect(0, 0, width, height);
		g2d.rotate(theta, width/2, height/2);
		g2d.fillArc(0, 0, width, height, upperLip, bodyAngle);
		g2d.rotate(-theta, width/2, height/2);
	}
	
	// public void paintCanvas(Graphics g)
	// {
		// move();
		// g.drawImage(symbol,x, y, this);
	// }
	public void setDirection(Direction d)
	{
		direction = d;
		
		switch(d)
		{
			case UP:
				theta = -90.0/180*Math.PI;
				break;
			case DOWN:
				theta = 90.0/180*Math.PI;
				break;
			case CENTER:
			case RIGHT:
				theta = 0;
				break;
			case LEFT:
				theta = Math.PI;
				break;
		}
	}
	
	public void move()
	{
		upperLip -= mouthMotionSpeed;
		bodyAngle += mouthMotionSpeed*2;
		if(upperLip == 0 || upperLip == 40)
			mouthMotionSpeed = -mouthMotionSpeed;
		drawPacman();
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