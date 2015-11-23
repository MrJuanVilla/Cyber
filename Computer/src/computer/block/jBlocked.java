package computer.block;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;

public class jBlocked {
    
    private JFrame jframe=null;
    public jBlocked( JFrame frame ){
        this.jframe = frame;
    }
    
    public void block(){
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate( new Runnable() {
                @Override
                public void run() {                   
                    front();
                }
              }, 500, 50 , TimeUnit.MILLISECONDS );
    }
    
    public void front(){
        jframe.setExtendedState( JFrame.MAXIMIZED_BOTH );
        jframe.toFront();
    }
    
}