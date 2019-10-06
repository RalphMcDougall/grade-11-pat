/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Ralph McDougall 13/10/2017
 */
public class ImageHandler {

    private static ArrayList<Image> allImages;

    private static Image TEXTURE_SHEET;

    public static void setupImageHandler(int TILE_SIZE) throws SlickException {
        GameLogger.logInfo("Starting to load ImageHandler");

        TEXTURE_SHEET = new Image("resources/textureSheet.png");
        allImages = new ArrayList<Image>();

        loadAllImages(TILE_SIZE);

        GameLogger.logInfo("ImageHandler loading completed");
    }

    private static void loadAllImages(int TILE_SIZE) {
        for (int y = 0; y < TEXTURE_SHEET.getHeight() / TILE_SIZE; ++y) {
            for (int x = 0; x < TEXTURE_SHEET.getWidth() / TILE_SIZE; ++x) {
                GameLogger.logInfo("Getting image at (" + x + ", " + y + ") on texture sheet");
                Image img = TEXTURE_SHEET.getSubImage(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                allImages.add(img);
            }
        }

    }

    public static Image getImage(int n) {
        if (n >= allImages.size()) {
            GameLogger.logWarning("Requested image index is out of range");
            return null;
        }
        return allImages.get(n);
    }

}
