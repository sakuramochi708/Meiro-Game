import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;

/* View : サウンドのクラス（親クラス） */
class Sound {
    private Bgm_se bgm;

    // コンストラクタ
    public Sound(Bgm_se bgm) {
        this.bgm = bgm;
    }

    // サウンドを再生する
    public void Sound_play() {
        bgm.play();
    }

    // サウンドをループさせる
    public void Sound_loop() {
        bgm.loop();
    }

    // サウンドをストップさせる
    public void Sound_stop() {
        bgm.stop();
    }

    // ＢＧＭクラスをゲットする
    public Bgm_se getBGM() {
        return bgm;
    }
}

/* View : ソードのＳＥ */
class Sword_SE extends Sound {
    public Sword_SE(Bgm_se b) {
        super(b);
    }
}

/* View : ソードのＳＥ */
class Hammer_SE extends Sound {
    public Hammer_SE(Bgm_se b) {
        super(b);
    }
}

/* View : ハンマーのＳＥ */
class Book_SE extends Sound {
    public Book_SE(Bgm_se b) {
        super(b);
    }
}

/* View : コインのＳＥ */
class Coin_SE extends Sound {
    public Coin_SE(Bgm_se b) {
        super(b);
    }
}

/* View : スターのＳＥ */
class Star_SE extends Sound {
    public Star_SE(Bgm_se b) {
        super(b);
    }
}

/* View : アイテムを得るときのＳＥ */
class GetItem_SE extends Sound {
    public GetItem_SE(Bgm_se b) {
        super(b);
    }
}

/* View : 敵が死んだときのＳＥ */
class DeathEnemy_SE extends Sound {
    public DeathEnemy_SE(Bgm_se b) {
        super(b);
    }
}

/* View : トラップのＳＥ */
class Trap_SE extends Sound {
    public Trap_SE(Bgm_se b) {
        super(b);
    }
}

/* View : ガチャのＳＥ */
class Gacha_SE extends Sound {
    public Gacha_SE(Bgm_se b) {
        super(b);
    }
}

/* View : ボタンのＳＥ */
class Button_SE extends Sound {
    public Button_SE(Bgm_se b) {
        super(b);
    }
}

/* View : スタート画面のＢＧＭ */
class Start_BGM extends Sound {
    public Start_BGM(Bgm_se b) {
        super(b);
    }
}

/* View : ゲーム画面のＢＧＭ */
class Game_BGM extends Sound {
    public Game_BGM(Bgm_se b) {
        super(b);
    }
}

/* View : ゲームオーバー画面のＢＧＭ */
class GameOver_BGM extends Sound {
    public GameOver_BGM(Bgm_se b) {
        super(b);
    }
}

/* View : リザルト画面のＢＧＭ */
class Result_BGM extends Sound {
    public Result_BGM(Bgm_se b) {
        super(b);
    }
}

/* View : 地獄級のときのＢＧＭ */
class Lunatic_BGM extends Sound {
    public Lunatic_BGM(Bgm_se b) {
        super(b);
    }
}

/* View : サウンドパック */
class Sound_Pack {
    private Sword_SE sword_se;
    private Hammer_SE hammer_se;
    private Book_SE book_se;
    private Coin_SE coin_se;
    private Star_SE star_se;
    private GetItem_SE getitem_se;
    private DeathEnemy_SE deathenemy_se;
    private Trap_SE trap_se;
    private Gacha_SE gacha_se;
    private Button_SE button_se;

    private Start_BGM start_bgm;
    private Game_BGM game_bgm;
    private GameOver_BGM gameover_bgm;
    private Result_BGM result_bgm;
    private Lunatic_BGM lunatic_bgm;

    public Sound_Pack() {
        sword_se = new Sword_SE(new Bgm_se("Sword"));
        hammer_se = new Hammer_SE(new Bgm_se("Hammer"));
        book_se = new Book_SE(new Bgm_se("Book"));
        coin_se = new Coin_SE(new Bgm_se("Coin"));
        star_se = new Star_SE(new Bgm_se("Star"));
        getitem_se = new GetItem_SE(new Bgm_se("getItem"));
        deathenemy_se = new DeathEnemy_SE(new Bgm_se("DeathEnemy"));
        trap_se = new Trap_SE(new Bgm_se("Trap"));
        gacha_se = new Gacha_SE(new Bgm_se("Gacha"));
        button_se = new Button_SE(new Bgm_se("Button"));

        start_bgm = new Start_BGM(new Bgm_se("Start"));
        game_bgm = new Game_BGM(new Bgm_se("Game"));
        gameover_bgm = new GameOver_BGM(new Bgm_se("GameOver"));
        result_bgm = new Result_BGM(new Bgm_se("Result"));
        lunatic_bgm = new Lunatic_BGM(new Bgm_se("Lunatic"));
    }

