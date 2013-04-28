/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arihant_ims;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Administrator
 */
public class Validation extends javax.swing.JFrame {

    public Validation() {
        initComponents();
    }
    static Connection conn;
    static PreparedStatement pst;
    static ResultSet rs;

    public static String contactNumChk(String value) {

        char c = value.charAt(0);
        if (c == '+') {
            value = value.substring(1);
        }
        try {
            long l = Long.parseLong(value);
        } catch (Exception e) {
            return ("Please enter a valid contact number");
        }
        return ("");
    }

    public static String qtyChk(String value) {
        try {
            double d = Double.parseDouble(value);
            if (d < 0) {
                return ("Please enter a positive quantity");
            }
        } catch (Exception e) {
            return ("Please enter a valid quantity");
        }
        return ("");
    }
    
      public static boolean  ratioChk(String value) {
        try {
            double d = Double.parseDouble(value);
            if (d < 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    
    
    

    public static String reorderQtyChk(String value) {
        try {
            double d = Double.parseDouble(value);
            if (d <= 0) {
                return ("Please enter a positive quantity");
            }
        } catch (Exception e) {
            return ("Please enter a valid reorder quantity");
        }
        return ("");
    }

    public static String priceChk(String value) {
        try {
            double d = Double.parseDouble(value);
            if (d <= 0) {
                return ("Please enter a positive quantity");
            }
        } catch (Exception e) {
            return ("Please enter a valid price");
        }
        return ("");
    }

    public static String primaryKeyChk(String value, String ColumnName, String tableName) {
        String sql = "select * from " + tableName + " where " + ColumnName + " = '" + value.toLowerCase() + "'";
        // System.out.print(sql);
        conn = CreateConnection.connectDB();
        boolean bool = false;
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            bool = rs.next();
            if (bool) {
                return ("Duplicate value ");
            }
        } catch (Exception e) {
            // System.out.println("exception here in primary key chk");
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                // System.out.println("exception here in primary key chk");
            }
        }
        return ("");

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Validation().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
