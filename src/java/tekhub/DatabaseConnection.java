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
import javax.ws.rs.Path;

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
             System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
             System.out.println(ex.getMessage());
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
