import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;

/* View : ゲーム画面（迷路部） */
class MazePanel extends JPanel implements Observer {
	protected Maze maze;
	protected Character ch;
	protected Enemy ene;
	private int mazeSize;
	private boolean[][] wall;
	private int startRow, startCol, goalRow, goalCol;
	private int rowchar, colchar, rowenemy, colenemy, randm, enemy_id, height;
	private Random rand;
	private Dimension screenSize;
	private Image img;
	private Ellipse2D.Double inner;
	protected AllItem allitem;
	protected AllTrap alltrap;

	public MazePanel(Maze maze, Character ch, Enemy ene, AllItem allitem, AllTrap alltrap) {
		this.maze = maze;
		this.ch = ch;
		this.ene = ene;
		this.allitem = allitem;
		this.alltrap = alltrap;
		mazeSize = maze.getMazesize();
		startRow = maze.getRowstart();
		startCol = maze.getColstart();
		goalRow = maze.getRowgoal();
		goalCol = maze.getColgoal();

		rand = new Random();
		randm = rand.nextInt(11);
		wall = new boolean[mazeSize][mazeSize];

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		if (screenSize.getHeight() < 1000) {
			height = 300;
		} else {
			this.setSize(1000, 1000);
			height = 400;
		}
		rowchar = ch.getRowchar();
		colchar = ch.getColchar();

		this.ch.addObserver(this);
		this.ene.addObserver(this);
		this.setBackground(Color.WHITE);
		this.setFocusable(true);
		this.setOpaque(true);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		colchar = ch.getColchar();
		rowchar = ch.getRowchar();
		for (int x = 0; x < mazeSize; x++) {
			for (int y = 0; y < mazeSize; y++) {
				wall[x][y] = maze.getWall(x, y);
			}
		}

		for (int i = rowchar - 3; i <= rowchar + 4; i++) { // 味方を中心に上下左右3マス表示する場合
			for (int j = colchar - 3; j <= colchar + 4; j++) {
				if (i >= 0 && i < mazeSize && j >= 0 && j < mazeSize) {
					if (i == startRow && j == startCol) {
						img = Toolkit.getDefaultToolkit().getImage("data/start.jpg");
						g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
					} else if (i == goalRow && j == goalCol) {
						img = Toolkit.getDefaultToolkit().getImage("data/goal2.jpg");
						g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
					} else if (wall[i][j]) {
						img = Toolkit.getDefaultToolkit().getImage("data/kabe_v1.png");
						g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
					} else if (maze.getRoute(i, j)) {
						img = Toolkit.getDefaultToolkit().getImage("data/yuka_v2.png");
						g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
					} else {
						img = Toolkit.getDefaultToolkit().getImage("data/yuka_v1.png");
						g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
					}
				} else {
					if (j == mazeSize || i == mazeSize || (j == -1 && i <= mazeSize && i >= 0)
							|| (i == -1 && j >= 0 && j <= mazeSize)) {
						img = Toolkit.getDefaultToolkit().getImage("data/soto1.png");
						g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
					} else {
						img = Toolkit.getDefaultToolkit().getImage("data/soto2.png");
						g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);

					}
				}
				if (allitem.getCoinPosition(i, j)) { // コインを表示
					img = Toolkit.getDefaultToolkit().getImage("data/coin.png");
					g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
				}
				if (allitem.getSwordPosition(i, j)) { // ソードを表示
					img = Toolkit.getDefaultToolkit().getImage("data/sword.png");
					g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
				}
				if (allitem.getHammerPosition(i, j)) { // ハンマーを表示
					img = Toolkit.getDefaultToolkit().getImage("data/hammer.png");
					g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
				}
				if (allitem.getStarPosition(i, j)) {// スターを表示
					img = Toolkit.getDefaultToolkit().getImage("data/star.png");
					g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
				}
				if (allitem.getBookPosition(i, j)) {// マップを表示
					img = Toolkit.getDefaultToolkit().getImage("data/map.png");
					g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
				}
				if (alltrap.getMirrorPosition_step(i, j)) {
					img = Toolkit.getDefaultToolkit().getImage("data/trap.png");
					g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
				}
				if (i == rowchar && j == colchar) {
					if (ch.getDirechar() == 0) {
						img = Toolkit.getDefaultToolkit().getImage("data/char0.png");
						g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
					} else if (ch.getDirechar() == 1) {
						img = Toolkit.getDefaultToolkit().getImage("data/char1.png");
						g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
					} else if (ch.getDirechar() == 2) {
						img = Toolkit.getDefaultToolkit().getImage("data/char2.png");
						g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
					} else if (ch.getDirechar() == 3) {
						img = Toolkit.getDefaultToolkit().getImage("data/char3.png");
						g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
					}
				} else if (ene.getEnemyPosition(i, j)) {
					enemy_id = (ene.getEnemyKey(i, j) + randm) % 11;// ランダムにenemykeyを割り当てて表示する敵を変える
					if (enemy_id == 0) {
						img = Toolkit.getDefaultToolkit().getImage("data/ahriman.png");
						g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
					} else if (enemy_id == 1) {
						img = Toolkit.getDefaultToolkit().getImage("data/w_snake.png");
						g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
					} else if (enemy_id == 2) {
						img = Toolkit.getDefaultToolkit().getImage("data/akuma.png");
						g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
					} else if (enemy_id == 3) {
						img = Toolkit.getDefaultToolkit().getImage("data/skull_white.png");
						g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
					} else if (enemy_id == 4) {
						img = Toolkit.getDefaultToolkit().getImage("data/shark.png");
						g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
					} else if (enemy_id == 5) {
						img = Toolkit.getDefaultToolkit().getImage("data/manticore.png");
						g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
					} else if (enemy_id == 6) {
						img = Toolkit.getDefaultToolkit().getImage("data/prominence.png");
						g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
					} else if (enemy_id == 7) {
						img = Toolkit.getDefaultToolkit().getImage("data/dragon_f.png");
						g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
					} else if (enemy_id == 8) {
						img = Toolkit.getDefaultToolkit().getImage("data/anglerfish1.png");
						g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
					} else if (enemy_id == 9) {
						img = Toolkit.getDefaultToolkit().getImage("data/sraim.png");
						g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
					} else if (enemy_id == 10) {
						img = Toolkit.getDefaultToolkit().getImage("data/knight.png");
						g.drawImage(img, 400 + (j - colchar) * 100, height + (i - rowchar) * 100, 100, 100, this);
					}
				}
			}
		}
		Graphics2D g2d = (Graphics2D) g.create(); // 迷路を円形に表示↓
		Area outter = new Area(new Rectangle(0, 0, this.getWidth(), this.getHeight()));
		if (screenSize.getHeight() < 1000) {
			inner = new Ellipse2D.Double(this.getWidth() / 100 * 12, this.getHeight() / 100 * 10,
					this.getWidth() / 10 * 7, this.getHeight() / 10 * 7);
		} else {
			inner = new Ellipse2D.Double(130, 100, 700, 700);
		}
		outter.subtract(new Area(inner));
		if (ch.getInvincible() == true) {// スターの使用中背景変更
			g2d.setColor(Color.CYAN);
		} else {
			g2d.setColor(Color.BLACK);
		}
		g2d.fill(outter); // 迷路を円形に表示↑
	}

	public void update(Observable o, Object arg) {
		repaint();
	}

	public JPanel getPanel() {
		return this;
	}
}