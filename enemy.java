import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/* Model : 1体ｎの敵に対す処理 */
class P_Enemy {
	private Maze maze;
	protected int rowenemy;
	protected int colenemy;
	protected boolean isLive; // 生きているか死んでいるか
	protected static int mazesize;
	protected int direction;
	protected boolean isMove;
	public int id = 0;

	// 初期化
	public P_Enemy(Maze maze) {
		this.maze = maze;
		mazesize = maze.getMazesize();
		isMove = false;
		isLive = true;

		do { // 敵の座標をランダムで選ぶ。キャラの目の前に現われたらまずいので、とりあえずキャラより5マス離して生成。
			Random rnd = new Random();
			rowenemy = rnd.nextInt(mazesize - 3) + 1;
			colenemy = rnd.nextInt(mazesize - 3) + 1;
		} while (maze.getWall(rowenemy, colenemy) || (rowenemy >= mazesize - 5 && colenemy <= 5));
	}

	// 敵のx座標を返す
	public int getRowP_Enemy() {
		return rowenemy;
	}

	// 敵のy座標を返す
	public int getColP_Enemy() {
		return colenemy;
	}

	// 敵一体を動かす
	public void moveOneEnemy() {
		do {
			Random rnd = new Random();
			direction = rnd.nextInt(4); // 0, 1, 2, 3
			if (direction == 0 && !maze.getWall(rowenemy - 1, colenemy) && rowenemy - 1 != 0) {
				rowenemy--; // 上に移動
				isMove = true;
			} else if (direction == 1 && !maze.getWall(rowenemy, colenemy + 1)) {
				colenemy++; // 右に移動
				isMove = true;
			} else if (direction == 2 && !maze.getWall(rowenemy + 1, colenemy) && rowenemy + 1 != maze.getRowstart()) {
				rowenemy++; // 下に移動
				isMove = true;
			} else if (direction == 3 && !maze.getWall(rowenemy, colenemy - 1)) {
				colenemy--; // 左に移動
				isMove = true;
			} else {
				isMove = false;
			}
		} while (!isMove);
		isMove = false;
		return;
	}

	// 生きていればtrueを返す
	public boolean getlive() {
		return isLive;
	}

	// 敵が死ぬ
	public void die() {
		isLive = false;
	}

	// 敵のIDを設定
	public void setId(int id) {
		this.id = id;
	}

	// 敵のIDを返す
	public int getId() {
		return id;
	}
}

/* Model : 敵全体の管理・処理 */
class Enemy extends Observable implements ActionListener {
	private int maxenemy;
	private Map<Integer, P_Enemy> P_ene;
	private javax.swing.Timer timer;
	private boolean flagdeath;

	// コンストラクタ（敵生成）
	public Enemy(Maze m, int mode) {
		P_ene = new HashMap<>();
		maxenemy = selectMaxEnemy(mode);
		flagdeath = false;

		for (int i = 0; i < maxenemy; i++) {
			P_Enemy temp = new P_Enemy(m);
			temp.setId(i);
			P_ene.put(i, temp);
		}

		timer = new javax.swing.Timer(1000, this);
		timer.start();
	}

	// 難易度に応じて敵の数を返す
	public int selectMaxEnemy(int mode) {
		if (mode == 3) {
			return 200;
		} else if (mode == 2) {
			return 30;
		} else if (mode == 1) {
			return 7;
		} else {
			return 4;
		}
	}

	// 敵のタイマーを停止する
	public void stopTimer() {
		timer.stop();
	}

	// x座標, y座標を渡して、その位置に敵がいればtrue
	public boolean getEnemyPosition(int x, int y) {
		for (P_Enemy a : P_ene.values()) {
			if (a.getRowP_Enemy() == x && a.getColP_Enemy() == y) {
				return true;
			}
		}
		return false;
	}

	// 任意のx, y座標に敵がいればそのキーを返す。いなければ-1を返す。
	public int getEnemyKey(int x, int y) {
		int key = -1;
		for (P_Enemy a : P_ene.values()) {
			if (a.getRowP_Enemy() == x && a.getColP_Enemy() == y) {
				key = a.getId();
			}
		}
		return key;
	}

	// 敵の設定数を返す
	public int getEnemyCount() {
		return maxenemy;
	}

	// 任意の敵一体の値(value)を返す
	public P_Enemy getOneEnemyValue(int id) {
		return P_ene.get(id);
	}

	// 任意の敵一体を殺す
	public void killOneEnemy(int id) {
		P_ene.remove(id);
		flagdeath = true;
		setChanged();
		notifyObservers();
	}

	// SEをbooleanで返す
	public boolean getFlagDeath() {
		boolean temp = flagdeath;
		flagdeath = false;
		return temp;
	}

	// timerの処理
	// 敵をランダムに動かす
	public void actionPerformed(ActionEvent e) {
		for (P_Enemy a : P_ene.values()) {
			a.moveOneEnemy();
		}
		setChanged();
		notifyObservers();
	}
}