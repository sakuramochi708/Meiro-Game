import java.awt.*;
import java.awt.event.*;

import javax.security.auth.Subject;
import javax.swing.*;
import java.util.*;
import java.io.*;

/* View : ゲーム画面（ＵＩ部） */
class ItemPanel extends JPanel implements Observer {
	JLabel label1, label2, star_time_label, book_time_label, trap_label;
	Image img;
	JPanel mainpanel1, upperpanel1, mainpanel2, subpanel, item_time_panel, trap_panel;
	Font font;
	String str;
	long min, sec;
	protected Character chara;
	public ImagePanel imagepanel1, imagepanel2, imagepanel3, imagepanel4, coinpanel;
	protected AllItem allitem;
	protected Count_time time;
	private long timer;
	private Dimension screenSize;

	public ItemPanel(AllItem allitem, Character ch, Count_time time) {
		this.allitem = allitem;
		this.chara = ch;
		this.time = time;
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		if (screenSize.getHeight() < 1000) {
			this.setPreferredSize(new Dimension(1000, (int) screenSize.getHeight() / 100 * 92));
		} else {
			this.setPreferredSize(new Dimension(1000, 950));
		}
		this.setLayout(new GridLayout(3, 1));
		this.setOpaque(false);
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("data/PixelMplus12-Regular.ttf"));
			font = font.deriveFont(100f);
		} catch (FontFormatException e) {
			System.out.println("形式がフォントではありません。");
		} catch (IOException e) {
			System.out.println("入出力エラーでフォントを読み込むことができませんでした。");
		}

		// grid1個目
		mainpanel1 = new JPanel();
		mainpanel1.setPreferredSize(new Dimension(1000, 100));
		upperpanel1 = new JPanel();
		upperpanel1.setOpaque(false);
		upperpanel1.setPreferredSize(new Dimension(1000, 100));

		timer = time.getTime();

		label1 = new JLabel();
		set_time(label1, time);
		label1.setForeground(Color.RED);
		label1.setFont(font);
		label1.setOpaque(false);
		upperpanel1.add(label1);

		// タイマーを中央に寄せる
		mainpanel1.setLayout(new GridLayout(1, 3));

		trap_panel = new JPanel();
		trap_label = new JLabel("状態異常", JLabel.CENTER);
		font = font.deriveFont(80f);
		trap_label.setFont(font);
		font = font.deriveFont(100f);
		trap_label.setOpaque(false);
		trap_panel.setOpaque(false);
		trap_panel.add(trap_label);

		mainpanel1.add(trap_panel);
		mainpanel1.add(upperpanel1);
		coinpanel = new ImagePanel("data/coin.png", chara);
		coinpanel.setOpaque(false);
		mainpanel1.add(coinpanel);
		mainpanel1.setOpaque(false);
		this.add(mainpanel1);

		// grid2個目
		mainpanel1 = new JPanel();
		mainpanel1.setPreferredSize(new Dimension(1000, 280));
		label2 = new JLabel("<html><span style='font-size:80pt;color:yellow;'>ミラーorランダム発動中</span></html>",
				JLabel.CENTER);
		label2.setPreferredSize(new Dimension(1000, 280));
		label2.setOpaque(false);
		mainpanel1.setOpaque(false);
		this.add(mainpanel1);

		// grid3個目
		mainpanel2 = new JPanel();
		mainpanel2.setLayout(new BoxLayout(mainpanel2, BoxLayout.LINE_AXIS));

		imagepanel1 = new ImagePanel("data/star.png", chara);
		imagepanel1.setOpaque(false);

		mainpanel2.add(imagepanel1);
		imagepanel2 = new ImagePanel("data/sword.png", chara);
		imagepanel2.setOpaque(false);
		mainpanel2.add(imagepanel2);

		imagepanel3 = new ImagePanel("data/hammer.png", chara);
		imagepanel3.setOpaque(false);

		mainpanel2.add(imagepanel3);
		imagepanel4 = new ImagePanel("data/map.png", chara);
		imagepanel4.setOpaque(false);
		mainpanel2.add(imagepanel4);
		mainpanel2.setOpaque(false);

		// アイテムを中央に寄せる
		mainpanel1 = new JPanel();
		mainpanel1.setLayout(new GridLayout(1, 3));
		item_time_panel = new JPanel();
		item_time_panel.setLayout(new GridLayout(4, 1));
		item_time_panel.setOpaque(false);
		star_time_label = new JLabel();
		book_time_label = new JLabel();
		set_star_time(star_time_label, ch);
		set_book_time(book_time_label, ch);
		star_time_label.setForeground(Color.RED);
		star_time_label.setOpaque(false);
		star_time_label.setPreferredSize(new Dimension(300, 200));
		star_time_label.setVerticalAlignment(JLabel.BOTTOM);
		star_time_label.setHorizontalAlignment(JLabel.CENTER);

		book_time_label.setForeground(Color.RED);
		book_time_label.setOpaque(false);
		book_time_label.setPreferredSize(new Dimension(300, 200));
		book_time_label.setVerticalAlignment(JLabel.BOTTOM);
		book_time_label.setHorizontalAlignment(JLabel.CENTER);

		subpanel = new JPanel();
		subpanel.setOpaque(false);
		item_time_panel.add(subpanel);

		subpanel = new JPanel();
		subpanel.setOpaque(false);
		item_time_panel.add(subpanel);

		item_time_panel.add(star_time_label);
		item_time_panel.add(book_time_label);
		mainpanel1.add(item_time_panel);
		mainpanel1.add(mainpanel2);
		subpanel = new JPanel();
		subpanel.setOpaque(false);
		mainpanel1.add(subpanel);
		mainpanel1.setOpaque(false);

		this.add(mainpanel1);
		this.setOpaque(false);
	}

	// 時間表記を定める
	public void set_time(JLabel label, Count_time time) {
		timer = time.getTime();
		min = (timer / 600);
		sec = (timer / 10) % 60;
		str = String.format("%02d:%02d", min, sec);
		label.setText(str);
		label.setForeground(Color.RED);
	}

	// スターの残り時間についての表示
	public void set_star_time(JLabel label, Character ch) {
		sec = ch.getStarTime();
		str = String.format("スター終了まで%d", sec);
		label.setText(str);
		font = font.deriveFont(35f);
		label.setFont(font);
		font = font.deriveFont(100f);
		if (sec == 0) {
			label.setForeground(Color.BLACK);
		} else {
			label.setForeground(Color.RED);
		}
	}

	// マップ残り時間についての表示
	public void set_book_time(JLabel label, Character ch) {
		sec = ch.getBookTime();
		str = String.format("マップ終了まで%d", sec);
		label.setText(str);
		font = font.deriveFont(35f);
		label.setFont(font);
		font = font.deriveFont(100f);
		label.setForeground(Color.RED);
		if (sec == 0 && ch.getInvincible() == false) {
			label.setForeground(Color.BLACK);
		} else if (sec == 0 && ch.getInvincible() == true) {
			label.setForeground(Color.CYAN);
		} else {
			label.setForeground(Color.RED);
		}
	}

	// 状態異常についての表示
	public void set_trap_condition(JLabel label, Character ch) {
		if (ch.get_mirror_now() == true) {
			label.setForeground(Color.RED);
		} else if (ch.get_mirror_now() == false && ch.getInvincible() == true) {
			label.setForeground(Color.CYAN);
		} else {
			label.setForeground(Color.BLACK);
		}
	}

	public void update(Observable o, Object arg) {
		repaint();
	}

	class ImagePanel extends JPanel {
		private String img_name, itemcount;
		private int width, height, size = 0, item_count = 0;
		private Font font;
		private Character chara;

		public ImagePanel(String img_name, Character ch) {
			this.img_name = img_name;
			chara = ch;
			try {
				font = Font.createFont(Font.TRUETYPE_FONT, new File("data/PixelMplus12-Regular.ttf"));
				font = font.deriveFont(30f);
				font = font.deriveFont(Font.BOLD);
			} catch (FontFormatException e) {
				System.out.println("形式がフォントではありません。");
			} catch (IOException e) {
				System.out.println("入出力エラーでフォントを読み込むことができませんでした。");
			}
		}

		public void paintComponent(Graphics g) {
			set_time(label1, time);
			set_star_time(star_time_label, chara);
			set_book_time(book_time_label, chara);
			set_trap_condition(trap_label, chara);
			super.paintComponent(g);
			Image img = Toolkit.getDefaultToolkit().getImage(img_name);
			width = this.getSize().width;
			this.height = getSize().height;
			if (width < height) {
				size = width;
			} else {
				size = height;
			} // アイテムを綺麗に表示するために短い方の長さに揃える
				// 現在のアイテム数を取得
			if (img_name == "data/star.png") {
				item_count = chara.getStarCount();
				if (chara.getInvincible() == true) {
					g.setColor(Color.BLUE);
					g.fillRect(0, height / 3 * 2, size, size);
				}
			} else if (img_name == "data/sword.png") {
				item_count = chara.getSwordCount();
			} else if (img_name == "data/hammer.png") {
				item_count = chara.getHammerCount();
			} else if (img_name == "data/map.png") {
				item_count = chara.getBookCount();
			} else if (img_name == "data/coin.png") {
				item_count = chara.getCoinCount();
			}
			if (img_name == "data/coin.png") {
				Graphics2D g2 = (Graphics2D) g;
				BasicStroke bs = new BasicStroke(5);
				g2.setStroke(bs);
				g.drawImage(img, size / 3, 10, size / 2, size / 2, this);
				g.setColor(Color.RED);
				g2.drawRect(size / 3, 10, size / 2 + 1, size / 2 + 1);
				itemcount = Integer.toString(item_count);
				g.setFont(font);
				g.drawString(itemcount, size / 4 * 3, size / 2);
			} else {
				Graphics2D g2 = (Graphics2D) g;
				BasicStroke bs = new BasicStroke(3);
				g2.setStroke(bs);
				g.drawImage(img, 0, height / 3 * 2, size, size, this);
				g.setColor(Color.RED);
				g2.drawRect(0, height / 3 * 2, size - 1, size - 1);
				itemcount = Integer.toString(item_count);
				g.setFont(font);
				if (item_count >= 10 && screenSize.getHeight() >= 1000) {
					g.drawString(itemcount, width - 35, height - 30);
				} else if (screenSize.getHeight() >= 1000) {
					g.drawString(itemcount, width - 20, height - 30);
				} else if (item_count >= 10) {
					g.drawString(itemcount, width - 35, height - 20);
				} else {
					g.drawString(itemcount, width - 20, height - 20);
				}
			}
		}
	}
}
