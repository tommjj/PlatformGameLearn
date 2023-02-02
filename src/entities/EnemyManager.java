package entities;

import gamesatates.Playing;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import utilz.LoadSave;
import static utilz.LoadSave.getSpriteAtlas;
import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] crabby;
    private ArrayList<Crabby> crabbies = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImg();
        addEnemy();
    }

    private void addEnemy() {
        crabbies = LoadSave.GetCrabs();
    }

    public void update(int[][] lvlData, Player player) {
        for (Crabby c : crabbies) {
            if (c.isActive()) {
                c.update(lvlData, player);
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawCrabs(g, xLvlOffset);
    }

    private void drawCrabs(Graphics g, int xLvlOffset) {
        for (Crabby c : crabbies) {
            if (c.isActive()) {
                g.drawImage(crabby[c.getEnemyState()][c.getAniIndex()], (int) (c.getHitbox().x) - xLvlOffset - CRABBY_DRAWOFFSET_X + c.flipX(), (int) (c.getHitbox().y) - CRABBY_DRAWOFFSET_Y, (CRABBY_WIDTH * c.flipW()), CRABBY_HEIGHT, null);
                c.drawHitbox(g, xLvlOffset);
                c.drawAttackBox(g, xLvlOffset);
            }
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Crabby c : crabbies) {
            if (attackBox.intersects(c.getHitbox())) {
                c.hurt(10);
                return;
            }
        }
    }

    private void loadEnemyImg() {
        crabby = new BufferedImage[5][9];
        BufferedImage temp = getSpriteAtlas(LoadSave.CRABBY_SPRITE);
        for (int i = 0; i < crabby.length; i++) {
            for (int j = 0; j < crabby[i].length; j++) {
                crabby[i][j] = temp.getSubimage(j * CRABBY_WIDTH_DEFAULT, i * CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
            }
        }
    }

    public void resetAll() {
        for(Crabby c: crabbies) {
            c.resetEnemy();
        }
    }
}
