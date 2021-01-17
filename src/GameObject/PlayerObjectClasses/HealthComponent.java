
/**
 *
 * HealthComponent.java
 * Written by: Megan (Em) Powers
 *
 * Represents the health bar and lives objects.
 *
 */

package GameObject.PlayerObjectClasses;

import GameObject.PlayerObject;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HealthComponent extends PlayerObject {
    private BufferedImage healthImage;

    private int x;
    private int y;

    public HealthComponent(BufferedImage healthImage, int x, int y) {
        this.healthImage = healthImage;
        this.x = x;
        this.y = y;
    }

    public void drawImage(Graphics2D g) {
        g.drawImage(healthImage, x, y, null);
    }
}
