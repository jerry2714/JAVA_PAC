import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;



class Game extends Panel implements Initialize, KeyListener, General
{
	GameLoop gl;
	Pacman pacman;
	Background bg;
	Foreground fg;
	Dot dot;
	PowerPellet powerPellet;
	Score score;
	GameOptionMenu goMenu;
	int frameWidth, frameHeight;
	
	Timer timer;
	Game()
	{
		
	}
	public void setFrameSize(int width, int height)
	{
		frameWidth = width;
		frameHeight = height;
		gl.setFrameSize(width, height);
	}
	public void init()
	{
		timer = new Timer();
		gl = new GameLoop();
		pacman = new Pacman();
		bg = new Background();
		fg = new Foreground();
		dot = new Dot();
		powerPellet = new PowerPellet();
		score = new Score();
		goMenu = new GameOptionMenu();
		
		setFrameSize(getWidth(), getHeight());
		gl.init();
		gl.addToDrawList(pacman);
		gl.addToDrawList(dot);
		gl.addToDrawList(bg);
		gl.addToDrawList(fg);
		gl.addToDrawList(powerPellet);
		gl.addToDrawList(score);
		
		gl.addToMovingList(pacman);
		gl.addToMovingList(dot);
		gl.addToMovingList(powerPellet);
		
		for(int i = 1; i < 5; i++)
		{
			Ghost g = new Ghost();
			gl.addToDrawList(g);
			gl.addToMovingList(g);
			g.setPosition(i*100, 400);
			g.setDirection(GameObject.Direction.CENTER);
		}
		
		pacman.setDirection(GameObject.Direction.CENTER);
		pacman.setPosition(50, 50);
		bg.init();
		fg.init();
		score.init();
		dot.changePos();
		gscontrol.init();
		goMenu.setBounds(frameWidth/2-150, frameHeight/2-150, 300, 300);
		goMenu.init();
		this.setLayout(null);
		this.add(goMenu);
		this.add(gl.getCanvas());
		goMenu.setVisible(false);
		this.addKeyListener(this);
	}
	public void gameStart()
	{
		
		this.requestFocus();
		// timer.purge();
		//timer.scheduleAtFixedRate(gl, 0, 10);
		gscontrol.init();
		timer.schedule(gl, 0, 15);
		gl.pauseSwitch();
	}
	public void gamePauseSwitch()
	{
		gl.pauseSwitch();
		if(goMenu.isVisible())
		{
			goMenu.setVisible(false);
			this.requestFocus();
		}
		else
			goMenu.setVisible(true);
		System.out.println("pause");
	}
	public void gameExit()
	{
		
		try
		{
			timer.cancel();
			gl.exit();
			this.remove(goMenu);
			this.remove(gl.getCanvas());
			timer = null;
			gl = null;
			pacman = null;
			bg = null;
			fg = null;
			dot = null;
			powerPellet = null;
			score = null;
			goMenu = null;
			this.removeKeyListener(this);
		}
		catch(NullPointerException e){}
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
				gamePauseSwitch();
				break;
		}
	}
	public void keyReleased(KeyEvent e)
	{
	}
	public void keyTyped(KeyEvent e){}
}

class GameLoop extends TimerTask implements Initialize, General //基本上是遊戲迴圈
{
	static GameCanvas canvas = new GameCanvas();
	LinkedList<GameObject> drawList = new LinkedList<GameObject>();//存放所有遊戲中物件的串列
	Iterator<GameObject> drawListItr;//list的Iterator
	
	LinkedList<GameObject> movingList = new LinkedList<GameObject>();//存放遊戲中會移動的物件的串列
	Iterator<GameObject> movingListItr;//list的Iterator
	
	int frameWidth, frameHeight;
	
