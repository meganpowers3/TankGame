
/**
 *
 * GameScore.java
 * Written by: Megan (Em) Powers
 *
 * Keeps track of and displays the game score.
 *
 */

package GameObject.PlayerObjectClasses;

import GameObject.MainRoom;
import java.awt.*;
import java.awt.image.ImageObserver;

public class GameScore implements ImageObserver {

    private MainRoom game;
    int x;
    int y;
    Font font = new Font(Font.SANS_SERIF, Font.BOLD, 25);
    Tank currTank;
    int currScore;

    public GameScore(MainRoom game, int x, int y, Tank currTank){
        this.game = game;
        this.x = x;
        this.y = y;
        this.currTank = currTank;
        this.currScore = currTank.getScore();
    }

    public void drawImage(Graphics2D g, ImageObserver o){
        g.setFont(font);
        g.drawString("SCORE: " + currTank.getScore(), x, y);
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return false;
    }

}
