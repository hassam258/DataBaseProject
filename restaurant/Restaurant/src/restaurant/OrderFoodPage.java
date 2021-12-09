/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hassam Uddin
 */
public class OrderFoodPage extends javax.swing.JFrame {

    /**
     * Creates new form OrderFoodPage
     */    
    
    Double TotalFPrice;
    public OrderFoodPage() throws SQLException {
        initComponents();
        countOrderId();
    }
ObservableList<Integer> table = FXCollections.observableArrayList();
    ObservableList<Floor> tablelist = FXCollections.observableArrayList();
    ObservableList<String> AllKitchen = FXCollections.observableArrayList("1","2","3","4","5");
    ObservableList<String> AllOrderType = FXCollections.observableArrayList("Home Delivery","Table");
    
    ObservableList<OrderFood> AllOrder = FXCollections.observableArrayList();
    
     void countOrderId() throws SQLException
        {
               
        
        Connection con = dbaction.getConnection();
        Statement statement = con.createStatement();
        String Query = "SELECT count(Order_ID) as SumOrderId FROM order_table";
         
        int TotalId;
        ResultSet resultset = statement.executeQuery(Query);
         
         while(resultset.next())
         {
            int id = resultset.getInt("SumOrderId");
                        
            TotalId = (id + 1);
            
            //Set Item in All Text Field
            OrderID.setText(""+TotalId); 
             
         }
        
            
        }
    
