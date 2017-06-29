package tankgame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

class ImgTank extends NewTank {

    @Override // 画画
    public void draw(Graphics gp, MJPanel imgobs) {
        if (this.isInit()) {
            // 坦克出生时的动态图片效果。并且这个时候坦克不能移动
            gp.drawImage(this.getInitimg()[this.getInitimer() % 4], this.getX(), this.getY(), this.getSize(), this.getSize(), imgobs);
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(ImgTank.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.setInitimer(this.getInitimer() - 1);
            if(this.getInitimer() <= 0){
                this.setInit(false);
            }
        } else {
            String key = String.valueOf(this.getHp()) + String.valueOf(this.getDirection());
            HashMap<String, Image> imgs = this.getImg();
            gp.drawImage(imgs.get(key), this.getX(), this.getY(), this.getSize(), this.getSize(), imgobs);
            // 画子弹
            for (int i = 0; i < this.getShots().size(); i++) {
                this.getShots().get(i).draw(gp, imgobs);
            }
        }
    }

    public static ImgTank enemy1Tank() {
        ImgTank tank = new ImgTank();
        tank.setHp(1);
        tank.setSize(40);
        tank.setDirection(2);
        tank.setSpeed(4);
        tank.setPeople(0);
        tank.setShot_num(1);
        tank.setX(0);
        tank.setY(0);
        HashMap<String, Image> img = new HashMap<String, Image>();
        img.put("11", Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/enemy1U.gif")));
        img.put("12", Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/enemy1D.gif")));
        img.put("13", Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/enemy1L.gif")));
        img.put("14", Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/enemy1R.gif")));
        tank.setImg(img);
        return tank;
    }

    public static ImgTank enemy2Tank() {
        ImgTank tank = new ImgTank();
        tank.setHp(1);
        tank.setSize(40);
        tank.setDirection(2);
        tank.setSpeed(6);
        tank.setPeople(0);
        tank.setShot_num(2);
        tank.setX(0);
        tank.setY(0);
        HashMap<String, Image> img = new HashMap<String, Image>();
        img.put("11", Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/enemy2U.gif")));
        img.put("12", Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/enemy2D.gif")));
        img.put("13", Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/enemy2L.gif")));
        img.put("14", Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/enemy2R.gif")));
        tank.setImg(img);
        return tank;
    }

    // people tank
    public static ImgTank peopleTank(int p) {
        ImgTank tank = new ImgTank();
        tank.setHp(1);
        tank.setSize(40);
        tank.setDirection(1);
        tank.setSpeed(4);
        tank.setPeople(p);
        tank.setShot_num(2);
        try {
            tank.setImg(ImgTank.peopleImgSource(p));
        } catch (Exception ex) {
            Logger.getLogger(ImgTank.class.getName()).log(Level.SEVERE, null, ex);
        }
        tank.setX(0);
        tank.setY(MJPanel.height - tank.getSize());
        return tank;
    }

    protected static HashMap<String, Image> peopleImgSource(int p) throws Exception {
        HashMap<String, Image> img = new HashMap<String, Image>();

        if (p == 1) {
            // 向上
            img.put("11", Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/p1tankU.gif")));
            // 向下
            img.put("12", Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/p1tankD.gif")));
            // 向左
            img.put("13", Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/p1tankL.gif")));
            // 向右
            img.put("14", Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/p1tankR.gif")));
        } else if (p == 2) {
            // 向上
            img.put("11", Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/p2tankU.gif")));
            // 向下
            img.put("12", Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/p2tankD.gif")));
            // 向左
            img.put("13", Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/p2tankL.gif")));
            // 向右
            img.put("14", Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/p2tankR.gif")));
        } else {
            throw new Exception("未定义 people: " + p);
        }

        return img;
    }

    public void fire() {
        
        if (this.getShots().size() < this.getShot_num() && this.isInit() == false) {
            Image img;
            // 创建一颗子弹放入到子弹向量中
            if (this.getPeople() == 0) {
                img = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/enemymissile.gif"));
            } else {
                img = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/tankmissile.gif"));
            }
            Sound.play(this.getClass().getResource("").getPath() + "source/fire.wav");
            this.getShots().add(new ImgShot(this, img));
        }

    }

    public void moveU() {
        if (this.isInit() == false) {
            int y = this.getY() - this.getSpeed();
            if (y < 0) {
                y = 0;
            }
            this.setY(y);
        }
    }

    public void moveD() {
        if (this.isInit() == false) {
            int y = this.getY() + this.getSpeed();
            if (y > MJPanel.height - this.getSize()) {
                y = MJPanel.height - this.getSize();
            }
            this.setY(y);
        }
    }

    public void moveL() {
        if (this.isInit() == false) {
            int x = this.getX() - this.getSpeed();
            if (x < 0) {
                x = 0;
            }
            this.setX(x);
        }
    }

    public void moveR() {
        if (this.isInit() == false) {
            int x = this.getX() + this.getSpeed();
            if (x > MJPanel.width - this.getSize()) {
                x = MJPanel.width - this.getSize();
            }
            this.setX(x);
        }
    }
}

abstract class NewTank {

    private int x;
    private int y;
    private int hp = 1;
    private int people = 0;
    private int direction = 1;
    private int speed = 10;
    private int size = 40;
    private int shot_num = 2;
    private Vector<ImgShot> shots = new Vector<ImgShot>();
    private HashMap<String, Image> img = new HashMap<String, Image>();
    private boolean init = true;
    private int initimer = 16;
    private Image[] initimg = new Image[4];

    public NewTank() {
        initimg[0] = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/born1.gif"));
        initimg[1] = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/born2.gif"));
        initimg[2] = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/born3.gif"));
        initimg[3] = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tankgame/source/born4.gif"));
    }

    /**
     * @return the hp
     */
    public int getHp() {
        return hp;
    }

    /**
     * @param hp the hp to set
     */
    public void setHp(int hp) {
        this.hp = hp;
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

    /**
     * @return the img
     */
    public HashMap<String, Image> getImg() {
        return img;
    }

    /**
     * @param img the img to set
     */
    public void setImg(HashMap<String, Image> img) {
        this.img = img;
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

    abstract public void draw(Graphics gp, MJPanel imgobs);

    abstract public void fire();

    abstract public void moveU();

    abstract public void moveD();

    abstract public void moveL();

    abstract public void moveR();

    /**
     * @return the shot_num
     */
    public int getShot_num() {
        return shot_num;
    }

    /**
     * @param shot_num the shot_num to set
     */
    public void setShot_num(int shot_num) {
        this.shot_num = shot_num;
    }

    /**
     * @return the shots
     */
    public Vector<ImgShot> getShots() {
        return shots;
    }

    /**
     * @param shots the shots to set
     */
    public void setShots(Vector<ImgShot> shots) {
        this.shots = shots;
    }

    /**
     * @return the people
     */
    public int getPeople() {
        return people;
    }

    /**
     * @param people the people to set
     */
    public void setPeople(int people) {
        this.people = people;
    }

    /**
     * @return the init
     */
    public boolean isInit() {
        return init;
    }

    /**
     * @param init the init to set
     */
    public void setInit(boolean init) {
        this.init = init;
    }

    /**
     * @return the initimer
     */
    public int getInitimer() {
        return initimer;
    }

    /**
     * @return the initimg
     */
    public Image[] getInitimg() {

        return initimg;
    }

    /**
     * @param initimer the initimer to set
     */
    public void setInitimer(int initimer) {
        this.initimer = initimer;
    }

    /**
     * @param initimg the initimg to set
     */
    public void setInitimg(Image[] initimg) {
        this.initimg = initimg;
    }

}
