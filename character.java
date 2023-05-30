import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;

/* Model : キャラクター */
class Character extends Observable implements ActionListener {
	protected Maze meiro; // 迷路
	protected int rowchar; // プレイヤーx座標
	protected int colchar; // プレイヤーy座標
	protected int direction; // プレイヤーの向き
	protected Have_Item have_item; // プレイヤーのアイテム管理
	protected Step_Trap step_trap; // トラップの管理
	protected Enemy enemy; // 敵情報
	protected boolean isLive; // キャラが生きているどうか
	protected boolean invincible; // 無敵かどうか
	protected boolean mirror_now; // 状態異常かどうか
	protected javax.swing.Timer judge_timer; // 定期的に呼び出す

	// コンストラクタ
	public Character(Maze maze, AllItem allitem, Enemy enemy, AllTrap alltrap, Mana_Item add_item) {
		meiro = maze;
		have_item = new Have_Item(maze, this, allitem, add_item);
		step_trap = new Step_Trap(this, alltrap);

		this.enemy = enemy;
		rowchar = maze.getRowstart();
		colchar = maze.getColstart();
		direction = 0;
		isLive = true;
		invincible = false;
		mirror_now = false;

		judge_timer = new javax.swing.Timer(5, this);
		judge_timer.setActionCommand("Judge");
		judge_timer.start();
	}

	// キャラのx座標を返す
	public int getRowchar() {
		return rowchar;
	}

	// キャラのy座標を返す
	public int getColchar() {
		return colchar;
	}

	// キャラが生きているかどうかを返す
	public boolean getisLive() {
		return isLive;
	}

	// ミラーが発動しているかどうかを返す
	// 発動中ならtrue, 発動していないならfalse;
	public boolean get_mirror_now() {
		return mirror_now;
	}

	// 死亡パターン１：敵とぶつかったら死亡（敵にぶつかりにいったとき）
	public void deathChara(Enemy ene, int row, int col) {
		if (ene.getEnemyPosition(row, col)) {
			isLive = false;
		}
	}

	// 死亡パターン２：敵とプレイヤーが同座標の時の対処
	public boolean isEnemy() {
		if (enemy.getEnemyPosition(getRowchar(), getColchar())) {
			// スター付与状態なら
			if (getInvincible()) {
				have_item.starMode(enemy, rowchar, colchar);
				return true;
			}
			// それ以外なら
			else {
				isLive = false;
				return true;
			}
		}
		return false;
	}

	// 死亡パターン３：強制死亡
	public void die_char() {
		isLive = false;
	}

	// キャラを動かす
	public void moveChar(int addrow, int addcol, int setdire) {
		if (!mirror_now || invincible) {
			normal_moveChar(addrow, addcol, setdire);
		} else {
			abnormal_moveChar(addrow, addcol, setdire);
		}
	}

	// 正常にキャラが動く
	public void normal_moveChar(int addrow, int addcol, int setdire) {
		int tmp_row = addrow + rowchar;
		int tmp_col = addcol + colchar;
		direction = setdire;

		if (tmp_row == meiro.getRowstart() + 1 && tmp_col == meiro.getColstart()) {
			// スタート時に下に行くときにマグマに落ちた判定にしました
			isLive = false;
		} else if (!meiro.getWall(tmp_row, tmp_col)) {
			colchar = tmp_col;
			rowchar = tmp_row;
			have_item.gettingItem(getRowchar(), getColchar());
			// トラップを踏んだ時の対処
			if (step_trap.steppingMirror(getRowchar(), getColchar()) && !invincible) {
				step_trap.setMirrorMode();
			}
		}
		if (invincible) {
			have_item.starMode(enemy, rowchar, colchar);
		} else {
			deathChara(enemy, getRowchar(), getColchar());
		}
		setChanged();
		notifyObservers();
	}

