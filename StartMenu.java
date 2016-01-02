import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

class StartMenu extends Panel implements KeyListener, MouseMotionListener, MouseListener
{
	
	TheButton startBtn = new TheButton("開始");
	TheButton exitBtn = new TheButton("離開");
	int frameWidth, frameHeight;
	public StartMenu()
	{
		setLayout(null);
		setBackground(Color.white);
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
	//String exit = "離開";
	Font font = new Font("標楷體", Font.BOLD, 32);
	Color color = Color.gray;
	
	public TheButton(String str)
	{
		setBackground(color);
		this.str = str;
	}
	public void paint(Graphics g)
	{
		g.setFont(font);
		int stringLen = (int) g.getFontMetrics().getStringBounds(str, g).getWidth();
		int stringHi = (int) g.getFontMetrics().getStringBounds(str, g).getHeight();
        int x = getWidth()/2 - stringLen/2;
        int y = getHeight()/2 + stringHi/3; // 這邊/3看起來比/2還像是置中，原因未知
        g.drawString(str, x, y);
	}
	public void mouseClicked (MouseEvent e)
	{
		if(str == "離開")
			RunningPacman.frm.exit();
		else if(str == "開始")
		{
			
		}
	}
	public void mouseEntered (MouseEvent e){color = color.brighter();setBackground(color);}
	public void mouseExited  (MouseEvent e){color = color.darker();setBackground(color);}
	public void mousePressed (MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
}