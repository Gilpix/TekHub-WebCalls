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
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
    Date today = new Date();
    long timeStamp = today.getTime();
    JSONArray mainArray = new JSONArray();
    JSONObject mainObject = new JSONObject();
    DatabaseConnection databaseConn = new DatabaseConnection();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ItemResource
     */
    public ItemResource() {
    }

    /**
     * Retrieves representation of an instance of tekhub.ItemResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    
    //Get list of all items
    @GET
    @Path("getItemList")
    @Produces(MediaType.APPLICATION_JSON)
    public String getItemList() {

        JSONObject singleChoice = new JSONObject();
        mainObject.clear();
        mainArray.clear();
        Connection conn = null;
        conn = databaseConn.getConnection(conn);

        try {
            String itemname, itemDesc, isAvailable, availableDate, itemCondition, borrowNum, addedDate, avgRating, itemPic;
            int itemId;
            String sql;
            sql = "SELECT Item.itemId,itemname,itemDesc,isAvailable,availableDate,itemCondition,borrowNum,addedDate,avg(rating) as avgRating,itemPic from Item "
                    + "left join Feedback On Item.itemId=Feedback.itemId "
                    + "group by Item.itemId;";

            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                itemId = rs.getInt("itemId");
                itemname = rs.getString("itemname");
                itemDesc = rs.getString("itemDesc");
                isAvailable = rs.getString("isAvailable");
                availableDate = rs.getString("availableDate");
                itemCondition = rs.getString("itemCondition");
                borrowNum = rs.getString("borrowNum");
                addedDate = rs.getString("addedDate");
                avgRating = rs.getString("avgRating");
                itemPic = rs.getString("itemPic");

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
            databaseConn.closeConnection(conn, rs, stm);

        } catch (SQLException ex) {
            msg = ex.getMessage();

        } catch (Exception ex) {
            msg = ex.getMessage();
        }

        if (mainArray.toString().equals("[]")) {
            singleChoice.accumulate("Status", "error");
            singleChoice.accumulate("Timestamp", timeStamp);

        }

        return singleChoice.toString();

    }

 
    //Update availability of item
    @GET
    @Path("updateItemAvailability")
    @Produces(MediaType.APPLICATION_JSON)
    public String updateItemAvailability() {
        int qRes = 3;

        mainObject.clear();

        Connection conn = null;
        conn = databaseConn.getConnection(conn);

        try {
            String sql;
            //sql = "Update Item  set isAvailable='1'";
            //sql = "Update Item inner join Orders On Orders.itemId=Item.itemId set isAvailable='1' where Orders.returnDate<"+AdminResource.getCurrentDate();
            sql = "Update Item join (select itemId,returnDate from Orders) as rd On rd.itemId=Item.itemId set isAvailable='1' where rd.returnDate<=CURDATE();";

            PreparedStatement stm = conn.prepareStatement(sql);
            System.out.println("##########");

            qRes = stm.executeUpdate();
            System.out.println("##########" + qRes + "####");
            if (qRes >= 0) {
                mainObject.accumulate("Status", "ok");
                mainObject.accumulate("Timestamp", timeStamp);
            } else if (qRes < 0) {
                mainObject.accumulate("Status", "Error");
                mainObject.accumulate("Timestamp", timeStamp);
            }

            databaseConn.closeConnection(conn, null, stm);

        } catch (SQLException ex) {
            String Message = ex.getMessage();
        }
        // throw new UnsupportedOperationException();
        return mainObject.toString();
    }

   
    

    

}
