import java.util.*;

class TTask extends TimerTask
{
	TThread t = new TThread();
	public void run()
	{
		System.out.println("TTask");
		t.start();
	}
}
class TThread extends Thread
{
	public void run()
	{
		System.out.println("TThread");
	}
}
public class TimerTest
{
	static public void main(String args[])
	{
		// Timer t = new Timer();
		// TTask tt = new TTask();
		// t.schedule(tt, 0, 1000);
		
		TThread t = new TThread();
		t.start();
		t.start();
	}
}