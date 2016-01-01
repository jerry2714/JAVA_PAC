import java.awt.*;
interface Initialize	
{
	public void init();
	public void setFrameSize(int width, int height);//為了使繪圖區大小在程式開始時跟視窗一致(世界麻煩，目前想不到好一點的方法)
}
interface General	//通用常數
{
	Color transparent = new Color(0, 0, 0, 0);//透明色
	GameStateControl gscontrol = new GameStateControl();
	
	enum Number{PACMAN, GHOST, DOT, POWER_PELLET}
}
enum Priority
{
	BACK_GROUND,
	DOT,
	POWER_PELLET,
	SHOCKED_GHOST,
	PACMAN,
	GHOST,
	FORE_GROUND,
	ICON
}
