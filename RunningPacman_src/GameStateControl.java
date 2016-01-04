class GameStateControl	//全體狀態以及各種狀態查看、切換等等方法，
{                        //interface General裡會有一個instance供所遊戲相關者使用
	public int score;
	public int life;
	public enum GameState{COMMON, GAME_OVER, GAME_PAUSE}//遊戲本體狀態
	public enum PacmanState{COMMON, KILLED, RETRIVE}//小精靈狀態
	
	
	public GameState game;
	public PacmanState pac;
	public boolean ghostStateSwitch = false;
	public boolean oneMoreGhost = false;
	
	public int pacX, pacY;
	
	public GameStateControl(){init();}
	public void init()
	{
		score = 0;
		life = 3;
		game = GameState.GAME_PAUSE;
		pac = PacmanState.COMMON;
		ghostStateSwitch = false;
		oneMoreGhost = false;
	}
	
	//遊戲暫停系列
	public void pause(){game = GameState.GAME_PAUSE;}
	public boolean isPause()
	{
		if(game == GameState.COMMON)
			return false;
		else 
			return true;
	}
	public void resume(){game = GameState.COMMON;}
	
	//鬼長太醜被自己嚇到系列
	public void ghostShock()
	{
		if(ghostStateSwitch)
			ghostStateSwitch = false;
		else
			ghostStateSwitch = true;
	}
	
	
	//小精靈系列
	public void pacmanKilled(){pac = PacmanState.KILLED;}
	public boolean pacmanIsKilled()
	{
		if(pac == PacmanState.KILLED)
			return true;
		else return false;
	}
	public void pacmanRetrive(){pac = PacmanState.RETRIVE;}
	public void pacPosUpdate(int x, int y){pacX = x; pacY = y;}
}