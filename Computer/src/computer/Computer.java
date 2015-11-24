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
    public static Socket serverSocket = null;
    public static BufferedReader is = null;
    public static BufferedWriter os = null;
    public static boolean isBlocked = false;

    public void Computer() {
        System.out.println("Constructor");
    }

    public static void main(String[] args) throws InterruptedException {

        //Inicializacion
        try {
            Computer test = new Computer();
            serverSocket = new Socket("localhost", 2048);
            is = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
        } catch (UnknownHostException e) {
            Log.log(Level.WARNING, "Host not fond");
            Log.log(Level.WARNING, e.toString());
        } catch (IOException e) {
            Log.log(Level.WARNING, "Buffered failed to Open");
            Log.log(Level.WARNING, e.toString());
        }
        doBlock();

        //Comunicacion
        System.out.println("Cliente Iniciado");
        if (serverSocket != null && is != null && os != null) {
            System.out.println("Enviando Mensajes");
            try {
                os.write("Me conecte a ti");
                os.newLine();
                os.flush();

                String message;
                while (true) {
                    message = is.readLine();
                    if (message != null) {
                        System.out.println("Mensaje Servidor: " + message);
                        if(message.equals("blockSignal")){
                            if(isBlocked) undoBlock(frame);
                            else doBlock();
                        }
                    }
                }
            } catch (UnknownHostException e) {
                Log.log(Level.WARNING, "Host not fond");
                Log.log(Level.WARNING, e.toString());
            } catch (IOException e) {
                Log.log(Level.WARNING, "Couldnt Connect to Server");
                Log.log(Level.WARNING, e.toString());
            }
        }

    }

    static public void undoBlock(JFrame frame) {
        isBlocked = false;
        System.out.println("Computadora Desbloqueada");
        if (frame != null) {
            frame.dispose();
            frame = null;
        }
    }

    static public void doBlock() {
            isBlocked = true;
            System.out.println("Computadora Blockeada");
            /*invokeLater(new Runnable() {
             public void run() {
             (frame = new PantallaBloqueo()).setVisible(true);
             }
             });*/
    }

}
