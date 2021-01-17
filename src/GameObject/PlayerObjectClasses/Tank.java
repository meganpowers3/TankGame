
/**
 *
 * Weapon.java
 * Written by: @ajsouza
 * Contributions by: Megan (Em) Powers
 *
 * Acts as an abstract class for weapon powerups.
 *
 */

package GameObject.PlayerObjectClasses;

import GameObject.MainRoom;
import GameObject.Music;
import GameObject.PlayerObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import static javax.imageio.ImageIO.read;

public class Tank extends PlayerObject {

    private int x;
    private int y;
    private int vx;
    private int vy;
    private int angle;
    private int health = 100;
    private int shellDamage;
    private int shellSpeed;
    public int lives;
    public int score = 0;
    private boolean canMove = true;

    private final int R = 2;
    private final int ROTATION_SPEED = 4;

    private BufferedImage tankImage;
    private BufferedImage bulletImage;
    private BufferedImage rocketImage;
    private BufferedImage bombImage;
    private BufferedImage livesImage;
    private static BufferedImage downTank;
    private BufferedImage [] healthBarImg;
    private HealthComponent healthBar;
    private Rectangle hitBox;

    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootShell;
    private boolean shootRocket;
    private boolean shootBomb;
    private Shell playerShell;
    private Rocket playerRocket;
    private Rocket playerRocket2;
    public int timetoCollide = 0;
    private BouncingBomb playerBomb;
    private ArrayList<Shell> shellList;
    private ArrayList<Rocket> rocketList;
    private ArrayList<BouncingBomb> bombList;
    private ArrayList<HealthComponent> lifeList;
    private String currentTank;

    private int hits = 0;

    private boolean rocketFire = false;
    private boolean bombFire = false;
    private int move = 0;

    private MainRoom room;

    //plane game
    String largeSFX;
    Music m1;

    public Tank(int x, int y, int vx, int vy, int currentLives, int angle, int health, int bulletDamage, BufferedImage tankImage) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.lives = 3;
        this.health = 100;
        this.healthBarImg = new BufferedImage[5];
        this.shellDamage = 5;
        this.shellSpeed = 1;
        this.tankImage = tankImage;
        this.angle = angle;
        this.score = 0;
        this.hitBox = new Rectangle(x, y, this.tankImage.getWidth(), this.tankImage.getHeight());
        this.largeSFX = "Explosion_large.wav";
        this.m1 = new Music(2, largeSFX);

        this.shellList = new ArrayList<Shell>(100);
        this.rocketList = new ArrayList<Rocket>(1);
        this.bombList = new ArrayList<BouncingBomb>(2);
        this.lifeList = new ArrayList<HealthComponent>(3);

