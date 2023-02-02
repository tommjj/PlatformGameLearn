package ui;

import java.awt.Rectangle;

public class PauseButton {
    protected int x, y, width, height;
    protected Rectangle bounds;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWhidth(int whidth) {
        this.width = whidth;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWhidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    
    
    public PauseButton(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        initBounds();
        
    }
    
    private void initBounds() {
        bounds = new Rectangle(x, y, width, height);
    }
}
