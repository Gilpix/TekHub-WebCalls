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
@Path("item")
public class ItemResource {
    
    
    
    
       String msg; 
 Date today=new Date();
 long timeStamp=today.getTime();
 JSONArray mainArray=new JSONArray();
 JSONObject mainObject = new JSONObject();
 
 DatabaseConnection databaseConn=new DatabaseConnection();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ItemResource
     */
    public ItemResource() {
    }

    /**
     * Retrieves representation of an instance of tekhub.ItemResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    
    
    
    
    
    
    
    
    
    
@GET
            @Path("getItemList")
    @Produces(MediaType.APPLICATION_JSON)
    public String getItemList() {
        
        JSONObject singleChoice =new JSONObject();
        mainObject.clear();
        mainArray.clear();
        
        Connection conn = null;
              conn=  databaseConn.getConnection(conn);
         
        try {     
            String itemname,itemDesc,isAvailable,availableDate,itemCondition,borrowNum,addedDate;
            int itemId;
                        
              String sql;
    sql = "SELECT itemId,itemname,itemDesc,isAvailable,availableDate,itemCondition,borrowNum,addedDate from Item";
    
    
   
      PreparedStatement stm = conn.prepareStatement(sql);
              
                ResultSet rs=stm.executeQuery();

                while(rs.next()) {
                    itemId = rs.getInt("itemId");
                    itemname = rs.getString("itemname");
                    itemDesc = rs.getString("itemDesc");
                     isAvailable = rs.getString("isAvailable");
                    availableDate = rs.getString("availableDate");
                    itemCondition = rs.getString("itemCondition");
                     borrowNum = rs.getString("borrowNum");
                    addedDate = rs.getString("addedDate");
      
   
//Display values
    
         singleChoice.accumulate("itemId", itemId);
        singleChoice.accumulate("itemname", itemname);
        singleChoice.accumulate("itemDesc", itemDesc);
        singleChoice.accumulate("isAvailable", isAvailable);
        singleChoice.accumulate("availableDate", availableDate);
        singleChoice.accumulate("itemCondition", itemCondition);
        singleChoice.accumulate("borrowNum", borrowNum);
        singleChoice.accumulate("addedDate", addedDate);

        
        mainArray.add(singleChoice);
        singleChoice.clear();
     
    }
                singleChoice.accumulate("Status", "ok");
        singleChoice.accumulate("Timestamp", timeStamp);
        singleChoice.accumulate("itemLists", mainArray);
                     databaseConn.closeConnection(conn,rs,stm);


        }
        catch (SQLException ex) {
                       msg=ex.getMessage();
                      
                    } catch (Exception ex) {
              msg=ex.getMessage();
          }
 
        if(mainArray.toString().equals("[]"))
        {
            singleChoice.clear();
                   
             singleChoice.accumulate("Status", "error");
        singleChoice.accumulate("Timestamp", timeStamp);
       
       }
        
         return singleChoice.toString();
         
    }
    
    
    
    
    
    
    
    
   
    
    @GET
            @Path("getItemListByAvailablity")
    @Produces(MediaType.APPLICATION_JSON)
    public String getItemListByAvailablity() {
        
        JSONObject singleChoice =new JSONObject();
        mainObject.clear();
        mainArray.clear();
        
        Connection conn = null;
              conn=  databaseConn.getConnection(conn);
         
        try {     
            String itemname,itemDesc,isAvailable,availableDate,itemCondition,borrowNum,addedDate;
            int itemId;
                        
              String sql;
    sql = "SELECT itemId,itemname,itemDesc,isAvailable,availableDate,itemCondition,borrowNum,addedDate from Item where isAvailable = '1'";
    
   
      PreparedStatement stm = conn.prepareStatement(sql);
              
                ResultSet rs=stm.executeQuery();

                while(rs.next()) {
                    itemId = rs.getInt("itemId");
                    itemname = rs.getString("itemname");
                    itemDesc = rs.getString("itemDesc");
                     isAvailable = rs.getString("isAvailable");
                    availableDate = rs.getString("availableDate");
                    itemCondition = rs.getString("itemCondition");
                     borrowNum = rs.getString("borrowNum");
                    addedDate = rs.getString("addedDate");
      
   
//Display values
    
         singleChoice.accumulate("itemId", itemId);
        singleChoice.accumulate("itemname", itemname);
        singleChoice.accumulate("itemDesc", itemDesc);
        singleChoice.accumulate("isAvailable", isAvailable);
        singleChoice.accumulate("availableDate", availableDate);
        singleChoice.accumulate("itemCondition", itemCondition);
        singleChoice.accumulate("borrowNum", borrowNum);
        singleChoice.accumulate("addedDate", addedDate);

        
        mainArray.add(singleChoice);
        singleChoice.clear();
     
    }
                singleChoice.accumulate("Status", "ok");
        singleChoice.accumulate("Timestamp", timeStamp);
        singleChoice.accumulate("itemLists", mainArray);
                     databaseConn.closeConnection(conn,rs,stm);


        }
        catch (SQLException ex) {
                       msg=ex.getMessage();
                      
                    } catch (Exception ex) {
              msg=ex.getMessage();
          }
 
        if(mainArray.toString().equals("[]"))
        {
            singleChoice.clear();
                   
             singleChoice.accumulate("Status", "error");
        singleChoice.accumulate("Timestamp", timeStamp);
       
       }
        
         return singleChoice.toString();
         
    }
    
    
    
    
    
    
      
@GET
            @Path("getItemListByPopularity")
    @Produces(MediaType.APPLICATION_JSON)
    public String getItemListByPopularity() {
        
        JSONObject singleChoice =new JSONObject();
        mainObject.clear();
        mainArray.clear();
        
        Connection conn = null;
              conn=  databaseConn.getConnection(conn);
         
        try {     
            String itemname,itemDesc,isAvailable,availableDate,itemCondition,borrowNum,addedDate;
            int itemId;
                        
              String sql;
    sql = " SELECT itemId,itemname,itemDesc,isAvailable,availableDate,itemCondition,borrowNum,addedDate from Item order by borrownum desc";
    
   
      PreparedStatement stm = conn.prepareStatement(sql);
              
                ResultSet rs=stm.executeQuery();

                while(rs.next()) {
                    itemId = rs.getInt("itemId");
                    itemname = rs.getString("itemname");
                    itemDesc = rs.getString("itemDesc");
                     isAvailable = rs.getString("isAvailable");
                    availableDate = rs.getString("availableDate");
                    itemCondition = rs.getString("itemCondition");
                     borrowNum = rs.getString("borrowNum");
                    addedDate = rs.getString("addedDate");
      
   
//Display values
    
         singleChoice.accumulate("itemId", itemId);
        singleChoice.accumulate("itemname", itemname);
        singleChoice.accumulate("itemDesc", itemDesc);
        singleChoice.accumulate("isAvailable", isAvailable);
        singleChoice.accumulate("availableDate", availableDate);
        singleChoice.accumulate("itemCondition", itemCondition);
        singleChoice.accumulate("borrowNum", borrowNum);
        singleChoice.accumulate("addedDate", addedDate);

        
        mainArray.add(singleChoice);
        singleChoice.clear();
     
    }
                singleChoice.accumulate("Status", "ok");
        singleChoice.accumulate("Timestamp", timeStamp);
        singleChoice.accumulate("itemLists", mainArray);
                     databaseConn.closeConnection(conn,rs,stm);


        }
        catch (SQLException ex) {
                       msg=ex.getMessage();
                      
                    } catch (Exception ex) {
              msg=ex.getMessage();
          }
 
        if(mainArray.toString().equals("[]"))
        {
            singleChoice.clear();
                   
             singleChoice.accumulate("Status", "error");
        singleChoice.accumulate("Timestamp", timeStamp);
       
       }
        
         return singleChoice.toString();
         
    }
    
    
    
    
    
    
    
    @GET
            @Path("getItemListByNewestItem")
    @Produces(MediaType.APPLICATION_JSON)
    public String getItemListByNewestItem() {
        
        JSONObject singleChoice =new JSONObject();
        mainObject.clear();
        mainArray.clear();
        
        Connection conn = null;
              conn=  databaseConn.getConnection(conn);
         
        try {     
            String itemname,itemDesc,isAvailable,availableDate,itemCondition,borrowNum,addedDate;
            int itemId;
                        
              String sql;
    sql = " SELECT itemId,itemname,itemDesc,isAvailable,availableDate,itemCondition,borrowNum,addedDate from Item order by addedDate desc";
    
   
      PreparedStatement stm = conn.prepareStatement(sql);
              
                ResultSet rs=stm.executeQuery();

                while(rs.next()) {
                    itemId = rs.getInt("itemId");
                    itemname = rs.getString("itemname");
                    itemDesc = rs.getString("itemDesc");
                     isAvailable = rs.getString("isAvailable");
                    availableDate = rs.getString("availableDate");
                    itemCondition = rs.getString("itemCondition");
                     borrowNum = rs.getString("borrowNum");
                    addedDate = rs.getString("addedDate");
      
   
//Display values
    
         singleChoice.accumulate("itemId", itemId);
        singleChoice.accumulate("itemname", itemname);
        singleChoice.accumulate("itemDesc", itemDesc);
        singleChoice.accumulate("isAvailable", isAvailable);
        singleChoice.accumulate("availableDate", availableDate);
        singleChoice.accumulate("itemCondition", itemCondition);
        singleChoice.accumulate("borrowNum", borrowNum);
        singleChoice.accumulate("addedDate", addedDate);

        
        mainArray.add(singleChoice);
        singleChoice.clear();
     
    }
                singleChoice.accumulate("Status", "ok");
        singleChoice.accumulate("Timestamp", timeStamp);
        singleChoice.accumulate("itemLists", mainArray);
                     databaseConn.closeConnection(conn,rs,stm);


        }
        catch (SQLException ex) {
                       msg=ex.getMessage();
                      
                    } catch (Exception ex) {
              msg=ex.getMessage();
          }
 
        if(mainArray.toString().equals("[]"))
        {
            singleChoice.clear();
                   
             singleChoice.accumulate("Status", "error");
        singleChoice.accumulate("Timestamp", timeStamp);
       
       }
        
         return singleChoice.toString();
         
    }
    
    
    
    
    
    
    
    
      @GET
            @Path("getItemListByAvailability")
    @Produces(MediaType.APPLICATION_JSON)
    public String getItemListByAvailability() {
        
        JSONObject singleChoice =new JSONObject();
        mainObject.clear();
        mainArray.clear();
        
        Connection conn = null;
              conn=  databaseConn.getConnection(conn);
         
        try {     
            String itemname,itemDesc,isAvailable,availableDate,itemCondition,borrowNum,addedDate;
            int itemId;
                        
              String sql;
    sql = " SELECT itemId,itemname,itemDesc,isAvailable,availableDate,itemCondition,borrowNum,addedDate from Item where isAvailable='1'";
    
   
      PreparedStatement stm = conn.prepareStatement(sql);
              
                ResultSet rs=stm.executeQuery();

                while(rs.next()) {
                    itemId = rs.getInt("itemId");
                    itemname = rs.getString("itemname");
                    itemDesc = rs.getString("itemDesc");
                     isAvailable = rs.getString("isAvailable");
                    availableDate = rs.getString("availableDate");
                    itemCondition = rs.getString("itemCondition");
                     borrowNum = rs.getString("borrowNum");
                    addedDate = rs.getString("addedDate");
      
   
//Display values
    
         singleChoice.accumulate("itemId", itemId);
        singleChoice.accumulate("itemname", itemname);
        singleChoice.accumulate("itemDesc", itemDesc);
        singleChoice.accumulate("isAvailable", isAvailable);
        singleChoice.accumulate("availableDate", availableDate);
        singleChoice.accumulate("itemCondition", itemCondition);
        singleChoice.accumulate("borrowNum", borrowNum);
        singleChoice.accumulate("addedDate", addedDate);

        
        mainArray.add(singleChoice);
        singleChoice.clear();
     
    }
                singleChoice.accumulate("Status", "ok");
        singleChoice.accumulate("Timestamp", timeStamp);
        singleChoice.accumulate("itemLists", mainArray);
                     databaseConn.closeConnection(conn,rs,stm);


        }
        catch (SQLException ex) {
                       msg=ex.getMessage();
                      
                    } catch (Exception ex) {
              msg=ex.getMessage();
          }
 
        if(mainArray.toString().equals("[]"))
        {
            singleChoice.clear();
                   
             singleChoice.accumulate("Status", "error");
        singleChoice.accumulate("Timestamp", timeStamp);
       
       }
        
         return singleChoice.toString();
         
    }
    
    
    
    
    
    
    
    
        @GET
            @Path("getItemListBySearch&{searchText}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getItemListBySearch(@PathParam("searchText") String search) {
        
        JSONObject singleChoice =new JSONObject();
        mainObject.clear();
        mainArray.clear();
        
        Connection conn = null;
              conn=  databaseConn.getConnection(conn);
         
        try {     
            String itemname,itemDesc,isAvailable,availableDate,itemCondition,borrowNum,addedDate;
            int itemId;
                        
              String sql;
    sql = " SELECT itemId,itemname,itemDesc,isAvailable,availableDate,itemCondition,borrowNum,addedDate from Item WHERE itemname like '%"+search+"%'";
    
   
      PreparedStatement stm = conn.prepareStatement(sql);
      //System.out.println("@@@@@@@@@@@@@@&&&&&&&&@&&&&&&&&&&&&&&&");
      
      //stm.setString(1, search);
      
      
              
                ResultSet rs=stm.executeQuery();
                                    System.out.println("1111111@@@@@@@@@@@@@@&&&&&&&&@&&&&&&&&&&&&&&&");


                while(rs.next()) {
                   // System.out.println("@@@@@@@@@@@@@@&&&&&&&&@&&&&&&&&&&&&&&&");
                    itemId = rs.getInt("itemId");
                    itemname = rs.getString("itemname");
                    itemDesc = rs.getString("itemDesc");
                     isAvailable = rs.getString("isAvailable");
                    availableDate = rs.getString("availableDate");
                    itemCondition = rs.getString("itemCondition");
                     borrowNum = rs.getString("borrowNum");
                    addedDate = rs.getString("addedDate");
      
   
//Display values
    
         singleChoice.accumulate("itemId", itemId);
        singleChoice.accumulate("itemname", itemname);
        singleChoice.accumulate("itemDesc", itemDesc);
        singleChoice.accumulate("isAvailable", isAvailable);
        singleChoice.accumulate("availableDate", availableDate);
        singleChoice.accumulate("itemCondition", itemCondition);
        singleChoice.accumulate("borrowNum", borrowNum);
        singleChoice.accumulate("addedDate", addedDate);

        
        mainArray.add(singleChoice);
        singleChoice.clear();
     
    }
                singleChoice.accumulate("Status", "ok");
        singleChoice.accumulate("Timestamp", timeStamp);
        singleChoice.accumulate("itemLists", mainArray);
                     databaseConn.closeConnection(conn,rs,stm);


        }
        catch (SQLException ex) {
                       msg=ex.getMessage();
                      
                    } catch (Exception ex) {
              msg=ex.getMessage();
          }
 
        if(mainArray.toString().equals("[]"))
        {
            singleChoice.clear();
                   
             singleChoice.accumulate("Status", "error");
        singleChoice.accumulate("Timestamp", timeStamp);
       
       }
        
         return singleChoice.toString();
         
    }
    
    
    
    
    
    
}
