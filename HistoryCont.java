import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/* Controller : 履歴画面＋警告画面に対して */
class HistoryCont implements ActionListener, KeyListener {
	public Game frame;

	// コンストラクタ
	public HistoryCont(Game f) {
		frame = f;
		frame.addKeyListener(this);
		frame.ht_view.b1.addActionListener(this);
		frame.ht_view.b1.setActionCommand("return");
		frame.ht_view.b2.addActionListener(this);
		frame.ht_view.b2.setActionCommand("datareset");
		frame.ht_view2.b1.addActionListener(this);
		frame.ht_view2.b1.setActionCommand("yes");
		frame.ht_view2.b2.addActionListener(this);
		frame.ht_view2.b2.setActionCommand("no");
	}

	// キーリスナーを手動で加える
	public void add() {
		frame.addKeyListener(this);
	}

	// キーリスナーを外す
	public void remove() {
		frame.removeKeyListener(this);
	}

	// ボタンが押されたときの処理
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("return")) {
			frame.history_start();
			return;
		} else if (cmd.equals("datareset")) {
			frame.history_notion();
			return;
		} else if (cmd.equals("yes")) {
			frame.mana_file.resetData(frame.mana_item);
			frame.notion_history_yes();
			return;
		} else if (cmd.equals("no")) {
			frame.notion_history_no();
			return;
		}
	}

	// キーの処理
	public void keyPressed(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {

	}
}
