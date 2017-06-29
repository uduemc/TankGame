package tankgame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author Administrator
 */
public class Blast {

    private static Blast _self = null;
    private Vector<BlastData> bData = new Vector<BlastData>();

    // 检测有子弹击中坦克时的情况
    public static void runtimes(ImgTank p1, ImgTank p2, Vector<ImgTank> pc) {
        // 验证 p1 的子弹是否打中敌人
        Iterator<ImgShot> it = p1.getShots().iterator();
        while (it.hasNext()) {
            ImgShot stmp = it.next();
            // 敌人坦克
            Iterator<ImgTank> tk = pc.iterator();
            while (tk.hasNext()) {
                ImgTank ttmp = tk.next();
                // 是否有碰到
                if (Blast.isBlast(stmp, ttmp)) {
                    Blast.self().add(p1, stmp, ttmp);
                }
            }
        }
    }

    // 击中
    public static void draw(Graphics gp) {
        Iterator<BlastData> bdit = Blast.self().getbData().iterator();
        while (bdit.hasNext()) {
            BlastData blastData = bdit.next();
            // 消除子弹
            blastData.getFrom().getShots().remove(blastData.getShot());
            // 加入子弹碰撞声音
            Sound.play(Sound.class.getResource("").getPath() + "source/hit.wav");
            // 被攻击的坦克生命值减 1
            blastData.getTo().setHp(blastData.getTo().getHp() - 1);
            bdit.remove();
        }
    }

    // 检测子弹跟坦克是否有碰撞
    public static boolean isBlast(ImgShot shot, ImgTank tank) {
        boolean bool = false;
        if ((shot.getX() > (tank.getX() - shot.getSize()) && shot.getX() < (tank.getX() + tank.getSize() + shot.getSize())) && (shot.getY() < (tank.getY() + tank.getSize() + shot.getSize()) && shot.getY() > (tank.getY() - shot.getY()))) {
            bool = true;
        }
        return bool;
    }

    public void add(ImgTank from, ImgShot shot, ImgTank tank) {
        this.getbData().add(new BlastData(from, shot, tank));
    }

    public static Blast self() {
        if (Blast._self == null) {
            Blast._self = new Blast();
        }
        return Blast._self;
    }

    /**
     * @return the bData
     */
    public Vector<BlastData> getbData() {
        return bData;
    }

    /**
     * @param bData the bData to set
     */
    public void setbData(Vector<BlastData> bData) {
        this.bData = bData;
    }

}

class DrawBlast extends Thread {

    private int x;
    private int y;
    private int timer = 41;

    public DrawBlast(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Image getImage(){
        if(this.getTimer() == 41){
            Sound.play(Sound.class.getResource("").getPath() + "source/blast.wav");
        }
        this.timer();
        if(this.getTimer() > 35){
            return Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/blast8.gif"));
        }
        else if(this.getTimer() > 30){
            return Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/blast7.gif"));
        }
        else if(this.getTimer() > 25){
            return Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/blast6.gif"));
        }
        else if(this.getTimer() > 20){
            return Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/blast5.gif"));
        }
        else if(this.getTimer() > 15){
            return Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/blast4.gif"));
        }
        else if(this.getTimer() > 10){
            return Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/blast3.gif"));
        }
        else if(this.getTimer() > 5){
            return Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/blast2.gif"));
        }
        else{
            return Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/blast1.gif"));
        }
        
    }

    public void timer() {
        this.setTimer(this.getTimer() - 1);
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
     * @return the timer
     */
    public int getTimer() {
        return timer;
    }

    /**
     * @param timer the timer to set
     */
    public void setTimer(int timer) {
        this.timer = timer;
    }
}

class BlastData {

    private ImgTank from;
    private ImgShot shot;
    private ImgTank to;

    public BlastData(ImgTank from, ImgShot shot, ImgTank to) {
        this.from = from;
        this.shot = shot;
        this.to = to;
    }

    /**
     * @return the from
     */
    public ImgTank getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(ImgTank from) {
        this.from = from;
    }

    /**
     * @return the shot
     */
    public ImgShot getShot() {
        return shot;
    }

    /**
     * @param shot the shot to set
     */
    public void setShot(ImgShot shot) {
        this.shot = shot;
    }

    /**
     * @return the to
     */
    public ImgTank getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(ImgTank to) {
        this.to = to;
    }
}
