package com.sergio.pla.gameObjets;

import com.sergio.pla.assetLoader.Assetloader;
import com.sergio.pla.gameWorld.GameWorld;

/**
 * Control de desplazamiento
 * <p>
 * Controla el desplazamiento de los objetos en el juego
 *
 * @author Sergio Pla
 */
public class ScrollHandler {
    private Grass frontGrass, backGrass;
    private Pipe pipe1, pipe2, pipe3;
    public static final int SCROLL_SPEED = -59;
    public static final int PIPE_GAP = 49;
    private GameWorld gameWorld;

    /**
     * Crea un controlador de movimiento y establece la altura del mundo
     *
     * @param gameWorld Mundo del juego para establecerle los objetos
     * @param yPos      Altura a la que iniciar el juego
     */
    public ScrollHandler(GameWorld gameWorld, float yPos) {
        this.gameWorld = gameWorld;
        frontGrass = new Grass(0, yPos, 143, 11, SCROLL_SPEED);
        backGrass = new Grass(frontGrass.getTailX(), yPos, 143, 11,
                SCROLL_SPEED);

        pipe1 = new Pipe(210, 0, 22, 60, SCROLL_SPEED, yPos);
        pipe2 = new Pipe(pipe1.getTailX() + PIPE_GAP, 0, 22, 70, SCROLL_SPEED, yPos);
        pipe3 = new Pipe(pipe2.getTailX() + PIPE_GAP, 0, 22, 60, SCROLL_SPEED, yPos);
    }

    /**
     * Actualiza todos los objetos en el controlador para gestionar el movimiento durante el render
     *
     * @param delta Velocidad a la que establecer el movimiento
     */
    public void update(float delta) {
        // Actualiza todos los objetos
        frontGrass.update(delta);
        backGrass.update(delta);
        pipe1.update(delta);
        pipe2.update(delta);
        pipe3.update(delta);

        // Verifique si alguna de las tuberías se desplaza hacia la izquierda y reinicia en este caso
        if (pipe1.isScrolledLeft()) {
            pipe1.reset(pipe3.getTailX() + PIPE_GAP);
        } else if (pipe2.isScrolledLeft()) {
            pipe2.reset(pipe1.getTailX() + PIPE_GAP);

        } else if (pipe3.isScrolledLeft()) {
            pipe3.reset(pipe2.getTailX() + PIPE_GAP);
        }

        // Lo mismo con la hierba
        if (frontGrass.isScrolledLeft()) {
            frontGrass.reset(backGrass.getTailX());

        } else if (backGrass.isScrolledLeft()) {
            backGrass.reset(frontGrass.getTailX());
        }
    }

    /**
     * Devuelve la hierba de alante
     *
     * @return Hierba de alante
     */
    public Grass getFrontGrass() {
        return frontGrass;
    }

    /**
     * Devuelve la hierba de fondo
     *
     * @return Hierba de fondo
     */
    public Grass getBackGrass() {
        return backGrass;
    }

    /**
     * Debuelve el tubo1
     *
     * @return Tubo1
     */
    public Pipe getPipe1() {
        return pipe1;
    }

    /**
     * Devuelve el tubo2
     *
     * @return Tubo2
     */
    public Pipe getPipe2() {
        return pipe2;
    }

    /**
     * Devuelve el tubo3
     *
     * @return Tubo3
     */
    public Pipe getPipe3() {
        return pipe3;
    }

    /**
     * Para el juego completamente
     */
    public void stop() {
        frontGrass.stop();
        backGrass.stop();
        pipe1.stop();
        pipe2.stop();
        pipe3.stop();
    }

    /**
     * Comprueba si el pajaro ha atravesado una tuberia, de ser asi puntua y ademas devuelve si ha colisionado con alguna tuberia
     *
     * @param bird Pajaro al que realizarle las comprobaciones
     * @return Si ha colisionado
     */
    public boolean collides(Bird bird) {
        if (!pipe1.isScored()
                && pipe1.getX() + (pipe1.getWidth() / 2) < bird.getX()
                + bird.getWidth()) {
            addScore(1);
            pipe1.setScored(true);
            Assetloader.coin.play();
        } else if (!pipe2.isScored()
                && pipe2.getX() + (pipe2.getWidth() / 2) < bird.getX()
                + bird.getWidth()) {
            addScore(1);
            pipe2.setScored(true);
            Assetloader.coin.play();

        } else if (!pipe3.isScored()
                && pipe3.getX() + (pipe3.getWidth() / 2) < bird.getX()
                + bird.getWidth()) {
            addScore(1);
            pipe3.setScored(true);
            Assetloader.coin.play();

        }
        return (pipe1.collides(bird) || pipe2.collides(bird) || pipe3.collides(bird));
    }

    /**
     * Incrementa la puntuacion obtenida en el incremento incicado
     *
     * @param increment Incremento para puntuar
     */
    private void addScore(int increment) {
        gameWorld.addScore(increment);
    }

    /**
     * Reinicia todo el controlador con todos sus objetos para poder jugar otra vez
     */
    public void onRestart() {
        frontGrass.onRestart(0, SCROLL_SPEED);
        backGrass.onRestart(frontGrass.getTailX(), SCROLL_SPEED);
        pipe1.onRestart(210, SCROLL_SPEED);
        pipe2.onRestart(pipe1.getTailX() + PIPE_GAP, SCROLL_SPEED);
        pipe3.onRestart(pipe2.getTailX() + PIPE_GAP, SCROLL_SPEED);
    }
}
