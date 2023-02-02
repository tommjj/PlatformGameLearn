package ui;

import gamesatates.Gamestate;
import gamesatates.Playing;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import main.Game;
import static utilz.Constants.UI.PauseButtons.SOUND_SIZE;
import static utilz.Constants.UI.UrmButtons.URM_SIZE;
import utilz.LoadSave;
import static utilz.LoadSave.getSpriteAtlas;
import static utilz.Constants.UI.VolumeButtons.*;

public class PauseOverlay {
    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    private SoundButton musicButton, sfxButton;
    private UrmButton menuB, replayB, unpause;
    private VolumeButton volumeButton;
    
    
    public PauseOverlay(Playing playing) {
        this.playing = playing;
        
        loadBackground();
        createSoundButtons();
        createUrmButtons();
        createVolumeButton();
    }
    
    private void createVolumeButton() {
        int vX = (int) (309* Game.SCALE);
        int vY = (int) (278* Game.SCALE);
        volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }
    
    private void createUrmButtons() {
        int menuX = (int) (313 * Game.SCALE);
        int replayX = (int) (387 * Game.SCALE);
        int unpauseX = (int) (462 * Game.SCALE);
        int bY = (int) (325 * Game.SCALE);
        menuB = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
        replayB = new UrmButton(replayX, bY, URM_SIZE, URM_SIZE, 1);
        unpause = new UrmButton(unpauseX, bY, URM_SIZE, URM_SIZE, 0);
    }
    
    public void loadBackground() {
        backgroundImg = getSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
        bgW = (int) (backgroundImg.getWidth() * Game.SCALE);
        bgH = (int) (backgroundImg.getHeight() * Game.SCALE);
        bgX = (int) (Game.GAME_WIDTH / 2 - bgW / 2);
        bgY = (int) (25 * Game.SCALE);
    }
    
    private void createSoundButtons() {
        int soundX = (int) (450 * Game.SCALE);
        int soundY = (int) (140 * Game.SCALE);
        musicButton = new SoundButton(soundX, soundY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton(soundX, soundY + SOUND_SIZE, SOUND_SIZE, SOUND_SIZE);
    }
    
    public void update() {
        musicButton.update();
        sfxButton.update();
        
        menuB.update();
        replayB.update();
        unpause.update();
        
        volumeButton.update(); 
    }
    
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);
        
        musicButton.draw(g);
        sfxButton.draw(g);
        
        menuB.draw(g);
        replayB.draw(g);
        unpause.draw(g);
        
        volumeButton.draw(g);
    }
    
    public void mouseDragged(MouseEvent e) {
        if(volumeButton.isMousePressed()) {
            volumeButton.changeX(e.getX());
        }
    }
    
    public void mousePressed(MouseEvent e) {
        if (isIn(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        } else if (isIn(e, menuB)) {
            menuB.setMousePressed(true);
        } else if (isIn(e, replayB)) {
            replayB.setMousePressed(true);
        } else if (isIn(e, unpause)) {
            unpause.setMousePressed(true);
        } else if (isIn(e, volumeButton)) {
            volumeButton.setMousePressed(true);
        }        
    }
    
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, musicButton)) {
            if (musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
            }
        } else if (isIn(e, sfxButton)) {
            if (sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
            }
        } else if (isIn(e, menuB)) {
            if (menuB.isMousePressed()) {
                Gamestate.state = Gamestate.MENU;
                 playing.setPaused(false);
            }
        } else if (isIn(e, replayB)) {
            if (replayB.isMousePressed()) {
                System.out.println("Replay");
                playing.resetAll();
            }
        } else if (isIn(e, unpause)) {
            if (unpause.isMousePressed()) {
                playing.setPaused(false);
            }
        } 
        musicButton.resetBools();
        sfxButton.resetBools();
        menuB.resetBools();
        replayB.resetBools();
        unpause.resetBools();
        volumeButton.resetBools();
    }
    
    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpause.setMouseOver(false);
        volumeButton.setMouseOver(false);
        
        if (isIn(e, musicButton)) {
            musicButton.setMouseOver(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMouseOver(true);
        } else if (isIn(e, menuB)) {
            menuB.setMouseOver(true);
        } else if (isIn(e, replayB)) {
            replayB.setMouseOver(true);
        } else if (isIn(e, unpause)) {
            unpause.setMouseOver(true);
        } else if (isIn(e, volumeButton)) {
            volumeButton.setMouseOver(true);
        }
    }
    
    public boolean isIn(MouseEvent e, PauseButton mb) {
        return mb.getBounds().contains(e.getX(), e.getY());
    }
    
}
