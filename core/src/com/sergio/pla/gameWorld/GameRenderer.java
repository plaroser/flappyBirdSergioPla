package com.sergio.pla.gameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sergio.pla.assetLoader.Assetloader;
import com.sergio.pla.gameObjets.Bird;
import com.sergio.pla.gameObjets.Grass;
import com.sergio.pla.gameObjets.Pipe;
import com.sergio.pla.gameObjets.ScrollHandler;

/**
 * Carga toda la ejecucion del juego para que se pueda hacer la magia de jugar
 *
 * @author Sergio Pla
 */
public class GameRenderer {
    private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher;

    private int midPointY;
    private int gameHeight;

    // SergioPlaGame Objects
    private Bird bird;
    private ScrollHandler scroller;
    private Grass frontGrass, backGrass;
    private Pipe[] pipes;

    // SergioPlaGame Assets
    private TextureRegion bg, grass;
    private Animation birdAnimation;
    private TextureRegion birdMid, birdDown, birdUp;
    private TextureRegion skullUp, skullDown, bar;

    /**
     * Crea una nueva instancia del renderer e inicializa todos sus objetos
     *
     * @param world      Mundo en el que renderizar todos los objetos
     * @param gameHeight Altura del juego
     * @param midPointY  Posicion Y en la que empezar el juego
     */
    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
        myWorld = world;

        this.gameHeight = gameHeight;
        this.midPointY = midPointY;

        cam = new OrthographicCamera();
        cam.setToOrtho(true, 136, gameHeight);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        // Métodos para inicializar variables de instancia
        initGameObjects();
        initAssets();
    }

    /**
     * Inicializa todos los objetos necesarios para inidiar el juego
     */
    private void initGameObjects() {
        bird = myWorld.getBird();
        scroller = myWorld.getScroller();
        frontGrass = scroller.getFrontGrass();
        backGrass = scroller.getBackGrass();
        pipes = new Pipe[scroller.pipesLength()];
        for (int i = 0;i<scroller.pipesLength();i++){
            pipes[i]=scroller.getPipe(i);
        }

    }

    /**
     * Carga todos los recursos necesarios para iniciar el juego
     */
    private void initAssets() {
        bg = Assetloader.bg;
        grass = Assetloader.grass;
        birdAnimation = Assetloader.birdAnimation;
        birdMid = Assetloader.bird;
        birdDown = Assetloader.birdDown;
        birdUp = Assetloader.birdUp;
        skullUp = Assetloader.skullUp;
        skullDown = Assetloader.skullDown;
        bar = Assetloader.bar;
    }

    /**
     * Dibuja el cesped y el fondo del juego
     */
    private void drawGrass() {
        batcher.draw(grass, frontGrass.getX(), frontGrass.getY(),
                frontGrass.getWidth(), frontGrass.getHeight());
        batcher.draw(grass, backGrass.getX(), backGrass.getY(),
                backGrass.getWidth(), backGrass.getHeight());
    }

    /**
     * Dibuja la punta de los tubos
     */
    private void drawSkulls() {
        for (Pipe pipe:pipes) {
            batcher.draw(skullUp, pipe.getX() - 1,
                    pipe.getY() + pipe.getHeight() - 14, 24, 14);
            batcher.draw(skullDown, pipe.getX() - 1,
                    pipe.getY() + pipe.getHeight() + 45, 24, 14);
        }

    }

    /**
     * Dibuja los tubos en la pantalla
     */
    private void drawPipes() {
        for (Pipe pipe:pipes) {
            batcher.draw(bar, pipe.getX(), pipe.getY(), pipe.getWidth(),
                    pipe.getHeight());
            batcher.draw(bar, pipe.getX(), pipe.getY() + pipe.getHeight() + 45,
                    pipe.getWidth(), midPointY + 66 - (pipe.getHeight() + 45));
        }

    }

    /**
     * Renderiza todo el juego en la pantalla
     *
     * @param runTime Tiempo de juego actual
     */
    public void render(float runTime) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Dibuja el fondo
        shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);

        // Dibuja el cesped
        shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 66, 136, 11);

        shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 52);

        shapeRenderer.end();

        batcher.begin();
        batcher.disableBlending();
        batcher.draw(bg, 0, midPointY + 23, 136, 43);

        // 1. Imprime la hierba
        drawGrass();

        // 2. Imprime los tubos
        drawPipes();
        batcher.enableBlending();

        // 3. Imprime la punta de los tubos
        drawSkulls();

        // Si el pajaro esta listo lo Imprime en el estado correspondiente
        if (bird.shouldntFlap()) {
            batcher.draw(birdMid, bird.getX(), bird.getY(),
                    bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                    bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
            //Sino lo imprime por defecro
        } else {
            batcher.draw((TextureRegion) birdAnimation.getKeyFrame(runTime), bird.getX(),
                    bird.getY(), bird.getWidth() / 2.0f,
                    bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(),
                    1, 1, bird.getRotation());
        }

        //Si el mundo esta preparado
        if (myWorld.isReady()) {
            // Imprime texto
            Assetloader.fontText
                    .draw(batcher, "Touch me", (136 / 2) - (42 - 1), 75);

            //Sino es que el juego se ha acabado y puede ser un HIGH_SCORE
        } else {

            if (myWorld.isGameOver() || myWorld.isHighScore()) {
                //Si el juego se ha acabado pero no ha superado la marca
                if (myWorld.isGameOver()) {
                    //Imprime "Game Over"
                    Assetloader.fontText.draw(batcher, "Game Over", 24, 55);
                    //Imprime la puntuacion maxima
                    Assetloader.fontText.draw(batcher, "High Score:", 22, 105);

                    String highScore = Assetloader.getHighScore() + "";

                    Assetloader.fontText.draw(batcher, highScore, (136 / 2)
                            - (3 * highScore.length() - 1), 127);
                } else {
                    Assetloader.fontText.draw(batcher, "High Score!", 18, 55);
                }

                Assetloader.fontText.draw(batcher, "Try again?", 24, 75);

                String score = myWorld.getScore() + "";

                Assetloader.fontText.draw(batcher, score,
                        (136 / 2) - (3 * score.length() - 1), 11);

            }
            String score = myWorld.getScore() + "";

            Assetloader.fontText.draw(batcher, "" + myWorld.getScore(), (136 / 2)
                    - (3 * score.length() - 1), 11);
        }
        batcher.end();
    }
}
