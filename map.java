import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;

/* Model : ２次ベクトル構造体 */
class Position_make {
	int x;
	int y;

	public Position_make() {

	}

	public Position_make(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

/* Model : 深さ優先探索 */
class Tansaku {
	public Maze m;
	private Position_make ch_p;
	private Stack<Position_make> route;
	private Stack<Position_make> branch;
	private Position_make cur;
	private Position_make next;
	private Position_make prev;
	private Position_make[] candidate;
	private boolean direction; // true = FORWARD , false = BACKWARD
	private int n; // 次の行先が可能な場所の個数を格納
	private static Position_make[] possible_moves = { new Position_make(0, -1), new Position_make(1, 0),
			new Position_make(0, 1), new Position_make(-1, 0) };
	private Position_make tmp;
	private boolean[][] tansaku_zumi;

	public Tansaku(Maze maze, int chara_x, int chara_y) {
		tmp = new Position_make();

		this.m = maze;
		tansaku_zumi = new boolean[m.getMazesize()][m.getMazesize()];
		for (int i = 0; i < m.getMazesize(); i++) {
			for (int j = 0; j < m.getMazesize(); j++) {
				tmp.x = i;
				tmp.y = j;
				if (is_Start(m, tmp) || is_Wall(m, tmp)) {
					tansaku_zumi[i][j] = true;
				} else {
					tansaku_zumi[i][j] = false;
				}
			}
		}
		ch_p = new Position_make(chara_x, chara_y);
		route = new Stack<Position_make>();
		branch = new Stack<Position_make>();
		candidate = new Position_make[4];
		direction = true;
		solve();
	}

	// 探索パート
	public void solve() {
		prev = ch_p;
		cur = ch_p;
		route.push(cur);

		while (!is_Goal(m, cur)) {
			already_Tansaku(tansaku_zumi, cur);
			n = next_possible_move(m, cur, prev, candidate, direction);
			prev = cur;
			if (n == 0) {
				direction = false;
				cur = route.pop();
			} else if (n == 1) {
				if (direction == true) {
					route.push(cur);
					cur = candidate[0];
				} else {
					cur = route.pop();
				}
			} else {
				if (direction == true) {
					route.push(cur);
					for (int k = 0; k < n; k++) {
						branch.push(candidate[k]);
					}
					cur = branch.pop();

					if (!is_Goal(m, cur)) {
						route.push(cur);
					}
				} else {
					int check = 0;
					Position_make distance = new Position_make();
					Position_make maybe_next = new Position_make();
					maybe_next = branch.pop();
					distance.x = maybe_next.x - cur.x;
					distance.y = maybe_next.y - cur.y;
					for (int k = 0; k < 4; k++) {
						if (distance.x == possible_moves[k].x && distance.y == possible_moves[k].y) {
							check = 1;
							break;
						}
					}
					if (check == 1) {
						route.push(cur);
						cur = maybe_next;
						direction = true;
						if (is_Goal(m, cur) == false) {
							route.push(cur);
						}
					} else {
						branch.push(maybe_next);
						cur = route.pop();
					}
				}
			}
		}
		// ゴールに到達した
		while (!route.empty()) {
			tmp = route.pop();
			m.writeRoute(tmp.x, tmp.y);
		}
	}

	// ゴールについたよ
	public void make_line() {
		return;
	}

	// 探索したことを制御する
	public boolean is_Tansaku(boolean[][] tansaku_zumi, Position_make p) {
		if (tansaku_zumi[p.x][p.y]) {
			return true;
		}
		return false;
	}

	// 探索済みにする
	public void already_Tansaku(boolean[][] tansaku_zumi, Position_make p) {
		tansaku_zumi[p.x][p.y] = true;
	}

	// 次に行動可能な方向を探索する
	public int next_possible_move(Maze m, Position_make cur, Position_make prev, Position_make[] candidate,
			boolean direction) {
		int nn = 0;
		for (int i = 0; i < 4; i++) {
			next = new Position_make();
			next = move(cur, possible_moves[i]);
			if (direction == true) {
				if (is_in(m, next) && !is_Start(m, next) && !is_Wall(m, next) && !is_Tansaku(tansaku_zumi, next)
						&& !equal(next, prev)) {
					candidate[nn] = next;
					nn += 1;
				}
			} else {
				if (is_in(m, next) && !is_Start(m, next) && !is_Wall(m, next) && !equal(next, prev)) {
					candidate[nn] = next;
					nn += 1;
				}
			}

		}
		return nn;
	}

	// 移動する
	public Position_make move(Position_make p, Position_make d) {
		Position_make q = new Position_make();
		q.x = p.x + d.x;
		q.y = p.y + d.y;
		return q;
	}

	// ２点が同じかどうか
	public boolean equal(Position_make p, Position_make q) {
		if (p.x == q.x && p.y == q.y) {
			return true;
		}
		return false;
	}

	// 迷路の枠のなかに存在しているか
	public boolean is_in(Maze m, Position_make p) {
		if (m.getWall(p.x, p.y) == false) {
			return true;
		}
		return false;
	}

	// Start地点もWallと同じように壁と処理する
	public boolean is_Start(Maze m, Position_make p) {
		if (p.x == m.getRowstart() && p.y == m.getColstart()) {
			return true;
		}
		return false;
	}

	// 壁かどうか
	public boolean is_Wall(Maze m, Position_make p) {
		if (m.getWall(p.x, p.y)) {
			return true;
		}
		return false;
	}

	// ゴールかどうか
	public boolean is_Goal(Maze m, Position_make p) {
		if (p.x == m.getRowgoal() && p.y == m.getColgoal()) {
			return true;
		}
		return false;
	}
}