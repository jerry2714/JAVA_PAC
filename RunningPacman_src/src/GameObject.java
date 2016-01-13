import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

//遊戲物件，所有遊戲進行中繪圖區內可視物需繼承
//有繼承Canvas，所其他畫面中的一些元件也可利用
abstract class GameObject extends GameCanvas implements Comparable<GameObject>, General
{
	private Priority priority;//繪圖優先權(最大的最後繪製，所以蓋在最上面)
	protected BufferedImage img;	//此物件目前的完整圖形
	protected BufferedImage symbol;	//此物件代表性圖形(形狀完整，大小為x,y,width,height)
	private Number id;
	protected int ox, oy, x, y;//上一次座標、新座標(使用於繪圖時須強轉int)
	protected int width, height;	//長寬
	public static enum Direction{CENTER, RIGHT, LEFT, UP, DOWN}//物件移動方向，CENTER代表不動
	protected Direction direction = Direction.CENTER;//物件移動方向
	protected Rectangle rect = new Rectangle();	//代表物件的矩形(其實就是xy和width height)
	                                            //存在的目的是偵測物體碰撞可以用intersection()
	
	
	public void setPriority(Priority a){priority = a;}
	public void setPosition(int x, int y)//名字跟setLocation區隔，因為有繼承Canvas，可能會用到
	{
		this.x = x; this.y = y;
		rect.setBounds(x, y, width, height);
	}
	public void setx(int x){this.x = x;}//
	public void sety(int y){this.y = y;}//
	public int getx(){return x;}
	public int gety(){return y;}
	public void setDirection(Direction d){direction = d;}
	public Number getId(){return id;}
	public void setId(Number n){id = n;}
	public int getAlpha(int x, int y)//取得該座標點的(偽)Alpha值
	{
		return (img.getRGB(x, y) >> 24);	//BufferedImage的getRGB回傳的color model
		                                    //是ARGB，從MSB開始算8bits代表Alpha值，
											//所以右移24bits取得Alpha值
											//(沒有很確定，但可行所以應該沒錯，不過有位元補位的問題，
											//所以其實只能用來判斷是否為0(即透明))
	}
	public int getSymbolAlpha(int x, int y){return (symbol.getRGB(x, y) >> 24);}
	public int scalingBackWidth(int n)		//根據目前繪製用的圖案大小和原圖大小之間的比例
	{                                       //去將一個長度放大回該長度在原圖中的大小  
		return (int)(n*img.getWidth()/width);
	}
	public int scalingBackHeight(int n)
	{
		return (int)(n*img.getHeight()/height);
	}
	public Rectangle getRect(){return rect;}
	public void paintCanvas(Graphics g)//預設遊戲物件繪圖方法
	{
		action();		//先呼叫各自的action()
		g.drawImage(img, x, y, width, height, this);//預設直接使用xy和width, height繪圖
	}
	public void paint(Graphics g)
	{
		g.drawImage(img, (int)x, (int)y, this);
	}
	public void action(){}	//繼承後改寫，會在繪圖前呼叫
	public void hitReact(GameObject g){}
	
	void outOfAreaFix()
	{
		if(x > Foreground.foreX+Foreground.foreW/* && direction == Direction.RIGHT*/)
			x = 0 + Foreground.foreX - width;
		if(x + width < Foreground.foreX/* && direction == Direction.LEFT*/)
			x = Foreground.foreX+Foreground.foreW;
		if(y > Foreground.foreY+Foreground.foreH/* && direction == Direction.DOWN*/)
			y = 0 + Foreground.foreY - height;
		if(y + height < Foreground.foreY/* && direction == Direction.UP*/)
			y = Foreground.foreY+Foreground.foreH;
		rect.setBounds(x, y, width, height);
	}
	public int compareTo(GameObject go)//將繪圖優先權設為排序依據
	{
		return this.priority.ordinal() - go.priority.ordinal();
	}
}
