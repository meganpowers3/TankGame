
/**
 *
 * BreakableWall.java
 * Written by: Megan (Em) Powers
 *
 * Represents a breakable wall.
 *
 */

package GameObject.PlayerObjectClasses;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall {
    private int wallType;
    private int x;
    private int y;
    private int width;
    private int height;
    private int numHits = 3;
    private boolean isVisible = true;
    private static BufferedImage wallImage;
    private Rectangle hitBox;

    public BreakableWall(int width, int height, int x, int y) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        wallType = 1;
        this.hitBox = new Rectangle(x, y, wallImage.getWidth(), wallImage.getHeight());
    }

    public void setWallType(){
        this.wallType = 1;
    }

    public int getWallType(){
        return this.wallType;
    }

    public void setNumHits(){
        this.numHits = 3;
    }

    public int getNumHits(){
        return numHits;
    }

    public static void setBreakable(BufferedImage img){
        wallImage = img;
    }

    private void subtractHits(int n){
        this.numHits = numHits - n;
    }

    public void collided(){
        this.numHits = numHits - 1;
        isDestroyed();
    }

    public void isDestroyed(){
        if (numHits < 1){
            this.isVisible = false;
        }
    }

    public Rectangle getHitBox(){
        return hitBox.getBounds();
    }

    public boolean getShowing(){
        return isVisible;
    }

    public void drawImage(Graphics2D g) {
        if(isVisible == true) {
            g.drawImage(wallImage, x, y, null);
        }
    }
}
