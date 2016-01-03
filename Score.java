import java.awt.*;

class Score extends GameObject
{
	StringBuffer strBuf = new StringBuffer("score: 0000");
	String str;
	int score = 0;
	int stage = 0;
	public void init()
	{
		str = "0000";
		strBuf.replace(7, 11, str);
		//System.out.println(strBuf);
		setPriority(Priority.ICON);
		stage = 1;
	}
	public void hitReact(GameObject g){}
	public void action()
	{
		if(gscontrol.score != score)
		{
			score = gscontrol.score;
			str = Integer.toString(score);
			strBuf.replace(11-str.length(), 11, str);
		}
		if(gscontrol.oneMoreGhost == false && score > stage * 500)
		{
			gscontrol.oneMoreGhost = true;
			stage++;
		}
	}
	public void paintCanvas(Graphics g)
	{
		action();
		g.setColor(Color.black);
		g.drawString(strBuf.toString(), 5, 10);
	}
	// static public void main(String args[])
	// {
		// Score s = new Score();
		// s.init();
		//System.out.println(s.strBuf.toString());
		// Frame frm = new Frame();
		// frm.setSize(300, 300);
		
		
		// g.drawString(s.strBuf.toString(), 30, 30);
		
		// frm.setVisible(true);
		// Graphics g = frm.getGraphics();
	// }
	
	
}