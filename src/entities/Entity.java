package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import main.Game;

public abstract class Entity {

    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
  

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
    }

    protected void initHitbox(float x, float y, int width, int height) {
        hitbox = new Rectangle2D.Float( x, y, width, height);
    }

//    protected void updateHitbox() {
//        hitbox.x = (int) x;
//        hitbox.y = (int) y;
//    }
    
    protected void drawHitbox(Graphics g , int xLvlOffset) {
        //test
        g.setColor(Color.red);
        g.drawRect((int)hitbox.x - xLvlOffset,(int) hitbox.y, (int)hitbox.width, (int) hitbox.height);
        g.drawLine((int)hitbox.x - xLvlOffset,(int) hitbox.y, (int)hitbox.x - xLvlOffset, (int) (hitbox.y - 100* Game.SCALE));
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }
    
    
}
