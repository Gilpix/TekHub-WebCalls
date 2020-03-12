/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tekhub;

import static com.mysql.cj.MysqlType.BLOB;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import static java.sql.JDBCType.BLOB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Types.BLOB;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Decoder.BinaryStream;
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
import static oracle.jdbc.OracleTypes.BLOB;
import static oracle.jdbc.driver.Representation.BLOB;
import oracle.sql.BLOB;
import sun.misc.BASE64Encoder;

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
            String itemname,itemDesc,isAvailable,availableDate,itemCondition,borrowNum,addedDate,avgRating,itemPic;
            int itemId;
            Blob b;
            String imageString ;
            byte[] blobAsBytes;
                        
              String sql;
            sql = "SELECT Item.itemId,itemname,itemDesc,isAvailable,availableDate,itemCondition,borrowNum,addedDate,avg(rating) as avgRating,itemPic from Item " +
                    "left join Feedback On Item.itemId=Feedback.itemId " +
                    "group by Item.itemId;";
    
  
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
                    avgRating=rs.getString("avgRating");
                    itemPic=rs.getString("itemPic");
                    
//                     b =rs.getBlob("itemPic");
//                     blobAsBytes = b.getBytes(1, (int) b.length());
//                    
//                     BASE64Encoder encoder = new BASE64Encoder();
//                     imageString = encoder.encode(blobAsBytes).replace("\n", "").replace("\r", "");
               
                        
//Display values
         singleChoice.accumulate("itemId", itemId);
        singleChoice.accumulate("itemname", itemname);
        singleChoice.accumulate("itemDesc", itemDesc);
        singleChoice.accumulate("isAvailable", isAvailable);
        singleChoice.accumulate("availableDate", availableDate);
        singleChoice.accumulate("itemCondition", itemCondition);
        singleChoice.accumulate("borrowNum", borrowNum);
        singleChoice.accumulate("addedDate", addedDate);
        singleChoice.accumulate("avgRating", avgRating);
        singleChoice.accumulate("itemPic", itemPic);

        
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
            //singleChoice.clear();
                   
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
    
    
    
    
    
    
    @GET
            @Path("updateItemAvailability")
    @Produces(MediaType.APPLICATION_JSON)
    public String updateItemAvailability() {
         int qRes=3;
        
        mainObject.clear();
        
        Connection conn = null;
              conn=  databaseConn.getConnection(conn);
         
        try {     
              String sql;
    //sql = "Update Item  set isAvailable='1'";
    //sql = "Update Item inner join Orders On Orders.itemId=Item.itemId set isAvailable='1' where Orders.returnDate<"+AdminResource.getCurrentDate();
        sql="Update Item join (select itemId,returnDate from Orders) as rd On rd.itemId=Item.itemId set isAvailable='1' where rd.returnDate<=CURDATE();";
    
      PreparedStatement stm = conn.prepareStatement(sql);
       System.out.println("##########");
              
                 qRes=stm.executeUpdate();
                System.out.println("##########"+qRes+"####");
                  if(qRes>=0)
                  {
                   mainObject.accumulate("Status", "ok");
                    mainObject.accumulate("Timestamp", timeStamp);
                  }
                  else if(qRes<0){
                      mainObject.accumulate("Status", "Error");
                    mainObject.accumulate("Timestamp", timeStamp);
                  }
                  
                  databaseConn.closeConnection(conn,null,stm);
            
        } catch (SQLException ex) {
            String Message=ex.getMessage();
        }
             // throw new UnsupportedOperationException();
  return mainObject.toString();
    }
    
    
    
    
     //Add Items to database
    @GET
    @Path("addItem&{ITEM_NAME}&{ITEM_DESC}&{ITEM_CONDITION}&{ITEM_PIC_PATH}")
    @Produces("text/plain")
    public String addItem(@PathParam("ITEM_NAME") String item_name, 
            @PathParam("ITEM_DESC") String item_desc, 
            @PathParam("ITEM_CONDITION") String item_conditon,
            @PathParam("ITEM_PIC_PATH") String item_image_path) {
      
        Connection conn = null;
        conn=  databaseConn.getConnection(conn);
        int qRes=0,itemIdFromDb = 0;

        try { 
            
            // InputStream in = new FileInputStream("storage/emulated/0/DCIM/Camera/IMG_20200306_205102.jpg");
            
            
             String sql;
            sql = "INSERT INTO Item VALUES(?,?,?,?,?,?,?,?,?)";
    
            String sql2= "SELECT itemId FROM Item ORDER BY itemId DESC LIMIT 1";
            PreparedStatement stm1 = conn.prepareStatement(sql2);
            ResultSet rs=stm1.executeQuery();
            while(rs.next()) itemIdFromDb = rs.getInt("itemId");
            if(itemIdFromDb>1){
                PreparedStatement stm = conn.prepareStatement(sql);
                 stm.setInt(1,itemIdFromDb+1);
                stm.setString(2,item_name);
                stm.setString(3,item_desc);
                stm.setString(4,"1");
                stm.setDate(5, AdminResource.getCurrentDate());
                stm.setString(6, item_conditon);               
                stm.setInt(7,0);
                stm.setDate(8, AdminResource.getCurrentDate());
                //stm.setBinaryStream(9,fis,(int)file.length());
                //stm.setBlob(9,in);

                qRes=stm.executeUpdate();
                  if(qRes==1)
                  {
                   mainObject.accumulate("Status", "ok");
                    mainObject.accumulate("Timestamp", timeStamp);
                  }
                  else{
                      mainObject.accumulate("Status", "Error");
                    mainObject.accumulate("Timestamp", timeStamp);
                  }}
                  
 
      
                  databaseConn.closeConnection(conn,null,stm1);
            
        } catch (SQLException ex) {
            String Message=ex.getMessage();
        }
             // throw new UnsupportedOperationException();
  return mainObject.toString();
    }

    
    
    
    
    
    
    
    
        
