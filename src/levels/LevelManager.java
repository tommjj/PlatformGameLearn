package levels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import utilz.LoadSave;
import static main.Game.*;

public class LevelManager {

    private BufferedImage[] levelSprite;
    private Levels levelOne;

    public LevelManager(Game game) {
        importOutsideSprite();
        levelOne = new Levels(LoadSave.getLevelData());

    }

    public Levels getCrrentLevel() {
        return levelOne;
    }     

    private void importOutsideSprite() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                int index = i*12 + j;
                levelSprite[index] = img.getSubimage(j*32, i*32, 32, 32);
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        for (int i = 0; i < levelOne.getLvlData().length; i++) {
            for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
                int index = levelOne.getSpriteIndex(i, j);
                
                g.drawImage(levelSprite[index],(TILES_SIZE * i) - xLvlOffset, TILES_SIZE * j, TILES_SIZE, TILES_SIZE, null);
            }
        }            
    }
    
    public void drawGrid(Graphics g, int xLvlOffset, Color color) {
        for (int i = 0; i < levelOne.getLvlData().length; i++) {
            for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {     
                g.setColor(color);
                g.drawRect((TILES_SIZE * i) - xLvlOffset, TILES_SIZE * j, TILES_SIZE, TILES_SIZE);
            }
        }
    }

    public void update() {

    }

}
