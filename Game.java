import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;

interface Initialize	//為了使繪圖區大小在程式開始時跟視窗一致(世界麻煩，目前想不到好意點的方法)
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
class GameLoop extends TimerTask implements Initialize //基本上是繪圖迴圈
{
	static GameCanvas canvas = new GameCanvas();
	LinkedList<GameObject> list = new LinkedList<GameObject>();//存放所有遊戲中物件的串列
	Iterator<GameObject> listItr;//list的Iterator
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
	public void addToList(GameObject go)//將一個GameObject加入串列
	{
		list.add(go);
	}
	public void listSort()//將list中的元素根據優先權進行排序
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
	private static BufferedImage img = null;//繪圖用
	private static Graphics2D g2d;//img的Graphic context
	public static int frameWidth, frameHeight;
	
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
	private int priority;//繪圖優先權(最大的最後繪製，所以蓋在最上面)
	BufferedImage img;	//此物件目前的完整圖形
	int ox, oy, x, y;	//上一次座標、新座標
	int width, height;	//長寬
	public static enum Direction{CENTER, RIGHT, LEFT, UP, DOWN}//物件移動方向，CENTER代表不動
	Direction direction = Direction.CENTER;//物件移動方向
	public void setPriority(int a){priority = a;}
	public void setPosition(int x, int y){this.x = x; this.y = y;}//名字跟setLocation區隔，因為有繼承Canvas，可能會用到
	public void setDirection(Direction d){direction = d;}
	public int getAlpha(int x, int y)//取得該座標點的(偽)Alpha值
	{
		return (img.getRGB(x, y) >> 24);	//BufferedImage的getRGB回傳的color model
		                                    //是ARGB，從MSB開始算8bits代表Alpha值，
											//所以右移24bits取得Alpha值
											//(沒有很確定，但可行所以應該沒錯，不過有位元補位的問題，
											//所以其實只能用來判斷是否為0(即透明))
	}
	public int scalingBackWidth(int n)		//根據目前繪製用的圖案大小和原圖大小之間的比例
	{                                       //去將一個長度放大回該長度在原圖中的大小  
		return (int)(n*img.getWidth()/width);
	}
	public int scalingBackHeight(int n)
	{
		return (int)(n*img.getHeight()/height);
	}
	// public void show1(){System.out.println(priority);}
	
	public int compareTo(GameObject go)//將繪圖優先權設為排序依據
	{
		return this.priority - go.priority;
	}
}