@GET
            @Path("getItemm&{ITEM_ID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getItemm(@PathParam("ITEM_ID") int item_name) {
        
        JSONObject singleChoice =new JSONObject();
        mainObject.clear();
        mainArray.clear();
        
        Connection conn = null;
              conn=  databaseConn.getConnection(conn);
         
        try {
            byte[] imgData;
                       
              String sql2,sm;
    sql2 = "SELECT itemname,itemPic from test where itemId>?;";
    //String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
    
//   PreparedStatement pstmt = conn.prepareStatement("INSERT INTO test VALUES(?,?)");
//      pstmt.setInt(2, 11);
//      //Inserting Blob type
//      InputStream in = new FileInputStream("/Users/kulartist/Desktop/TekHub Resources/icons/filter.png");
//      pstmt.setBlob(1, in);
//      pstmt.execute();
    
   
      PreparedStatement stm2 = conn.prepareStatement(sql2);
            stm2.setInt(1, item_name);
                ResultSet rs=stm2.executeQuery();
                while(rs.next()) {
                    
                    sm=rs.getString("itemname");
                       imgData = rs.getBytes("itemPic");
    
    
                       singleChoice.accumulate("itemname", sm);
        singleChoice.accumulate("itemPic", imgData);
        
        mainArray.add(singleChoice);
        singleChoice.clear();
     
    }
                singleChoice.accumulate("Status", "ok");
        singleChoice.accumulate("itemLists", mainArray);
                     databaseConn.closeConnection(conn,rs,stm2);


        }
        catch (SQLException ex) {
                       msg=ex.getMessage();
                      
                    } catch (Exception ex) {
              msg=ex.getMessage();
          }
        if(mainArray.toString().equals("[]"))
        {                   
             singleChoice.accumulate("Status", "error");       
       }
        
         return singleChoice.toString();
         
    }
    
    
    
    
}
