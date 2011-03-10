package tfdhs.core.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * TableModel for a map of list, where the key will be the value in the first
 * row and for each value in the list a row will be created with the key/value.
 * 
 * @author frode
 * 
 */
public class ListValueMapTableModel implements TableModel {

    private final DefaultTableModel model;

    /**
     * Create a new Header Field Table model with two columns, the name of the
     * header and its value.
     */
    public ListValueMapTableModel() {
	model = new DefaultTableModel(new String[] { "Header Name",
		"Header Value" }, 0) {
	    @Override
	    public Class<?> getColumnClass(int column) {
		return String.class;
	    }

	};
    }

    /**
     * Update the model to contain the (key,listItem) found in the map. Adds a
     * row for each element found in the list of key values.
     * 
     * @param data
     *            the data to update the table model with.
     */
    public void updateModel(Map<String, List<String>> data) {
	model.setRowCount(0);
	for (Entry<String, List<String>> header : data.entrySet()) {
	    for (String value : header.getValue()) {
		model.addRow(new Object[] { header.getKey(), value });
	    }
	}

	model.fireTableRowsInserted(0, model.getRowCount());
    }

    /**
     * Insert (key,value) to map, will create a new key->list entry if the map
     * does not contain the key. The list that the key maps to will contain
     * duplicates if insert is called multiple times with same (key,value) pair.
     * 
     * @param key
     *            the key to add to the map.
     * @param value
     *            the value to add to the list that the key maps to.
     * @param map
     *            the map to insert (key,value) into.
     */
    protected static void insert(String key, String value,
	    Map<String, List<String>> map) {
	if (!map.containsKey(key)) {
	    map.put(key, new ArrayList<String>());
	}
	map.get(key).add(value);
    }

    /**
     * Create a map where all unique names (first row) will be a map key and all
     * values that shares same key will be added to the map value list. Key and
     * list values can be an empty String but never <code>null</code>.
     * 
     * @return a map created from the table data.
     */
    public Map<String, List<String>> getData() {
	Map<String, List<String>> headerMap = new HashMap<String, List<String>>();
	for (int i = 0; i < model.getRowCount(); i++) {
	    String header = stringValue(model.getValueAt(i, 0));
	    String value = stringValue(model.getValueAt(i, 1));

	    insert(header, value, headerMap);
	}
	return headerMap;
    }

    /**
     * @param object
     *            the object to get String value of.
     * @return String value of an object, empty String if the object is
     *         <code>null</code>.
     */
    protected String stringValue(Object object) {
	return object == null ? "" : "" + object;
    }

    /**
     * Adds a new empty row to the table model.
     * 
     * @return the row index of the new row that was added.
     */
    public int addRow() {
	model.addRow(new String[] {});
	return model.getRowCount() - 1;
    }

    /**
     * Removes the row at row from the model. Notification of the row being
     * removed will be sent to all the listeners.
     * 
     * @param row
     *            the row to remove.
     * @throws ArrayIndexOutOfBoundsException
     *             if the row was invalid
     */
    public void removeRow(int row) {
	model.removeRow(row);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRowCount() {
	return model.getRowCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTableModelListener(TableModelListener l) {
	model.addTableModelListener(l);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getColumnClass(int column) {
	return model.getColumnClass(column);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getColumnCount() {
	return model.getColumnCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getColumnName(int column) {
	return model.getColumnName(column);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getValueAt(int row, int column) {
	return model.getValueAt(row, column);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCellEditable(int row, int column) {
	return model.isCellEditable(row, column);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeTableModelListener(TableModelListener l) {
	model.removeTableModelListener(l);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValueAt(Object aValue, int row, int column) {
	model.setValueAt(aValue, row, column);
    }

}
