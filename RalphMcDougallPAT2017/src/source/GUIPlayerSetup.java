/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.InternalTextureLoader;

/**
 *
 * @author Ralph McDougall 12/10/2017
 */
public class GUIPlayerSetup extends JFrame {
    
    private JFrame parentGUI;
    
    private AppGameContainer appgc;
    private boolean startedGame = false;
    
    /**
     * Creates new form GUIPlayerSetup
     */
    public GUIPlayerSetup(JFrame parentGUI) {
        initComponents();
        this.parentGUI = parentGUI;
        fixGUI();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        pnlDifficulty1 = new javax.swing.JPanel();
        lblDifficulty1 = new javax.swing.JLabel();
        cmbDifficulty1 = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlBackground = new javax.swing.JPanel();
        pnlTitle = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnBack = new javax.swing.JButton();
        btnContinue = new javax.swing.JButton();
        pnlName = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        txtfldName = new javax.swing.JTextField();
        pnlDifficulty = new javax.swing.JPanel();
        lblDifficulty = new javax.swing.JLabel();
        cmbDifficulty = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtareaHelpDisplay = new javax.swing.JTextArea();
        pnlRuntimeOptions = new javax.swing.JPanel();
        chkboxFullscreen = new javax.swing.JCheckBox();
        chkboxLockFrameRate = new javax.swing.JCheckBox();
        chkboxShowFPS = new javax.swing.JCheckBox();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        lblDifficulty1.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        lblDifficulty1.setText("DIFFICULTY: ");

        cmbDifficulty1.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        cmbDifficulty1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "EASY", "MEDIUM", "HARD", "INSANE" }));

        javax.swing.GroupLayout pnlDifficulty1Layout = new javax.swing.GroupLayout(pnlDifficulty1);
        pnlDifficulty1.setLayout(pnlDifficulty1Layout);
        pnlDifficulty1Layout.setHorizontalGroup(
            pnlDifficulty1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDifficulty1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDifficulty1)
                .addGap(18, 18, 18)
                .addComponent(cmbDifficulty1, 0, 175, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlDifficulty1Layout.setVerticalGroup(
            pnlDifficulty1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDifficulty1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDifficulty1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbDifficulty1)
                    .addComponent(lblDifficulty1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(600, 600));

        pnlBackground.setBackground(new java.awt.Color(0, 0, 102));
        pnlBackground.setMaximumSize(new java.awt.Dimension(600, 600));
        pnlBackground.setMinimumSize(new java.awt.Dimension(600, 600));
        pnlBackground.setPreferredSize(new java.awt.Dimension(600, 600));

        pnlTitle.setBackground(new java.awt.Color(255, 255, 255));
        pnlTitle.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 10));

        lblTitle.setFont(new java.awt.Font("AR JULIAN", 0, 48)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("NEW GAME");

        javax.swing.GroupLayout pnlTitleLayout = new javax.swing.GroupLayout(pnlTitle);
        pnlTitle.setLayout(pnlTitleLayout);
        pnlTitleLayout.setHorizontalGroup(
            pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTitleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlTitleLayout.setVerticalGroup(
            pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTitleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnBack.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        btnBack.setText("GO BACK");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnContinue.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        btnContinue.setText("CONTINUE");
        btnContinue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContinueActionPerformed(evt);
            }
        });

        lblName.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        lblName.setText("NAME:");

        txtfldName.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N

        javax.swing.GroupLayout pnlNameLayout = new javax.swing.GroupLayout(pnlName);
        pnlName.setLayout(pnlNameLayout);
        pnlNameLayout.setHorizontalGroup(
            pnlNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblName)
                .addGap(51, 51, 51)
                .addComponent(txtfldName)
                .addContainerGap())
        );
        pnlNameLayout.setVerticalGroup(
            pnlNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txtfldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblDifficulty.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        lblDifficulty.setText("DIFFICULTY: ");

        cmbDifficulty.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        cmbDifficulty.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "EASY", "MEDIUM", "HARD", "INSANE" }));

        javax.swing.GroupLayout pnlDifficultyLayout = new javax.swing.GroupLayout(pnlDifficulty);
        pnlDifficulty.setLayout(pnlDifficultyLayout);
        pnlDifficultyLayout.setHorizontalGroup(
            pnlDifficultyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDifficultyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDifficulty)
                .addGap(18, 18, 18)
                .addComponent(cmbDifficulty, 0, 175, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlDifficultyLayout.setVerticalGroup(
            pnlDifficultyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDifficultyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDifficultyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbDifficulty)
                    .addComponent(lblDifficulty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtareaHelpDisplay.setEditable(false);
        txtareaHelpDisplay.setColumns(20);
        txtareaHelpDisplay.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        txtareaHelpDisplay.setRows(5);
        txtareaHelpDisplay.setText("Use W, A, S, D to move\nUse I, J, K, L to change which way you are facing\nUse SPACEBAR to shoot fireballs\nCollect power-up-pellets to increase your stats\nand to clear the swarm temporarily.");
        jScrollPane1.setViewportView(txtareaHelpDisplay);

        chkboxFullscreen.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        chkboxFullscreen.setSelected(true);
        chkboxFullscreen.setText("Fullscreen");

        chkboxLockFrameRate.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        chkboxLockFrameRate.setSelected(true);
        chkboxLockFrameRate.setText("Lock Frame Rate");

        chkboxShowFPS.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        chkboxShowFPS.setSelected(true);
        chkboxShowFPS.setText("Show Frame Rate");

        javax.swing.GroupLayout pnlRuntimeOptionsLayout = new javax.swing.GroupLayout(pnlRuntimeOptions);
        pnlRuntimeOptions.setLayout(pnlRuntimeOptionsLayout);
        pnlRuntimeOptionsLayout.setHorizontalGroup(
            pnlRuntimeOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRuntimeOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRuntimeOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRuntimeOptionsLayout.createSequentialGroup()
                        .addComponent(chkboxLockFrameRate)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlRuntimeOptionsLayout.createSequentialGroup()
                        .addComponent(chkboxFullscreen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chkboxShowFPS)
                        .addGap(27, 27, 27))))
        );
        pnlRuntimeOptionsLayout.setVerticalGroup(
            pnlRuntimeOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRuntimeOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRuntimeOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkboxFullscreen)
                    .addComponent(chkboxShowFPS))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkboxLockFrameRate)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBack)
                .addGap(271, 271, 271))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(255, 255, 255)
                        .addComponent(btnContinue))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlDifficulty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlRuntimeOptions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(49, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(pnlName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlDifficulty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlRuntimeOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack)
                    .addComponent(btnContinue))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlBackgroundLayout = new javax.swing.GroupLayout(pnlBackground);
        pnlBackground.setLayout(pnlBackgroundLayout);
        pnlBackgroundLayout.setHorizontalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );
        pnlBackgroundLayout.setVerticalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        GameLogger.logInfo("\"Go Back\" button pressed");
        closeGUI();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnContinueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContinueActionPerformed
        GameLogger.logInfo("\"Continue\" button pressed");
        String name = txtfldName.getText();
        
        if (name.length() > 30)
        {
            GameLogger.logInfo("Name \"" + name + "\" too long");
            JOptionPane.showMessageDialog(this, "Your name is too long. Please choose one shorter than 30 characters.");
            return;
        }
        
        txtfldName.setText("");
        
        if (name.replace(" ", "").length() == 0)
        {
            GameLogger.logInfo("No name entered or only whitespace entered");
            JOptionPane.showMessageDialog(this, "Please enter a name");
            return;
        }
        
        int difficulty = cmbDifficulty.getSelectedIndex();
        
        GameLogger.logInfo("Starting game for (" + name + " : " + difficulty + ")");
        
        boolean makeFullscreen = chkboxFullscreen.isSelected();
        boolean lockFrameRate = chkboxLockFrameRate.isSelected();
        boolean showFPS = chkboxShowFPS.isSelected();
        
        try
        {
            // This makes the fonts work properly
            new Graphics(){
            public void resetDefaultFont(){
                DEFAULT_FONT = null;
                }
            }.resetDefaultFont();
            
            this.setVisible(false);
            
            this.startedGame = true;
            
            GameLogger.flush();
            
            this.appgc = new AppGameContainer(new GameHandler("Ralph McDougall PAT 2017", this, name, difficulty));
            this.appgc.setDisplayMode(GameHandler.SCREEN_WIDTH, GameHandler.SCREEN_HEIGHT, false);
            this.appgc.setForceExit(false);
            this.appgc.setShowFPS(showFPS);
            
            if (lockFrameRate)
            {   
                this.appgc.setTargetFrameRate(120);
            }
            if (makeFullscreen)
            {
                this.appgc.setFullscreen(true);
            }
            this.appgc.start();
            closeGUI();
        }
        catch(SlickException ex)
        {
            this.setVisible(true);
            JOptionPane.showMessageDialog(this, "Unable to start game!\n" + ex.toString());
            GameLogger.logError("Unable to start game.");
            GameLogger.logError(ex.toString());
        }
        
        
    }//GEN-LAST:event_btnContinueActionPerformed
    
    public void closeGUI()
    {
        GameLogger.logInfo("Closing GUIPlayerSetup");
        this.parentGUI.setVisible(true);
        
        InternalTextureLoader.get().clear();
        SoundStore.get().clear();
        
        if (this.startedGame)
        {
            this.appgc.exit();
        }
        
        this.dispose();
    }
    
    private void fixGUI()
    {
        // Fixes the location and size of the GUI
        this.setResizable(false);
        this.setLocationRelativeTo(this.parentGUI);
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnContinue;
    private javax.swing.JCheckBox chkboxFullscreen;
    private javax.swing.JCheckBox chkboxLockFrameRate;
    private javax.swing.JCheckBox chkboxShowFPS;
    private javax.swing.JComboBox<String> cmbDifficulty;
    private javax.swing.JComboBox<String> cmbDifficulty1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDifficulty;
    private javax.swing.JLabel lblDifficulty1;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JPanel pnlDifficulty;
    private javax.swing.JPanel pnlDifficulty1;
    private javax.swing.JPanel pnlName;
    private javax.swing.JPanel pnlRuntimeOptions;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JTextArea txtareaHelpDisplay;
    private javax.swing.JTextField txtfldName;
    // End of variables declaration//GEN-END:variables
}
