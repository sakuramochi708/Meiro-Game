import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

/* View : スタート画面 */
class StartPanel extends JPanel {
    JButton b1, b2, b3, b4, b5, b6;
    JLabel label;
    Image img;
    Font font;

    public StartPanel() {
        JPanel p = new JPanel();
        JPanel kari = new JPanel();
        JPanel upperpanel = new JPanel();
        upperpanel.setPreferredSize(new Dimension(1000, 500));
        upperpanel.setOpaque(false);
        this.setLayout(new GridLayout(2, 1));
        kari.setLayout(new GridLayout(1, 2));
        this.setSize(1000, 1000);
        ImagePanel imagepanel = new ImagePanel();
        p.setLayout(new GridLayout(5, 1));
        b1 = new JButton("初級");
        b2 = new JButton("中級");
        b3 = new JButton("上級");
        b4 = new JButton("地獄級");
        b5 = new JButton("ガチャ");
        b6 = new JButton("履歴");

        p.add(b1);
        p.add(b2);
        p.add(b3);
        p.add(b4);
        kari.add(b6);
        kari.add(b5);
        p.add(kari);

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("data/PixelMplus12-Regular.ttf"));
            font = font.deriveFont(150f);
        } catch (FontFormatException e) {
            System.out.println("形式がフォントではありません。");
        } catch (IOException e) {
            System.out.println("入出力エラーでフォントを読み込むことができませんでした。");
        }

        label = new JLabel("迷路ゲーム", JLabel.CENTER);
        label.setFont(font);
        label.setForeground(Color.YELLOW);
        label.setOpaque(false);
        label.setPreferredSize(new Dimension(1000, 500));
        upperpanel.add(label);
        imagepanel.add(upperpanel);
        this.add(imagepanel);
        this.add(p, BorderLayout.CENTER);

    }

    class ImagePanel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image img = Toolkit.getDefaultToolkit().getImage("data/eath2.jpg");
            g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    public JPanel getStartPanel() {
        return this;
    }
}