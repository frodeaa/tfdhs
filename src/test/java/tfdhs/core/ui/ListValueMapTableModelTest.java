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
}
