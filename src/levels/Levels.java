package levels;

public class Levels {
    private int[][] lvlData;

    public Levels() {
    }

    public int[][] getLvlData() {
        return lvlData;
    }
       

    public Levels(int[][] lvlData) {
        this.lvlData = lvlData;
    }
    
    public int getSpriteIndex(int x, int y) {
        return lvlData[x][y];
    }
}
