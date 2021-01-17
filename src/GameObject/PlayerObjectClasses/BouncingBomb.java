
/**
 *
 * BouncingBomb.java
 * Written by: Megan (Em) Powers
 *
 * Represents a bouncing bomb weapon.
 *
 */

package GameObject.PlayerObjectClasses;

import GameObject.MainRoom;
import GameObject.Music;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class BouncingBomb extends Weapon {
    private int damage;
    private int bombXSpeed;
    private int bombYSpeed;
    private int vx;
    private int vy;
    private int angle;
    private final int R = 2;

    private Rectangle hitBox;

    private static BufferedImage bombImage;
    private static BufferedImage largeExplosionImg;

    private boolean isShowing = false;
    public static boolean smallExplosion = false;
    public static boolean largeExplosion = false;
    public boolean collision = false;
    private String tankFiredFrom;

    public int timetoCollide = 0;

    String largeSFX;
    Music m1;

    public BouncingBomb(int damage, int bombXSpeed, int bombYSpeed, int vx, int vy, int angle, int x, int y, BufferedImage bombImage) {
        super(damage, bombXSpeed, bombYSpeed, vx, vy, angle, x, y, bombImage);
        this.isShowing = true;
        largeSFX = "Explosion_large.wav";
        m1 = new Music(2, largeSFX);
        this.hitBox = new Rectangle(x, y, bombImage.getWidth(), bombImage.getHeight());
    }

    public boolean getShowing(){
        return this.isShowing;
    }

    public void setShowing(boolean s){
        this.isShowing = s;
    }

    public static void setImg(BufferedImage img){
        bombImage = img;
    }

    public static void setLargeExplosionImg(BufferedImage largeE){
        largeExplosionImg = largeE;
    }

    public static void setLargeExplosion(boolean hasExploded){
        largeExplosion = hasExploded;
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

    public void bounce(){
        angle = angle * 3;
    }

    public void fireBomb(int w, int h) {

        x += Math.round(R * Math.cos(Math.toRadians(angle)));
        y += Math.round(R * Math.sin(Math.toRadians(angle)));
        this.hitBox.setLocation(x, y);
        this.checkBorder();

        if(collision){
            timetoCollide = timetoCollide + 1;
        }
    }

    public void hitObject(boolean collided){
        collision = collided;
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
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.bombImage.getWidth() / 2.0, this.bombImage.getHeight() / 2.0);

        if(isShowing == true) {
            if (collision == true && largeExplosion == true) {

                g.drawImage(this.bombImage, rotation, null);

                if (timetoCollide > 60) {
                    m1.play();
                    g.drawImage(largeExplosionImg, rotation, null);
                }
                if (timetoCollide > 63) {
                    this.isShowing = false;
                }
            } else {
                g.drawImage(this.bombImage, rotation, null);
            }
        }
    }
}
