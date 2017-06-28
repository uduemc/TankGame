package tankgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Vector;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
class MJPanel extends JPanel implements KeyListener, Runnable {

    final static int width = 600;
    final static int height = 700;

    ImgTank p1 = null;
    Tank p2 = null;
    Vector<ImgTank> pc = new Vector<ImgTank>();
    LinkedList<ImgTank> tanks = null;
    TheLevel theLevel = new TheLevel();

    MoveThread p1move = null;
    ShotThread p1shot = null;

    public MJPanel() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(MJPanel.width, MJPanel.height));
        this.setBackground(Color.BLACK);
        p1 = ImgTank.peopleTank(1);//Tank.myTank(); // 创建一个绘制的坦克
        theLevel.initTanks();
        pc.add(this.theLevel.getTanks().poll());
    }

    public void paint(Graphics gp) {
        super.paint(gp);
        
        // 开始
        p1.draw(gp, this);
        
        for(int i=0;i<this.pc.size();i++){
            this.pc.get(i).draw(gp, this);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // 键盘按下
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) {

            if (p1move == null) {
                p1move = new MoveThread(e.getKeyCode(), this.p1);
                p1move.start();
            } else {
                if (p1move.getKeyCode() != e.getKeyCode()) {
                    if (p1move.isLive() == true) {
                        p1move.end();
                    }
                    p1move = new MoveThread(e.getKeyCode(), this.p1);
                    p1move.start();
                } else {
                    if (p1move.isLive() == false) {
                        p1move = new MoveThread(e.getKeyCode(), this.p1);
                        p1move.start();
                    }
                }
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_J) {
            if (p1shot == null) {
                p1shot = new ShotThread(e.getKeyCode(), this.p1);
                p1shot.start();
            } else {
                if (p1shot.getKeyCode() != e.getKeyCode()) {
                    if (p1shot.isLive() == true) {
                        p1shot.end();
                    }
                    p1shot = new ShotThread(e.getKeyCode(), this.p1);
                    p1shot.start();
                } else {
                    if (p1shot.isLive() == false) {
                        p1shot = new ShotThread(e.getKeyCode(), this.p1);
                        p1shot.start();
                    }
                }
            }
        }

    }

    // 抬起
    @Override
    public void keyReleased(KeyEvent e) {
        if (p1move != null && p1move.getKeyCode() == e.getKeyCode()) {
            p1move.end();
        }

        if (p1shot != null && p1shot.getKeyCode() == e.getKeyCode()) {
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

    private int fire = 0;

    public ShotThread(int code, ImgTank tk) {
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
                if (this.getFire() <= 0) {
                    this.getTk().fire();
                    this.setFire(2);
                } else {
                    this.setFire(this.getFire() - 1);
                }

            }

            if (!this.isLive()) {
                break;
            }
        }
    }

    /**
     * @return the fire
     */
    public int getFire() {
        return fire;
    }

    /**
     * @param fire the fire to set
     */
    public void setFire(int fire) {
        this.fire = fire;
    }
}

class MoveThread extends BaseThread {

    public MoveThread(int code, ImgTank tk) {
        super(code, tk);
    }

    public void end() {
        this.setLive(false);
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(40);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (this.getKeyCode() == KeyEvent.VK_W) {
                this.getTk().setDirection(1);
                this.getTk().moveU();
            }

            if (this.getKeyCode() == KeyEvent.VK_S) {
                this.getTk().setDirection(2);
                this.getTk().moveD();
            }

            if (this.getKeyCode() == KeyEvent.VK_A) {
                this.getTk().setDirection(3);
                this.getTk().moveL();
            }

            if (this.getKeyCode() == KeyEvent.VK_D) {
                this.getTk().setDirection(4);
                this.getTk().moveR();
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
    private ImgTank tk;

    public BaseThread(int code, ImgTank tk) {
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
    public ImgTank getTk() {
        return tk;
    }

    /**
     * @param tk the tk to set
     */
    public void setTk(ImgTank tk) {
        this.tk = tk;
    }
}
