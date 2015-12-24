import java.awt.*;
import java.awt.event.*;
public class AlphaTest
{
	static Ghost ghost1 = new Ghost("src\\images\\Ghost1.png");
	static public void main(String args[])
	{
		ghost1.setDirection(GameObject.Direction.UP);
		
		Frame frm = new Frame();
		frm.setSize(800, 800);
		Panel p = new Panel();
		p.setBackground(Color.black);
		ghost1.setBackground(Color.white);
		ghost1.setSize(512,512);
		p.add(ghost1);
		frm.add(p);
		
		ghost1.addMouseMotionListener(new MMLis());
		frm.setVisible(true);
		System.out.println(ghost1.scalingBackWidth(75));
	}
	
	static class MMLis extends MouseMotionAdapter
	{
		public void mouseMoved(MouseEvent e)
		{
			System.out.println(ghost1.getAlpha(e.getX(), e.getY()));
		}
	}
}