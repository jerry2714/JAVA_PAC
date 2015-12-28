import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

//�C������A�Ҧ��C���i�椤ø�ϰϤ��i�������~��
//���~��Canvas�A�Ҩ�L�e�������@�Ǥ���]�i�Q��
abstract class GameObject extends GameCanvas implements Comparable<GameObject>, General
{
	private Priority priority;//ø���u���v(�̤j���̫�ø�s�A�ҥH�\�b�̤W��)
	protected BufferedImage img;	//������ثe������ϧ�
	protected BufferedImage symbol;	//������N��ʹϧ�(�Ϊ�����A�j�p��x,y,width,height)
	private Number id;
	protected int ox, oy, x, y;//�W�@���y�СB�s�y��(�ϥΩ�ø�Ϯɶ��j��int)
	protected int width, height;	//���e
	public static enum Direction{CENTER, RIGHT, LEFT, UP, DOWN}//���󲾰ʤ�V�ACENTER�N����
	protected Direction direction = Direction.CENTER;//���󲾰ʤ�V
	protected Rectangle rect = new Rectangle();	//�N���󪺯x��(���N�Oxy�Mwidth height)
	                                            //�s�b���ت��O��������I���i�H��intersection()
	
	
	public void setPriority(Priority a){priority = a;}
	public void setPosition(int x, int y)//�W�r��setLocation�Ϲj�A�]�����~��Canvas�A�i��|�Ψ�
	{
		this.x = x; this.y = y;
		rect.setBounds(x, y, width, height);
	}
	public void setx(int x){this.x = x;}//
	public void sety(int y){this.y = y;}//
	public int getx(){return x;}
	public int gety(){return y;}
	public void setDirection(Direction d){direction = d;}
	public Number getId(){return id;}
	public void setId(Number n){id = n;}
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
		action();		//���I�s�U�۪�action()
		g.drawImage(img, x, y, width, height, this);//�w�]�����ϥ�xy�Mwidth, heightø��
	}
	public void paint(Graphics g)
	{
		g.drawImage(img, (int)x, (int)y, this);
	}
	abstract public void action();	//�~�ӫ��g�A�|�bø�ϫe�I�s
	abstract public void hitReact(Number num);
	public int compareTo(GameObject go)//�Nø���u���v�]���ƧǨ̾�
	{
		return this.priority.ordinal() - go.priority.ordinal();
	}
}
