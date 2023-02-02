package utilz;

import entities.Crabby;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.Game;
import main.GamePanel;
import static utilz.Constants.EnemyConstants.*;

public class LoadSave {

    public static final String PLAYER_ATLAS = "Image/player_sprites.png";
    public static final String LEVEL_ATLAS = "Image/outside_sprites.png";
    public static final String LEVEL_ONE_DATA = "Image/level_one_data_long_enemy.png";
    public static final String MENU_BUTTONS = "Image/button_atlas.png";
    public static final String MENU_BACKGROUND = "Image/menu_background.png";
    public static final String PAUSE_BACKGROUND = "Image/pause_menu.png";
    public static final String SOUND_BUTTONS = "Image/sound_button.png";
    public static final String URM_BUTTONS = "Image/urm_buttons.png";
    public static final String VOLUME_BUTTONS = "Image/volume_buttons.png";
    public static final String MENU_BACKGROUND_IMG = "Image/background_menu.png";
    public static final String PLAYING_BACKGROUND_IMG = "Image/playing_bg_img.png";
    public static final String BIG_CLOUDS = "Image/big_clouds.png";
    public static final String SMALL_CLOUDS = "Image/small_clouds.png";
    public static final String CRABBY_SPRITE = "Image/crabby_sprite.png";
    public static final String STATUS_BAR = "Image/health_power_bar.png";

    public static BufferedImage getSpriteAtlas(String path) {
        InputStream is = LoadSave.class.getResourceAsStream("/" + path);
        BufferedImage img = null;
        try {
            img = ImageIO.read(is);
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return img;
    }
    
    public static ArrayList<Crabby> GetCrabs() {
        BufferedImage img = getSpriteAtlas(LEVEL_ONE_DATA);
        ArrayList<Crabby> list = new ArrayList<>();
        
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                int value = color.getGreen();
                if (value == CRABBY) {
                    list.add(new Crabby(j * Game.TILES_SIZE, i * Game.TILES_SIZE));
                }
                
            }
        }
        return list;
    }

    public static int[][] getLevelData() {

        BufferedImage img = getSpriteAtlas(LEVEL_ONE_DATA);
        int[][] lvlData = new int[img.getWidth()][img.getHeight()];
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                int value = color.getRed();
                if (value >= 48) {
                    value = 0;
                }
                lvlData[j][i] = value;
            }
        }
        return lvlData;
    }

}
