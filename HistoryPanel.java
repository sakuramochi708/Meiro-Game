import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import java.util.*;
import java.awt.*;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;

/* View : 履歴画面 */
class HistoryPanel extends JPanel {
	JButton b1, b2;
	JPanel mainpanel, itempanel, timepanel, subpanel, buttonpanel, buttonempty;
	JLabel j_sword, j_hammer, j_star, j_coin, j_book, j_hazure, j_dogacha, j_easy, j_normal, j_hard, j_lunatic, j_empty;
	JLabel item, cleartime;
	Font font;
	Dimension screenSize;
	ImagePanel background;
	Color lightgreen, deeppink;

	public HistoryPanel(History_make his) {
		this.setSize(1000, 1000);

		mainpanel = new JPanel();
		mainpanel.setLayout(new GridLayout(5, 1));
		mainpanel.setOpaque(false);
		itempanel = new JPanel();
		itempanel.setOpaque(false);
		itempanel.setLayout(new GridLayout(2, 2));
		timepanel = new JPanel();
		timepanel.setLayout(new GridLayout(2, 2));
		timepanel.setOpaque(false);
		subpanel = new JPanel();
		subpanel.setOpaque(false);
		subpanel.setLayout(new GridLayout(1, 3));
		background = new ImagePanel();
		lightgreen = new Color(127, 255, 0);
		deeppink = new Color(255, 20, 147);

		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("data/PixelMplus12-Regular.ttf"));
			font = font.deriveFont(50f);
			font = font.deriveFont(Font.BOLD);
		} catch (FontFormatException e) {
			System.out.println("形式がフォントではありません。");
		} catch (IOException e) {
			System.out.println("入出力エラーでフォントを読み込むことができませんでした。");
		}
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		if (screenSize.getHeight() < 1000) {
			this.setPreferredSize(new Dimension(1000, (int) screenSize.getHeight() / 100 * 92));
			mainpanel.setPreferredSize(new Dimension(1000, (int) screenSize.getHeight() / 100 * 92));
		} else {
			this.setPreferredSize(new Dimension(1000, 950));
			mainpanel.setPreferredSize(new Dimension(1000, 1000));
		}

		// button
		b1 = new JButton("戻る");
		b1.setOpaque(false);
		b2 = new JButton("データリセット");
		b2.setOpaque(false);
		buttonpanel = new JPanel();
		buttonpanel.setLayout(new GridLayout(1, 3));
		buttonpanel.add(b1);
		buttonempty = new JPanel();
		buttonempty.setOpaque(false);
		buttonpanel.add(buttonempty);
		buttonpanel.add(b2);
		buttonpanel.setBackground(Color.BLACK);

		// 各履歴の情報
		j_sword = new JLabel("Sword : " + his.getSwordCount(), JLabel.CENTER);
		j_sword.setFont(font);
		j_sword.setForeground(deeppink);
		j_hammer = new JLabel("Hammer : " + his.getHammerCount(), JLabel.CENTER);
		j_hammer.setFont(font);
		j_hammer.setForeground(deeppink);
		j_star = new JLabel("Star : " + his.getStarCount(), JLabel.CENTER);
		j_star.setFont(font);
		j_star.setForeground(deeppink);
		j_book = new JLabel("Book : " + his.getBookCount(), JLabel.CENTER);
		j_book.setFont(font);
		j_book.setForeground(deeppink);
		j_coin = new JLabel("Coin : " + his.getCoinCount(), JLabel.CENTER);
		j_coin.setFont(font);
		j_coin.setForeground(lightgreen);
		j_hazure = new JLabel("はずれ : " + his.getHazureCount(), JLabel.CENTER);
		j_hazure.setFont(font);
		j_hazure.setForeground(lightgreen);
		j_dogacha = new JLabel("ガチャ回数" + his.getDoGachaCount(), JLabel.CENTER);
		j_dogacha.setFont(font);
		j_dogacha.setForeground(lightgreen);
		item = new JLabel("所持アイテム");
		item.setFont(font);
		item.setForeground(Color.CYAN);
		item.setHorizontalAlignment(JLabel.CENTER);
		cleartime = new JLabel("クリアタイム");
		cleartime.setFont(font);
		cleartime.setForeground(Color.CYAN);
		cleartime.setHorizontalAlignment(JLabel.CENTER);

		// time
		if (his.getEasyTime() == -1) {
			j_easy = new JLabel("初級 : --:--:-- ", JLabel.CENTER);
		} else {
			long min = his.getEasyTime() / 600;
			long sec = (his.getEasyTime() / 10) % 60;
			long mil = his.getEasyTime() % 10;
			j_easy = new JLabel("初級 : " + String.format("%02d:%02d:%01d", min, sec, mil), JLabel.CENTER);
		}

		if (his.getNormalTime() == -1) {
			j_normal = new JLabel("中級 : --:--:-- ", JLabel.CENTER);
		} else {
			long min = his.getNormalTime() / 600;
			long sec = (his.getNormalTime() / 10) % 60;
			long mil = his.getNormalTime() % 10;
			j_normal = new JLabel("中級 : " + String.format("%02d:%02d:%01d", min, sec, mil), JLabel.CENTER);
		}

		if (his.getHardTime() == -1) {
			j_hard = new JLabel("上級 : --:--:-- ", JLabel.CENTER);
		} else {
			long min = his.getHardTime() / 600;
			long sec = (his.getHardTime() / 10) % 60;
			long mil = his.getHardTime() % 10;
			j_hard = new JLabel("上級 : " + String.format("%02d:%02d:%01d", min, sec, mil), JLabel.CENTER);
		}

		if (his.getLunaticTime() == -1) {
			j_lunatic = new JLabel("地獄級 : --:--:-- ", JLabel.CENTER);
		} else {
			long min = his.getLunaticTime() / 600;
			long sec = (his.getLunaticTime() / 10) % 60;
			long mil = his.getLunaticTime() % 10;
			j_lunatic = new JLabel("地獄級 : " + String.format("%02d:%02d:%01d", min, sec, mil), JLabel.CENTER);
		}
		j_empty = new JLabel();
		j_easy.setFont(font);
		j_easy.setForeground(Color.MAGENTA);
		j_normal.setFont(font);
		j_normal.setForeground(Color.MAGENTA);
		j_hard.setFont(font);
		j_hard.setForeground(Color.MAGENTA);
		j_lunatic.setFont(font);
		j_lunatic.setForeground(Color.MAGENTA);

		mainpanel.add(item);
		itempanel.add(j_sword);
		itempanel.add(j_hammer);
		itempanel.add(j_star);
		itempanel.add(j_book);
		mainpanel.add(itempanel);
		mainpanel.add(cleartime);
		timepanel.add(j_easy);
		timepanel.add(j_normal);
		timepanel.add(j_hard);
		timepanel.add(j_lunatic);
		mainpanel.add(timepanel);
		subpanel.add(j_coin);
		subpanel.add(j_dogacha);
		subpanel.add(j_hazure);
		mainpanel.add(subpanel);
		background.add(mainpanel);

		this.setLayout(new BorderLayout());
		this.add(background, BorderLayout.CENTER);
		this.add(buttonpanel, BorderLayout.SOUTH);
	}

	public JPanel Panel() {
		return this;
	}

	class ImagePanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Image img = Toolkit.getDefaultToolkit().getImage("data/rireki.jpg");
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}
}