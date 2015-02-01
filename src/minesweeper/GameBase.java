package minesweeper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameBase {

	private final static int MIN_SIDE_LENGTH = 5;  // 一辺の長さの最低値
	private final static int MAX_SIDE_LENGTH = 10; // 一辺の長さの最大値
	private final static int MIN_BOMB_AMOUNT = 5;  // 爆弾の最低数
	private final static int MAX_BOMB_AMOUNT = 20; // 爆弾の最大数
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1);

	public GameBase(){}

	// マップを生成するクラス
	private PlayBoard makeBoard()
	{
		int sideLength = inputSideLength();
		int bombAmount = inputBombAmount();
		PlayBoard playBoard = new PlayBoard(sideLength, bombAmount);

		return playBoard;
	}

	public void playGame()
	{
		// タイトルの表示
		showTitle();
		// マップの生成
		PlayBoard playBoard = makeBoard();

		// メインループ
		while ( ! playBoard.isClear())
		{
			playBoard.printMap();
			String input = inputNextPosition(playBoard);

			int xCordinate = (int)(input.charAt(0) - 'a');
			int yCordinate = Integer.parseInt(input.substring(1,2));
			openSquareRecursive(xCordinate, yCordinate, playBoard);
		}

		// 終了処理
		endGame(playBoard);
	}


	private String inputNextPosition(PlayBoard playBoard)
	{
		String inputString = "";
		System.out.println();
		//System.out.println("空けたい場所を選択してください ( 例：a2 ）");

		try
		{
			do
			{
				System.out.println("空けたい場所を選択してください ( 例：a2 ）");
				inputString = br.readLine();
			}
			while ( ! isValidInput(inputString, playBoard));

			return inputString;
		}
		catch (IOException e)
		{
			System.out.println("入力エラーです。もう一度入力してください");
			System.exit(-1);
		}
		return inputString;
	}

	private boolean isValidInput(String input, PlayBoard playBoard)
	{
		char a = 'a' - 1;
		for  (int i = 0; i < playBoard.getSideLength(); i++)
		{
			a++;
		}

		Pattern p = Pattern.compile("[a-" + (char)(a - 1) + "][0-" + (playBoard.getSideLength() - 1) +"]");
		Matcher m = p.matcher(input);

		if ( ! m.find())
		{
			return false;
		}
		return true;
	}

	private void endGame(PlayBoard playBoard)
	{
		playBoard.printMap();

		System.out.println();
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
			 System.out.println(MIN_SIDE_LENGTH + "×" + MIN_SIDE_LENGTH + "～" + MAX_SIDE_LENGTH + "×" + MAX_SIDE_LENGTH + "までサイズを選べます");
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
			 System.out.println(MIN_BOMB_AMOUNT + "～" + MAX_BOMB_AMOUNT + "まで爆弾の個数を選べます");
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
			openSquareRecursive(xCordinate + 1, yCordinate + 1, playBoard);
			openSquareRecursive(xCordinate + 1, yCordinate    , playBoard);
			openSquareRecursive(xCordinate + 1, yCordinate - 1, playBoard);
			openSquareRecursive(xCordinate - 1, yCordinate + 1, playBoard);
			openSquareRecursive(xCordinate - 1, yCordinate    , playBoard);
			openSquareRecursive(xCordinate - 1, yCordinate - 1, playBoard);
			openSquareRecursive(xCordinate    , yCordinate + 1, playBoard);
			openSquareRecursive(xCordinate    , yCordinate - 1, playBoard);
			break;
		default:
			playBoard.setOpen(yCordinate, xCordinate);
			break;
		}
	}
}
