package org.elephant.mapper.ui;

import org.elephant.mapper.EleExportableCollection;
import org.elephant.mapper.Exportable;
import org.elephant.mapper.Monster;

import javax.swing.table.DefaultTableModel;

public class MonsterTableModel extends DefaultTableModel {
    String [] columnNames = {"UUID", "Name"};
    public MonsterTableModel(EleExportableCollection<Monster> monsters) {
        super();
        this.setColumnIdentifiers(columnNames);
        refresh(monsters);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public void refresh(EleExportableCollection<Monster> monsters) {
        setRowCount(0);
        for (Exportable monster: monsters) {
            Monster mon = (Monster)monster;
            String[] row = {mon.getUuid(), mon.getName()};
            addRow(row);
        }
    }
}
