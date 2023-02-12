package levels;

import gamesatates.Gamestate;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.Game;
import utilz.LoadSave;
import static main.Game.*;

public class LevelManager {
    
    private Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Levels> levels;
    private int levelIndex = 0;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprite();
        levels = new ArrayList<>();
        buildAllLevels();

    }
    
    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLvls();
        for (BufferedImage img : allLevels) {
            levels.add(new Levels(img));
        }
    }

    public Levels getCrrentLevel() {
        return levels.get(levelIndex);
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
        for (int i = 0; i < levels.get(levelIndex).getLvlData().length; i++) {
            for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
                int index = levels.get(levelIndex).getSpriteIndex(i, j);
                
                g.drawImage(levelSprite[index],(TILES_SIZE * i) - xLvlOffset, TILES_SIZE * j, TILES_SIZE, TILES_SIZE, null);
            }
        }            
    }
    
    public void drawGrid(Graphics g, int xLvlOffset, Color color) {
        for (int i = 0; i < levels.get(levelIndex).getLvlData().length; i++) {
            for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {     
                g.setColor(color);
                g.drawRect((TILES_SIZE * i) - xLvlOffset, TILES_SIZE * j, TILES_SIZE, TILES_SIZE);
            }
        }
    }

    public void update() {

    }

    public int getAmountOfLevels() {
        return  levels.size();
    }
    
    public void loadNextLevel() {
        levelIndex++;
        if(levelIndex >= levels.size()) {
            levelIndex = 0;
            System.out.println("No more levels!");
            Gamestate.state = Gamestate.MENU;
        }
        
        Levels newLevel = levels.get(levelIndex);
        
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLvlData());
        game.getPlaying().setMaxLvlOffsetX(newLevel.getMaxLvlOffsetX());
    }
}
