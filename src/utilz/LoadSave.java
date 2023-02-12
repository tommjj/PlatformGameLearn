package utilz;

import entities.Crabby;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
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
    public static final String COMPLETED_IMG = "Image/completed_sprite.png";

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
    
    public static BufferedImage[] GetAllLvls() {
        URL url = LoadSave.class.getResource("/Image/lvls");
        File file = null;
        
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException ex) {
            
        }
        
        File[] files = file.listFiles();
        File[] fileSorted = new File[files.length];
        
        for (int i = 0; i < fileSorted.length; i++) {
            for (int j = 0; j < fileSorted.length; j++) {
                if(files[j].getName().equals((i + 1) + ".png")) {
                    fileSorted[i] = files[j];
                }
            }
        }
        
        BufferedImage[] imgs = new BufferedImage[fileSorted.length];
        
        for (int i = 0; i < imgs.length; i++) {
            try {
                imgs[i] = ImageIO.read(fileSorted[i]);
            } catch (IOException ex) {
               
            }
        }
        
        return imgs;
    }
}
