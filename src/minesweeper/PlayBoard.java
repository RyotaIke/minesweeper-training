package minesweeper;

import java.util.ArrayList;
import java.util.Collections;

public class PlayBoard {
	private MapSymbol[][] mapArray;		// マップ
	private int sideLength; 			// 一辺の長さ（下限は5,上限は20)
	private int maxBombAmount;			// マップ上に設置できる爆弾の最大数
	private int amountOfSafeSquare;		// 残っている爆弾の数

	private PlayBoard(){}

	public PlayBoard(int sideLength, int maxBombAmount)
	{
		this.sideLength         = sideLength;
		this.maxBombAmount      = maxBombAmount;
		this.amountOfSafeSquare = (sideLength * sideLength) - maxBombAmount;
		this.mapArray           = new MapSymbol[this.sideLength][this.sideLength];
		InitMap();
	}

	private void InitMap()
	{

		setBombOnMap();
		calculateNumbers();
	}

	public boolean isClear()
	{
		int amountOfOpenSquare = 0;

		for (int height = 0; height < this.sideLength; height++)
		{
			for (int width = 0; width < this.sideLength; width++)
			{
				if (this.mapArray[height][width].getIsOpen())
				{
					amountOfOpenSquare++;
				}
			}
		}

		return (amountOfOpenSquare == (amountOfSafeSquare)) ? true : false;
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
					int amountOfBombs = 0;

					if (isBombExist(width + 1, height + 1)) amountOfBombs++;
					if (isBombExist(width + 1, height - 1)) amountOfBombs++;
					if (isBombExist(width + 1, height))     amountOfBombs++;
					if (isBombExist(width - 1, height + 1)) amountOfBombs++;
					if (isBombExist(width - 1, height - 1)) amountOfBombs++;
					if (isBombExist(width - 1, height))     amountOfBombs++;
					if (isBombExist(width    , height + 1)) amountOfBombs++;
					if (isBombExist(width    , height - 1)) amountOfBombs++;

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
		char a = 'a';
		System.out.print("  ");
		for  (int height = 0; height < this.sideLength; height++)
		{
			System.out.print(" " + a++);
		}
		System.out.println();

		for (int height = 0; height < this.sideLength; height++)
		{
			System.out.print(height + " ");
			for (int width = 0; width < this.sideLength; width++)
			{
				System.out.print("|");
				if ( ! this.mapArray[height][width].getIsOpen())
				{
					System.out.print("?");
					continue;
				}

				if (this.mapArray[height][width].getIsFlag())
				{
					System.out.print("F");
					continue;
				}

				if (this.mapArray[height][width].isBomb())
				{
					System.out.print("X");
				}
				else
				{
					System.out.print(this.mapArray[height][width].getStatus());
				}

			}
			System.out.println("|");
		}
	}

	public void setOpen(int height, int width)
	{
		if (width >= 0 && width <= this.sideLength - 1 && height >= 0 && height <= this.sideLength - 1)
		{
			this.mapArray[height][width].setIsOpen(true);
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

	public int getStatus(int height, int width)
	{
		if (width >= 0 && width <= this.sideLength - 1 && height >= 0 && height <= this.sideLength - 1)
		{
			return this.mapArray[height][width].getStatus();
		}
		return -1;

	}
}
