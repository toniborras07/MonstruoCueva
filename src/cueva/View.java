/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package cueva;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author toniborras
 */
public class View extends javax.swing.JFrame implements MouseListener {

    private int dimension;
    private boolean manual;
    private Agente a;
    private CasillaGrafica[][] mapa;
    private boolean selTesoro;
    private boolean selMonster;
    private boolean selFoso;
    private boolean selCamino;
    private boolean iniciar;
    private Charmander charmander;
    private ImageIcon monstruoImg = new ImageIcon("src/img/gyarados.png");
    private ImageIcon charm = new ImageIcon("src/img/charmander.png");
    private ImageIcon verde = new ImageIcon("src/img/foso.png");
    private ImageIcon tesoroimg = new ImageIcon("src/img/tesoro.png");
    private ImageIcon camino = new ImageIcon("src/img/piedra.png");

    private ImageIcon charmanderEste = new ImageIcon("src/img/charmanderEste.png");
    private ImageIcon charmanderEstePaso = new ImageIcon("src/img/charmanderEstePaso.png");
    private ImageIcon charmanderNorte = new ImageIcon("src/img/charmanderNorte.png");
    private ImageIcon charmanderNortePaso = new ImageIcon("src/img/charmanderNortePaso.png");
    private ImageIcon charmanderOeste = new ImageIcon("src/img/charmanderOeste.png");
    private ImageIcon charmanderOestePaso = new ImageIcon("src/img/charmanderOestePaso.png");
    private ImageIcon charmanderSur = new ImageIcon("src/img/charmanderSur.png");
    private ImageIcon charmanderSurPaso = new ImageIcon("src/img/charmanderSurPaso.png");
    Icon icono;
//    EventosTeclado teclado;

    /**
     * Creates new form View
     */
    public View(Main p, Tamanyo dim) {
        manual = true;
        this.selCamino = false;
        this.selFoso = false;
        this.selMonster = false;
        this.selTesoro = false;
        this.prog = p;
        this.iniciar = false;
//        teclado= new EventosTeclado();

        this.dimension = tamanyo(dim);
        mapa = new CasillaGrafica[dimension][dimension];

        initComponents();
        charmander = new Charmander((this.principal.getWidth() / dimension));

    }

    public int tamanyo(Tamanyo t) {
        switch (t) {
            case PEQUEÑO:
                return 8;

            case MEDIANO:
                return 12;

            case GRANDE:
                return 17;
        }
        return 0;
    }

    public void mostrar() {
        this.pack();
        setLocationRelativeTo(null);

        setTitle("La Cueva del Monstruo");
        Icon icono = new ImageIcon(monstruoImg.getImage().getScaledInstance(this.monstruo.getWidth(), this.monstruo.getHeight(), Image.SCALE_DEFAULT));
        this.monstruo.setIcon(icono);
        Icon icono2 = new ImageIcon(camino.getImage().getScaledInstance(this.jLabel2.getWidth(), this.jLabel2.getHeight(), Image.SCALE_DEFAULT));
        this.jLabel2.setIcon(icono2);
        Icon icono3 = new ImageIcon(tesoroimg.getImage().getScaledInstance(this.tesoroo.getWidth(), this.tesoroo.getHeight(), Image.SCALE_DEFAULT));
        this.tesoroo.setIcon(icono3);
        Icon icono4 = new ImageIcon(verde.getImage().getScaledInstance(this.jLabel1.getWidth(), this.jLabel1.getHeight(), Image.SCALE_DEFAULT));
        this.jLabel1.setIcon(icono4);

        this.pintarMapa();
        this.mapa[0][0].add(this.charmander);
        this.charmander.setPosX(0);
        this.charmander.setPosY(0);
        this.charmander.iniciarCharmander();

        this.setResizable(false);
        setFocusable(true);
        this.revalidate();
        this.setVisible(true);
//        this.addKeyListener(teclado);
//        this.principal.addKeyListener(teclado);
//        this.opciones.addKeyListener(teclado);

    }

    public void quitarTesoro(int x, int y) {
        JLabel label = (JLabel) mapa[x][y].getComponent(0);
        label.setIcon(null);
        this.repaint();
        //Quitar imagen del tesoro y los brillos del lado
    }

