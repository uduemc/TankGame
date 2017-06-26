package tankgame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.event.KeyListener;
import java.util.Vector;
import javax.swing.JFrame;

class TJFrame extends JFrame{

    MJPanel mjp = null;
    RJPanel rjp = new RJPanel();
    
    Vector tv = new Vector();
    
    public TJFrame() {
        mjp = new MJPanel();
        Thread th = new Thread(mjp);
        th.start();
        
        this.setLayout(new GridBagLayout());
        
        this.add(mjp);
        this.addKeyListener(mjp);
        // this.add(rjp,BorderLayout.EAST);
    }
    
    public void go(){
        this.setTitle("90坦克(Java)");
        this.setSize(MJPanel.width + 6,MJPanel.height + 28);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
