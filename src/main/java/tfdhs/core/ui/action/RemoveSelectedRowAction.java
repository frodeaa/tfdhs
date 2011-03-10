package tfdhs.core.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTable;

import tfdhs.core.ui.ListValueMapTableModel;

public final class RemoveSelectedRowAction extends AbstractAction {
    private final ListValueMapTableModel model;
    private final JTable table;

    public RemoveSelectedRowAction(String name,
    	ListValueMapTableModel model, JTable table) {
        super(name);
        this.model = model;
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        int rowSelected = table.getSelectedRow();
        if (rowSelected > -1) {
    	model.removeRow(rowSelected);
    	int rowCount = model.getRowCount();
    	int newSelect = Math.min(Math.max(0, rowSelected - 1),
    		Math.min(rowSelected, rowCount));
    	if (rowCount > 0) {
    	    table.setRowSelectionInterval(newSelect, newSelect);
    	}
        }
    }
}