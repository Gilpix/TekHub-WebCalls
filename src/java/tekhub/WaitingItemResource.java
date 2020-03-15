/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tekhub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * REST Web Service
 *
 * @author kulartist
 */
@Path("WaitingItem")
public class WaitingItemResource {
    
    
    
    
      
      String Message; 
      JSONArray mainArray=new JSONArray();
      JSONObject mainObject = new JSONObject(); 
      DateFormat df = new SimpleDateFormat("yyyy/MM/dd"); 
      DatabaseConnection databaseConn=new DatabaseConnection();

 
 
    private static java.sql.Date getCurrentDate() {
        
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
        
    }
  

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of WaitingItemResource
     */
    public WaitingItemResource() {
    }

    /**
     * Retrieves representation of an instance of tekhub.WaitingItemResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    
   
    //Add item to waiting list
    @GET
    @Path("addWaitingItem&{USERID}&{ITEMID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String placeOrder(@PathParam("USERID") int userId, @PathParam("ITEMID") int itemId) {
        
        Connection conn = null;
              conn=  databaseConn.getConnection(conn);
              int qRes=0;
  
        try {           
            
                String sql;
                sql = "Insert Into WaitingItem(userId,itemId) Values (?,?);";
 
                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setInt(1,userId);
                stm.setInt(2,itemId);
              
                qRes=stm.executeUpdate();
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
       }
         
         return mainObject.toString();
         
    }
    
    
   
    //Get list of all items in waiting list
    @GET
    @Path("getWaitingList&{USERID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getWaitingList(@PathParam("USERID") int userId) {
        
        JSONObject singleChoice =new JSONObject();
        mainObject.clear();
        mainArray.clear();
        
        Connection conn = null;
        conn=  databaseConn.getConnection(conn);
         
        try {     
               String itemname,itemPic;
               java.sql.Date availableDate;
               int itemId;
                        
               String sql;
               sql = "SELECT WaitingItem.itemId,itemname,availableDate,Item.itemPic from Item join WaitingItem On WaitingItem.itemId=Item.itemId " +
                     "join User On User.userId=WaitingItem.userId where User.userId=?;";
    
   
                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setInt(1, userId);
              
                ResultSet rs=stm.executeQuery();
                stm.setInt(1, userId);

                while(rs.next()) {
                    itemId = rs.getInt("itemId");
                    itemname = rs.getString("itemname");
                    itemPic = rs.getString("itemPic"); 
                    availableDate = rs.getDate("availableDate"); 
      
   
//Display values
          
                 singleChoice.accumulate("itemId", itemId);
                 singleChoice.accumulate("itemname", itemname);
                 singleChoice.accumulate("itemPic", itemPic);
                 singleChoice.accumulate("availableDate", availableDate.toString());
   
                 mainArray.add(singleChoice);
                 singleChoice.clear();
     
    }
                 singleChoice.accumulate("Status", "ok");
                 singleChoice.accumulate("WaitingList", mainArray);
                 databaseConn.closeConnection(conn,rs,stm);


        }
        catch (SQLException ex) {
                      String msg=ex.getMessage();
                      
                    } catch (Exception ex) {
              String msg=ex.getMessage();
          }
 
        

         return singleChoice.toString();
         
    }
    
    
    
    //Delete item from waiting list
    @GET
    @Path("deleteWaitingItem&{USERID}&{ITEMID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteWaitingItem(@PathParam("USERID") int userId, @PathParam("ITEMID") int itemId) {
        
        Connection conn = null;
        conn=  databaseConn.getConnection(conn);
        int qRes=0;
         
        try {           
            
                String sql;
                sql = "DELETE FROM WaitingItem WHERE userId=? and itemId=? ;";
 
                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setInt(1,userId);
                stm.setInt(2,itemId);
              
                qRes=stm.executeUpdate();
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
       }
         
         return mainObject.toString();
         
    }
    
    
}
