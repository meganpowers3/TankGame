
/**
 *
 * Wall.java
 * Written by: Megan (Em) Powers
 *
 * Acts as a parent class for walls.
 *
 */

package GameObject.PlayerObjectClasses;

import GameObject.PlayerObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends PlayerObject {
    private int wallType;
    private int x, y;
    private boolean isVisible;
    private static BufferedImage wallImage;
    private Rectangle hitBox;

    public Wall(int width, int height, int x, int y) {
        this.x = x;
        this.y = y;
        this.wallType = wallType;
    }

    public Wall() {

    }

    public boolean getShowing(){
        return isVisible;
    }

    public static void setUnbreakable(BufferedImage img){
        wallImage = img;
    }

    public Rectangle getHitBox(){
        return hitBox.getBounds();
    }

    public void drawImage(Graphics2D g) {
        g.drawImage(this.wallImage, x, y, null);
    }

}
