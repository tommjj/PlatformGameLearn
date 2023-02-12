package ui;

import gamesatates.Gamestate;
import gamesatates.Playing;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import main.Game;
import static utilz.Constants.UI.UrmButtons.URM_SIZE;
import utilz.LoadSave;

public class LevelCompletedOverlay {
    private Playing playing;
    private UrmButton menu, next;
    private BufferedImage img;
    private int bgX, bgY, bgW, bgH;
    
    public LevelCompletedOverlay(Playing playing) {
        this.playing = playing;
        initImg();
        initButtons();
    }
    
    private void initImg() {
        img = LoadSave.getSpriteAtlas(LoadSave.COMPLETED_IMG);
       
        bgW = (int) (img.getWidth() * Game.SCALE);
        bgH = (int) (img.getHeight() * Game.SCALE);
        bgX =  Game.GAME_WIDTH/2 - bgW/2;
        bgY = (int) (75 * Game.SCALE);
    }

    private void initButtons() {
        int menuX = (int) (330 * Game.SCALE);
        int nextX = (int) (445 * Game.SCALE);
        int y = (int) (195 * Game.SCALE);
        menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
        next = new UrmButton(nextX, y, URM_SIZE, URM_SIZE, 0);
    }
    
    public void updete() {
        menu.update();
        next.update();
    }
    
    public void draw(Graphics g) {
        g.drawImage(img, bgX, bgY, bgW, bgH, null);
        menu.draw(g);
        next.draw(g);
    }
    
     public boolean isIn(MouseEvent e, PauseButton mb) {
        return mb.getBounds().contains(e.getX(), e.getY());
    }
    
    public void mouseMoved(MouseEvent e) {
        menu.setMouseOver(false);
        next.setMouseOver(false);
        
        if (isIn(e, menu)) {
            menu.setMouseOver(true);
        } else if (isIn(e, next)) {
            next.setMouseOver(true);
        }
    }
    
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menu)) {
            if (menu.isMousePressed()) {
                playing.resetAll();
                Gamestate.state = Gamestate.MENU;
            }
        } else if (isIn(e, next)) {
            if (next.isMousePressed()) {
               playing.loadNextLevel();
            }
        }
        
        menu.resetBools();
        next.resetBools();
    }
    
    public void mousePressed(MouseEvent e) {
        if (isIn(e, menu)) {
            menu.setMousePressed(true);
        } else if (isIn(e, next)) {
            next.setMousePressed(true);
        }
    }
}
