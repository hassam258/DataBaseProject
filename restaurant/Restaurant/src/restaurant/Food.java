
package restaurant;

import com.mysql.jdbc.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Shakil Ahmed
 */
public class Food {

    int FoodId;
    String FoodName;
    Double ByingPrice;
    Double SellingPrice;
    Double Profit;
  DBAction dbaction= new DBAction();
    public Food() {
    }

    public Food(String FoodName, Double ByingPrice, Double SellingPrice) {
        this.FoodName = FoodName;
        this.ByingPrice = ByingPrice;
        this.SellingPrice = SellingPrice;
       
    }

    
    public Food(int FoodId, String FoodName, Double ByingPrice, Double SellingPrice, Double Profit) {
        this.FoodId = FoodId;
        this.FoodName = FoodName;
        this.ByingPrice = ByingPrice;
        this.SellingPrice = SellingPrice;
        this.Profit = Profit;
    }
    //Srach Method for Employee Table
     ObservableList<Food> SearchFood(int foodId) throws SQLException{
         
         
         ObservableList<Food> list = FXCollections.observableArrayList();
       
         Connection con = dbaction.getConnection();
         Statement statement = con.createStatement();
         String Query ="SELECT Food_Name, Buying_price, Selling_Price,Profit FROM food Where  Food_Id="+foodId;
         
         ResultSet resultset = statement.executeQuery(Query);
         
         while(resultset.next())
         {
             String foodn = resultset.getString("Food_Name");
            double bprice = resultset.getDouble("Buying_price");
            double sprice = resultset.getDouble("Selling_Price");
            double profit=resultset.getDouble("Profit");
            
            
            //Initialize data in Employee object
            Food food  = new Food(foodId,foodn,bprice,sprice,profit);            
            
            list.add(food);
             
         }
         return list;
     }
       //Delete Food item
     
  //Delete Employees Database Action
    void DeleteFoods(int SelectedFoodId) throws SQLException
     {
         java.sql.Connection con = dbaction.getConnection();
         Statement statement = con.createStatement();
         
        
            String Query = "DELETE FROM food WHERE Food_Id ="+SelectedFoodId;
            statement.executeUpdate(Query);
          con.close();
         statement.close();
     }
    

    public int getFoodId() {
        return FoodId;
    }

    public String getFoodName() {
        return FoodName;
    }

    public Double getByingPrice() {
        return ByingPrice;
    }

    public Double getSellingPrice() {
        return SellingPrice;
    }

    public Double getProfit() {
        return Profit;
    }

    public void setFoodId(int FoodId) {
        this.FoodId = FoodId;
    }

    public void setFoodName(String FoodName) {
        this.FoodName = FoodName;
    }

    public void setByingPrice(Double ByingPrice) {
        this.ByingPrice = ByingPrice;
    }

    public void setSellingPrice(Double SellingPrice) {
        this.SellingPrice = SellingPrice;
    }

    public void setProfit(Double Profit) {
        this.Profit = Profit;
    }

    @Override
    public String toString() {
        return "Food{" + "FoodId=" + FoodId + ", FoodName=" + FoodName + ", ByingPrice=" + ByingPrice + ", SellingPrice=" + SellingPrice + ", Profit=" + Profit + '}';
    }
    
    
    
}
