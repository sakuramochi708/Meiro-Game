import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

/* View : ゲームオーバー画面 */
class GameoverPanel extends JPanel {
    JButton b1;
    JPanel mainpanel;
    JLabel label;
    Font font;

    public GameoverPanel() {
        this.setSize(1000, 1000);
        this.setLayout(new BorderLayout());
        mainpanel = new JPanel();
        ImagePanel imagepanel = new ImagePanel();
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("data/PixelMplus12-Regular.ttf"));
            font = font.deriveFont(200f);
        } catch (FontFormatException e) {
            System.out.println("形式がフォントではありません。");
        } catch (IOException e) {
            System.out.println("入出力エラーでフォントを読み込むことができませんでした。");
        }
        label = new JLabel("GAMEOVER", JLabel.CENTER);
        label.setFont(font);
        label.setForeground(Color.RED);
        label.setOpaque(false);
        label.setPreferredSize(new Dimension(1000, 900));
        imagepanel.add(label);

        b1 = new JButton("スタート画面へ");
        b1.setOpaque(true);
        b1.setBackground(Color.black);

        this.add(imagepanel);
        this.add(b1, BorderLayout.SOUTH);
    }

    class ImagePanel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image img = Toolkit.getDefaultToolkit().getImage("data/gameover.png");
            g.drawImage(img, 0, 0, this.getWidth(), 1000, this);
        }
    }

    public JPanel Panel() {
        return this;
    }

}