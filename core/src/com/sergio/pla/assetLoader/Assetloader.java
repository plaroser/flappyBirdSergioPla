package com.sergio.pla.assetLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sergio.pla.utils.GameCallBack;

/**
 * Sergio Pla
 */
public class Assetloader {
    public static final String HIGH_SCORE = "highScore", SOUND = "sound";
    public static Texture texture;
    public static TextureRegion bg, grass;

    public static Animation birdAnimation;
    public static TextureRegion bird, birdDown, birdUp;
    public static TextureRegion skullUp, skullDown, bar;
    public static Sound dead, coin, flap, kill;
    public static BitmapFont fontText;
    public static Preferences prefs;


    /**
     * Carga todas las texturas en el programa
     */
    public static void load(GameCallBack callBack) {
        texture = new Texture(Gdx.files.internal("texture.png"));
        dead = Gdx.audio.newSound(Gdx.files.internal("dead.wav"));
        flap = Gdx.audio.newSound(Gdx.files.internal("flap.wav"));
        coin = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
        kill = Gdx.audio.newSound(Gdx.files.internal("kill.mp3"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        prefs = Gdx.app.getPreferences("SergioGame");
        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }
        prefs.putBoolean(SOUND, callBack.isSound());
        prefs.flush();

        Gdx.app.log("Pref", "Sonido activado en callBack: " + callBack.isSound());
        Gdx.app.log("Pref", "Sonido activado en gdx: " + prefs.getBoolean(SOUND));
        bg = new TextureRegion(texture, 0, 0, 136, 43);
        bg.flip(false, true);

        grass = new TextureRegion(texture, 0, 43, 143, 11);
        grass.flip(false, true);

        birdDown = new TextureRegion(texture, 136, 0, 17, 12);
        birdDown.flip(false, true);

        bird = new TextureRegion(texture, 153, 0, 17, 12);
        bird.flip(false, true);

        birdUp = new TextureRegion(texture, 170, 0, 17, 12);
        birdUp.flip(false, true);

        TextureRegion[] birds = {birdDown, bird, birdUp};
        birdAnimation = new Animation(0.06f, birds);
        birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        skullUp = new TextureRegion(texture, 192, 0, 24, 14);

        skullDown = new TextureRegion(skullUp);
        skullDown.flip(false, true);


        bar = new TextureRegion(texture, 136, 16, 22, 3);
        bar.flip(false, true);

        fontText = new BitmapFont(Gdx.files.internal("text.fnt"));
        fontText.getData().setScale(.25f, -.25f);


    }

    /**
     * Elimina todos los recursos cargados durante el juego
     */
    public static void dispose() {
        texture.dispose();
        coin.dispose();
        flap.dispose();
        dead.dispose();
        fontText.dispose();
        kill.dispose();
    }

    /**
     * Establece una nueva puntiacion maxima
     *
     * @param val puntuacion obtenida
     */
    public static void setHighScore(int val) {
        prefs.putInteger(HIGH_SCORE, val);
        prefs.flush();
    }

    /**
     * Devuelve la mayor puntuacion maxima
     *
     * @return Mayor puntuacion maxima
     */
    public static int getHighScore() {
        return prefs.getInteger(HIGH_SCORE);
    }
}
