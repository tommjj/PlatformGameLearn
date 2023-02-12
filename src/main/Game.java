package main;

import gamesatates.Gamestate;
import gamesatates.Menu;
import gamesatates.Playing;
import java.awt.Graphics;
import javafx.scene.layout.Background;
import utilz.LoadSave;

public class Game implements Runnable {

    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120, UPS_SET = 200;

    private Playing playing;
    private Menu menu;
    

    //final size
    public static final int TILES_DEFAULT_SIZE = 32;//Kích thước của một block
    public static final float SCALE = 1.75f; //Tỉ Lệ kích thước của game
    public static final int TILES_IN_WIDTH = 26;
    public static final int TILES_IN_HEIGHT = 14;
    public static final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public static final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public static final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
    
   

    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        new GameWindow(gamePanel);
        
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        startGameLoop();       
    }

    public void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
    }

    public Playing getPlaying() {
        return playing;
    }

    public Menu getMenu() {
        return menu;
    }

    

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        
        switch (Gamestate.state) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case OPITONS:
                break;
            case QUIT:
                System.exit(0);
                break;
            default:
                break;
        }
    }

    public void rander(Graphics g) {

        switch (Gamestate.state) {
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            default:
                break;
        }

    }

    public void windowFocusLost() {
        if (Gamestate.state == Gamestate.PLAYING) {
            playing.getPlayer().resetDirBoolean();
        }
    }

    @Override
    public void run() {
        double timePerFreme = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;
        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFreme;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
}
