
/**
 *
 * UnbreakableWall.java
 * @author anthony-pc
 * Written by: Megan (Em) Powers
 *
 * Represents an unbreakable wall.
 *
 */

package GameObject.PlayerObjectClasses;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UnbreakableWall extends Wall {
    private int wallType;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean isVisible = true;
    private static BufferedImage wallImage;
    private Rectangle hitBox;

    public UnbreakableWall(int width, int height, int x, int y) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        wallType = 1;
        this.hitBox = new Rectangle(x, y, this.wallImage.getWidth(), this.wallImage.getHeight());
    }
    public void setWallType(){
        this.wallType = 0;
    }

    public static void setUnbreakable(BufferedImage img){
        wallImage = img;
    }

    public int getWallType(){
        return this.wallType;
    }

    public Rectangle getHitBox(){
        return hitBox.getBounds();
    }

    public boolean getShowing(){
        return isVisible;
    }

    public void drawImage(Graphics2D g) {
        g.drawImage(wallImage, x, y, null);
    }

}
