package tankgame;

import java.util.Vector;
import javax.swing.JFrame;

class TJFrame extends JFrame implements Runnable {

    MJPanel mjp = new MJPanel();
    RJPanel rjp = new RJPanel();
    
    Vector tv = new Vector();
    
    public TJFrame() {
        this.add(mjp);
        // this.add(rjp,BorderLayout.EAST);
    }
    
    public void vAdd(long id){
        this.tv.add(id);
    }
    
    public void go(){
        this.setTitle("90坦克(Java)");
        this.setSize(MJPanel.width ,MJPanel.height);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void run() {
        while(true){
            System.out.println(Thread.currentThread().getId());
            this.go();
        }
    }
}
