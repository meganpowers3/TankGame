package GameObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import GameObject.PlayerObjectClasses.BouncingBomb;
import GameObject.PlayerObjectClasses.BreakableWall;
import GameObject.PlayerObjectClasses.UnbreakableWall;
import GameObject.PlayerObjectClasses.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import static javax.imageio.ImageIO.read;

/**
 * Main driver class of Tank Example.
 * Class is responsible for loading resources and
 * initializing game objects. Once completed, control will
 * be given to infinite loop which will act as our game loop.
 * A very simple game loop.
 * @author anthony-pc
 * @author Em Powers
 */

public class MainRoom extends JPanel  {

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;
    private BufferedImage world;
    private Graphics2D buffer;
    private JFrame jFrame;
    private Tank tankOne;
    private Tank tankTwo;
    private Powerup test;

    //Check if tank hit
    public static boolean tankOneHit = false;
    public static boolean tankTwoHit = false;
    private Background backgroundImage;

    // Images
    private BufferedImage backgroundTile, breakableWallImg, unbreakableWallImg, menuImg, helpImg, powerupImg, healthImg, rocketImg, bombImg,
            shield1Img, shield2Img, smallExplosionImg, largeExplosionImg, gameOverOneScreen, gameOverTwoScreen;

    private static final MainRoom tankGame = new MainRoom();
    public MapInterpreter mapFile;

    public int i = 1;

    //Tank starting positions
    public static final int TANKONE_START_X = 60;
    public static final int TANKONE_START_Y = 60;
    public static final int TANKTWO_START_X = 1050;
    public static final int TANKTWO_START_Y = 860;

    // Menu
    public static MainMenu menu;

    //GameScores
    GameScore p1S;
    GameScore p2S;

    //Audio
    Music m;
    static Music m1;
    static Music m2;
    static String largeSFX;
    static String smallSFX;

    private static Collision collisionChecker;

    enum GameState{
        TITLE_STATE,
        MAIN_STATE,
        MENU_STATE,
        POWERUP_STATE,
        GAMEOVER1_STATE,
        GAMEOVER2_STATE,
    }

    static GameState currentState = GameState.TITLE_STATE;

    public static void setState(GameState g){
        currentState = g;
    }

    public static ArrayList<PlayerObject> mapObjects = new ArrayList<>();

    public void fillMapArray(ArrayList<PlayerObject> mapObjects){
        this.mapObjects = mapObjects;
    }

    public void addObjects(PlayerObject currObject){
        this.mapObjects.add(currObject);
    }

