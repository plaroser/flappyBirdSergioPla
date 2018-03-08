package com.sergio.pla.gameObjets;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Tubo del juego, es continuo para poder darle la longitud necesaria
 *
 * @author Sergio Pla
 */
public class Pipe extends scrollable {

    private Random r;
    private Rectangle skullUp, skullDown, barUp, barDown;
    /**
     * Distancia entre tubos
     */
    public static final int VERTICAL_GAP = 45;
    /**
     * Anchura del tubo
     */
    public static final int SKULL_WIDTH = 24;
    /**
     * Altura del tubo
     */
    public static final int SKULL_HEIGHT = 11;
    /**
     * Altura de comienzo del mundo
     */
    private float groundY;
    private boolean isScored = false;

    /**
     * Crea un nuevo tubo
     *
     * @param x           Pocicion X
     * @param y           Posicion Y
     * @param width       Anchura
     * @param height      ALtura
     * @param scrollSpeed Velocidad de movimiento
     * @param groundY     Altura del suelo en la que aparecer
     */
    public Pipe(float x, float y, int width, int height, float scrollSpeed, float groundY) {
        super(x, y, width, height, scrollSpeed);
        // Inicializa en aleatorio para determinar la altura
        r = new Random();
        skullUp = new Rectangle();
        skullDown = new Rectangle();
        barUp = new Rectangle();
        barDown = new Rectangle();
        this.groundY = groundY;
    }

    /**
     * Resetea el tubo
     *
     * @param newX Nueva posicion
     */
    @Override
    public void reset(float newX) {
        // LLama al padre para resetearlo
        super.reset(newX);
        // Cambia la altura en un numero aleatorio
        height = r.nextInt(90) + 15;
        isScored = false;
    }

    /**
     * Actualiza el estado del tubo
     *
     * @param delta Tiempo de juego actual
     */
    @Override
    public void update(float delta) {
        super.update(delta);
        float shift = (SKULL_WIDTH - width) / 2;
        barUp.set(position.x, position.y, width, height);
        barDown.set(position.x, position.y + height + VERTICAL_GAP, width, groundY - (position.y + VERTICAL_GAP + height));
        skullUp.set(position.x - shift, position.y + height - SKULL_HEIGHT, SKULL_WIDTH, SKULL_HEIGHT);
        skullDown.set(position.x - shift, barDown.getY(), SKULL_WIDTH, SKULL_HEIGHT);

    }

    public Rectangle getSkullUp() {
        return skullUp;
    }

    public Rectangle getSkullDown() {
        return skullDown;
    }

    public Rectangle getBarUp() {
        return barUp;
    }

    public Rectangle getBarDown() {
        return barDown;
    }

    /**
     * Devuelve si el tubo actual ha colisionado con el pajaro
     *
     * @param bird Pajaro al que comprobar la colision
     * @return Si ha colisionado
     */
    public boolean collides(Bird bird) {
        if (position.x < bird.getX() + bird.getWidth()) {

            return (Intersector.overlaps(bird.getBoundingCircle(), barUp)
                    || Intersector.overlaps(bird.getBoundingCircle(), barDown)
                    || Intersector.overlaps(bird.getBoundingCircle(), skullUp)
                    || Intersector.overlaps(bird.getBoundingCircle(), skullDown));
        }
        return false;
    }

    /**
     * Devuelve si ha puntuado
     *
     * @return Ha puntuado
     */
    public boolean isScored() {
        return isScored;
    }

    /**
     * Establece si tiene que puntuar
     *
     * @param b Tiene que puntuar
     */
    public void setScored(boolean b) {
        isScored = b;
    }

    /**
     * Reinicia el estado del tubo
     *
     * @param x           Nueva altura del tubo
     * @param scrollSpeed Velocidad actual del tubo
     */
    public void onRestart(float x, float scrollSpeed) {
        velocity.x = scrollSpeed;
        reset(x);
    }

}
