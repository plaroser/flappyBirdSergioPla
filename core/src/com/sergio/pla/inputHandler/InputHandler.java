package com.sergio.pla.inputHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.sergio.pla.gameObjets.Bird;
import com.sergio.pla.gameWorld.GameWorld;

/**
 * @author Sergio Pla
 */
public class InputHandler implements InputProcessor {
    private Bird myBird;
    private GameWorld gameWorld;

    /**
     * Crea una nueva instancia de InputHandler
     *
     * @param gameWorld Referencia GameWorld para trabajar con el
     */
    public InputHandler(GameWorld gameWorld) {
        // myBird now represents the gameWorld's bird.
        this.gameWorld = gameWorld;
        myBird = gameWorld.getBird();
    }

    /**
     * Pulsar en la pantalla tactil
     *
     * @param screenX
     * @param screenY
     * @param pointer
     * @param button
     * @return
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return fly();
    }

    /**
     * Pulsar una tecla del teclado
     *
     * @param keycode
     * @return
     */
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.BACK:
                Gdx.app.log("Tecla", "Tecla atras pulsada");
                break;
            case Input.Keys.SPACE:
            case Input.Keys.UP:
                return fly();
        }
        return false;
    }

    private boolean fly() {
        if (gameWorld.isReady()) {
            gameWorld.start();
        }
        myBird.onClick();
        if (gameWorld.isGameOver() || gameWorld.isHighScore()) {
            // Reset all variables, go to GameState.READ
            gameWorld.restart();
        }
        return true; // Return true to say we handled the touch.
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
