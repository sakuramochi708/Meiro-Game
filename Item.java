import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/* Model : アイテムの管理を行う（親クラス） */
class Item {
	protected Maze meiro;
	protected int rowitem;
	protected int colitem;
	protected int mazesize;
	protected int id;
	protected boolean isSet;

	// 初期化
	public Item(Maze maze) {
		meiro = maze;
		mazesize = meiro.getMazesize();
		// アイテムの座標をランダムで選ぶ。
		do {
			Random rnd = new Random();
			rowitem = rnd.nextInt(mazesize - 3);
			colitem = rnd.nextInt(mazesize - 3);
		} while (meiro.getWall(rowitem, colitem));
		isSet = true;
	}

	// アイテムのx座標を返す
	public int getRowItem() {
		return rowitem;
	}

	// アイテムのy座標を返す
	public int getColItem() {
		return colitem;
	}

	// アイテムにIDを振る
	public void setId(int id) {
		this.id = id;
	}

	// アイテムのIDを返す
	public int getId() {
		return id;
	}

	// 設置されているアイテムを消す
	public void eraseItem() {
		isSet = false;
	}
}

/* Model : アイテム（ソード） */
class Sword extends Item {
	// アイテム・Sword
	public Sword(Maze maze) {
		super(maze);
	}
}

/* Model : アイテム（ハンマー） */
class Hammer extends Item {
	// アイテム・Hammer
	public Hammer(Maze maze) {
		super(maze);
	}
}

/* Model : アイテム（スター） */
class Star extends Item {
	// アイテム・Star
	public Star(Maze maze) {
		super(maze);
	}
}

/* Model : アイテム（マップ） */
class Book extends Item {
	// アイテム・Book
	public Book(Maze maze) {
		super(maze);
	}
}

/* Model : アイテム（コイン） */
class Coin extends Item {
	// アイテム・Coin
	public Coin(Maze maze) {
		super(maze);
	}
}

/* Model : マップのアイテム情報を管理 */
class AllItem extends Observable {
	private int maxsword, maxhammer, maxstar, maxbook, maxcoin;
	private int mode;
	private Map<Integer, Sword> swordmap;
	private Map<Integer, Hammer> hammermap;
	private Map<Integer, Star> starmap;
	private Map<Integer, Book> bookmap;
	private Map<Integer, Coin> coinmap;

	// コンストラクタ（アイテムを生成）
	public AllItem(Maze maze, Enemy ene) {
		swordmap = new HashMap<>();
		hammermap = new HashMap<>();
		starmap = new HashMap<>();
		bookmap = new HashMap<>();
		coinmap = new HashMap<>();

		mode = maze.getMode();

		if (mode == 3) { // 地獄級
			maxsword = 50;
			maxhammer = 10;
			maxstar = 10;
			maxbook = 5;
			maxcoin = 100;
		} else if (mode == 2) { // 上級
			maxsword = 15;
			maxhammer = 8;
			maxstar = 5;
			maxbook = 3;
			maxcoin = 25;
		} else if (mode == 1) { // 中級
			maxsword = 5;
			maxhammer = 5;
			maxstar = 3;
			maxbook = 3;
			maxcoin = 9;
		} else { // 初級
			maxsword = 10;
			maxhammer = 10;
			maxstar = 5;
			maxbook = 5;
			maxcoin = 4;
		}

		for (int i = 0; i < maxsword; i++) {
			boolean ovarlap = false;
			Sword tempsword = new Sword(maze);
			if (!swordmap.isEmpty()) {
				for (Sword a : swordmap.values()) {
					if (tempsword.getRowItem() == a.getRowItem() && tempsword.getColItem() == a.getColItem()) {
						ovarlap = true;
					}
				}
			}
			if (!ovarlap) {
				tempsword.setId(i);
				swordmap.put(i, tempsword);
			} else {
				i--;
			}
		}

		for (int i = 0; i < maxhammer; i++) {
			boolean ovarlap = false;
			Hammer temphammer = new Hammer(maze);
			if (!hammermap.isEmpty()) {
				for (Hammer a : hammermap.values()) {
					if (temphammer.getRowItem() == a.getRowItem() && temphammer.getColItem() == a.getColItem()) {
						ovarlap = true;
					}
				}
			}
			if (!ovarlap) {
				temphammer.setId(i);
				hammermap.put(i, temphammer);
			} else {
				i--;
			}
		}

		for (int i = 0; i < maxstar; i++) {
			boolean ovarlap = false;
			Star tempstar = new Star(maze);
			if (!starmap.isEmpty()) {
				for (Star a : starmap.values()) {
					if (tempstar.getRowItem() == a.getRowItem() && tempstar.getColItem() == a.getColItem()) {
						ovarlap = true;
					}
				}
			}
			if (!ovarlap) {
				tempstar.setId(i);
				starmap.put(i, tempstar);
			} else {
				i--;
			}
		}

		for (int i = 0; i < maxbook; i++) {
			boolean ovarlap = false;
			Book tempbook = new Book(maze);
			if (!bookmap.isEmpty()) {
				for (Book a : bookmap.values()) {
					if (tempbook.getRowItem() == a.getRowItem() && tempbook.getColItem() == a.getColItem()) {
						ovarlap = true;
					}
				}
			}
			if (!ovarlap) {
				tempbook.setId(i);
				bookmap.put(i, tempbook);
			} else {
				i--;
			}
		}

		for (int i = 0; i < maxcoin; i++) {
			boolean ovarlap = false;
			Coin tempcoin = new Coin(maze);
			if (!coinmap.isEmpty()) {
				for (Coin a : coinmap.values()) {
					if (tempcoin.getRowItem() == a.getRowItem() && tempcoin.getColItem() == a.getColItem()) {
						ovarlap = true;
					}
				}
			}
			if (!ovarlap) {
				tempcoin.setId(i);
				coinmap.put(i, tempcoin);
			} else {
				i--;
			}
		}
	}

