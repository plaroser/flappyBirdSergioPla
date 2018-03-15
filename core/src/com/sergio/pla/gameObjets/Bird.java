package com.sergio.pla.gameObjets;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.sergio.pla.assetLoader.Assetloader;

/**
 * @author Sergio Pla
 */
public class Bird {

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private float rotation;
    private int width;
    private int height;
    private Circle boundingCircle;
    private boolean alive;

    /**
     * Crea el pajaro en una determinada posicion y con un tamaño determinado
     *
     * @param x      posicion sobre el eje X
     * @param y      posicion sobre el eje Y
     * @param width  Tamaño de anchura
     * @param height Tamaño de altura
     */
    public Bird(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 460);
        boundingCircle = new Circle();
        alive = true;
    }

    /**
     * Actualiza el estado del pajaro y establece el angulo de giro
     *
     * @param delta Tiempo de juego actual
     */
    public void update(float delta) {
        if (position.y < -13) {
            position.y = -13;
            velocity.y = 0;
        }

        velocity.add(acceleration.cpy().scl(delta));
        boundingCircle.set(position.x + 9, position.y + 6, 6.5f);
        if (velocity.y > 200) {
            velocity.y = 200;
        }

        position.add(velocity.cpy().scl(delta));

        if (velocity.y < 0) {

        }

        if (isFalling() || !isAlive()) {
            rotation += 280 * delta;
            if (!isAlive()) {
                if (rotation > 90) {
                    rotation = 90;
                }
            }else{
                if (rotation > 20) {
                    rotation = 20;
                }
            }
        } else {
            rotation -= 380 * delta;
            if (rotation < -20) {
                rotation = -20;
            }
        }
    }

    /**
     * Comportamiento durante un click si esta vivo
     */
    public void onClick() {
        if (isAlive()) {
            if (Assetloader.prefs.getBoolean(Assetloader.SOUND)) {
                Assetloader.flap.play();
            }
            velocity.y = -100;
        }
    }

    /**
     * Devuelve la posicion actual X
     *
     * @return Posicion X
     */
    public float getX() {
        return position.x;
    }

    /**
     * Devuelve la posicion actual Y
     *
     * @return Posicion Y
     */
    public float getY() {
        return position.y;
    }

    /**
     * Devuelve el ancho actual
     *
     * @return Ancho en pixeles
     */
    public float getWidth() {
        return width;
    }

    /**
     * Devuelve el alto actual
     *
     * @return Alto actual
     */
    public float getHeight() {
        return height;
    }

    /**
     * Devuelve el angulo actual
     *
     * @return Angulo actual
     */
    public float getRotation() {
        return rotation;
    }

    /**
     * Muestra si esta cayendo
     *
     * @return Esta cayendo
     */
    public boolean isFalling() {
        return velocity.y > 20;
    }

    /**
     * Muestra si debe aletear
     *
     * @return Debe aletear
     */
    public boolean shouldntFlap() {
        return (velocity.y > 70 || !isAlive());
    }

    /**
     * Devuelve el circulo que rodea el pajaro
     *
     * @return Circulo que rodea el pajaro
     */
    public Circle getBoundingCircle() {
        return boundingCircle;
    }

    /**
     * Devuelve si esta vivo
     *
     * @return Esta vivo
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Mata el pajaro actual
     */
    public void die() {
        alive = false;
        velocity.y = 0;
    }

    /**
     * Para el movimiento del pajaro
     */
    public void decelerate() {
        acceleration.y = 0;
    }

    /**
     * Reinicia el estado del pajaro y establece la altura donde debe aparecer
     *
     * @param y Altura donde debe aparecer
     */
    public void onRestart(int y) {
        position.y = y;
        velocity.y = 0;
        velocity.x = 0;
        acceleration.x = 0;
        acceleration.y = 460;

        rotation = 0;
        alive = true;
    }
}
