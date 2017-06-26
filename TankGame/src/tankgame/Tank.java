package tankgame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

class Tank extends BaseTank {

    public static Tank myTank() {
        Tank my = new Tank();
        // 坦克大小
        my.setSize(30);
        // 坦克颜色
        my.setColor((new Color(Color.red.getRed(), Color.red.getGreen(),
                Color.red.getBlue(), 128)));
        // 坦克方向
        my.setDirection(1);
        // 坦克坐标�
        my.setX(1);
        my.setLevel(1);
        my.setY(MJPanel.height - my.tankHeight());
        return my;
    }

    public static Tank pcTank(int p) {
        Tank my = new Tank();
        // ���ô�С
        my.setSize(30);
        // ������ɫ
        my.setColor((new Color(Color.lightGray.getRed(), Color.lightGray.getGreen(),
                Color.lightGray.getBlue(), 218)));
        // ���÷���
        my.setDirection(2);
        // ���û�ͼ����� ���½�
        my.setLevel(1);
        if (p % 3 == 0) {
            my.setX(1);
            my.setY(1);
        } else if (p % 3 == 1) {
            my.setX(MJPanel.width / 2 - my.tankWidth() / 2);
            my.setY(1);
        } else if (p % 3 == 2) {
            my.setX(MJPanel.width - my.tankWidth());
            my.setY(1);
        }
        return my;
    }

    public void shot(Graphics gp) {
        // ���ӵ�
        if (this.shot != null) {
            this.shot.draw(gp);

            Thread th = new Thread(this.shot);
            th.start();
        }
    }

    public void fire() {
        // ����
        if (this.shot == null) {
            this.shot = new Shot(this);
        } else if (this.shot.isMove() == false) {
            this.shot = new Shot(this);
        }
    }

    public void draw(Graphics gp) {
        // ����̹����ɫ
        gp.setColor(this.getColor());
        // ��̹���Ĵ�
        this.drawTrack(gp);
        // ��̹���м�Ĳ���
        this.drawMain(gp);
        // ��̹����Ͳ
        this.drawCannon(gp);
    }

    public void drawCannon(Graphics gp) {
        int x = this.getX();
        int y = this.getY();
        int direaction = this.getDirection();

        int x1, x2, y1, y2;
        int thickness = this.levelThickness();

        switch (direaction) {
            case 1:
                x1 = x2 = x + this.tankWidth() / 2;
                y1 = y + thickness / 2;
                y2 = y + this.tankHeight() / 2;
                break;
            case 2:
                x1 = x2 = x + this.tankWidth() / 2;
                y1 = y + this.tankHeight() / 2;
                y2 = y + this.tankHeight() - thickness / 2;
                break;
            case 3:
                y1 = y2 = y + this.tankHeight() / 2 - thickness / 2;
                x1 = x + thickness / 2;
                x2 = x + this.tankWidth() / 2;
                break;
            case 4:
                y1 = y2 = y + this.tankHeight() / 2;
                x1 = x + this.tankWidth() / 2;
                x2 = x + this.tankWidth() - thickness / 2;
                break;
            default:
                x1 = x2 = x + this.tankWidth() / 2;
                y1 = y + thickness / 2;
                y2 = y + this.tankHeight() / 2;
        }
        Graphics2D g2 = (Graphics2D) gp;
        g2.setStroke(new BasicStroke(thickness));
        g2.drawLine(x1, y1, x2, y2);
    }

    public void drawMain(Graphics gp) {
        // ��һ��������
        // �����
        int x = this.getX();
        int y = this.getY();
        int direaction = this.getDirection();

        int rectX;
        int rectY;
        int rectWidth;
        int rectHeight;

        int ovalX;
        int ovalY;
        int ovalLength;

        if (direaction == 1 || direaction == 2) {
            rectX = x + 5;
            rectY = y + (this.tankHeight() - this.levelSize()) / 2;
            rectWidth = this.tankWidth() - 10;
            rectHeight = this.levelSize();
        } else {
            rectX = x + (this.tankWidth() - this.levelSize()) / 2;
            rectY = y + 5;
            rectWidth = this.levelSize();
            rectHeight = this.tankHeight() - 10;
        }
        gp.fillRect(rectX, rectY, rectWidth, rectHeight);

        if (rectWidth > rectHeight) {
            ovalX = rectX + ((rectWidth - rectHeight) / 2);
            ovalY = rectY;
            ovalLength = rectHeight;
        } else if (rectWidth < rectHeight) {
            ovalX = rectX;
            ovalY = rectY + ((rectHeight - rectWidth) / 2);
            ovalLength = rectWidth;
        } else {
            ovalX = rectX;
            ovalY = rectY;
            ovalLength = rectWidth;
        }

        gp.fillOval(ovalX, ovalY, ovalLength, ovalLength);
    }

    public void drawTrack(Graphics gp) {
        int x = this.getX();
        int y = this.getY();
        int direaction = this.getDirection();
        int width;
        int height;
        int x2;
        int y2;
        if (direaction == 1 || direaction == 2) {
            width = 5;
            height = this.getSize();
            x2 = x + this.tankWidth() - 5;
            y2 = y;
        } else {
            width = this.getSize();
            height = 5;
            x2 = x;
            y2 = y + this.tankHeight() - 5;
        }

        gp.fillRect(x, y, width, height);
        gp.fillRect(x2, y2, width, height);
    }
}
