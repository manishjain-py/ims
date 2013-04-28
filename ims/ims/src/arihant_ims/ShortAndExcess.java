package arihant_ims;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class ShortAndExcess extends javax.swing.JDialog {

    Connection conn;

    public ShortAndExcess(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        conn = CreateConnection.connectDB();
        fill_table();


    }

    private void fill_table() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM ShortAndExcess order by rowid limit 40");

            rs = pst.executeQuery();
            Table.setModel(C.getModel(rs));
        } catch (SQLException ex) {
            Logger.getLogger(InTransaction.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception ex) {
            }
        }
    }

    public void filler_rawMaterial() {
        ComboItemName.removeAllItems();
        ComboItemName.addItem("Select");
        try {
            PreparedStatement pst = conn.prepareStatement("Select itemName from RawMaterials");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ComboItemName.addItem(rs.getString("itemName"));

            }
            rs.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(ShortAndExcess.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void filler_finishedGoods() {
        ComboItemName.removeAllItems();
        ComboItemName.addItem("Select");
        try {
            PreparedStatement pst = conn.prepareStatement("Select itemName from FinishedGoods");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ComboItemName.addItem(rs.getString("itemName"));
            }
            rs.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(ShortAndExcess.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jFrame3 = new javax.swing.JFrame();
        jFrame4 = new javax.swing.JFrame();
        createConnection1 = new arihant_ims.CreateConnection();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        ComboItemName = new javax.swing.JComboBox();
        ComboCategory = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        ComboSoE = new javax.swing.JComboBox();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnAdd1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame3Layout = new javax.swing.GroupLayout(jFrame3.getContentPane());
        jFrame3.getContentPane().setLayout(jFrame3Layout);
        jFrame3Layout.setHorizontalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame3Layout.setVerticalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jFrame4.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jFrame4Layout = new javax.swing.GroupLayout(jFrame4.getContentPane());
        jFrame4.getContentPane().setLayout(jFrame4Layout);
        jFrame4Layout.setHorizontalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame4Layout.setVerticalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Editor", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24), new java.awt.Color(0, 0, 255))); // NOI18N

        jLabel2.setText("<html><h2>Category");

        ComboItemName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select" }));
        ComboItemName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ComboItemNameKeyPressed(evt);
            }
        });

        ComboCategory.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "Raw Materials", "Finished Goods" }));
        ComboCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboCategoryActionPerformed(evt);
            }
        });
        ComboCategory.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ComboCategoryKeyPressed(evt);
            }
        });

        jLabel4.setText("<html><h2>Short / Excess");

        txtQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantityActionPerformed(evt);
            }
        });
        txtQuantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQuantityKeyPressed(evt);
            }
        });

        jLabel3.setText("<html><h2>Item Name");

        ComboSoE.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "Short", "Excess" }));
        ComboSoE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ComboSoEKeyPressed(evt);
            }
        });

        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });

        jLabel5.setText("<html><h2>Quantity");

        txtDate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 51), null, null, java.awt.Color.white));
        txtDate.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDate.setEnabled(false);

        jLabel1.setText("<html><h2>Date");

        btnAdd.setText("<html><h1>Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        btnAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAddKeyPressed(evt);
            }
        });

        btnAdd1.setText("<html><h1>Delete");
        btnAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd1ActionPerformed(evt);
            }
        });
        btnAdd1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAdd1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(ComboCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ComboItemName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ComboSoE, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAdd1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(80, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ComboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboSoE, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Short & Excess Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24), new java.awt.Color(0, 0, 204))); // NOI18N

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Date", "Category", "Item Name", "Quantity", "Short/Excess"
            }
        ));
        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });
        Table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(Table);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
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

        jButton1.setText("Clear DB");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(328, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(395, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ComboCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboCategoryActionPerformed
        String category = ComboCategory.getSelectedItem().toString();
        if (category.equals("Raw Materials")) {
            filler_rawMaterial();
        } else if (category.equals("Finished Goods")) {
            filler_finishedGoods();
        } else {                                                // if selected item is "Select" then reset combo itemname
            ComboItemName.removeAllItems();
            ComboItemName.addItem("Select");
        }
    }//GEN-LAST:event_ComboCategoryActionPerformed

    private void txtQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantityActionPerformed

    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar obj = jDateChooser1.getCalendar();
        String dateNow = "";
        if (obj != null) {
            dateNow = formatter1.format(obj.getTime());
        }
        txtDate.setText(dateNow);
    }//GEN-LAST:event_jDateChooser1PropertyChange
    public void RawMaterial_update(String item, String type, double qty) {
        if (type.equals("Short")) {
            try {
                String sql = "update RawMaterials set openingStock = openingStock -" + qty + " where itemName = '" + item + "'";
                Statement st = conn.createStatement();
                int chk_update = st.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                try {
                } catch (Exception e) {
                }

            }
        } else {
            try {
                String sql = "update RawMaterials set openingStock = openingStock +" + qty + " where itemName = '" + item + "'";
                Statement st = conn.createStatement();
                int chk_update = st.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                try {
                } catch (Exception e) {
                }

            }
        }

    }
    public void RawMaterial_undo(String item, String type, double qty) {
        if (type.equals("Short")) {
            try {
                String sql = "update RawMaterials set openingStock = openingStock +" + qty + " where itemName = '" + item + "'";
                Statement st = conn.createStatement();
                int chk_update = st.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                try {
                } catch (Exception e) {
                }

            }
        } else {
            try {
                String sql = "update RawMaterials set openingStock = openingStock -" + qty + " where itemName = '" + item + "'";
                Statement st = conn.createStatement();
                int chk_update = st.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                try {
                } catch (Exception e) {
                }

            }
        }

    }

    public void Finished_update(String item, String type, double qty) {
        if (type.equals("Short")) {
            try {
                String sql = "update FinishedGoods set openingStock = openingStock -" + qty + " where itemName = '" + item + "'";
                Statement st = conn.createStatement();
                int chk_update = st.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                try {
                } catch (Exception e) {
                }

            }
        } else {
            try {
                String sql = "update FinishedGoods set openingStock = openingStock +" + qty + " where itemName = '" + item + "'";
                Statement st = conn.createStatement();
                int chk_update = st.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                try {
                } catch (Exception e) {
                }

            }
        }


    }
    public void Finished_undo(String item, String type, double qty) {
        if (type.equals("Short")) {
            try {
                String sql = "update FinishedGoods set openingStock = openingStock +" + qty + " where itemName = '" + item + "'";
                Statement st = conn.createStatement();
                int chk_update = st.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                try {
                } catch (Exception e) {
                }

            }
        } else {
            try {
                String sql = "update FinishedGoods set openingStock = openingStock -" + qty + " where itemName = '" + item + "'";
                Statement st = conn.createStatement();
                int chk_update = st.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                try {
                } catch (Exception e) {
                }

            }
        }


    }
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        boolean valid = validity();
        if (valid == true) {
            int id = 1;
            int rcount = Table.getRowCount();
            if (rcount != 0) //  if it is not a first record then take value of id from table else id == 0            
            {
                id = Integer.parseInt(Table.getValueAt(rcount - 1, 0).toString()) + 1;
            }
            String category = ComboCategory.getSelectedItem().toString();
            String itemName = ComboItemName.getSelectedItem().toString();
            String soe = ComboSoE.getSelectedItem().toString();
            String date = txtDate.getText();
            Double qty = Double.parseDouble(txtQuantity.getText());
            try {
                PreparedStatement pst = conn.prepareStatement("insert into ShortAndExcess values (?,?,?,?,round(?,2),?)");
                pst.setInt(1, id);
                pst.setString(2, date);
                pst.setString(3, category);
                pst.setString(4, itemName);
                pst.setDouble(5, qty);
                pst.setString(6, soe);
                pst.addBatch();
                pst.executeBatch();
                pst.close();
                JOptionPane.showMessageDialog(null, "Record added successfully");
                RawMaterial_update(itemName, soe, qty);
                Finished_update(itemName, soe, qty);
                reset();
                fill_table();
            } catch (SQLException ex) {
                Logger.getLogger(ShortAndExcess.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {//do nothing
        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CreateConnection.DeleteAll("ShortAndExcess", conn);

        ShortAndExcess soa = new ShortAndExcess(null, true);   // to refresh the page
        soa.setVisible(true);
        this.setVisible(false);


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyPressed
        int a = evt.getKeyCode();
        if (a == 10) {
            try {
                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
            } catch (AWTException ex) {
                Logger.getLogger(OutTransaction.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (a == 27) {
            jButton2.doClick();
        } else if (a == 113) {
            btnAdd.doClick();
        }          // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2KeyPressed

    private void btnAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAddKeyPressed
        int a = evt.getKeyCode();
        if (a == 10) {
            try {
                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
            } catch (AWTException ex) {
                Logger.getLogger(OutTransaction.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (a == 27) {
            jButton2.doClick();
        } else if (a == 113) {
            btnAdd.doClick();
        }         // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddKeyPressed
    public void deletion(String sql) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "item deleted");
        } catch (SQLException ex) {
            //System.out.print("hi");
            //   Logger.getLogger(SampleCopy.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
            }
        }
    }
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        int a = evt.getKeyCode();
        if (a == 27) {
            jButton2.doClick();
        } else if (a == 113) {
            btnAdd.doClick();
        }          // TODO add your handling code here:
    }//GEN-LAST:event_formKeyPressed

    private void ComboCategoryKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ComboCategoryKeyPressed
        int a = evt.getKeyCode();
        if (a == 27) {
            jButton2.doClick();
        } else if (a == 113) {
            btnAdd.doClick();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_ComboCategoryKeyPressed

    private void ComboItemNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ComboItemNameKeyPressed
        int a = evt.getKeyCode();
        if (a == 27) {
            jButton2.doClick();
        } else if (a == 113) {
            btnAdd.doClick();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_ComboItemNameKeyPressed

    private void ComboSoEKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ComboSoEKeyPressed
        int a = evt.getKeyCode();
        if (a == 27) {
            jButton2.doClick();
        } else if (a == 113) {
            btnAdd.doClick();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_ComboSoEKeyPressed

    private void txtQuantityKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQuantityKeyPressed
        int a = evt.getKeyCode();
        if (a == 27) {
            jButton2.doClick();
        } else if (a == 113) {
            btnAdd.doClick();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantityKeyPressed

    private void TableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableKeyPressed
        int a = evt.getKeyCode();
        if (a == 27) {
            jButton2.doClick();
        } else if (a == 113) {
            btnAdd.doClick();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_TableKeyPressed

    private void btnAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd1ActionPerformed
        int row_num = Table.getSelectedRow();
        String row_id = Table.getModel().getValueAt(row_num, 0).toString();
        String itemName = Table.getModel().getValueAt(row_num, 3).toString();
        String soe = Table.getModel().getValueAt(row_num, 5).toString();
        Double qty = Double.parseDouble(Table.getModel().getValueAt(row_num, 4).toString());
        deletion("delete from ShortAndExcess where id=" + row_id);
        RawMaterial_undo(itemName, soe, qty);
        Finished_undo(itemName, soe, qty);
        fill_table();
    }//GEN-LAST:event_btnAdd1ActionPerformed

    private void btnAdd1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAdd1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAdd1KeyPressed

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
        int row_num = Table.getSelectedRow();
        String row_id = Table.getModel().getValueAt(row_num, 0).toString();
        // TODO add your handling code here:
    }//GEN-LAST:event_TableMouseClicked

    public boolean validity() {
        String category = ComboCategory.getSelectedItem().toString();
        String itemName = ComboItemName.getSelectedItem().toString();
        String soe = ComboSoE.getSelectedItem().toString();
        String date = txtDate.getText();
        boolean qty = Validation.ratioChk(txtQuantity.getText());
        if (category.equals("Select")) {
            JOptionPane.showMessageDialog(null, "Please Select Category");
            return false;
        }
        if (itemName.equals("Select")) {
            JOptionPane.showMessageDialog(null, "Please Select ItemName");
            return false;
        }
        if (soe.equals("Select")) {
            JOptionPane.showMessageDialog(null, "Please Select Short/Excess");
            return false;
        }
        if (date.equals("")) {
            JOptionPane.showMessageDialog(null, "Please Select Date");
            return false;
        }
        if (qty == false) {
            JOptionPane.showMessageDialog(null, "Please Enter Valid Quantity");
            return false;
        } else {
            return true;
        }
    }

    public void reset() {
        txtDate.setText("");

        ComboItemName.removeAllItems();
        ComboItemName.addItem("Select");
        ComboSoE.removeAllItems();
        ComboSoE.addItem("Select");
        ComboSoE.addItem("Short");
        ComboSoE.addItem("Excess");
        ComboCategory.setSelectedItem("Select");
        txtQuantity.setText("");
    }

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
            java.util.logging.Logger.getLogger(ShortAndExcess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShortAndExcess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShortAndExcess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShortAndExcess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                ShortAndExcess dialog = new ShortAndExcess(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox ComboCategory;
    private javax.swing.JComboBox ComboItemName;
    private javax.swing.JComboBox ComboSoE;
    private javax.swing.JTable Table;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAdd1;
    private arihant_ims.CreateConnection createConnection1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JFrame jFrame4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtQuantity;
    // End of variables declaration//GEN-END:variables
}
