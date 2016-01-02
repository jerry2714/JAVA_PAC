import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

//�p���F
class Pacman extends GameObject
{
	Graphics2D g2d;
	
	private final int UPPER_LIP = 40;
	private final int BODY_ANGLE = 280;
	private int upperLip = UPPER_LIP;	//�p���F�W�L�B��x�b����
	private int bodyAngle = BODY_ANGLE;//�p���F�qupperLip�}�l�⪺����(���F�qupperLip�}�l�f�ɰw�e�X�p���F����)
	private double theta = 0;
	double temp = 0;
	double speed = 3;	//���ʳt��(�ȩwspeed�A�ثe���ʧ@�k�� x+=speed�o�ءA����resize�N�|�v�T���ʳt��)
	double mouthMotionSpeed = 4;//�p���F�L�ڶ}�X�t��(�@�k��W�������A���D�]�@��)
	//int bigW, bigH;
	public Pacman()
	{
		width = height = 30;
		// bigW = width*50;
		// bigH = height*50;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g2d = img.createGraphics();
		g2d.setBackground(transparent);//Color4�ѼơA�̫�@�ӬOAlpha
		g2d.setColor(Color.yellow);
		g2d.fillArc(0, 0, width, height, upperLip, bodyAngle);//�e�X�p���F
		setPriority(Priority.PACMAN);
		
		symbol = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = symbol.createGraphics();
		g.fillOval(0, 0, width, height);
		setId(Number.PACMAN);
	}
	public void drawPacman()//���e�p���F(���V�M�L�ڱi�}����)
	{
		double t = theta;	// �קKtheta�ñ�(theta�����ܩM���禡�X�{�b���Pthread)
		g2d.clearRect(0, 0, width, height);
		g2d.rotate(t, width/2, height/2);
		g2d.fillArc(0, 0, width, height, upperLip, bodyAngle);
		g2d.rotate(-t, width/2, height/2);
	}
	
	public void setDirection(Direction d)
	{
		direction = d;
		
		switch(d) //��L�ڪ�����
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
		//System.out.println(theta);
	}
	
	public void action()
	{
		switch(gscontrol.pac)
		{
			case COMMON:
				upperLip -= mouthMotionSpeed;
				bodyAngle += mouthMotionSpeed*2;
				if(upperLip <= 0 || upperLip >= 40)
				{
					mouthMotionSpeed = -mouthMotionSpeed;
					if(upperLip < 0)
					{
						upperLip = 0;
						bodyAngle = 0;
					}
					else if(upperLip > UPPER_LIP)
					{
						upperLip = UPPER_LIP;
						bodyAngle = BODY_ANGLE;
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
				break;
			case KILLED:
				if(!dying())
				{
					gscontrol.pacmanRetrive();
					upperLip = UPPER_LIP;
					bodyAngle = BODY_ANGLE;
				}
				break;
		}
		drawPacman();
		outOfAreaFix();
		rect.setBounds(x, y, width, height);
		gscontrol.pacPosUpdate(x+width/2, y+height/2);
	}
	public boolean dying() //�������ʵe�A�ʵe�٨S�����Nreturn true�A�ʵe�����F�Nreturn false
	{
		mouthMotionSpeed = 1;
		bodyAngle -= mouthMotionSpeed;
		if(bodyAngle == 0)
			return false;
		else
			return true;
	}
	
	public void hitReact(GameObject g)
	{
		Number num = g.getId();
		switch(num)
		{
			case GHOST:
				if(((Ghost)g).ghostIsShocked() || ((Ghost)g).ghostIsEscaping())
				{}
				else
				{
					gscontrol.pacmanKilled();
				}
				break;
			case POWER_PELLET:
					gscontrol.ghostShock();
				break;
		}
	}
	
	public void paintCanvas(Graphics g)
	{
		action();
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
