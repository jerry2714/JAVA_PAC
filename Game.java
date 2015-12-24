import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;

interface Initialize	//���F��ø�ϰϤj�p�b�{���}�l�ɸ�����@�P(�@�ɳ·СA�ثe�Q����n�N�I����k)
{
	public void init();
	public void setFrameSize(int width, int height);
}
class Game extends Panel implements Initialize
{
	GameLoop gl = new GameLoop();
	Ghost ghost1 = new Ghost("src\\images\\Ghost1.png");
	Ghost ghost2 = new Ghost("src\\images\\Ghost2.png");
	Background bg = new Background();
	int frameWidth, frameHeight;
	
	Timer timer = new Timer();
	Game()
	{
		this.setLayout(new BorderLayout());
		this.add(gl.getCanvas());
	}
	public void setFrameSize(int width, int height)
	{
		frameWidth = width;
		frameHeight = height;
		gl.setFrameSize(width, height);
	}
	public void init()
	{
		setFrameSize(getWidth(), getHeight());
		gl.init();
		gl.addToList(ghost1);
		gl.addToList(ghost2);
		gl.addToList(bg);
		ghost1.setDirection(GameObject.Direction.UP);
		ghost2.setDirection(GameObject.Direction.UP);
		bg.init();
		ghost2.setPosition(300, 500);
		ghost1.setPosition(600, 500);
		
		this.addKeyListener(new KeyLis());
	}
	public void gameStart()
	{
		// if(gl.getCanvas().isVisible())
		// {
			// System.out.println("true");
			// System.out.println(gl.getCanvas().getCanvasWidth());
		// }
		this.requestFocus();
		// timer.purge();
		timer.scheduleAtFixedRate(gl, 0, 1);
		gl.pauseSwitch();
	}
	public void gameExit()
	{
		timer.cancel();
	}
	
	class KeyLis extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			int k = e.getKeyCode();
			switch(k)
			{
				case KeyEvent.VK_UP:
					ghost1.setDirection(GameObject.Direction.UP);
					break;
				case KeyEvent.VK_DOWN:
					ghost1.setDirection(GameObject.Direction.DOWN);
					break;
				case KeyEvent.VK_LEFT:
					ghost1.setDirection(GameObject.Direction.LEFT);
					break;
				case KeyEvent.VK_RIGHT:
					ghost1.setDirection(GameObject.Direction.RIGHT);
					break;
				case KeyEvent.VK_ESCAPE:
					gl.pauseSwitch();
					break;
			}
		}
		public void keyReleased(KeyEvent e)
		{
		}
	}
}
class GameLoop extends TimerTask implements Initialize //�򥻤W�Oø�ϰj��
{
	static GameCanvas canvas = new GameCanvas();
	LinkedList<GameObject> list = new LinkedList<GameObject>();//�s��Ҧ��C�������󪺦�C
	Iterator<GameObject> listItr;//list��Iterator
	boolean isRunning = false;
	public void setFrameSize(int width, int height)
	{
		canvas.setFrameSize(width, height);
	}
	public void init()
	{
		list.clear();
		canvas.init();
	}
	public GameCanvas getCanvas()
	{
		return canvas;
	}
	public void addToList(GameObject go)//�N�@��GameObject�[�J��C
	{
		list.add(go);
	}
	public void listSort()//�Nlist���������ھ��u���v�i��Ƨ�
	{
		Collections.sort(list);
	} 
	public void run()
	{
		if(isRunning)
		{
			listSort();
			listItr = list.iterator();
			while(listItr.hasNext())
			{
				canvas.draw(listItr.next());
			}
			canvas.update(canvas.getGraphics());
		}
	}
	public void pauseSwitch()
	{
		if(isRunning)
			isRunning = false;
		else
			isRunning = true;
	}

	
}
class GameCanvas extends Canvas implements Initialize
{
	private static BufferedImage img = null;//ø�ϥ�
	private static Graphics2D g2d;//img��Graphic context
	public static int frameWidth, frameHeight;
	
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
		//System.out.println("update");
		paint(g);
	}
	public void paint(Graphics g)
	{
		//setBackground(new Color(255, 0, 0));
		//System.out.println("paint");
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}
	
	public void draw(GameObject go)
	{
		go.paintCanvas(g2d);
		//System.out.println("draw");
	}
}

class GameObject extends GameCanvas implements Comparable<GameObject>
{
	private int priority;//ø���u���v(�̤j���̫�ø�s�A�ҥH�\�b�̤W��)
	BufferedImage img;	//������ثe������ϧ�
	int ox, oy, x, y;	//�W�@���y�СB�s�y��
	int width, height;	//���e
	public static enum Direction{CENTER, RIGHT, LEFT, UP, DOWN}//���󲾰ʤ�V�ACENTER�N����
	Direction direction = Direction.CENTER;//���󲾰ʤ�V
	public void setPriority(int a){priority = a;}
	public void setPosition(int x, int y){this.x = x; this.y = y;}//�W�r��setLocation�Ϲj�A�]�����~��Canvas�A�i��|�Ψ�
	public void setDirection(Direction d){direction = d;}
	public int getAlpha(int x, int y)//���o�Ӯy���I��(��)Alpha��
	{
		return (img.getRGB(x, y) >> 24);	//BufferedImage��getRGB�^�Ǫ�color model
		                                    //�OARGB�A�qMSB�}�l��8bits�N��Alpha�ȡA
											//�ҥH�k��24bits���oAlpha��
											//(�S���ܽT�w�A���i��ҥH���ӨS���A���L���줸�ɦ쪺���D�A
											//�ҥH���u��ΨӧP�_�O�_��0(�Y�z��))
	}
	public int scalingBackWidth(int n)		//�ھڥثeø�s�Ϊ��Ϯפj�p�M��Ϥj�p���������
	{                                       //�h�N�@�Ӫ��ש�j�^�Ӫ��צb��Ϥ����j�p  
		return (int)(n*img.getWidth()/width);
	}
	public int scalingBackHeight(int n)
	{
		return (int)(n*img.getHeight()/height);
	}
	// public void show1(){System.out.println(priority);}
	
	public int compareTo(GameObject go)//�Nø���u���v�]���ƧǨ̾�
	{
		return this.priority - go.priority;
	}
}
