package computer.block;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.BorderFactory.createEtchedBorder;

public class PantallaBloqueo extends JFrame {
    
    private JButton btnSalir;
    private JPanel jPanel1;
    
    public PantallaBloqueo() {
        this.setUndecorated(true);//Quita Bordes
        initComponents();
        this.setDefaultCloseOperation( DO_NOTHING_ON_CLOSE  );//Evita cerrar la pantalla de bloqueo con ALT + C
        this.setExtendedState( MAXIMIZED_BOTH );//Maximizado
        this.setAlwaysOnTop(true);//Al frente    
        new jBlocked( this ).block(); //Instancia jBlocked con la funcion bloqueo.
    }
    
    @SuppressWarnings("unchecked")
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        GridBagConstraints gridBagConstraints;
        jPanel1 = new JPanel();
        btnSalir = new JButton();
        
        jPanel1.setBackground(new Color(51, 51, 255));
        jPanel1.setBorder(createEmptyBorder(1, 1, 1, 1));
        jPanel1.setLayout(new GridBagLayout());
        
        btnSalir.setText("Cancelar");
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel1.add(btnSalir, gridBagConstraints);
        getContentPane().add(jPanel1, BorderLayout.CENTER);
        
        pack();
    }

    private void btnSalirActionPerformed(ActionEvent evt) {
       System.exit(0);
    }

}