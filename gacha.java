import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/* Model : ガチャの処理 */
class Gacha extends Observable {
	private Mana_Item have;
	private Mana_File file;
	private int item_id;
	private boolean flaggacha;

	// ガチャの初期設定（コンストラクタ）
	public Gacha(Mana_File file, Mana_Item have) {
		this.have = have;
		this.file = file;
		item_id = 0;
		flaggacha = false;
	}

	// 所持しているコインの枚数を返す
	public int getHaveCoin() {
		return have.coin;
	}

	// 所持しているソードの個数を返す
	public int getHaveSword() {
		return have.sword;
	}

	// 所持しているハンマーの数を返す
	public int getHaveHammer() {
		return have.hammer;
	}

	// 所持しているスターの数を返す
	public int getHaveStar() {
		return have.star;
	}

	// 所持しているマップの数を返す
	public int getHaveBook() {
		return have.book;
	}

	// ガチャで得たアイテムのidを返す
	public int getItem() {
		return item_id;
		// 0: 初期画面（何もない） 1: sword, 2: hammer, 3: star, 4:book, 5: hazure;
	}

	// ガチャを行う。
	public void action_gacha() {
		if (have.coin >= 10) {
			flaggacha = true;
			have.coin -= 10;
			have.do_gacha += 1;
			// ガチャをしよう
			double r = Math.random();
			if (r > 0.9) {
				item_id = 4;
				have.book += 1;
			} else if (r > 0.8) {
				item_id = 3;
				have.star += 1;
			} else if (r > 0.6) {
				item_id = 2;
				have.hammer += 1;
			} else if (r > 0.4) {
				item_id = 1;
				have.sword += 1;
			} else {
				item_id = 5;
				have.hazure += 1;
			}
		}
		file.write_data(have); // データ保存
		setChanged();
		notifyObservers();
	}

	// SEをbooleanで返す
	public boolean getFlagGacha() {
		boolean temp = flaggacha;
		flaggacha = false;
		return temp;
	}

}
