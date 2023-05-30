import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;

/* View : ガチャ画面 */
class GachaPanel extends JPanel implements Observer {
	JButton b1, b2;
	JPanel panel, mainpanel, img_panel;
	Font font;
	ImagePanel background, gacha_img, getitem_img;
	Gacha gacha;
	private Dimension screenSize;

	public GachaPanel(Gacha gacha) {
		this.gacha = gacha;
		this.setLayout(new BorderLayout());
		panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		mainpanel = new JPanel();
		mainpanel.setOpaque(false);
		mainpanel.setLayout(new GridLayout(2, 1));
		img_panel = new JPanel();
		img_panel.setOpaque(false);
		getitem_img = new ImagePanel("getitem", gacha);
		getitem_img.setOpaque(false);
		background = new ImagePanel("data/gacha.jpg", gacha);
		gacha_img = new ImagePanel("data/gacha.png", gacha);

		gacha_img.setOpaque(false);
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		if (screenSize.getHeight() < 1000) {
			this.setPreferredSize(new Dimension(1000, (int) screenSize.getHeight()));
			gacha_img.setPreferredSize(new Dimension(1000, (int) screenSize.getHeight() / 2));
			mainpanel.setPreferredSize(new Dimension(1000, (int) screenSize.getHeight()));
			img_panel.setPreferredSize(
					new Dimension((int) screenSize.getHeight() / 2, (int) screenSize.getHeight() / 2));
		} else {
			this.setPreferredSize(new Dimension(1000, 950));
			gacha_img.setPreferredSize(new Dimension(400, 400));
			mainpanel.setPreferredSize(new Dimension(1000, 950));
			img_panel.setPreferredSize(new Dimension(400, 400));
		}

		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("data/PixelMplus12-Regular.ttf"));
			font = font.deriveFont(100f);
			font = font.deriveFont(Font.BOLD);
		} catch (FontFormatException e) {
			System.out.println("形式がフォントではありません。");
		} catch (IOException e) {
			System.out.println("入出力エラーでフォントを読み込むことができませんでした。");
		}

		b1 = new JButton("ひく");
		b1.setOpaque(true);
		b1.setFont(font);
		b2 = new JButton("もどる");
		b2.setOpaque(true);
		b2.setFont(font);
		panel.add(b1);
		panel.add(b2);

		this.add(panel, BorderLayout.SOUTH);
		mainpanel.add(gacha_img);
		mainpanel.add(getitem_img);
		background.add(mainpanel);
		this.add(background, BorderLayout.CENTER);

		this.gacha.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		repaint();
	}

	class ImagePanel extends JPanel {
		private String img_name, str1, str2;
		private int width, height, size = 0, coin;
		private Gacha gacha;

		public ImagePanel(String img_name, Gacha gacha) {
			this.img_name = img_name;
			this.gacha = gacha;
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			str1 = String.format("残り");
			str2 = String.format("%02d回", gacha.getHaveCoin() / 10);
			Image img = Toolkit.getDefaultToolkit().getImage(img_name);
			if (img_name == "data/gacha.jpg") {
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			} else {
				width = this.getSize().width;
				height = this.getSize().height;
				if (width < height) {
					size = width;
				} else {
					size = height;
				} // アイテムを綺麗に表示するために短い方の長さに揃える
				if (img_name == "getitem") {
					if (gacha.getItem() == 1) {// ソード獲得
						g.setColor(Color.CYAN);
						img = Toolkit.getDefaultToolkit().getImage("data/sword.png");
						g.fillRect(this.getWidth() / 3, 0, size / 3 * 2, size / 3 * 2);
					} else if (gacha.getItem() == 2) {// ハンマー獲得
						g.setColor(Color.YELLOW);
						img = Toolkit.getDefaultToolkit().getImage("data/hammer.png");
						g.fillRect(this.getWidth() / 3, 0, size / 3 * 2, size / 3 * 2);
					} else if (gacha.getItem() == 3) {// スター獲得
						g.setColor(Color.BLUE);
						img = Toolkit.getDefaultToolkit().getImage("data/star.png");
						g.fillRect(this.getWidth() / 3, 0, size / 3 * 2, size / 3 * 2);
					} else if (gacha.getItem() == 4) {// マップ獲得
						g.setColor(Color.RED);
						img = Toolkit.getDefaultToolkit().getImage("data/map.png");
						g.fillRect(this.getWidth() / 3, 0, size / 3 * 2, size / 3 * 2);
					} else if (gacha.getItem() == 5) {// ハズレ
						img = Toolkit.getDefaultToolkit().getImage("data/hazure.png");
					}
					g.drawImage(img, this.getWidth() / 3, 0, size / 3 * 2, size / 3 * 2, this);
					g.setFont(font);
					g.setColor(Color.RED);
					g.drawString(str1, this.getWidth() / 10 * 7, this.getHeight() / 3);
					g.drawString(str2, this.getWidth() / 10 * 7, this.getHeight() / 3 + 110);
				} else {
					g.drawImage(img, this.getWidth() / 4, 0, size, size, this);
				}
			}
		}
	}
}