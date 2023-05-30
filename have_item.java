import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;

/* Model : プレイヤーの所持するアイテム数を管理＋使用 */
class Have_Item extends Observable implements ActionListener {
	protected Maze maze;
	protected Character ch;
	protected AllItem allitem; // アイテム一覧
	protected int swordcount; // sword所持数
	protected int hammercount; // hammer 所持数
	protected int starcount; // star所持数
	protected int bookcount; // book所持数
	protected int coincount; // coin所持数

	protected javax.swing.Timer star_timer;
	protected int star_time;
	protected javax.swing.Timer book_timer;
	protected int book_time;

	protected boolean flagsword, flaghammer, flagcoin, flagbook, flaggetitem, flagstar;

	// コンストラクタ（所持数の初期化など）
	public Have_Item(Maze maze, Character c, AllItem a, Mana_Item add_item) {
		this.maze = maze;
		ch = c;
		allitem = a;
		swordcount = add_item.sword;
		hammercount = add_item.hammer;
		starcount = add_item.star;
		bookcount = add_item.book;
		coincount = 0;

		star_timer = new javax.swing.Timer(1000, this);
		star_timer.setActionCommand("Star");
		book_timer = new javax.swing.Timer(1000, this);
		book_timer.setActionCommand("Book");

		flagsword = false;
		flaghammer = false;
		flagbook = false;
		flagcoin = false;
		flaggetitem = false;
		flagstar = false;
	}

	// アイテムを拾う
	public void gettingItem(int rowchar, int colchar) {
		if (allitem.getSwordPosition(rowchar, colchar)) {
			swordcount++;
			allitem.eraseSword(rowchar, colchar);
			flaggetitem = true;
		}
		if (allitem.getHammerPosition(rowchar, colchar)) {
			hammercount++;
			allitem.eraseHammer(rowchar, colchar);
			flaggetitem = true;
		}
		if (allitem.getStarPosition(rowchar, colchar)) {
			starcount++;
			allitem.eraseStar(rowchar, colchar);
			flaggetitem = true;
		}
		if (allitem.getBookPosition(rowchar, colchar)) {
			bookcount++;
			allitem.eraseBook(rowchar, colchar);
			flaggetitem = true;
		}
		if (allitem.getCoinPosition(rowchar, colchar)) {
			coincount++;
			allitem.eraseCoin(rowchar, colchar);
			flagcoin = true;
		}
		setChanged();
		notifyObservers();
	}

	// swordを使用する
	public void useSword(Maze maze, Enemy ene, int rowchar, int colchar, int direction) {
		if (swordcount > 0) {
			flagsword = true;
			if (direction == 0) {
				if (ene.getEnemyPosition(rowchar - 1, colchar)) {
					ene.killOneEnemy(ene.getEnemyKey(rowchar - 1, colchar));
					swordcount--;
				} else if (ene.getEnemyPosition(rowchar - 2, colchar) && !maze.getWall(rowchar - 1, colchar)) {
					ene.killOneEnemy(ene.getEnemyKey(rowchar - 2, colchar));
					swordcount--;
				}
			} else if (direction == 1) {
				if (ene.getEnemyPosition(rowchar, colchar + 1)) {
					ene.killOneEnemy(ene.getEnemyKey(rowchar, colchar + 1));
					swordcount--;
				} else if (ene.getEnemyPosition(rowchar, colchar + 2) && !maze.getWall(rowchar, colchar + 1)) {
					ene.killOneEnemy(ene.getEnemyKey(rowchar, colchar + 2));
					swordcount--;
				}
			} else if (direction == 2) {
				if (ene.getEnemyPosition(rowchar + 1, colchar)) {
					ene.killOneEnemy(ene.getEnemyKey(rowchar + 1, colchar));
					swordcount--;
				} else if (ene.getEnemyPosition(rowchar + 2, colchar) && !maze.getWall(rowchar + 1, colchar)) {
					ene.killOneEnemy(ene.getEnemyKey(rowchar + 2, colchar));
					swordcount--;
				}
			} else if (direction == 3) {
				if (ene.getEnemyPosition(rowchar, colchar - 1)) {
					ene.killOneEnemy(ene.getEnemyKey(rowchar, colchar - 1));
					swordcount--;
				} else if (ene.getEnemyPosition(rowchar, colchar - 2) && !maze.getWall(rowchar, colchar - 1)) {
					ene.killOneEnemy(ene.getEnemyKey(rowchar, colchar - 2));
					swordcount--;
				}
			}
			setChanged();
			notifyObservers();
		}
	}

