package minesweeper;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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

class GameBase
{
	private final static int MIN_SIDE_LENGTH = 5;  // 一辺の長さの最低値
	private final static int MAX_SIDE_LENGTH = 10; // 一辺の長さの最大値
	private final static int MIN_BOMB_AMOUNT = 5;  // 爆弾の最低数
	private final static int MAX_BOMB_AMOUNT = 20; // 爆弾の最大数
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1);

	public GameBase(){}

	public PlayBoard makeBoard()
	{
		showTitle();

		int sideLength = inputSideLength();
		int bombAmount = inputBombAmount();
		PlayBoard playBoard = new PlayBoard(sideLength, bombAmount);

		return playBoard;
	}


	public void playGame()
	{
		PlayBoard playBoard = makeBoard();

		while ( ! playBoard.isClear())
		{
			playBoard.printMap();
			inputNextAction(playBoard);
		}

		endGame(playBoard);
	}

	private void inputNextAction(PlayBoard playBoard)
	{
		 System.out.println();
		 System.out.println("空けたい場所を選んでくださいな ( 例：a2 ）");

		 try
		 {
			 String str = br.readLine();
			 int width  = Integer.parseInt(str.substring(0,1));
			 int height = Integer.parseInt(str.substring(1,2));
			 openSquareRecursive(height, width, playBoard);
		 }
		 catch (Exception e)
		 {
			 System.out.println("入力エラーです。");
		 }
	}

	private void endGame(PlayBoard playBoard)
	{
		playBoard.printMap();

		System.out.println("####################################");
		System.out.println("#                                  #");
		System.out.println("#       おめでとうございます       #");
		System.out.println("#                                  #");
		System.out.println("####################################");
	}


	private void showTitle()
	{
		System.out.println("####################################");
		System.out.println("#                                  #");
		System.out.println("#     マインスイーパへようこそ     #");
		System.out.println("#                                  #");
		System.out.println("####################################");
		System.out.println();

		return ;
	}

	private int inputSideLength()
	{
		int sideLength    = 0;

		while (sideLength < MIN_SIDE_LENGTH || MAX_SIDE_LENGTH < sideLength)
		{
			 System.out.println();
			 System.out.println("５×５～１０×１０までサイズを選べます");
			 System.out.println("プレイしたいサイズを入力してください");

			 try
			 {
				 String str = br.readLine();
				 sideLength = Integer.parseInt(str);
			 }
			 catch (Exception e)
			 {
				 System.out.println("入力エラーです。もう一度入力してください");
			 }
		}

		return sideLength;
	}

	private int inputBombAmount()
	{
		int bombAmount    = 0;

		while (bombAmount < MIN_BOMB_AMOUNT || MAX_BOMB_AMOUNT < bombAmount)
		{
			 System.out.println();
			 System.out.println("５～２０まで爆弾の個数を選べます");
			 System.out.println("設置したい爆弾の個数を入力してください");

			 try
			 {
				 String str = br.readLine();
				 bombAmount = Integer.parseInt(str);
			 }
			 catch (Exception e)
			 {
				 System.out.println("入力エラーです。もう一度入力してください");
			 }
		}

		return bombAmount;
	}

	// 周りのところもあける
	private void openSquareRecursive(int xCordinate, int yCordinate, PlayBoard playBoard)
	{
		System.out.println(xCordinate + ":" + yCordinate);
		if (playBoard.getIsOpen(yCordinate,xCordinate))
		{
			System.out.println(xCordinate + ":" + yCordinate);
			return;
		}
		switch (playBoard.getStatus(yCordinate, xCordinate))
		{
		case -1:
			break;
		case 0:
			playBoard.setOpen(yCordinate, xCordinate);
			//openSquareRecursive(xCordinate + 1, yCordinate + 1, playBoard);
			//openSquareRecursive(xCordinate + 1, yCordinate - 1, playBoard);
			openSquareRecursive(xCordinate + 1, yCordinate    , playBoard);
			//openSquareRecursive(xCordinate - 1, yCordinate + 1, playBoard);
			//openSquareRecursive(xCordinate - 1, yCordinate - 1, playBoard);
			openSquareRecursive(xCordinate - 1, yCordinate    , playBoard);
			openSquareRecursive(xCordinate    , yCordinate + 1, playBoard);
			openSquareRecursive(xCordinate    , yCordinate - 1, playBoard);
			break;
		default:
			playBoard.setOpen(yCordinate, xCordinate);
			break;
		}
	}
}