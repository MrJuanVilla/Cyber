package cyber;

import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import javax.imageio.ImageIO;

public class Cyber {

    public static final Logger Log = Logger.getLogger(Cyber.class.getName());
    public static Thread[] threadPool = new Thread[8];
    public static connectionHandler[] classesPool = new connectionHandler[8];

    public static void main(String[] args) {
        Cyber instance = new Cyber();
        instance.main();
    }
    
    public void main() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        int index = 0;

        for (int i = 0; i < threadPool.length; i++) {
            threadPool[i] = null;
        }

        try {
            InitGUI();
        } catch (IOException ex) {
            Log.log(Level.WARNING, "File Exception");
            Log.log(Level.WARNING, ex.toString());
        }

        try {
            serverSocket = new ServerSocket(2048);
            for (;;) {
                index = -1;
                clientSocket = null;
                System.out.println("Esperando conexiones");
                clientSocket = serverSocket.accept();
                forLookUp:
                for (int i = 0; i < threadPool.length; i++) {
                    if (threadPool[i] == null) {
                        index = i;
                        break forLookUp;
                    } else {
                        if (threadPool[i].isInterrupted() || !threadPool[i].isAlive()) {
                            index = i;
                            break forLookUp;
                        }
                    }
                }

                if (index != -1) {
                    classesPool[index] = new connectionHandler(clientSocket, index,this);
                    threadPool[index] = new Thread(classesPool[index]);
                    threadPool[index].start();
                    comps[index].setVisible(true);
                    System.out.println("Conexion Comp " + index + " - Aceptada");
                }

                for (int i = 0; i < threadPool.length; i++) {
                    if (threadPool[i] != null) {
                        if (threadPool[i].isInterrupted() || !threadPool[i].isAlive()) {
                            threadPool[i] = null;
                            classesPool[i] = null;
                            comps[i].setVisible(false);
                        }
                    }
                }
            }

        } catch (IOException e) {
            Log.log(Level.WARNING, "Server Connection Interrupted");
        }
    }
    
    private static JPanel[] comps;
    private static JPanel mainPanel;

    public void InitGUI() throws IOException {
        JFrame frame = new JFrame("GridLayout Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 510));
        frame.setMinimumSize(new Dimension(800, 510));
        frame.setResizable(true);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        BufferedImage image = ImageIO.read(Cyber.class.getResource("/comp.png"));
        comps = new JPanel[threadPool.length];
        for (int i = 0; i < comps.length; i++) {
            comps[i] = createCompController(image, i);
            c.gridx = i % 4;
            c.gridy = i / 4;
            //JPanel label
            JPanel container = new JPanel();
            container.setBorder(BorderFactory.createTitledBorder("Comp" + (i + 1)));
            container.setPreferredSize(new Dimension(190,220));
            container.setMinimumSize(new Dimension(190,220));
            comps[i].setVisible(false);
            container.add(comps[i]);
            mainPanel.add(container, c);
        }
        frame.setContentPane(mainPanel);

        frame.pack();
        frame.setVisible(true);
    }

    public void hideGUI(int index){
        comps[index].setVisible(false);
    }
    
    public JPanel createCompController(BufferedImage image, int index) {
        JPanel cellPanel = new JPanel();
        cellPanel.setLayout(new BorderLayout());
        
        JPanel temp = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(image, 15, 15, 150, 150, this);
            }
        };
        temp.setPreferredSize(new Dimension(150, 150));
        temp.setMinimumSize(new Dimension(150, 150));
        temp.setAlignmentY(SwingConstants.CENTER);
        
        JPanel subPanel = new JPanel();
        subPanel.add(pauseButton(index));
        subPanel.add(new JButton("Shut"));
        subPanel.add(new JButton("$"));
        subPanel.setAlignmentY(SwingConstants.CENTER);
        
        JLabel timer =  new JLabel("");
        timer.setAlignmentY(SwingConstants.CENTER);
        
        cellPanel.add(timer,BorderLayout.NORTH);
        cellPanel.add(temp,BorderLayout.CENTER);
        cellPanel.add(subPanel, BorderLayout.SOUTH);
        return cellPanel;
    }

    public JButton pauseButton(int id) {
        JButton temp = new JButton();
        temp.setText("Start");
        temp.setBounds(new Rectangle(10,40));
        temp.addActionListener(
                new CustomListener(id) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (threadPool[id] != null) {
                            if (threadPool[id].isAlive() && !threadPool[id].isInterrupted()) {
                                if (classesPool[id] != null) {
                                    classesPool[id].BlockSignal();
                                    if(classesPool[id].isBlock) temp.setText("Start");
                                    else temp.setText("Stop");
                                }
                            }
                        }
                    }
                }
        );
        return temp;
    }
}

class CustomListener implements ActionListener {

    private final int computerId;
    private boolean stop = true;

    public CustomListener(int computerId) {
        this.computerId = computerId;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
