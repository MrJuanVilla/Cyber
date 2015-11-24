package cyber;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class connectionHandler implements Runnable {

    public static Logger Log = Logger.getLogger(connectionHandler.class.getName());
    private final Socket clientSocket;
    private final int computerId;
    private Cyber father = null;
    private BufferedReader is = null;
    private BufferedWriter os = null;
    public boolean isBlock;

    connectionHandler(Socket clientSocket, int computerId, Cyber father) {
        this.clientSocket = clientSocket;
        this.computerId = computerId;
        this.father = father;
        this.isBlock = false;
        try {
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            Log.log(Level.WARNING, "Coulndt Initialize Comp " + identify() + " Buffers");
            Log.log(Level.WARNING, e.toString());
            father.hideGUI(identify());
            Thread.currentThread().interrupt();
        }
    }

    public void run() {
        System.out.println("Comunication Comp " + identify());
        try {
            String nextline;
            while (true) {
                nextline = is.readLine();
                if (nextline != null) {
                    System.out.println("Comp " + identify() + ": " + nextline);
                }
            }

        } catch (IOException e) {
            father.hideGUI(identify());
            Log.log(Level.WARNING, "Computer " + computerId + ": Connection Lost");
            Log.log(Level.WARNING, e.toString());

        }
    }

    public void BlockSignal() {
        System.out.println("Attempting to Block Computer: " + identify());
        try {
            os.write("blockSignal");
            os.newLine();
            os.flush();
        } catch (IOException e) {
            Log.log(Level.WARNING, "Couldnt Send Block Message");
            Log.log(Level.WARNING, "Computer " + identify() + ": " + e.toString());
        }
        father.hideGUI(identify());
        father.reset(identify());
    }

    public int identify() {
        return this.computerId;
    }

}
