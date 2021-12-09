package restaurant;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shayan Ali
 */
public class AllEmployee extends javax.swing.JFrame {

   
 public AllEmployee(){
     initComponents();
   initialize();
 }
    DBAction dbaction = new DBAction();
    static ObservableList<Employee> list1 = FXCollections.observableArrayList();
    static ObservableList<Employee> list2 = FXCollections.observableArrayList();
    

    public void initialize() {
        // TODO
        
        //At First clear Value from JAVA FX Table
        EmployeePanelController.emplist2.clear();
        EmployeePanelController.emplist.clear();
        
        
        //This Function Only For Employee Table
        //Create Multiple Row Selected option
//        EmployeeTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        EmployeeDetailsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        //Database Action
        DBAction dbaction = new DBAction();  
        
               
        
        try {
            EmployeePanelController.emplist = dbaction.getAllEmployee();
        } catch (SQLException ex) {
            Logger.getLogger(AllEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        DefaultTableModel model = (DefaultTableModel) jTableEmployees.getModel();
        ((DefaultTableModel)jTableEmployees.getModel()).setNumRows(0);
        Object rowData[] = new Object[4];
        for(int i=0; i<EmployeePanelController.emplist.size(); i++){
        rowData[0]= EmployeePanelController.emplist.get(i).getEmployeeID();
         rowData[1]= EmployeePanelController.emplist.get(i).getDesignation();
          rowData[2]= EmployeePanelController.emplist.get(i).getJoinDate();
           rowData[3]= EmployeePanelController.emplist.get(i).getSalary();
           model.addRow(rowData);
                }

        
        
        
    }    


//    @FXML
//    private void ExportAllEmployee(ActionEvent event) {
//    }
//
    private void DeleteEmployee() throws SQLException {
        //Create Observable List for get Selected item        
        DefaultTableModel model =(DefaultTableModel) jTableEmployees.getModel();
        
       
       int row= jTableEmployees.getSelectedRow();
        String SelectedEmployee=model.getValueAt(row, 0).toString();
        //Remove Selected Item From Database
        DBAction dbaction = new DBAction();
        dbaction.DeleteEmployes(Integer.parseInt(SelectedEmployee));
        JOptionPane.showMessageDialog(null, "Employee have been Successfully Deleted");
        
    }
//    
//    
//

   private void ExportEmployee() throws SQLException, FileNotFoundException, IOException {
        
        
        Connection con = dbaction.getConnection();
        String query = "SELECT Employee_ID, Designation, Join_Date, Salary FROM employee";
        Statement statement = con.createStatement();
        ResultSet resultset = statement.executeQuery(query);
        
        
        //Create Excell Worksheet
        Workbook wb = new XSSFWorkbook();
        
        //Create Excell Sheet
        Sheet sheet = wb.createSheet("Employe Details");
        
        
        
        //Create Header Column Row
        Row header = sheet.createRow(0);
        
        //Push Header column Name
        header.createCell(0).setCellValue("Employee ID");
        header.createCell(1).setCellValue("Designation");
        header.createCell(2).setCellValue("Join Date");
        header.createCell(3).setCellValue("Salary");
        
        //Create Index for Multiple row
        int index = 1;
        //Push Content Excell from Database
        while(resultset.next())
        {            //Create Multiple variable row
            Row row = sheet.createRow(index);
            
            //Get data from Database
            int id = resultset.getInt("Employee_ID");
            String des = resultset.getString("Designation");
            Date date = resultset.getDate("Join_Date");
            double sal = resultset.getDouble("Salary");
            
            row.createCell(0).setCellValue(id);
            row.createCell(1).setCellValue(des);
            row.createCell(2).setCellValue(date);
            row.createCell(3).setCellValue(sal);
            
            //Increase row one by one
            index++;            
        }
        
        
        FileOutputStream fileOut = new FileOutputStream("contacts.xlsx");
        wb.write(fileOut);
        fileOut.close();
        
        JOptionPane.showMessageDialog(null, "Your Employee Details Export Sucess!","Welcome",1);
                
    }



    private void ExportEmployeeDetails(ActionEvent event) {
    }


//    private void DeleteEmployeeDetails(ActionEvent event) throws SQLException {
//        
//        //Create Observable List for get Selected item        
//        ArrayList<Employee> SelectedEmployee = new ArrayList<>();
////        SelectedEmployee = EmployeeDetailsTable.getSelectionModel().getSelectedItems();
//        
//        //Remove Selected Item from Database
//        DBAction dbaction = new DBAction();
////        dbaction.DeleteEmployesDetails(SelectedEmployee);
//        
//        //Remove Selected Item from Javafx Table
//        EmployeePanelController.emplist2.removeAll(SelectedEmployee);
//    }

    //Search employe Method for Employee Table
   

    private void SearchEmployee() throws SQLException {
        
        list1.clear();
        
        try{
            
        String Search = search.getText();
        if(Search.equals("")){
            initialize();
            
        }
        else{
            
            DBAction dbaction = new DBAction();        
            list1 = dbaction.SearchEmployee(Integer.parseInt(Search));
           
            DefaultTableModel model = (DefaultTableModel) jTableEmployees.getModel();
             ((DefaultTableModel)jTableEmployees.getModel()).setNumRows(0);
        Object rowData[] = new Object[4];
        for(int i=0; i<list1.size(); i++){
        rowData[0]= list1.get(i).getEmployeeID();
         rowData[1]= list1.get(i).getDesignation();
          rowData[2]= list1.get(i).getJoinDate();
           rowData[3]= list1.get(i).getSalary();
           model.addRow(rowData);
                }
        }
            
        }catch(Exception e)        {
            JOptionPane.showMessageDialog(null, "Please Type Correct Employee ID");
    }
        
        
    }
//
//    @FXML
//    private void SearchEmployeeDetails(ActionEvent event) throws SQLException {
//        
//        list1.clear();
//        list2.clear();
//        
//        try{
//        String search = Search2.getText();
//        if(search.equals("")){
//            initialize(null,null);
//        }
//        else{
//        
//            DBAction dbaction = new DBAction();
//        
//            list2 = dbaction.SearchEmployeeDetails(Integer.parseInt(search));
//        
//            //Put Value from Database into JavaFX Table
//            ID.setCellValueFactory(new PropertyValueFactory<>("EmployeeID"));
//            FirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
//            LastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
//            Phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
//            Gendar.setCellValueFactory(new PropertyValueFactory<>("Gender"));
//            Country.setCellValueFactory(new PropertyValueFactory<>("Country"));
//            Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
//            Passport.setCellValueFactory(new PropertyValueFactory<>("Passport_NID"));
//        
//            //Set Item in EmployeePanel Controller
//            EmployeeDetailsTable.setItems(list2);
//        }
//        }catch(Exception e)
//        {
//            JOptionPane.showMessageDialog(null, "Please Type Correct Employee ID");
//        }
//        
//        
//        
//    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButtonAddEmployee = new javax.swing.JButton();
        jButtonAllEmployee = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        search = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableEmployees = new javax.swing.JTable();
        export = new javax.swing.JButton();
        exportall = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        back = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

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
        jLabel1.setText("All Employees");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(145, 145, 145)
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

        jButtonAddEmployee.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonAddEmployee.setText("Add Employee");
        jButtonAddEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddEmployeeActionPerformed(evt);
            }
        });

        jButtonAllEmployee.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonAllEmployee.setText("All Employees");
        jButtonAllEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAllEmployeeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonAddEmployee, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                    .addComponent(jButtonAllEmployee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jButtonAddEmployee)
                .addGap(59, 59, 59)
                .addComponent(jButtonAllEmployee)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(0, 153, 102));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Search");

        search.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        search.setToolTipText("search by ID");
        search.setName(""); // NOI18N
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        jTableEmployees.setBackground(new java.awt.Color(204, 204, 204));
        jTableEmployees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee ID", "Designation", "Join Date", "Salary"
            }
        ));
        jTableEmployees.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableEmployees);

        export.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        export.setText("export");
        export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportActionPerformed(evt);
            }
        });

        exportall.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        exportall.setText("Export All");
        exportall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportallActionPerformed(evt);
            }
        });

        delete.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        delete.setText("Delete");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        back.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        back.setText("back");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 211, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(back)
                        .addGap(18, 18, 18)
                        .addComponent(export)
                        .addGap(18, 18, 18)
                        .addComponent(exportall)
                        .addGap(18, 18, 18)
                        .addComponent(delete))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(export)
                    .addComponent(exportall)
                    .addComponent(delete)
                    .addComponent(back))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
     try {
         
         SearchEmployee();
         search.setText("");
     } catch (SQLException ex) {
         Logger.getLogger(AllEmployee.class.getName()).log(Level.SEVERE, null, ex);
     }
    
    }//GEN-LAST:event_searchActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        // TODO add your handling code here:
        HomePage phh = new HomePage();
        phh.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backActionPerformed

    private void exportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportActionPerformed
     try {
         ExportEmployee();
     } catch (SQLException ex) {
         Logger.getLogger(AllEmployee.class.getName()).log(Level.SEVERE, null, ex);
     } catch (IOException ex) {
         Logger.getLogger(AllEmployee.class.getName()).log(Level.SEVERE, null, ex);
     }
    }//GEN-LAST:event_exportActionPerformed

    private void exportallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportallActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exportallActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
     try {
         DeleteEmployee();
          ((DefaultTableModel)jTableEmployees.getModel()).setNumRows(0);
          initialize();
     } catch (SQLException ex) {
         Logger.getLogger(AllEmployee.class.getName()).log(Level.SEVERE, null, ex);
     }
       
    }//GEN-LAST:event_deleteActionPerformed

    private void jButtonAddEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddEmployeeActionPerformed
      new AddEmployeee().setVisible(true);
    }//GEN-LAST:event_jButtonAddEmployeeActionPerformed

    private void jButtonAllEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAllEmployeeActionPerformed
        this.setVisible(true);
    }//GEN-LAST:event_jButtonAllEmployeeActionPerformed

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
            java.util.logging.Logger.getLogger(AllEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AllEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AllEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AllEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AllEmployee().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back;
    private javax.swing.JButton delete;
    private javax.swing.JButton export;
    private javax.swing.JButton exportall;
    private javax.swing.JButton jButtonAddEmployee;
    private javax.swing.JButton jButtonAllEmployee;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableEmployees;
    private javax.swing.JTextField search;
    // End of variables declaration//GEN-END:variables
}
