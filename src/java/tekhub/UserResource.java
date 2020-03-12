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
import java.util.Date;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
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
@Path("user")
public class UserResource  {
    
     String Message; 
 Date today=new Date();
 long timeStamp=today.getTime();
 JSONArray mainArray=new JSONArray();
 JSONObject mainObject = new JSONObject();
 
 DatabaseConnection databaseConn=new DatabaseConnection();
 

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UserResource
     */
    public UserResource() {
    }

    /**
     * Retrieves representation of an instance of tekhubuser.UserResource
     * @return an instance of java.lang.String
     */
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    
    
    
    
    @GET
    @Path("registerUser&{USERID}&{NAME}&{EMAIL}&{PASSWORD}&{MOBNO}&{AGE}&{GENDER}")
    @Produces(MediaType.APPLICATION_JSON)
    public String registerUser(@PathParam("USERID") int user_id, @PathParam("NAME") String name, @PathParam("EMAIL") String email, @PathParam("PASSWORD") String password, 
            @PathParam("MOBNO") String mobNo, @PathParam("AGE") int age, @PathParam("GENDER") String gender) {
        
        
        
        Connection conn = null;
              conn=  databaseConn.getConnection(conn);
              int qRes=0,qRes1=0;
         
        try {           
            
              String sql,sql1;
    sql = "INSERT INTO User VALUES(?,?,?,?,?)";
    sql1 = "INSERT INTO Student VALUES(?,?,?,?,?)";
    
   
      PreparedStatement stm = conn.prepareStatement(sql);
                stm.setInt(1,user_id);
                stm.setString(2,name);
                stm.setString(3,"1");
                stm.setString(4,email);
                stm.setString(5,password);
                
                PreparedStatement stm1 = conn.prepareStatement(sql1);
                stm1.setInt(1,user_id);
                stm1.setString(2,mobNo);
                stm1.setString(3,"1");
                stm1.setInt(4,age);
                stm1.setString(5,gender);
                

                  qRes=stm.executeUpdate();
                  qRes1=stm1.executeUpdate();
                  if(qRes==1&&qRes1==1)
                  {
                   mainObject.accumulate("Status", "ok");
                    mainObject.accumulate("Timestamp", timeStamp);
                  }

    
                  databaseConn.closeConnection(conn,null,stm);
            
        } catch (SQLException ex) {
            Message=ex.getMessage();
        }
        
         if(qRes!=1)
        {
            mainObject.clear();
        mainObject.accumulate("Status", "error");
        mainObject.accumulate("Timestamp", timeStamp);
        mainObject.accumulate("Message", "Not Registered");
       }
         
         return mainObject.toString();
         
    }
    
    
    
    
 
    
      @GET
            @Path("userProfile&{USERID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String userProfile(@PathParam("USERID") String usr_id) {
        
        JSONObject singleUser =new JSONObject();
        mainObject.clear();
        mainArray.clear();
        
        Connection conn = null;
              conn=  databaseConn.getConnection(conn);
         
        try {          
            
              String sql;
              String userId,name,email,mobNo,age,gender;
    sql = "SELECT Student.userId,name,email,mobNo,age,gender from User "
            + "left join Student ON Student.userId=User.userId WHERE Student.userId=?";
    
    
   
      PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1,usr_id);
                 
                ResultSet rs=stm.executeQuery();

                while(rs.next()) {
       userId = rs.getString("userId");
     name = rs.getString("name");
     email = rs.getString("email");
     mobNo = rs.getString("mobNo");
     age = rs.getString("age");
     gender = rs.getString("gender");
//Display values
    singleUser.accumulate("Status", "ok");
        singleUser.accumulate("Timestamp", timeStamp);
          //singleUser.accumulate("USERNAME", userName);
     singleUser.accumulate("userId", userId);
        singleUser.accumulate("name", name);
          singleUser.accumulate("email", email);
          singleUser.accumulate("mobNo", mobNo);
        singleUser.accumulate("age", age);
          singleUser.accumulate("gender", gender);
          //singleUser.accumulate("PASSWORD", user_password);
     
    }
                     databaseConn.closeConnection(conn,rs,stm);


        }
        catch (SQLException ex) {
                       Message=ex.getMessage();
                      
                    } catch (Exception ex) {
              Message=ex.getMessage();
          }
 
