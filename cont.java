import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/* Controller : ゲーム画面に対してのコントローラー */
class GameCont implements ActionListener, KeyListener {
	public Game frame;

	// コンストラクタ
	public GameCont(Game g) {
		frame = g;
		frame.addKeyListener(this);
	}

	// キーリスナーを取り外す
	public void remove() {
		frame.removeKeyListener(this);
	}

	// ボタンの定義
	public void actionPerformed(ActionEvent e) {

	}

	// キーの定義
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		if (frame.getPanelmode() == 1) {
			switch (k) {
				case KeyEvent.VK_W:
					frame.chara.moveChar(-1, 0, 0); // row, col
					if (!frame.isGoal()) {
						frame.chara.isEnemy();
					}
					break;
				case KeyEvent.VK_A:
					frame.chara.moveChar(0, -1, 3);
					if (!frame.isGoal()) {
						frame.chara.isEnemy();
					}
					break;
				case KeyEvent.VK_D:
					frame.chara.moveChar(0, 1, 1);
					if (!frame.isGoal()) {
						frame.chara.isEnemy();
					}
					break;
				case KeyEvent.VK_S:
					frame.chara.moveChar(1, 0, 2);
					if (!frame.isGoal()) {
						frame.chara.isEnemy();
					}
					break;
				case KeyEvent.VK_R:
					frame.pushReset();
					break;
				case KeyEvent.VK_L:
					frame.chara.useSword(frame.enemy);
					break;
				case KeyEvent.VK_K:
					frame.chara.useHammer();
					break;
				case KeyEvent.VK_J:
					frame.chara.useStar(frame.enemy);
					break;
				case KeyEvent.VK_I:
					frame.chara.useBook();
					break;
			}
		}
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {

	}
}