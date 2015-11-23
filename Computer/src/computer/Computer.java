package computer;

import computer.block.PantallaBloqueo;
import static java.awt.EventQueue.invokeLater;
import java.io.*;
import java.net.*;
import java.util.logging.*;
import javax.swing.JFrame;
import javax.swing.UIManager;
import static javax.swing.UIManager.getInstalledLookAndFeels;
import static javax.swing.UIManager.setLookAndFeel;

public class Computer {
    public static Logger Log = Logger.getLogger(Computer.class.getName());
    public static JFrame frame;

    public static void main(String[] args) throws InterruptedException {
        Socket serverSocket = null;
        BufferedReader is = null;
        BufferedWriter os = null;
        
        doBlock();

        try {
            serverSocket = new Socket("localhost", 2048);
            is = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
            System.out.println("Conexion iniciando");
        } catch (UnknownHostException e) {
            Log.log(Level.WARNING, "Host not fond");
            Log.log(Level.WARNING, e.toString());
        } catch (IOException e) {
            Log.log(Level.WARNING, "Couldnt Connect to Server");
            Log.log(Level.WARNING, e.toString());
        }

        
        if (serverSocket != null && is != null && os != null) {
            System.out.println("Enviando Mensajes");
            try {
                os.write("Me conecte a ti");
                os.newLine();
                os.flush();
                
                /*Area de Comunicacion entre sockets
                String responseLine;
                while((responseLine = is.readLine()) != null){
                    System.out.println("Soy Compu: " + responseLine);
                    if(responseLine.equals("block")){
                        doBlock();
                    }
                    if(responseLine.equals("unblock")){
                        undoBlock(frame);
                        os.write("Desbloqueado");
                        os.newLine();
                        os.flush();
                    }
                }*/
                
            } catch (UnknownHostException e) {
                Log.log(Level.WARNING, "Host not fond");
                Log.log(Level.WARNING, e.toString());
            } catch (IOException e) {
                Log.log(Level.WARNING, "Couldnt Connect to Server");
                Log.log(Level.WARNING, e.toString());
            }
        }
        
    }

    static public void undoBlock(JFrame frame){
        if (frame != null) {
            frame.dispose();
        }
    }
    
    static public void doBlock() {
        invokeLater(new Runnable() {
            public void run() {
                (frame = new PantallaBloqueo()).setVisible(true);
            }
        });
    }

}