	// ミラー発動中のランダムな動き
	public void abnormal_moveChar(int addrow, int addcol, int setdire) {
		step_trap.Random_setMove(getRowchar(), getColchar(), getDirechar(), meiro);
		rowchar = step_trap.getRandomRow();
		colchar = step_trap.getRandomCol();
		direction = step_trap.getRandomDirection();
		have_item.gettingItem(getRowchar(), getColchar());
		// さらにトラップを踏んでしまったときの処理
		if (step_trap.steppingMirror(getRowchar(), getColchar()) && !invincible) {
			step_trap.setMirrorMode();
		}
		if (invincible) {
			have_item.starMode(enemy, rowchar, colchar);
		} else {
			deathChara(enemy, getRowchar(), getColchar());
		}
		setChanged();
		notifyObservers();
	}

	// キャラの向きを返す。上=0, 右=1, 下=2, 左=3
	public int getDirechar() {
		return direction;
	}

	// swordを使用する
	public void useSword(Enemy ene) {
		have_item.useSword(meiro, ene, rowchar, colchar, direction);
		setChanged();
		notifyObservers();
	}

	// hammerを使用する
	public void useHammer() {
		have_item.useHammer(meiro, rowchar, colchar, direction);
		setChanged();
		notifyObservers();
	}

	// starを使用する
	public void useStar(Enemy ene) {
		have_item.useStar(ene, rowchar, colchar);
		setChanged();
		notifyObservers();
	}

	// bookを使用する
	public void useBook() {
		have_item.useBook(meiro, rowchar, colchar);
		setChanged();
		notifyObservers();
	}

	// キャラが無敵かどうか
	public boolean getInvincible() {
		return invincible;
	}

	// キャラを無敵にする
	public void setInvincible() {
		invincible = true;
	}

	// キャラの無敵を解除する
	public void eraseInvincible() {
		invincible = false;
	}

	// キャラのミラー状態を付与
	public void setMirror() {
		mirror_now = true;
	}

	// キャラのミラー状態を解除
	public void eraseMirror() {
		mirror_now = false;
	}

	// 所持しているswordの数を返す
	public int getSwordCount() {
		int temp = have_item.have_getSwordCount();
		return temp;
	}

	// 所持しているHammerの数を返す
	public int getHammerCount() {
		int temp = have_item.have_getHammerCount();
		return temp;
	}

	// 所持しているStarの数を返す
	public int getStarCount() {
		int temp = have_item.have_getStarCount();
		return temp;
	}

	// 所持しているBookの数を返す
	public int getBookCount() {
		int temp = have_item.have_getBookCount();
		return temp;
	}

	// 所持しているCoinの数を返す
	public int getCoinCount() {
		int temp = have_item.have_getCoinCount();
		return temp;
	}

	// Starの残り時間を返す
	public int getStarTime() {
		return have_item.getStarTime();
	}

	// Bookの残り時間を返す
	public int getBookTime() {
		return have_item.getBookTime();
	}

	// Mirrorの残り時間を返す
	public int getMirrorTime() {
		return step_trap.getMirrorTime();
	}

	// Have＿Itemクラスを渡す
	public Have_Item getHave_Item() {
		return have_item;
	}

	// Step_Trapクラスを渡す
	public Step_Trap getStep_Trap() {
		return step_trap;
	}

	// Timerの管理
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if (cmd.equals("Judge")) {
			isEnemy();
			setChanged();
			notifyObservers();
		}
	}

	// モデルのテスト用 *他の人の気にしなくてよし!!
	public void printChar() {
		int mazeSize = meiro.getMazesize();
		for (int i = 0; i < mazeSize; i++) {
			for (int j = 0; j < mazeSize; j++) {
				if (i == rowchar && j == colchar) {
					System.out.print("CH");
				} else if (i == meiro.getRowstart() && j == meiro.getColstart()) {
					System.out.print("ST");
				} else if (i == meiro.getRowgoal() && j == meiro.getColgoal()) {
					System.out.print("Go");
				} else if (meiro.getWall(i, j)) {
					System.out.print("[]");
				} else {
					System.out.print("　");
				}
			}
			System.out.println();
		}
	}
}