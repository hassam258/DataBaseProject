package restaurant;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shayan Ali
 */
public class AddFoodItems extends javax.swing.JFrame {

    /**
     * Creates new form AddFoodItems
     */
    public AddFoodItems() throws SQLException {
        initComponents();
        SetAllFood();
    }
Food food = new Food();    
     DBAction dbaction = new DBAction();
    static ObservableList<Food> list1 = FXCollections.observableArrayList();
    
   ObservableList<Food> foodlist = FXCollections.observableArrayList();
   
   
   void SetAllFood() throws SQLException
    {
        //Clear Initial Table Data
        foodlist.clear();
        
        
        DefaultTableModel model = (DefaultTableModel) jTableAddFood.getModel();              
        
        
        foodlist = new AllFoodItems().getAllFood();
        
         ((DefaultTableModel)jTableAddFood.getModel()).setNumRows(0);
        Object rowData[] = new Object[5];
        for(int i=0; i<foodlist.size(); i++){
        rowData[0]= foodlist.get(i).getFoodId();
         rowData[1]= foodlist.get(i).getFoodName();
          rowData[2]= foodlist.get(i).getByingPrice();
           rowData[3]= foodlist.get(i).getSellingPrice();
           rowData[4]= foodlist.get(i).getProfit();
           model.addRow(rowData);
                }
        
 
    }
     private void AddFood() throws SQLException {
        
        String fid = FoodId.getText();
        String FName = FoodName.getText();
        String BPrice = BuyingPrice.getText();
        String SPrice = SellingPrice.getText();
        
        if(fid.equals("") && FName.equals("") && BPrice.equals("") && SPrice.equals(""))return;
        
        
        int foodid = Integer.parseInt(fid);
        Double Buyprice = Double.parseDouble(BPrice);
        Double Sellprice = Double.parseDouble(SPrice);
        
        Connection con = dbaction.getConnection();
        Statement statement = con.createStatement();
        
        String Query = "INSERT INTO food(Food_Id,Food_Name,Buying_price,Selling_Price,Profit) VALUES("+foodid+",'"+FName+"',"+Buyprice+", "+Sellprice+","+(Sellprice-Buyprice)+")";
        
        statement.executeUpdate(Query); 
        
        JOptionPane.showMessageDialog(null, "Congrats! Your Food Item Added", "Welcome", 1);
        
        FoodId.setText("");
        FoodName.setText("");
        BuyingPrice.setText("");
        SellingPrice.setText("");
    }
     private void Update() throws SQLException {
        
        String fid = FoodId.getText();
        String FName = FoodName.getText();
        String BPrice = BuyingPrice.getText();
        String SPrice = SellingPrice.getText();
        
        if(fid.equals("") && FName.equals("") && BPrice.equals("") && SPrice.equals(""))return;
        
        
        int foodid = Integer.parseInt(fid);
        Double Buyprice = Double.parseDouble(BPrice);
        Double Sellprice = Double.parseDouble(SPrice);
        
        Connection con = dbaction.getConnection();
        Statement statement = con.createStatement();
        
        String Query = "UPDATE food SET Food_Name = '"+FName+"', Buying_price = "+Buyprice+", Selling_Price = "+Sellprice+", Profit = "+(Sellprice - Buyprice)+" WHERE Food_Id = "+foodid;
        statement.executeUpdate(Query); 
        
        JOptionPane.showMessageDialog(null, "Congrats! Your Food Item Updated", "Welcome", 1);
        
        FoodId.setText("");
        FoodName.setText("");
        BuyingPrice.setText("");
        SellingPrice.setText("");
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButtonAddFood = new javax.swing.JButton();
        jButtonAllFood = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        FoodId = new javax.swing.JTextField();
        FoodName = new javax.swing.JTextField();
        BuyingPrice = new javax.swing.JTextField();
        SellingPrice = new javax.swing.JTextField();
        cancel = new javax.swing.JButton();
        update = new javax.swing.JButton();
        add = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableAddFood = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(685, 483));

