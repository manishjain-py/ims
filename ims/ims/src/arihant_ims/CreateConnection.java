/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arihant_ims;

/**
 *
 * @author admin
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class CreateConnection {

    Connection con;

    public static Connection connectDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:.//db//IMS_DB.sqlite");
            return con;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
    
    public static void DeleteAll(String TableName,Connection conn){
        
//        PreparedStatement pst;
//    String sql = "delete from "+ TableName ;
//        try {
//            pst = conn.prepareStatement(sql);
//             pst.executeUpdate();
//             if(!TableName.equals("TEMP")){
//              JOptionPane.showMessageDialog(null, "Database table cleared.");
//             }
//             pst.close(); 
//        } catch (SQLException ex) {
//             JOptionPane.showMessageDialog(null, "Error occured."+ex);
//            //Logger.getLogger(CreateConnection.class.getName()).log(Level.SEVERE, null, ex);
//        }
                   
    }
    
    
     public static void DeleteAll1(String TableName,Connection conn){
        
         PreparedStatement pst;
         String sql = "delete from " + TableName;
         try {
             pst = conn.prepareStatement(sql);
             pst.executeUpdate();
             if (!TableName.equals("TEMP")) {
                 //JOptionPane.showMessageDialog(null, "Database table cleared.");
             }
             pst.close();
         } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error occured." + ex);
             //Logger.getLogger(CreateConnection.class.getName()).log(Level.SEVERE, null, ex);
         }
                   
    }
    
    
}
