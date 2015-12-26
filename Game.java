import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;

interface Initialize	
{
	public void init();
	public void setFrameSize(int width, int height);//���F��ø�ϰϤj�p�b�{���}�l�ɸ�����@�P(�@�ɳ·СA�ثe�Q����n�@�I����k)
}
interface General	//�q���ܼ�
{
	Color transparent = new Color(0, 0, 0, 0);//�z����
}
class Game extends Panel implements Initialize, KeyListener
{
	GameLoop gl = new GameLoop();
	Ghost ghost1 = new Ghost("src\\images\\Ghost1.png");
	Ghost ghost2 = new Ghost("src\\images\\Ghost2.png");
	Pacman pacman = new Pacman();
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
		gl.addToDrawList(ghost1);
		gl.addToDrawList(ghost2);
		gl.addToDrawList(pacman);
		gl.addToDrawList(bg);
		
		gl.addToMovingList(ghost1);
		gl.addToMovingList(ghost2);
		gl.addToMovingList(pacman);
		
		ghost1.setDirection(GameObject.Direction.CENTER);
		ghost2.setDirection(GameObject.Direction.CENTER);
		pacman.setDirection(GameObject.Direction.CENTER);
		pacman.setPosition(50, 50);
		bg.init();
		ghost2.setPosition(300, 500);
		ghost1.setPosition(600, 500);
		
		
		this.addKeyListener(this);
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
		//timer.scheduleAtFixedRate(gl, 0, 10);
		timer.schedule(gl, 0, 10);
		gl.pauseSwitch();
	}
	public void gameExit()
	{
		timer.cancel();
	}
	
	
	public void keyPressed(KeyEvent e)
	{
		int k = e.getKeyCode();
		switch(k)
		{
			case KeyEvent.VK_UP:
				pacman.setDirection(GameObject.Direction.UP);
				break;
			case KeyEvent.VK_DOWN:
				pacman.setDirection(GameObject.Direction.DOWN);
				break;
			case KeyEvent.VK_LEFT:
				pacman.setDirection(GameObject.Direction.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				pacman.setDirection(GameObject.Direction.RIGHT);
				break;
			case KeyEvent.VK_ESCAPE:
				gl.pauseSwitch();
				break;
		}
	}
	public void keyReleased(KeyEvent e)
	{
	}
	public void keyTyped(KeyEvent e){}
}
class GameLoop extends TimerTask implements Initialize //�򥻤W�Oø�ϰj��
{
	static GameCanvas canvas = new GameCanvas();
	LinkedList<GameObject> drawList = new LinkedList<GameObject>();//�s��Ҧ��C�������󪺦�C
	Iterator<GameObject> drawListItr;//list��Iterator
	
	LinkedList<GameObject> movingList = new LinkedList<GameObject>();//�s��C�����|���ʪ����󪺦�C
	Iterator<GameObject> movingListItr;//list��Iterator
	boolean isRunning = false;
	
	HitDetecter hd = new HitDetecter();
	public void setFrameSize(int width, int height)
	{
		canvas.setFrameSize(width, height);
	}
	public void init()
	{
		drawList.clear();
		canvas.init();
		canvas.setEnabled(false);
	}
	public GameCanvas getCanvas()
	{
		return canvas;
	}
	public void addToDrawList(GameObject go)//�N�@��GameObject�[�J��C
	{
		drawList.add(go);
	}
	public void addToMovingList(GameObject go)//�N�@��GameObject�[�J��C
	{
		movingList.add(go);
	}
	public void listSort()//�Nlist���������ھ��u���v�i��Ƨ�
	{
		Collections.sort(drawList);
		Collections.sort(movingList);
	} 
	public void run()
	{
		if(isRunning)
		{
			listSort();
			drawListItr = drawList.iterator();
			while(drawListItr.hasNext())
			{
				canvas.draw(drawListItr.next());
			}
			canvas.update(canvas.getGraphics());
			hd.run();
		}
	}
	public void pauseSwitch()
	{
		if(isRunning)
			isRunning = false;
		else
			isRunning = true;
	}
	class HitDetecter extends Thread
	{
		public boolean hitDetect(GameObject o1, GameObject o2)
		{
			int x1, y1;
			int x2, y2;
			int w, h;
			Rectangle r = o1.getRect().intersection(o2.getRect());
			if(!r.isEmpty())
			{
				x1 = (int)(r.getX()-o1.getx());
				x2 = (int)(r.getX()-o2.getx());
				y1 = (int)(r.getY()-o1.gety());
				y2 = (int)(r.getY()-o2.gety());
				w = (int)r.getWidth();
				h = (int)r.getHeight();
				
				int tx1, ty1, tx2, ty2;
				ty1 = y1; ty2 = y2;
				for(int i = 0; i < h; i++)
				{
					tx1 = x1; tx2 = x2;
					for(int j = 0; j < w; j++)
					{
						if(o1.getSymbolAlpha(tx1, ty1) != 0 && o2.getSymbolAlpha(tx2, ty2) != 0)
							return true;
						tx1++; tx2++;
					}
					ty1++; ty2++;
				}
			}
			return false;
		}
		public void run()//iterate�L�{�ݭק�(��1)
		{
			Iterator<GameObject> itr1, itr2;
			itr1 = movingList.iterator();
			int j = 0;
			GameObject o1, o2;
			while(itr1.hasNext())
			{
				j++;
				itr2 = movingList.iterator();
				try
				{
					for(int i = 0; i < j; i++)
						itr2.next();
					o1 = itr1.next();
					while(itr2.hasNext())
					{
						o2 = itr2.next();
						if(hitDetect(o1, o2) && o1 != o2)//��1:��o1==o2�����D�A���O�ɶ�
						{
							
						}
						
					}
				}
				catch(NoSuchElementException e)
				{
					System.out.println("movingList iterating error");
				}
			}
			// System.out.println("over");
			
		}
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
	//public void paintCanvas(Graphics2D g){}//���Ҧ��l���O��g2d�i��ާ@(ø��)
	
	
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