        jPanel1.setBackground(new java.awt.Color(0, 153, 102));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setFont(new java.awt.Font("Kristen ITC", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Add Food Item");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jLabel1)
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));

        jButtonAddFood.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonAddFood.setText("Add Food Items");
        jButtonAddFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddFoodActionPerformed(evt);
            }
        });

        jButtonAllFood.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonAllFood.setText("All Food Items");
        jButtonAllFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAllFoodActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonAllFood, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAddFood, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jButtonAddFood)
                .addGap(81, 81, 81)
                .addComponent(jButtonAllFood)
                .addContainerGap(252, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(0, 153, 102));

        jPanel5.setBackground(new java.awt.Color(0, 153, 102));

        jLabel4.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jLabel4.setText("Food Name");

        jLabel2.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jLabel2.setText("Buying Price");

        jLabel5.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jLabel5.setText("Selling Price");

        FoodId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FoodIdActionPerformed(evt);
            }
        });

        cancel.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        update.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        update.setText("Update");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        add.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        add.setText("Add");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jLabel3.setText("Food ID");

        jTableAddFood.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Food Id", "Food Name", "Buying Price", "Selling price", "Profit"
            }
        ));
        jTableAddFood.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableAddFoodMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableAddFood);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(cancel)
                        .addGap(72, 72, 72)
                        .addComponent(update)
                        .addGap(91, 91, 91)
                        .addComponent(add)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BuyingPrice)
                                    .addComponent(FoodId))
                                .addGap(65, 65, 65)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(FoodName, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                                    .addComponent(SellingPrice))))
                        .addGap(57, 57, 57))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(FoodId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(FoodName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(BuyingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(SellingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancel)
                    .addComponent(update)
                    .addComponent(add))
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        try {
            AddFood();
            SetAllFood();
        } catch (SQLException ex) {
            Logger.getLogger(AddFoodItems.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_addActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
       try {
            Update();
            SetAllFood();
        } catch (SQLException ex) {
            Logger.getLogger(AddFoodItems.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_updateActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        // TODO add your handling code here:
        HomePage hph = new HomePage();
        hph.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_cancelActionPerformed

    private void FoodIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FoodIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FoodIdActionPerformed

    private void jTableAddFoodMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableAddFoodMouseClicked
          DefaultTableModel model=(DefaultTableModel)jTableAddFood.getModel();
          int selectedRow=jTableAddFood.getSelectedRow();
          FoodId.setText(model.getValueAt(selectedRow, 0).toString());
          FoodName.setText(model.getValueAt(selectedRow, 1).toString());
          BuyingPrice.setText(model.getValueAt(selectedRow, 2).toString());
          SellingPrice.setText(model.getValueAt(selectedRow, 3).toString());
          
        
    }//GEN-LAST:event_jTableAddFoodMouseClicked

    private void jButtonAllFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAllFoodActionPerformed
        try {
            new AllFoodItems().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(AddFoodItems.class.getName()).log(Level.SEVERE, null, ex);
        }
       this.dispose();
    }//GEN-LAST:event_jButtonAllFoodActionPerformed

    private void jButtonAddFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddFoodActionPerformed
        this.setVisible(true);
    }//GEN-LAST:event_jButtonAddFoodActionPerformed

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
            java.util.logging.Logger.getLogger(AddFoodItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddFoodItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddFoodItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddFoodItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AddFoodItems().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(AddFoodItems.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BuyingPrice;
    private javax.swing.JTextField FoodId;
    private javax.swing.JTextField FoodName;
    private javax.swing.JTextField SellingPrice;
    private javax.swing.JButton add;
    private javax.swing.JButton cancel;
    private javax.swing.JButton jButtonAddFood;
    private javax.swing.JButton jButtonAllFood;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTableAddFood;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
}
