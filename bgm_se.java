import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.sampled.*;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/* View : BGM, SE */
class Bgm_se {

    Clip clip;

    public Bgm_se(String str) {
        if (str.equals("Sword")) {
            clip = createClip(new File("bgm/sword.wav"));
        } else if (str.equals("Hammer")) {
            clip = createClip(new File("bgm/hammer.wav"));
        } else if (str.equals("DeathEnemy")) {
            clip = createClip(new File("bgm/syoumetu.wav"));
        } else if (str.equals("Game")) {
            clip = createClip(new File("bgm/dangeon01.wav"));
        } else if (str.equals("GameOver")) {
            clip = createClip(new File("bgm/dead-sound.wav"));
        } else if (str.equals("Start")) {
            clip = createClip(new File("bgm/start1.wav"));
        } else if (str.equals("Result")) {
            clip = createClip(new File("bgm/gameclear.wav"));
        } else if (str.equals("Book")) {
            clip = createClip(new File("bgm/book_se.wav"));
        } else if (str.equals("getItem")) {
            clip = createClip(new File("bgm/soubi-01.wav"));
        } else if (str.equals("Coin")) {
            clip = createClip(new File("bgm/coin.wav"));
        } else if (str.equals("Star")) {
            clip = createClip(new File("bgm/makestar3.wav"));
        } else if (str.equals("Trap")) {
            clip = createClip(new File("bgm/Trap.wav"));
        } else if (str.equals("Gacha")) {
            clip = createClip(new File("bgm/gacha.wav"));
        } else if (str.equals("Lunatic")) {
            clip = createClip(new File("bgm/lunatic.wav"));
        } else if (str.equals("Button")) {
            clip = createClip(new File("bgm/button.wav"));
        }

    }

    public static Clip createClip(File path) {
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(path)) {
            AudioFormat af = ais.getFormat();
            DataLine.Info dataLine = new DataLine.Info(Clip.class, af);
            Clip c = (Clip) AudioSystem.getLine(dataLine);
            c.open(ais);
            FloatControl volume = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-30.0f); // ここで音量を調整(引数はデシベル)
            return c;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void play() {
        clip.setFramePosition(0);
        clip.loop(0);
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}