    DBAction dbaction = new DBAction();
     private void OrderFood(ActionEvent event) throws SQLException {
        
        String Id = OrderID.getText();
        if(Id.equals(""))return;
        
        int oid = Integer.parseInt(Id);
        
        Connection con = dbaction.getConnection();
       
        //Statement for Query Order Taable
        Statement statement = con.createStatement();     
        
        //Query for Insert Value in Order Table
        Statement statement1 = con.createStatement();
        
        //Statement to Calculate Total and Total Profit
        Statement statement2 = con.createStatement();
        String Q = "SELECT sum(TotalPrice) as GrandTotal FROM temptable WHERE Order_ID = "+oid;
        double Total=0;
        ResultSet rs1 = statement2.executeQuery(Q);
        while(rs1.next())
        {
            Total = rs1.getDouble("GrandTotal");
        }
        
        
        Statement statement3 = con.createStatement();
        String Q2 = "SELECT sum(Profit) as GrandTotal FROM temptable WHERE Order_ID = "+oid;
        double TotalProfit=0;
        ResultSet rs2 = statement3.executeQuery(Q2);
        while(rs2.next())
        {
            TotalProfit = rs2.getDouble("GrandTotal");
        }
        
        //Query Specific one column for Order Food Table
        String Query = "SELECT Order_ID, OrderType, Kitchen FROM temptable WHERE Order_ID = "+oid+" GROUP BY Order_ID;";
        ResultSet rs = statement.executeQuery(Query);        
        
        while(rs.next())
            
        {
            //Insert Value in Order Table from collecting data in temp table database
            String Query2 = "INSERT INTO order_table(Order_ID, Order_Type,Kitchen,Total,Profit) VALUES("+rs.getInt("Order_ID")+",'"+rs.getString("OrderType")+"',"+rs.getInt("Kitchen")+","+Total+","+TotalProfit+")";
            statement1.executeUpdate(Query2);
        }
        
        
        
        //Statement For query Purchase Table
        Statement statement4 = con.createStatement();
        
         //Query For Insert Value in Purchase Table
        Statement statement5 = con.createStatement();
        
        
        //Query Specific one by one column from purchase table
        String q1 = "SELECT Order_ID, TableNo, FoodID, FoodName, Quantity, TotalPrice, Profit,Kitchen,OrderType FROM temptable WHERE Order_ID = "+oid;
        ResultSet rs4 = statement4.executeQuery(q1);
        
        //For Table Status Update 
        //Update Table Status After Confirming Order.
        Statement tablestatment = con.createStatement();
        LocalDate date = LocalDate.now();        
        Date date1 = Date.valueOf(date);
        
        //System.out.println(""+date1+" "+date);
        
        //Insert Kitchen Food Statement
        Statement kitchenstate = con.createStatement();
        
        
        while(rs4.next())
        {
            //Insert Value in Purchage Table After Collecting data from temp table database
            String Que ="INSERT INTO purchase(Order_ID,Table_Id, Food_Id,Quantity,Sub_Total, Sub_Profit, Selldate) VALUES("+rs4.getInt("Order_ID")+","+rs4.getInt("TableNo")+","+rs4.getInt("FoodID")+","+rs4.getInt("Quantity")+","+rs4.getDouble("TotalPrice")+","+rs4.getDouble("Profit")+",'"+date1+"')";
            statement5.executeUpdate(Que);
            
            //Insert Value in Kitchen Table
            String kitchenQuery= "INSERT INTO kitchen(Order_ID,Food_Name,Quantity,TableNo,Order_Type,kitchenId) VALUES("+rs4.getInt("Order_ID")+",'"+rs4.getString("FoodName")+"',"+rs4.getInt("Quantity")+","+rs4.getInt("TableNo")+",'"+rs4.getString("OrderType")+"',"+rs4.getInt("Kitchen")+")";
            kitchenstate.executeUpdate(kitchenQuery);            
            
            
            //For Table Update Statment
            String Tablequery = "UPDATE floor1 set Status = 'Booked' WHERE floor1.Table_Id = "+rs4.getInt("TableNo");
            tablestatment.executeUpdate(Tablequery);
            
        }
        
        
        //After Completing Order Delete Temporary table data in Database
        Statement statement6 = con.createStatement();
        String DeleteQuery = "DELETE FROM temptable WHERE Order_ID = "+oid;
        statement6.executeUpdate(DeleteQuery);
        
        AllOrder.clear();
        JOptionPane.showMessageDialog(null, "You are Successfully Completed Order! Thank you.", "Welcome!",1);
        
        
    }
      private void AddFood() throws SQLException {
        
         
        String orderid = OrderID.getText();
        String foodid = FoodID.getText();
        String foodname = FoodName.getText();
        String quantity = Quantity.getText();
        String table = (String) TableNo.getSelectedItem();
        String kitchen = (String) KitchenNo.getSelectedItem();
        String totalprice = TotalPrice.getText();
        String ordertype = (String) OrderType.getSelectedItem();
        
        if(orderid.equals("") && foodid.equals("") && foodname.equals("") && quantity.equals("") && kitchen.equals("") && totalprice.equals("") && ordertype.equals("")) JOptionPane.showMessageDialog(null, "Please Fill the given Fields");
        
        
        int oid = Integer.parseInt(orderid);
        int fid = Integer.parseInt(foodid);
        int quan = Integer.parseInt(quantity);
        int kit = Integer.parseInt(kitchen);
        Double tprice = Double.parseDouble(totalprice);
        
        
//        Connection con = dbaction.getConnection();
//        Statement statement = con.createStatement();        
//        
//        
//        
//        Statement statement2 = con.createStatement();
//        //Calculate Profit for each food
//        String Query1 = "SELECT Profit FROM food WHERE  Food_Id  = "+fid;
//         
//        ResultSet resultset = statement2.executeQuery(Query1);
//         
//        double Total=0;
//         while(resultset.next())
//         {
//            Double profit = resultset.getDouble("Profit");
//                        
//            Total = (profit * quan);  
//         }
//                     
//           //Insert Food Item for Temporary
//          String Query = "INSERT INTO temptable(Order_ID,FoodID,FoodName,Quantity,OrderType,TableNo,Kitchen,TotalPrice,Profit) VALUES("+oid+","+fid+",'"+foodname+"', "+quan+", '"+ordertype+"',"+table+","+kit+","+tprice+","+Total+")";
//          statement.executeUpdate(Query); 
//                
//          
//          //Call get All Order List
//          AllOrder.clear();
//          OrderFood orderfood=new OrderFood();
//          
//          
//                
           DefaultTableModel model = (DefaultTableModel) jTableOrderFood.getModel();              
        
//        AllOrder =  orderfood.getAllOrder(oid);
//     
        
//         ((DefaultTableModel)jTableOrderFood.getModel()).setNumRows(0);
//        Object rowData[] = new Object[8];
//       
//        rowData[0]=orderid;
//        rowData[1]=foodid;
//         rowData[2]= foodname;
////         FoodName.setText((String) rowData[2]);
//         
//        rowData[3]=quantity;
//         rowData[4]=table;
////           TotalPrice.setText((String) rowData[4]);
//        rowData[5]=kitchen;
//        rowData[6]=totalprice;
//        rowData[7]=ordertype;
//          
          
           model.addRow(new Object[]{orderid,foodid,foodname,quantity,table,kitchen,totalprice,ordertype});
               
          
          JOptionPane.showMessageDialog(null, "Successfully Added food","Welcome",1);
          
          FoodID.setText("");
          FoodName.setText("");
          Quantity.setText("");
          TotalPrice.setText("");
        
    }
       private void FoodIDAction() {
        
        try{
        String Id = FoodID.getText();
        if(Id.equals(""))return;
        
        Connection con = dbaction.getConnection();
        Statement statement = con.createStatement();
        String Query = "SELECT Food_Name FROM food WHERE Food_Id  = "+Integer.parseInt(Id);
         
        ResultSet resultset = statement.executeQuery(Query);
         
         while(resultset.next())
         {
            String foodname = resultset.getString("Food_Name");
            
           
            //Set Item in All Text Field
            FoodName.setText(foodname);            
            
             
         }
         
        }catch(Exception e)
        {
             JOptionPane.showMessageDialog(null, "Please Type Correct Food ID","Wrong!",0);
        }
         
        
    }
      private void QuantityAction() throws SQLException {
        
        try{
        String Id = FoodID.getText();
        String quantity = Quantity.getText();
        if(Id.equals("") && quantity.equals(""))return;
        
        Double quan = Double.parseDouble(quantity);
        Double Total;
        
        Connection con = dbaction.getConnection();
        Statement statement = con.createStatement();
        String Query = "SELECT Selling_Price FROM food WHERE Food_Id  = "+Integer.parseInt(Id);
         
        ResultSet resultset = statement.executeQuery(Query);
         
         while(resultset.next())
         {
            Double sellingprice = resultset.getDouble("Selling_Price");
                        
            Total = (sellingprice * quan);
            
            //Set Item in All Text Field
            TotalPrice.setText(""+Total);  
             
         }
         
        }catch(Exception e)
        {
             JOptionPane.showMessageDialog(null, "Please Order At least 1 Food");
        }
         
    }
 private void CancelOrder() throws SQLException {
        
//        String Id = OrderID.getText();
//        if(Id.equals(""))return;
//        
//        int oid = Integer.parseInt(Id);
//        
//        Connection con = dbaction.getConnection();
//        Statement statement = con.createStatement();
//        statement.executeUpdate("DELETE FROM temptable WHERE Order_ID = "+oid);
     ((DefaultTableModel)jTableOrderFood.getModel()).setNumRows(0);
          FoodID.setText("");
          FoodName.setText("");
          Quantity.setText("");
          TotalPrice.setText("");
        
         JOptionPane.showMessageDialog(null, "You are Successfully Completed Order! Thank you.", "Welcome!",1);
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jTextField5 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButtonDashBoard = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        Quantity = new javax.swing.JTextField();
        OrderID = new javax.swing.JTextField();
        FoodID = new javax.swing.JTextField();
        FoodName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableOrderFood = new javax.swing.JTable();
        TableNo = new javax.swing.JComboBox<>();
        KitchenNo = new javax.swing.JComboBox<>();
        OrderType = new javax.swing.JComboBox<>();
        TotalPrice = new javax.swing.JTextField();
        jButtonAddOrder = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jButtonOrder = new javax.swing.JButton();

        jLabel5.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Order ID");

        jLabel9.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Order ID");

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        jMenuItem3.setText("jMenuItem3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setFont(new java.awt.Font("Kristen ITC", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Order Food");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addComponent(jLabel1)
                .addContainerGap(175, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 560, 100));

