import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/* Main : フレーム作成・画面推移の処理 */
class Game extends JFrame implements ActionListener {

	// main()
	public static void main(String[] args) {
		new Game();
	}

	/* M ( Maze 関連 ) */
	public Maze maze; // M
	public Character chara; // M
	public Enemy enemy; // M
	public AllItem allitem;
	public AllTrap alltrap;
	/* M ( その他 ) */
	public Select_Item select;
	public Gacha gacha;
	public Mana_File mana_file; // FILE入出力管理
	public Mana_Item mana_item; // 所持アイテム数管理
	public Mana_Item mana_select_item; // 選択したアイテム数を管理
	public History_make history;
	/* V */
	public StartPanel st_view;
	public SelectPanel sl_view;
	public GamePanel view; // V
	public ResultPanel re_view;
	public GameoverPanel go_view;
	public GachaPanel gc_view;
	public HistoryPanel ht_view;
	public ResetPanel ht_view2;
	public View_se view_se;
	public View_bgm view_bgm;
	public View_seGacha view_segacha;
	public View_seButton view_sebutton;
	/* C */
	public StartCont st_cont;
	public SelectCont sl_cont;
	public GameCont cont;
	public ResultCont re_cont;
	public GameoverCont go_cont;
	public GachaCont gc_cont;
	public HistoryCont ht_cont;
	/* other */
	private Count_time count;
	private long time; // countの時間を受け取る
	private javax.swing.Timer timer;
	// StartPanel = 0, GamePanel = 1, ResultPanel = 2, GameOverPanel = 3,
	// SelectPanel = 4; Gacha = 5, history = 6;
	private int panelmode;
	// difficult: Easy = 0, Normal = 1, Hard = 2, Lunatic = 3
	private int mode;

