import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/* Model : タイマーを管理 */
class Count_time extends Observable implements ActionListener {
	private javax.swing.Timer time;
	private long clock;

	// コンストラクタ
	public Count_time() {
		time = new javax.swing.Timer(100, this);
		time.setActionCommand("clock");
		clock = 0;
		time.start();
	}

	// 0.1秒ごとに呼び出し
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("clock")) {
			clock++;
			setChanged();
			notifyObservers();
		}
	}

	// 現在経過時間を返す
	public long getTime() {
		return clock;
	}

	// 計測をストップする。
	public void stopTimer() {
		time.stop();
	}

}