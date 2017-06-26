
package tankgame;

public class TankGame {

    public static void main(String[] args) {
        TJFrame window = new TJFrame();
        
        Thread th1 = new Thread(window);
        Thread th2 = new Thread(window);
        
        window.vAdd(th1.getId());
        window.vAdd(th2.getId());
        
        
        
        th1.start();
        th2.start();
    }
    
}
