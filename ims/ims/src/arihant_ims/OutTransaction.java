package arihant_ims;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;

public class OutTransaction extends javax.swing.JDialog {

    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    String click = "";
    String PaneLabel = "";
    String editItem = "";
    String rowIdStr = "";
    static String sqlQuerry = "";
    int n = 0;
    int m = 0;
    int row = 0;

    public OutTransaction(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        conn = CreateConnection.connectDB();
        initComponents();
        Dimension scrn = getToolkit().getScreenSize();
        this.setBounds(0, 0, scrn.width, scrn.height);
        try {

            fill_table();
            disableAll();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
            }
        }
        n = 1;
        Calendar currentDate = Calendar.getInstance();
        DateChooser1.setDate(currentDate.getTime());
    }

    private void fill_table() {
        try {
            pst = conn.prepareStatement("SELECT * FROM transactionsOut order by transactionId desc limit 40");
            rs = pst.executeQuery();
            Table.setModel(C.getModel(rs));
        } catch (SQLException ex) {
            Logger.getLogger(OutTransaction.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception ex) {
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        TextSearch = new javax.swing.JTextField();
        PanelTools = new javax.swing.JPanel();
        ButtonAdd = new javax.swing.JButton();
        ButtonDelete = new javax.swing.JButton();
        ButtonClear = new javax.swing.JButton();
        ButtonRefresh = new javax.swing.JButton();
        ButtonSubmit = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        TextDate = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        ComboBoxParty = new javax.swing.JComboBox();
        TextPartyName = new javax.swing.JTextField();
        DateChooser1 = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        LabelMaterialName = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        ComboBoxRaw = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        TextQuantity = new javax.swing.JTextField();
        TextPrice = new javax.swing.JTextField();
        ButtonAddItem = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableItem = new javax.swing.JTable();
        ButtonDeleteItem = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        TextPackingQuantity = new javax.swing.JTextField();
        ComboBoxPackingType = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jRadioButton5 = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TextAreaComments = new javax.swing.JTextArea();
        jRadioButton6 = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        TextStartDate = new javax.swing.JTextField();
        TextEndDate = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        DateChooserStart = new com.toedter.calendar.JDateChooser();
        DateChooserEnd = new com.toedter.calendar.JDateChooser();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SEARCH", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 24), new java.awt.Color(0, 0, 255))); // NOI18N

        TextSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TextSearch)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(TextSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        PanelTools.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TOOLS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 24), new java.awt.Color(51, 51, 255))); // NOI18N

        ButtonAdd.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        ButtonAdd.setText("<HTML><H2>ADD   NEW TRANSACTION");
        ButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonAddActionPerformed(evt);
            }
        });
        ButtonAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ButtonAddKeyPressed(evt);
            }
        });

        ButtonDelete.setText("<HTML><H2>DELETE");
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

        ButtonClear.setText("<HTML><H2>CLEAR");
        ButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonClearActionPerformed(evt);
            }
        });
        ButtonClear.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ButtonClearKeyPressed(evt);
            }
        });

        ButtonRefresh.setText("<HTML><H2>REFRESH");
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

        ButtonSubmit.setText("<html><h2>SUBMIT TRANSCTION");
        ButtonSubmit.setEnabled(false);
        ButtonSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSubmitActionPerformed(evt);
            }
        });
        ButtonSubmit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ButtonSubmitKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout PanelToolsLayout = new javax.swing.GroupLayout(PanelTools);
        PanelTools.setLayout(PanelToolsLayout);
        PanelToolsLayout.setHorizontalGroup(
            PanelToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(ButtonDelete)
            .addComponent(ButtonClear)
            .addComponent(ButtonRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
            .addComponent(ButtonSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        PanelToolsLayout.setVerticalGroup(
            PanelToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelToolsLayout.createSequentialGroup()
                .addComponent(ButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ButtonRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ButtonSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "TRANSACTION", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18), new java.awt.Color(0, 51, 255)), "OUT TRANSACTION", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18), new java.awt.Color(0, 0, 255))); // NOI18N

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select Date and party name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 10), new java.awt.Color(51, 102, 255))); // NOI18N

        jLabel3.setText("<html><h2>Date");

        TextDate.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        TextDate.setForeground(new java.awt.Color(0, 0, 204));
        TextDate.setEnabled(false);

        jLabel5.setText("<html><h2>Customer<br> Name");

        buttonGroup2.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButton3.setText("New");
        jRadioButton3.setEnabled(false);
        jRadioButton3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButton3ItemStateChanged(evt);
            }
        });

        buttonGroup2.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButton4.setText("Customer");
        jRadioButton4.setEnabled(false);
        jRadioButton4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButton4ItemStateChanged(evt);
            }
        });

        ComboBoxParty.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Party Name" }));
        ComboBoxParty.setEnabled(false);

        TextPartyName.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        TextPartyName.setEnabled(false);

        DateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                DateChooser1PropertyChange(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "All Items Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 10), new java.awt.Color(51, 102, 255))); // NOI18N

        LabelMaterialName.setText("<html><h2>Item");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButton1.setText("Raw Material");
        jRadioButton1.setEnabled(false);
        jRadioButton1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButton1ItemStateChanged(evt);
            }
        });
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButton2.setText("Finished Goods");
        jRadioButton2.setEnabled(false);
        jRadioButton2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButton2ItemStateChanged(evt);
            }
        });

        ComboBoxRaw.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Finished Goods" }));
        ComboBoxRaw.setEnabled(false);
        ComboBoxRaw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxRawActionPerformed(evt);
            }
        });

        jLabel2.setText("<html><h2>Quantity");

        TextQuantity.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        TextQuantity.setEnabled(false);
        TextQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextQuantityActionPerformed(evt);
            }
        });

        TextPrice.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        TextPrice.setEnabled(false);

        ButtonAddItem.setText("<html><h1>Add Item");
        ButtonAddItem.setNextFocusableComponent(TextAreaComments);
        ButtonAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonAddItemActionPerformed(evt);
            }
        });
        ButtonAddItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ButtonAddItemKeyPressed(evt);
            }
        });

        TableItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Item Name", "Quantity", "Price", "Packing Type", "Total Packing"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(TableItem);

        ButtonDeleteItem.setText("<html><h1>Delete Item");
        ButtonDeleteItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonDeleteItemActionPerformed(evt);
            }
        });
        ButtonDeleteItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ButtonDeleteItemKeyPressed(evt);
            }
        });

        jLabel7.setText("<html><h2>Packing Type");

        jLabel9.setText("<html><h2>Price");

        TextPackingQuantity.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        TextPackingQuantity.setEnabled(false);
        TextPackingQuantity.setNextFocusableComponent(ButtonAddItem);
        TextPackingQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextPackingQuantityActionPerformed(evt);
            }
        });

        ComboBoxPackingType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Packing Type" }));
        ComboBoxPackingType.setEnabled(false);
        ComboBoxPackingType.setNextFocusableComponent(TextPackingQuantity);
        ComboBoxPackingType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxPackingTypeActionPerformed(evt);
            }
        });

        jLabel10.setText("<html><h2>Packing Quantity");

        buttonGroup1.add(jRadioButton5);
        jRadioButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButton5.setSelected(true);
        jRadioButton5.setText("Packaging");
        jRadioButton5.setEnabled(false);
        jRadioButton5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButton5ItemStateChanged(evt);
            }
        });

        jLabel8.setText("<html><h2>Narration");
        jLabel8.setAutoscrolls(true);

        TextAreaComments.setColumns(20);
        TextAreaComments.setRows(5);
        TextAreaComments.setEnabled(false);
        TextAreaComments.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextAreaCommentsKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(TextAreaComments);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ButtonDeleteItem)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jRadioButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jRadioButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton5)))
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(LabelMaterialName)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 102, Short.MAX_VALUE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(ComboBoxPackingType, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(TextPrice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(TextQuantity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(TextPackingQuantity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(ComboBoxRaw, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(ButtonAddItem, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelMaterialName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboBoxRaw, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBoxPackingType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextPackingQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonAddItem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonDeleteItem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonGroup2.add(jRadioButton6);
        jRadioButton6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButton6.setText("Supplier");
        jRadioButton6.setEnabled(false);
        jRadioButton6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButton6ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jRadioButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jRadioButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButton6)
                        .addGap(69, 69, 69))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TextPartyName, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(TextDate, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(DateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(ComboBoxParty, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TextDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(DateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton3)
                            .addComponent(jRadioButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jRadioButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ComboBoxParty, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextPartyName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Transaction Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 24), new java.awt.Color(51, 51, 255))); // NOI18N

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table);

        TextStartDate.setEditable(false);
        TextStartDate.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        TextStartDate.setEnabled(false);

        TextEndDate.setEditable(false);
        TextEndDate.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        TextEndDate.setEnabled(false);

        jButton1.setText("Go");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        jLabel1.setText("<html><h2>Start Date");

        jLabel6.setText("<html><h2>End Date");

        jButton6.setText("Clear DB table");
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

        DateChooserStart.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                DateChooserStartPropertyChange(evt);
            }
        });

        DateChooserEnd.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                DateChooserEndPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap(36, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DateChooserStart, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22))
                            .addComponent(TextStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(DateChooserEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(TextEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 49, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(DateChooserStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(DateChooserEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton6)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jButton1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton3.setText("<html><h1>HOME");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jButton3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton3KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jButton3KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PanelTools, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(188, 188, 188))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(PanelTools, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelTools.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
        deleteItemsTable();
        comboBoxPartyNameFiller();
        comboBoxRawMaterialFiller();
        int row_num = Table.getSelectedRow();
        rowIdStr = Table.getModel().getValueAt(row_num, 0).toString();
        String sql = "select * from transactionsOut where transactionId='" + rowIdStr + "'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                TextDate.setText(rs.getString("tdate"));
                TextQuantity.setText(rs.getString("quantity"));
                TextPrice.setText(rs.getString("price"));
                ComboBoxParty.setSelectedItem(rs.getString("party"));
                TextPartyName.setText(rs.getString("party"));
                TextAreaComments.setText(rs.getString("narration"));
                ComboBoxRaw.removeAllItems();
                ComboBoxRaw.addItem(rs.getString("item"));
                ComboBoxRaw.setSelectedItem(rs.getString("item"));


                ComboBoxPackingType.removeAllItems();
                ComboBoxPackingType.addItem(rs.getString("packingType"));
                ComboBoxRaw.setSelectedItem(rs.getString("packingType"));

                TextPackingQuantity.setText(rs.getString("noOfPacking"));

                click = "readyForEdit";
            }
        } catch (SQLException ex) {
            Logger.getLogger(OutTransaction.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
            }
        }
        disableAll();
        // System.out.print(ComboBoxRaw.getSelectedItem());
    }//GEN-LAST:event_TableMouseClicked

    private void TextSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextSearchKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_TextSearchKeyReleased

    private void ButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonClearActionPerformed

        deleteItemsTable();
        fill_table();
        click = "clear";
        comboBoxPartyNameFiller();
        comboBoxRawMaterialFiller();
        TextDate.setText("");
        TextQuantity.setText("");
        TextPrice.setText("");
        TextPartyName.setText("");
        TextAreaComments.setText("");
    }//GEN-LAST:event_ButtonClearActionPerformed

    public void deleteItemsTable() {

        while (row >= 0) {
            ((DefaultTableModel) TableItem.getModel()).removeRow(row);
            Object[] abc = null;
            ((DefaultTableModel) TableItem.getModel()).addRow(abc);
            row--;
        }
        row = 0;
    }

    private void ButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonAddActionPerformed
        Calendar currentDate = Calendar.getInstance();
        DateChooser1.setDate(currentDate.getTime());
        DateChooser1.grabFocus();
        deleteItemsTable();
        fill_table();
        enableAll();
        onAddButtonClick();
        click = "add";
    }//GEN-LAST:event_ButtonAddActionPerformed

    public String todayDate() {
//   SimpleDateFormat formatter1 =  new SimpleDateFormat("yyyy-MM-dd");
//   String dateNow1 = formatter1.format(DateChooser1.getCalendar().getTime());
//   System.out.println(dateNow1);  
//   TextDate.setText(dateNow1);   
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = formatter.format(currentDate.getTime());
        return dateNow;

    }

    public void onAddButtonClick() {
        TextDate.setText(todayDate());
        TextQuantity.setText("");
        TextPrice.setText("");
        TextPartyName.setText("");
        TextPackingQuantity.setText("");
        comboBoxPartyNameFiller();
        comboBoxRawMaterialFiller();
        comboBoxPackingTypeNameFiller();
        jRadioButton2.setSelected(true);
        jRadioButton4.setSelected(true);
        TextAreaComments.setText("");
        rawMaterialOptionChange();
        partyNameOptionChange();
        LabelMaterialName.setText("<html><h2>Item");

    }

    public void comboBoxPackingTypeNameFiller() {
        ComboBoxPackingType.removeAllItems();
        ComboBoxPackingType.addItem("Select Packing Type");
        try {
            pst = conn.prepareStatement("select * from packing order by \"TypeOfPacking\"");
            rs = pst.executeQuery();
            while (rs.next()) {
                ComboBoxPackingType.addItem(rs.getString("TypeOfPacking"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
            }
        }
    }

    public void comboBoxPartyNameFiller() {
        if (jRadioButton4.isSelected() == true) {
            ComboBoxParty.removeAllItems();
            ComboBoxParty.addItem("Select Party Name");
            try {
                pst = conn.prepareStatement("select * from Customers order by \"name\"");
                rs = pst.executeQuery();
                while (rs.next()) {

                    ComboBoxParty.addItem(rs.getString("name"));
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    pst.close();
                    rs.close();
                } catch (SQLException ex) {
                }
            }
        } else {
            ComboBoxParty.removeAllItems();
            ComboBoxParty.addItem("Select Supplier Name");
            try {
                pst = conn.prepareStatement("select * from Suppliers order by \"name\"");
                rs = pst.executeQuery();
                while (rs.next()) {

                    ComboBoxParty.addItem(rs.getString("name"));
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    pst.close();
                    rs.close();
                } catch (SQLException ex) {
                }
            }
        }
    }

    public void comboBoxRawMaterialFiller() {

        if (jRadioButton1.isSelected() == true) {
            LabelMaterialName.setText("<html><h2>Item<br>");
            ComboBoxRaw.removeAllItems();
            ComboBoxRaw.addItem("Select Raw Material");
            ComboBoxRaw.setEnabled(true);
            TextQuantity.setText("");
            TextQuantity.setEnabled(true);
            try {
                pst = conn.prepareStatement("select * from RawMaterials order by \"itemName\"");
                rs = pst.executeQuery();
                while (rs.next()) {
                    if (rs.getString("itemName").equals("")) {
                    } else {
                        ComboBoxRaw.addItem(rs.getString("itemName"));
                    }

                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    pst.close();
                    rs.close();
                } catch (SQLException ex) {
                }
            }
            try {
                pst = conn.prepareStatement("select * from RawMaterials order by \"itemAlias\"");
                rs = pst.executeQuery();
                while (rs.next()) {
                    if (rs.getString("itemAlias").equals(null)) {
                    } else if (rs.getString("itemAlias").equals("")) {
                    } else {
                        ComboBoxRaw.addItem(rs.getString("itemAlias"));
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    pst.close();
                    rs.close();
                } catch (SQLException ex) {
                }
            }



        } else if (jRadioButton2.isSelected() == true) {
            LabelMaterialName.setText("<html><h2>Item");
            ComboBoxRaw.removeAllItems();
            ComboBoxRaw.addItem("Select Finished Goods");
            ComboBoxRaw.setEnabled(true);
            TextQuantity.setText("");
            TextQuantity.setEnabled(true);

            try {
                pst = conn.prepareStatement("select * from FinishedGoods order by \"itemName\"");
                rs = pst.executeQuery();
                while (rs.next()) {
                    if (rs.getString("itemName").equals("")) {
                    } else {
                        ComboBoxRaw.addItem(rs.getString("itemName"));
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    pst.close();
                    rs.close();
                } catch (SQLException ex) {
                }
            }
            try {
                pst = conn.prepareStatement("select * from FinishedGoods order by \"itemAlias\"");
                rs = pst.executeQuery();
                while (rs.next()) {
                    if (rs.getString("itemAlias").equals("")) {
                    } else {
                        ComboBoxRaw.addItem(rs.getString("itemAlias"));
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    pst.close();
                    rs.close();
                } catch (SQLException ex) {
                }
            }
        } else {
            LabelMaterialName.setText("<html><h2>Packing");
            ComboBoxRaw.removeAllItems();
            ComboBoxRaw.addItem("Packing");
            ComboBoxRaw.setEnabled(false);
            TextQuantity.setText("0");
            TextQuantity.setEnabled(false);
        }
    }

    public void rawMaterialOptionChange() {
        comboBoxRawMaterialFiller();
    }

    public void partyNameOptionChange() {
        comboBoxPartyNameFiller();
        TextPartyName.setText("");
        if (jRadioButton3.isSelected()) {
            ComboBoxParty.setEnabled(false);
            TextPartyName.setEnabled(true);
        } else {
            ComboBoxParty.setEnabled(true);
            TextPartyName.setEnabled(false);
        }
    }

    public void enableAll() {
        TextDate.setEnabled(false);
        TextQuantity.setEnabled(true);
        TextPrice.setEnabled(true);
        ComboBoxRaw.setEnabled(true);
        ComboBoxParty.setEnabled(true);
        jRadioButton1.setEnabled(true);
        jRadioButton2.setEnabled(true);
        jRadioButton3.setEnabled(true);
        jRadioButton4.setEnabled(true);
        jRadioButton5.setEnabled(true);
        jRadioButton6.setEnabled(true);
        ButtonSubmit.setEnabled(true);
        DateChooser1.setEnabled(true);
        ButtonAddItem.setEnabled(true);
        ButtonDeleteItem.setEnabled(true);
        TextAreaComments.setEnabled(true);
        TextPackingQuantity.setEnabled(true);
        ComboBoxPackingType.setEnabled(true);
    }

    public void disableAll() {
        TextDate.setEnabled(false);
        TextQuantity.setEnabled(false);
        TextPrice.setEnabled(false);
        TextPartyName.setEnabled(false);
        ComboBoxRaw.setEnabled(false);
        ComboBoxParty.setEnabled(false);
        jRadioButton1.setEnabled(false);
        jRadioButton2.setEnabled(false);
        jRadioButton3.setEnabled(false);
        jRadioButton4.setEnabled(false);
        jRadioButton6.setEnabled(false);
        jRadioButton5.setEnabled(false);
        ButtonSubmit.setEnabled(false);
        DateChooser1.setEnabled(false);
        ButtonAddItem.setEnabled(false);
        ButtonDeleteItem.setEnabled(false);
        TextAreaComments.setEnabled(false);
        TextPackingQuantity.setEnabled(false);
        ComboBoxPackingType.setEnabled(false);

    }

    public void deletion(String sql) {
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

    public void updationInRawMaterialsTable(String sql) {

        try {
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
        } catch (SQLException ex) {
            //Logger.getLogger(SampleCopy.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
            }
        }
    }

    public void updationInFinishedGoodsTable(String sql) {

        try {
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
        } catch (SQLException ex) {
            // Logger.getLogger(SampleCopy.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
            }
        }
    }

    private void ButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDeleteActionPerformed
        if (click.equals("readyForEdit")) {
            int i = JOptionPane.showConfirmDialog(null, "Are you sure", "Delete Confirmation", 0);
            // System.out.print(i);
            if (i == 0) {
                click = "";
                String sql = "delete from transactionsOut  where transactionId='" + rowIdStr + "'";
                deletion(sql);
                sqlQuerry = " update FinishedGoods set  openingStock = round(openingStock + " + TextQuantity.getText() + ") where itemName = '" + ComboBoxRaw.getSelectedItem() + "' or itemAlias = '" + ComboBoxRaw.getSelectedItem() + "'";
                updationInRawMaterialsTable(sqlQuerry);
                //System.out.println(sqlQuerry);
                sqlQuerry = " update RawMaterials  set  openingStock = round(openingStock + " + TextQuantity.getText() + ") where itemName = '" + ComboBoxRaw.getSelectedItem() + "' or itemAlias = '" + ComboBoxRaw.getSelectedItem() + "'";
                updationInFinishedGoodsTable(sqlQuerry);
                sqlQuerry = " update Packing set Quantity =Quantity + " + TextPackingQuantity.getText() + " where TypeOfPacking = '" + ComboBoxPackingType.getSelectedItem().toString() + "'";
                updationInFinishedGoodsTable(sqlQuerry);
                //System.out.println(sqlQuerry);
                fill_table();
                onAddButtonClick();
                disableAll();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please Select an item to delete from search box or table");
        }

// TODO add your handling code here:
    }//GEN-LAST:event_ButtonDeleteActionPerformed

    private void ButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonRefreshActionPerformed
        try {
            fill_table();        // TODO add your handling code here:
        } catch (Exception e) {
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
            }
        }
    }//GEN-LAST:event_ButtonRefreshActionPerformed

    private void ButtonSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSubmitActionPerformed
      String strDate = "";
        PaneLabel = "";
        int flag = 0;
        if (click.equals("add") || click.equals("clear")) {
//all validatons
            negativeStockCheck();
            validations();
            if (PaneLabel.equals("")) {
                String sql = "";

//entry in customers table
                if (jRadioButton3.isSelected() == true) {
                    sql = " Insert into Customers values( '"
                            + TextPartyName.getText() + "','','','','')";
                    try {
                        Statement stm = conn.createStatement();
                        stm.executeUpdate(sql);
                        stm.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(OutTransaction.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            pst.close();
                            rs.close();
                        } catch (SQLException ex) {
                        }
                    }
                }

                String partyName = "";
//set party name
                if (jRadioButton3.isSelected() == true) {
                    partyName = TextPartyName.getText();
                } else {
                    partyName = (String) ComboBoxParty.getSelectedItem();
                }
                int count = 0;
                while (count < row) {

                    flag = 1;
                    String strItemName = TableItem.getValueAt(count, 0).toString();
                    String strQuantity = TableItem.getValueAt(count, 1).toString();
                    String strPrice = TableItem.getValueAt(count, 2).toString();
                    String packingType = TableItem.getValueAt(count, 3).toString();
                    String numberOfPacking = TableItem.getValueAt(count, 4).toString();
                    count++;
//update raw materials tables


                    sql = "select itemName from RawMaterials where itemName = '" + strItemName + "'or itemAlias = '" + strItemName + "'";

                    try {
                        Statement stm1 = null;
                        stm1 = conn.createStatement();
                        rs = stm1.executeQuery(sql);
                        strItemName = rs.getObject(1).toString();
                        rs.close();
                        stm1.close();
                    } catch (Exception e) {
                    }


                    sql = " update RawMaterials set openingStock =round(openingStock - " + strQuantity + ",2) where itemName = '" + strItemName + "'or itemAlias = '" + strItemName + "'";
// System.out.println(sql);

                    try {
                        Statement stm = conn.createStatement();
                        stm.executeUpdate(sql);
                        stm.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(OutTransaction.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            pst.close();
                            rs.close();
                        } catch (SQLException ex) {
                        }
                    }
//update finished good tables


                    sql = "select itemName from FinishedGoods where itemName = '" + strItemName + "'or itemAlias = '" + strItemName + "'";

                    try {
                        Statement stm1 = null;
                        stm1 = conn.createStatement();
                        rs = stm1.executeQuery(sql);
                        strItemName = rs.getObject(1).toString();
                        rs.close();
                        stm1.close();

                    } catch (Exception e) {
                    }
                    sql = " update FinishedGoods set openingStock =round(openingStock - " + strQuantity + ",2) where itemName = '" + strItemName + "'or itemAlias = '" + strItemName + "'";
//System.out.print(sql);
                    try {
                        Statement stm = conn.createStatement();
                        stm.executeUpdate(sql);
                        stm.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(OutTransaction.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            pst.close();
                            rs.close();
                        } catch (SQLException ex) {
                        }
                    }

//update packing
                    sql = " update Packing set Quantity =Quantity - " + numberOfPacking + " where TypeOfPacking = '" + packingType + "'";
                    // System.out.print(sql);
                    try {
                        Statement stm = conn.createStatement();
                        stm.executeUpdate(sql);
                        stm.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(OutTransaction.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            pst.close();
                            rs.close();
                        } catch (SQLException ex) {
                        }
                    }
                    //update out transaction
                    strDate = TextDate.getText();
                    sql = "Insert into transactionsOut values( '"
                            + get_id() + "','"
                            + TextDate.getText() + "','" + strItemName + "',round('" + strQuantity + "',2),round('"
                            + strPrice + "',2),'" + partyName + "','" + packingType + "','" + numberOfPacking + "','" + TextAreaComments.getText() + "')";
                    // System.out.print(sql);
                    try {
                        Statement stm = conn.createStatement();
                        stm.executeUpdate(sql);
                        stm.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(OutTransaction.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            pst.close();
                            rs.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(OutTransaction.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    enableAll();

                }

            } else {
                JOptionPane.showMessageDialog(null, PaneLabel);
            }
            if (flag == 1) {
                JOptionPane.showMessageDialog(null, "Transaction added");
                disableAll();
                deleteItemsTable();
                ButtonAdd.doClick();
                TextDate.setText(strDate);
            }

        }// onAddButtonClick();
        fill_table();

    }//GEN-LAST:event_ButtonSubmitActionPerformed
    public void validations() {

//for party name
        if (jRadioButton3.isSelected() == true) {
            if (TextPartyName.getText().equals("")) {
                PaneLabel = "Please enter a Party Name";
            } else {
                if (PaneLabel.equals("")) {
                    PaneLabel = Validation.primaryKeyChk(TextPartyName.getText(), "name", "Customers");
                }
            }
        } else {
            if (ComboBoxParty.getSelectedItem().equals("Select Supplier Name")) {
                PaneLabel = "Please Select Supplier Name";
            }
            if (ComboBoxParty.getSelectedItem().equals("Select Party Name")) {
                PaneLabel = "Please select Party Name";
            }

        }

        if (TextDate.getText().equals("")) {
            PaneLabel = "Please select Date";
        }

        if (row == 0) {
            PaneLabel = "Please add at least one item";
        }


    }

    private String get_id() {
        String id = "1";
        Statement stm = null;
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery("select max(transactionId) from transactionsOut");
            if (rs.getObject(1) != null) {
                id = rs.getObject(1).toString();
                Integer temp = Integer.parseInt(id);
                temp = temp + 1;
                id = temp.toString();
            } else {
                id = "1";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Recipe.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(Recipe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return id;
    }

    public void negativeStockCheck() {
        int count = 0;
        String sql;
        while (count < row) {
            String strItemName = TableItem.getValueAt(count, 0).toString();
            String strQuantity = TableItem.getValueAt(count, 1).toString();
            String strPrice = TableItem.getValueAt(count, 2).toString();
            String strPackingType = TableItem.getValueAt(count, 3).toString();
            String strPackingQuantity = TableItem.getValueAt(count, 4).toString();
            count++;

            sql = " select openingStock from RawMaterials where itemName = '" + strItemName + "' or itemAlias = '" + strItemName + "'";
//System.out.println(sql);
            try {
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                if (rs.next()) {
                    System.out.print("in negtive stock chk for raw materials");
                    Double strOpeningStock = rs.getDouble("openingStock");
                    double openingStock = strOpeningStock;
                    int quantityNeeded = Integer.valueOf(strQuantity);
                    if (openingStock == 0) {
                        PaneLabel = strItemName + " is out of stock ";
                    } else if (openingStock < quantityNeeded) {
                        PaneLabel = "Available quantity for " + strItemName + " is only " + openingStock;
                    }
                }
            } catch (SQLException ex) {

                Logger.getLogger(OutTransaction.class.getName()).log(Level.SEVERE,
                        null, ex);
            } finally {
                try {
                    pst.close();
                    rs.close();
                } catch (SQLException ex) {
                }
            }
            sql = " select openingStock from FinishedGoods where itemName = '" + strItemName + "' or itemAlias = '" + strItemName + "'";
// System.out.println(sql);
            try {
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                if (rs.next()) {
                    Double strOpeningStock = rs.getDouble("openingStock");
                    double openingStock = strOpeningStock;
                    int quantityNeeded = Integer.valueOf(strQuantity);
                    if (openingStock == 0) {
                        PaneLabel = strItemName + " is out of stock ";
                    } else if (openingStock < quantityNeeded) {
                        PaneLabel = "Available quantity for " + strItemName + " is only " + openingStock;
                    }
                }

            } catch (SQLException ex) {

                Logger.getLogger(OutTransaction.class.getName()).log(Level.SEVERE,
                        null, ex);
            } finally {
                try {
                    pst.close();
                    rs.close();
                } catch (SQLException ex) {
                }
            }



            sql = " select Quantity from Packing where TypeOfPacking = '" + strPackingType + "' ";
// System.out.println(sql);
            try {
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String strOpeningStock = rs.getString("Quantity");
                    int openingStock = Integer.valueOf(strOpeningStock);
                    int quantityNeeded = Integer.valueOf(strPackingQuantity);
                    if (openingStock < quantityNeeded) {
                        PaneLabel = "Enter packing quantity less than or equal to " + openingStock + " for " + strItemName;
                    }
                }
            } catch (SQLException ex) {

                Logger.getLogger(OutTransaction.class.getName()).log(Level.SEVERE,
                        null, ex);
            } finally {
                try {
                    pst.close();
                    rs.close();
                } catch (SQLException ex) {
                }
            }

        }
    }

    private void jRadioButton1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButton1ItemStateChanged
        rawMaterialOptionChange();  // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ItemStateChanged

    private void jRadioButton2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButton2ItemStateChanged
        rawMaterialOptionChange();     // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton2ItemStateChanged

    private void jRadioButton3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButton3ItemStateChanged
        partyNameOptionChange();       // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton3ItemStateChanged

    private void jRadioButton4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButton4ItemStateChanged
        partyNameOptionChange();         // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton4ItemStateChanged

    private void DateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_DateChooser1PropertyChange
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar obj = DateChooser1.getCalendar();
        String dateNow = "";
        if (obj != null) {
            dateNow = formatter1.format(obj.getTime());
        }
        //System.out.println(dateNow1);
        TextDate.setText(dateNow);// TODO add your handling code here:
    }//GEN-LAST:event_DateChooser1PropertyChange

    private void TextQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextQuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextQuantityActionPerformed

    private void ComboBoxRawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxRawActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBoxRawActionPerformed

    private void ButtonAddItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonAddItemActionPerformed

        int count = 0;
        boolean chk = true;
        String packingQuantity = TextPackingQuantity.getText();
        String packingType = ComboBoxPackingType.getSelectedItem().toString();
        String rawMaterial = ComboBoxRaw.getSelectedItem().toString();
        String price = TextPrice.getText();
        String quantity = TextQuantity.getText();
        if (rawMaterial.equals("Select Raw Material")) {
            JOptionPane.showMessageDialog(null, "Please select raw material");
        } else if (rawMaterial.equals("Select Finished Goods")) {
            JOptionPane.showMessageDialog(null, "Please select finished goods ");
        } else if (!(Validation.qtyChk(quantity)).equals("")) {
            JOptionPane.showMessageDialog(null, Validation.qtyChk(quantity));
        } else if (!(Validation.priceChk(price)).equals("")) {
            JOptionPane.showMessageDialog(null, Validation.priceChk(price));
        } else if (!(Validation.qtyChk(packingQuantity)).equals("")) {
            JOptionPane.showMessageDialog(null, Validation.priceChk(price));
        } else if (packingType.equals("Select Packing Type")) {
            JOptionPane.showMessageDialog(null, "Please select Packing Type");
        } else {
            while (count < row) {

//                if (TableItem.getValueAt(count, 0).equals("Packing")) {
//                } else if (rawMaterial.equals(TableItem.getValueAt(count, 0))) {
//                    JOptionPane.showMessageDialog(null, "This item is already added");
//                    chk = false;
//                    break;
//                }
                count++;
            }
            if (chk) {
                TableItem.setValueAt(rawMaterial, row, 0);
                TableItem.setValueAt(quantity, row, 1);
                TableItem.setValueAt(price, row, 2);
                TableItem.setValueAt(packingType, row, 3);
                TableItem.setValueAt(packingQuantity, row, 4);
                row++;
            }
            comboBoxRawMaterialFiller();
            TextPrice.setText("");
            TextQuantity.setText("");
            TextPackingQuantity.setText("");
            comboBoxPackingTypeNameFiller();
            if (jRadioButton5.isSelected()) {
                TextQuantity.setText("0");
            }
        }

    }//GEN-LAST:event_ButtonAddItemActionPerformed

    public boolean duplicateValueChecker(String currentRawMaterial, String previousEnteredValue) {


        if (jRadioButton1.isSelected() == true) {
            // raw materials table

            try {
                pst = conn.prepareStatement("select * from RawMaterials where item = '" + currentRawMaterial + "' and itemAlias = '" + previousEnteredValue + "'");
                rs = pst.executeQuery();
                if (rs != null) {
                    return true;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    pst.close();
                    rs.close();
                } catch (SQLException ex) {
                }
            }


            try {
                pst = conn.prepareStatement("select * from RawMaterials where item = '" + previousEnteredValue + "' and itemAlias = '" + currentRawMaterial + "'");
                rs = pst.executeQuery();
                if (rs != null) {
                    return true;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    pst.close();
                    rs.close();
                } catch (SQLException ex) {
                }
            }


        } else {
            //finished goods table
            try {
                pst = conn.prepareStatement("select * from FinishedGoods where item = '" + currentRawMaterial + "' and itemAlias = '" + previousEnteredValue + "'");
                rs = pst.executeQuery();
                if (rs != null) {
                    return true;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    pst.close();
                    rs.close();
                } catch (SQLException ex) {
                }
            }


            try {
                pst = conn.prepareStatement("select * from FinishedGoods where item = '" + previousEnteredValue + "' and itemAlias = '" + currentRawMaterial + "'");
                rs = pst.executeQuery();
                if (rs != null) {
                    return true;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    pst.close();
                    rs.close();
                } catch (SQLException ex) {
                }
            }
        }



        return false;
    }

    private void ButtonDeleteItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDeleteItemActionPerformed
        if (TableItem.getSelectedRow() == -1 || TableItem.getSelectedRow() >= row) {
            JOptionPane.showMessageDialog(null, "Select an item to delete");
        } else {
            ((DefaultTableModel) TableItem.getModel()).removeRow(TableItem.getSelectedRow());
            Object[] abc = null;
            ((DefaultTableModel) TableItem.getModel()).addRow(abc);
            row--;
        }        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonDeleteItemActionPerformed

    private void DateChooserStartPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_DateChooserStartPropertyChange
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar obj = DateChooserStart.getCalendar();
        String dateNow = "";
        if (obj != null) {
            dateNow = formatter1.format(obj.getTime());
        }
        //System.out.println(dateNow1);
        TextStartDate.setText(dateNow);       // TODO add your handling code here:
    }//GEN-LAST:event_DateChooserStartPropertyChange

    private void DateChooserEndPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_DateChooserEndPropertyChange
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar obj = DateChooserEnd.getCalendar();
        String dateNow = "";
        if (obj != null) {
            dateNow = formatter1.format(obj.getTime());
        }
        //System.out.println(dateNow1);
        TextEndDate.setText(dateNow);   // TODO add your handling code here:
    }//GEN-LAST:event_DateChooserEndPropertyChange

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int j = 0;

        try {
            String strDate1 = TextStartDate.getText();
            String strDate2 = TextEndDate.getText();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date d1 = sdf.parse(strDate1);
            java.util.Date d2 = sdf.parse(strDate2);

            if (d1.before(d2)) {
                //System.out.println("d1 is before d2");
            } else if (d1.after(d2)) {
                j = 1;
                //System.out.println("d1 is after d2");
            } else {
                // System.out.println("d1 is equal to d2");
            }
        } catch (Exception e) {
            //
        }
        if (TextStartDate.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please select Start Date");
        } else if (TextEndDate.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please select End Date");
        } else if (j == 1) {
            JOptionPane.showMessageDialog(null, "End date is less than Start date");
        } else {
            try {
                String sql = "Select *  from transactionsOut where tdate between'" + TextStartDate.getText() + "' and '" + TextEndDate.getText() + "'";

                pst = conn.prepareStatement(sql);
                // System.out.print(sql);
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
        }// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        this.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void ButtonSubmitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ButtonSubmitKeyPressed
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
            jButton3.doClick();
        }//esc key
        
        else if(a == 113){
            ButtonSubmit.doClick();
        }//f2 key
        
        else if(a == 114){
            ButtonAdd.doClick();
        }//f3 key
    }//GEN-LAST:event_ButtonSubmitKeyPressed

    private void ButtonAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ButtonAddKeyPressed
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
            jButton3.doClick();
        }//esc key
        
        else if(a == 113){
            ButtonSubmit.doClick();
        }//f2 key
        
        else if(a == 114){
            ButtonAdd.doClick();
        }//f3 key// TODO add your handling code here:
    }//GEN-LAST:event_ButtonAddKeyPressed

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
        }   else if(a==27){
            jButton3.doClick();
        }//esc key
        
        else if(a == 113){
            ButtonSubmit.doClick();
        }//f2 key
        
        else if(a == 114){
            ButtonAdd.doClick();
        }//f3 key      // TODO add your handling code here:
    }//GEN-LAST:event_ButtonDeleteKeyPressed

    private void ButtonClearKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ButtonClearKeyPressed
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
            jButton3.doClick();
        }//esc key
        
        else if(a == 113){
            ButtonSubmit.doClick();
        }//f2 key
        
        else if(a == 114){
            ButtonAdd.doClick();
        }//f3 key     // TODO add your handling code here:
    }//GEN-LAST:event_ButtonClearKeyPressed

    private void ButtonRefreshKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ButtonRefreshKeyPressed
        int a = evt.getKeyCode();
        if (a == 10) {
            try {
                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
            } catch (AWTException ex) {
                Logger.getLogger(OutTransaction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }     else if(a==27){
            jButton3.doClick();
        }//esc key
        
        else if(a == 113){
            ButtonSubmit.doClick();
        }//f2 key
        
        else if(a == 114){
            ButtonAdd.doClick();
        }//f3 key    // TODO add your handling code here:
    }//GEN-LAST:event_ButtonRefreshKeyPressed

    private void ButtonDeleteItemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ButtonDeleteItemKeyPressed
        int a = evt.getKeyCode();
        if (a == 10) {
            try {
                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
            } catch (AWTException ex) {
                Logger.getLogger(OutTransaction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }     else if(a==27){
            jButton3.doClick();
        }//esc key
        
        else if(a == 113){
            ButtonSubmit.doClick();
        }//f2 key
        
        else if(a == 114){
            ButtonAdd.doClick();
        }//f3 key    // TODO add your handling code here:
    }//GEN-LAST:event_ButtonDeleteItemKeyPressed

    private void ButtonAddItemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ButtonAddItemKeyPressed
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
        else if(a == 113){
            ButtonSubmit.doClick();
        }
        
        else if(a == 114){
            ButtonAdd.doClick();
        }
    }//GEN-LAST:event_ButtonAddItemKeyPressed

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
        }    else if(a==27){
            jButton3.doClick();
        }//esc key
        
        else if(a == 113){
            ButtonSubmit.doClick();
        }//f2 key
        
        else if(a == 114){
            ButtonAdd.doClick();
        }//f3 key     // TODO add your handling code here:
    }//GEN-LAST:event_jButton1KeyPressed

    private void jButton3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton3KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3KeyReleased

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
        }    else if(a==27){
            jButton3.doClick();
        }//esc key
        
        else if(a == 113){
            ButtonSubmit.doClick();
        }//f2 key
        
        else if(a == 114){
            ButtonAdd.doClick();
        }//f3 key     // TODO add your handling code here:
    }//GEN-LAST:event_jButton3KeyPressed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        CreateConnection.DeleteAll("transactionsOut", conn);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6KeyPressed

    private void ComboBoxPackingTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxPackingTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBoxPackingTypeActionPerformed

    private void TextPackingQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextPackingQuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextPackingQuantityActionPerformed

    private void jRadioButton5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButton5ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton5ItemStateChanged

    private void TextAreaCommentsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextAreaCommentsKeyPressed
        int a = evt.getKeyCode();
        if (a == 9) {
            ButtonSubmit.grabFocus();
        }
    }//GEN-LAST:event_TextAreaCommentsKeyPressed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButton6ItemStateChanged
        partyNameOptionChange();       // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton6ItemStateChanged

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
 int a = evt.getKeyCode();
         if(a==27){
            jButton3.doClick();
        }
        else if(a == 113){
            ButtonSubmit.doClick();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyPressed

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
            java.util.logging.Logger.getLogger(OutTransaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OutTransaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OutTransaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OutTransaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                OutTransaction dialog = new OutTransaction(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton ButtonAdd;
    private javax.swing.JButton ButtonAddItem;
    private javax.swing.JButton ButtonClear;
    private javax.swing.JButton ButtonDelete;
    private javax.swing.JButton ButtonDeleteItem;
    private javax.swing.JButton ButtonRefresh;
    private javax.swing.JButton ButtonSubmit;
    private javax.swing.JComboBox ComboBoxPackingType;
    private javax.swing.JComboBox ComboBoxParty;
    private javax.swing.JComboBox ComboBoxRaw;
    private com.toedter.calendar.JDateChooser DateChooser1;
    private com.toedter.calendar.JDateChooser DateChooserEnd;
    private com.toedter.calendar.JDateChooser DateChooserStart;
    private javax.swing.JLabel LabelMaterialName;
    private javax.swing.JPanel PanelTools;
    private javax.swing.JTable Table;
    private javax.swing.JTable TableItem;
    private javax.swing.JTextArea TextAreaComments;
    private javax.swing.JTextField TextDate;
    private javax.swing.JTextField TextEndDate;
    private javax.swing.JTextField TextPackingQuantity;
    private javax.swing.JTextField TextPartyName;
    private javax.swing.JTextField TextPrice;
    private javax.swing.JTextField TextQuantity;
    private javax.swing.JTextField TextSearch;
    private javax.swing.JTextField TextStartDate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
