/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arihant_ims;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class Query {

    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    public ArrayList<ProductionProcess> a(  String itemName ,String sDate) {

        //String itemName = "r1";
       // String sDate = "2012-12-23";
        //make array list of all strings less than sdate
        ArrayList<ProductionProcess> al = new ArrayList<ProductionProcess>();
        //al.add(sDate);


        String sql = " select * from Production where date < '" + sDate + "'";
        System.out.println(sql);
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                ProductionProcess objpp = new ProductionProcess();
                objpp.setDate(rs.getString(1));
                objpp.setReceipeName(rs.getString(2));
                objpp.setQuantity(rs.getInt(3));
                al.add(objpp);
                System.out.println("mm");
                System.out.println(objpp.getQuantity());
            }

        } catch (SQLException ex) {

            Logger.getLogger(InTransaction.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException e) {
            }
        }



         System.out.println(al.size());
        //array list is set


        for (int i = 0; i < al.size(); i++) {

            ProductionProcess objPP = al.get(i);

            String recepie = objPP.getReceipeName();
            int quantity = objPP.getQuantity();
            String recipeList[] = returnRecepieList();
            int noOfRecipe = recipeList.length;

            int quantityOfItemInRecipe = quantityOfItemInRecipe(recepie, itemName, quantity);

            if (quantityOfItemInRecipe == 0) {
                al.remove(i);
                i--;
            }


        }


        return al;

    }

    public int quantityOfItemInRecipe(String recipe, String itemName, int quantity) {
        int quantityOfItemInRecipe = 0;

        if (itemName.equals(recipe)) {
            return quantity;
        }
        String recipeList[] = returnRecepieList();
        int noOfRecipe = recipeList.length;

        String sql = " select * from Recipe where recipeeName = '" + recipe + "'";
        System.out.println(sql);
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                rs.getString(1);
                int tempQty = 0;
                int col = 2;
                while (col > 40 || rs.getString(col).isEmpty() || rs.getString(col).equals(null)) {
                    tempQty = tempQty + quantityOfItemInRecipe(rs.getString(col), itemName, rs.getInt(col + 1));
                    col = col + 2;
                }
                quantityOfItemInRecipe = quantity * tempQty;
            }

        } catch (SQLException ex) {

            Logger.getLogger(InTransaction.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException e) {
            }
        }

        return quantityOfItemInRecipe;
    }public String[] returnRecepieList() {
        String sql = " select * from Recipe";
        String recipe[] = null;
        // System.out.println(sql);
        int noOfRecepie = 0;
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                noOfRecepie++;
            }
            rs.first();
            recipe = new String[noOfRecepie];
            int i = 0;
            while (rs.next()) {
                recipe[i] = rs.getString("recipeeName");
                i++;
            }


        } catch (SQLException ex) {

            Logger.getLogger(InTransaction.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException e) {
            }
        }
        return recipe;
    }


    public static void main(String ags[]) {
        try {
            Query objQuery = new Query();
            
            ArrayList<ProductionProcess> al = new ArrayList<ProductionProcess>();
            al = objQuery.a("2012-02-20","r1");
            for (int i = 0; i < al.size(); i++) {

            ProductionProcess objPP = al.get(i);
            System.out.println(objPP.getReceipeName());
            System.out.println(objPP.getQuantity());
        }
        } catch (Exception e) {
        }
    }
}
