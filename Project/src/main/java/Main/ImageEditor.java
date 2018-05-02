/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;

/**
 *
 * @author Big Bertha
 */
public class ImageEditor {
    
    public static void addStringToImage(String toAdd, String file) throws IOException {
        File img = new File(file);
        BufferedImage image = ImageIO.read(img);
        
        int randomWidth = ThreadLocalRandom.current().nextInt(0, (int) (image.getWidth()*.65));
        int randomHeight = ThreadLocalRandom.current().nextInt(15, (int) (image.getHeight()));

        Graphics g = image.getGraphics();
        g.setFont(g.getFont().deriveFont(30f));
        g.drawString(toAdd, randomWidth, randomHeight);
        g.dispose();

        ImageIO.write(image, "jpg", img);       
    }
}
