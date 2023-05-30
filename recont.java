import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/* Controller : リザルト画面に対する */
class ResultCont implements ActionListener, KeyListener {
	public Game frame;

	// コンストラクタ
	public ResultCont(Game g) {
		frame = g;
		frame.addKeyListener(this);
		frame.re_view.b1.addActionListener(this);
		frame.re_view.b1.setActionCommand("Result");
	}

	// キーリスナーの取り外し
	public void remove() {
		frame.removeKeyListener(this);
	}

	// ボタンの操作
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Result")) {
			frame.result_start();
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