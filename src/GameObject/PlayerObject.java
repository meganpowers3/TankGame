
/**
 *
 * PlayerObject.java
 * Written by: Megan (Em) Powers
 *
 * Abstract class that represents all objects associated with the game.
 *
 */

package GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public abstract class PlayerObject {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected BufferedImage image;
    public Rectangle hitBoxRectangle;

    public PlayerObject(BufferedImage image, int x, int y){
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    public PlayerObject() {

    }

    public PlayerObject(int i, int i1) {
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getHeight(){
        return this.height;
    }

    public void setX(int a){
        this.x = a;
    }

    public void setY(int b){
        this.y = b;
    }

    public abstract void drawImage(Graphics2D g);

}
