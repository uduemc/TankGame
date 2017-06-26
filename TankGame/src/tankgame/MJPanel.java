package tankgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
class MJPanel extends JPanel implements KeyListener, Runnable {

    final static int width = 600;
    final static int height = 700;

    Tank p1 = null;
    Tank p2 = null;

    MoveThread p1move = null;
    ShotThread p1shot = null;

    public MJPanel() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(MJPanel.width, MJPanel.height));
        this.setBackground(Color.BLACK);
        p1 = Tank.myTank();
    }

    public void paint(Graphics gp) {
        super.paint(gp);

        p1.draw(gp);
        p1.shot(gp);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // 键盘按下
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) {
            if (p1move != null && p1move.isLive() == true) {
                p1move.end();
            }
            p1move = new MoveThread(e.getKeyCode(), this.p1);
            p1move.start();
        }

        if (e.getKeyCode() == KeyEvent.VK_J) {
            p1shot = new ShotThread(e.getKeyCode(), this.p1);
            p1shot.start();
        }

    }

    // 抬起
    @Override
    public void keyReleased(KeyEvent e) {
        if ( p1move != null && p1move.getKeyCode() == e.getKeyCode()) {
            p1move.end();
        }
        if ( p1shot != null && p1shot.getKeyCode() == e.getKeyCode()) {
            p1shot.end();
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(20);
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.repaint();
        }
    }
}

class ShotThread extends BaseThread {

    public ShotThread(int code, Tank tk) {
        super(code, tk);
    }

    public void end() {
        this.setLive(false);
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (this.getKeyCode() == KeyEvent.VK_J) {
                this.getTk().fire();
            }

            if (!this.isLive()) {
                break;
            }
        }
    }
}

class MoveThread extends BaseThread {

    public MoveThread(int code, Tank tk) {
        super(code, tk);
    }

    public void end() {
        this.setLive(false);
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (this.getKeyCode() == KeyEvent.VK_W) {
                this.getTk().setDirection(1);
                this.getTk().moveW();
            }

            if (this.getKeyCode() == KeyEvent.VK_S) {
                // ��
                this.getTk().setDirection(2);
                this.getTk().moveS();
            }

            if (this.getKeyCode() == KeyEvent.VK_A) {
                // ��
                this.getTk().setDirection(3);
                this.getTk().moveA();
            }

            if (this.getKeyCode() == KeyEvent.VK_D) {
                // ��
                this.getTk().setDirection(4);
                this.getTk().moveD();
            }

            if (!this.isLive()) {
                break;
            }
        }
    }

}

class BaseThread extends Thread {

    private int keyCode;
    private boolean live = true;
    private Tank tk;

    public BaseThread(int code, Tank tk) {
        this.keyCode = code;
        this.tk = tk;
    }

    /**
     * @return the keyCode
     */
    public int getKeyCode() {
        return keyCode;
    }

    /**
     * @param keyCode the keyCode to set
     */
    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    /**
     * @return the live
     */
    public boolean isLive() {
        return live;
    }

    /**
     * @param live the live to set
     */
    public void setLive(boolean live) {
        this.live = live;
    }

    /**
     * @return the tk
     */
    public Tank getTk() {
        return tk;
    }

    /**
     * @param tk the tk to set
     */
    public void setTk(Tank tk) {
        this.tk = tk;
    }
}
