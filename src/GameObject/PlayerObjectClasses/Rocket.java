/**
 *
 * Rocket.java
 * Written by: Megan (Em) Powers
 *
 * Represents the rocket powerup.
 *
 */

package GameObject.PlayerObjectClasses;

import GameObject.MainRoom;
import GameObject.Music;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Rocket extends Weapon {
    private int damage;
    private int rocketXSpeed;
    private int rocketYSpeed;
    private int vx;
    private int vy;
    private int angle;
    private final int R = 2;

    private Rectangle hitBox;

    private static BufferedImage rocketImage;
    private static BufferedImage smallExplosionImg;
    private static BufferedImage largeExplosionImg;

    public static boolean smallExplosion = false;
    public static boolean largeExplosion = false;
    private boolean isShowing = false;

    public boolean collision = false;
    public int timetoCollide = 0;
    private String tankFiredFrom;
    double dir = 1;
    String largeSFX;
    Music m1;

    public Rocket(int damage, int rocketXSpeed, int rocketYSpeed, int vx, int vy, int angle, int x, int y, BufferedImage rocketImage) {
        super(damage, rocketXSpeed, rocketYSpeed, vx, vy, angle, x, y, rocketImage);
        this.isShowing = true;
        largeSFX = "Explosion_large.wav";
        m1 = new Music(2, largeSFX);
        this.hitBox = new Rectangle(x, y, rocketImage.getWidth(),  rocketImage.getHeight());
    }

    public boolean getShowing(){
        return this.isShowing;
    }

    public void setShowing(boolean s){
        this.isShowing = s;
    }

    public static void setSmallExplosionImg(BufferedImage smallE){
        smallExplosionImg = smallE;
    }

    public static void setLargeExplosionImg(BufferedImage largeE){
        largeExplosionImg = largeE;
    }

    public static void setSmallExplosion(boolean hasExploded){
        smallExplosion = hasExploded;
    }

    public static void setLargeExplosion(boolean hasExploded){
        largeExplosion = hasExploded;
    }

    public static boolean getSmallExplosion(){
        return smallExplosion;
    }

    public boolean getLargeExplosion(){
        return largeExplosion;
    }

    private int getDamage() {
        return this.damage;
    }

    public Rectangle getHitBox(){
        return hitBox.getBounds();
    }

    public static void setImg(BufferedImage img){
        rocketImage = img;
    }

    public String getTankFiredFrom(){
        return this.tankFiredFrom;
    }

    public void setTankFiredFrom(String tankFiredFrom){
        this.tankFiredFrom = tankFiredFrom;
    }

    public void setVX(int vx){
        this.vx = vx;
    }

    public void setVY(int vy){
        this.vy = vy;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setAngle(int angle){
        this.angle = angle;
    }

    public void hitObject(boolean collided){
        collision = collided;
    }

    public void fireRocket(int w, int h) {
        if(y < h-40 && y > 0 && x > 0 && x < w-40 && !collision){
            x += Math.round(R * Math.cos(Math.toRadians(angle)) * 3);
            y += Math.round(R * Math.sin(Math.toRadians(angle)) * 3);
            this.hitBox.setLocation(x, y);
            this.checkBorder();
        }
        else{
            timetoCollide = timetoCollide + 1;
        }
    }

    private void checkBorder() {
        if (x < 0) {
            x = 0;
            this.isShowing = false;
        }
        if (x >= MainRoom.SCREEN_WIDTH) {
            x = MainRoom.SCREEN_WIDTH;
            this.isShowing = false;
        }
        if (y < 0) {
            y = 0;
            this.isShowing = false;
        }
        if (y >= MainRoom.SCREEN_HEIGHT) {
            y = MainRoom.SCREEN_HEIGHT;
            this.isShowing = false;
        }
    }

    public void drawImage(Graphics2D g) {

        AffineTransform rotation = AffineTransform.getTranslateInstance(x * dir, y * dir);
        rotation.rotate(Math.toRadians(angle), this.rocketImage.getWidth() / 2.0, this.rocketImage.getHeight() / 2.0);

        if(isShowing == true) {
            if (collision == true && smallExplosion == true) {
                g.drawImage(this.rocketImage, rotation, null);
                g.drawImage(smallExplosionImg, rotation, null);
                m1.play();

                if (timetoCollide > 1) {
                    this.isShowing = false;
                }

            } else if (collision == true && largeExplosion == true) {
                g.drawImage(this.rocketImage, rotation, null);
                g.drawImage(largeExplosionImg, rotation, null);
                m1.play();

                if (timetoCollide > 1) {
                    this.isShowing = false;
                }
            } else {
                g.drawImage(this.rocketImage, rotation, null);
            }
        }
    }
}
