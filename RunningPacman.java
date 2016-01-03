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
	
	
	public static Game game = new Game();
	public static StartMenu stmu = new StartMenu();
	public static CardLayout card = new CardLayout();
	public static RunningPacman frm = new RunningPacman("Running Pacman");
	
	public static void main(String args[])
	{
		System.gc();
		frm.setResizable(false);
		
		
		
		frm.setSize(800, 600);
		frm.setLayout(card);
		
		//frm.setBackground(new Color(255, 255, 0));
		frm.addWindowListener(frm);
		
		frm.add(game, "Game");
		frm.add(stmu, "StartMenu");
		frm.setVisible(true);
		
		stmu.init();
		card.show(frm, "StartMenu");

	}
	static public void gameStart()
	{
		game.init();
		card.show(frm, "Game");
		game.gameStart();
	}
	static public void show(String str)
	{
		card.show(frm, str);
	}
	static public void exit()
	{
		game.gameExit();
		frm.dispose();
	}
	public void windowClosing(WindowEvent e)
	{
		exit();
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