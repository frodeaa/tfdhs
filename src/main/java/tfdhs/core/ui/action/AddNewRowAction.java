package tfdhs.core.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTable;

import tfdhs.core.ui.ListValueMapTableModel;

public final class AddNewRowAction extends AbstractAction {
    private final ListValueMapTableModel model;
    private final JTable table;

    public AddNewRowAction(String arg0, ListValueMapTableModel model,
	    JTable table) {
	super(arg0);
	this.model = model;
	this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
	int newRow = model.addRow();
	table.setRowSelectionInterval(newRow, newRow);
    }
}