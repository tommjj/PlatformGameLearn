package utilz;

import java.awt.geom.Rectangle2D;
import main.Game;

public class HelpMethods {

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        if (!IsSolid(x, y, lvlData)) {
            if (!IsSolid(x + width, y + height, lvlData)) {
                if (!IsSolid(x + width, y, lvlData)) {
                    if (!IsSolid(x, y + height, lvlData)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //Kiểm tra toà đo đó có block nào ko
    public static boolean IsSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData.length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth) {
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }

        int xIndex = (int) (x / Game.TILES_SIZE);
        int yIndex = (int) (y / Game.TILES_SIZE);

        return IsTileSolid(xIndex, yIndex, lvlData);
    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[xTile][yTile];

        if (value >= 48 || value < 0 || value != 11) {
            return true;
        }
        return false;
    }

    public static float GetEntityXposNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int curentTile = (int) (hitbox.x / Game.TILES_SIZE);
        if (xSpeed > 0) { // right
            int tileXPos = curentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else { //left
            return curentTile * Game.TILES_SIZE;
        }
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int curentTile = (int) (hitbox.y / Game.TILES_SIZE);
        if (airSpeed > 0) { //fall
            int tileYPos = curentTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else { //jump
            return curentTile * Game.TILES_SIZE;
        }

    }

    //kiem tra player co dang dung tren sang khong
    public static boolean IsEntityOnfloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData)) {
            if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData)) {
                return false;
            }
        }
        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
        return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData) && IsSolid(hitbox.x + xSpeed + hitbox.width, hitbox.y + hitbox.height + 1, lvlData);
    }

    public static boolean IsAllTileWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, lvlData)) {
                return false;
            }
            if (!IsTileSolid(xStart + i, y + 1, lvlData)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox,
            Rectangle2D.Float secondHitbox, int tileY) {
        int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);

        if (firstXTile > secondXTile) {
            return IsAllTileWalkable(secondXTile, firstXTile, tileY , lvlData);
        } else {
            return IsAllTileWalkable(firstXTile, secondXTile, tileY , lvlData);
        }
    }
}
