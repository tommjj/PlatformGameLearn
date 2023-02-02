package gamesatates;

import entities.EnemyManager;
import entities.Player;
import static gamesatates.Gamestate.MENU;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import levels.LevelManager;
import main.Game;
import static main.Game.*;
import ui.GameOverOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;
import static utilz.Constants.Enviroment.*;

public class Playing extends State implements Statemethods {

    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private GameOverOverlay gameOverOverlay;

    private PauseOverlay pauseOverlay;
    private boolean paused = false;

    private boolean gameOver = false;

    //camara
    private int xLvlOffset = 0;
    private int leftBorder = (int) (0.4 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.6 * Game.GAME_WIDTH);
    private int lvlTilesWide = LoadSave.getLevelData().length;
    private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
    private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;

    //bg
    private BufferedImage backgroundImg, bigCloud, smallCloud;
    private int[] smallCloudsPos;
    private Random rnd = new Random();

    public Playing(Game game) {
        super(game);

        initClasses();

        loadBackground();
    }

    private void loadBackground() {
        backgroundImg = LoadSave.getSpriteAtlas(LoadSave.PLAYING_BACKGROUND_IMG);
        bigCloud = LoadSave.getSpriteAtlas(LoadSave.BIG_CLOUDS);
        smallCloud = LoadSave.getSpriteAtlas(LoadSave.SMALL_CLOUDS);
        smallCloudsPos = new int[8];
        for (int i = 0; i < smallCloudsPos.length; i++) {
            smallCloudsPos[i] = (int) (90 * Game.SCALE) + rnd.nextInt((int) (100 * Game.SCALE));
        }
    }

    public void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        player = new Player(200, 200, (int) (64 * SCALE), (int) (40 * SCALE), this);
        player.loadLvlData(levelManager.getCrrentLevel().getLvlData());

        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
    }

    public Player getPlayer() {
        return this.player;
    }

    public void windowFocusLost() {
        player.resetDirBoolean();

    }

    @Override
    public void update() {
        if (paused ) {
            pauseOverlay.update();
        } else if(!gameOver){
            levelManager.update();
            player.update();
            enemyManager.update(levelManager.getCrrentLevel().getLvlData(), player);
            checkCloseToBorder();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder) {
            xLvlOffset += diff - rightBorder;
        } else if (diff < leftBorder) {
            xLvlOffset += diff - leftBorder;
        }

        if (xLvlOffset > maxLvlOffsetX) {
            xLvlOffset = maxLvlOffsetX;
        } else if (xLvlOffset < 0) {
            xLvlOffset = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);

        drawClouds(g);

        levelManager.draw(g, xLvlOffset);
        //levelManager.drawGrid(g, xLvlOffset, new Color(255, 0, 0, 55));
        enemyManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (gameOver) {
            gameOverOverlay.draw(g);
        }
    }

    private void drawClouds(Graphics g) {
        for (int i = 0; i < 3; i++) {
            g.drawImage(bigCloud, (int) (i * BIG_CLOUD_WIDTH - (xLvlOffset * 0.3)), (int) (204 * Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
        }
        for (int i = 0; i < smallCloudsPos.length; i++) {
            g.drawImage(smallCloud, (int) (SMALL_CLOUD_WIDTH * 4 * i - (xLvlOffset * 0.7)), smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
        }

    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    public void resetAll() {
        //TODO: reset lbl
        gameOver = false;
        paused = false;
        player.resetAll();
        enemyManager.resetAll();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                player.setAttacking(true);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseOverlay.mousePressed(e);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseOverlay.mouseReleased(e);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseOverlay.mouseMoved(e);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    break;
                case KeyEvent.VK_A:
                    player.setLeft(true);

                    break;
                case KeyEvent.VK_S:

                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    Gamestate.setState(MENU);
                    player.resetDirBoolean();
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
                    break;
                default:
                    break;
            }
        } else {
            gameOverOverlay.keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    break;
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_S:
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseOverlay.mouseDragged(e);
            }
        }
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

}
