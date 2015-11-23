package cyber;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class connectionHandler implements Runnable {

    public static Logger Log = Logger.getLogger(connectionHandler.class.getName());
    private final Socket clientSocket;
    private final int computerId;
    BufferedReader is = null;
    BufferedWriter os = null;

    connectionHandler(Socket clientSocket, int computerId) {
        this.clientSocket = clientSocket;
        this.computerId = computerId;
    }

    public void run() {
        System.out.println("Run Started");
        try {
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            /*Area de comunicacion entre sockets.
            String nextline;

            nextline = is.readLine();
            if (nextline != null) {
                System.out.println("Recibi: " + nextline);
            }

            os.write("block");
            os.newLine();
            os.flush();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Wrong");
            }

            os.write("unblock");
            os.newLine();
            os.flush();
`           */
        } catch (IOException e) {
            Log.log(Level.WARNING, "Computer " + computerId + ": Connection Lost");
            Log.log(Level.WARNING, e.toString());
        }
    }

}
