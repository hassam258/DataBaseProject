package restaurant;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static restaurant.AllEmployee.list1;




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shayan Ali
 */
public class AllFoodItems extends javax.swing.JFrame {

    /**
     * Creates new form AllFoodItems
     */
    public AllFoodItems() throws SQLException {
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
        
        
        DefaultTableModel model = (DefaultTableModel) jTableFood.getModel();              
        
        
        foodlist = getAllFood();
        
         ((DefaultTableModel)jTableFood.getModel()).setNumRows(0);
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
    
    
    //Get All Food Item Method
     ObservableList<Food> getAllFood() throws SQLException{
        
        ObservableList<Food> list = FXCollections.observableArrayList();         
         
         Connection con = dbaction.getConnection();
         Statement statement = con.createStatement();
         
         String Query = "SELECT Food_Id,Food_Name,Buying_price,Selling_Price,Profit FROM food;";
         
         ResultSet resultset = statement.executeQuery(Query);
         
         while(resultset.next())
         {
             int id = resultset.getInt("Food_Id");
             String fname = resultset.getString("Food_Name");
             Double bprice = resultset.getDouble("Buying_price");
             Double sprice = resultset.getDouble("Selling_Price");
             Double profit = resultset.getDouble("Profit");             
             
             Food food = new Food(id,fname,bprice,sprice,profit);
             list.add(food);             
         }
         return list;
    }
    
     private void SearchFood() throws SQLException {
        
      list1.clear();
        
        try{
            
        String Search = searchFood.getText();
        if(Search.equals("")){
            SetAllFood();
            
        }
        else{
            
          Food food = new Food();        
            list1 = food.SearchFood(Integer.parseInt(Search));
           
            DefaultTableModel model = (DefaultTableModel) jTableFood.getModel();
             ((DefaultTableModel)jTableFood.getModel()).setNumRows(0);
        Object rowData[] = new Object[5];
        for(int i=0; i<list1.size(); i++){
        rowData[0]= list1.get(i).getFoodId();
         rowData[1]= list1.get(i).getFoodName();
          rowData[2]= list1.get(i).getByingPrice();
           rowData[3]= list1.get(i).getSellingPrice();
           rowData[4]=list1.get(i).getProfit();
           model.addRow(rowData);
                }
        }
            
        }catch(Exception e)        {
            JOptionPane.showMessageDialog(null, "Please Type Correct Food ID");
    }
     }
     
     private void DeleteFoodItem() throws SQLException {
        //Create Observable List for get Selected item        
        DefaultTableModel model =(DefaultTableModel) jTableFood.getModel();
        
       
       int row= jTableFood.getSelectedRow();
        String SelectedFoodItem=model.getValueAt(row, 0).toString();
        //Remove Selected Item From Database
       JOptionPane.showMessageDialog(null, "Employee have Successfully Deleted");
        food.DeleteFoods(Integer.parseInt(SelectedFoodItem));
        JOptionPane.showMessageDialog(null, "Food Item  have been Successfully Deleted");
        
    }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButtonAddFood = new javax.swing.JButton();
        jButtonAllFood = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        searchFood = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableFood = new javax.swing.JTable();
        export = new javax.swing.JButton();
        Back = new javax.swing.JButton();
        delete = new javax.swing.JButton();

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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("All Food Items");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(19, 19, 19))
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
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonAddFood, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                    .addComponent(jButtonAllFood, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jButtonAddFood)
                .addGap(59, 59, 59)
                .addComponent(jButtonAllFood)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(0, 153, 102));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Search");

        searchFood.setToolTipText("search by ID");
        searchFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchFoodActionPerformed(evt);
            }
        });

        jTableFood.setBackground(new java.awt.Color(204, 204, 204));
        jTableFood.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Food ID", "Food Name", "Buying Price", "Selling Price", "Profit"
            }
        ));
        jScrollPane2.setViewportView(jTableFood);

        export.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        export.setText("Export");
        export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportActionPerformed(evt);
            }
        });

        Back.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Back.setText("Back");
        Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackActionPerformed(evt);
            }
        });

        delete.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        delete.setText("Delete");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Back)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(export)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(delete))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(searchFood, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchFood, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delete)
                    .addComponent(export)
                    .addComponent(Back))
                .addGap(19, 19, 19))
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

    private void BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackActionPerformed
        // TODO add your handling code here:
        HomePage ph = new HomePage();
        ph.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BackActionPerformed

    private void exportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exportActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        try {
            DeleteFoodItem();
            SetAllFood();
        } catch (SQLException ex) {
            Logger.getLogger(AllFoodItems.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_deleteActionPerformed

    private void searchFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFoodActionPerformed
        try {
            SearchFood();
            searchFood.setText("");
        } catch (SQLException ex) {
            Logger.getLogger(AllFoodItems.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_searchFoodActionPerformed

    private void jButtonAddFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddFoodActionPerformed
        try {
            new AddFoodItems().setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(AllFoodItems.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }//GEN-LAST:event_jButtonAddFoodActionPerformed

    private void jButtonAllFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAllFoodActionPerformed
        this.setVisible(true);
    }//GEN-LAST:event_jButtonAllFoodActionPerformed

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
            java.util.logging.Logger.getLogger(AllFoodItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AllFoodItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AllFoodItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AllFoodItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AllFoodItems().setVisible(true);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Unable to  Delete Selected Food");
                    Logger.getLogger(AllFoodItems.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Back;
    private javax.swing.JButton delete;
    private javax.swing.JButton export;
    private javax.swing.JButton jButtonAddFood;
    private javax.swing.JButton jButtonAllFood;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableFood;
    private javax.swing.JTextField searchFood;
    // End of variables declaration//GEN-END:variables
}