    {
        try {
            backgroundTile = read(MainRoom.class.getClassLoader().getResource("Background.bmp"));
            unbreakableWallImg = read(MainRoom.class.getClassLoader().getResource("Wall1.gif"));
            breakableWallImg = read(MainRoom.class.getClassLoader().getResource("Wall2.gif"));
            menuImg = read(MainRoom.class.getClassLoader().getResource("Main_Screen.png"));
            helpImg = read(MainRoom.class.getClassLoader().getResource("Instruction_Screen.png"));
            powerupImg = read(MainRoom.class.getClassLoader().getResource("PowerupInformative.png"));
            healthImg = read(MainRoom.class.getClassLoader().getResource("health.png"));
            rocketImg = read(MainRoom.class.getClassLoader().getResource("Rocket.gif"));
            bombImg = read(MainRoom.class.getClassLoader().getResource("Bouncing.gif"));
            shield1Img = read(MainRoom.class.getClassLoader().getResource("Shield1R.gif"));
            shield2Img = read(MainRoom.class.getClassLoader().getResource("Shield2.gif"));
            gameOverOneScreen = read(MainRoom.class.getClassLoader().getResource("Player_One_GO.png"));
            gameOverTwoScreen = read(MainRoom.class.getClassLoader().getResource("Player_Two_GO.png"));
            smallExplosionImg = read(MainRoom.class.getClassLoader().getResource("Explosion_small.gif"));
            largeExplosionImg = read(MainRoom.class.getClassLoader().getResource("Explosion_large.gif"));

            // Setting images in classes
            UnbreakableWall.setUnbreakable(unbreakableWallImg);
            BreakableWall.setBreakable(breakableWallImg);
            Shell.setLargeExplosionImg(largeExplosionImg);
            Shell.setSmallExplosionImg(smallExplosionImg);
            Rocket.setLargeExplosionImg(largeExplosionImg);
            Rocket.setSmallExplosionImg(smallExplosionImg);
            BouncingBomb.setLargeExplosionImg(largeExplosionImg);
            smallSFX = "Explosion_small.wav";
            largeSFX = "Explosion_small.wav";

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MainRoom getRoom(){
        return tankGame;
    }

    public static void main(String[] args) {
        MainRoom tankExample = new MainRoom();
        tankExample.init();
        tankExample.collisionChecker = new Collision();

        m1 = new Music(2, smallSFX);
        m2 = new Music(2, largeSFX);


        try {
            while (true) {
                tankExample.repaint();
                if (MainRoom.currentState == GameState.MAIN_STATE) {

                    tankExample.repaint();

                    for(int i = 0; i < mapObjects.size(); i++){
                        for(int j = 0; j < mapObjects.size(); j++){
                            PlayerObject o1 = mapObjects.get(i);
                            PlayerObject o2 = mapObjects.get(j);
                            tankExample.collisionChecker.collisionCheck(o1, o2);
                        }
                    }

                    if(tankExample.tankOne.getHealth() == 0){
                        tankExample.tankTwo.score++;
                    }
                    if(tankExample.tankTwo.getHealth() == 0){
                        tankExample.tankOne.score++;
                    }

                    if(tankExample.tankOne.getScore() >= 3){
                        MainRoom.currentState = GameState.GAMEOVER1_STATE;
                    }
                    if(tankExample.tankTwo.getScore() >= 3){
                        MainRoom.currentState = GameState.GAMEOVER2_STATE;
                    }

                    for (int i = 0; i < mapObjects.size(); i++) {
                        if(MainRoom.mapObjects.get(i) instanceof BreakableWall) {
                            if(((BreakableWall) MainRoom.mapObjects.get(i)).getShowing() == false) {
                                MainRoom.mapObjects.remove(i);
                            }
                        }
                        if(MainRoom.mapObjects.get(i) instanceof Shell) {
                            if(((Shell) MainRoom.mapObjects.get(i)).getShowing() == false) {
                                MainRoom.mapObjects.remove(i);
                            }
                        }


                    }

                    if (tankExample.tankOne.getHealth() == 0) {
                        if (tankExample.tankOne.lives > 0) {
                            tankExample.tankOne.lives--;
                            System.out.println("lives " + tankExample.tankOne.lives);
                            tankExample.tankOne.setHealth(100);
                            tankExample.tankOne.setX(TANKONE_START_X);
                            tankExample.tankOne.setY(TANKONE_START_Y);

                        }
                    }

                    if (tankExample.tankTwo.getHealth() == 0) {
                        if (tankExample.tankTwo.lives > 0) {
                            tankExample.tankTwo.lives--;
                            System.out.println("lives " + tankExample.tankTwo.lives);
                            tankExample.tankTwo.setHealth(100);
                            tankExample.tankTwo.setX(TANKTWO_START_X);
                            tankExample.tankTwo.setY(TANKTWO_START_Y);

                        }
                    }

                    tankExample.tankOne.update();
                    tankExample.tankTwo.update();

                    Thread.sleep(1000 / 144);
                }
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }



    private void init() {
        this.jFrame = new JFrame("Tank Rotation");
        this.world = new BufferedImage(MainRoom.SCREEN_WIDTH, MainRoom.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage tank1Image = null;
        BufferedImage tank2Image = null;
        BufferedImage tankDownImg = null;
        BufferedImage backgroundSprite = null;
        BufferedImage backgroundTile = null;
        menu = new MainMenu();

        try {
            /*
             * There is a subtle difference between using class
             * loaders and File objects to read in resources for your
             * tank games. First, File objects when given to input readers
             * will use your project's working directory as the base path for
             * finding the file. Class loaders will use the class path of the project
             * as the base path for finding files. For Intellij, this will be in the out
             * folder. if you expand the out folder, the expand the production folder, you
             * will find a folder that has the same name as your project. This is the folder
             * where your class path points to by default. Resources, will need to be stored
             * in here for class loaders to load resources correctly.
             *
             * Also one important thing to keep in mind, Java input readers given File objects
             * cannot be used to access file in jar files. So when building your jar, if you want
             * all files to be stored inside the jar, you'll need to class loaders and not File
             * objects.
             *
             */
            //Using class loaders to read in resources
            mapFile = new MapInterpreter("level.txt");
            mapFile.load();
            mapFile = new MapInterpreter("world.txt");
            tank1Image = read(MainRoom.class.getClassLoader().getResource("tank1.png"));
            tank2Image = read(MainRoom.class.getClassLoader().getResource("tank2.png"));
            backgroundTile = read(MainRoom.class.getClassLoader().getResource("Background.bmp"));
            menuImg = read(MainRoom.class.getClassLoader().getResource("Main_Screen.png"));
            helpImg = read(MainRoom.class.getClassLoader().getResource("Instruction_Screen.png"));
            powerupImg = read(MainRoom.class.getClassLoader().getResource("PowerupInformative.png"));
            Powerup.setHealthImg(read(MainRoom.class.getClassLoader().getResource("health.png")));
            Powerup.setRocketImg(read(MainRoom.class.getClassLoader().getResource("Rocket.gif")));
            Powerup.setBombImg(read(MainRoom.class.getClassLoader().getResource("Weapon.gif")));
            Powerup.setShield1Img(read(MainRoom.class.getClassLoader().getResource("Shield1R.gif")));
            Powerup.setShield2Img(read(MainRoom.class.getClassLoader().getResource("Shield2.gif")));

            tankDownImg = read(MainRoom.class.getClassLoader().getResource("tankDown.png"));
            Tank.setDownTankImg(tankDownImg);
            //Using file objects to read in resources.
            //tankImage = read(new File("tank1.png"));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        tankOne = new Tank(TANKONE_START_X, TANKONE_START_Y, 0, 0, 3,0, 100, 1, tank1Image);
        tankOne.setTank("tankOne");
        tankTwo = new Tank(TANKTWO_START_X, TANKTWO_START_Y, 0, 0, 3, 0, 100, 1, tank2Image);
        tankTwo.setTank("tankTwo");
        backgroundImage = new Background(300, 300, 0, 0, 0, backgroundSprite);

        p1S = new GameScore(this, 40, 60, tankOne);
        p2S = new GameScore(this, 690, 60, tankTwo);

        Controller tankOneControl = new Controller(tankOne, KeyEvent.VK_W,
                KeyEvent.VK_S,
                KeyEvent.VK_A,
                KeyEvent.VK_D,
                KeyEvent.VK_SPACE,
                KeyEvent.VK_X,
                KeyEvent.VK_C);

        Controller tankTwoControl = new Controller(tankTwo, KeyEvent.VK_UP,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT,
                KeyEvent.VK_ENTER,
                KeyEvent.VK_N,
                KeyEvent.VK_M);

        mapObjects.add(tankOne);
        tankOne.setRoom(this);
        mapObjects.add(tankTwo);
        tankTwo.setRoom(this);
        m = new Music(1, "Music.mid");
        this.jFrame.setLayout(new BorderLayout());
        this.jFrame.add(this);
        this.jFrame.addKeyListener(tankOneControl);
        this.jFrame.addKeyListener(tankTwoControl);
        this.jFrame.setSize(MainRoom.SCREEN_WIDTH, MainRoom.SCREEN_HEIGHT + 30);
        this.jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);

        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jFrame.setVisible(true);

    }

    //The following method is borrowed from the gameWorld class of the Airstrike game, with modifications. It is not mine.

    public void drawBackGroundWithTileImage(Graphics2D g2) {
        int TileWidth = backgroundTile.getWidth(this);
        int TileHeight = backgroundTile.getHeight(this);
        int move = 1;

        int NumberX = (int) (SCREEN_WIDTH / TileWidth);
        int NumberY = (int) (SCREEN_HEIGHT / TileHeight);

        for (int i = -1; i <= NumberY; i++) {
            for (int j = 0; j <= NumberX; j++) {
                g2.drawImage(backgroundTile, j * TileWidth,
                        i * TileHeight + (move % TileHeight), TileWidth,
                        TileHeight, this);
            }
        }
        move += 1;
    }

    public void launchGame(){

        for(int i = 0; i < tankOne.getShells().size(); i++){
            tankOne.getShells().get(i).fireBullet(SCREEN_WIDTH, SCREEN_HEIGHT);
            if((tankOne.getShells().get(i)).getShowing()) {
                tankOne.getShells().get(i).fireBullet(SCREEN_WIDTH, SCREEN_HEIGHT);
            }else{
                tankOne.getShells().remove(i);
            }
        }

        for(int i = 0; i < tankTwo.getShells().size(); i++){
            tankTwo.getShells().get(i).fireBullet(SCREEN_WIDTH, SCREEN_HEIGHT);
            if((tankTwo.getShells().get(i)).getShowing()) {
                tankTwo.getShells().get(i).fireBullet(SCREEN_WIDTH, SCREEN_HEIGHT);
            }else{
                tankTwo.getShells().remove(i);
            }
        }

        for(int i = 0; i < tankOne.getRockets().size(); i++){
            if((tankOne.getRockets().get(i)).getShowing()) {
                tankOne.getRockets().get(i).fireRocket(SCREEN_WIDTH, SCREEN_HEIGHT);
            }else{
                tankOne.getRockets().remove(i);
            }
        }

        for(int i = 0; i < tankTwo.getRockets().size(); i++){
            if((tankTwo.getRockets().get(i)).getShowing()) {
                tankTwo.getRockets().get(i).fireRocket(SCREEN_WIDTH, SCREEN_HEIGHT);
            }else{
                tankTwo.getRockets().remove(i);
            }
        }

        for(int i = 0; i < tankOne.getBombs().size(); i++){
            if((tankOne.getBombs().get(i)).getShowing()) {
                tankOne.getBombs().get(i).fireBomb(SCREEN_WIDTH, SCREEN_HEIGHT);
            }else{
                tankOne.getBombs().remove(i);
            }
        }

        for(int i = 0; i < tankTwo.getBombs().size(); i++){
            if((tankTwo.getBombs().get(i)).getShowing()) {
                tankTwo.getBombs().get(i).fireBomb(SCREEN_WIDTH, SCREEN_HEIGHT);
            }else{
                tankTwo.getBombs().remove(i);
            }
        }
    }

    public void drawSplitScreen(Graphics2D g2){
        int tankOneX = tankOne.getX();
        int tankOneY = tankOne.getY();
        int tankTwoX = tankTwo.getX();
        int tankTwoY = tankTwo.getY();

        if(tankOneX < SCREEN_WIDTH / 4){
            tankOneX  = SCREEN_WIDTH / 4;
        }
        if(tankTwoX < SCREEN_WIDTH / 4){
            tankTwoX  = SCREEN_WIDTH / 4;
        }
        if(tankOneX > world.getWidth() - SCREEN_WIDTH / 4){
            tankOneX  = world.getWidth() - SCREEN_WIDTH / 4;
        }
        if(tankTwoX > world.getWidth() - SCREEN_WIDTH / 4){
            tankTwoX  = world.getWidth() -SCREEN_WIDTH / 4;
        }

        if(tankOneY < SCREEN_HEIGHT / 2){
            tankOneY  = SCREEN_HEIGHT/ 2;
        }
        if(tankTwoY < SCREEN_HEIGHT / 2){
            tankTwoY  = SCREEN_HEIGHT / 2;
        }
        if(tankOneY > world.getHeight() - SCREEN_HEIGHT / 2){
            tankOneY  = world.getHeight() - SCREEN_HEIGHT / 2;
        }
        if(tankTwoY > world.getHeight() - SCREEN_HEIGHT / 2){
            tankTwoY  = world.getHeight() - SCREEN_HEIGHT / 2;
        }

        BufferedImage leftScreen = world.getSubimage(tankOneX - SCREEN_WIDTH / 4, tankOneY - SCREEN_HEIGHT / 2, SCREEN_WIDTH / 2, SCREEN_HEIGHT);
        BufferedImage rightScreen = world.getSubimage(tankTwoX - SCREEN_WIDTH / 4, tankTwoY - SCREEN_HEIGHT / 2, SCREEN_WIDTH / 2, SCREEN_HEIGHT);

        g2.drawImage(leftScreen, 0, 0, null);
        g2.drawImage(rightScreen, SCREEN_WIDTH / 2, 0, null);
    }


    public void drawMinimap(Graphics2D g2) {

        g2.drawRect(SCREEN_WIDTH / 2 - 20,0,20,SCREEN_HEIGHT);
        g2.setColor(Color.getHSBColor(22, 22,22));
        g2.fillRect(SCREEN_WIDTH / 2 - 20,0,20,SCREEN_HEIGHT);

        g2.drawRect(493,595,270,200);
        g2.setColor(Color.getHSBColor(22, 22,22));
        g2.fillRect(493,595,270,200);

        BufferedImage miniMapOne;
        BufferedImage miniMapTwo;


        BufferedImage before = world.getSubimage(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT - 10);
        int w = before.getWidth();
        int h = before.getHeight();
        BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(.2, .2);
        AffineTransformOp scaleOp =
                new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        after = scaleOp.filter(before, after);

        g2.drawImage(after, 500, 600, null);
    }


    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        buffer = world.createGraphics();

        if (MainRoom.currentState == GameState.TITLE_STATE) {
            g.drawImage(menuImg, 0, 0, menuImg.getWidth(), menuImg.getHeight(), null);
        } else if(MainRoom.currentState == GameState.MENU_STATE) {
            g.drawImage(helpImg, 0, 0, helpImg.getWidth(), helpImg.getHeight(), null);
            menu.drawImage(g2);
        }else if(MainRoom.currentState == GameState.POWERUP_STATE){
            g.drawImage(powerupImg, 0, 0, powerupImg.getWidth(), powerupImg.getHeight(), null);
            menu.drawImage(g2);
        }else if(MainRoom.currentState == GameState.MAIN_STATE) {
            drawBackGroundWithTileImage(buffer);
            this.tankOne.drawImage(buffer);
            this.tankTwo.drawImage(buffer);

            launchGame();

            for (int i = 0; i < mapObjects.size(); i++) {
                mapObjects.get(i).drawImage(buffer);
            }

            drawSplitScreen(g2);
            drawMinimap(g2);
            p1S.drawImage(g2, this);
            p2S.drawImage(g2, this);

        }else if(MainRoom.currentState == GameState.GAMEOVER1_STATE){
            g.drawImage(gameOverOneScreen, 0, 0, menuImg.getWidth(), menuImg.getHeight(), null);
            tankOne.setScore(0);
        }else if(MainRoom.currentState == GameState.GAMEOVER2_STATE){
            g2.drawImage(gameOverTwoScreen, 0, 0, menuImg.getWidth(), menuImg.getHeight(), null);
            tankTwo.setScore(0);
        }
    }
}