        try {
            bulletImage = read(MainRoom.class.getClassLoader().getResource("Shell.gif"));
            rocketImage = read(MainRoom.class.getClassLoader().getResource("Rocket.gif"));
            bombImage = read(MainRoom.class.getClassLoader().getResource("Weapon.gif"));
            livesImage = read(MainRoom.class.getClassLoader().getResource("life.png"));
            healthBarImg[0] = read(MainRoom.class.getClassLoader().getResource("Health_Full.png"));
            healthBarImg[1] = read(MainRoom.class.getClassLoader().getResource("Health_80.png"));
            healthBarImg[2] = read(MainRoom.class.getClassLoader().getResource("Health_60.png"));
            healthBarImg[3] = read(MainRoom.class.getClassLoader().getResource("Health_40.png"));
            healthBarImg[4] = read(MainRoom.class.getClassLoader().getResource("Health_20.png"));
            Shell.setImg(bulletImage);
            BouncingBomb.setImg(bombImage);
            Rocket.setImg(rocketImage);
        } catch (IOException e) {
            System.out.println("Assets not found, please try again.");
            e.printStackTrace();
        }
    }

    public void setRoom(MainRoom room){
        this.room = room;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public Rectangle getHitBox(){
        return hitBox.getBounds();
    }

    public void toggleUpPressed() {
        this.UpPressed = true;
    }

    public void toggleDownPressed() {
        this.DownPressed = true;
    }

    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    public void toggleShoot() {
        this.shootShell = true;
    }

    public void toggleRocket() {
            this.shootRocket = true;
    }

    public void toggleBomb() {
        this.shootBomb = true;
    }

    public void setHits(int hits){
        this.hits = hits;
    }

    public int getHits(){
        return hits;
    }

    public ArrayList<Shell> getShells(){
        return this.shellList;
    }

    public ArrayList<Rocket> getRockets(){
        return this.rocketList;
    }

    public ArrayList<BouncingBomb> getBombs(){
        return this.bombList;
    }

    // Toggle controller keys

    public void unToggleUpPressed() {
        this.UpPressed = false;
    }

    public void unToggleDownPressed() {
        this.DownPressed = false;
    }

    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void unToggleShoot() {
        this.shootShell = false;
    }

    public void unToggleRocket() {
        this.shootRocket = false;
    }

    public void unToggleBomb() {
        this.shootBomb = false;
    }

    // If the tank can fire objects

    public void canFireRocket(boolean canFire){
        this.rocketFire = true;
    }

    public void canFireBomb(boolean canFire){
        this.bombFire = true;
    }

    public void setScore(int s){
        this.score = this.score + s;
    }

    public int getScore(){
        return score;
    }

    public void setTank(String tank){
        this.currentTank = tank;
    }

    public String getTank(){
        return currentTank;
    }

    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if(this.shootShell){
            fireShell();
            shootShell = false;
        }

        if(this.shootRocket == true && this.rocketFire == true){
            fireRocket();
            rocketFire = false;
        }

        if(this.shootBomb && this.bombFire){
            fireBomb();
            shootBomb = false;
            if(bombList.size() > 1) {
                bombFire = false;
            }
        }

       canMove = true;

        /**
         *  if (playerRect.getY() < wallRect.getY()) {
         *                         tankExample.tankOne.goBack(0, -2);
         *                     }
         *                     if (playerRect.getY() > wallRect.getY()) {
         *                         tankExample.tankOne.goBack(0, 2);
         *                     }
         *                     if (playerRect.getX() < wallRect.getX()) {
         *                         tankExample.tankOne.goBack(-2, 0);
         *                     }
         *                     if (playerRect.getX() > wallRect.getX()) {
         *                         tankExample.tankOne.goBack(2, 0);
         *                     }
         */
    }

    private void rotateLeft() {
        this.angle -= this.ROTATION_SPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATION_SPEED;
    }

    public Rectangle tankCollision(){
        return new Rectangle(x + vx, y + vy, 60, 60);
    }

    private void fireShell(){
        playerShell = new Shell(shellDamage, 1, 1, vx, vy, angle, x, y, bulletImage);
        playerShell.setX(x + vx);
        playerShell.setY(y + vy / 2);
        playerShell.setVX(vx);
        playerShell.setVX(vy);
        playerShell.setAngle(angle);
        playerShell.setTankFiredFrom(currentTank);
        room.addObjects(playerShell);
        shellList.add(playerShell);
    }

    private void fireRocket(){
        playerRocket = new Rocket(shellDamage, 5, 5, vx, vy, angle, x, y, rocketImage);
        room.addObjects(playerRocket);
        playerRocket.setX(x + vx);
        playerRocket.setY(y + vy / 2);
        playerRocket.setVX(vx);
        playerRocket.setVX(vy);
        playerRocket.setTankFiredFrom(currentTank);
        playerRocket.setAngle(angle);
        rocketList.add(playerRocket);
    }

    private void fireBomb(){
        playerBomb = new BouncingBomb(shellDamage, 3, 3, vx, vy, angle, x, y, bombImage);
        playerBomb.setX(x + vx);
        playerBomb.setY(y + vy / 2);
        playerBomb.setVX(vx);
        playerBomb.setVX(vy);
        playerBomb.setAngle(angle);
        playerBomb.setTankFiredFrom(currentTank);
        room.addObjects(playerBomb);
        bombList.add(playerBomb);
    }

    public Shell getShell(){
        return playerShell;
    }

    public Rocket getRocket(){
        return playerRocket;
    }

    public BouncingBomb getBomb(){
        return playerBomb;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        if(canMove == true) {
            x -= vx;
            y -= vy;
            this.checkBorder();
        }
        this.hitBox.setLocation(x, y);
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));

        if(canMove == true) {
            x += vx;
            y += vy;
            this.checkBorder();
        }
        this.hitBox.setLocation(x, y);
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

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getVX(){
        return vx;
    }

    public int getVY(){
        return vy;
    }

    public static void setDownTankImg(BufferedImage img){
        downTank = img;
    }


    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    public void loseHealth(int h){

        if(hits < 1) {
            if (health - h > 0) {
                this.health = this.health - h;
            } else {
                health = 0;
                m1.play();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }else{
            hits = hits - 1;
        }
    }

    public int getHealth(){
        return this.health;
    }

    public void drawHealthBar(Graphics2D g2d){

        if(this.health > 80){
            healthBar = new HealthComponent(healthBarImg[0], x+50, y + 20);
            healthBar.drawImage(g2d);
        }else if(this.health > 60 && this.health <= 80){
            healthBar = new HealthComponent(healthBarImg[1], x+50, y + 20);
            healthBar.drawImage(g2d);
        }else if(this.health > 40 && this.health <= 60){
            healthBar = new HealthComponent(healthBarImg[2], x+50, y + 20);
            healthBar.drawImage(g2d);
        }else if(this.health > 20 && this.health <= 40){
            healthBar = new HealthComponent(healthBarImg[3], x+50, y + 20);
            healthBar.drawImage(g2d);
        }else if(this.health > 0 && this.health <= 20) {
            healthBar = new HealthComponent(healthBarImg[4], x + 50, y + 20);
            healthBar.drawImage(g2d);
        }
    }

    public void drawLives(Graphics2D g2d){
        for(int i = 0; i < lives; i++){
            HealthComponent lifeObject = new HealthComponent(this.livesImage, x + i * livesImage.getWidth(null)-15, y);
            lifeObject.drawImage(g2d);
            lifeList.add(lifeObject);
        }
    }

    public void setMotion(boolean canMove){
        this.canMove = canMove;
    }

    public boolean getMotion(){
        return canMove;
    }


    @Override
    public void drawImage(Graphics2D g2d) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.tankImage.getWidth() / 2.0, this.tankImage.getHeight() / 2.0);

        if(health < 1) {
            try {
                g2d.drawImage(downTank, rotation, null);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(health != 0) {
            g2d.drawImage(this.tankImage, rotation, null);
        }

        drawLives(g2d);
        drawHealthBar(g2d);
    }


}
