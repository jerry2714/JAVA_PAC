import java.awt.*;
import java.awt.event.*;
public class AlphaTest
{
	static Ghost ghost1 = new Ghost("src\\images\\Ghost1.png");
	static Pacman pacman = new Pacman();
	static public void main(String args[])
	{
		pacman.setDirection(GameObject.Direction.UP);
		
		Frame frm = new Frame();
		frm.setSize(800, 800);
		Panel p = new Panel();
		p.setBackground(Color.black);
		pacman.setBackground(Color.white);
		pacman.setSize(200,200);
		p.add(pacman);
		frm.add(p);
		
		pacman.addMouseMotionListener(new MMLis());
		frm.setVisible(true);
		// System.out.println(ghost1.scalingBackWidth(75));
	}
	
	static class MMLis extends MouseMotionAdapter
	{
		public void mouseMoved(MouseEvent e)
		{
			System.out.println(pacman.getAlpha(e.getX(), e.getY()));
		}
	}
}