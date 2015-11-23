package cyber;

import java.util.logging.Logger;
import javax.swing.UIManager.LookAndFeelInfo;
import static javax.swing.UIManager.getInstalledLookAndFeels;
import static javax.swing.UIManager.setLookAndFeel;
import static java.awt.EventQueue.invokeLater;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Cyber {

    public static Logger Log = Logger.getLogger(Cyber.class.getName());

    public static void main(String[] args) {
        Thread[] threadPool = new Thread[8];
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        int index = 0;
        
        for(int i = 0; i < threadPool.length; i++){
            threadPool[i] = null;
        }
        
        try {
            serverSocket = new ServerSocket(2048);
            for (;;) {
                index = 0;
                clientSocket = null;
                System.out.println("Esperando conexiones");
                clientSocket = serverSocket.accept();
                
                forLookUp: for (int i = 0; i < threadPool.length; i++) {
                    if (threadPool[i] == null) {
                        index = i;
                        break forLookUp;
                    } else {
                        if (!threadPool[i].isAlive()) {
                            index = i;
                            break forLookUp;
                        }
                        index = -1;
                    }
                }
                
                if (index != -1) {
                    threadPool[index] = new Thread(new connectionHandler(clientSocket, index));
                    threadPool[index].start();
                    System.out.println("Conexion del Computador " + index + " - Aceptada");
                }
            }
        } catch (IOException e) {
            Log.log(Level.WARNING, "Server Connection Acceptance Interrupted");
        }
    }

}