    // BGM, SEを流す
    public void SoundPack_play(String str) {
        if (str.equals("Sword")) {
            sword_se.Sound_play();
        } else if (str.equals("Hammer")) {
            hammer_se.Sound_play();
        } else if (str.equals("Book")) {
            book_se.Sound_play();
        } else if (str.equals("Coin")) {
            coin_se.Sound_play();
        } else if (str.equals("Star")) {
            star_se.Sound_play();
        } else if (str.equals("getItem")) {
            getitem_se.Sound_play();
        } else if (str.equals("DeathEnemy")) {
            deathenemy_se.Sound_play();
        } else if (str.equals("Start")) {
            start_bgm.Sound_play();
        } else if (str.equals("Game")) {
            game_bgm.Sound_play();
        } else if (str.equals("GameOver")) {
            gameover_bgm.Sound_play();
        } else if (str.equals("Result")) {
            result_bgm.Sound_play();
        } else if (str.equals("Trap")) {
            trap_se.Sound_play();
        } else if (str.equals("Gacha")) {
            gacha_se.Sound_play();
        } else if (str.equals("Lunatic")) {
            lunatic_bgm.Sound_play();
        } else if (str.equals("Button")) {
            button_se.Sound_play();
        }
    }

    public void SoundPack_loop(String str) {
        if (str.equals("Sword")) {
            sword_se.Sound_loop();
        } else if (str.equals("Hammer")) {
            hammer_se.Sound_loop();
        } else if (str.equals("Book")) {
            book_se.Sound_loop();
        } else if (str.equals("Coin")) {
            coin_se.Sound_loop();
        } else if (str.equals("Star")) {
            star_se.Sound_loop();
        } else if (str.equals("getItem")) {
            getitem_se.Sound_loop();
        } else if (str.equals("DeathEnemy")) {
            deathenemy_se.Sound_loop();
        } else if (str.equals("Start")) {
            start_bgm.Sound_loop();
        } else if (str.equals("Game")) {
            game_bgm.Sound_loop();
        } else if (str.equals("GameOver")) {
            gameover_bgm.Sound_loop();
        } else if (str.equals("Result")) {
            result_bgm.Sound_loop();
        } else if (str.equals("Trap")) {
            trap_se.Sound_loop();
        } else if (str.equals("Gacha")) {
            gacha_se.Sound_loop();
        } else if (str.equals("Lunatic")) {
            lunatic_bgm.Sound_loop();
        } else if (str.equals("Button")) {
            button_se.Sound_loop();
        }
    }

    public Bgm_se getSoundPack(String str) {
        if (str.equals("Sword")) {
            return sword_se.getBGM();
        } else if (str.equals("Hammer")) {
            return hammer_se.getBGM();
        } else if (str.equals("Book")) {
            return book_se.getBGM();
        } else if (str.equals("Coin")) {
            return coin_se.getBGM();
        } else if (str.equals("Star")) {
            return star_se.getBGM();
        } else if (str.equals("getItem")) {
            return getitem_se.getBGM();
        } else if (str.equals("DeathEnemy")) {
            return deathenemy_se.getBGM();
        } else if (str.equals("Start")) {
            return start_bgm.getBGM();
        } else if (str.equals("Game")) {
            return game_bgm.getBGM();
        } else if (str.equals("GameOver")) {
            return gameover_bgm.getBGM();
        } else if (str.equals("Result")) {
            return result_bgm.getBGM();
        } else if (str.equals("Trap")) {
            return trap_se.getBGM();
        } else if (str.equals("Gacha")) {
            return gacha_se.getBGM();
        } else if (str.equals("Lunatic")) {
            return lunatic_bgm.getBGM();
        } else if (str.equals("Button")) {
            return button_se.getBGM();
        } else {
            return null;
        }
    }

    public void SoundPack_stop(String str) {
        if (str.equals("Start")) {
            start_bgm.Sound_stop();
        } else if (str.equals("Game")) {
            game_bgm.Sound_stop();
        } else if (str.equals("GameOver")) {
            gameover_bgm.Sound_stop();
        } else if (str.equals("Result")) {
            result_bgm.Sound_stop();
        } else if (str.equals("Lunatic")) {
            lunatic_bgm.Sound_stop();
        }
    }
}
