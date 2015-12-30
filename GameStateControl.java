class GameStateControl	//���骬�A�H�ΦU�ت��A�d�ݡB����������k�A
{                        //interface General�̷|���@��instance�ѩҹC�������̨ϥ�
	int score;
	enum GameState{COMMON, GAME_OVER, GAME_PAUSE}//�C�����骬�A
	enum PacmanState{COMMON, KILLED}//�p���F���A
	enum GhostState{COMMON, SHOCKED}//������F���A
	
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
	
	//�C���Ȱ��t�C
	void pause(){game = GameState.GAME_PAUSE;}
	boolean isPause()
	{
		if(game == GameState.COMMON)
			return false;
		else 
			return true;
	}
	void resume(){game = GameState.COMMON;}
	
	//��������Q�ۤv�~��t�C
	void ghostShock(){ghost = GhostState.SHOCKED;}
	boolean ghostIsShocked()
	{
		if (ghost == GhostState.SHOCKED)
			return true;
		else
			return false;
	}
	void ghostRecover(){ghost = GhostState.COMMON;}
	
	//�p���F�t�C
	void pacmanKilled(){pac = PacmanState.KILLED;}
	void pacmanRetrive(){pac = PacmanState.COMMON;}
	void pacPosUpdate(int x, int y){pacX = x; pacY = y;}
}