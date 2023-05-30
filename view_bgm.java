import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;

/* View : SE */
class View_se extends Sound_Pack implements Observer {
    private Character ch;
    private Enemy ene;
    private Maze maze;
    private Have_Item have_item;
    private Step_Trap step_trap;

    private Bgm_se deathenemy_se;
    private Bgm_se sword_se;
    private Bgm_se hammer_se;
    private Bgm_se book_se;
    private Bgm_se coin_se;
    private Bgm_se getitem_se;
    private Bgm_se star_se;
    private Bgm_se trap_se;
    private Bgm_se game_bgm;
    private Bgm_se lunatic_bgm;

    // コンストラクタ
    public View_se(Maze maze, Character ch, Enemy ene) {
        this.maze = maze;
        this.ch = ch;
        this.ene = ene;
        this.have_item = ch.getHave_Item();
        this.step_trap = ch.getStep_Trap();

        deathenemy_se = getSoundPack("DeathEnemy");
        sword_se = getSoundPack("Sword");
        hammer_se = getSoundPack("Hammer");
        book_se = getSoundPack("Book");
        coin_se = getSoundPack("Coin");
        getitem_se = getSoundPack("getItem");
        trap_se = getSoundPack("Trap");
        star_se = getSoundPack("Star");
        game_bgm = getSoundPack("Game");
        lunatic_bgm = getSoundPack("Lunatic");

        this.ene.addObserver(this);
        this.have_item.addObserver(this);
        this.step_trap.addObserver(this);
    }

    // SEを鳴らす
    public void Play_SE() {
        if (ene.getFlagDeath()) {
            deathenemy_se.play();
        }
        if (have_item.getSwordSE()) {
            sword_se.play();
        }
        if (have_item.getHammerSE()) {
            hammer_se.play();
        }
        if (have_item.getBookSE()) {
            book_se.play();
        }
        if (have_item.getGetitemSE()) {
            getitem_se.play();
        }
        if (have_item.getCoinSE()) {
            coin_se.play();
        }
        if (step_trap.getTrapSE()) {
            trap_se.play();
        }
        if (have_item.getStarSE()) {
            star_se.play();
        }
    }

    // SEを止める
    public void Stop_SE() {
        if (ch.getRowchar() == maze.getRowgoal() && ch.getColchar() == maze.getColgoal()) {
            star_se.stop();
        }
    }

    // アップデート
    public void update(Observable o, Object arg) {
        Play_SE();
        Stop_SE();
    }

    // BGMを鳴らす
    public void Play_BGM() {
        if (maze.getMode() == 3) {
            lunatic_bgm.play();
        } else {
            game_bgm.play();
        }
    }

    // BGMを止める
    public void Stop_BGM() {
        if (maze.getMode() == 3) {
            lunatic_bgm.stop();
        } else {
            game_bgm.stop();
        }
    }
}

/* View : ガチャ画面のときのＳＥ */
class View_seGacha extends Sound_Pack implements Observer {
    private Gacha gacha;
    private Bgm_se gacha_se;

    // コンストラクタ
    public View_seGacha(Gacha gacha) {
        this.gacha = gacha;
        gacha_se = getSoundPack("Gacha");
        this.gacha.addObserver(this);
    }

    // SEを鳴らす
    public void Play_SE() {
        if (gacha.getFlagGacha()) {
            gacha_se.play();
        }
    }

    // アップデート
    public void update(Observable o, Object arg) {
        Play_SE();
    }
}

/* View : 各画面のＢＧＭ */
class View_bgm extends Sound_Pack {
    private Bgm_se start_bgm;
    private Bgm_se game_bgm;
    private Bgm_se gameover_bgm;
    private Bgm_se result_bgm;

    // コンストラクタ
    public View_bgm() {
        start_bgm = getSoundPack("Start");
        game_bgm = getSoundPack("Game");
        gameover_bgm = getSoundPack("GameOver");
        result_bgm = getSoundPack("Result");
    }

    // BGMを流す
    public void Play_BGM(String str) {
        if (str.equals("Start")) {
            start_bgm.play();
            start_bgm.loop();
        } else if (str.equals("Game")) {
            game_bgm.play();
            game_bgm.loop();
        } else if (str.equals("GameOver")) {
            gameover_bgm.play();
            gameover_bgm.loop();
        } else if (str.equals("Result")) {
            result_bgm.play();
            result_bgm.loop();
        }
    }

    // BGMを止める
    public void Stop_BGM(String str) {
        if (str.equals("Start")) {
            start_bgm.stop();
        } else if (str.equals("Game")) {
            game_bgm.stop();
        } else if (str.equals("GameOver")) {
            gameover_bgm.stop();
        } else if (str.equals("Result")) {
            result_bgm.stop();
        }
    }
}

/* View : ボタンを押す */
class View_seButton extends Sound_Pack implements Observer {
    private Select_Item select_item;
    private Bgm_se button_se;

    // コンストラクタ
    public View_seButton(Select_Item select_item) {
        this.select_item = select_item;
        button_se = getSoundPack("Button");
        this.select_item.addObserver(this);
    }

    // 鳴らす
    public void Play_SE() {
        if (select_item.getFlagButton()) {
            button_se.play();
        }
    }

    // アップデート
    public void update(Observable o, Object arg) {
        Play_SE();
    }
}