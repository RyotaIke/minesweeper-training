package minesweeper;

import java.util.ArrayList;
import java.util.Collections;

/**
 * マインスイーパのマップの操作、情報を扱うクラス
 * @author ryota
 */
public class PlayBoard {

	private MapSymbol[][] mapArray;		// マップ
	private int sideLength; 			// 一辺の長さ（下限は5,上限は20)
	private int maxBombAmount;			// マップ上に設置できる爆弾の最大数
	private int amountOfSafeSquare;		// マップ上の、爆弾を除いたマスの総数

	// デフォルトコンストラクタ(引数ありを前提としているためprivate)
	private PlayBoard(){}

	/**
	 * 引数ありコンストラクタ
	 * @param sideLength	一辺の長さ
	 * @param maxBombAmount	マップ上に設置できる爆弾の最大数
	 */
	public PlayBoard(int sideLength, int maxBombAmount)
	{
		this.sideLength         = sideLength;
		this.maxBombAmount      = maxBombAmount;
		this.amountOfSafeSquare = (sideLength * sideLength) - maxBombAmount;
		this.mapArray           = new MapSymbol[this.sideLength][this.sideLength];
		InitMap();
	}

	// マップの初期化を行う
	private void InitMap()
	{
		setBombOnMap();
		calculateNumbers();
	}

	/**
	 * クリア条件に達しているかどうかを判定して返す
	 * @return true (クリアしている時) / false (クリアしていない時)
	 */
	public boolean isClear()
	{
		int amountOfOpenSquare = 0; /** 爆弾を除く、既にあいているマスの総数 */

		for (int yCordinate = 0; yCordinate < this.sideLength; yCordinate++)
		{
			for (int xCordinate = 0; xCordinate < this.sideLength; xCordinate++)
			{
				if (this.mapArray[yCordinate][xCordinate].getIsOpen() && ! this.mapArray[yCordinate][xCordinate].getIsFlag())
				{
					amountOfOpenSquare++;
				}
			}
		}

		return (amountOfOpenSquare == (amountOfSafeSquare)) ? true : false;
	}

	// maxBombAmountの数だけマップに爆弾をセットする
	private void setBombOnMap()
	{
		ArrayList<MapSymbol> mapSymbolList = new ArrayList<MapSymbol>();

		for (int i = 0; i < this.maxBombAmount; i++)
		{
			mapSymbolList.add(new MapSymbol(-1));
		}

		for (int j = 0; j < ((this.sideLength * this.sideLength) - this.maxBombAmount); j++)
		{
			mapSymbolList.add(new MapSymbol());
		}

		Collections.shuffle(mapSymbolList);

		for (int yCordinate = 0; yCordinate < this.sideLength; yCordinate++)
		{
			for (int xCordinate = 0; xCordinate < this.sideLength; xCordinate++)
			{
				this.mapArray[yCordinate][xCordinate] = mapSymbolList.get((yCordinate * this.sideLength) + xCordinate);
			}
		}
	}

	// 隣接したマスに爆弾がいくつあるかを計算して挿入する
	private void calculateNumbers()
	{
		for (int yCordinate = 0; yCordinate < this.sideLength; yCordinate++)
		{
			for (int xCordinate = 0; xCordinate < this.sideLength; xCordinate++)
			{
				if ( ! this.mapArray[yCordinate][xCordinate].isBomb())
				{
					int amountOfBombs = 0;

					if (isBombExist(xCordinate + 1, yCordinate + 1)) amountOfBombs++;
					if (isBombExist(xCordinate + 1, yCordinate - 1)) amountOfBombs++;
					if (isBombExist(xCordinate + 1, yCordinate))     amountOfBombs++;
					if (isBombExist(xCordinate - 1, yCordinate + 1)) amountOfBombs++;
					if (isBombExist(xCordinate - 1, yCordinate - 1)) amountOfBombs++;
					if (isBombExist(xCordinate - 1, yCordinate))     amountOfBombs++;
					if (isBombExist(xCordinate    , yCordinate + 1)) amountOfBombs++;
					if (isBombExist(xCordinate    , yCordinate - 1)) amountOfBombs++;

					this.mapArray[yCordinate][xCordinate].setStatus(amountOfBombs);
				}
			}
		}
	}

	/**
	 * 指定された場所がマップの範囲内かどうかを判定して返す
	 *
	 * @param xCordinate x座標
	 * @param yCordinate y座標
	 * @return true (マップの範囲内の時) / false (マップの範囲外の時)
	 */
	public boolean isValidPosition(int xCordinate, int yCordinate)
	{
		if (xCordinate >= 0 && xCordinate <= this.sideLength - 1 && yCordinate >= 0 && yCordinate <= this.sideLength - 1)
		{
			return true;
		}
		return false;
	}

	/**
	 * マップを表示する
	 * 爆弾マスは X 、旗が立っているマスは F 、まだ開かれていないマスは ? がそれぞれ表示される
	 */
	public void printMap()
	{
		char a = 'a';
		System.out.print("  ");
		for  (int height = 0; height < this.sideLength; height++)
		{
			System.out.print(" " + a++);
		}
		System.out.println();

		for (int yCordinate = 0; yCordinate < this.sideLength; yCordinate++)
		{
			System.out.print(yCordinate + " ");
			for (int xCordinate = 0; xCordinate < this.sideLength; xCordinate++)
			{
				System.out.print("|");
				if ( ! this.mapArray[yCordinate][xCordinate].getIsOpen())
				{
					System.out.print("?");
					continue;
				}

				if (this.mapArray[yCordinate][xCordinate].getIsFlag())
				{
					System.out.print("F");
					continue;
				}

				if (this.mapArray[yCordinate][xCordinate].isBomb())
				{
					System.out.print("X");
				}
				else
				{
					System.out.print(this.mapArray[yCordinate][xCordinate].getStatus());
				}

			}
			System.out.println("|");
		}
	}

	// 指定された座標に爆弾があるかどうかを返す
	private boolean isBombExist(int xCordinate, int yCordinate)
	{
		if (isValidPosition(xCordinate, yCordinate))
		{
			return this.mapArray[yCordinate][xCordinate].isBomb();
		}
		return false;
	}

	public void setOpen(int xCordinate, int yCordinate, boolean state)
	{
		if (isValidPosition(xCordinate, yCordinate))
		{
			this.mapArray[yCordinate][xCordinate].setIsOpen(state);
		}
	}

	public void setFlag(int xCordinate, int yCordinate, boolean state)
	{
		if (isValidPosition(xCordinate, yCordinate))
		{
			this.mapArray[yCordinate][xCordinate].setIsFlag(state);
		}
	}

	public boolean getIsOpen(int height, int width)
	{
		if (width >= 0 && width <= this.sideLength - 1 && height >= 0 && height <= this.sideLength - 1)
		{
			return this.mapArray[height][width].getIsOpen();
		}
		return false;
	}

	public boolean getIsFlag(int height, int width)
	{
		if (width >= 0 && width <= this.sideLength - 1 && height >= 0 && height <= this.sideLength - 1)
		{
			return this.mapArray[height][width].getIsFlag();
		}
		return false;
	}

	public boolean getIsBomb(int xCordinate, int yCordinate)
	{
		return this.mapArray[yCordinate][xCordinate].isBomb();
	}

	public int getStatus(int height, int width)
	{
		if (width >= 0 && width <= this.sideLength - 1 && height >= 0 && height <= this.sideLength - 1)
		{
			return this.mapArray[height][width].getStatus();
		}
		return -1;
	}


	public int getSideLength()
	{
		return this.sideLength;
	}
}
