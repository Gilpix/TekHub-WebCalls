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
@Path("feedback")
public class FeedbackResource {

    @Context
    private UriInfo context;
    
   
 String Message; 
 JSONArray mainArray=new JSONArray();
 JSONObject mainObject = new JSONObject();
 
 DatabaseConnection databaseConn=new DatabaseConnection();
 DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
 
 
  /**
     * Creates a new instance of FeedbackResource
     */
    public FeedbackResource() {
    }
 
 
 
 private static java.sql.Date getCurrentDate() {
        
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }
 


    /**
     * Retrieves representation of an instance of tekhub.FeedbackResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

  
//Add Feedback - by student
    @GET
    @Path("addFeedback&{USERID}&{ITEMID}&{RATING}&{MESSAGE}&{ISISSUE}")
    @Produces(MediaType.APPLICATION_JSON)
    public String addFeedback(@PathParam("USERID") int userId, @PathParam("ITEMID") int itemId,
            @PathParam("RATING") String rating,@PathParam("MESSAGE") String message,@PathParam("ISISSUE") String isIssue) {
        
        Connection conn = null;
              conn=  databaseConn.getConnection(conn);
              int qRes=0;
              
        try {           
            
              String sql;
    sql = "INSERT INTO Feedback (userId,itemId,rating,message,isIssue,feedDate) VALUES (?,?,?,?,?,?);";
 

      PreparedStatement stm = conn.prepareStatement(sql);
                stm.setInt(1,userId);
                stm.setInt(2,itemId);
                stm.setString(3,rating);
                stm.setString(4, (message));
                stm.setString(5, (isIssue));
                stm.setDate(6, getCurrentDate());
       

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
        mainObject.accumulate("Message", "Not Registered");
       }
         
         return mainObject.toString();
         
    }
    
    

//Get all feedbacks for items    
@GET
            @Path("getFeedbackList&{ITEMID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFeedbackList(@PathParam("ITEMID") int itemId) {
        
        JSONObject singleChoice =new JSONObject();
        mainObject.clear();
        mainArray.clear();
        
        Connection conn = null;
              conn=  databaseConn.getConnection(conn);
         
        try {     
            String name,rating,message;
            java.sql.Date feedDate;
            int userId,orderId;
                        
            String sql;
            sql = "SELECT name,rating,message,feedDate from Feedback join User ON User.userId=Feedback.userId where itemId=?;";
    
   
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, itemId);
              
                ResultSet rs=stm.executeQuery();
                

                while(rs.next()) {
                    name = rs.getString("name");
                    rating = rs.getString("rating");
                    message = rs.getString("message");
                    feedDate = rs.getDate("feedDate"); 
        
    //Display values
            singleChoice.accumulate("name", name);
            singleChoice.accumulate("rating", rating);
            singleChoice.accumulate("message", message);
            singleChoice.accumulate("feedDate", feedDate.toString());
     
   
        mainArray.add(singleChoice);
        singleChoice.clear();
     
    }
            singleChoice.accumulate("Status", "ok");
            singleChoice.accumulate("FeedbackList", mainArray);
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
    