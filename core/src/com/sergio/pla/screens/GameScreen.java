package com.sergio.pla.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.sergio.pla.gameWorld.GameRenderer;
import com.sergio.pla.gameWorld.GameWorld;
import com.sergio.pla.inputHandler.InputHandler;

/**
 * @author Sergio Pla
 */
public class GameScreen implements Screen {

    private GameWorld world;
    private GameRenderer renderer;
    private float runTime;

    /**
     * Crea la pantalla de juego
     */
    public GameScreen() {

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 136;
        float gameHeight = screenHeight / (screenWidth / gameWidth);

        int midPointY = (int) (gameHeight / 2);
        world = new GameWorld(midPointY);
        renderer = new GameRenderer(world, (int) gameHeight, midPointY);

        Gdx.input.setInputProcessor(new InputHandler(world));

    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render(runTime);
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("Juego", "resizing: width: " + width + "height: " + height);
    }

    @Override
    public void show() {
        Gdx.app.log("Juego", "show()");
    }

    @Override
    public void hide() {
        Gdx.app.log("Juego", "hide()");
    }

    @Override
    public void pause() {
        Gdx.app.log("Juego", "pause()");
    }

    @Override
    public void resume() {
        Gdx.app.log("Juego", "resume()");
    }

    @Override
    public void dispose() {
        Gdx.app.log("Juego", "dispose()");
    }

}
