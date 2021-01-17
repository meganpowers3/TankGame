/**
 *
 * Powerup.java
 * Written by: Megan (Em) Powers
 *
 * Represents visible powerups on the map.
 *
 */

package GameObject.PlayerObjectClasses;

import GameObject.PlayerObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Powerup extends PlayerObject {
    public boolean health = false;
    public boolean bouncingBomb = false;
    public boolean rocket = false;
    public boolean shield1 = false;
    public boolean shield2 = false;
    boolean exists = true;
    static private BufferedImage healthImg;
    static private BufferedImage bombImg;
    static private BufferedImage rocketImg;
    static private BufferedImage shield1Img;
    static private BufferedImage shield2Img;
    public BufferedImage currImage;
    private boolean isShowing;

    public Powerup(int x, int y, boolean health, boolean bouncingBomb, boolean rocket, boolean shield1, boolean shield2, boolean visible){
        this.x = x;
        this.y = y;
        this.hitBoxRectangle = new Rectangle(x, y, 30, 30);
        this.health = health;
        this.bouncingBomb = bouncingBomb;
        this.rocket = rocket;
        this.shield1 = shield1;
        this.shield2 = shield2;
        this.isShowing = visible;
    }

    public boolean getShowing(){
        return this.isShowing;
    }

    public void setShowing(boolean s){
        this.isShowing = s;
    }

    public static void setHealthImg(BufferedImage image){
        Powerup.healthImg = image;
    }

    public static void setBombImg(BufferedImage image){
        Powerup.bombImg = image;
    }

    public static void setRocketImg(BufferedImage image){
        Powerup.rocketImg = image;
    }

    public static void setShield1Img(BufferedImage image){
        Powerup.shield1Img = image;
    }

    public static void setShield2Img(BufferedImage image){
        Powerup.shield2Img = image;
    }

    public void update(){

    }

    public void drawImage(Graphics2D g) {
        if(this.isShowing) {
            if (this.health) {
                g.drawImage(healthImg, x, y, 30, 30, null);
            }
            if (this.bouncingBomb) {
                g.drawImage(bombImg, x, y, 30, 30, null);
            }
            if (this.rocket) {
                g.drawImage(rocketImg, x, y, 30, 30, null);
            }

            if (this.shield1) {
                g.drawImage(shield1Img, x, y, 30, 30, null);
            }

            if (this.shield2) {
                g.drawImage(shield2Img, x, y, 30, 30, null);
            }
        }
    }

    public void collided() {
        this.exists = false;
    }

}