	// hammer を使用する
	public void useHammer(Maze meiro, int rowchar, int colchar, int direction) {
		if (hammercount > 0) {
			flaghammer = true;
			if (direction == 0 && meiro.getWall(rowchar - 1, colchar) && rowchar - 1 != 0) {
				meiro.breakWall(rowchar - 1, colchar);
				hammercount--;
			} else if (direction == 1 && meiro.getWall(rowchar, colchar + 1) && colchar + 1 != meiro.getMazesize() - 1
					&& rowchar != meiro.getMazesize() - 1) {
				meiro.breakWall(rowchar, colchar + 1);
				hammercount--;
			} else if (direction == 2 && meiro.getWall(rowchar + 1, colchar)
					&& rowchar + 1 != meiro.getMazesize() - 1) {
				meiro.breakWall(rowchar + 1, colchar);
				hammercount--;
			} else if (direction == 3 && meiro.getWall(rowchar, colchar - 1) && colchar - 1 != 0) {
				meiro.breakWall(rowchar, colchar - 1);
				hammercount--;
			}
			setChanged();
			notifyObservers();
		}
	}

	// star を使用
	public void useStar(Enemy ene, int rowchar, int colchar) {
		if (starcount > 0) {
			ch.eraseMirror(); // 状態異常を治す
			flagstar = true;
			starcount--;
			ch.setInvincible();
			star_time = 10;
			star_timer.start();
			ch.eraseMirror();
		}
		setChanged();
		notifyObservers();
	}

	// star状態の時の処理
	public void starMode(Enemy ene, int rowchar, int colchar) {
		// 無敵なら処理をする
		if (ch.getInvincible()) {
			if (ene.getEnemyPosition(rowchar, colchar)) {
				ene.killOneEnemy(ene.getEnemyKey(rowchar, colchar));
			}
		}
	}

	// bookを使用
	public void useBook(Maze maze, int rowchar, int colchar) {
		if (bookcount > 0) {
			bookcount--;
			flagbook = true;
			maze.resetRoute();
			Tansaku t;
			if (rowchar == maze.getRowstart() && colchar == maze.getColstart()) {
				t = new Tansaku(maze, rowchar - 1, colchar); // スタート地点で使用したときの例外処理
			} else {
				t = new Tansaku(maze, rowchar, colchar);
			}
			t.make_line();
			book_time = select_booktime(maze.getMode());
			book_timer.start();
		}
		setChanged();
		notifyObservers();
	}

	// bookの表示時間を管理
	public int select_booktime(int mode) {
		if (mode == 3) {
			return 30;
		} else if (mode == 2) {
			return 20;
		} else if (mode == 1) {
			return 10;
		} else {
			return 10;
		}
	}

	// スターとマップの時間を管理
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Star")) {
			star_time--;
			if (star_time <= 0) {
				star_timer.stop();
				ch.eraseInvincible();
				flagstar = false;
			}
			setChanged();
			notifyObservers();
		} else if (cmd.equals("Book")) {
			book_time--;
			if (book_time <= 0) {
				book_timer.stop();
				maze.resetRoute();
			}
			setChanged();
			notifyObservers();
		}
	}

	// スターの残り時間を取得する
	public int getStarTime() {
		return star_time;
	}

	// マップの残り時間を取得する
	public int getBookTime() {
		return book_time;
	}

	// ソードの所持数を取得する
	public int have_getSwordCount() {
		return swordcount;
	}

	// ハンマーの所持数を取得する
	public int have_getHammerCount() {
		return hammercount;
	}

	// スターの所持数を取得する
	public int have_getStarCount() {
		return starcount;
	}

	// マップの所持数を取得する
	public int have_getBookCount() {
		return bookcount;
	}

	// コインの所持数を取得する
	public int have_getCoinCount() {
		return coincount;
	}

	// ソードのSEをbooleanで返す
	public boolean getSwordSE() {
		boolean temp = flagsword;
		flagsword = false;
		return temp;
	}

	// ハンマーのSEをbooleanで返す
	public boolean getHammerSE() {
		boolean temp = flaghammer;
		flaghammer = false;
		return temp;
	}

	// マップのSEをbooleanで返す
	public boolean getBookSE() {
		boolean temp = flagbook;
		flagbook = false;
		return temp;
	}

	// コインのSEをBooleanで返す
	public boolean getCoinSE() {
		boolean temp = flagcoin;
		flagcoin = false;
		return temp;
	}

	// アイテムゲットのSEをBooleanで返す
	public boolean getGetitemSE() {
		boolean temp = flaggetitem;
		flaggetitem = false;
		return temp;
	}

	// スターのSEをBooleanで返す
	public boolean getStarSE() {
		boolean temp = flagstar;
		flagstar = false;
		return temp;
	}
}
