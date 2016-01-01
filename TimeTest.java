import java.time.*;

public class TimeTest
{
	public static void main(String args[])
	{
		Instant i1 = null, i2 = null;
		i1 = i1.now();
		i2 = i2.now();
		i2 = i2.plus(Duration.ofSeconds(1));
		if(i1.equals(i2))
			System.out.println("Y");
		System.out.println(i1);
		System.out.println(i2);
		System.out.println(i2.minus(Duration.ofSeconds(10)));
		
		if(i2.minus(Duration.ofSeconds(10)).isA)
	}
}