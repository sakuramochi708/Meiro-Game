import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/* Controller : セレクト画面に対する */
class SelectCont implements ActionListener, KeyListener {
	public Game frame;

	// コンストラクタ
	public SelectCont(Game g) {
		frame = g;
		frame.addKeyListener(this);
		frame.sl_view.b1.addActionListener(this);
		frame.sl_view.b1.setActionCommand("sw+");
		frame.sl_view.b2.addActionListener(this);
		frame.sl_view.b2.setActionCommand("sw-");
		frame.sl_view.b3.addActionListener(this);
		frame.sl_view.b3.setActionCommand("ha+");
		frame.sl_view.b4.addActionListener(this);
		frame.sl_view.b4.setActionCommand("ha-");
		frame.sl_view.b5.addActionListener(this);
		frame.sl_view.b5.setActionCommand("st+");
		frame.sl_view.b6.addActionListener(this);
		frame.sl_view.b6.setActionCommand("st-");
		frame.sl_view.b7.addActionListener(this);
		frame.sl_view.b7.setActionCommand("bo+");
		frame.sl_view.b8.addActionListener(this);
		frame.sl_view.b8.setActionCommand("bo-");
		frame.sl_view.b9.addActionListener(this);
		frame.sl_view.b9.setActionCommand("decide");
		frame.sl_view.b10.addActionListener(this);
		frame.sl_view.b10.setActionCommand("modoru");
	}

	// キーを取り外す
	public void remove() {
		frame.removeKeyListener(this);
	}

	// ボタンの操作
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("sw+")) {
			frame.select.push_plus(1);
			return;
		} else if (cmd.equals("sw-")) {
			frame.select.push_minus(1);
			return;
		} else if (cmd.equals("ha+")) {
			frame.select.push_plus(2);
			return;
		} else if (cmd.equals("ha-")) {
			frame.select.push_minus(2);
			return;
		} else if (cmd.equals("st+")) {
			frame.select.push_plus(3);
			return;
		} else if (cmd.equals("st-")) {
			frame.select.push_minus(3);
			return;
		} else if (cmd.equals("bo+")) {
			frame.select.push_plus(4);
			return;
		} else if (cmd.equals("bo-")) {
			frame.select.push_minus(4);
			return;
		} else if (cmd.equals("decide")) {
			frame.select_maze();
			return;
		} else if (cmd.equals("modoru")) {
			frame.pushreturn();
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