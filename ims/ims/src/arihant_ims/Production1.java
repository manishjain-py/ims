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
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

public class Production1 extends javax.swing.JDialog {

    String button = "";
    static int donow = 0;
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    int row = 0, col = 0;
    int row_temp = 0;
    int row_num = 0;
    double original_batch = 0;
    int flag = 0;
    String quantity[] = new String[20];
    int prod_id = 0;

    public Connection getConnection() {
        return conn;
    }

    /**
     * Creates new form recipe
     */
    public Production1(java.awt.Frame parent, boolean modal) {

        super(parent, modal);
        conn = CreateConnection.connectDB();
        initComponents();
        Dimension scrn = getToolkit().getScreenSize();
        this.setBounds(0, 0, scrn.width, scrn.height);
        Table.setRowHeight(25);
        pnlCustom.setVisible(false);
        btnGo.setVisible(false);
        //Panel.setVisible(false);
        ComboCustomRecipee.setVisible(false);
        lblCustom.setVisible(false);
        TxtBat.setEditable(false);
        jButton3.setVisible(false);



        filler2();
    }

    public void setv(Boolean flag) {
        Panel.setVisible(flag);
        ButtonEdit.setVisible(flag);
        ButtonDelete.setVisible(flag);
    }

    private void filler2() {
        try {

            pst = conn.prepareStatement("select * from Recipe order by \"recipeeName\"");
            rs = pst.executeQuery();
            while (rs.next()) {
                ComboRecipeName.addItem(rs.getString("recipeeName"));
                if (rs.getString("recipeeAlias") != null && !rs.getString("recipeeAlias").equals("")) {
                    ComboRecipeName.addItem(rs.getString("recipeeAlias"));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(Production1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void filler() {
        try {

            pst = conn.prepareStatement("select * from RawMaterials order by \"itemName\"");
            rs = pst.executeQuery();
            while (rs.next()) {
                ComboItem.addItem(rs.getString("itemName"));
                if (rs.getString("itemAlias") != null && !rs.getString("itemAlias").equals("")) {
                    ComboItem.addItem(rs.getString("itemAlias"));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(Production1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void filler_recipe(String rname) {
        try {
            PreparedStatement pst11 = conn.prepareStatement("select recipeeName , recipeeAlias from Recipe where recipeeName <> '" + rname + "' order by recipeeName");
            ResultSet rs11 = pst11.executeQuery();
            while (rs11.next()) {
              //  System.out.println(rs11.getString("recipeeName"));
                if (check_recursiveRecipe(rname, rs11.getString("recipeeName")) == false) {
                  //  System.out.println("in if");
                    ComboItem.addItem(rs11.getString("recipeeName"));
                    if (rs11.getString("recipeeAlias") != null && !rs11.getString("recipeeAlias").equals("")) {
                        ComboItem.addItem(rs11.getString("recipeeAlias"));
                    }
                  //  System.out.println("end if");
                }
            }
            rs11.close();
            pst11.close();
        } catch (SQLException ex) {
            Logger.getLogger(Production1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean check_recursiveRecipe(String parentRecipe, String childRecipe) {
        try {
            PreparedStatement pst1 = conn.prepareStatement("select * from Recipe where recipeeName = '" + childRecipe + "'");
            ResultSet rs1 = pst1.executeQuery();
            int i = 2;
            if (rs1.next()) {
                String childItem = rs1.getString(i);
                while (!(childItem).equals("") && !(childItem == null)) {
                   // System.out.println(childItem);
                    if (getRecipeName(childItem).equals("")) // it is a raw material
                    {
                       // System.out.println("rawmaterial");
                        i = i + 2;
                        childItem = rs1.getString(i);
                    } else { // it is a recipe
                        if (childItem.equals(parentRecipe)) {
                          //  System.out.println("true");
                            rs1.close();
                            pst1.close();
                            return true;
                        } else {          // going down to next level check
                          //  System.out.println("again recipe");
                            rs1.close();
                            pst1.close();
                            return (check_recursiveRecipe(parentRecipe, childItem));
                        }
                    }
                }
                rs1.close();
                pst1.close();
              //  System.out.println("ret 1");
                return false;
            }


        } catch (SQLException ex) {
            Logger.getLogger(Production1.class.getName()).log(Level.SEVERE, null, ex);
        }
       // System.out.println("ret 2");
        return false;

    }

    public void fillByCustom() {
        try {
            String name = ComboCustomRecipee.getSelectedItem().toString();

            if (!name.equals("Select")) {
                pst = conn.prepareStatement("Select * from CustomRecipee where recipeeName='" + name + "'");
                rs = pst.executeQuery();
                int cnt = 2;
                int r = 0;
                int c = 0;
                original_batch = rs.getDouble(42);
                original_batch = RoundTo2Decimals(original_batch);
                String cvt = Double.toString(original_batch);
                TxtBat.setText(cvt);
                String chker = rs.getString(cnt);
                while (!chker.equals("")) {
                    chker = rs.getString(cnt++);
                    Table.setValueAt(chker, r, c++);
                    if (c == 2) {
                        r++;
                        c = 0;
                    }

                }
                row = r;
                while (r < Table.getRowCount()) {
                    Table.setValueAt("", r, c++);
                    if (c == 2) {
                        r++;
                        c = 0;
                    }
                }
            } else {
                int r1 = 0, c1 = 0;
                while (r1 < Table.getRowCount()) {
                    Table.setValueAt("", r1, c1++);
                    if (c1 == 2) {
                        r1++;
                        c1 = 0;
                    }
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(Production1.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
                rs.close();
            } // TODO add your handling code here: // TODO add your handling code here:
            catch (SQLException ex) {
                Logger.getLogger(Production1.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void disableCustom() {
        ComboCustomRecipee.setVisible(false);
        ComboItem.removeAllItems();
        ComboItem.addItem("Select");
        lblCustom.setVisible(false);
        pnlCustom.setVisible(false);
        btnGo.setVisible(false);
        TxtBat.setEditable(false);
        btnQty.doClick();

    }

    public String getRecipeName(String name) {
        String myrecipeName = "";
        try {
            pst = conn.prepareStatement("select recipeeName from Recipe where recipeeName='" + name + "'or recipeeAlias='" + name + "'");
            rs = pst.executeQuery();
            while (rs.next()) {
                myrecipeName = rs.getString("recipeeName");
            }

        } catch (SQLException ex) {
            Logger.getLogger(EditRecipe.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(EditRecipe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myrecipeName;
    }

    public String getRawItemName(String name) {
        String myRawItemName = "";
        try {
            pst = conn.prepareStatement("select itemName from RawMaterials where itemName='" + name + "'or itemAlias='" + name + "'");
            rs = pst.executeQuery();
            while (rs.next()) {
                myRawItemName = rs.getString("itemName");
            }

        } catch (SQLException ex) {
            Logger.getLogger(EditRecipe.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(EditRecipe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myRawItemName;
    }

    public void production_entry() {
        if (!txtDate.getText().equals("")) {
            try {
                String myDate = txtDate.getText();
                double quantity = original_batch;
                double cost = Double.parseDouble(lblTotCost.getText());
                String rname;
                prod_id = pickId();
                if (ComboCustomRecipee.getSelectedIndex() > 0) {
                    rname = ComboCustomRecipee.getSelectedItem().toString();
                } else {
                    rname = getRecipeName(ComboRecipeName.getSelectedItem().toString());
                }
                pst = conn.prepareStatement("insert into Production values(?,?,round(?,2),round(?,2),?)");
                pst.setString(1, myDate);
                pst.setString(2, rname);
                pst.setDouble(3, quantity);
                pst.setDouble(4, cost);
                pst.setInt(5, prod_id);

                pst.addBatch();
                pst.executeBatch();


            } catch (SQLException ex) {
                Logger.getLogger(Production1.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    pst.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Production1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter the date");
        }
    }

    public void Finished_update() {
        String iname = getRecipeName(ComboRecipeName.getSelectedItem().toString());
        double newcost = Double.parseDouble(lblTotCost.getText());
        try {
            pst = conn.prepareStatement("select itemName from FinishedGoods where itemName = '" + iname + "'");
            rs = pst.executeQuery();
            if (rs.next()) {
                pst.close();
                rs.close();
                //updating cost
                pst = conn.prepareStatement("update FinishedGoods set cost = " + newcost + " where itemName ='" + iname + "'");
                pst.executeUpdate();
                pst.close();
                // updating quantity
                pst = conn.prepareStatement("update FinishedGoods set openingStock = openingStock + " + original_batch + " where itemName ='" + iname + "'");
                pst.executeUpdate();
                pst.close();

            } else {
                pst.close();
                rs.close();

                pst = conn.prepareStatement("insert into FinishedGoods values (?,?,?,?,?,?,?)");
                pst.setString(1, iname);
                pst.setDouble(2, newcost);
                pst.setInt(3, 0);
                pst.setDouble(4, original_batch);
                pst.setString(5, null);
                pst.setString(6, null);
                pst.setDouble(7, 0);
                pst.addBatch();
                pst.executeBatch();

                pst.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Production1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Reset() {

        Calendar currentDate = Calendar.getInstance();
        jDateChooser1.setDate(currentDate.getTime());
        txtDate.setText("");
        ComboRecipeName.setSelectedIndex(0);
        lblCustom.setVisible(false);
        ComboCustomRecipee.setVisible(false);
        buttonGroup1.clearSelection();
        lblRMC.setText("");
        lblMc.setText("");
        txtPc.setText("");
        lblTotCost.setText("");
        TxtBat.setText("");
        pnlCustom.setVisible(false);
        // to make table empty
        int rr = 0, cc = 0;
        while (rr < Table.getRowCount()) {
            Table.setValueAt("", rr, cc++);
            if (cc == 2) {
                rr++;
                cc = 0;
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        ComboRecipeName = new javax.swing.JComboBox();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        btn_submit = new javax.swing.JButton();
        ComboCustomRecipee = new javax.swing.JComboBox();
        lblCustom = new javax.swing.JLabel();
        lblPc = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnShowCost = new javax.swing.JButton();
        lblRMC = new javax.swing.JLabel();
        lblMc = new javax.swing.JLabel();
        lblTotCost = new javax.swing.JLabel();
        txtPc = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        txtDate = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        pnlCustom = new javax.swing.JPanel();
        ButtonDelete = new javax.swing.JButton();
        ButtonEdit = new javax.swing.JButton();
        Panel = new javax.swing.JPanel();
        ComboItem = new javax.swing.JComboBox();
        TextRatio = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Btn_AddItem = new javax.swing.JButton();
        btnPercent = new javax.swing.JButton();
        btnQty = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnGo = new javax.swing.JButton();
        TxtBat = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PRODUCTION", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 24), new java.awt.Color(51, 51, 255))); // NOI18N

        jLabel4.setText("<html><h1>Recipe Name");

        ComboRecipeName.setFont(new java.awt.Font("Times New Roman", 2, 18)); // NOI18N
        ComboRecipeName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select" }));
        ComboRecipeName.setToolTipText("Select raw material to be used in Recipe from drop down menu");
        ComboRecipeName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboRecipeNameItemStateChanged(evt);
            }
        });
        ComboRecipeName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboRecipeNameActionPerformed(evt);
            }
        });
        ComboRecipeName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ComboRecipeNameKeyPressed(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("<html><h2>Standard");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        jRadioButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRadioButton1KeyPressed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("<html><h2>customized");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });
        jRadioButton2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRadioButton2KeyPressed(evt);
            }
        });

        btn_submit.setText("<html><h1>SEND TO PRODUCTION");
        btn_submit.setToolTipText("Finalize the recipe");
        btn_submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_submitActionPerformed(evt);
            }
        });
        btn_submit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_submitKeyPressed(evt);
            }
        });

        ComboCustomRecipee.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select" }));
        ComboCustomRecipee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboCustomRecipeeActionPerformed(evt);
            }
        });
        ComboCustomRecipee.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ComboCustomRecipeeKeyPressed(evt);
            }
        });

        lblCustom.setText("<html><h1>customized Recipee");

        lblPc.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Total Cost", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18), java.awt.Color.blue)); // NOI18N

        jLabel2.setText("<html><h2>Raw Material Cost");

        jLabel3.setText("<html><h2>Manufacturing Cost");

        jLabel7.setText("<html><h2>Packaging Cost");

        jLabel8.setText("<html><h2>Total Cost");

        btnShowCost.setText("<html><h2>SHOW COST");
        btnShowCost.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnShowCost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowCostActionPerformed(evt);
            }
        });
        btnShowCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnShowCostKeyPressed(evt);
            }
        });

        lblRMC.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblRMC.setForeground(java.awt.Color.blue);

        lblMc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblMc.setForeground(java.awt.Color.blue);

        lblTotCost.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTotCost.setForeground(java.awt.Color.blue);

        txtPc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPcKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout lblPcLayout = new javax.swing.GroupLayout(lblPc);
        lblPc.setLayout(lblPcLayout);
        lblPcLayout.setHorizontalGroup(
            lblPcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(lblPcLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(lblPcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(lblPcLayout.createSequentialGroup()
                        .addGroup(lblPcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(lblPcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(lblPcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblRMC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                                .addComponent(lblMc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txtPc, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30))
                    .addGroup(lblPcLayout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 151, Short.MAX_VALUE)
                        .addComponent(lblTotCost, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(lblPcLayout.createSequentialGroup()
                .addGap(172, 172, 172)
                .addComponent(btnShowCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        lblPcLayout.setVerticalGroup(
            lblPcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblPcLayout.createSequentialGroup()
                .addGroup(lblPcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(lblPcLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblRMC, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lblPcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lblPcLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lblPcLayout.createSequentialGroup()
                        .addComponent(lblMc, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)))
                .addGroup(lblPcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPc, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(lblPcLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lblPcLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(lblPcLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(lblTotCost, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(btnShowCost, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel9.setText("<html><h1>Date");

        jDateChooser1.setDateFormatString("MM,dd,yyyy");
        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });

        txtDate.setEditable(false);
        txtDate.setText(" ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                                .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(173, 173, 173))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(225, 225, 225)))
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(ComboCustomRecipee, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(ComboRecipeName, 0, 230, Short.MAX_VALUE)
                                                .addComponent(txtDate))))
                                    .addComponent(ComboCustomRecipee, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(lblCustom, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(btn_submit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 28, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblPc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtDate, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ComboRecipeName, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCustom, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboCustomRecipee, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(lblPc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_submit, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Details of Recipe", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 24), new java.awt.Color(0, 0, 255))); // NOI18N

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Item", "Quantity"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table.setToolTipText("select an item and click on edit or delete ");
        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Table);

        ButtonDelete.setText("<html><h2>Delete");
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

        ButtonEdit.setText("<html><h2>Edit");
        ButtonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonEditActionPerformed(evt);
            }
        });
        ButtonEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ButtonEditKeyPressed(evt);
            }
        });

        Panel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add to Recipee", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        ComboItem.setFont(new java.awt.Font("Times New Roman", 2, 18)); // NOI18N
        ComboItem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select" }));
        ComboItem.setToolTipText("Select raw material to be used in Recipe from drop down menu");
        ComboItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboItemActionPerformed(evt);
            }
        });

        TextRatio.setToolTipText("ratio of raw material in Recipe");

        jLabel5.setText("<html><h3>Enter Quantity");

        jLabel6.setText("<html><h3>Select Item");

        Btn_AddItem.setText("<html><h2>Add to Recipe");
        Btn_AddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_AddItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelLayout = new javax.swing.GroupLayout(Panel);
        Panel.setLayout(PanelLayout);
        PanelLayout.setHorizontalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelLayout.createSequentialGroup()
                        .addComponent(ComboItem, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106))
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addComponent(TextRatio, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Btn_AddItem, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(110, 110, 110))
        );
        PanelLayout.setVerticalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLayout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboItem, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(TextRatio, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Btn_AddItem, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlCustomLayout = new javax.swing.GroupLayout(pnlCustom);
        pnlCustom.setLayout(pnlCustomLayout);
        pnlCustomLayout.setHorizontalGroup(
            pnlCustomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCustomLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pnlCustomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCustomLayout.createSequentialGroup()
                        .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(pnlCustomLayout.createSequentialGroup()
                        .addComponent(ButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addComponent(ButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(288, 288, 288))))
        );
        pnlCustomLayout.setVerticalGroup(
            pnlCustomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCustomLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlCustomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnPercent.setText("Show Percentage");
        btnPercent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPercentActionPerformed(evt);
            }
        });

        btnQty.setText("Show Quantity");
        btnQty.setEnabled(false);
        btnQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQtyActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnGo.setText("GO");
        btnGo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoActionPerformed(evt);
            }
        });
        btnGo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnGoFocusLost(evt);
            }
        });
        btnGo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnGoKeyPressed(evt);
            }
        });

        TxtBat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtBatActionPerformed(evt);
            }
        });

        jLabel1.setText("<html><h2>BATCH SIZE");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtBat, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnGo, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(TxtBat)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnGo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlCustom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnQty, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(btnPercent))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQty, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPercent, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(pnlCustom, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jButton3.setText("Clear DB table");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setText("<html><h1>Delete Record");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setText("<html><h3>Reconciliation Report");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jButton4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton4KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                            .addComponent(jButton4))
                        .addGap(184, 184, 184)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(394, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void clear_all() {
        // TextRecipeName.setEnabled(true);
        TextRatio.setEnabled(true);
        //TextRecipeName.setText("");
        TextRatio.setText("");
        ComboItem.setSelectedIndex(0);
    }

    double calcCost(String arr1[], int rows) {
        String cost[][] = new String[rows][2];
        int m, n;
        String val = null;
        rows = rows * 2;
        for (m = 0, n = 0; m < rows; m = m + 2) {
            try {
                String itemName = getRawItemName(arr1[m]);
                if (!itemName.equals("")) { //item is a raw material
                    //System.out.println("raw");
                    pst = conn.prepareStatement("select max(transactionId) as 'transactionId' from transactionsIN where item='" + itemName + "'");
                    rs = pst.executeQuery();

                    if (rs.getObject(1) != null) {
                        String itemId = rs.getString("transactionId");
                        pst.close();
                        rs.close();
                        pst = conn.prepareStatement("select price as 'abc' from transactionsIN where transactionId='" + itemId + "'");
                        rs = pst.executeQuery();
                        val = rs.getString("abc");
                    } else {
                        pst.close();
                        rs.close();
                        pst = conn.prepareStatement("select cost as 'abc' from RawMaterials where itemName='" + itemName + "'");
                        rs = pst.executeQuery();
                        val = rs.getString("abc");
                    }
                } else {       // if item is a recipe
                    String fg[] = new String[40];
                    String rcolumn = "";
                    double f_batch = 0, fval = 0;
                    int cnum = 2, fi = 0;
                    itemName = getRecipeName(arr1[m]);
                    pst = conn.prepareStatement("select * from Recipe where recipeeName='" + itemName + "'");
                    rs = pst.executeQuery();
                    rcolumn = rs.getString(cnum);
                    while (!(rcolumn.equals(""))) {
                        fg[fi] = rcolumn;
                        cnum++;
                        fi++;
                        rcolumn = rs.getString(cnum);
                    }
                    f_batch = rs.getDouble(42);
                    rs.close();
                    pst.close();
                    double fcost = calcCost(fg, fi / 2);
                    fval = fcost / f_batch;
                    val = Double.toString(fval);

                }

                if (val != null) {
                    cost[n][0] = val;
                }
                // System.out.println(cost[n][0]);
                cost[n][1] = arr1[m + 1];


                n++;

            } catch (SQLException ex) {
                Logger.getLogger(EditRecipe.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    pst.close();
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EditRecipe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        double totalCost = 0;
        for (int z = 0; z < n; z++) {
            totalCost = totalCost + (Double.parseDouble(cost[z][0]) * Double.parseDouble(cost[z][1]));

        }

        return totalCost;
    }

    public void costing() {
        String arr1[] = new String[40];
        int inc = 0;
        int rows = 0, cols = 0;
        if (Validation.ratioChk(txtPc.getText())) { // Validation check for packaging cost
            while (!(Table.getValueAt(rows, cols) == null) && !(Table.getValueAt(rows, cols).equals(""))) {
                arr1[inc++] = Table.getValueAt(rows, cols++).toString();
                if (cols == 2) {
                    rows++;
                    cols = 0;

                }
            }
            double totalCost = calcCost(arr1, rows);
            totalCost = RoundTo2Decimals(totalCost);
            String rmc = Double.toString(totalCost);
            lblRMC.setText(rmc);
            double mc = totalCost * 0.15;
            mc = RoundTo2Decimals(mc);
            lblMc.setText(Double.toString(mc));
            double pc = Double.parseDouble(txtPc.getText());
            double finalCost = ((totalCost + mc) / original_batch) + pc;
            finalCost = RoundTo2Decimals(finalCost);
            lblTotCost.setText(Double.toString(finalCost));
        } else {
            while (!(Table.getValueAt(rows, cols) == null) && !(Table.getValueAt(rows, cols).equals(""))) {
                arr1[inc++] = Table.getValueAt(rows, cols++).toString();
                if (cols == 2) {
                    rows++;
                    cols = 0;

                }
            }
            double totalCost = calcCost(arr1, rows);
            totalCost = RoundTo2Decimals(totalCost);
            String rmc = Double.toString(totalCost);
            lblRMC.setText(rmc);
            double mc = totalCost * 0.15;
            mc = RoundTo2Decimals(mc);
            lblMc.setText(Double.toString(mc));
            double pc = Double.parseDouble("5");
            double finalCost = ((totalCost + mc) / original_batch) + pc;
            finalCost = RoundTo2Decimals(finalCost);
            lblTotCost.setText(Double.toString(finalCost));
        }
    }

    public String todayTime() {
//   SimpleDateFormat formatter1 =  new SimpleDateFormat("dd-MM-yyyy");
//   String dateNow1 = formatter1.format(DateChooser1.getCalendar().getTime());
//   TextDate.setText(dateNow1);   
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        String dateNow = formatter.format(currentDate.getTime());

        return dateNow;


    }

    private void btn_submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_submitActionPerformed
        btnQty.doClick();
        if (ComboRecipeName.getSelectedIndex() > 0) {
            if (!txtDate.getText().equals("")) {
                boolean outOfStock = check_stock();
                boolean isItemMissing = checkItemMissing(ComboRecipeName.getSelectedItem().toString());
                if(isItemMissing){
                    JOptionPane.showMessageDialog(null, "This recipe seems inconsistent. Kindly check the reconciliation report");
                }
                //JOptionPane.showMessageDialog(null, isItemMissing);
                    if (outOfStock == false && isItemMissing==false) {
                    costing();
                    //System.out.println(outOfStock);
                    RawMaterial_update();
                    production_entry();
                    Finished_update();
                    if (button.equals("2")) {
                        custom_table();
                    }
                    String useDate = txtDate.getText();
                    JOptionPane.showMessageDialog(null, "Production Successful");
                    Reset();
                    txtDate.setText(useDate);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Enter Date");
            }
    }//GEN-LAST:event_btn_submitActionPerformed
   
          else
            JOptionPane.showMessageDialog(null, "Select recipe from the list");
    }

    public boolean checkItemMissing(String recipeName) {

        boolean isItemMissing = false;

//        JOptionPane.showMessageDialog(null, recipeName);
        PreparedStatement pstCurrentBlock = null;
        ResultSet rsCurrentBlock = null;
        String subItemName = "";
        Boolean inRaw = true;
        Boolean inRecipe = true;
        Boolean inFG = true;

        Connection conn = CreateConnection.connectDB();
        // JOptionPane.showMessageDialog(null, "in1");
        try {
            // JOptionPane.showMessageDialog(null, "in2");
            inFG = checkInFG(recipeName);
            if (!inFG) {
                // JOptionPane.showMessageDialog(null, "in2.1");
                isItemMissing = true;
            } else {
                // JOptionPane.showMessageDialog(null, "in3");
                pstCurrentBlock = conn.prepareStatement("select * from Recipe where recipeeName = '" + recipeName + "'");
                rsCurrentBlock = pstCurrentBlock.executeQuery();
                // JOptionPane.showMessageDialog(null, "in");
                int i = 2;
                while (i < 40) {
                    //JOptionPane.showMessageDialog(null, i);
                    subItemName = rsCurrentBlock.getString(i);
                    if (!subItemName.equals("")) {
                        inRecipe = checkInRecipe(subItemName);

                        if (inRecipe) {
                            // to do
                            //return true or false, if true = item is missing, false = item is not missing

                            isItemMissing = checkItemMissing(subItemName);
                            // JOptionPane.showMessageDialog(null , subItemName + isItemMissing);
                        }

                        if (!inRecipe) {
                            inRaw = checkInRaw(subItemName);
                            if (!inRaw) {
                                isItemMissing = true;
                            }
                        }
                    }
                    i = i + 2;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                if(pstCurrentBlock!=null){
                    pstCurrentBlock.close();
                }
                if(rsCurrentBlock!=null){
                    rsCurrentBlock.close();
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(Recipe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return isItemMissing;

    }

    public static boolean checkInRaw(String itemName) {
        Connection conn = CreateConnection.connectDB();
        PreparedStatement pstRAW = null;
        ResultSet rsRAW = null;
        boolean inRAW = true;
        try {

            pstRAW = conn.prepareStatement("select * from RawMaterials where itemName='" + itemName + "'");
            rsRAW = pstRAW.executeQuery();
            if (rsRAW.next()) {
            } else {
                inRAW = false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                pstRAW.close();
                rsRAW.close();
            } catch (SQLException ex) {
                Logger.getLogger(Recipe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return inRAW;
    }

    public static boolean checkInFG(String itemName) {
        Connection conn = CreateConnection.connectDB();
        PreparedStatement pstFG = null;
        ResultSet rsFG = null;
        Boolean inFg = true;
        try {

            pstFG = conn.prepareStatement("select * from FinishedGoods where itemName='" + itemName + "'");
            rsFG = pstFG.executeQuery();
            if (rsFG.next()) {
            } else {
                inFg = false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                pstFG.close();
                rsFG.close();
            } catch (SQLException ex) {
                Logger.getLogger(Recipe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return inFg;
    }

    public static boolean checkInRecipe(String itemName) {
        Connection conn = CreateConnection.connectDB();
        PreparedStatement pstRecipe = null;
        ResultSet rsRecipe = null;
        boolean inRecipe = true;
        try {

            pstRecipe = conn.prepareStatement("select * from Recipe where recipeeName='" + itemName + "'");
            rsRecipe = pstRecipe.executeQuery();
            if (rsRecipe.next()) {
            } else {
                inRecipe = false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                pstRecipe.close();
                rsRecipe.close();
            } catch (SQLException ex) {
                Logger.getLogger(Recipe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return inRecipe;
    }

    public int pickId() {
        int id = 0;
        try {
            pst = conn.prepareStatement("select max(prod_id) from Production");
            rs = pst.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1) + 1;
            }
            rs.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Production1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public void RawMaterial_update() {
        int rowc = 0, colc = 0;
        double quant = 0;
        String itm;
        while (!(Table.getValueAt(rowc, colc) == null) && !(Table.getValueAt(rowc, colc).equals(""))) {
            itm = Table.getValueAt(rowc, colc++).toString();


            quant = Double.parseDouble(Table.getValueAt(rowc++, colc).toString());
            colc = 0;
            try {
                String sql = "update RawMaterials set openingStock = openingStock -" + quant + " where itemName = '" + itm + "'";
                Statement st = conn.createStatement();
                int chk_update = st.executeUpdate(sql);
                st.close();
                if (chk_update == 0) {
                    String sql1 = "update FinishedGoods set openingStock = openingStock -" + quant + " where itemName = '" + itm + "'";
                    Statement st1 = conn.createStatement();
                    st1.executeUpdate(sql1);
                    st1.close();
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                try {
                } catch (Exception e) {
                }
            }
        }


    }

    public boolean check_stock() {
        int rowc = 0, colc = 0;
        double quant = 0;
        String itm;
        while (!(Table.getValueAt(rowc, colc) == null) && !(Table.getValueAt(rowc, colc).equals(""))) {
            itm = Table.getValueAt(rowc, colc++).toString();


            quant = Double.parseDouble(Table.getValueAt(rowc++, colc).toString());
            colc = 0;
            try {
                String sql = "select openingStock from RawMaterials where itemName = '" + itm + "'";
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    double remainingStock = Double.parseDouble(rs.getString("openingStock")) - quant;
                    rs.close();
                    pst.close();
                    if (remainingStock < 0) {
                        JOptionPane.showMessageDialog(null, itm + "out of stock");

                        return true;
                    }
                } else {
                    rs.close();
                    pst.close();
                    sql = "select openingStock from FinishedGoods where itemName = '" + itm + "'";
                    pst = conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    double remainingStock = Double.parseDouble(rs.getString("openingStock")) - quant;
                    rs.close();
                    pst.close();
                    if (remainingStock < 0) {
                        JOptionPane.showMessageDialog(null, itm + "out of stock");
                        return true;
                    }
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                try {
                } catch (Exception e) {
                }
            }

        }
        return false;

    }

    public void custom_table() {
        String date = txtDate.getText();
        String time = todayTime();
        String rn = getRecipeName(ComboRecipeName.getSelectedItem().toString());
        String RecipeName = rn.concat("@");
        RecipeName = RecipeName.concat(date);
        RecipeName = RecipeName.concat("/");
        RecipeName = RecipeName.concat(time);
        if (RecipeName.equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter recipe name");
        } else {
            try {


                // Total Quantity Calculation
                double tot_qty = 0;
                int rowst = 0, colst = 1;
                while (!(Table.getValueAt(rowst, colst) == null) && !(Table.getValueAt(rowst, colst).equals(""))) {
                    double qty = Double.parseDouble(Table.getValueAt(rowst++, colst).toString());
                    tot_qty = tot_qty + qty;
                }

                String arr[] = new String[40];
                int inc = 0;
                int rows = 0, cols = 0;
                while (!(Table.getValueAt(rows, cols) == null) && !(Table.getValueAt(rows, cols).equals(""))) {

                    //if (cols == 0)
                    arr[inc++] = Table.getValueAt(rows, cols++).toString();
                    /*
                     *
                     *
                     * else if (cols == 1) // Converting into percentage { int r
                     * = Integer.parseInt(Table.getValueAt(rows,
                     * cols++).toString()); float per = (float)r/tot_qty * 100 ;
                     * arr[inc++] = Float.toString(per); }
                     */
                    if (cols == 2) {
                        rows++;
                        cols = 0;
                    }
                }
                while (inc < 40) {
                    arr[inc++] = "";
                }

                String sql = "insert into CustomRecipee values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);

                pst.setString(1, RecipeName);
                int i = 0;
                while (i < 40) {
                    pst.setString(i + 2, arr[i]);
                    //System.out.println(arr[i] + "   ");
                    i++;
                }
                pst.setDouble(42, tot_qty);
                pst.setInt(43, prod_id);
                pst.addBatch();
                pst.executeBatch();
                // JOptionPane.showMessageDialog(null, "Recipe1 added");
                //clear_all();

            } catch (SQLException ex) {
                Logger.getLogger(Recipe.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    pst.close();
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Recipe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        //for(int i=0;i<=row;i++){
        //((DefaultTableModel) Table.getModel()).removeRow(0);
    }

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
    }//GEN-LAST:event_TableMouseClicked

    private void refreshTable() {
        int temprow1 = row;
        for (int i = 0; i < temprow1; i++) {
            ((DefaultTableModel) Table.getModel()).removeRow(0);
            Object[] abc = null;
            ((DefaultTableModel) Table.getModel()).addRow(abc);
        }
    }

    private void ComboRecipeNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboRecipeNameActionPerformed

        if (ComboRecipeName.getSelectedIndex() > 0) {

            button = "3";

            ComboCustomRecipee.removeAllItems();
            ComboCustomRecipee.addItem("Select");
            lblRMC.setText("");
            lblMc.setText("");
            txtPc.setText("");
            lblTotCost.setText("");
            jRadioButton1.doClick();
        } else {
            refreshTable();      // if user select "select" item in between the process
        }

    }//GEN-LAST:event_ComboRecipeNameActionPerformed

    private void ComboRecipeNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboRecipeNameItemStateChanged
        ComboItem.setSelectedIndex(0);
        TextRatio.setText("");


    }//GEN-LAST:event_ComboRecipeNameItemStateChanged

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        if (ComboRecipeName.getSelectedIndex() > 0) {
            if (button.equals("2")) {
            } else {
                try {
                    TxtBat.setEditable(true);
                    button = "2";
                    String name = getRecipeName(ComboRecipeName.getSelectedItem().toString());
                    filler();
                    filler_recipe(name);
                    if (!name.equals("Select")) {
                        pnlCustom.setVisible(true);
                        btnGo.setVisible(true);
                        pst = conn.prepareStatement("Select * from Recipe where recipeeName='" + name + "'");
                        rs = pst.executeQuery();
                        int cnt = 2;
                        int r = 0;
                        int c = 0;
                        original_batch = rs.getDouble(42);
                        original_batch = RoundTo2Decimals(original_batch);
                       // JOptionPane.showMessageDialog(null, original_batch);
                        String cvt = Double.toString(original_batch);
                       // JOptionPane.showMessageDialog(null, cvt);
                        TxtBat.setText(cvt);
                        String chker = rs.getString(cnt);
                        while (!(chker == null) && !chker.equals("")) {
                            chker = rs.getString(cnt++);
                            Table.setValueAt(chker, r, c++);
                            if (c == 2) {
                                r++;
                                c = 0;
                            }

                        }
                        row = r;
                        while (r < Table.getRowCount()) {
                            Table.setValueAt("", r, c++);
                            if (c == 2) {
                                r++;
                                c = 0;
                            }
                        }
                    } else {
                        int r1 = 0, c1 = 0;
                        while (r1 < Table.getRowCount()) {
                            Table.setValueAt("", r1, c1++);
                            if (c1 == 2) {
                                r1++;
                                c1 = 0;
                            }
                        }

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Production1.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        pst.close();
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(Production1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                setv(true);

                // adding item to customRecipee combo box

                try {
                    String sub0 = ComboRecipeName.getSelectedItem().toString();
                    String sub = getRecipeName(sub0);
                    pst = conn.prepareStatement("Select * from CustomRecipee where recipeeName LIKE '" + sub + "%'");
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        ComboCustomRecipee.setVisible(true);
                        lblCustom.setVisible(true);
                        ComboCustomRecipee.addItem(rs.getString("recipeeName"));


                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Production1.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        rs.close();
                        pst.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(Production1.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }


            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a Recipe");
        }

    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        if (ComboRecipeName.getSelectedIndex() > 0) {
            if (button.equals("1")) {
            } else {
                disableCustom();

                button = "1";
                try {
                    String name = getRecipeName(ComboRecipeName.getSelectedItem().toString());
                    if (!name.equals("Select")) {
                        pst = conn.prepareStatement("Select * from Recipe where recipeeName='" + name + "'");
                        rs = pst.executeQuery();
                        int cnt = 2;
                        int r = 0;
                        int c = 0;
                        original_batch = rs.getDouble(42);
                        original_batch = RoundTo2Decimals(original_batch);
                        String cvt = Double.toString(original_batch);
                        TxtBat.setText(cvt);
                        String chker = rs.getString(cnt);
                        while (!(chker == null) && !chker.equals("")) {
                            chker = rs.getString(cnt++);
                            Table.setValueAt(chker, r, c++);

                            if (c == 2) {
                                r++;
                                c = 0;
                            }

                        }
                        row = r;
                        while (r < Table.getRowCount()) {
                            Table.setValueAt("", r, c++);
                            if (c == 2) {
                                r++;
                                c = 0;
                            }
                        }
                    } else {
                        int r1 = 0, c1 = 0;
                        while (r1 < Table.getRowCount()) {
                            Table.setValueAt("", r1, c1++);
                            if (c1 == 2) {
                                r1++;
                                c1 = 0;
                            }
                        }

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Production1.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        pst.close();
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(Production1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                setv(false);
            }
    }//GEN-LAST:event_jRadioButton1ActionPerformed
       else
            JOptionPane.showMessageDialog(null, "Select the Recipe");
    }

    private void Btn_AddItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_AddItemActionPerformed
        btnQty.doClick();
        int count = 0;
        boolean chk = true;
        String str[] = new String[2];
        str[0] = getRawItemName(ComboItem.getSelectedItem().toString());
        if (str[0].equals("")) {
            str[0] = getRecipeName(ComboItem.getSelectedItem().toString());
        }
        str[1] = TextRatio.getText();
        for (int i = 0; i < 1; i++) {
            if (ComboRecipeName.getSelectedItem().toString().equals("Select")) {
                JOptionPane.showMessageDialog(null, "No recipe has been selected");
                chk = false;
                break;
            }
            while (count < row) {
                if (str[0].equals(Table.getValueAt(count++, 0))) {
                    JOptionPane.showMessageDialog(null, "This item is already added");
                    chk = false;
                    break;
                }
            }
            if (ComboItem.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Select an item");
                chk = false;
                break;
            }
            if (ComboItem.getSelectedIndex() != 0) {
                if (TextRatio.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Enter Quantity for the item");
                    chk = false;
                    break;
                } else if (!Validation.ratioChk(TextRatio.getText())) {
                    JOptionPane.showMessageDialog(null, "Quantity is not valid");
                    chk = false;
                }
            }
            int a = 0;
            while (!(Table.getValueAt(a, 0) == null) && !(Table.getValueAt(a, 0).equals(""))) {
                a++;
            }


            row = a;
            col = 0;
            if (chk) {
                Table.setValueAt(str[0], row, col);
                Double ratioDouble = Double.parseDouble(str[1]);
                ratioDouble = Production1.RoundTo2Decimals(ratioDouble);
                Table.setValueAt(ratioDouble.toString(), row, col + 1);
                row++;
                original_batch = original_batch + Double.parseDouble(str[1]);
                original_batch = RoundTo2Decimals(original_batch);
                String cvt = Double.toString(original_batch);
                TxtBat.setText(cvt);
            }


        }
    }//GEN-LAST:event_Btn_AddItemActionPerformed

    private void ButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDeleteActionPerformed
        btnQty.doClick();
        if (Table.getSelectedRow() == -1 || Table.getSelectedRow() >= row) {
            JOptionPane.showMessageDialog(null, "Select an item to delete");
        } else {
            int r = Table.getSelectedRow();
            double val = Double.parseDouble(Table.getValueAt(r, 1).toString());
            original_batch = original_batch - val;
            original_batch = RoundTo2Decimals(original_batch);
            String cvt = Double.toString(original_batch);
            TxtBat.setText(cvt);
            ((DefaultTableModel) Table.getModel()).removeRow(Table.getSelectedRow());
            Object[] abc = null;
            ((DefaultTableModel) Table.getModel()).addRow(abc);
            row = row - 1;
        }
        //  Panel.setVisible(false);
    }//GEN-LAST:event_ButtonDeleteActionPerformed

    private void ButtonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditActionPerformed
        btnQty.doClick();
        if (Table.getSelectedRow() == -1 || Table.getSelectedRow() >= row) {
            JOptionPane.showMessageDialog(null, "Select an item to edit");
        } else {
            row_num = Table.getSelectedRow();
            String str = Table.getValueAt(row_num, 0).toString();
            String rat = JOptionPane.showInputDialog("Enter Quantity for " + str);
            if (rat == null) {
                JOptionPane.showMessageDialog(null, "No value entered");
            } else if (rat.equals("")) {
                JOptionPane.showMessageDialog(null, "No value entered");
            } else if (!Validation.ratioChk(rat)) {
                JOptionPane.showMessageDialog(null, "Quantity is not valid");

            } else {
                double temp = Double.parseDouble(Table.getValueAt(row_num, 1).toString());
                Double rat1 = Production1.RoundTo2Decimals(Double.parseDouble(rat));
                Table.setValueAt(rat1, row_num, 1);
                original_batch = original_batch + Double.parseDouble(rat) - temp;
                original_batch = RoundTo2Decimals(original_batch);
                String cvt = Double.toString(original_batch);
                TxtBat.setText(cvt);
                //Panel.setVisible(false);
            }
            //Label.setText("<html><h2>Enter ratio for " + str);
            //Panel.setVisible(true);
        }
    }//GEN-LAST:event_ButtonEditActionPerformed

    private void TxtBatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtBatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtBatActionPerformed

    private void btnGoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoActionPerformed
        try {
            btnQty.doClick();
            String s = TxtBat.getText();
            double new_batch = Double.parseDouble(s);
            new_batch = Production1.RoundTo2Decimals(new_batch);
            Double new_batch1 = Production1.RoundTo2Decimals(new_batch);
            TxtBat.setText(new_batch1.toString());
            if (!s.equals("") && Validation.ratioChk(s) && new_batch != 0) {

                int r = 0;
                double change = 0;
                double q = 0;
                //int ob = 0;



                while (!(Table.getValueAt(r, 1).equals("")) && !(Table.getValueAt(r, 1) == null)) {
                    q = Double.parseDouble(Table.getValueAt(r, 1).toString());
                    try {
                        change = q / original_batch * new_batch;
                        change = RoundTo2Decimals(change);
                        // ob = ob+change;
                    } catch (ArithmeticException e) {
                        System.out.println("Batch size zero");
                    }

                    Table.setValueAt(change, r, 1);
                    r++;
                }
                original_batch = new_batch;
                original_batch = RoundTo2Decimals(original_batch);
                //original_batch = ob;
                //TxtBat.setText(Integer.toString(ob));
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Value");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid Value");
        }

    }//GEN-LAST:event_btnGoActionPerformed

    private void ComboCustomRecipeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboCustomRecipeeActionPerformed
        if (ComboCustomRecipee.getSelectedIndex() > 0) {

            fillByCustom();
            btnQty.doClick();


        }
    }//GEN-LAST:event_ComboCustomRecipeeActionPerformed

    private void btnShowCostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowCostActionPerformed
        if (ComboRecipeName.getSelectedIndex() > 0) {
            btnQty.doClick();

            costing();
        } else {
            JOptionPane.showMessageDialog(null, "Select one Recipe");
        }
    }//GEN-LAST:event_btnShowCostActionPerformed

    private void ComboItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboItemActionPerformed

    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar obj = jDateChooser1.getCalendar();
        String dateNow = "";
        if (obj != null) {
            dateNow = formatter1.format(obj.getTime());
        }
        //System.out.println(dateNow1);
        txtDate.setText(dateNow);
    }//GEN-LAST:event_jDateChooser1PropertyChange

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

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
        }

        if (a == 27) {
            jButton2.doClick();
        } else if (a == 113) {
            btn_submit.doClick();
        }
    }//GEN-LAST:event_jButton2KeyPressed

    private void btnShowCostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnShowCostKeyPressed
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
            btn_submit.doClick();
        }
    }//GEN-LAST:event_btnShowCostKeyPressed

    private void btn_submitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_submitKeyPressed
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
            // jButton3.doClick();
        }//esc key       // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_submitKeyPressed

    private void btnGoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGoKeyPressed
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
            jButton3.doClick();
        }//esc key       // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGoKeyPressed

    private void ButtonEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ButtonEditKeyPressed
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
            jButton3.doClick();
        }//esc key       // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonEditKeyPressed

    private void ButtonDeleteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ButtonDeleteKeyPressed
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
            jButton3.doClick();
        }//esc key       // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonDeleteKeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        CreateConnection.DeleteAll("CustomRecipee", conn);
        CreateConnection.DeleteAll("Production", conn);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnPercentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPercentActionPerformed
        int rows = 0;
        double conversion;

        String percentage;
        while (!(Table.getValueAt(rows, 1) == null) && !(Table.getValueAt(rows, 1).equals(""))) {
            quantity[rows] = Table.getValueAt(rows, 1).toString();
            conversion = (Double.parseDouble(quantity[rows]) / original_batch) * 100;
            conversion = RoundTo2Decimals(conversion);
            percentage = Double.toString(conversion);
            Table.setValueAt(percentage, rows, 1);
            rows++;

        }
        btnPercent.setEnabled(false);
        btnQty.setEnabled(true);

    }//GEN-LAST:event_btnPercentActionPerformed

    private void btnQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQtyActionPerformed
        int rows = 0;

        while (!(Table.getValueAt(rows, 1) == null) && !(Table.getValueAt(rows, 1).equals(""))) {
            Table.setValueAt(quantity[rows], rows, 1);
            rows++;

        }
        btnPercent.setEnabled(true);
        btnQty.setEnabled(false);
    }//GEN-LAST:event_btnQtyActionPerformed
    public static double RoundTo2Decimals(double Rval) {
        int Rpl = 2;
        double p = (double)Math.pow(10,Rpl);
        Rval = Rval * p;
        double tmp = Math.round(Rval);
        return (double)tmp/p;
    }
    private double RoundTo2Decimals1(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
    }

    private void btnGoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnGoFocusLost
    }//GEN-LAST:event_btnGoFocusLost

    private void ComboRecipeNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ComboRecipeNameKeyPressed
        int a = evt.getKeyCode();
        if (a == 27) {
            jButton2.doClick();
        } else if (a == 113) {
            btn_submit.doClick();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_ComboRecipeNameKeyPressed

    private void ComboCustomRecipeeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ComboCustomRecipeeKeyPressed
        int a = evt.getKeyCode();
        if (a == 27) {
            jButton2.doClick();
        } else if (a == 113) {
            btn_submit.doClick();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_ComboCustomRecipeeKeyPressed

    private void jRadioButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRadioButton1KeyPressed
        int a = evt.getKeyCode();
        if (a == 27) {
            jButton2.doClick();
        } else if (a == 113) {
            btn_submit.doClick();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1KeyPressed

    private void jRadioButton2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRadioButton2KeyPressed
        int a = evt.getKeyCode();
        if (a == 27) {
            jButton2.doClick();
        } else if (a == 113) {
            btn_submit.doClick();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton2KeyPressed

    private void txtPcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPcKeyPressed
        int a = evt.getKeyCode();
        if (a == 27) {
            jButton2.doClick();
        } else if (a == 113) {
            btn_submit.doClick();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtPcKeyPressed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        int a = evt.getKeyCode();
        if (a == 27) {
            jButton2.doClick();
        } else if (a == 113) {
            btn_submit.doClick();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyPressed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.setVisible(false);
        RecipeReconciliation er = new RecipeReconciliation();
        er.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4KeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        prodDelete er = new prodDelete(null, true);
        er.setVisible(true);       // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed
    /*
     * private String get_id() { String id = ""; Statement stm = null; try { stm
     * = conn.createStatement(); rs = stm.executeQuery("select max(recipeeId)
     * from CustomRecipee"); if(rs.getObject(1)!= null){ id =
     * rs.getObject(1).toString(); Integer temp = Integer.parseInt(id); temp =
     * temp + 1; id = temp.toString(); }else{ id="1";
     *
     * }
     *
     * } catch (SQLException ex) {
     * Logger.getLogger(Recipe1.class.getName()).log(Level.SEVERE, null, ex); }
     * finally { try { stm.close(); rs.close(); } catch (SQLException ex) {
     * Logger.getLogger(Recipe1.class.getName()).log(Level.SEVERE, null, ex); }
     * } return id; }
     */

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
            java.util.logging.Logger.getLogger(Production1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Production1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Production1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Production1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                final Production1 dialog = new Production1(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        try {
                            Connection connection = dialog.getConnection();
                            if (connection != null) {
                                connection.close();
                            }
                        } catch (SQLException sqle) {
                            Logger.getLogger(Production1.class.getName()).log(Level.SEVERE, null, sqle);
                        }
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_AddItem;
    private javax.swing.JButton ButtonDelete;
    private javax.swing.JButton ButtonEdit;
    private javax.swing.JComboBox ComboCustomRecipee;
    private javax.swing.JComboBox ComboItem;
    private javax.swing.JComboBox ComboRecipeName;
    private javax.swing.JPanel Panel;
    private javax.swing.JTable Table;
    private javax.swing.JTextField TextRatio;
    private javax.swing.JTextField TxtBat;
    private javax.swing.JButton btnGo;
    private javax.swing.JButton btnPercent;
    private javax.swing.JButton btnQty;
    private javax.swing.JButton btnShowCost;
    private javax.swing.JButton btn_submit;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblCustom;
    private javax.swing.JLabel lblMc;
    private javax.swing.JPanel lblPc;
    private javax.swing.JLabel lblRMC;
    private javax.swing.JLabel lblTotCost;
    private javax.swing.JPanel pnlCustom;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtPc;
    // End of variables declaration//GEN-END:variables
}
