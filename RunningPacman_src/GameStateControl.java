class GameStateControl	//���骬�A�H�ΦU�ت��A�d�ݡB����������k�A
{                        //interface General�̷|���@��instance�ѩҹC�������̨ϥ�
	public int score;
	public int life;
	public enum GameState{COMMON, GAME_OVER, GAME_PAUSE}//�C�����骬�A
	public enum PacmanState{COMMON, KILLED, RETRIVE}//�p���F���A
	
	
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
	
	//�C���Ȱ��t�C
	public void pause(){game = GameState.GAME_PAUSE;}
	public boolean isPause()
	{
		if(game == GameState.COMMON)
			return false;
		else 
			return true;
	}
	public void resume(){game = GameState.COMMON;}
	
	//��������Q�ۤv�~��t�C
	public void ghostShock()
	{
		if(ghostStateSwitch)
			ghostStateSwitch = false;
		else
			ghostStateSwitch = true;
	}
	
	
	//�p���F�t�C
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