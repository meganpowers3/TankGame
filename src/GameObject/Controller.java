/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObject;


import GameObject.PlayerObjectClasses.Tank;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author anthony-pc
 * @additions made by Em Powers
 *
 * Controls key events.
 *
 */

public class Controller implements KeyListener {

    private Tank tank;
    private final int up;
    private final int down;
    private final int right;
    private final int left;
    private final int shoot;
    private final int fireBomb;
    private final int fireRocket;

    public Controller(Tank tank, int up, int down, int left, int right, int shoot, int fireBomb, int fireRocket) {
        this.tank = tank;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.shoot = shoot;
        this.fireBomb = fireBomb;
        this.fireRocket = fireRocket;
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyPressed = ke.getKeyCode();
        if (keyPressed == up) {
            this.tank.toggleUpPressed();
        }
        if (keyPressed == down) {
            this.tank.toggleDownPressed();
        }
        if (keyPressed == left) {
            this.tank.toggleLeftPressed();
        }
        if (keyPressed == right) {
            this.tank.toggleRightPressed();
        }
        if (keyPressed == shoot) {
            this.tank.toggleShoot();
        }

        if (keyPressed == fireBomb) {
            this.tank.toggleBomb();
        }

        if (keyPressed == fireRocket) {
            this.tank.toggleRocket();
        }

        if (keyPressed == KeyEvent.VK_P) {
            MainRoom.currentState = MainRoom.GameState.MAIN_STATE;
        }

        if (keyPressed == KeyEvent.VK_H) {
            MainRoom.currentState = MainRoom.GameState.MENU_STATE;
        }

        if (keyPressed == KeyEvent.VK_RIGHT && MainRoom.currentState == MainRoom.GameState.MENU_STATE) {
            MainRoom.currentState = MainRoom.GameState.POWERUP_STATE;
        }

        if (keyPressed == KeyEvent.VK_LEFT && MainRoom.currentState == MainRoom.GameState.POWERUP_STATE) {
            MainRoom.currentState = MainRoom.GameState.MENU_STATE;
        }

        if(MainRoom.currentState == MainRoom.GameState.GAMEOVER1_STATE || MainRoom.currentState == MainRoom.GameState.GAMEOVER2_STATE) {
            if (keyPressed == KeyEvent.VK_SHIFT) {
                MainRoom.currentState = MainRoom.GameState.MENU_STATE;
            }
            if (keyPressed == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int keyReleased = ke.getKeyCode();
        if (keyReleased  == up) {
            this.tank.unToggleUpPressed();
        }
        if (keyReleased == down) {
            this.tank.unToggleDownPressed();
        }
        if (keyReleased  == left) {
            this.tank.unToggleLeftPressed();
        }
        if (keyReleased  == right) {
            this.tank.unToggleRightPressed();
        }
        if (keyReleased  == shoot) {
            this.tank.unToggleShoot();
        }
        if (keyReleased  == fireRocket) {
            this.tank.unToggleRocket();
        }

        if (keyReleased  == fireBomb) {
            this.tank.unToggleBomb();
        }
    }
}