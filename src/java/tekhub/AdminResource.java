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
import java.time.Instant;
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
    JSONArray mainArray = new JSONArray();

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
    
    //Add Items to database
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

    //Delete Students from database
    
    @GET
    @Path("deleteStudent&{Student_Id}")
    @Produces("text/plain")
    public String deleteStudent(@PathParam("Student_Id") int student_id)
             {
      
        Connection conn = null;
        conn=  databaseConn.getConnection(conn);
        int qRes;
        qRes = 0;
        int qRes2 = 0;
        int qRes3 = 0;

      
        try {           
            
             String sql,sql2,sql3;
            sql = "DELETE FROM Student where userId="+student_id;
            sql2="delete from User where userId="+student_id;
            sql3="delete from Orders where userId="+student_id;
            
            PreparedStatement stm3 = conn.prepareStatement(sql3);
                  qRes3=stm3.executeUpdate();
                  System.out.println("row deleted Orders "+qRes3);

                  PreparedStatement stm1 = conn.prepareStatement(sql);
                  qRes=stm1.executeUpdate();
                  System.out.println("row deleted student "+qRes);
            
    PreparedStatement stm = conn.prepareStatement(sql2);
                  qRes2=stm.executeUpdate();
                  System.out.println("row deleted user "+qRes2);
            
                        
        
            
                

                  if(qRes==1 && qRes2==1 || qRes3>1)
                  {
                   mainObject.accumulate("Status", "Ok");
                    mainObject.accumulate("Timestamp", timeStamp);
                  }
                  else{
                      mainObject.accumulate("Status", "Error");
                    mainObject.accumulate("Timestamp", timeStamp);
                  }
                    
 
      
                  databaseConn.closeConnection(conn,null,stm);
            
        } catch (SQLException ex) {
            Message=ex.getMessage();
        }
  return mainObject.toString();
    }
    
    //list all students
    
    @GET
    @Path("listStudents")
    @Produces("text/plain")
    public String listStudents()
             {
        JSONObject singleChoice =new JSONObject();
        mainObject.clear();
        mainArray.clear();
        Connection conn = null;
        conn=  databaseConn.getConnection(conn);

        try {           
            String studentName,studentEmail;
            int studentId;
            Instant instant=Instant.now();
            long time=instant.getEpochSecond();
             String sql;
            sql = "select Student.userId,User.name,User.email from Student\n" +
"join User on User.userId=Student.userId";
    
            PreparedStatement stm1 = conn.prepareStatement(sql);
            ResultSet rs=stm1.executeQuery();
            while(rs.next()) {
                    studentId = rs.getInt("userId");
                    studentName = rs.getString("name");
                    studentEmail = rs.getString("email");
                    singleChoice.accumulate("StudentId", studentId);
                    singleChoice.accumulate("Name", studentName);
                    singleChoice.accumulate("email", studentEmail);
                    mainArray.add(singleChoice);
                    singleChoice.clear();
     
    }    
                    singleChoice.accumulate("Status", "Ok");
                    singleChoice.accumulate("Timestamp", timeStamp);
                    singleChoice.accumulate("studentList", mainArray);
                     databaseConn.closeConnection(conn,rs,stm1);


        }
        catch (SQLException ex) {
            String msg = ex.getMessage();
                      
                    } catch (Exception ex) {
            String msg = ex.getMessage();
          }
 
        if(mainArray.toString().equals("[]"))
        {
            singleChoice.clear();
                   
             singleChoice.accumulate("Status", "Error");
        singleChoice.accumulate("Timestamp", timeStamp);
       
       }
        
         return singleChoice.toString();
             }
    
     @GET
    @Path("searchStudent&{searchKey}")
    @Produces("text/plain")
    public String searchStudents(@PathParam("searchKey") String student_name)
             {
        JSONObject singleChoice =new JSONObject();
        mainObject.clear();
        mainArray.clear();
        Connection conn = null;
        conn=  databaseConn.getConnection(conn);

        try {           
            String studentName,studentEmail;
            int studentId;
            Instant instant=Instant.now();
            long time=instant.getEpochSecond();
             String sql;
            sql = "select Student.userId,User.name,User.email from Student\n" +
"join User on User.userId=Student.userId where User.name like '%"+student_name+"%'";
    
            PreparedStatement stm1 = conn.prepareStatement(sql);
            ResultSet rs=stm1.executeQuery();
            while(rs.next()) {
                    studentId = rs.getInt("userId");
                    studentName = rs.getString("name");
                    studentEmail = rs.getString("email");
                    singleChoice.accumulate("StudentId", studentId);
                    singleChoice.accumulate("Name", studentName);
                    singleChoice.accumulate("email", studentEmail);
                    mainArray.add(singleChoice);
                    singleChoice.clear();
     
    }    
                    singleChoice.accumulate("Status", "Ok");
                    singleChoice.accumulate("Timestamp", timeStamp);
                    singleChoice.accumulate("studentList", mainArray);
                     databaseConn.closeConnection(conn,rs,stm1);


        }
        catch (SQLException ex) {
            String msg = ex.getMessage();
                      
                    } catch (Exception ex) {
            String msg = ex.getMessage();
          }
 
        if(mainArray.toString().equals("[]"))
        {
            singleChoice.clear();
                   
             singleChoice.accumulate("Status", "Error");
        singleChoice.accumulate("Timestamp", timeStamp);
       
       }
        
         return singleChoice.toString();
             }
    
}
