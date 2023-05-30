import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/* Model : 履歴画面の管理 */
class History_make {
	private Mana_Item mi;

	// コンストラクタ
	public History_make(Mana_Item mi) {
		this.mi = mi;
	}

	// コインの所持枚数を取得する
	public int getCoinCount() {
		return mi.coin;
	}

	// ソードの所持数を取得する
	public int getSwordCount() {
		return mi.sword;
	}

	// ハンマーの所持数を取得する
	public int getHammerCount() {
		return mi.hammer;
	}

	// スターの所持数を取得する
	public int getStarCount() {
		return mi.star;
	}

	// マップの所持数を取得する
	public int getBookCount() {
		return mi.book;
	}

	// ガチャで外れた回数を取得する
	public int getHazureCount() {
		return mi.hazure;
	}

	// ガチャを行った回数を取得する
	public int getDoGachaCount() {
		return mi.do_gacha;
	}

	// 初級のレコード記録を返す。未プレイなら-1を返す
	public long getEasyTime() {
		if (mi.rec_easy == Long.MAX_VALUE) {
			return -1;
		} else {
			return mi.rec_easy;
		}
	}

	// 中級のレコード記録を返す。未プレイなら-1を返す
	public long getNormalTime() {
		if (mi.rec_normal == Long.MAX_VALUE) {
			return -1;
		} else {
			return mi.rec_normal;
		}
	}

	// 上級のレコード記録を返す。未プレイなら-1を返す
	public long getHardTime() {
		if (mi.rec_hard == Long.MAX_VALUE) {
			return -1;
		} else {
			return mi.rec_hard;
		}
	}

	// 地獄級のレコード記録を返す。未プレイなら-1を返す
	public long getLunaticTime() {
		if (mi.rec_lunatic == Long.MAX_VALUE) {
			return -1;
		} else {
			return mi.rec_lunatic;
		}
	}
}
