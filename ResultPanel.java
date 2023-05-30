import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.*;

/* View : リザルト画面 */
class ResultPanel extends JPanel {
	JButton b1;
	JLabel label1, label2, coin_label, gamelevel_label, recordlabel;
	long min, sec, milisec;
	String str1;
	Font font;
	int coin, gamelevel;
	ImagePanel imagepanel, coin_panel;
	JPanel mainpanel, subpanel, recordpanel;
	private Dimension screenSize;

	public ResultPanel(long time, int coin, int gamelevel, boolean newrecord) {
		this.setSize(this.getWidth(), this.getHeight());
		this.coin = coin;
		this.gamelevel = gamelevel;
		min = time / 600;
		sec = (time / 10) % 60;
		milisec = time % 10;
		str1 = String.format("%02d:%02d:%01d", min, sec, milisec);

		mainpanel = new JPanel();
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		if (screenSize.getHeight() < 1000) {
			this.setPreferredSize(new Dimension(1000, (int) screenSize.getHeight() / 100 * 92));
			mainpanel.setPreferredSize(new Dimension(1000, (int) screenSize.getHeight() / 100 * 92));
		} else {
			this.setPreferredSize(new Dimension(1000, 950));
			mainpanel.setPreferredSize(new Dimension(1000, 1000));
		}
		this.setLayout(new BorderLayout());
		imagepanel = new ImagePanel("background");

		mainpanel.setLayout(new GridLayout(3, 1));
		mainpanel.setOpaque(false);
		font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("data/PixelMplus12-Regular.ttf"));
			font = font.deriveFont(120f);
		} catch (FontFormatException e) {
			System.out.println("形式がフォントではありません。");
		} catch (IOException e) {
			System.out.println("入出力エラーでフォントを読み込むことができませんでした。");
		}

		label1 = new JLabel("CONGRATULATIONS", JLabel.CENTER);
		label1.setFont(font);
		label1.setForeground(Color.RED);
		mainpanel.add(label1, BorderLayout.CENTER);
		label1.setOpaque(false);
		mainpanel.add(label1, BorderLayout.CENTER);
		label2 = new JLabel();
		label2.setText(str1);
		font = font.deriveFont(150f);
		label2.setFont(font);
		label2.setForeground(Color.RED);
		label2.setHorizontalAlignment(JLabel.CENTER);
		font = font.deriveFont(150f);

		subpanel = new JPanel();
		subpanel.setLayout(new GridLayout(1, 2));
		subpanel.setOpaque(false);
		gamelevel_label = new JLabel();
		recordpanel = new JPanel();
		recordpanel.setOpaque(false);
		recordpanel.setLayout(new GridLayout(2, 1));
		recordlabel = new JLabel();
		if (newrecord == true) {
			font = font.deriveFont(Font.BOLD, 75f);
			recordlabel.setText("New Record!!");
			recordlabel.setFont(font);
			font = font.deriveFont(Font.PLAIN, 150f);
			recordlabel.setForeground(Color.BLUE);
			recordlabel.setHorizontalAlignment(JLabel.CENTER);
		}
		if (gamelevel == 0) {
			gamelevel_label.setText("初級");
		} else if (gamelevel == 1) {
			gamelevel_label.setText("中級");
		} else if (gamelevel == 2) {
			gamelevel_label.setText("上級");
		} else {
			gamelevel_label.setText("地獄級");
		}

		gamelevel_label.setFont(font);
		gamelevel_label.setForeground(Color.RED);
		gamelevel_label.setHorizontalAlignment(JLabel.CENTER);
		coin_panel = new ImagePanel("data/coin.png");
		coin_panel.setOpaque(false);

		recordpanel.add(gamelevel_label);
		recordpanel.add(recordlabel);
		subpanel.add(recordpanel);
		subpanel.add(coin_panel);
		mainpanel.add(subpanel);

		mainpanel.add(label2, BorderLayout.CENTER);
		label2.setOpaque(false);
		b1 = new JButton("スタート画面へ");
		b1.setOpaque(true);
		b1.setBackground(Color.GREEN);
		imagepanel.add(mainpanel);
		this.add(imagepanel);
		this.add(b1, BorderLayout.SOUTH);
	}

	class ImagePanel extends JPanel {
		private String img_name, itemcount;
		private Font font;
		private int width, height, size = 0;

		public ImagePanel(String img_name) {
			this.img_name = img_name;
			try {
				font = Font.createFont(Font.TRUETYPE_FONT, new File("data/PixelMplus12-Regular.ttf"));
				font = font.deriveFont(80f);
				font = font.deriveFont(Font.BOLD);
			} catch (FontFormatException e) {
				System.out.println("形式がフォントではありません。");
			} catch (IOException e) {
				System.out.println("入出力エラーでフォントを読み込むことができませんでした。");
			}
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (img_name == "background") {
				Image img = Toolkit.getDefaultToolkit().getImage("data/pipo-battlebg002.jpg");
				g.drawImage(img, 0, 0, this.getWidth(), 1000, this);
			} else if (img_name == "data/coin.png") {
				Image img = Toolkit.getDefaultToolkit().getImage(img_name);
				width = this.getSize().width;
				height = this.getSize().height;
				if (width < height) {
					size = width;
				} else {
					size = height;
				} // アイテムを綺麗に表示するために短い方の長さに揃える
				Graphics2D g2 = (Graphics2D) g;
				BasicStroke bs = new BasicStroke(5);
				g2.setStroke(bs);
				g.drawImage(img, size / 3, 5, size, size, this);
				g.setColor(Color.RED);
				g2.drawRect(size / 3, 0, size - 15, size - 15);
				itemcount = Integer.toString(coin);
				g.setFont(font);
				if (coin >= 10) {
					g.drawString(itemcount, size, size - 20);
				} else {
					g.drawString(itemcount, size / 15 * 17, size - 20);
				}
			}
		}
	}

	public JPanel getPanel() {
		return this;
	}
}