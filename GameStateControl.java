class GameStateControl	//全體狀態以及各種狀態查看、切換等等方法，
{                        //interface General裡會有一個instance供所遊戲相關者使用
	int score;
	enum GameState{COMMON, GAME_OVER, GAME_PAUSE}//遊戲本體狀態
	enum PacmanState{COMMON, KILLED}//小精靈狀態
	enum GhostState{COMMON, SHOCKED}//全體幽靈狀態
	
	GameState game;
	PacmanState pac;
	GhostState ghost;
	
	int pacX, pacY;
	
	GameStateControl(){init();}
	void init()
	{
		score = 0;
		game = GameState.GAME_PAUSE;
		pac = PacmanState.COMMON;
		ghost = GhostState.COMMON;
	}
	
	//遊戲暫停系列
	void pause(){game = GameState.GAME_PAUSE;}
	boolean isPause()
	{
		if(game == GameState.COMMON)
			return false;
		else 
			return true;
	}
	void resume(){game = GameState.COMMON;}
	
	//鬼長太醜被自己嚇到系列
	void ghostShock(){ghost = GhostState.SHOCKED;}
	boolean ghostIsShocked()
	{
		if (ghost == GhostState.SHOCKED)
			return true;
		else
			return false;
	}
	void ghostRecover(){ghost = GhostState.COMMON;}
	
	//小精靈系列
	void pacmanKilled(){pac = PacmanState.KILLED;}
	void pacmanRetrive(){pac = PacmanState.COMMON;}
	void pacPosUpdate(int x, int y){pacX = x; pacY = y;}
}