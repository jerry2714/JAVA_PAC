import java.awt.*;
interface Initialize	
{
	public void init();
	public void setFrameSize(int width, int height);//���F��ø�ϰϤj�p�b�{���}�l�ɸ�����@�P(�@�ɳ·СA�ثe�Q����n�@�I����k)
}
interface General	//�q�α`��
{
	Color transparent = new Color(0, 0, 0, 0);//�z����
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
