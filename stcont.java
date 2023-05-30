import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/* Controller : スタート画面に対して */
class StartCont implements ActionListener, KeyListener {
	public Game frame;

	// コンストラクタ
	public StartCont(Game g) {
		frame = g;
		frame.addKeyListener(this);
		frame.st_view.b1.addActionListener(this);
		frame.st_view.b1.setActionCommand("Easy");
		frame.st_view.b2.addActionListener(this);
		frame.st_view.b2.setActionCommand("Normal");
		frame.st_view.b3.addActionListener(this);
		frame.st_view.b3.setActionCommand("Hard");
		frame.st_view.b4.addActionListener(this);
		frame.st_view.b4.setActionCommand("Lunatic");
		frame.st_view.b5.addActionListener(this);
		frame.st_view.b5.setActionCommand("Gacha");
		frame.st_view.b6.addActionListener(this);
		frame.st_view.b6.setActionCommand("rireki");
	}

	// キーリスナーを手動でつける
	public void add() {
		frame.addKeyListener(this);
	}

	// キーリスナーを取り外す
	public void remove() {
		frame.removeKeyListener(this);
	}

	// ボタンの操作
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Easy")) {
			frame.start_select(0);
			return;
		}
		if (cmd.equals("Normal")) {
			frame.start_select(1);
			return;
		}
		if (cmd.equals("Hard")) {
			frame.start_select(2);
			return;
		}
		if (cmd.equals("Lunatic")) {
			frame.start_select(3);
			return;
		}
		if (cmd.equals("Gacha")) {
			frame.start_gacha();
			return;
		}
		if (cmd.equals("rireki")) {
			frame.start_history();
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