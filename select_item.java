import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/* Model : ゲーム開始前のアイテムの使用数を設定する場所の管理 */
class Select_Item extends Observable {
	private Mana_Item mi;
	private Mana_Item use;
	private boolean flagbutton;

	// コンストラクタ
	public Select_Item(Mana_Item mi, Mana_Item use) {
		this.mi = mi;
		this.use = use;
		flagbutton = false;
	}

	// 現在所持しているソードの最大数を確認する
	public int gethaveSword() {
		return mi.sword;
	}

	// 現在所持しているハンマーの最大数を確認する
	public int gethaveHammer() {
		return mi.hammer;
	}

	// 現在所持しているスターの最大数を確認する
	public int gethaveStar() {
		return mi.star;
	}

	// 現在所持しているマップの最大数を確認する
	public int gethaveBook() {
		return mi.book;
	}

	// 使用するソード数を確認する
	public int getUseSword() {
		return use.sword;
	}

	// 使用するハンマー数を確認する
	public int getUseHammer() {
		return use.hammer;
	}

	// 使用するスター数を確認する
	public int getUseStar() {
		return use.star;
	}

	// 使用するマップ数を確認する
	public int getUseBook() {
		return use.book;
	}

	// 各アイテムの使用数を増加させる
	public void push_plus(int mode) {
		if (mode == 1) {
			if (mi.sword > use.sword) {
				use.sword++;
			}
		} else if (mode == 2) {
			if (mi.hammer > use.hammer) {
				use.hammer++;
			}
		} else if (mode == 3) {
			if (mi.star > use.star) {
				use.star++;
			}
		} else if (mode == 4) {
			if (mi.book > use.book) {
				use.book++;
			}
		}
		flagbutton = true;
		setChanged();
		notifyObservers();
	}

	// 各アイテムの使用数を減少させる
	public void push_minus(int mode) {
		if (mode == 1) {
			if (use.sword > 0) {
				use.sword--;
			}
		} else if (mode == 2) {
			if (use.hammer > 0) {
				use.hammer--;
			}
		} else if (mode == 3) {
			if (use.star > 0) {
				use.star--;
			}
		} else if (mode == 4) {
			if (use.book > 0) {
				use.book--;
			}
		}
		flagbutton = true;
		setChanged();
		notifyObservers();
	}

	// ボタンのＳＥを鳴らす
	public boolean getFlagButton() {
		boolean temp = flagbutton;
		flagbutton = false;
		return temp;
	}
}