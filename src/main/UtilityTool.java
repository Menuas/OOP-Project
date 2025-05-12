package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import exceptions.InvalidImageSizeException;

public class UtilityTool {
    public static final int MAP_TILE_SIZE = 48;

    public BufferedImage scaleImage(BufferedImage originalImage, int width, int height) throws InvalidImageSizeException {
        if (originalImage.getWidth() > MAP_TILE_SIZE || originalImage.getHeight() > MAP_TILE_SIZE) {
            throw new InvalidImageSizeException();
        }

        BufferedImage scaledImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(originalImage, 0, 0, width, height, null);
        g2.dispose();
        return scaledImage;
    }

}
