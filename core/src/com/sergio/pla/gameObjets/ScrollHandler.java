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
    private Pipe[] pipes;
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

        pipes = new Pipe[3];

        pipes[0] = new Pipe(210, 0, 22, 60, SCROLL_SPEED, yPos);
        pipes[1] = new Pipe(pipes[0].getTailX() + PIPE_GAP, 0, 22, 70, SCROLL_SPEED, yPos);
        pipes[2] = new Pipe(pipes[1].getTailX() + PIPE_GAP, 0, 22, 60, SCROLL_SPEED, yPos);

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

        for (Pipe pipe : pipes) {
            pipe.update(delta);
        }


        // Verifique si alguna de las tuberías se desplaza hacia la izquierda y reinicia en este caso
        if (pipes[0].isScrolledLeft()) {
            pipes[0].reset(pipes[2].getTailX() + PIPE_GAP);
        } else if (pipes[1].isScrolledLeft()) {
            pipes[1].reset(pipes[1].getTailX() + PIPE_GAP);

        } else if (pipes[2].isScrolledLeft()) {
            pipes[2].reset(pipes[1].getTailX() + PIPE_GAP);
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

    public int pipesLength(){
        return pipes.length;
    }

    public Pipe getPipe(int i) {
        if (i >= 0 && i < pipes.length) {
            return pipes[i];
        }
        return null;
    }


    /**
     * Para el juego completamente
     */
    public void stop() {
        frontGrass.stop();
        backGrass.stop();

        for (Pipe pipe : pipes) {
            pipe.stop();
        }
    }

    /**
     * Comprueba si el pajaro ha atravesado una tuberia, de ser asi puntua y ademas devuelve si ha colisionado con alguna tuberia
     *
     * @param bird Pajaro al que realizarle las comprobaciones
     * @return Si ha colisionado
     */
    public boolean collides(Bird bird) {
        for (Pipe pipe : pipes) {
            if (!pipe.isScored()
                    && pipe.getX() + (pipe.getWidth() / 2) < bird.getX()
                    + bird.getWidth()) {
                addScore(1);
                pipe.setScored(true);
                if (Assetloader.prefs.getBoolean(Assetloader.SOUND)) {
                    Assetloader.coin.play();
                }
            }
        }

        boolean collides = false;
        for (Pipe pipe : pipes) {
            collides = collides || pipe.collides(bird);
        }
        return collides;
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
        pipes[0].onRestart(210, SCROLL_SPEED);
        pipes[1].onRestart(pipes[0].getTailX() + PIPE_GAP, SCROLL_SPEED);
        pipes[2].onRestart(pipes[1].getTailX() + PIPE_GAP, SCROLL_SPEED);
    }
}
