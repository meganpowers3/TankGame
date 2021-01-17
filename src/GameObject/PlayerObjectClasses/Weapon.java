
/**
 *
 * Weapon.java
 * Written by: Megan (Em) Powers
 *
 * Acts as an abstract class for weapons.
 *
 */

package GameObject.PlayerObjectClasses;

import GameObject.MainRoom;
import GameObject.PlayerObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class Weapon extends PlayerObject {

        private int damage;
        private int xSpeed;
        private int ySpeed;
        private boolean isShowing = false;
        private BufferedImage img;
        private Rectangle hitBox;
        public boolean collision = false;
        public int timetoCollide = 0;

        private int vx;
        private int vy;
        private int angle;
        private final int R = 2;
        private final int ROTATION_SPEED = 4;

        public Weapon(int damage, int xSpeed, int ySpeed, int vx, int vy, int angle, int x, int y, BufferedImage img) {
            this.img = img;
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.damage = damage;
            this.xSpeed = xSpeed;
            this.ySpeed = ySpeed;
            this.img = img;
            this.isShowing = true;
            this.hitBox = new Rectangle(x, y, img.getWidth(), img.getHeight());
        }

        public boolean getShowing(){
            return this.isShowing;
        }

        public void setShowing(boolean s){
            this.isShowing = s;
        }

        private int getDamage() {
            return this.damage;
        }

        public Rectangle getHitBox(){
            return hitBox.getBounds();
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
            rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);

            if (collision == true) {
                g.drawImage(this.img, rotation, null);

                if (timetoCollide > 3) {
                    this.isShowing = false;
                } else {
                    g.drawImage(this.img, rotation, null);
                }
            }
        }
}
