package game_models;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private static Sound instance;
    private Clip clip;

    public static final Sound explosion = new Sound("res\\Explosion.wav");
    public static final Sound soundgame = new Sound("res\\som de fundo.wav");
    public static final Sound soundShoot = new Sound("res\\Laser_Shoot.wav");
    public static final Sound kill = new Sound("res\\sound_kill.wav");

    private Sound(String filename) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filename));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static Sound getInstance(String filename) {
        if (instance == null) {
            instance = new Sound(filename);
        }
        return instance;
    }

    //Método para começar o som
    public void play() {
        if (clip != null) {
            new Thread(() -> {
                clip.setFramePosition(0);
                clip.start();
            }).start();
        }
    }

    //Método para deixar o som em loop
    public void loop() {
        if (clip != null) {
            new Thread(() -> {
                clip.setFramePosition(0);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }).start();
        }
    }

    //Método para parar o som
    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }
}
