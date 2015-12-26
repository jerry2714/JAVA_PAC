import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

//�C������A�Ҧ��C���i�椤ø�ϰϤ��i�������~��
//���~��Canvas�A�Ҩ�L�e�������@�Ǥ���]�i�Q��
class GameObject extends GameCanvas implements Comparable<GameObject>, General
{
	private int priority;//ø���u���v(�̤j���̫�ø�s�A�ҥH�\�b�̤W��)
	protected BufferedImage img;	//������ثe������ϧ�
	protected BufferedImage symbol;	//������N��ʹϧ�(�Ϊ�����A�j�p��x,y,width,height)
	protected int ox, oy, x, y;//�W�@���y�СB�s�y��(�ϥΩ�ø�Ϯɶ��j��int)
	protected int width, height;	//���e
	public static enum Direction{CENTER, RIGHT, LEFT, UP, DOWN}//���󲾰ʤ�V�ACENTER�N����
	protected Direction direction = Direction.CENTER;//���󲾰ʤ�V
	protected Rectangle rect = new Rectangle();
	public void setPriority(int a){priority = a;}
	public void setPosition(int x, int y){this.x = x; this.y = y;}//�W�r��setLocation�Ϲj�A�]�����~��Canvas�A�i��|�Ψ�
	public void setx(int x){this.x = x;}//
	public void sety(int y){this.y = y;}//
	public int getx(){return x;}
	public int gety(){return y;}
	public void setDirection(Direction d){direction = d;}
	
	public int getAlpha(int x, int y)//���o�Ӯy���I��(��)Alpha��
	{
		return (img.getRGB(x, y) >> 24);	//BufferedImage��getRGB�^�Ǫ�color model
		                                    //�OARGB�A�qMSB�}�l��8bits�N��Alpha�ȡA
											//�ҥH�k��24bits���oAlpha��
											//(�S���ܽT�w�A���i��ҥH���ӨS���A���L���줸�ɦ쪺���D�A
											//�ҥH���u��ΨӧP�_�O�_��0(�Y�z��))
	}
	public int getSymbolAlpha(int x, int y){return (symbol.getRGB(x, y) >> 24);}
	public int scalingBackWidth(int n)		//�ھڥثeø�s�Ϊ��Ϯפj�p�M��Ϥj�p���������
	{                                       //�h�N�@�Ӫ��ש�j�^�Ӫ��צb��Ϥ����j�p  
		return (int)(n*img.getWidth()/width);
	}
	public int scalingBackHeight(int n)
	{
		return (int)(n*img.getHeight()/height);
	}
	public Rectangle getRect(){return rect;}
	public void paintCanvas(Graphics g)//�w�]�C������ø�Ϥ�k
	{
		/*if(img == null)
			System.out.println("ghost missed");*/
		move();		//���I�s�U�۪�move()
		g.drawImage(img, (int)x, (int)y, width, height, this);//�w�]�����ϥ�xy�Mwidth, heightø��
	}
	public void paint(Graphics g)
	{
		g.drawImage(img, (int)x, (int)y, this);
	}
	public void move(){}	//�~�ӫ��g�A�|�bø�ϫe�I�s
	public int compareTo(GameObject go)//�Nø���u���v�]���ƧǨ̾�
	{
		return this.priority - go.priority;
	}
}
