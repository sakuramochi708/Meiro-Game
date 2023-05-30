import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import java.util.*;
import java.awt.*;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;

/* View : リセット画面（警告画面） */
class ResetPanel extends JPanel {
	JButton b1, b2;
	JPanel buttonpanel;
	JLabel resetlabel;
	Dimension screenSize;
	Font font;

	public ResetPanel() {
		this.setSize(1000, 1000);
		this.setLayout(new BorderLayout());
		this.setBackground(Color.BLACK);
		resetlabel = new JLabel("本当にリセットしますか");
		resetlabel.setForeground(Color.RED);
		resetlabel.setHorizontalAlignment(JLabel.CENTER);
		resetlabel.setVerticalAlignment(JLabel.CENTER);
		buttonpanel = new JPanel();
		buttonpanel.setLayout(new GridLayout(1, 2));
		buttonpanel.setBackground(Color.BLACK);

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
		} else {
			this.setPreferredSize(new Dimension(1000, 950));
		}

		// button
		b1 = new JButton("はい");
		b1.setOpaque(false);
		b2 = new JButton("いいえ");
		b2.setOpaque(false);
		buttonpanel.add(b1);
		buttonpanel.add(b2);

		resetlabel.setFont(font);

		this.add(resetlabel, BorderLayout.CENTER);
		this.add(buttonpanel, BorderLayout.SOUTH);
	}
}