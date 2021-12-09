/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hassam Uddin
 */
public class UserHome extends javax.swing.JFrame {

    /**
     * Creates new form UserHome
     */
      Double TotalFPrice;
 ObservableList<OrderFood> AllOrder = FXCollections.observableArrayList();
    public UserHome() {
        initComponents();
    }
 DBAction dbaction = new DBAction();
   private void PrintAction() throws PrinterException, SQLException {
        
         
         //Update Table Status Unbooked after Checkout
         String orderid = OrderId.getText();
         if(orderid.equals(""))return;        
         int oid = Integer.parseInt(orderid);  
         
         //Database Connection
         Connection con = dbaction.getConnection();
         
         //Search Table
         Statement TableState = con.createStatement();         
         String TableQuery = "SELECT Table_Id FROM purchase WHERE Order_ID = "+oid;
         ResultSet TableResult = TableState.executeQuery(TableQuery);
         
         //Update Table Statement
         Statement statement = con.createStatement();
         
         while(TableResult.next()){         
            
             String query ="UPDATE floor1 set Status = 'UnBooked' WHERE floor1.Table_Id = "+TableResult.getInt("Table_Id");
             statement.executeUpdate(query);         
         }
         //Print Method
        
   }
    private void AddOutputAction() throws SQLException {
         
        OutPut.setText("");
        
         String orderid = OrderId.getText();
         if(orderid.equals(""))return;        
         int oid = Integer.parseInt(orderid);

         
         Date d = new Date();
         String date = d.toString();
         
         
         Connection con = dbaction.getConnection();
         
         
         Statement st1 = con.createStatement();
         String quer = "SELECT Table_Id FROM purchase WHERE Order_ID = "+oid;
         ResultSet re = st1.executeQuery(quer);
         
         
         
         
         OutPut.append("\t    SHS Restaurant\n  220/ \n        Karachi- Pakistan\n \nHassamuddin258@gmail.com"
         
                 +"\n  - - - - - - - - - - - - - - - - - - - - - - - -"
                 +"\nOrder ID : "+oid+"\t Table : "
         ); 
         
         String tab = new String();
         while(re.next())
         {
             tab = re.getString("Table_Id");
             
             OutPut.append(""+tab+", ");
         }
         
         
         OutPut.append(
         
         
                 "\nOrder Time : "+date
                 +"\n  - - - - - - - - - - - - - - - - - - - - - - - -"
                 +"\nFood Name\t Rate\t Qty\t Price"
                 
         );
         
         
        
         
        String query = "SELECT food.Food_Name,food.Selling_Price, purchase.Quantity,  purchase.Sub_Total FROM purchase join food ON purchase.Food_Id = food.Food_Id WHERE purchase.Order_ID ="+oid;
        Statement statement = con.createStatement();
        ResultSet resultset = statement.executeQuery(query);
        
        
        Statement statement2 = con.createStatement();
        String Query2 = "SELECT order_table.Order_Type FROM order_table WHERE Order_ID = "+oid;
        ResultSet result = statement2.executeQuery(Query2);
        
        String ordertype = "";
        while(result.next())
        {
            ordertype = result.getString("order_table.Order_Type");
        }
        
        while(resultset.next())
        {
            //Get data from Database    
            String foodname = resultset.getString("food.Food_Name");
            Double SellingPrice = resultset.getDouble("food.Selling_Price");
            int quantity = resultset.getInt("purchase.Quantity");
            double totalprice = resultset.getDouble("purchase.Sub_Total");
           
            //Initialize data in Employee object
            OutPut.append("\n"+foodname+"\t\t "+SellingPrice+"\t "+quantity+"\t "+totalprice); 
            
        }   
        
        double round = Math.ceil(TotalFPrice);
        double r = round - TotalFPrice;
        
        OutPut.append("\n  - - - - - - - - - - - - - - - - - - - - - - - -"
        
                +"\nOrder Total : "+TotalFPrice
                +"\nRounding    : "+r
                +"\nTotal Amount: "+round
                +"\n  - - - - - - - - - - - - - - - - - - - - - - - -"
                +"\n                Thanks For Coming"
                +"\n  - - - - - - - - - - - - - - - - - - - - - - - -"
                +"\nDeveloped By:Hassam Shafique Shayan"
                
        );
         
       
         
        
    }

     private void OrderIdAction() throws SQLException {
        
         String orderid = OrderId.getText();
        if(orderid.equals(""))return;
        
        int oid = Integer.parseInt(orderid);
        
         AllOrder.clear();
         
          
          AllOrder = getAllOrder(oid);
DefaultTableModel model = (DefaultTableModel) jTableCheckOut.getModel();              
        
        
        
        
         ((DefaultTableModel)jTableCheckOut.getModel()).setNumRows(0);
        Object rowData[] = new Object[5];
        for(int i=0; i<AllOrder.size(); i++){
        rowData[0]= AllOrder.get(i).getOrderId();
         rowData[1]= AllOrder.get(i).getFoodName();
          rowData[2]=AllOrder.get(i).getQuantity();
           rowData[3]=AllOrder.get(i).getOrderType();
           rowData[4]=AllOrder.get(i).getTotalPrice();
           model.addRow(rowData);
                }
        
          
          Connection con = dbaction.getConnection();
          
          Statement st = con.createStatement();
          String query = "SELECT Total FROM order_table WHERE Order_ID = "+oid;
          ResultSet rs = st.executeQuery(query);
          
          double Total = 0;
          while(rs.next())
          {
              Total = rs.getDouble("Total");
          }
          GrandTotalLabel.setText(""+Total);
            TotalFPrice = Total;
    }
    
    
    //Get All Food Order For Specific Order Table
    
