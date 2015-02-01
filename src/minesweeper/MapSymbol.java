package minesweeper;

public class MapSymbol {

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
