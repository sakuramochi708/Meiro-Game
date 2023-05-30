import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/* Controller : ガチャ画面に対してのコントローラー */
class GachaCont implements ActionListener, KeyListener {
  public Game frame;

  // コンストラクタ
  public GachaCont(Game g) {
    frame = g;
    frame.addKeyListener(this);
    frame.gc_view.b1.addActionListener(this);
    frame.gc_view.b1.setActionCommand("hiku");
    frame.gc_view.b2.addActionListener(this);
    frame.gc_view.b2.setActionCommand("modoru");
  }

  // キーリスナーを取り除く
  public void remove() {
    frame.removeKeyListener(this);
  }

  // ボタンの操作
  public void actionPerformed(ActionEvent e) {
    String cmd = e.getActionCommand();
    if (cmd.equals("hiku")) {
      frame.gacha.action_gacha();
      return;
    }
    if (cmd.equals("modoru")) {
      frame.gacha_start();
      return;
    }
  }

  // キーの操作
  public void keyPressed(KeyEvent e) {

  }

  public void keyTyped(KeyEvent e) {

  }

  public void keyReleased(KeyEvent e) {

  }
}