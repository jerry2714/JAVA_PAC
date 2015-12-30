import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
 
public class RotationTest extends Canvas {
    public static void main(String[] args) {
        Frame frame = new Frame("AWTDemo");
        frame.addWindowListener(new AdapterDemo());
        frame.setSize(800, 800);
         
        RotationTest canvas = new RotationTest();
        frame.add(canvas);
         
        frame.setVisible(true);
    }
     
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        //g2.draw(new Rectangle(400, 400, 100, 100));
        //g2.rotate(30);
		g2.translate(100,100);
		g2.rotate(Math.PI*30/180);
		g2.setColor(Color.blue);
		g2.draw(new Rectangle(0, 0, 100, 100));
		g2.translate(40, 0);
		g2.draw(new Rectangle(0, 0, 100, 100));
		// g2.translate(-50,-50);
		//g2.translate(-400, -400);
		//g2.rotate(Math.PI*45/180, 50, 50);
		//g2.setColor(Color.red);
        //g2.draw(new Rectangle(0, 0, 100, 100));
    }    
}
 
class AdapterDemo extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}