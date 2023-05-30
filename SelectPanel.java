import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.border.LineBorder;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;

/* View : セレクト画面 */
class SelectPanel extends JPanel implements Observer {
	JPanel mainpanel, panel, subpanel;
	JPanel have_swordpanel, have_hammerpanel, have_starpanel, have_mappanel;
	JPanel max_swordpanel, max_hammerpanel, max_starpanel, max_mappanel;
	Font font;
	ImagePanel swordpanel, hammerpanel, starpanel, mappanel, backgroundpanel;
	private Dimension screenSize;
	JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b10;
	JLabel label, have_swordlabel, have_hammerlabel, have_starlabel, have_maplabel;
	JLabel max_swordlabel, max_hammerlabel, max_starlabel, max_maplabel;
	String str;
	Select_Item select;

	public SelectPanel(Select_Item select) {
		this.select = select;
		mainpanel = new JPanel();
		backgroundpanel = new ImagePanel("data/eath2.jpg");
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		if (screenSize.getHeight() < 1000) {
			mainpanel.setPreferredSize(new Dimension(1000, (int) screenSize.getHeight() / 100 * 95));
			this.setPreferredSize(new Dimension(1000, (int) screenSize.getHeight()));
			backgroundpanel.setPreferredSize(new Dimension(1000, (int) screenSize.getHeight()));
		} else {
			this.setPreferredSize(new Dimension(1000, 950));
			mainpanel.setPreferredSize(new Dimension(1000, 950));
			backgroundpanel.setPreferredSize(new Dimension(1000, 1000));
		}
		mainpanel.setLayout(new GridLayout(6, 5));
		mainpanel.setOpaque(false);

		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("data/PixelMplus12-Regular.ttf"));
			font = font.deriveFont(100f);
			font = font.deriveFont(Font.BOLD);
		} catch (FontFormatException e) {
			System.out.println("形式がフォントではありません。");
		} catch (IOException e) {
			System.out.println("入出力エラーでフォントを読み込むことができませんでした。");
		}

		subpanel = new JPanel();
		subpanel.setOpaque(false);
		mainpanel.add(subpanel);
		swordpanel = new ImagePanel("data/sword.png");
		hammerpanel = new ImagePanel("data/hammer.png");
		starpanel = new ImagePanel("data/star.png");
		mappanel = new ImagePanel("data/map.png");
		swordpanel.setOpaque(false);
		hammerpanel.setOpaque(false);
		starpanel.setOpaque(false);
		mappanel.setOpaque(false);
		mainpanel.add(swordpanel);
		mainpanel.add(hammerpanel);
		mainpanel.add(starpanel);
		mainpanel.add(mappanel);

		b1 = new JButton("+");
		b1.setFont(font);
		b1.setForeground(Color.RED);
		b2 = new JButton("-");
		b2.setFont(font);
		b2.setForeground(Color.BLUE);
		b3 = new JButton("+");
		b3.setFont(font);
		b3.setForeground(Color.RED);
		b4 = new JButton("-");
		b4.setFont(font);
		b4.setForeground(Color.BLUE);
		b5 = new JButton("+");
		b5.setFont(font);
		b5.setForeground(Color.RED);
		b6 = new JButton("-");
		b6.setFont(font);
		b6.setForeground(Color.BLUE);
		b7 = new JButton("+");
		b7.setFont(font);
		b7.setForeground(Color.RED);
		b8 = new JButton("-");
		b8.setFont(font);
		b8.setForeground(Color.BLUE);
		font = font.deriveFont(70f);
		b9 = new JButton("決定");
		b9.setForeground(Color.RED);
		b9.setBorder(new LineBorder(Color.YELLOW, 5, false));
		b9.setFont(font);
		b10 = new JButton("もどる");
		b10.setForeground(Color.RED);
		font = font.deriveFont(45f);
		b10.setFont(font);
		b10.setBorder(new LineBorder(Color.YELLOW, 5, false));
		font = font.deriveFont(100f);

		subpanel = new JPanel();
		subpanel.setOpaque(false);
		mainpanel.add(subpanel);
		mainpanel.add(b1);
		mainpanel.add(b3);
		mainpanel.add(b5);
		mainpanel.add(b7);

		label = new JLabel("使用数");
		font = font.deriveFont(50f);
		label.setFont(font);
		label.setForeground(Color.YELLOW);
		font = font.deriveFont(70f);
		panel = new JPanel();
		panel.setOpaque(false);
		panel.add(label);
		mainpanel.add(panel);

		// ソードの選択数
		have_swordlabel = new JLabel();
		have_swordlabel.setFont(font);
		set_haveitem(have_swordlabel, select, "sword");
		have_swordpanel = new JPanel();
		have_swordpanel.setOpaque(false);
		have_swordpanel.add(have_swordlabel);
		mainpanel.add(have_swordpanel);
		// ハンマーの選択数
		have_hammerlabel = new JLabel();
		have_hammerlabel.setFont(font);
		set_haveitem(have_hammerlabel, select, "hammer");
		have_hammerpanel = new JPanel();
		have_hammerpanel.setOpaque(false);
		have_hammerpanel.add(have_hammerlabel);
		mainpanel.add(have_hammerpanel);
		// スターの選択数
		have_starlabel = new JLabel();
		have_starlabel.setFont(font);
		set_haveitem(have_starlabel, select, "star");
		have_starpanel = new JPanel();
		have_starpanel.setOpaque(false);
		have_starpanel.add(have_starlabel);
		mainpanel.add(have_starpanel);
		// マップの選択数
		have_maplabel = new JLabel();
		have_maplabel.setFont(font);
		set_haveitem(have_maplabel, select, "map");
		have_mappanel = new JPanel();
		have_mappanel.setOpaque(false);
		have_mappanel.add(have_maplabel);
		mainpanel.add(have_mappanel);

		subpanel = new JPanel();
		subpanel.setOpaque(false);
		mainpanel.add(subpanel);
		mainpanel.add(b2);
		mainpanel.add(b4);
		mainpanel.add(b6);
		mainpanel.add(b8);

		label = new JLabel("所持数");
		font = font.deriveFont(50f);
		label.setFont(font);
		label.setForeground(Color.YELLOW);
		font = font.deriveFont(70f);
		panel = new JPanel();
		panel.setOpaque(false);
		panel.add(label);
		mainpanel.add(panel);

		// ソード所持数
		max_swordlabel = new JLabel();
		max_swordlabel.setFont(font);
		str = Integer.toString(select.gethaveSword());
		max_swordlabel.setText(str);
		max_swordlabel.setForeground(Color.YELLOW);
		max_swordpanel = new JPanel();
		max_swordpanel.setOpaque(false);
		max_swordpanel.add(max_swordlabel);
		mainpanel.add(max_swordpanel);
		// ハンマー所持数
		max_hammerlabel = new JLabel();
		max_hammerlabel.setFont(font);
		str = Integer.toString(select.gethaveHammer());
		max_hammerlabel.setText(str);
		max_hammerlabel.setForeground(Color.YELLOW);
		max_hammerpanel = new JPanel();
		max_hammerpanel.setOpaque(false);
		max_hammerpanel.add(max_hammerlabel);
		mainpanel.add(max_hammerpanel);
		// スター所持数
		max_starlabel = new JLabel();
		max_starlabel.setFont(font);
		str = Integer.toString(select.gethaveStar());
		max_starlabel.setText(str);
		max_starlabel.setForeground(Color.YELLOW);
		max_starpanel = new JPanel();
		max_starpanel.setOpaque(false);
		max_starpanel.add(max_starlabel);
		mainpanel.add(max_starpanel);
		// マップ所持数
		max_maplabel = new JLabel();
		max_maplabel.setFont(font);
		str = Integer.toString(select.gethaveBook());
		max_maplabel.setText(str);
		max_maplabel.setForeground(Color.YELLOW);
		max_mappanel = new JPanel();
		max_mappanel.setOpaque(false);
		max_mappanel.add(max_maplabel);
		mainpanel.add(max_mappanel);

		mainpanel.add(b10);
		subpanel = new JPanel();
		subpanel.setOpaque(false);
		mainpanel.add(subpanel);

		mainpanel.add(b9, BorderLayout.SOUTH);

		subpanel = new JPanel();
		subpanel.setOpaque(false);
		mainpanel.add(subpanel);

		subpanel = new JPanel();
		subpanel.setOpaque(false);
		mainpanel.add(subpanel);

		backgroundpanel.add(mainpanel);
		this.add(backgroundpanel);
		this.select.addObserver(this);
	}

	// アイテムの選択数を更新する関数
	public void set_haveitem(JLabel label, Select_Item select, String item_name) {
		if (item_name == "sword") {
			str = Integer.toString(select.getUseSword());
		} else if (item_name == "hammer") {
			str = Integer.toString(select.getUseHammer());
		} else if (item_name == "star") {
			str = Integer.toString(select.getUseStar());
		} else if (item_name == "map") {
			str = Integer.toString(select.getUseBook());
		}
		label.setText(str);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setForeground(Color.YELLOW);
	}

	class ImagePanel extends JPanel {
		private String img_name;
		private int width, height, size = 0;

		public ImagePanel(String img_name) {
			this.img_name = img_name;
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Image img = Toolkit.getDefaultToolkit().getImage(img_name);
			if (img_name == "data/eath2.jpg") {
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			} else {
				width = this.getSize().width;
				height = this.getSize().height;
				if (width < height) {
					size = width;
				} else {
					size = height;
				} // アイテムを綺麗に表示するために短い方の長さに揃える
				g.drawImage(img, 0, 0, size, size, this);
			}
		}
	}

	public void update(Observable o, Object arg) {
		repaint();
		set_haveitem(have_swordlabel, select, "sword");
		set_haveitem(have_hammerlabel, select, "hammer");
		set_haveitem(have_starlabel, select, "star");
		set_haveitem(have_maplabel, select, "map");
	}
}