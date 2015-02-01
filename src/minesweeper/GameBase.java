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
			String position = inputNextPosition(playBoard);
			String option   = inputNextAction(position, playBoard);
			if (checkBomb(position, playBoard) && option.equals("o"))
			{
				System.out.println("ここ？");
				int xCordinate = (int)(position.charAt(0) - 'a');
				int yCordinate = Integer.parseInt(position.substring(1,2));
				playBoard.setOpen(xCordinate, yCordinate, true);
				break;
			}
			executeNextAction(position, option, playBoard);
		}

		// 終了処理
		endGame(playBoard);
	}

	private boolean isAlreadyOpened(String position, PlayBoard playBoard)
	{
		int xCordinate = (int)(position.charAt(0) - 'a');
		int yCordinate = Integer.parseInt(position.substring(1,2));

		return playBoard.getIsOpen(yCordinate, xCordinate);
	}

	// マップを生成する
	private PlayBoard makeBoard()
	{
		int sideLength = inputSideLength();
		int bombAmount = inputBombAmount();
		PlayBoard playBoard = new PlayBoard(sideLength, bombAmount);

		return playBoard;
	}

	// 次に選択する座標をプレイヤーに入力してもらう
	private String inputNextPosition(PlayBoard playBoard)
	{
		String inputString = "";

		try
		{
			do
			{
				System.out.println("\n場所を選択してください ( 例：a2 ）");
				inputString = br.readLine();
			}
			while ( ! isValidInput(inputString, playBoard));

			return inputString;
		}
		catch (IOException e)
		{
			System.out.println("入力エラーです。ゲームを終了します");
			System.exit(-1);
		}
		return inputString;
	}

	private boolean checkBomb(String position, PlayBoard playBoard)
	{
		int xCordinate = (int)(position.charAt(0) - 'a');
		int yCordinate = Integer.parseInt(position.substring(1,2));

		return playBoard.getIsBomb(xCordinate, yCordinate);
	}

	// 次に選択する行動をプレイヤーに入力してもらう
	private String inputNextAction(String position, PlayBoard playBoard)
	{
		String inputString = "";
		Pattern p = Pattern.compile("[of]");
		Matcher m;

		try
		{
			do
			{
				if (isAlreadyOpened(position, playBoard))
				{
					System.out.println("キャンセルなら o を、旗を降ろす場合は f を入力してください");
				}
				else
				{
					System.out.println("開くなら o を、旗を立てる場合は f を入力してください");
				}
				inputString = br.readLine();
				m = p.matcher(inputString);
			}
			while ( ! m.find());

			return inputString;
		}
		catch (IOException e)
		{
			System.out.println("入力エラーです。ゲームを終了します");
			System.exit(-1);
		}
		return inputString;
	}

	// プレイヤーの選択した行動を実行する
	private void executeNextAction(String position, String option, PlayBoard playBoard)
	{
		int xCordinate = (int)(position.charAt(0) - 'a');
		int yCordinate = Integer.parseInt(position.substring(1,2));

		switch (option.charAt(0))
		{
		case 'f' :
			changeFlagCondition(xCordinate, yCordinate, playBoard);
			break;
		case 'o' :
			openSquareRecursive(xCordinate, yCordinate, playBoard);
			break;
		default :
			break;
		}
	}

	// 指定されたマスの旗の状態を変更する
	private void changeFlagCondition(int xCordinate, int yCordinate, PlayBoard playBoard)
	{
		if (playBoard.getIsFlag(yCordinate, xCordinate))
		{
			playBoard.setOpen(xCordinate, yCordinate, false);
			playBoard.setFlag(xCordinate, yCordinate, false);
		}
		else
		{
			playBoard.setOpen(xCordinate, yCordinate, true);
			playBoard.setFlag(xCordinate, yCordinate, true);
		}

	}


	private boolean isValidInput(String input, PlayBoard playBoard)
	{

		char a = 'a' - 1;

		for  (int i = 0; i < playBoard.getSideLength(); i++)
		{
			a++;
		}
		Pattern p = Pattern.compile("[a-" + a + "][0-" + (playBoard.getSideLength() - 1) +"]");
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

		if (playBoard.isClear())
		{
			showGameClear();
		}
		else
		{
			showGameOver();
		}
	}

	private void showGameClear()
	{
		System.out.println();
		System.out.println();
		System.out.println("####################################");
		System.out.println("#                                  #");
		System.out.println("#         ゲームクリアです         #");
		System.out.println("#       おめでとうございます       #");
		System.out.println("#                                  #");
		System.out.println("####################################");
	}

	private void showGameOver()
	{
		System.out.println();
		System.out.println();
		System.out.println("####################################");
		System.out.println("#                                  #");
		System.out.println("#        ゲームオーバーです        #");
		System.out.println("#      また挑戦してくださいね      #");
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

	// 選択された場所をひらく
	// もし選択されたところが 0 の時は再帰的に周りの場所もひらく
	private void openSquareRecursive(int xCordinate, int yCordinate, PlayBoard playBoard)
	{
		if (playBoard.getIsOpen(yCordinate,xCordinate))
		{
			return;
		}
		switch (playBoard.getStatus(yCordinate, xCordinate))
		{
		case -1:
			break;
		case 0:
			playBoard.setOpen(xCordinate, yCordinate, true);
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
			playBoard.setOpen(xCordinate, yCordinate, true);
			break;
		}
	}
}
