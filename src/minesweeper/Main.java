package minesweeper;

import java.util.ArrayList;
import java.util.Collections;

public class Main
{
	public static void main(String[] args)
	{
		PlayBoard playBoard = new PlayBoard(10, 10);
		playBoard.printMap();
	}
}

class PlayBoard
{
	private MapSymbol[][] mapArray;		// マップ
	private int sideLength; 			// 一辺の長さ（下限は5,上限は20)
	private int maxBombAmount;			// マップ上に設置できる爆弾の最大数
	private int amountOfRemainBomb;		// 残っている爆弾の数

	private PlayBoard(){}

	public PlayBoard(int sideLength, int maxBombAmount)
	{
		this.sideLength         = sideLength;
		this.maxBombAmount      = maxBombAmount;
		this.amountOfRemainBomb = maxBombAmount;
		this.mapArray           = new MapSymbol[this.sideLength][this.sideLength];
		InitMap();
	}

	private void InitMap()
	{
		setBombOnMap();
		calculateNumbers();
	}

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

		for (int k = 0; k < this.sideLength; k++)
		{
			for (int l = 0; l < this.sideLength; l++)
			{
				this.mapArray[k][l] = mapSymbolList.get((k * this.sideLength) + l);
			}
		}
	}

	private void calculateNumbers()
	{
		for (int height = 0; height < this.sideLength; height++)
		{
			for (int width = 0; width < this.sideLength; width++)
			{
				if ( ! this.mapArray[height][width].isBomb())
				{
					int xCoodinate    = width;
					int yCoodinate    = height;
					int amountOfBombs = 0;

					if (isBombExist(xCoodinate + 1, yCoodinate + 1)) amountOfBombs++;
					if (isBombExist(xCoodinate + 1, yCoodinate - 1)) amountOfBombs++;
					if (isBombExist(xCoodinate + 1, yCoodinate))     amountOfBombs++;
					if (isBombExist(xCoodinate - 1, yCoodinate + 1)) amountOfBombs++;
					if (isBombExist(xCoodinate - 1, yCoodinate - 1)) amountOfBombs++;
					if (isBombExist(xCoodinate - 1, yCoodinate))     amountOfBombs++;
					if (isBombExist(xCoodinate    , yCoodinate + 1)) amountOfBombs++;
					if (isBombExist(xCoodinate    , yCoodinate - 1)) amountOfBombs++;

					this.mapArray[height][width].setStatus(amountOfBombs);
				}
			}
		}
	}

	private boolean isBombExist(int width, int height)
	{
		if (width >= 0 && width <= this.sideLength - 1 && height >= 0 && height <= this.sideLength - 1)
		{
			return this.mapArray[height][width].isBomb();
		}
		return false;
	}

	public void printMap()
	{
		for (int height = 0; height < this.sideLength; height++)
		{
			for (int width = 0; width < this.sideLength; width++)
			{
//				if ( ! this.mapArray[height][width].isOpen())
//				{
//					System.out.print("?");
//					continue;
//				}

				if (this.mapArray[height][width].isBomb())
				{
					System.out.print("X");
				}
				else
				{
					System.out.print(this.mapArray[height][width].getStatus());
				}
			}
			System.out.println();
		}
	}
}

class MapSymbol
{
	private int     status; // 周りに爆弾がいくつあるかを示す数字 (-1だと爆弾として扱う)
	private boolean isOpen; // マップ上で既に選択され、表示扱いになっているかどうか
	private boolean isFlag; // 旗がたっているかどうか

	// コンストラクタ
	public MapSymbol()
	{
		this.status = 0;
		this.isOpen = false;
		this.isFlag = false;
	}

	// 引数ありコンストラクタ
	public MapSymbol(int status)
	{
		this.status = status;
		this.isOpen = false;
		this.isFlag = false;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public boolean isBomb()
	{
		return status == -1 ? true : false;
	}

	public void setBomb()
	{
		this.status = -1;
	}

	public boolean isOpen()
	{
		return isOpen;
	}

	public void setIsOpen(boolean isOpen)
	{
		this.isOpen = isOpen;
	}

	public boolean isFlag()
	{
		return isFlag;
	}

	public void setIsFlag(boolean isFlag)
	{
		this.isFlag = isFlag;
	}


}