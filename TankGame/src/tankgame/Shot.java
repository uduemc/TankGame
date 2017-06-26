package tankgame;

import java.awt.Color;
import java.awt.Graphics;

public class Shot implements Runnable {

    protected int x;
    protected int y;
    protected boolean move = false;
    protected int direction;
    protected int speed = 1;
    protected Tank tk = null;

    public Tank getTk() {
        return tk;
    }

    public void setTk(Tank tk) {
        this.tk = tk;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isMove() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Shot(Tank tk) {
        this.setTk(tk);
        switch (tk.getDirection()) {
            case 1:
                this.setX(tk.getX() + tk.tankWidth() / 2 - 4);
                this.setY(tk.getY() - 5);
                break;
            case 2:
                this.setX(tk.getX() + tk.tankWidth() / 2 - 4);
                this.setY(tk.getY() + tk.tankHeight() - 2);
                break;
            case 3:
                this.setX(tk.getX() - 4);
                this.setY(tk.getY() + tk.tankHeight() / 2 - 4);
                break;
            case 4:
                this.setX(tk.getX() + tk.tankWidth() - 4);
                this.setY(tk.getY() + tk.tankHeight() / 2 - 4);
                break;
        }
        this.setDirection(tk.getDirection());
        this.setMove(true);
    }

    public void draw(Graphics gp) {
        gp.setColor(Color.white);

        gp.fillOval(this.getX(), this.getY(), 8, 8);

    }

    @Override
    public void run() {
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

            if (this.getX() < 0 || this.getX() > MJPanel.width || this.getY() < 0 || this.getY() > MJPanel.height) {
                this.setMove(false);
            }

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
