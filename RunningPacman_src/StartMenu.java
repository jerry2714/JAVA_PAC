import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

class StartMenu extends Panel implements KeyListener, MouseMotionListener, MouseListener
{
	
	TheButton startBtn = new TheButton("�}�l", null, null, Color.yellow);
	TheButton exitBtn = new TheButton("���}", null, null, Color.yellow);
	int frameWidth, frameHeight;
	public StartMenu()
	{
		setLayout(null);
		setBackground(Color.black);
		add(startBtn);
		add(exitBtn);
		startBtn.addMouseListener(startBtn);
		exitBtn.addMouseListener(exitBtn);
	}
	public void init()
	{
		frameWidth = getWidth(); frameHeight = getHeight();
		startBtn.setBounds(frameWidth/2-50, frameHeight/5*3, 100, 60);
		exitBtn.setBounds(frameWidth/2-50, frameHeight/5*3+70, 100, 60);
	}
	public void paint(Graphics g)
	{
		String str = "Running Pacman";
		g.setColor(Color.yellow);
		g.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 55));
		int stringLen = (int) g.getFontMetrics().getStringBounds(str, g).getWidth();
		int stringHi = (int) g.getFontMetrics().getStringBounds(str, g).getHeight();
        int x = getWidth()/2 - stringLen/2;
        g.drawString(str, x, 100);
	}
	
	public void mouseMoved   (MouseEvent e){}
	public void mouseDragged (MouseEvent e){}
	public void mouseClicked (MouseEvent e){}
	public void mouseEntered (MouseEvent e){}
	public void mouseExited  (MouseEvent e){}
	public void mousePressed (MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void keyPressed (KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public void keyTyped    (KeyEvent e){}
}
class GameOptionMenu extends Panel implements KeyListener, MouseMotionListener, MouseListener
{
	static Font font = new Font("", Font.BOLD, 16);
	TheButton resumetBtn = new TheButton("�~��", font, null);
	TheButton exitBtn = new TheButton("�����C��", font, null);
	int frameWidth, frameHeight;
	public GameOptionMenu()
	{
		
		setBackground(Color.white);
		add(resumetBtn);
		add(exitBtn);
		resumetBtn.addMouseListener(resumetBtn);
		exitBtn.addMouseListener(exitBtn);
	}
	public void init()
	{
		frameWidth = getWidth(); frameHeight = getHeight();
		resumetBtn.setSize(100, 60);
		exitBtn.setSize(100, 60);
	}
	
	public void mouseMoved   (MouseEvent e){}
	public void mouseDragged (MouseEvent e){}
	public void mouseClicked (MouseEvent e){}
	public void mouseEntered (MouseEvent e){}
	public void mouseExited  (MouseEvent e){}
	public void mousePressed (MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void keyPressed (KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public void keyTyped    (KeyEvent e){}
}
class TheButton extends GameObject implements MouseListener
{
	String str;
	//String exit = "���}";
	Font font = new Font("�з���", Font.BOLD, 32);
	Color bgColor = Color.gray;
	Color textColor = Color.black;
	
	public TheButton(String str)
	{
		setBackground(bgColor);
		this.str = str;
	}

	public TheButton(String str, Font font, Color c)
	{
		if(c == null)
			setBackground(bgColor);
		else
			setBackground(c);
		if(font != null)
			this.font = font;
		this.str = str;
	}
	public TheButton(String str, Font font, Color bc, Color c)
	{
		if(bc == null)
			setBackground(bgColor);
		else
			setBackground(bc);
		if(c == null)
			textColor = Color.black;
		else 
			textColor = c;
		if(font != null)
			this.font = font;
		this.str = str;
	}
	public void paint(Graphics g)
	{
		g.setFont(font);
		g.setColor(textColor);
		int stringLen = (int) g.getFontMetrics().getStringBounds(str, g).getWidth();
		int stringHi = (int) g.getFontMetrics().getStringBounds(str, g).getHeight();
        int x = getWidth()/2 - stringLen/2;
        int y = getHeight()/2 + stringHi/3; // �o��/3�ݰ_�Ӥ�/2�ٹ��O�m���A��]����
        g.drawString(str, x, y);
	}
	public void mouseClicked (MouseEvent e)
	{
		if(str.equals("���}"))
			RunningPacman.exit();
		else if(str.equals("�}�l"))
		{
			RunningPacman.gameStart();
		}
		else if(str.equals("�~��"))
		{
			RunningPacman.game.gamePauseSwitch();
		}
		else if(str.equals("�����C��"))
		{
			RunningPacman.show("StartMenu");
			RunningPacman.game.gameExit();
		}
	}
	public void mouseEntered (MouseEvent e){bgColor = bgColor.brighter();setBackground(bgColor);}
	public void mouseExited  (MouseEvent e){bgColor = bgColor.darker();setBackground(bgColor);}
	public void mousePressed (MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
}