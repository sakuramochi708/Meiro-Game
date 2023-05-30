import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/* Model : プレイヤーデータの構造体 */
class Mana_Item {
	public int coin;
	public int sword;
	public int hammer;
	public int star;
	public int book;
	public int hazure;
	public int do_gacha;
	public long rec_easy;
	public long rec_normal;
	public long rec_hard;
	public long rec_lunatic;

	// コンストラクタ
	public Mana_Item() {
		;
	}

	// ゼロ初期化（最初に必ず行う！）
	public void zero_Mana_Item(Mana_Item mi) {
		mi.coin = 0;
		mi.sword = 0;
		mi.hammer = 0;
		mi.star = 0;
		mi.book = 0;
		mi.hazure = 0;
		mi.do_gacha = 0;
		mi.rec_easy = Long.MAX_VALUE;
		mi.rec_normal = Long.MAX_VALUE;
		mi.rec_hard = Long.MAX_VALUE;
		mi.rec_lunatic = Long.MAX_VALUE;
	}
}

/* Model : ファイル読み書き */
class Mana_File {
	// 初期生成
	public Mana_File(Mana_Item mi) {
		new Check_Data(); // FILEが存在するか
		getDataItem(mi);
	}

	// データにある各アイテムの数を読み込む
	public Mana_Item getDataItem(Mana_Item mi) {
		new Read_Data(mi);
		return mi;
	}

	// アイテム数を追加して書き込む
	public void addData(Mana_Item mi, Mana_Item add_mi) {
		mi.coin += add_mi.coin;
		mi.sword += add_mi.sword;
		mi.hammer += add_mi.hammer;
		mi.star += add_mi.star;
		mi.book += add_mi.book;
		mi.hazure += add_mi.hazure;
		mi.do_gacha += add_mi.do_gacha;

		new Write_Data(mi);
		return;
	}

	// アイテム数をへらす
	public void subData(Mana_Item mi, Mana_Item add_mi) {
		mi.coin -= add_mi.coin;
		mi.sword -= add_mi.sword;
		mi.hammer -= add_mi.hammer;
		mi.star -= add_mi.star;
		mi.book -= add_mi.book;
		mi.hazure -= add_mi.hazure;
		mi.do_gacha -= add_mi.do_gacha;

		new Write_Data(mi);
		return;
	}

	// 新記録を書き込む
	public void writeNewRecord(Mana_Item mi, long time, int mode) {
		if (mode == 0) {
			mi.rec_easy = time;
		} else if (mode == 1) {
			mi.rec_normal = time;
		} else if (mode == 2) {
			mi.rec_hard = time;
		} else {
			mi.rec_lunatic = time;
		}
		new Write_Data(mi);
		return;
	}

	// リセットする。
	public void resetData(Mana_Item mi) {
		new Write_Data();
		mi.zero_Mana_Item(mi);
	}

	// データを書き込む
	public void write_data(Mana_Item mi) {
		new Write_Data(mi);
	}
}

/* Model : ファイルがあるかどうか確認する（なかったら新規生成） */
class Check_Data {
	File file;

	public Check_Data() {
		file = new File("save/FILE");
		if (file.exists()) {
			return;
		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println(e);
			}
			new Write_Data();
		}
	}
}

/* Model : データ読み込み */
class Read_Data {
	BufferedInputStream bi = null;
	DataInputStream dis = null;

	public Read_Data(Mana_Item mi) {
		try {
			// インスタンス生成
			bi = new BufferedInputStream(new FileInputStream("save/FILE"));
			dis = new DataInputStream(bi);

			mi.coin = dis.readInt();
			mi.sword = dis.readInt();
			mi.hammer = dis.readInt();
			mi.star = dis.readInt();
			mi.book = dis.readInt();
			mi.hazure = dis.readInt();
			mi.do_gacha = dis.readInt();
			mi.rec_easy = dis.readLong();
			mi.rec_normal = dis.readLong();
			mi.rec_hard = dis.readLong();
			mi.rec_lunatic = dis.readLong();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

/* Model : データ書き込み */
class Write_Data {

	FileOutputStream fo = null;
	BufferedOutputStream bo = null;
	DataOutputStream dos = null;

	public Write_Data() {
		reset_Data();
	}

	public Write_Data(Mana_Item mi) {
		try {
			// インスタンス生成
			bo = new BufferedOutputStream(new FileOutputStream("save/FILE"));
			dos = new DataOutputStream(bo);

			// coin書き込み
			dos.writeInt(mi.coin);
			dos.writeInt(mi.sword);
			dos.writeInt(mi.hammer);
			dos.writeInt(mi.star);
			dos.writeInt(mi.book);
			dos.writeInt(mi.hazure);
			dos.writeInt(mi.do_gacha);
			dos.writeLong(mi.rec_easy);
			dos.writeLong(mi.rec_normal);
			dos.writeLong(mi.rec_hard);
			dos.writeLong(mi.rec_lunatic);

			// ストリームをフラッシュ
			dos.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 出力ストリームを閉じる
				dos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// データリセット
	public void reset_Data() {
		try {
			// インスタンス生成
			bo = new BufferedOutputStream(new FileOutputStream("save/FILE"));
			dos = new DataOutputStream(bo);

			// 書き込み
			dos.writeInt(0);
			dos.writeInt(0);
			dos.writeInt(0);
			dos.writeInt(0);
			dos.writeInt(0);
			dos.writeInt(0);
			dos.writeInt(0);
			dos.writeLong(Long.MAX_VALUE);
			dos.writeLong(Long.MAX_VALUE);
			dos.writeLong(Long.MAX_VALUE);
			dos.writeLong(Long.MAX_VALUE);

			// ストリームをフラッシュ
			dos.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 出力ストリームを閉じる
				dos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}