/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankgame;

import java.util.LinkedList;

/**
 *
 * @author Administrator
 */
public class TheLevel {

    private int level = 1;
    private LinkedList<ImgTank> tanks = new LinkedList<ImgTank>();

    public void initTanks() {
        switch (this.getLevel()) {
            case 1:
                this.level_1();
                break;
        }
    }

    // 第一关敌人的坦克
    public void level_1() {
        // tanks.offer();
        for (int i = 0; i < 18; i++) {
            getTanks().offer(ImgTank.enemy1Tank());
        }
        for (int i = 0; i < 2; i++) {
            getTanks().offer(ImgTank.enemy2Tank());
        }
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return the tanks
     */
    public LinkedList<ImgTank> getTanks() {
        return tanks;
    }

    /**
     * @param tanks the tanks to set
     */
    public void setTanks(LinkedList<ImgTank> tanks) {
        this.tanks = tanks;
    }
}
