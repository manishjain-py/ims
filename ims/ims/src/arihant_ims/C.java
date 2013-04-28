/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arihant_ims;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class C extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int m, int n) {
        return false;
    }
    
    public static C getModel(ResultSet rs) throws SQLException {

        C dataModel = new C();

        try {

            //create an array of column names
            ResultSetMetaData mdata = rs.getMetaData();
            int colCount = mdata.getColumnCount();
            String[] colNames = new String[colCount];
            for (int i = 1; i <= colCount; i++) {
                colNames[i - 1] = mdata.getColumnName(i);
            }
            dataModel.setColumnIdentifiers(colNames);

            //now populate the data
            while (rs.next()) {
                String[] rowData = new String[colCount];
                for (int i = 1; i <= colCount; i++) {
                    rowData[i - 1] = rs.getString(i);
                }
                dataModel.addRow(rowData);

            }
            return (dataModel);
        } finally {

            try {
                rs.close();
            } catch (SQLException e) {
            }
            return (dataModel);
        }
    }
}
