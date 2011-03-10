package tfdhs.core.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.table.DefaultTableModel;

/**
 * TableModel for a map of list, where the key will be the value in the first
 * row and for each value in the list a row will be created with the key/value.
 * 
 * @author frode
 * 
 */
public class ListValueMapTableModel {

    private final DefaultTableModel model;

    /**
     * Create a new Header Field Table model with two columns, the name of the
     * header and its value.
     */
    public ListValueMapTableModel() {
	model = new DefaultTableModel(new String[] { "Header Name",
		"Header Value" }, 0);
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

    public int getRowCount() {
	return model.getRowCount();
    }

}
