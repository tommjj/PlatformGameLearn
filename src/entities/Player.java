package entities;

import gamesatates.Playing;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import main.Game;
import static utilz.Constants.PlayerConstats.*;
import utilz.LoadSave;
import static utilz.HelpMethods.*;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private int aniTick = 0, aniIndex = 0, aniSqeed = 25;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false, doubleJump = false;
    private boolean left, right, jump;
    private float playerSpeed = 1.5f * Game.SCALE;

    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 4 * Game.SCALE;

    //jumping and Gravity
    private float airSpeed = 0.0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.4f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    //
    private int flipX = 0;
    private int flipW = 1;

    private int[][] lvlData;

    //StatusBarUI
    private BufferedImage statusbarImg;

    private int statusBarWidth = (int) (192 * Game.SCALE);
    private int statusBarHeight = (int) (58 * Game.SCALE);
    private int statusBarX = (int) (10 * Game.SCALE);
    private int statusBarY = (int) (10 * Game.SCALE);

    private int healthBarWidth = (int) (150 * Game.SCALE);
    private int healthBarHeight = (int) (4 * Game.SCALE);
    private int healthBarXStart = (int) (34 * Game.SCALE);
    private int healthBarYStart = (int) (14 * Game.SCALE);

    private int maxHealth = 100;
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;

    //AttackBox
    private Rectangle2D.Float attackBox;
    private boolean attackChecked;

    private Playing playing;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        loadAnimation();
        initHitbox(x, y, (int) (20 * Game.SCALE), (int) (27 * Game.SCALE));
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
    }

    public void update() {
        updateHealthBar();

        if (currentHealth <= 0) {
            playing.setGameOver(true);
            return;
        }

        updateAttackBox();
        if (attacking) {
            checkAttack();
        }
        updatePos();

        updateAminationTick();
        setAminations();

    }

    private void checkAttack() {
        if (attackChecked || aniIndex != 1) {
            return;
        }
        attackChecked = true;
        playing.checkEnemyHit(attackBox);
    }

    private void updateAttackBox() {
        if (right && !left) {
            attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 10);
        } else if (left && !right) {
            attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 10);
        }
        attackBox.y = hitbox.y + (int) (Game.SCALE * 10);
    }

    private void drawAttackBox(Graphics g, int xLvlOffset) {
        g.drawRect((int) attackBox.x - xLvlOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    public void render(Graphics g, int xLvlOffset) {
        drawHitbox(g, xLvlOffset);
        g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset) - xLvlOffset + flipX, (int) (hitbox.y - yDrawOffset), width * flipW, height, null);
        drawUI(g);
        drawAttackBox(g, xLvlOffset);
    }

    private void drawUI(Graphics g) {
        g.drawImage(statusbarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.red);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
    }

    private void updateHealthBar() {
        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
    }

    private void loadAnimation() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[7][8];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(64 * j, 40 * i, 64, 40);
            }
        }
        statusbarImg = LoadSave.getSpriteAtlas(LoadSave.STATUS_BAR);
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!IsEntityOnfloor(hitbox, lvlData)) {
            inAir = true;
        }
    }

    private void updateAminationTick() {
        aniTick++;
        if (aniTick >= aniSqeed) {
            aniIndex++;
            if (aniIndex >= GetSpritesAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
            }
            aniTick = 0;
        }
    }

    private void setAminations() {
        int startAni = playerAction;

        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if (inAir) {
            if (airSpeed > 0) {
                playerAction = FALLING;
            } else {
                playerAction = JUMP;
            }
        }
        //Attacking

        if (attacking) {
            playerAction = ATTACK;
            if (startAni != ATTACK) {
                aniIndex = 1;
                aniTick = 0;
                return;
            }
        }

        //resetAni
        if (startAni != playerAction) {
            aniTick = 0;
            aniIndex = 0;
        }
    }

    private void updatePos() {
        moving = false;

        if (jump) {
            jump();
        }
        if (!inAir) {
            if ((!right && !left) || (right && left)) {
                return;
            }
        }

        float xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
            flipX = width;
            flipW = -1;

        }
        if (right) {
            xSpeed += playerSpeed;
            flipX = 0;
            flipW = 1;
        }

        if (!inAir) {
            if (!IsEntityOnfloor(hitbox, lvlData)) {
                inAir = true;
            }
        }

        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0) {
                    resetInAir();
                } else {
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }
        } else {
            updateXPos(xSpeed);
        }
        moving = true;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
        doubleJump = false;
        //jump = false;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetEntityXposNextToWall(hitbox, xSpeed);
        }
    }

    public void changeHealth(int value) {
        currentHealth += value;

        if (currentHealth <= 0) {
            currentHealth = 0;
            //GameOver();
        } else if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
    }

    private void jump() {
        if (inAir && !doubleJump) {
            airSpeed = jumpSpeed;
            doubleJump = true;
        } else if (inAir) {
            jump = false;
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;
        jump = false;
    }

    public void resetDirBoolean() {
        setRight(false);
        setLeft(false);
    }

    public void resetAll() {
        resetDirBoolean();
        inAir = false;
        attacking = false;
        moving = false;
        playerAction = IDLE;
        currentHealth = maxHealth;
        
        hitbox.x = x;
        hitbox.y = y;
        
        if (!IsEntityOnfloor(hitbox, lvlData)) {
            inAir = true;
        }
        
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

}
