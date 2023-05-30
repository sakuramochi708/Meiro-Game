import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/* Model : 迷路 */
class Maze {
	protected int mazeSize;
	// 壁: true, 道: false
	protected static boolean[][] wall;
	protected static int row;
	protected static int col;
	protected static Stack<Integer> rowStack = new Stack<Integer>();
	protected static Stack<Integer> colStack = new Stack<Integer>();
	protected int startRow;
	protected int startCol = 1;
	protected int goalRow = 0;
	protected int goalCol;
	protected boolean[][] route;
	private int mode;

	// コンストラクタ（迷路生成）
	public Maze(int mode) {
		// 初期化
		this.mode = mode;
		mazeSize = setMazeSize(mode);
		startRow = mazeSize - 1;
		goalCol = mazeSize - 2;
		wall = new boolean[mazeSize][mazeSize];
		route = new boolean[mazeSize][mazeSize];
		for (int i = 0; i < mazeSize; i++) {
			for (int j = 0; j < mazeSize; j++) {
				wall[i][j] = true;
				route[i][j] = false;
			}
		}

		// ランダムに開始位置を選ぶ（1 〜 mazeSize - 2）
		Random rnd = new Random();
		row = rnd.nextInt(mazeSize - 2) + 1;
		col = rnd.nextInt(mazeSize - 2) + 1;
		wall[row][col] = false;
		rowStack.push(row);
		colStack.push(col);

		boolean continueFlag = true;

		// 以下、wall[][]全体を埋めるまで繰り返し
		while (continueFlag) {

			// 上下左右のいずれかに限界まで道を伸ばす
			extendPath();

			// 既にある道から次の開始位置を選ぶ（0 〜 mazeSize - 1（かつ 偶数？））
			continueFlag = false;

			while (!rowStack.empty() && !colStack.empty()) {
				row = rowStack.pop();
				col = colStack.pop();

				if (/* row % 2 == 0 && col % 2 == 0 && */canExtendPath()) {
					continueFlag = true;
					break;
				}
			}
		}
		setStartGoal();
	}

	// 難易度と迷路の大きさを結び付けるメソッド
	public int setMazeSize(int mode) {
		if (mode == 3) {
			// 地獄級
			return 100;
		} else if (mode == 2) {
			// 上級
			return 50;
		} else if (mode == 1) {
			// 中級
			return 30;
		} else {
			// 初級
			return 20;
		}
	}

	// 道を拡張するメソッド
	public void extendPath() {
		boolean extendFlag = true;

		while (extendFlag) {
			extendFlag = extendPathSub();
		}
	}

	// 道の拡張に成功したらtrue、失敗したらfalseを返すメソッド
	public boolean extendPathSub() {
		Random rmd = new Random();
		// 上: 0, 下: 1, 左: 2, 右: 3
		int direction = rmd.nextInt(4);

		for (int i = 0; i < 4; i++) {
			direction = (direction + i) % 4;
			if (canExtendPathWithDir(direction)) {
				movePoint(direction);
				return true;
			}
		}

		return false;
	}

	// 指定した方向へ拡張可能ならばtrue、不可能ならばfalseを返すメソッド
	public boolean canExtendPathWithDir(int direction) {
		int exRow = row, exCol = col;

		switch (direction) {
			case 0: // 上
				exRow--;
				break;

			case 1: // 下
				exRow++;
				break;

			case 2: // 左
				exCol--;
				break;

			case 3: // 右
				exCol++;
				break;
		}

		if (countSurroundingPath(exRow, exCol) > 1) {
			return false;
		}

		return true;
	}

	// 周囲1マスにある道の数を数えるメソッド
	public int countSurroundingPath(int row, int col) {
		int num = 0;

		if (row - 1 < 0 || !wall[row - 1][col]) {
			num++;
		}
		if (row + 1 > mazeSize - 1 || !wall[row + 1][col]) {
			num++;
		}
		if (col - 1 < 0 || !wall[row][col - 1]) {
			num++;
		}
		if (col + 1 > mazeSize - 1 || !wall[row][col + 1]) {
			num++;
		}

		return num;
	}

