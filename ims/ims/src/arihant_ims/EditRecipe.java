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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

public class EditRecipe extends javax.swing.JDialog {

    static int donow = 0;
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    int row = 0, col = 0;
    int row_temp = 0;
    int row_num = 0;
    String RecipeAlias = "";
    double cost = 0;
    double original_batch = 0;
    String quantity[] = new String[20];
    String myRecipe = "";

    /**
     * Creates new form recipe
     */
    public EditRecipe(java.awt.Frame parent, boolean modal) {

        super(parent, modal);
        conn = CreateConnection.connectDB();
        initComponents();
        Dimension scrn = getToolkit().getScreenSize();
        this.setBounds(0, 0, scrn.width, scrn.height);
        Table.setRowHeight(25);
        Panel.setVisible(false);

        // filler();
        filler2();
    }

    private void filler2() {
        try {

            pst = conn.prepareStatement("select * from Recipe order by recipeeName");
            rs = pst.executeQuery();
            while (rs.next()) {
                ComboRecipeName.addItem(rs.getString("recipeeName"));
                if (rs.getString("recipeeAlias") != null && !rs.getString("recipeeAlias").equals("")) {
                    ComboRecipeName.addItem(rs.getString("recipeeAlias"));
                }
            }
        } catch (Exception e) {
            Logger.getLogger(EditRecipe.class.getName()).log(Level.SEVERE, null, e);
            //JOptionPane.showMessageDialog(null, "i am "+e);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(EditRecipe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getItemName(String name) {
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

    private void filler(String recip) {
        try {
            ComboItem.removeAllItems();
            ComboItem.addItem("Select");
            pst = conn.prepareStatement("select * from RawMaterials order by itemName");
            rs = pst.executeQuery();
            while (rs.next()) {
                ComboItem.addItem(rs.getString("itemName"));
                if (!rs.getString("itemAlias").equals("")) {
                    // ComboItem.addItem(rs.getString("itemAlias"));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(Recipe.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        filler_recipe(recip);

        /*
         * try { // ComboItem.removeAllItems(); pst =
         * conn.prepareStatement("select * from FinishedGoods order by
         * itemName"); rs = pst.executeQuery(); while (rs.next()) {
         * if(!rs.getString("itemAlias").equals("") &&
         * !rs.getString("itemName").equals("")){
         * if(!rs.getString("itemName").equals(recip)){
         *
         * ComboItem.addItem(rs.getString("itemName"));
         * ComboItem.addItem(rs.getString("itemAlias")); } } } } catch
         * (Exception e) { JOptionPane.showMessageDialog(null, e); } finally {
         * try { pst.close(); rs.close(); } catch (SQLException ex) {
         * Logger.getLogger(Recipe1.class.getName()).log(Level.SEVERE, null,
         * ex); } }
         *
         */



    }

    public void filler_recipe(String rname) {
        try {
            PreparedStatement pst11 = conn.prepareStatement("select recipeeName , recipeeAlias from Recipe where recipeeName <> '" + rname + "' order by recipeeName" );
            ResultSet rs11 = pst11.executeQuery();
            while (rs11.next()) {
                // System.out.println(rs11.getString("recipeeName"));
                if (check_recursiveRecipe(rname, rs11.getString("recipeeName")) == false) {
                    // System.out.println("in if");
                    ComboItem.addItem(rs11.getString("recipeeName"));
                    if (rs11.getString("recipeeAlias") != null && !rs11.getString("recipeeAlias").equals("")) {
                        //ComboItem.addItem(rs11.getString("recipeeAlias"));
                    }
                    // System.out.println("end if");
                }
            }
            rs11.close();
            pst11.close();
        } catch (SQLException ex) {
            Logger.getLogger(Production.class.getName()).log(Level.SEVERE, null, ex);
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
                    //  System.out.println(childItem);
                    if (getRecipeName(childItem).equals("")) // it is a raw material
                    {
                        //   System.out.println("rawmaterial");
                        i = i + 2;
                        childItem = rs1.getString(i);
                    } else { // it is a recipe
                        if (childItem.equals(parentRecipe)) {
                            //  System.out.println("true");
                            rs1.close();
                            pst1.close();
                            return true;
                        } else {          // going down to next level check
                            // System.out.println("again recipe");
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
            Logger.getLogger(Production.class.getName()).log(Level.SEVERE, null, ex);
        }
        // System.out.println("ret 2");
        return false;

    }

    private double RoundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        ComboItem = new javax.swing.JComboBox();
        TextRatio = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        ComboRecipeName = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        Panel = new javax.swing.JPanel();
        Label = new javax.swing.JLabel();
        Text = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtBatch = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnQty = new javax.swing.JButton();
        btnPercent = new javax.swing.JButton();
        btnShowCost = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        ButtonEdit = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select a Recipe", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 24), new java.awt.Color(51, 51, 255))); // NOI18N
        jPanel3.setEnabled(false);

        jLabel4.setText("<html><h1>Recipe Name");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add Items to the Recipe", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 24), new java.awt.Color(0, 0, 255))); // NOI18N

        ComboItem.setFont(new java.awt.Font("Times New Roman", 2, 18)); // NOI18N
        ComboItem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select" }));
        ComboItem.setToolTipText("Select raw material to be used in Recipe from drop down menu");
        ComboItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboItemActionPerformed(evt);
            }
        });
        ComboItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ComboItemKeyTyped(evt);
            }
        });

        TextRatio.setToolTipText("ratio of raw material in Recipe");
        TextRatio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextRatioKeyPressed(evt);
            }
        });

        jLabel5.setText("<html><h3>Enter Quantity");

        jLabel6.setText("<html><h3>Select Item");

        jButton4.setText("<html><h2>Add to Recipe");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jButton4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jButton4KeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton4KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboItem, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextRatio))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(ComboItem)
                        .addGap(3, 3, 3)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TextRatio, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(142, Short.MAX_VALUE))
        );

        jButton5.setText("<html><h2>Submit Recipe");
        jButton5.setToolTipText("Finalize the recipe");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jButton5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton5KeyPressed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93)
                        .addComponent(ComboRecipeName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboRecipeName, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105))
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
            boolean[] canEdit = new boolean [] {
                false, false
            };

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
        Table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TableKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(Table);

        Label.setText("jLabel1");

        Text.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TextKeyTyped(evt);
            }
        });

        jButton1.setText("<html><h2>Submit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jButton1KeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        jButton3.setText("<html><h2>Cancel");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jButton3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jButton3KeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton3KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout PanelLayout = new javax.swing.GroupLayout(Panel);
        Panel.setLayout(PanelLayout);
        PanelLayout.setHorizontalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLayout.createSequentialGroup()
                .addComponent(Text, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton3)))
            .addGroup(PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanelLayout.setVerticalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Label, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Text, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 21, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtBatch.setEditable(false);

        jLabel1.setText("<html><h2>Batch Size");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtBatch, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBatch, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        btnQty.setText("Show Quantity");
        btnQty.setEnabled(false);
        btnQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQtyActionPerformed(evt);
            }
        });
        btnQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnQtyKeyPressed(evt);
            }
        });

        btnPercent.setText("Show Percentage");
        btnPercent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPercentActionPerformed(evt);
            }
        });
        btnPercent.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                btnPercentKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPercentKeyPressed(evt);
            }
        });

        btnShowCost.setText("Show Cost");
        btnShowCost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowCostActionPerformed(evt);
            }
        });
        btnShowCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                btnShowCostKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnShowCostKeyPressed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Edit or Delete Table Values", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18), new java.awt.Color(0, 0, 255))); // NOI18N

        ButtonEdit.setText("<html><h2>Edit");
        ButtonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonEditActionPerformed(evt);
            }
        });
        ButtonEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ButtonEditKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ButtonEditKeyPressed(evt);
            }
        });

        jButton2.setText("<html><h2>Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jButton2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jButton2KeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton2KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnQty, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(btnShowCost, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPercent)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQty)
                    .addComponent(btnShowCost)
                    .addComponent(btnPercent))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton6.setText("<html><h1>HOME");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jButton6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton6KeyPressed(evt);
            }
        });

        jButton7.setText("<html><h1>ADD NEW RECIPE");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jButton7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton7KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jButton7)))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private String getComboItemName(String item) {
        PreparedStatement pst9 = null;
        ResultSet rs9 = null;
        try {
            pst9 = conn.prepareStatement("select itemName from RawMaterials where itemName='" + item + "' or itemAlias='" + item + "'");
            rs9 = pst9.executeQuery();
            if (!rs9.next()) {
                pst9 = conn.prepareStatement("select itemName from FinishedGoods where itemName='" + item + "' or itemAlias='" + item + "'");
                rs9 = pst9.executeQuery();
                if (rs9.next()) {
                    item = rs9.getString("itemName");
                }
            } else {
                item = rs9.getString("itemName");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Recipe.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst9.close();
                rs9.close();
            } catch (SQLException ex) {
                Logger.getLogger(Recipe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return item;
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        btnQty.doClick();
        int count = 0;
        boolean chk = true;
        String str[] = new String[2];
        str[0] = getComboItemName(ComboItem.getSelectedItem().toString());

        //getItemName(ComboItem.getSelectedItem().toString());
        Double ratioDouble = Production.RoundTo2Decimals(Double.parseDouble(TextRatio.getText()));
        str[1] = ratioDouble.toString();
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
                    JOptionPane.showMessageDialog(null, "Enter quantity for the item");
                    chk = false;
                    break;
                } else if (!Validation.ratioChk(TextRatio.getText())) {
                    JOptionPane.showMessageDialog(null, "Quantity is not valid");
                    chk = false;
                }
            }
            int a = 0;
            while (Table.getValueAt(a, 0) != null && !Table.getValueAt(a, 0).equals("")) {
                a++;
            }

            //System.out.println(a);
            row = a;
            col = 0;
            if (chk) {
                Table.setValueAt(str[0], row, col);
                Table.setValueAt(str[1], row, col + 1);
                original_batch = original_batch + Double.parseDouble(str[1]);
                original_batch = Production.RoundTo2Decimals(original_batch);
                txtBatch.setText(Double.toString(original_batch));
                row++;
            }

        }
        ComboItem.grabFocus();
        ComboItem.setSelectedIndex(0);
        TextRatio.setText("");
    }//GEN-LAST:event_jButton4ActionPerformed
    public void clear_all() {
        // TextRecipeName.setEnabled(true);
        txtBatch.setText("");
        original_batch = 0;
        TextRatio.setEnabled(true);
        //TextRecipeName.setText("");
        TextRatio.setText("");
        ComboItem.setSelectedIndex(0);
        ComboRecipeName.setSelectedIndex(0);

        int clr = 0;
        int colm = 0;
        while (clr < Table.getRowCount()) {
            Table.setValueAt("", clr, colm++);
            if (colm == 2) {
                clr++;
                colm = 0;
            }
        }

    }

    /*
     * public int getCost(String RecipeName){ int cost=0; try {
     * PreparedStatement pst=conn.prepareStatement("select cost from Recipe
     * where recipeeName='"+RecipeName+"' or recipeeAlias='"+RecipeName+"'");
     * ResultSet rs=pst.executeQuery(); while(rs.next()){
     * cost=Integer.parseInt(rs.getString("batch")); } } catch (SQLException ex)
     * { Logger.getLogger(EditRecipe.class.getName()).log(Level.SEVERE, null,
     * ex); } return cost; }
     *
     * public int getQty(String RecipeName){ int qty=0; try { PreparedStatement
     * pst=conn.prepareStatement("select batch from Recipe where
     * recipeeName='"+RecipeName+"' or recipeeAlias='"+RecipeName+"'");
     * ResultSet rs=pst.executeQuery(); while(rs.next()){
     * qty=Integer.parseInt(rs.getString("batch")); } } catch (SQLException ex)
     * { Logger.getLogger(EditRecipe.class.getName()).log(Level.SEVERE, null,
     * ex); } return qty; }
     *
     *
     */
    public String getAlias(String name) {
        String alias = "";
        try {

            PreparedStatement prst = conn.prepareStatement("select itemAlias from FinishedGoods where itemName='" + name + "'");
            ResultSet rst = prst.executeQuery();
            if (rst.next()) {
                alias = rst.getString("itemAlias");
            }
            prst.close();
            rst.close();


        } catch (SQLException ex) {
            Logger.getLogger(Recipe.class.getName()).log(Level.SEVERE, null, ex);
        }


        return alias;
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        btnQty.doClick();
        String RecipeName = myRecipe;

        if (RecipeName.equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter recipe name");
        } else {
            if (row < 1) {
                JOptionPane.showMessageDialog(null, "No item in the Recipe. Add some items and submit again.");
            } else {
                try {
                    //pst = conn.prepareStatement("select recipeeName from Recipe where recipeename='" + RecipeName + "'");
                    //rs = pst.executeQuery();
                    //if (rs.next()) {
                    //  JOptionPane.showMessageDialog(null, "This Recipe is already in the List. Please go to EDIT RECIPE to make changes in the recipe");
                    // pst.close();
                    //rs.close();
                    //} else {
                    //  pst.close();
                    // rs.close();
                    RecipeAlias = getAlias(RecipeName);
                    String arr[] = new String[40];
                    int inc = 0;
                    int rows = 0, cols = 0;
                    //while (!(Table.getValueAt(rows, cols) == null)) {
                    while (!(Table.getValueAt(rows, cols) == null) && !(Table.getValueAt(rows, cols).toString().equals(""))) {
                        //JOptionPane.showMessageDialog(null, "rows=" + rows + "and col=" + cols + "value=" + Table.getValueAt(rows, cols));
                        arr[inc++] = Table.getValueAt(rows, cols++).toString();
                        if (cols == 2) {
                            rows++;
                            cols = 0;
                        }
                    }
                    while (inc < 40) {
                        arr[inc++] = "";
                    }

                    double tot_qty = 0;
                    int rowst = 0, colst = 1;
                    while (!((Table.getValueAt(rowst, colst) == null) || (Table.getValueAt(rowst, colst).toString().equals("")))) {
                        double qty = Double.parseDouble(Table.getValueAt(rowst++, colst).toString());
                        tot_qty = tot_qty + qty;
                    }

                    CalculateRecipeCostTable(arr);
                    //String sql = "insert into Recipe values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    String sql = "update Recipe set recipeeName=?,item1=?,ratio1=round(?,2),item2=?,ratio2=round(?,2),item3=?,ratio3=round(?,2),item4=?,ratio4=round(?,2),item5=?,ratio5=round(?,2),item6=?,ratio6=round(?,2),item7=?,ratio7=round(?,2),item8=?,ratio8=round(?,2),item9=?,ratio9=round(?,2),item10=?,ratio10=round(?,2),item11=?,ratio11=round(?,2),item12=?,ratio12=round(?,2),item13=?,ratio13=round(?,2),item14=?,ratio14=round(?,2),item15=?,ratio15=round(?,2),item16=?,ratio16=round(?,2),item17=?,ratio17=round(?,2),item18=?,ratio18=round(?,2),item19=?,ratio19=round(?,2),item20=?,ratio20=round(?,2),batch=round(?,2),recipeeAlias=?,cost=round(?,2) where recipeeName='" + RecipeName + "'";
                    pst = conn.prepareStatement(sql);
                    // pst.setString(1, get_id());
                    pst.setString(1, RecipeName);
                    int i = 0;
                    while (i < 40) {
                        pst.setString(i + 2, arr[i]);
                        //System.out.println(arr[i] + "   ");
                        i++;
                    }
                    pst.setDouble(42, tot_qty);
                    // JOptionPane.showMessageDialog(null, tot_qty);
                    pst.setString(43, RecipeAlias);
                    cost = cost / tot_qty;
                    pst.setDouble(44, cost);
                    pst.addBatch();
                    pst.executeBatch();
                    JOptionPane.showMessageDialog(null, "Recipe added");
                    clear_all();

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
        }
        int temprow1 = row;
        for (int i = 0; i < temprow1; i++) {
            ((DefaultTableModel) Table.getModel()).removeRow(0);
            Object[] abc = null;
            ((DefaultTableModel) Table.getModel()).addRow(abc);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    public void CalculateRecipeCostTable(String arr[]) {

        int x = 0;
        while (!arr[x].equals("")) {
            //JOptionPane.showMessageDialog(null, x); 
            String rec = arr[x];
            double quant = Double.parseDouble(arr[x + 1]);
            Boolean flag = chkInRecipe(rec);
            //JOptionPane.showMessageDialog(null, flag);
            if (flag) {
                CalculateRecipeCost(rec, quant);
            } else {
                CalculateRMCost(rec, quant);
            }
            x = x + 2;
        }
    }

    public void CalculateRecipeCost(String rname, double quant) {
        PreparedStatement pp1 = null;
        ResultSet rr1 = null;
        try {
            // pp1=conn.prepareStatement("select item1,item2,item3,item4,item5,item6,item7,item8,item9,item10,item11,item12,item13,item14,item15,item16,item17,item18,item19,item20 from Recipe where recipeeName='"+rname+"'");
            pp1 = conn.prepareStatement("select cost from Recipe where recipeeName='" + rname + "'");
            rr1 = pp1.executeQuery();
            cost = cost + (Double.parseDouble(rr1.getString("cost")) * quant);
            //JOptionPane.showMessageDialog(null, cost);
            pp1.close();
            rr1.close();
        } catch (SQLException ex) {
            Logger.getLogger(Recipe.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public boolean chkInRecipe(String rec) {
        try {
            PreparedStatement pp = conn.prepareStatement("select * from Recipe where recipeeName='" + rec + "'");
            ResultSet rr = pp.executeQuery();
            if (rr.next()) {
                rr.close();
                pp.close();
                return true;
            } else {
                rr.close();
                pp.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(Recipe.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public void CalculateRMCost(String rname, double quant) {

        try {
            PreparedStatement pp = conn.prepareStatement("select cost from RawMaterials where itemName='" + rname + "'");
            ResultSet rr = pp.executeQuery();
            cost = cost + (Double.parseDouble(rr.getString("cost")) * quant);

            //  JOptionPane.showMessageDialog(null, cost);
            pp.close();
            rr.close();
        } catch (SQLException ex) {
            Logger.getLogger(Recipe.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
    }//GEN-LAST:event_TableMouseClicked

    private void ButtonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditActionPerformed
        Text.setText("");
        btnQty.doClick();
        if (Table.getSelectedRow() == -1 || Table.getSelectedRow() >= row) {
            JOptionPane.showMessageDialog(null, "Select an item to edit");
        } else {
            row_num = Table.getSelectedRow();
            String str = Table.getValueAt(row_num, 0).toString();
            Label.setText("<html><h2>Enter quantity for " + str);
            Panel.setVisible(true);
        }
    }//GEN-LAST:event_ButtonEditActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (Text.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Enter quantity for the item");
        } else if (!Validation.ratioChk(Text.getText())) {
            JOptionPane.showMessageDialog(null, "Quantity is not valid");

        } else {
            double temp = Double.parseDouble(Table.getValueAt(row_num, 1).toString());
            Double ratioDouble = Production.RoundTo2Decimals(Double.parseDouble(Text.getText()));
            Table.setValueAt(ratioDouble.toString(), row_num, 1);
            
            original_batch = original_batch + Double.parseDouble(Text.getText()) - temp;
            original_batch = Production.RoundTo2Decimals(original_batch);
            String cvt = Double.toString(original_batch);
            txtBatch.setText(cvt);
            //Table.setValueAt(Text.getText(), row_num, 1);
            Panel.setVisible(false);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        btnQty.doClick();
        if (Table.getSelectedRow() == -1 || Table.getSelectedRow() >= row) {
            JOptionPane.showMessageDialog(null, "Select an item to delete");
        } else {
            int r = Table.getSelectedRow();
            double val = Double.parseDouble(Table.getValueAt(r, 1).toString());
            //JOptionPane.showMessageDialog(null,original_batch);
            // JOptionPane.showMessageDialog(null,val);
            original_batch = original_batch - val;
            original_batch = Production.RoundTo2Decimals(original_batch);
            // JOptionPane.showMessageDialog(null,original_batch);
            String cvt = Double.toString(original_batch);
            txtBatch.setText(cvt);
            ((DefaultTableModel) Table.getModel()).removeRow(Table.getSelectedRow());
            Object[] abc = null;
            ((DefaultTableModel) Table.getModel()).addRow(abc);
            row = row - 1;
        }
        Panel.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void ComboRecipeNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboRecipeNameActionPerformed
        original_batch = 0;
        int temprow = row;
        for (int i = 0; i < temprow; i++) {
            ((DefaultTableModel) Table.getModel()).removeRow(0);
            Object[] abc = null;
            ((DefaultTableModel) Table.getModel()).addRow(abc);
        }
        if (ComboRecipeName.getSelectedIndex() > 0) {
            try {
                //JOptionPane.showMessageDialog(null, "i'm running");
                String name = getItemName(ComboRecipeName.getSelectedItem().toString());
                if (!name.equals("Select")) {
                    pst = conn.prepareStatement("Select * from Recipe where recipeeName='" + name + "' or recipeeAlias='" + name + "'");
                    rs = pst.executeQuery();
                    int cnt = 2;
                    int r = 0;
                    int c = 0;
                    String chker = rs.getString(cnt);
                    //JOptionPane.showMessageDialog(null, chker);
                    while (!chker.equals("") && cnt < 42) {
                        chker = rs.getString(cnt++);
                        // JOptionPane.showMessageDialog(null, chker);
                        Table.setValueAt(chker, r, c++);
                        if (c == 2) {
                            original_batch = original_batch + Double.parseDouble(Table.getValueAt(r, c - 1).toString());
                            original_batch = Production.RoundTo2Decimals(original_batch);
                            
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
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(EditRecipe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        myRecipe = ComboRecipeName.getSelectedItem().toString();
        if (myRecipe.equals("Select")) {
        } else {
            try {
                PreparedStatement pstm = conn.prepareStatement("select itemName from FinishedGoods where itemName='" + myRecipe + "' or itemAlias='" + myRecipe + "'");
                ResultSet rstm = pstm.executeQuery();
                if (rstm.next()) {
                    myRecipe = rstm.getString("itemName");
                    //  JOptionPane.showMessageDialog(null, myRecipe);
                    //  JOptionPane.showMessageDialog(null, myRecipe1);
                }
                pstm.close();
                rstm.close();
            } catch (SQLException ex) {
                Logger.getLogger(Recipe.class.getName()).log(Level.SEVERE, null, ex);
            }

            filler(myRecipe);
            txtBatch.setText("");
            //JOptionPane.showMessageDialog(null, original_batch);
            //original_batch=0;
            TextRatio.setText("");
        }
    }//GEN-LAST:event_ComboRecipeNameActionPerformed
    public void enable_all() {
        // jPanel4.setEnabled(true);
    }
    private void ComboRecipeNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboRecipeNameItemStateChanged
        ComboItem.setSelectedIndex(0);
        TextRatio.setText("");// TODO add your handling code here:
    }//GEN-LAST:event_ComboRecipeNameItemStateChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Panel.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        this.setVisible(false);


    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        this.setVisible(false);
        Recipe rc = new Recipe(null, true);
        rc.setVisible(true);

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton6KeyPressed
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
        else if(a==27){
            jButton6.doClick();
        }
        
        else if(a == 113){
            jButton5.doClick();
        }       // TODO add your handling code here:
    }//GEN-LAST:event_jButton6KeyPressed

    private void jButton7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton7KeyPressed
        int a = evt.getKeyCode();
        if (a == 10) {
            try {
                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
            } catch (AWTException ex) {
                Logger.getLogger(OutTransaction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    else if(a==27){
            jButton6.doClick();
        }
        
        else if(a == 113){
            jButton5.doClick();
        }     // TODO add your handling code here:
    }//GEN-LAST:event_jButton7KeyPressed

    private void jButton4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton4KeyPressed
        int a = evt.getKeyCode();
        if (a == 10) {
            try {
                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
            } catch (AWTException ex) {
                Logger.getLogger(OutTransaction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4KeyPressed

    private void jButton5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton5KeyPressed
        int a = evt.getKeyCode();
        if (a == 10) {
            try {
                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
            } catch (AWTException ex) {
                Logger.getLogger(OutTransaction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   else if(a==27){
            jButton6.doClick();
        }
        
        else if(a == 113){
            jButton5.doClick();
        }       // TODO add your handling code here:
    }//GEN-LAST:event_jButton5KeyPressed

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
        }        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonEditKeyPressed

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
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2KeyPressed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        int a = evt.getKeyCode();
        if (a == 10) {
            try {
                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
            } catch (AWTException ex) {
                Logger.getLogger(OutTransaction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1KeyPressed

    private void jButton3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton3KeyPressed
        int a = evt.getKeyCode();
        if (a == 10) {
            try {
                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
            } catch (AWTException ex) {
                Logger.getLogger(OutTransaction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3KeyPressed

    private void btnQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQtyActionPerformed
        int rows = 0;

        while (!(Table.getValueAt(rows, 1) == null) && !(Table.getValueAt(rows, 1).equals(""))) {
            Table.setValueAt(quantity[rows], rows, 1);
            rows++;

        }
        btnPercent.setEnabled(true);
        btnQty.setEnabled(false);
    }//GEN-LAST:event_btnQtyActionPerformed

    private void btnPercentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPercentActionPerformed
        int rows = 0;
        double conversion;

        String percentage;
        while (!(Table.getValueAt(rows, 1) == null) && !(Table.getValueAt(rows, 1).equals(""))) {
            quantity[rows] = Table.getValueAt(rows, 1).toString();
            conversion = (Double.parseDouble(quantity[rows]) / original_batch) * 100;
            conversion = Production.RoundTo2Decimals(conversion);
            percentage = Double.toString(conversion);
            Table.setValueAt(percentage, rows, 1);
            rows++;

        }
        btnPercent.setEnabled(false);
        btnQty.setEnabled(true);
    }//GEN-LAST:event_btnPercentActionPerformed

    private void ComboItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboItemActionPerformed

    private void btnShowCostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowCostActionPerformed
        if (ComboRecipeName.getSelectedIndex() > 0) {
            btnQty.doClick();
            costing();
        } else {
            JOptionPane.showMessageDialog(null, "Select one Recipe");
        }
    }//GEN-LAST:event_btnShowCostActionPerformed

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
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_btnShowCostKeyPressed

    private void btnPercentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPercentKeyPressed
        int a = evt.getKeyCode();
        if (a == 10) {
            try {
                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
            } catch (AWTException ex) {
                Logger.getLogger(OutTransaction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPercentKeyPressed

    private void btnQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnQtyKeyPressed
        int a = evt.getKeyCode();
        if (a == 10) {
            try {
                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
            } catch (AWTException ex) {
                Logger.getLogger(OutTransaction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_btnQtyKeyPressed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        int a = evt.getKeyCode();
        if (a == 27) {
            jButton6.doClick();
        } else if (a == 113) {
            jButton5.doClick();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_formKeyPressed

    private void ComboRecipeNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ComboRecipeNameKeyPressed
  int a = evt.getKeyCode();
         if(a==27){
            jButton6.doClick();
        }
        
        else if(a == 113){
            jButton5.doClick();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_ComboRecipeNameKeyPressed

    private void ComboItemKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ComboItemKeyTyped
     int a = evt.getKeyCode();
         if(a==27){
            jButton6.doClick();
        }
        
        else if(a == 113){
            jButton5.doClick();
        }      // TODO add your handling code here:
    }//GEN-LAST:event_ComboItemKeyTyped

    private void TextRatioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextRatioKeyPressed
  int a = evt.getKeyCode();
         if(a==27){
            jButton6.doClick();
        }
        
        else if(a == 113){
            jButton5.doClick();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_TextRatioKeyPressed

    private void jButton4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton4KeyTyped
  int a = evt.getKeyCode();
         if(a==27){
            jButton6.doClick();
        }
        
        else if(a == 113){
            jButton5.doClick();
        }         // TODO add your handling code here:
    }//GEN-LAST:event_jButton4KeyTyped

    private void TableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TableKeyPressed
       int a = evt.getKeyCode();
         if(a==27){
            jButton6.doClick();
        }
        
        else if(a == 113){
            jButton5.doClick();
        }    // TODO add your handling code here:
    }//GEN-LAST:event_TableKeyPressed

    private void btnShowCostKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnShowCostKeyTyped
int a = evt.getKeyCode();
         if(a==27){
            jButton6.doClick();
        }
        
        else if(a == 113){
            jButton5.doClick();
        }           // TODO add your handling code here:
    }//GEN-LAST:event_btnShowCostKeyTyped

    private void btnPercentKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPercentKeyTyped
int a = evt.getKeyCode();
         if(a==27){
            jButton6.doClick();
        }
        
        else if(a == 113){
            jButton5.doClick();
        }           // TODO add your handling code here:
    }//GEN-LAST:event_btnPercentKeyTyped

    private void ButtonEditKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ButtonEditKeyTyped
int a = evt.getKeyCode();
         if(a==27){
            jButton6.doClick();
        }
        
        else if(a == 113){
            jButton5.doClick();
        }           // TODO add your handling code here:
    }//GEN-LAST:event_ButtonEditKeyTyped

    private void jButton2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyTyped
int a = evt.getKeyCode();
         if(a==27){
            jButton6.doClick();
        }
        
        else if(a == 113){
            jButton5.doClick();
        }           // TODO add your handling code here:
    }//GEN-LAST:event_jButton2KeyTyped

    private void TextKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextKeyTyped
int a = evt.getKeyCode();
         if(a==27){
            jButton6.doClick();
        }
        
        else if(a == 113){
            jButton5.doClick();
        }           // TODO add your handling code here:
    }//GEN-LAST:event_TextKeyTyped

    private void jButton1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyTyped
int a = evt.getKeyCode();
         if(a==27){
            jButton6.doClick();
        }
        
        else if(a == 113){
            jButton5.doClick();
        }           // TODO add your handling code here:
    }//GEN-LAST:event_jButton1KeyTyped

    private void jButton3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton3KeyTyped
int a = evt.getKeyCode();
         if(a==27){
            jButton6.doClick();
        }
        
        else if(a == 113){
            jButton5.doClick();
        }           // TODO add your handling code here:
    }//GEN-LAST:event_jButton3KeyTyped

    public void costing() {
        String arr1[] = new String[40];
        int inc = 0;
        int rows = 0, cols = 0;

        while (!(Table.getValueAt(rows, cols) == null) && !(Table.getValueAt(rows, cols).equals(""))) {
            arr1[inc++] = Table.getValueAt(rows, cols++).toString();
            if (cols == 2) {
                rows++;
                cols = 0;
            }
        }
        double totalCost = calcCost(arr1, rows);
        System.out.println(totalCost);
        double mc = totalCost * 0.15;
        double finalCost = ((totalCost + mc) / original_batch);
        finalCost = RoundTo2Decimals(finalCost);
        JOptionPane.showMessageDialog(null, "Per Unit Cost is : " + finalCost);

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

    private String get_id() {
        String id = "";
        Statement stm = null;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery("select max(recipeeId) from Recipe");
            id = rs.getObject(1).toString();
            Integer temp = Integer.parseInt(id);
            temp = temp + 1;
            id = temp.toString();


        } catch (SQLException ex) {
            Logger.getLogger(EditRecipe.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(EditRecipe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return id;
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
            java.util.logging.Logger.getLogger(EditRecipe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditRecipe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditRecipe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditRecipe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                EditRecipe dialog = new EditRecipe(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton ButtonEdit;
    private javax.swing.JComboBox ComboItem;
    private javax.swing.JComboBox ComboRecipeName;
    private javax.swing.JLabel Label;
    private javax.swing.JPanel Panel;
    private javax.swing.JTable Table;
    private javax.swing.JTextField Text;
    private javax.swing.JTextField TextRatio;
    private javax.swing.JButton btnPercent;
    private javax.swing.JButton btnQty;
    private javax.swing.JButton btnShowCost;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtBatch;
    // End of variables declaration//GEN-END:variables
}
