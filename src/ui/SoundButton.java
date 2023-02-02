package ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import utilz.LoadSave;
import static utilz.LoadSave.getSpriteAtlas;
import static utilz.Constants.UI.PauseButtons.*;

public class SoundButton extends PauseButton {

    private BufferedImage[][] soundImg;
    private boolean mouseOver, mousePressed;
    private boolean muted;
    private int rowIndex, colIndex;

    public SoundButton(int x, int y, int whidth, int height) {
        super(x, y, whidth, height);
        muted = false;
        loadSoundImg();
    }

    private void loadSoundImg() {
        BufferedImage temp = getSpriteAtlas(LoadSave.SOUND_BUTTONS);
        soundImg = new BufferedImage[2][3];
        for (int i = 0; i < soundImg.length; i++) {
            for (int j = 0; j < soundImg[i].length; j++) {
                soundImg[i][j] = temp.getSubimage(j * SOUND_SIZE_DEFAULT, i * SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
            }
        }

    }
    

    public void update() {
        if (muted) {
            rowIndex = 1;
        } else {
            rowIndex = 0;
        }
        colIndex = 0; 
        if(mouseOver) {
            colIndex = 1; 
        } 
        if(mousePressed) {
            colIndex = 2;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(soundImg[rowIndex][colIndex], x, y, width, height, null);

    }

    public void resetBools() {
        //mouseOver = false;
        mousePressed = false;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public boolean isMuted() {
        return muted;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWhidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
    
}