   ObservableList<OrderFood> getAllOrder(int OrderId) throws SQLException{
        
        
        ObservableList<OrderFood> Orderlist = FXCollections.observableArrayList();
        
        Connection con = dbaction.getConnection();
        String query = "SELECT purchase.Order_ID, food.Food_Name, purchase.Quantity,  purchase.Sub_Total FROM purchase join food ON purchase.Food_Id = food.Food_Id WHERE purchase.Order_ID ="+OrderId;
        Statement statement = con.createStatement();
        ResultSet resultset = statement.executeQuery(query);
        
        
        Statement statement2 = con.createStatement();
        String Query2 = "SELECT order_table.Order_Type FROM order_table WHERE Order_ID = "+OrderId;
        ResultSet result = statement2.executeQuery(Query2);
        
        String ordertype = "";
        while(result.next())
        {
            ordertype = result.getString("order_table.Order_Type");
        }
        
        while(resultset.next())
        {
            //Get data from Database    
            int orderid = resultset.getInt("purchase.Order_ID");
            String foodname = resultset.getString("food.Food_Name");
            int quantity = resultset.getInt("purchase.Quantity");
            double totalprice = resultset.getDouble("purchase.Sub_Total");
           
            //Initialize data in Employee object
            OrderFood orderfood = new OrderFood(orderid, foodname,quantity,totalprice,ordertype);            
            
            Orderlist.add(orderfood);
            
        }   
        
        return Orderlist;        
        
    }  
       
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButtonClose = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButtonOrder = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        OrderId = new javax.swing.JTextField();
        jButtonPrint = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableCheckOut = new javax.swing.JTable();
        GrandTotalLabel = new javax.swing.JLabel();
        GrandTotalLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        OutPut = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setFont(new java.awt.Font("Kristen ITC", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("User DashBoard");

        jButtonClose.setText("Close");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonClose)
                .addGap(27, 27, 27))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonClose)
                    .addComponent(jLabel1))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));

        jButtonOrder.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jButtonOrder.setText("Order");
        jButtonOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOrderActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jButton2.setText("DashBoard");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jButton2)
                .addGap(49, 49, 49)
                .addComponent(jButtonOrder)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(0, 153, 102));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(0, 153, 102));

        jLabel2.setFont(new java.awt.Font("Kristen ITC", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Order ID");

        jLabel3.setFont(new java.awt.Font("Kristen ITC", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Checkout");

        OrderId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OrderIdActionPerformed(evt);
            }
        });

        jButtonPrint.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jButtonPrint.setText("Print");
        jButtonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintActionPerformed(evt);
            }
        });

        jTableCheckOut.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order Id", "Food Name", "Quantity", "Order Type", "Price"
            }
        ));
        jScrollPane2.setViewportView(jTableCheckOut);

        GrandTotalLabel.setFont(new java.awt.Font("Kristen ITC", 1, 18)); // NOI18N
        GrandTotalLabel.setForeground(new java.awt.Color(255, 255, 255));
        GrandTotalLabel.setText("Total Price");

        GrandTotalLabel1.setFont(new java.awt.Font("Kristen ITC", 1, 18)); // NOI18N
        GrandTotalLabel1.setForeground(new java.awt.Color(255, 255, 255));
        GrandTotalLabel1.setText("Total Price");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(OrderId, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(GrandTotalLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(GrandTotalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 106, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(96, 96, 96))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jButtonPrint)
                        .addGap(43, 43, 43))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(OrderId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrandTotalLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GrandTotalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonPrint)
                .addGap(31, 31, 31))
        );

        OutPut.setColumns(20);
        OutPut.setRows(5);
        jScrollPane1.setViewportView(OutPut);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintActionPerformed
          try {
              AddOutputAction();
          } catch (SQLException ex) {
              Logger.getLogger(UserHome.class.getName()).log(Level.SEVERE, null, ex);
          }
    }//GEN-LAST:event_jButtonPrintActionPerformed

    private void jButtonOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOrderActionPerformed
        try {
            new OrderFoodPage().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(UserHome.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_jButtonOrderActionPerformed

    private void OrderIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OrderIdActionPerformed
          try {
              OrderIdAction();
          } catch (SQLException ex) {
              Logger.getLogger(UserHome.class.getName()).log(Level.SEVERE, null, ex);
          }
    }//GEN-LAST:event_OrderIdActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
       this.dispose();
       new Login().setVisible(true);
    }//GEN-LAST:event_jButtonCloseActionPerformed

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
            java.util.logging.Logger.getLogger(UserHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserHome().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel GrandTotalLabel;
    private javax.swing.JLabel GrandTotalLabel1;
    private javax.swing.JTextField OrderId;
    private javax.swing.JTextArea OutPut;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonOrder;
    private javax.swing.JButton jButtonPrint;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableCheckOut;
    // End of variables declaration//GEN-END:variables
}
