import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/* Model : トラップについての効果を指定 */
class Step_Trap extends Observable implements ActionListener {
    protected Character ch;
    protected AllTrap alltrap;
    protected Position[] possible_move;
    private int row, col, direction;
    private javax.swing.Timer mirror_timer;
    private int mirror_time;
    private boolean flagtrap;

    // コンストラクタ
    public Step_Trap(Character c, AllTrap a) {
        ch = c;
        alltrap = a;
        possible_move = new Position[4];
        possible_move[0] = new Position(-1, 0); // 上
        possible_move[1] = new Position(0, 1); // 右
        possible_move[2] = new Position(1, 0); // 下
        possible_move[3] = new Position(0, -1); // 左
        mirror_timer = new javax.swing.Timer(1000, this);
        mirror_timer.setActionCommand("Mirror");
        flagtrap = false;
    }

    // トラップを踏んだとき、迷路上のトラップを発動済みに変える
    public boolean steppingMirror(int rowchar, int colchar) {
        if (alltrap.getMirrorPosition(rowchar, colchar)) {
            int key = alltrap.getMirrorKey(rowchar, colchar);
            Mirror mirror = alltrap.getOneMirror(key);
            if (!mirror.getFunda()) {
                mirror.Fumu();
                flagtrap = true;
                return true;
            }
        }
        setChanged();
        notifyObservers();
        return false;
    }

    // ランダムに移動
    public void Random_setMove(int rowchar, int colchar, int dire, Maze maze) {
        Random rnd = new Random();
        int temp = rnd.nextInt(4);
        row = rowchar;
        col = colchar;
        int tmp_row = rowchar + possible_move[temp].row;
        int tmp_col = colchar + possible_move[temp].col;
        direction = dire;
        if (tmp_row == maze.getRowstart() + 1 && tmp_col == maze.getColstart()) {
            ch.die_char();
        } else if (!maze.getWall(tmp_row, tmp_col)) {
            row = tmp_row;
            col = tmp_col;
            direction = temp;
        } else {
            ;
        }
    }

    // ランダム時のRowを取得
    public int getRandomRow() {
        return row;
    }

    // ランダム時のcolを取得
    public int getRandomCol() {
        return col;
    }

    // ランダム時の方向を取得
    public int getRandomDirection() {
        return direction;
    }

    // ミラー状態にする
    public void setMirrorMode() {
        ch.setMirror();
        mirror_time = 10;
        mirror_timer.start();
    }

    // 残りミラー時間を返す
    public int getMirrorTime() {
        return mirror_time;
    }

    // SEを鳴らす
    public boolean getTrapSE() {
        boolean temp = flagtrap;
        flagtrap = false;
        return temp;
    }

    // ミラー時間を管理する
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("Mirror")) {
            mirror_time--;
            if (mirror_time <= 0) {
                mirror_timer.stop();
                ch.eraseMirror();
            }
            setChanged();
            notifyObservers();
        }
    }
}
