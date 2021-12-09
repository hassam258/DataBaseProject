
package restaurant;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Shakil Ahmed
 */
public class OrderFood {
    
    int OrderId;
    int FoodId;
    String FoodName;
    int Quantity;
    int TableNo;
    int KitchenNo;
    Double TotalPrice;
    String OrderType;
 DBAction dbaction = new DBAction();
    public OrderFood() {
    }

    public OrderFood(int OrderId, int FoodId, String FoodName, int Quantity, int TableNo, int KitchenNo, Double TotalPrice, String OrderType) {
        this.OrderId = OrderId;
        this.FoodId = FoodId;
        this.FoodName = FoodName;
        this.Quantity = Quantity;
        this.TableNo = TableNo;
        this.KitchenNo = KitchenNo;
        this.TotalPrice = TotalPrice;
        this.OrderType = OrderType;
    }

    public OrderFood(int OrderId, String FoodName, int Quantity, Double TotalPrice, String OrderType) {
        this.OrderId = OrderId;
        this.FoodName = FoodName;
        this.Quantity = Quantity;
        this.TotalPrice = TotalPrice;
        this.OrderType = OrderType;
    }

    public OrderFood(int OrderId, String FoodName, int Quantity, int TableNo, int KitchenNo, String OrderType) {
        this.OrderId = OrderId;
        this.FoodName = FoodName;
        this.Quantity = Quantity;
        this.TableNo = TableNo;
        this.KitchenNo = KitchenNo;
        this.OrderType = OrderType;
    }
ObservableList<OrderFood> getAllOrderId() throws SQLException{
        
        
        ObservableList<OrderFood> Orderlist = FXCollections.observableArrayList();
        
        Connection con = dbaction.getConnection();
        String query = "SELECT Order_ID,FoodID,FoodName,Quantity,OrderType,TableNo,Kitchen,TotalPrice FROM temptable";
        Statement statement = con.createStatement();
        ResultSet resultset = statement.executeQuery(query);
        
        while(resultset.next())
        {
            //Get data from Database    
            int orderid = resultset.getInt("Order_ID");
            int foodid = resultset.getInt("FoodID");
            String foodname = resultset.getString("FoodName");
            int quantity = resultset.getInt("Quantity");
            String ordertype = resultset.getString("OrderType");
            int table = resultset.getInt("TableNo");
            int kitchen = resultset.getInt("Kitchen");
            double totalprice = resultset.getDouble("TotalPrice");
           
            //Initialize data in Employee object
            OrderFood orderfood = new OrderFood(orderid,foodid,foodname,quantity,table,kitchen,totalprice,ordertype);            
            
            Orderlist.add(orderfood);
            
        }   
        
        return Orderlist;        
        
    } //Get All Food Order For Specific Order Table
    
   ObservableList<OrderFood> getAllOrder(int OrderId) throws SQLException{
        
        
        ObservableList<OrderFood> Orderlist = FXCollections.observableArrayList();
        
        Connection con = dbaction.getConnection();
        String query = "SELECT Order_ID,FoodID,FoodName,Quantity,OrderType,TableNo,Kitchen,TotalPrice FROM temptable WHERE Order_ID = "+OrderId;
        Statement statement = con.createStatement();
        ResultSet resultset = statement.executeQuery(query);
        
        while(resultset.next())
        {
            //Get data from Database    
            int orderid = resultset.getInt("Order_ID");
            int foodid = resultset.getInt("FoodID");
            String foodname = resultset.getString("FoodName");
            int quantity = resultset.getInt("Quantity");
            String ordertype = resultset.getString("OrderType");
            int table = resultset.getInt("TableNo");
            int kitchen = resultset.getInt("Kitchen");
            double totalprice = resultset.getDouble("TotalPrice");
           
            //Initialize data in Employee object
            OrderFood orderfood = new OrderFood(orderid,foodid,foodname,quantity,table,kitchen,totalprice,ordertype);            
            
            Orderlist.add(orderfood);
            
        }   
        
        return Orderlist;        
        
    }
   
   
   
       
    public int getOrderId() {
        return OrderId;
    }

    public int getFoodId() {
        return FoodId;
    }

    public String getFoodName() {
        return FoodName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public int getTableNo() {
        return TableNo;
    }

    public int getKitchenNo() {
        return KitchenNo;
    }

    public Double getTotalPrice() {
        return TotalPrice;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderId(int OrderId) {
        this.OrderId = OrderId;
    }

    public void setFoodId(int FoodId) {
        this.FoodId = FoodId;
    }

    public void setFoodName(String FoodName) {
        this.FoodName = FoodName;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public void setTableNo(int TableNo) {
        this.TableNo = TableNo;
    }

    public void setKitchenNo(int KitchenNo) {
        this.KitchenNo = KitchenNo;
    }

    public void setTotalPrice(Double TotalPrice) {
        this.TotalPrice = TotalPrice;
    }

    public void setOrderType(String OrderType) {
        this.OrderType = OrderType;
    }

    @Override
    public String toString() {
        return "OrderFood{" + "OrderId=" + OrderId + ", FoodId=" + FoodId + ", FoodName=" + FoodName + ", Quantity=" + Quantity + ", TableNo=" + TableNo + ", KitchenNo=" + KitchenNo + ", TotalPrice=" + TotalPrice + ", OrderType=" + OrderType + '}';
    }
    
    
    
}
