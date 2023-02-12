package levels;

import entities.Crabby;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.Game;
import static utilz.HelpMethods.GetCrabs;
import static utilz.HelpMethods.GetPlayerSpawn;
import static utilz.HelpMethods.getLevelData;

public class Levels {

    private BufferedImage img;
    private int[][] lvlData;
    private ArrayList<Crabby> crabs;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;

    public Levels(BufferedImage img) {
        this.img = img;
        createLevelData();
        createEnemies();
        calsLvloffset();
        calsPlayerSpawn();
    }

    public int[][] getLvlData() {
        return lvlData;
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[x][y];
    }

    private void createLevelData() {
        lvlData = getLevelData(img);
    }

    private void createEnemies() {
        crabs = GetCrabs(img);
    }

    private void calsLvloffset() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;
    }

    public ArrayList<Crabby> getCrabs() {
        return crabs;
    }

    public int getMaxLvlOffsetX() {
        return maxLvlOffsetX;
    }

    private void calsPlayerSpawn() {
        playerSpawn = GetPlayerSpawn(img);
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }

}
