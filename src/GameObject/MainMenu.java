/**
 *
 * MainMenu.java
 * Written by: Megan (Em) Powers
 *
 * Displays the content of the menu.
 *
 */

package GameObject;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainMenu extends PlayerObject {
    BufferedImage img;
    int x;
    int y;

    public MainMenu(){

    }

    @Override
    public void drawImage(Graphics2D g) {
        g.drawImage(this.img, x, y,null);
    }
}
