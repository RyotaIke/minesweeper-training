package minesweeper;

/**
 * MapSymbolは、マップ上の１マスをあらわすクラスです
 *
 * @author ike_ryota
 *
 */

public class MapSymbol {

	private int     status; // 周りに爆弾がいくつあるかを示す数字 (-1だと爆弾として扱う)
	private boolean isOpen; // マップ上で既に選択され、表示扱いになっているかどうか
	private boolean isFlag; // 旗がたっているかどうか

	/**
	 * コンストラクタ
	 */
	public MapSymbol()
	{
		this.status = 0;
		this.isOpen = false;
		this.isFlag = false;
	}

	/**
	 * 引数ありコンストラクタ
	 * @param int status // 周りに爆弾がいくつあるかを示す数字 (-1だと爆弾として扱う)
	 */
	public MapSymbol(int status)
	{
		this.status = status;
		this.isOpen = false;
		this.isFlag = false;
	}

	/**
	 * statusに対するgetter
	 * @return status
	 */
	public int getStatus()
	{
		return status;
	}

	/**
	 * statusに対するsetter
	 * @param status
	 */
	public void setStatus(int status)
	{
		this.status = status;
	}

	/**
	 * 爆弾かどうかを返す
	 * @return true (爆弾の時) / false (爆弾じゃないとき)
	 */
	public boolean isBomb()
	{
		return status == -1 ? true : false;
	}

	/**
	 * statusに-1(爆弾)をセットする
	 */
	public void setBomb()
	{
		this.status = -1;
	}

	/**
	 * isOpenに対するgetter
	 * @return isOpen
	 */
	public boolean getIsOpen()
	{
		return isOpen;
	}

	/**
	 * isOpenに対するsetter
	 * @param isOpen
	 */
	public void setIsOpen(boolean isOpen)
	{
		this.isOpen = isOpen;
	}

	/**
	 * isFlagに対するgetter
	 * @return
	 */
	public boolean getIsFlag()
	{
		return isFlag;
	}

	/**
	 * isFlagに対するsetter
	 * @param isFlag
	 */
	public void setIsFlag(boolean isFlag)
	{
		this.isFlag = isFlag;
	}
}
