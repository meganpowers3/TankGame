
/**
 *
 * Background.java
 * Written by: Megan (Em) Powers
 *
 * Displays a background image.
 *
 */

package GameObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Background{

    private int x;
    private int y;
    private int vx;
    private int vy;
    private int angle;

    private final int R = 2;

    private BufferedImage backgroundImage;

    public Background(int x, int y, int vx, int vy, int angle, BufferedImage backgroundImage){
        this.backgroundImage = backgroundImage;
    }

    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= MainRoom.SCREEN_WIDTH - 88) {
            x = MainRoom.SCREEN_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= MainRoom.SCREEN_HEIGHT - 80) {
            y = MainRoom.SCREEN_HEIGHT - 80;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.backgroundImage.getWidth() / 2.0, this.backgroundImage.getHeight() / 2.0);
        Graphics2D g2d2 = (Graphics2D) g;
        g2d2.scale(2, 2);
        g2d2.drawImage(this.backgroundImage, rotation, null);
    }

}
