package org.elephant.mapper.ui;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class LpcObjectEditableArrayTable extends DefaultTableModel{
    public LpcObjectEditableArrayTable(String columnName) {
        super();
        setColumnCount(1);
        setRowCount(2);
    }



    @Override
    public boolean isCellEditable(int row, int column) {
        return true;
    }

    public void reset() {
        setRowCount(0);
        setRowCount(2);
    }

    public void refresh(List<String> values) {
        setRowCount(0);
        if(values==null || values.size()==0) {
            reset();
        }
        for(String str: values) {
            String[] row = {str};
            addRow(row);
        }
    }

    public List<String> getValues() {
        List<String> ret = new ArrayList<>();
        for(Object row: getDataVector()) {
            if((String)((Vector)row).get(0) != null && ((String) ((Vector)row).get(0)).length()>0) {
                ret.add((String) ((Vector) row).get(0));
            }
        }
        return ret;
    }

}
