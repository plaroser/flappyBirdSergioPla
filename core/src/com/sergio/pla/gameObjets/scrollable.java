package com.sergio.pla.gameObjets;

import com.badlogic.gdx.math.Vector2;

/**
 * Clase para hacer objetos continuos
 *
 * @author Sergio Pla
 */
public class scrollable {
    protected Vector2 position;
    protected Vector2 velocity;
    protected int width;
    protected int height;
    protected boolean isScrolledLeft;

    /**
     * Construye  un objeto continuo
     *
     * @param x           Posicion inicial X
     * @param y           Posicion inicial Y
     * @param width       Anchura del objeto
     * @param height      Altura del objeto
     * @param scrollSpeed Velocidad de desplazamiento
     */
    public scrollable(float x, float y, int width, int height, float scrollSpeed) {
        position = new Vector2(x, y);
        velocity = new Vector2(scrollSpeed, 0);
        this.width = width;
        this.height = height;
        isScrolledLeft = false;
    }

    /**
     * Actualiza el estado del objeto
     *
     * @param delta Nueva velocidad
     */
    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));

        // Si el objeto desplazable ya no está visible
        if (position.x + width < 0) {
            isScrolledLeft = true;
        }
    }

    // Reset: Debe anular en la subclase para un comportamiento más específico.

    /**
     * Resetea el objeto y establece una nueva posicion sobre el objeto
     *
     * @param newX Nueva posicion X
     */
    public void reset(float newX) {
        position.x = newX;
        isScrolledLeft = false;
    }

    // Getters for instance variables

    /**
     * Devuelve si el objeto es continuo hacia la izquierda
     *
     * @return Es continuo hacia la izquierda
     */
    public boolean isScrolledLeft() {
        return isScrolledLeft;
    }

    /**
     * Devuelve el final del objeto en el eje X
     *
     * @return Posicion final del objeto en el eje X
     */
    public float getTailX() {
        return position.x + width;
    }

    /**
     * Devuelve la posicion en el eje X
     *
     * @return Posicion en el eje X
     */
    public float getX() {
        return position.x;
    }

    /**
     * Devuelve la posicion en el eje Y
     *
     * @return Posicion en el eje Y
     */
    public float getY() {
        return position.y;
    }

    /**
     * Devuelve la anchura del objeto
     *
     * @return Anchura del objeto
     */
    public int getWidth() {
        return width;
    }

    /**
     * Devuelve la altura del objeto
     *
     * @return Altura del objeto
     */
    public int getHeight() {
        return height;
    }

    /**
     * Para el movimiento del objeto
     */
    public void stop() {
        velocity.x = 0;
    }
}


