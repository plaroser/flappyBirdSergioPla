package com.sergio.pla.gameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.sergio.pla.assetLoader.Assetloader;
import com.sergio.pla.gameObjets.Bird;
import com.sergio.pla.gameObjets.ScrollHandler;

/**
 * Contiene todos los metodos para realizar los calculos del juego
 *
 * @author Sergio Pla
 */
public class GameWorld {
    private Bird bird;
    private ScrollHandler scrollHandler;
    private boolean isAlive = true;
    private Rectangle ground;
    private int score = 0;
    private int midPointY;

    /**
     * Todos los posibles estados del juego
     */
    public enum GameState {
        READY, RUNNING, GAMEOVER, HIGHSCORE
    }

    private GameState currentState;

    /**
     * Crea una nueva instancia del juego con el centro del juego
     *
     * @param midPointY Centro del juego
     */
    public GameWorld(int midPointY) {
        currentState = GameState.READY;
        bird = new Bird(33, midPointY - 5, 17, 12);
        scrollHandler = new ScrollHandler(this, midPointY + 66);
        ground = new Rectangle(0, midPointY + 66, 136, 11);
        currentState = GameState.READY;
        this.midPointY = midPointY;
    }

    /**
     * Actualiza el juego segun el estado actual
     *
     * @param delta velocidad de juego
     */
    public void update(float delta) {
        switch (currentState) {
            case READY:
                updateReady(delta);
                break;

            case RUNNING:
                updateRunning(delta);
                break;
            default:
                break;
        }
    }

    /**
     * Actualiza el juego mientras que el juego esta funcionando
     *
     * @param delta Velocidad del juego actual
     */
    public void updateRunning(float delta) {
        //Establece la velociad del juego minima
        if (delta > .15f) {
            delta = .15f;
        }

        bird.update(delta);
        scrollHandler.update(delta);

        //Si el pajaro esta vivo y colisiona
        if (bird.isAlive() && scrollHandler.collides(bird)) {
            scrollHandler.stop();
            Assetloader.dead.play();
            isAlive = false;
        }
        //Si el pajaro se sale del mundo(muere)
        if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {
            scrollHandler.stop();
            bird.die();
            bird.decelerate();
            currentState = GameState.GAMEOVER;
            Gdx.app.log("Juego", "Muerto");

            if (score > Assetloader.getHighScore()) {
                Assetloader.setHighScore(score);
                Gdx.app.log("Juego", "high score");
                currentState = GameState.HIGHSCORE;
            }
        }
    }

    /**
     * Devuelve el pajaro actual
     *
     * @return Pajaro actual
     */
    public Bird getBird() {
        return bird;

    }

    /**
     * Devuelve el scrollHandler actual
     *
     * @return scrollHandler actual
     */
    public ScrollHandler getScroller() {
        return scrollHandler;
    }

    /**
     * Devuelve la puntuacion actual obtenida
     *
     * @return Puntuacion obtenida
     */
    public int getScore() {
        return score;
    }

    /**
     * Incrementa la puntuacion obtenida
     *
     * @param increment Cantidad a incrementar
     */
    public void addScore(int increment) {
        score += increment;
    }

    /**
     * Devuelve si el juego esta preparado
     *
     * @return Esta preparado
     */
    public boolean isReady() {
        return (currentState == GameState.READY);
    }

    /**
     * Inicia el juego
     */
    public void start() {
        Gdx.app.log("Juego", "iniciado");
        currentState = GameState.RUNNING;
    }

    /**
     * Devuelve si el juego ha terminado
     *
     * @return Juego terminado
     */
    public boolean isGameOver() {
        return (currentState == GameState.GAMEOVER);
    }

    /**
     * Reinicia el juego para estar preparado para empezar
     */
    public void restart() {
        Gdx.app.log("Juego", "preparado");

        currentState = GameState.READY;
        score = 0;
        bird.onRestart(midPointY - 5);
        scrollHandler.onRestart();
        currentState = GameState.READY;
    }

    public void updateReady(float delta) {

    }

    /**
     * Devuelve si el juego ha hecho una puntuacion mas alta
     *
     * @return puntuacion mas alta
     */
    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }
}
