package tankgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
class MJPanel extends JPanel implements KeyListener, Runnable {
    
    final static int width = 600;
    final static int height = 700;
    
    // p1
    ImgTank p1 = null;
    // p2 未做
    Tank p2 = null;
    // 敌人当前所在画布上的坦克
    Vector<ImgTank> pc = new Vector<ImgTank>();
    // 关卡坦克的集合 初始化的时候进行设置 或者中间的时候进行设置
    LinkedList<ImgTank> tanks = null;
    // 关卡
    TheLevel theLevel = new TheLevel();
    // p1 的移动线程
    MoveThread p1move = null;
    // p1 发射子弹线程
    ShotThread p1shot = null;
    // 运行一次的中间件 初始化音乐
    boolean once = true;
    
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
        // 显示关卡
        if (!this.showLevel(gp)) {
            // 开始
            p1.draw(gp, this);
            
            for (int i = 0; i < this.pc.size(); i++) {
                this.pc.get(i).draw(gp, this);
            }
        }
        
    }
    
    public boolean showLevel(Graphics gp) {
        if (this.theLevel.timer() > 0) {
            // 播放音乐
            if (this.once()) {
                Sound.play(this.getClass().getResource("").getPath() + "source/start.wav");
            }
            
            gp.setColor(Color.white);
            gp.setFont(new Font("宋体", Font.PLAIN, 60));
            gp.drawString("第 " + this.theLevel.getLevel() + " 关", 12, 80);
            
            return true;
        }
        return false;
    }
    
    public boolean once() {
        if (this.once == true) {
            this.once = false;
            return true;
        }
        return false;
    }
    
    public void again() {
        this.once = true;
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