        if(singleUser.toString().equals("{}"))
        {
               Message=" Record not found";
                   
             singleUser.accumulate("Status", "error");
        singleUser.accumulate("Timestamp", timeStamp);
         singleUser.accumulate("UserID", usr_id);
        singleUser.accumulate("Message",  Message);
       }
        
         return singleUser.toString();
         
    }
   
    
    
    
    
    @GET
    @Path("editProfile&{USERID}&{EMAIL}&{MOBNO}&{AGE}&{GENDER}")
    @Produces(MediaType.APPLICATION_JSON)
    public String editProfile(@PathParam("USERID") int user_id,  @PathParam("EMAIL") String email, 
            @PathParam("MOBNO") String mobNo, @PathParam("AGE") int age, @PathParam("GENDER") String gender) {
        
        
        
        Connection conn = null;
              conn=  databaseConn.getConnection(conn);
              int qRes=0,qRes1=0;
         
        try {           
            
              String sql,sql1;
    sql = "UPDATE User set email=? where userId=?;";
    sql1 = "UPDATE Student set mobNo=?,age=?,gender=?  where userId=?;";
    
   
      PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1,email);
                stm.setInt(2,user_id);
                
                PreparedStatement stm1 = conn.prepareStatement(sql1);
                
                stm1.setString(1,mobNo);
               
                stm1.setInt(2,age);
                stm1.setString(3,gender);
                stm1.setInt(4,user_id);
                

                  qRes=stm.executeUpdate();
                  qRes1=stm1.executeUpdate();
                  if(qRes==1&&qRes1==1)
                  {
                   mainObject.accumulate("Status", "ok");
                    mainObject.accumulate("Timestamp", timeStamp);
                  }

    
                  databaseConn.closeConnection(conn,null,stm);
            
        } catch (SQLException ex) {
            Message=ex.getMessage();
        }
        
         if(qRes!=1)
        {
            mainObject.clear();
        mainObject.accumulate("Status", "error");
        mainObject.accumulate("Timestamp", timeStamp);
        mainObject.accumulate("Message", "Not Registered");
       }
         
         return mainObject.toString();
         
    }
    
    
    
    
    
     @GET
    @Path("userLogout&{USERID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String userLogout(@PathParam("USERID") int user_id) {
        
        
        
        Connection conn = null;
              conn=  databaseConn.getConnection(conn);
              int qRes=0,qRes1=0;
              
        try {           
            
              String sql,sql1;
    sql = "UPDATE User set isActive=? where userId=?;";
    
   
      PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1,"1");
                stm.setInt(2,user_id);
                

                  qRes=stm.executeUpdate();
                  if(qRes==1)
                  {
                   mainObject.accumulate("Status", "ok");
                    mainObject.accumulate("Timestamp", timeStamp);
                  }

    
                  databaseConn.closeConnection(conn,null,stm);
            
        } catch (SQLException ex) {
            Message=ex.getMessage();
        }
        
         if(qRes!=1)
        {
            mainObject.clear();
        mainObject.accumulate("Status", "error");
        mainObject.accumulate("Timestamp", timeStamp);
        mainObject.accumulate("Message", "not logout");
       }
         
         return mainObject.toString();
         
    }
    
    
    
    
    
    
    
    
    
    @GET
    @Path("userIdAlreadyLoggedIn&{USERID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String userIdAlreadyLoggedIn(@PathParam("USERID") int user_id) {
        
        
        
        Connection conn = null;
              conn=  databaseConn.getConnection(conn);
              int qRes=0,qRes1=0;
         
        try {           
            
              String sql,sql1;
    sql = "UPDATE User set isActive=? where userId=?;";
    
   
      PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1,"0");
                stm.setInt(2,user_id);
                

                  qRes=stm.executeUpdate();
                  if(qRes==1)
                  {
                   mainObject.accumulate("Status", "ok");
                    mainObject.accumulate("Timestamp", timeStamp);
                  }

    
                  databaseConn.closeConnection(conn,null,stm);
            
        } catch (SQLException ex) {
            Message=ex.getMessage();
        }
        
         if(qRes!=1)
        {
            mainObject.clear();
        mainObject.accumulate("Status", "error");
        mainObject.accumulate("Timestamp", timeStamp);
        mainObject.accumulate("Message", "not logout");
       }
         
         return mainObject.toString();
         
    }
    
    
    
    
    
    
     
    @GET
    @Path("resetPassword&{USERID}&{PASSWORD}")
    @Produces(MediaType.APPLICATION_JSON)
    public String resetPassword(@PathParam("USERID") int user_id,  @PathParam("PASSWORD") String newPassword) {
        
       
        Connection conn = null;
              conn=  databaseConn.getConnection(conn);
              int qRes=0;
         
        try {           
          
              String sql;
    sql = "UPDATE User set password=? where userId=?;";
    
   
      PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1,newPassword);
                stm.setInt(2,user_id);
                
                  qRes=stm.executeUpdate();
                
                  if(qRes==1)
                  {
                   mainObject.accumulate("Status", "ok");
                    mainObject.accumulate("Timestamp", timeStamp);
                  }
                  databaseConn.closeConnection(conn,null,stm);
            
        } catch (SQLException ex) {
            Message=ex.getMessage();
        }
        
         if(qRes!=1)
        {
            mainObject.clear();
        mainObject.accumulate("Status", "error");
        mainObject.accumulate("Timestamp", timeStamp);
        mainObject.accumulate("Message", "Not Registered");
       }
         
         return mainObject.toString();
         
    }
    
    
    
    
    
    
    
    
    

  @GET
            @Path("userLogin&{USERID}&{PASSWORD}")
    @Produces(MediaType.APPLICATION_JSON)
    public String userLogin(@PathParam("USERID") String usr_id,@PathParam("PASSWORD") String usr_password) {
        
        
        JSONObject singleUser =new JSONObject();
        mainObject.clear();
        mainArray.clear();
        String user_active="0";
        
        System.out.println("Hi");
        
        
        Connection conn = null;
              conn=  databaseConn.getConnection(conn);
         
        try {          
            
              String sql;
    sql = "SELECT userId,password,isActive FROM User WHERE userId=? and password=?";
   
      PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1,usr_id);
                 stm.setString(2,usr_password);

                ResultSet rs=stm.executeQuery();

                while(rs.next()) {
      String user_id = rs.getString("USERID");
    String user_password = rs.getString("PASSWORD");
     user_active = rs.getString("isActive");
   
     singleUser.accumulate("UserID", user_id);
        singleUser.accumulate("Password", user_password);
        singleUser.accumulate("userActive", user_active);
           
    }
                     databaseConn.closeConnection(conn,rs,stm);


        }
        catch (SQLException ex) {
                       Message=ex.getMessage();
                      
                    } catch (Exception ex) {
              Message=ex.getMessage();
          }
        if(!singleUser.toString().equals("{}"))
        {
        if(singleUser.getString("UserID").equals(usr_id) && singleUser.getString("Password").equals(usr_password) )
        {
            singleUser.clear();
             singleUser.accumulate("Status", "ok");
        singleUser.accumulate("Timestamp", timeStamp);
        singleUser.accumulate("userActive", user_active);
        }
        }
        else 
        {
           
               Message=" Wrong Credentials";
                   
             singleUser.accumulate("Status", "error");
        singleUser.accumulate("Timestamp", timeStamp);
         singleUser.accumulate("UserID", usr_id);
        singleUser.accumulate("Message",  Message);
        }

        
         return singleUser.toString();
         
    }
    
}