/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tekhub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.ResultSet;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * REST Web Service
 *
 * @author kulartist
 */
@Path("borrow")
public class OrdersResource {
    
    
    
    
      String Message; 
 //Date today;
// long timeStamp=today.getTime();
 JSONArray mainArray=new JSONArray();
 JSONObject mainObject = new JSONObject();
 
 
 DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
   //System.out.println(df.format(today));
 
 
 
 private static java.sql.Date getCurrentDate() {
        
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
        
    }
 
 
 
 
 DatabaseConnection databaseConn=new DatabaseConnection();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BorrowResource
     */
    

    /**
     * Retrieves representation of an instance of tekhub.OrdersResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    
    
    public  Date currentDate() {  
        long millis=System.currentTimeMillis();  
        java.sql.Date date=new java.sql.Date(millis);  
        return date; 
    }  
    
    
    
    
    @GET
    @Path("placeOrder&{USERID}&{ITEMID}&{PICKUPDATE}&{RETURNDATE}&{BORROWNUM}")
    @Produces(MediaType.APPLICATION_JSON)
    public String placeOrder(@PathParam("USERID") int userId, @PathParam("ITEMID") int itemId,
            @PathParam("PICKUPDATE") java.sql.Date pickupDate, @PathParam("RETURNDATE") java.sql.Date returnDate,@PathParam("BORROWNUM") int borrowNum) {
        
        
        
        Connection conn = null;
              conn=  databaseConn.getConnection(conn);
              int qRes=0,qRes1=0;
              
         
        try {           
            
              String sql;
    sql = "Insert Into Orders(userId,itemId,orderDate,pickupDate,returnDate) Values (?,?,?,?,?);";
   
    String sql1 ="Update Item set isAvailable =?,availableDate=?,borrowNum=? where itemId=?"; 
     //String sql2="Update Orders set returnDate=? where itemId=?";
     Date temp=returnDate;
 
    
   
      PreparedStatement stm = conn.prepareStatement(sql);
                stm.setInt(1,userId);
                stm.setInt(2,itemId);
                stm.setDate(3,Date.valueOf((getCurrentDate().toLocalDate().plusDays(1)).toString()));
                stm.setDate(4, Date.valueOf((pickupDate.toLocalDate().plusDays(1)).toString()));
                stm.setDate(5, Date.valueOf((temp.toLocalDate().plusDays(1)).toString()));
                
                
                
                 PreparedStatement stm1 = conn.prepareStatement(sql1);
                stm1.setString(1,"0");
                stm1.setDate(2,returnDate);
                stm1.setInt(3,borrowNum+1);
                stm1.setInt(4,itemId);
                
//                 PreparedStatement stm2 = conn.prepareStatement(sql2);
//                stm1.setDate(1,returnDate);
//                stm1.setInt(2,itemId);
              
                
              

                  qRes=stm.executeUpdate();
                  qRes1=stm1.executeUpdate();
                  if(qRes==1)
                  {
                   mainObject.accumulate("Status", "ok");
                  }

    
                  databaseConn.closeConnection(conn,null,stm);
            
        } catch (SQLException ex) {
            Message=ex.getMessage();
        }
        
         if(qRes!=1)
        {
            mainObject.clear();
        mainObject.accumulate("Status", "error");
//        mainObject.accumulate("Timestamp", timeStamp);
        mainObject.accumulate("Message", "Not Registered");
       }
         
         return mainObject.toString();
         
    }
    
    
    
    
    
    
    
    
       
    
@GET
            @Path("getOrderList&{USERID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrderList(@PathParam("USERID") int userId) {
        
        JSONObject singleChoice =new JSONObject();
        mainObject.clear();
        mainArray.clear();
        
        Connection conn = null;
              conn=  databaseConn.getConnection(conn);
         
        try {     
            String itemname,itemDesc,itemCondition,itemPic;
            java.sql.Date orderDate,pickupDate,returnDate;
            int itemId,orderId;
                        
              String sql;
    sql = "SELECT orderId,Orders.itemId,itemname,itemDesc,itemCondition,orderDate,pickupDate,returnDate,Item.itemPic from Item join Orders On Orders.itemId=Item.itemId where userId=?;";
    
   
      PreparedStatement stm = conn.prepareStatement(sql);
      stm.setInt(1, userId);
              
                ResultSet rs=stm.executeQuery();
                                    //System.out.println("0###########"+rs.last());

                

                while(rs.next()) {
                    orderId = rs.getInt("orderId");
                    itemId = rs.getInt("itemId");
                    itemname = rs.getString("itemname");
                    itemDesc = rs.getString("itemDesc");
                    itemCondition = rs.getString("itemCondition");
                    orderDate = rs.getDate("orderDate"); 
                    pickupDate = rs.getDate("pickupDate"); 
                    returnDate = rs.getDate("returnDate"); 
                    itemPic = rs.getString("itemPic"); 
      
   
//Display values

    
         singleChoice.accumulate("orderId", orderId);
          
           singleChoice.accumulate("itemId", itemId);
        singleChoice.accumulate("itemname", itemname);
        singleChoice.accumulate("itemDesc", itemDesc);
         singleChoice.accumulate("itemCondition", itemCondition);
        singleChoice.accumulate("orderDate", orderDate.toString());
        singleChoice.accumulate("pickupDate", pickupDate.toString());
        singleChoice.accumulate("returnDate", returnDate.toString());
        singleChoice.accumulate("itemPic", itemPic.toString());
   
        
        mainArray.add(singleChoice);
        singleChoice.clear();
     
    }
                singleChoice.accumulate("Status", "ok");
        singleChoice.accumulate("OrderList", mainArray);
                     databaseConn.closeConnection(conn,rs,stm);


        }
        catch (SQLException ex) {
                      String msg=ex.getMessage();
                      
                    } catch (Exception ex) {
              String msg=ex.getMessage();
          }
 
        

         return singleChoice.toString();
         
    }
    
    
    
    
    
    
    
}
