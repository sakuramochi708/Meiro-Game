import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/* Model : 新記録かどうかを判断するクラス */
class JudgeNewRecord {

	// コンストラクタ
	public JudgeNewRecord() {
		;
	}

	// ジャッジする
	public boolean judge(Mana_File mana_file, Mana_Item mi, long time, int mode) {
		if (mode == 0) {
			if (time < mi.rec_easy) {
				mana_file.writeNewRecord(mi, time, mode);
				return true;
			}
		} else if (mode == 1) {
			if (time < mi.rec_normal) {
				mana_file.writeNewRecord(mi, time, mode);
				return true;
			}
		} else if (mode == 2) {
			if (time < mi.rec_hard) {
				mana_file.writeNewRecord(mi, time, mode);
				return true;
			}
		} else if (mode == 3) {
			if (time < mi.rec_lunatic) {
				mana_file.writeNewRecord(mi, time, mode);
				return true;
			}
		}
		return false;
	}
}