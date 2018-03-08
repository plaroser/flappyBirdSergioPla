package com.sergio.pla.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.sergio.pla.assetLoader.Assetloader;
import com.sergio.pla.screens.GameScreen;
import com.sergio.pla.utils.GameCallBack;

/**
 * @author Sergio Pla
 */
public class SergioPlaGame extends Game {
    public GameCallBack callBack;


    public SergioPlaGame(GameCallBack callBack) {
        super();
        this.callBack = callBack;
    }

    /**
     * Lanza el juego
     */
    @Override
    public void create() {
        Gdx.app.log("Juego", "Juego creado!");
        Assetloader.load();
        setScreen(new GameScreen(callBack));
    }

    /**
     * Termina el juego
     */
    @Override
    public void dispose() {
        super.dispose();
        Assetloader.dispose();
    }
}
