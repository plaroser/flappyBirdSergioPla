package com.sergio.pla.gameObjets;

/**
 * Cesped que hay debajo del juego de manera continua
 *
 * @author Sergio Pla
 */
public class Grass extends scrollable {
    /**
     * Crea el cesped debajo del juego
     *
     * @param x           Posicion X
     * @param y           Posicion Y
     * @param width       Anchura
     * @param height      Altura
     * @param scrollspeed velocidad de movimiento
     */
    public Grass(float x, float y, int width, int height, float scrollspeed) {
        super(x, y, width, height, scrollspeed);
    }

    /**
     * reinicia el cesped
     *
     * @param x           Posicion x nueva
     * @param scrollSpeed Velocidad de movimiento nueva
     */
    public void onRestart(float x, float scrollSpeed) {
        position.x = x;
        velocity.x = scrollSpeed;
    }
}