	// 指定した方向へ1マスrowとcolを移動させるメソッド
	public void movePoint(int direction) {
		switch (direction) {
			case 0: // 上
				row--;
				break;

			case 1: // 下
				row++;
				break;

			case 2: // 左
				col--;
				break;

			case 3: // 右
				col++;
				break;
		}

		wall[row][col] = false;
		rowStack.push(row);
		colStack.push(col);
	}

	// 上下左右いずれかの方向へ移動できるならtrue、できないならfalseを返すメソッド
	public boolean canExtendPath() {
		return (canExtendPathWithDir(0) || canExtendPathWithDir(1) || canExtendPathWithDir(2)
				|| canExtendPathWithDir(3));
	}

	// モデルのテスト用 *他の人の気にしなくてよし!!
	public void printMaze() {
		for (int i = 0; i < mazeSize; i++) {
			for (int j = 0; j < mazeSize; j++) {
				if (i == startRow && j == startCol) {
					System.out.print("ST");
				} else if (i == goalRow && j == goalCol) {
					System.out.print("Go");
				} else if (wall[i][j]) {
					System.out.print("[]");
				} else {
					System.out.print("　");
				}
			}
			System.out.println();
		}
	}

	// スタートとゴールの前方4ますを道にする
	public void setStartGoal() {
		wall[startRow - 1][startCol] = false;
		wall[startRow - 2][startCol] = false;
		wall[startRow - 1][startCol + 1] = false;
		wall[startRow - 2][startCol + 1] = false;
		wall[goalRow + 1][goalCol] = false;
		wall[goalRow + 2][goalCol] = false;
		wall[goalRow + 1][goalCol - 1] = false;
		wall[goalRow + 2][goalCol - 1] = false;
		// v4_3 追記
		wall[startRow][startCol] = false;
		wall[goalRow][goalCol] = false;
	}

	// ゴールのx座標を返す
	public int getRowgoal() {
		return goalRow;
	}

	// ゴールのy座標を返す
	public int getColgoal() {
		return goalCol;
	}

	// スタートのx座標を返す
	public int getRowstart() {
		return startRow;
	}

	// スタートのx座標を返す
	public int getColstart() {
		return startCol;
	}

	// 迷路のサイズ(mazesize)を返す
	public int getMazesize() {
		return mazeSize;
	}

	// 指定した座標が壁ならtrue, 道ならfalseを返す
	public boolean getWall(int x, int y) {
		return wall[x][y];
	}

	// 壁を壊す
	public void breakWall(int x, int y) {
		wall[x][y] = false;
	}

	// ゴール地点ならtrue, そうでなければfalseを返す
	public boolean isGoal(int x, int y) {
		if (x == goalRow && y == goalCol) {
			return true;
		} else {
			return false;
		}
	}

	// スタート地点ならtrue, そうでなければfalseを返す
	public boolean isStart(int x, int y) {
		if (x == startRow && y == startCol) {
			return true;
		} else {
			return false;
		}
	}

	// 迷路のサイズの中にいるならtrue, そうでなければfalseを返す
	public boolean is_in(int x, int y) {
		if (0 <= x && x <= mazeSize - 1 && 0 <= y && y <= mazeSize - 1) {
			return true;
		}
		return false;
	}

	// 指定したマスがゴールまでの軌跡上にあるため、指定するメソッド
	public void writeRoute(int x, int y) {
		route[x][y] = true;
	}

	// 探索で得た軌跡の情報をリセットするメソッド
	public void resetRoute() {
		for (int i = 0; i < mazeSize; i++) {
			for (int j = 0; j < mazeSize; j++) {
				route[i][j] = false;
			}
		}
	}

	// そのマスがゴールまでの軌跡ならtrue, そうでなければfalseを返す
	public boolean getRoute(int x, int y) {
		return route[x][y];
	}

	// 難易度のモード値を返す
	public int getMode() {
		return mode;
	}
}