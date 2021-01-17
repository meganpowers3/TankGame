
/**
 *
 * MapInterpreter.java
 * Written by: Megan (Em) Powers
 *
 * Acts to read in an external text file and utilize certain characters as markers for object placement.
 *
*/

package GameObject;

import GameObject.PlayerObjectClasses.BreakableWall;
import GameObject.PlayerObjectClasses.UnbreakableWall;
import GameObject.PlayerObjectClasses.Powerup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MapInterpreter{
    int begin;
    Integer currentLetter;
    String mapFile;
    BufferedReader map;
    int width, height;

    public MapInterpreter(String mapFile){
        this.mapFile = mapFile;
        String currLine;
        try {
            map = new BufferedReader(new InputStreamReader(MainRoom.class.getClassLoader().getResource("world.txt").openStream()));
            currLine = map.readLine();
            width = currLine.length();
            height = 0;

            while(currLine != null){
                height = height + 1;
                currLine = map.readLine();
            }
            map.close();
        } catch (IOException e) {
            System.out.println("Resource not found");
            e.printStackTrace();
        }
    }

    public void read(Object theObject) {

    }


    public void load(){
        MainRoom mainGame = MainRoom.getRoom();

        try {
            map = new BufferedReader(new InputStreamReader(MainRoom.class.getClassLoader().getResource("world.txt").openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String currLine;

        try {
            currLine = map.readLine();
            width = currLine.length();
            height = -30;

            while(currLine != null){
                for(int i = 0, j = currLine.length(); i < j; i++){
                    char c = currLine.charAt(i);

                    if(c == 'b'){

                        UnbreakableWall wall = new UnbreakableWall(1, 1, i*30, height);
                        mainGame.addObjects(wall);
                    }
                    if(c == 'u'){

                        BreakableWall wall = new BreakableWall(1, 1, i*30, height);
                        mainGame.addObjects(wall);
                    }
                    if(c == 'h'){
                        Powerup health = new Powerup(i*30, height, true, false, false, false, false, true );
                        mainGame.addObjects(health);
                    }
                    if(c == 'n'){
                        Powerup p1shield = new Powerup(i*30, height, false, false, false, true, false, true);
                        mainGame.addObjects(p1shield);
                    }
                    if(c == 'm'){
                        Powerup p2shield = new Powerup(i*30, height, false, false, false, false, true, true);
                        mainGame.addObjects(p2shield);
                    }
                    if(c == 'r'){
                        Powerup rocketPickup = new Powerup(i*30, height, false, false, true, false, false, true );
                        mainGame.addObjects(rocketPickup);
                    }
                    if(c == 'o'){
                        Powerup bouncingPickup = new Powerup(i*30, height, false, true, false, false, false, true );
                        mainGame.addObjects(bouncingPickup);
                    }
                }
                height = height + 30;
                currLine = map.readLine();
            }
            map.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
