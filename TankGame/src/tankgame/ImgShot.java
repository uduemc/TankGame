package tankgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class ImgShot extends NewShot implements Runnable {

    public ImgShot(ImgTank tank, Image img) {
        super(tank, img);
        switch (this.getDirection()) {
            case 1:
                this.setX(this.getTank().getX() + this.getTank().getSize() / 2 - this.getSize() / 2);
                this.setY(this.getTank().getY() - this.getSize() / 2);
                break;
            case 2:
                this.setX(this.getTank().getX() + this.getTank().getSize() / 2 - this.getSize() / 2);
                this.setY(this.getTank().getY() + this.getTank().getSize() - this.getSize() / 2);
                break;
            case 3:
                this.setX(this.getTank().getX() - this.getSize() / 2);
                this.setY(this.getTank().getY() + this.getTank().getSize() / 2 - this.getSize() / 2);
                break;
            case 4:
                this.setX(this.getTank().getX() + this.getTank().getSize() - this.getSize() / 2);
                this.setY(this.getTank().getY() + this.getTank().getSize() / 2 - this.getSize() / 2);
                break;
        }
        Thread th = new Thread(this);
        th.start();
    }

    public int getPeople() {
        return this.getTank().getPeople();
    }

    @Override
    public void run() {
        if (this.isMove()) {
            while (true) {
                switch (this.getDirection()) {
                    case 1:
                        this.setY(this.getY() - this.getSpeed());
                        break;
                    case 2:
                        this.setY(this.getY() + this.getSpeed());
                        break;
                    case 3:
                        this.setX(this.getX() - this.getSpeed());
                        break;
                    case 4:
                        this.setX(this.getX() + this.getSpeed());
                        break;
                }

                // 判断自动是否遇到墙壁
                if (this.getX() < 0 || this.getX() > MJPanel.width || this.getY() < 0 || this.getY() > MJPanel.height) {
                    // 子弹碰撞
                    Sound.play(this.getClass().getResource("").getPath() + "source/hit.wav");
                    this.setMove(false);
                    this.getTank().getShots().remove(this);
                }

                // 判断是否遇到敌对坦克
                if (!this.isMove()) {
                    break;
                }

                try {
                    Thread.sleep(20);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void draw(Graphics gp, MJPanel imgobs) {
        gp.drawImage(this.getImg(), this.getX(), this.getY(), this.getSize(), this.getSize(), imgobs);
    }

}

abstract class NewShot {

    private int x;
    private int y;
    private boolean move = false;
    private int direction;
    private int speed = 5;
    private int size = 12;
    private ImgTank tank = null;
    private Image img = null;

    public NewShot(ImgTank tank, Image img) {
        this.setTank(tank);
        this.setImg(img);
        this.setMove(true);
        this.setDirection(tank.getDirection());
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the move
     */
    public boolean isMove() {
        return move;
    }

    /**
     * @param move the move to set
     */
    public void setMove(boolean move) {
        this.move = move;
    }

    /**
     * @return the direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * @return the tank
     */
    public ImgTank getTank() {
        return tank;
    }

    /**
     * @param tank the tank to set
     */
    public void setTank(ImgTank tank) {
        this.tank = tank;
    }

    /**
     * @return the img
     */
    public Image getImg() {
        return img;
    }

    /**
     * @param img the img to set
     */
    public void setImg(Image img) {
        this.img = img;
    }

    abstract public void draw(Graphics gp, MJPanel imgobs);

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }
}
