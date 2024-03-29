/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arihant_ims;

/**
 *
 * @author admin
 */
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class prodDelete extends javax.swing.JDialog {

    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    String click = "";
   
    /**
     * Creates new form RawMaterials
     */
    public prodDelete(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        conn = CreateConnection.connectDB();
        initComponents();
        Dimension scrn = getToolkit().getScreenSize();
        this.setBounds(0, 0, scrn.width, scrn.height);
        Table.setSelectionForeground(Color.red);
        Table.setRowHeight(25);
        try {

            /*
             * String sq = "insert into RawMaterials values(?,?,?,?)";
             *
             * pst = conn.prepareStatement(sq); pst.setString(1,"tom");
             * pst.setString(2, "6"); pst.setString(3, "67"); pst.setString(4,
             * "46"); pst.addBatch(); pst.executeBatch();
             *
             *
             *
             */

            fill_table();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
            }


        }
    }

    private void fill_table() {
        try {
            pst = conn.prepareStatement("select * from production");
            rs = pst.executeQuery();
            Table.setModel(C.getModel(rs));
        } catch (SQLException ex) {
            Logger.getLogger(prodDelete.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception ex) {
            }


        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        ButtonDelete = new javax.swing.JButton();
        ButtonRefresh = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("RAW MATERIALS");
        setBackground(new java.awt.Color(255, 51, 204));
        setForeground(java.awt.Color.white);
        setUndecorated(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CONTROLS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 24), new java.awt.Color(51, 51, 255))); // NOI18N

        ButtonDelete.setText("<html><h2>DELETE");
        ButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonDeleteActionPerformed(evt);
            }
        });
        ButtonDelete.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ButtonDeleteKeyPressed(evt);
            }
        });

        ButtonRefresh.setText("<html><h2>REFRESH");
        ButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonRefreshActionPerformed(evt);
            }
        });
        ButtonRefresh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ButtonRefreshKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonDelete)
                    .addComponent(ButtonRefresh))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(ButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ButtonRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "LIST", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 24), new java.awt.Color(51, 0, 255))); // NOI18N

        Table.setAutoCreateRowSorter(true);
        Table.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        Table.setRowMargin(2);
        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 899, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton2.setText("<html><h1>HOME");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jButton2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton2KeyPressed(evt);
            }
        });

        jButton3.setText("<html><h1>Back to Production");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jButton3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton3KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(122, 122, 122)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 206, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
   click = "selectedForDelete";
    }//GEN-LAST:event_TableMouseClicked

    private void ButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDeleteActionPerformed
 if (click.equals("selectedForDelete")) {
            int i = JOptionPane.showConfirmDialog(null, "Are you sure", "Delete Confirmation", 0);
        System.out.println(i);
        String tabl = "";
        if(i==0){
        int row_num = Table.getSelectedRow();
        String prod_id = Table.getModel().getValueAt(row_num,4 ).toString();
        String recipeName = Table.getModel().getValueAt(row_num,1 ).toString();
        double quantity =  Double.parseDouble(Table.getModel().getValueAt(row_num,2 ).toString());
        //1) updating Raw materials(addition)

        tabl=  checkCustomTable(prod_id);     // checking whether it is recipe or custom recipe
              if(tabl == "Recipe")     // to update raw materials according to recipe table
              {
                  updateByRecipe(recipeName);
                  System.out.println("deletion by recipee successsssssssssss");
              }
              else{                   // to update raw material according to custom recipe table
              updateByCustomRecipe(prod_id);
              System.out.println("deletion by custom successsssssssssss");
              }

        //2) updating finished goods(subtraction)

          updateFinishedGoods(recipeName,quantity);

        //3) deleting record from production table

          deleteFromProduction(prod_id);
          JOptionPane.showMessageDialog(null, "item deleted successfully");
        // Refreshing UI
          fill_table();
        }
    }//GEN-LAST:event_ButtonDeleteActionPerformed

    }
    private void deleteFromProduction(String prod_id){
        int pid = Integer.parseInt(prod_id);
       // deleting Record from database

        String sql = "delete from Production where prod_id ="+pid+"";
                Statement st;
        try {
            st = conn.createStatement();
             int chk_update = st.executeUpdate(sql);
                st.close();
        } catch (SQLException ex) {
            Logger.getLogger(prodDelete.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

   private void updateFinishedGoods(String rname, double quant) {
        String sql = "update FinishedGoods set openingStock = openingStock -" + quant + " where itemName = '" + rname + "'";
                Statement st;
        try {
            st = conn.createStatement();
             int chk_update = st.executeUpdate(sql);
                st.close();
        } catch (SQLException ex) {
            Logger.getLogger(prodDelete.class.getName()).log(Level.SEVERE, null, ex);
        }

   }
 private String checkCustomTable(String prod_id){
      

       try {
            pst = conn.prepareStatement("select * from CustomRecipee where prod_id ='" + prod_id + "'");
             rs = pst.executeQuery();
             if(rs.next()){
                 rs.close();
                 pst.close();
                return "customRecipe";
             }
rs.close();
pst.close();

        } catch (SQLException ex) {
            Logger.getLogger(prodDelete.class.getName()).log(Level.SEVERE, null, ex);
        }

    return "Recipe";

 }
   private void updateByRecipe(String recipeName){
        try {
            System.out.println(recipeName);
            pst = conn.prepareStatement("select * from Recipe where recipeeName ='"+recipeName+"'");
            rs = pst.executeQuery();
            if(rs.next()){
             int c = 1;
              System.out.println("m aa gya ander");
              while(c<40){
                String itm = rs.getString(++c);
                Double quant = rs.getDouble(++c);
                  System.out.println(itm+"--------"+quant);
                 try {
                String sql = "update RawMaterials set openingStock = openingStock +" + quant + " where itemName = '" + itm + "'";
                Statement st = conn.createStatement();
                int chk_update = st.executeUpdate(sql);
                st.close();
                if (chk_update == 0) {
                    String sql1 = "update FinishedGoods set openingStock = openingStock +" + quant + " where itemName = '" + itm + "'";
                    Statement st1 = conn.createStatement();
                    st1.executeUpdate(sql1);
                    st1.close();
                }
                System.out.println("chkupdate"+chk_update);

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
                }
            }
            rs.close();
            pst.close();
       
        } catch (SQLException ex) {
            Logger.getLogger(prodDelete.class.getName()).log(Level.SEVERE, null, ex);
        }

   }
    private void updateByCustomRecipe(String prod_id){
        try {
            System.out.println("productionid="+prod_id);
            int pid = Integer.parseInt(prod_id);
            pst = conn.prepareStatement("select * from CustomRecipee where prod_id ="+pid+"");
            rs = pst.executeQuery();
            if(rs.next()){
            System.out.println("m aa gya");
                int c = 1;

              while(c<40){
                String itm = rs.getString(++c);
                Double quant = rs.getDouble(++c);
                System.out.println(itm+"--------"+quant);

                 try {
                String sql = "update RawMaterials set openingStock = openingStock +" + quant + " where itemName = '" + itm + "'";
                Statement st = conn.createStatement();
                int chk_update = st.executeUpdate(sql);
                st.close();
                if (chk_update == 0) {
                    String sql1 = "update FinishedGoods set openingStock = openingStock +" + quant + " where itemName = '" + itm + "'";
                    Statement st1 = conn.createStatement();
                    st1.executeUpdate(sql1);
                    st1.close();
                }
                System.out.println("chkupdate"+chk_update);

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
                }
            }
            rs.close();
            pst.close();

            // dELETING record from custom recipe

                String sql = "delete from CustomRecipee where prod_id ="+pid+"";
                Statement st = conn.createStatement();
                int chk_update = st.executeUpdate(sql);
                st.close();
        } catch (SQLException ex) {
            Logger.getLogger(prodDelete.class.getName()).log(Level.SEVERE, null, ex);
        }

   }

    private void ButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonRefreshActionPerformed
      
    }//GEN-LAST:event_ButtonRefreshActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setVisible(false);
       // this.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyPressed
       // TODO add your handling code here:
    }//GEN-LAST:event_jButton2KeyPressed

    private void ButtonDeleteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ButtonDeleteKeyPressed
       // TODO add your handling code here:
    }//GEN-LAST:event_ButtonDeleteKeyPressed

    private void ButtonRefreshKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ButtonRefreshKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonRefreshKeyPressed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
       // TODO add your handling code here:
    }//GEN-LAST:event_formKeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.setVisible(false);
        Production er = new Production(null, true);
        er.setVisible(true);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(prodDelete.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(prodDelete.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(prodDelete.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(prodDelete.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                prodDelete dialog = new prodDelete(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonDelete;
    private javax.swing.JButton ButtonRefresh;
    private javax.swing.JTable Table;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
