import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class RunningPacman extends Frame implements WindowListener
{
	public RunningPacman(){}
	public RunningPacman(String str){super(str);}
	
	
	static Game game = new Game();
	public static void main(String args[])
	{
		RunningPacman frm = new RunningPacman("Running Pacman");
		frm.setResizable(false);
		
		
		
		frm.setSize(800, 600);
		//frm.setLocation(100, 100);
		
		frm.setBackground(new Color(255, 255, 0));
		frm.addWindowListener(frm);
		
		frm.add(game);
		
		frm.setVisible(true);
		game.init();
		game.gameStart();
		//game.gamePause();

	}
	
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
	public void windowClosed(WindowEvent e)     {}
	public void windowOpened(WindowEvent e)     {}
	public void windowIconified(WindowEvent e)  {}
	public void windowDeiconified(WindowEvent e){}
	public void windowDeactivated(WindowEvent e){}
	
}