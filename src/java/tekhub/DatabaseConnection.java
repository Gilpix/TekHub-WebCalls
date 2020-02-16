/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tekhub;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author kulartist
 */
@Path("generic")
public class DatabaseConnection {
    
     static String classs = "com.mysql.cj.jdbc.Driver";
     static String url = "jdbc:mysql://tekhubinstance.co3rvmyoxbtb.ca-central-1.rds.amazonaws.com:3306/TEKHUB?autoReconnect=true&useSSL=false";
     static String un = "kuldeep";
     static String password = "Sonu1993";    
    
   
    public Connection getConnection(Connection conn) {
      
        try {
             Class.forName(classs);
            conn = DriverManager.getConnection(url, un, password);
            
        } catch (SQLException ex) {
            //Logger.getLogger(ShapesAny.class.getName()).log(Level.SEVERE, null, ex);
            //return ex.getMessage();
             System.out.println(ex.getMessage()+"1111#######################$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$###");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
             System.out.println(ex.getMessage()+"  22222#######################$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$###");
           // return ex.getMessage();
        }

         return conn;
        
    }
    
    
    
    
     public void closeConnection(Connection conn, ResultSet rs,PreparedStatement ps )  {
         
         
        try {
                        if (rs != null)
                            rs.close();
                        if (ps != null)
                            ps.close();
                        if (conn != null)
                            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
         
     }
    
    
    
    
    
    
    
    
}