        jPanel3.setBackground(new java.awt.Color(0, 153, 102));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, -1));

        jPanel4.setBackground(new java.awt.Color(0, 102, 102));

        jButton2.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jButton2.setText("order Food");

        jButtonDashBoard.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jButtonDashBoard.setText("DashBoard");
        jButtonDashBoard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDashBoardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jButtonDashBoard))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jButtonDashBoard)
                .addGap(34, 34, 34)
                .addComponent(jButton2)
                .addContainerGap(382, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 120, 520));

        jPanel5.setBackground(new java.awt.Color(0, 153, 102));

        jLabel2.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Order ID");

        jLabel3.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Food Name");

        jLabel4.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Table No");

        jLabel6.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Total Price");

        jLabel7.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Food ID");

        jLabel8.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Quantity");

        jLabel10.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Kitchen");

        jLabel11.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("order Type");

        Quantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QuantityActionPerformed(evt);
            }
        });

        FoodID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FoodIDActionPerformed(evt);
            }
        });

        jTableOrderFood.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order ID", "Food ID", "Food Name", "Quantity", "Price", "Table No", "Order Type", "Kitchen"
            }
        ));
        jScrollPane1.setViewportView(jTableOrderFood);

        TableNo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        KitchenNo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5" }));

        OrderType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Table", "Home Dilvery" }));

        TotalPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TotalPriceActionPerformed(evt);
            }
        });

        jButtonAddOrder.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jButtonAddOrder.setText("Add");
        jButtonAddOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddOrderActionPerformed(evt);
            }
        });

        jButtonCancel.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonOrder.setFont(new java.awt.Font("Kristen ITC", 1, 10)); // NOI18N
        jButtonOrder.setText("Order");
        jButtonOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(FoodName, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(OrderID, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(KitchenNo, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonAddOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(FoodID, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(TableNo, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(OrderType, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(83, 83, 83))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonCancel)
                .addGap(120, 120, 120))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7)
                            .addComponent(FoodID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(OrderID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(FoodName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jButtonAddOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(KitchenNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(TableNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(OrderType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(TotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonCancel)
                        .addGap(28, 28, 28))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jButtonOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 580, 520));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void FoodIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FoodIDActionPerformed
        FoodIDAction();
    }//GEN-LAST:event_FoodIDActionPerformed

    private void TotalPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TotalPriceActionPerformed
      
    }//GEN-LAST:event_TotalPriceActionPerformed

    private void jButtonAddOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddOrderActionPerformed
        try {
            AddFood();
        } catch (SQLException ex) {
            Logger.getLogger(OrderFoodPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonAddOrderActionPerformed

    private void jButtonDashBoardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDashBoardActionPerformed
       new UserHome().setVisible(true);
       this.dispose();
    }//GEN-LAST:event_jButtonDashBoardActionPerformed

    private void QuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QuantityActionPerformed
        try {
            QuantityAction();
        } catch (SQLException ex) {
            Logger.getLogger(OrderFoodPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_QuantityActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
         DefaultTableModel model = (DefaultTableModel) jTableOrderFood.getModel();     
        ((DefaultTableModel)jTableOrderFood.getModel()).setNumRows(0);
          FoodID.setText("");
          FoodName.setText("");
          Quantity.setText("");
          TotalPrice.setText("");
        
        JOptionPane.showMessageDialog(null, "Successfully Cancel Order!","Welcome",1);
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOrderActionPerformed
         DefaultTableModel model = (DefaultTableModel) jTableOrderFood.getModel();     
        ((DefaultTableModel)jTableOrderFood.getModel()).setNumRows(0);
          FoodID.setText("");
          FoodName.setText("");
          Quantity.setText("");
          TotalPrice.setText("");
        
        JOptionPane.showMessageDialog(null, "Successfully Cancel Order!","Welcome",1);
    }//GEN-LAST:event_jButtonOrderActionPerformed

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
            java.util.logging.Logger.getLogger(OrderFoodPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderFoodPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderFoodPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderFoodPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new OrderFoodPage().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(OrderFoodPage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField FoodID;
    private javax.swing.JTextField FoodName;
    private javax.swing.JComboBox<String> KitchenNo;
    private javax.swing.JTextField OrderID;
    private javax.swing.JComboBox<String> OrderType;
    private javax.swing.JTextField Quantity;
    private javax.swing.JComboBox<String> TableNo;
    private javax.swing.JTextField TotalPrice;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonAddOrder;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonDashBoard;
    private javax.swing.JButton jButtonOrder;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableOrderFood;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables

    private void cancelOrder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
