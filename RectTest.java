import java.util.*;
import java.awt.*;

public class RectTest
{
	public static void main(String args[])
	{
		Rectangle r1 = new Rectangle(60, 60, 100, 200);
		Rectangle r2 = new Rectangle(50, 50, 100, 200);
		
		Rectangle r3 = r2.intersection(r1);
		Rectangle r4 = new Rectangle(0, 0, 5, 1);
		
		System.out.println(r1);
		System.out.println(r2);
		System.out.println(r3.getWidth());
		System.out.println(r3);
		
		if(r4.isEmpty())
			System.out.println("empty");
	}
}