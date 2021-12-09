/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 *
 * @author Hassam Uddin
 */
public class MainPage extends javax.swing.JFrame {

    /**
     * Creates new form MainPage
     */
    public MainPage() {
        initComponents();
    }

      static ObservableList<AppUser> appuserlist = FXCollections.observableArrayList();
    DBAction dbaction = new DBAction();
    //Admin Login Button Action
  
    private void adminlogin() throws SQLException, IOException {
        
        String username =jTextUser.getText();
        String password = jPassword.getText();
       
        appuserlist = dbaction.getAllUser();
        
        int i=0;
        for(AppUser app : appuserlist)
        {
            String usern = app.UserName;
            String pass = app.Password;
            
            if(username.equalsIgnoreCase(usern) && password.equalsIgnoreCase(pass))
            {
                
                i = 1;
                break;
                //JOptionPane.showMessageDialog(null, "Welcome to Admin Panel","Welcome",1);
                
            }else{
                i =0;
            }
            i =0;                        
        }
        //Provide Information to better user interface.
        if(i==1){
            JOptionPane.showMessageDialog(null, "Welcome to Admin Panel","Welcome",1);
            new HomePage().setVisible(true);
            jTextUser.setText("");
            jPassword.setText("");
            
            this.dispose();
        }else{
            JOptionPane.showMessageDialog(null, "Your id or password Wrong!","Wrong Information",0);
        }
        
    }

    //User Login Buttion Action
    
    private void userlogin() throws SQLException, IOException {
        
        String username = jTextUser.getText();
        String password = jPassword.getText();
       
        appuserlist = dbaction.getAllAppUser();
        
        int i=0;
        for(AppUser app : appuserlist)
        {
            String usern = app.UserName;
            String pass = app.Password;
            
            if(username.equalsIgnoreCase(usern) && password.equalsIgnoreCase(pass))
            {
               
                i = 1;
                break;
                //JOptionPane.showMessageDialog(null, "Welcome to Admin Panel","Welcome",1);
                
            }else{
                i =0;
            }
            i =0;                        
        }
        //Provide Information to better user interface.
        if(i==1){
            JOptionPane.showMessageDialog(null, "Welcome to User Panel","Welcome",1);
             new UserHome().setVisible(true);
            this.dispose();
        }else{
            JOptionPane.showMessageDialog(null, "Your id or password Wrong!","Wrong Information",0);
        }  
        
        
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jAdmin = new javax.swing.JButton();
        jUserButton = new javax.swing.JButton();
        jTextUser = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPassword = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Food2.jpg"))); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Kristen ITC", 1, 18)); // NOI18N
        jLabel2.setText("User Name");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 120, -1));

        jAdmin.setBackground(new java.awt.Color(204, 204, 204));
        jAdmin.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jAdmin.setText("Admin Log in");
        jAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAdminActionPerformed(evt);
            }
        });
        jPanel1.add(jAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 360, -1, -1));

        jUserButton.setBackground(new java.awt.Color(204, 204, 204));
        jUserButton.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jUserButton.setText("User Log in");
        jUserButton.setBorderPainted(false);
        jUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUserButtonActionPerformed(evt);
            }
        });
        jPanel1.add(jUserButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, -1, -1));

        jTextUser.setBackground(new java.awt.Color(153, 153, 153));
        jTextUser.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextUser.setBorder(null);
        jTextUser.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextUserFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextUserFocusLost(evt);
            }
        });
        jTextUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextUserActionPerformed(evt);
            }
        });
        jPanel1.add(jTextUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, 210, 27));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/unlock (2).png"))); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 30, 30));

        jLabel1.setFont(new java.awt.Font("Kristen ITC", 1, 18)); // NOI18N
        jLabel1.setText("Password");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 105, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/id-card (2).png"))); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 30, 40));

        jPassword.setBackground(new java.awt.Color(153, 153, 153));
        jPassword.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordActionPerformed(evt);
            }
        });
        jPanel1.add(jPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, 210, 30));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Food2.jpg"))); // NOI18N
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 570));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAdminActionPerformed
        try {
            adminlogin();
            jTextUser.setText("");
            jPassword.setText("");
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jAdminActionPerformed

    private void jUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUserButtonActionPerformed
        try {
            userlogin();
            jTextUser.setText("");
            jPassword.setText("");
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jUserButtonActionPerformed

    private void jTextUserFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextUserFocusGained
        //              if(jTextUser.getText().equals("User Id")){
            //
            //                 jTextUser.setText("");
            //                  jTextUser.setForeground(new Color(153,153,153));
            //
            //              }
    }//GEN-LAST:event_jTextUserFocusGained

    private void jTextUserFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextUserFocusLost
        //        // TODO add your handling code here:
        //          if(jTextUser.getText().equals("")){
            //
            //                 jTextUser.setText("User Id");
            //                  jTextUser.setForeground(new Color(153,153,153));
            //
            //              }
    }//GEN-LAST:event_jTextUserFocusLost

    private void jTextUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextUserActionPerformed

    private void jPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jAdmin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPassword;
    private javax.swing.JTextField jTextUser;
    private javax.swing.JButton jUserButton;
    // End of variables declaration//GEN-END:variables
}
