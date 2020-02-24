 // http://localhost:8080/TekHub-WebCalls/webcall/admin/addItem&USB-Type-C&usb type c cable 3ft long&1&New

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
import java.util.Date;
import static javax.management.Query.value;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * REST Web Service
 *
 * @author sonym
 */
@Path("admin")
public class AdminResource {
    
    String Message; 
    Date today=new Date();
    long timeStamp=today.getTime();
    JSONObject mainObject = new JSONObject();
    DatabaseConnection databaseConn=new DatabaseConnection();
    Date endDate1;
    Date endDate2;
    int itemIdFromDb;

    @Context
    private UriInfo context;

    
    public AdminResource() {
    }

    private static java.sql.Date getCurrentDate() {
        
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
        
    }
    @GET
    @Path("addItem&{ITEM_NAME}&{ITEM_DESC}&{IS_AVAILABLE}&{ITEM_CONDITION}")
    @Produces("text/plain")
    public String addItem(@PathParam("ITEM_NAME") String item_name, 
            @PathParam("ITEM_DESC") String item_desc,@PathParam("IS_AVAILABLE") String is_available, 
            @PathParam("ITEM_CONDITION") String item_conditon) {
      
        Connection conn = null;
        conn=  databaseConn.getConnection(conn);
        int qRes=0;

      
        try {           
            
             String sql;
            sql = "INSERT INTO Item VALUES(?,?,?,?,?,?,?,?)";
    
            String sql2= "SELECT itemId FROM Item ORDER BY itemId DESC LIMIT 1";
            PreparedStatement stm1 = conn.prepareStatement(sql2);
            ResultSet rs=stm1.executeQuery();
            while(rs.next()) itemIdFromDb = rs.getInt("itemId");
            if(itemIdFromDb>1){
                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setInt(1,itemIdFromDb+1);
                stm.setString(2,item_name);
                stm.setString(3,item_desc);
                stm.setString(4,is_available);
                stm.setDate(5, getCurrentDate());
                stm.setString(6, item_conditon);               
                stm.setInt(7,0);
                stm.setDate(8, getCurrentDate());

                qRes=stm.executeUpdate();
                  if(qRes==1)
                  {
                   mainObject.accumulate("Status", "ok");
                    mainObject.accumulate("Timestamp", timeStamp);
                  }
                  else{
                      mainObject.accumulate("Status", "Error");
                    mainObject.accumulate("Timestamp", timeStamp);
                  }
             }       
 
      
                  databaseConn.closeConnection(conn,null,stm1);
            
        } catch (SQLException ex) {
            Message=ex.getMessage();
        }
             // throw new UnsupportedOperationException();
  return mainObject.toString();
    }

    
    
}