	// 剣の最大数を返す
	public int getSword_max() {
		return maxsword;
	}

	// ハンマーの最大数を返す
	public int getHammer_max() {
		return maxhammer;
	}

	// スターの最大数を返す
	public int getStar_max() {
		return maxstar;
	}

	// 本の最大数を返す
	public int getBook_max() {
		return maxbook;
	}

	// コインの最大数を返す
	public int getCoin_max() {
		return maxcoin;
	}

	// 任意のx, y座標の剣を消す
	public void eraseSword(int x, int y) {
		boolean temp = false;
		for (Sword a : swordmap.values()) {
			if (a.getRowItem() == x && a.getColItem() == y) {
				swordmap.remove(a.getId());
				temp = true;
				break;
			}
		}
		if (temp) {
			setChanged();
			notifyObservers();
		}
	}

	// 任意のx, y座標のハンマーを消す
	public void eraseHammer(int x, int y) {
		boolean temp = false;
		for (Hammer a : hammermap.values()) {
			if (a.getRowItem() == x && a.getColItem() == y) {
				hammermap.remove(a.getId());
				temp = true;
				break;
			}
		}
		if (temp) {
			setChanged();
			notifyObservers();
		}
	}

	// 任意のx, y座標のスターを消す
	public void eraseStar(int x, int y) {
		boolean temp = false;
		for (Star a : starmap.values()) {
			if (a.getRowItem() == x && a.getColItem() == y) {
				starmap.remove(a.getId());
				temp = true;
				break;
			}
		}
		if (temp) {
			setChanged();
			notifyObservers();
		}
	}

	// 任意のx, y座標の本を消す
	public void eraseBook(int x, int y) {
		boolean temp = false;
		for (Book a : bookmap.values()) {
			if (a.getRowItem() == x && a.getColItem() == y) {
				bookmap.remove(a.getId());
				temp = true;
				break;
			}
		}
		if (temp) {
			setChanged();
			notifyObservers();
		}
	}

	// 任意のx, y座標のコインを消す
	public void eraseCoin(int x, int y) {
		boolean temp = false;
		for (Coin a : coinmap.values()) {
			if (a.getRowItem() == x && a.getColItem() == y) {
				coinmap.remove(a.getId());
				temp = true;
				break;
			}
		}
		if (temp) {
			setChanged();
			notifyObservers();
		}
	}

	// x, y座標を指定して、そこに剣があればtrue
	public boolean getSwordPosition(int x, int y) {
		for (Sword a : swordmap.values()) {
			if (a.getRowItem() == x && a.getColItem() == y) {
				return true;
			}
		}
		return false;
	}

	// x, y座標を指定して、そこにハンマーがあればtrue
	public boolean getHammerPosition(int x, int y) {
		for (Hammer a : hammermap.values()) {
			if (a.getRowItem() == x && a.getColItem() == y) {
				return true;
			}
		}
		return false;
	}

	// x, y座標を指定して、そこにスターがあればtrue
	public boolean getStarPosition(int x, int y) {
		for (Star a : starmap.values()) {
			if (a.getRowItem() == x && a.getColItem() == y) {
				return true;
			}
		}
		return false;
	}

	// x, y座標を指定して、そこに本があればtrue
	public boolean getBookPosition(int x, int y) {
		for (Book a : bookmap.values()) {
			if (a.getRowItem() == x && a.getColItem() == y) {
				return true;
			}
		}
		return false;
	}

	// x, y座標を指定して、そこにコインがあればtrue
	public boolean getCoinPosition(int x, int y) {
		for (Coin a : coinmap.values()) {
			if (a.getRowItem() == x && a.getColItem() == y) {
				return true;
			}
		}
		return false;
	}

	// 任意の剣1つの値(value)を返す
	public Sword getOneSword(int id) {
		return swordmap.get(id);
	}

	// 任意のハンマー1つの値(value)を返す
	public Hammer getOneHammer(int id) {
		return hammermap.get(id);
	}

	// 任意のスター1つの値(value)を返す
	public Star getOneStar(int id) {
		return starmap.get(id);
	}

	// 任意の本1つの値(value)を返す
	public Book getOneBook(int id) {
		return bookmap.get(id);
	}

	// 任意のコイン1つの値(value)を返す
	public Coin getOneCoin(int id) {
		return coinmap.get(id);
	}
}