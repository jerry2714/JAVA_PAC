import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class RunningGhost extends Frame
{
	public RunningGhost(){}
	public RunningGhost(String str){super(str);}
	
	
	static Game game = new Game();
	public static void main(String args[])
	{
		RunningGhost frm = new RunningGhost("Running Ghost");
		frm.setResizable(false);
		
		
		
		frm.setSize(800, 600);
		frm.setLocation(100, 100);
		
		frm.setBackground(new Color(255, 255, 0));
		frm.addWindowListener(new WinLis());
		
		frm.add(game);
		
		frm.setVisible(true);
		game.init();
		game.gameStart();
		//game.gamePause();

	}
	
	
	static class WinLis extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			Frame frm = (Frame)e.getSource();
			game.gameExit();
			frm.dispose();
			
		}
		public void windowActivated(WindowEvent e)
		{
			Frame frm = (Frame)e.getSource();
			game.requestFocus();
		}
	}
}