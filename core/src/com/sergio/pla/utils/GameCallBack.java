package com.sergio.pla.utils;

/**
 * Created by Sergio Pla on 3/8/2018.
 *
 * Nos ayuda a interactuar entre LibGDX y las demas plataformas
 */

public interface GameCallBack {
    /**
     * Metodo para volver a la plataforma inicial desde LibGDX
     */
    void callBack();

    /**
     * Metodo para comprobar si el sonido esta activado
     * @return Si el sonido esta activado
     */
    boolean isSound();

    /**
     * Metodo para guardar una puntuacion máxima
     * @param score Puntuacion máxima
     */
    void setHighScore(int score);

    /**
     * Metodo para leer la puntuacion máxima
     * @return Puntuacion máxima
     */
    int getHighScore();
}
