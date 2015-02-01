package minesweeper;

/**
 * マインスイーパを起動するメインクラス
 * @author ryota
 *
 */
public class MineSweeper
{
	public static void main(String[] args)
	{
		GameBase gb = new GameBase();
		gb.playGame();
	}
}
