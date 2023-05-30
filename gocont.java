import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/* Controller : ゲームオーバー画面に対して */
class GameoverCont implements ActionListener, KeyListener {
	public Game frame;

	// コンストラクタ
	public GameoverCont(Game g) {
		frame = g;
		frame.addKeyListener(this);
		frame.go_view.b1.addActionListener(this);
		frame.go_view.b1.setActionCommand("Gameover");
	}

	// キーリスナーを取り除く
	public void remove() {
		frame.removeKeyListener(this);
	}

	// ボタンの操作
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Gameover")) {
			frame.gameover_start();
			return;
		}
	}

	// キーの操作
	public void keyPressed(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {

	}
}