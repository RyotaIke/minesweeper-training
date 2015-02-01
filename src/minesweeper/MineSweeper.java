package minesweeper;


//メインクラス
public class MineSweeper
{
	public static void main(String[] args)
	{
		GameBase gb = new GameBase();
		gb.playGame();
		//inputInitSetting();

	}


	private void startGame()
	{
		GameBase gameBase = new GameBase();
		gameBase.makeBoard();
	}

}
