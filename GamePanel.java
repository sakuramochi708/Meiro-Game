import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;

/* View : ゲーム画面（統合版） */
public class GamePanel extends JPanel {
    private MazePanel mazepanel;
    private ItemPanel itempanel;
    private JPanel mainpanel;

    public GamePanel(Maze maze, Character ch, Enemy ene, AllItem allitem, Count_time time, AllTrap alltrap) {
        this.setBackground(Color.BLACK);
        mazepanel = new MazePanel(maze, ch, ene, allitem, alltrap);
        itempanel = new ItemPanel(allitem, ch, time);
        mazepanel.add(itempanel);
        this.add(mazepanel);
    }

}