	// コンストラクタ（ゲーム起動）
	public Game() {
		// ベースフレーム作成
		this.setTitle("Meiro Game");
		this.setSize(1000, 1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		mana_item = new Mana_Item();
		mana_file = new Mana_File(mana_item);

		makeStPanel();
		setFocusable(true);
	}

	// Frameをreturn
	public Game getFrame() {
		return this;
	}

	// panelmode を return
	public int getPanelmode() {
		return panelmode;
	}

	/* 画面の推移 */
	// StartPanel 生成
	public void makeStPanel() {
		st_view = new StartPanel();
		view_bgm = new View_bgm();
		st_cont = new StartCont(this);
		view_bgm.Play_BGM("Start");

		this.add(st_view);
		panelmode = 0;
		this.repaint();
		this.revalidate();
		this.requestFocus();
		return;
	}

	// StartPanel -> SelectPanel
	public void start_select(int mode) {
		this.mode = mode;
		st_cont.remove();

		mana_select_item = new Mana_Item();
		mana_select_item.zero_Mana_Item(mana_select_item);
		select = new Select_Item(mana_item, mana_select_item);
		sl_view = new SelectPanel(select);
		view_sebutton = new View_seButton(select);
		sl_cont = new SelectCont(this);

		this.remove(st_view);
		this.add(sl_view);
		panelmode = 4;
		setFocusable(true);
		this.repaint();
		this.revalidate();
		this.requestFocus();
	}

	// SelectPanel -> GamePanel(呼び出し)
	public void select_maze() {
		select_maze(mode);
	}

	// SelectPanel -> GamePanel(実行
	public void select_maze(int mode) {
		this.mode = mode;

		this.remove(sl_view);
		sl_cont.remove();
		select = null;
		sl_view = null;
		sl_cont = null;
		view_sebutton = null;

		maze = new Maze(mode);
		enemy = new Enemy(maze, mode);
		allitem = new AllItem(maze, enemy);
		alltrap = new AllTrap(maze);
		chara = new Character(maze, allitem, enemy, alltrap, mana_select_item);
		count = new Count_time();
		view = new GamePanel(maze, chara, enemy, allitem, count, alltrap);
		view_se = new View_se(maze, chara, enemy);
		cont = new GameCont(this);

		// キャラの生死・クリアしたかどうかの判断タイマー
		timer = new javax.swing.Timer(5, this);
		timer.setActionCommand("timer");
		timer.start();

		this.add(view);
		panelmode = 1;
		this.repaint();
		setFocusable(true);
		this.revalidate();
		this.requestFocus();

		// BGM
		view_bgm.Stop_BGM("Start");
		view_se.Play_BGM();

		return;
	}

	// SelectPanel -> StartPanel
	public void pushreturn() {
		this.remove(sl_view);
		sl_cont.remove();

		select = null;
		sl_view = null;
		sl_cont = null;

		this.add(st_view);
		panelmode = 0;
		this.repaint();
		setFocusable(true);
		this.revalidate();
		this.requestFocus();

	}

	// GamePanel -> StartPanel (pushReset)
	public void pushReset() {

		timer.stop();
		enemy.stopTimer();
		count.stopTimer();

		this.remove(view);
		cont.remove();
		enemy = null;
		maze = null;
		allitem = null;
		alltrap = null;
		chara = null;
		view = null;
		cont = null;
		count = null;

		this.add(st_view);
		panelmode = 0;

		this.repaint();
		setFocusable(true);
		this.revalidate();
		this.requestFocus();

		// BGM
		view_se.Stop_BGM();
		view_bgm.Play_BGM("Start");

		return;
	}

	// タイマーの管理（ResultPanel, GameoverPanelに移行するフラグを検知している）
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("timer")) {
			if (!isGoal()) { // ゴールかどうかの判断
				if (!chara.getisLive()) { // キャラが生きているかの判断
					changeGameOverPanel();
				}
			}
			return;
		}
	}

	// GamePanel -> ResultPanel (Goal)
	public boolean isGoal() {
		if (chara.getRowchar() == maze.getRowgoal() && chara.getColchar() == maze.getColgoal()) {
			// 敵判定の停止
			timer.stop();
			enemy.stopTimer();

			// タイマー計測
			count.stopTimer();
			time = count.getTime();

			// 選択したアイテムの数を引く
			mana_file.subData(mana_item, mana_select_item);

			// コインの回収
			Mana_Item add_mana_item = new Mana_Item();
			add_mana_item.zero_Mana_Item(add_mana_item);
			add_mana_item.coin = chara.getCoinCount();
			mana_file.addData(mana_item, add_mana_item);

			// パネル生成
			this.remove(view);
			cont.remove();
			maze = null;
			enemy = null;
			allitem = null;
			alltrap = null;
			chara = null;
			view = null;
			cont = null;
			count = null;

			// 新記録かどうかの判断
			JudgeNewRecord jnr = new JudgeNewRecord();
			re_view = new ResultPanel(time, add_mana_item.coin, mode, jnr.judge(mana_file, mana_item, time, mode));
			re_cont = new ResultCont(this);

			this.add(re_view);
			panelmode = 2;
			this.repaint();
			setFocusable(true);
			this.revalidate();
			this.requestFocus();

			// BGM
			view_se.Stop_BGM();
			view_se = null;
			view_bgm.Play_BGM("Result");

			return true;
		}
		return false;
	}

	// Game -> GameOver (死んだぁ)
	public void changeGameOverPanel() {
		// システム停止
		timer.stop();
		enemy.stopTimer();
		count.stopTimer();

		// 選択したアイテムの数を引く
		mana_file.subData(mana_item, mana_select_item);

		this.remove(view);
		cont.remove();
		maze = null;
		enemy = null;
		allitem = null;
		alltrap = null;
		chara = null;
		view = null;
		cont = null;
		count = null;

		go_view = new GameoverPanel();
		go_cont = new GameoverCont(this);
		panelmode = 3;
		this.add(go_view);
		this.repaint();
		setFocusable(true);
		this.revalidate();
		this.requestFocus();

		// BGM
		view_se.Stop_BGM();
		view_se = null;
		view_bgm.Play_BGM("GameOver");

		return;
	}

	// ResultPanel -> StartPanel
	public void result_start() {

		this.remove(re_view);
		re_cont.remove();
		re_view = null;
		re_cont = null;

		this.add(st_view);
		panelmode = 0;
		this.repaint();
		setFocusable(true);
		this.revalidate();
		this.requestFocus();

		// BGM
		view_bgm.Stop_BGM("Result");
		view_bgm.Play_BGM("Start");

		return;
	}

	// GameoverPanel -> StartPanel
	public void gameover_start() {

		this.remove(go_view);
		go_cont.remove();
		go_view = null;
		go_cont = null;

		this.add(st_view);
		panelmode = 0;
		this.repaint();
		setFocusable(true);
		this.revalidate();
		this.requestFocus();

		// BGM
		view_bgm.Stop_BGM("GameOver");
		view_bgm.Play_BGM("Start");
	}

	// Start -> Gacha
	public void start_gacha() {
		// 取り外し
		st_cont.remove();
		this.remove(st_view);

		// 付加
		gacha = new Gacha(mana_file, mana_item);
		gc_view = new GachaPanel(gacha);
		gc_cont = new GachaCont(this);
		view_segacha = new View_seGacha(gacha);

		this.add(gc_view);
		panelmode = 5;
		this.repaint();
		setFocusable(true);
		this.revalidate();
		this.requestFocus();
	}

	// Gacha -> Start
	public void gacha_start() {
		// 取り外し
		gc_cont.remove();
		this.remove(gc_view);
		gacha = null;
		gc_view = null;
		gc_cont = null;

		// 取付
		this.add(st_view);
		st_cont.add();
		panelmode = 0;

		this.repaint();
		setFocusable(true);
		this.revalidate();
		this.requestFocus();
	}

	// HistoryPanel -> StartPanel
	public void history_start() {
		this.remove(ht_view);
		ht_cont.remove();

		history = null;
		ht_view = null;
		ht_view2 = null;
		ht_cont = null;

		this.add(st_view);
		panelmode = 0;
		this.repaint();
		setFocusable(true);
		this.revalidate();
		this.requestFocus();
	}

	// StartPanel -> SelectPanel
	public void start_history() {
		st_cont.remove();

		history = new History_make(mana_item);
		ht_view = new HistoryPanel(history);
		ht_view2 = new ResetPanel();
		ht_cont = new HistoryCont(this);

		this.remove(st_view);
		this.add(ht_view);
		panelmode = 6;
		setFocusable(true);
		this.repaint();
		this.revalidate();
		this.requestFocus();
	}

	// history -> reset
	public void history_notion() {
		this.remove(ht_view);
		this.add(ht_view2);
		panelmode = 7;
		setFocusable(true);
		this.repaint();
		this.revalidate();
		this.requestFocus();
	}

	// reset -> history (not resetdata)
	public void notion_history_no() {
		this.remove(ht_view2);
		this.add(ht_view);
		panelmode = 6;
		setFocusable(true);
		this.repaint();
		this.revalidate();
		this.requestFocus();
	}

	// reset -> history (resetdata)
	public void notion_history_yes() {
		this.remove(ht_view2);
		ht_view = null;
		ht_cont = null;

		ht_view = new HistoryPanel(history);
		ht_cont = new HistoryCont(this);
		this.add(ht_view);
		panelmode = 6;
		setFocusable(true);
		this.repaint();
		this.revalidate();
		this.requestFocus();
	}
}