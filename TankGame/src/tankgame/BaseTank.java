package tankgame;

import java.awt.Color;
import java.awt.Graphics;

abstract class BaseTank {

    protected int x;
    protected int y;

    // people 0-���� 1-p1
    protected int people = 0;
    // ��ɫ
    protected Color color;
    // ��С
    protected int size;
    // ���� 1�� 2�� 3�� 4��
    protected int direction;
    // ���� Ĭ��Ϊ1
    protected int hp = 1;
    // �ȼ�
    protected int level = 1;
    // �ٶ�
    protected int speed = 5;
    // �ӵ�
    protected Shot shot = null;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    //w
    public int tankWidth() {
        int width;
        if (this.getDirection() == 1 || this.getDirection() == 2) {
            width = (int) (this.getSize() * 0.815);
        } else {
            width = this.getSize();
        }
        return width;
    }

    // ��ȡ̹�˸߶�
    public int tankHeight() {
        int height;
        if (this.getDirection() == 1 || this.getDirection() == 2) {
            height = this.getSize();
        } else {
            height = (int) (this.getSize() * 0.815);
        }
        return height;
    }

    //
    public int levelSize() {
        int size;
        switch (this.getLevel()) {
            case 1:
                size = 16;
                break;
            case 2:
                size = 16;
                break;
            case 3:
                size = 18;
                break;
            case 4:
                size = 18;
                break;
            default:
                size = 16;
        }
        return size;
    }

    //
    public int levelThickness() {
        int thickness;
        switch (this.getLevel()) {
            case 1:
                thickness = 2;
                break;
            case 2:
                thickness = 4;
                break;
            case 3:
                thickness = 2;
                break;
            case 4:
                thickness = 4;
                break;
            default:
                thickness = 2;
        }
        return thickness;
    }

    public void moveA() {
        int x = this.getX();
        x -= this.getSpeed();
        if (x > MJPanel.width - this.tankWidth()) {
            x = MJPanel.width - this.tankWidth();
        } else if (x < 0) {
            x = 0;
        }
        this.setX(x);
    }

    public void moveD() {
        int x = this.getX();
        x += this.getSpeed();
        if (x > MJPanel.width - this.tankWidth()) {
            x = MJPanel.width - this.tankWidth();
        } else if (x < 0) {
            x = 0;
        }
        this.setX(x);
    }

    public void moveW() {
        int y = this.getY();
        y -= this.getSpeed();
        if (y < 0) {
            y = 0;
        } else if (y > MJPanel.height - this.tankHeight()) {
            y = MJPanel.height - this.tankHeight();
        }
        this.setY(y);
    }

    public void moveS() {
        int y = this.getY();
        y += this.getSpeed();
        if (y < 0) {
            y = 0;
        } else if (y > MJPanel.height - this.tankHeight()) {
            y = MJPanel.height - this.tankHeight();
        }
        this.setY(y);
    }

    abstract public void draw(Graphics g);

}
