
/**
 *
 * Collision.java
 * Written by: Megan (Em) Powers
 *
 * Detects object collisions.
 *
 */


package GameObject;
import GameObject.PlayerObjectClasses.*;

public class Collision {
    MainRoom room;
    Controller controller;

    public Collision(){
        this.room = room;
    }

    public void collisionCheck(PlayerObject o1, PlayerObject o2){
        if(o1 instanceof Tank && o2 instanceof Tank && ((Tank) o1).getTank() == "tankOne" && ((Tank) o2).getTank() == "tankTwo"){
            if (((Tank) o1).tankCollision().intersects(((Tank) o2).tankCollision())) {
                    ((Tank) o1).setMotion(false);
                    ((Tank) o2).setMotion(false);
            }
        }

        if(o1 instanceof Shell && o2 instanceof BreakableWall && o1 != null){
            if (((Shell) o1).getHitBox().intersects(((BreakableWall) o2).getHitBox()) && !((Shell) o1).collision) {
                ((BreakableWall) o2).collided();
                ((Shell) o1).hitObject(true);
                ((Shell) o1).setSmallExplosion(true);
            }
        }

        if(o1 instanceof Shell && o2 instanceof UnbreakableWall && o1 != null){
            if (((Shell) o1).getHitBox().intersects(((UnbreakableWall) o2).getHitBox()) && !((Shell) o1).collision) {
                ((Shell) o1).hitObject(true);
                ((Shell) o1).setSmallExplosion(true);
            }
        }

        if(o1 instanceof Shell && o2 instanceof Tank && o1 != null){
            if (((Shell) o1).getHitBox().intersects(((Tank) o2).getHitBox()) && !((Shell) o1).collision && !((Shell) o1).getTankFiredFrom().equals(((Tank) o2).getTank())) {
                 ((Shell) o1).hitObject(true);
                 ((Tank) o2).loseHealth(5);
                 ((Shell) o1).setLargeExplosion(true);
            }
        }

        if(o1 instanceof Rocket && o2 instanceof Tank && o1 != null){
            if (((Rocket) o1).getHitBox().intersects(((Tank) o2).getHitBox()) && !((Rocket) o1).collision && !((Rocket) o1).getTankFiredFrom().equals(((Tank) o2).getTank())) {
                ((Rocket) o1).hitObject(true);
                ((Tank) o2).loseHealth(20);
                ((Rocket) o1).setLargeExplosion(true);
            }
        }

        if(o1 instanceof Rocket && o2 instanceof BreakableWall && o1 != null){
            if (((Rocket) o1).getHitBox().intersects(((BreakableWall) o2).getHitBox()) && !((Rocket) o1).collision) {
                ((BreakableWall) o2).collided();
                ((Rocket) o1).hitObject(true);
                ((Rocket) o1).setSmallExplosion(true);
            }
        }

        if(o1 instanceof Rocket && o2 instanceof UnbreakableWall && o1 != null){
            if (((Rocket) o1).getHitBox().intersects(((UnbreakableWall) o2).getHitBox()) && !((Rocket) o1).collision) {
                ((Rocket) o1).hitObject(true);
                ((Rocket) o1).setSmallExplosion(true);
            }
        }

        if(o1 instanceof BouncingBomb && o2 instanceof Tank && o1 != null){
            if (((BouncingBomb) o1).getHitBox().intersects(((Tank) o2).getHitBox()) && !((BouncingBomb) o1).collision) {
                ((Tank) o2).loseHealth(15);
                ((BouncingBomb) o1).setLargeExplosion(true);
                ((BouncingBomb) o1).hitObject(true);
            }
        }

        if(o1 instanceof BouncingBomb && o2 instanceof Wall && o1 != null){
            if (((BouncingBomb) o1).getHitBox().intersects(((Wall) o2).getHitBox()) && !((BouncingBomb) o1).collision) {
                ((BouncingBomb) o1).bounce();
                if (((BouncingBomb) o1).getHitBox().intersects(((Wall) o2).getHitBox()) && !((BouncingBomb) o1).collision){
                    ((BouncingBomb) o1).setLargeExplosion(true);
                    ((BouncingBomb) o1).hitObject(true);
                }
            }
        }

        if(o1 instanceof Tank && o2 instanceof BreakableWall && o1 != null){
            if(((Tank) o1).tankCollision().intersects(((BreakableWall) o2).getHitBox())){
                if(((BreakableWall) o2).getShowing() == true) {
                    ((Tank) o1).setMotion(false);
                }
            }
        }

        if(o1 instanceof Tank && o2 instanceof UnbreakableWall && o2 != null){
            if(((Tank) o1).tankCollision().intersects(((UnbreakableWall) o2).getHitBox())){
                ((Tank) o1).setMotion(false);
            }
        }


        // GET HIT BOX RECTANGLE
        if(o1 instanceof Tank && o2 instanceof Powerup && o2 != null) {
            if (((Tank) o1).getHitBox().intersects(((Powerup) o2).hitBoxRectangle)) {
                if (((Powerup) o2).health) {
                    ((Tank) o1).setHealth(100);
                    ((Powerup) o2).setShowing(false);
                }

                if (((Powerup) o2).shield1 && ((Tank) o1).getTank() == "tankOne") {
                    ((Tank) o1).setHealth(100);
                    ((Tank) o1).setHits(3);
                    ((Powerup) o2).setShowing(false);
                }

                if (((Powerup) o2).shield2 && ((Tank) o1).getTank() == "tankTwo") {
                    ((Tank) o1).setHealth(100);
                    ((Tank) o1).setHits(3);
                    ((Powerup) o2).setShowing(false);
                }

                if (((Powerup) o2).rocket) {
                    ((Tank) o1).canFireRocket(true);
                    ((Powerup) o2).setShowing(false);
                }

                if (((Powerup) o2).bouncingBomb) {
                    ((Tank) o1).canFireBomb(true);
                    ((Powerup) o2).setShowing(false);
                }

            }
        }
    }
}