    public void moverCharmander(Direccion dir) {
        int y, x;
        int next;
        boolean llegado;

        y = charmander.getY();
        x = charmander.getX();

        llegado = false;

        switch (dir) {
            case NORTE:

                if (charmander.getDireccion() != Direccion.NORTE) {
                    charmander.setDireccion(Direccion.NORTE);

                    charmander.setIcon(icono = new ImageIcon(charmanderNorte.getImage().getScaledInstance(charmander.getWidth(), charmander.getHeight(), Image.SCALE_DEFAULT)));

                }
//                if (charmander.getPosY() > 0) {
                mapa[charmander.getPosY() - 1][charmander.getPosX()].add(charmander);
                this.charmander.setPosY(charmander.getPosY() - 1);
//                }else{
//                    this.prog.getAgente().getCasillaActual().setEstados(Estado.GOLPENORTE);
//                }

                break;
            case SUR:

                if (charmander.getDireccion() != Direccion.SUR) {
                    charmander.setDireccion(Direccion.SUR);
                    charmander.setIcon(icono = new ImageIcon(charmanderSur.getImage().getScaledInstance(charmander.getWidth(), charmander.getHeight(), Image.SCALE_DEFAULT)));

                }

//                if (charmander.getPosY() < mapa.length - 1) {
                mapa[charmander.getPosY() + 1][charmander.getPosX()].add(charmander);
                this.charmander.setPosY(charmander.getPosY() + 1);
//                }else{
//                    this.prog.getAgente().getCasillaActual().setEstados(Estado.GOLPESUR);
//                }

//                charmander.setIcon(icono = new ImageIcon(charmanderSur.getImage().getScaledInstance(charmander.getWidth(), charmander.getHeight(), Image.SCALE_DEFAULT)));
                break;
            case ESTE:

                if (charmander.getDireccion() != Direccion.ESTE) {
                    charmander.setDireccion(Direccion.ESTE);
                    charmander.setIcon(icono = new ImageIcon(charmanderEste.getImage().getScaledInstance(charmander.getWidth(), charmander.getHeight(), Image.SCALE_DEFAULT)));

                }
//                if (charmander.getPosX() < mapa.length - 1) {
                mapa[charmander.getPosY()][charmander.getPosX() + 1].add(charmander);
                this.charmander.setPosX(charmander.getPosX() + 1);
//                }else{
//                    this.prog.getAgente().getCasillaActual().setEstados(Estado.GOLPEESTE);
//                }

                break;
            case OESTE:

                if (charmander.getDireccion() != Direccion.OESTE) {
                    charmander.setDireccion(Direccion.OESTE);
                    charmander.setIcon(icono = new ImageIcon(charmanderOeste.getImage().getScaledInstance(charmander.getWidth(), charmander.getHeight(), Image.SCALE_DEFAULT)));

                }
//                if (charmander.getPosX() > 0) {
                mapa[charmander.getPosY()][charmander.getPosX() - 1].add(charmander);
                this.charmander.setPosX(charmander.getPosX() - 1);
//                }else{
//                    this.prog.getAgente().getCasillaActual().setEstados(Estado.GOLPEOESTE);
//                }

                break;

        }
        this.repaint();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        opciones = new javax.swing.JPanel();
        monstruo = new javax.swing.JLabel();
        tesoroo = new javax.swing.JLabel();
        fosoo = new javax.swing.JLabel();
        init = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        monster = new javax.swing.JButton();
        tesoro = new javax.swing.JButton();
        foso = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        mapas = new javax.swing.JComboBox<>();
        velocidades = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        principal = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(850, 700));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        opciones.setBackground(new java.awt.Color(100, 100, 100));
        opciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                opcionesMouseReleased(evt);
            }
        });

        monstruo.setText("jLabel1");

        tesoroo.setText("jLabel2");

        fosoo.setText("jLabel3");

        init.setText("Iniciar");
        init.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initActionPerformed(evt);
            }
        });

        jLabel1.setText("jLabel1");

        monster.setText("Monstruos");
        monster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monsterActionPerformed(evt);
            }
        });

        tesoro.setText("Tesoros");
        tesoro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tesoroActionPerformed(evt);
            }
        });

        foso.setText("Fosos");
        foso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fosoActionPerformed(evt);
            }
        });

        jButton1.setText("Camino");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("jLabel2");

        mapas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pequeño", "Medio", "Grande"}));
        mapas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mapasActionPerformed(evt);
            }
        });

        velocidades.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Manual","Lenta", "Media", "Rápida" }));
        velocidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                velocidadesActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(245, 245, 245));
        jLabel3.setText("Tamaño Mapa: ");

        jLabel4.setForeground(new java.awt.Color(245, 245, 245));
        jLabel4.setText("Velocidad Agente:");

        javax.swing.GroupLayout opcionesLayout = new javax.swing.GroupLayout(opciones);
        opciones.setLayout(opcionesLayout);
        opcionesLayout.setHorizontalGroup(
            opcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(opcionesLayout.createSequentialGroup()
                .addGroup(opcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, opcionesLayout.createSequentialGroup()
                        .addGroup(opcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, opcionesLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(opcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(monster, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(117, 117, 117))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, opcionesLayout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(tesoroo, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, opcionesLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(opcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tesoro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, opcionesLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(9, 9, 9))
                                    .addComponent(foso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(125, 125, 125))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, opcionesLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(opcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(opcionesLayout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(opcionesLayout.createSequentialGroup()
                                        .addGroup(opcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(velocidades, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(mapas, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(init, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addComponent(fosoo, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(opcionesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(opcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(opcionesLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(monstruo, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        opcionesLayout.setVerticalGroup(
            opcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(opcionesLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(init)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(5, 5, 5)
                .addComponent(mapas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(velocidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(monstruo, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(monster, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(opcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(opcionesLayout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(fosoo, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(90, 90, 90))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, opcionesLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(tesoroo, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tesoro)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(foso, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))))
        );

        getContentPane().add(opciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 700));

        principal.setBackground(new java.awt.Color(253, 253, 150));
        principal.setPreferredSize(new java.awt.Dimension(700, 700));
        principal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                principalMouseReleased(evt);
            }
        });
        principal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                principalKeyPressed(evt);
            }
        });
        principal.setLayout(new java.awt.GridLayout(dimension, dimension));
        getContentPane().add(principal, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 700, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void monsterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monsterActionPerformed
        // TODO add your handling code here:
        this.selCamino = false;
        this.selFoso = false;
        this.selMonster = true;
        this.selTesoro = false;
    }//GEN-LAST:event_monsterActionPerformed

    private void tesoroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tesoroActionPerformed
        // TODO add your handling code here:
        this.selCamino = false;
        this.selFoso = false;
        this.selMonster = false;
        this.selTesoro = true;
    }//GEN-LAST:event_tesoroActionPerformed

    private void fosoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fosoActionPerformed
        // TODO add your handling code here:ImageIcon monstruoImg = new ImageIcon("src/img/Monstruo.png");
        this.selCamino = false;
        this.selFoso = true;
        this.selMonster = false;
        this.selTesoro = false;
    }//GEN-LAST:event_fosoActionPerformed

    private void mapasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mapasActionPerformed
        // TODO add your handling code here:
        String itemSeleccionado = (String) mapas.getSelectedItem();
        if ("Pequeño".equals(itemSeleccionado)) {
            if (dimension != tamanyo(Tamanyo.PEQUEÑO)) {
                this.dimension = tamanyo(Tamanyo.PEQUEÑO);
                mapa = new CasillaGrafica[dimension][dimension];
                this.prog.getCueva().setCueva(Tamanyo.PEQUEÑO);
                principal.removeAll();
                principal.setLayout(new java.awt.GridLayout(dimension, dimension));
                pintarMapa();
                principal.revalidate();
                charmander = new Charmander((this.principal.getWidth() / dimension));
                this.mapa[0][0].add(this.charmander);
                this.charmander.setPosX(0);
                this.charmander.setPosY(0);
                this.charmander.iniciarCharmander();
                System.out.println("cambiado");
            }

        } else if ("Medio".equals(itemSeleccionado)) {
            if (dimension != tamanyo(Tamanyo.MEDIANO)) {
                this.dimension = tamanyo(Tamanyo.MEDIANO);
                mapa = new CasillaGrafica[dimension][dimension];
                this.prog.getCueva().setCueva(Tamanyo.MEDIANO);
                principal.removeAll();
                principal.setLayout(new java.awt.GridLayout(dimension, dimension));
                pintarMapa();
                principal.revalidate();
                charmander = new Charmander((this.principal.getWidth() / dimension));
                this.mapa[0][0].add(this.charmander);
                this.charmander.setPosX(0);
                this.charmander.setPosY(0);
                this.charmander.iniciarCharmander();
                System.out.println("cambiado");
            }
        } else {
            if (dimension != tamanyo(Tamanyo.GRANDE)) {
                this.dimension = tamanyo(Tamanyo.GRANDE);
                mapa = new CasillaGrafica[dimension][dimension];
                this.prog.getCueva().setCueva(Tamanyo.GRANDE);
                this.principal.removeAll();
                principal.setLayout(new java.awt.GridLayout(dimension, dimension));
                pintarMapa();
                principal.revalidate();
                charmander = new Charmander((this.principal.getWidth() / dimension));
                this.mapa[0][0].add(this.charmander);
                this.charmander.setPosX(0);
                this.charmander.setPosY(0);
                this.charmander.iniciarCharmander();
                System.out.println("cambiado");
            }
        }

        this.repaint();
    }//GEN-LAST:event_mapasActionPerformed

    private void principalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_principalMouseReleased
        // TODO add your handling code here:
        int x = 0, y = 0, xCasilla, yCasilla;
        //Algoritmo encargado de destapar una casilla si se encontraba previa- 
        //mente tapada. Se obtienen las coordenadas de la casilla clickada con 
        //el boton izquierdo del raton
        if (evt.getButton() == MouseEvent.BUTTON1) {
            x = evt.getX();
            y = evt.getY();

            xCasilla = x / (this.principal.getWidth() / dimension);
            yCasilla = y / (this.principal.getWidth() / dimension);

            switch (getOpcion()) {
                case 0:
                    JLabel label = (JLabel) mapa[yCasilla][xCasilla].getComponent(0);
                    label.setIcon(null);
                    if (mapa[yCasilla][xCasilla].getMonster()) {
                        if (yCasilla > 0) {
                            cambiarIconos(yCasilla - 1, xCasilla, mapa[yCasilla - 1][xCasilla]);

                        }
                        if (yCasilla < mapa.length - 1) {
                            cambiarIconos(yCasilla + 1, xCasilla, mapa[yCasilla + 1][xCasilla]);
                        }

                        if (xCasilla > 0) {
                            cambiarIconos(yCasilla, xCasilla - 1, mapa[yCasilla][xCasilla - 1]);
                        }
                        if (xCasilla < mapa.length - 1) {
                            cambiarIconos(yCasilla, xCasilla + 1, mapa[yCasilla][xCasilla + 1]);
                        }
                    }

                    if (mapa[yCasilla][xCasilla].getHoyo()) {
                        if (yCasilla > 0) {
                            JLabel label1 = (JLabel) mapa[yCasilla - 1][xCasilla].getComponent(0);
                            label1.setIcon(null);
                        }
                        if (yCasilla < mapa.length - 1) {
                            JLabel label2 = (JLabel) mapa[yCasilla + 1][xCasilla].getComponent(0);
                            label2.setIcon(null);
                        }

                        if (xCasilla > 0) {
                            JLabel label3 = (JLabel) mapa[yCasilla][xCasilla - 1].getComponent(0);
                            label3.setIcon(null);
                        }
                        if (xCasilla < mapa.length - 1) {
                            JLabel label4 = (JLabel) mapa[yCasilla][xCasilla + 1].getComponent(0);
                            label4.setIcon(null);
                        }
                    }
                    this.repaint();

                    break;
                case 1:

                    JLabel gyarados = new JLabel();

                    JLabel hedor1 = new JLabel();
                    JLabel hedor2 = new JLabel();
                    JLabel hedor3 = new JLabel();
                    JLabel hedor4 = new JLabel();

                    hedor1.setSize(mapa[xCasilla][yCasilla].getWidth(), mapa[xCasilla][yCasilla].getHeight());
                    hedor2.setSize(mapa[xCasilla][yCasilla].getWidth(), mapa[xCasilla][yCasilla].getHeight());
                    hedor3.setSize(mapa[xCasilla][yCasilla].getWidth(), mapa[xCasilla][yCasilla].getHeight());
                    hedor4.setSize(mapa[xCasilla][yCasilla].getWidth(), mapa[xCasilla][yCasilla].getHeight());
                    gyarados.setSize(mapa[xCasilla][yCasilla].getWidth(), mapa[xCasilla][yCasilla].getHeight());
                    ImageIcon gy = new ImageIcon("src/img/Monstruo.png");
                    Icon iconomonstruo = new ImageIcon(gy.getImage().getScaledInstance(gyarados.getWidth(), gyarados.getHeight(), Image.SCALE_DEFAULT));

//                    mapa[yCasilla][xCasilla].setLayout(new GridLayout(1,1));
                    ImageIcon hedor = new ImageIcon("src/img/hedor.png");
                    Icon iconohedor = new ImageIcon(hedor.getImage().getScaledInstance(gyarados.getWidth() / 3, gyarados.getHeight() / 3, Image.SCALE_DEFAULT));

                    if (yCasilla > 0) {
                        mapa[yCasilla - 1][xCasilla].add(hedor1);
                    }
                    if (yCasilla < mapa.length - 1) {
                        mapa[yCasilla + 1][xCasilla].add(hedor2);
                    }

                    if (xCasilla > 0) {
                        mapa[yCasilla][xCasilla - 1].add(hedor4);
                    }
                    if (xCasilla < mapa.length - 1) {
                        mapa[yCasilla][xCasilla + 1].add(hedor3);
                    }

                    mapa[yCasilla][xCasilla].add(gyarados);
                    this.prog.getCueva().setMonstruo(xCasilla, yCasilla);
                    mapa[yCasilla][xCasilla].setMonster(true);
                    gyarados.setIcon(iconomonstruo);
                    hedor1.setIcon(iconohedor);
                    hedor2.setIcon(iconohedor);
                    hedor3.setIcon(iconohedor);
                    hedor4.setIcon(iconohedor);

                    break;
                case 2:

                    JLabel foso = new JLabel();
                    JLabel brisa1 = new JLabel();
                    JLabel brisa2 = new JLabel();
                    JLabel brisa3 = new JLabel();
                    JLabel brisa4 = new JLabel();

                    foso.setSize(mapa[xCasilla][yCasilla].getWidth(), mapa[xCasilla][yCasilla].getHeight());
                    brisa1.setSize(mapa[xCasilla][yCasilla].getWidth(), mapa[xCasilla][yCasilla].getHeight());
                    brisa2.setSize(mapa[xCasilla][yCasilla].getWidth(), mapa[xCasilla][yCasilla].getHeight());
                    brisa3.setSize(mapa[xCasilla][yCasilla].getWidth(), mapa[xCasilla][yCasilla].getHeight());
                    brisa4.setSize(mapa[xCasilla][yCasilla].getWidth(), mapa[xCasilla][yCasilla].getHeight());
                    ImageIcon fo = new ImageIcon("src/img/foso.png");
                    Icon iconofoso = new ImageIcon(fo.getImage().getScaledInstance(foso.getWidth(), foso.getHeight(), Image.SCALE_DEFAULT));
                    ImageIcon brisa = new ImageIcon("src/img/brisa.png");
                    Icon iconobrisa = new ImageIcon(brisa.getImage().getScaledInstance(foso.getWidth() / 3, foso.getHeight() / 3, Image.SCALE_DEFAULT));
//                    mapa[yCasilla][xCasilla].setLayout(new GridLayout(1,1));
                    mapa[yCasilla][xCasilla].add(foso);
                    this.prog.getCueva().setPrecipicio(xCasilla, yCasilla);
                    if (yCasilla > 0) {
                        mapa[yCasilla - 1][xCasilla].add(brisa1);
                    }
                    if (yCasilla < mapa.length - 1) {
                        mapa[yCasilla + 1][xCasilla].add(brisa2);
                    }

                    if (xCasilla > 0) {
                        mapa[yCasilla][xCasilla - 1].add(brisa4);
                    }
                    if (xCasilla < mapa.length - 1) {
                        mapa[yCasilla][xCasilla + 1].add(brisa3);
                    }

                    mapa[yCasilla][xCasilla].setHoyo(true);

                    foso.setIcon(iconofoso);
                    brisa1.setIcon(iconobrisa);
                    brisa2.setIcon(iconobrisa);
                    brisa3.setIcon(iconobrisa);
                    brisa4.setIcon(iconobrisa);

                    break;
                case 3:

                    JLabel tresor = new JLabel();

//                    mapa[yCasilla][xCasilla].setLayout(new GridLayout(1,1));
                    tresor.setSize(mapa[xCasilla][yCasilla].getWidth(), mapa[xCasilla][yCasilla].getHeight());
                    ImageIcon te = new ImageIcon("src/img/tesoro.png");
                    Icon iconotesoro = new ImageIcon(te.getImage().getScaledInstance(tresor.getWidth(), tresor.getHeight(), Image.SCALE_DEFAULT));

                    mapa[yCasilla][xCasilla].add(tresor);
                    this.prog.getCueva().setTesoro(xCasilla, yCasilla);
                    mapa[yCasilla][xCasilla].setTesoro(true);
                    tresor.setIcon(iconotesoro);

                    break;

            }
            this.repaint();
        }
    }//GEN-LAST:event_principalMouseReleased

    public void cambiarIconos(int yCasilla, int xCasilla, CasillaGrafica c) {
        for (int i = 0; i < c.getComponentCount(); i++) {
            JLabel l = (JLabel) c.getComponent(i);
            l.setIcon(null);
        }

        JLabel brisa1 = new JLabel();
        brisa1.setSize(mapa[xCasilla][yCasilla].getWidth(), mapa[xCasilla][yCasilla].getHeight());
        ImageIcon brisa = new ImageIcon("src/img/brisa.png");
        Icon iconobrisa = new ImageIcon(brisa.getImage().getScaledInstance(brisa1.getWidth() / 3, brisa1.getHeight() / 3, Image.SCALE_DEFAULT));

        JLabel hedor1 = new JLabel();
        hedor1.setSize(mapa[xCasilla][yCasilla].getWidth(), mapa[xCasilla][yCasilla].getHeight());
        ImageIcon hedor = new ImageIcon("src/img/hedor.png");
        Icon iconohedor = new ImageIcon(hedor.getImage().getScaledInstance(hedor1.getWidth() / 3, hedor1.getHeight() / 3, Image.SCALE_DEFAULT));

        if (yCasilla > 0) {
            if (mapa[yCasilla - 1][xCasilla].getHoyo()) {
                c.add(brisa1);
            }

            if (mapa[yCasilla - 1][xCasilla].getMonster()) {
                c.add(hedor1);
            }

            if (mapa[yCasilla - 1][xCasilla].getTesoro()) {

            }
        }
        if (yCasilla < mapa.length - 1) {
            if (mapa[yCasilla + 1][xCasilla].getHoyo()) {
                c.add(brisa1);
            }

            if (mapa[yCasilla + 1][xCasilla].getMonster()) {
                c.add(hedor1);
            }

            if (mapa[yCasilla + 1][xCasilla].getTesoro()) {

            }
        }

        if (xCasilla > 0) {
            if (mapa[yCasilla][xCasilla - 1].getHoyo()) {
                c.add(brisa1);
            }

            if (mapa[yCasilla][xCasilla - 1].getMonster()) {
                c.add(hedor1);
            }

            if (mapa[yCasilla][xCasilla - 1].getTesoro()) {

            }

        }
        if (xCasilla < mapa.length - 1) {
            if (mapa[yCasilla][xCasilla + 1].getHoyo()) {
                c.add(brisa1);
            }

            if (mapa[yCasilla][xCasilla + 1].getMonster()) {
                c.add(hedor1);
            }

            if (mapa[yCasilla][xCasilla + 1].getTesoro()) {

            }
        }
        brisa1.setIcon(iconobrisa);
        hedor1.setIcon(iconohedor);
    }
    private void opcionesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opcionesMouseReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_opcionesMouseReleased

    private void velocidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_velocidadesActionPerformed
        // TODO add your handling code here:
        String itemSeleccionado = (String) velocidades.getSelectedItem();
        if ("Lenta".equals(itemSeleccionado)) {
            manual = false;
            cambiarVelocidad(1);

        } else if ("Media".equals(itemSeleccionado)) {
            manual = false;
            cambiarVelocidad(2);

        } else if ("Rápida".equals(itemSeleccionado)) {
            manual = false;
            cambiarVelocidad(3);

        } else {
            manual = true;
            cambiarVelocidad(0);
        }
    }//GEN-LAST:event_velocidadesActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.selCamino = true;
        this.selFoso = false;
        this.selMonster = false;
        this.selTesoro = false;
    }//GEN-LAST:event_jButton1ActionPerformed

    private void initActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_initActionPerformed
        // TODO add your handling code here:
        if (!manual) {
            this.iniciar = true;
        }

    }//GEN-LAST:event_initActionPerformed

    private void principalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_principalKeyPressed
        // TODO add your handling code here:
//        System.out.println(evt.getKeyCode());
    }//GEN-LAST:event_principalKeyPressed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == evt.VK_SPACE) {
            try {
                this.prog.getAgente().razonar();
            } catch (InterruptedException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_formKeyPressed

    public void pintarMapa() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (this.dimension == 8 || this.dimension == 12) {
                    mapa[i][j] = new CasillaGrafica(this.principal.getWidth() / dimension);
                    principal.add(mapa[i][j]);
                    mapa[i][j].ponerImagen();
                } else {
                    mapa[i][j] = new CasillaGrafica((this.principal.getWidth() / dimension) + 1);
                    principal.add(mapa[i][j]);
                    mapa[i][j].ponerImagen();
                }

            }

        }
    }
    /**
     * @param args the command line arguments
     */
    private Main prog;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JButton foso;
    private javax.swing.JLabel fosoo;
    private javax.swing.JButton init;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JComboBox<String> mapas;
    private javax.swing.JButton monster;
    private javax.swing.JLabel monstruo;
    private javax.swing.JPanel opciones;
    private javax.swing.JPanel principal;
    private javax.swing.JButton tesoro;
    private javax.swing.JLabel tesoroo;
    private javax.swing.JComboBox<String> velocidades;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent arg0) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        int x = 0, y = 0, xCasilla, yCasilla;
        //Algoritmo encargado de destapar una casilla si se encontraba previa- 
        //mente tapada. Se obtienen las coordenadas de la casilla clickada con 
        //el boton izquierdo del raton
        if (arg0.getButton() == MouseEvent.BUTTON1) {
            x = arg0.getX();
            y = arg0.getY();

            xCasilla = x / (this.principal.getWidth() / dimension);
            yCasilla = y / (this.principal.getWidth() / dimension);

            switch (getOpcion()) {
                case 0:
                    mapa[xCasilla][yCasilla].setIcon(camino);
                    break;
                case 1:
                    System.out.println("ggifji");
                    JLabel gyarados = new JLabel();
                    gyarados.setSize(mapa[xCasilla][yCasilla].getWidth(), mapa[xCasilla][yCasilla].getHeight());
                    ImageIcon gy = new ImageIcon("src/img/Monstruo.png");
                    Icon icono = new ImageIcon(monstruoImg.getImage().getScaledInstance(gyarados.getWidth(), gyarados.getHeight(), Image.SCALE_DEFAULT));

                    mapa[xCasilla][yCasilla].add(gyarados);
                    gyarados.setIcon(icono);

                    break;
                case 2:

                    break;
                case 3:

                    break;

            }
        }

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getOpcion() {
        if (this.selCamino) {
            return 0;
        } else if (this.selMonster) {
            return 1;
        } else if (this.selFoso) {
            return 2;
        } else if (this.selTesoro) {
            return 3;
        }
        return 4;
    }

    private void cambiarVelocidad(int vel) {
        switch (vel) {
            case 1:
                break;

        }
    }

    public Charmander getCharmander() {
        return this.charmander;
    }

    public boolean isIniciar() {
        return iniciar;
    }

    public void setIniciar(boolean iniciar) {
        this.iniciar = iniciar;
    }

    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }

}