	HitDetecter hd = new HitDetecter();
	public void setFrameSize(int width, int height)
	{
		frameWidth = width; frameHeight = height;
		canvas.setFrameSize(width, height);
		canvas.setBounds(0, 0, width, height);
	}
	public void init()
	{
		drawList.clear();
		movingList.clear();
		canvas.init();
		canvas.setEnabled(false);
	}
	public GameCanvas getCanvas()
	{
		return canvas;
	}
	public void addToDrawList(GameObject go)//將一個GameObject加入串列
	{
		drawList.add(go);
	}
	public void addToMovingList(GameObject go)//將一個GameObject加入串列
	{
		movingList.add(go);
	}
	public void listSort()//將list中的元素根據優先權進行排序
	{
		Collections.sort(drawList);
		Collections.sort(movingList);
	} 
	public void run()
	{
		if(!gscontrol.isPause())
		{
			//System.out.println(this.scheduledExecutionTime());
			listSort();
			drawListItr = drawList.iterator();
			while(drawListItr.hasNext())
				canvas.draw(drawListItr.next());
			canvas.update(canvas.getGraphics());
			hd.run();
			if(gscontrol.oneMoreGhost)
			{
				Ghost g = new Ghost();
				drawList.add(g);
				movingList.add(g);
				g.setPosition(0, frameHeight);
				g.setDirection(GameObject.Direction.CENTER);
				gscontrol.oneMoreGhost = false;
			}
			if(gscontrol.pac == gscontrol.pac.RETRIVE)
			{
				gscontrol.pac = gscontrol.pac.COMMON;
				
				gscontrol.life--;
				if(gscontrol.life == 0)
				{
					gameOver();
					return;
				}
				linedUp();
			}
			
		}
	}
	void gameOver()
	{
		Graphics g = canvas.getGraphics();
		g.setColor(Color.yellow);
		g.setFont(new Font("", Font.BOLD, 50));
		g.drawString("GAME OVER", frameWidth/2, frameHeight/2);
		try
		{
			Thread.sleep(2000);
			RunningPacman.show("StartMenu");
			RunningPacman.game.gameExit();
			
		}
		catch(Exception e){System.out.println("interupt erreor");}
	}
	public void linedUp()
	{
		listSort();
		movingListItr = movingList.iterator();
		GameObject g;
		Number num;
		int i = 0;
		while(movingListItr.hasNext())
		{
			g = movingListItr.next();
			num = g.getId();
			
			switch(num)
			{
				case PACMAN:
					g.setPosition(frameWidth/2, 100);
					break;
				case GHOST:
					i++;
					g.setPosition(i*60, 400);
					g.init();
					System.out.println("y");
					break;
			}
		}
		System.out.println("in");
	}
	
	public void pauseSwitch()
	{
		if(gscontrol.isPause())
			gscontrol.resume();
		else
			gscontrol.pause();
	}
	public void exit()
	{
		drawList.clear();
		movingList.clear();
	}
	
	class HitDetecter //extends Thread
	{
		public boolean hitDetect(GameObject o1, GameObject o2) //偵測物體碰撞
		{
			int x1, y1;
			int x2, y2;
			int w, h;
			Rectangle r = o1.getRect().intersection(o2.getRect());
			if(!r.isEmpty())
			{
				x1 = (int)(r.getX()-o1.getx());//重疊區左上角在o1裡的座標
				y1 = (int)(r.getY()-o1.gety());
				x2 = (int)(r.getX()-o2.getx());//重疊區左上角在o2裡的座標
				y2 = (int)(r.getY()-o2.gety());
				w = (int)r.getWidth();
				h = (int)r.getHeight();
				
				int tx1, ty1, tx2, ty2;
				ty1 = y1; ty2 = y2;
				for(int i = 0; i < h; i++)
				{
					tx1 = x1; tx2 = x2;
					try{
					for(int j = 0; j < w; j++)
					{
						if(o1.getSymbolAlpha(tx1, ty1) != 0 && o2.getSymbolAlpha(tx2, ty2) != 0)
							return true;
						tx1++; tx2++;
					}
					ty1++; ty2++;}
					catch(NullPointerException e)
					{
						System.out.println(r.getX()+" "+o1.getx());
						System.out.println(r.getY()+" "+o1.gety());
						System.out.println(x1+" "+y1+" "+x2+" "+y2);
						System.out.println(tx1+" "+ty1+" "+tx2+" "+ty2);
						System.exit(0);
					}
				}
			}
			return false;
		}
		public void run()//iterate過程需修改(註1)
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
						if(hitDetect(o1, o2) && o1 != o2)//註1:有o1==o2的問題，浪費時間
						{
							o1.hitReact(o2);
							o2.hitReact(o1);
						}
						
					}
				}
				catch(NoSuchElementException e)
				{
					System.out.println("movingList iterating error");
				}
			}
		}
	}
}

