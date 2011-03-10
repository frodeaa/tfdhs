package tfdhs.core.ui;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link ListValueMapTableModel}.
 * 
 * @author frode
 * 
 */
public class ListValueMapTableModelTest {

    private ListValueMapTableModel model;

    @Before
    public void setUp() {
	model = new ListValueMapTableModel();
    }

    @Test
    public void testUpdateModel() {

	Map<String, List<String>> data = new HashMap<String, List<String>>();
	data.put("Header1", Collections.nCopies(3, "Value1"));
	data.put("Header2", Collections.nCopies(3, "Value2"));

	model.updateModel(data);

	assertEquals("Model row count", 6, model.getRowCount());

    }

    @Test
    public void testGetData() {

	Map<String, List<String>> data = new HashMap<String, List<String>>();
	data.put("Header1", Collections.nCopies(3, "Value1"));
	data.put("Header2", Collections.nCopies(3, "Value2"));

	model.updateModel(data);

	Map<String, List<String>> dataRetrieved = model.getData();

	assertEquals("Data in not the same as data out", data, dataRetrieved);

    }

    @Test
    public void testAddNullTableModelListener() {

	model.addTableModelListener(null);

    }

    @Test
    public void testRemoveNullTableModelListener() {

	model.removeTableModelListener(null);

    }

    @Test
    public void testStringValue() {

	assertEquals("String value of null", "", model.stringValue(null));
	assertEquals("String value of empty String", "", model.stringValue(""));
	assertEquals("String value of number", "3", model.stringValue(3));
	assertEquals("String value of String with space", " word ",
		model.stringValue(" word "));

    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testRemoveRowWhenEmptyThrowsIndexOutOfBounds() {

	assertEquals("Model should be empty", 0, model.getRowCount());

	model.removeRow(0);

    }

    @Test
    public void testRemoveRow() {

	Map<String, List<String>> data = new HashMap<String, List<String>>();
	data.put("Header", Collections.nCopies(3, "Value"));
	model.updateModel(data);

	assertEquals("Model row count", 3, model.getRowCount());

	model.removeRow(0);
	assertEquals("Model row count", 2, model.getRowCount());

	model.removeRow(0);
	assertEquals("Model row count", 1, model.getRowCount());

	model.removeRow(0);
	assertEquals("Model row count", 0, model.getRowCount());

    }

    @Test
    public void testAddRow() {

	int newRowIndex = model.addRow();

	assertEquals("New row index", 0, newRowIndex);
	assertEquals("New row count", 1, model.getRowCount());

	newRowIndex = model.addRow();
	assertEquals("New row index", 1, newRowIndex);
	assertEquals("New row count", 2, model.getRowCount());

    }

    @Test
    public void testSetGetValueAt() {

	int row = model.addRow();

	model.setValueAt("header", row, 0);
	assertEquals("header", model.getValueAt(row, 0));

	model.setValueAt("value", row, 1);
	assertEquals("value", model.getValueAt(row, 1));

    }

    @Test
    public void testIsCellEditable() {

	int row = model.addRow();

	assertTrue("Not editable", model.isCellEditable(row, 0));
	assertTrue("Not editable", model.isCellEditable(row, 1));

    }

    @Test
    public void testGetColumnName() {

	assertEquals("Column 0", "Header Name", model.getColumnName(0));
	assertEquals("Column 1", "Header Value", model.getColumnName(1));

    }

    @Test
    public void testGetColumnCount() {

	assertEquals("Column count", 2, model.getColumnCount());

    }

    @Test
    public void testGetColumnClass() {

	assertEquals("Column 0 class", String.class, model.getColumnClass(0));
	assertEquals("Column 1 class", String.class, model.getColumnClass(1));

    }
}
