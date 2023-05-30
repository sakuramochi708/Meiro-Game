import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/* Model : トラップの管理を行う（親クラス） */
class Trap {
	protected int rowenemy;
	protected int colenemy;
	protected boolean isFunda;
	protected static int mazesize;
	protected int direction;
	public int id = 0;

	// 初期化
	public Trap(Maze maze) {
		mazesize = maze.getMazesize();
		isFunda = false;

		do { // トラップの座標をランダムで選ぶ。キャラの目の前に現われたらまずいので、とりあえずキャラより5マス離して生成。
			Random rnd = new Random();
			rowenemy = rnd.nextInt(mazesize - 3) + 1;
			colenemy = rnd.nextInt(mazesize - 3) + 1;
		} while (maze.getWall(rowenemy, colenemy) || (rowenemy >= mazesize - 5 && colenemy <= 5));
	}

	// トラップのx座標を返す
	public int getRowTrap() {
		return rowenemy;
	}

	// トラップのy座標を返す
	public int getColTrap() {
		return colenemy;
	}

	// 踏んであればtrueを返す
	public boolean getFunda() {
		return isFunda;
	}

	// トラップを踏む
	public void Fumu() {
		isFunda = true;
	}

	// トラップにIDを振る
	public void setId(int id) {
		this.id = id;
	}

	// トラップのIDを返す
	public int getId() {
		return id;
	}
}

/* Model : トラップ（ミラー） */
class Mirror extends Trap {
	// トラップ・ミラー（ランダムに動くようになる）
	Mirror(Maze maze) {
		super(maze);
	}
}

/* Model : トラップを迷路上に設置・管理する */
class AllTrap {
	private int maxmirror;
	private Map<Integer, Mirror> mirrormap;

	// コンストラクタ（トラップを生成）
	public AllTrap(Maze maze) {
		mirrormap = new HashMap<>();

		int mode = maze.getMode();
		if (mode == 3) {
			maxmirror = 8;
		} else if (mode == 2) {
			maxmirror = 5;
		} else if (mode == 1) {
			maxmirror = 3;
		} else {
			maxmirror = 2;
		}

		for (int i = 0; i < maxmirror; i++) {
			boolean overlap = false;
			Mirror tempmirror = new Mirror(maze);
			if (!mirrormap.isEmpty()) {
				for (Mirror a : mirrormap.values()) {
					if (tempmirror.getRowTrap() == a.getRowTrap() && tempmirror.getColTrap() == a.getColTrap()) {
						overlap = true;
					}
				}
			}
			if (!overlap) {
				tempmirror.setId(i);
				mirrormap.put(i, tempmirror);
			} else {
				i--;
			}
		}
	}

	// ミラーの最大数を返す
	public int getMirror_max() {
		return maxmirror;
	}

	// ミラーの位置
	public boolean getMirrorPosition(int x, int y) {
		for (Mirror a : mirrormap.values()) {
			if (a.getRowTrap() == x && a.getColTrap() == y) {
				return true;
			}
		}
		return false;
	}

	// 任意のミラー1つの値(values)を返す
	public Mirror getOneMirror(int id) {
		return mirrormap.get(id);
	}

	// ミラーのキーを返す。そこにいなければ-1
	public int getMirrorKey(int x, int y) {
		int key = -1;
		for (Mirror a : mirrormap.values()) {
			if (a.getRowTrap() == x && a.getColTrap() == y) {
				key = a.getId();
			}
		}
		return key;
	}

	// 任意のミラーの値（value）を返す
	public Mirror getOneMirrorValue(int id) {
		return mirrormap.get(id);
	}

	// ミラーの位置を返す。ミラーを踏んだかどうかまで踏襲
	public boolean getMirrorPosition_step(int x, int y) {
		int key = getMirrorKey(x, y);
		if (key < 0) {
			return false;
		} else {
			Mirror a = getOneMirrorValue(key);
			return a.getFunda();
		}
	}
}