import java.awt.*;

class Poly extends Canvas
{
	int x[] = {20, 500, 500, 20};
	int y[] = {20,  20, 500, 500};
	
	public void paint(Graphics g)
	{
		g.clipRect(50, 50, 100, 100);
		g.fillPolygon(x, y, x.length);
	}
}

public class PolygonTest
{
	static public void main(String args[])
	{
		Frame frm = new Frame();
		Poly p = new Poly();
		
		frm.setSize(1200, 800);
		frm.add(p);
		
		frm.setVisible(true);
	}
}